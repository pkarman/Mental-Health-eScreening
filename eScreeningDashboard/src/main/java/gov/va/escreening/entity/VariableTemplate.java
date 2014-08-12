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
@Table(name = "variable_template")
@NamedQueries({
    @NamedQuery(name = "VariableTemplate.findAll", query = "SELECT v FROM VariableTemplate v")})
public class VariableTemplate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "variable_template_id")
    private Integer variableTemplateId;
    @Column(name = "override_display_value")
    private String overrideDisplayValue;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "assessment_variable_id", referencedColumnName = "assessment_variable_id")
    @ManyToOne(optional = false)
    private AssessmentVariable assessmentVariableId;
    @JoinColumn(name = "template_id", referencedColumnName = "template_id")
    @ManyToOne(optional = false)
    private Template templateId;

    public VariableTemplate() {
    }

    public VariableTemplate(Integer variableTemplateId) {
        this.variableTemplateId = variableTemplateId;
    }

    public VariableTemplate(Integer variableTemplateId, Date dateCreated) {
        this.variableTemplateId = variableTemplateId;
        this.dateCreated = dateCreated;
    }

    public Integer getVariableTemplateId() {
        return variableTemplateId;
    }

    public void setVariableTemplateId(Integer variableTemplateId) {
        this.variableTemplateId = variableTemplateId;
    }

    public String getOverrideDisplayValue() {
        return overrideDisplayValue;
    }

    public void setOverrideDisplayValue(String overrideDisplayValue) {
        this.overrideDisplayValue = overrideDisplayValue;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public AssessmentVariable getAssessmentVariableId() {
        return assessmentVariableId;
    }

    public void setAssessmentVariableId(AssessmentVariable assessmentVariableId) {
        this.assessmentVariableId = assessmentVariableId;
    }

    public Template getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Template templateId) {
        this.templateId = templateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (variableTemplateId != null ? variableTemplateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VariableTemplate)) {
            return false;
        }
        VariableTemplate other = (VariableTemplate) object;
        if ((this.variableTemplateId == null && other.variableTemplateId != null) || (this.variableTemplateId != null && !this.variableTemplateId.equals(other.variableTemplateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.VariableTemplate[ variableTemplateId=" + variableTemplateId + " ]";
    }
    
}
