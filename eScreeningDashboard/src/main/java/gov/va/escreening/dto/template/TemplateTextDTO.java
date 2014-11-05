package gov.va.escreening.dto.template;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)

public class TemplateTextDTO extends TemplateBaseBlockDTO {
	
	@JsonProperty("type")
	private String nodeType(){return "text";}
	private List<TemplateBaseContent> contents;

	public List<TemplateBaseContent> getContents() {
		return contents;
	}

	public void setContents(List<TemplateBaseContent> contents) {
		this.contents = contents;
	}

	public String toFreeMarkerFormat(Set<Integer>ids) {
		StringBuffer sb = new StringBuffer();
		if (this.getName()!=null)
			sb.append("<#-- NAME:"+this.getName()+"-->\n");
		if (this.getSection()!=null)
			sb.append("<#-- SECTION:"+getSection()+" -->\n");
		if (this.getSummary()!=null)
			sb.append("<#-- SUMMARY:"+getSummary()+" -->\n");

		for (TemplateBaseContent content : contents) {
			if(content instanceof TemplateTextContent)
			{
				sb.append(((TemplateTextContent)content).toFreeMarkerString());
			}
			else
			{
				// varirable content
				sb.append("${"+((TemplateVariableContent)content).translate(null, content, null, ids)+"}");
			}
		}

		return sb.toString();
	}

}
