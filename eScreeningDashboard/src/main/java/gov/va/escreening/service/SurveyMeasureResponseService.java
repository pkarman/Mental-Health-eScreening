package gov.va.escreening.service;

import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface SurveyMeasureResponseService {

    /**
     * Retrieves any responses from the veteran for a survey measure.
     * @param veteranAssessmentId
     * @param measureQuestionMap
     * @return
     */
    Hashtable<Integer, List<SurveyMeasureResponse>> findForVeteranAssessmentId(int veteranAssessmentId, Map<Integer, Integer> measureQuestionMap);

    public abstract String generateQuestionsAndAnswers(Survey survey, Integer veteranAssessmentId);

}