package gov.va.escreening.dto.template;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateElseBlockDTO extends TemplateBaseBlockDTO {
	
	@JsonProperty("type")
	private String nodeType(){return "else";}
	
	@Override
	public String toFreeMarkerFormat(Set<Integer>ids)
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("<#else>");
		
		for(INode child : getChildren())
		{
			sb.append(child.toFreeMarkerFormat(ids));
		}
		
		return sb.toString();
	}
	
}
