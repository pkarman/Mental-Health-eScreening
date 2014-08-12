package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.domain.VeteranAssessmentDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.form.VeteranDetailFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/dashboard")
public class VeteranDetailController {

    private static final Logger logger = LoggerFactory.getLogger(VeteranDetailController.class);

    private CreateAssessmentDelegate createAssessmentDelegate;

    @Autowired
    public void setCreateAssessmentDelegate(CreateAssessmentDelegate createAssessmentDelegate) {
        this.createAssessmentDelegate = createAssessmentDelegate;
    }

    public VeteranDetailController() {
        // Default constructor.
    }

    /**
     * Returns the backing bean for the form.
     * @return
     */
    @ModelAttribute
    public VeteranDetailFormBean getVeteranDetailFormBean() {
        logger.debug("Creating new VeteranDetailFormBean");
        return new VeteranDetailFormBean();
    }

    /**
     * Initialize and setup page.
     * @param veteranDetailFormBean
     * @param veteranId
     * @param veteranIen
     * @param forceRefresh
     * @param model
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/veteranDetail", method = RequestMethod.GET)
    public String setUpPageVeteranDetail(
            Model model,
            @ModelAttribute VeteranDetailFormBean veteranDetailFormBean,
            @RequestParam(value = "vid", required = false) Integer veteranId,
            @RequestParam(value = "vien", required = false) String veteranIen,
            @RequestParam(value = "frefresh", required = false, defaultValue = "0") Boolean forceRefresh,
            @RequestParam(value = "ibc", required = false, defaultValue= "false") boolean isBatteryCreated,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In setUpPageVeteranDetail");

        // Get veteran.
        VeteranDto veteranDto = createAssessmentDelegate.fetchVeteran(escreenUser, veteranId, veteranIen, forceRefresh);

        if (veteranDto == null) {
            String s = String.format("Cannot find veteran with Veteran ID %s or VistA ID %s", veteranId, veteranIen);
            throw new IllegalArgumentException(s);
            // Or show message in this page?
        }

        // Get veteran appointments.
        List<VistaVeteranAppointment> veteranAppointmentList = new ArrayList<VistaVeteranAppointment>();
        List<VistaVeteranClinicalReminder> veteranClinicalReminderList = new ArrayList<VistaVeteranClinicalReminder>();

        if (escreenUser.getCprsVerified()) {
            if (veteranDto.getVeteranIen() != null) {
                veteranAppointmentList = createAssessmentDelegate.getVeteranAppointments(escreenUser,
                        veteranDto.getVeteranIen());
                veteranClinicalReminderList = createAssessmentDelegate.getVeteranClinicalReminders(escreenUser,
                        veteranDto.getVeteranIen());
            }
        }

        List<VeteranAssessmentDto> veteranAssessmentList = new ArrayList<VeteranAssessmentDto>();

        if (veteranDto.getVeteranId() != null) {
            veteranAssessmentList = createAssessmentDelegate.getVeteranAssessments(veteranDto.getVeteranId(), escreenUser.getProgramIdList());
        }

        veteranDetailFormBean.setVeteranId(veteranDto.getVeteranId());
        veteranDetailFormBean.setVeteranIen(veteranDto.getVeteranIen());
        veteranDetailFormBean.setHasActiveAssessment(false);

        for (VeteranAssessmentDto assessment : veteranAssessmentList) {
            if (StringUtils.equalsIgnoreCase(assessment.getAssessmentStatus(), "Clean")) {
                veteranDetailFormBean.setHasActiveAssessment(true);
                break;
            }
            else if (StringUtils.equalsIgnoreCase(assessment.getAssessmentStatus(), "Incomplete")) {
                veteranDetailFormBean.setHasActiveAssessment(true);
                break;
            }
        }

        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
        model.addAttribute("veteran", veteranDto);
        model.addAttribute("veteranAppointmentList", veteranAppointmentList);
        model.addAttribute("veteranClinicalReminderList", veteranClinicalReminderList);
        model.addAttribute("veteranAssessmentList", veteranAssessmentList);
        model.addAttribute("ibc", isBatteryCreated);
        return "dashboard/veteranDetail";
    }

    /**
     * Imports the user from VistA and then redirects back using the newly assigned veteranId.
     * @param model
     * @param veteranDetailFormBean
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/veteranDetail", method = RequestMethod.POST, params = "importVeteranButton")
    public String processImportVeteran(Model model,
            @ModelAttribute VeteranDetailFormBean veteranDetailFormBean,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("vien: {}: ", veteranDetailFormBean.getVeteranIen());

        Integer veteranId = createAssessmentDelegate.importVistaRecord(escreenUser,
                veteranDetailFormBean.getVeteranIen());

        model.addAttribute("vid", veteranId);
        return "redirect:/dashboard/veteranDetail";
    }

    /**
     * Calls the service to create a new assessment. Any 'clean' or 'incomplete' assessments will be retired. There can
     * only be one 'active' assessment for a veteran.
     * @param model
     * @param veteranDetailFormBean
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/veteranDetail", method = RequestMethod.POST, params = "createAssessmentButton")
    public String processCreateAssessment(Model model,
            @ModelAttribute VeteranDetailFormBean veteranDetailFormBean,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("veteranId: {}", veteranDetailFormBean.getVeteranId());

        // Redirect to create assessment page with the veteranId parameter.
        model.addAttribute("vid", veteranDetailFormBean.getVeteranId());
        return "redirect:/dashboard/editVeteranAssessment";
    }
}
