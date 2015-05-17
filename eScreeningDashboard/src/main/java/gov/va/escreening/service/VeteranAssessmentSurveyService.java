package gov.va.escreening.service;

import gov.va.escreening.dto.VeteranAssessmentProgressDto;
import gov.va.escreening.dto.ae.AssessmentRequest;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentMeasureVisibility;

import java.util.List;
import java.util.Map;

public interface VeteranAssessmentSurveyService {

    /**
     * Get all surveys associated with the given assessment ID.
     * @param assessmentId the ID of the assessment to get surveys for
     */
    List<Survey> getSurveys(Integer veteranAssessmentId);

//    /**
//     * Updates the assessment progress for veteranAssessmentId.
//     * @param veteranAssessmentId
//     */
//    void updateProgress(int veteranAssessmentId, long sessionCreationTime);

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

	public abstract void updateProgress(VeteranAssessment va, AssessmentRequest req,
			Survey survey, List<VeteranAssessmentMeasureVisibility> visList);

    Map<Integer,Integer> calculateAvgTimePerSurvey(Map<String, Object> requestData);
}
