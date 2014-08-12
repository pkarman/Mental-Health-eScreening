<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
MILITARY DEPLOYMENTS AND HISTORY:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign deployments_section>
	
	<#assign allComplete = false>
	<#assign allAnswersNone = false>

	<#-- var5000 -->
	<#assign Q1_text = "">
	<#assign Q1_complete = false>
	<#assign Q1_isAnswerNone = false>
	<#if (var5001.value)??>
		<#if var5001.value = "true">
			<#assign Q1_isAnswerNone = true>
			<#assign Q1_complete = true>
		<#else>
			<#assign Q1_isAnswerNone = false>
		</#if>
	</#if>
	<#if !Q1_isAnswerNone && !Q1_complete && (var5000.children)?? && ((var5000.children)?size > 0)>
		<#assign Q1_complete = true>
	</#if>

	<#-- var5020 -->
	<#assign Q2_text = "">
	<#assign Q2_complete = false>
	<#assign Q2_isAnswerNone = false>
	<#if (var5021.value)??>
		<#if var5021.value = "true">
			<#assign Q2_isAnswerNone = true>
			<#assign Q2_complete = true>
		<#else>
			<#assign Q2_isAnswerNone = false>
		</#if>
	</#if>
	<#if !Q2_isAnswerNone && !Q2_complete && (var5020.children)?? && ((var5020.children)?size > 0)>
		<#assign Q2_complete = true>
	</#if>

	<#-- var5130 -->
	<#assign Q3_text = "">
	<#assign Q3_complete = false>
	<#assign Q3_isAnswerNone = false>
	<#if (var5131.value)??>
		<#if var5131.value = "true">
			<#assign Q3_isAnswerNone = true>
			<#assign Q3_complete = true>
		<#else>
			<#assign Q3_isAnswerNone = false>
		</#if>
	</#if>
	<#assign counter = 0>
	<#assign all_rows = "">

<#if !Q3_isAnswerNone && !Q3_complete && (var5130.children)?? && (var5130.children)?has_content>
	<#assign rows = {}>
	  <#list ((var5130.children)![]) as v>
		<#if (v.children)?? >
		  <#list v.children as c>
		  <#if (c.row)??>
			<#assign beg_months = ["var5061", "var5062", "var5063", "var5064", "var5065", "var5066",
									"var5067", "var5068", "var5069", "var5070", "var5071", "var5072"]>

			<#assign end_months = ["var5100", "var5102", "var5103", "var5104", "var5105", "var5106",
									"var5107", "var5108", "var5109", "var5110", "var5111", "var5112"]>

			<#if beg_months?seq_contains(c.key)>
				<#assign row_key = "var5060">
			<#elseif end_months?seq_contains(c.key)>	
				<#assign row_key = "var5100">
			<#else>
				<#assign row_key = (c.key)?string>
			</#if>

			<#assign row_idx = (c.row)?string>
			<#assign row_value = ((c.displayText)!"")?string>
			<#assign r ={}>
			<#assign row_name = ("row" + row_idx + "_" + row_key)?string > 
			
			<#assign rows =  rows + {row_name:row_value}> 
		   </#if>
		</#list>
		</#if>
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
		<#list uniqueRows as r>
			<#assign loc = (rows[r + "_" + "var5041"])!"">
			
			<#assign beg_month = (rows[r + "_" + "var5060"])!"">
			<#assign beg_yr = (rows[r + "_" + "var5081"])!"">
			
			<#assign end_month = (rows[r + "_" + "var5100"])!"">
			<#assign end_yr = (rows[r + "_" + "var5121"])!"">
			
			<#if (loc?has_content) && (beg_month?has_content) && (beg_yr?has_content) && (end_month?has_content) && (end_yr?has_content)>
				<#assign all_rows = all_rows +	beg_month + "/" + beg_yr + "-"  + end_month + "/" + end_yr + ": "  + loc + "<br>" >
				<#assign counter = counter + 1>
				
			<#else>
				<#-- if Q3 is missing any answers, reset everything  and set complete = false-->
				<#assign Q3_complete = false>
				<#assign all_rows = "">
				<#assign counter = 0>
				<#break>
			</#if>
		</#list>
		<#if (counter > 0)>
			<#assign Q3_complete = true>
		</#if>
	</#if>
  </#if>
  
  	<#if Q1_complete && Q2_complete && Q3_complete>
  		<#assign allComplete = true>
  	</#if>
	<#if Q1_isAnswerNone && Q2_isAnswerNone && Q3_isAnswerNone>
  		<#assign allAnswersNone = true>
  	</#if>
	
	<#if allComplete>
		<#if allAnswersNone>
			${getAnsweredNoneAllText("Military Deployments History")}
		<#else>
			<#if Q1_isAnswerNone>
				The Veteran reported receiving no disciplinary actions.<br><br>
			<#else>
				The Veteran reported receiving the following disciplinary actions: ${getSelectMultiDisplayText(var5000)}.<br><br>
			</#if>

			<#if Q2_isAnswerNone>
				The Veteran reported receiving no personal awards or commendations.<br><br>
			<#else>
				The Veteran reported receiving the following personal awards or commendations: ${getSelectMultiDisplayText(var5020)}.<br><br>
			</#if>

			
			<#if (counter == 1)>
				<#assign frag = counter + " time">
				<#assign frag2 = "area">
				<#assign frag3 = "period">
			<#else>
				<#assign frag = counter + " times">
				<#assign frag2 = "areas">
				<#assign frag3 = "periods">
			</#if>
			The Veteran was deployed ${frag} to the following ${frag2} during the following time ${frag3}:<br><br>
			${all_rows}<br>
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>


  </#assign>
  <#if !(deployments_section = "") >
     ${deployments_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}