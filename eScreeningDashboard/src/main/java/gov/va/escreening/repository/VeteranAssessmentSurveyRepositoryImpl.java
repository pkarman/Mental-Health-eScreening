package gov.va.escreening.repository;

import gov.va.escreening.dto.ae.Measure;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.VeteranAssessmentSurvey;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class VeteranAssessmentSurveyRepositoryImpl extends AbstractHibernateRepository<VeteranAssessmentSurvey>
        implements VeteranAssessmentSurveyRepository {

    public VeteranAssessmentSurveyRepositoryImpl() {
        super();

        setClazz(VeteranAssessmentSurvey.class);
    }

    public List<VeteranAssessmentSurvey> forVeteranAssessmentId(int veteranAssessmentId) {

        String sql = "SELECT vas FROM VeteranAssessmentSurvey vas WHERE vas.veteranAssessment.veteranAssessmentId = :veteranAssessmentId ORDER BY vas.survey.displayOrder";

        TypedQuery<VeteranAssessmentSurvey> query = entityManager.createQuery(sql, VeteranAssessmentSurvey.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);

        return query.getResultList();
    }

    public VeteranAssessmentSurvey getByVeteranAssessmentIdAndSurveyId(int veteranAssessmentId, int surveyId) {

        String sql = "SELECT vas FROM VeteranAssessmentSurvey vas JOIN vas.veteranAssessment va JOIN vas.survey s WHERE va.veteranAssessmentId = :veteranAssessmentId AND s.surveyId = :surveyId";

        TypedQuery<VeteranAssessmentSurvey> query = entityManager.createQuery(sql, VeteranAssessmentSurvey.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);
        query.setParameter("surveyId", surveyId);

        List<VeteranAssessmentSurvey> results = query.getResultList();

        if (results.size() > 0) {
            return results.get(0);
        }
        else {
            return null;
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> calculateProgress(int veteranAssessmentId) {

        return entityManager
                .createNativeQuery(CALCULATE_PROGRESS_MAIN)
                .setParameter("veteranAssessmentId", veteranAssessmentId)
                .getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<Object[]> getMhaTestResponse(int veteranAssessmentId, int surveyId) {

        String sql = "SELECT * FROM veteran_assessment";
        
        return entityManager
                .createNativeQuery(sql)
                .setParameter("veteranAssessmentId", veteranAssessmentId)
                .setParameter("surveyId", surveyId)
                .getResultList();
    }

    @Override
    public List<Survey> findSurveyListByVeteranAssessmentId(int veteranAssessmentId) {
        
        List<VeteranAssessmentSurvey> veteranAssessmentSurveyList = forVeteranAssessmentId( veteranAssessmentId);
        
        List<Survey> surveyList = new ArrayList<Survey>();
        
        for (VeteranAssessmentSurvey veteranAssessmentSurvey : veteranAssessmentSurveyList) {
            surveyList.add(veteranAssessmentSurvey.getSurvey());
        }
        
        return surveyList;
    }
    
    @Override
    public List<VeteranAssessmentSurvey> findSurveyBySurveyAssessment(int veteranAssessmentId, int surveyId) {
    	
        String sql = "SELECT vas FROM VeteranAssessmentSurvey vas JOIN vas.veteranAssessment va JOIN vas.survey s "
        		+ "WHERE va.veteranAssessmentId = :veteranAssessmentId AND s.surveyId = :surveyId";

        TypedQuery<VeteranAssessmentSurvey> query = entityManager.createQuery(sql, VeteranAssessmentSurvey.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);
        query.setParameter("surveyId", surveyId);

        return query.getResultList();
    }
    
    /* query strings */
    /* adapted from the SurveyPageRepositoryImpl.SKIP_QUERY_FORMAT */ 
    private static final String CALCULATE_PROGRESS_MAIN = 
            "SELECT all_measures.survey_id, count(all_measures.measure_id), count(answered_measures.measure_id) " 
            +"FROM( " 
                +"SELECT s.survey_id, spm.measure_id "
                +"FROM measure m " 
                +"LEFT OUTER JOIN veteran_assessment_measure_visibility vamv ON " 
                +"m.measure_id=vamv.measure_id AND vamv.veteran_assessment_id = :veteranAssessmentId "
                +"INNER JOIN survey_page_measure spm ON ifnull(m.parent_measure_id, m.measure_id)=spm.measure_id " 
                +"INNER JOIN survey_page sp ON spm.survey_page_id=sp.survey_page_id "
                +"INNER JOIN survey s ON sp.survey_id=s.survey_id "
                +"INNER JOIN veteran_assessment_survey vas ON sp.survey_id=vas.survey_id " 
                +"WHERE vas.veteran_assessment_id = :veteranAssessmentId "
                +"AND ifnull(vamv.is_visible, 1) "
                +"AND m.measure_type_id IN ( " + Measure.COUNTED_MEASURE_TYPES + " ) " 
                +"GROUP BY spm.survey_page_id, spm.measure_id " 
            +") all_measures "
            +"LEFT OUTER JOIN ( "
              /* Business rules are: if table parent question contains a non-false answer we include it; if an entire row 
               of child questions have at least one true or non-null we return the parent;  if there is any number 
               of completed rows but at least one that is incomplete we don't include the parent */
                +"SELECT DISTINCT spm.measure_id "
                +"FROM survey_measure_response smr " 
                +"INNER JOIN measure m ON smr.measure_id=m.measure_id " 
                +"INNER JOIN survey_page_measure spm on ifnull(m.parent_measure_id, m.measure_id)=spm.measure_id " 
                +"INNER JOIN survey s ON smr.survey_id=s.survey_id "
                +"WHERE smr.veteran_assessment_id = :veteranAssessmentId  "
                +"AND m.measure_type_id IN ( " + Measure.COUNTED_MEASURE_TYPES + " ) " 
                +"AND (  "
                   +"(spm.measure_id=smr.measure_id AND (smr.boolean_value = 1 OR smr.number_value IS NOT NULL OR smr.text_value IS NOT NULL) ) "
                   +" OR ( " /* check to see if this is a parent with answered children */
                     
                          /* used to make sure the second sub-query contains at least one answer 
                            (i.e. when no child answers have been given the second sub-query will be true which we don't want) */
                        +"0 NOT IN  "
                            +"(SELECT COUNT(*) FROM survey_measure_response smr2 "
                              +"INNER JOIN measure m ON smr2.measure_id=m.measure_id "   
                              +"WHERE smr2.veteran_assessment_id = :veteranAssessmentId AND "
                              +"m.parent_measure_id = spm.measure_id "
                            +") "
                        +"AND "
                        
                        +"0 NOT IN ( "
                            /* collect responses grouped by measure and tabular row. Get sum of the number of answers. 
                               If a row has a 0 then that row had not answer for that measure 
                            */
                            +"SELECT ifnull( sum(smr2.boolean_value = 1 OR smr2.number_value IS NOT NULL OR smr2.text_value IS NOT NULL), 0) answer_count "
                            +"FROM survey_measure_response smr2 "
                            +"INNER JOIN survey s ON smr2.survey_id=s.survey_id "           
                            +"INNER JOIN measure m ON smr2.measure_id=m.measure_id "           
                            +"WHERE smr2.veteran_assessment_id = :veteranAssessmentId "
                            +"AND m.parent_measure_id = spm.measure_id "
                            +"AND m.measure_type_id IN ( " + Measure.COUNTED_MEASURE_TYPES + " ) "
                            +"GROUP BY smr2.measure_id, smr2.tabular_row "
                        +") "
                      +") "
                 +") "
            +") answered_measures " 
            +"ON answered_measures.measure_id=all_measures.measure_id " 
            +"group by all_measures.survey_id ";
}
