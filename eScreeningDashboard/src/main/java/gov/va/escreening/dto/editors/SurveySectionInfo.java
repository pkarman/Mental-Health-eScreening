package gov.va.escreening.dto.editors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.va.escreening.entity.SurveySectionBaseProperties;
import gov.va.escreening.serializer.JsonDateSerializer;

@JsonRootName(value="surveySection")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveySectionInfo implements Serializable, SurveySectionBaseProperties {

	private static final long serialVersionUID = 1L;

    private Integer surveySectionId;
	private String name;
	private String description;
	private Integer displayOrder;
    private Date dateCreated;
    private List<SurveyInfo> surveyInfoList = new ArrayList<SurveyInfo>();

    @JsonProperty("id")
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

    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonProperty("createdDate")
    public Date getDateCreated() {
		return dateCreated;
	}

    public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

    public SurveySectionInfo() {

	}

	@Override
	public String toString() {
		return "SurveySectionItem [surveySectionId=" + surveySectionId + ", name=" + name + ", description=" + description + ", displayOrder=" + displayOrder + ", dateCreated=" + dateCreated + "]";
	}

    @JsonProperty("surveys")
    public List<SurveyInfo> getSurveyInfoList() {
		return surveyInfoList;
	}

    public void setSurveyInfoList(List<SurveyInfo> surveyInfoList) {
		this.surveyInfoList = surveyInfoList;
	}
}
