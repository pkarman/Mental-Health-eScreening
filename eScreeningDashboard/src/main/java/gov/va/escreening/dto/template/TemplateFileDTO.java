package gov.va.escreening.dto.template;

import gov.va.escreening.dto.TemplateTypeDTO;

import java.io.Serializable;
import java.util.List;

public class TemplateFileDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8179415554770183325L;
	
	private Integer templateId;
	private TemplateTypeDTO templateType;
	private List<TemplateElementNodeDTO> nodes;
	
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
	public List<TemplateElementNodeDTO> getNodes() {
		return nodes;
	}
	public void setNodes(List<TemplateElementNodeDTO> nodes) {
		this.nodes = nodes;
	}
	
	

}
