package gov.va.escreening.repository;

import gov.va.escreening.domain.AssessmentExpirationDaysEnum;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.SortDirection;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.entity.VeteranAssessmentDashboardAlert;
import gov.va.escreening.validation.DateValidationHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@Repository("esVeteranAssessmentDashboardAlertRepository")
public class VeteranAssessmentDashboardAlertRepositoryImpl extends AbstractHibernateRepository<VeteranAssessmentDashboardAlert> implements VeteranAssessmentDashboardAlertRepository {
	@Resource(name = "dateValidationHelper")
	DateValidationHelper dvh;

	public VeteranAssessmentDashboardAlertRepositoryImpl() {
		setClazz(VeteranAssessmentDashboardAlert.class);
	}

	@Override
	public List<VeteranAssessmentDashboardAlert> findByVeteranAssessmentId(
			int veteranAssessmentId) {

		String sql = "SELECT vada FROM VeteranAssessmentDashboardAlert vada " + "JOIN vada.veteranAssessment va " + "WHERE va.veteranAssessmentId = :veteranAssessmentId " + "ORDER BY vada.dateCreated";

		TypedQuery<VeteranAssessmentDashboardAlert> query = entityManager.createQuery(sql, VeteranAssessmentDashboardAlert.class);
		query.setParameter("veteranAssessmentId", veteranAssessmentId);

		List<VeteranAssessmentDashboardAlert> resultList = query.getResultList();

		return resultList;
	}

	/**
	 * fetches data from multiple columns and returns data as a List of Array. Array index 0 represents the Dash-board
	 * Alert Name, index 1 represents the count of the alert
	 */
	@Override
	public List findAlertsByProgram(int programId) {

		archiveStaleAssessments();

		StringBuilder sqlBldr = new StringBuilder();
		sqlBldr.append("SELECT da.name, count(vada), va.veteranAssessmentId ");
		sqlBldr.append("FROM VeteranAssessmentDashboardAlert as vada ");
		sqlBldr.append("JOIN vada.veteranAssessment as va ");

		// if user wants data for only certain program Id then use programId in
		// the sql else ignore it
		if (programId != 99) {
			sqlBldr.append("inner JOIN va.program as program ");
		}
		sqlBldr.append("inner JOIN vada.dashboardAlert as da ");
		sqlBldr.append("inner JOIN va.assessmentStatus as vas ");

		// except Created=1 and Finalized=5
		sqlBldr.append("WHERE va.dateArchived is null ");
		sqlBldr.append("AND vas.assessmentStatusId not in :assessmentStatusToFilter ");

		if (programId != 99) {
			sqlBldr.append("AND program.programId = :programId ");
		}

		sqlBldr.append("GROUP BY da.dashboardAlertId");

		Query query = entityManager.createQuery(sqlBldr.toString());

		if (programId != 99) {
			query.setParameter("programId", programId);
		}
		query.setParameter("assessmentStatusToFilter", Lists.newArrayList(AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentStatusEnum.FINALIZED.getAssessmentStatusId()));
		List resultList = query.getResultList();

		return resultList;
	}

	@Override
	public List<Map<String, Object>> findNearingCompletionAssessments(
			int programId, int totalRequestedRecords) {

		archiveStaleAssessments();

		StringBuilder sqlBldr = new StringBuilder();
		sqlBldr.append("SELECT vet.lastName, vet.ssnLastFour, va ");
		sqlBldr.append("FROM VeteranAssessment as va ");
		sqlBldr.append("inner JOIN va.veteran as vet ");

		// if user wants data for only certain program Id then use programId in
		// the sql else ignore it
		if (programId != 99) {
			sqlBldr.append("inner JOIN va.program as program ");
		}
		sqlBldr.append("inner JOIN va.assessmentStatus as vas ");

		sqlBldr.append("WHERE va.percentComplete > 0 ");
		sqlBldr.append("AND va.dateArchived is null ");
		sqlBldr.append("AND vas.assessmentStatusId not in :assessmentStatusToFilter ");

		if (programId != 99) {
			sqlBldr.append("AND program.programId = :programId ");
		}

		sqlBldr.append("ORDER BY va.percentComplete DESC");

		Query query = entityManager.createQuery(sqlBldr.toString()).setMaxResults(totalRequestedRecords);

		if (programId != 99) {
			query.setParameter("programId", programId);
		}
		query.setParameter("assessmentStatusToFilter", Lists.newArrayList(AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentStatusEnum.FINALIZED.getAssessmentStatusId()));

		List resultList = query.getResultList();

		return createResponseItems(resultList);
	}

	@Override
	public List<Map<String, Object>> findSlowMovingAssessments(int programId,
			int totalRequestedRecords) {

		archiveStaleAssessments();

		StringBuilder sqlBldr = new StringBuilder();
		sqlBldr.append("SELECT vet.lastName, vet.ssnLastFour, va ");
		sqlBldr.append("FROM VeteranAssessment as va ");
		sqlBldr.append("inner JOIN va.veteran as vet ");

		// if user wants data for only certain program Id then use programId in
		// the sql else ignore it
		if (programId != 99) {
			sqlBldr.append("inner JOIN va.program as program ");
		}
		sqlBldr.append("inner JOIN va.assessmentStatus as vas ");

		sqlBldr.append("WHERE va.duration > 0 ");
		sqlBldr.append("AND va.dateArchived is null ");
		sqlBldr.append("AND vas.assessmentStatusId not in :assessmentStatusToFilter ");

		if (programId != 99) {
			sqlBldr.append("AND program.programId = :programId ");
		}

		sqlBldr.append("ORDER BY va.duration DESC");

		Query query = entityManager.createQuery(sqlBldr.toString()).setMaxResults(totalRequestedRecords);

		if (programId != 99) {
			query.setParameter("programId", programId);
		}
		query.setParameter("assessmentStatusToFilter", Lists.newArrayList(AssessmentStatusEnum.CLEAN.getAssessmentStatusId(), AssessmentStatusEnum.FINALIZED.getAssessmentStatusId()));

		List resultList = query.getResultList();

		return createResponseItems(resultList);
	}

	private List<Map<String, Object>> createResponseItems(List resultList) {
		List<Map<String, Object>> listOfResponses = new ArrayList<Map<String, Object>>();

		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			Object[] object = (Object[]) iterator.next();

			Map<String, Object> simpleResultMap = new HashMap<String, Object>();
			simpleResultMap.put("lastName", object[0]);
			simpleResultMap.put("lastFourSsn", object[1]);

			VeteranAssessment va = (VeteranAssessment) object[2];
			simpleResultMap.put("duration", va.getDuration());
			simpleResultMap.put("percentComplete", va.getPercentComplete());
			simpleResultMap.put("noOfAlerts", va.getDashboardAlerts().size());
			simpleResultMap.put("vaid", va.getVeteranAssessmentId());

			listOfResponses.add(simpleResultMap);
		}

		return listOfResponses;
	}

	private SearchResult<VeteranAssessment> searchFilteredVeteranAssessment(
			Integer programId, SearchAttributes searchAttributes) {
		StringBuilder sqlBldr = new StringBuilder();
		sqlBldr.append("SELECT va ");
		sqlBldr.append("FROM VeteranAssessment as va ");
		sqlBldr.append("inner JOIN va.veteran as vet ");

		sqlBldr.append("inner JOIN va.program as program ");
		sqlBldr.append("inner JOIN va.assessmentStatus as vas ");
		sqlBldr.append("inner JOIN va.clinician as clinician ");
		sqlBldr.append("inner JOIN va.createdByUser as user ");

		sqlBldr.append("WHERE va.dateArchived is null ");
		// if user wants data for only certain program Id then use programId in
		// the sql else ignore it
		if (programId != null) {
			sqlBldr.append("AND program.programId = :programId ");
		}

		String orderByColumn = getOrderByColumn(searchAttributes);
		String orderByDirection = getOrderByDirection(searchAttributes);

		sqlBldr.append(String.format("ORDER BY %s %s", orderByColumn, orderByDirection));

		TypedQuery<VeteranAssessment> query = entityManager.createQuery(sqlBldr.toString(), VeteranAssessment.class);

		if (programId != null) {
			query.setParameter("programId", programId);
		}

		query.setFirstResult(searchAttributes.getRowStartIndex()).setMaxResults(searchAttributes.getPageSize());
		List<VeteranAssessment> resultList = query.getResultList();

		int totalRecsFound = getTotalRecords(programId);

		SearchResult<VeteranAssessment> searchResult = new SearchResult<VeteranAssessment>();
		searchResult.setTotalNumRowsFound(totalRecsFound);
		searchResult.setResultList(resultList);

		return searchResult;
	}

	private int getTotalRecords(Integer programId) {
		StringBuilder sqlBldr = new StringBuilder();
		sqlBldr.append("SELECT count(va) ");
		sqlBldr.append("FROM VeteranAssessment as va ");
		sqlBldr.append("WHERE va.dateArchived is null ");
		if (programId != null) {
			sqlBldr.append("AND program.programId = :programId ");
		}

		Query query = entityManager.createQuery(sqlBldr.toString());

		if (programId != null) {
			query.setParameter("programId", programId);
		}
		Long result = (Long) query.getSingleResult();
		return result.intValue();
	}

	private String getOrderByDirection(SearchAttributes searchAttributes) {
		if (searchAttributes.getSortDirection() == SortDirection.SORT_DESCENDING) {
			return "DESC";
		} else {
			return "ASC";
		}
	}

	private String getOrderByColumn(SearchAttributes searchAttributes) {
		// Set default order by field and then check if one was passed to us.
		String orderByColumn = "va.dateUpdated";

		if (StringUtils.isNotBlank(searchAttributes.getSortColumn())) {
			if (searchAttributes.getSortColumn().equalsIgnoreCase("programName")) {
				orderByColumn = "program.name";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("clinicianName")) {
				orderByColumn = "clinician.lastName";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("createdBy")) {
				orderByColumn = "user.lastName";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("assessmentDate")) {
				orderByColumn = "va.dateUpdated";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("veteranId")) {
				orderByColumn = "vet.veteranId";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("veteranName")) {
				orderByColumn = "vet.lastName";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("assessmentStatusName")) {
				orderByColumn = "vas.name";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("ssnLastFour")) {
				orderByColumn = "vet.ssnLastFour";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("duration")) {
				orderByColumn = "va.duration";
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("percentComplete")) {
				orderByColumn = "va.percentComplete";
			}
		}

		return orderByColumn;
	}

	@Override
	public SearchResult<VeteranAssessment> searchVeteranAssessment(
			Integer programId, SearchAttributes searchAttributes) {

		archiveStaleAssessments();

		return searchFilteredVeteranAssessment(programId, searchAttributes);
	}

	/**
	 * method to
	 * <ol>
	 * <li>archive records which are in Clean state and have not been attended for the last
	 * {@link AssessmentExpirationDaysEnum#CLEAN} days</li>
	 * <li>archive records which are in FINALIZED state and have not been attended for the last
	 * {@link AssessmentExpirationDaysEnum#FINALIZED} days</li>
	 * </ol>
	 * 
	 * PS: THis method has to be called as part os an existing Transaction
	 */
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	private final void archiveStaleAssessments() {
		{
			// take the smallest possible date allowed for clean assessments to be available
			String smallestDate = getSmallestPossibleAllowedDateAsStr(dvh.validWorkingDates(AssessmentExpirationDaysEnum.CLEAN));
			String updateSql = String.format("update veteran_assessment set date_archived = now() where date_archived is null and assessment_status_id = 1 and date_created < %s", smallestDate);
			Query archiveUnAttendedCleanSql = entityManager.createNativeQuery(updateSql);
			int recsUpdated1 = archiveUnAttendedCleanSql.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("%s records were archived as their assessment status is CLEAN and they have not been attended for more than %s days", recsUpdated1, AssessmentExpirationDaysEnum.CLEAN.getExpirationDays()));
			}
		}

		{
			// take all valid dates allowed for finalized assessments to be available
			String smallestDate = getSmallestPossibleAllowedDateAsStr(dvh.validWorkingDates(AssessmentExpirationDaysEnum.FINALIZED));
			String updateSql = String.format("update veteran_assessment set date_archived = now() where date_archived is null and assessment_status_id = 5 and date_updated < %s", smallestDate);
			Query archiveUnAttendedFinalizedSql = entityManager.createNativeQuery(updateSql);
			int recsUpdated2 = archiveUnAttendedFinalizedSql.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("%s records were archived as their assessment status is FINALIZED and they have not been attended for more than %s days", recsUpdated2, AssessmentExpirationDaysEnum.FINALIZED.getExpirationDays()));
			}
		}
	}

	private String getSmallestPossibleAllowedDateAsStr(
			Collection<LocalDate> validWorkingDates) {
		LocalDate smallestDate = null;
		for (Iterator<LocalDate> iter = validWorkingDates.iterator(); iter.hasNext();) {
			LocalDate d = iter.next();
			if (!iter.hasNext()) {
				smallestDate = d;
			}
		}
		Preconditions.checkNotNull(smallestDate);
		return String.format("str_to_date('%s', '%s')", smallestDate.toString("yyyy-MM-dd"), "%Y-%m-%d");
	}

}
