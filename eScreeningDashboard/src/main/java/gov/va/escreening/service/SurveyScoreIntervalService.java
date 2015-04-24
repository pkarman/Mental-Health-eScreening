package gov.va.escreening.service;

import java.util.Map;

/**
 * Created by kliu on 3/6/15.
 */
public interface SurveyScoreIntervalService {

    public String getScoreMeaning(Integer surveyId, Number score);

    public Map<String, Object> generateMetadata(Integer surveyId, String avName);

}