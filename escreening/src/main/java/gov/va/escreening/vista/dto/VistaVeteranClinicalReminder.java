package gov.va.escreening.vista.dto;

import java.io.Serializable;

public class VistaVeteranClinicalReminder implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clinicalReminderIen;
    private String name;
    private String dueDateString;

    public String getClinicalReminderIen() {
        return clinicalReminderIen;
    }

    public void setClinicalReminderIen(String clinicalReminderIen) {
        this.clinicalReminderIen = clinicalReminderIen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDueDateString() {
        return dueDateString;
    }

    public void setDueDateString(String dueDateString) {
        this.dueDateString = dueDateString;
    }

    public VistaVeteranClinicalReminder() {

    }

    public VistaVeteranClinicalReminder(String clinicalReminderIen, String name, String dueDateString) {
        this.clinicalReminderIen = clinicalReminderIen;
        this.name = name;
        this.dueDateString = dueDateString;
    }

    @Override
    public String toString() {
        return "VistaVeteranClinicalReminder [clinicalReminderIen=" + clinicalReminderIen + ", name=" + name
                + ", dueDateString=" + dueDateString + "]";
    }

}
