package gov.va.escreening.dto.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ @Type(value = TemplateTextDTO.class, name = "text"), 
			@Type(value = TemplateIfBlockDTO.class, name = "if"),
			@Type(value = TemplateElseIfBlockDTO.class, name = "elseif"),
			@Type(value = TemplateElseBlockDTO.class, name = "else")
			})
@JsonInclude(Include.NON_NULL)
public interface INode {
	
	public void setType(String type);
	
	public String getType();
	
	public String toFreeMarkerFormat();

}
