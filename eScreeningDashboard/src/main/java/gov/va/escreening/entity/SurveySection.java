/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.va.escreening.entity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
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
@Table(name = "survey_section")
@NamedQueries({ @NamedQuery(name = "SurveySection.findAll", query = "SELECT s FROM SurveySection s") })
public class SurveySection implements Serializable, SurveySectionBaseProperties {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "survey_section_id")
	private Integer surveySectionId;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
    @Column(name = "description")
    private String description;
	@Column(name = "display_order")
	private Integer displayOrder;
	@Basic(optional = false)
	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@OneToMany(mappedBy = "surveySection")
	private List<Survey> surveyList;

	public SurveySection() {
	}

	public SurveySection(Integer surveySectionId) {
		this.surveySectionId = surveySectionId;
	}

	public SurveySection(Integer surveySectionId, String name,
			Date dateCreated) {
		this.surveySectionId = surveySectionId;
		this.name = name;
		this.dateCreated = dateCreated;
	}

	public Integer getSurveySectionId() {
		return surveySectionId;
	}

	public void setSurveySectionId(Integer surveySectionId) {
		this.surveySectionId = surveySectionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

	public List<Survey> getSurveyList() {
		return surveyList;
	}

    private void setSurveyList(List<Survey> surveyLists) {
        this.surveyList = surveyLists;
    }

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (surveySectionId != null ? surveySectionId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof SurveySection)) {
			return false;
		}
		SurveySection other = (SurveySection) object;
		if ((this.surveySectionId == null && other.surveySectionId != null)
				|| (this.surveySectionId != null && !this.surveySectionId
						.equals(other.surveySectionId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "gov.va.escreening.entity.SurveySection[ surveySectionId="
				+ surveySectionId + " ]";
	}

}
