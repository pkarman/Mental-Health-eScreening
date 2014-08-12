package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor={CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class, 
		UnsupportedOperationException.class, CouldNotResolveVariableValueException.class, UnsupportedOperationException.class, Exception.class})
public class MeasureAssessmentVariableResolverImpl implements MeasureAssessmentVariableResolver {

	@Autowired
	private MeasureAnswerAssessmentVariableResolver measureAnswerVariableResolver;
	@Autowired
	private MeasureRepository measureRepository;
    @Autowired
    private SurveyMeasureResponseRepository surveyMeasureResponseRepository;
    
    public static final int MEASURE_TYPE_ID_FREETEXT = 1;
    public static final int MEASURE_TYPE_ID_SELECTONE = 2;
    public static final int MEASURE_TYPE_ID_SELECTMULTI = 3;
    public static final int MEASURE_TYPE_ID_TABLEQUESTION = 4;
    public static final int MEASURE_TYPE_ID_READONETEXT = 5;
    public static final int MEASURE_TYPE_ID_SELECTONEMATRIX = 6;
    public static final int MEASURE_TYPE_ID_SELECTMULTIMATRIX = 7;
    public static final int MEASURE_TYPE_ID_INSTRUCTION = 8;
    
    private static final Logger logger = LoggerFactory.getLogger(MeasureAssessmentVariableResolverImpl.class);
    
    @Override
    public String resolveCalculationValue(AssessmentVariable assessmentVariable,
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash) {
    	
    	if(assessmentVariable.getMeasure() == null || assessmentVariable.getMeasure().getMeasureId() == null)
   			throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type Measure did not contain the required measureid"
   			  + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));
    		
		Integer measureId = assessmentVariable.getMeasure().getMeasureId();
		List<SurveyMeasureResponse> responses = surveyMeasureResponseRepository.getForVeteranAssessmentAndMeasure(veteranAssessmentId, measureId);
		if(responses == null || responses.size() == 0)
			throw new CouldNotResolveVariableException(String.format("There were not any measure reponses for measureId: %s, assessmentId: %s", 
				measureId, veteranAssessmentId));

    	String result = null;
		int measureTypeId = assessmentVariable.getMeasure().getMeasureType().getMeasureTypeId();
		switch(measureTypeId) {
			case MEASURE_TYPE_ID_FREETEXT:
				result = resolveFreeTextCalculationValue(assessmentVariable, responses, veteranAssessmentId);
				break;
			case MEASURE_TYPE_ID_SELECTONE:
				result = resolveSelectOneCalculationValue(assessmentVariable, responses, veteranAssessmentId);
				break;
			case MEASURE_TYPE_ID_SELECTONEMATRIX:
				result = resolveSelectOneCalculationValue(assessmentVariable, responses, veteranAssessmentId);
				break;
			case MEASURE_TYPE_ID_SELECTMULTI:
			case MEASURE_TYPE_ID_SELECTMULTIMATRIX:
			case MEASURE_TYPE_ID_TABLEQUESTION:
			case MEASURE_TYPE_ID_READONETEXT:
			case MEASURE_TYPE_ID_INSTRUCTION:
			default:
				throw new UnsupportedOperationException(String.format("Resolution of calculation value for measure type of: %s is not supported.", measureTypeId));
		}
		
		if(result == null) result="null";
//			throw new CouldNotResolveVariableValueException(String.format("Was unable to resolve the calculation value for measureid: %s, assessmentId: %s", 
//					measureId, veteranAssessmentId));
    	
    	return result;
    }
    
    /*
    	List<AssessmentVariable> ethnicityList = new ArrayList<AssessmentVariable>();
		ethnicityList.add(new AssessmentVariable(31, "var31", "string", "answer_220", "false", "Hispanic/Latino", null, null));  
		ethnicityList.add(new AssessmentVariable(32, "var32", "string", "answer_221", "true", "Not Hispanic/Latino", "none Hispanic/Latino", null));
        ethnicityList.add(new AssessmentVariable(33, "var33", "none", "answer_222", "false", null, null, null)); //decline to answer 
		root.put("var30", new AssessmentVariable(30, "var30", "string", "measure_21", null, null, null, null, ethnicityList));  
    */
    @Override
	public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,  
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash) {
		
		Integer measureId = assessmentVariable.getMeasure().getMeasureId();
		if(measureId == null)
			throw new AssessmentVariableInvalidValueException(String.format("AssessmentVariable of type Measure did not conatain the required measureid"
			  + " VeteranAssessment id was: %s, AssessmentVariable id was: %s", veteranAssessmentId, assessmentVariable.getAssessmentVariableId()));
		
		List<SurveyMeasureResponse> responses = surveyMeasureResponseRepository.getForVeteranAssessmentAndMeasure(veteranAssessmentId, measureId);
		if(responses == null){
			responses = Collections.emptyList();
		}
		int measureTypeId = assessmentVariable.getMeasure().getMeasureType().getMeasureTypeId();
		if(responses.size() == 0 
				&& measureTypeId != AssessmentConstants.MEASURE_TYPE_TABLE
				&& measureTypeId != AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX
				&& measureTypeId != AssessmentConstants.MEASURE_TYPE_SELECT_ONE_MATRIX)
			throw new CouldNotResolveVariableException(String.format("There were not any measure reponses for measureId: %s, assessmentId: %s", 
				measureId, veteranAssessmentId));
		
		AssessmentVariableDto variableDto = processMeasureType(measureTypeId, measureId, veteranAssessmentId, assessmentVariable,	responses, measureAnswerHash);
		if(variableDto == null)
			throw new CouldNotResolveVariableException(String.format("Was unable to resolve the assessment variable for measureid: %s, assessmentId: %s", 
					measureId, veteranAssessmentId));
		
    	return variableDto;
    }
    
    private AssessmentVariableDto processMeasureType(int measureTypeId, int measureId, int veteranAssessmentId, AssessmentVariable assessmentVariable,
    		List<SurveyMeasureResponse> responses, Map<Integer, AssessmentVariable> measureAnswerHash) {
    	
    	AssessmentVariableDto variableDto = null;
    	
		switch(measureTypeId) {
			case MEASURE_TYPE_ID_FREETEXT:
				variableDto = resolveFreeTextAssessmentVariableQuestion(assessmentVariable, measureId, veteranAssessmentId, responses, measureAnswerHash);
				break;
			case MEASURE_TYPE_ID_SELECTONE:
				variableDto = resolveSelectOneAssessmentVariableQuestion(assessmentVariable, measureId, veteranAssessmentId, responses, measureAnswerHash);
				break;
			case MEASURE_TYPE_ID_SELECTMULTI:
				variableDto = resolveSelectMultiAssessmentVariableQuestion(assessmentVariable, measureId, veteranAssessmentId, responses, measureAnswerHash);
				break;
			case MEASURE_TYPE_ID_TABLEQUESTION:
				//TODO need to do some additional work here
				variableDto = resolveTableAssessmentVariableQuestion(assessmentVariable, veteranAssessmentId, measureAnswerHash);
				break;
			case MEASURE_TYPE_ID_SELECTMULTIMATRIX:				
				variableDto = resolveSelectMultiAssessmentVariableQuestion(assessmentVariable, measureId, veteranAssessmentId, responses, measureAnswerHash);
				break;
			case MEASURE_TYPE_ID_SELECTONEMATRIX:
				variableDto = resolveSelectOneAssessmentVariableQuestion(assessmentVariable, measureId, veteranAssessmentId, responses, measureAnswerHash);
				break;
			case MEASURE_TYPE_ID_READONETEXT:
			case MEASURE_TYPE_ID_INSTRUCTION:
			default:
				throw new UnsupportedOperationException(String.format("Resolve assessment variable for measure type of: %s is not supported.", measureTypeId));
		}
		
		return variableDto;
    }
    
    private AssessmentVariableDto resolveFreeTextAssessmentVariableQuestion(AssessmentVariable assessmentVariable, 
    		Integer measureId, Integer veteranAssessmentId, List<SurveyMeasureResponse> responses, Map<Integer, AssessmentVariable> measureAnswerHash) {
		AssessmentVariableDto answerVariableDto = measureAnswerVariableResolver.resolveAssessmentVariable(assessmentVariable, responses.get(0), 
				veteranAssessmentId, measureAnswerHash);
		AssessmentVariableDto questionVariableDto = createAssessmentVariableDtoForQuestion(assessmentVariable);
		questionVariableDto.getChildren().add(answerVariableDto);
    	return questionVariableDto;
    }

    private AssessmentVariableDto resolveSelectOneAssessmentVariableQuestion(AssessmentVariable assessmentVariable, 
    		Integer measureId, Integer veteranAssessmentId, List<SurveyMeasureResponse> responses, Map<Integer, AssessmentVariable> measureAnswerHash) {
    
    	AssessmentVariableDto questionVariableDto = createAssessmentVariableDtoForQuestion(assessmentVariable);
    	
    	//loop to find the first true value then process the answer
    	for(SurveyMeasureResponse response : responses) {
    		if(response.getBooleanValue() != null && response.getBooleanValue()) {
    			//call the answer level to resolve the value
    			AssessmentVariableDto answerVariableDto = measureAnswerVariableResolver.resolveAssessmentVariable(assessmentVariable, response, 
    				veteranAssessmentId, measureAnswerHash);
    			questionVariableDto.getChildren().add(answerVariableDto);
    			break;  //found what we were looking for
    		}
    	}

    	return questionVariableDto;
    }
    
    private AssessmentVariableDto resolveSelectMultiAssessmentVariableQuestion(AssessmentVariable assessmentVariable, 
    		Integer measureId, Integer veteranAssessmentId, List<SurveyMeasureResponse> responses, Map<Integer, AssessmentVariable> measureAnswerHash) {
  
    	AssessmentVariableDto questionVariableDto = createAssessmentVariableDtoForQuestion(assessmentVariable);
    	
    	//loop to find all of the true values then add them to the collection
    	for(SurveyMeasureResponse response : responses) {
    		if(response.getBooleanValue() != null && response.getBooleanValue()) {
    			//call the answer level to resolve the value
    			AssessmentVariableDto answerVariableDto = measureAnswerVariableResolver.resolveAssessmentVariable(assessmentVariable, response, 
    				veteranAssessmentId, measureAnswerHash);
    			questionVariableDto.getChildren().add(answerVariableDto);
    		}
    	}

    	return questionVariableDto;
    }

    private AssessmentVariableDto resolveTableAssessmentVariableQuestion(AssessmentVariable assessmentVariable, 
    	Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash) {
    	
    	AssessmentVariableDto questionVariableDto = createAssessmentVariableDtoForQuestion(assessmentVariable);
    	
    	Measure parentMeasure = assessmentVariable.getMeasure();
    	List<SurveyMeasureResponse> parentResponses = parentMeasure.getSurveyMeasureResponseList();
    	
    	SurveyMeasureResponse parentResponse = parentResponses.isEmpty() ? null : parentResponses.get(0); //there should only be one response for the parent question for table type
    	if(parentResponse != null && parentResponse.getBooleanValue()) {
    		AssessmentVariableDto answerVariableDto = measureAnswerVariableResolver.resolveAssessmentVariable(assessmentVariable, parentResponse, 
    				veteranAssessmentId, measureAnswerHash);
    		questionVariableDto.getChildren().add(answerVariableDto);
    	}
    	else {
	    	Set<Measure> childMeasures = parentMeasure.getChildren();
	    	if(childMeasures.size() == 0)
				throw new CouldNotResolveVariableException(String.format("Could not resolve Measure of type: %s, the measure was expected to have dhild measures but did not have any.  "
						+ "MeasureId: %s, AssessmentVariableId: %s, assessmentId: %s", 	MEASURE_TYPE_ID_TABLEQUESTION, 
						parentMeasure.getMeasureId(), assessmentVariable.getAssessmentVariableId(),  veteranAssessmentId));
	    		    		
	    	for(Measure childMeasure : childMeasures) {
		    	int tabularRow = 0;
		    	do {
		    		List<SurveyMeasureResponse> responses = 
		    				surveyMeasureResponseRepository.findForAssessmentIdMeasureRow(veteranAssessmentId, childMeasure.getMeasureId(), tabularRow);
		    		if(responses == null || responses.size() == 0 || tabularRow > 1000)
		    			break;
	    	
		    		//otherwise we have a response to process
		    		AssessmentVariableDto childQuestionVariableDto = processMeasureType(childMeasure.getMeasureType().getMeasureTypeId(), childMeasure.getMeasureId(), 
		    				veteranAssessmentId, assessmentVariable,	responses, measureAnswerHash);
					questionVariableDto.getChildren().add(childQuestionVariableDto);
		    		
		    		tabularRow = tabularRow + 1;
		    	} while(true);
	    	}
    	}
    	return questionVariableDto;
    }
    
    private String resolveFreeTextCalculationValue(AssessmentVariable answerVariable, List<SurveyMeasureResponse> responses, 
    		Integer veteranAssessmentId) {
    	
    	String result = null;
    	if(responses.size() > 1)
			throw new CouldNotResolveVariableException(String.format("Measure of type: %s had more than one free text value, answerVariableId: %s, assessmentId: %s",
				MEASURE_TYPE_ID_FREETEXT, answerVariable.getAssessmentVariableId(), veteranAssessmentId));
    	
    	//There should only be one response for this type
    	SurveyMeasureResponse response = responses.get(0);
		result = measureAnswerVariableResolver.resolveCalculationValue(answerVariable, veteranAssessmentId, response);
	
    	return result;
    }
    
    private String resolveSelectOneCalculationValue(AssessmentVariable answerVariable, List<SurveyMeasureResponse> responses, 
		Integer veteranAssessmentId) {
    	
    	String result = null;
    	
    	//look for the true value then call answer to pull the value
    	for(SurveyMeasureResponse response : responses) {
    		if(response.getBooleanValue() != null && response.getBooleanValue()) {
    			//call the answer level to resolve the value
    			result = measureAnswerVariableResolver.resolveCalculationValue(answerVariable, veteranAssessmentId, response);
    		}
    	}
    	
    	return result;
    }

	private AssessmentVariableDto createAssessmentVariableDtoForQuestion(AssessmentVariable assessmentVariable) {
		Integer id = assessmentVariable.getAssessmentVariableId();
		String variableName = String.format("var%s", id);
		String displayName = String.format("measure_%s", assessmentVariable.getMeasure().getMeasureId());
		Integer column = getColumn(assessmentVariable);
		AssessmentVariableDto variableDto = new AssessmentVariableDto(id, variableName, "list", displayName, column);
		return variableDto;
	}
	
	private Integer getColumn(AssessmentVariable assessmentVariable) {
		if(assessmentVariable.getMeasure() != null && assessmentVariable.getMeasure().getDisplayOrder() != null)
			return assessmentVariable.getMeasure().getDisplayOrder();
		return AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN;
	}
}