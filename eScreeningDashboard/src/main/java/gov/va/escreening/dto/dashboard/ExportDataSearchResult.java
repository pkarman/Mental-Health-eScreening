package gov.va.escreening.dto.dashboard;

import java.io.Serializable;

public class ExportDataSearchResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer exportLogId;
    private String exportedDate;
    private String exportedBy;
    private String assignedClinician;
    private String createdByUser;
    private String exportType;
    private String assessmentStartDate;
    private String assessmentEndDate;
    private String programName;
    private Integer veteranId;
    private String comment;

    public Integer getExportLogId() {
        return exportLogId;
    }

    public void setExportLogId(Integer exportLogId) {
        this.exportLogId = exportLogId;
    }

    public String getExportedOn() {
        return exportedDate;
    }

    public void setExportedOn(String exportedOn) {
        this.exportedDate = exportedOn;
    }

    public String getExportedBy() {
        return exportedBy;
    }

    public void setExportedBy(String exportedBy) {
        this.exportedBy = exportedBy;
    }

    public String getAssignedClinician() {
        return assignedClinician;
    }

    public void setAssignedClinician(String assignedClinician) {
        this.assignedClinician = assignedClinician;
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
        this.createdByUser = createdByUser;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getAssessmentStartDate() {
        return assessmentStartDate;
    }

    public void setAssessmentStartDate(String assessmentStartDate) {
        this.assessmentStartDate = assessmentStartDate;
    }

    public String getAssessmentEndDate() {
        return assessmentEndDate;
    }

    public void setAssessmentEndDate(String assessmentEndDate) {
        this.assessmentEndDate = assessmentEndDate;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ExportDataSearchResult() {
    }

    public ExportDataSearchResult(Integer exportLogId, String exportedDate,
            String exportedBy, String assignedClinician,
            String createdByUser, String exportType,
            String assessmentStartDate, String assessmentEndDate,
            String programName, Integer veteranId, String comment) {

        this.exportLogId = exportLogId;
        this.exportedDate = exportedDate;
        this.exportedBy = exportedBy;
        this.assignedClinician = assignedClinician;
        this.createdByUser = createdByUser;
        this.exportType = exportType;
        this.assessmentStartDate = assessmentStartDate;
        this.assessmentEndDate = assessmentEndDate;
        this.programName = programName;
        this.veteranId = veteranId;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ExportDataSearchResult [exportLogId=" + exportLogId + ", exportedDate=" + exportedDate
                + ", exportedBy=" + exportedBy + ", assignedClinician=" + assignedClinician + ", createdByUser="
                + createdByUser + ", exportType=" + exportType + ", assessmentStartDate=" + assessmentStartDate
                + ", assessmentEndDate=" + assessmentEndDate + ", programName=" + programName + ", veteranId="
                + veteranId + ", comment=" + comment + "]";
    }

}