package gov.va.escreening.dto.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateElseBlockDTO extends TemplateBaseBlockDTO {
	
	@Override
	public String toFreeMarkerFormat()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<#else>");
		
		for(INode child : getChildren())
		{
			sb.append(child.toFreeMarkerFormat());
		}
		
		return sb.toString();
	}
}
