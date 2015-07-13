package gov.va.escreening.vista.dto;

import java.io.Serializable;

public class VistaClinicalReminderAndCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clinicalReminderTypeName;
    private String clinicalReminderIen;
    private String printName;
    private String clinicalReminderName;
    private String clinicalReminderClass;

    /**
     * Type is either R (Reminder) or C (Category)
     * @return
     */
    public String getClinicalReminderTypeName() {
        return clinicalReminderTypeName;
    }

    public void setClinicalReminderTypeName(String clinicalReminderTypeName) {
        this.clinicalReminderTypeName = clinicalReminderTypeName;
    }

    public String getClinicalReminderIen() {
        return clinicalReminderIen;
    }

    public void setClinicalReminderIen(String clinicalReminderIen) {
        this.clinicalReminderIen = clinicalReminderIen;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getClinicalReminderName() {
        return clinicalReminderName;
    }

    public void setClinicalReminderName(String clinicalReminderName) {
        this.clinicalReminderName = clinicalReminderName;
    }

    /**
     * Class is either N (National), V (VISN), or L (Local)
     * @return
     */
    public String getClinicalReminderClass() {
        return clinicalReminderClass;
    }

    public void setClinicalReminderClass(String clinicalReminderClass) {
        this.clinicalReminderClass = clinicalReminderClass;
    }

    public VistaClinicalReminderAndCategory() {

    }

    @Override
    public String toString() {
        return "VistaClinicalReminderAndCategory [clinicalReminderTypeName=" + clinicalReminderTypeName
                + ", clinicalReminderIen=" + clinicalReminderIen + ", printName=" + printName
                + ", clinicalReminderName=" + clinicalReminderName + ", clinicalReminderClass=" + clinicalReminderClass
                + "]";
    }

}
