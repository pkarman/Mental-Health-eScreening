package gov.va.escreening.variableresolver;

import java.text.SimpleDateFormat;

public interface CustomAssessmentVariableResolver extends VariableResolver {
	public static final Integer CUSTOM_PACKET_VERSION_VARIABLE_ID = 1;
	public static final Integer CUSTOM_ASSIGNED_CLINICIAN_VARIABLE_ID = 2;
	public static final Integer CUSTOM_SIGNING_CLINICIAN_VARIABLE_ID = 3;
	public static final Integer CUSTOM_TODAYS_DATE = 4;
	public static final Integer CUSTOM_ASSESSMENT_DURATION = 5;
	public static final Integer CUSTOM_VETERAN_APPOINTMENTS = 6;
	public static final Integer CUSTOM_ASSESSMENT_LAST_MODIFIED = 7;
	
	public static final Integer SYSTEM_PROPERTIES_ESCREENING_PACKET_VERSION_ID = 1;
	
	public static final SimpleDateFormat APPOINTMENT_DATE_FORMAT = new SimpleDateFormat("MM-dd-yy@hh:mm");
	public static final SimpleDateFormat TODAYS_DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");
	
}
