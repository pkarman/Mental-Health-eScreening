package gov.va.escreening.repository;

import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ListMultimap;

public interface SurveyMeasureResponseRepository extends RepositoryInterface<SurveyMeasureResponse> {

    /**
     * Retrieves the SurveyMeasureReponse for a veteran assessment and measureId.
     * @param veteranAssessmentId
     * @param measureId
     * @return
     */
    List<SurveyMeasureResponse> getForVeteranAssessmentAndMeasure(int veteranAssessmentId, int measureId);
	
    /**
     * Retrieves the SurveyMeasureReponse for a veteran assessment and survey.
     * @param veteranAssessmentId
     * @param surveyId
     * @return a ListMultimap mapping from answer IDs to responses. Calling get() for an answer ID, will return a Collection with SurveyMeasureResponse.tabularRow iteration order.
     */
     ListMultimap<Integer, SurveyMeasureResponse> getForVeteranAssessmentAndSurvey(int veteranAssessmentId, int surveyId);

    /**
     * Retrieves the SurveyMeasureResposne based on veteran assessment and measure answer.
     * @param veteranAssessmentId
     * @param measureAnswerId
     * @param tabularRow the optional row index 
     * @return
     */
    SurveyMeasureResponse find(int veteranAssessmentId, int measureAnswerId, @Nullable Integer tabularRow);

    /**
     * Retrieves the SurveyMeasureResposne based on veteran assessment, measure, and tabularRow id.
     * @param veteranAssessmentId
     * @param measureId
     * @param tabularRow 
     * @return
     */
    List<SurveyMeasureResponse> findForAssessmentIdMeasureRow(int veteranAssessmentId, int measureId, int tabularRow);
    
    /**
     * <strong>Permanently</strong> deletes a measure answer.
     * @param veteranAssessmentId 
     * @param surveyId
     * @param measure - the measure that should have its responses deleted
     * @param responsesToLeave - a comma delimited list of survey_measure_response_id that should not be deleted
     * @return the number of responses deleted
     */
    int deleteResponseForMeasureAnswerId(Integer veteranAssessmentId, Integer surveyId,
            gov.va.escreening.entity.Measure measure, String responsesToLeave);
    
    /**
     * <strong>Permanently</strong> deletes all answers for all given measure IDs for the given assessment.
     * @param veteranAssessmentId - assessment ID for which the answers will be deleted
     * @param measureIds - the measures for which we should delete all answers
     * @return the number of responses deleted
     */
    int deleteResponsesForMeasures(Integer veteranAssessmentId, Collection<Integer> measureIds);

    /**
     * Returns all the responses to surveys for a veteran assessment.
     * @param veteranAssessmentId
     * @return
     */
    List<SurveyMeasureResponse> findForVeteranAssessmentId(int veteranAssessmentId);
    
    /**
     * Get the number of rows for a table type question by measaureid and assessmentid
     * @param veteranAssessmentId
     * @param measureId
     * @return
     */
    Integer getNumRowsForAssessmentIdMeasure(int veteranAssessmentId, Measure parentMeasureId);
}