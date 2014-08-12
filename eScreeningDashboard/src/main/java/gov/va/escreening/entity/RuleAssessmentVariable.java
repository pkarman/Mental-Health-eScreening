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
 * @author jjinn
 */
@Entity
@Table(name = "rule_assessment_variable")
@NamedQueries({
        @NamedQuery(name = "RuleAssessmentVariable.findAll", query = "SELECT r FROM RuleAssessmentVariable r") })
public class RuleAssessmentVariable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rule_assessment_variable_id")
    private Integer ruleAssessmentVariableId;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "rule_id", referencedColumnName = "rule_id")
    @ManyToOne(optional = false)
    private Rule rule;
    @JoinColumn(name = "assessment_variable_id", referencedColumnName = "assessment_variable_id")
    @ManyToOne(optional = false)
    private AssessmentVariable assessmentVariable;

    public RuleAssessmentVariable() {
    }

    public RuleAssessmentVariable(Integer ruleAssessmentVariableId) {
        this.ruleAssessmentVariableId = ruleAssessmentVariableId;
    }

    public RuleAssessmentVariable(Integer ruleAssessmentVariableId, Date dateCreated) {
        this.ruleAssessmentVariableId = ruleAssessmentVariableId;
        this.dateCreated = dateCreated;
    }

    public Integer getRuleAssessmentVariableId() {
        return ruleAssessmentVariableId;
    }

    public void setRuleAssessmentVariableId(Integer ruleAssessmentVariableId) {
        this.ruleAssessmentVariableId = ruleAssessmentVariableId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public AssessmentVariable getAssessmentVariable() {
        return assessmentVariable;
    }

    public void setAssessmentVariable(AssessmentVariable assessmentVariable) {
        this.assessmentVariable = assessmentVariable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruleAssessmentVariableId != null ? ruleAssessmentVariableId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuleAssessmentVariable)) {
            return false;
        }
        RuleAssessmentVariable other = (RuleAssessmentVariable) object;
        if ((this.ruleAssessmentVariableId == null && other.ruleAssessmentVariableId != null)
                || (this.ruleAssessmentVariableId != null && !this.ruleAssessmentVariableId
                        .equals(other.ruleAssessmentVariableId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.RuleAssessmentVariable[ ruleAssessmentVariableId="
                + ruleAssessmentVariableId + " ]";
    }

}
