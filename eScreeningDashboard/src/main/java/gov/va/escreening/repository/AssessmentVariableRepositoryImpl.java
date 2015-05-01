package gov.va.escreening.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Battery;

import org.springframework.stereotype.Repository;

@Repository
public class AssessmentVariableRepositoryImpl extends AbstractHibernateRepository<AssessmentVariable> implements AssessmentVariableRepository {
    public AssessmentVariableRepositoryImpl() {
        super();
        setClazz(AssessmentVariable.class);
    }

    @Override
    public List<AssessmentVariable> findAllFormulae() {
        String sql = "FROM AssessmentVariable av where av.assessmentVariableTypeId=4";
        TypedQuery<AssessmentVariable> query = entityManager.createQuery(sql, AssessmentVariable.class);
        List<AssessmentVariable> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public AssessmentVariable findOneByDisplayName(String name) {
        String sql = "FROM AssessmentVariable av where av.displayName='" + name + "'";
        TypedQuery<AssessmentVariable> query = entityManager.createQuery(sql, AssessmentVariable.class);
        AssessmentVariable result = query.getSingleResult();
        return result;
    }

    @Override
    public Collection<AssessmentVariable> findByDisplayNames(List<String> displayNames) {

        String sql = "FROM AssessmentVariable av where av.displayName in (:displayNames)";
        Query query = entityManager.createQuery(sql);
        query.setParameter("displayNames", displayNames);
        Collection<AssessmentVariable> result = query.getResultList();
        return result;
    }
}