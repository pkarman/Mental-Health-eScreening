package gov.va.escreening.service;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

public interface SurveyPageService {

    /**
     * Retrieves the first survey page ID containing a skipped question in the given survey section.
     * @param veteranAssessmentId
     * @param surveySectionId - ID of the survey section which we are targeting to find the first survey 
     * page containing a skipped question. If null, then all survey pages for the assessment are searched. 
     * @return the ID of the first surveyPage that has an unanswered measure for surveySectionId. 
     * If all measures have been answered, then it will return the first page of the survey section.
     */
    Integer getFirstUnansweredSurveyPage(int veteranAssessmentId, @Nullable Integer surveySectionId);
    
    /**
     * @param veteranAssessmentId
     * @param surveyPageId
     * @return the ID of the next survey page that contains an unanswered question. The Optional will be empty if there are no more
     */
    Optional<Integer> getNextUnansweredSurveyPage(int veteranAssessmentId, int surveyPageId);
    
}
