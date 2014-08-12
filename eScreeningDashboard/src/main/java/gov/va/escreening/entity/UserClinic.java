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
@Table(name = "user_clinic")
@NamedQueries({ @NamedQuery(name = "UserClinic.findAll", query = "SELECT u FROM UserClinic u") })
public class UserClinic implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "user_clinic_id")
	private Integer userClinicId;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	@ManyToOne(optional = false)
	private User user;
	@JoinColumn(name = "clinic_id", referencedColumnName = "clinic_id")
	@ManyToOne(optional = false)
	private Clinic clinic;

	public UserClinic() {
	}

	public UserClinic(Integer userClinicId) {
		this.userClinicId = userClinicId;
	}

	public UserClinic(Integer userClinicId, Date dateCreated) {
		this.userClinicId = userClinicId;
		this.dateCreated = dateCreated;
	}

	public Integer getUserClinicId() {
		return userClinicId;
	}

	public void setUserClinicId(Integer userClinicId) {
		this.userClinicId = userClinicId;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userClinicId != null ? userClinicId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof UserClinic)) {
			return false;
		}
		UserClinic other = (UserClinic) object;
		if ((this.userClinicId == null && other.userClinicId != null)
				|| (this.userClinicId != null && !this.userClinicId
						.equals(other.userClinicId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.UserClinic[ userClinicId="
				+ userClinicId + " ]";
	}

}
