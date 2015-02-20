package gov.va.escreening.delegate;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.domain.VeteranWithClinicalReminderFlag;
import gov.va.escreening.dto.BatchBatteryCreateResult;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.VeteranRepository;
import gov.va.escreening.repository.VistaRepository;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VeteranService;
import gov.va.escreening.service.VeteranServiceImpl;
import gov.va.escreening.vista.dto.VistaClinicAppointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.map.HashedMap;
import org.hibernate.validator.util.privilegedactions.GetClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchCreateDelegateImpl implements BatchBatteryCreateDelegate {
	@Autowired
	VistaRepository vistaRepo;

	@Autowired
	ClinicRepository clinicRepo;

	@Autowired
	private VistaRepository vistaService;

	@Autowired
	private VeteranService veteranService;
	
	@Autowired
	private VeteranRepository veteranRepo;
	
	@Autowired
	private CreateAssessmentDelegate createAssessmentDelegate;
	
	private static Logger logger = LoggerFactory.getLogger(BatchCreateDelegateImpl.class);

	@Override
	public List<VistaClinicAppointment> searchVeteranByAppointments(
			EscreenUser user, String clinicIen, String startdate, String enddate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date start;
		try {
			start = format.parse(startdate);
			Date end = format.parse(enddate);

			return searchVeteranByAppointments(user, clinicIen, start, end);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}

	}

	@Override
	public List<VistaClinicAppointment> searchVeteranByAppointments(
			EscreenUser user, String clinicIen, Date start, Date end) {
		
		try
		{
		List<VistaClinicAppointment> appList = vistaRepo.getAppointmentsForClinic(user.getVistaDivision(),
				user.getVistaVpid(), user.getVistaDuz(), "ESCREEN",
				clinicIen, start, end);
		
		Map<String, VistaClinicAppointment> appMap = new HashedMap<String, VistaClinicAppointment>();
		
		//Now, go through the veterans and only return the closest appointment to the startDate???
		for(VistaClinicAppointment app : appList)
		{	
			String vetIen = app.getVeteranIen();
			if(appMap.containsKey(vetIen))
			{
				Date appTime = app.getAppointmentDate();
				Date current = Calendar.getInstance().getTime();
				if(appTime.after(current))
				{
					if(appTime.before(appMap.get(vetIen).getAppointmentDate()))
					{
						appMap.put(vetIen, app);
					}
				}
			}
			else
			{
				appMap.put(vetIen, app);
			}
		}
		
		return new ArrayList<VistaClinicAppointment>(appMap.values());
		}catch(Exception ex)
		{
			logger.error("Error getting veterans by appointments", ex);
			return new ArrayList<VistaClinicAppointment>();
		}
	}

	/**
	 * Import a list of veterans
	 * @param iens
	 * @param escreenUser
	 * @return
	 */
	private List<VeteranDto> importVeterans(List<String> iens,
			EscreenUser escreenUser) {
		List<VeteranDto> vList = new ArrayList<VeteranDto>();

		for (String ien : iens) {
			VeteranDto vistaVeteranDto = vistaService.getVeteran(
					escreenUser.getVistaDivision(), escreenUser.getVistaVpid(),
					escreenUser.getVistaDuz(), "", ien);

			Integer veteranId = veteranService
					.importVistaVeteranRecord(vistaVeteranDto);

			vistaVeteranDto.setVeteranId(veteranId);

			vList.add(vistaVeteranDto);
		}
		return vList;
	}

	@Override
	public List<VeteranWithClinicalReminderFlag> getVeteranDetails(String[] veteranIens, EscreenUser user,
			List<VistaClinicAppointment> appList) {
		// TODO Auto-generated method stub
		List<String> vetInDB = new ArrayList<String>();
		List<String> vetToImport = new ArrayList<String>();
		List<Veteran> vetList = veteranRepo.getVeteranByIens(veteranIens);
		
		for(Veteran v : vetList)
		{
			vetInDB.add(v.getVeteranIen());
		}
		
		for(String s: veteranIens)
		{
			if(!vetInDB.contains(s))
			{
				vetToImport.add(s);
			}
		}
		
		List<VeteranDto> imported = importVeterans(vetToImport, user);
		
		List<VeteranWithClinicalReminderFlag> result = new ArrayList<VeteranWithClinicalReminderFlag>(imported.size());
		for(Veteran v:vetList)
		{
			result.add(new VeteranWithClinicalReminderFlag(VeteranServiceImpl.convertVeteranToVeteranDto(v)));
		}
		
		for(VeteranDto dto : imported)
		{
			result.add(new VeteranWithClinicalReminderFlag(dto));
		}
		
		for(VeteranWithClinicalReminderFlag v : result)
		{
			for(VistaClinicAppointment appt : appList)
			{
				if(appt.getVeteranIen().equals(v.getVeteranIen()))
				{
					v.setApptDate(appt.getApptDate());
					v.setApptTime(appt.getApptTime());
					break;
				}
			}
		}
		return result;
	}

	@Override
	public List<BatchBatteryCreateResult> batchCreate(List<VeteranWithClinicalReminderFlag> vets, int programId, int clinicId, 
			int clinicianId, int noteTitleId, int batteryId, Map<Integer, Set<Integer>> surveyMap, List<Integer> selectedSurvey,
			EscreenUser escreenUser)
	{
		List<BatchBatteryCreateResult> resultList = new ArrayList<BatchBatteryCreateResult>(vets.size());
		for(VeteranDto vet : vets)
		{
			Set<Integer> surveyList = surveyMap.get(vet.getVeteranId());
			if(surveyList == null)
			{
				surveyList = new HashSet<Integer>();
			}
			surveyList.addAll(selectedSurvey);
			// Add
			createAssessmentDelegate.createVeteranAssessment(escreenUser, vet.getVeteranId(), programId, clinicId, 
					clinicianId, noteTitleId, batteryId, new ArrayList<Integer>(surveyList));

			BatchBatteryCreateResult result = new BatchBatteryCreateResult();
			result.setVet(vet);
			result.setSucceed(true);
			resultList.add(result);
		}
			
		return resultList;
		
	}
}
