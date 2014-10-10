package gov.va.escreening.dto.template;

import java.io.Serializable;
import java.util.List;

public class TemplateElementNodeDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8322597683027468035L;
	
	private String nodeType;
	private List<TemplateElementNodeDTO> childrenNodes;
	private String content;
	private boolean isLeaf;
	
	
	public String getType() {
		return nodeType;
	}
	public void setType(String type) {
		this.nodeType = type;
	}
	public List<TemplateElementNodeDTO> getChildrenNodes() {
		return childrenNodes;
	}
	public void setChildrenNodes(List<TemplateElementNodeDTO> childrenNodes) {
		this.childrenNodes = childrenNodes;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
	

}
