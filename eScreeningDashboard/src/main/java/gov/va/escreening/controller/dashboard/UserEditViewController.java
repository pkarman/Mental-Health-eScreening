package gov.va.escreening.controller.dashboard;

import gov.va.escreening.dto.CallResult;
import gov.va.escreening.dto.PasswordResetRequest;
import gov.va.escreening.form.UserEditViewFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ProgramService;
import gov.va.escreening.service.RoleService;
import gov.va.escreening.service.UserService;
import gov.va.escreening.service.UserStatusService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class UserEditViewController {

    private static final Logger logger = LoggerFactory.getLogger(UserEditViewController.class);

    private ProgramService programService;
    private RoleService roleService;
    private UserService userService;
    private UserStatusService userStatusService;

    @Autowired
    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserStatusService(UserStatusService userStatusService) {
        this.userStatusService = userStatusService;
    }

    /**
     * Returns the backing bean for the form.
     * @return
     */
    @ModelAttribute
    public UserEditViewFormBean getUserEditViewFormBean() {
        logger.debug("Creating new UserEditViewFormBean");
        return new UserEditViewFormBean();
    }

    /**
     * Initialize and setup view.
     * @param model
     * @param userEditViewFormBean
     * @param userId If null or < 1, assume create new record.
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/userEditView", method = RequestMethod.GET)
    public String setUpPageEditView(Model model,
            @ModelAttribute UserEditViewFormBean userEditViewFormBean,
            @RequestParam(value = "uid", required = false) Integer userId,
            @CurrentUser EscreenUser escreenUser) {

        logger.trace("In setUpPageEditView");
        logger.debug("uid " + userId);

        if (userId != null && userId > 0) {
            userEditViewFormBean.setIsCreateMode(false);
            userEditViewFormBean = userService.getUserEditViewFormBean(userId);

            if (userEditViewFormBean == null) {
                throw new IllegalArgumentException("User not found.");
            }
        }
        else {
            userEditViewFormBean.setIsCreateMode(true);
        }

        model.addAttribute("userEditViewFormBean", userEditViewFormBean);
        model.addAttribute("programList", programService.getDropdownObjects());
        model.addAttribute("roleList", roleService.getRoleDropDownObjects());
        model.addAttribute("userStatusList", userStatusService.getUserStatusDropDownObjects());

        return "adminTab/userEditView";
    }

    /**
     * Saves the new user or updates the existing user.
     * @param model
     * @param userEditViewFormBean
     * @param result
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/userEditView", method = RequestMethod.POST, params = "saveButton")
    public String processSave(Model model,
            @Valid @ModelAttribute UserEditViewFormBean userEditViewFormBean,
            BindingResult result,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In processSave");

        if (userEditViewFormBean.getUserId() != null && userEditViewFormBean.getUserId() > 0) {
            userEditViewFormBean.setIsCreateMode(false);
        }
        else {
            userEditViewFormBean.setIsCreateMode(true);

            // check for additional validation, namely Password.
            if (StringUtils.isEmpty(userEditViewFormBean.getPassword())) {
                result.rejectValue("password", "password", "Password is required");
            }

            if (StringUtils.isEmpty(userEditViewFormBean.getPasswordConfirmed())) {
                result.rejectValue("passwordConfirmed", "passwordConfirmed", "Confirmed Password is required");
            }

            // StringUtils takes care of NULL when comparing.
            if (!StringUtils.equals(userEditViewFormBean.getPassword(), userEditViewFormBean.getPasswordConfirmed())) {
                result.rejectValue("passwordConfirmed", "passwordConfirmed",
                        "Password and Confirmed Password must be the same");
            }
        }

        // If there is an error, return the same view.
        if (result.hasErrors()) {

            model.addAttribute("programList", programService.getDropdownObjects());
            model.addAttribute("roleList", roleService.getRoleDropDownObjects());
            model.addAttribute("userStatusList", userStatusService.getUserStatusDropDownObjects());

            return "adminTab/userEditView";
        }

        CallResult callResult = new CallResult();
        callResult.setHasError(false);

        try {
            if (!userEditViewFormBean.getIsCreateMode()) {
                logger.debug("Edit mode");
                userService.updateUser(userEditViewFormBean);
            }
            else {
                logger.debug("Add mode");

                Integer userId = userService.createUser(userEditViewFormBean);

                logger.debug("Created new User with userId: " + userId);
            }
        }
        catch (DataIntegrityViolationException e) {
            callResult.setHasError(true);
            callResult.setUserMessage("Another user is already using the Login ID.");
            callResult.setSystemMessage(e.getMessage());
            logger.warn("Constraint violation exception while trying to save new user to database: " + e);
        }
        catch (Exception e) {
            callResult.setHasError(true);
            callResult
                    .setUserMessage("An unexpected error occured while saving to the database. Please try again and if the problem persists contact the technical administrator.");
            callResult.setSystemMessage(e.getMessage());
            logger.error("Excpetion while trying to save new user to database: " + e);
        }

        // If there is an error, return the same view.
        if (callResult.getHasError()) {

            model.addAttribute("callResult", callResult);

            model.addAttribute("programList", programService.getDropdownObjects());
            model.addAttribute("roleList", roleService.getRoleDropDownObjects());
            model.addAttribute("userStatusList", userStatusService.getUserStatusDropDownObjects());

            return "adminTab/userEditView";
        }

        return "redirect:/dashboard/userListView";
    }

    /**
     * User clicked on the cancel button. Redirect to list view page.
     * @param model
     * @return
     */
    @RequestMapping(value = "/userEditView", method = RequestMethod.POST, params = "cancelButton")
    public String processCancel(Model model) {

        logger.debug("In processCancel");

        return "redirect:/dashboard/userListView";
    }

    /**
     * Resets the user's password. Does not update the last password changed date field.
     * @param passwordResetRequest
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/userEditView/users/resetpass", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    public CallResult resetPassword(
            @RequestBody PasswordResetRequest passwordResetRequest,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In resetPassword");

        CallResult callResult = new CallResult();
        callResult.setHasError(false);

        if (passwordResetRequest == null) {
            callResult.setHasError(true);
            callResult.setUserMessage("Password and Confirmed Password fields are required.");

            return callResult;
        }
        else {
            boolean hasError = false;
            String errorMessage = "";

            if (StringUtils.isEmpty(passwordResetRequest.getPassword())) {
                hasError = true;
                errorMessage += "Password is a required field";
            }
            else {
                int passwordLength = passwordResetRequest.getPassword().length();

                if (passwordLength < 8 || passwordLength > 50) {
                    hasError = true;
                    errorMessage += "Password must be at least 8 characters and less than 50 characters";
                }

                // Validate complexity rule.
                String error = validatePasswordParam(passwordResetRequest.getPassword());

                if (error != null) {
                    hasError = true;
                    errorMessage += "Password must contain at least one digit, one uppercase letter, and one lowercase letter, one special character (@#%$^ etc.), and be at least 8 characters.";
                }
            }

            if (StringUtils.isEmpty(passwordResetRequest.getPasswordConfirmed())) {
                hasError = true;
                errorMessage += "Confirmed Password is a required field";
            }

            if (!StringUtils.equals(passwordResetRequest.getPassword(), passwordResetRequest.getPasswordConfirmed())) {
                hasError = true;
                errorMessage += "Passowrd and Confirmed Password must match";
            }

            // If we ahve errors, then just return here.
            if (hasError) {
                callResult.setHasError(true);
                callResult.setUserMessage(errorMessage);

                return callResult;
            }
        }

        // If we got this far, then we just need to save to database.
        try {
            userService.resetUserPassword(passwordResetRequest.getUserId(), passwordResetRequest.getPassword());
        }
        catch (Exception e) {
            callResult.setHasError(true);
            callResult
                    .setUserMessage("An error occured while trying to save the new password. Please try again and if the problem persists, contact the technical administrator.");
            callResult.setSystemMessage(e.toString());

            logger.error("Failed to update user's password from admin page: " + e);

            return callResult;
        }

        // Successfully updated password
        callResult.setHasError(false);
        callResult.setUserMessage("Successfully updated password.");

        return callResult;
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

}
