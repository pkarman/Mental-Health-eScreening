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
@Table(name = "measure_type")
@NamedQueries({ @NamedQuery(name = "MeasureType.findAll", query = "SELECT m FROM MeasureType m") })
public class MeasureType implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "measure_type_id")
	private Integer measureTypeId;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "measureType")
	private List<Measure> measureList;

	public MeasureType() {
	}

	public MeasureType(Integer measureTypeId) {
		this.measureTypeId = measureTypeId;
	}

	public MeasureType(Integer measureTypeId, String name, Date dateCreated) {
		this.measureTypeId = measureTypeId;
		this.name = name;
		this.dateCreated = dateCreated;
	}

	public Integer getMeasureTypeId() {
		return measureTypeId;
	}

	public void setMeasureTypeId(Integer measureTypeId) {
		this.measureTypeId = measureTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<Measure> getMeasureList() {
		return measureList;
	}

	public void setMeasureList(List<Measure> measureList) {
		this.measureList = measureList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (measureTypeId != null ? measureTypeId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof MeasureType)) {
			return false;
		}
		MeasureType other = (MeasureType) object;
		if ((this.measureTypeId == null && other.measureTypeId != null)
				|| (this.measureTypeId != null && !this.measureTypeId
						.equals(other.measureTypeId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.MeasureType[ measureTypeId="
				+ measureTypeId + " ]";
	}

}
