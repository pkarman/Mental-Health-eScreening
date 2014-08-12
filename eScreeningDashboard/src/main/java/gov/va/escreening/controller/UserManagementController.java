package gov.va.escreening.controller;

import gov.va.escreening.domain.UserDto;
import gov.va.escreening.entity.User;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.security.LoginHelper;
import gov.va.escreening.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/dashboard")
public class UserManagementController {

    private static String CPRS_VERIFIED = "Verified";
    private static String CPRS_NOT_VERIFIED = "Not Verified";

    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/userManagement", method = RequestMethod.GET)
    public ModelAndView userManagementDo(ModelAndView modelAndView) {
        logger.debug("userManagement invoked navigating to user management view.");
        modelAndView.setViewName("userManagement");
        return modelAndView;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public ModelAndView navigateToCreateUserView(ModelAndView modelAndView) {
        logger.debug("createUser invoked navigating to create user view.");
        modelAndView.setViewName("createUser");

        modelAndView.addObject("cprsVerifiedValue", CPRS_NOT_VERIFIED);

        return modelAndView;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public ModelAndView createUser(@RequestParam String firstNameParam, @RequestParam String middleInitialParam,
            @RequestParam String lastNameParam, @RequestParam String userIdParam,
            @RequestParam String passwordParam, @RequestParam String emailAddressParam,
            @RequestParam String emailAddress2Param, @RequestParam String phoneNumberParam,
            @RequestParam String phoneNumber2Param, @RequestParam String userRoleSelectParam,
            @RequestParam String cprsVerifiedParam,
            @RequestParam String userStatusSelectParam, @RequestParam String clinicsParam, ModelAndView modelAndView,
            HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {

        logger.debug("createUser action invoked, creating user with passed in parameters");
        List<String> errors = validateFormParameters(firstNameParam, middleInitialParam, lastNameParam, userIdParam,
                passwordParam, emailAddressParam, emailAddress2Param, phoneNumberParam, phoneNumber2Param,
                true);

        if (errors.size() > 0) {
            modelAndView.addObject("createUserStatusMessage", errors);
            modelAndView.setViewName("createUser");
            setUserDataInSessionForViewCreateMode(modelAndView, firstNameParam, middleInitialParam, lastNameParam,
                    userIdParam, passwordParam, emailAddressParam, emailAddress2Param, phoneNumberParam,
                    phoneNumber2Param, userRoleSelectParam, userStatusSelectParam, clinicsParam, cprsVerifiedParam);
            return modelAndView;
        }

        // need to parse out the clinicsParam which is a comma delimited list of clinics
        List<Integer> clinicIdList = getClinicIdListFromString(clinicsParam);

        String preparedValue = LoginHelper.prepareValueWithSha256(passwordParam);

        UserDto userToAdd = new UserDto();

        userToAdd.setLoginId(userIdParam);
        userToAdd.setPassword(preparedValue);
        userToAdd.setFirstName(firstNameParam);
        userToAdd.setMiddleName(middleInitialParam);
        userToAdd.setLastName(lastNameParam);
        userToAdd.setEmailAddress(emailAddressParam);
        userToAdd.setEmailAddress2(emailAddress2Param);
        userToAdd.setPhoneNumber(phoneNumberParam);
        userToAdd.setPhoneNumber2(phoneNumber2Param);
        userToAdd.setCprsVerified(convertCprsVerified(cprsVerifiedParam));
        userToAdd.setUserStatusId(Integer.parseInt(userStatusSelectParam));
        userToAdd.setRoleId(Integer.parseInt(userRoleSelectParam));
        userToAdd.setClinicIdList(clinicIdList);

        try {
            User user = userService.create(userToAdd, escreenUser.getUsername());
            logger.debug("New UserID: " + user.getUserId());
        }
        catch (PersistenceException ex) {
            logger.error("Could not create user.", ex);

            if (ex.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex
                        .getCause();
                logger.error("Constraint violated: " + constraintViolationException.getConstraintName());

                modelAndView.addObject("createUserStatusMessage", "The specified username of " + userIdParam
                        + " already exists. Please use a unique username. The new user was not created.");
            }
            else {
                modelAndView.addObject("createUserStatusMessage", "A database error occured while creating the user");
            }

            setUserDataInSessionForView(modelAndView, userToAdd);
            return modelAndView;
        }
        catch (Exception ex) {

            modelAndView.addObject("createUserStatusMessage", "An error occured while creating the user");
            setUserDataInSessionForView(modelAndView, userToAdd);
            return modelAndView;
        }

        modelAndView.addObject("createUserStatusMessage", "User was successfully added");

        modelAndView.setViewName("createUser");
        modelAndView.addObject("cprsVerifiedValue", CPRS_NOT_VERIFIED);

        return modelAndView;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.GET)
    public ModelAndView navigateToEditUserView(ModelAndView modelAndView, @RequestParam String mode,
            @RequestParam String userId) {

        logger.debug("editUser invoked navigating to edit user view.");

        Integer userIdInt = Integer.parseInt(userId);
        UserDto user = userService.getUserDtoByUserId(userIdInt);

        modelAndView.addObject("userManagementPageMode", mode);

        if (user == null)
            modelAndView.addObject("createUserStatusMessage", "Could not retrieve user id " + userId);
        else {
            setUserDataInSessionForView(modelAndView, user);
        }

        modelAndView.setViewName("createUser");
        return modelAndView;
    }

    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public ModelAndView editUser(@RequestParam String idOfUserParam,
            @RequestParam String firstNameParam, @RequestParam String middleInitialParam,
            @RequestParam String lastNameParam, @RequestParam String userIdParam,
            @RequestParam String passwordParam, @RequestParam String emailAddressParam,
            @RequestParam String emailAddress2Param, @RequestParam String phoneNumberParam,
            @RequestParam String cprsVerifiedParam,
            @RequestParam String phoneNumber2Param, @RequestParam String userRoleSelectParam,
            @RequestParam String userStatusSelectParam, @RequestParam String clinicsParam, ModelAndView modelAndView,
            HttpServletRequest request, HttpServletResponse response, @CurrentUser EscreenUser escreenUser) {

        logger.debug("editUser action invoked, editing a user with passed in parameters");

        List<String> errors = validateFormParameters(firstNameParam, middleInitialParam, lastNameParam, userIdParam,
                passwordParam, emailAddressParam, emailAddress2Param, phoneNumberParam, phoneNumber2Param,
                false);

        if (errors.size() > 0) {
            modelAndView.addObject("createUserStatusMessage", errors);
            modelAndView.setViewName("editUser");
            // setUserDataInSessionForView(modelAndView, editedUser);
            return modelAndView;
        }

        //get the original LoginId
        //TODO get the user by userid and save their login
        Integer userIdInt = Integer.parseInt(idOfUserParam);
        UserDto preEditUser = userService.getUserDtoByUserId(userIdInt);
        
        // need to parse out the clinicsParam which is a comma delimited list of clinics
        List<Integer> clinicIdList = getClinicIdListFromString(clinicsParam);

        if (StringUtils.isNotBlank(passwordParam)) {
            passwordParam = LoginHelper.prepareValueWithSha256(passwordParam);
        }
        
        UserDto userToUpdate = new UserDto();
        userToUpdate.setUserId(Integer.parseInt(idOfUserParam));
        userToUpdate.setLoginId(userIdParam);
        userToUpdate.setPassword(passwordParam);
        userToUpdate.setFirstName(firstNameParam);
        userToUpdate.setMiddleName(middleInitialParam);
        userToUpdate.setLastName(lastNameParam);
        userToUpdate.setEmailAddress(emailAddressParam);
        userToUpdate.setEmailAddress2(emailAddress2Param);
        userToUpdate.setPhoneNumber(phoneNumberParam);
        userToUpdate.setPhoneNumber2(phoneNumber2Param);
        userToUpdate.setCprsVerified(convertCprsVerified(cprsVerifiedParam));
        userToUpdate.setUserStatusId(Integer.parseInt(userStatusSelectParam));
        userToUpdate.setRoleId(Integer.parseInt(userRoleSelectParam));
        userToUpdate.setClinicIdList(clinicIdList);
        
        UserDto checkedUser = null;
        try {
        	if(!preEditUser.getLoginId().equalsIgnoreCase(userToUpdate.getLoginId())) {
	        	//check to see if the loginId is already in use, if it is then do not allow the update to continue
	        	checkedUser = userService.getUserDtoByLoginId(userToUpdate.getLoginId());
	        	
	        	//the loginid is already in use do not continue.
	            modelAndView.addObject("createUserStatusMessage",
	                    String.format("The username of %s has already been used, please try a different username.", userToUpdate.getLoginId()));
        	}
        }
        catch(NullPointerException npe) {
        	//swallow the exception this is expected if the loginid doesn't exist yet.
        }
        
        if(checkedUser == null) {
	        try {
	      		userService.updateByUserId(userToUpdate, escreenUser.getUsername());
	        }
	        catch (PersistenceException ex) {
	            logger.error("Could not update user.", ex);
	
	            if (ex.getCause() instanceof ConstraintViolationException) {
	                ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex
	                        .getCause();
	                logger.error("Constraint violated: " + constraintViolationException.getConstraintName());
	
	                modelAndView.addObject("createUserStatusMessage",
	                        String.format("Your changes to %s were successfully saved", userToUpdate.getLoginId()));
	            }
	            else {
	                modelAndView.addObject("createUserStatusMessage", "A database error occured while creating the user");
	            }
	
	            setUserDataInSessionForView(modelAndView, userToUpdate);
	            modelAndView.addObject("loginId", userToUpdate.getLoginId());
	            modelAndView.addObject("userManagementPageMode", "edit");
	            modelAndView.setViewName("createUser");
	            return modelAndView;
	        }
	        catch (Exception ex) {
	            logger.error("Could not update user.", ex);
	            modelAndView.addObject("createUserStatusMessage",
	                    String.format("Your changes to %s were successfully saved", userToUpdate.getLoginId()));
	
	            modelAndView.addObject("createUserStatusMessage", "An error occured while saving the user");
	            setUserDataInSessionForView(modelAndView, userToUpdate);
	            modelAndView.addObject("loginId", userToUpdate.getLoginId());
	            modelAndView.addObject("userManagementPageMode", "edit");
	            modelAndView.setViewName("createUser");
	            return modelAndView;
	        }
        }
        
        // set the user data on the view
        setUserDataInSessionForView(modelAndView, userToUpdate);
        modelAndView.addObject("loginId", userToUpdate.getLoginId());
        modelAndView.addObject("userManagementPageMode", "edit");
        modelAndView.setViewName("createUser");

        return modelAndView;
    }

    public List<Integer> getClinicIdListFromString(String delimitedClinicString) {

        List<Integer> clinicIdList = new ArrayList<Integer>();

        if (delimitedClinicString == null || delimitedClinicString.isEmpty()) {
            return clinicIdList;
        }

        List<String> clinicIdStringList = Arrays.asList(delimitedClinicString.split(","));

        for (String clinicIdString : clinicIdStringList) {
            clinicIdList.add(Integer.parseInt(clinicIdString));
        }

        return clinicIdList;
    }

    private void setUserDataInSessionForView(ModelAndView modelAndView, UserDto user) {
        // set the values in the session
        modelAndView.addObject("idParamVal", user.getUserId());
        modelAndView.addObject("firstNameValue", user.getFirstName());
        modelAndView.addObject("lastNameValue", user.getLastName());
        modelAndView.addObject("userIdValue", user.getLoginId());
        modelAndView.addObject("emailAddressValue", user.getEmailAddress());
        modelAndView.addObject("phoneNumberValue", user.getPhoneNumber());
        modelAndView.addObject("middleInitialValue", user.getMiddleName());
        modelAndView.addObject("emailAddress2Value", user.getEmailAddress2());
        modelAndView.addObject("phoneNumber2Value", user.getPhoneNumber2());
        modelAndView.addObject("selectedUserStatusId", user.getUserStatusId());
        modelAndView.addObject("selectedUserRoleId", user.getRoleId());
        modelAndView.addObject("selectedUserClinicDelimitedIDs", StringUtils.join(user.getClinicIdList(), ","));
        if (user.getCprsVerified() == 1)
            modelAndView.addObject("cprsVerifiedValue", CPRS_VERIFIED);
        else
            modelAndView.addObject("cprsVerifiedValue", CPRS_NOT_VERIFIED);
    }

    private void setUserDataInSessionForViewCreateMode(ModelAndView modelAndView, String firstNameParam,
            String middleInitialParam, String lastNameParam, String userIdParam, String passwordParam,
            String emailAddressParam, String emailAddress2Param, String phoneNumberParam, String phoneNumber2Param,
            String userRoleSelectParam, String userStatusSelectParam, String clinicsParam, String cprsVerifiedParam) {
        // set the values in the session
        modelAndView.addObject("firstNameValue", firstNameParam);
        modelAndView.addObject("lastNameValue", lastNameParam);
        modelAndView.addObject("userIdValue", userIdParam);
        modelAndView.addObject("emailAddressValue", emailAddressParam);
        modelAndView.addObject("phoneNumberValue", phoneNumberParam);
        modelAndView.addObject("middleInitialValue", middleInitialParam);
        modelAndView.addObject("emailAddress2Value", emailAddress2Param);
        modelAndView.addObject("phoneNumber2Value", phoneNumber2Param);
        modelAndView.addObject("selectedUserStatusId", userStatusSelectParam);
        modelAndView.addObject("selectedUserRoleId", userRoleSelectParam);
        modelAndView.addObject("selectedUserClinicDelimitedIDs", clinicsParam);
        modelAndView.addObject("cprsVerifiedValue", cprsVerifiedParam);
    }

    public List<String> validateFormParameters(String firstNameParam, String middleNameParam, String lastNameParam,
            String userIdParam, String passwordParam, String emailAddressParam,
            String emailAddress2Param, String phoneNumberParam, String phoneNumber2Param, boolean passwordRequired) {

        List<String> errors = new ArrayList<String>();

        String error = validateFirstNameParam(firstNameParam);
        if (error != null)
            errors.add(error);

        error = validateMiddleNameParam(middleNameParam);
        if (error != null)
            errors.add(error);

        error = validateLastNameParam(lastNameParam);
        if (error != null)
            errors.add(error);

        error = validateLoginIdParam(userIdParam);
        if (error != null)
            errors.add(error);

        error = validateEmailOneParam(emailAddressParam);
        if (error != null)
            errors.add(error);

        error = validateEmailTwoParam(emailAddress2Param);
        if (error != null)
            errors.add(error);

        error = validatePhoneOneParam(phoneNumberParam);
        if (error != null)
            errors.add(error);

        error = validatePhoneTwoParam(phoneNumber2Param);
        if (error != null)
            errors.add(error);

        // password is required for account creation, but is optional for edit.
        if (passwordRequired || (passwordParam != null && !passwordParam.isEmpty())) {
            error = validatePasswordParam(passwordParam);
            if (error != null)
                errors.add(error);
        }

        return errors;
    }

    public String validatePasswordParam(String passwordParam) {
        String error = null;

        if (passwordParam != null && !passwordParam.isEmpty()) {
            Pattern pattern = Pattern.compile("^.*(?=.{8,})(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%&?]).*$");
            Matcher matcher = pattern.matcher(passwordParam);
            boolean matched = matcher.matches();
            if (!matched)
                error = "Password must contain at least one digit, one uppercase letter, and one lowercase letter, one special character (@#%$^ etc.), and be at least 8 characters.";
        }

        return error;
    }

    public String validatePhoneOneParam(String phoneOneParam) {
        String error = null;

        // phone one is required must be 10 characters
        if (phoneOneParam == null || phoneOneParam.isEmpty())
            error = "Phone number one is a required parameter.";
        else if (phoneOneParam.length() < 1 || phoneOneParam.length() > 11)
            error = "Phone number one must be no more than 10 characters";

        return error;
    }

    public String validatePhoneTwoParam(String phoneTwoParam) {
        String error = null;

        // phone one is optional and must be no more than 10 characters
        if (phoneTwoParam == null || phoneTwoParam.isEmpty())
            return error;
        else if (phoneTwoParam.length() < 1 || phoneTwoParam.length() > 11)
            error = "Phone number two must be no more than 10 characters";

        return error;
    }

    public String validateEmailOneParam(String emailParam) {
        // email one is required, must be between 1 and 50 characters, and must be a valid email.
        String error = null;

        if (emailParam == null || emailParam.isEmpty())
            error = "Email one is a required parameter.";

        if (error == null)
            if (emailParam.length() < 1 || emailParam.length() > 50)
                error = "Email one must be no longer than 50 characters.";

        if (error == null) {
            Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(emailParam);
            boolean matched = matcher.matches();
            if (!matched)
                error = "A valid email address (ex: example@example.com) must be entered.";
        }
        return error;
    }

    public String validateEmailTwoParam(String emailTwoParam) {
        // email two is optional, must be between 1 and 50 characters, and must be a valid email.
        String error = null;

        if (emailTwoParam == null || emailTwoParam.isEmpty())
            return error;

        if (error == null)
            if (emailTwoParam.length() < 1 || emailTwoParam.length() > 50)
                error = "Email two must be no longer than 50 characters.";

        if (error == null) {
            Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(emailTwoParam);
            boolean matched = matcher.matches();
            if (!matched)
                error = "A valid email address (ex: example@example.com) must be entered.";
        }
        return error;
    }

    public String validateFirstNameParam(String firstNameParam) {
        String error = null;

        // firstNameParam is required must be between 1 and 30 characters
        if (firstNameParam == null || firstNameParam.isEmpty())
            error = "First name is a required parameter.";
        else if (firstNameParam.length() < 1 || firstNameParam.length() > 30)
            error = "First name must be between 1 and 30 characters in length.";

        return error;
    }

    public String validateMiddleNameParam(String middleNameParam) {
        String error = null;

        // middleNameParam is optional and must be between 1 and 30 characters
        if (middleNameParam != null && !middleNameParam.isEmpty())
            if (middleNameParam.length() < 1 || middleNameParam.length() > 30)
                error = "Middle name must be between 1 and 30 characters in length.";

        return error;
    }

    public String validateLastNameParam(String lastNameParam) {
        String error = null;

        // lastNameParam is required must be between 1 and 30 characters
        if (lastNameParam == null || lastNameParam.isEmpty())
            error = "Last name is a required parameter.";
        else if (lastNameParam.length() < 1 || lastNameParam.length() > 30)
            error = "Last name must be between 1 and 30 characters in length.";

        return error;
    }

    public String validateLoginIdParam(String loginIdParam) {
        String error = null;

        // loginIdParam is required must be between 1 and 30 characters
        if (loginIdParam == null || loginIdParam.isEmpty())
            error = "Login id is a required parameter.";
        else if (loginIdParam.length() < 1 || loginIdParam.length() > 30)
            error = "Login id must be between 1 and 30 characters in length.";

        return error;
    }

    /*
     * private Integer convertCprsVerified(String cprsVerified) { if (cprsVerified == null || cprsVerified.isEmpty())
     * return 0; return Integer.parseInt(cprsVerified); }
     */

    private Integer convertCprsVerified(String cprsVerified) {
        if (cprsVerified == null || cprsVerified.isEmpty())
            return 0;

        if (cprsVerified.toLowerCase().equals(CPRS_VERIFIED.toLowerCase()))
            return 1;
        else
            return 0;
    }

}
