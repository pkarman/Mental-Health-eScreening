package gov.va.escreening.repository;

import gov.va.escreening.dto.report.Report599DTO;
import gov.va.escreening.dto.report.ScoreDateDTO;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;
import gov.va.escreening.util.ReportRepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

import static gov.va.escreening.util.ReportRepositoryUtil.*;

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
                " where vassr.dateCompleted between :fromDate and :toDate " +
                " and vassr.survey.id = :surveyId  " +
                " and vassr.veteran.id = :veteranId " +
                " and vassr.screenNumber is null" +
                " order by vassr.dateCompleted desc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);
        query.setParameter("surveyId", surveyId);
        query.setParameter("veteranId", veteranId);

        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        return query.getResultList();
    }

    @Override
    public List<VeteranAssessmentSurveyScore> getDataForIndividual(Integer clinicId, Integer surveyId, Integer veteranId, String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr  " +
                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompleted <= :toDate " +
                " and vassr.survey.id = :surveyId  " +
                " and vassr.veteran.id = :veteranId " +
                " and vassr.clinic.id = :clinicId " +
                " and vassr.screenNumber is null "+
                " order by vassr.dateCompleted desc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);
        query.setParameter("surveyId", surveyId);
        query.setParameter("veteranId", veteranId);
        query.setParameter("clinicId", clinicId);
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
                " and vassr.screenNumber is null " +
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
                " and date_completed >= :fromDate and date_completed <= :toDate " +
                " and s.screen_number is null ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("clinicId", clinicId);
        query.setParameter("surveyId", surveyId);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        List<Object> result =  query.getResultList();

        return ((Number)result.get(0)).intValue();
    }

    @Override
    public List<Report599DTO> getClinicStatisticReportsPartVIPositiveScreensReport(String fromDate, String toDate, List<Integer> clinicIds, List<String> surveyNameList) {

        Query q = entityManager.createNativeQuery("select survey.name, score.screen_number, count(*) from veteran_assessment_survey_score score inner join survey survey " +
                "on score.survey_id = survey.survey_id " +
                "where score.screen_number is not null and survey.name in (:surveyNames) " +
                "  and score.date_completed >= :fromDate and score.date_completed <= :toDate " +
                "  and score.clinic_id in (:clinicIds) " +
                " group by survey.name, score.screen_number " +
                " order by survey.name, score.screen_number ");

        setParameters(q, fromDate, toDate, clinicIds);
        q.setParameter("surveyNames", surveyNameList );

        List<Object[]>  rows = q.getResultList();

        List<Report599DTO> dtos = new ArrayList<>();

        Map<String, Report599DTO> cache = new HashMap<>();

        for(Object[] aRow : rows) {

            String moduleName = (String) aRow[0];
            Report599DTO dto = cache.get(moduleName);
            if (dto == null) {
                dto = new Report599DTO();
                dto.setModuleName(moduleName);
                cache.put(moduleName, dto);
                dtos.add(dto);
            }

            Integer screenNumber = ((Number) aRow[1]).intValue();
            Integer count = ((Number) aRow[2]).intValue();

            if (screenNumber == 0) {
                dto.setNegativeCount(Integer.toString(count));
            } else if (screenNumber == 1) {
                dto.setPositiveCount(Integer.toString(count));
            } else if (screenNumber == 999){
                dto.setMissingCount(Integer.toString(count));
            }
        }

        for(Report599DTO dto : dtos){

            int total = 0;
            if (dto.getMissingCount() == null){
                dto.setMissingCount("0");
            }
            else{
                total += Integer.parseInt(dto.getMissingCount());
            }

            if (dto.getNegativeCount()==null){
                dto.setNegativeCount("0");
            }else{
                total += Integer.parseInt(dto.getNegativeCount());
            }

            if (dto.getPositiveCount() == null){
                dto.setPositiveCount("0");
            }else{
                total += Integer.parseInt(dto.getPositiveCount());
            }

            if (total == 0){
                dto.setMissingPercent("0%");
                dto.setPositivePercent("0%");
                dto.setNegativePercent("0%");

                dto.setMissingCount("0/0");
                dto.setPositiveCount("0/0");
                dto.setNegativeCount("0/0");
            }else{
                dto.setMissingPercent(
                        String.format("%3.0f%%", Float.parseFloat(dto.getMissingCount()) / total * 100)
                );
                dto.setPositivePercent(
                        String.format("%3.0f%%", Float.parseFloat(dto.getPositiveCount()) / total * 100)
                );
                dto.setNegativePercent(
                        String.format("%3.0f%%", Float.parseFloat(dto.getNegativeCount()) / total * 100)
                );
                dto.setMissingCount(dto.getMissingCount()+"/"+total);
                dto.setPositiveCount(dto.getPositiveCount()+"/"+total);
                dto.setNegativeCount(dto.getNegativeCount()+"/"+total);
            }
        }

        return dtos;
    }

    @Override
    public List<ScoreDateDTO> getDataForClicnic(Integer clinicId, Integer surveyId,
                                                                String fromDate, String toDate) {

        String sql = "select c.name, survey_id, date(date_completed), count(*), avg(survey_score) " +
                " from veteran_assessment_survey_score s " +
                " inner join clinic c on s.clinic_id = c.clinic_id " +
                " where c.clinic_id = :clinicId and survey_id = :surveyId " +
                " and date_completed >= :fromDate and date_completed <= :toDate " +
                " and s.screen_number is null " +
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
                " and screen_number is null" +
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


    private void setParameters(Query query, String fromDate, String toDate, List<Integer> clinicIds){
        query.setParameter("clinicIds", clinicIds);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));
    }

    private void setParameters(Query query, String fromDate, String toDate, Integer clinicId){
        query.setParameter("clinicId", clinicId);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));
    }

}
