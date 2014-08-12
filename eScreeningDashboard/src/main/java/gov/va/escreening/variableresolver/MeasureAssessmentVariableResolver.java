package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;

import java.util.Map;

public interface MeasureAssessmentVariableResolver {
    
    /**
     * Resolves the given AssessmentVariable to an AssessmentVariableDto
     * @param assessmentVariable
     * @param veteranAssessmentId
     * @param measureAnswerHash
     * @return
     */
	AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable, 
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash);
	
	String resolveCalculationValue(AssessmentVariable assessmentVariable,  
			Integer veteranAssessmentId, Map<Integer, AssessmentVariable> measureAnswerHash);
}
