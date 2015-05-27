package gov.va.escreening.repository;

import java.util.Collection;
import java.util.List;

import gov.va.escreening.entity.AssessmentVariable;

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
        return entityManager
                .createQuery(sql, AssessmentVariable.class)
                .getResultList();
    }

    @Override
    public AssessmentVariable findOneByDisplayName(String name) {
        String sql = "FROM AssessmentVariable av where av.displayName=:displayName";
        return entityManager
                .createQuery(sql, AssessmentVariable.class)
                .setParameter("displayName", name)
                .getSingleResult();
    }

    @Override
    public Collection<AssessmentVariable> findByDisplayNames(List<String> displayNames) {
        String sql = "FROM AssessmentVariable av where av.displayName in (:displayNames)";
        return entityManager
                .createQuery(sql, AssessmentVariable.class)
                .setParameter("displayNames", displayNames)
                .getResultList();
    }
    
    @Override
    public Collection<AssessmentVariable> getParentVariables(AssessmentVariable childVariable){
        String sql = "SELECT avc.variableParent FROM AssessmentVarChildren avc WHERE avc.variableChild.assessmentVariableId=:childId";
        return entityManager
                .createQuery(sql, AssessmentVariable.class)
                .setParameter("childId", childVariable.getAssessmentVariableId())
                .getResultList();
    }
}