package gov.va.escreening.dto.template;

public class TemplateTextContent extends TemplateBaseContent{
	
	private String content;
	
	public TemplateTextContent()
	{
		setType("text");
	}

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
