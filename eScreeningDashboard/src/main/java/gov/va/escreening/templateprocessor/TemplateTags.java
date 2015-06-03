package gov.va.escreening.templateprocessor;

import java.util.Arrays;

/**
 * This is an enum to holds all supported template tags that must be replaced by the {@link TemplateTagProcessor}<br/>
 * <br/>
 * Note: If you change these please update ClinicalNoteTemplateFunctions.ftl
 * @author Robin Carnow
 *
 */
public enum TemplateTags {
	BATTERY_HEADER_START, BATTERY_HEADER_END, 
	BATTERY_FOOTER_START, BATTERY_FOOTER_END,
	SECTION_TITLE_START, SECTION_TITLE_END, 
	SECTION_START, SECTION_END,
	MODULE_TITLE_START, MODULE_TITLE_END, 
	MODULE_START, MODULE_END,
	MODULE_COMPONENTS_START,MODULE_COMPONENTS_END,  
	LINE_BREAK, NBSP,
	IMG_LOGO_VA_HC, IMG_CESMITH_BLK_BRDR,
	IMG_VA_VET_SMRY,
	
	GRAPH_SECTION_START, GRAPH_SECTION_END,
	GRAPH_BODY_START, GRAPH_BODY_END,
	
	MATRIX_TABLE_START, MATRIX_TABLE_END,
	MATRIX_TR_START, MATRIX_TR_END, 
	TABLE_TR_CTR_START, TABLE_TR_END,
	MATRIX_TD_START, MATRIX_TD_END,  
	MATRIX_TH_START, MATRIX_TH_END,
	
	TABLE_TD_END,
	TABLE_TD_CTR_START, 
	TABLE_TD_LFT_START,
	TABLE_TD_SPACER1_START
	;
	
	
	public enum Style {
		TEXT("([", "]+)"), 
		XML("(<[", "]+>)"), 
		FREEMARKER_VAR("(\\$\\{[", "]+\\})");
		
		private final String start, end;
		Style(String start, String end){
			this.start = start;
			this.end = end;
		}
	}
	
	private String xmlString;
	
	private TemplateTags(){
		xmlString = '<' + this.toString() + '>';
	}
	
	public String xml(){
		return String.format(xmlString);
	}
	
	/**
	 * Creates a regex with a group which contains an OR'ing of each tag given. 
	 * @param style dictates what the surrounding strings are around the tag (e.g. ${FREE_MARKER}, <XML_STYLE>)
	 * @param tags the tags that will be OR'ed together
	 * @return a regex string with a large group that contains a tag entry for each passed in.  
	 * Example: 
	 */
	public static String createTagRegex(Style style, Iterable<TemplateTags> tags){
		StringBuilder pattern = new StringBuilder(style.start);
		
		for(TemplateTags tag : tags){
			pattern
				.append('(')
				.append(tag)
				.append(')');
		}
		
		return pattern.append(style.end).toString();
	}
	
	/**
	 * Convenience method so you don't have to.
	 */
	public static String createTagRegex(Style style, TemplateTags... tags){
		return createTagRegex(style, Arrays.asList(tags));
	}
}
