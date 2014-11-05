package gov.va.escreening.dto.template;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateElseIfBlockDTO extends TemplateIfBlockDTO {

	@Override
	public String toFreeMarkerFormat(Set<Integer>ids) {

		StringBuffer sb = new StringBuffer();

		sb.append("<#elseif ")
				.append("(")
				.append(FormulaUtil.createFormula(getOperator(), getLeft(),
						getRight(), ids)).append(")");

		if (getConditions() != null && getConditions().size() > 0) {
			for (TemplateFollowingConditionBlock tfcb : getConditions()) {
				sb.append(tfcb.toFreeMarkerFormatFormula(ids));
			}
		}
		sb.append(" >\n");

		return sb.toString();
	}
	
	@JsonProperty("type")
	private String nodeType(){return "elseif";}
	
	

}
