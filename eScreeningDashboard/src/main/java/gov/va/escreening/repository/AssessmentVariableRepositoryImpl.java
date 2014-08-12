package gov.va.escreening.repository;

import gov.va.escreening.entity.AssessmentVariable;

import org.springframework.stereotype.Repository;

@Repository
public class AssessmentVariableRepositoryImpl extends AbstractHibernateRepository<AssessmentVariable>
        implements AssessmentVariableRepository {
    public AssessmentVariableRepositoryImpl() {
        super();
        setClazz(AssessmentVariable.class);
    }
}