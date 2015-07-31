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
@Table(name = "person_type")
@NamedQueries({
    @NamedQuery(name = "PersonType.findAll", query = "SELECT p FROM PersonType p")})
public class PersonType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "person_type_id")
    private Integer personTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personTypeId")
    private List<VeteranAssessmentAuditLog> veteranAssessmentAuditLogList;

    public PersonType() {
    }

    public PersonType(Integer personTypeId) {
        this.personTypeId = personTypeId;
    }

    public PersonType(Integer personTypeId, String name, Date dateCreated) {
        this.personTypeId = personTypeId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getPersonTypeId() {
        return personTypeId;
    }

    public void setPersonTypeId(Integer personTypeId) {
        this.personTypeId = personTypeId;
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
        hash += (personTypeId != null ? personTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonType)) {
            return false;
        }
        PersonType other = (PersonType) object;
        if ((this.personTypeId == null && other.personTypeId != null) || (this.personTypeId != null && !this.personTypeId.equals(other.personTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.PersonType[ personTypeId=" + personTypeId + " ]";
    }
    
}
