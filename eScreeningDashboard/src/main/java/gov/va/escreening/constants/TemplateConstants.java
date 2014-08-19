package gov.va.escreening.constants;

import java.util.HashMap;
import java.util.Map;


public class TemplateConstants {
	
    public enum ViewType {HTML, TEXT}
    
    /**
     * Each template type found in DB lookup
     * @author Robin Carnow
     *
     */
    public enum TemplateType{
    	CPRS_HEADER(1, "CPRS header"), CPRS_FOOTER(2, "CPRS footer"), CPRS_ENTRY(3, "CPRS module"),
    	ASSESS_SCORE_TABLE(4, "assessment score table"), ASSESS_CONCLUSION(5, "assessment conclusion"),  //TODO: add an entry for assessment introduction
    	VET_SUMMARY_HEADER(6, "veteran summary header"), VET_SUMMARY_FOOTER(7, "veteran summary footer"), VET_SUMMARY_ENTRY(8, "veteran summary module"),
    	VISTA_QA(9, "VistA questions/answers");
    	
		private int id;
		private String name;
		 
		private TemplateType(int id, String name) {
		   this.id = id;
		   this.name = name;
		}
		
		/**
		 * @return the template_type.template_type_id for this template type
		 */
		public int getId() {
		   return id;
		}
		
		@Override
		public String toString(){
			return name;
		}
    }
    
    private static Map<Integer, TemplateType> idMap = new HashMap<>();
    
    static{
    	for(TemplateType type : TemplateType.values())
    		idMap.put(type.getId(),  type);
    }
    
    /**
     * @param id template type ID
     * @return the TemplateType for the given ID
     */
    public static TemplateType typeForId(Integer id){
    	return idMap.get(id);
    }
    
    public interface TemplateDocument{
    	public TemplateType getHeaderType();
    	
    	public TemplateType getEntryType();
    	
    	public TemplateType getFooterType();
    }

    /**
     * Defines each of the template system's document type 
     * @author Robin Carnow
     *
     */
    public enum DocumentType implements TemplateDocument{
    	CPRS_NOTE(TemplateType.CPRS_HEADER, TemplateType.CPRS_ENTRY, TemplateType.CPRS_FOOTER),
    	VET_SUMMARY(TemplateType.VET_SUMMARY_HEADER, TemplateType.VET_SUMMARY_ENTRY, TemplateType.VET_SUMMARY_FOOTER);
    	
    	TemplateType headerType, entryType, footerType;
    	
    	DocumentType(TemplateType headerType, TemplateType entryType, TemplateType footerType){
    		this.headerType = headerType;
    		this.entryType = entryType;
    		this.footerType = footerType;
    	}

		@Override
		public TemplateType getHeaderType() {
			return headerType;
		}

		@Override
		public TemplateType getEntryType() {
			return entryType;
		}

		@Override
		public TemplateType getFooterType() {
			return footerType;
		}
    
    }
}