package gov.va.escreening.dto.template;

import gov.va.escreening.dto.TemplateTypeDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TemplateFileDTO implements Serializable{

	private static final long serialVersionUID = 8179415554770183325L;
	
	private Integer id;
	private boolean isGraphical;
	private TemplateTypeDTO type;
	private List<INode> blocks = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer templateId) {
		this.id = templateId;
	}
	public TemplateTypeDTO getType() {
		return type;
	}
	public void setType(TemplateTypeDTO templateType) {
		this.type = templateType;
	}
	public List<INode> getBlocks() {
		return blocks;
	}
	public void setBlocks(List<INode> nodes) {
		this.blocks = nodes;
	}
	public boolean getIsGraphical() {
		return isGraphical;
	}
	
	public void setIsGraphical(Boolean isGraphical2) {
		this.isGraphical = isGraphical2;		
	}
	
}
