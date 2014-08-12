package gov.va.escreening.dto.editors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.va.escreening.entity.BatteryBaseProperties;
import gov.va.escreening.serializer.JsonDateSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonRootName(value="battery")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatteryInfo implements Serializable, BatteryBaseProperties {
    private static final long serialVersionUID = 1L;

    private Integer batteryId;
    private String name;
    private String description;
    private Boolean isDisabled;
    private List<SurveyInfo> surveys = new ArrayList<SurveyInfo>();
    private Date dateCreated;

    @JsonProperty("id")
    public Integer getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(Integer batteryId) {
        this.batteryId = batteryId;
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

    public Boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonProperty("createdDate")
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<SurveyInfo> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyInfo> surveys) {
        this.surveys = surveys;
    }

    public BatteryInfo() {

    }

    @Override
    public String toString() {
        return "BatteryItem [batteryId=" + batteryId + ", name=" + name + ", description=" + description
                + ", isDisabled=" + isDisabled + ", surveys=" + surveys + ", dateCreated=" + dateCreated + "]";
    }

}
