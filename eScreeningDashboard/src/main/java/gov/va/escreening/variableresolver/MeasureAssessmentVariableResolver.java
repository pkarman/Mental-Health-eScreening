package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

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

	String resolveCalculationValue(AssessmentVariable measureVariable,
			Pair<Measure, gov.va.escreening.dto.ae.Measure> answer,
			Map<Integer, AssessmentVariable> measureAnswerHash);
}
