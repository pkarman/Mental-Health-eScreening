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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "assessment_variable")
@NamedQueries({
    @NamedQuery(name = "AssessmentVariable.findAll", query = "SELECT a FROM AssessmentVariable a")})
public class AssessmentVariable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assessment_variable_id")
    private Integer assessmentVariableId;
    @Basic(optional = false)
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "description")
    private String description;
    @Column(name = "formula_template")
    private String formulaTemplate;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentVariableId")
    private List<VariableTemplate> variableTemplateList;
    @JoinColumn(name = "measure_id", referencedColumnName = "measure_id")
    @ManyToOne
    private Measure measure;
    @JoinColumn(name = "assessment_variable_type_id", referencedColumnName = "assessment_variable_type_id")
    @ManyToOne(optional = false)
    private AssessmentVariableType assessmentVariableTypeId;
    @JoinColumn(name = "measure_answer_id", referencedColumnName = "measure_answer_id")
    @ManyToOne
    private MeasureAnswer measureAnswer;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentVariableId")
    //private List<RuleAssessmentVariable> ruleAssessmentVariableList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variableParent")
    private List<AssessmentVarChildren> assessmentVarChildrenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variableChild")
    private List<AssessmentVarChildren> assessmentVarChildrenList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentVariableId")
    private List<AssessmentVariableColumn> assessmentVariableColumnList;

    public AssessmentVariable() {
    }

    public AssessmentVariable(Integer assessmentVariableId) {
        this.assessmentVariableId = assessmentVariableId;
    }

    public AssessmentVariable(Integer assessmentVariableId, String displayName, Date dateCreated) {
        this.assessmentVariableId = assessmentVariableId;
        this.displayName = displayName;
        this.dateCreated = dateCreated;
    }

    public Integer getAssessmentVariableId() {
        return assessmentVariableId;
    }

    public void setAssessmentVariableId(Integer assessmentVariableId) {
        this.assessmentVariableId = assessmentVariableId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormulaTemplate() {
        return formulaTemplate;
    }

    public void setFormulaTemplate(String formulaTemplate) {
        this.formulaTemplate = formulaTemplate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<VariableTemplate> getVariableTemplateList() {
        return variableTemplateList;
    }

    public void setVariableTemplateList(List<VariableTemplate> variableTemplateList) {
        this.variableTemplateList = variableTemplateList;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public AssessmentVariableType getAssessmentVariableTypeId() {
        return assessmentVariableTypeId;
    }

    public void setAssessmentVariableTypeId(AssessmentVariableType assessmentVariableTypeId) {
        this.assessmentVariableTypeId = assessmentVariableTypeId;
    }

    public MeasureAnswer getMeasureAnswer() {
        return measureAnswer;
    }

    public void setMeasureAnswer(MeasureAnswer measureAnswer) {
        this.measureAnswer = measureAnswer;
    }

    //public List<RuleAssessmentVariable> getRuleAssessmentVariableList() {
    //    return ruleAssessmentVariableList;
    //}

    //public void setRuleAssessmentVariableList(List<RuleAssessmentVariable> ruleAssessmentVariableList) {
    //    this.ruleAssessmentVariableList = ruleAssessmentVariableList;
    //}

    public List<AssessmentVarChildren> getAssessmentVarChildrenList() {
        return assessmentVarChildrenList;
    }

    public void setAssessmentVarChildrenList(List<AssessmentVarChildren> assessmentVarChildrenList) {
        this.assessmentVarChildrenList = assessmentVarChildrenList;
    }

    public List<AssessmentVarChildren> getAssessmentVarChildrenList1() {
        return assessmentVarChildrenList1;
    }

    public void setAssessmentVarChildrenList1(List<AssessmentVarChildren> assessmentVarChildrenList1) {
        this.assessmentVarChildrenList1 = assessmentVarChildrenList1;
    }

    public List<AssessmentVariableColumn> getAssessmentVariableColumnList() {
        return assessmentVariableColumnList;
    }

    public void setAssessmentVariableColumnList(List<AssessmentVariableColumn> assessmentVariableColumnList) {
        this.assessmentVariableColumnList = assessmentVariableColumnList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assessmentVariableId != null ? assessmentVariableId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessmentVariable)) {
            return false;
        }
        AssessmentVariable other = (AssessmentVariable) object;
        if ((this.assessmentVariableId == null && other.assessmentVariableId != null) || (this.assessmentVariableId != null && !this.assessmentVariableId.equals(other.assessmentVariableId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.AssessmentVariable[ assessmentVariableId=" + assessmentVariableId + " ]";
    }
    
}
