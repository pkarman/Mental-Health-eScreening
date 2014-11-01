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

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

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
		sqlBldr.append("WHERE vas.assessmentStatusId not in (1,5)  ");
		sqlBldr.append("AND date(va.dateUpdated) in (:workingDates) ");

		if (programId != 99) {
			sqlBldr.append("AND program.programId = :programId ");
		}

		sqlBldr.append("GROUP BY da.dashboardAlertId");

		Query query = entityManager.createQuery(sqlBldr.toString());

		if (programId != 99) {
			query.setParameter("programId", programId);
		}

		query.setParameter("workingDates", dvh.validWorkingDates(AssessmentExpirationDaysEnum.FINALIZED));

		List resultList = query.getResultList();

		return resultList;
	}

	@Override
	public List<Map<String, Object>> findNearingCompletionAssessments(
			int programId, int totalRequestedRecords) {

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
		sqlBldr.append("AND date(va.dateUpdated) in (:workingDates) ");
		sqlBldr.append("AND vas.assessmentStatusId not in (1,5)  ");

		if (programId != 99) {
			sqlBldr.append("AND program.programId = :programId ");
		}

		sqlBldr.append("ORDER BY va.percentComplete DESC");

		Query query = entityManager.createQuery(sqlBldr.toString()).setMaxResults(totalRequestedRecords);

		if (programId != 99) {
			query.setParameter("programId", programId);
		}
		query.setParameter("workingDates", dvh.validWorkingDates(AssessmentExpirationDaysEnum.FINALIZED));

		List resultList = query.getResultList();

		return createResponseItems(resultList);
	}

	@Override
	public List<Map<String, Object>> findSlowMovingAssessments(int programId,
			int totalRequestedRecords) {
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
		sqlBldr.append("AND date(va.dateUpdated) in (:workingDates) ");
		sqlBldr.append("AND vas.assessmentStatusId not in (1,5) ");

		if (programId != 99) {
			sqlBldr.append("AND program.programId = :programId ");
		}

		sqlBldr.append("ORDER BY va.duration DESC");

		Query query = entityManager.createQuery(sqlBldr.toString()).setMaxResults(totalRequestedRecords);

		if (programId != 99) {
			query.setParameter("programId", programId);
		}
		query.setParameter("workingDates", dvh.validWorkingDates(AssessmentExpirationDaysEnum.FINALIZED));
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

	@Override
	public SearchResult<VeteranAssessment> searchVeteranAssessment(
			Integer programId, SearchAttributes searchAttributes) {
		StringBuilder sqlBldr = new StringBuilder();
		sqlBldr.append("SELECT va ");
		sqlBldr.append("FROM VeteranAssessment as va ");
		sqlBldr.append("inner JOIN va.veteran as vet ");

		sqlBldr.append("inner JOIN va.program as program ");
		sqlBldr.append("inner JOIN va.assessmentStatus as vas ");
		sqlBldr.append("inner JOIN va.clinician as clinician ");
		sqlBldr.append("inner JOIN va.createdByUser as user ");

		sqlBldr.append("WHERE vas.assessmentStatusId not in (:deletedState) ");
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
		query.setParameter("deletedState", AssessmentStatusEnum.DELETED.getAssessmentStatusId());

		query.setFirstResult(searchAttributes.getRowStartIndex()).setMaxResults(searchAttributes.getPageSize());
		List<VeteranAssessment> resultList = query.getResultList();

		int filterCnt = filterByAssessmentStatusAndWorkingDays(resultList);

		int totalRecsFound = getCountOfThisAboveQuery(programId) - filterCnt;

		SearchResult<VeteranAssessment> searchResult = new SearchResult<VeteranAssessment>();
		searchResult.setTotalNumRowsFound(totalRecsFound);
		searchResult.setResultList(resultList);

		return searchResult;
	}

	private int getCountOfThisAboveQuery(Integer programId) {
		StringBuilder sqlBldr = new StringBuilder();
		sqlBldr.append("SELECT count(va) ");
		sqlBldr.append("FROM VeteranAssessment as va ");
		if (programId != null) {
			sqlBldr.append("WHERE program.programId = :programId ");
		}

		Query query = entityManager.createQuery(sqlBldr.toString());

		if (programId != null) {
			query.setParameter("programId", programId);
		}
		Long result = (Long) query.getSingleResult();
		return result.intValue();
	}

	private int filterByAssessmentStatusAndWorkingDays(
			List<VeteranAssessment> resultList) {

		final List<LocalDate> xpireDaysFinalizedState = dvh.validWorkingDates(AssessmentExpirationDaysEnum.FINALIZED);
		final List<LocalDate> xpireDaysCleanState = dvh.validWorkingDates(AssessmentExpirationDaysEnum.CLEAN);

		// get a list of those assessment which have been in Finalized state from more than 2 days
		Predicate<VeteranAssessment> statEqFinalized_AliveMoreThan2Days_Predicate = new Predicate<VeteranAssessment>() {
			@Override
			public boolean apply(VeteranAssessment input) {
				return input.getAssessmentStatus().getAssessmentStatusId() == AssessmentStatusEnum.FINALIZED.getAssessmentStatusId() && !xpireDaysFinalizedState.contains(LocalDate.fromDateFields(input.getDateUpdated()));
			}
		};
		Collection<VeteranAssessment> finalizedWithMoreThan2DaysList = Collections2.filter(resultList, statEqFinalized_AliveMoreThan2Days_Predicate);
		// take the size and save in a local variable as removing data from resultList will remove from
		// finalizedWithMoreThan2DaysList too as Collections2.filter returns a reference
		int finalizedWithMoreThan2DaysListCnt = finalizedWithMoreThan2DaysList.size();

		// get a list of those assessment which have been in Clean state from more than 5 days
		Predicate<VeteranAssessment> statEqClean_AliveMoreThan5Days_Predicate = new Predicate<VeteranAssessment>() {
			@Override
			public boolean apply(VeteranAssessment input) {
				return input.getAssessmentStatus().getAssessmentStatusId() == AssessmentStatusEnum.CLEAN.getAssessmentStatusId() && !xpireDaysCleanState.contains(LocalDate.fromDateFields(input.getDateCreated()));
			}
		};
		Collection<VeteranAssessment> cleanWithMoreThan5DaysList = Collections2.filter(resultList, statEqClean_AliveMoreThan5Days_Predicate);
		int cleanWithMoreThan5DaysListCnt = cleanWithMoreThan5DaysList.size();

		// now we have these two lists --- we will go and remove these records from the list
		resultList.removeAll(finalizedWithMoreThan2DaysList);
		resultList.removeAll(cleanWithMoreThan5DaysList);

		return (cleanWithMoreThan5DaysListCnt + finalizedWithMoreThan2DaysListCnt);
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
}
