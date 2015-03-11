package gov.va.escreening.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/tabletConfiguration")
public class TabletConfigurationController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(TabletConfigurationController.class);

    /**
     * Initialize and setup page.
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String setUpPageListView(Model model) {

        return "tabletConfiguration";
    }
}
