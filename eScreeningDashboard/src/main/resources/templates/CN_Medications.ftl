<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
MEDICATIONS:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign med_section>
  
<#if (var3500.children)?? && (var3500.children)?has_content>
	<#assign rows = {}>
	<#assign v3500 = var3500>
	<#list ((v3500.children)![]) as v>
		<#list v.children as c>
		  <#if (c.row)??>
			<#assign row_idx = (c.row)?string>
			<#assign row_key = (c.key)?string>
			<#assign row_value = (c.value)?string>
			<#assign r ={}>
			<#assign row_name = ("row" + row_idx + "_" + row_key)?string >
			<#assign rows =  rows + {row_name:row_value}>
		   </#if>
		</#list>
	 </#list>
	
	<#assign uniqueRows = []>
	<#assign e = []>
	<#if (rows?keys)??>
		<#list (rows?keys?sort) as k>
			<#assign e = (k?split("_"))>
			<#if !(uniqueRows?seq_contains(e[0]))>
				<#assign uniqueRows = uniqueRows + [e[0]]>
			</#if>
		</#list>
	</#if>

	<#assign outputText = "">
	<#if uniqueRows?has_content>
		<#assign all_rows = "">
	
		<#list uniqueRows as r>
			<#assign med = (rows[r + "_" + "var3511"])!"">
			<#assign dose = (rows[r + "_" + "var3521"])!"">
			<#assign freq = (rows[r + "_" + "var3531"])!"">
			<#assign dur = (rows[r + "_" + "var3541"])!"">
			<#assign doc = (rows[r + "_" + "var3551"])!"">

			<#if (med?has_content) && (dose?has_content) && (freq?has_content) && (dur?has_content) && (doc?has_content)>
				<#assign all_rows = all_rows + "<br>" +
					"MEDICATION/SUPPLEMENT: " + med + "<br>" 
					+ "DOSAGE: " + dose + "<br>" 
					+ "FREQUENCY: " + freq + "<br>" 
					+ "DURATION: " + dur + "<br>" 
					+ "PRESCRIBER NAME AND/OR FACILITY: " + doc + "<br><br>" >
			<#else>
				<assign outputText = getNotCompletedText()>
			</#if>
		</#list>
	<#else>
			<assign outputText = getAnsweredNoneAllText("Medications")>
	</#if>

	<#if outputText?has_content>
		${outputText}
	<#else>
		${all_rows}
	</#if>
<#else>
	${getNotCompletedText()}
</#if>

  </#assign>
  <#if !(med_section = "") >
     ${med_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}