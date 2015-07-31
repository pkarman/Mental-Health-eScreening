package gov.va.escreening.dto;

import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.Date;

public class VariableTemplateDTO {

	private Integer variableTemplateId;
    private String overrideDisplayValue;
    private Date dateCreated;
    private AssessmentVariableDto assessmentVariableId;
    
	public Integer getVariableTemplateId() {
		return variableTemplateId;
	}
	public void setVariableTemplateId(Integer variableTemplateId) {
		this.variableTemplateId = variableTemplateId;
	}
	public String getOverrideDisplayValue() {
		return overrideDisplayValue;
	}
	public void setOverrideDisplayValue(String overrideDisplayValue) {
		this.overrideDisplayValue = overrideDisplayValue;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public AssessmentVariableDto getAssessmentVariableId() {
		return assessmentVariableId;
	}
	public void setAssessmentVariableId(AssessmentVariableDto assessmentVariableId) {
		this.assessmentVariableId = assessmentVariableId;
	}

}
