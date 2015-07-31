package gov.va.escreening.vista.dto;

import java.util.Date;

/**
 * Created by pouncilt on 5/29/14.
 */
public class PatientDemographics {
    private String fullName = null;
    private String sex = null;
    private String dateOfBirth = null;
    private String socialSecurityNumber = null;
    private Long locationIEN = null;
    private String locationName = null;
    private String patientRoomAndBedCode = null;
    private String cwadCode = null;
    private Boolean patientIsSensitive = null;
    private Date patientAdmittedDate = null;
    private Boolean conv = null;
    private Boolean serviceConnected = null;
    private Integer serviceConnectedPercentage = null;
    private Long integrationControlNumber = null;
    private Integer age = null;
    private Integer treatingSpeciality = null;

    public PatientDemographics(String fullName, String sex, String dateOfBirth, String socialSecurityNumber,
                               Long locationIEN, String locationName, String patientRoomAndBedCode, String cwadCode,
                               Boolean patientIsSensitive, Date patientAdmittedDate, Boolean conv,
                               Boolean serviceConnected, Integer serviceConnectedPercentage,
                               Long integrationControlNumber , Integer age, Integer treatingSpeciality) {
        this.fullName = fullName;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.socialSecurityNumber = socialSecurityNumber;
        this.locationIEN = locationIEN;
        this.locationName = locationName;
        this.patientRoomAndBedCode = patientRoomAndBedCode;
        this.cwadCode = cwadCode;
        this.patientIsSensitive = patientIsSensitive;
        this.patientAdmittedDate = patientAdmittedDate;
        this.conv = conv;
        this.serviceConnected = serviceConnected;
        this.serviceConnectedPercentage = serviceConnectedPercentage;
        this.integrationControlNumber = integrationControlNumber;
        this.age = age;
        this.treatingSpeciality = treatingSpeciality;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSex() {
        return sex;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public Long getLocationIEN() {
        return locationIEN;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getPatientRoomAndBedCode() {
        return patientRoomAndBedCode;
    }

    public String getCwadCode() {
        return cwadCode;
    }

    public Boolean getPatientIsSensitive() {
        return patientIsSensitive;
    }

    public Date getPatientAdmittedDate() {
        return patientAdmittedDate;
    }

    public Boolean getConv() {
        return conv;
    }

    public Boolean getServiceConnected() {
        return serviceConnected;
    }

    public Integer getServiceConnectedPercentage() {
        return serviceConnectedPercentage;
    }

    public Long getIntegrationControlNumber() {
        return integrationControlNumber;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getTreatingSpeciality() {
        return treatingSpeciality;
    }

    public Boolean getInpatientStatus() {
        return (locationName == null) ? false : true;
    }

    @Override
    public String toString() {
        return "PatientDemographics{" +
                "fullName='" + fullName + '\'' +
                ", sex='" + sex + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", locationIEN=" + locationIEN +
                ", locationName='" + locationName + '\'' +
                ", patientRoomAndBedCode='" + patientRoomAndBedCode + '\'' +
                ", cwadCode='" + cwadCode + '\'' +
                ", patientIsSensitive=" + patientIsSensitive +
                ", patientAdmittedDate=" + patientAdmittedDate +
                ", conv=" + conv +
                ", serviceConnected=" + serviceConnected +
                ", serviceConnectedPercentage=" + serviceConnectedPercentage +
                ", integrationControlNumber=" + integrationControlNumber +
                ", age=" + age +
                ", treatingSpeciality=" + treatingSpeciality +
                ", inpatientStatus=" + this.getInpatientStatus() +
                '}';
    }
}
