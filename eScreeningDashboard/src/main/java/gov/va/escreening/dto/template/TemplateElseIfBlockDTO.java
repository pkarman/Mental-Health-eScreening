package gov.va.escreening.dto.template;

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

		sb.append("\n<#elseif ( ")
			.append(FormulaUtil.createFormula(getOperator(), getLeft(),
						getRight(), avIds)).append(")");

		if (getConditions() != null && getConditions().size() > 0) {
			for (TemplateFollowingConditionBlock tfcb : getConditions()) {
				sb.append(tfcb.toFreeMarkerFormatFormula(avIds));
			}
		}
		sb.append(" >\n");

		return addChildren(sb, avIds, assessmentVariableService);
	}
}
