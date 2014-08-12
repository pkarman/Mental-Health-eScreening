package gov.va.escreening.domain;

import java.io.Serializable;

public class BatterySurveyDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer batterySurveyId;
    private Integer batteryId;
    private String batteryName;
    private Integer surveyId;
    private String surveyName;
    private String description;

    public Integer getBatterySurveyId() {
        return batterySurveyId;
    }

    public void setBatterySurveyId(Integer batterySurveyId) {
        this.batterySurveyId = batterySurveyId;
    }

    public Integer getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(Integer batteryId) {
        this.batteryId = batteryId;
    }

    public String getBatteryName() {
        return batteryName;
    }

    public void setBatteryName(String batteryName) {
        this.batteryName = batteryName;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public BatterySurveyDto() {

    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
