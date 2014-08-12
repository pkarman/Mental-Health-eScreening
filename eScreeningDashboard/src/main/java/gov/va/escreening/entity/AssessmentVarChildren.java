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
@Table(name = "assessment_var_children")
@NamedQueries({
    @NamedQuery(name = "AssessmentVarChildren.findAll", query = "SELECT a FROM AssessmentVarChildren a")})
public class AssessmentVarChildren implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assessment_var_children_id")
    private Integer assessmentVarChildrenId;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "variable_parent", referencedColumnName = "assessment_variable_id")
    @ManyToOne(optional = false)
    private AssessmentVariable variableParent;
    @JoinColumn(name = "variable_child", referencedColumnName = "assessment_variable_id")
    @ManyToOne(optional = false)
    private AssessmentVariable variableChild;

    public AssessmentVarChildren() {
    }

    public AssessmentVarChildren(Integer assessmentVarChildrenId) {
        this.assessmentVarChildrenId = assessmentVarChildrenId;
    }

    public AssessmentVarChildren(Integer assessmentVarChildrenId, Date dateCreated) {
        this.assessmentVarChildrenId = assessmentVarChildrenId;
        this.dateCreated = dateCreated;
    }

    public Integer getAssessmentVarChildrenId() {
        return assessmentVarChildrenId;
    }

    public void setAssessmentVarChildrenId(Integer assessmentVarChildrenId) {
        this.assessmentVarChildrenId = assessmentVarChildrenId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AssessmentVariable getVariableParent() {
        return variableParent;
    }

    public void setVariableParent(AssessmentVariable variableParent) {
        this.variableParent = variableParent;
    }

    public AssessmentVariable getVariableChild() {
        return variableChild;
    }

    public void setVariableChild(AssessmentVariable variableChild) {
        this.variableChild = variableChild;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentVarChildrenId != null ? assessmentVarChildrenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessmentVarChildren)) {
            return false;
        }
        AssessmentVarChildren other = (AssessmentVarChildren) object;
        if ((this.assessmentVarChildrenId == null && other.assessmentVarChildrenId != null) || (this.assessmentVarChildrenId != null && !this.assessmentVarChildrenId.equals(other.assessmentVarChildrenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.AssessmentVarChildren[ assessmentVarChildrenId=" + assessmentVarChildrenId + " ]";
    }
    
}
