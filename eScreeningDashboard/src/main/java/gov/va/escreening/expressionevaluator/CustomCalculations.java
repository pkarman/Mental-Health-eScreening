package gov.va.escreening.expressionevaluator;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class CustomCalculations {
	
	public static final String CALCULATE_AGE = "calculateAge";
	
	public static String calculateAge(String veteranBirthDate) {
		DateTime today = DateTime.now();
	     
		DateTimeFormatter fmt = DateTimeFormat.forPattern("MM/dd/yyyy");
		DateTime birthDate = fmt.parseDateTime(veteranBirthDate);
		    
		Interval interval = new Interval(birthDate, today);
		Period period = interval.toPeriod();
	    
		int years = period.getYears();
		return String.valueOf(years);
	}
	
}