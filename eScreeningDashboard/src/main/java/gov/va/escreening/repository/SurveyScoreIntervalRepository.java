package gov.va.escreening.repository;

import gov.va.escreening.entity.SurveyScoreInterval;

import java.util.List;

/**
 * Created by kliu on 3/6/15.
 */
public interface SurveyScoreIntervalRepository extends RepositoryInterface<SurveyScoreInterval>{

    public List<SurveyScoreInterval> getIntervalsBySurvey(Integer surveyId);
}
