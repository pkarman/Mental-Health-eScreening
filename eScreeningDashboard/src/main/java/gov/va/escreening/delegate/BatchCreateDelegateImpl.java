package gov.va.escreening.delegate;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.repository.VistaRepository;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.VeteranService;
import gov.va.escreening.vista.dto.VistaClinicAppointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	@Override
	public List<VistaClinicAppointment> searchVeteranByAppointments(
			EscreenUser user, String clinicIen, String startdate, String enddate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date start;
		try {
			start = format.parse(startdate);
			Date end = format.parse(enddate);

			return vistaRepo.getAppointmentsForClinic(user.getVistaDivision(),
					user.getVistaVpid(), user.getVistaDuz(), "ESCREEN",
					clinicIen, start, end);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
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

}
