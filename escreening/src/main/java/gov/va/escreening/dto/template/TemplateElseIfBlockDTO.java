package gov.va.escreening.dto.template;

import gov.va.escreening.condition.BlockUtil;
import gov.va.escreening.service.AssessmentVariableService;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateElseIfBlockDTO extends TemplateIfBlockDTO {

	@JsonProperty("type")
	private String nodeType(){return "elseif";}
	
	@Override
	public StringBuilder appendFreeMarkerFormat(StringBuilder sb, Set<Integer>avIds, AssessmentVariableService assessmentVariableService) {
	    StringBuilder result = sb;
	    
		result.append("\n<#elseif ( ")
			.append(BlockUtil.conditionToFreeMarker(getOperator(), getLeft(),
						getRight(), avIds)).append(")");

		if (getConditions() != null && getConditions().size() > 0) {
			for (TemplateFollowingConditionBlock tfcb : getConditions()) {
			    result = tfcb.toFreeMarker(result, avIds);
			}
		}
		result.append(" >\n");

		return addChildren(result, avIds, assessmentVariableService);
	}
}
