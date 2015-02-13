package gov.va.escreening.delegate;

import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.vista.dto.VistaClinicAppointment;

import java.util.Date;
import java.util.List;

public interface BatchBatteryCreateDelegate {
	List<VistaClinicAppointment> searchVeteranByAppointments(EscreenUser user, String clinicIen, 
			String startdate, String enddate);
	
	/**
	 * Returns veteranDTOs from the local DB, if veteran does not exist, import first.
	 * @param veteranIens
	 * @return
	 */
	List<VeteranDto> getVeteranDetails(String[] veteranIens, EscreenUser user);

	public abstract List<VistaClinicAppointment> searchVeteranByAppointments(EscreenUser user,
			String clinicIen, Date start, Date end);

}
