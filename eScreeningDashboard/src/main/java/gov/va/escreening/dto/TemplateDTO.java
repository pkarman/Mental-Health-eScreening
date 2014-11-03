package gov.va.escreening.dto;

import java.util.Date;
import java.util.List;

public class TemplateDTO {
	private Integer templateId;
    private String name;
    private String description;
    
    private String templateFile;
    private Date dateCreated;
    private Integer templateTypeId;
    private boolean isGraphical;
    
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Integer getTemplateTypeId() {
		return templateTypeId;
	}
	public void setTemplateType(Integer templateTypeId) {
		this.templateTypeId = templateTypeId;
	}
	public boolean isGraphical() {
		return isGraphical;
	}
	public void setGraphical(boolean isGraphical) {
		this.isGraphical = isGraphical;
	}

}
