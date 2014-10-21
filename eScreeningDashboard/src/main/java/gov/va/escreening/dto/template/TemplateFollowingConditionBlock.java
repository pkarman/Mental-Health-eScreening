package gov.va.escreening.dto.template;

import java.util.List;

public class TemplateFollowingConditionBlock{
	
	private String left;

	private String operator;

	private String right;

	private List<TemplateFollowingConditionBlock> conditions;

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
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
