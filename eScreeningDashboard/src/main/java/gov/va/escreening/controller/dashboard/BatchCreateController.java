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
import gov.va.escreening.entity.Clinic;
import gov.va.escreening.entity.Program;
import gov.va.escreening.form.BatchCreateFormBean;
import gov.va.escreening.form.VeteranClinicApptSearchFormBean;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.VeteranService;
import gov.va.escreening.vista.dto.VistaClinicAppointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
@SessionAttributes({"vetIens", "vetSurveyMap", "clinicId"})
public class BatchCreateController {
	
    private static final Logger logger = LoggerFactory.getLogger(BatchCreateController.class);

    @Autowired
    private VeteranService veteranService;
    
    @Autowired
    private ClinicService clinicService;
    
    @Autowired
    private BatchBatteryCreateDelegate batchCreateDelegate;

    @Autowired
	private CreateAssessmentDelegate createAssessmentDelegate;
    
    @Autowired
    private ClinicRepository clinicRepo;
    
    
	
	@RequestMapping(value = "/selectVeterans", method = RequestMethod.POST, params="searchButton")
	public String searchVeterans(@CurrentUser EscreenUser escreenUser,
            @ModelAttribute VeteranClinicApptSearchFormBean selectVeteranFormBean, Model model) {

		logger.debug("In VeteranSearchRestController searchVeterans by clinic");
		model.addAttribute("isPostBack", true);
		model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
		
		
		String clinicIen = selectVeteranFormBean.getSelectedClinic();
		List<VistaClinicAppointment> appList = batchCreateDelegate.searchVeteranByAppointments(escreenUser, clinicIen, 
					selectVeteranFormBean.getStartDate(), selectVeteranFormBean.getEndDate());
			
		model.addAttribute("result",appList);
		model.addAttribute("searchResultListSize", appList.size());
		selectVeteranFormBean.setResult(appList);
		
		return "dashboard/selectVeterans";
		
	}
	
//	@RequestMapping(value="/views/**", method=RequestMethod.GET)
//	public String setupBasePages(HttpServletRequest request)
//	{
//		String uri = request.getRequestURI();
//		logger.info("setupBasePages "+uri);
//		return "/dashboard/"+uri.substring(uri.lastIndexOf("/"));
//	}
	
	@ModelAttribute
	public BatchCreateFormBean getEditVeteranAssessmentFormBean()
	{
		return new BatchCreateFormBean();
	}
	
	@RequestMapping(value="/selectVeterans", method = RequestMethod.POST, params="selectVeterans")
	public ModelAndView selectVeteransForBatchCreate2(Model model,
			@ModelAttribute VeteranClinicApptSearchFormBean selectVeteranFormBean,
			BindingResult result,
			@RequestParam String[] vetIens, @RequestParam String clinicId, @CurrentUser EscreenUser user)
	{	
//		Map<String, Object> paramMap = new HashMap<>();
//		paramMap.put("vetIens", vetIens);
//		paramMap.put("clinicId", clinicId);
		model.addAttribute("vetIens", vetIens);
		model.addAttribute("clinicId", clinicId);
		
		return new ModelAndView(new RedirectView("editVeteransAssessment"), model.asMap());
	}
	
	@RequestMapping(value="/editVeteransAssessment", method = RequestMethod.GET)
	public String selectVeteransForBatchCreate(Model model,
			@ModelAttribute BatchCreateFormBean batchCreateFormBean,
			@RequestParam String[] vetIens, @RequestParam String clinicId,
			BindingResult result, @CurrentUser EscreenUser user)
	{
		
		List<VeteranWithClinicalReminderFlag> vetList = batchCreateDelegate.getVeteranDetails(vetIens, user);
		
		batchCreateFormBean.setVeterans(vetList);
		model.addAttribute("veteransSize", vetList.size());
		
		Map<Integer, Set<Integer>> vetSurveyMap = new HashMap<Integer, Set<Integer>>();
		
		//Now getting the list of the surveys per veteran
		for(VeteranWithClinicalReminderFlag v : vetList)
		{
			Map<Integer, String> autoAssignedSurveyMap = 
					createAssessmentDelegate.getPreSelectedSurveyMap(user, v.getVeteranIen());
			if(!autoAssignedSurveyMap.isEmpty())
			{
				vetSurveyMap.put(v.getVeteranId(), autoAssignedSurveyMap.keySet());
				v.setDueClinicalReminder(true);
			}
		}
		
		model.addAttribute("vetSurveyMap", vetSurveyMap);
		batchCreateFormBean.setVetSurveyMap(vetSurveyMap);
		
		model.addAttribute("isCprsVerified", user.getCprsVerified());
		
		
		Clinic c = clinicRepo.findByIen(clinicId);
		batchCreateFormBean.setSelectedClinicId(c.getClinicId());
		batchCreateFormBean.setClinic(c.getName());
		
		if(c.getProgram()!= null)
		{
			int programId = c.getProgram().getProgramId();
			batchCreateFormBean.setSelectedProgramId(programId);
			batchCreateFormBean.setProgram(c.getProgram().getName());
			List<DropDownObject> noteTitleList = createAssessmentDelegate.getNoteTitleList(programId);
			model.addAttribute("noteTitleList", noteTitleList);

			// Get all clinician list since we have a clinic.
			List<DropDownObject> clinicianList = createAssessmentDelegate.getClinicianList(programId);
			model.addAttribute("clinicianList", clinicianList);
			
		}
		else
		{
		   List<DropDownObject> programList = createAssessmentDelegate.getProgramList(user.getProgramIdList());
		   model.addAttribute("programList", programList);
		}
		
		List<DropDownObject> batteryList = createAssessmentDelegate.getBatteryList();
		List<DropDownObject> availableBatteryList = new ArrayList<>();
		
		
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
		model.addAttribute("batteryList", availableBatteryList);
		
		List<BatterySurveyDto> batterySurveyList = createAssessmentDelegate.getBatterySurveyList();
		model.addAttribute("batterySurveyList", batterySurveyList);

		// 7. Get all the modules (surveys) that can be assigned
		List<SurveyDto> surveyList = createAssessmentDelegate.getSurveyList();

		// 8. Populate survey list with list of batteries it is associated with
		// to make it easier in view.
		for (BatterySurveyDto batterySurvey : batterySurveyList) {

			BatteryDto batteryDto = new BatteryDto(batterySurvey.getBatteryId(), batterySurvey.getBatteryName());

			for (SurveyDto survey : surveyList) {
				if (survey.getSurveyId().intValue() == batterySurvey.getSurveyId().intValue()) {
					if (survey.getBatteryList() == null) {
						survey.setBatteryList(new ArrayList<BatteryDto>());
					}

					survey.getBatteryList().add(batteryDto);
					break;
				}
			}
		}
		model.addAttribute("surveyList", surveyList);
		
		return "dashboard/editVeteransAssessment";
	}
	
	/**
     * Returns the backing bean for the form.
     * @return
     */
    @ModelAttribute("selectVeteranFormBean")
    public VeteranClinicApptSearchFormBean getSelectVeteranFormBean() {
        logger.debug("Creating new SelectVeteranFormBean");
        return new VeteranClinicApptSearchFormBean();
    }

    /**
     * Initialize and setup page.
     * @param selectVeteranFormBean
     * @param model
     * @return
     */
    @RequestMapping(value = "/selectVeterans", method = RequestMethod.GET)
    public String setUpPageSelectVeteran(@CurrentUser EscreenUser escreenUser,
            @ModelAttribute VeteranClinicApptSearchFormBean selectVeteranFormBean, Model model) {

        model.addAttribute("isPostBack", false);
        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
        
    	List<DropDownObject> clinicList = new ArrayList<DropDownObject>();
    	for(ClinicDto c : clinicService.getClinicDtoList())
    	{
    		DropDownObject o = new DropDownObject(c.getClinicIen(), c.getClinicName());
    		clinicList.add(o);
    	}
    	
    	 model.addAttribute("clinics", clinicList);
        return "dashboard/selectVeterans";
    }
    
//    @RequestMapping(value="/editVeteransAssessment", method = RequestMethod.GET)
//	public String setupPage(@CurrentUser EscreenUser escreenUser, Model model,
//			@ModelAttribute BatchCreateFormBean editVeteranAssessmentFormBean,
//			BindingResult result)
//    {
//    	model.addAttribute("isPostBack", false);
//        model.addAttribute("isCprsVerified", escreenUser.getCprsVerified());
//    	return "dashboard/editVeteransAssessment";
//    }
    
	@RequestMapping(value="/editVeteransAssessment", method = RequestMethod.POST, params="saveButton")
	public ModelAndView selectVeteransForBatchCreate(@CurrentUser EscreenUser escreenUser, Model model,
			@ModelAttribute BatchCreateFormBean editVeteranAssessmentFormBean,
			BindingResult result)
	{
		
		Integer selectedBatteryId = editVeteranAssessmentFormBean.getSelectedBatteryId();
		Integer selectedClinicId = editVeteranAssessmentFormBean.getSelectedClinicId();
		Integer selectedClinicianId = editVeteranAssessmentFormBean.getSelectedClinicianId();
		Integer selectedNoteTitleId = editVeteranAssessmentFormBean.getSelectedNoteTitleId();
		
		if(selectedBatteryId == null || selectedClinicId==null || selectedClinicianId==null ||selectedNoteTitleId==null)
		{
			result.reject("selectedBatteryId");
			return new ModelAndView("editVeteransAssessment");
		}
		
		
		List<BatchBatteryCreateResult> results = batchCreateDelegate.batchCreate(editVeteranAssessmentFormBean.getVeterans(), 
				editVeteranAssessmentFormBean.getSelectedProgramId(),
				selectedClinicId,
				selectedClinicianId,
				selectedNoteTitleId,
				selectedBatteryId,
				editVeteranAssessmentFormBean.getVetSurveyMap(),
				editVeteranAssessmentFormBean.getSelectedSurveyIdList(),
				escreenUser);
		
				
		model.addAttribute("batchCreateResult", results);
		return new ModelAndView(new RedirectView("batchComplete"), "batchCreateResult", results);
	}

}
