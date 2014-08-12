package gov.va.escreening.domain;

import java.io.Serializable;
import java.util.Date;

public class VeteranAssessmentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer veteranAssessmentId;
    private String clinicianLastName;
    private String clinicianFirstName;
    private String clinicName;
    private String programName;
    private String veteranSsnLastFour;
    private String veteranFirstName;
    private String veteranLastName;
    private Integer duration;
    private Integer percentComplete;
    private String assessmentStatus;
    private Date dateCreated;
    private String createdByUserLastName;
    private String createdByUserFirstName;
    private String createdByUserMiddleName;
    private String batteryName;

    public Integer getVeteranAssessmentId() {
        return veteranAssessmentId;
    }

    public void setVeteranAssessmentId(Integer veteranAssessmentId) {
        this.veteranAssessmentId = veteranAssessmentId;
    }

    public String getClinicianLastName() {
        return clinicianLastName;
    }

    public void setClinicianLastName(String clinicianLastName) {
        this.clinicianLastName = clinicianLastName;
    }

    public String getClinicianFirstName() {
        return clinicianFirstName;
    }

    public void setClinicianFirstName(String clinicianFirstName) {
        this.clinicianFirstName = clinicianFirstName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getVeteranSsnLastFour() {
        return veteranSsnLastFour;
    }

    public void setVeteranSsnLastFour(String veteranSsnLastFour) {
        this.veteranSsnLastFour = veteranSsnLastFour;
    }

    public String getVeteranFirstName() {
        return veteranFirstName;
    }

    public void setVeteranFirstName(String veteranFirstName) {
        this.veteranFirstName = veteranFirstName;
    }

    public String getVeteranLastName() {
        return veteranLastName;
    }

    public void setVeteranLastName(String veteranLastName) {
        this.veteranLastName = veteranLastName;
    }

    public Integer getDuration() {
        return duration;
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

    public String getAssessmentStatus() {
        return assessmentStatus;
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedByUserLastName() {
        return createdByUserLastName;
    }

    public void setCreatedByUserLastName(String createdByUserLastName) {
        this.createdByUserLastName = createdByUserLastName;
    }

    public String getCreatedByUserFirstName() {
        return createdByUserFirstName;
    }

    public void setCreatedByUserFirstName(String createdByUserFirstName) {
        this.createdByUserFirstName = createdByUserFirstName;
    }

    public String getCreatedByUserMiddleName() {
        return createdByUserMiddleName;
    }

    public void setCreatedByUserMiddleName(String createdByUserMiddleName) {
        this.createdByUserMiddleName = createdByUserMiddleName;
    }

    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    public VeteranAssessmentDto() {

    }

}
