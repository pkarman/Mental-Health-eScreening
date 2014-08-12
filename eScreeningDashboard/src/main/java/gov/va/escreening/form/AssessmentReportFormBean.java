package gov.va.escreening.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

public class AssessmentReportFormBean implements Serializable {

    private static final long serialVersionUID = -1L;

    @Size(max = 32, message = "Assessment GUID must be less than 32 characters")
    private String veteranAssessmentGuid;
    private Integer veteranAssessmentId;
    private Integer veteranId;
    private Integer programId;
    private Integer clinicianId;
    private Integer createdByUserId;
    private Date fromAssessmentDate;
    private Date toAssessmentDate;
    private Boolean showDeletedClinicians;
    private Boolean showDeletedAssessmentCreators;
    private List<Integer> programIdList;
    private Boolean hasParameter;

    public String getVeteranAssessmentGuid() {
        return veteranAssessmentGuid;
    }

    public void setVeteranAssessmentGuid(String veteranAssessmentGuid) {
        this.veteranAssessmentGuid = veteranAssessmentGuid;
    }

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

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public Integer getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(Integer clinicianId) {
        this.clinicianId = clinicianId;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Date getFromAssessmentDate() {
        return fromAssessmentDate;
    }

    public void setFromAssessmentDate(Date fromAssessmentDate) {
        this.fromAssessmentDate = fromAssessmentDate;
    }

    public Date getToAssessmentDate() {
        return toAssessmentDate;
    }

    public void setToAssessmentDate(Date toAssessmentDate) {
        this.toAssessmentDate = toAssessmentDate;
    }

    public Boolean getShowDeletedClinicians() {
        return showDeletedClinicians;
    }

    public void setShowDeletedClinicians(Boolean showDeletedClinicians) {
        this.showDeletedClinicians = showDeletedClinicians;
    }

    public Boolean getShowDeletedAssessmentCreators() {
        return showDeletedAssessmentCreators;
    }

    public void setShowDeletedAssessmentCreators(Boolean showDeletedAssessmentCreators) {
        this.showDeletedAssessmentCreators = showDeletedAssessmentCreators;
    }

    public List<Integer> getProgramIdList() {
        return programIdList;
    }

    public void setProgramIdList(List<Integer> programIdList) {
        this.programIdList = programIdList;
    }

    public Boolean getHasParameter() {
        return hasParameter;
    }

    public void setHasParameter(Boolean hasParameter) {
        this.hasParameter = hasParameter;
    }

    public AssessmentReportFormBean() {

    }

    @Override
    public String toString() {
        return "AssessmentReportFormBean [veteranAssessmentGuid=" + veteranAssessmentGuid + ", veteranAssessmentId="
                + veteranAssessmentId + ", veteranId=" + veteranId + ", programId=" + programId + ", clinicianId="
                + clinicianId + ", createdByUserId=" + createdByUserId + ", fromAssessmentDate=" + fromAssessmentDate
                + ", toAssessmentDate=" + toAssessmentDate + ", showDeletedClinicians=" + showDeletedClinicians
                + ", showDeletedAssessmentCreators=" + showDeletedAssessmentCreators + ", programIdList="
                + programIdList + ", hasParameter=" + hasParameter + "]";
    }

}
