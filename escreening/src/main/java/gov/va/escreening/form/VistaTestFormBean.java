package gov.va.escreening.form;

import java.io.Serializable;

public class VistaTestFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lastFourSsn;
    private String firstName;
    private String middleName;
    private String lastName;
    private String searchString;
    private String ien;
    private String selectedRpcId;
    private String clinicalReminderIen;
    private String dialogElementIen;

    public String getLastFourSsn() {
        return lastFourSsn;
    }

    public void setLastFourSsn(String lastFourSsn) {
        this.lastFourSsn = lastFourSsn;
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

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getIen() {
        return ien;
    }

    public void setIen(String ien) {
        this.ien = ien;
    }

    public String getSelectedRpcId() {
        return selectedRpcId;
    }

    public void setSelectedRpcId(String selectedRpcId) {
        this.selectedRpcId = selectedRpcId;
    }

    public String getClinicalReminderIen() {
        return clinicalReminderIen;
    }

    public void setClinicalReminderIen(String clinicalReminderIen) {
        this.clinicalReminderIen = clinicalReminderIen;
    }

    public String getDialogElementIen() {
        return dialogElementIen;
    }

    public void setDialogElementIen(String dialogElementIen) {
        this.dialogElementIen = dialogElementIen;
    }

    public VistaTestFormBean() {

    }

}
