package gov.va.escreening.controller.dashboard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/dashboard")
public class MyAccountController {

    private static final Logger logger = LoggerFactory.getLogger(MyAccountController.class);

    @RequestMapping(value = "/myAccount", method = RequestMethod.GET)
    public String setupPage(Model model) {
        logger.debug("In myAccount ");

        return "dashboard/myAccount";
    }

}
