package gov.va.escreening.constants;

import java.util.EnumSet;

public class TemplateConstants {
	
    public enum ViewType {HTML, TEXT}
    
	
	public static EnumSet<TemplateConstants.Style> styleHeader = EnumSet.of(TemplateConstants.Style.CPRS_NOTE_HEADER, TemplateConstants.Style.VETERAN_SUMMARY_HEADER);
	public static EnumSet<Style> styleFooter = EnumSet.of(TemplateConstants.Style.CPRS_NOTE_FOOTER, TemplateConstants.Style.VETERAN_SUMMARY_FOOTER);
//	public static EnumSet<Style> styleBody = EnumSet.of(TemplateConstants.Style.CPRS_NOTE_BODY, TemplateConstants.Style.VETERAN_SUMMARY_BODY);
	public static EnumSet<Style> styleSpecial = EnumSet.of(TemplateConstants.Style.CPRS_NOTE_SCORING_MATRIX, TemplateConstants.Style.CPRS_NOTE_SCORING_QA);
	public static EnumSet<Style> styleConclusion = EnumSet.of(TemplateConstants.Style.CPRS_NOTE_CONCLUSION);
    
    //Style type determines which what is included in the CPRS note (which type)
    public static final Integer TEMPLATE_CPRS_NOTE_STYLE_BASIC = 30;
    public static final Integer TEMPLATE_CPRS_NOTE_STYLE_BASIC_SCORE = 31;
    public static final Integer TEMPLATE_CPRS_NOTE_STYLE_BASIC_QA = 32;
    public static final Integer TEMPLATE_CPRS_NOTE_STYLE_BASIC_SCORE_QA = 33;  
    
	//Template types for CPRS Note Generation
	public static final Integer TEMPLATE_TYPE_CPRS_NOTE_HEADER = 1;
	public static final Integer TEMPLATE_TYPE_CPRS_NOTE_FOOTER = 2;
	public static final Integer TEMPLATE_TYPE_CPRS_NOTE_ENTRY = 3;
	public static final Integer TEMPLATE_TYPE_CPRS_NOTE_SPECIAL = 4;
	
	//Template types for veteran summary
	public static final Integer TEMPLATE_TYPE_VETERAN_SUMMARY_HEADER = 10;
	public static final Integer TEMPLATE_TYPE_VETERAN_SUMMARY_FOOTER = 11;
	public static final Integer TEMPLATE_TYPE_VETERAN_SUMMARY_ENTRY = 12;

	//Battery Scoring (ticket: 527) one per battery
	public static final Integer TEMPLATE_TYPE_SCORING_NOTE_HEADER = 21;
	public static final Integer TEMPLATE_TYPE_SCORING_NOTE_FOOTER = 22;
	public static final Integer TEMPLATE_TYPE_SCORING_NOTE_ENTRY = 23;
		
		
	//Start Battery text: shown when veteran first logs in (ticket: TBD)
	
	//Completion text: shown to veteran after he/she completes a battery (ticket: 502); one per battery
	
	
	/** 
	 * Contains the different "styles" for headers, footers and special section. 
	 * Styles equate to to templates, i.e. CPRS Note header template tbl template_id = 1 so in here the 
	 * we set the "code" field in this enum element to CPRS_NOTE_HEADER(1). The "1" is the template_id.
	 * */
	public enum Style{
		CPRS_NOTE_HEADER(1), VETERAN_SUMMARY_HEADER(200), CPRS_NOTE_FOOTER(2), VETERAN_SUMMARY_FOOTER(220),
			CPRS_NOTE_SCORING_MATRIX(100), CPRS_NOTE_SCORING_QA(102), CPRS_NOTE_CONCLUSION(500);
			
		private int code;
		 
		private Style(int c) {
		   code = c;
		}
		 
		public int getCode() {
		   return code;
		}
		

		
		
	}
	

	
	public static TemplateConstants.Style getStyleByCode(int id){
		TemplateConstants.Style result = null;
		for( TemplateConstants.Style s : TemplateConstants.Style.values()){
			if(s.getCode() == id){
				result = s;
				break;
			}
		}
		
		return result;
	}
	
	
}