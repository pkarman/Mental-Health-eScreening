package gov.va.escreening.repository;

import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.SortDirection;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.dto.report.Report593ByDayDTO;
import gov.va.escreening.dto.report.Report593ByTimeDTO;
import gov.va.escreening.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static gov.va.escreening.util.ReportRepositoryUtil.getDateFromString;

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

		String sql = "SELECT va FROM VeteranAssessment va JOIN va.veteran v JOIN va.assessmentStatus ast WHERE va.dateArchived is null AND v.veteranId = :veteranId AND ast.assessmentStatusId IN (:assessmentStatusIdList) ORDER BY va.veteranAssessmentId";

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

		String sql = "SELECT DISTINCT va FROM VeteranAssessment va JOIN va.program p WHERE va.dateArchived is null AND p.programId IN (:programIdList)";

		if (programIdList != null && programIdList.size() > 0) {
			TypedQuery<VeteranAssessment> query = entityManager.createQuery(sql, VeteranAssessment.class);
			query.setParameter("programIdList", programIdList);
			veteranAssessmentList = query.getResultList();
		}

		return veteranAssessmentList;
	}

	@Override
	public List<VeteranAssessment> searchVeteranAssessmentForExport(Integer clinicanId,
			Integer createdByUserId, Integer programId,
			Date fromAssessmentDate, Date toAssessmentDate, Integer veteranId,
			List<Integer> programIdList) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<VeteranAssessment> criteriaQuery = criteriaBuilder.createQuery(VeteranAssessment.class);

		Root<VeteranAssessment> veteranAssessmentRoot = criteriaQuery.from(VeteranAssessment.class);
		Join<VeteranAssessment, AssessmentStatus> assessmentStatusJoin = veteranAssessmentRoot.join("assessmentStatus");

		List<Predicate> criteriaList = new ArrayList<Predicate>();

		// only include assessments in the appropriate state
		Expression<Integer> statusExp = assessmentStatusJoin.get("assessmentStatusId");
		Predicate statusPredicate = statusExp.in(getExportAssessmentStates());
		criteriaList.add(statusPredicate);

		if (clinicanId != null) {
			Join<VeteranAssessment, User> clinicianJoin = veteranAssessmentRoot.join("clinician");
			criteriaList.add(criteriaBuilder.equal(clinicianJoin.get("userId"), clinicanId));
		}

		if (createdByUserId != null) {
			Join<VeteranAssessment, User> createdByUserJoin = veteranAssessmentRoot.join("createdByUser");
			criteriaList.add(criteriaBuilder.equal(createdByUserJoin.get("userId"), createdByUserId));
		}

		if (fromAssessmentDate != null) {
			criteriaList.add(criteriaBuilder.greaterThan(veteranAssessmentRoot.<Date> get("dateCreated"), fromAssessmentDate));
		}

		if (toAssessmentDate != null) {
			criteriaList.add(criteriaBuilder.lessThanOrEqualTo(veteranAssessmentRoot.<Date> get("dateCreated"), toAssessmentDate));
		}

		if (veteranId != null) {
			Join<VeteranAssessment, Veteran> veteranJoin = veteranAssessmentRoot.join("veteran");
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
			Join<VeteranAssessment, Program> programJoin = veteranAssessmentRoot.join("program");
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

		String sql = "SELECT va FROM VeteranAssessment va JOIN va.veteran v WHERE va.dateArchived is null AND v.veteranId = :veteranId ORDER BY va.dateCreated DESC";

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

		String sql = "SELECT va FROM VeteranAssessment va JOIN va.veteran v JOIN va.program p WHERE va.dateArchived is null AND v.veteranId = :veteranId AND p.programId IN (:programIdList) ORDER BY va.veteranAssessmentId";

		if (programIdList != null && programIdList.size() > 0) {
			TypedQuery<VeteranAssessment> query = entityManager.createQuery(sql, VeteranAssessment.class);
			query.setParameter("veteranId", veteranId);
			query.setParameter("programIdList", programIdList);
			veteranAssessmentList = query.getResultList();
		}

		return veteranAssessmentList;
	}

    @Override
    public Integer getVeteranCountFor593(String fromDate, String toDate, List<Integer> clinicIds) {

        Query q = entityManager.createNativeQuery("select count(distinct veteran_id) from veteran_assessment " +
                " where date_completed >= :fromDate and date_completed <= :toDate " +
                "and clinic_id in (:clinicIds) and date_completed is not null ");


        setParametersFor593(q, fromDate, toDate, clinicIds);
        Object r = q.getSingleResult();

        if (r!=null){
            return ((Number)r).intValue();
        }

        return null;
    }

    @Override
    public Integer getBatteryCountFor593(String fromDate, String toDate, List<Integer> clinicIds) {

        Query q = entityManager.createNativeQuery("select count(*) from veteran_assessment " +
                " where date_completed >= :fromDate and date_completed <= :toDate " +
                "and clinic_id in (:clinicIds) and date_completed is not null ");


        setParametersFor593(q, fromDate, toDate, clinicIds);
        Object r = q.getSingleResult();

        if (r!=null){
            return ((Number)r).intValue();
        }

        return null;
    }

    @Override
    public Integer getAvgDurantionFor593(String fromDate, String toDate, List<Integer> clinicIds) {

        Query q = entityManager.createNativeQuery("select avg(duration) from veteran_assessment " +
                " where date_completed >= :fromDate and date_completed <= :toDate " +
                "and clinic_id in (:clinicIds) and date_completed is not null ");

        setParametersFor593(q, fromDate, toDate, clinicIds);
        Object r = q.getSingleResult();

        if (r!=null){
            return ((Number)r).intValue();
        }

        return null;
    }

    @Override
    public Integer getVetWithMultipleBatteriesFor593(String fromDate, String toDate, List<Integer> clinicIds) {

        Query q = entityManager.createNativeQuery("select  count(veteran_id) from veteran_assessment " +
                " where date_completed >= :fromDate and date_completed <= :toDate " +
                "and clinic_id in (:clinicIds) and date_completed is not null " +
                "group by veteran_id " +
                "having count(*) > 1 " );


        setParametersFor593(q, fromDate, toDate, clinicIds);
        List r = q.getResultList();

        if (r!=null){
            return r.size();
        }

        return 0;
    }

    @Override
    public Integer getNumOfAssessmentPerClinicianClinicFor593(String fromDate, String toDate, List<Integer> clinicIds){

        Query q = entityManager.createNativeQuery("select count");
      //  if ()
        setParametersFor593(q, fromDate, toDate, clinicIds
        );

        return 100;
    }

    @Override
    public List<Report593ByDayDTO> getBatteriesByDayFor593(String fromDate, String toDate, List<Integer> clinicIds){
        Query q = entityManager.createNativeQuery("select date_format(date_completed, '%m/%d/%Y'), date_format(date_completed, '%W'), " +
                " count(*)" +
                " from veteran_assessment " +
                " where date_completed >= :fromDate and date_completed <= :toDate " +
                "and clinic_id in (:clinicIds) and date_completed is not null " +
                "group by date_format( date_completed, '%Y%m%d' ) " +
                "order by date_format( date_completed, '%Y%m%d' ) ");

        setParametersFor593(q, fromDate, toDate, clinicIds );

        List<Report593ByDayDTO> result = new ArrayList<>();
        for(Object aRow : q.getResultList()){
            Object [] objs = (Object [])aRow;
            Report593ByDayDTO dto = new Report593ByDayDTO();
            dto.setTotal(Integer.toString(((Number)objs[2]).intValue()));
            dto.setDate((String) objs[0]);
            dto.setDayOfWeek((String)objs[1]);
            result.add(dto);
        }
        return result;
    }

    @Override
    public List<Report593ByTimeDTO> getBatteriesByTimeFor593(String fromDate, String toDate, List<Integer>clinicIds){

        HashMap<String, Report593ByTimeDTO> cache = new HashMap<>();
        Report593ByTimeDTO dto = null;

        Query q = entityManager.createNativeQuery("select date_format(date_completed, '%m/%d/%Y'), date_format(date_completed, '%W'), " +
                " count(*)" +
                " from veteran_assessment " +
                " where date_completed >= :fromDate and date_completed <= :toDate " +
                "and clinic_id in (:clinicIds) and date_completed is not null " +
                "and  extract(HOUR from date_completed) >= :fr and extract(HOUR from date_completed) <= :to "+
                "group by date_format( date_completed, '%Y%m%d' ) " +
                "order by date_format( date_completed, '%Y%m%d' ) ");
        setParametersFor593(q, fromDate, toDate, clinicIds);


        List result = getData(q,  6, 7);
        if (result!=null && result.size()>0) {
            for (Object aRow : result) {
                Object[] objs = (Object[])aRow;
                dto = new Report593ByTimeDTO();
                dto.setDate((String)objs[0]);
                dto.setDayOfWeek((String)objs[1]);
                dto.setSixToEight(Integer.toString(((Number) objs[2]).intValue()));
                cache.put(dto.getDate(), dto);
            }
        }


        setParametersFor593(q, fromDate, toDate, clinicIds);

        result = getData(q, 8, 9);
        if (result!=null && result.size()>0) {
            for (Object aRow : result) {
                Object[] objs = (Object[])aRow;
                dto = cache.get((String)objs[0]);
                if (dto == null) {
                    dto = new Report593ByTimeDTO();
                }
                dto.setDate((String)objs[0]);
                dto.setDayOfWeek((String)objs[1]);
                dto.setEightToTen(Integer.toString(((Number)objs[2]).intValue()));
                cache.put(dto.getDate(), dto);
            }
        }

         result = getData(q, 10, 11);
        if (result!=null && result.size()>0) {
            for (Object aRow : result) {
                Object[] objs = (Object[])aRow;
                dto = cache.get((String)objs[0]);
                if (dto == null) {
                    dto = new Report593ByTimeDTO();
                }
                dto.setDate((String)objs[0]);
                dto.setDayOfWeek((String)objs[1]);
                dto.setTenToTwelve(Integer.toString(((Number) objs[2]).intValue()));
                cache.put(dto.getDate(), dto);
            }
        }
         result = getData(q, 12, 13);
        if (result!=null && result.size()>0) {
            for (Object aRow : result) {
                Object[] objs = (Object[])aRow;
                dto = cache.get((String)objs[0]);
                if (dto == null) {
                    dto = new Report593ByTimeDTO();
                }
                dto.setDate((String)objs[0]);
                dto.setDayOfWeek((String)objs[1]);
                dto.setTwelveToTwo(Integer.toString(((Number)objs[2]).intValue()));
                cache.put(dto.getDate(), dto);
            }
        }
         result = getData(q, 14, 15);
        if (result!=null && result.size()>0) {
            for (Object aRow : result) {
                Object[] objs = (Object[])aRow;
                dto = cache.get((String)objs[0]);
                if (dto == null) {
                    dto = new Report593ByTimeDTO();
                }
                dto.setDate((String)objs[0]);
                dto.setDayOfWeek((String)objs[1]);
                dto.setTwoToFour(Integer.toString(((Number) objs[2]).intValue()));
                cache.put(dto.getDate(), dto);
            }
        }

        result = getData(q, 16, 17);
        if (result!=null && result.size()>0) {
            for (Object aRow : result) {
                Object[] objs = (Object[])aRow;
                dto = cache.get((String)objs[0]);
                if (dto == null) {
                    dto = new Report593ByTimeDTO();
                }
                dto.setDate((String)objs[0]);
                dto.setDayOfWeek((String)objs[1]);
                dto.setFourToSix(Integer.toString(((Number) objs[2]).intValue()));
                cache.put(dto.getDate(), dto);
            }
        }

        List<Report593ByTimeDTO> resultList = new ArrayList<>();
        if (!cache.isEmpty()) {
            resultList.addAll(cache.values());

            for(Report593ByTimeDTO d : resultList){
                if (d.getEightToTen()==null){
                    d.setEightToTen("0");
                }
                if (d.getFourToSix()==null){
                    d.setFourToSix("0");
                }
                if (d.getSixToEight()==null){
                    d.setSixToEight("0");
                }
                if (d.getTenToTwelve()==null){
                    d.setTenToTwelve("0");
                }
                if (d.getTwelveToTwo()==null){
                    d.setTwelveToTwo("0");
                }
                if (d.getTwoToFour()==null){
                    d.setTwoToFour("0");
                }

                d.setTotal(
                        Integer.toString(
                                Integer.parseInt(d.getEightToTen())+
                                Integer.parseInt(d.getFourToSix())+
                                Integer.parseInt(d.getSixToEight())+
                                Integer.parseInt(d.getTenToTwelve())+
                                Integer.parseInt(d.getTwelveToTwo())+
                                Integer.parseInt(d.getTwoToFour())
                        )
                );
            }

            Collections.sort(resultList, new Comparator<Report593ByTimeDTO>() {
                @Override
                public int compare(Report593ByTimeDTO o1, Report593ByTimeDTO o2) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        return sdf.parse(o1.getDate()).compareTo(sdf.parse(o2.getDate()));
                    }
                    catch(Exception e){
                        return 0;
                    }

                }
            });
        }

        return resultList;


    }

    private void setParametersFor593(Query q , String fromDate, String toDate, List<Integer> clinicIds){
        q.setParameter("fromDate", getDateFromString(fromDate+ " 00:00:00"));
        q.setParameter("toDate", getDateFromString(toDate+" 23:59:59"));
        q.setParameter("clinicIds", clinicIds);
    }


    private List getData(Query q,  int fr, int to){
        q.setParameter("fr", fr);
        q.setParameter("to", to);
        return q.getResultList();
    }
}
