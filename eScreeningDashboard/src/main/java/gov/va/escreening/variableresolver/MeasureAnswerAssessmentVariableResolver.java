package gov.va.escreening.variableresolver;

import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.SurveyMeasureResponse;

import org.apache.commons.lang3.tuple.Pair;

//TODO: see if we can remove this interface
public interface MeasureAnswerAssessmentVariableResolver extends VariableResolver{
	
	String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, 
			SurveyMeasureResponse response);
	
	String resolveCalculationValue(AssessmentVariable assessmentVariable, Integer veteranAssessmentId, NullValueHandler smrNullHandler);

	String resolveCalculationValue(AssessmentVariable answerVariable,
			Pair<Measure, gov.va.escreening.dto.ae.Measure> answer);

	/**
	 * Resolves the given AV using the given response. 
	 * @param assessmentVariable
	 * @param params
	 * @param response
	 * @return
	 */
	public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,
            ResolverParameters params, Answer response);
            
	/**
	 * @param answer
	 * @param index if index is null then null is returned
	 * @return
	 */
	String resolveCalculationValue(Pair<Measure, gov.va.escreening.dto.ae.Measure> answer, Integer index);
}
