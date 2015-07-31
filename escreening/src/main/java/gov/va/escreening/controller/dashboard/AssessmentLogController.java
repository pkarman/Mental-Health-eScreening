package gov.va.escreening.controller.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/dashboard")
public class AssessmentLogController {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentLogController.class);

    @RequestMapping(value = "/assessmentLog", method = RequestMethod.GET)
    public String setupPage(Model model,
            @RequestParam(value = "vaid", required = false) Integer veteranAssessmentId) {

        logger.debug("In AuditLogController::setupPage");

        logger.debug("veteranAssessmentId: " + veteranAssessmentId);

        return "dashboard/assessmentLog";
    }

}