package gov.va.escreening.expressionevaluator;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.*;

public class CustomCalculations {
	private static final String NUMBER_FORMAT = "%sf";
    public static final String DEFAULT_VALUE = "notset";
	
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
	
	/**
	 * Gets the numerical value of the given list-typed variable.
	 * For select measures this means the calculation vaule.
	 * For text answers it means the veteran entered value
	 * @param var
	 * @return
	 */
	//TODO: See if we should move this to FormulaAssessmentVariableResolverImpl
	public static String getListVariableAsNumber(AssessmentVariableDto var){
	    if(var == null){
	        return DEFAULT_VALUE;
	    }
	    String result;
	    
	    if(var.getMeasureTypeId() != null){
    	    switch(var.getMeasureTypeId()){
    	        case AssessmentConstants.MEASURE_TYPE_FREE_TEXT:
    	             result = getFreeTextAnswer(var, DEFAULT_VALUE);
    	             if(result != null && result != DEFAULT_VALUE){
    	                 return String.format(NUMBER_FORMAT, result);
    	             }
    	             break;
    	        case AssessmentConstants.MEASURE_TYPE_SELECT_ONE:
    	            AssessmentVariableDto response = getSelectOneAnswerVar(var);
    	            if(response == null){
    	                return DEFAULT_VALUE;
    	            }
    	            result = response.getCalculationValue();
    	            if(result == null){
    	                result = response.getOtherText();
    	            }
    	            if(result != null){
    	                return String.format(NUMBER_FORMAT, result);
    	            }
    	            break;
    	        case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI:// we could sum the calculation values
    	        default: 
    	            return "[Error: unsupported question type]";
    	    }
	    }
	    else{
	        return "[Error: unsupported question type]";
	    }
	    
	    return DEFAULT_VALUE;
	}
	
	//TODO: See if we should move this to FormulaAssessmentVariableResolverImpl
	public static String getVariableValue(AssessmentVariableDto var){
	    if(var == null || var.getValue() == null){
	        return DEFAULT_VALUE;
	    }
	    if(var.getValue() == "true" || var.getValue() == "false" ){
	        return var.getValue();
	    }
	    
	    try{
	        Double.valueOf(var.getValue());
	        return String.format(NUMBER_FORMAT, var.getValue());
	    }
	    catch(Exception e){ /* ignore */}

	    return var.getValue();
	}
	
	public static String getFreeTextAnswer(AssessmentVariableDto var, String defaultValue){	    
	    AssessmentVariableDto answer = getFirstAnswer(var);
	    if(answer == null){
	        return DEFAULT_VALUE;
	    }
	    
	    String result = getResponseText(answer);
	    if(result != DEFAULT_VALUE){
	        return result;
	    }
	    
	    if(answer.getValue() != null){
	        return answer.getValue();
	    }
	    
	    return defaultValue;
	}
	
	/**
	 * Extracts a value for a variable (will not work on parent type variables)<br/>
	 * Logic taken from Freemarker template helper functions.
	 * @param var
	 * @return
	 */
	public static String getResponseText(AssessmentVariableDto var){
	    if(var == null){
	        return DEFAULT_VALUE;
	    }
	    
	    if(!Strings.isNullOrEmpty(var.getOverrideText())){
	        return var.getOtherText();
	    }
	    
	    if(!Strings.isNullOrEmpty(var.getOverrideText())){
	        return var.getOverrideText();
	    }
	    
	    if(!Strings.isNullOrEmpty(var.getDisplayText())){
            return var.getDisplayText();
        }
	    
	    return DEFAULT_VALUE;
	}
	
	public static AssessmentVariableDto getSelectOneAnswerVar(AssessmentVariableDto var){
	    return getFirstAnswer(var);
	}
	
	/**
	 * Extract the 
	 * @param var
	 * @return
	 */
	private static AssessmentVariableDto getFirstAnswer(AssessmentVariableDto var){
	    if(var == null || var.getChildren() == null || var.getChildren().isEmpty()){
	        return null;
	    }
	    
	    return var.getChildren().get(0);
	}
	
	
}