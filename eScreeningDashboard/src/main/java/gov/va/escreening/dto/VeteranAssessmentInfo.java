package gov.va.escreening.dto;

import java.io.Serializable;
import java.util.Date;

public class VeteranAssessmentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer veteranAssessmentId;
    private Integer veteranId;
    private String veteranIen;
    private String veteranFullName;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDate;
    private String ssnLastFour;
    private String phone;
    private String officePhone;
    private String cellPhone;
    private String email;
    private Integer batteryId;
    private String batteryName;
    private Integer createdByUserId;
    private String createdByUserFullName;
    private Date dateCreated;
    private Date dateCompleted;
    private Integer programId;
    private String programName;
    private Integer clinicId;
    private String clinicName;
    private Integer noteTitleId;
    private String noteTitleName;
    private Integer clinicianId;
    private String clinicianFullName;
    private Integer assessmentStatusId;
    private String assessmentStatusName;

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

    public String getVeteranIen() {
        return veteranIen;
    }

    public void setVeteranIen(String veteranIen) {
        this.veteranIen = veteranIen;
    }

    public String getVeteranFullName() {
        return veteranFullName;
    }

    public void setVeteranFullName(String veteranFullName) {
        this.veteranFullName = veteranFullName;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(Integer batteryId) {
        this.batteryId = batteryId;
    }

    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    public Integer getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Integer createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getCreatedByUserFullName() {
        return createdByUserFullName;
    }

    public void setCreatedByUserFullName(String createdByUserFullName) {
        this.createdByUserFullName = createdByUserFullName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public Integer getNoteTitleId() {
        return noteTitleId;
    }

    public void setNoteTitleId(Integer noteTitleId) {
        this.noteTitleId = noteTitleId;
    }

    public String getNoteTitleName() {
        return noteTitleName;
    }

    public void setNoteTitleName(String noteTitleName) {
        this.noteTitleName = noteTitleName;
    }

    public Integer getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(Integer clinicianId) {
        this.clinicianId = clinicianId;
    }

    public String getClinicianFullName() {
        return clinicianFullName;
    }

    public void setClinicianFullName(String clinicianFullName) {
        this.clinicianFullName = clinicianFullName;
    }

    public Integer getAssessmentStatusId() {
        return assessmentStatusId;
    }

    public void setAssessmentStatusId(Integer assessmentStatusId) {
        this.assessmentStatusId = assessmentStatusId;
    }

    public String getAssessmentStatusName() {
        return assessmentStatusName;
    }

    public void setAssessmentStatusName(String assessmentStatusName) {
        this.assessmentStatusName = assessmentStatusName;
    }

    public VeteranAssessmentInfo() {
        // Default constructor
    }
}
