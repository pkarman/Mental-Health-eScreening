package gov.va.escreening.dto.dashboard;

import freemarker.template.SimpleDate;
import gov.va.escreening.dto.AlertDto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AssessmentSearchResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clinicianName = "";
    private String createdBy = "";
    private String createDate = "";
    private String apptDateAndTime = "";
    private String assessmentDate = "";
    private String completeDate = "";
    private Integer veteranId;
    private String veteranName = "";
    private String assessmentStatusName = "";
    private Integer veteranAssessmentId;
    private String programName;
    private String clinicName;
    private String ssnLastFour;
    private Integer duration;
    private Integer percentComplete;
    private List<AlertDto> alerts;

    public AssessmentSearchResult() {

    }

    public String getApptDateAndTime() {
        return apptDateAndTime == null ? "Not Applicable" : apptDateAndTime;
    }

    public void setApptDateAndTime(String apptDateAndTime) {
        this.apptDateAndTime = apptDateAndTime;
    }

    public String getClinicianName() {
        return clinicianName;
    }

    public void setClinicianName(String clinicianName) {
        this.clinicianName = clinicianName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public String getVeteranName() {
        return veteranName;
    }

    public void setVeteranName(String veteranName) {
        this.veteranName = veteranName;
    }

    public String getAssessmentStatusName() {
        return assessmentStatusName;
    }

    public void setAssessmentStatusName(String assessmentStatusName) {
        this.assessmentStatusName = assessmentStatusName;
    }

    public Integer getVeteranAssessmentId() {
        return veteranAssessmentId;
    }

    public void setVeteranAssessmentId(Integer veteranAssessmentId) {
        this.veteranAssessmentId = veteranAssessmentId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
    }

    public String getDuration() {
        int min = (int) duration / 60;
        int sec = (int) duration % 60;

        String minSec = String.format("%02d min %02d sec ", min, sec);
        return minSec;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(Integer percentComplete) {
        this.percentComplete = percentComplete;
    }

    public List<AlertDto> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<AlertDto> alerts) {
        this.alerts = alerts;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    @Override
    public String toString() {
        return "AssessmentSearchResult [clinicianName=" + clinicianName
                + ", createdBy=" + createdBy + ", assessmentDate=" + assessmentDate + ", veteranId=" + veteranId
                + ", veteranName=" + veteranName + ", assessmentStatusName=" + assessmentStatusName
                + ", veteranAssessmentId=" + veteranAssessmentId + "]";
    }

}
