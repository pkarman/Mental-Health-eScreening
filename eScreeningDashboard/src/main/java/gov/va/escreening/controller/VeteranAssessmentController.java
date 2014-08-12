package gov.va.escreening.controller;

import gov.va.escreening.delegate.AssessmentDelegate;

import javax.servlet.http.HttpServletRequest;

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

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String setupAssessmentHomeForm(Model model, HttpServletRequest request) {

        logger.debug("setupAssessmentHomeForm called");

        model.addAttribute("veteranFullName", assessmentDelegate.getVeteranFullName());

        assessmentDelegate.ensureValidAssessmentContext();


        return "/assessment/assessmentHome";
    }
}
