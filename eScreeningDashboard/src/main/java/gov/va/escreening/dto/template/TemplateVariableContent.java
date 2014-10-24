package gov.va.escreening.dto.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateVariableContent extends TemplateBaseContent{

	private TemplateAssessmentVariableDTO content;
	
	public TemplateAssessmentVariableDTO getContent() {
		return content;
	}

	public void setContent(TemplateAssessmentVariableDTO content) {
		this.content = content;
	}

}
