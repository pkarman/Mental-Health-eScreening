package gov.va.escreening.delegate;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.ae.AssessmentResponse;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.exception.IllegalSystemStateException;
import gov.va.escreening.exception.InvalidAssessmentContextException;

import java.util.List;

public interface AssessmentDelegate {

    /**
     * Finds the veteran in the local data store.
     * 
     * @param veteran
     * @return
     */
    List<VeteranDto> findVeterans(VeteranDto veteran);

    /**
     * Returns the assessment that was created beforehand by a clinician. If there is none, then null is returned. An
     * assessment status must be marked as 'Clean'
     * 
     * @param veteranId
     * @param programId
     * @return
     */
    VeteranAssessment getAvailableVeteranAssessment(Integer veteranId, Integer programId);

    /**
     * Configures the session scoped AssessmentContext so the Veteran can take an on-line assessment.
     * 
     * @param veteran
     * @param veteranAssessment
     */
    void setUpAssessmentContext(VeteranDto veteran, VeteranAssessment veteranAssessment);

    /**
     * Checks the current AssessmentContext and throws an InvalidAssessmentContextException if <br/>
     * the veteran is not logged in.<br/>
     * This should be called at the top of any veteran assessment controller method.
     * @throws InvalidAssessmentContextException
     */
    void ensureValidAssessmentContext();

    /**
     * Uses the current AssessmentContext to get the list of SurveySections
     * @return
     */
    List<SurveySection> getAssessmentSections();

    /**
     * Saves the submitted responses to measures, and returns the next page of the survey.
     * 
     * @param assessmentRequest
     * @return
     */
    AssessmentResponse processPage(AssessmentRequest assessmentRequest);
    
    /**
     * Returns the veteranAssessmentId from the AssessmentContext.
     * @return
     */
    Integer getVeteranAssessmentId();

    /**
     * Returns the full name of the veteran.
     * @return
     */
    String getVeteranFullName();

    /**
     * Updates the assessment status as complete and logs it to the database.
     */
    void markAssessmentAsComplete();

	/**
	 * Renders the assessment completion template and inserts it into a CompletionResponse
	 * @return CompletionResponse containing the completion message
	 * @throws EntityNotFoundException if the context has an assessment ID but there is no known assessment with that ID
	 * @throws InvalidAssessmentContextException if the current context does not have an assessment ID 
	 * @throws IllegalSystemStateException if no battery is found to be associated with the current assessment 
	 * context or the battery does not have a completion message template defined for it. 
	 */
	String getCompletionMessage() throws IllegalSystemStateException;

	/**
	 * Renders the assessment welcome message template using assessment variables related to the current AssessmentContext 
	 * @return the assessment welcome message
	 * @throws EntityNotFoundException if the context has an assessment ID but there is no known assessment with that ID
	 * @throws InvalidAssessmentContextException if the current context does not have an assessment ID 
	 * @throws IllegalSystemStateException if no battery is found to be associated with the current assessment 
	 * context or the battery does not have a welcome message template defined for it. 
	 */
	public String getWelcomeMessage() throws IllegalSystemStateException;

    void recordAllReportableScores(VeteranAssessment veteranAssessment);
}
