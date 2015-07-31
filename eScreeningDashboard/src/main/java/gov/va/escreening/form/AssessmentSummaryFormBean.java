package gov.va.escreening.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class AssessmentSummaryFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Clinic is required")
    private Integer selectedClinicId;

    @NotNull(message = "Clinician is required")
    private Integer selectedClinicianId;

    @NotNull(message = "Note Title is required")
    private Integer selectedNoteTitleId;

    @NotNull(message = "Status is required")
    private Integer selectedAssessmentStatusId;

    @NotNull(message = "Veteran Assessment required")
    private Integer selectedVeteranAssessmentId;

    private Integer veteranId;
    private String ssnLastFour;
    private String lastName;

    public AssessmentSummaryFormBean() {
        // Default constructor.
    }

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public Integer getSelectedClinicId() {
        return selectedClinicId;
    }

    public void setSelectedClinicId(Integer selectedClinicId) {
        this.selectedClinicId = selectedClinicId;
    }

    public Integer getSelectedClinicianId() {
        return selectedClinicianId;
    }

    public void setSelectedClinicianId(Integer selectedClinicianId) {
        this.selectedClinicianId = selectedClinicianId;
    }

    public Integer getSelectedNoteTitleId() {
        return selectedNoteTitleId;
    }

    public void setSelectedNoteTitleId(Integer selectedNoteTitleId) {
        this.selectedNoteTitleId = selectedNoteTitleId;
    }

    public Integer getSelectedAssessmentStatusId() {
        return selectedAssessmentStatusId;
    }

    public void setSelectedAssessmentStatusId(Integer selectedAssessmentStatusId) {
        this.selectedAssessmentStatusId = selectedAssessmentStatusId;
    }

    public Integer getSelectedVeteranAssessmentId() {
        return selectedVeteranAssessmentId;
    }

    public void setSelectedVeteranAssessmentId(Integer selectedVeteranAssessmentId) {
        this.selectedVeteranAssessmentId = selectedVeteranAssessmentId;
    }
}
