package gov.va.escreening.repository;

import gov.va.escreening.entity.SurveySection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
class SurveySectionRepositoryImpl  extends AbstractHibernateRepository<SurveySection>
        implements SurveySectionRepository{

    private static final Logger logger = LoggerFactory.getLogger(SurveyRepositoryImpl.class);

    public SurveySectionRepositoryImpl() {
        super();
        setClazz(SurveySection.class);
    }
    
    public List<SurveySection> findForVeteranAssessmentId(int veteranAssessmentId){
        logger.debug("in findForVeteranAssessmentId()");

        String sql = "SELECT distinct ss FROM Survey s JOIN s.surveySection ss "
                + "WHERE s.surveyId IN (SELECT vas.survey.surveyId FROM VeteranAssessmentSurvey vas where vas.veteranAssessment.veteranAssessmentId=:veteranAssessmentId) "
                + "ORDER BY ss.displayOrder";

        TypedQuery<SurveySection> query = entityManager.createQuery(sql, SurveySection.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);

        return query.getResultList();
    }
    @Override
    public List<SurveySection> getSurveySectionList() {

        List<SurveySection> resultList = new ArrayList<SurveySection>();
        String sql = "FROM SurveySection s ORDER BY s.displayOrder, s.name, s.surveySectionId";

        TypedQuery<SurveySection> query = entityManager.createQuery(sql, SurveySection.class);

        resultList = query.getResultList();

        return resultList;
    }
}
