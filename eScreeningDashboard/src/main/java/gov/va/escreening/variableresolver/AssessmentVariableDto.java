package gov.va.escreening.variableresolver;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.exception.CouldNotResolveVariableException;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssessmentVariableDto {
	private static final String VARIABLE_FORMAT = "var%s";
	private static final String DISPLAY_TEXT_FORMAT_FORMULA = "formula_%s";
	private static final String DISPLAY_TEXT_FORMAT_MEASURE = "measure_%s";
	private static final String DISPLAY_TEXT_FORMAT_MEASURE_ANSWER = "answer_%s";
	private static final Logger logger = LoggerFactory.getLogger(AssessmentVariableDto.class);	
	
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
	private String displayName;
	private Integer measureTypeId;
	
	private Integer answerId;
	
	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	private List<AssessmentVariableDto> children = new ArrayList<AssessmentVariableDto>();
	
	public AssessmentVariableDto(){};

	public AssessmentVariableDto(Integer variableId, String key, String type, String name, Integer column) {
		this.variableId = variableId;
		this.key = key;
		this.type = type;
		this.name = name;
		this.column = column;
	}
	
	public AssessmentVariableDto(Integer variableId, String key, String type, String name, Integer column, Integer measureTypeId) {
		this.variableId = variableId;
		this.key = key;
		this.type = type;
		this.name = name;
		this.column = column;
		this.measureTypeId = measureTypeId;
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
	
	/**
	 * This constructor creates a DTO using the given AV entity.<br/>
	 * It is assumed that the av is not a Hibernate transient entity.<br/>
	 * Custom assessment variables are not supported. <br/>
	 * Some fields cannot be set because they are based on a veteran response:
	 * <ol><li>value is not set</li>
	 * <li>displayText needs a response to be set so this is not set</li>
	 * <li>overrideText uses the variableTemplate we are in the process of stopping its use. So this is not set</li>
	 * <li>otherValue is not set because this is only used for response-carrying AVs (only used for measureAnswer AVs)</li>
	 * <li>row is not set because the response is needed (it is only set for measureAnswer AVs)</li>
	 */
	public AssessmentVariableDto(AssessmentVariable av){
		variableId = av.getAssessmentVariableId();
		key = String.format(VARIABLE_FORMAT, variableId);
		type = createVariableTypeString(av);
		name = createName(av);
		column = createColumn(av);
		displayName = av.getDisplayName();
		
		if(av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER){
			
			if(av.getMeasureAnswer() != null){
				answerId = av.getMeasureAnswer().getMeasureAnswerId();
				calculationValue = av.getMeasureAnswer().getCalculationValue();
			}
			else{
				logger.warn("measure answer not found for AV with ID: " + av.getAssessmentVariableId());
			}
		}
		else if(av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE
				&& av.getMeasure() != null && av.getMeasure().getMeasureType() != null ){
			measureTypeId = av.getMeasure().getMeasureType().getMeasureTypeId();
		}
	}

	public void setResponse(String resolvedValue) {
		this.displayText = resolvedValue;
		this.value = resolvedValue;
	}
	
	/**
	 * Sets all fields having to do with a response except for the setting of OverrideText which should be removed.<br/>
	 * Note: Much of this logic was taken from MeasureAnswerAssessmentVairableResolverImpl to centralize and simplify this code.
	 * 
	 * @param response the response to use for setting fields. This should not be a transient object.
	 */
	public void setResponse(SurveyMeasureResponse response){
		setValue(getAnswerValue(response));
		setRow(response.getTabularRow());
		
		//TODO: the following logic was simplified from MeasureAnswerAssessmentVairableResolverImpl (we should remove one of these)
		setOtherText(response.getOtherValue());
		setOtherValue(response.getOtherValue());
		
		
		if(response.getMeasure().getMeasureType().getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_FREE_TEXT){
			setDisplayText(value);
		}
		else{
			//The constraint has been removed which would set null here if the answer is of type none. Template functions do not assume
			//this business rule but it is possible that the handwritten templates do.  This constraint was lifted because it causes the
			//delimited output of select multi to throw error since null was being returned here for the display text.  PO would like to
			//show the text of the None answer so null should not be returned.
			setDisplayText(response.getMeasureAnswer().getAnswerText());
		}
	}
	
	private Integer createColumn(AssessmentVariable av){
		switch(av.getAssessmentVariableTypeId().getAssessmentVariableTypeId()){
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
				if (av.getMeasure() != null
					&& av.getMeasure().getDisplayOrder() != null)
					return av.getMeasure().getDisplayOrder();
				break;
				
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
				if (av.getMeasureAnswer() != null
					&& av.getMeasureAnswer().getDisplayOrder() != null)
					return av.getMeasureAnswer().getDisplayOrder();
				break;
				
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
				return AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN;
				
			default:
				throw new UnsupportedOperationException("Invliad type: " + av.getAssessmentVariableTypeId().getAssessmentVariableTypeId());
		}
		return AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN;
		
	}
	
	private String createName(AssessmentVariable av){
		switch(av.getAssessmentVariableTypeId().getAssessmentVariableTypeId()){
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
				return String.format(DISPLAY_TEXT_FORMAT_MEASURE, av.getAssessmentVariableId());
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
				return String.format(DISPLAY_TEXT_FORMAT_MEASURE_ANSWER, av.getAssessmentVariableId());
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
				return String.format(DISPLAY_TEXT_FORMAT_FORMULA, av.getAssessmentVariableId());
				
			default:
				throw new UnsupportedOperationException("Invliad type: " + av.getAssessmentVariableTypeId().getAssessmentVariableTypeId());
		}
	}
	
	private String getAnswerValue(SurveyMeasureResponse response) {
		if(response.getBooleanValue() != null)
			return response.getBooleanValue().toString().toLowerCase();
		if(response.getNumberValue() != null)
			return response.getNumberValue().toString();
		if(response.getTextValue() != null)
			return response.getTextValue();

		throw new CouldNotResolveVariableException(
			String.format("A value was not set for surveymeasureresponseid: %s",
					response.getSurveyMeasureResponseId()));
	}

	
	//possible types are string, none, and other
	private String createVariableTypeString(AssessmentVariable av) {
		switch(av.getAssessmentVariableTypeId().getAssessmentVariableTypeId()){
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
				return "list";
		
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
				String measureAnswerType = av.getMeasureAnswer().getAnswerType();
				if(measureAnswerType == null)
					return "string";
				if(measureAnswerType.toLowerCase().equals("none"))
					return "none";
				if(measureAnswerType.toLowerCase().equals("other"))
					return "other";

				//otherwise its a string
				return "string";
				
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
				return "string";
			
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM:
				if(av.getAssessmentVariableId() == CustomAssessmentVariableResolverImpl.CUSTOM_VETERAN_APPOINTMENTS)
					return "list";
				return "string";
				
			default:
				throw new UnsupportedOperationException("Invliad type: " + av.getAssessmentVariableTypeId().getAssessmentVariableTypeId());
		}
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

	public void setDisplayName(String displayName) {
		this.displayName=displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public Integer getMeasureTypeId() {
		return measureTypeId;
	}

	public void setMeasureTypeId(Integer measureTypeId) {
		this.measureTypeId = measureTypeId;
	}

	@Override
	public String toString() {
		return "AssessmentVariableDto [displayName="+displayName+", variableId=" + variableId + ", key="
				+ key + ", type=" + type + ", name=" + name + ", value="
				+ value + ", displayText=" + displayText + ", overrideText="
				+ overrideText + ", otherText=" + otherText + ", column="
				+ column + ", row=" + row + ", calculationValue=" + calculationValue 
				+ ", otherValue=" + otherValue + ", children=" + children 
				+ ", measureTypeId=" + measureTypeId + "]";
	}
}