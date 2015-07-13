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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "veteran_assessment_action")
@NamedQueries({ @NamedQuery(name = "VeteranAssessmentAction.findAll", query = "SELECT v FROM VeteranAssessmentAction v") })
public class VeteranAssessmentAction implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "veteran_assessment_action_id")
	private Integer veteranAssessmentActionId;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "veteranAssessmentAction")
	private List<VeteranAssessmentLog> veteranAssessmentLogList;

	public VeteranAssessmentAction() {
	}

	public VeteranAssessmentAction(Integer veteranAssessmentActionId) {
		this.veteranAssessmentActionId = veteranAssessmentActionId;
	}

	public VeteranAssessmentAction(Integer veteranAssessmentActionId,
			String name, Date dateCreated) {
		this.veteranAssessmentActionId = veteranAssessmentActionId;
		this.name = name;
		this.dateCreated = dateCreated;
	}

	public Integer getVeteranAssessmentActionId() {
		return veteranAssessmentActionId;
	}

	public void setVeteranAssessmentActionId(Integer veteranAssessmentActionId) {
		this.veteranAssessmentActionId = veteranAssessmentActionId;
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

	public List<VeteranAssessmentLog> getVeteranAssessmentLogList() {
		return veteranAssessmentLogList;
	}

	public void setVeteranAssessmentLogList(
			List<VeteranAssessmentLog> veteranAssessmentLogList) {
		this.veteranAssessmentLogList = veteranAssessmentLogList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (veteranAssessmentActionId != null ? veteranAssessmentActionId
				.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof VeteranAssessmentAction)) {
			return false;
		}
		VeteranAssessmentAction other = (VeteranAssessmentAction) object;
		if ((this.veteranAssessmentActionId == null && other.veteranAssessmentActionId != null)
				|| (this.veteranAssessmentActionId != null && !this.veteranAssessmentActionId
						.equals(other.veteranAssessmentActionId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.VeteranAssessmentAction[ veteranAssessmentActionId="
				+ veteranAssessmentActionId + " ]";
	}

}
