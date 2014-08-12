package gov.va.escreening.repository;

import gov.va.escreening.dto.SearchAttributes;
import gov.va.escreening.dto.SortDirection;
import gov.va.escreening.dto.dashboard.ReportFieldHelper;
import gov.va.escreening.dto.dashboard.SearchResult;
import gov.va.escreening.dto.dashboard.VeteranSearchResult;
import gov.va.escreening.entity.Program;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class VeteranRepositoryImpl extends AbstractHibernateRepository<Veteran> implements VeteranRepository {

    private static final Logger logger = LoggerFactory.getLogger(VeteranRepositoryImpl.class);

    public VeteranRepositoryImpl() {
        super();
        setClazz(Veteran.class);
    }

    @Override
    public SearchResult<VeteranSearchResult> searchVeterans(Integer veteranId, String lastName, String ssnLastFour,
            List<Integer> programIdList, SearchAttributes searchAttributes) {

        logger.debug("in searchVeterans()");

        SearchResult<VeteranSearchResult> searchResult = new SearchResult<VeteranSearchResult>();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Veteran> veteranRoot = criteriaQuery.from(Veteran.class);
        Join<Veteran, VeteranAssessment> veteranAssessmentJoin = veteranRoot.join("veteranAssessmentList");
        Join<VeteranAssessment, Program> programJoin = veteranAssessmentJoin.join("program");

        Expression<Long> countOfAssessment = criteriaBuilder.count(veteranAssessmentJoin);
        Expression<Date> maxDateCreated = criteriaBuilder.greatest(veteranAssessmentJoin.<Date> get("dateCreated"));

        criteriaQuery.select(criteriaBuilder.array(
                veteranRoot.get("veteranId"),
                veteranRoot.get("vistaLocalPid"),
                veteranRoot.get("firstName"),
                veteranRoot.get("middleName"),
                veteranRoot.get("lastName"),
                veteranRoot.get("ssnLastFour"),
                veteranRoot.get("email"),
                veteranRoot.get("gender"),
                maxDateCreated,
                countOfAssessment));

        criteriaQuery.groupBy(veteranRoot);

        List<Predicate> criteriaList = new ArrayList<Predicate>();

        if (veteranId != null) {
            criteriaList.add(criteriaBuilder.equal(veteranRoot.<Integer> get("veteranId"), veteranId));
        }

        if (StringUtils.isNotBlank(ssnLastFour)) {
            criteriaList.add(criteriaBuilder.like(veteranRoot.<String> get("ssnLastFour"), "%" + ssnLastFour + "%"));
        }

        if (StringUtils.isNotBlank(lastName)) {
            criteriaList.add(criteriaBuilder.like(veteranRoot.<String> get("lastName"), "%" + lastName + "%"));
        }

        if (programIdList != null && programIdList.size() > 0) {
            Expression<Integer> exp = programJoin.get("programId");
            Predicate programIdPredicate = exp.in(programIdList);
            criteriaList.add(programIdPredicate);
        }

        if (criteriaList.size() > 0) {
            criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[0])));
        }

        // Set default order by field and then check if one was passed to us.
        @SuppressWarnings("rawtypes")
        Expression orderByPath = veteranRoot.get("veteranId");

        if (StringUtils.isNotBlank(searchAttributes.getSortColumn())) {
            if (searchAttributes.getSortColumn().equalsIgnoreCase("veteranId")) {
                orderByPath = veteranRoot.get("veteranId");
            }
            else if (searchAttributes.getSortColumn().equalsIgnoreCase("veteranName")) {
                orderByPath = veteranRoot.get("lastName");
            }
            else if (searchAttributes.getSortColumn().equalsIgnoreCase("ssnLastFour")) {
                orderByPath = veteranRoot.get("ssnLastFour");
            }
            else if (searchAttributes.getSortColumn().equalsIgnoreCase("email")) {
                orderByPath = veteranRoot.get("email");
            }
            else if (searchAttributes.getSortColumn().equalsIgnoreCase("gender")) {
                orderByPath = veteranRoot.get("gender");
            }
            else if (searchAttributes.getSortColumn().equalsIgnoreCase("lastAssessmentDate")) {
                orderByPath = maxDateCreated;
            }
            else if (searchAttributes.getSortColumn().equalsIgnoreCase("totalAssessments")) {
                orderByPath = countOfAssessment;
            }
        }

        if (searchAttributes.getSortDirection() == SortDirection.SORT_DESCENDING) {

            criteriaQuery.orderBy(criteriaBuilder.desc(orderByPath));
        }
        else {
            criteriaQuery.orderBy(criteriaBuilder.asc(orderByPath));
        }

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);

        // Get the total count of records
        Integer totalCount = query.getResultList().size();
        searchResult.setTotalNumRowsFound(totalCount);

        // Generate the query based on the criteria.
        // Now get only the page.
        query.setFirstResult(searchAttributes.getRowStartIndex());
        query.setMaxResults(searchAttributes.getPageSize());

        List<Object[]> veterans = query.getResultList();

        List<VeteranSearchResult> veteranSearchResults = new ArrayList<VeteranSearchResult>();

        for (Object[] record : veterans) {
            VeteranSearchResult veteranSearchResult = new VeteranSearchResult();

            veteranSearchResult.setVeteranId(Integer.parseInt(String.valueOf(record[0])));

            // set pid
            if (record[1] != null)
                veteranSearchResult.setVistaLocalPid(String.valueOf(record[1]));
            else
                veteranSearchResult.setVistaLocalPid("");

            // set veteran name
            if (record[2] != null && record[4] != null) {
                veteranSearchResult.setVeteranName(String.valueOf(record[4]) + ", " + String.valueOf(record[2]));
            }
            else {
                if (record[4] != null)
                    veteranSearchResult.setVeteranName(String.valueOf(record[4]));
                else
                    veteranSearchResult.setVeteranName("");
            }

            // set ssn last four
            if (record[5] != null)
                veteranSearchResult.setSsnLastFour(String.valueOf(record[5]));
            else
                veteranSearchResult.setSsnLastFour("");

            // set email
            if (record[6] != null)
                veteranSearchResult.setEmail(String.valueOf(record[6]));
            else
                veteranSearchResult.setEmail("");

            // set gender
            if (record[7] != null)
                veteranSearchResult.setGender(String.valueOf(record[7]));
            else
                veteranSearchResult.setGender("");

            if (record[8] != null) {
                veteranSearchResult.setLastAssessmentDate(
                        ReportFieldHelper.convertDataSourceDateToReportFieldString((Date) record[8]));
            }

            if (record[9] != null) {
                veteranSearchResult.setTotalAssessments(Integer.parseInt(String.valueOf(record[9])));
            }

            veteranSearchResults.add(veteranSearchResult);
        }

        searchResult.setResultList(veteranSearchResults);

        return searchResult;
    }

    @Override
    public List<Veteran> getVeterans(Veteran veteran) {
        logger.debug("in getVeterans()");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Veteran> criteriaQuery = criteriaBuilder.createQuery(Veteran.class);
        Root<Veteran> veteranRoot = criteriaQuery.from(Veteran.class);

        List<Predicate> criteriaList = new ArrayList<Predicate>();

        if (StringUtils.isNotBlank(veteran.getSsnLastFour())) {
            criteriaList.add(criteriaBuilder.equal(veteranRoot.get("ssnLastFour"), veteran.getSsnLastFour()));
        }

        if (StringUtils.isNotBlank(veteran.getLastName())) {
            criteriaList.add(criteriaBuilder.equal(veteranRoot.get("lastName"), veteran.getLastName()));
        }

        if (veteran.getBirthDate() != null) {
            criteriaList.add(criteriaBuilder.equal(veteranRoot.get("birthDate"), veteran.getBirthDate()));
        }

        if (StringUtils.isNotBlank(veteran.getMiddleName())) {
            criteriaList.add(criteriaBuilder.equal(veteranRoot.get("middleName"), veteran.getMiddleName()));
        }

        criteriaQuery.select(veteranRoot);
        criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[0])));
        criteriaQuery.orderBy(criteriaBuilder.asc(veteranRoot.get("veteranId")));

        // Generate the query based on the criteria.
        TypedQuery<Veteran> query = entityManager.createQuery(criteriaQuery);
        List<Veteran> veterans = query.getResultList();

        return veterans;
    }

    @Override
    public List<Veteran> searchVeterans(String lastName, String ssnLastFour) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Veteran> criteriaQuery = criteriaBuilder.createQuery(Veteran.class);

        Root<Veteran> veteranRoot = criteriaQuery.from(Veteran.class);

        List<Predicate> criteriaList = new ArrayList<Predicate>();

        if (StringUtils.isNotBlank(lastName)) {
            criteriaList.add(criteriaBuilder.like(veteranRoot.<String> get("lastName"), lastName + "%"));
        }

        if (StringUtils.isNotBlank(ssnLastFour)) {
            criteriaList.add(criteriaBuilder.equal(veteranRoot.<String> get("ssnLastFour"), ssnLastFour));
        }

        criteriaQuery.select(veteranRoot);

        if (criteriaList.size() > 0) {
            criteriaQuery.where(criteriaBuilder.and(criteriaList.toArray(new Predicate[0])));
        }

        // Set default order by field and then check if one was passed to us.
        @SuppressWarnings("rawtypes")
        Expression orderByPath = veteranRoot.get("veteranId");
        criteriaQuery.orderBy(criteriaBuilder.asc(orderByPath));

        // Generate the query based on the criteria.
        TypedQuery<Veteran> query = entityManager.createQuery(criteriaQuery);

        List<Veteran> veteranResultList = query.getResultList();

        return veteranResultList;
    }

}
