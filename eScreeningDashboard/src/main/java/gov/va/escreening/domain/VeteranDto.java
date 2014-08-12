package gov.va.escreening.domain;

import java.io.Serializable;
import java.util.Date;

//Adapter class for entity.Veteran
public class VeteranDto implements Serializable {

    private static final long serialVersionUID = -3868672830203182302L;

    // Database fields
    private Integer veteranId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String ssnLastFour;
    private Date birthDate;
    private String birthDateString;
    private String bestTimeToCall;
    private String bestTimeToCallOther;
    private String suffix;
    private String email;
    private String phone;
    private Date dateCreated;
    private String gender;
    private String vistaLocalPid;
    private String guid;
    private String veteranIen;
    private Date dateRefreshedFromVista;
    private Boolean inpatientStatus;

    // MDWS fields
    private String homePhone;
    private String cellPhone;
    private String workPhone;

    // Context fields
    private String recordSource;

    public Integer getVeteranId() {
        return veteranId;
    }

    public void setVeteranId(Integer veteranId) {
        this.veteranId = veteranId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = ssnLastFour;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public void setBirthDateString(String birthDateString) {
        this.birthDateString = birthDateString;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(String recordSource) {
        this.recordSource = recordSource;
    }

    public String getVistaLocalPid() {
        return vistaLocalPid;
    }

    public void setVistaLocalPid(String vistaLocalPid) {
        this.vistaLocalPid = vistaLocalPid;
    }

    public String getBestTimeToCall() {
        return bestTimeToCall;
    }

    public void setBestTimeToCall(String bestTimeToCall) {
        this.bestTimeToCall = bestTimeToCall;
    }

    public String getBestTimeToCallOther() {
        return bestTimeToCallOther;
    }

    public void setBestTimeToCallOther(String bestTimeToCallOther) {
        this.bestTimeToCallOther = bestTimeToCallOther;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getVeteranIen() {
        return veteranIen;
    }

    public void setVeteranIen(String veteranIen) {
        this.veteranIen = veteranIen;
    }

    public Boolean getInpatientStatus() {
        return inpatientStatus;
    }

    public void setInpatientStatus(Boolean inpatientStatus) {
        this.inpatientStatus = inpatientStatus;
    }

    public Date getDateRefreshedFromVista() {
        return dateRefreshedFromVista;
    }

    public void setDateRefreshedFromVista(Date dateRefreshedFromVista) {
        this.dateRefreshedFromVista = dateRefreshedFromVista;
    }

    public VeteranDto() {

    }

    @Override
    public String toString() {
        return "VeteranDto [veteranId=" + veteranId + ", firstName=" + firstName + ", middleName=" + middleName
                + ", lastName=" + lastName + ", ssnLastFour=" + ssnLastFour + ", birthDate=" + birthDate
                + ", bestTimeToCall=" + bestTimeToCall + ", bestTimeToCallOther=" + bestTimeToCallOther + ", suffix="
                + suffix + ", email=" + email + ", phone=" + phone + ", dateCreated=" + dateCreated + ", gender="
                + gender + ", vistaLocalPid=" + vistaLocalPid + ", guid=" + guid + ", veteranIen=" + veteranIen
                + ", dateRefreshedFromVista=" + dateRefreshedFromVista + ", homePhone=" + homePhone + ", cellPhone="
                + cellPhone + ", workPhone=" + workPhone + ", recordSource=" + recordSource + ", inpatientStatus="
                + inpatientStatus + "]";
    }

}
