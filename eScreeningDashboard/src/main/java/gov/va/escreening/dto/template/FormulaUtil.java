package gov.va.escreening.dto.template;

import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.exception.EscreeningDataValidationException;

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
	
	public static String createFormula(String operator, TemplateBaseContent left,
			TemplateBaseContent right, Set<Integer> ids) {
		
		switch (operator) {
		case "eq":
			return TemplateBaseContent.translate(operator, left, right, ids) + " = " + TemplateBaseContent.translate(null, right, null, ids);
		case "neq":
			return TemplateBaseContent.translate(operator, left, right, ids) + " != " + TemplateBaseContent.translate(null, right, null, ids);
		case "gt":
			return TemplateBaseContent.translate(operator, left, right, ids) + " > " + TemplateBaseContent.translate(null, right, null, ids);
		case "lt":
			return TemplateBaseContent.translate(operator, left, right, ids) + " < " + TemplateBaseContent.translate(null, right, null, ids);
		case "gte":
			return TemplateBaseContent.translate(operator, left, right, ids) + " >= " + TemplateBaseContent.translate(null, right, null, ids);
		case "lte":
			return TemplateBaseContent.translate(operator, left, right, ids) + " <= " + TemplateBaseContent.translate(null, right, null, ids);
		case "answered":
		case "nanswered":
		case "result":
		case "nresult":
		case "response":
		case "nresponse":
		case "none":
		case "nnone":
			return TemplateBaseContent.translate(operator, left, right, ids);
		default:
		    ErrorBuilder.throwing(EscreeningDataValidationException.class)
            .toAdmin("Operator: '" + operator + "' is unsupported for variable")
            .toUser("An unsupported template operation was used.  Please call support")
            .throwIt();
		    return "";
		}
	}

}
