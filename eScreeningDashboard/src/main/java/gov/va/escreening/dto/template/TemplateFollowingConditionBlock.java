package gov.va.escreening.dto.template;

import gov.va.escreening.condition.BlockTranslator;
import gov.va.escreening.condition.BlockUtil;

import java.util.List;
import java.util.Set;

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
		if ("AND".equalsIgnoreCase(connector))
		{
			return " && ";
		}
		else
		{
			return " || ";
		}
	}
	
	public StringBuilder toFreeMarker(StringBuilder sb, Set<Integer>ids){
	    return appendTranslation(BlockUtil.getFreeMarkerTranslator(), sb, ids);
	}

	public StringBuilder toSpringEl(StringBuilder sb, Set<Integer>ids){
	    return appendTranslation(BlockUtil.getSpringElTranslator(), sb, ids);
	}
	
	public StringBuilder appendTranslation(BlockTranslator translator, StringBuilder sb, Set<Integer>ids){
	    StringBuilder result = sb;
        
        result.append(" ")
            .append(getOperatorForConnector())
            .append(" (")
            .append(BlockUtil.translateCondition(translator, operator, left, right, ids));
        
        if (conditions != null && conditions.size() > 0){
            
            for(TemplateFollowingConditionBlock tfcb : conditions){
                result = tfcb.appendTranslation(translator, result, ids);
            }
        }
        
        return result.append(" ) ");
	}
}

