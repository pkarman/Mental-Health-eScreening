package gov.va.escreening.variableresolver;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.exception.AssessmentEngineDataValidationException;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Override
    public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,
            ResolverParameters params){
        return resolveAssessmentVariable(assessmentVariable, params, null);
    }
    
    @Override
    public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,
            ResolverParameters params, Answer response) {
        logger.debug("Resolving answer variable with AV ID: {}", assessmentVariable.getAssessmentVariableId());

        Integer avId = assessmentVariable.getAssessmentVariableId();
        params.checkUnresolvable(avId);
        AssessmentVariableDto variableDto = params.getResolvedVariable(avId);
        
        if(variableDto == null 
                || (response != null && response.getRowId() != null && ! response.getRowId().equals(variableDto.getRow()))){
            
            MeasureAnswer measureAnswer = assessmentVariable.getMeasureAnswer();
            if (measureAnswer == null)
                ErrorBuilder.throwing(AssessmentVariableInvalidValueException.class)
                    .toAdmin(String.format("An assessment variable with ID %s does not have an associated measure answer.", assessmentVariable.getAssessmentVariableId()))
                    .toUser("A system inconsistency was detected. Please call support")
                    .throwIt();
    
            if(response == null){
                logger.warn("Resolving of an answer is not passing in a response object. This will result in only the first row value being used.");
                List<Answer>responses = params.getAnswerResponse(measureAnswer.getMeasureAnswerId());
                //this is a temporary fix until something better is figured out
                if(!responses.isEmpty()){
                    response = responses.get(0);
                }
            }
            
            try{
                if (response == null)
                    throw new CouldNotResolveVariableException(String.format("There is no reponse for MeasureAnswer with ID: %s",
                            measureAnswer.getMeasureAnswerId()));
        
                variableDto = new AssessmentVariableDto(assessmentVariable);
                
                //TODO: This is wrong (remove this after researching if some templates assume this):
                variableDto.setVariableId(getAnswerAssessmentVariableId(assessmentVariable, response.getAnswerId(), params));
                
                variableDto.setResponse(response);
                
                //TODO: remove the use of override text (two hand written templates still use it)
                variableDto.setOverrideText(getOverrideText(response, params));
                
                //add resolved Dto to the cache
                params.addResolvedVariable(variableDto);
                
                
            }
            catch(Exception e){
                params.addUnresolvableVariable(assessmentVariable.getAssessmentVariableId());
                throw e;
            }
        }
        logger.debug("Resolved answer variable with AV ID: {} to:\n{}", assessmentVariable.getAssessmentVariableId(), variableDto);
        return variableDto;
    }

    @Override
    public String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, NullValueHandler smrNullHandle) {

        MeasureAnswer measureAnswer = assessmentVariable.getMeasureAnswer();
        if (measureAnswer == null)
            throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type MeasureAnswer did not reference a MeasureAnswer"
                    + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));

        SurveyMeasureResponse response = surveyMeasureResponseRepository.findSmrUsingPreFetch(veteranAssessmentId, measureAnswer.getMeasureAnswerId(), null);
        if (response == null) {
            smrNullHandle.handleMeasureAnswer(veteranAssessmentId, measureAnswer);
        }

        String calcValue = resolveCalculationValue(assessmentVariable, veteranAssessmentId, response);
        logger.debug("Resolved assessment variable {} to value {}", assessmentVariable, calcValue);
        return calcValue;
    }

    public String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, List<Measure> measureResp,
                                          List<gov.va.escreening.entity.Measure> measures) {

        MeasureAnswer measureAnswer = assessmentVariable.getMeasureAnswer();
        if (measureAnswer == null)
            throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type MeasureAnswer did not reference a MeasureAnswer"
                    + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));

        SurveyMeasureResponse response = surveyMeasureResponseRepository.findSmrUsingPreFetch(veteranAssessmentId, measureAnswer.getMeasureAnswerId(), null);
        if (response == null)
            throw new CouldNotResolveVariableValueException(String.format("There was no MeasureAnswer reponse for MeasureAnswerId: %s, assessmentId: %s",
                    measureAnswer.getMeasureAnswerId(), veteranAssessmentId));

        String calcValue = resolveCalculationValue(assessmentVariable, veteranAssessmentId, response);
        logger.debug("Resolved assessment variable {} to value {}", assessmentVariable, calcValue);
        return calcValue;
    }

    @Override
    public String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, SurveyMeasureResponse response) {

        MeasureAnswer measureAnswer = response.getMeasureAnswer();
        if (measureAnswer == null)
            throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type MeasureAnswer did not reference a MeasureAnswer"
                    + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));

		String resolvedValue = resolveResponseValueWithOutCalulationType(response, measureAnswer);

        if (resolvedValue == null) {
		    throw new CouldNotResolveVariableValueException(String.format("No value was found for MeasureAnswer "
                + "for the associated SurveyMeasureResponse: %s, MeasureAnswerId: %s, assessmentId: %s",
                response.getSurveyMeasureResponseId(), measureAnswer.getMeasureAnswerId(), veteranAssessmentId));
        }
        return resolvedValue;
    }

    private boolean isSelectOne(gov.va.escreening.entity.Measure measure){
    	return measure != null
        		&& measure.getMeasureType() != null 
        		&& measure.getMeasureType().getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_SELECT_ONE;
    }
    
    private String resolveResponseValueWithOutCalulationType(SurveyMeasureResponse response, MeasureAnswer measureAnswer){

    	gov.va.escreening.entity.Measure measure = measureAnswer.getMeasure();
    	if(isSelectOne(measure)){
    		return String.format("%sf", getMeasureAnswerCalculationValue(measureAnswer));
    	}
    	
        Long numValue = response.getNumberValue();
        if (numValue != null) {
            //treat numbers as floats to handle decimals when dividing numbers
            return String.format("%sf", String.valueOf(numValue));
        }
        Boolean boolValue = response.getBooleanValue();
        if (boolValue != null) {
            return String.valueOf(boolValue);
        }
        String strValue = response.getTextValue();
        if (strValue != null) {
        	if(measure != null && measure.isNumeric()){
        		return String.format("%sf", String.valueOf(strValue));
        	}
            return strValue;
        }
        
        return getMeasureAnswerCalculationValue(measureAnswer);
    }

    private String getMeasureAnswerCalculationValue(MeasureAnswer measureAnswer) {
        if (measureAnswer.getCalculationValue() != null && !measureAnswer.getCalculationValue().isEmpty()) {
            return measureAnswer.getCalculationValue();
        }
        return null;
    }

    private Integer getAnswerAssessmentVariableId(AssessmentVariable parentAssessmentVariable, Integer measureAnswerId,
                                                  ResolverParameters params) {

        //If this answer has a mapped AssessmentVariable use that, otherwise use the parent id.
        AssessmentVariable var = params.getAnswerAv(measureAnswerId);
        if(var != null) {
            return var.getAssessmentVariableId();
        }

        //It hasn't been mapped so use the parent's id
        return parentAssessmentVariable.getAssessmentVariableId();
    }

	//TODO: This should be removed (only two template use this)
    private String getOverrideText(Answer response, ResolverParameters params) {

        //check to see if the answer id is in the hash
        Integer measureAnswerId = response.getAnswerId();
        AssessmentVariable answerVariable = params.getAnswerAv(measureAnswerId);
        if (answerVariable != null && !answerVariable.getVariableTemplateList().isEmpty()) {
            //if found then see if an override value has been set.
            VariableTemplate variableTemplate = answerVariable.getVariableTemplateList().get(0);
            if (variableTemplate.getOverrideDisplayValue() != null && !variableTemplate.getOverrideDisplayValue().isEmpty())
                return variableTemplate.getOverrideDisplayValue();
        }

        //Check to see if the measure answer has an override value
        return null;
    }

    @Override
	public String resolveCalculationValue(
            AssessmentVariable answerVariable,
            Pair<gov.va.escreening.entity.Measure, Measure> answer) {

        int dbAnswerCount = answer.getLeft().getMeasureAnswerList().size();
        int dtoAnswerCount = answer.getRight().getAnswers().size();
        if(dbAnswerCount != dtoAnswerCount){
            ErrorBuilder.throwing(AssessmentEngineDataValidationException.class)
                .toAdmin("UI sent a different number of responses (" + dtoAnswerCount + ") than what was expected(" +  dbAnswerCount + ").")
                .toUser("A system error has occurred. Please contact support.")
                .throwIt();
        }
        
        MeasureAnswer measureAnswer = answerVariable.getMeasureAnswer();

        Integer answerIndex = null;
        for (int i = 0; i < dtoAnswerCount; i++) {
            Answer ans = answer.getRight().getAnswers().get(i);
            if (ans.getAnswerId().intValue() == measureAnswer.getMeasureAnswerId()) {
                answerIndex = i;
                break;
            }
        }

		return formatValue(answer, answerIndex);
    }

    @Override
    public String resolveCalculationValue(Pair<gov.va.escreening.entity.Measure, gov.va.escreening.dto.ae.Measure> answer, Integer index) {
		return formatValue(answer, index);
	}
	
	private String formatValue(Pair<gov.va.escreening.entity.Measure, gov.va.escreening.dto.ae.Measure> answer, Integer index){
		if(index == null)
		    return null;
		
		gov.va.escreening.entity.Measure dbMeasure = answer.getLeft();
        gov.va.escreening.dto.ae.Measure measureDto = answer.getRight();
		
        String answerResponse = measureDto.getAnswers().get(index).getAnswerResponse();
        
	    if(dbMeasure.isNumeric()){
	        return String.format("%sf",answerResponse);
	    }
	    
		if(isSelectOne(dbMeasure)){
			//treat numbers as floats to handle decimals when dividing numbers
		    String calculationValue = dbMeasure.getMeasureAnswerList().get(index).getCalculationValue();
		    if(calculationValue == null){
		        calculationValue = answerResponse.trim().toLowerCase() == "true" ? "1" : "0";
		    }
            return String.format("%sf", calculationValue);
		}
		
		return answerResponse;
    }
}