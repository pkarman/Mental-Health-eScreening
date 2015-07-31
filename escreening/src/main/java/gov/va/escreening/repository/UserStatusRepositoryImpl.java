package gov.va.escreening.repository;

import gov.va.escreening.entity.UserStatus;

import org.springframework.stereotype.Repository;

@Repository
public class UserStatusRepositoryImpl extends AbstractHibernateRepository<UserStatus> implements UserStatusRepository {

    public UserStatusRepositoryImpl() {
        super();

        setClazz(UserStatus.class);
    }

}
