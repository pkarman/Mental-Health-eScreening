package gov.va.escreening.delegate;

import gov.va.escreening.entity.Clinic;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.VistaRepository;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.vista.dto.VistaClinicAppointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class BatchCreateDelegateImpl implements BatchBatteryCreateDelegate
{
	@Autowired
	VistaRepository vistaRepo;
	
	@Autowired
	ClinicRepository clinicRepo;

	@Override
	public List<VistaClinicAppointment> searchVeteranByAppointments(EscreenUser user,
			int clinicID, String startdate, String enddate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		Date start;
		try {
			start = format.parse(startdate);
			Date end = format.parse(enddate);
			Clinic c = clinicRepo.findOne(clinicID);
			return vistaRepo.getAppointmentsForClinic(user.getVistaDivision(), 
					user.getVistaVpid(), user.getVistaDuz(), "ESCREEN",
					c.getVistaIen(), start, end);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		
	}

}
