package gov.va.escreening.controller.dashboard;

import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/dashboard")
public class ImportDataController {

    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(ImportDataController.class);

    /**
     * Initialize and setup page.
     * @param model
     * @return
     */
    @RequestMapping(value = "/importData", method = RequestMethod.GET)
    public String setUpPageImportData(Model model, @CurrentUser EscreenUser escreenUser) {

        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
        return "systemTab/importData";
    }

}
