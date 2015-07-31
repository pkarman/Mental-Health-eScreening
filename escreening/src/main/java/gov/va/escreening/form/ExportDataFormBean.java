package gov.va.escreening.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExportDataFormBean implements Serializable {

    private static final long serialVersionUID = -1L;
    private Integer clinicianId;
    private List<Integer> programIdList;
    private String commentText;
    private Integer createdByUserId;
    private Integer exportedByUserId;
    private Integer exportFilterTypeId;
    private Integer exportLogId;
    private Integer exportTypeId;
    private Date fromAssessmentDate;
    private Boolean hasParameter;
    private Integer veteranId;
    private Date toAssessmentDate;
    private Integer programId;

    public Integer getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(Integer clinicianId) {
        this.clinicianId = clinicianId;
    }

    public List<Integer> getProgramIdList() {
        return programIdList;
    }

    public void setProgramIdList(List<Integer> programIdList) {
        this.programIdList = programIdList;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Integer getExportedByUserId() {
        return exportedByUserId;
    }

    public void setExportedByUserId(Integer exportedByUserId) {
        this.exportedByUserId = exportedByUserId;
    }

    public Integer getExportFilterTypeId() {
        return exportFilterTypeId;
    }

    public void setExportFilterTypeId(Integer exportFilterTypeId) {
        this.exportFilterTypeId = exportFilterTypeId;
    }

    public Integer getExportLogId() {
        return exportLogId;
    }

    public void setExportLogId(Integer exportLogId) {
        this.exportLogId = exportLogId;
    }

    public Integer getExportTypeId() {
        return exportTypeId;
    }

    public void setExportTypeId(Integer exportTypeId) {
        this.exportTypeId = exportTypeId;
    }

    public Date getFromAssessmentDate() {
        return fromAssessmentDate;
    }

    public void setFromAssessmentDate(Date fromAssessmentDate) {
        this.fromAssessmentDate = fromAssessmentDate;
    }

    public Boolean getHasParameter() {
        return hasParameter;
    }

    public void setHasParameter(Boolean hasParameter) {
        this.hasParameter = hasParameter;
    }

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public Date getToAssessmentDate() {
        return toAssessmentDate;
    }

    public void setToAssessmentDate(Date toAssessmentDate) {
        this.toAssessmentDate = toAssessmentDate;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    @Override
    public String toString() {
        return "ExportDataFormBean [clinicianId=" + clinicianId
                + ", programIdList=" + programIdList + ", commentText="
                + commentText + ", createdByUserId=" + createdByUserId
                + ", exportedByUserId=" + exportedByUserId
                + ", exportFilterTypeId=" + exportFilterTypeId
                + ", exportLogId=" + exportLogId + ", exportTypeId="
                + exportTypeId + ", fromAssessmentDate=" + fromAssessmentDate
                + ", hasParameter=" + hasParameter + ", veteranId=" + veteranId
                + ", toAssessmentDate=" + toAssessmentDate + ", programId="
                + programId + "]";
    }
}