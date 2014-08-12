package gov.va.escreening.repository;

import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

@Repository
public class SurveyMeasureResponseRepositoryImpl extends AbstractHibernateRepository<SurveyMeasureResponse> implements
        SurveyMeasureResponseRepository {

    private static final Logger logger = LoggerFactory.getLogger(SurveyMeasureResponseRepositoryImpl.class);

    public SurveyMeasureResponseRepositoryImpl() {
        super();

        setClazz(SurveyMeasureResponse.class);
    }
    
    @Override
    public ListMultimap<Integer, SurveyMeasureResponse> getForVeteranAssessmentAndSurvey(int veteranAssessmentId, int surveyId) {

        logger.trace("getSurveyPagesForVeteranAssessmentId");

        String sql = "SELECT smr FROM SurveyMeasureResponse smr WHERE smr.veteranAssessment.veteranAssessmentId = :veteranAssessmentId AND smr.survey.surveyId = :surveyId ORDER BY smr.tabularRow";

        TypedQuery<SurveyMeasureResponse> query = entityManager.createQuery(sql, SurveyMeasureResponse.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);
        query.setParameter("surveyId", surveyId);

        //get query results and convert into a Map
        List<SurveyMeasureResponse> measureResponseList = query.getResultList();
        ListMultimap<Integer, SurveyMeasureResponse> surveyMeasureResponseMap = LinkedListMultimap.create();
        for(SurveyMeasureResponse measureResponse : measureResponseList){
            surveyMeasureResponseMap.put(measureResponse.getMeasureAnswer().getMeasureAnswerId().intValue(), measureResponse);
        }
        
        return surveyMeasureResponseMap;
    }

    @Override
    public List<SurveyMeasureResponse> findForAssessmentIdMeasureRow(int veteranAssessmentId, int measureId, int tabularRow) {
        logger.trace("findForAssessmentIdMeasureRow");    	
    	
        String sql = "SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va JOIN smr.measure m JOIN smr.measureAnswer ma WHERE va.veteranAssessmentId = :veteranAssessmentId AND m.measureId = :measureId AND smr.tabularRow = :tabularRow ORDER BY ma.displayOrder";
        
        TypedQuery<SurveyMeasureResponse> query = entityManager.createQuery(sql, SurveyMeasureResponse.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);
        query.setParameter("measureId", measureId);
        query.setParameter("tabularRow", tabularRow);
        List<SurveyMeasureResponse> surveyMeasureResponseList = query.getResultList();
        
        return surveyMeasureResponseList;
    }
    
    @Override
    public List<SurveyMeasureResponse> getForVeteranAssessmentAndMeasure(int veteranAssessmentId, int measureId) {
        logger.trace("getForVeteranAssessmentAndMeasure");
        
        String sql = "SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va JOIN smr.measure m WHERE va.veteranAssessmentId = :veteranAssessmentId AND m.measureId = :measureId";

        TypedQuery<SurveyMeasureResponse> query = entityManager.createQuery(sql, SurveyMeasureResponse.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);
        query.setParameter("measureId", measureId);
        List<SurveyMeasureResponse> surveyMeasureResponseList = query.getResultList();

        return surveyMeasureResponseList;
    }
    
    @Override
    public SurveyMeasureResponse find(int veteranAssessmentId, int measureAnswerId, 
            @Nullable Integer tabularRow) {

        logger.trace("find");

        String sql = "SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va JOIN smr.measureAnswer ma WHERE va.veteranAssessmentId = :veteranAssessmentId AND ma.measureAnswerId = :measureAnswerId";

        if(tabularRow != null){
            sql += " AND smr.tabularRow = :tabularRow";
        }
        
        TypedQuery<SurveyMeasureResponse> query = entityManager
                .createQuery(sql, SurveyMeasureResponse.class)
                .setParameter("veteranAssessmentId", veteranAssessmentId)
                .setParameter("measureAnswerId", measureAnswerId);

        if(tabularRow != null){
            query.setParameter("tabularRow", tabularRow);
        }
        
        List<SurveyMeasureResponse> surveyMeasureResponseList = query.getResultList();

        if (surveyMeasureResponseList.size() > 0) {
            return surveyMeasureResponseList.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public int deleteResponseForMeasureAnswerId(Integer veteranAssessmentId, Integer surveyId,
            gov.va.escreening.entity.Measure measure, String responsesToLeave) {

        String newResponseIds = Strings.isNullOrEmpty(responsesToLeave) ? "" : " AND smr.surveyMeasureResponseId NOT IN (" + responsesToLeave + ") ";
        String sql = "DELETE FROM SurveyMeasureResponse AS smr "
                + "WHERE smr.veteranAssessment.veteranAssessmentId = :veteranAssessmentId AND "
                + "smr.survey.surveyId = :surveyId AND "
                + "smr.measure.measureId = :measureId "
                + newResponseIds;
        
        return entityManager
                .createQuery(sql)
                .setParameter("veteranAssessmentId", veteranAssessmentId)
                .setParameter("surveyId", surveyId)
                .setParameter("measureId", measure.getMeasureId())
                .executeUpdate();
    }
    
    @Override
    public int deleteResponsesForMeasures(Integer veteranAssessmentId, Collection<Integer> measureIds){
        
        if (measureIds == null || measureIds.isEmpty()) {
            return 0;
        }
        
        String sql = "DELETE FROM SurveyMeasureResponse AS smr "
                + "WHERE smr.veteranAssessment.veteranAssessmentId = :veteranAssessmentId AND "
                + "smr.measure.measureId IN (:measureIds)";
        
        return entityManager
                .createQuery(sql)
                .setParameter("veteranAssessmentId", veteranAssessmentId)
                .setParameter("measureIds", measureIds)
                .executeUpdate();
    }


    @Override
    public List<SurveyMeasureResponse> findForVeteranAssessmentId(int veteranAssessmentId) {

        logger.trace("findForVeteranAssessmentId");

        String sql = "SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va WHERE va.veteranAssessmentId = :veteranAssessmentId";

        TypedQuery<SurveyMeasureResponse> query = entityManager.createQuery(sql, SurveyMeasureResponse.class);
        query.setParameter("veteranAssessmentId", veteranAssessmentId);

        List<SurveyMeasureResponse> surveyMeasureResponseList = query.getResultList();

        return surveyMeasureResponseList;
    }

    @Override
    public Integer getNumRowsForAssessmentIdMeasure(int veteranAssessmentId, Measure parentMeasure) {
    	
        logger.trace("getNumRowsForAssessmentIdMeasure");

        String sql = "SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va JOIN smr.measure m "
        		+ "WHERE va.veteranAssessmentId = :veteranAssessmentId AND "
        		+ "m.parent = :parentMeasure "
        		+ "GROUP BY smr.tabularRow";
        
        TypedQuery<SurveyMeasureResponse> query = entityManager
                .createQuery(sql, SurveyMeasureResponse.class)
                .setParameter("veteranAssessmentId", veteranAssessmentId)
                .setParameter("parentMeasure", parentMeasure);
        
        List<SurveyMeasureResponse> surveyMeasureResponseList = query.getResultList();

        Integer numberOfRows = surveyMeasureResponseList.size();
    	
    	return numberOfRows;
    }
}