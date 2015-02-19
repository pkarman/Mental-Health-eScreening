package gov.va.escreening.dto.template;

import gov.va.escreening.service.AssessmentVariableService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateTableBlockDTO extends TemplateBaseBlockDTO {

	@JsonProperty("type")
	private String nodeType(){return "table";}
	
	private final AssessmentVariableService assessmentVariableService;
	private TemplateVariableContent table;
	static final String VAR_FORMAT = "[var%d]";
	static final String REPLACEMENT_FORMAT = "getTableVariable(%s, %s, %s)";
	
	
	@Autowired
    public TemplateTableBlockDTO(AssessmentVariableService assessmentVariableService){
		this.assessmentVariableService = assessmentVariableService;
	}
	
	public TemplateVariableContent getTable() {
		return table;
	}

	public void setTable(TemplateVariableContent table) {
		this.table = table;
	}
	
	@Override
	public StringBuilder appendFreeMarkerFormat(StringBuilder sb, Set<Integer>ids) {
		StringBuilder result = addHeader(sb);
		
		String varName = String.format(VAR_FORMAT, table.getContent().getId());
		String tableHashName = createTableHashName(result);
		String rowIndexName = createRowIndexName(result);
		
		//add id for table
		ids.add(table.getContent().getId());
		
		//create table hash object
		result.append("<#assign ").append(tableHashName).append("=createTableHash(").append(varName).append(")> \n");		
		result.append("<#assign lastIndex=(").append(tableHashName).append("?size)-1 > \n");
		
		//start loop over rows using rowIndexName as the index
		result.append("<#if lastIndex >= 0> \n");
		result.append("<#list 0..lastIndex as ").append(rowIndexName).append("> \n");
		
		//add all child blocks
		result = addChildren(result, ids);
		
		//close if and list loop
		result.append("</#list>\n</#if>\n");
	
		//collect all child question variable IDs and search for their use in child blocks (i.e. VAR_FORMAT)
		//for usages of each child question, substitute a call to look up the appropriate AV by row (i.e. REPLACEMENT_FORMAT)
		for(Integer avId : assessmentVariableService.getAssessmentVarsForMeasure(table.getContent().getMeasureId()).keySet()){
			if(avId != table.getContent().getId()){
				String varValue = String.format(VAR_FORMAT, avId);
				String replacement = String.format(REPLACEMENT_FORMAT, tableHashName, varValue, rowIndexName);
				int foundVarIndex = result.indexOf(varValue);
				while(foundVarIndex > -1){
					result.replace(foundVarIndex, foundVarIndex + varValue.length(), replacement);
					//look for the next one
					foundVarIndex = result.indexOf(varValue, foundVarIndex + replacement.length());
				}
			}
		}

		return result;
	}
	
	String createTableHashName(StringBuilder sb){
		//guards against the same table question used both as a table block and then again as a child block of itself
		Integer seed = sb.length();
		return "table_hash_" + table.getContent().getId() + "_" + seed;
	}
	
	String createRowIndexName(StringBuilder sb){
		//guards against the same table question used both as a table block and then again as a child block of itself
		Integer seed = sb.length();
		return "row_index_" + table.getContent().getId() + "_" + seed;
	}
}
