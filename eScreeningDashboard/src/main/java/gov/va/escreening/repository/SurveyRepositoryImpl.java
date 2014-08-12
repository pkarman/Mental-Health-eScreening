package gov.va.escreening.repository;

import gov.va.escreening.entity.Survey;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class SurveyRepositoryImpl extends AbstractHibernateRepository<Survey>
        implements SurveyRepository {

    private static final Logger logger = LoggerFactory.getLogger(SurveyRepositoryImpl.class);

    public SurveyRepositoryImpl() {
        super();
        setClazz(Survey.class);
    }

    @Override
    public List<Survey> getAssignableSurveys() {

        logger.debug("in getAssignableSurveys()");

        String sql = "SELECT s FROM Survey s JOIN s.surveySection ss WHERE ss.surveySectionId != :surveySectionId ORDER BY s.displayOrder";

        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);
        query.setParameter("surveySectionId", 1);

        List<Survey> assignableSurveys = query.getResultList();

        return assignableSurveys;
    }

    @Override
    public List<Survey> getRequiredSurveys() {
        logger.debug("in getRequiredSurveys()");
        String sql = "SELECT s FROM Survey s JOIN s.surveySection ss WHERE ss.surveySectionId = :surveySectionId ORDER BY s.displayOrder";

        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);
        query.setParameter("surveySectionId", 1);

        List<Survey> requiredSurveys = query.getResultList();

        return requiredSurveys;
    }
    
    @Override
    public List<Survey> findForVeteranAssessmentId(int veteranAssessmentId) {

        logger.debug("in findForVeteranAssessmentId()");

        String sql = "SELECT s FROM Survey s WHERE s.surveyId IN (SELECT s2.surveyId FROM VeteranAssessment va JOIN va.veteranAssessmentSurveyList vas JOIN vas.survey s2 WHERE va.veteranAssessmentId = :veteranAssessmentId) ORDER BY s.displayOrder";

        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);

        List<Survey> resultList = query.getResultList();

        return resultList;
    }
    
    @Override
    public List<Survey> getSurveyList() {
        
        String sql = "SELECT s FROM Survey s JOIN s.surveySection ss ORDER BY s.name";

        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);

        return query.getResultList();
    }
    
    @Override
    public List<Survey> getMhaSurveyList()
    {
        String sql = "From Survey s where s.hasMha='1'";
        TypedQuery<Survey> query = entityManager.createQuery(sql, Survey.class);

        return query.getResultList();
    }
}
