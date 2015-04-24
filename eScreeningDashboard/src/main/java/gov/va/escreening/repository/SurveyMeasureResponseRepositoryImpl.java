package gov.va.escreening.repository;

import gov.va.escreening.context.VeteranAssessmentSmrList;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.SurveyMeasureResponse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.base.Strings;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

@Repository
public class SurveyMeasureResponseRepositoryImpl extends AbstractHibernateRepository<SurveyMeasureResponse> implements SurveyMeasureResponseRepository {

	@Resource(name = "veteranAssessmentSmrList")
	VeteranAssessmentSmrList smrLister;

	private static final Logger logger = LoggerFactory.getLogger(SurveyMeasureResponseRepositoryImpl.class);

	public SurveyMeasureResponseRepositoryImpl() {
		super();

		setClazz(SurveyMeasureResponse.class);
	}

	@Override
	public ListMultimap<Integer, SurveyMeasureResponse> getForVeteranAssessmentAndSurvey(
			int veteranAssessmentId, int surveyId) {

		logger.trace("getSurveyPagesForVeteranAssessmentId");

		ListMultimap<Integer, SurveyMeasureResponse> responseMap = smrLister.fetchCachedSmrBySurvey(veteranAssessmentId, surveyId);
		
		if(responseMap == null){
		    TypedQuery<SurveyMeasureResponse> query = entityManager
		        .createQuery(
		            "SELECT smr FROM SurveyMeasureResponse smr WHERE smr.veteranAssessment.veteranAssessmentId = :veteranAssessmentId AND smr.survey.surveyId = :surveyId ORDER BY smr.tabularRow",
		            SurveyMeasureResponse.class)
	            .setParameter("veteranAssessmentId", veteranAssessmentId)
	            .setParameter("surveyId", surveyId);
	         
	        // get query results and convert into a Map
	        responseMap = toMultiMap(query.getResultList());
	        
	        smrLister.setCachedSmrBySurvey(veteranAssessmentId, surveyId, responseMap);
		}
		
		return responseMap;
	}

	private ListMultimap<Integer, SurveyMeasureResponse> toMultiMap(List<SurveyMeasureResponse> measureResponseList){
	    ListMultimap<Integer, SurveyMeasureResponse> surveyMeasureResponseMap = LinkedListMultimap.create();
        for (SurveyMeasureResponse measureResponse : measureResponseList) {
            surveyMeasureResponseMap.put(measureResponse.getMeasureAnswer().getMeasureAnswerId().intValue(), measureResponse);
        }

        return surveyMeasureResponseMap;
	}
	
	private List<SurveyMeasureResponse> fetchSmrList(int veteranAssessmentId) {
		List<SurveyMeasureResponse> smrList = smrLister.fetchCachedSmr(veteranAssessmentId);
		return smrList;
	}

	@Override
	public List<SurveyMeasureResponse> getForVeteranAssessmentAndMeasure(
			int veteranAssessmentId, int measureId) {
		logger.trace("getForVeteranAssessmentAndMeasure");

		/**
		 * <pre>
		 * String sql = &quot;SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va JOIN smr.measure m WHERE va.veteranAssessmentId = :veteranAssessmentId AND m.measureId = :measureId&quot;;
		 * 
		 * TypedQuery&lt;SurveyMeasureResponse&gt; query = entityManager.createQuery(sql, SurveyMeasureResponse.class);
		 * query.setParameter(&quot;veteranAssessmentId&quot;, veteranAssessmentId);
		 * query.setParameter(&quot;measureId&quot;, measureId);
		 * List&lt;SurveyMeasureResponse&gt; surveyMeasureResponseList = query.getResultList();
		 * </pre>
		 */
		List<SurveyMeasureResponse> surveyMeasureResponseList = new ArrayList<SurveyMeasureResponse>();
		for (SurveyMeasureResponse smr : fetchSmrList(veteranAssessmentId)) {
			if (smr.getMeasure().getMeasureId().equals(measureId)) {
				surveyMeasureResponseList.add(smr);
			}
		}
		return surveyMeasureResponseList;
	}

	@Override
	public SurveyMeasureResponse findSmrUsingPreFetch(int veteranAssessmentId,
			int measureAnswerId, @Nullable Integer tabularRow) {
		/**
		 * <pre>
		 * String sql = &quot;SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va JOIN smr.measureAnswer ma WHERE va.veteranAssessmentId = :veteranAssessmentId AND ma.measureAnswerId = :measureAnswerId&quot;;
		 * 
		 * if (tabularRow != null) {
		 * 	sql += &quot; AND smr.tabularRow = :tabularRow&quot;;
		 * }
		 * 
		 * TypedQuery&lt;SurveyMeasureResponse&gt; query = entityManager.createQuery(sql, SurveyMeasureResponse.class).setParameter(&quot;veteranAssessmentId&quot;, veteranAssessmentId).setParameter(&quot;measureAnswerId&quot;, measureAnswerId);
		 * 
		 * if (tabularRow != null) {
		 * 	query.setParameter(&quot;tabularRow&quot;, tabularRow);
		 * }
		 * 
		 * List&lt;SurveyMeasureResponse&gt; surveyMeasureResponseList = query.getResultList();
		 * 
		 * if (surveyMeasureResponseList.size() &gt; 0) {
		 * 	return surveyMeasureResponseList.get(0);
		 * } else {
		 * 	return null;
		 * }
		 * </pre>
		 */
		for (SurveyMeasureResponse smr : fetchSmrList(veteranAssessmentId)) {
			if (smr.getMeasureAnswer().getMeasureAnswerId().equals(measureAnswerId)) {
				if (tabularRow == null || tabularRow.equals(smr.getTabularRow())) {
					return smr;
				}
			}
		}
		return null;

	}

	@Override
	public int deleteResponseForMeasureAnswerId(Integer veteranAssessmentId,
			Integer surveyId, gov.va.escreening.entity.Measure measure,
			String responsesToLeave) {

		String newResponseIds = Strings.isNullOrEmpty(responsesToLeave) ? "" : " AND smr.surveyMeasureResponseId NOT IN (" + responsesToLeave + ") ";
		String sql = "DELETE FROM SurveyMeasureResponse AS smr " + "WHERE smr.veteranAssessment.veteranAssessmentId = :veteranAssessmentId AND " + "smr.survey.surveyId = :surveyId AND " + "smr.measure.measureId = :measureId " + newResponseIds;

		return entityManager.createQuery(sql).setParameter("veteranAssessmentId", veteranAssessmentId).setParameter("surveyId", surveyId).setParameter("measureId", measure.getMeasureId()).executeUpdate();
	}

	@Override
	public int deleteResponsesForMeasures(Integer veteranAssessmentId,
			Collection<Integer> measureIds) {

		if (measureIds == null || measureIds.isEmpty()) {
			return 0;
		}

		String sql = "DELETE FROM SurveyMeasureResponse AS smr " + "WHERE smr.veteranAssessment.veteranAssessmentId = :veteranAssessmentId AND " + "smr.measure.measureId IN (:measureIds)";

		return entityManager.createQuery(sql).setParameter("veteranAssessmentId", veteranAssessmentId).setParameter("measureIds", measureIds).executeUpdate();
	}

	@Override
	public List<SurveyMeasureResponse> findForVeteranAssessmentId(
			int veteranAssessmentId) {

		logger.trace("findForVeteranAssessmentId");

		String sql = "SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va WHERE va.veteranAssessmentId = :veteranAssessmentId";

		TypedQuery<SurveyMeasureResponse> query = entityManager.createQuery(sql, SurveyMeasureResponse.class);
		query.setParameter("veteranAssessmentId", veteranAssessmentId);

		List<SurveyMeasureResponse> surveyMeasureResponseList = query.getResultList();

		return surveyMeasureResponseList;
	}

	@Override
	public Integer getNumRowsForAssessmentIdMeasure(int veteranAssessmentId,
			Measure parentMeasure) {

		logger.trace("getNumRowsForAssessmentIdMeasure");

		String sql = "SELECT smr FROM SurveyMeasureResponse smr JOIN smr.veteranAssessment va JOIN smr.measure m " + "WHERE va.veteranAssessmentId = :veteranAssessmentId AND " + "m.parent = :parentMeasure " + "GROUP BY smr.tabularRow";

		TypedQuery<SurveyMeasureResponse> query = entityManager.createQuery(sql, SurveyMeasureResponse.class).setParameter("veteranAssessmentId", veteranAssessmentId).setParameter("parentMeasure", parentMeasure);

		List<SurveyMeasureResponse> surveyMeasureResponseList = query.getResultList();

		Integer numberOfRows = surveyMeasureResponseList.size();

		return numberOfRows;
	}
}