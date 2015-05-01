package gov.va.escreening.repository;

import gov.va.escreening.entity.SurveyScoreInterval;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by kliu on 3/6/15.
 */
@Repository
public class SurveyScoreIntervalRepositoryImpl extends AbstractHibernateRepository<SurveyScoreInterval>
        implements SurveyScoreIntervalRepository {

    public SurveyScoreIntervalRepositoryImpl(){
        super();
        setClazz(SurveyScoreInterval.class);
    }

    @Override
    public List<SurveyScoreInterval> getIntervalsBySurvey(Integer surveyId) {

        TypedQuery<SurveyScoreInterval> query = entityManager.createQuery(
                "select i from SurveyScoreInterval i where i.survey.id = "+surveyId+" order by i.id",
                SurveyScoreInterval.class);
        return query.getResultList();
    }
}
