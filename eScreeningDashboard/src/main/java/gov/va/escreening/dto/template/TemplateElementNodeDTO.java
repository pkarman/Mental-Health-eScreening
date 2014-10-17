package gov.va.escreening.dto.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TemplateElementNodeDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8322597683027468035L;
	
	private String title;
	private String section;
	private String type;
	private String content;
	private List<TemplateElementNodeDTO> children = new ArrayList<>();
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<TemplateElementNodeDTO> getChildren() {
		return children;
	}
	public void setChildren(List<TemplateElementNodeDTO> childrenNodes) {
		this.children = childrenNodes;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
}
