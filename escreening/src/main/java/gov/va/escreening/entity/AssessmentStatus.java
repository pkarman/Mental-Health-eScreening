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
@Table(name = "assessment_status")
@NamedQueries({
    @NamedQuery(name = "AssessmentStatus.findAll", query = "SELECT a FROM AssessmentStatus a")})
public class AssessmentStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "assessment_status_id")
    private Integer assessmentStatusId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentStatusId")
    private List<VeteranAssessmentAuditLog> veteranAssessmentAuditLogList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentStatus")
    private List<VeteranAssessment> veteranAssessmentList;

    public AssessmentStatus() {
    }

    public AssessmentStatus(Integer assessmentStatusId) {
        this.assessmentStatusId = assessmentStatusId;
    }

    public AssessmentStatus(Integer assessmentStatusId, String name, Date dateCreated) {
        this.assessmentStatusId = assessmentStatusId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getAssessmentStatusId() {
        return assessmentStatusId;
    }

    public void setAssessmentStatusId(Integer assessmentStatusId) {
        this.assessmentStatusId = assessmentStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<VeteranAssessmentAuditLog> getVeteranAssessmentAuditLogList() {
        return veteranAssessmentAuditLogList;
    }

    public void setVeteranAssessmentAuditLogList(List<VeteranAssessmentAuditLog> veteranAssessmentAuditLogList) {
        this.veteranAssessmentAuditLogList = veteranAssessmentAuditLogList;
    }

    public List<VeteranAssessment> getVeteranAssessmentList() {
        return veteranAssessmentList;
    }

    public void setVeteranAssessmentList(List<VeteranAssessment> veteranAssessmentList) {
        this.veteranAssessmentList = veteranAssessmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentStatusId != null ? assessmentStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessmentStatus)) {
            return false;
        }
        AssessmentStatus other = (AssessmentStatus) object;
        if ((this.assessmentStatusId == null && other.assessmentStatusId != null) || (this.assessmentStatusId != null && !this.assessmentStatusId.equals(other.assessmentStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.AssessmentStatus[ assessmentStatusId=" + assessmentStatusId + " ]";
    }
    
}
