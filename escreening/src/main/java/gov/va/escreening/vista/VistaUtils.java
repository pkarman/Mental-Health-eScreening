package gov.va.escreening.vista;

import gov.va.escreening.domain.NameDto;
import gov.va.escreening.vista.dto.VistaDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VistaUtils {

    private static final Logger logger = LoggerFactory.getLogger(VistaUtils.class);

    /**
     * Converts a VistA date time string to a date object.
     * @param vistaDateString
     * @return
     */
    public static Date convertVistaDate(String vistaDateString) {
        Date date = null;

        if (StringUtils.isBlank(vistaDateString)) {
            return date;
        }

        try {
            String dateComponent = null;
            String timeComponent = null;

            if (vistaDateString.contains(";")) {
                String[] components = StringUtils.splitPreserveAllTokens(vistaDateString, ';');
                if (components.length != 3) throw new IllegalArgumentException("VistaDateString(\"+ vistaDateString+\") format is not valid.  Valid format is \"<Location IEN>;<Date/Time>;<Service Category>\" i.e. 32;3140422.165720;X");
                components = StringUtils.splitPreserveAllTokens(components[1], '.');
                dateComponent = components[0];
                timeComponent = components[1];

            } else if (vistaDateString.contains(".")) {
                String[] components = StringUtils.splitPreserveAllTokens(vistaDateString, '.');
                if (components.length != 2) throw new IllegalArgumentException("VistaDateString(\"+ vistaDateString+\") format is not valid.  Valid format is \"YYYMMDD.HHMMSS\" i.e. 3140422.165720");
                dateComponent = components[0];
                timeComponent = components[1];
            }
            else if (vistaDateString.length() == 7) {
                dateComponent = vistaDateString;
                timeComponent = "000000";
            } else {
                throw new IllegalArgumentException("VistaDateString("+ vistaDateString+") format is not supported.  There are 3 supported formats for a vista date string:\nValid format is \"<Location IEN>;<Date/Time>;<Service Category>\" i.e. 32;3140422.165720;X\nValid format is \"YYYMMDD.HHMMSS\" i.e. 3140422.165720\nValid format is \"YYYMMDD\" i.e. 3140422");
            }

            if (timeComponent == null) {
                timeComponent = "000000";
            }
            else {
                timeComponent = StringUtils.rightPad(timeComponent, 6, "0");
            }

            String year = StringUtils.left(dateComponent, 3);
            dateComponent = (1700 + Integer.valueOf(year)) + StringUtils.substring(dateComponent, 3) + "."
                    + timeComponent;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd.HHmmss");
            date = sdf.parse(dateComponent);
        } catch (ParseException e) {
            logger.error("Failed to convert VistA date string: " + vistaDateString, e);
            throw new IllegalArgumentException("VistaDateString("+ vistaDateString+") format is not supported.  There are 3 supported formats for a vista date string:\nValid format is \"<Location IEN>;<Date/Time>;<Service Category>\" i.e. 32;3140422.165720;X\nValid format is \"YYYMMDD.HHMMSS\" i.e. 3140422.165720\nValid format is \"YYYMMDD\" i.e. 3140422");
        }

        return date;
    }

    /**
     * Breaks up a component of a VistA veteran name string and returns a NameDto object.
     * @param vistaNameString
     * @return
     */
    public static NameDto convertVistaName(String vistaNameString) {

        if (StringUtils.isBlank(vistaNameString)) {
            return null;
        }

        NameDto nameDto = new NameDto();

        // Name
        String[] nameFields = StringUtils.splitPreserveAllTokens(vistaNameString, ',');

        // 1. Last Name
        nameDto.setLastName(nameFields[0]);

        if (nameFields.length > 1) {
            // 2. First Name and Middle Name. User can have > 1 middle name separated by " "
            String[] firstMiddleNameComponents = StringUtils.split(nameFields[1], " ", 2);

            nameDto.setFirstName(firstMiddleNameComponents[0]);

            if (firstMiddleNameComponents.length > 1) {
                nameDto.setMiddleName(firstMiddleNameComponents[1]);
            }
        }

        return nameDto;
    }

    /**
     * Reads the data portion of a string based on the data label.
     * @param text
     * @param dataLabel
     * @return
     */
    public static String readDataFromText(String text, String dataLabel) {

        int pos = StringUtils.indexOf(text, dataLabel);

        StringBuilder sb = new StringBuilder();

        if (pos > -1) {
            pos += dataLabel.length();

            for (int i = pos; i < text.length(); ++i) {

                if (StringUtils.isBlank(text.charAt(i) + "")) {
                    break;
                }
                else {
                    sb.append(text.charAt(i));
                }
            }
        }

        if ("UNSPECIFIED".equals(sb.toString())) {
            return null;
        }
        else {
            return sb.toString();
        }
    }

    /**
     * Converts a date into a VistA date time string.
     * @param date
     * @return
     */
    public static String convertToVistaDateString(Date date, VistaDateFormat dateformat) {

        if (date == null) {
            return null;
        }

        DateTime dateTime = new DateTime(date);
        return (dateTime.getYear() - 1700) + dateTime.toString(dateformat.getFormat());
    }

    /**
     * Convenience method that gets the Vista Date from the Vista Visit String.
     *
     * @param vistaVisitString  Represents the Vista Visit String for which the Vista Date is in.
     * @return  The Vista Date as a String.
     */
    public static String getVistaDateString(String vistaVisitString) {
        String[] vistaVisitStringArray = vistaVisitString.split(";");
        if(vistaVisitStringArray.length != 3) {
            String errorMsg = "Invalid Vista Visit String ("+vistaVisitString+").  Valid format is '[Clinic IEN];[Visit Date String];[Service Category]";
            throw new IllegalArgumentException(errorMsg);
        }
        return vistaVisitStringArray[1];
    }

    /**
     * Convenience method that gets the Clinic IEN from the Vista Visit String.
     *
     * @param vistaVisitString  Represents the Vista Visit String for which the Clinic IEN is in.
     * @return  The Clinic IEN as a String.
     */
    public static String getClinicIEN(String vistaVisitString) {
        String[] vistaVisitStringArray = vistaVisitString.split(";");
        if(vistaVisitStringArray.length != 3) {
            String errorMsg = "Invalid Vista Visit String ("+vistaVisitString+").  Valid format is '[Clinic IEN];[Visit Date String];[Service Category]";
            throw new IllegalArgumentException(errorMsg);
        }
        return vistaVisitStringArray[0];
    }
}
