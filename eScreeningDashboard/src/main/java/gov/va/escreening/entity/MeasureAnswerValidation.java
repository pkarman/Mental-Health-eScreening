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
@Table(name = "measure_answer_validation")
@NamedQueries({ @NamedQuery(name = "MeasureAnswerValidation.findAll", query = "SELECT m FROM MeasureAnswerValidation m") })
public class MeasureAnswerValidation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "measure_answer_validation_id")
	private Integer measureAnswerValidationId;
	@Column(name = "boolean_value")
	private Integer booleanValue;
	@Column(name = "number_value")
	private Integer numberValue;
	@Column(name = "text_value")
	private String textValue;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@JoinColumn(name = "measure_answer_id", referencedColumnName = "measure_answer_id")
	@ManyToOne(optional = false)
	private MeasureAnswer measureAnswer;
	@JoinColumn(name = "validation_id", referencedColumnName = "validation_id")
	@ManyToOne(optional = false)
	private Validation validation;

	public MeasureAnswerValidation() {
	}

	public MeasureAnswerValidation(Integer measureAnswerValidationId) {
		this.measureAnswerValidationId = measureAnswerValidationId;
	}

	public MeasureAnswerValidation(Integer measureAnswerValidationId,
			Date dateCreated) {
		this.measureAnswerValidationId = measureAnswerValidationId;
		this.dateCreated = dateCreated;
	}

	public Integer getMeasureAnswerValidationId() {
		return measureAnswerValidationId;
	}

	public void setMeasureAnswerValidationId(Integer measureAnswerValidationId) {
		this.measureAnswerValidationId = measureAnswerValidationId;
	}

	public Integer getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(Integer booleanValue) {
		this.booleanValue = booleanValue;
	}

	public Integer getNumberValue() {
		return numberValue;
	}

	public void setNumberValue(Integer numberValue) {
		this.numberValue = numberValue;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public MeasureAnswer getMeasureAnswer() {
		return measureAnswer;
	}

	public void setMeasureAnswer(MeasureAnswer measureAnswer) {
		this.measureAnswer = measureAnswer;
	}

	public Validation getValidation() {
		return validation;
	}

	public void setValidation(Validation validation) {
		this.validation = validation;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (measureAnswerValidationId != null ? measureAnswerValidationId
				.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof MeasureAnswerValidation)) {
			return false;
		}
		MeasureAnswerValidation other = (MeasureAnswerValidation) object;
		if ((this.measureAnswerValidationId == null && other.measureAnswerValidationId != null)
				|| (this.measureAnswerValidationId != null && !this.measureAnswerValidationId
						.equals(other.measureAnswerValidationId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.MeasureAnswerValidation[ measureAnswerValidationId="
				+ measureAnswerValidationId + " ]";
	}

}
