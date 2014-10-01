/* ********************************************** */
/* template   UPDATES */
/* ********************************************** */
/********Service History Skip Logic *********************/

UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SERVICE HISTORY: 
${MODULE_TITLE_END}
${MODULE_START}
        <#assign nextLine = "">
		<#if (var3180.children)??  && (var3180.children?size>0) || ((var3160.children)?? && (var3160.children)?has_content)>
			<#-- var3180: ${var3180}<Br><br>  -->	
			<#if (var3180.children)??  && (var3180.children?size>0)>			
			<#assign rows = {}>

			<#list ((var3180.children)![]) as v>
				<#list v.children as c>
					<#if (c.row)?? >
						<#assign row_idx = (c.row)?string>
						<#assign row_key = (c.key)?string>
						<#assign row_value = (c.displayText)?string>
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

			<#assign isFirst = true>
			<#list uniqueRows as ur>
				
				<#assign group = {}>
				<#assign group_keys = []>
				<#list rows?keys as rk>
					<#if rk?starts_with(ur)>
						<#assign group = group + {rk:rows[rk]}> 
						<#assign group_keys = group_keys + [rk]>
					</#if>
				</#list>
			
				<#assign type = "">
				<#assign branch = "">
				<#assign beg_yr = "">
				<#assign end_yr = "">
				<#assign dischargeType = "">
				<#assign rank = "">
				<#assign job = "">
				
				<#assign typeIsComplete = false>
				<#assign branchIsComplete = false>
				<#assign beg_yrIsComplete = false>
				<#assign end_yrIsComplete = false>
				<#assign dischargeTypeIsComplete = false>
				<#assign rankIsComplete = false>
				<#assign jobIsComplete = false>

				<#-- build a row -->
				<#list group_keys as gk>
			
					<#assign var = ((gk?split("_"))[1])!"">
					
					<#-- check if TYPE answer is present -->
					<#if (["var3071", "var3072", "var3073"])?seq_contains(var)>
						<#assign type =  ((group[gk])!"")>
						<#if (type?length > 0)>
							<#assign typeIsComplete = true>
						</#if>						
					</#if>

					<#-- check if BRANCH answer is present -->
					<#if (["var3081", "var3082", "var3083", "var3084", "var3085", "var3086"])?seq_contains(var)>
						<#assign branch =  ((group[gk])!"")>
						<#if (branch?length > 0)>
							<#assign branchIsComplete = true>
						</#if>						
					</#if>

					<#-- check if BEGINNING YEAR answer is present -->
					<#if (["var3091"])?seq_contains(var)>
						<#assign beg_yr =  ((group[gk])!"")>
						<#if (beg_yr?length > 0)>
							<#assign beg_yrIsComplete = true>
						</#if>												
					</#if>


					<#-- check if ENDING YEAR answer is present -->
					<#if (["var3101"])?seq_contains(var)>
						<#assign end_yr =  ((group[gk])!"")>
						<#if (end_yr?length > 0)>
							<#assign end_yrIsComplete = true>
						</#if>						
					</#if>


					<#-- check if DISCHARGE TYPE answer is present -->
					<#if (["var3111", "var3112", "var3113", "var3114", "var3115", "var3116", "var3117"])?seq_contains(var)>
						<#assign dischargeType =  ((group[gk])!"")>
						<#if (dischargeType?length > 0)>
							<#assign dischargeTypeIsComplete = true>
						</#if>						
					</#if>


					<#-- check if RANK answer is present -->
					<#if (["var3121", "var3122", "var3123", "var3124", "var3125", "var3126", "var3127",
							"var3128", "var3129", "var3130", "var3131", "var3132", "var3133", "var3134",
							"var3135", "var3136", "var3137", "var3138", "var3139", "var3140"])?seq_contains(var)>
						<#assign rank =  ((group[gk])!"")>
						<#if (rank?length > 0)>
							<#assign rankIsComplete = true>
						</#if>						
					</#if>

					<#-- check if JOB answer is present -->
					<#if (["var3151"])?seq_contains(var)>
						<#assign job =  ((group[gk])!"")>
						<#if (job?length > 0)>
							<#assign jobIsComplete = true>
						</#if>						
					</#if>
			</#list>

			<#if typeIsComplete>
				<#if isFirst>
					<#assign isFirst=false>	
					The Veteran reported entering the ${type} 
				<#else>
					${nextLine}The Veteran reported previous enlistment in the ${type}
				</#if>
				<#if branchIsComplete> ${NBSP}${branch}</#if>
				<#if beg_yrIsComplete> ${NBSP}in ${beg_yr}</#if>
				<#if dischargeTypeIsComplete && end_yrIsComplete>, received an ${dischargeType} discharge in ${end_yr}
				<#elseif dischargeTypeIsComplete>, received an ${dischargeType} discharge
				<#elseif end_yrIsComplete>, received a discharge in ${end_yr}
				</#if>
				<#if rankIsComplete>${NBSP}at the rank of ${rank}</#if>.
				<#assign nextLine="${LINE_BREAK}${LINE_BREAK}">
			</#if>
		  </#list>
		</#if>	
		  <#if ((var3160.children)?? && (var3160.children)?has_content)>
			${nextLine}The Veteran also served in the following operations or combat zones: ${getSelectMultiDisplayText(var3160)}.
            <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		  </#if>
		<#else>	 
			${getNotCompletedText()}. ${NBSP}
		</#if>
${MODULE_END}
'
where template_id = 12;

/** Update OIR/OEF skip logic ***/
UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
OEF/OIF CLINICAL REMINDER:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var660.children)?? && ((var660.children)?size > 0)  >
	  <#if hasValue(getSelectMultiDisplayText(var660)) >
    	<#if isSelectedAnswer(var660, var661)> <#-- no -->
			The Veteran\'s most recent deployment was not for Operation Enduring Freedom (OEF) or Operation Iraqi Freedom (OIF)
    		
		<#elseif isSelectedAnswer(var660, var659)><#-- yes -->
    			The Veteran\'s most recent deployment was for " + ${getSelectOneDisplayText(var660)}
    			<#if var683?? && getSelectOneDisplayText(var683) != "notfound"> ${NBSP}in ${getSelectOneDisplayText(var683)}</#if>   		
		<#elseif isSelectedAnswer(var660, var662)><#-- yes -->
    		The Veteran\'s most recent deployment was for ${getSelectOneDisplayText(var660)}
    		<#if var663?? && getSelectOneDisplayText(var663) != "notfound"> ${NBSP}in ${getSelectOneDisplayText(var663)}</#if>
    	</#if>
    	. ${NBSP}
    </#if>
	<#else>
		${getNotCompletedText()}.
	</#if>
${MODULE_END}'
where template_id = 13;

update template 
set template_file = '
<#include "clinicalnotefunctions">
 <#-- Template start --> ${MODULE_TITLE_START} MILITARY DEPLOYMENTS AND HISTORY: ${MODULE_TITLE_END} 
 ${MODULE_START} <#assign deployments_section>  
 <#assign allComplete = false> 
 <#assign allAnswersNone = false> 
 
  <#-- var5000 --> 
  <#assign Q1_text = ""> <#assign Q1_complete = false> <#assign Q1_isAnswerNone = false>
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
   			<#assign Q2_isAnswerNone = true> <#assign Q2_complete = true> 
   		<#else> <#assign Q2_isAnswerNone = false> 
   		</#if> 
   </#if> 
   <#if !Q2_isAnswerNone && !Q2_complete && (var5020.children)?? && ((var5020.children)?size > 0)> 
   		<#assign Q2_complete = true> 
   </#if>  
   
   <#-- var5130 --> 
   <#assign Q3_text = ""> <#assign Q3_complete = false> <#assign Q3_isAnswerNone = false> 
   
   <#if (var5131.value)??>
        <#assign Q3_complete = true> 
   		<#if var5131.value = "true"> 
   			<#assign Q3_isAnswerNone = true>
   		<#else> 
   			<#assign Q3_isAnswerNone = false> 
   		</#if> 
   </#if>
    
   <#assign counter = 0> 
   <#assign all_rows = "">  
   <#if !Q3_isAnswerNone && (var5130.children)?? && (var5130.children)?has_content> 
   		<#assign rows = {}> 
   
   		<#list ((var5130.children)![]) as v> 
   			<#if (v.children)?? > 
   				<#list v.children as c> 
   					<#if (c.row)??> 
   						<#assign beg_months = ["var5061", "var5062", "var5063", "var5064", "var5065", "var5066", "var5067", "var5068", "var5069", "var5070", "var5071", "var5072"]>  
   						<#assign end_months = ["var5101", "var5102", "var5103", "var5104", "var5105", "var5106", "var5107", "var5108", "var5109", "var5110", "var5111", "var5112"]>  
   
   						<#if beg_months?seq_contains(c.key)> <#assign row_key = "var5060"> 
   						<#elseif end_months?seq_contains(c.key)> 
   							<#assign row_key = "var5100"> 
   						<#else> <#assign row_key = (c.key)?string> 
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
    		<#assign innerNextLine = ""> 
    		<#list uniqueRows as r> 
    			<#assign loc = (rows[r + "_" + "var5041"])!"">  
    			<#assign beg_month = (rows[r + "_" + "var5060"])!""> 
    			<#assign beg_yr = (rows[r + "_" + "var5081"])!"">  
    			<#assign end_month = (rows[r + "_" + "var5100"])!""> 
    			<#assign end_yr = (rows[r + "_" + "var5121"])!"">  
    			<#if (loc?has_content) && (beg_month?has_content) && (beg_yr?has_content) && (end_month?has_content) && (end_yr?has_content)> ${innerNextLine}
    				<#assign all_rows = all_rows +	beg_month + "/" + beg_yr + "-"  + end_month + "/" + end_yr + ": "  + loc + LINE_BREAK> 
    				<#assign innerNextLine = "${LINE_BREAK}"> 
    				<#assign counter = counter + 1>  
    			<#else> 
    			<#-- if Q3 is missing any answers, reset everything  and set complete = false--> 
    				<#assign Q3_complete = false> <#assign all_rows = ""> <#assign counter = 0> 
    				<#break> 
    			</#if> 
    		</#list> 
    		<#if (counter > 0)> 
    			<#assign Q3_complete = true> 
    		</#if> 
    	</#if> 
    </#if>  
    
    <#if Q1_isAnswerNone && Q2_isAnswerNone && Q3_isAnswerNone> <#assign allAnswersNone = true> 
    </#if>     
    <#assign nextLine = ""> 
   	<#if allAnswersNone> ${getAnsweredNoneAllText("Military Deployments History")} 
   	<#else> 
    	<#if Q1_isAnswerNone> The Veteran reported receiving no disciplinary actions. <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}"> 
    	<#elseif Q1_complete> The Veteran reported receiving the following disciplinary actions: ${getSelectMultiDisplayText(var5000)}. 
  			<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}"> 
   		</#if>  
    
   		<#if Q2_isAnswerNone> ${nextLine}The Veteran reported receiving no personal awards or commendations. <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">     		
   		<#elseif Q2_complete> ${nextLine}The Veteran reported receiving the following personal awards or commendations: ${getSelectMultiDisplayText(var5020)}. 
    			<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}"> 
    	</#if>   
    
   		<#if (counter <= 1)> 
   			<#assign frag = counter + " time"> <#assign frag2 = "area"> <#assign frag3 = "period"> 
   		<#else> 
   			<#assign frag = counter + " times"> <#assign frag2 = "areas"> <#assign frag3 = "periods">     		
   		</#if> 		
   		${nextLine}The Veteran was deployed ${frag}
   		<#if (counter>0)> to the following ${frag2} during the following time ${frag3}:${LINE_BREAK}${LINE_BREAK} ${all_rows} 
   		<#else>.
   		</#if>
    </#if>  
    </#assign> <#if !(deployments_section = "") > ${deployments_section} <#else> ${noParagraphData} </#if> 
    ${MODULE_END} 
    '
where template_id = 14;

