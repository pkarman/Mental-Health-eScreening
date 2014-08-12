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
@Table(name = "survey_measure_response")
@NamedQueries({ @NamedQuery(name = "SurveyMeasureResponse.findAll", query = "SELECT s FROM SurveyMeasureResponse s") })
public class SurveyMeasureResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "survey_measure_response_id")
	private Integer surveyMeasureResponseId;
	@Column(name = "boolean_value")
	private Boolean booleanValue;
	@Column(name = "number_value")
	private Long numberValue;
	@Column(name = "text_value")
	private String textValue;
	@Column(name = "other_value")
	private String otherValue;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "score")
	private Float score;
	@Column(name = "tabular_row")
	private Integer tabularRow;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Basic(optional = false)
    @Column(name = "date_modified")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
	@ManyToOne(optional = false)
	private Survey survey;
	@JoinColumn(name = "measure_id", referencedColumnName = "measure_id")
	@ManyToOne(optional = false)
	private Measure measure;
	@JoinColumn(name = "veteran_assessment_id", referencedColumnName = "veteran_assessment_id")
	@ManyToOne(optional = false)
	private VeteranAssessment veteranAssessment;
	@JoinColumn(name = "measure_answer_id", referencedColumnName = "measure_answer_id")
	@ManyToOne(optional = false)
	private MeasureAnswer measureAnswer;

	public SurveyMeasureResponse() {
	}

	public SurveyMeasureResponse(Integer surveyMeasureResponseId) {
		this.surveyMeasureResponseId = surveyMeasureResponseId;
	}

	public SurveyMeasureResponse(Integer surveyMeasureResponseId,
			Date dateCreated) {
		this.surveyMeasureResponseId = surveyMeasureResponseId;
		this.dateCreated = dateCreated;
	}

	public Integer getSurveyMeasureResponseId() {
		return surveyMeasureResponseId;
	}

	public void setSurveyMeasureResponseId(Integer surveyMeasureResponseId) {
		this.surveyMeasureResponseId = surveyMeasureResponseId;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public Long getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(Long numberValue) {
		this.numberValue = numberValue;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Integer getTabularRow() {
		return tabularRow;
	}

	public void setTabularRow(Integer tabularRow) {
		this.tabularRow = tabularRow;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
    
	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}
	public VeteranAssessment getVeteranAssessment() {
		return veteranAssessment;
	}

	public void setVeteranAssessment(VeteranAssessment veteranAssessment) {
		this.veteranAssessment = veteranAssessment;
	}

	public MeasureAnswer getMeasureAnswer() {
		return measureAnswer;
	}

	public void setMeasureAnswer(MeasureAnswer measureAnswer) {
		this.measureAnswer = measureAnswer;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (surveyMeasureResponseId != null ? surveyMeasureResponseId
				.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SurveyMeasureResponse)) {
			return false;
		}
		SurveyMeasureResponse other = (SurveyMeasureResponse) object;
		if ((this.surveyMeasureResponseId == null && other.surveyMeasureResponseId != null)
				|| (this.surveyMeasureResponseId != null && !this.surveyMeasureResponseId
						.equals(other.surveyMeasureResponseId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.SurveyMeasureResponse[ surveyMeasureResponseId="
				+ surveyMeasureResponseId + " ]";
	}

}
