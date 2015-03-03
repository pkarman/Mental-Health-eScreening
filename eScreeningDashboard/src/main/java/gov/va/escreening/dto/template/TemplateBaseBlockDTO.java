package gov.va.escreening.dto.template;

import gov.va.escreening.service.AssessmentVariableService;

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
			@Type(value = TemplateElseBlockDTO.class, name = "else"),
			@Type(value = TemplateTableBlockDTO.class, name = "table")
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
	public StringBuilder appendFreeMarkerFormat(StringBuilder sb, Set<Integer> ids, AssessmentVariableService assessmentVariableService) {
		return addChildren(sb, ids, assessmentVariableService);
	}
	
	/**
	 * Appends all children of this block to the given StringBuilder
	 * @param sb
	 * @param ids the 
	 * @return the same StringBuilder passed in (for chaining)
	 */
	protected StringBuilder addChildren(StringBuilder sb, Set<Integer> ids, AssessmentVariableService assessmentVariableService){
		StringBuilder result = sb;
		if(getChildren() != null) {
			for(INode child : getChildren()){
				result = child.appendFreeMarkerFormat(result, ids, assessmentVariableService);
			}
		}
		return result;
	}
	
	/**
	 * Adds the header for this block
	 * @param sb StringBuilder to append to
	 * @return same StringBuilder passed in (for chaining)
	 */
	protected StringBuilder addHeader(StringBuilder sb) {
		if (this.getName()!=null)
			sb.append("<#-- NAME:"+this.getName()+"-->\n");
		if (this.getSection()!=null)
			sb.append("<#-- SECTION:"+getSection()+" -->\n");
		if (this.getSummary()!=null)
			sb.append("<#-- SUMMARY:"+getSummary()+" -->\n");
		return sb;
	}

}
