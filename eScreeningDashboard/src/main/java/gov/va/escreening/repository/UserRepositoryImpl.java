package gov.va.escreening.repository;

import gov.va.escreening.entity.User;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends AbstractHibernateRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super();
        setClazz(User.class);
    }

    @Override
    public List<User> findByProgramIdAndRoleIdList(int programId, List<Integer> roleIdList) {

        String sql = "SELECT u FROM User u JOIN u.role r JOIN u.userProgramList up JOIN up.program p WHERE r.roleId IN (:roleIdList) AND p.programId = :programId ORDER BY u.lastName, u.firstName";

        if (roleIdList == null || roleIdList.size() < 1) {
            return new ArrayList<User>();
        }

        TypedQuery<User> query = entityManager.createQuery(sql, User.class);
        query.setParameter("roleIdList", roleIdList);
        query.setParameter("programId", programId);

        return query.getResultList();
    }

    @Override
    public User findByLoginId(String loginId) {

        String sql = "SELECT u FROM User u WHERE u.loginId = :loginId";

        TypedQuery<User> query = entityManager.createQuery(sql, User.class);
        query.setParameter("loginId", loginId);

        List<User> userList = query.getResultList();

        if (userList.size() > 0) {
            return userList.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public User findByUserId(Integer userId) {

        String sql = "SELECT u FROM User u WHERE u.userId = :userId";

        TypedQuery<User> query = entityManager.createQuery(sql, User.class);
        query.setParameter("userId", userId);

        List<User> userList = query.getResultList();

        if (userList.size() > 0) {
            return userList.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public List<User> findCreatorsByUserStatusListAndAssessmentProgramIdList(List<Integer> userStatusIdList,
            List<Integer> programIdList) {

        List<User> resultList = new ArrayList<User>();

        String sql = "SELECT DISTINCT u FROM User u JOIN u.userStatus us JOIN u.createdByVeteranAssessmentList va JOIN va.program p WHERE us.userStatusId IN (:userStatusIdList) AND p.programId IN (:programIdList) ORDER BY u.lastName, u.firstName";

        if (programIdList != null && programIdList.size() > 0 && userStatusIdList != null
                && userStatusIdList.size() > 0) {
            TypedQuery<User> query = entityManager.createQuery(sql, User.class);
            query.setParameter("userStatusIdList", userStatusIdList);
            query.setParameter("programIdList", programIdList);
            resultList = query.getResultList();
        }

        return resultList;
    }

    @Override
    public List<User> findCliniciansByUserStatusListAndAssessmentProgramIdList(List<Integer> userStatusIdList,
            List<Integer> programIdList) {

        List<User> resultList = new ArrayList<User>();

        String sql = "SELECT DISTINCT u FROM User u JOIN u.userStatus us JOIN u.clinicianVeteranAssessmentList va JOIN va.program p WHERE us.userStatusId IN (:userStatusIdList) AND p.programId IN (:programIdList) ORDER BY u.lastName, u.firstName";

        if (programIdList != null && programIdList.size() > 0 && userStatusIdList != null
                && userStatusIdList.size() > 0) {
            TypedQuery<User> query = entityManager.createQuery(sql, User.class);
            query.setParameter("userStatusIdList", userStatusIdList);
            query.setParameter("programIdList", programIdList);
            resultList = query.getResultList();
        }

        return resultList;
    }

    @Override
    public int getCountOfUsersMappedToDuzDivision(int userId, String vistaDuz, String vistaDivision) {

        String sql = "SELECT u FROM User u WHERE u.cprsVerified = 1 AND u.userId != :userId AND u.vistaDuz = :vistaDuz AND u.vistaDivision = :vistaDivision";

        TypedQuery<User> query = entityManager.createQuery(sql, User.class);
        query.setParameter("userId", userId);
        query.setParameter("vistaDuz", vistaDuz);
        query.setParameter("vistaDivision", vistaDivision);

        List<User> userList = query.getResultList();

        if (userList == null) {
            return 0;
        }
        else {
            return userList.size();
        }
    }

    @Override
    public List<User> findCliniciansByProgram(int programId) {

        String sql = "SELECT u FROM User u";

        // TODO update database to associate users with programs and then filter on program.
        // TODO filter by user role = clinician and program. A user can belong to more than 1 program.

        TypedQuery<User> query = entityManager.createQuery(sql, User.class);
        List<User> userList = query.getResultList();

        return userList;
    }

}
