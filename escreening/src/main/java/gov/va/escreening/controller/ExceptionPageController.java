package gov.va.escreening.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExceptionPageController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionPageController.class);

    @RequestMapping(value = "/accountIssue", method = RequestMethod.GET)
    public String setupAccountIssueExceptionForm(Model model) {

        logger.debug("setupAccountIssueForm called");

        return "exceptions/accountIssue";
    }

    @RequestMapping(value = "/notauthorized")
    public String setupNotAuthorizedExceptionForm(Model model) {
        logger.debug("Show notauthorized page");

        return "exceptions/notauthorized";
    }
}
