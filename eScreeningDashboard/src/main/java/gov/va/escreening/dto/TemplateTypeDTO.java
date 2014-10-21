package gov.va.escreening.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This object represents a template type and, in the context of a specific 
 * referenced object (e.g. module or battery), an optional template ID (when such 
 * a relation exists).
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateTypeDTO {

	private Integer id;
	private String name;
	private String description;
	private Integer templateId;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer templateTypeId) {
		this.id = templateTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String templateTypeName) {
		this.name = templateTypeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String templateTypeDescription) {
		this.description = templateTypeDescription;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

}
