package gov.va.escreening.repository;

import gov.va.escreening.entity.SurveyPage;

import java.util.List;

import javax.annotation.Nullable;

public interface SurveyPageRepository extends RepositoryInterface<SurveyPage> {

    /**
     * Retrieves all the SurveyPages for a veteranAssessmentId in a sorted order;
     * @return
     */
    List<SurveyPage> getSurveyPagesForVeteranAssessmentId(int veteranAssessmentId);

    /**
     * For the given veteran survey, a bit vector is retrieved which indicates for each survey page, 
     * which ones have at least one answer.
     * The list of surveys are in display order so you can get previous/next unanswered/answered survey page.
     * @param veteranAssessmentId
     * @param surveyPageId
     * @return a List of tuples first value is the survey page ID, second is boolean telling if that 
     * page contains at least one response
     */
    public List<Object[]> getSuveyPageAnswerStatuses(int veteranAssessmentId);

    
    /**
     * For the given veteran survey, a bit vector is retrieved which indicates for each survey page, 
     * which have at least one skipped measure.
     * The list of surveys are in display order so you can get previous/next unanswered/answered survey page. 
     * @param veteranAssessmentId
     * @param surveySectionId - an integer representing the survey section ID to narrow the search to.  If null, then all pages are returned
     * @return a List of tuples first value is the survey page ID, second is an integer with value 0 or 1 telling if that 
     * page contains at least one skipped measure
     */
    public List<Object[]> getSurveyPageSkippedStatuses(int veteranAssessmentId, @Nullable Integer surveySectionId);

    /**
     * Same as other implementation but includes pages for entire assessment
     * @param veteranAssessmentId
     * @return
     */
    public List<Object[]> getSurveyPageSkippedStatuses(int veteranAssessmentId);
}
