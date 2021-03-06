package gov.va.escreening.domain;

import gov.va.escreening.entity.Survey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SurveyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer surveyId;
    private String name;
    private String description;
    private boolean isPublished;
    private Integer version;
    private Date dateCreated;
    private String note;
    private SurveySectionDto surveySection;
    private List<BatteryDto> batteryList;
    private Integer displayOrderForSection;

    public SurveyDto() {
    }

    public SurveyDto(Survey survey) {
        setSurveyId(survey.getSurveyId());
        setName(survey.getName());
        setDescription(survey.getDescription());
        isPublished = survey.isPublished();
        setVersion(survey.getVersion());
        setDateCreated(survey.getDateCreated());

        SurveySectionDto section = new SurveySectionDto(survey.getSurveySection());
        setSurveySection(section);
    }

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

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public SurveySectionDto getSurveySection() {
        return surveySection;
    }

    public void setSurveySection(SurveySectionDto surveySection) {
        this.surveySection = surveySection;
    }

    public List<BatteryDto> getBatteryList() {
        return batteryList;
    }

    public void setBatteryList(List<BatteryDto> batteryList) {
        this.batteryList = batteryList;
    }

    public Integer getDisplayOrderForSection() {
        return displayOrderForSection;
    }

    public void setDisplayOrderForSection(Integer displayOrderForSection) {
        this.displayOrderForSection = displayOrderForSection;
    }
}
