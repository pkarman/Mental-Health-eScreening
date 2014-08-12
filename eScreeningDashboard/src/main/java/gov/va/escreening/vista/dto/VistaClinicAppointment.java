package gov.va.escreening.vista.dto;

import java.io.Serializable;
import java.util.Date;

public class VistaClinicAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String veteranIen;
    private String firstName;
    private String middleName;
    private String lastName;
    private String clinicIen;
    private Date appointmentDate;
    private String visitStatusName;

    public String getVeteranIen() {
        return veteranIen;
    }

    public void setVeteranIen(String veteranIen) {
        this.veteranIen = veteranIen;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getClinicIen() {
        return clinicIen;
    }

    public void setClinicIen(String clinicIen) {
        this.clinicIen = clinicIen;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getVisitStatusName() {
        return visitStatusName;
    }

    public void setVisitStatusName(String visitStatusName) {
        this.visitStatusName = visitStatusName;
    }

    public VistaClinicAppointment() {

    }

    @Override
    public String toString() {
        return "VistaClinicAppointment [veteranIen=" + veteranIen
                + ", firstName=" + firstName + ", middleName=" + middleName
                + ", lastName=" + lastName + ", clinicIen=" + clinicIen
                + ", appointmentDate=" + appointmentDate + ", visitStatusName="
                + visitStatusName + "]";
    }

}
