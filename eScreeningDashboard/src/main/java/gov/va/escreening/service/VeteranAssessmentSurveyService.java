package gov.va.escreening.service;

import gov.va.escreening.dto.VeteranAssessmentProgressDto;
import gov.va.escreening.entity.Survey;

import java.util.List;

public interface VeteranAssessmentSurveyService {

    /**
     * Get all surveys associated with the given assessment ID.
     * @param assessmentId the ID of the assessment to get surveys for
     */
    List<Survey> getSurveys(Integer veteranAssessmentId);

    /**
     * Updates the assessment progress for veteranAssessmentId.
     * @param veteranAssessmentId
     */
    void updateProgress(int veteranAssessmentId, long sessionCreationTime);

    /**
     * Tests to see if the specified veteranAssessmentId contains the specified survey
     * @param veteranAssessmentId
     * @param surveyId
     * @return
     */
    Boolean doesVeteranAssessmentContainSurvey(int veteranAssessmentId, int surveyId);

    /**
     * Retrieves the progress by survey section.
     * @param veteranAssessmentId
     * @return
     */
    List<VeteranAssessmentProgressDto> getProgress(int veteranAssessmentId);
}
