package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.SelectVeteranResultDto;
import gov.va.escreening.form.MapVeteranToVistaRecordFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/dashboard")
public class MapVeteranToVistaRecordController {

    private static final Logger logger = LoggerFactory.getLogger(MapVeteranToVistaRecordController.class);

    private CreateAssessmentDelegate createAssessmentDelegate;

    @Autowired
    public void setCreateAssessmentDelegate(CreateAssessmentDelegate createAssessmentDelegate) {
        this.createAssessmentDelegate = createAssessmentDelegate;
    }

    public MapVeteranToVistaRecordController() {
        // Default constructor.
    }

    /**
     * Returns the backing bean for the form.
     * @return
     */
    @ModelAttribute
    public MapVeteranToVistaRecordFormBean getMapVeteranToVistaRecordFormBean() {
        logger.debug("Creating new MapVeteranToVistaRecordFormBean");
        return new MapVeteranToVistaRecordFormBean();
    }

    /**
     * Initialize and setup page.
     * @param veteranId
     * @param mapVeteranToVistaRecordFormBean
     * @param model
     * @return
     */
    @RequestMapping(value = "/mapVeteranToVistaRecord", method = RequestMethod.GET)
    public String setUpPageSelectVeteran(
            @RequestParam(value = "vid", required = true) Integer veteranId,
            @ModelAttribute MapVeteranToVistaRecordFormBean mapVeteranToVistaRecordFormBean,
            Model model, @CurrentUser EscreenUser escreenUser) {

        mapVeteranToVistaRecordFormBean.setVeteranId(veteranId);

        logger.debug("setUp: " + mapVeteranToVistaRecordFormBean.toString());
        
        VeteranDto veteranDto = createAssessmentDelegate.fetchVeteran(escreenUser, veteranId, null, false);

        // Prepopulate search form with target Veteran.
        mapVeteranToVistaRecordFormBean.setLastName(veteranDto.getLastName());
        mapVeteranToVistaRecordFormBean.setSsnLastFour(veteranDto.getSsnLastFour());

        model.addAttribute("isPostBack", false);
        model.addAttribute("veteran", veteranDto);

        return "dashboard/mapVeteranToVistaRecord";
    }

    /**
     * Process search request.
     * @param mapVeteranToVistaRecordFormBean
     * @param result
     * @param model
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/mapVeteranToVistaRecord", method = RequestMethod.GET, params = "searchButton")
    public String searchPageSelectVeteran(
            @Valid @ModelAttribute MapVeteranToVistaRecordFormBean mapVeteranToVistaRecordFormBean,
            BindingResult result, Model model, @CurrentUser EscreenUser escreenUser) {

        logger.debug(mapVeteranToVistaRecordFormBean.toString());

        // If there is an error, return the same view.
        if (result.hasErrors()) {
            return "dashboard/mapVeteranToVistaRecord";
        }

        List<SelectVeteranResultDto> searchResultList = createAssessmentDelegate.searchVistaForVeterans(escreenUser,
                mapVeteranToVistaRecordFormBean.getLastName(), mapVeteranToVistaRecordFormBean.getSsnLastFour());

        if (searchResultList == null) {
            searchResultList = new ArrayList<SelectVeteranResultDto>();
        }
        
        VeteranDto veteranDto = createAssessmentDelegate.fetchVeteran(escreenUser, mapVeteranToVistaRecordFormBean.getVeteranId(), null, false);

        model.addAttribute("isPostBack", true);
        model.addAttribute("veteran", veteranDto);
        model.addAttribute("searchResultList", searchResultList);
        model.addAttribute("searchResultListSize", searchResultList.size());

        return "dashboard/mapVeteranToVistaRecord";
    }

    /**
     * Maps the veteran to the vista record.
     * @param model
     * @param mapVeteranToVistaRecordFormBean
     * @return
     */
    @RequestMapping(value = "/mapVeteranToVistaRecord", method = RequestMethod.POST, params = "selectVeteranButton")
    public String createPageSelectVeteran(Model model,
            @ModelAttribute MapVeteranToVistaRecordFormBean mapVeteranToVistaRecordFormBean) {

        logger.debug("veteranId: " + mapVeteranToVistaRecordFormBean.getVeteranId());
        logger.debug("veteranIen: " + mapVeteranToVistaRecordFormBean.getSelectedVeteranIen());

        // Update database.
        createAssessmentDelegate.updateVeteranIen(mapVeteranToVistaRecordFormBean.getVeteranId(),
                mapVeteranToVistaRecordFormBean.getSelectedVeteranIen());

        // Redirect user.
        model.addAttribute("vid", mapVeteranToVistaRecordFormBean.getVeteranId());
        // return "dashboard/mapVeteranToVistaRecord";
        return "redirect:/dashboard/veteranDetail";
    }
}
