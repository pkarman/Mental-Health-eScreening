package gov.va.escreening.repository;

import gov.va.escreening.entity.UserClinic;

import org.springframework.stereotype.Repository;

@Repository
public class UserClinicRepositoryImpl extends AbstractHibernateRepository<UserClinic> implements UserClinicRepository {

    public UserClinicRepositoryImpl() {
        super();

        setClazz(UserClinic.class);
    }

}
