package gov.va.escreening.repository;

import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessmentSurvey;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kliu on 3/3/15.
 */
@Repository
public class VeteranAssessmentSurveyScoreRepositoryImpl extends AbstractHibernateRepository<VeteranAssessmentSurveyScore>
        implements VeteranAssessmentSurveyScoreRepository {

    @Autowired
    private VeteranRepository veteranRepository;

    public VeteranAssessmentSurveyScoreRepositoryImpl() {
        super();

        setClazz(VeteranAssessmentSurveyScore.class);
    }

    @Override
    public List<VeteranAssessmentSurveyScore> getDataForIndividual(String lastName, String ssnLastFour, List<Integer> surveyIds, String fromDate, String toDate) {

        List<VeteranAssessmentSurveyScore> scores = new ArrayList<>();

        List<Veteran> veterans = veteranRepository.searchVeterans(lastName, ssnLastFour);



        if (veterans != null && veterans.size() > 0) {

            List<Integer> veteranIds = new ArrayList<>(veterans.size());

            for(Veteran v : veterans){
                veteranIds.add(v.getVeteranId());
            }

            String hql = "select vassr from VeteranAssessmentSurveyScore vassr  " +
                    " where vassr.dateCompleted >= :fromDate " +
                    " and vassr.dateCompeted <= :toDate " +
                    " and vassr.survey.id in ( :surveyIds ) " +
                    " and vassr.veteran.id in ( :veteranIds )" +
                    " order by vassr.survey.id, vassr.dateCompleted desc ";

            TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);
            query.setParameter("surveyIds", surveyIds);
            query.setParameter("veteranIds", veteranIds);
            query.setParameter("fromDate", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(fromDate+" 00:00:00"));
            query.setParameter("toDate", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(toDate+" 23:59:59"));

            scores = query.getResultList();

        }
        return scores;
    }

    @Override
    public List<VeteranAssessmentSurveyScore> getIndividualDataForClicnic(List<Integer> clinicIds, List<Integer> surveyIds,
                                                                String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr "+
                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompeted <= :toDate " +
                " and vassr.clinic.id in ( :clinicIds ) "+
                " and vassr.survey.id in ( :surveyIds ) " +
                " order by vassr.veteran.id, vassr.survey.id, vassr.dateCompleted asc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);

        query.setParameter("clinicIds", clinicIds);
        query.setParameter("surveyIds", surveyIds);
        query.setParameter("fromDate", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(fromDate+" 00:00:00"));
        query.setParameter("toDate", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(toDate+" 23:59:59"));

        return query.getResultList();
    }

    @Override
    public List<VeteranAssessmentSurveyScore> getDataForClicnic(List<Integer> clinicIds, List<Integer> surveyIds,
                                                                          String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr "+
                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompeted <= :toDate " +
                " and vassr.clinic.id in ( :clinicIds ) "+
                " and vassr.survey.id in ( :surveyIds ) " +
                " order by vassr.clinic.id, vassr.survey.id, vassr.dateCompleted asc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);

        query.setParameter("clinicIds", clinicIds);
        query.setParameter("surveyIds", surveyIds);
        query.setParameter("fromDate", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(fromDate+" 00:00:00"));
        query.setParameter("toDate", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(toDate+" 23:59:59"));

        return query.getResultList();
    }
}
