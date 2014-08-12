package gov.va.escreening.controller;

import gov.va.escreening.delegate.AssessmentDelegate;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.form.AssessmentLoginFormBean;
import gov.va.escreening.service.VeteranService;
import gov.va.escreening.validation.AssessmentLoginFormBeanValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Strings;

@Controller
public class AssessmentLoginController {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentLoginController.class);

    @Autowired
    private AssessmentDelegate assessmentDelegate;

    @Autowired
    private VeteranService veteranService;

    @ModelAttribute
    public AssessmentLoginFormBean getAssessmentLoginFormBean() {
        AssessmentLoginFormBean assessmentLoginFormBean = new AssessmentLoginFormBean();
        assessmentLoginFormBean.setAdditionalFieldRequired(false);
        return assessmentLoginFormBean;
    }

    @RequestMapping(value = "/assessmentLogin", method = RequestMethod.GET)
    public String setupForm(@ModelAttribute AssessmentLoginFormBean assessmentLoginFormBean, Model model) {
        logger.debug("assessmentLogin called GET");
        return "/assessmentLogin";
    }

    @RequestMapping(value = "/assessmentLogin", method = RequestMethod.POST)
    public String processForm(@Valid @ModelAttribute AssessmentLoginFormBean assessmentLoginFormBean,
            BindingResult result, Model model) {

        logger.debug("assessmentLogin called POST");

        // Validate class level constraints.
        new AssessmentLoginFormBeanValidator().validate(assessmentLoginFormBean, result);

        // If there is an error, return the same view.
        if (result.hasErrors()) {
            return "/assessmentLogin";
        }

        // Create a param object to pass to the search method.
        VeteranDto searchVeteranParam = createVeteranSearchObject(assessmentLoginFormBean);

        // Now check to see if the Veteran is in our database
        List<VeteranDto> dbVeterans = null;

        try {
            dbVeterans = assessmentDelegate.findVeterans(searchVeteranParam);
        }
        catch (JDBCConnectionException e) {
            logger.error("Error retrieving data from database: " + e);

            model.addAttribute("loginErrorMessage",
                    "Could not connect to the database. Please try again and if the problem persists, notify the clerk.");
            return "/assessmentLogin";
        }
        catch (CannotCreateTransactionException e) {
            logger.error("Error retrieving data from database: " + e);

            model.addAttribute("loginErrorMessage",
                    "Could not communicate with the database. Please try again and if the problem persists, notify the clerk.");
            return "/assessmentLogin";
        }

        if (dbVeterans == null || dbVeterans.size() < 1) {
            // Veteran was not found in the local database
            // The Veteran can not continue, allow them to try to reenter their credentials
            model.addAttribute("loginErrorMessage", "Last name / Last 4 SSN were not found, please try again.");
            return "/assessmentLogin";
        }
        else if (dbVeterans.size() == 1) {
            // We have a database record. Set the context to the database record
            VeteranDto contextVeteran = dbVeterans.get(0);
            return completeVeteranLogin(contextVeteran);
        }
        else if (dbVeterans.size() > 1) {
            // More than 1 veteran records found. See if we can filter it down to 1
            if (!assessmentLoginFormBean.getAdditionalFieldRequired()) {
                assessmentLoginFormBean.setAdditionalFieldRequired(true);
                logger.debug("Setting additionalFieldRequired to true and prompting for dob and middle name");
                return "/assessmentLogin";
            }
            else {
                logger.debug("More than 1 veteran records found. Redirect user to an exception page and let them work it out with the clerk.");
                return "redirect:/accountIssue";
            }
        }
        else {
            return "redirect:/accountIssue";
        }
    }

    /**
     * Extracts the fields used to search database for veterans.
     * @param assessmentLoginFormBean
     * @return
     */
    private VeteranDto createVeteranSearchObject(AssessmentLoginFormBean assessmentLoginFormBean) {
        // Create a user object for searching.
        VeteranDto veteran = new VeteranDto();
        veteran.setLastName(assessmentLoginFormBean.getLastName());
        veteran.setSsnLastFour(assessmentLoginFormBean.getLastFourSsn());

        Date dateOfBirth = null;

        // Validator ensures that the date field has a value and is valid if it was required.
        if (assessmentLoginFormBean.getAdditionalFieldRequired()) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            // There should never be a parsing error since the validator takes care of that check.

            try {
                dateOfBirth = sdf.parse(assessmentLoginFormBean.getBirthDate());
            }
            catch (ParseException e) {
                logger.error("Error parsing date", e);
            }

            veteran.setBirthDate(dateOfBirth);

            veteran.setMiddleName(assessmentLoginFormBean.getMiddleName());
        }
        return veteran;
    }

    /**
     * Fetches the Veteran Assessment for this Veteran. A Veteran Assessment must have already been created by a Staff
     * Member. If none is found, then user is redirected to the 'See clerk' page.
     * @param veteran
     * @return
     */
    private String completeVeteranLogin(VeteranDto veteran) {
        // Check if there is a valid and current Assessment. If not, then redirect to 'Please see clerk' page.
        VeteranAssessment veteranAssessment = assessmentDelegate.getAvailableVeteranAssessment(veteran
                .getVeteranId());

        // If an assessment hasn't been set up for this user, then redirect to error message.
        if (veteranAssessment == null || veteranAssessment.getVeteranAssessmentId() == null) {
            logger.debug("Found user, but could not locate valid assessment");
            return "redirect:/accountIssue";
        }
        else {
            // Initialize and setup the session scoped AssessmentContext.
            logger.debug("Found available assessment for user");
            assessmentDelegate.setUpAssessmentContext(veteran, veteranAssessment);
            return "redirect:/assessment/home";
        }
    }

    @RequestMapping(value = "/assessment/handleLogoutRequest", method = RequestMethod.GET)
    public String handleLogoutRequest(HttpSession session,
            @RequestParam(value = "reason", required = false) String reason) {

        logger.trace("handleLogoutRequest called");

        // delete everything out of the session
        session.invalidate();

        logger.trace("Cleared assessment session.");

        // After logging out redirect to the login page.
        if (Strings.isNullOrEmpty(reason)) {
            return "redirect:/assessmentLogin";
        }

        return "redirect:/assessmentLogin?reason=" + reason;
    }

}
