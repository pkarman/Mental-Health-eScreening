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
	public StringBuilder appendFreeMarkerFormat(AssessmentVariableService assessmentVariableService, StringBuilder sb, Set<Integer>ids) {

		sb.append("\n<#elseif ( ")
			.append(FormulaUtil.createFormula(getOperator(), getLeft(),
						getRight(), ids)).append(")");

		if (getConditions() != null && getConditions().size() > 0) {
			for (TemplateFollowingConditionBlock tfcb : getConditions()) {
				sb.append(tfcb.toFreeMarkerFormatFormula(ids));
			}
		}
		sb.append(" >\n");

		return addChildren(assessmentVariableService, sb, ids);
	}
}
