package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u") })
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "login_id")
    private String loginId;
    @Column(name = "password")
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "email_address2")
    private String emailAddress2;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "phone_number_ext")
    private String phoneNumberExt;
    @Column(name = "phone_number2")
    private String phoneNumber2;
    @Column(name = "phone_number2_ext")
    private String phoneNumber2Ext;
    @Column(name = "vista_duz")
    private String vistaDuz;
    @Column(name = "vista_vpid")
    private String vistaVpid;
    @Column(name = "vista_division")
    private String vistaDivision;
    @Column(name = "cprs_verified")
    private Boolean cprsVerified;
    @Column(name = "date_password_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePasswordChanged;
    @Column(name = "last_login_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserProgram> userProgramList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UserClinic> userClinicList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdByUser")
    private List<VeteranAssessment> createdByVeteranAssessmentList;
    @OneToMany(mappedBy = "clinician")
    private List<VeteranAssessment> clinicianVeteranAssessmentList;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Role role;
    @JoinColumn(name = "user_status_id", referencedColumnName = "user_status_id")
    @ManyToOne(optional = false)
    private UserStatus userStatus;

    public User() {
    }

    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String loginId, Date dateCreated) {
        this.userId = userId;
        this.loginId = loginId;
        this.dateCreated = dateCreated;
    }

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

    public List<UserProgram> getUserProgramList() {
        return userProgramList;
    }

    public void setUserProgramList(List<UserProgram> userProgramList) {
        this.userProgramList = userProgramList;
    }

    public List<UserClinic> getUserClinicList() {
        return userClinicList;
    }

    public void setUserClinicList(List<UserClinic> userClinicList) {
        this.userClinicList = userClinicList;
    }

    public List<VeteranAssessment> getCreatedByVeteranAssessmentList() {
        return createdByVeteranAssessmentList;
    }

    public void setCreatedByVeteranAssessmentList(List<VeteranAssessment> createdByVeteranAssessmentList) {
        this.createdByVeteranAssessmentList = createdByVeteranAssessmentList;
    }

    public List<VeteranAssessment> getClinicianVeteranAssessmentList() {
        return clinicianVeteranAssessmentList;
    }

    public void setClinicianVeteranAssessmentList(List<VeteranAssessment> clinicianVeteranAssessmentList) {
        this.clinicianVeteranAssessmentList = clinicianVeteranAssessmentList;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.User[ userId=" + userId + " ]";
    }

}
