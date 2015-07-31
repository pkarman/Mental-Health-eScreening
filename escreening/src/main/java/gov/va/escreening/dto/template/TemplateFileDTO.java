package gov.va.escreening.dto.template;

import gov.va.escreening.dto.TemplateTypeDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateFileDTO implements Serializable{

	private static final long serialVersionUID = 8179415554770183325L;
	
	private Integer id;
	private boolean isGraphical;
	private TemplateTypeDTO type;
	private String name;
	private String json;
	private GraphParamsDto graph;
	
	private List<INode> blocks = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer templateId) {
		this.id = templateId;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public GraphParamsDto getGraph() {
        return graph;
    }
    public void setGraph(GraphParamsDto graph) {
        this.graph = graph;
    }
}
