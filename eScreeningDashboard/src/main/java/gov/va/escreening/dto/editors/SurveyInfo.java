package gov.va.escreening.dto.editors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Lists;
import gov.va.escreening.entity.SurveyBaseProperties;
import gov.va.escreening.serializer.JsonDateSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonRootName(value="survey")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"isIncludedInBattery", "surveyStatusItemInfo"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyInfo implements Serializable, SurveyBaseProperties {

    private static final long serialVersionUID = 1L;

    private Integer surveyId;
    private String name;
    private String description;
    private Boolean isPublished;
    private Integer version;
    private boolean hasMha;
    private String mhaTestName;
    private Integer displayOrderForSection=1;
	private Date dateCreated;
    private Boolean isIncludedInBattery;
    private SurveySectionInfo surveySectionInfo;
    private SurveyStatusInfo surveyStatusInfo;
    private List<Integer> clinicalReminderIdList=Lists.newArrayList();


    public List<Integer> getClinicalReminderIdList() {
		return clinicalReminderIdList;
	}

	public void setClinicalReminderIdList(List<Integer> clinicalReminderIdList) {
		this.clinicalReminderIdList = clinicalReminderIdList;
	}

	@JsonProperty("id")
	@Override
	public Integer getSurveyId() {
        return surveyId;
    }

	@Override
    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }
    
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Boolean getIsPublished() {
        return isPublished;
    }

    @Override
    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean isMha() {
        return hasMha;
    }

    @Override
    public void setMha(boolean hasMha) {
        this.hasMha = hasMha;
    }

    @Override
    public String getMhaTestName() {
        return mhaTestName;
    }

    @Override
    public void setMhaTestName(String mhaTestName) {
        this.mhaTestName = mhaTestName;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonProperty("createdDate")
    @Override
    public Date getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public void setDisplayOrderForSection(Integer displayOrder) {
        this.displayOrderForSection=displayOrder;
    }

    @Override
    public Integer getDisplayOrderForSection() {
        return this.displayOrderForSection;
    }

    public Boolean getIsIncludedInBattery() {
        return isIncludedInBattery;
    }

    public void setIsIncludedInBattery(Boolean isIncludedInBattery) {
        this.isIncludedInBattery = isIncludedInBattery;
    }

    @JsonProperty("surveySection")
    public SurveySectionInfo getSurveySectionInfo() {
        return surveySectionInfo;
    }

    public void setSurveySectionInfo(SurveySectionInfo surveySectionInfo) {
        this.surveySectionInfo = surveySectionInfo;
    }

    public SurveyStatusInfo getSurveyStatusInfo() {
        return surveyStatusInfo;
    }

    public void setSurveyStatusInfo(SurveyStatusInfo surveyStatusInfo) {
        this.surveyStatusInfo = surveyStatusInfo;
    }

    public SurveyInfo() {

    }

    @Override
    public String toString() {
        return "SurveyItem [surveyId=" + surveyId 
                + ", name=" + name 
                + ", description=" + description 
                + ", isPublished=" + isPublished
                + ", version=" + version 
                + ", isMha=" + hasMha 
                + ", mhaTestName=" + mhaTestName
                + ", dateCreated=" + dateCreated
                + ", isIncludedInBattery=" + isIncludedInBattery
                + "]";
    }
}
