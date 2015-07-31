package gov.va.escreening.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kliu on 3/23/15.
 */
public class ReportRepositoryUtil {

    public static Date getDateFromString(String str){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            return simpleDateFormat.parse(str);
        }
        catch(ParseException e){
            return null;
        }

    }
}
