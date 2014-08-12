package gov.va.escreening.domain;

import gov.va.escreening.entity.SurveySection;

import java.io.Serializable;
import java.util.Date;

public class SurveySectionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer surveySectionId;
    private String name;
    private Integer displayOrder;
    private Date dateCreated;
    
    public SurveySectionDto() {}
    
    public SurveySectionDto(SurveySection section) {
    	setSurveySectionId(section.getSurveySectionId());
    	setName(section.getName());
    	setDisplayOrder(section.getDisplayOrder());
    	setDateCreated(section.getDateCreated());
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
}
