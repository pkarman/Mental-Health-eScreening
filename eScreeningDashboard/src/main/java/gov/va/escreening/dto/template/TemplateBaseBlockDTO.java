package gov.va.escreening.dto.template;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TemplateBaseBlockDTO implements INode{
	private String summary;
	private String name;
	private String section;
	private List<INode> children;
	
	
	public String getName() {
		return name;
	}
	public void setName(String title) {
		this.name = title;
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
