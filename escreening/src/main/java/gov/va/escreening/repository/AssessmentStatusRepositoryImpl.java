package gov.va.escreening.repository;

import gov.va.escreening.entity.AssessmentStatus;

import org.springframework.stereotype.Repository;

@Repository
public class AssessmentStatusRepositoryImpl extends AbstractHibernateRepository<AssessmentStatus> implements
        AssessmentStatusRepository {

    public AssessmentStatusRepositoryImpl() {
        super();
        setClazz(AssessmentStatus.class);
    }

}
