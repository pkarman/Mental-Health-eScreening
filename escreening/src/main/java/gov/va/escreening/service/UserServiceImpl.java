package gov.va.escreening.service;

import gov.va.escreening.domain.RoleEnum;
import gov.va.escreening.domain.UserDto;
import gov.va.escreening.domain.UserListDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.UserClinic;
import gov.va.escreening.entity.UserProgram;
import gov.va.escreening.form.UserEditViewFormBean;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.ProgramRepository;
import gov.va.escreening.repository.RoleRepository;
import gov.va.escreening.repository.UserClinicRepository;
import gov.va.escreening.repository.UserProgramRepository;
import gov.va.escreening.repository.UserRepository;
import gov.va.escreening.repository.UserStatusRepository;
import gov.va.escreening.security.LoginHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserClinicRepository userClinicRepository;
    @Autowired
    private UserProgramRepository userProgramRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getClinicianDropDownObjects(int programId) {

        List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();

        List<Integer> clinicianRoleIdList = new ArrayList<Integer>();
        clinicianRoleIdList.add(RoleEnum.CLINICIAN.getRoleId());

        List<User> users = userRepository.findByProgramIdAndRoleIdList(programId, clinicianRoleIdList);

        for (User user : users) {
            String fullName = user.getLastName() + ", " + user.getFirstName();
            DropDownObject dropDown = new DropDownObject(String.valueOf(user.getUserId()), fullName);
            dropDownList.add(dropDown);
        }

        return dropDownList;
    }

    @Override
    public User findUser(int userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserListDto> getUserListDtoList() {
        List<UserListDto> userListDtoList = new ArrayList<UserListDto>();

        List<User> users = userRepository.findAll();

        for (User user : users) {
            UserListDto userListDto = new UserListDto();

            userListDto.setUserId(user.getUserId());
            userListDto.setLoginId(user.getLoginId());
            userListDto.setFirstName(user.getFirstName() + "");
            userListDto.setLastName(user.getLastName());

            if (user.getRole() != null) {
                userListDto.setRoleName(user.getRole().getName());
            }

            userListDto.setUserStatusName(user.getUserStatus().getName());

            userListDtoList.add(userListDto);
        }

        return userListDtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserDtoByUserId(Integer userId) {
        User user = userRepository.findByUserId(userId);
        UserDto userDto = initializeUserDto(user);
        return userDto;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserDtoByLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId);
        UserDto userDto = initializeUserDto(user);
        return userDto;
    }

    private UserDto initializeUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setClinicIdList(new ArrayList<Integer>());

        for (UserClinic userClinic : user.getUserClinicList()) {
            userDto.getClinicIdList().add(userClinic.getClinic().getClinicId());
        }

        if (user.getCprsVerified()) {
            userDto.setCprsVerified(1);
        }
        else {
            userDto.setCprsVerified(0);
        }

        userDto.setDateCreated(user.getDateCreated());
        userDto.setEmailAddress(user.getEmailAddress());
        userDto.setEmailAddress2(user.getEmailAddress2());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setLoginId(user.getLoginId());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setPhoneNumber2(user.getPhoneNumber2());

        if (user.getRole() != null) {
            userDto.setRoleId(user.getRole().getRoleId());
        }

        userDto.setUserId(user.getUserId());

        if (user.getUserStatus() != null) {
            userDto.setUserStatusId(user.getUserStatus().getUserStatusId());
        }

        return userDto;
    }

    @Override
    public User create(UserDto userDto, String createdByLoginId) {
        User user = new User();

        user.setLoginId(userDto.getLoginId());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());
        user.setEmailAddress(userDto.getEmailAddress());
        user.setEmailAddress2(userDto.getEmailAddress2());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhoneNumber2(userDto.getPhoneNumber2());

        if (userDto.getCprsVerified() != null && userDto.getCprsVerified() > 0) {
            user.setCprsVerified(true);
        }
        else {
            user.setCprsVerified(false);
        }

        if (userDto.getUserStatusId() != null) {
            user.setUserStatus(userStatusRepository.findOne(userDto.getUserStatusId()));
        }

        if (userDto.getRoleId() != null) {
            user.setRole(roleRepository.findOne(userDto.getRoleId()));
        }

        // Add the clinics.
        updateUserClinicList(user, userDto.getClinicIdList());

        userRepository.create(user);

        return user;
    }

    public User updateByUserId(UserDto userDto, String updatedByLoginId) {

        User user = userRepository.findByUserId(userDto.getUserId());

        user.setLoginId(userDto.getLoginId());
        if (StringUtils.isNotEmpty(userDto.getPassword())) {
            user.setPassword(userDto.getPassword());
        }
        user.setFirstName(userDto.getFirstName());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());
        user.setEmailAddress(userDto.getEmailAddress());
        user.setEmailAddress2(userDto.getEmailAddress2());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhoneNumber2(userDto.getPhoneNumber2());

        if (userDto.getCprsVerified() != null && userDto.getCprsVerified() > 0) {
            user.setCprsVerified(true);
        }
        else {
            user.setCprsVerified(false);
        }

        if (userDto.getUserStatusId() != null) {
            user.setUserStatus(userStatusRepository.findOne(userDto.getUserStatusId()));
        }

        if (userDto.getRoleId() != null) {
            user.setRole(roleRepository.findOne(userDto.getRoleId()));
        }

        // Need to merge the user clinic stuff here.
        updateUserClinicList(user, userDto.getClinicIdList());

        userRepository.update(user);

        return user;
    }

    @Override
    public void updateUserClinicList(User user, List<Integer> clinicIdList) {

        // If the value is null, then create an empty list to trigger removing everything.
        if (clinicIdList == null) {
            clinicIdList = new ArrayList<Integer>();
        }

        List<Integer> existingClinicIdList = new ArrayList<Integer>();

        // First remove anything that doesn't exists in the current list.
        if (user.getUserClinicList() != null && user.getUserClinicList().size() > 0) {

            for (int i = 0; i < user.getUserClinicList().size(); ++i) {
                boolean found = false;
                int existingClinicId = user.getUserClinicList().get(i).getClinic().getClinicId().intValue();

                for (int j = 0; j < clinicIdList.size(); ++j) {
                    if (existingClinicId == clinicIdList.get(j).intValue()) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // remove from existing.
                    UserClinic userClinicToDelete = user.getUserClinicList().remove(i);
                    logger.debug("Deleting userClinic: " + userClinicToDelete.getUserClinicId());
                    userClinicRepository.delete(userClinicToDelete);
                    --i;
                }
                else {
                    existingClinicIdList.add(existingClinicId);
                }
            }
        }

        // Now add anything not already associated with the user.
        if (clinicIdList != null && clinicIdList.size() > 0) {

            if (user.getUserClinicList() == null) {
                user.setUserClinicList(new ArrayList<UserClinic>());
            }

            for (int i = 0; i < clinicIdList.size(); ++i) {
                boolean found = false;

                for (int j = 0; j < existingClinicIdList.size(); ++j) {
                    if (clinicIdList.get(i).intValue() == existingClinicIdList.get(j).intValue()) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    UserClinic userClinic = new UserClinic();
                    userClinic.setUser(user);
                    userClinic.setClinic(clinicRepository.findOne(clinicIdList.get(i)));
                    user.getUserClinicList().add(userClinic);
                }
                else {
                    // Already associated with user. Nothing to do.
                }
            }
        }

    }

    @Override
    public boolean verifyPassword(String loginId, String password) {

        User user = findByLoginId(loginId);
        String protectedString = LoginHelper.prepareValueWithSha256(password);

        if (user == null) {
            return false;
        }

        if (user.getPassword().equals(protectedString)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void updatePassword(String loginId, String password) {
        User user = findByLoginId(loginId);
        user.setPassword(LoginHelper.prepareValueWithSha256(password));
        user.setDatePasswordChanged(new Date());

        userRepository.update(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getAssessmentClinicianDropDownObjects(List<Integer> userStatusIdList,
            List<Integer> programIdList) {

        List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();

        List<User> users = userRepository.findCliniciansByUserStatusListAndAssessmentProgramIdList(userStatusIdList,
                programIdList);

        for (User user : users) {
            String fullName = user.getLastName() + ", " + user.getFirstName();
            DropDownObject dropDown = new DropDownObject(String.valueOf(user.getUserId()), fullName);
            dropDownList.add(dropDown);
        }

        return dropDownList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getAssessmentCreatorDropDownObjects(List<Integer> userStatusIdList,
            List<Integer> programIdList) {

        List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();

        List<User> users = userRepository.findCreatorsByUserStatusListAndAssessmentProgramIdList(userStatusIdList,
                programIdList);

        for (User user : users) {
            String fullName = user.getLastName() + ", " + user.getFirstName();
            DropDownObject dropDown = new DropDownObject(String.valueOf(user.getUserId()), fullName);
            dropDownList.add(dropDown);
        }

        return dropDownList;
    }

    @Transactional(readOnly = true)
    @Override
    public String getFullName(User user) {
        if (user == null) {
            return "";
        }
        else {
            return StringUtils
                    .join(new Object[] { user.getLastName(), ", ", user.getFirstName(), " ", user.getMiddleName() });
        }
    }

    @Transactional(readOnly = true)
    @Override
    public int getCountOfUsersMappedToDuzDivision(int userId, String vistaDuz, String vistaDivision) {
        return userRepository.getCountOfUsersMappedToDuzDivision(userId, vistaDuz, vistaDivision);
    }

    @Override
    public void updateVistaFields(int userId, String vistaDuz, String vistaDivision, Boolean cprsVerified) {
        User user = userRepository.findOne(userId);

        user.setVistaDuz(vistaDuz);
        user.setVistaDivision(vistaDivision);
        user.setCprsVerified(cprsVerified);

        userRepository.update(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DropDownObject> getCliniciansByProgram(int programId) {

        List<DropDownObject> dropDownList = new ArrayList<DropDownObject>();

        List<User> users = userRepository.findCliniciansByProgram(programId);

        for (User user : users) {
            String fullName = user.getLastName() + ", " + user.getFirstName();
            DropDownObject dropDown = new DropDownObject(String.valueOf(user.getUserId()), fullName);
            dropDownList.add(dropDown);
        }

        return dropDownList;
    }

    @Transactional(readOnly = true)
    @Override
    public UserEditViewFormBean getUserEditViewFormBean(int userId) {

        User user = userRepository.findByUserId(userId);

        if (user == null) {
            return null;
        }

        UserEditViewFormBean userEditViewFormBean = new UserEditViewFormBean();

        userEditViewFormBean.setUserId(user.getUserId());

        userEditViewFormBean.setCprsVerified(user.getCprsVerified());
        userEditViewFormBean.setDateCreated(user.getDateCreated());
        userEditViewFormBean.setDatePasswordChanged(user.getDatePasswordChanged());
        userEditViewFormBean.setEmailAddress(user.getEmailAddress());
        userEditViewFormBean.setEmailAddress2(user.getEmailAddress2());
        userEditViewFormBean.setFirstName(user.getFirstName());
        userEditViewFormBean.setLastLoginDate(user.getLastLoginDate());
        userEditViewFormBean.setLastName(user.getLastName());
        userEditViewFormBean.setLoginId(user.getLoginId());
        userEditViewFormBean.setMiddleName(user.getMiddleName());
        userEditViewFormBean.setPhoneNumber(user.getPhoneNumber());
        userEditViewFormBean.setPhoneNumberExt(user.getPhoneNumberExt());
        userEditViewFormBean.setPhoneNumber2(user.getPhoneNumber2());
        userEditViewFormBean.setPhoneNumber2Ext(user.getPhoneNumber2Ext());
        userEditViewFormBean.setVistaDivision(user.getVistaDivision());
        userEditViewFormBean.setVistaDuz(user.getVistaDuz());
        userEditViewFormBean.setVistaVpid(user.getVistaVpid());

        if (user.getUserStatus() != null) {
            userEditViewFormBean.setSelectedUserStatusId(user.getUserStatus().getUserStatusId());
        }

        if (user.getRole() != null) {
            userEditViewFormBean.setSelectedRoleId(user.getRole().getRoleId());
        }

        if (user.getUserProgramList() != null) {
            List<Integer> selectedProgramIdList = new ArrayList<Integer>();

            for (UserProgram userProgram : user.getUserProgramList()) {
                selectedProgramIdList.add(userProgram.getProgram().getProgramId());
            }

            userEditViewFormBean.setSelectedProgramIdList(selectedProgramIdList);
        }

        return userEditViewFormBean;
    }

    @Override
    public void updateLastLoginData(String loginId) {

        User user = userRepository.findByLoginId(loginId);
        user.setLastLoginDate(new Date());
        userRepository.update(user);
    }

    @Override
    public void updateUser(UserEditViewFormBean userEditViewFormBean) {

        User user = userRepository.findOne(userEditViewFormBean.getUserId());

        user.setEmailAddress(userEditViewFormBean.getEmailAddress());
        user.setEmailAddress2(userEditViewFormBean.getEmailAddress2());
        user.setFirstName(userEditViewFormBean.getFirstName());
        user.setLastName(userEditViewFormBean.getLastName());
        user.setLoginId(userEditViewFormBean.getLoginId());
        user.setMiddleName(userEditViewFormBean.getMiddleName());
        user.setPhoneNumber(userEditViewFormBean.getPhoneNumber());
        user.setPhoneNumberExt(userEditViewFormBean.getPhoneNumberExt());
        user.setPhoneNumber2(userEditViewFormBean.getPhoneNumber2());
        user.setPhoneNumber2Ext(userEditViewFormBean.getPhoneNumber2Ext());

        if (userEditViewFormBean.getSelectedRoleId() != null) {
            user.setRole(roleRepository.findOne(userEditViewFormBean.getSelectedRoleId()));
        }

        if (userEditViewFormBean.getSelectedUserStatusId() != null) {
            user.setUserStatus(userStatusRepository.findOne(userEditViewFormBean.getSelectedUserStatusId()));
        }

        // Initialize if we have to.
        if (userEditViewFormBean.getSelectedProgramIdList() == null) {
            userEditViewFormBean.setSelectedProgramIdList(new ArrayList<Integer>());
        }

        // Get a reference to the list to make it easier to work with.
        List<Integer> programIdList = userEditViewFormBean.getSelectedProgramIdList();

        List<Integer> existingProgramIdList = new ArrayList<Integer>();

        // First remove anything that doesn't exists in the current list.
        if (user.getUserProgramList() != null && user.getUserProgramList().size() > 0) {

            for (int i = 0; i < user.getUserProgramList().size(); ++i) {

                boolean found = false;
                int existingProgramId = user.getUserProgramList().get(i).getProgram().getProgramId().intValue();

                for (int j = 0; j < programIdList.size(); ++j) {
                    if (existingProgramId == programIdList.get(j).intValue()) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // remove from existing.
                    UserProgram userProgramToDelete = user.getUserProgramList().remove(i);
                    userProgramRepository.delete(userProgramToDelete);
                    --i;
                }
                else {
                    existingProgramIdList.add(existingProgramId);
                }
            }
        }

        // Now add anything not already associated with the user.
        if (programIdList != null && programIdList.size() > 0) {

            if (user.getUserProgramList() == null) {
                user.setUserProgramList(new ArrayList<UserProgram>());
            }

            for (int i = 0; i < programIdList.size(); ++i) {
                boolean found = false;

                for (int j = 0; j < existingProgramIdList.size(); ++j) {
                    if (programIdList.get(i).intValue() == existingProgramIdList.get(j).intValue()) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    UserProgram userProgram = new UserProgram();
                    userProgram.setUser(user);
                    userProgram.setProgram(programRepository.findOne(programIdList.get(i)));
                    user.getUserProgramList().add(userProgram);
                }
                else {
                    // Already associated with user. Nothing to do.
                }
            }
        }

        userRepository.update(user);
    }

    @Override
    public int createUser(UserEditViewFormBean userEditViewFormBean) {
        User user = new User();

        user.setCprsVerified(false);
        user.setDateCreated(new Date());
        user.setEmailAddress(userEditViewFormBean.getEmailAddress());
        user.setEmailAddress2(userEditViewFormBean.getEmailAddress2());
        user.setFirstName(userEditViewFormBean.getFirstName());
        user.setLastName(userEditViewFormBean.getLastName());
        user.setLoginId(userEditViewFormBean.getLoginId());
        user.setMiddleName(userEditViewFormBean.getMiddleName());
        user.setPassword(LoginHelper.prepareValueWithSha256(userEditViewFormBean.getPassword()));
        user.setPhoneNumber(userEditViewFormBean.getPhoneNumber());
        user.setPhoneNumberExt(userEditViewFormBean.getPhoneNumberExt());
        user.setPhoneNumber2(userEditViewFormBean.getPhoneNumber2());
        user.setPhoneNumber2Ext(userEditViewFormBean.getPhoneNumber2Ext());

        if (userEditViewFormBean.getSelectedRoleId() != null) {
            user.setRole(roleRepository.findOne(userEditViewFormBean.getSelectedRoleId()));
        }

        if (userEditViewFormBean.getSelectedUserStatusId() != null) {
            user.setUserStatus(userStatusRepository.findOne(userEditViewFormBean.getSelectedUserStatusId()));
        }

        if (userEditViewFormBean.getSelectedProgramIdList() != null
                && userEditViewFormBean.getSelectedProgramIdList().size() > 0) {

            // Initialize the field.
            user.setUserProgramList(new ArrayList<UserProgram>());

            // Add each one to user as well as program.
            for (Integer programId : userEditViewFormBean.getSelectedProgramIdList()) {
                UserProgram userProgram = new UserProgram();

                Program program = programRepository.findOne(programId);
                userProgram.setUser(user);
                userProgram.setProgram(program);

                if (program.getUserProgramList() == null) {
                    program.setUserProgramList(new ArrayList<UserProgram>());
                }

                program.getUserProgramList().add(userProgram);
                user.getUserProgramList().add(userProgram);
            }
        }

        userRepository.create(user);

        return user.getUserId();
    }

    @Override
    public void resetUserPassword(int userId, String password) {

        User user = userRepository.findOne(userId);
        user.setPassword(LoginHelper.prepareValueWithSha256(password));
        user.setDatePasswordChanged(new Date());

        userRepository.update(user);
    }

}
