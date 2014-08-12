package gov.va.escreening.variableresolver;

import java.util.ArrayList;
import java.util.List;

public class AssessmentVariableDto {

	private Integer variableId;
	private String key;
	private String type;
	private String name;
	private String value;
	private String displayText;
	private String overrideText;
	private String otherText;
	private Integer column;
	private Integer row ;
	private String calculationValue;
	private String otherValue;
	
	private List<AssessmentVariableDto> children = new ArrayList<AssessmentVariableDto>();
	
	public AssessmentVariableDto(){};

	public AssessmentVariableDto(Integer variableId, String key, String type, String name, Integer column) {
		this.variableId = variableId;
		this.key = key;
		this.type = type;
		this.name = name;
		this.column = column;
	}
	
	public AssessmentVariableDto(Integer variableId, String key, String type, String name, String value,
			String displayText, String overrideText, String otherText, Integer column) {
		this.variableId = variableId;
		this.key = key;
		this.type = type;
		this.name = name;
		this.value = value;
		this.displayText = displayText;
		this.overrideText = overrideText;
		this.otherText = otherText;
		this.column = column;
	}
	
	public AssessmentVariableDto(Integer variableId, String key, String type, String name, String value,
			String displayText, String overrideText, String otherText, List<AssessmentVariableDto> children) {
		this.variableId = variableId;
		this.key = key;
		this.type = type;
		this.name = name;
		this.value = value;
		this.displayText = displayText;
		this.overrideText = overrideText;
		this.otherText = otherText;
		this.children = children;
	}
	
	public AssessmentVariableDto(Integer variableId, String key, String type, String name, String value,
			String displayText, String overrideText, String otherText, String calcValue, Integer column, Integer row, String otherValue) {
		this.variableId = variableId;
		this.key = key;
		this.type = type;
		this.name = name;
		this.value = value;
		this.displayText = displayText;
		this.overrideText = overrideText;
		this.otherText = otherText;
		this.calculationValue = calcValue;
		this.column = column;
		this.row = row;
		this.otherValue = otherValue;
	}

	public Integer getVariableId() {
		return variableId;
	}

	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDisplayText() {
		return displayText;
	}
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}
	
	public String getOverrideText() {
		return overrideText;
	}
	public void setOverrideText(String overrideText) {
		this.overrideText = overrideText;
	}
	
	public String getOtherText() {
		return otherText;
	}
	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}
	
	public Integer getColumn() {
		return column;
	}
	public void setColumn(Integer column) {
		this.column = column;
	}
			
	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public String getCalculationValue() {
		return calculationValue;
	}

	public void setCalculationValue(String calculationValue) {
		this.calculationValue = calculationValue;
	}

	public String getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(String otherValue) {
		this.otherValue = otherValue;
	}
	
	public List<AssessmentVariableDto> getChildren() {
		return children;
	}
	public void setChildren(List<AssessmentVariableDto> children) {
		this.children = children;
	}

	
	

	@Override
	public String toString() {
		return "AssessmentVariableDto [variableId=" + variableId + ", key="
				+ key + ", type=" + type + ", name=" + name + ", value="
				+ value + ", displayText=" + displayText + ", overrideText="
				+ overrideText + ", otherText=" + otherText + ", column="
				+ column + ", row=" + row + ", calculationValue=" + calculationValue 
				+ ", otherValue=" + otherValue + ", children=" + children + "]";
	}
	
}