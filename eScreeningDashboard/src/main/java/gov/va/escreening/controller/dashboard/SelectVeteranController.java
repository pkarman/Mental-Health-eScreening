package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.dto.SelectVeteranResultDto;
import gov.va.escreening.form.CreateVeteranFormBean;
import gov.va.escreening.form.SelectVeteranFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/dashboard")
public class SelectVeteranController {

    private static final Logger logger = LoggerFactory.getLogger(SelectVeteranController.class);

    private CreateAssessmentDelegate createAssessmentDelegate;

    @Autowired
    public void setCreateAssessmentDelegate(CreateAssessmentDelegate createAssessmentDelegate) {
        this.createAssessmentDelegate = createAssessmentDelegate;
    }

    public SelectVeteranController() {
        // Default constructor.
    }

    /**
     * Returns the backing bean for the form.
     *
     * @return
     */
    @ModelAttribute
    public SelectVeteranFormBean getSelectVeteranFormBean() {
        logger.debug("Creating new SelectVeteranFormBean");
        return new SelectVeteranFormBean();
    }

    /**
     * Initialize and setup page.
     *
     * @param selectVeteranFormBean
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectVeteran", method = RequestMethod.GET)
    public String setUpPageSelectVeteran(HttpServletRequest request, @CurrentUser EscreenUser escreenUser,
                                         @ModelAttribute SelectVeteranFormBean selectVeteranFormBean, Model model) {

        model.addAttribute("isPostBack", false);
        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
        accomodateCreateVeteranFormBeamFromSearchResult(request, selectVeteranFormBean, model);

        return "dashboard/selectVeteran";
    }

    private void accomodateCreateVeteranFormBeamFromSearchResult(HttpServletRequest request, SelectVeteranFormBean selectVeteranFormBean, Model model) {
        HttpSession session = request.getSession();
        String lastName = (String) session.getAttribute("lastName");
        String ssnLastFour = (String) session.getAttribute("ssnLastFour");
        boolean updateCreateVeteranFormBean = false;

        if (lastName != null) {
            selectVeteranFormBean.setLastName(lastName);
            session.removeAttribute("lastName");
            updateCreateVeteranFormBean = true;
        }
        if (ssnLastFour != null) {
            selectVeteranFormBean.setSsnLastFour(ssnLastFour);
            session.removeAttribute("ssnLastFour");
            updateCreateVeteranFormBean = true;
        }
        if (updateCreateVeteranFormBean) {
            model.addAttribute("selectVeteranFormBean", selectVeteranFormBean);

            // also add the search result previously displayed
            model.addAttribute("searchResultList", session.getAttribute("searchResultList"));
            model.addAttribute("searchResultListSize", session.getAttribute("searchResultListSize"));

            model.addAttribute("isPostBack", true);
        }
    }

    /**
     * Process search request.
     *
     * @param selectVeteranFormBean
     * @param result
     * @param model
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/selectVeteran", method = RequestMethod.POST, params = "searchButton")
    public String searchPageSelectVeteran(HttpServletRequest request, @Valid @ModelAttribute SelectVeteranFormBean selectVeteranFormBean,
                                          BindingResult result, Model model, @CurrentUser EscreenUser escreenUser) {

        logger.debug(selectVeteranFormBean.toString());

        // If there is an error, return the same view.
        if (result.hasErrors()) {
            model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
            model.addAttribute("isPostBack", true);
            return "dashboard/selectVeteran";
        }

        List<SelectVeteranResultDto> searchResultList = createAssessmentDelegate.searchVeterans(escreenUser,
                selectVeteranFormBean.getLastName(), selectVeteranFormBean.getSsnLastFour());

        if (searchResultList == null) {
            searchResultList = new ArrayList<SelectVeteranResultDto>();
        }

        {
            // save the search result in Session Cache so it is available when user presses cancel button
            request.getSession().setAttribute("searchResultList", searchResultList);
            request.getSession().setAttribute("searchResultListSize", searchResultList.size());
        }

        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
        model.addAttribute("isPostBack", true);
        model.addAttribute("searchResultList", searchResultList);
        model.addAttribute("searchResultListSize", searchResultList.size());

        return "dashboard/selectVeteran";
    }

    /**
     * Redirects user to Create New Veteran page.
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectVeteran", method = RequestMethod.POST, params = "createButton")
    public String createPageSelectVeteran(HttpServletRequest request, @Valid @ModelAttribute SelectVeteranFormBean selectVeteranFormBean,
                                          BindingResult result, Model model, @CurrentUser EscreenUser escreenUser) {

        request.getSession().setAttribute("lastName", selectVeteranFormBean.getLastName());
        request.getSession().setAttribute("ssnLastFour", selectVeteranFormBean.getSsnLastFour());

        return "redirect:/dashboard/createVeteran";
    }
}
