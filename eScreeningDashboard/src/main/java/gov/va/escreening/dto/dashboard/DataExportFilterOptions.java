package gov.va.escreening.dto.dashboard;

import java.io.Serializable;
import java.util.Date;

public class DataExportFilterOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer exportLogId;
    private Integer exportedByUserId;
    private Integer clinicianUserId;
    private Integer createdByUserId;
    private Integer exportTypeId;
    private Date assessmentStart;
    private Date assessmentEnd;
    private Integer programId;
    private Integer veteranId;
    private String comment;
    private String filePath;

    public Integer getExportLogId() {
        return exportLogId;
    }

    public void setExportLogId(Integer exportLogId) {
        this.exportLogId = exportLogId;
    }

    public Integer getExportedByUserId() {
        return exportedByUserId;
    }

    public void setExportedByUserId(Integer exportedByUserId) {
        this.exportedByUserId = exportedByUserId;
    }

    public Integer getClinicianUserId() {
        return clinicianUserId;
    }

    public void setClinicianUserId(Integer clinicianUserId) {
        this.clinicianUserId = clinicianUserId;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Integer getExportTypeId() {
        return exportTypeId;
    }

    public void setExportTypeId(Integer exportTypeId) {
        this.exportTypeId = exportTypeId;
    }

    public Date getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(Date assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public Date getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(Date assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "DataExportFilterOptions [exportLogId=" + exportLogId + ", exportedByUserId=" + exportedByUserId
                + ", clinicianUserId=" + clinicianUserId + ", createdByUserId=" + createdByUserId + ", exportTypeId="
                + exportTypeId + ", assessmentStart=" + assessmentStart + ", assessmentEnd=" + assessmentEnd
                + ", programId=" + programId + ", veteranId=" + veteranId + ", comment=" + comment + ", filePath="
                + filePath + "]";
    }

}