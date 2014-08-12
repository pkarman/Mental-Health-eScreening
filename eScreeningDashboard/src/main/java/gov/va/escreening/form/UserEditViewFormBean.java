package gov.va.escreening.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserEditViewFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    @Size(max = 30, message = "Login ID must be 30 characters or less")
    @NotEmpty(message = "Login ID is required")
    private String loginId;
    @NotNull(message = "Role is required")
    private Integer selectedRoleId;
    @NotNull(message = "Status is required")
    private Integer selectedUserStatusId;
    @Size(min = 8, max = 50, message = "Password must be at least 8 characters and less than 50 characters")
    @Pattern(regexp = "^.*(?=.{8,})(?!.*\\s)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%&?]).*$", message = "Password must contain at least one digit, one uppercase letter, and one lowercase letter, one special character (@#%$^ etc.), and be at least 8 characters.")
    private String password;
    private String passwordConfirmed;

    @Size(max = 30, message = "First Name must be 30 characters or less")
    @NotEmpty(message = "First Name is required")
    private String firstName;
    @Size(max = 30, message = "Middle Name must be 30 characters or less")
    private String middleName;

    @Size(max = 30, message = "Last Name must be 30 characters or less")
    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @Size(max = 50, message = "Email Address must be 50 characters or less")
    @NotEmpty(message = "Email Address is required")
    @Email(message = "Email Address must be a valid email address format")
    private String emailAddress;
    @Size(max = 50, message = "Alternate Email Address must be 50 characters or less")
    @Email(message = "Alternate Email Address must be a valid email address format")
    private String emailAddress2;

    @Size(max = 20, message = "Phone Number must be 20 characters or less")
    @NotEmpty(message = "Phone Number is required")
    private String phoneNumber;
    @Size(max = 10, message = "Phone Number Extension must be 10 characters or less")
    private String phoneNumberExt;
    @Size(max = 20, message = "Alternate Phone Number must be 20 characters or less")
    private String phoneNumber2;
    @Size(max = 10, message = "Alternate Phone Number Extensin must be 10 characters or less")
    private String phoneNumber2Ext;

    private String vistaDuz;
    private String vistaVpid;
    private String vistaDivision;
    private Boolean cprsVerified;
    private Date datePasswordChanged;
    private Date lastLoginDate;
    private Date dateCreated;
    private Boolean isCreateMode;
    private List<Integer> selectedProgramIdList = new ArrayList<Integer>();

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

    public Integer getSelectedRoleId() {
        return selectedRoleId;
    }

    public void setSelectedRoleId(Integer selectedRoleId) {
        this.selectedRoleId = selectedRoleId;
    }

    public Integer getSelectedUserStatusId() {
        return selectedUserStatusId;
    }

    public void setSelectedUserStatusId(Integer selectedUserStatusId) {
        this.selectedUserStatusId = selectedUserStatusId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(String passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
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

    public String getPhoneNumberExt() {
        return phoneNumberExt;
    }

    public void setPhoneNumberExt(String phoneNumberExt) {
        this.phoneNumberExt = phoneNumberExt;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber2Ext() {
        return phoneNumber2Ext;
    }

    public void setPhoneNumber2Ext(String phoneNumber2Ext) {
        this.phoneNumber2Ext = phoneNumber2Ext;
    }

    public String getVistaDuz() {
        return vistaDuz;
    }

    public void setVistaDuz(String vistaDuz) {
        this.vistaDuz = vistaDuz;
    }

    public String getVistaVpid() {
        return vistaVpid;
    }

    public void setVistaVpid(String vistaVpid) {
        this.vistaVpid = vistaVpid;
    }

    public String getVistaDivision() {
        return vistaDivision;
    }

    public void setVistaDivision(String vistaDivision) {
        this.vistaDivision = vistaDivision;
    }

    public Boolean getCprsVerified() {
        return cprsVerified;
    }

    public void setCprsVerified(Boolean cprsVerified) {
        this.cprsVerified = cprsVerified;
    }

    public Date getDatePasswordChanged() {
        return datePasswordChanged;
    }

    public void setDatePasswordChanged(Date datePasswordChanged) {
        this.datePasswordChanged = datePasswordChanged;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getIsCreateMode() {
        return isCreateMode;
    }

    public void setIsCreateMode(Boolean isCreateMode) {
        this.isCreateMode = isCreateMode;
    }

    public List<Integer> getSelectedProgramIdList() {
        return selectedProgramIdList;
    }

    public void setSelectedProgramIdList(List<Integer> selectedProgramIdList) {
        this.selectedProgramIdList = selectedProgramIdList;
    }

    public UserEditViewFormBean() {
        // default constructor
    }

}
