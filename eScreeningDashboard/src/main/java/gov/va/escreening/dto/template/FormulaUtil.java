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
	
	public static String createFormula(String operator, String left,
			String right) {
		
		switch (operator) {
		case "eq":
			return left + " = " + right;
		case "neq":
			return left + " != " + right;
		case "gt":
			return left + " > " + right;
		case "lt":
			return left + " < " + right;
		case "gte":
			return left + " >= " + right;
		case "lte":
			return left + " <= " + right;
		case "answered":
			return left + "??";
		case "nanswered":
			return "!" + left + "??";
		case "result":
			return "hasValue(" + left + ")";
		case "nresult":
			return "!hasValue(" + left + ")";
		case "response":
			return "";
		case "nresponse":
			return "";
		default:
			return "";
		}
	}

}
