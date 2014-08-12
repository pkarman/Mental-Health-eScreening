package gov.va.escreening.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

public class EditVeteranAssessmentFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer veteranAssessmentId;
    private Integer veteranId;
    @NotNull(message = "Program is required")
    private Integer selectedProgramId;
    @NotNull(message = "Clinic is required")
    private Integer selectedClinicId;
    @NotNull(message = "Clinican Is required")
    private Integer selectedClinicianId;
    @NotNull(message = "Note Title is required")
    private Integer selectedNoteTitleId;
    @NotNull(message = "Battery is required")
    private Integer selectedBatteryId;
    private List<Integer> selectedSurveyIdList = new ArrayList<Integer>();

    public Integer getVeteranAssessmentId() {
        return veteranAssessmentId;
    }

    public void setVeteranAssessmentId(Integer veteranAssessmentId) {
        this.veteranAssessmentId = veteranAssessmentId;
    }

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public Integer getSelectedProgramId() {
        return selectedProgramId;
    }

    public void setSelectedProgramId(Integer selectedProgramId) {
        this.selectedProgramId = selectedProgramId;
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

    public Integer getSelectedBatteryId() {
        return selectedBatteryId;
    }

    public void setSelectedBatteryId(Integer selectedBatteryId) {
        this.selectedBatteryId = selectedBatteryId;
    }

    public List<Integer> getSelectedSurveyIdList() {
        return selectedSurveyIdList;
    }

    public void setSelectedSurveyIdList(List<Integer> selectedSurveyIdList) {
        this.selectedSurveyIdList = selectedSurveyIdList;
    }

    public EditVeteranAssessmentFormBean() {

    }

    @Override
    public String toString() {
        return "EditVeteranAssessmentFormBean [veteranAssessmentId=" + veteranAssessmentId + ", veteranId=" + veteranId
                + ", selectedProgramId=" + selectedProgramId + ", selectedClinicId=" + selectedClinicId
                + ", selectedClinicianId=" + selectedClinicianId + ", selectedNoteTitleId="
                + selectedNoteTitleId + ", selectedSurveyIdList=" + selectedSurveyIdList + "]";
    }

}
