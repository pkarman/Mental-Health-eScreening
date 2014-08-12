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
@Table(name = "clinical_reminder_survey")
@NamedQueries({
        @NamedQuery(name = "ClinicalReminderSurvey.findAll", query = "SELECT c FROM ClinicalReminderSurvey c") })
public class ClinicalReminderSurvey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clinical_reminder_survey_id")
    private Integer clinicalReminderSurveyId;
    @JoinColumn(name = "clinical_reminder_id", referencedColumnName = "clinical_reminder_id")
    @ManyToOne(optional = false)
    private ClinicalReminder clinicalReminder;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(optional = false)
    private Survey survey;

    public ClinicalReminderSurvey() {
    }

    public ClinicalReminderSurvey(Integer clinicalReminderSurveyId) {
        this.clinicalReminderSurveyId = clinicalReminderSurveyId;
    }

    public Integer getClinicalReminderSurveyId() {
        return clinicalReminderSurveyId;
    }

    public void setClinicalReminderSurveyId(Integer clinicalReminderSurveyId) {
        this.clinicalReminderSurveyId = clinicalReminderSurveyId;
    }

    public ClinicalReminder getClinicalReminder() {
        return clinicalReminder;
    }

    public void setClinicalReminder(ClinicalReminder clinicalReminder) {
        this.clinicalReminder = clinicalReminder;
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
        hash += (clinicalReminderSurveyId != null ? clinicalReminderSurveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClinicalReminderSurvey)) {
            return false;
        }
        ClinicalReminderSurvey other = (ClinicalReminderSurvey) object;
        if ((this.clinicalReminderSurveyId == null && other.clinicalReminderSurveyId != null)
                || (this.clinicalReminderSurveyId != null && !this.clinicalReminderSurveyId
                        .equals(other.clinicalReminderSurveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.ClinicalReminderSurvey[ clinicalReminderSurveyId="
                + clinicalReminderSurveyId + " ]";
    }

}
