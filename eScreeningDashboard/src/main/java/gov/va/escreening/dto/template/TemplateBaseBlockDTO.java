package gov.va.escreening.dto.template;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TemplateBaseBlockDTO implements INode{
	private String summary;
	private String title;
	private String section;
	private List<INode> children;
	
	
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
	
	public List<INode> getChildren() {
		return children;
	}

	public void setChildren(List<INode> childrens) {
		this.children = childrens;
	}
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Override
	public String toFreeMarkerFormat() {
		if (children == null || children.size()==0)
		{
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		
		for(INode node : children)
		{
			sb.append(node.toFreeMarkerFormat());
		}
		return sb.toString();
	}
}
