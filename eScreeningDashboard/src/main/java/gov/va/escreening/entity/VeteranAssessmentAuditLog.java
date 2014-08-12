/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jocchiuzzo
 */
@Entity
@Table(name = "veteran_assessment_audit_log")
@NamedQueries({
    @NamedQuery(name = "VeteranAssessmentAuditLog.findAll", query = "SELECT v FROM VeteranAssessmentAuditLog v")})
public class VeteranAssessmentAuditLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "veteran_assessment_audit_log_id")
    private Integer veteranAssessmentAuditLogId;
    @Basic(optional = false)
    @Column(name = "veteran_assessment_id")
    private int veteranAssessmentId;
    @Basic(optional = false)
    @Column(name = "person_id")
    private int personId;
    @Basic(optional = false)
    @Column(name = "person_last_name")
    private String personLastName;
    @Column(name = "person_first_name")
    private String personFirstName;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "person_type_id", referencedColumnName = "person_type_id")
    @ManyToOne(optional = false)
    private PersonType personTypeId;
    @JoinColumn(name = "assessment_status_id", referencedColumnName = "assessment_status_id")
    @ManyToOne(optional = false)
    private AssessmentStatus assessmentStatusId;
    @JoinColumn(name = "veteran_assessment_event_id", referencedColumnName = "veteran_assessment_event_id")
    @ManyToOne(optional = false)
    private VeteranAssessmentEvent veteranAssessmentEventId;

    public VeteranAssessmentAuditLog() {
    }

    public VeteranAssessmentAuditLog(Integer veteranAssessmentAuditLogId) {
        this.veteranAssessmentAuditLogId = veteranAssessmentAuditLogId;
    }

    public VeteranAssessmentAuditLog(Integer veteranAssessmentAuditLogId, int veteranAssessmentId, int personId, String personLastName, Date dateCreated) {
        this.veteranAssessmentAuditLogId = veteranAssessmentAuditLogId;
        this.veteranAssessmentId = veteranAssessmentId;
        this.personId = personId;
        this.personLastName = personLastName;
        this.dateCreated = dateCreated;
    }

    public Integer getVeteranAssessmentAuditLogId() {
        return veteranAssessmentAuditLogId;
    }

    public void setVeteranAssessmentAuditLogId(Integer veteranAssessmentAuditLogId) {
        this.veteranAssessmentAuditLogId = veteranAssessmentAuditLogId;
    }

    public int getVeteranAssessmentId() {
        return veteranAssessmentId;
    }

    public void setVeteranAssessmentId(int veteranAssessmentId) {
        this.veteranAssessmentId = veteranAssessmentId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public PersonType getPersonTypeId() {
        return personTypeId;
    }

    public void setPersonTypeId(PersonType personTypeId) {
        this.personTypeId = personTypeId;
    }

    public AssessmentStatus getAssessmentStatusId() {
        return assessmentStatusId;
    }

    public void setAssessmentStatusId(AssessmentStatus assessmentStatusId) {
        this.assessmentStatusId = assessmentStatusId;
    }

    public VeteranAssessmentEvent getVeteranAssessmentEventId() {
        return veteranAssessmentEventId;
    }

    public void setVeteranAssessmentEventId(VeteranAssessmentEvent veteranAssessmentEventId) {
        this.veteranAssessmentEventId = veteranAssessmentEventId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (veteranAssessmentAuditLogId != null ? veteranAssessmentAuditLogId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VeteranAssessmentAuditLog)) {
            return false;
        }
        VeteranAssessmentAuditLog other = (VeteranAssessmentAuditLog) object;
        if ((this.veteranAssessmentAuditLogId == null && other.veteranAssessmentAuditLogId != null) || (this.veteranAssessmentAuditLogId != null && !this.veteranAssessmentAuditLogId.equals(other.veteranAssessmentAuditLogId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.VeteranAssessmentAuditLog[ veteranAssessmentAuditLogId=" + veteranAssessmentAuditLogId + " ]";
    }
    
}
