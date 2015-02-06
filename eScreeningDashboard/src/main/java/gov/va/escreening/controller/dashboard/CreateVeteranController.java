package gov.va.escreening.controller.dashboard;

import gov.va.escreening.form.CreateVeteranFormBean;
import gov.va.escreening.service.VeteranService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/dashboard")
public class CreateVeteranController {

    private static final Logger logger = LoggerFactory.getLogger(CreateVeteranController.class);

    private VeteranService veteranService;

    @Autowired
    public void setVeteranService(VeteranService veteranService) {
        this.veteranService = veteranService;
    }

    /**
     * Returns the backing bean for the form.
     *
     * @return
     */
    @ModelAttribute
    public CreateVeteranFormBean getCreateVeteranFormBean() {
        logger.debug("Creating new CreateVeteranFormBean");
        return new CreateVeteranFormBean();
    }

    /**
     * Initialize and setup page.
     *
     * @param createVeteranFormBean
     * @param model
     * @return
     */
    @RequestMapping(value = "/createVeteran", method = RequestMethod.GET)
    public String setUpPageCreateVeteran(HttpServletRequest request, @ModelAttribute CreateVeteranFormBean createVeteranFormBean, Model model) {
        accomodateCreateVeteranFormBeamFromSearchResult(request, createVeteranFormBean, model);
        return "dashboard/createVeteran";
    }

    private void accomodateCreateVeteranFormBeamFromSearchResult(HttpServletRequest request, CreateVeteranFormBean createVeteranFormBean, Model model) {
        HttpSession session = request.getSession();
        String lastName = (String) session.getAttribute("lastName");
        String ssnLastFour = (String) session.getAttribute("ssnLastFour");
        boolean updateCreateVeteranFormBean = false;

        if (lastName != null) {
            createVeteranFormBean.setLastName(lastName);
            session.removeAttribute("lastName");
            updateCreateVeteranFormBean = true;
        }
        if (ssnLastFour != null) {
            createVeteranFormBean.setSsnLastFour(ssnLastFour);
            session.removeAttribute("ssnLastFour");
            updateCreateVeteranFormBean = true;
        }
        if (updateCreateVeteranFormBean) {
            model.addAttribute("createVeteranFormBean", createVeteranFormBean);
        }

    }

    /**
     * Saves the form into the database.
     *
     * @param createVeteranFormBean
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "/createVeteran", method = RequestMethod.POST, params = "saveButton")
    public String processCreateVeteran(@Valid @ModelAttribute CreateVeteranFormBean createVeteranFormBean,
                                       BindingResult result, Model model) {

        // Need to validate birthDate.
        if (StringUtils.isNotBlank(createVeteranFormBean.getBirthDateString())) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            try {
                createVeteranFormBean.setBirthDate(sdf.parse(createVeteranFormBean.getBirthDateString()));
            } catch (ParseException pe) {
                result.rejectValue("birthDateString", "birthDateString", "A valid Date of Birth is required.");
                logger.error("Failed to parse birthDateString", pe);
            }
        }

        // If there is an error, return the same view.
        if (result.hasErrors()) {
            return "dashboard/createVeteran";
        }

        // Save to database, get veteranId, and then redirect to next page.
        try {
            Integer veteranId = veteranService.add(createVeteranFormBean);
            return "redirect:/dashboard/veteranDetail?vid=" + veteranId;
        } catch (DataIntegrityViolationException dve) {
            if (dve.getCause() instanceof ConstraintViolationException) {
                logger.error("Veteran being created already exists", dve);
                result.rejectValue(null, null, "You are creating a duplicate record. Add more information in the fields, or click Cancel.");
            } else {
                throw dve;
            }
        }

        // If we get here there is an error, return the same view.
        return "dashboard/createVeteran";

    }

    /**
     * User clicked on the cancel button. Redirect to select veteran page.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/createVeteran", method = RequestMethod.POST, params = "cancelButton")
    public String cancelCreateVeteran(HttpServletRequest request,@Valid @ModelAttribute CreateVeteranFormBean createVeteranFormBean,
                                      BindingResult result, Model model) {

        logger.debug("In cancelCreateVeteran");

        request.getSession().setAttribute("lastName", createVeteranFormBean.getLastName());
        request.getSession().setAttribute("ssnLastFour", createVeteranFormBean.getSsnLastFour());

        return "redirect:/dashboard/selectVeteran";
    }
}
