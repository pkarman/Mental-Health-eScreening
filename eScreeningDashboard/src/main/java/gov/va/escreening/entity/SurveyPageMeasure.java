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
@Table(name = "survey_page_measure")
@NamedQueries({ @NamedQuery(name = "SurveyPageMeasure.findAll", query = "SELECT s FROM SurveyPageMeasure s") })
public class SurveyPageMeasure implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "survey_page_measure_id")
	private Integer surveyPageMeasureId;
	@Column(name = "display_order")
	private Integer displayOrder;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@JoinColumn(name = "survey_page_id", referencedColumnName = "survey_page_id")
	@ManyToOne(optional = false)
	private SurveyPage surveyPage;
	@JoinColumn(name = "measure_id", referencedColumnName = "measure_id")
	@ManyToOne(optional = false)
	private Measure measure;

	public SurveyPageMeasure() {
	}

	public SurveyPageMeasure(Integer surveyPageMeasureId) {
		this.surveyPageMeasureId = surveyPageMeasureId;
	}

	public SurveyPageMeasure(Integer surveyPageMeasureId, Date dateCreated) {
		this.surveyPageMeasureId = surveyPageMeasureId;
		this.dateCreated = dateCreated;
	}

	public Integer getSurveyPageMeasureId() {
		return surveyPageMeasureId;
	}

	public void setSurveyPageMeasureId(Integer surveyPageMeasureId) {
		this.surveyPageMeasureId = surveyPageMeasureId;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public SurveyPage getSurveyPage() {
		return surveyPage;
	}

	public void setSurveyPage(SurveyPage surveyPage) {
		this.surveyPage = surveyPage;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (surveyPageMeasureId != null ? surveyPageMeasureId.hashCode()
				: 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SurveyPageMeasure)) {
			return false;
		}
		SurveyPageMeasure other = (SurveyPageMeasure) object;
		if ((this.surveyPageMeasureId == null && other.surveyPageMeasureId != null)
				|| (this.surveyPageMeasureId != null && !this.surveyPageMeasureId
						.equals(other.surveyPageMeasureId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.SurveyPageMeasure[ surveyPageMeasureId="
				+ surveyPageMeasureId + " ]";
	}

}
