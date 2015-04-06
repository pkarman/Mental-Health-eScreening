package gov.va.escreening.repository;

import gov.va.escreening.entity.SurveyAttempt;
import org.springframework.stereotype.Repository;

/**
 * Created by munnoo on 3/24/15.
 */
@Repository("surveyAttemptRepository")
public class SurveyAttemptRepositoryImpl extends AbstractHibernateRepository<SurveyAttempt> implements SurveyAttemptRepository {
}
