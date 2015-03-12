package gov.va.escreening.service;

/**
 * Created by kliu on 3/6/15.
 */
public interface SurveyScoreIntervalService {

    public String getScoreMeaning(Integer surveyId, Number score);

    public String generateMetadataJson(Integer surveyId);
}
