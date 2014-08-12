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
@Table(name = "veteran_assessment_log")
@NamedQueries({ @NamedQuery(name = "VeteranAssessmentLog.findAll", query = "SELECT v FROM VeteranAssessmentLog v") })
public class VeteranAssessmentLog implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "veteran_assessment_log_id")
	private Integer veteranAssessmentLogId;
	@Basic(optional = false)
	@Column(name = "veteran_assessment_id")
	private int veteranAssessmentId;
	@Basic(optional = false)
	@Column(name = "veteran_id")
	private int veteranId;
	@Column(name = "veteran_asessment_survey_id")
	private Integer veteranAsessmentSurveyId;
	@Column(name = "survey_id")
	private Integer surveyId;
	@Column(name = "survey_page_id")
	private Integer surveyPageId;
	@Column(name = "measure_id")
	private Integer measureId;
	@Basic(optional = false)
	@Column(name = "message")
	private String message;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@JoinColumn(name = "veteran_assessment_action_id", referencedColumnName = "veteran_assessment_action_id")
	@ManyToOne(optional = false)
	private VeteranAssessmentAction veteranAssessmentAction;

	public VeteranAssessmentLog() {
	}

	public VeteranAssessmentLog(Integer veteranAssessmentLogId) {
		this.veteranAssessmentLogId = veteranAssessmentLogId;
	}

	public VeteranAssessmentLog(Integer veteranAssessmentLogId,
			int veteranAssessmentId, int veteranId, String message,
			Date dateCreated) {
		this.veteranAssessmentLogId = veteranAssessmentLogId;
		this.veteranAssessmentId = veteranAssessmentId;
		this.veteranId = veteranId;
		this.message = message;
		this.dateCreated = dateCreated;
	}

	public Integer getVeteranAssessmentLogId() {
		return veteranAssessmentLogId;
	}

	public void setVeteranAssessmentLogId(Integer veteranAssessmentLogId) {
		this.veteranAssessmentLogId = veteranAssessmentLogId;
	}

	public int getVeteranAssessmentId() {
		return veteranAssessmentId;
	}

	public void setVeteranAssessmentId(int veteranAssessmentId) {
		this.veteranAssessmentId = veteranAssessmentId;
	}

	public int getVeteranId() {
		return veteranId;
	}

	public void setVeteranId(int veteranId) {
		this.veteranId = veteranId;
	}

	public Integer getVeteranAsessmentSurveyId() {
		return veteranAsessmentSurveyId;
	}

	public void setVeteranAsessmentSurveyId(Integer veteranAsessmentSurveyId) {
		this.veteranAsessmentSurveyId = veteranAsessmentSurveyId;
	}

	public Integer getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}

	public Integer getSurveyPageId() {
		return surveyPageId;
	}

	public void setSurveyPageId(Integer surveyPageId) {
		this.surveyPageId = surveyPageId;
	}

	public Integer getMeasureId() {
		return measureId;
	}

	public void setMeasureId(Integer measureId) {
		this.measureId = measureId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public VeteranAssessmentAction getVeteranAssessmentAction() {
		return veteranAssessmentAction;
	}

	public void setVeteranAssessmentAction(
			VeteranAssessmentAction veteranAssessmentAction) {
		this.veteranAssessmentAction = veteranAssessmentAction;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (veteranAssessmentLogId != null ? veteranAssessmentLogId
				.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof VeteranAssessmentLog)) {
			return false;
		}
		VeteranAssessmentLog other = (VeteranAssessmentLog) object;
		if ((this.veteranAssessmentLogId == null && other.veteranAssessmentLogId != null)
				|| (this.veteranAssessmentLogId != null && !this.veteranAssessmentLogId
						.equals(other.veteranAssessmentLogId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.VeteranAssessmentLog[ veteranAssessmentLogId="
				+ veteranAssessmentLogId + " ]";
	}

}
