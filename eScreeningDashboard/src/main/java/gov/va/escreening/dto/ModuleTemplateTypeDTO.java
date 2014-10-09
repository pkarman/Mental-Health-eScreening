package gov.va.escreening.dto;

public class ModuleTemplateTypeDTO {

	private Integer templateTypeId;
	private String templateTypeName;
	private String templateTypeDescription;
	private Integer templateID;

	public Integer getTemplateTypeId() {
		return this.templateTypeId;
	}

	public void setTemplateTypeId(Integer templateTypeId) {
		this.templateTypeId = templateTypeId;
	}

	public String getTemplateTypeName() {
		return templateTypeName;
	}

	public void setTemplateTypeName(String templateTypeName) {
		this.templateTypeName = templateTypeName;
	}

	public String getTemplateTypeDescription() {
		return templateTypeDescription;
	}

	public void setTemplateTypeDescription(String templateTypeDescription) {
		this.templateTypeDescription = templateTypeDescription;
	}

	public Integer getTemplateID() {
		return templateID;
	}

	public void setTemplateID(Integer templateID) {
		this.templateID = templateID;
	}

}
