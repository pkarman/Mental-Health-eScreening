package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.exception.CouldNotResolveVariableException;

import java.util.List;

public interface VariableResolverService {
    /**
     * Resolves all variables for the given template and assessment
     * @param veteranAssessmentId
     * @param templateId
     * @return
     * @throws CouldNotResolveVariableException if the assessmentVariable cannot be resolved for the given veteranAssessmentId
     */
	List<AssessmentVariableDto> resolveVariablesForTemplateAssessment(Integer veteranAssessmentId, Integer templateId);

	/**
	 * Resolves the given AssessmentVariable to a list of AssessmentVariableDto
	 * @return the resolved assessment variable dtos
	 * @throws CouldNotResolveVariableException if the assessmentVariable cannot be resolved for the given veteranAssessmentId
	 */
    Iterable<AssessmentVariableDto> resolveVariablesFor(Integer veteranAssessmentId, Iterable<AssessmentVariable> dbVariables);
}
