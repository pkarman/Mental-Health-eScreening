package gov.va.escreening.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.dto.report.Report599DTO;
import gov.va.escreening.dto.report.ScoreDateDTO;
import gov.va.escreening.entity.VeteranAssessmentSurveyScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

    @Resource(name = "modulesForPositiveScreeningMap")
    Map<String, String> modulesForPositiveScreeningMap;

    Map<String, List<Map>> posNegScreenScoreRulesMap;

    /**
     * build posNegScreenScoreRulesMap after dependency injection is done to perform any initialization
     */
    @PostConstruct
    private void constructPosNegScreenScoreRulesMap() {
        Gson gson = new GsonBuilder().create();
        this.posNegScreenScoreRulesMap = Maps.newHashMap();
        for (String moduleName : modulesForPositiveScreeningMap.keySet()) {
            String modulePosNegJson = modulesForPositiveScreeningMap.get(moduleName);
            List<Map> modulePosNegMap = gson.fromJson(modulePosNegJson, List.class);
            this.posNegScreenScoreRulesMap.put(moduleName, modulePosNegMap);
        }
    }

    public VeteranAssessmentSurveyScoreRepositoryImpl() {
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
    public List<VeteranAssessmentSurveyScore> getDataForIndividual(Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr  " +
                " where vassr.dateCompleted between :fromDate and :toDate " +
                " and vassr.survey.id = :surveyId  " +
                ((avName != null) ? " and vassr.avName = :avName  " : "") +
                " and vassr.veteran.id = :veteranId " +
                " and vassr.screenNumber is null" +
                " order by vassr.dateCompleted desc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);
        query.setParameter("surveyId", surveyId);
        if (avName != null) query.setParameter("avName", avName);
        query.setParameter("veteranId", veteranId);

        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        return query.getResultList();
    }

    @Override
    public List<VeteranAssessmentSurveyScore> getDataForIndividual(Integer clinicId, Integer surveyId, String avName, Integer veteranId, String fromDate, String toDate) {

        String hql = "select vassr from VeteranAssessmentSurveyScore vassr  " +
                " where vassr.dateCompleted >= :fromDate " +
                " and vassr.dateCompleted <= :toDate " +
                " and vassr.survey.id = :surveyId  " +
                ((avName != null) ? " and vassr.avName = :avName  " : "") +
                " and vassr.veteran.id = :veteranId " +
                " and vassr.clinic.id = :clinicId " +
                " and vassr.screenNumber is null " +
                " order by vassr.dateCompleted desc ";

        TypedQuery<VeteranAssessmentSurveyScore> query = entityManager.createQuery(hql, VeteranAssessmentSurveyScore.class);
        query.setParameter("surveyId", surveyId);
        if (avName != null) query.setParameter("avName", avName);
        query.setParameter("veteranId", veteranId);
        query.setParameter("clinicId", clinicId);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        return query.getResultList();
    }

    /**
     * Query for 600 Individual data for clinic
     *
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
    public int getVeteranCountForClinic(Integer clinicId, Integer surveyId, String avName, String fromDate, String toDate) {
        String sql = "SELECT count(DISTINCT(veteran_id)) " +
                " FROM veteran_assessment_survey_score s " +
                " WHERE clinic_id = :clinicId AND survey_id = :surveyId " +
                ((avName != null) ? " AND s.av_name = :avName " : "") +
                " AND s.date_completed between :fromDate :toDate " +
                " AND s.screen_number IS NULL ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("clinicId", clinicId);
        query.setParameter("surveyId", surveyId);
        if (avName != null) query.setParameter("avName", avName);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        List<Object> result = query.getResultList();

        return ((Number) result.get(0)).intValue();
    }

    @Override
    public List<Report599DTO> getClinicStatisticReportsPartVIPositiveScreensReport(String fromDate, String toDate, List<Integer> clinicIds) {

        List<Report599DTO> dtos = new ArrayList<>();

        // step#1: partition modules in three lists
        // (1) modules with assessment variables which do not need to be splitted. use generic Query q
        List<String> regularModules = partitionRegularModules();
        processRegularModules(dtos, regularModules, fromDate, toDate, clinicIds);
        // (2) modules with assessment variables which needs to be splitted (splittable). use a Query with group by splittable
        Map<String, List<Map>> splittableModules = partitionSplittableModules();
        processSplittableModules(dtos, splittableModules, fromDate, toDate, clinicIds);
        // (3) modules with assessment variables which needs to be splitted (non-splittable). Use a Query with no splittable assessment variables
        processNonSplittablesFromSplittableModules(dtos, splittableModules, fromDate, toDate, clinicIds);


        for (Report599DTO dto : dtos) {
            int total = 0;
            if (dto.getMissingCount() == null) {
                dto.setMissingCount("0");
            } else {
                total += Integer.parseInt(dto.getMissingCount());
            }

            if (dto.getNegativeCount() == null) {
                dto.setNegativeCount("0");
            } else {
                total += Integer.parseInt(dto.getNegativeCount());
            }

            if (dto.getPositiveCount() == null) {
                dto.setPositiveCount("0");
            } else {
                total += Integer.parseInt(dto.getPositiveCount());
            }

            if (total == 0) {
                dto.setMissingPercent("0%");
                dto.setPositivePercent("0%");
                dto.setNegativePercent("0%");

                dto.setMissingCount("0/0");
                dto.setPositiveCount("0/0");
                dto.setNegativeCount("0/0");
            } else {
                dto.setMissingPercent(
                        String.format("%3.0f%%", Float.parseFloat(dto.getMissingCount()) / total * 100)
                );
                dto.setPositivePercent(
                        String.format("%3.0f%%", Float.parseFloat(dto.getPositiveCount()) / total * 100)
                );
                dto.setNegativePercent(
                        String.format("%3.0f%%", Float.parseFloat(dto.getNegativeCount()) / total * 100)
                );
                dto.setMissingCount(dto.getMissingCount() + "/" + total);
                dto.setPositiveCount(dto.getPositiveCount() + "/" + total);
                dto.setNegativeCount(dto.getNegativeCount() + "/" + total);
            }
        }
        return dtos;
    }

    private void processNonSplittablesFromSplittableModules(List<Report599DTO> dtos, Map<String, List<Map>> splittableModules, String fromDate, String toDate, List<Integer> clinicIds) {
        for (String moduleName : splittableModules.keySet()) {
            Query q = entityManager.createNativeQuery("SELECT survey.name, score.screen_number, count(*) " +
                    "FROM veteran_assessment_survey_score score INNER JOIN survey survey " +
                    "ON score.survey_id = survey.survey_id " +
                    "WHERE score.screen_number IS NOT NULL AND survey.name = :surveyName " +
                    "AND score.av_name NOT IN (:splittableAvs) " +
                    "AND score.date_completed BETWEEN :fromDate AND :toDate " +
                    "AND score.clinic_id IN (:clinicIds) " +
                    "GROUP BY survey.name, score.screen_number " +
                    "ORDER BY survey.name, score.screen_number");

            setParameters(q, fromDate, toDate, clinicIds);
            q.setParameter("surveyName", moduleName);
            List<String> splittableAvs = getAvs(splittableModules.get(moduleName));
            q.setParameter("splittableAvs", splittableAvs);
            List<Object[]> rows = q.getResultList();
            primeDTosFromRawRows(dtos, rows);
        }
    }

    private void processSplittableModules(List<Report599DTO> dtos, Map<String, List<Map>> splittableModules, String fromDate, String toDate, List<Integer> clinicIds) {

        for (String moduleName : splittableModules.keySet()) {
            for (Map m : splittableModules.get(moduleName)) {
                Query q = entityManager.createNativeQuery("SELECT survey.name, score.screen_number, count(*) " +
                        "FROM veteran_assessment_survey_score score INNER JOIN survey survey " +
                        "ON score.survey_id = survey.survey_id " +
                        "WHERE score.screen_number IS NOT NULL AND survey.name = :surveyName " +
                        "AND score.av_name = :splittableAv " +
                        "AND score.date_completed BETWEEN :fromDate AND :toDate " +
                        "AND score.clinic_id IN (:clinicIds) " +
                        "GROUP BY survey.name, score.screen_number " +
                        "ORDER BY survey.name, score.screen_number");

                setParameters(q, fromDate, toDate, clinicIds);
                q.setParameter("surveyName", moduleName);
                q.setParameter("splittableAv", m.get("var"));
                List<Object[]> rows = q.getResultList();

                for (Object[] r : rows) {
                    r[0] = m.get("alias").toString();
                }

                primeDTosFromRawRows(dtos, rows);
            }
        }
    }

    private List<String> getAvs(List<Map> splittableModules) {
        List<String> avLst = Lists.newArrayList();
        for (Map m : splittableModules) {
            avLst.add(m.get("var").toString());
        }
        return avLst;
    }

    private Map<String, List<Map>> partitionSplittableModules() {
        Map<String, List<Map>> modulesWithSplittableAVs = Maps.newHashMap();

        for (String moduleName : this.posNegScreenScoreRulesMap.keySet()) {
            List<Map> splittableAVs = Lists.newArrayList();
            for (Map posNegScreenScoreRulesMap : this.posNegScreenScoreRulesMap.get(moduleName)) {
                Boolean splittable = (Boolean) posNegScreenScoreRulesMap.get("split");
                if (splittable != null && splittable) {
                    splittableAVs.add(posNegScreenScoreRulesMap);
                }
            }
            if (!splittableAVs.isEmpty()) {
                modulesWithSplittableAVs.put(moduleName, splittableAVs);
            }
        }

        return modulesWithSplittableAVs;
    }

    private void processRegularModules(List<Report599DTO> dtos, List<String> regularModules, String fromDate, String toDate, List<Integer> clinicIds) {
        Query q = entityManager.createNativeQuery("SELECT survey.name, score.screen_number, count(*) " +
                "FROM veteran_assessment_survey_score score INNER JOIN survey survey " +
                "ON score.survey_id = survey.survey_id " +
                "WHERE score.screen_number IS NOT NULL AND survey.name IN (:surveyNames) " +
                "AND score.date_completed BETWEEN :fromDate AND :toDate " +
                "AND score.clinic_id IN (:clinicIds) " +
                "GROUP BY survey.name, score.screen_number " +
                "ORDER BY survey.name, score.screen_number");

        setParameters(q, fromDate, toDate, clinicIds);
        q.setParameter("surveyNames", regularModules);
        List<Object[]> rows = q.getResultList();
        primeDTosFromRawRows(dtos, rows);
    }

    private void primeDTosFromRawRows(List<Report599DTO> dtos, List<Object[]> rows) {
        Map<String, Report599DTO> cache = Maps.newHashMap();
        for (Object[] aRow : rows) {
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
            } else if (screenNumber == 999) {
                dto.setMissingCount(Integer.toString(count));
            }
        }

    }

    private List<String> partitionRegularModules() {
        List<String> regularModules = Lists.newArrayList();
        for (String moduleName : posNegScreenScoreRulesMap.keySet()) {
            if (!isSplittable(posNegScreenScoreRulesMap.get(moduleName))) {
                regularModules.add(moduleName);
            }
        }
        return regularModules;
    }

    private boolean isSplittable(List<Map> posNegScreenScoreRulesList) {
        for (Map posNegScreenScoreRulesMap : posNegScreenScoreRulesList) {
            Boolean splittable = (Boolean) posNegScreenScoreRulesMap.get("split");
            if (splittable != null && splittable) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ScoreDateDTO> getDataForClicnic(Integer clinicId, Integer surveyId,
                                                String avName, String fromDate, String toDate) {

        String sql = "SELECT c.name, survey_id, date(date_completed), count(*), avg(survey_score) " +
                " FROM veteran_assessment_survey_score s " +
                " INNER JOIN clinic c ON s.clinic_id = c.clinic_id " +
                " WHERE c.clinic_id = :clinicId AND survey_id = :surveyId " +
                ((avName != null) ? " AND s.av_name = :avName " : "") +
                " AND s.date_completed between :fromDate AND :toDate " +
                " AND s.screen_number IS NULL " +
                " GROUP BY c.name, survey_id, date(date_completed) " +
                " ORDER BY c.name, survey_id, date(date_completed) ";

        Query query = entityManager.createNativeQuery(sql);

        query.setParameter("clinicId", clinicId);
        query.setParameter("surveyId", surveyId);
        if (avName != null) query.setParameter("avName", avName);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));

        List<Object[]> result = query.getResultList();

        List<ScoreDateDTO> scores = new ArrayList<>(result.size());

        for (Object[] objects : result) {
            ScoreDateDTO scoreDateDTO = new ScoreDateDTO();
            scoreDateDTO.setClinicName((String) objects[0]);
            scoreDateDTO.setSurveyId((Integer) objects[1]);
            scoreDateDTO.setDateCompleted((Date) objects[2]);
            scoreDateDTO.setCount(((Number) objects[3]).intValue());
            scoreDateDTO.setScore(((Number) objects[4]).floatValue());

            scores.add(scoreDateDTO);
        }

        return scores;
    }


    private void setParameters(Query query, String fromDate, String toDate, List<Integer> clinicIds) {
        query.setParameter("clinicIds", clinicIds);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));
    }

    private void setParameters(Query query, String fromDate, String toDate, Integer clinicId) {
        query.setParameter("clinicId", clinicId);
        query.setParameter("fromDate", getDateFromString(fromDate + " 00:00:00"));
        query.setParameter("toDate", getDateFromString(toDate + " 23:59:59"));
    }

}
