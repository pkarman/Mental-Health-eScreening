package gov.va.escreening.dto.template;

public class TemplateAssessmentVariableDTO {

	private Integer id;
	private String displayName;
	private Integer typeId;
	private Integer measureId;
	private Integer measureTypeId;
	private Integer measureAnswerId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getMeasureId() {
		return measureId;
	}
	public void setMeasureId(Integer measureId) {
		this.measureId = measureId;
	}
	public Integer getMeasureTypeId() {
		return measureTypeId;
	}
	public void setMeasureTypeId(Integer measureTypeId) {
		this.measureTypeId = measureTypeId;
	}
	public Integer getMeasureAnswerId() {
		return measureAnswerId;
	}
	public void setMeasureAnswerId(Integer measureAnswerId) {
		this.measureAnswerId = measureAnswerId;
	}
	
}
