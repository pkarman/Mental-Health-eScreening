package gov.va.escreening.repository;

import gov.va.escreening.entity.HealthFactor;

import org.springframework.stereotype.Repository;

@Repository
public class HealthFactorRepositoryImpl extends AbstractHibernateRepository<HealthFactor> implements HealthFactorRepository {

    
    //PLEASE NOTE: if you update or create a health factor please call eventService.updateHealthFactorEvent(hf)
    
    
    public HealthFactorRepositoryImpl() {
        super();

        setClazz(HealthFactor.class);
    }    
}
