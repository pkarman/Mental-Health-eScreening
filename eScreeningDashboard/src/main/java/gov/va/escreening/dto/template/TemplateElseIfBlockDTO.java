package gov.va.escreening.dto.template;

public class TemplateElseIfBlockDTO extends TemplateIfBlockDTO {

	@Override
	public String toFreeMarkerFormat() {

		StringBuffer sb = new StringBuffer();

		sb.append("<#elseif ")
				.append("(")
				.append(FormulaUtil.createFormula(getOperator(), getLeft(),
						getRight())).append(")");

		if (getConditions() != null && getConditions().size() > 0) {
			for (TemplateFollowingConditionBlock tfcb : getConditions()) {
				sb.append(tfcb.toFreeMarkerFormatFormula());
			}
		}
		sb.append(" >\n");

		return sb.toString();
	}

}
