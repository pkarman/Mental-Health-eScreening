package gov.va.escreening.controller.dashboard;

import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.dto.ae.MheExceptionCode;
import gov.va.escreening.entity.User;
import gov.va.escreening.exception.EscreeningDataValidationException;
import gov.va.escreening.exception.VistaLinkException;
import gov.va.escreening.exception.VistaLockedAccountException;
import gov.va.escreening.exception.VistaVerificationAlreadyMappedException;
import gov.va.escreening.exception.VistaVerificationException;
import gov.va.escreening.form.MyAccountFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.UserService;
import gov.va.escreening.service.VistaService;
import gov.va.escreening.validation.MyAccountFormBeanValidator;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class MyAccountRestController extends BaseDashboardRestController {

    private static final Logger logger = LoggerFactory.getLogger(MyAccountRestController.class);
    private static final int MAX_VISTA_VERIFY_ACCOUNT_COUNT = 3;

    @Autowired
    private UserService userService;
    @Resource(name="vistaService")
    private VistaService vistaService;

    @RequestMapping(value = "/myAccount/services/users/active/init", method = RequestMethod.GET)
    @ResponseBody
    public MyAccountFormBean getsForm() {
        logger.debug("In myAccount ");

        MyAccountFormBean myAccountFormBean = new MyAccountFormBean();

        return myAccountFormBean;
    }

    @RequestMapping(value = "/myAccount/services/users/active/updatepass", method = RequestMethod.POST)
    @ResponseBody
    public String processForm(@Valid @RequestBody MyAccountFormBean myAccountFormBean, BindingResult result,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In MyAccountRestController processForm ");

        // Call Spring validator to compare new and confirmed password field.
        new MyAccountFormBeanValidator().validate(myAccountFormBean, result);

        handleResultErrors(result);

        // Validate current username/password
        if (!userService.verifyPassword(escreenUser.getUsername(), myAccountFormBean.getCurrentPassword())) {
            ErrorBuilder.throwing(EscreeningDataValidationException.class)
                .toUser("Failed to verify current password.")
                .toAdmin("User service given invalid password for user: " + escreenUser.getUsername())
                .setCode(MheExceptionCode.AUTHENTICATION_FAILED.getCode())
                .throwIt();
        }

        // If we got this far, then all is well. Change password.
        userService.updatePassword(escreenUser.getUsername(), myAccountFormBean.getNewPassword());

        return "Success";
    }

    @RequestMapping(value = "/myAccount/services/users/active/myInfo", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, String> getMyInfo(HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {
        logger.debug("In getMyInfo ");

        User user = userService.findUser(escreenUser.getUserId());

        HashMap<String, String> myInfo = new HashMap<String, String>();
        myInfo.put("firstName", user.getFirstName());
        myInfo.put("middleName", user.getMiddleName());
        myInfo.put("lastName", user.getLastName());
        myInfo.put("cprsVerified", user.getCprsVerified() ? "true" : "false");
        myInfo.put("phoneNumber", user.getPhoneNumber());
        myInfo.put("emailAddress", user.getEmailAddress());

        if (user.getRole() != null) {
            myInfo.put("roleName", user.getRole().getName());
        }

        return myInfo;
    }

    @RequestMapping(value = "/myAccount/services/users/active/verifyVistaAccount", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> verifyVistaAccount(HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {

        logger.debug("In verifyVistaAccount ");

        int vistaVerifyAccountCount = 0;

        if (request.getSession().getAttribute("VISTA_VERIFY_ACCOUNT_COUNT") != null) {
            vistaVerifyAccountCount = Integer.valueOf(request.getSession().getAttribute("VISTA_VERIFY_ACCOUNT_COUNT")
                    .toString());
        }

        String accessCode = request.getParameter("accessCode");
        String verifyCode = request.getParameter("verifyCode");

        boolean hasError = false;
        String userMessage = "";

        if (StringUtils.isEmpty(accessCode)) {
            hasError = true;
            userMessage = "Access Code is required. ";
        }

        if (StringUtils.isEmpty(verifyCode)) {
            hasError = true;
            userMessage += "Verify Code is required. ";
        }

        if (!hasError) {
            String clientIp = request.getRemoteAddr();
            logger.debug(clientIp);

            try {
                vistaService.verifyVistaAccount(escreenUser.getUserId(), accessCode, verifyCode, clientIp);
                hasError = false;
                userMessage += "Successfully verified VistA Access/Verify codes. You must log out and log back in for the new setting to take effect";
            }
            catch (VistaLockedAccountException e) {
                hasError = true;
                userMessage = "Account is locked. Failed to verify Acess/Verify codes";
                logger.debug("Failed to verify Acess/Verify codes. Account is locked.  " + escreenUser.getUserId());
            }
            catch (VistaVerificationAlreadyMappedException e) {
                hasError = true;
                userMessage = "Another account has already verified this Acess/Verify codes.";
                logger.debug("Another account has already verified this Acess/Verify codes " + escreenUser.getUserId());
            }
            catch (VistaVerificationException e) {
                hasError = true;

                ++vistaVerifyAccountCount;

                if (vistaVerifyAccountCount >= MAX_VISTA_VERIFY_ACCOUNT_COUNT) {
                    userMessage = "Invalid Acess/Verify codes. Please see your admin. Too many attempts to verify a VistA account will lock your account.";
                }
                else {
                    userMessage = "Invalid Acess/Verify codes.";
                }

                request.getSession().setAttribute("VISTA_VERIFY_ACCOUNT_COUNT", vistaVerifyAccountCount);

                logger.debug("Invalid Acess/Verify codes for user " + escreenUser.getUserId());
            }
            catch (VistaLinkException e) {
                hasError = true;
                userMessage = "Failed to connect to VistA.";
                logger.error("Exception occured connecting to VistA " + e);
            }
            catch (Exception e) {
                hasError = true;
                userMessage = "Unexpected error trying to connect to VistA to verify Access/Verify codes.";
                logger.error("Unexpected error verifying Access/Verify codes " + e);
            }
        }

        HashMap<String, String> result = new HashMap<String, String>();
        result.put("isSuccessful", hasError ? "false" : "true");
        result.put("userMessage", userMessage);

        return result;
    }
}
