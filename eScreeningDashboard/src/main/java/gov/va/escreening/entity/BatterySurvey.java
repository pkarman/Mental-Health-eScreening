package gov.va.escreening.entity;

import java.io.Serializable;

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

/**
 * 
 * @author jjinn
 */
@Entity
@Table(name = "battery_survey")
@NamedQueries({
        @NamedQuery(name = "BatterySurvey.findAll", query = "SELECT b FROM BatterySurvey b") })
public class BatterySurvey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "battery_survey_id")
    private Integer batterySurveyId;
    @JoinColumn(name = "battery_id", referencedColumnName = "battery_id")
    @ManyToOne(optional = false)
    private Battery battery;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(optional = false)
    private Survey survey;

    public BatterySurvey() {
    }

    public BatterySurvey(Integer batterySurveyId) {
        this.batterySurveyId = batterySurveyId;
    }

    public Integer getBatterySurveyId() {
        return batterySurveyId;
    }

    public void setBatterySurveyId(Integer batterySurveyId) {
        this.batterySurveyId = batterySurveyId;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (batterySurveyId != null ? batterySurveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BatterySurvey)) {
            return false;
        }
        BatterySurvey other = (BatterySurvey) object;
        if ((this.batterySurveyId == null && other.batterySurveyId != null)
                || (this.batterySurveyId != null && !this.batterySurveyId.equals(other.batterySurveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.BatterySurvey[ batterySurveyId=" + batterySurveyId + " ]";
    }

}
