package gov.va.escreening.controller.dashboard;

import com.google.common.base.Throwables;
import gov.va.escreening.delegate.SaveToVistaContext;
import gov.va.escreening.delegate.VistaDelegate;
import gov.va.escreening.domain.AssessmentStatusEnum;
import gov.va.escreening.dto.*;
import gov.va.escreening.form.AssessmentSummaryFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dashboard")
public class AssessmentSummaryController implements MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentSummaryController.class);
    @Resource(name = "assessmentStatusMap")
    Map<String, String> assessmentStatusMap;

    private MessageSource messageSource;

    @Resource(type = AssessmentStatusService.class)
    private AssessmentStatusService assessmentStatusService;

    @Resource(type = ClinicService.class)
    private ClinicService clinicService;

    @Resource(type = NoteTitleService.class)
    private NoteTitleService noteTitleService;

    @Resource(type = UserService.class)
    private UserService userService;

    @Resource(type = VeteranAssessmentDashboardAlertService.class)
    private VeteranAssessmentDashboardAlertService veteranAssessmentDashboardAlertService;

    @Resource(type = VeteranAssessmentService.class)
    private VeteranAssessmentService veteranAssessmentService;

    @Resource(type = VeteranAssessmentSurveyService.class)
    private VeteranAssessmentSurveyService veteranAssessmentSurveyService;

    @Resource(name = "vistaDelegate")
    private VistaDelegate vistaDelegate;

    @Resource(type = AssessmentEngineService.class)
    private AssessmentEngineService assessmentEngineService;

    /**
     * Returns the backing bean for the form.
     *
     * @return
     */
    @ModelAttribute
    public AssessmentSummaryFormBean getAssessmentSummaryFormBean() {
        logger.debug("Creating new AssessmentSummaryFormBean");
        return new AssessmentSummaryFormBean();
    }

    /**
     * Sets up the form for the view.
     *
     * @param model
     * @param assessmentSummaryFormBean
     * @param veteranAssessmentId
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/assessmentSummary", method = RequestMethod.GET)
    public String setupPage(
            HttpServletRequest request,
            Model model,
            @ModelAttribute AssessmentSummaryFormBean assessmentSummaryFormBean,
            @RequestParam(value = "vaid", required = true) Integer veteranAssessmentId,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("Using the assessment summary mapping");
        logger.debug("veteranAssessmentId: " + veteranAssessmentId);

        VeteranAssessmentInfo veteranAssessmentInfo = veteranAssessmentService.getVeteranAssessmentInfo(veteranAssessmentId);
        model.addAttribute("veteranAssessmentInfo", veteranAssessmentInfo);

        assessmentSummaryFormBean.setSelectedAssessmentStatusId(veteranAssessmentInfo.getAssessmentStatusId());
        assessmentSummaryFormBean.setSelectedClinicianId(veteranAssessmentInfo.getClinicianId());
        assessmentSummaryFormBean.setSelectedClinicId(veteranAssessmentInfo.getClinicId());
        assessmentSummaryFormBean.setSelectedNoteTitleId(veteranAssessmentInfo.getNoteTitleId());
        assessmentSummaryFormBean.setSelectedVeteranAssessmentId(veteranAssessmentId);

        // Populate reference data.
        fillModel(model, veteranAssessmentInfo, escreenUser);

        // Only tech admins can change status once it is in Finalized status.
        boolean isReadOnly = false;
        boolean isAbleToChangeStatus = false;

        if (assessmentSummaryFormBean.getSelectedAssessmentStatusId().equals(AssessmentStatusEnum.FINALIZED.getAssessmentStatusId())) {
            isAbleToChangeStatus = false;
            isReadOnly = true;
        } else {
            isAbleToChangeStatus = true;
            isReadOnly = false;
        }

        if (!isAbleToChangeStatus && request.isUserInRole("Healthcare System Technical Administrator")) {
            isAbleToChangeStatus = true;
        }

        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
        model.addAttribute("isReadOnly", isReadOnly);
        model.addAttribute("isAbleToChangeStatus", isAbleToChangeStatus);

        return "dashboard/assessmentSummary";
    }

    @RequestMapping(value = "/assessmentSummary", method = RequestMethod.POST)
    public String processSavePageAndSaveToVista(
            HttpServletRequest request,
            Model model,
            @Valid @ModelAttribute AssessmentSummaryFormBean assessmentSummaryFormBean,
            BindingResult result,
            @RequestParam(value = "saveButton", required = false) String saveButton,
            @RequestParam(value = "saveToVistaButton", required = false) String saveToVistaButton,
            @CurrentUser EscreenUser escreenUser) {

        Integer veteranAssessmentId = assessmentSummaryFormBean.getSelectedVeteranAssessmentId();

        SaveToVistaContext ctxt = new SaveToVistaContext(veteranAssessmentId, escreenUser, assessmentEngineService);

        if (result.hasErrors()) {
            VeteranAssessmentInfo veteranAssessmentInfo = veteranAssessmentService.getVeteranAssessmentInfo(veteranAssessmentId);
            model.addAttribute("veteranAssessmentInfo", veteranAssessmentInfo);

            // Populate reference data.
            fillModel(model, veteranAssessmentInfo, escreenUser);
            model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());

            return "dashboard/assessmentSummary";
        }

        try {
            veteranAssessmentService.update(veteranAssessmentId, assessmentSummaryFormBean);
            ctxt.addCallResult(new CallResult(false, "Successfully updated assessment.", null));
        } catch (Exception e) {
            ctxt.addCallResult(new CallResult(true, "An unexpected error occured while saving to the database. Please try again and if the problem persists, contact the technical administrator.", Throwables.getRootCause(e).getMessage()));
        }


        // Check if we have any errors before trying to save to VistA
        if (saveToVistaButton != null && !ctxt.hasError()) {
            ctxt.getCallResults().clear();
            try {
                vistaDelegate.saveVeteranAssessmentToVista(ctxt);

                if (ctxt.isSuccessful()) {
                    ctxt.addCallResult(new CallResult(false, messageSource.getMessage("vista.pass.saved", null, null), null));
                    assessmentSummaryFormBean.setSelectedAssessmentStatusId(AssessmentStatusEnum.FINALIZED.getAssessmentStatusId());
                }

            } catch (IllegalStateException e) {
                saveVeteranAssessmentToVistaFailed(ctxt, "vista.err.data.missing", e);
            } catch (Exception e) {
                saveVeteranAssessmentToVistaFailed(ctxt, "vista.err.fatal", e);
            }
        }

        // Repopulate the model for the view.
        model.addAttribute("callResults", ctxt.getCallResults());
        model.addAttribute("hasErrors", ctxt.hasError());

        // Repopulate lookup model with data from database.
        VeteranAssessmentInfo veteranAssessmentInfo = veteranAssessmentService.getVeteranAssessmentInfo(veteranAssessmentId);
        model.addAttribute("veteranAssessmentInfo", veteranAssessmentInfo);

        // Populate reference data.
        fillModel(model, veteranAssessmentInfo, escreenUser);
        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());

        // Only tech admins can change status once it is in Finalized status.
        boolean isReadOnly = false;
        boolean isAbleToChangeStatus = false;

        if (assessmentSummaryFormBean.getSelectedAssessmentStatusId().equals(AssessmentStatusEnum.FINALIZED.getAssessmentStatusId())) {
            isAbleToChangeStatus = false;
            isReadOnly = true;
        } else {
            isAbleToChangeStatus = true;
            isReadOnly = false;
        }

        if (!isAbleToChangeStatus && request.isUserInRole("Healthcare System Technical Administrator")) {
            isAbleToChangeStatus = true;
        }

        model.addAttribute("isReadOnly", isReadOnly);
        model.addAttribute("isAbleToChangeStatus", isAbleToChangeStatus);

        return "dashboard/assessmentSummary";
    }


    private void saveVeteranAssessmentToVistaFailed(SaveToVistaContext ctxt, String errMsgKey, Exception exception) {
        String errMsg = Throwables.getStackTraceAsString(exception);
        CallResult callResult = new CallResult(true, messageSource.getMessage(errMsgKey, null, null), errMsg);
        logger.error(String.format("saveVeteranAssessmentToVistaFailed: vaid %s could not be saved to Vista. UserMsg=%s. SystemMsg=%s", ctxt.getVeteranAssessmentId(), callResult.getUserMessage(), callResult.getSystemMessage()));
        ctxt.addCallResult(callResult);
    }

    /**
     * Handle cancel button event.
     *
     * @param model
     * @param assessmentSummaryFormBean
     * @param escreenUser
     * @return
     */
    @RequestMapping(value = "/assessmentSummary", method = RequestMethod.POST, params = "cancelButton")
    public String processCancelPage(
            Model model,
            @ModelAttribute AssessmentSummaryFormBean assessmentSummaryFormBean,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug(assessmentSummaryFormBean.toString());

        return "redirect:/dashboard/assessmentDashboard";
    }

    /**
     * Adds all the reference data to the model.
     *
     * @param model
     */
    private void fillModel(Model model,
                           VeteranAssessmentInfo veteranAssessmentInfo, EscreenUser escreenUser) {

        // Populate the lookup fields.
        List<DropDownObject> assessmentStatusList = assessmentStatusService.getAssessmentStatusList();
        model.addAttribute("assessmentStatusList", assessmentStatusList);

        // filter assessmentList based on Role & current State
        filterStatusByRoleAndCurrentState(assessmentStatusList, veteranAssessmentInfo, escreenUser);

        // Program should already selected.
        Integer programId = veteranAssessmentInfo.getProgramId();
        if (programId != null) {
            List<DropDownObject> clinicList = clinicService.getDropDownObjectsByProgramId(programId);
            model.addAttribute("clinicList", clinicList);

            List<DropDownObject> noteTitleList = noteTitleService.getNoteTitleList(programId);
            model.addAttribute("noteTitleList", noteTitleList);

            List<DropDownObject> clinicianList = userService.getClinicianDropDownObjects(programId);
            model.addAttribute("clinicianList", clinicianList);
        }

        // Get the progress list.
        List<VeteranAssessmentProgressDto> progressList = veteranAssessmentSurveyService.getProgress(veteranAssessmentInfo.getVeteranAssessmentId());
        model.addAttribute("progressList", progressList);

        List<AlertDto> alertList = veteranAssessmentDashboardAlertService.findForVeteranAssessmentId(veteranAssessmentInfo.getVeteranAssessmentId());
        model.addAttribute("alertList", alertList);
    }

    /**
     * filter status based on role of user plus also the current state
     *
     * @param assessmentStatusList  list to be filtered. this is passed by reference and contents
     *                              of this list will be mutated
     * @param veteranAssessmentInfo veteran info to be used to extract the current state of the
     *                              assessment status
     * @param escreenUser           the user performing this action
     */
    private void filterStatusByRoleAndCurrentState(
            List<DropDownObject> assessmentStatusList,
            VeteranAssessmentInfo veteranAssessmentInfo, EscreenUser escreenUser) {
        Integer currentStatusId = veteranAssessmentInfo.getAssessmentStatusId();

        String authority = escreenUser.getAuthority();

        String filterKey = authority + ":" + currentStatusId;

        String allowedStatusesByRole = assessmentStatusMap.get(filterKey);
        if (allowedStatusesByRole != null) {
            List<String> allowableAssessmentStatuses = Arrays.asList(allowedStatusesByRole.split(","));

            // traverse through the assessmentStatusList and remove that is not
            // in allowableAssessmentStatuses
            for (Iterator<DropDownObject> ddoIter = assessmentStatusList.iterator(); ddoIter.hasNext(); ) {
                DropDownObject ddo = ddoIter.next();
                if (!allowableAssessmentStatuses.contains(ddo.getStateId())) {
                    ddoIter.remove();
                }
            }
        } else {
            // if no allowable state is found for current role and current
            // state, then DO NOT ALLOW to select any other state
            assessmentStatusList.clear();
        }
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
