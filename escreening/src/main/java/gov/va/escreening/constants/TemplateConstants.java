package gov.va.escreening.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;


public class TemplateConstants {
	
    public enum ViewType {HTML, TEXT, CPRS_PREVIEW}
    
    /**
     * Each template type found in DB lookup
     * @author Robin Carnow
     *
     */
    public enum TemplateType{
    	CPRS_HEADER(1, "CPRS header"), CPRS_FOOTER(2, "CPRS footer"), CPRS_ENTRY(3, "CPRS module", true), CPRS_PROGRESS_HISTORY(11, "CPRS module longitudinal progress over time", true),
    	ASSESS_SCORE_TABLE(4, "assessment score table"), ASSESS_CONCLUSION(5, "assessment conclusion"), ASSESS_WELCOME(10, "assessment welcome"),
    	VET_SUMMARY_HEADER(6, "veteran summary header"), VET_SUMMARY_FOOTER(7, "veteran summary footer"), VET_SUMMARY_ENTRY(8, "veteran summary module", true),
    	VISTA_QA(9, "VistA questions/answers", true);
    	
		private int id;
		private String name;
		private boolean isModuleTemplate;
		
		private TemplateType(int id, String name) {
		    this(id, name, false);
		}
		
		private TemplateType(int id, String name, boolean isModuleTemplate) {
		   this.id = id;
		   this.name = name;
		   this.isModuleTemplate = isModuleTemplate;
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
    
    private static final Map<Integer, TemplateType> idMap = new HashMap<>();
    private static final Set<TemplateType> moduleTemplateTypes;
    private static final String moduleTemplateTypeIdString;
    
    static{
        List<Integer> moduleTemplateIds = new ArrayList<>();
        ImmutableSet.Builder<TemplateType> typeSetBuilder = ImmutableSet.builder();
    	for(TemplateType type : TemplateType.values()){
    		idMap.put(type.getId(),  type);
    	
    		if(type.isModuleTemplate){
    		    typeSetBuilder.add(type);
    		    moduleTemplateIds.add(type.id);
    		}
    	}
    	moduleTemplateTypes = typeSetBuilder.build();
    	moduleTemplateTypeIdString = Joiner.on(',').join(moduleTemplateIds);
    }
    
    /**
     * @param id template type ID
     * @return the TemplateType for the given ID
     */
    public static TemplateType typeForId(Integer id){
    	return idMap.get(id);
    }
    
    public static boolean isSurveyTemplate(Integer typeId){
        return moduleTemplateTypes.contains(typeForId(typeId));
    }
    
    public static Set<TemplateType> moduleTemplateTypes(){
        return moduleTemplateTypes;
    }
    
    public static String moduleTemplateIds(){
        return moduleTemplateTypeIdString;
    }
   
    /**
     * Defines each of the template system's document types 
     * @author Robin Carnow
     *
     */
    public enum DocumentType {
    	CPRS_NOTE(TemplateType.CPRS_HEADER, TemplateType.CPRS_ENTRY, TemplateType.CPRS_FOOTER, TemplateType.VISTA_QA, TemplateType.CPRS_PROGRESS_HISTORY),
    	VET_SUMMARY(TemplateType.VET_SUMMARY_HEADER, TemplateType.VET_SUMMARY_ENTRY, TemplateType.VET_SUMMARY_FOOTER);
    	
    	private final TemplateType headerType, entryType, footerType;
    	private final Set<TemplateType> allTypes;
    	private final Set<TemplateType> batteryTypes; 
    	
    	DocumentType(TemplateType headerType, TemplateType entryType, TemplateType footerType, TemplateType... otherTypes){
    		this.headerType = headerType;
    		this.entryType = entryType;
    		this.footerType = footerType;
    		allTypes = ImmutableSet.<TemplateType>builder()
		            .add(headerType, entryType, footerType)
		            .add(otherTypes)
		            .build();
    		
    		ImmutableSet.Builder<TemplateType> batteryTypeSetBuilder = ImmutableSet.<TemplateType>builder();
    		for(TemplateType type : allTypes){
    		    if(!type.isModuleTemplate){
    		        batteryTypeSetBuilder.add(type);
    		    }
    		}
    		batteryTypes = batteryTypeSetBuilder.build();
    	}

		public TemplateType getHeaderType() {
			return headerType;
		}

		public TemplateType getEntryType() {
			return entryType;
		}

		public TemplateType getFooterType() {
			return footerType;
		}
		
		public Set<TemplateType> getRequiredTypes(){
		    return allTypes;
		}
		
		public Set<TemplateType> getRequiredBatteryTypes(){
		    return batteryTypes;
		}
    
    }
}