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
@Table(name = "measure_validation")
@NamedQueries({ @NamedQuery(name = "MeasureValidation.findAll", query = "SELECT m FROM MeasureValidation m") })
public class MeasureValidation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "measure_validation_id")
	private Integer measureValidationId;
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
	@JoinColumn(name = "measure_id", referencedColumnName = "measure_id")
	@ManyToOne(optional = false)
	private Measure measure;
	@JoinColumn(name = "validation_id", referencedColumnName = "validation_id")
	@ManyToOne(optional = false)
	private Validation validation;

	public MeasureValidation() {
	}

	public MeasureValidation(Integer measureValidationId) {
		this.measureValidationId = measureValidationId;
	}

	public MeasureValidation(Integer measureValidationId, Date dateCreated) {
		this.measureValidationId = measureValidationId;
		this.dateCreated = dateCreated;
	}

	public Integer getMeasureValidationId() {
		return measureValidationId;
	}

	public void setMeasureValidationId(Integer measureValidationId) {
		this.measureValidationId = measureValidationId;
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

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
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
		hash += (measureValidationId != null ? measureValidationId.hashCode()
				: 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof MeasureValidation)) {
			return false;
		}
		MeasureValidation other = (MeasureValidation) object;
		if ((this.measureValidationId == null && other.measureValidationId != null)
				|| (this.measureValidationId != null && !this.measureValidationId
						.equals(other.measureValidationId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.MeasureValidation[ measureValidationId="
				+ measureValidationId + " ]";
	}

}
