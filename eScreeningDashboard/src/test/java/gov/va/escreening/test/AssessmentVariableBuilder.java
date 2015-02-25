package gov.va.escreening.test;

import gov.va.escreening.entity.User;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.test.TestAssessmentVariableBuilder.CustomAvBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.FreeTextAvBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.MatrixAVBuilder;
import gov.va.escreening.test.TestAssessmentVariableBuilder.SelectAVBuilder;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Builder interface for assessment variables used for integration testing 
 * of Templates and the resolver service classes. Mainly this interface allows
 * for a recursive builder pattern rooted in {@link TestAssessmentVariableBuilder}
 * 
 * @author Robin Carnow
 * 
 */
public interface AssessmentVariableBuilder{
	
	/**
	 * Adds a new free text measure to the assessment variable builder
	 * @param id of assessment variable should be a number under 100
	 * @param measureText the measure's text (question text shown to user)
	 * @param response if this is null you are testing what happens if the AV has no response
	 * @return FreeTextAvBuilder so free-text-specific methods can be called to further customize the added AV
	 */
	public FreeTextAvBuilder addFreeTextAV(int id, String measureText, String response);
	
	/**
	 * Adds a select one question
	 * @param avId AV ID for the added question
	 * @param questionText
	 * @return SelectAVBuilder so select-question-AV-specific methods can be called to further 
	 * customize the added AV (e.g. add answers)
	 */
	public SelectAVBuilder addSelectOneAV(@Nullable Integer avId, @Nullable String questionText);
	
	/**
	 * Adds a select multi question
	 * @param avId AV ID for the added question
	 * @param questionText
	 * @return SelectAVBuilder so select-question-AV-specific AV-specific methods can be called to further 
	 * customize the added AV (e.g. add answers)
	 */
	public SelectAVBuilder addSelectMultiAV(@Nullable Integer avId, @Nullable String questionText);
	
	/**
	 * Adds a select one matrix question AV
	 * @param avId
	 * @param questionText
	 * @return MatrixAVBuilder so matrix-AV-specific methods can be called to further 
	 * customize the added AV (e.g. add questions and columns)
	 */
	public MatrixAVBuilder addSelectOneMatrixAV(Integer avId, String questionText);
	
	/**
	 * Adds a select multi matrix question AV
	 * @param avId
	 * @param questionText
	 * @return MatrixAVBuilder so matrix-AV-specific methods can be called to further 
	 * customize the added AV (e.g. add questions and columns)
	 */
	public MatrixAVBuilder addSelectMultiMatrixAV(Integer avId, String questionText);
	
	/**
	 * Adds a new custom assessment variable to the builder 
	 * @param id of the custom AV. Please use constants defined in {@link CustomAssessmentVariableResolver}
	 * @return CustomAvBuilder so custom AV-specific methods can be called to further customize the added AV
	 */
	public CustomAvBuilder addCustomAV(int id);
	
	/**
	 * Method that builds the DTOs representing the AVs that have been added.
	 * @return list of built AssessmentVariableDto
	 */
	public List<AssessmentVariableDto> getDTOs();
	
}
