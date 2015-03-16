package gov.va.escreening.repository;

import gov.va.escreening.dto.report.ScoreDateDTO;
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

    /**
     * Query for 601
     *
     * @param surveyId
     * @param veteranId
     * @param fromDate
     * @param toDate
     * @return
     */
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

    /**
     * Query for 600 Individual data for clinic
     *
     * @param clinicIds
     * @param surveyIds
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public List<VeteranAssessmentSurveyScore> getIndividualDataForClicnic(Integer clinicId, List<Integer> surveyIds,
                                                                          String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr " +
                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompleted <= :toDate " +
                " and vassr.clinic.id = :clinicId  " +
                " and vassr.survey.id = (:surveyIds)  " +
                " order by vassr.clinic.id, vassr.veteran.id, vassr.survey.id,  vassr.dateCompleted asc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);

        query.setParameter("clinicId", clinicId);
        query.setParameter("surveyIds", surveyIds);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        return query.getResultList();
    }

    @Override
    public int getVeteranCountForClinic(Integer clinicId, Integer surveyId, String fromDate, String toDate){
        String sql = "select count(distinct(veteran_id)) "+
                " from veteran_assessment_survey_score s " +
                " where clinic_id = :clinicId and survey_id = :surveyId " +
                " and date_completed >= :fromDate and date_completed <= :toDate ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("clinicId", clinicId);
        query.setParameter("surveyId", surveyId);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        List<Object> result =  query.getResultList();

        return ((Number)result.get(0)).intValue();
    }

    @Override
    public List<ScoreDateDTO> getDataForClicnic(Integer clinicId, Integer surveyId,
                                                                String fromDate, String toDate) {

        String sql = "select c.name, survey_id, date(date_completed), count(*), avg(survey_score) " +
                " from veteran_assessment_survey_score s " +
                " inner join clinic c on s.clinic_id = c.clinic_id " +
                " where c.clinic_id = :clinicId and survey_id = :surveyId " +
                " and date_completed >= :fromDate and date_completed <= :toDate " +
                " group by c.name, survey_id, date(date_completed) " +
                " order by c.name, survey_id, date(date_completed) ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("clinicId", clinicId);
        query.setParameter("surveyId", surveyId);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        List<Object[]> result =  query.getResultList();

        List<ScoreDateDTO> scores = new ArrayList<>(result.size());

        for(Object [] objects : result){
            ScoreDateDTO scoreDateDTO = new ScoreDateDTO();
            scoreDateDTO.setClinicName((String)objects[0]);
            scoreDateDTO.setSurveyId((Integer)objects[1]);
            scoreDateDTO.setDateCompleted((Date)objects[2]);
            scoreDateDTO.setCount(((Number)objects[3]).intValue());
            scoreDateDTO.setScore(((Number)objects[4]).floatValue());

            scores.add(scoreDateDTO);
        }

        return scores;
    }



    @Override
    public List getAverageForClicnic(Integer clinicId, Integer surveyId,
                                  String fromDate, String toDate) {

        String sql = "select clinic_id, survey_id, avg(survey_score) " +
                " from veteran_assessment_survey_score " +
                " where clinic_id = :clinicId and survey_id = :surveyId " +
                " and date_completed >= :fromDate and date_completed <= :toDate " +
                " group by clinic_id, survey_id " +
                " order by clinic_id, survey_id  ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("clinicId", clinicId);
        query.setParameter("surveyId", surveyId);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        List result =  query.getResultList();

        return result;
    }
}
