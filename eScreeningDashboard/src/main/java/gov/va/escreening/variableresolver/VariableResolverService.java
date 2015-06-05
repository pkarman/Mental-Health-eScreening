package gov.va.escreening.variableresolver;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.exception.CouldNotResolveVariableException;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Optional;

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
	 * Resolves the given AssessmentVariable to a list of AssessmentVariableDto. 
	 * If the same variables are to be resolved for different assessments, it is better to use {@link #resolveAssessmentVariable(AssessmentVariable, ResolverParameters)} 
	 * @param veteranAssessmentId
	 * @param dbVariables
	 * @return the resolved assessment variable dtos
	 * @throws CouldNotResolveVariableException if the assessmentVariable cannot be resolved for the given veteranAssessmentId
	 */
    Iterable<AssessmentVariableDto> resolveVariablesFor(Integer veteranAssessmentId, 
            Collection<AssessmentVariable> dbVariables);
    
    /**
     * 
     * @param assessmentVariable AV to resolve
     * @param veteranAssessmentId the assessment ID to resolve against
     * @param params the ResolverParameters object to utilize
     * @return an {@code Optional} of the resolved dto. Will be absent if an allowable 
     * exception occurred (e.g. variable could not be resolved).
     */
    public Optional<AssessmentVariableDto> resolveAssessmentVariable(AssessmentVariable assessmentVariable, 
            ResolverParameters params);
}
