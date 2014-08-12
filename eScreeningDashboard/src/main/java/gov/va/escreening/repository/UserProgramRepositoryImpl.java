package gov.va.escreening.repository;

import gov.va.escreening.entity.UserProgram;

import org.springframework.stereotype.Repository;

@Repository
public class UserProgramRepositoryImpl extends AbstractHibernateRepository<UserProgram> implements
        UserProgramRepository {

    public UserProgramRepositoryImpl() {
        super();

        setClazz(UserProgram.class);
    }

}
