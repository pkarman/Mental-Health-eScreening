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
	public StringBuilder appendFreeMarkerFormat(StringBuilder sb, Set<Integer>avIds, AssessmentVariableService assessmentVariableService){
		return addChildren(sb.append("\n<#else>\n"), avIds, assessmentVariableService);
	}
}
