package gov.va.escreening.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardLoginController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardLoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        logger.debug("In the dashboard login controller.");

        return "dashboardLogin";
    }

    @RequestMapping(value = "/handleLogoutRequest")
    public String handleLoginRequest(Model model, HttpSession session) {
        logger.debug("In the logout controller.");

        // delete everything out of the session
        session.invalidate();
        logger.debug("Cleared the session for the user.");

        // After logging out redirect to the login page.

        return "redirect:/dashboard/login";
    }
}