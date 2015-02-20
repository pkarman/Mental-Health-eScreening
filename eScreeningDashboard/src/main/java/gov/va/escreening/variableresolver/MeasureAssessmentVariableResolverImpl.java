package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.exception.CouldNotResolveVariableValueException;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.*;

@Transactional(noRollbackFor = { CouldNotResolveVariableException.class,
		AssessmentVariableInvalidValueException.class,
		UnsupportedOperationException.class,
		CouldNotResolveVariableValueException.class,
		UnsupportedOperationException.class, Exception.class })
public class MeasureAssessmentVariableResolverImpl implements
		MeasureAssessmentVariableResolver {

	//Please add to the constructor and do not use field based @Autowired	
	private final MeasureAnswerAssessmentVariableResolver measureAnswerVariableResolver;
	private final SurveyMeasureResponseRepository surveyMeasureResponseRepository;

	private static final Logger logger = LoggerFactory
			.getLogger(MeasureAssessmentVariableResolverImpl.class);

	@Autowired
	public MeasureAssessmentVariableResolverImpl(
			MeasureAnswerAssessmentVariableResolver mavr, 
			SurveyMeasureResponseRepository smrr){
		measureAnswerVariableResolver = checkNotNull(mavr);
		surveyMeasureResponseRepository = checkNotNull(smrr);
	}
	
	@Override
	public String resolveCalculationValue(
			AssessmentVariable assessmentVariable, Integer veteranAssessmentId,
			Map<Integer, AssessmentVariable> measureAnswerHash) {

		if (assessmentVariable.getMeasure() == null
				|| assessmentVariable.getMeasure().getMeasureId() == null)
			throw new AssessmentVariableInvalidValueException(
					String.format(
							"AssessmentVariable of type Measure did not contain the required measureid"
									+ " VeteranAssessment id was: %s, AssessmentVariable id was: %s",
							veteranAssessmentId,
							assessmentVariable.getAssessmentVariableId()));

		Integer measureId = assessmentVariable.getMeasure().getMeasureId();
		List<SurveyMeasureResponse> responses = surveyMeasureResponseRepository
				.getForVeteranAssessmentAndMeasure(veteranAssessmentId,
						measureId);
		if (responses == null || responses.size() == 0)
			throw new CouldNotResolveVariableException(
					String.format(
							"There were not any measure reponses for measureId: %s, assessmentId: %s",
							measureId, veteranAssessmentId));

		String result = null;
		int measureTypeId = assessmentVariable.getMeasure().getMeasureType()
				.getMeasureTypeId();
		switch (measureTypeId) {
		case AssessmentConstants.MEASURE_TYPE_FREE_TEXT:
			result = resolveFreeTextCalculationValue(assessmentVariable,
					responses, veteranAssessmentId);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_ONE:
			result = resolveSelectOneCalculationValue(assessmentVariable,
					responses, veteranAssessmentId);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_ONE_MATRIX: 
			result = resolveSelectOneCalculationValue(assessmentVariable,
					responses, veteranAssessmentId);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI:
		case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX:  
		case AssessmentConstants.MEASURE_TYPE_TABLE:
		case AssessmentConstants.MEASURE_TYPE_READ_ONLY: 
		case AssessmentConstants.MEASURE_TYPE_INSTRUCTION:  
		default:
			throw new UnsupportedOperationException(
					String.format(
							"Resolution of calculation value for measure type of: %s is not supported.",
							measureTypeId));
		}

		if (result == null)
			result = "null";
		// throw new
		// CouldNotResolveVariableValueException(String.format("Was unable to resolve the calculation value for measureid: %s, assessmentId: %s",
		// measureId, veteranAssessmentId));

		return result;
	}

	/*
	 * List<AssessmentVariable> ethnicityList = new
	 * ArrayList<AssessmentVariable>(); ethnicityList.add(new
	 * AssessmentVariable(31, "var31", "string", "answer_220", "false",
	 * "Hispanic/Latino", null, null)); ethnicityList.add(new
	 * AssessmentVariable(32, "var32", "string", "answer_221", "true",
	 * "Not Hispanic/Latino", "none Hispanic/Latino", null));
	 * ethnicityList.add(new AssessmentVariable(33, "var33", "none",
	 * "answer_222", "false", null, null, null)); //decline to answer
	 * root.put("var30", new AssessmentVariable(30, "var30", "string",
	 * "measure_21", null, null, null, null, ethnicityList));
	 */
	@Override
	public AssessmentVariableDto resolveAssessmentVariable(
			AssessmentVariable assessmentVariable, Integer veteranAssessmentId,
			Map<Integer, AssessmentVariable> measureAnswerHash) {

		Integer measureId = assessmentVariable.getMeasure().getMeasureId();
		if (measureId == null)
			throw new AssessmentVariableInvalidValueException(
					String.format(
							"AssessmentVariable of type Measure did not conatain the required measureid"
									+ " VeteranAssessment id was: %s, AssessmentVariable id was: %s",
							veteranAssessmentId,
							assessmentVariable.getAssessmentVariableId()));

		List<SurveyMeasureResponse> responses = surveyMeasureResponseRepository
				.getForVeteranAssessmentAndMeasure(veteranAssessmentId,
						measureId);
		if (responses == null) {
			responses = Collections.emptyList();
		}
		int measureTypeId = assessmentVariable.getMeasure().getMeasureType()
				.getMeasureTypeId();
		if (responses.size() == 0
				&& measureTypeId != AssessmentConstants.MEASURE_TYPE_TABLE
				&& measureTypeId != AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX
				&& measureTypeId != AssessmentConstants.MEASURE_TYPE_SELECT_ONE_MATRIX)
			throw new CouldNotResolveVariableException(
					String.format(
							"There were not any measure reponses for measureId: %s, assessmentId: %s",
							measureId, veteranAssessmentId));

		AssessmentVariableDto variableDto = processMeasureType(measureTypeId,
				measureId, veteranAssessmentId, assessmentVariable, responses,
				measureAnswerHash);
		if (variableDto == null)
			throw new CouldNotResolveVariableException(
					String.format(
							"Was unable to resolve the assessment variable for measureid: %s, assessmentId: %s",
							measureId, veteranAssessmentId));

		return variableDto;
	}

	private AssessmentVariableDto processMeasureType(int measureTypeId,
			int measureId, int veteranAssessmentId,
			AssessmentVariable assessmentVariable,
			List<SurveyMeasureResponse> responses,
			Map<Integer, AssessmentVariable> measureAnswerHash) {

		AssessmentVariableDto variableDto = null;

		switch (measureTypeId) {
		case AssessmentConstants.MEASURE_TYPE_FREE_TEXT:
		case AssessmentConstants.MEASURE_TYPE_READ_ONLY:
			variableDto = resolveFreeTextAssessmentVariableQuestion(
					assessmentVariable, measureId, veteranAssessmentId,
					responses, measureAnswerHash);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_ONE:
			variableDto = resolveSelectOneAssessmentVariableQuestion(
					assessmentVariable, measureId, veteranAssessmentId,
					responses, measureAnswerHash);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI:
			variableDto = resolveSelectMultiAssessmentVariableQuestion(
					assessmentVariable, measureId, veteranAssessmentId,
					responses, measureAnswerHash);
			break;
		case AssessmentConstants.MEASURE_TYPE_TABLE:
			variableDto = resolveTableAssessmentVariableQuestion(
					assessmentVariable, veteranAssessmentId, measureAnswerHash);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX:
			variableDto = resolveSelectMultiMatrixAssessmentVariableQuestion(
					assessmentVariable, measureId, veteranAssessmentId,
					responses, measureAnswerHash);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_ONE_MATRIX:
			variableDto = resolveSelectOneMatrixAssessmentVariableQuestion(
					assessmentVariable, measureId, veteranAssessmentId,
					responses, measureAnswerHash);
			break;
		case AssessmentConstants.MEASURE_TYPE_INSTRUCTION:
		default:
			throw new UnsupportedOperationException(
					String.format(
							"Resolve assessment variable for measure type of: %s is not supported.",
							measureTypeId));
		}

		return variableDto;
	}

	private AssessmentVariableDto resolveFreeTextAssessmentVariableQuestion(
			AssessmentVariable assessmentVariable, Integer measureId,
			Integer veteranAssessmentId, List<SurveyMeasureResponse> responses,
			Map<Integer, AssessmentVariable> measureAnswerHash) {
		
		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);
		addResolvedAnswerTo(questionVariableDto, responses.get(0), measureAnswerHash);
		
		return questionVariableDto;
	}

	private AssessmentVariableDto resolveSelectOneAssessmentVariableQuestion(
			AssessmentVariable assessmentVariable, Integer measureId,
			Integer veteranAssessmentId, List<SurveyMeasureResponse> responses,
			Map<Integer, AssessmentVariable> measureAnswerHash) {

		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);

		// loop to find the first true value then process the answer
		for (SurveyMeasureResponse response : responses) {
			if (response.getBooleanValue() != null
					&& response.getBooleanValue()) {
				
				addResolvedAnswerTo(questionVariableDto, response, measureAnswerHash);
				
				//commenting out these because the question should not have these set only the child answer
				//questionVariableDto.setAnswerId(answerVariableDto.getAnswerId());
				//questionVariableDto.setValue(answerVariableDto.getCalculationValue());
				break; // found what we were looking for
			}
		}

		return questionVariableDto;
	}

	private AssessmentVariableDto resolveSelectOneMatrixAssessmentVariableQuestion(
			AssessmentVariable assessmentVariable, Integer measureId,
			Integer veteranAssessmentId, List<SurveyMeasureResponse> responses,
			Map<Integer, AssessmentVariable> measureAnswerHash) {

		return resolveParentQuestion(assessmentVariable, measureId, veteranAssessmentId, 
				responses, measureAnswerHash, new SelectOneChildResolverFunction());
	}
	
	
	private AssessmentVariableDto resolveSelectMultiAssessmentVariableQuestion(
			AssessmentVariable assessmentVariable, Integer measureId,
			Integer veteranAssessmentId, List<SurveyMeasureResponse> responses,
			Map<Integer, AssessmentVariable> measureAnswerHash) {

		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);

		// loop to find all of the true values then add them to the collection
		for (SurveyMeasureResponse response : responses) {
			if (response.getBooleanValue() != null
					&& response.getBooleanValue()) {
				
				// call the answer level to resolve the value
				addResolvedAnswerTo(questionVariableDto, response, measureAnswerHash);
			}
		}

		return questionVariableDto;
	}
	
	private AssessmentVariableDto resolveSelectMultiMatrixAssessmentVariableQuestion(
			AssessmentVariable assessmentVariable, Integer measureId,
			Integer veteranAssessmentId, List<SurveyMeasureResponse> responses,
			Map<Integer, AssessmentVariable> measureAnswerHash) {

		return resolveParentQuestion(assessmentVariable, measureId, veteranAssessmentId, 
				responses, measureAnswerHash, new SelectMultiChildResolverFunction());
	}
	

	private AssessmentVariableDto resolveTableAssessmentVariableQuestion(
			AssessmentVariable assessmentVariable, Integer veteranAssessmentId,
			Map<Integer, AssessmentVariable> measureAnswerHash) {

		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);

		Measure parentMeasure = assessmentVariable.getMeasure();
		List<SurveyMeasureResponse> parentResponses = parentMeasure
				.getSurveyMeasureResponseList();

		SurveyMeasureResponse parentResponse = parentResponses.isEmpty() ? null
				: parentResponses.get(0); // there should only be one response
											// for the parent question for table
											// type
		if (parentResponse != null && parentResponse.getBooleanValue()) {
			addResolvedAnswerTo(questionVariableDto, parentResponse, measureAnswerHash);
		} else {
			Set<Measure> childMeasures = parentMeasure.getChildren();
			if (childMeasures.size() == 0)
				throw new CouldNotResolveVariableException(
						String.format(
								"Could not resolve Measure of type: %s, the measure was expected to have child measures but did not have any.  "
										+ "MeasureId: %s, AssessmentVariableId: %s, assessmentId: %s",
								AssessmentConstants.MEASURE_TYPE_TABLE,
								parentMeasure.getMeasureId(),
								assessmentVariable.getAssessmentVariableId(),
								veteranAssessmentId));

			for (Measure childMeasure : childMeasures) {
				int tabularRow = 0;
				do {
					List<SurveyMeasureResponse> responses = surveyMeasureResponseRepository
							.findForAssessmentIdMeasureRow(veteranAssessmentId,
									childMeasure.getMeasureId(), tabularRow);
					if (responses == null || responses.isEmpty()
							|| tabularRow > 1000)
						break;

					try {
						// otherwise we have a response to process
						AssessmentVariable childAV = childMeasure.getAssessmentVariable();
						AssessmentVariableDto childQuestionVariableDto = processMeasureType(
								childMeasure.getMeasureType()
										.getMeasureTypeId(),
								childMeasure.getMeasureId(),
								veteranAssessmentId, childAV,
								responses, measureAnswerHash);
						
						questionVariableDto.getChildren().add(childQuestionVariableDto);
						
					} catch ( AssessmentVariableInvalidValueException avive) {
						logger.warn(avive.getMessage());
						//this is so that when a child measure is missing, the parent should still 
						//be resolved.
					} catch (CouldNotResolveVariableException cnrve){
						logger.warn(cnrve.getMessage());
						//this is so that when a child measure is missing, the parent should still 
						//be resolved.
					}

					tabularRow = tabularRow + 1;
				} while (true);
			}
		}
		return questionVariableDto;
	}

	private String resolveFreeTextCalculationValue(
			AssessmentVariable answerVariable,
			List<SurveyMeasureResponse> responses, Integer veteranAssessmentId) {

		String result = null;
		if (responses.size() > 1)
			throw new CouldNotResolveVariableException(
					String.format(
							"Measure of type: %s had more than one free text value, answerVariableId: %s, assessmentId: %s",
							AssessmentConstants.MEASURE_TYPE_FREE_TEXT,
							answerVariable.getAssessmentVariableId(),
							veteranAssessmentId));

		// There should only be one response for this type
		SurveyMeasureResponse response = responses.get(0);
		result = measureAnswerVariableResolver.resolveCalculationValue(
				answerVariable, veteranAssessmentId, response);

		return result;
	}

	private String resolveSelectOneCalculationValue(
			AssessmentVariable answerVariable,
			List<SurveyMeasureResponse> responses, Integer veteranAssessmentId) {

		String result = null;

		// look for the true value then call answer to pull the value
		for (SurveyMeasureResponse response : responses) {
			if (response.getBooleanValue() != null
					&& response.getBooleanValue()) {
				// call the answer level to resolve the value
				result = measureAnswerVariableResolver.resolveCalculationValue(
						answerVariable, veteranAssessmentId, response);
			}
		}

		return result;
	}

	/**
	 * Resolves response to an assessment variable for the measure answer represented 
	 * by the response, and appends this AV dto to the children of the passed in question AV.
	 * @param questionVariableDto the AV we are adding children to 
	 * @param response the veteran's response to the given question
	 * @param measureAnswerHash a map from measure_answer_id to assessment_variable
	 */
	private void addResolvedAnswerTo(AssessmentVariableDto questionVariableDto, SurveyMeasureResponse response, Map<Integer, AssessmentVariable> measureAnswerHash){
		Integer measureAnswerId = response.getMeasureAnswer().getMeasureAnswerId();
		if(measureAnswerHash.containsKey(measureAnswerId)){
			// call the answer level to resolve the value
			AssessmentVariableDto answerVariableDto = measureAnswerVariableResolver
					.resolveAssessmentVariable(measureAnswerHash.get(measureAnswerId), response, measureAnswerHash);
			
			questionVariableDto.getChildren().add(answerVariableDto);
		}
		else{
			logger.warn("The measure answer with ID {} was found to not be in the measureAnswerHash. This indicates possible DB data corruption because all measure answers should have a cooresponding assessment variable.", measureAnswerId);
		}
	}
	
	private AssessmentVariableDto resolveParentQuestion(
			AssessmentVariable assessmentVariable, Integer measureId,
			Integer veteranAssessmentId, List<SurveyMeasureResponse> responses,
			Map<Integer, AssessmentVariable> measureAnswerHash,
			ChildResolverFunction resolverFunction){
		AssessmentVariableDto questionVariableDto = new AssessmentVariableDto(assessmentVariable);

		Measure parentMeasure = assessmentVariable.getMeasure();
		Set<Measure> childMeasures = parentMeasure.getChildren();
		if (childMeasures.size() == 0)
			throw new CouldNotResolveVariableException(
					String.format(
							"Could not resolve Measure of type: %s, the measure was expected to have child measures but did not have any.  "
									+ "MeasureId: %s, AssessmentVariableId: %s, assessmentId: %s",
							AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX,
							parentMeasure.getMeasureId(),
							assessmentVariable.getAssessmentVariableId(),
							veteranAssessmentId));
		
		//resolve children
		for (Measure childMeasure : childMeasures) {
			Integer childMeasureId = childMeasure.getMeasureId(); 
			
			List<SurveyMeasureResponse> childResponses = surveyMeasureResponseRepository
					.getForVeteranAssessmentAndMeasure(veteranAssessmentId, childMeasure.getMeasureId());
			
			resolverFunction.addChild(questionVariableDto, childMeasure.getAssessmentVariable(), 
					childMeasureId, veteranAssessmentId, childResponses, 
					measureAnswerHash);
		}
		
		return questionVariableDto;
	}
	
	//if only we had lamdas
	private interface ChildResolverFunction {
		void addChild(AssessmentVariableDto parentAvDto, AssessmentVariable childAV, Integer childMeasureId, 
				Integer veteranAssessmentId, List<SurveyMeasureResponse> childResponses, Map<Integer, AssessmentVariable> measureAnswerHash);
	}
	
	private class SelectOneChildResolverFunction implements ChildResolverFunction {
		@Override
		public void addChild(AssessmentVariableDto parentAvDto, AssessmentVariable childAV, Integer childMeasureId, 
				Integer veteranAssessmentId, List<SurveyMeasureResponse> childResponses, Map<Integer, AssessmentVariable> measureAnswerHash){
			
			parentAvDto.getChildren().add( 
					resolveSelectOneAssessmentVariableQuestion(childAV, childMeasureId, 
							veteranAssessmentId, childResponses, measureAnswerHash));
		}
	}
	
	private class SelectMultiChildResolverFunction implements ChildResolverFunction {
		@Override
		public void addChild(AssessmentVariableDto parentAvDto, AssessmentVariable childAV, Integer childMeasureId, 
				Integer veteranAssessmentId, List<SurveyMeasureResponse> childResponses, Map<Integer, AssessmentVariable> measureAnswerHash){
			
			parentAvDto.getChildren().add( 
					resolveSelectMultiAssessmentVariableQuestion(childAV, childMeasureId, 
							veteranAssessmentId, childResponses, measureAnswerHash));
		}
	}

	
	@Override
	public String resolveCalculationValue(AssessmentVariable measureVariable,
			Pair<Measure, gov.va.escreening.dto.ae.Measure> answer,
			Map<Integer, AssessmentVariable> measureAnswerHash) {
		
		String result = null;
		int measureTypeId = measureVariable.getMeasure().getMeasureType()
				.getMeasureTypeId();
		switch (measureTypeId) {
		case AssessmentConstants.MEASURE_TYPE_FREE_TEXT:
			result = answer.getRight().getAnswers().get(0).getAnswerResponse();
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_ONE:
			result = resolveSelectOneCalculationValue(measureVariable,
					answer);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_ONE_MATRIX:
			result = resolveSelectOneCalculationValue(measureVariable,
					answer);
			break;
		case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI:
		case AssessmentConstants.MEASURE_TYPE_SELECT_MULTI_MATRIX:
		case AssessmentConstants.MEASURE_TYPE_TABLE:
		case AssessmentConstants.MEASURE_TYPE_READ_ONLY:
		case AssessmentConstants.MEASURE_TYPE_INSTRUCTION:
		default:
			throw new UnsupportedOperationException(
					String.format(
							"Resolution of calculation value for measure type of: %s is not supported.",
							measureTypeId));
		}

//		if (result == null)
//			
//		 throw new CouldNotResolveVariableValueException(String.format("Was unable to resolve the calculation value for measureid: %s",
//		     measureId));

		return result;
	}

	private String resolveSelectOneCalculationValue(
			AssessmentVariable measureVariable,
			Pair<Measure, gov.va.escreening.dto.ae.Measure> answer) {
		
		String result = null;

		// look for the true value then call answer to pull the value
		for (Answer answerVal : answer.getRight().getAnswers()) {
			if(answerVal.getAnswerResponse()!=null && answerVal.getAnswerResponse().equalsIgnoreCase("true"))
			{
				// call the answer level to resolve the value
				result = measureAnswerVariableResolver.resolveCalculationValue(answer.getLeft(), answerVal);
				break;
			}
		}

		return result;
	}
}