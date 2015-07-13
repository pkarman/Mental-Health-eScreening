/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author jocchiuzzo
 */
@Entity
@Table(name = "validation")
@NamedQueries({ @NamedQuery(name = "Validation.findAll", query = "SELECT v FROM Validation v") })
public class Validation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "validation_id")
	private Integer validationId;
	@Basic(optional = false)
	@Column(name = "code")
	private String code;
	@Basic(optional = false)
	@Column(name = "description")
	private String description;
	@Basic(optional = false)
	@Column(name = "data_type")
	private String dataType;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "validation")
	private List<MeasureAnswerValidation> measureAnswerValidationList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "validation")
	private List<MeasureValidation> measureValidationList;

	public Validation() {
	}

	public Validation(Integer validationId) {
		this.validationId = validationId;
	}

	public Validation(Integer validationId, String code, String description,
			String dataType, Date dateCreated) {
		this.validationId = validationId;
		this.code = code;
		this.description = description;
		this.dataType = dataType;
		this.dateCreated = dateCreated;
	}

	public Integer getValidationId() {
		return validationId;
	}

	public void setValidationId(Integer validationId) {
		this.validationId = validationId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<MeasureAnswerValidation> getMeasureAnswerValidationList() {
		return measureAnswerValidationList;
	}

	public void setMeasureAnswerValidationList(
			List<MeasureAnswerValidation> measureAnswerValidationList) {
		this.measureAnswerValidationList = measureAnswerValidationList;
	}

	public List<MeasureValidation> getMeasureValidationList() {
		return measureValidationList;
	}

	public void setMeasureValidationList(
			List<MeasureValidation> measureValidationList) {
		this.measureValidationList = measureValidationList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (validationId != null ? validationId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Validation)) {
			return false;
		}
		Validation other = (Validation) object;
		if ((this.validationId == null && other.validationId != null)
				|| (this.validationId != null && !this.validationId
						.equals(other.validationId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.Validation[ validationId="
				+ validationId + " ]";
	}

}
