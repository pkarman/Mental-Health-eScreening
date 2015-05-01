package gov.va.escreening.dto.template;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateAssessmentVariableDTO {

	private Integer id;
	private String name;
	private String displayName;
	private Integer typeId;
	private Integer measureId;
	private Integer measureTypeId;
	private Integer measureAnswerId;
	private List<VariableTransformationDTO> transformations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VariableTransformationDTO> getTransformations() {
		return transformations;
	}

	public void setTransformations(
			List<VariableTransformationDTO> transformations) {
		this.transformations = transformations;
	}

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
	
	//TODO: move to the use of enum/enum sets (here and in AssessmentConstants)
	public boolean measureTypeIn(Integer... measureTypeIds){
		if(measureTypeId == null){
			return false;
		}
		for(Integer typeId: measureTypeIds){
			if(measureTypeId == typeId){
				return true;
			}
		}
		return false;
	}
	
	public boolean measureTypeIs(Integer measureTypeId){
		return this.measureTypeId != null && this.measureTypeId == measureTypeId;  
	}
	
	//TODO: move to the use of enum/enum sets (here and in AssessmentConstants)
	public boolean typeIn(Integer... typeIds){
		if(typeId == null){
			return false;
		}
		for(Integer typeId: typeIds){
			if(this.typeId == typeId){
				return true;
			}
		}
		return false;
	}
	
	public boolean typeIs(Integer typeId){
		return this.typeId != null && this.typeId == typeId;  
	}
}
