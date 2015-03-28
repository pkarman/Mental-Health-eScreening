package gov.va.escreening.expressionevaluator;

import gov.va.escreening.variableresolver.AssessmentVariableDto;

import java.util.Hashtable;
import java.util.Map;

public class FormulaDto {
	String expressionTemplate;
	Map<Integer, String> variableValueMap = new Hashtable<Integer,String>();
	Map<Integer, String> childFormulaMap = new Hashtable<Integer, String>();
	
	/**
	 * Optional map from assessment variable ID to AssessmentVariableDto. 
	 * Used when the AV DTOs are needed in a formula for complex expressions (e.g. # of table entries)
	 */
	Map<Integer, AssessmentVariableDto> avMap = new Hashtable<>();
	
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
	
    public Map<Integer, AssessmentVariableDto> getAvMap() {
        return avMap;
    }
    public void setAvMap(Map<Integer, AssessmentVariableDto> avMap) {
        this.avMap = avMap;
    }
}