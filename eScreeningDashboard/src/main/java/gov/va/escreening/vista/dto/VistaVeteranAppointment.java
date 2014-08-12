package gov.va.escreening.vista.dto;

import java.io.Serializable;
import java.util.Date;

public class VistaVeteranAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clinicIen;
    private String clinicName;
    private Date appointmentDate;

    public String getClinicIen() {
        return clinicIen;
    }

    public void setClinicIen(String clinicIen) {
        this.clinicIen = clinicIen;
    }

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
        return "VistaVeteranAppointment [clinicIen=" + clinicIen + ", clinicName=" + clinicName + ", appointmentDate="
                + appointmentDate + "]";
    }

}
