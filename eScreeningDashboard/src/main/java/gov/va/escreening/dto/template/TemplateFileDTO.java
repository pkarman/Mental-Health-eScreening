package gov.va.escreening.dto.template;

import gov.va.escreening.dto.TemplateTypeDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TemplateFileDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179415554770183325L;
	
	private Integer templateId;
	private boolean isGraphical;
	private TemplateTypeDTO templateType;
	private List<TemplateElementNodeDTO> blocks = new ArrayList<>();
	
	public Integer getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}
	public TemplateTypeDTO getTemplateType() {
		return templateType;
	}
	public void setTemplateType(TemplateTypeDTO templateType) {
		this.templateType = templateType;
	}
	public List<TemplateElementNodeDTO> getBlocks() {
		return blocks;
	}
	public void setBlocks(List<TemplateElementNodeDTO> nodes) {
		this.blocks = nodes;
	}
	public boolean getIsGraphical() {
		return isGraphical;
	}
	
	public void setIsGraphical(Boolean isGraphical2) {
		this.isGraphical = isGraphical2;		
	}
	
}
