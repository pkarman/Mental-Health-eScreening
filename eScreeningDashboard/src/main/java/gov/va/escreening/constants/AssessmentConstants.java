package gov.va.escreening.constants;



/* PLEASE: define all as final */


public class AssessmentConstants {
	
	//if display order is not specified then this value is used.
	public static final int ASSESSMENT_VARIABLE_DEFAULT_COLUMN = 0; 
	
	//Assessement Variable types
	public static final int ASSESSMENT_VARIABLE_TYPE_MEASURE = 1;
	public static final int ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER = 2;
	public static final int ASSESSMENT_VARIABLE_TYPE_CUSTOM = 3;
	public static final int ASSESSMENT_VARIABLE_TYPE_FORMULA = 4;
	
	//Assessment events
	public static final int ASSESSMENT_EVENT_CREATED = 1;
	public static final int ASSESSMENT_EVENT_SURVEY_ASSIGNED = 2;
	public static final int ASSESSMENT_EVENT_UPDATED = 3;
	public static final int ASSESSMENT_EVENT_MARKED_COMPLETED = 4;
	public static final int ASSESSMENT_EVENT_MARKED_FINALIZED = 5;
	public static final int ASSESSMENT_EVENT_VISTA_SAVE = 6;

	//Measure Answer Constants
	public static final int MEASURE_TYPE_FREE_TEXT = 1;
	public static final int MEASURE_TYPE_SELECT_ONE = 2;
	public static final int MEASURE_TYPE_SELECT_MULTI = 3;
	public static final int MEASURE_TYPE_TABLE = 4;
	public static final int MEASURE_TYPE_READ_ONLY = 5;
	public static final int MEASURE_TYPE_SELECT_ONE_MATRIX = 6;
	public static final int MEASURE_TYPE_SELECT_MULTI_MATRIX = 7;
	public static final int MEASURE_TYPE_INSTRUCTION = 8;

	//Measure Constants
	public static final int MEASURE_IDENTIFICATION_FIRST_NAME_ID = 1;
	public static final int MEASURE_IDENTIFICATION_MIDDLE_NAME_ID = 2;
	//public static final int MEASURE_IDENTIFICATION_SUFFIX_ID = 3;
	public static final int MEASURE_IDENTIFICATION_LAST_NAME_ID = 4;
	public static final int MEASURE_IDENTIFICATION_SSN_LAST_FOUR = 5;
	public static final int MEASURE_IDENTIFICATION_EMAIL = 6;
	public static final int MEASURE_IDENTIFICATION_PHONE_= 7;
	public static final int MEASURE_IDENTIFICATION_CALL_TIME = 8;

	//Person type constants
	public static final int PERSON_TYPE_USER = 1;
	public static final int PERSON_TYPE_VETERAN = 2;
	
	//Survey Constants
	public static final int SURVEY_IDENTIFICATION_ID = 1;

}
