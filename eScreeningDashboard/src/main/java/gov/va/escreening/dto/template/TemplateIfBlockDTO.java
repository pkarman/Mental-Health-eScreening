package gov.va.escreening.dto.template;

import gov.va.escreening.service.AssessmentVariableService;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateIfBlockDTO extends TemplateBaseBlockDTO {
	
	@JsonProperty("type")
	private String nodeType(){return "if";}
	
	private String operator;
	private TemplateBaseContent left;
	private TemplateBaseContent right;
	
	private List<TemplateFollowingConditionBlock> conditions;
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public TemplateBaseContent getLeft() {
		return left;
	}

	public void setLeft(TemplateBaseContent left) {
		this.left = left;
	}

	public TemplateBaseContent getRight() {
		return right;
	}

	public void setRight(TemplateBaseContent right) {
		this.right = right;
	}

	
	
	public List<TemplateFollowingConditionBlock> getConditions() {
		return conditions;
	}

	public void setConditions(List<TemplateFollowingConditionBlock> conditions) {
		this.conditions = conditions;
	}

	@Override
	public StringBuilder appendFreeMarkerFormat(AssessmentVariableService assessmentVariableService, StringBuilder sb, Set<Integer> ids) {
		addHeader(sb);
		
		sb.append("<#if ").append("(").append(FormulaUtil.createFormula(operator, left, right, ids)).append(")");
		
		if (conditions != null && conditions.size() > 0)
		{
			for(TemplateFollowingConditionBlock tfcb : conditions)
			{
				sb.append(tfcb.toFreeMarkerFormatFormula(ids));
			}
		}
		sb.append(" >\n");
		
		return addChildren(assessmentVariableService, sb, ids).append("\n</#if>\n");
	}
}
