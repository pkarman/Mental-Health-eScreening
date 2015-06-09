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
	 * This is a convenience method which includes responses copied from other assessments. See other method for details
	 * @throws CouldNotResolveVariableException if the assessmentVariable cannot be resolved for the given veteranAssessmentId
	 */
    Iterable<AssessmentVariableDto> resolveVariablesFor(Integer veteranAssessmentId, 
            Collection<AssessmentVariable> dbVariables);
    
    /**
     * Resolves the given AssessmentVariable list to a list of AssessmentVariableDto. 
     * If the same variables are to be resolved for different assessments, it is better to use {@link #resolveAssessmentVariable(AssessmentVariable, ResolverParameters)} 
     * @param veteranAssessmentId 
     * @param dbVariables variables to resolve 
     * @param includeCopiedResponse when true copied responses from other assessments will be included; otherwise 
     * they will be filtered out.
     * @return the resolved assessment variable dtos
     * @throws CouldNotResolveVariableException if the assessmentVariable cannot be resolved for the given veteranAssessmentId
     */
    public Iterable<AssessmentVariableDto> resolveVariablesFor(Integer veteranAssessmentId, 
            Collection<AssessmentVariable> dbVariables, boolean includeCopiedResponse);
    
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
