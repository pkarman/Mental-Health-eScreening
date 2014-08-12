package gov.va.escreening.repository;

import gov.va.escreening.entity.User;

import java.util.List;

public interface UserRepository extends RepositoryInterface<User> {

    /**
     * Returns all the users associated with Program programId and is in a role in 'roleIdList'
     * @param programId
     * @param roleIdList
     * @return
     */
    List<User> findByProgramIdAndRoleIdList(int programId, List<Integer> roleIdList);

    /**
     * Returns a user based on their loginId.
     * @param loginId
     */
    User findByLoginId(String loginId);

    /**
     * Returns a user based on their userId.
     * @param loginId
     */
    User findByUserId(Integer userId);

    /**
     * Retrieves all the users who have created an assessment that belongs to one of the programs in programIdList.
     * @param userStatusIdList
     * @param programIdList
     * @return
     */
    List<User> findCreatorsByUserStatusListAndAssessmentProgramIdList(List<Integer> userStatusIdList,
            List<Integer> programIdList);

    /**
     * Retrieves all the clinicians who are assigned an assessment that belongs to one of the programs in programIdList.
     * @param userStatusIdList
     * @param programIdList
     * @return
     */
    List<User> findCliniciansByUserStatusListAndAssessmentProgramIdList(List<Integer> userStatusIdList,
            List<Integer> programIdList);

    /**
     * Returns the number of users who has been mapped to the same DUZ number for the same DIVISION excluding user
     * 'userId'
     * @param userId
     * @param vistaDuz
     * @param vistaDivision
     * @return
     */
    int getCountOfUsersMappedToDuzDivision(int userId, String vistaDuz, String vistaDivision);

    /**
     * Retrieves active users who are in the clinician role and are associated with program 'programId'
     * @param programId
     * @return
     */
    List<User> findCliniciansByProgram(int programId);
}
