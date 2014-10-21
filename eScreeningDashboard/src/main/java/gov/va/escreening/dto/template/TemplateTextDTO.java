package gov.va.escreening.dto.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TemplateTextDTO extends TemplateBaseBlockDTO{
	private String content;
	
	public TemplateTextDTO()
	{
		setType("text");
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String toFreeMarkerFormat()
	{
		return content.replace("<br/>", "${LINE_BREAK}").replace("&nbsp;", "${NBSP}");
	}

}
