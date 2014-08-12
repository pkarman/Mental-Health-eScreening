package gov.va.escreening.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidationHelper {
	
	public static boolean isFieldPopulated(String fieldValue) {
		if(fieldValue==null || fieldValue.isEmpty())
			return false;
		return true;
	}
	
	public static boolean doesFieldContainAValidEmailAddress(String fieldValue) {
		if(fieldValue==null || fieldValue.isEmpty())
			return false;
		
		String emailPattern = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(fieldValue);
		boolean doesItMatch = matcher.matches();
		return doesItMatch;
	}	
	
	
	/* At least 8 chars
	Contains at least one digit
	Contains at least one lower alpha char and one upper alpha char
	Contains at least one char within a set of special char (@#%$^ etc.)
	Not containing blank, tab etc.  */
	public static boolean isFieldValidPassword(String fieldValue) {
		if(fieldValue==null || fieldValue.isEmpty())
			return false;
		
		String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		Pattern pattern = Pattern.compile(passwordPattern);
		Matcher matcher = pattern.matcher(fieldValue);
		boolean doesItMatch = matcher.matches();
		return doesItMatch;
	}
}
