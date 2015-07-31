package gov.va.escreening.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String loginId;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private String emailAddress2;
    private String phoneNumber;
    private String phoneNumber2;
    private Integer cprsVerified;
    private Date dateCreated;
    private Integer roleId;
    private List<Integer> clinicIdList;
    private Integer userStatusId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress2() {
        return emailAddress2;
    }

    public void setEmailAddress2(String emailAddress2) {
        this.emailAddress2 = emailAddress2;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public Integer getCprsVerified() {
        return cprsVerified;
    }

    public void setCprsVerified(Integer cprsVerified) {
        this.cprsVerified = cprsVerified;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getClinicIdList() {
        return clinicIdList;
    }

    public void setClinicIdList(List<Integer> clinicIdList) {
        this.clinicIdList = clinicIdList;
    }

    public Integer getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Integer userStatusId) {
        this.userStatusId = userStatusId;
    }

    public UserDto() {

    }
}
