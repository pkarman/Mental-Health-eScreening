package gov.va.escreening.service;

import gov.va.escreening.domain.UserDto;
import gov.va.escreening.domain.UserListDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.User;
import gov.va.escreening.form.UserEditViewFormBean;

import java.util.List;

public interface UserService {

    /**
     * Returns a list of dropDownObject of users associated with clinic clinicId in the format of 'userId' and 'lastName
     * + ', ' + 'firstName'
     * @param programId
     * @return
     */
    List<DropDownObject> getClinicianDropDownObjects(int programId);

    /**
     * Retrieves a user based on their userId
     * @param userId
     * @return
     */
    User findUser(int userId);

    /**
     * Retrieves a user based on their loginId.
     * @param loginId
     * @return
     */
    User findByLoginId(String loginId);

    /**
     * Retrieves the user list in a userListDto list.
     * @return
     */
    List<UserListDto> getUserListDtoList();

    /**
     * Retrieves a user as a userDto object.
     * @param loginId
     * @return
     */
    UserDto getUserDtoByLoginId(String loginId);

    /**
     * Retrieves a user as a userDto object.
     * @param loginId
     * @return
     */
    UserDto getUserDtoByUserId(Integer userId);

    /**
     * Creates a new user.
     * @param userDto
     * @param createdByLoginId
     */
    User create(UserDto userDto, String createdByLoginId);

    /**
     * Updates the user based on the userDto. If password is not empty, then password is also updated.
     * @param userDto
     * @param updatedByUserId
     * @return
     */
    User updateByUserId(UserDto userDto, String updatedByLoginId);

    /**
     * Updates the user's association with clinics based on the clinicIdList.
     * @param user
     * @param clinicIdList
     */
    void updateUserClinicList(User user, List<Integer> clinicIdList);

    /**
     * Verifies loginId and password are valid.
     * @param loginId
     * @param password
     * @return
     */
    boolean verifyPassword(String loginId, String password);

    /**
     * Updates the user password.
     * @param loginId
     * @param password
     */
    void updatePassword(String loginId, String password);

    /**
     * Returns a drop down list of clinicians who are assigned an assessment that belongs to one of the programs in
     * programIdList.
     * @param userStatusIdList
     * @param programIdList
     * @return
     */
    List<DropDownObject> getAssessmentClinicianDropDownObjects(List<Integer> userStatusIdList,
            List<Integer> programIdList);

    /**
     * Returns a drop down list of users who created an assessment that belongs to one of the programs in programIdList.
     * @param programIdList
     * @return
     */
    List<DropDownObject> getAssessmentCreatorDropDownObjects(List<Integer> userStatusIdList, List<Integer> programIdList);

    /**
     * Extracts and returns the full name of the user in the following format: Last, First Middle
     * @param user
     * @return
     */
    String getFullName(User user);

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
     * Updates the vista related fields for user 'userId'
     * @param userId
     * @param vistaDuz
     * @param vistaDivision
     * @param cprsVerified
     */
    void updateVistaFields(int userId, String vistaDuz, String vistaDivision, Boolean cprsVerified);

    /**
     * Retrieves all the active clinicians in program 'programId'
     * @param programId
     * @return
     */
    List<DropDownObject> getCliniciansByProgram(int programId);

    /**
     * Returns the user object as a UserEditViewFormBean
     * @param userId
     * @return
     */
    UserEditViewFormBean getUserEditViewFormBean(int userId);

    /**
     * Updates the last login related data for user 'userId'
     * @param userId
     */
    void updateLastLoginData(String loginId);

    /**
     * Updates the user
     * @param userEditViewFormBean
     */
    void updateUser(UserEditViewFormBean userEditViewFormBean);

    /**
     * Creates a new user and returns the 'userId'
     * @param userEditViewFormBean
     * @return
     */
    int createUser(UserEditViewFormBean userEditViewFormBean);

    /**
     * Used by admins to update a user's password.
     * @param userId
     * @param password
     */
    void resetUserPassword(int userId, String password);

}
