package gov.va.escreening.controller.dashboard;

import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.NoteTitleService;
import gov.va.escreening.service.UserService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class EditVeteranAssessmentRestController {

    private static final Logger logger = LoggerFactory.getLogger(EditVeteranAssessmentRestController.class);

    private ClinicService clinicService;
    private NoteTitleService noteTitleService;
    private UserService userService;

    @Autowired
    public void setClinicService(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @Autowired
    public void setNoteTitleService(NoteTitleService noteTitleService) {
        this.noteTitleService = noteTitleService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/editVeteranAssessment/programs/{programId}/clinics", method = RequestMethod.GET)
    @ResponseBody
    public List<DropDownObject> getClinicForProgramId(@PathVariable Integer programId,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In getClinicForProgramId");

        List<DropDownObject> resultList = clinicService.getDropDownObjectsByProgramId(programId);

        return resultList;
    }

    @RequestMapping(value = "/editVeteranAssessment/programs/{programId}/clinicians", method = RequestMethod.GET)
    @ResponseBody
    public List<DropDownObject> getClinicianForProgramId(@PathVariable Integer programId,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In getClinicianForClinicId");

        List<DropDownObject> resultList = userService.getClinicianDropDownObjects(programId);

        return resultList;
    }

    @RequestMapping(value = "/editVeteranAssessment/programs/{programId}/noteTitles", method = RequestMethod.GET)
    @ResponseBody
    public List<DropDownObject> getNotTitleForProgramId(@PathVariable Integer programId,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In getNotTitleForProgramId");

        List<DropDownObject> resultList = noteTitleService.getNoteTitleList(programId);

        return resultList;
    }
}
