package gov.va.escreening.variableresolver;

import gov.va.escreening.dto.ae.Answer;
import gov.va.escreening.entity.AssessmentVariable;

public interface MeasureAnswerAssessmentVariableResolver extends VariableResolver{
	
	/**
	 * Resolves the given AV using the given response. 
	 * @param assessmentVariable
	 * @param params
	 * @param response
	 * @return
	 */
	public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,
            ResolverParameters params, Answer response);
}
