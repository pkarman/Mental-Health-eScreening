package gov.va.escreening.repository;

import gov.va.escreening.entity.User;
import gov.va.escreening.entity.UserProgram;

import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserProgramRepositoryImpl extends AbstractHibernateRepository<UserProgram> implements
        UserProgramRepository {

    public UserProgramRepositoryImpl() {
        super();

        setClazz(UserProgram.class);
    }

    @Override
    public boolean hasUserAndProgram(Integer userId, Integer programId) {
        String sql = "SELECT up FROM UserProgram up JOIN up.user user JOIN up.program program WHERE user.userId = :userId AND program.programId=:programId";

        TypedQuery<UserProgram> query = entityManager.createQuery(sql, UserProgram.class);
        query.setParameter("userId", userId);
        query.setParameter("programId", programId);

        List<UserProgram> upList = query.getResultList();
        return !upList.isEmpty();
    }
}
