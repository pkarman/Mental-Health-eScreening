package gov.va.escreening.repository;

import gov.va.escreening.entity.HealthFactor;

import org.springframework.stereotype.Repository;

@Repository
public class HealthFactorRepositoryImpl extends AbstractHibernateRepository<HealthFactor> implements HealthFactorRepository {

    public HealthFactorRepositoryImpl() {
        super();

        setClazz(HealthFactor.class);
    }    
}
