package gov.va.escreening.dto.template;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ @Type(value = TemplateTextDTO.class, name = "text"), 
			@Type(value = TemplateIfBlockDTO.class, name = "if"),
			@Type(value = TemplateElseIfBlockDTO.class, name = "elseif"),
			@Type(value = TemplateElseBlockDTO.class, name = "else")
			})
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
	public String toFreeMarkerFormat(Set<Integer> ids) {
		if (children == null || children.size()==0)
		{
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(INode node : children)
		{
			sb.append(node.toFreeMarkerFormat(ids));
		}
		return sb.toString();
	}
}
