package gov.va.escreening.dto.dashboard;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportFieldHelper {
	
	public static String convertDataSourceDateToReportFieldString(Date assessmentDate) {
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
		String reportDate = df.format(assessmentDate);
		return reportDate;
	} 
}
