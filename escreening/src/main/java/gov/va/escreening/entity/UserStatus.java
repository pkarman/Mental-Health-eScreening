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
@Table(name = "user_status")
@NamedQueries({ @NamedQuery(name = "UserStatus.findAll", query = "SELECT u FROM UserStatus u") })
public class UserStatus implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "user_status_id")
	private Integer userStatusId;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "userStatus")
	private List<User> userList;

	public UserStatus() {
	}

	public UserStatus(Integer userStatusId) {
		this.userStatusId = userStatusId;
	}

	public UserStatus(Integer userStatusId, String name, Date dateCreated) {
		this.userStatusId = userStatusId;
		this.name = name;
		this.dateCreated = dateCreated;
	}

	public Integer getUserStatusId() {
		return userStatusId;
	}

	public void setUserStatusId(Integer userStatusId) {
		this.userStatusId = userStatusId;
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

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userStatusId != null ? userStatusId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof UserStatus)) {
			return false;
		}
		UserStatus other = (UserStatus) object;
		if ((this.userStatusId == null && other.userStatusId != null)
				|| (this.userStatusId != null && !this.userStatusId
						.equals(other.userStatusId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.UserStatus[ userStatusId="
				+ userStatusId + " ]";
	}

}
