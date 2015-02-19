/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.va.escreening.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Khalid Rizvi
 */
@Entity
@Table(name = "assessment_formula")
@NamedQueries({
        @NamedQuery(name = "AssessmentFormula.findAll", query = "SELECT a FROM AssessmentFormula a")})
public class AssessmentFormula implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "assessment_formula_id")
    private Integer assessmentFormulaId;

    @Basic(optional = false)
    @JoinColumn(name = "assessment_variable_id", referencedColumnName = "assessment_variable_id")
    @ManyToOne(optional = false)
    private AssessmentVariable parentAssessment;

    @Basic(optional = false)
    @Column(name = "display_order")
    private Integer displayOrder;

    @Basic(optional = false)
    @Column(name = "formula_token")
    private String formulaToken;

    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public AssessmentFormula() {
    }

    public Integer getAssessmentFormulaId() {
        return assessmentFormulaId;
    }

    public void setAssessmentFormulaId(Integer assessmentFormulaId) {
        this.assessmentFormulaId = assessmentFormulaId;
    }

    public AssessmentVariable getParentAssessment() {
        return parentAssessment;
    }

    public void setParentAssessment(AssessmentVariable parentAssessment) {
        this.parentAssessment = parentAssessment;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getFormulaToken() {
        return formulaToken;
    }

    public void setFormulaToken(String formulaToken) {
        this.formulaToken = formulaToken;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssessmentFormula)) return false;

        AssessmentFormula that = (AssessmentFormula) o;

        if (!assessmentFormulaId.equals(that.assessmentFormulaId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return assessmentFormulaId.hashCode();
    }

    @Override
    public String toString() {
        return "AssessmentFormula{" +
                "id=" + assessmentFormulaId +
                ", parentId=" + parentAssessment.getAssessmentVariableId() +
                ", order=" + displayOrder +
                ", token='" + formulaToken + '\'' +
                '}';
    }
}
