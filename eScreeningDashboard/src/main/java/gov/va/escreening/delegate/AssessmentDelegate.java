package gov.va.escreening.delegate;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.dto.ae.AssessmentResponse;
import gov.va.escreening.dto.ae.CompletionResponse;
import gov.va.escreening.entity.SurveySection;
import gov.va.escreening.entity.VeteranAssessment;
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
     * @return
     */
    VeteranAssessment getAvailableVeteranAssessment(Integer veteranId);

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
     * @return A CompletionResponse for the current VeteranAssessment
     */
    CompletionResponse getCompletionResponse();
    
    /**
     * Updates the assessment status as complete and logs it to the database.
     */
    void markAssessmentAsComplete();
}
