package gov.va.escreening.template.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gov.va.escreening.variableresolver.AssessmentVariableDto;
/**
 * Freemarker helper function takes a table question and returns an array of Maps where each entry is a row/entry, and the Map 
 * at that location has a key for every question which was answered for that row.  
 * It was done this way for simple calculation of number of rows used by numberOfEntries function.
 * Note: in the future we can change the way we organize the table question children array so it is not so flat. This would required
 * the update of any templates which were hand made (currently template IDs are: 6,12,14,20,22)
 * 
 * @author Robin Carnow
 *
 */
public class TableHashCreator {

	public List<Map<Integer, AssessmentVariableDto>> create(AssessmentVariableDto table){
		if(table == null || table.getChildren() == null || table.getChildren().isEmpty()){
			return Collections.emptyList();
		}
		List<Map<Integer, AssessmentVariableDto>> rows = new ArrayList<>();
		
		for(AssessmentVariableDto qVar : table.getChildren()){
			if(qVar.getVariableId() != null && qVar.getChildren() != null && !qVar.getChildren().isEmpty()){
				Integer rowIndex = qVar.getChildren().get(0).getRow();
				if(rowIndex != null){
					ensureSize(rows, rowIndex);
					rows.get(rowIndex).put(qVar.getVariableId(), qVar);
				}
			}
		}
		
		return rows;
	}

	private void ensureSize(List<Map<Integer, AssessmentVariableDto>> rows,
			Integer rowIndex) {
		if(rowIndex >= rows.size()){
			for(int i = rows.size(); i <= rowIndex; i++){
				rows.add(new HashMap<Integer, AssessmentVariableDto>());
			}
		}
	}
	
/*	was using this originally:
 * <#assign rows = []>
	<#if table != DEFAULT_VALUE && table.children?? && !(wasAnswerNone(variableObj)) >
		<#list variableObj.children as qVar>
			<#if qVar.variableId && qVar.children?? && (qVar.children?size > 0) && qVar.children(0).row?? >
				<#-- add entire question variable to the hash at the row index given by the first child response -->
				<#assign rowIndex = qVar.children(0).row?int >
				<#if !(rows[rowIndex]??)>
					<#assign rows[rowIndex] = []>
				</#if>
				<#assign rows[rowIndex][qVar.variableId] = qVar >
			</#if>
		</#list>
	</#if>
*/
}
