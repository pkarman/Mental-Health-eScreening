package gov.va.escreening.dto;

public class ModuleTemplateTypeDTO {
	
	
	private String templateTypeName;
	private String templateTypeDescription;
	private boolean givenTemplateExists;
	
	
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
	public boolean isGivenTemplateExists() {
		return givenTemplateExists;
	}
	public void setGivenTemplateExists(boolean givenTemplateExists) {
		this.givenTemplateExists = givenTemplateExists;
	}

}
