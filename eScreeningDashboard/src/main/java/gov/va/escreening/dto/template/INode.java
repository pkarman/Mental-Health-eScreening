package gov.va.escreening.dto.template;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ @Type(value = TemplateTextDTO.class, name = "text"), 
			@Type(value = TemplateIfBlockDTO.class, name = "if"),
			@Type(value = TemplateElseIfBlockDTO.class, name = "elseif"),
			@Type(value = TemplateElseBlockDTO.class, name = "else"),
			@Type(value = TemplateTableBlockDTO.class, name = "table")
			})
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface INode {
	
	/**
	 * Appends the FreeMarker translation of this block to the given StringBuilder and return the result
	 * @param sb this is being appended to
	 * @param ids during translation each block is responsible for added assessment variable IDs for variables they contain and need for rendering 
	 * @return a StringBuilder which contains the content passed in (via sb) along with this block's content.  
	 * <b>Please Note:</b> The returned StringBuilder does not have to be the same one passed in.  So callers should use the 
	 * returned result (functional style) instead of reusing the passed in StringBuilder (shared mutable state-style). 
	 * Implementing it this way allows for efficient translation into freemarker *and* allows for simple testing.
	 */
	public StringBuilder appendFreeMarkerFormat(StringBuilder sb, Set<Integer> ids);
	

}
