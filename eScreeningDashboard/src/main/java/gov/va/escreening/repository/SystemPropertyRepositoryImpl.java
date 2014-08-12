package gov.va.escreening.repository;

import gov.va.escreening.entity.SystemProperty;

import org.springframework.stereotype.Repository;

@Repository
public class SystemPropertyRepositoryImpl  extends AbstractHibernateRepository<SystemProperty> implements SystemPropertyRepository {

    public SystemPropertyRepositoryImpl() {
        super();

        setClazz(SystemProperty.class);
    }
}