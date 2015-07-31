package gov.va.escreening.dto.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateTextContent extends TemplateBaseContent{
	
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String toFreeMarkerString()
	{
		return content.replace("<br/>", "${LINE_BREAK}").replace("&nbsp;", "${NBSP}");
	}

}
