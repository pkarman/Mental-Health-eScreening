package gov.va.escreening.dto.editors;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.va.escreening.entity.SurveyBaseProperties;
import gov.va.escreening.serializer.JsonDateSerializer;

@JsonRootName(value="survey")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"isIncludedInBattery", "surveyStatusItemInfo"})
public class SurveyInfo implements Serializable, SurveyBaseProperties {

    private static final long serialVersionUID = 1L;


    private Integer surveyId;
    private String name;
    private String vistaTitle;
    private String description;
    private Integer version;
    private Integer displayOrder;
    private boolean hasMha;
    private String mhaTestName;
    private String mhaResultGroupIen;
    private Boolean clinicalReminder;
	private Date dateCreated;
    private Boolean isIncludedInBattery;
    private SurveySectionInfo surveySectionInfo;
    private SurveyStatusInfo surveyStatusInfo;


    @JsonProperty("id")
    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isMha() {
        return hasMha;
    }

    public void setMha(boolean hasMha) {
        this.hasMha = hasMha;
    }

    public String getMhaTestName() {
        return mhaTestName;
    }

    public void setMhaTestName(String mhaTestName) {
        this.mhaTestName = mhaTestName;
    }

    public String getMhaResultGroupIen() {
        return mhaResultGroupIen;
    }

    public void setMhaResultGroupIen(String mhaResultGroupIen) {
        this.mhaResultGroupIen = mhaResultGroupIen;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonProperty("createdDate")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String getVistaTitle() {
        return vistaTitle;
    }

    @Override
    public void setVistaTitle(String vistaTitle) {
        this.vistaTitle = vistaTitle;
    }

    @Override
    public Boolean isClinicalReminder() {
        return this.clinicalReminder;
    }

    public void setClinicalReminder(boolean clinicalReminder) {
        this.clinicalReminder = clinicalReminder;
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
        return "SurveyItem [surveyId=" + surveyId + ", name=" + name + ", description=" + description + ", version="
                + version + ", displayOrder=" + displayOrder + ", isMha=" + hasMha + ", mhaTestName=" + mhaTestName
                + ", mhaResultGroupIen=" + mhaResultGroupIen + ", dateCreated=" + dateCreated
                + ", isIncludedInBattery=" + isIncludedInBattery + /*", surveySectionItem=" + surveySectionItem
                + ", surveyStatusItem=" + surveyStatusItem + */"]";
    }
}
