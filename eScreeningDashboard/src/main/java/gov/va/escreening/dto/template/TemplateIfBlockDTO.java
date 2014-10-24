package gov.va.escreening.dto.template;

import java.util.List;

public class TemplateIfBlockDTO extends TemplateBaseBlockDTO {
	
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
	public String toFreeMarkerFormat()
	{
		StringBuffer sb = new StringBuffer();
		
		if (this.getName()!=null)
			sb.append("<#-- NAME:"+this.getName()+"-->\n");
		if (this.getSection()!=null)
			sb.append("<#-- SECTION:"+getSection()+" -->\n");
		if (this.getSummary()!=null)
			sb.append("<#-- SUMMARY:"+getSummary()+" -->\n");
		
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
