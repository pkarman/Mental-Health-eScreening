package gov.va.escreening.dto.template;

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
			TemplateBaseContent right) {
		
		switch (operand) {
		case "eq":
			return TemplateBaseContent.translate(operand, left, right) + " = " + TemplateBaseContent.translate(null, right, null);
		case "neq":
			return TemplateBaseContent.translate(operand, left, right) + " != " + TemplateBaseContent.translate(null, right, null);
		case "gt":
			return TemplateBaseContent.translate(operand, left, right) + " > " + TemplateBaseContent.translate(null, right, null);
		case "lt":
			return TemplateBaseContent.translate(operand, left, right) + " < " + TemplateBaseContent.translate(null, right, null);
		case "gte":
			return TemplateBaseContent.translate(operand, left, right) + " >= " + TemplateBaseContent.translate(null, right, null);
		case "lte":
			return TemplateBaseContent.translate(operand, left, right) + " <= " + TemplateBaseContent.translate(null, right, null);
		case "answered":
		case "nanswered":
		case "result":
		case "nresult":
		case "response":
		case "nresponse":
			return TemplateBaseContent.translate(operand, left, right);
		default:
			return "";
		}
	}

}
