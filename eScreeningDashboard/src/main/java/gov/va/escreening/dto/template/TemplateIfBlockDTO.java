package gov.va.escreening.dto.template;

import java.util.List;

public class TemplateIfBlockDTO extends TemplateBaseBlockDTO {
	
	public TemplateIfBlockDTO()
	{
		setType("if");
	}

	private String operator;
	private String left;
	private String right;
	
	private List<TemplateFollowingConditionBlock> conditions;
	
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
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

	@Override
	public String toFreeMarkerFormat()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<#if ").append("(").append(FormulaUtil.createFormula(operator, left, right)).append(")");
		
		if (conditions != null && conditions.size() > 0)
		{
			for(TemplateFollowingConditionBlock tfcb : conditions)
			{
				sb.append(tfcb.toFreeMarkerFormatFormula());
			}
		}
		sb.append(" >\n");
		if (getChildren()!=null)
		{
			for(INode child : getChildren())
			{
				sb.append(child.toFreeMarkerFormat());
			}
		}
		sb.append("\n</#if>\n");
		
		return sb.toString();
	}
	
	
}
