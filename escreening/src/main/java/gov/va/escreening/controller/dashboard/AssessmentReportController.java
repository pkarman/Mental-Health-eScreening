package gov.va.escreening.controller.dashboard;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/dashboard")
public class AssessmentReportController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(AssessmentReportController.class);

    @RequestMapping(value = "/assessmentReport", method = RequestMethod.GET)
    public String setupPage(Model model, HttpServletRequest request,
            @RequestParam(value = "vid", required = false) Integer veteranId) {

        // Add this to the session so the Veteran Search page can use it when a user clicks on the link.
        request.getSession().setAttribute("veteranId", veteranId);

        return "dashboard/assessmentReport";
    }
}