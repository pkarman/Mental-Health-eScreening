package gov.va.escreening.controller;

import gov.va.escreening.delegate.AssessmentDelegate;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/assessment")
public class VeteranAssessmentController {

    private static final Logger logger = LoggerFactory.getLogger(VeteranAssessmentController.class);

    @Autowired
    private AssessmentDelegate assessmentDelegate;

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String setupAssessmentHomeForm(Model model, HttpServletResponse response) {

        logger.trace("setupAssessmentHomeForm called");
        
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        
        model.addAttribute("veteranFullName", assessmentDelegate.getVeteranFullName());

        assessmentDelegate.ensureValidAssessmentContext();


        return "/assessment/assessmentHome";
    }
}
