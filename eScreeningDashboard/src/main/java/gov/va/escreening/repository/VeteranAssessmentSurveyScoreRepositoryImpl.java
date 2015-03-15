package gov.va.escreening.repository;

import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private Date getDateFromString(String str){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        try {
            return simpleDateFormat.parse(str);
        }
        catch(ParseException e){
            return null;
        }

    }

    @Override
    public List<VeteranAssessmentSurveyScore> getDataForIndividual(Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr  " +
                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompleted <= :toDate " +
                " and vassr.survey.id = :surveyId  " +
                " and vassr.veteran.id = :veteranId " +
                " order by vassr.dateCompleted desc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);
        query.setParameter("surveyId", surveyId);
        query.setParameter("veteranId", veteranId);

        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        return query.getResultList();
    }

    @Override
    public List<VeteranAssessmentSurveyScore> getIndividualDataForClicnic(List<Integer> clinicIds, List<Integer> surveyId,
                                                                          String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr " +
                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompeted <= :toDate " +
                " and vassr.clinic.id in ( :clinicIds ) " +
                " and vassr.survey.id = :surveyId  " +
                " order by vassr.veteran.id, vassr.dateCompleted asc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);

        query.setParameter("clinicIds", clinicIds);
        query.setParameter("surveyId", surveyId);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));


        String q = " select avg(score), date(score.date_completed), score, v." +
                "                from veteran_assessment_survey_score score\n" +
                "        inner join veteran v on v.veteran_id = score.veteran_id\n" +
                "        inner join clinic c on c.clinic_id = score.clinic_id\n" +
                "        inner join survey s on s.survey_id = score.survey_id\n" +
                "        where v.veteran_id in (11)\n" +
                "        and score.survey_id in (22)\n" +
                "        and score.date_completed > 'aa'\n" +
                "        and score.date_completed < 'bb'\n" +
                "        group by survey_id, date(score.date_completed)";

        Query quer = entityManager.createNativeQuery(q);


        return quer.getResultList();
    }

    @Override
    public List<VeteranAssessmentSurveyScore> getDataForClicnic(List<Integer> clinicIds, List<Integer> surveyIds,
                                                                String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr " +
                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompeted <= :toDate " +
                " and vassr.clinic.id in ( :clinicIds ) " +
                " and vassr.survey.id in ( :surveyIds ) " +
                " order by vassr.clinic.id, vassr.survey.id, vassr.dateCompleted asc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);

        query.setParameter("clinicIds", clinicIds);
        query.setParameter("surveyIds", surveyIds);
        query.setParameter("fromDate", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(fromDate + " 00:00:00"));
        query.setParameter("toDate", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(toDate + " 23:59:59"));

        return query.getResultList();
    }
}
