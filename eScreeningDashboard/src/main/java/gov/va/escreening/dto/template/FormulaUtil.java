package gov.va.escreening.dto.template;

import java.util.Set;

public class FormulaUtil {
	
	/*public String toFreeMarkerFormatFormula(String operator, String left, String right) {
		StringBuffer sb = new StringBuffer();

		sb.append("(").append(createFormula(operator, left, right)).append(")");

		if (conditions != null && conditions.size() > 0) {
			for (TemplateFollowingConditionBlock condition : conditions) {
				sb.append(condition.toFreeMarkerFormatFormula());
			}
		}

		return sb.toString();
	}*/
	
	public static String createFormula(String operand, TemplateBaseContent left,
			TemplateBaseContent right, Set<Integer> ids) {
		
		switch (operand) {
		case "eq":
			return TemplateBaseContent.translate(operand, left, right, ids) + " = " + TemplateBaseContent.translate(null, right, null, ids);
		case "neq":
			return TemplateBaseContent.translate(operand, left, right, ids) + " != " + TemplateBaseContent.translate(null, right, null, ids);
		case "gt":
			return TemplateBaseContent.translate(operand, left, right, ids) + " > " + TemplateBaseContent.translate(null, right, null, ids);
		case "lt":
			return TemplateBaseContent.translate(operand, left, right, ids) + " < " + TemplateBaseContent.translate(null, right, null, ids);
		case "gte":
			return TemplateBaseContent.translate(operand, left, right, ids) + " >= " + TemplateBaseContent.translate(null, right, null, ids);
		case "lte":
			return TemplateBaseContent.translate(operand, left, right, ids) + " <= " + TemplateBaseContent.translate(null, right, null, ids);
		case "answered":
		case "nanswered":
		case "result":
		case "nresult":
		case "response":
		case "nresponse":
			return TemplateBaseContent.translate(operand, left, right, ids);
		default:
			return "";
		}
	}

}
