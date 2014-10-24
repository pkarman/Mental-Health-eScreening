package gov.va.escreening.dto.template;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateFollowingConditionBlock{
	
	private TemplateBaseContent left;

	private String operator;

	private TemplateBaseContent right;

	private List<TemplateFollowingConditionBlock> conditions;

	public TemplateBaseContent getLeft() {
		return left;
	}

	public void setLeft(TemplateBaseContent left) {
		this.left = left;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	
	private String connector;

	public String getConnector() {
		return connector;
	}

	public void setConnector(String connector) {
		this.connector = connector;
	}
	
	private String getOperatorForConnector()
	{
		if ("AND".equals(connector))
		{
			return " && ";
		}
		else
		{
			return " || ";
		}
	}
	
	public String toFreeMarkerFormatFormula()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" ").append(getOperatorForConnector()).append(" (").append(FormulaUtil.createFormula(operator, left, right));
		
		if (conditions != null && conditions.size() > 0)
		{
			for(TemplateFollowingConditionBlock tfcb : conditions)
			{
				sb.append(tfcb.toFreeMarkerFormatFormula());
			}
		}
		
		sb.append(" ) ");
		return sb.toString();
	}
	
	

}
