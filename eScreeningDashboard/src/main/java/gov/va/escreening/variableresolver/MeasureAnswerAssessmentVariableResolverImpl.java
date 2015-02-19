package gov.va.escreening.variableresolver;

import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.*;

@Transactional(noRollbackFor={CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class,
		UnsupportedOperationException.class, CouldNotResolveVariableValueException.class, UnsupportedOperationException.class, Exception.class})
public class MeasureAnswerAssessmentVariableResolverImpl implements MeasureAnswerAssessmentVariableResolver {

	//Please add to the constructor and do not use field based @Autowired
    private final SurveyMeasureResponseRepository surveyMeasureResponseRepository;

    //TODO refactor this to use the enum instead.
    public static final int CALCULATION_TYPE_NUMBER = 1;
    public static final int CALCULATION_TYPE_USER_ENTERED_NUMBER = 2;
    public static final int CALCULATION_TYPE_USER_ENTERED_STRING = 3;
    public static final int CALCULATION_TYPE_USER_ENTERED_BOOLEAN = 4;

    private static final Logger logger = LoggerFactory.getLogger(MeasureAnswerAssessmentVariableResolverImpl.class);
    
    @Autowired
    public MeasureAnswerAssessmentVariableResolverImpl(SurveyMeasureResponseRepository smrr){
    	this.surveyMeasureResponseRepository = checkNotNull(smrr);
    }    
    
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

		return resolveAssessmentVariable(assessmentVariable, response, measureAnswerHash);
	}

	@Override
	public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable parentAssessmentVariable,
			SurveyMeasureResponse response, Map<Integer, AssessmentVariable> measureAnswerHash) {

		AssessmentVariableDto variableDto = new AssessmentVariableDto(parentAssessmentVariable);
		//TODO: This is wrong (will fix this soon):
		variableDto.setVariableId(getAnswerAssessmentVariableId(parentAssessmentVariable, response, measureAnswerHash));
		
		variableDto.setResponse(response);
		
		//TODO: remove the use of override text
		variableDto.setOverrideText(getOverrideText(response, measureAnswerHash));
		
		return variableDto;
	}

    @Override
    public String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId) {

		MeasureAnswer measureAnswer = assessmentVariable.getMeasureAnswer();
		if(measureAnswer == null)
			throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type MeasureAnswer did not reference a MeasureAnswer"
			  + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));

    	SurveyMeasureResponse response = surveyMeasureResponseRepository.findSmrUsingPreFetch(veteranAssessmentId, measureAnswer.getMeasureAnswerId(), null);
		if(response == null) {
			logger.warn(String.format("There was no MeasureAnswer reponse for MeasureAnswerId: %s, assessmentId: %s",
					measureAnswer.getMeasureAnswerId(), veteranAssessmentId));
			return measureAnswer.getMeasure().getMeasureType().getMeasureTypeId()==3?"false":"0";
		}

    	String calcValue =  resolveCalculationValue(assessmentVariable, veteranAssessmentId, response);
    	logger.debug("Resolved assessment variable {} to value {}", assessmentVariable, calcValue);
    	return calcValue;
    }

    public String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, List<Measure> measureResp,
    		List<gov.va.escreening.entity.Measure> measures) {

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
			return measureAnswerHash.get(measureAnswerId).getAssessmentVariableId();
		}

		//It hasn't been mapped so use the parent's id
		return parentAssessmentVariable.getAssessmentVariableId();
	}

	//TODO: This should be removed
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

	@Override
	public String resolveCalculationValue(AssessmentVariable answerVariable,
			Pair<gov.va.escreening.entity.Measure, Measure> answer) {
		MeasureAnswer measureAnswer = answerVariable.getMeasureAnswer();

		  String answerValue = null;
		  for(Answer ans : answer.getRight().getAnswers())
		  {
			  if(ans.getAnswerId().intValue() == measureAnswer.getMeasureAnswerId())
			  {
				  answerValue = ans.getAnswerResponse();
				  break;
			  }
		  }
		String resolvedValue = measureAnswer.getCalculationType() == null ?
		        answerValue :
		        resolveResponseUsingCalculationType(answerVariable, answer);

		return resolvedValue;
	}

	private String resolveResponseUsingCalculationType(
			AssessmentVariable answerVariable,
			Pair<gov.va.escreening.entity.Measure, Measure> answer) {

		  MeasureAnswer measureAnswer = answerVariable.getMeasureAnswer();

		  String answerValue = null;
		  for(Answer ans : answer.getRight().getAnswers())
		  {
			  if(ans.getAnswerId().intValue() == measureAnswer.getMeasureAnswerId())
			  {
				  answerValue = ans.getAnswerResponse();
				  break;
			  }
		  }

		  int calculationType = measureAnswer.getCalculationType().getCalculationTypeId();
	        switch(measureAnswer.getCalculationType().getCalculationTypeId()) {
	            case CALCULATION_TYPE_USER_ENTERED_NUMBER:

	                if(answerValue != null){
	                    //treat numbers as floats to handle decimals when dividing numbers
	                    return String.format("%sf",answerValue);
	                }
	                break;
	            case CALCULATION_TYPE_USER_ENTERED_BOOLEAN:
	                if(answerValue != null)
	                   return String.valueOf(answerValue);
	                break;
	            case CALCULATION_TYPE_USER_ENTERED_STRING:
	                return answerValue;
	            case CALCULATION_TYPE_NUMBER:
	                String calcValue = getMeasureAnswerCalculationValue(measureAnswer);
	                if(calcValue != null)
	                    //treat numbers as floats to handle decimals when dividing numbers
	                    return String.format("%sf", calcValue);
	                break;
	            default:
	                throw new UnsupportedOperationException(String.format("Referenced calculation type of: %s is not supported.  AssessmentVariableid: %s",
	                    calculationType, answerVariable.getAssessmentVariableId()));
	        }
	        return null;
	}

	@Override
	public String resolveCalculationValue(
			gov.va.escreening.entity.Measure measure, Answer answerVal) {
		MeasureAnswer measureAnswer =null;
		for(MeasureAnswer ma : measure.getMeasureAnswerList())
		{
			if(ma.getMeasureAnswerId().intValue() == answerVal.getAnswerId())
			{
				measureAnswer = ma;
				break;
			}
		}

		String answerValue = answerVal.getAnswerResponse();

		  int calculationType = measureAnswer.getCalculationType().getCalculationTypeId();
	        switch(measureAnswer.getCalculationType().getCalculationTypeId()) {
	            case CALCULATION_TYPE_USER_ENTERED_NUMBER:

	                if(answerValue != null){
	                    //treat numbers as floats to handle decimals when dividing numbers
	                    return String.format("%sf",answerValue);
	                }
	                break;
	            case CALCULATION_TYPE_USER_ENTERED_BOOLEAN:
	                if(answerValue != null)
	                   return String.valueOf(answerValue);
	                break;
	            case CALCULATION_TYPE_USER_ENTERED_STRING:
	                return answerValue;
	            case CALCULATION_TYPE_NUMBER:
	                String calcValue = getMeasureAnswerCalculationValue(measureAnswer);
	                if(calcValue != null)
	                    //treat numbers as floats to handle decimals when dividing numbers
	                    return String.format("%sf", calcValue);
	                break;
	            default:
	                throw new UnsupportedOperationException(String.format("Referenced calculation type of: %s is not supported.  measureAnswerId: %s",
	                    calculationType, measureAnswer.getMeasureAnswerId()));
	        }
	        return null;
	}
}