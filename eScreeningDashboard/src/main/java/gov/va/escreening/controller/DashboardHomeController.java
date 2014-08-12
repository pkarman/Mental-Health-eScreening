package gov.va.escreening.controller;

import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardHomeController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardHomeController.class);

    private List<WebInvocationPrivilegeEvaluator> webInvocationPrivilegeEvaluators;

    @Autowired
    public void setWebInvocationPrivilegeEvaluators(
            List<WebInvocationPrivilegeEvaluator> webInvocationPrivilegeEvaluators) {
        this.webInvocationPrivilegeEvaluators = webInvocationPrivilegeEvaluators;
    }

    @RequestMapping(value = "/")
    public String defaultpage(Model model, HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {
        logger.debug("Using the / mapping");
        return homePage(model, request, escreenUser);
    }

    @RequestMapping(value = "/home")
    public String homePage(Model model, HttpServletRequest request, @CurrentUser EscreenUser escreenUser) {
        logger.debug("Using the home mapping");
        // logger.debug("BasicUser.fullName: " + escreenUser.getFullName());
        return "dashboardHome";
    }

    @RequestMapping(value = "/allowedTabs", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Boolean> getTabPermissions(HttpServletRequest request) {
        logger.debug("Request for tab permissions");

        List<String> tabs = Arrays.asList("home", "userManagement", "formsEditor", "assessmentDashboard",
                "assessmentReport", "veteranSearch", "exportData", "createBattery", "programManagement",
                "systemConfiguration", "myAccount");

        Map<String, Boolean> dashboardTabs = new LinkedHashMap<String, Boolean>();

        // Take a vote. The other http security filter for clinician will always say TRUE.
        for (String tab : tabs) {
            boolean isAuthorized = true;

            for (WebInvocationPrivilegeEvaluator webInvocationPrivilegeEvaluator : webInvocationPrivilegeEvaluators) {
                isAuthorized = isAuthorized
                        && webInvocationPrivilegeEvaluator.isAllowed("/dashboard/" + tab, SecurityContextHolder
                                .getContext().getAuthentication());
            }

            dashboardTabs.put(tab, isAuthorized);
        }

        return dashboardTabs;
    }
}
