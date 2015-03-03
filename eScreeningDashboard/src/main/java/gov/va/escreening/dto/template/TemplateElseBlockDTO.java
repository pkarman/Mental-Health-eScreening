package gov.va.escreening.dto.template;

import gov.va.escreening.service.AssessmentVariableService;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateElseBlockDTO extends TemplateBaseBlockDTO {
	
	@JsonProperty("type")
	private String nodeType(){return "else";}
	
	@Override
	public StringBuilder appendFreeMarkerFormat(AssessmentVariableService assessmentVariableService, StringBuilder sb, Set<Integer>ids){
		return addChildren(assessmentVariableService, new StringBuilder("\n<#else>\n"), ids);
	}
}
