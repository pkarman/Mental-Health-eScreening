package gov.va.escreening.vista.extractor;

import com.google.common.base.Throwables;
import gov.va.escreening.vista.VistaUtils;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrqqpxRemindersExtractor implements VistaRecordExtractor<VistaVeteranClinicalReminder> {
    private static Logger logger = LoggerFactory.getLogger(OrqqpxRemindersExtractor.class);
    public static final SimpleDateFormat clinicalReminderDueDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    @Override
    public VistaVeteranClinicalReminder extractData(String record) {
        // IEN^PRINT NAME^DUE DATE/TIME^LAST OCCURRENCE DATE/TIME
        // 500047^Hepatitis C risk Factor Screening^DUE NOW^^2^1^1^^^^0

        if (StringUtils.isBlank(record)) {
            return null;
        }

        VistaVeteranClinicalReminder vistaVeteranClinicalReminder = new VistaVeteranClinicalReminder();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        // 0. IEN
        vistaVeteranClinicalReminder.setClinicalReminderIen(fields[0]);

        // 1. Name
        vistaVeteranClinicalReminder.setName(fields[1]);

        // 2. Due Date
        if (StringUtils.isNotBlank(fields[2])) {
            if (!"DUE NOW".equalsIgnoreCase(fields[2])) {
                Date dueDate = VistaUtils.convertVistaDate(fields[2]);

                if (dueDate != null) {
                    vistaVeteranClinicalReminder.setDueDateString(clinicalReminderDueDateFormat.format(dueDate));
                }
            }
            else {
                vistaVeteranClinicalReminder.setDueDateString(fields[2]);
            }
        }
        // So, if piece 6 (5th index) is set to 1... then the reminder is “Due”. We can see this reflected in the document Liz sent....
        //918^Evaluation of + PTSD Screen^DUE NOW^^2^1^1^^^^1     <—Says “Due Now”, and piece 6 is set to “1” for “Due”
        //<—snip—>
        //265^Screen for PTSD^3151124.083813^3141124.083813^2^1^1^^^^1  <—Says due “3141124.083813”, and piece 6 is set to “1” for “Due”
        int dueNowStatus=0; // not due
        try {
            dueNowStatus = Integer.parseInt(fields[5]);
        }catch(NumberFormatException nfe){
            logger.error(Throwables.getRootCause(nfe).getMessage());
        }
        vistaVeteranClinicalReminder.setDueNow(dueNowStatus==1);

        return vistaVeteranClinicalReminder;
    }

}
