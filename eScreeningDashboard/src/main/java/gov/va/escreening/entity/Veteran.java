/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author jocchiuzzo
 */
@Entity
@Table(name = "veteran")
@NamedQueries({ @NamedQuery(name = "Veteran.findAll", query = "SELECT v FROM Veteran v") })
public class Veteran implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "veteran_id")
    private Integer veteranId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "suffix")
    private String suffix;
    
    @Basic(optional = false)
    @Column(name = "ssn_last_four")
    private String ssnLastFour;
    
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "office_phone")
    private String officePhone;
    
    @Column(name = "cell_phone")
    private String cellPhone;
    
    @Column(name = "best_time_to_call")
    private String bestTimeToCall;
    
    @Column(name = "best_time_to_call_other")
    private String bestTimeToCallOther;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "vista_local_pid")
    private String vistaLocalPid;
    
    @Basic(optional = false)
    @Column(name = "guid")
    private String guid;
    
    @Column(name = "veteran_ien")
    private String veteranIen;

    @Column(name = "date_refreshed_from_vista")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRefreshedFromVista;

    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veteran")
    private List<VeteranAssessment> veteranAssessmentList;

    public Veteran() {
    }

    public Veteran(Integer veteranId) {
        this.veteranId = veteranId;
    }

    public Veteran(Integer veteranId, String lastName, String ssnLastFour, String guid,
            Date dateCreated) {
        this.veteranId = veteranId;
        this.lastName = lastName;
        this.ssnLastFour = ssnLastFour;
        this.dateCreated = dateCreated;
    }

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
        this.firstName = truncate(firstName, 255);
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = truncate(middleName, 255);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = truncate(lastName, 255);
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = truncate(suffix, 255);
    }

    public String getSsnLastFour() {
        return ssnLastFour;
    }

    public void setSsnLastFour(String ssnLastFour) {
        this.ssnLastFour = truncate(ssnLastFour, 4);
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = truncate(email, 50);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = truncate(phone, 50);
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

    public String getBestTimeToCall() {
        return bestTimeToCall;
    }

    public void setBestTimeToCall(String bestTimeToCall) {
        this.bestTimeToCall = truncate(bestTimeToCall, 50);
    }

    public String getBestTimeToCallOther() {
        return bestTimeToCallOther;
    }

    public void setBestTimeToCallOther(String bestTimeToCallOther) {
        this.bestTimeToCallOther = truncate(bestTimeToCallOther, 50);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = truncate(gender, 10);
    }

    public String getVistaLocalPid() {
        return vistaLocalPid;
    }

    public void setVistaLocalPid(String vistaLocalPid) {
        this.vistaLocalPid = truncate(vistaLocalPid, 16);
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = truncate(guid, 36);
    }

    public String getVeteranIen() {
        return veteranIen;
    }

    public void setVeteranIen(String veteranIen) {
        this.veteranIen = veteranIen;
    }

    public Date getDateRefreshedFromVista() {
        return dateRefreshedFromVista;
    }

    public void setDateRefreshedFromVista(Date dateRefreshedFromVista) {
        this.dateRefreshedFromVista = dateRefreshedFromVista;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<VeteranAssessment> getVeteranAssessmentList() {
        return veteranAssessmentList;
    }

    public void setVeteranAssessmentList(
            List<VeteranAssessment> veteranAssessmentList) {
        this.veteranAssessmentList = veteranAssessmentList;
    }

    private String truncate(String value, int maxLength) {
        if (value == null)
            return null;
        return value.substring(0, Math.min(value.length(), maxLength));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (veteranId != null ? veteranId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are
        // not set
        if (!(object instanceof Veteran)) {
            return false;
        }
        Veteran other = (Veteran) object;
        if ((this.veteranId == null && other.veteranId != null)
                || (this.veteranId != null && !this.veteranId
                        .equals(other.veteranId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.Veteran[ veteranId="
                + veteranId + " ]";
    }
}