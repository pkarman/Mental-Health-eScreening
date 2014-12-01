package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor={CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class, 
		UnsupportedOperationException.class, CouldNotResolveVariableValueException.class, UnsupportedOperationException.class, Exception.class})
public class MeasureAnswerAssessmentVariableResolverImpl implements MeasureAnswerAssessmentVariableResolver {
	
    @Autowired 
    private SurveyMeasureResponseRepository surveyMeasureResponseRepository;
    
    //TODO refactor this to use the enum instead.
    public static final int CALCULATION_TYPE_NUMBER = 1;
    public static final int CALCULATION_TYPE_USER_ENTERED_NUMBER = 2;
    public static final int CALCULATION_TYPE_USER_ENTERED_STRING = 3;
    public static final int CALCULATION_TYPE_USER_ENTERED_BOOLEAN = 4;
    
    private static final Logger logger = LoggerFactory.getLogger(MeasureAnswerAssessmentVariableResolverImpl.class);
    
    /*	new AssessmentVariable(70, "var70", "string", "answer_270", "ACCOUNTANT", "ACCOUNTANT", null, null));  */
    @Override
	public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable, 
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash) {
		
		MeasureAnswer measureAnswer = assessmentVariable.getMeasureAnswer();
		if(measureAnswer == null)
			throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type MeasureAnswer did not reference a MeasureAnswer"
			  + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));
		
		SurveyMeasureResponse response = surveyMeasureResponseRepository.findSmrUsingPreFetch(veteranAssessmentId, measureAnswer.getMeasureAnswerId(), null);
		if(response == null)
			throw new CouldNotResolveVariableException(String.format("There was not a MeasureAnswer reponse for MeasureAnswerId: %s, assessmentId: %s", 
				measureAnswer.getMeasureAnswerId(), veteranAssessmentId));
		
		Integer id = assessmentVariable.getAssessmentVariableId();
		String variableName = String.format("var%s", id);
		String type = getVariableTypeString(measureAnswer.getAnswerType());
		String displayName = String.format("answer_%s", measureAnswer.getMeasureAnswerId()  );
		String value = getValue(response, veteranAssessmentId);
		String displayText = getDisplayText(type, measureAnswer, response, veteranAssessmentId);
		String overrideText = getOverrideText(response, measureAnswerHash);  
		String otherText = getOtherText(response);
		Integer column = getColumn(measureAnswer);	
		Integer row = response.getTabularRow();
		String calcValue = getMeasureAnswerCalculationValue(measureAnswer);
		String otherValue = response.getOtherValue();
		
		AssessmentVariableDto variableDto = 
			new AssessmentVariableDto(id, variableName, type, displayName, value, displayText, overrideText, otherText, calcValue, column, row, otherValue);
		variableDto.setAnswerId(measureAnswer.getMeasureAnswerId());
		return variableDto;
	}
    
	@Override
	public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable parentAssessmentVariable, 
			SurveyMeasureResponse response, Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash) {
		    	
		Integer id = getAnswerAssessmentVariableId(parentAssessmentVariable, response, measureAnswerHash);
		String variableName = String.format("var%s", id);
		String type = getVariableTypeString(response.getMeasureAnswer().getAnswerType());
		String displayName = String.format("answer_%s", response.getMeasureAnswer().getMeasureAnswerId());
		
		String value = getValue(response, veteranAssessmentId);
		String displayText = getDisplayText(type, response.getMeasureAnswer(), response, veteranAssessmentId);
		String overrideText = getOverrideText(response, measureAnswerHash);  
		String otherText = getOtherText(response);
		Integer column = getColumn(response.getMeasureAnswer());
		Integer row = response.getTabularRow();
		String calcValue = response.getMeasureAnswer().getCalculationValue();
		String otherValue = response.getOtherValue();
		AssessmentVariableDto variableDto = 
			new AssessmentVariableDto(id, variableName, type, displayName, value, displayText, overrideText, otherText, calcValue, column, row, otherValue);
		
		variableDto.setAnswerId(response.getMeasureAnswer().getMeasureAnswerId());
		
		return variableDto;
	}
    
    @Override
    public String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId) {
    	
		MeasureAnswer measureAnswer = assessmentVariable.getMeasureAnswer();
		if(measureAnswer == null)
			throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type MeasureAnswer did not reference a MeasureAnswer"
			  + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));

    	SurveyMeasureResponse response = surveyMeasureResponseRepository.findSmrUsingPreFetch(veteranAssessmentId, measureAnswer.getMeasureAnswerId(), null);
    		if(response == null)
    			throw new CouldNotResolveVariableValueException(String.format("There was no MeasureAnswer reponse for MeasureAnswerId: %s, assessmentId: %s", 
    				measureAnswer.getMeasureAnswerId(), veteranAssessmentId));
    	
    	String calcValue =  resolveCalculationValue(assessmentVariable, veteranAssessmentId, response);
    	logger.debug("Resolved assessment variable {} to value {}", assessmentVariable, calcValue);
    	return calcValue;
    }
    
    @Override
    public String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, SurveyMeasureResponse response) {

		MeasureAnswer measureAnswer = response.getMeasureAnswer();
		if(measureAnswer == null)
			throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type MeasureAnswer did not reference a MeasureAnswer"
			  + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));

		String resolvedValue = measureAnswer.getCalculationType() == null ?
		        resolveResponseValueWithOutCalulationType(response) :
		        resolveResponseUsingCalculationType(assessmentVariable, veteranAssessmentId, response);
		
		if(resolvedValue == null){
		    throw new CouldNotResolveVariableValueException(String.format("MeasureAnswer specified having a calculation type of '%s' but the "
                + "value was not found for the associated SurveyMeasureResponse: %s, MeasureAnswerId: %s, assessmentId: %s", 
                measureAnswer.getCalculationType(), response.getSurveyMeasureResponseId(), measureAnswer.getMeasureAnswerId(), veteranAssessmentId));
		}
    	return resolvedValue;
    }
    
    private String resolveResponseUsingCalculationType(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, SurveyMeasureResponse response){
        MeasureAnswer measureAnswer = response.getMeasureAnswer();
        int calculationType = measureAnswer.getCalculationType().getCalculationTypeId();
        switch(measureAnswer.getCalculationType().getCalculationTypeId()) {
            case CALCULATION_TYPE_USER_ENTERED_NUMBER:
                Long numValue = response.getNumberValue();
                if(numValue != null){ 
                    //treat numbers as floats to handle decimals when dividing numbers
                    return String.format("%sf", String.valueOf(numValue));
                }
                break;
            case CALCULATION_TYPE_USER_ENTERED_BOOLEAN:
                Boolean boolValue = response.getBooleanValue();
                if(boolValue != null) 
                   return String.valueOf(boolValue);  
                break;
            case CALCULATION_TYPE_USER_ENTERED_STRING:
                return response.getTextValue();
            case CALCULATION_TYPE_NUMBER:               
                String calcValue = getMeasureAnswerCalculationValue(measureAnswer);
                if(calcValue != null)
                    //treat numbers as floats to handle decimals when dividing numbers
                    return String.format("%sf", calcValue);
                break;
            default:
                throw new UnsupportedOperationException(String.format("Referenced calculation type of: %s is not supported.  AssessmentVariableid: %s, assessmentId: %s", 
                    calculationType, assessmentVariable.getAssessmentVariableId(), veteranAssessmentId));
        }
        return null;
    }
    
    private String resolveResponseValueWithOutCalulationType(SurveyMeasureResponse response){
        
        Long numValue = response.getNumberValue();
        if(numValue != null){
            //treat numbers as floats to handle decimals when dividing numbers
            return String.format("%sf", String.valueOf(numValue));
        }
        Boolean boolValue = response.getBooleanValue();
        if(boolValue != null){
            return String.valueOf(boolValue);
        }
        String strValue = response.getTextValue();
        if(strValue != null){
            return strValue;
        }
        MeasureAnswer measureAnswer = response.getMeasureAnswer();
        return getMeasureAnswerCalculationValue(measureAnswer);
    }

    private Integer getColumn(MeasureAnswer measureAnswer) {
    	if(measureAnswer.getDisplayOrder() != null)
    		return measureAnswer.getDisplayOrder();
    	return AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN;
    }
    
    private String getMeasureAnswerCalculationValue(MeasureAnswer measureAnswer) {
    	if(measureAnswer.getCalculationValue() != null && !measureAnswer.getCalculationValue().isEmpty()) {
    		return measureAnswer.getCalculationValue(); 
    	}
    	return null;
    }
    
	private Integer getAnswerAssessmentVariableId(AssessmentVariable parentAssessmentVariable, SurveyMeasureResponse response, 
			Map<Integer, AssessmentVariable> measureAnswerHash) {
		
		//If this answer has a mapped AssessmentVariable use that, otherwise use the parent id.
		Integer measureAnswerId = response.getMeasureAnswer().getMeasureAnswerId();
		if(measureAnswerHash.containsKey(measureAnswerId)) {
			VariableTemplate variableTemplate = measureAnswerHash.get(measureAnswerId).getVariableTemplateList().get(0);
			return variableTemplate.getVariableTemplateId();
		}

		//It hasn't been mapped so use the parent's id
		return parentAssessmentVariable.getAssessmentVariableId();
	}
	
	//possible types are string, none, and other
	private String getVariableTypeString(String type) {
		if(type == null)
			return "string";
		if(type.toLowerCase().equals("none"))
			return "none";
		if(type.toLowerCase().equals("other"))
			return "other";
		
		//otherwise its a string
		return "string";
	}
	
	private String getOverrideText(SurveyMeasureResponse response, Map<Integer, AssessmentVariable> measureAnswerHash) {
		
		//check to see if the answer id is in the hash
		Integer measureAnswerId = response.getMeasureAnswer().getMeasureAnswerId();
		if(measureAnswerHash.containsKey(measureAnswerId)) {
			//if found then see if an override value has been set.
			VariableTemplate variableTemplate = measureAnswerHash.get(measureAnswerId).getVariableTemplateList().get(0);
			if(variableTemplate.getOverrideDisplayValue() != null && !variableTemplate.getOverrideDisplayValue().isEmpty())
				return variableTemplate.getOverrideDisplayValue();
		}
		
		//Check to see if the measure answer has an override value
		return null;
	}
	
	private String getDisplayText(String answerType, MeasureAnswer measureAnswer, SurveyMeasureResponse response, Integer veteranAssessmentId) {
		
		int measureTypeId = measureAnswer.getMeasure().getMeasureType().getMeasureTypeId();
		if(measureTypeId == MeasureAssessmentVariableResolverImpl.MEASURE_TYPE_ID_FREETEXT) {
			return getValue(response, veteranAssessmentId);
		}

		//The constraint has been removed which would return null here if the answer is of type none. Template functions do not assume 
		//this business rule but it is possible that the handwritten templates do.  This constraint was lifted because it causes the 
		//delimited output of select multi to throw error since null was being returned here for the display text.  PO would like to 
		//show the text of the None answer so null should not be returned. 
		return measureAnswer.getAnswerText();
	}
	
	private String getValue(SurveyMeasureResponse response, Integer veteranAssessmentId) {
		if(response.getBooleanValue() != null)
			return response.getBooleanValue().toString().toLowerCase();
		if(response.getNumberValue() != null)
			return response.getNumberValue().toString();
		if(response.getTextValue() != null)
			return response.getTextValue();
		
		throw new CouldNotResolveVariableException(
			String.format("A value was not set for surveymeasureresponseid: %s, assessmentid %s", 
					response.getSurveyMeasureResponseId(), veteranAssessmentId));
	}
	
	private String getOtherText(SurveyMeasureResponse response) {
		if(response.getOtherValue() != null) 
			return response.getOtherValue();
		
		//otherwise just return null;
		return null;
	}
    
    
}