package gov.va.escreening.controller.dashboard;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/dashboard")
public class VeteranSearchController {

    private static final Logger logger = LoggerFactory.getLogger(VeteranSearchController.class);

    @RequestMapping(value = "/veteranSearch", method = RequestMethod.GET)
    public String veteranSearch(Locale locale, Model model) {
        logger.debug("In veteranSearch");

        return "dashboard/veteranSearch";
    }
}