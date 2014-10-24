package gov.va.escreening.dto.template;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TemplateTextDTO extends TemplateBaseBlockDTO {
	private List<TemplateBaseContent> contents;

	public List<TemplateBaseContent> getContents() {
		return contents;
	}

	public void setContents(List<TemplateBaseContent> contents) {
		this.contents = contents;
	}

	public String toFreeMarkerFormat() {
		StringBuffer sb = new StringBuffer();

		for (TemplateBaseContent content : contents) {
			if(content instanceof TemplateTextContent)
			{
				sb.append(((TemplateTextContent)content).toFreeMarkerString());
			}
			else
			{
				// varirable content
				sb.append("${"+((TemplateVariableContent)content).translate(null, content, null)+"}");
			}
		}

		return sb.toString();
	}

}
