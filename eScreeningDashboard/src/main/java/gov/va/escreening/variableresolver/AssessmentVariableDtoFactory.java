package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.VariableTemplate;

import java.util.List;
import java.util.Map;

public interface AssessmentVariableDtoFactory {
    
	AssessmentVariableDto createAssessmentVariableDto(AssessmentVariable assessmentVariable, Integer veteranAssessmentId,
			Map<Integer, AssessmentVariable> measureAnswerHash);
	
	/**
	 * Creates a Map from {@link gov.va.escreening.entity.MeasureAnswer} ID to AssessmentVariable. Only 
	 * AssessmentVariable of type AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER are included.
	 */
	Map<Integer, AssessmentVariable> createMeasureAnswerTypeHash(List<VariableTemplate> variableTemplates);
	
	/**
     * Creates a Map from {@link gov.va.escreening.entity.MeasureAnswer} ID to AssessmentVariable. Only 
     * AssessmentVariable of type AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER are included.
     */
    Map<Integer, AssessmentVariable> createMeasureAnswerTypeHash(Iterable<AssessmentVariable> assessmentVariables);
}

