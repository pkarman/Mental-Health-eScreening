package gov.va.escreening.templateprocessor;

import java.util.EnumSet;

import gov.va.escreening.constants.TemplateConstants.TemplateType;
import gov.va.escreening.constants.TemplateConstants.ViewType;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.exception.TemplateProcessorException;

public interface TemplateProcessorService {

	/**
	 * Generates the CPRS Note for the given assessment
	 * @param veteranAssessmentId
	 * @param viewType from {@link TemplateTagProcessor} indicates how the the note should be rendered 
	 * @param optionalTemplates a set of optional {@link TemplateType} which will be included. Header and footer will always be included. 
	 * Supported template types are: {@link TemplateType.VISTA_QA} and {@link TemplateType.ASSESS_SCORE_TABLE}  
	 * @return the rendered CPRS Note
     * @throws IllegalSystemStateException if the system is missing a required template or template property. These 
     * errors can be fixed by updating the database.
     * @throws TemplateProcessorException if a bug was found in code (including template code), or a needed resource cannot be found. 
	 */
	public String generateCPRSNote(int veteranAssessmentId, ViewType viewType, EnumSet<TemplateType> optionalTemplates) throws IllegalSystemStateException, TemplateProcessorException;

	/**
	 * Convenience method for when no optional templates should be used.
	 * @param veteranAssessmentId
	 * @param viewType from {@link TemplateTagProcessor} indicates how the the note should be rendered   
	 * @return the rendered CPRS Note
     * @throws IllegalSystemStateException if the system is missing a required template or template property. These 
     * errors can be fixed by updating the database.
     * @throws TemplateProcessorException if a bug was found in code (including template code), or a needed resource cannot be found. 
	 */
	public String generateCPRSNote(int veteranAssessmentId, ViewType viewType) throws IllegalSystemStateException, TemplateProcessorException;

	
	/**
	 * Renders the given assessment's veteran summary printout (this is always in HTML)
	 * @param veteranAssessmentId
	 * @return HTML of the veteran summary printout
	 */
	public String generateVeteranPrintout(int veteranAssessmentId) throws IllegalSystemStateException, TemplateProcessorException;	
	
}
