package gov.va.escreening.dto.editors;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyStatusInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer surveyStatusId;
    private String name;

    public Integer getSurveyStatusId() {
        return surveyStatusId;
    }

    public void setSurveyStatusId(Integer surveyStatusId) {
        this.surveyStatusId = surveyStatusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SurveyStatusInfo() {

    }

    public SurveyStatusInfo(Integer surveyStatusId, String name) {
        this.surveyStatusId = surveyStatusId;
        this.name = name;
    }

}
