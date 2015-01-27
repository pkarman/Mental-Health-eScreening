package gov.va.escreening.expressionevaluator;

public interface ExpressionEvaluatorService {
	String evaluateFormula(FormulaDto formulaDto) throws NoSuchMethodException, SecurityException ;

	String testFormula(String formulaAsStr);
}
