package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;


/**
 * Used to Spring inject, but after a refactor, the methods are in VariableResolver
 * 
 * @author Robin Carnow
 *
 */
public interface AssessmentVariableDtoFactory {
    /**
     * Resolves the given {@link AssessmentVariable} to an {@link AssessmentVariableDto} 
     * with veteran responses
     * @param assessmentVariable the assessment variable to resolve
     * @param params parameters to use during resolution of variable
     * @return resolved assessment variable DTO
     */
    public AssessmentVariableDto resolveAssessmentVariable(
            AssessmentVariable assessmentVariable,
            ResolverParameters params);
}

