package gov.va.escreening.dto.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(Include.NON_NULL)
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ @Type(value = TemplateTextContent.class, name = "text"), 
	@Type(value = TemplateVariableContent.class, name = "var")
	})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class TemplateBaseContent {
	
}
