package gov.va.escreening.controller.dashboard;

import gov.va.escreening.delegate.BatchBatteryCreateDelegate;
import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.domain.BatteryDto;
import gov.va.escreening.domain.BatterySurveyDto;
import gov.va.escreening.domain.ClinicDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.DataTableResponse;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.dto.dashboard.VeteranSearchResult;
import gov.va.escreening.entity.Clinic;
import gov.va.escreening.form.EditVeteranAssessmentFormBean;
import gov.va.escreening.form.CreateVeteranAssessementsFormBean;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.VistaRepository;
import gov.va.escreening.security.CurrentUser;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.VeteranService;
import gov.va.escreening.vista.dto.VistaClinicAppointment;
import gov.va.escreening.webservice.Response;
import gov.va.escreening.webservice.ResponseStatus;
import gov.va.escreening.webservice.ResponseStatus.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dashboard")
public class CreateAssessmentRestController {

    private static final Logger logger = LoggerFactory.getLogger(CreateAssessmentRestController.class);

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

    @RequestMapping(value = "/veteranSearch/services/veterans/search2", method = RequestMethod.POST)
    @ResponseBody
    public DataTableResponse<VeteranDto> getVeteransByLastNameLastFour(HttpServletRequest request,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In getVeteransByLastNameLastFour");

        String lastName = request.getParameter("lastName");
        String ssnLastFour = request.getParameter("ssnLastFour");

        List<VeteranDto> veterans;

        if (StringUtils.isBlank(lastName) || StringUtils.isBlank(ssnLastFour)) {
            veterans = new ArrayList<VeteranDto>();
        }
        else {
            VeteranDto veteran = new VeteranDto();
            veteran.setLastName(lastName);
            veteran.setSsnLastFour(ssnLastFour);

            veterans = veteranService.findVeterans(veteran);
        }

        DataTableResponse<VeteranDto> dataTableResponse = new DataTableResponse<VeteranDto>();
        dataTableResponse.setTotalRecords(veterans.size());
        dataTableResponse.setTotalDisplayRecords(veterans.size());

        dataTableResponse.setData(veterans);

        return dataTableResponse;
    }

    @RequestMapping(value = "/veteranSearch/services/veterans/search3", method = RequestMethod.POST)
    @ResponseBody
    public List<VeteranDto> getVeteransByLastNameLastFour3(@RequestBody VeteranDto veteranDto,
            @CurrentUser EscreenUser escreenUser) {

        logger.debug("In getVeteransByLastNameLastFour3");

        String lastName = veteranDto.getLastName();
        String ssnLastFour = veteranDto.getSsnLastFour();

        logger.debug(lastName + " " + ssnLastFour);

        List<VeteranDto> veterans;

        if (StringUtils.isBlank(lastName) || StringUtils.isBlank(ssnLastFour)) {
            veterans = new ArrayList<VeteranDto>();
        }
        else {
            VeteranDto veteran = new VeteranDto();
            veteran.setLastName(lastName);
            veteran.setSsnLastFour(ssnLastFour);

            veterans = veteranService.findVeterans(veteran);
        }

        return veterans;
    }
    
    
    @RequestMapping(value = "/clinics/list", method = RequestMethod.GET)
    @ResponseBody
    public List<ClinicDto> getAllClinics(@CurrentUser EscreenUser escreenUser) 
    {
    	return clinicService.getClinicDtoList();
    }
    
	@RequestMapping(value = "/veteranSearch/veteransbyclinic", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Response<List<VistaClinicAppointment>> searchVeterans(
			@RequestParam String clinicIen, @RequestParam String startDate, @RequestParam String endDate,			
			@CurrentUser EscreenUser escreenUser) {

		logger.debug("In VeteranSearchRestController searchVeterans by clinic");
		Response<List<VistaClinicAppointment>> resp = new Response<List<VistaClinicAppointment>>();
		try
		{
			List<VistaClinicAppointment> appList = batchCreateDelegate.searchVeteranByAppointments(escreenUser, clinicIen, startDate, endDate);
			
			resp.setPayload(appList);
			resp.setStatus(new ResponseStatus(Request.Succeeded));
			return resp;
		}
		catch(Exception ex)
		{
			logger.error("Error getting appointment list for clinic", ex);
			resp.setStatus(new ResponseStatus(Request.Failed, ex.getMessage()));
			return resp;
		}
		
	}
	
	@RequestMapping(value="/views/**", method=RequestMethod.GET)
	public String setupBasePages(HttpServletRequest request)
	{
		String uri = request.getRequestURI();
		logger.info("setupBasePages "+uri);
		return "/dashboard/"+uri.substring(uri.lastIndexOf("/"));
	}
	
	@RequestMapping(value="/veteranSearch/selectVeterans", method = {RequestMethod.GET, RequestMethod.POST})
	public String selectVeteransForBatchCreate(Model model,
			@ModelAttribute CreateVeteranAssessementsFormBean editVeteranAssessmentFormBean,
			BindingResult result,
			@RequestParam String[] vetIens, @RequestParam int clinicId, @CurrentUser EscreenUser user)
	{
		List<VeteranDto> vetList = batchCreateDelegate.getVeteranDetails(vetIens, user);
		CreateVeteranAssessementsFormBean formBean = editVeteranAssessmentFormBean;
		formBean.setVeterans(vetList);
		
		Map<Integer, Set<Integer>> vetSurveyMap = new HashMap<Integer, Set<Integer>>();
		
		//Now getting the list of the surveys per veteran
		for(VeteranDto v : vetList)
		{
			Map<Integer, String> autoAssignedSurveyMap = 
					createAssessmentDelegate.getPreSelectedSurveyMap(user, v.getVeteranIen());
			if(!autoAssignedSurveyMap.isEmpty())
			{
				vetSurveyMap.put(v.getVeteranId(), autoAssignedSurveyMap.keySet());
			}
		}
		
		formBean.setVetSurveyMap(vetSurveyMap);
		
		Clinic c = clinicRepo.findOne(clinicId);
		formBean.setSelectedClinicId(c.getClinicId());
		
		int programId = c.getProgram().getProgramId();
		formBean.setSelectedProgramId(programId);
		
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
		List<DropDownObject> noteTitleList = createAssessmentDelegate.getNoteTitleList(programId);
		model.addAttribute("noteTitleList", noteTitleList);

		// Get all clinician list since we have a clinic.
		List<DropDownObject> clinicianList = createAssessmentDelegate.getClinicianList(programId);
		model.addAttribute("clinicianList", clinicianList);
		
		return "redirect:/dashboard/views/editVeteransAssessment";
	}
}
