package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;

import java.util.Map;

public interface FormulaAssessmentVariableResolver {
	AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable,  
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash);
}
