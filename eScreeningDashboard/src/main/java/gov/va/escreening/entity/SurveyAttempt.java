/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author jocchiuzzo
 */
@Entity
@Table(name = "survey_attempt")
@NamedQueries({ @NamedQuery(name = "SurveyAttempt.findAll", query = "SELECT s FROM SurveyAttempt s") })
public class SurveyAttempt implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "survey_attempt_id")
	private Integer surveyAttemptId;
	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@JoinColumn(name = "veteran_assessment_survey_id", referencedColumnName = "veteran_assessment_survey_id")
	@ManyToOne(optional = false)
	private VeteranAssessmentSurvey veteranAssessmentSurvey;

	public SurveyAttempt() {
	}

	public SurveyAttempt(Integer surveyAttemptId) {
		this.surveyAttemptId = surveyAttemptId;
	}

	public SurveyAttempt(Integer surveyAttemptId, Date dateCreated) {
		this.surveyAttemptId = surveyAttemptId;
		this.dateCreated = dateCreated;
	}

	public Integer getSurveyAttemptId() {
		return surveyAttemptId;
	}

	public void setSurveyAttemptId(Integer surveyAttemptId) {
		this.surveyAttemptId = surveyAttemptId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public VeteranAssessmentSurvey getVeteranAssessmentSurvey() {
		return veteranAssessmentSurvey;
	}

	public void setVeteranAssessmentSurvey(
			VeteranAssessmentSurvey veteranAssessmentSurvey) {
		this.veteranAssessmentSurvey = veteranAssessmentSurvey;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (surveyAttemptId != null ? surveyAttemptId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SurveyAttempt)) {
			return false;
		}
		SurveyAttempt other = (SurveyAttempt) object;
		if ((this.surveyAttemptId == null && other.surveyAttemptId != null)
				|| (this.surveyAttemptId != null && !this.surveyAttemptId
						.equals(other.surveyAttemptId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.SurveyAttempt[ surveyAttemptId="
				+ surveyAttemptId + " ]";
	}

}
