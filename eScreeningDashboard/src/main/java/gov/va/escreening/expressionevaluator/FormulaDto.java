package gov.va.escreening.expressionevaluator;

import java.util.Hashtable;
import java.util.Map;

public class FormulaDto {
	String expressionTemplate;
	Map<Integer, String> variableValueMap = new Hashtable<Integer,String>();
	Map<Integer, String> childFormulaMap = new Hashtable<Integer, String>();
	
	public String getExpressionTemplate() {
		return expressionTemplate;
	}
	public void setExpressionTemplate(String expressionTemplate) {
		this.expressionTemplate = expressionTemplate;
	}
	
	public Map<Integer, String> getVariableValueMap() {
		return variableValueMap;
	}
	public void setVariableValueMap(Map<Integer, String> variableValueMap) {
		this.variableValueMap = variableValueMap;
	}
	
	public Map<Integer, String> getChildFormulaMap() {
		return childFormulaMap;
	}
	public void setChildFormulaMap(Map<Integer, String> childFormulaMap) {
		this.childFormulaMap = childFormulaMap;
	}
}