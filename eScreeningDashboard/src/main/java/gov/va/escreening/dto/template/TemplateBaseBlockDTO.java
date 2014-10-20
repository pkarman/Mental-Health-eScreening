package gov.va.escreening.dto.template;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TemplateBaseBlockDTO implements INode{
	private String title;
	private String section;
	private String type;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public List<INode> getChildren() {
		return children;
	}

	public void setChildren(List<INode> childrens) {
		this.children = childrens;
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
