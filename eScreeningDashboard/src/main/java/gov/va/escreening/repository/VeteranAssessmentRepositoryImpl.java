package gov.va.escreening.repository;

import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.SortDirection;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.entity.AssessmentStatus;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class VeteranAssessmentRepositoryImpl extends AbstractHibernateRepository<VeteranAssessment> implements VeteranAssessmentRepository {

	public VeteranAssessmentRepositoryImpl() {
		super();

		setClazz(VeteranAssessment.class);
	}

	@Override
	public List<VeteranAssessment> findByVeteranIdAndAssessmentStatusIdList(
			Integer veteranId, List<Integer> assessmentStatusIdList) {

		List<VeteranAssessment> veteranAssessmentList = new ArrayList<VeteranAssessment>();

		String sql = "SELECT va FROM VeteranAssessment va JOIN va.veteran v JOIN va.assessmentStatus ast WHERE v.veteranId = :veteranId AND ast.assessmentStatusId IN (:assessmentStatusIdList) ORDER BY va.veteranAssessmentId";

		if (assessmentStatusIdList != null && assessmentStatusIdList.size() > 0) {
			TypedQuery<VeteranAssessment> query = entityManager.createQuery(sql, VeteranAssessment.class);
			query.setParameter("veteranId", veteranId);
			query.setParameter("assessmentStatusIdList", assessmentStatusIdList);
			veteranAssessmentList = query.getResultList();
		}

		return veteranAssessmentList;
	}

	@Override
	public List<VeteranAssessment> findByProgramIdList(
			List<Integer> programIdList) {

		List<VeteranAssessment> veteranAssessmentList = new ArrayList<VeteranAssessment>();

		String sql = "SELECT DISTINCT va FROM VeteranAssessment va JOIN va.program p WHERE p.programId IN (:programIdList)";

		if (programIdList != null && programIdList.size() > 0) {
			TypedQuery<VeteranAssessment> query = entityManager.createQuery(sql, VeteranAssessment.class);
			query.setParameter("programIdList", programIdList);
			veteranAssessmentList = query.getResultList();
		}

		return veteranAssessmentList;
	}

	@Override
	public List<VeteranAssessment> searchVeteranAssessment(Integer clinicanId,
			Integer createdByUserId, Integer programId,
			Date fromAssessmentDate, Date toAssessmentDate, Integer veteranId,
			List<Integer> programIdList) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VeteranAssessment> criteriaQuery = criteriaBuilder.createQuery(VeteranAssessment.class);

		Root<VeteranAssessment> veteranAssessmentRoot = criteriaQuery.from(VeteranAssessment.class);
		Join<VeteranAssessment, AssessmentStatus> assessmentStatusJoin = veteranAssessmentRoot.join("assessmentStatus");
		Join<VeteranAssessment, Veteran> veteranJoin = veteranAssessmentRoot.join("veteran");
		Join<VeteranAssessment, Program> programJoin = veteranAssessmentRoot.join("program");
		Join<VeteranAssessment, User> clinicianJoin = veteranAssessmentRoot.join("clinician");
		Join<VeteranAssessment, User> createdByUserJoin = veteranAssessmentRoot.join("createdByUser");

		List<Predicate> criteriaList = new ArrayList<Predicate>();

		// only include assessments in the appropriate state
		Expression<Integer> statusExp = assessmentStatusJoin.get("assessmentStatusId");
		Predicate statusPredicate = statusExp.in(getExportAssessmentStates());
		criteriaList.add(statusPredicate);

		if (clinicanId != null) {
			criteriaList.add(criteriaBuilder.equal(clinicianJoin.get("userId"), clinicanId));
		}

		if (createdByUserId != null) {
			criteriaList.add(criteriaBuilder.equal(createdByUserJoin.get("userId"), createdByUserId));
		}

		if (fromAssessmentDate != null) {
			criteriaList.add(criteriaBuilder.greaterThanOrEqualTo(veteranAssessmentRoot.<Date> get("dateCreated"), fromAssessmentDate));
		}

		if (toAssessmentDate != null) {
			criteriaList.add(criteriaBuilder.lessThanOrEqualTo(veteranAssessmentRoot.<Date> get("dateCreated"), toAssessmentDate));
		}

		if (veteranId != null) {
			criteriaList.add(criteriaBuilder.equal(veteranJoin.get("veteranId"), veteranId));
		}

		if (programId != null) {
			// criteriaList.add(criteriaBuilder.equal(programJoin.get("programId"), programId));
			if (programIdList == null) {
				programIdList = new ArrayList<Integer>();
			}

			programIdList.add(programId);
		}

		if (programIdList != null && programIdList.size() > 0) {
			Expression<Integer> exp = programJoin.get("programId");
			Predicate programIdPredicate = exp.in(programIdList);
			criteriaList.add(programIdPredicate);
		}

		criteriaQuery.select(veteranAssessmentRoot);
		criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[0])));

		@SuppressWarnings("rawtypes")
		Expression orderByPath = veteranAssessmentRoot.get("veteranAssessmentId");

		criteriaQuery.orderBy(criteriaBuilder.desc(orderByPath));

		// Generate the query based on the criteria.
		TypedQuery<VeteranAssessment> query = entityManager.createQuery(criteriaQuery);

		List<VeteranAssessment> veteranAssessments = query.getResultList();

		return veteranAssessments;
	}

	@Override
	public SearchResult<VeteranAssessment> searchVeteranAssessment(
			Integer veteranAssessmentId, Integer veteranId, Integer programId,
			Integer clinicanId, Integer createdByUserId,
			Date fromAssessmentDate, Date toAssessmentDate,
			List<Integer> programIdList, SearchAttributes searchAttributes) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VeteranAssessment> criteriaQuery = criteriaBuilder.createQuery(VeteranAssessment.class);

		Root<VeteranAssessment> veteranAssessmentRoot = criteriaQuery.from(VeteranAssessment.class);
		Join<VeteranAssessment, AssessmentStatus> assessmentStatusJoin = veteranAssessmentRoot.join("assessmentStatus");
		Join<VeteranAssessment, Veteran> veteranJoin = veteranAssessmentRoot.join("veteran");
		Join<VeteranAssessment, Program> programJoin = veteranAssessmentRoot.join("program");
		Join<VeteranAssessment, User> clinicianJoin = veteranAssessmentRoot.join("clinician");
		Join<VeteranAssessment, User> createdByUserJoin = veteranAssessmentRoot.join("createdByUser");

		List<Predicate> criteriaList = new ArrayList<Predicate>();

		if (veteranAssessmentId != null) {
			criteriaList.add(criteriaBuilder.equal(veteranAssessmentRoot.get("veteranAssessmentId"), veteranAssessmentId));
		}

		if (veteranId != null) {
			criteriaList.add(criteriaBuilder.equal(veteranJoin.get("veteranId"), veteranId));
		}

		if (programId != null) {
			criteriaList.add(criteriaBuilder.equal(programJoin.get("programId"), programId));
		}

		if (clinicanId != null) {
			criteriaList.add(criteriaBuilder.equal(clinicianJoin.get("userId"), clinicanId));
		}

		if (createdByUserId != null) {
			criteriaList.add(criteriaBuilder.equal(createdByUserJoin.get("userId"), createdByUserId));
		}

		if (fromAssessmentDate != null) {
			criteriaList.add(criteriaBuilder.greaterThanOrEqualTo(veteranAssessmentRoot.<Date> get("dateCreated"), fromAssessmentDate));
		}

		if (toAssessmentDate != null) {
			criteriaList.add(criteriaBuilder.lessThanOrEqualTo(veteranAssessmentRoot.<Date> get("dateCreated"), toAssessmentDate));
		}

		if (programIdList != null && programIdList.size() > 0) {
			Expression<Integer> exp = programJoin.get("programId");
			Predicate programIdPredicate = exp.in(programIdList);
			criteriaList.add(programIdPredicate);
		}

		criteriaQuery.select(veteranAssessmentRoot);
		criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[0])));

		// Set default order by field and then check if one was passed to us.
		@SuppressWarnings("rawtypes")
		Expression orderByPath = veteranAssessmentRoot.get("veteranAssessmentId");

		if (StringUtils.isNotBlank(searchAttributes.getSortColumn())) {
			if (searchAttributes.getSortColumn().equalsIgnoreCase("programName")) {
				orderByPath = programJoin.get("name");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("clinicianName")) {
				orderByPath = clinicianJoin.get("lastName");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("createdBy")) {
				orderByPath = createdByUserJoin.get("lastName");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("assessmentDate")) {
				orderByPath = veteranAssessmentRoot.get("dateCreated");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("veteranId")) {
				orderByPath = veteranJoin.get("veteranId");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("veteranName")) {
				orderByPath = veteranJoin.get("lastName");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("assessmentStatusName")) {
				orderByPath = assessmentStatusJoin.get("name");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("ssnLastFour")) {
				orderByPath = veteranJoin.get("ssnLastFour");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("duration")) {
				orderByPath = veteranAssessmentRoot.get("duration");
			} else if (searchAttributes.getSortColumn().equalsIgnoreCase("percentComplete")) {
				orderByPath = veteranAssessmentRoot.get("percentComplete");
			}
		}

		if (searchAttributes.getSortDirection() == SortDirection.SORT_DESCENDING) {

			criteriaQuery.orderBy(criteriaBuilder.desc(orderByPath));
		} else {
			criteriaQuery.orderBy(criteriaBuilder.asc(orderByPath));
		}

		// Generate the query based on the criteria.
		TypedQuery<VeteranAssessment> query = entityManager.createQuery(criteriaQuery);

		SearchResult<VeteranAssessment> searchResult = new SearchResult<VeteranAssessment>();

		// Get the total count. Not a very efficient way....
		Integer totalCount = query.getResultList().size();
		searchResult.setTotalNumRowsFound(totalCount);

		// Now get only the page.
		query.setFirstResult(searchAttributes.getRowStartIndex());
		query.setMaxResults(searchAttributes.getPageSize());

		List<VeteranAssessment> veteranAssessments = query.getResultList();
		searchResult.setResultList(veteranAssessments);

		return searchResult;
	}

	@Override
	public Date getDateModified(int veteranAssessmentId) {
		String sql = "SELECT UNIX_TIMESTAMP(max(date_created)), UNIX_TIMESTAMP(max(date_modified)) FROM survey_measure_response WHERE veteran_assessment_id = :veteranAssessmentId";

		@SuppressWarnings("unchecked")
		List<Object[]> rows = entityManager.createNativeQuery(sql).setParameter("veteranAssessmentId", veteranAssessmentId).getResultList();

		if (rows.isEmpty()) {
			// no results saved for this assessment
			return new Date(0);
		}

		Object[] row = rows.get(0);

		Long created = null;
		try {
			created = Long.valueOf(row[0].toString());
		} catch (Exception e) {
		}

		Long modified = null;
		try {
			modified = Long.valueOf(row[1].toString());
		} catch (Exception e) {
		}

		if (created == null) {
			if (modified == null) {
				// no results saved for this assessment
				return new Date(0);
			}
			// this should never happen but we know what happens when we say that :)
			return new Date(modified);
		} else if (modified == null) {
			return new Date(created);
		}

		// found both
		return new Date(Math.max(created, modified));
	}

	@Override
	public List<VeteranAssessment> findByVeteranId(int veteranId) {

		List<VeteranAssessment> veteranAssessmentList = new ArrayList<VeteranAssessment>();

		String sql = "SELECT va FROM VeteranAssessment va JOIN va.veteran v WHERE v.veteranId = :veteranId ORDER BY va.dateCreated DESC";

		TypedQuery<VeteranAssessment> query = entityManager.createQuery(sql, VeteranAssessment.class);
		query.setParameter("veteranId", veteranId);
		veteranAssessmentList = query.getResultList();

		return veteranAssessmentList;
	}

	private List<Integer> getExportAssessmentStates() {
		List<Integer> exportStates = new ArrayList<Integer>();
		exportStates.add(AssessmentStatusEnum.COMPLETE.getAssessmentStatusId());
		exportStates.add(AssessmentStatusEnum.INCOMPLETE.getAssessmentStatusId());
		exportStates.add(AssessmentStatusEnum.FINALIZED.getAssessmentStatusId());
		exportStates.add(AssessmentStatusEnum.REVIEWED.getAssessmentStatusId());
		return exportStates;
	}

	@Override
	public List<Measure> getMeasuresFor(Integer veteranAssessmentId) {
		String sql = "SELECT m FROM VeteranAssessmentSurvey vas " + "JOIN vas.survey s " + "JOIN s.surveyPageList sp " + "JOIN sp.measures m " + "WHERE vas.veteranAssessment.veteranAssessmentId = :veteranAssessmentId";

		return entityManager.createQuery(sql, Measure.class).setParameter("veteranAssessmentId", veteranAssessmentId).getResultList();
	}

	@Override
	public List<VeteranAssessment> findByVeteranIdAndProgramIdList(
			int veteranId, List<Integer> programIdList) {

		List<VeteranAssessment> veteranAssessmentList = new ArrayList<VeteranAssessment>();

		String sql = "SELECT va FROM VeteranAssessment va JOIN va.veteran v JOIN va.program p WHERE v.veteranId = :veteranId AND p.programId IN (:programIdList) ORDER BY va.veteranAssessmentId";

		if (programIdList != null && programIdList.size() > 0) {
			TypedQuery<VeteranAssessment> query = entityManager.createQuery(sql, VeteranAssessment.class);
			query.setParameter("veteranId", veteranId);
			query.setParameter("programIdList", programIdList);
			veteranAssessmentList = query.getResultList();
		}

		return veteranAssessmentList;
	}

}
