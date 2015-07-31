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
@Table(name = "veteran_assessment_event")
@NamedQueries({
    @NamedQuery(name = "VeteranAssessmentEvent.findAll", query = "SELECT v FROM VeteranAssessmentEvent v")})
public class VeteranAssessmentEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "veteran_assessment_event_id")
    private Integer veteranAssessmentEventId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veteranAssessmentEventId")
    private List<VeteranAssessmentAuditLog> veteranAssessmentAuditLogList;

    public VeteranAssessmentEvent() {
    }

    public VeteranAssessmentEvent(Integer veteranAssessmentEventId) {
        this.veteranAssessmentEventId = veteranAssessmentEventId;
    }

    public VeteranAssessmentEvent(Integer veteranAssessmentEventId, String name, Date dateCreated) {
        this.veteranAssessmentEventId = veteranAssessmentEventId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getVeteranAssessmentEventId() {
        return veteranAssessmentEventId;
    }

    public void setVeteranAssessmentEventId(Integer veteranAssessmentEventId) {
        this.veteranAssessmentEventId = veteranAssessmentEventId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (veteranAssessmentEventId != null ? veteranAssessmentEventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VeteranAssessmentEvent)) {
            return false;
        }
        VeteranAssessmentEvent other = (VeteranAssessmentEvent) object;
        if ((this.veteranAssessmentEventId == null && other.veteranAssessmentEventId != null) || (this.veteranAssessmentEventId != null && !this.veteranAssessmentEventId.equals(other.veteranAssessmentEventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.VeteranAssessmentEvent[ veteranAssessmentEventId=" + veteranAssessmentEventId + " ]";
    }
    
}
