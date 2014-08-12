package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author jjinn
 */
@Entity
@Table(name = "event")
@NamedQueries({
        @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e") })
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "event_id")
    private Integer eventId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    /**
     * This is the ID of the {@link Event.eventType}-specific entity (e.g. Consult, HealthFactor, etc.) 
     */
    @Basic(optional = false)
    @Column(name = "related_object_id")
    private Integer relatedObjectId;
    @Basic(optional = false)
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @JoinColumn(name = "event_type_id", referencedColumnName = "event_type_id")
    @ManyToOne(optional = false)
    private EventType eventType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name="rule_event",
        joinColumns={ @JoinColumn(name="event_id", referencedColumnName="event_id") },
        inverseJoinColumns={ @JoinColumn(name="rule_id", referencedColumnName="rule_id") }
    )
    private Set<Rule> rules;
        
        

    public Event() {
    }

    public Event(Integer eventId) {
        this.eventId = eventId;
    }

    public Event(Integer eventId, String name, Date dateCreated) {
        this.eventId = eventId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRelatedObjectId() {
        return relatedObjectId;
    }

    public void setRelatedObjectId(Integer related_object_id) {
        this.relatedObjectId = related_object_id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public void setRuleEventList(Set<Rule> rules) {
        this.rules = rules;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventId != null ? eventId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.eventId == null && other.eventId != null)
                || (this.eventId != null && !this.eventId.equals(other.eventId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.Event[ eventId=" + eventId 
                + ", eventType=" + eventType 
                + ", relatedObjectId=" + relatedObjectId + "]";
    }

}
