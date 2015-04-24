/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
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
    
    //can't make this lazy until we stop using override values (see MeasureAnswerAssessmentVariableResolverImpl)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentVariableId")//, fetch = FetchType.LAZY) 
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
    
    //TODO: We should change this to have a list (or Set really) of AssessmentVariables and not a list of AssessmentVarChildren
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variableParent", orphanRemoval = true)
    private List<AssessmentVarChildren> assessmentVarChildrenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessmentVariableId", fetch = FetchType.LAZY)
    private List<AssessmentVariableColumn> assessmentVariableColumnList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentAssessment", orphanRemoval = true)
    @OrderBy("displayOrder")
    private List<AssessmentFormula> assessmentFormulas;

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

    //TODO: We should change this to have a list of AssessmentVariables and not a list of AssessmentVarChildren
    public List<AssessmentVarChildren> getAssessmentVarChildrenList() {
        return assessmentVarChildrenList;
    }

    public void setAssessmentVarChildrenList(List<AssessmentVarChildren> assessmentVarChildrenList) {
        if (this.assessmentVarChildrenList == null) {
            this.assessmentVarChildrenList = assessmentVarChildrenList;
        } 
        else {
            this.assessmentVarChildrenList.clear();
            this.assessmentVarChildrenList.addAll(assessmentVarChildrenList);
        }
    }

    public List<AssessmentVariableColumn> getAssessmentVariableColumnList() {
        return assessmentVariableColumnList;
    }

    public void setAssessmentVariableColumnList(List<AssessmentVariableColumn> assessmentVariableColumnList) {
        this.assessmentVariableColumnList = assessmentVariableColumnList;
    }

    public List<AssessmentFormula> getAssessmentFormulas() {
        return assessmentFormulas;
    }

    public void setAssessmentFormulas(List<AssessmentFormula> assessmentFormulas) {
        if (this.assessmentFormulas == null) {
            this.assessmentFormulas = assessmentFormulas;
        } else {
            this.assessmentFormulas.clear();
            this.assessmentFormulas.addAll(assessmentFormulas);
        }
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
        return "[id=" + assessmentVariableId + "]";
    }

    public Map<String, Object> getAsMap() {
        Map<String, Object> asMap = Maps.newHashMap();
        asMap.put("id", assessmentVariableId);
        asMap.put("name", displayName);
        return asMap;
    }

    public List<String> getAsList() {
        return Arrays.asList(getDisplayName(), 
                getAssessmentVariableId().toString(), 
                getDescription(), 
                getFormulaTemplate(), 
                Integer.valueOf(getDisplayName().length()+1).toString());
    }

    public void attachFormulaTokens(List<String> tokens) {
        int row = 0;
        List<AssessmentFormula> afList = Lists.newArrayList();
        for (String token : tokens) {
            AssessmentFormula af = new AssessmentFormula();

            String rawToken = token;
            String formulaToken = null;
            if (rawToken.substring(0, 1).equals("t")) {
                formulaToken = rawToken.substring(2);
                af.setUserDefined(true);
            } else {
                formulaToken = rawToken.substring(2);
                af.setUserDefined(false);
            }

            af.setFormulaToken(formulaToken);
            af.setParentAssessment(this);
            af.setDisplayOrder(++row);
            af.setDateCreated(new Date());
            afList.add(af);
        }
        setAssessmentFormulas(afList);
    }

    public Map<String, Object> getAsFormulaVar() {
        Map<String, Object> formulaAsMap = getAsMap();
        formulaAsMap.put("description", getDescription());
        return formulaAsMap;
    }
}
