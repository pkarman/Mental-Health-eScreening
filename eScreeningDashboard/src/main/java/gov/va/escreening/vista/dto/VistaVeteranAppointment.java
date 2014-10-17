package gov.va.escreening.vista.dto;

import java.io.Serializable;
import java.util.Date;

public class VistaVeteranAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clinicName;
    private Date appointmentDate;
    private String status;


    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public VistaVeteranAppointment() {

    }

    @Override
    public String toString() {
        return "VistaVeteranAppointment [clinicName=" + clinicName + ", appointmentDate=" + appointmentDate + "status="+status+"]";
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
