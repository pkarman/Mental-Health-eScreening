package gov.va.escreening.dto;


public class MeasureAnswerDTO {
	
	private Integer measureAnswerId;
    private String exportName;
    private String otherExportName;
    private String answerText;
    private String answerType;
    private Integer answerValue;
    private String calculationValue;
    private String mhaValue;
    private Integer displayOrder;
    private String vistaText;
    
	public Integer getMeasureAnswerId() {
		return measureAnswerId;
	}
	public void setMeasureAnswerId(Integer measureAnswerId) {
		this.measureAnswerId = measureAnswerId;
	}
	public String getExportName() {
		return exportName;
	}
	public void setExportName(String exportName) {
		this.exportName = exportName;
	}
	public String getOtherExportName() {
		return otherExportName;
	}
	public void setOtherExportName(String otherExportName) {
		this.otherExportName = otherExportName;
	}
	public String getAnswerText() {
		return answerText;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public Integer getAnswerValue() {
		return answerValue;
	}
	public void setAnswerValue(Integer answerValue) {
		this.answerValue = answerValue;
	}
	public String getCalculationValue() {
		return calculationValue;
	}
	public void setCalculationValue(String calculationValue) {
		this.calculationValue = calculationValue;
	}
	public String getMhaValue() {
		return mhaValue;
	}
	public void setMhaValue(String mhaValue) {
		this.mhaValue = mhaValue;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getVistaText() {
		return vistaText;
	}
	public void setVistaText(String vistaText) {
		this.vistaText = vistaText;
	}
	
	

}
