package gov.va.escreening.controller.dashboard;

import gov.va.escreening.form.CreateVeteranFormBean;
import gov.va.escreening.service.VeteranService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @return
     */
    @ModelAttribute
    public CreateVeteranFormBean getCreateVeteranFormBean() {
        logger.debug("Creating new CreateVeteranFormBean");
        return new CreateVeteranFormBean();
    }

    /**
     * Initialize and setup page.
     * @param createVeteranFormBean
     * @param model
     * @return
     */
    @RequestMapping(value = "/createVeteran", method = RequestMethod.GET)
    public String setUpPageCreateVeteran(@ModelAttribute CreateVeteranFormBean createVeteranFormBean, Model model) {

        return "dashboard/createVeteran";
    }

    /**
     * Saves the form into the database.
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
            }
            catch (ParseException e) {
                result.rejectValue("birthDateString", "birthDateString", "A valid Date of Birth is required.");
                logger.error("Failed to parse birthDateString", e);
            }
        }

        // If there is an error, return the same view.
        if (result.hasErrors()) {
            return "dashboard/createVeteran";
        }

        // Save to database, get veteranId, and then redirect to next page.
        Integer veteranId = veteranService.add(createVeteranFormBean);

        return "redirect:/dashboard/veteranDetail?vid=" + veteranId;
    }

    /**
     * User clicked on the cancel button. Redirect to select veteran page.
     * @param model
     * @return
     */
    @RequestMapping(value = "/createVeteran", method = RequestMethod.POST, params = "cancelButton")
    public String cancelCreateVeteran(Model model) {

        logger.debug("In cancelCreateVeteran");

        return "redirect:/dashboard/selectVeteran";
    }
}
