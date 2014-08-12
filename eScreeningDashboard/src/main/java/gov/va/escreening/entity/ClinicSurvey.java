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
@Table(name = "clinic_survey")
@NamedQueries({
        @NamedQuery(name = "ClinicSurvey.findAll", query = "SELECT c FROM ClinicSurvey c") })
public class ClinicSurvey implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clinic_survey_id")
    private Integer clinicSurveyId;
    @JoinColumn(name = "clinic_id", referencedColumnName = "clinic_id")
    @ManyToOne(optional = false)
    private Clinic clinic;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(optional = false)
    private Survey survey;

    public ClinicSurvey() {
    }

    public ClinicSurvey(Integer clinicSurveyId) {
        this.clinicSurveyId = clinicSurveyId;
    }

    public Integer getClinicSurveyId() {
        return clinicSurveyId;
    }

    public void setClinicSurveyId(Integer clinicSurveyId) {
        this.clinicSurveyId = clinicSurveyId;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
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
        hash += (clinicSurveyId != null ? clinicSurveyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClinicSurvey)) {
            return false;
        }
        ClinicSurvey other = (ClinicSurvey) object;
        if ((this.clinicSurveyId == null && other.clinicSurveyId != null)
                || (this.clinicSurveyId != null && !this.clinicSurveyId.equals(other.clinicSurveyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gov.va.escreening.entity.ClinicSurvey[ clinicSurveyId=" + clinicSurveyId + " ]";
    }

}
