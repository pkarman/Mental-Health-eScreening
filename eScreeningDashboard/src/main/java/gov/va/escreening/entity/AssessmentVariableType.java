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
@Table(name = "assessment_variable_type")
@NamedQueries({
    @NamedQuery(name = "AssessmentVariableType.findAll", query = "SELECT a FROM AssessmentVariableType a")})
public class AssessmentVariableType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "assessment_variable_type_id")
    private Integer assessmentVariableTypeId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentVariableTypeId")
    private List<AssessmentVariable> assessmentVariableList;

    public AssessmentVariableType() {
    }

    public AssessmentVariableType(Integer assessmentVariableTypeId) {
        this.assessmentVariableTypeId = assessmentVariableTypeId;
    }

    public AssessmentVariableType(Integer assessmentVariableTypeId, String name, Date dateCreated) {
        this.assessmentVariableTypeId = assessmentVariableTypeId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getAssessmentVariableTypeId() {
        return assessmentVariableTypeId;
    }

    public void setAssessmentVariableTypeId(Integer assessmentVariableTypeId) {
        this.assessmentVariableTypeId = assessmentVariableTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<AssessmentVariable> getAssessmentVariableList() {
        return assessmentVariableList;
    }

    public void setAssessmentVariableList(List<AssessmentVariable> assessmentVariableList) {
        this.assessmentVariableList = assessmentVariableList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentVariableTypeId != null ? assessmentVariableTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessmentVariableType)) {
            return false;
        }
        AssessmentVariableType other = (AssessmentVariableType) object;
        if ((this.assessmentVariableTypeId == null && other.assessmentVariableTypeId != null) || (this.assessmentVariableTypeId != null && !this.assessmentVariableTypeId.equals(other.assessmentVariableTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.AssessmentVariableType[ assessmentVariableTypeId=" + assessmentVariableTypeId + " ]";
    }
    
}
