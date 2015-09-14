package gov.va.escreening.vista.dto;

import gov.va.escreening.vista.extractor.OrqqpxRemindersExtractor;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class VistaVeteranClinicalReminder implements Serializable {

    private static final long serialVersionUID = 1L;
    private String clinicalReminderIen;
    private String name;

    private String dueDateString;
    private boolean dueNow;

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

    public boolean isDueNow() {
        return dueNow;
//        boolean dueNow = "DUE NOW".equals(dueDateString);
//        if (!dueNow){
//            dueNow=calculateDueNow(dueDateString);
//        }
//        return dueNow;
    }

    public void setDueNow(boolean dueNow) {
        this.dueNow = dueNow;
    }

//    private boolean calculateDueNow(String dueDateString) {
//        try {
//            Date dueDate = OrqqpxRemindersExtractor.clinicalReminderDueDateFormat.parse(dueDateString);
//            Calendar c = Calendar.getInstance();
//            c.add(Calendar.DAY_OF_YEAR, 1);
//            Date tomorrow = c.getTime();
//            return dueDate.before(tomorrow);
//        } catch (ParseException e) {
//            return false;
//        }
//    }
}
