package gov.va.escreening.dto.template;

import gov.va.escreening.service.AssessmentVariableService;

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

	@Override
	public StringBuilder appendFreeMarkerFormat(AssessmentVariableService assessmentVariableService, StringBuilder sb, Set<Integer> ids) {
		addHeader(sb);

		for (TemplateBaseContent content : contents) {
			if(content instanceof TemplateTextContent)
			{
				sb.append(((TemplateTextContent)content).toFreeMarkerString());
			}
			else
			{
				// variable content
				sb.append("${")
					.append(TemplateVariableContent.translate(null, content, null, ids))
					.append("}");
			}
		}

		return sb;
	}
}
