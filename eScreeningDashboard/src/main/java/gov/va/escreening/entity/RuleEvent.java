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
@Table(name = "rule_event")
@NamedQueries({
        @NamedQuery(name = "RuleEvent.findAll", query = "SELECT r FROM RuleEvent r") })
public class RuleEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rule_event_id")
    private Integer ruleEventId;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "rule_id", referencedColumnName = "rule_id")
    @ManyToOne(optional = false)
    private Rule rule;
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    @ManyToOne(optional = false)
    private Event event;

    public RuleEvent() {
    }

    public RuleEvent(Integer ruleEventId) {
        this.ruleEventId = ruleEventId;
    }

    public RuleEvent(Integer ruleEventId, Date dateCreated) {
        this.ruleEventId = ruleEventId;
        this.dateCreated = dateCreated;
    }

    public Integer getRuleEventId() {
        return ruleEventId;
    }

    public void setRuleEventId(Integer ruleEventId) {
        this.ruleEventId = ruleEventId;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruleEventId != null ? ruleEventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RuleEvent)) {
            return false;
        }
        RuleEvent other = (RuleEvent) object;
        if ((this.ruleEventId == null && other.ruleEventId != null)
                || (this.ruleEventId != null && !this.ruleEventId.equals(other.ruleEventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.RuleEvent[ ruleEventId=" + ruleEventId + " ]";
    }

}
