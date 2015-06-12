package gov.va.escreening.dto.template;

import gov.va.escreening.condition.BlockUtil;
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
	public StringBuilder appendFreeMarkerFormat(StringBuilder sb, Set<Integer> ids, AssessmentVariableService assessmentVariableService) {
		StringBuilder result = addHeader(sb);
		
		result.append("<#if ").append("(").append(BlockUtil.conditionToFreeMarker(operator, left, right, ids)).append(")");
		
		if (conditions != null && conditions.size() > 0){
			for(TemplateFollowingConditionBlock tfcb : conditions){
				result = tfcb.toFreeMarker(result, ids);
			}
		}
		result.append(" >\n");
		
		return addChildren(result, ids, assessmentVariableService).append("\n</#if>\n");
	}

    /**
     * Appends the Spring EL translation of this block.
     * @param sb
     * @param AvIds
     * @return
     */
    public StringBuilder translateToSpringEl(StringBuilder sb, Set<Integer> avIds){
        StringBuilder result = sb;
        
        result.append(BlockUtil.conditionToSpringEl(operator, left, right, avIds));
        
        if (conditions != null && conditions.size() > 0){
            for(TemplateFollowingConditionBlock tfcb : conditions) {
                result = tfcb.toSpringEl(result, avIds);
            }
        }
        return result;
    }
}
