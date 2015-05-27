package gov.va.escreening.entity;

import gov.va.escreening.dto.template.TemplateIfBlockDTO;
import gov.va.escreening.service.TemplateServiceImpl;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author jjinn, Robin Carnow
 */
@Entity
@Table(name = "rule")
@NamedQueries({
    @NamedQuery(name = "Rule.findAll", query = "SELECT r FROM Rule r")})
public class Rule implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(Rule.class);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rule_id")
    private Integer ruleId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "expression")
    private String expression;
    @Column(name="condition_json")
    private String condition;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name="rule_event",
        joinColumns={ @JoinColumn(name="rule_id", referencedColumnName="rule_id") },
        inverseJoinColumns={ @JoinColumn(name="event_id", referencedColumnName="event_id", unique=true) }
    )
    private Set<Event> events;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
        name="rule_assessment_variable",
        joinColumns={ @JoinColumn(name="rule_id", referencedColumnName="rule_id") },
        inverseJoinColumns={ @JoinColumn(name="assessment_variable_id", referencedColumnName="assessment_variable_id", unique=true) }
    )
    private Set<AssessmentVariable> assessmentVariables;
    
    public Rule() {
    }

    public Rule(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public Rule(Integer ruleId, String name, String expression, Date dateCreated) {
        this.ruleId = ruleId;
        this.name = name;
        this.expression = expression;
        this.dateCreated = dateCreated;
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
    
    public String getCondition() {
        return condition;
    }
    
    /**
     * This is for hibernate. Please use other method.
     * @param json raw json string to save
     */
    public void setCondition(String json) {
        this.condition = json;
    }
    
    /**
     * Sets the jsonFile field using the given condition
     * @param condition
     */
    public void setCondition(TemplateIfBlockDTO condition){
        if(condition != null){
            // save raw json file to the database
            ObjectMapper om = new ObjectMapper();
            try{
                this.condition = om.writeValueAsString(condition);
            }
            catch(IOException e) {
                logger.error("Error setting condition data", e);
                this.condition = null;
            }
        }
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    
    public Set<AssessmentVariable> getAssessmentVariables() {
        return assessmentVariables;
    }

    public void setAssessmentVariables(Set<AssessmentVariable> assessmentVariables) {
        this.assessmentVariables = assessmentVariables;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruleId != null ? ruleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rule)) {
            return false;
        }
        Rule other = (Rule) object;
        if ((this.ruleId == null && other.ruleId != null) || (this.ruleId != null && !this.ruleId.equals(other.ruleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.Rule[ ruleId=" + ruleId 
                + ", expression='" + expression 
                + "', events: " + Arrays.toString(events.toArray())  
                +"]";
    }    
}
