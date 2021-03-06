package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.BatchBatteryCreateDelegate;
import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.domain.BatteryDto;
import gov.va.escreening.domain.BatterySurveyDto;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranWithClinicalReminderFlag;
import gov.va.escreening.dto.BatchBatteryCreateResult;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.Program;
import gov.va.escreening.form.BatchCreateFormBean;
import gov.va.escreening.form.VeteranClinicApptSearchFormBean;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.VeteranService;
import gov.va.escreening.vista.dto.VistaClinicAppointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/dashboard")
@SessionAttributes({"vetIens", "vetSurveyMap", "clinicId", "searchResult", "clinics", "veterans"})
public class BatchCreateController {

    private static final Logger logger = LoggerFactory
            .getLogger(BatchCreateController.class);
    @Resource(name = "editVeteranAssessmentController")
    EditVeteranAssessmentController editVeteranAssessmentController;
    @Autowired
    private VeteranService veteranService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private BatchBatteryCreateDelegate batchCreateDelegate;
    @Autowired
    private CreateAssessmentDelegate createAssessmentDelegate;

    @RequestMapping(value = "/selectVeterans", method = RequestMethod.POST, params = "searchButton")
    public String searchVeterans(
            @CurrentUser EscreenUser escreenUser,
            @ModelAttribute VeteranClinicApptSearchFormBean selectVeteranFormBean,
            BindingResult result,
            Model model) {

        logger.trace("In VeteranSearchRestController searchVeterans by clinic");
        model.addAttribute("isPostBack", true);
        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());

        String clinicIenPlusClinicName = selectVeteranFormBean.getSelectedClinic();
        String clinicIen=clinicIenPlusClinicName.split("[|]")[0];
        if (clinicIen == null) {
            result.reject("clinicIen");
            return "dashboard/selectVeterans";
        }
        List<VistaClinicAppointment> appList = batchCreateDelegate
                .searchVeteranByAppointments(escreenUser, clinicIen,
                        selectVeteranFormBean.getStartDate(),
                        selectVeteranFormBean.getEndDate());

        model.addAttribute("searchResult", appList);
        model.addAttribute("searchResultListSize", appList.size());
        selectVeteranFormBean.setResult(appList);

        return "dashboard/selectVeterans";

    }

    @ModelAttribute
    public BatchCreateFormBean getEditVeteranAssessmentFormBean() {
        return new BatchCreateFormBean();
    }

    @RequestMapping(value = "/selectVeterans", method = RequestMethod.POST, params = "selectVeterans")
    public ModelAndView selectVeteransForBatchCreate2(
            Model model,
            @ModelAttribute VeteranClinicApptSearchFormBean selectVeteranFormBean,
            BindingResult result, @RequestParam String[] vetIens,
            @RequestParam String clinicId, @CurrentUser EscreenUser user) {
        model.addAttribute("vetIens", vetIens);
        model.addAttribute("clinicId", clinicId);

        RedirectView view = new RedirectView("editVeteransAssessment");
        view.setExposeModelAttributes(false);
        return new ModelAndView(view);
    }

    @RequestMapping(value = "/editVeteransAssessment", method = RequestMethod.GET)
    public String selectVeteransForBatchCreate(Model model,
                                               @ModelAttribute BatchCreateFormBean batchCreateFormBean,
                                               BindingResult result,
                                               @RequestParam(value = "vid", required = false) Integer vId,
                                               @RequestParam(value = "programId", required = false) Integer programId,
                                               @RequestParam(value = "clinicId", required = false) Integer clinicId,
                                               @RequestParam(value = "noteTitleId", required = false) Integer noteTitleId,
                                               @RequestParam(value = "clinicianId", required = false) Integer clinicianId,
                                               @CurrentUser EscreenUser user) {

        // replace user from the loggedin user to the clinician
        user = clinicianId != null ? editVeteranAssessmentController.findEscreenUser(clinicianId) : user;
        batchCreateFormBean.setVeteranId(vId);
        batchCreateFormBean.setSelectedProgramId(programId);
        batchCreateFormBean.setSelectedClinicianId(clinicId);
        batchCreateFormBean.setSelectedClinicianId(clinicianId);
        batchCreateFormBean.setSelectedNoteTitleId(noteTitleId);

        String[] vetIens = (String[]) model.asMap().get("vetIens");
        String clinicIen = (String) model.asMap().get("clinicId");
        List<VistaClinicAppointment> appList = (List<VistaClinicAppointment>) model
                .asMap().get("searchResult");
        List<VeteranWithClinicalReminderFlag> vetList = batchCreateDelegate
                .getVeteranDetails(vetIens, user, appList);

        model.addAttribute("veteransSize", vetList.size());
        model.addAttribute("veterans", vetList);
        //batchCreateFormBean.setVetSurveyMap(vetSurveyMap);

        model.addAttribute("isCprsVerified", user.getCprsVerified());
        model.addAttribute("isCreateMode", true);

        ClinicDto clinicDto = clinicService.getClinicDtoByIen(clinicIen);
        batchCreateFormBean.setSelectedClinicId(clinicDto.getClinicId());

        model.addAttribute("clinic", clinicDto.getClinicName());

        //todo krizvi which program to choose
        model.addAttribute("programList", clinicDto.getProgramsAsDropDownList());
        if (programId != null) {
            List<DropDownObject> noteTitleList = createAssessmentDelegate.getNoteTitleList(programId);
            model.addAttribute("noteTitleList", noteTitleList);

            // Get all clinician list since we have a clinic.
            List<DropDownObject> clinicianList = createAssessmentDelegate.getClinicianList(programId);
            model.addAttribute("clinicianList", clinicianList);
        }

        List<DropDownObject> batteryList = createAssessmentDelegate.getBatteryList();

        Map<String, String> pm = new HashMap<String, String>();

        for (DropDownObject b : batteryList) {
            List<Program> ps = createAssessmentDelegate.getProgramsForBattery(Integer.parseInt(b.getStateId()));
            StringBuilder sb = new StringBuilder();
            for (Program p : ps) {
                sb.append("program_" + p.getProgramId()).append(" ");
            }
            pm.put(b.getStateId(), sb.toString());
        }

        model.addAttribute("programsMap", pm);
        model.addAttribute("batteryList", batteryList);

        List<BatterySurveyDto> batterySurveyList = createAssessmentDelegate
                .getBatterySurveyList();
        model.addAttribute("batterySurveyList", batterySurveyList);

        // 7. Get all the modules (surveys) that can be assigned
        List<SurveyDto> surveyList = createAssessmentDelegate.getSurveyList();

        // 8. Populate survey list with list of batteries it is associated with
        // to make it easier in view.
        for (BatterySurveyDto batterySurvey : batterySurveyList) {

            BatteryDto batteryDto = new BatteryDto(
                    batterySurvey.getBatteryId(),
                    batterySurvey.getBatteryName());

            for (SurveyDto survey : surveyList) {
                if (survey.getSurveyId().intValue() == batterySurvey
                        .getSurveyId().intValue()) {
                    if (survey.getBatteryList() == null) {
                        survey.setBatteryList(new ArrayList<BatteryDto>());
                    }

                    survey.getBatteryList().add(batteryDto);
                    break;
                }
            }
        }

        //Set pre-selected
        Map<Integer, Integer> preselectedSurveys = new HashMap<Integer, Integer>();

        Map<Integer, Set<Integer>> vetSurveyMap = new HashMap<Integer, Set<Integer>>();

        // Now getting the list of the surveys per veteran
        for (VeteranWithClinicalReminderFlag v : vetList) {
            Map<Integer, String> autoAssignedSurveyMap = createAssessmentDelegate
                    .getPreSelectedSurveyMap(user, v.getVeteranIen());
            if (!autoAssignedSurveyMap.isEmpty()) {
                vetSurveyMap.put(v.getVeteranId(),
                        new HashSet(autoAssignedSurveyMap.keySet()));
                v.setDueClinicalReminders(true);
            }

            // For each survey, pre-select it and also indicate reason in
            // the note.
            if (autoAssignedSurveyMap != null && !autoAssignedSurveyMap.isEmpty()) {
                for (int i = 0; i < surveyList.size(); ++i) {
                    Integer surveyId = surveyList.get(i).getSurveyId();

                    // Preselect it and populate note field.
                    if (autoAssignedSurveyMap.containsKey(surveyId)) {
                        surveyList.get(i).setNote(autoAssignedSurveyMap.get(surveyId));
                    }
                }
            }
        }

        model.addAttribute("vetSurveyMap", vetSurveyMap);

        for (Map.Entry<Integer, Set<Integer>> entry : vetSurveyMap.entrySet()) {
            for (Integer surveyId : entry.getValue()) {
                if (!preselectedSurveys.containsKey(surveyId)) {
                    preselectedSurveys.put(surveyId, 1);
                } else {
                    preselectedSurveys.put(surveyId, preselectedSurveys.get(surveyId) + 1);
                }
            }
        }
        model.addAttribute("surveyList", surveyList);

        int[] checkIndicator = new int[surveyList.size()];

        for (int i = 0; i < surveyList.size(); i++) {
            int checked = 0;
            SurveyDto s = surveyList.get(i);
            if (preselectedSurveys.containsKey(s.getSurveyId())) {
                int num = preselectedSurveys.get(s.getSurveyId());
                if (num == vetIens.length) {
                    checked = 2;
                } else {
                    checked = 1;
                }
            }
            checkIndicator[i] = checked;
        }
        logger.info("Check indicators: " + checkIndicator);
        model.addAttribute("dueClinicalReminders", checkIndicator);
        return clinicianId == null ? "dashboard/editVeteransAssessment" : "dashboard/editVeteransAssessmentTail";
    }

    /**
     * Returns the backing bean for the form.
     *
     * @return
     */
    @ModelAttribute("selectVeteranFormBean")
    public VeteranClinicApptSearchFormBean getSelectVeteranFormBean() {
        logger.trace("Creating new SelectVeteranFormBean");
        return new VeteranClinicApptSearchFormBean();
    }

    /**
     * Initialize and setup page.
     *
     * @param selectVeteranFormBean
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectVeterans", method = RequestMethod.GET)
    public String setUpPageSelectVeteran(
            @CurrentUser EscreenUser escreenUser,
            @ModelAttribute VeteranClinicApptSearchFormBean selectVeteranFormBean,
            Model model) {

        model.addAttribute("isPostBack", false);
        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());

        return "dashboard/selectVeterans";
    }

    @RequestMapping(value = "/batchComplete", method = RequestMethod.GET)
    public String setupPage(@CurrentUser EscreenUser escreenUser, Model model) {
        model.addAttribute("isPostBack", true);
        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
        return "dashboard/batchComplete";
    }

    @RequestMapping(value = "/editVeteransAssessment", method = RequestMethod.POST)
    public ModelAndView selectVeteransForBatchCreate(
            @CurrentUser EscreenUser escreenUser, Model model,
            @ModelAttribute BatchCreateFormBean editVeteranAssessmentFormBean,
            BindingResult result) {

        Integer selectedBatteryId = editVeteranAssessmentFormBean
                .getSelectedBatteryId();
        Integer selectedClinicId = editVeteranAssessmentFormBean
                .getSelectedClinicId();
        Integer selectedClinicianId = editVeteranAssessmentFormBean
                .getSelectedClinicianId();
        Integer selectedNoteTitleId = editVeteranAssessmentFormBean
                .getSelectedNoteTitleId();


        if (selectedBatteryId == null || selectedClinicId == null
                || selectedClinicianId == null || selectedNoteTitleId == null) {
            result.reject("selectedBatteryId");

            throw new IllegalArgumentException("Required ");
        }

        List<VeteranWithClinicalReminderFlag> vets = (List<VeteranWithClinicalReminderFlag>) model.asMap().get("veterans");
        List<BatchBatteryCreateResult> results = batchCreateDelegate
                .batchCreate(
                        vets,
                        editVeteranAssessmentFormBean.getSelectedProgramId(),
                        selectedClinicId,
                        selectedClinicianId,
                        selectedNoteTitleId,
                        selectedBatteryId,
                        (Map<Integer, Set<Integer>>) model.asMap().get("vetSurveyMap"),
                        editVeteranAssessmentFormBean.getSelectedSurveyIdList(),
                        escreenUser);

        model.addAttribute("veterans", results);
        for (BatchBatteryCreateResult r : results) {
            if (!r.getSucceed()) {
                model.addAttribute("hasErrors", true);
                break;
            }
        }
        RedirectView view = new RedirectView("batchComplete");
        view.setExposeModelAttributes(false);
        return new ModelAndView(view, model.asMap());

    }

}
