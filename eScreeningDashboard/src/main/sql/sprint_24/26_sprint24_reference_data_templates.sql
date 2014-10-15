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

INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10540, 15);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10541, 15);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10542, 15);

/*** Exposures skip logic tiket-589 ***/
update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
EXPOSURES:
${MODULE_TITLE_END}
${MODULE_START}
	 <#-- var2200: ${var2200}<br><br>  var2230: ${var2230}<br><br>  var2240: ${var2240}<br><br>  var2250: ${var2250}<br><br> var2260: ${var2260}<br><br>  -->
	
    <#assign nextLine = "">
	<#if (var10540.children)??  && ((var10540.children)?size > 0) || (var2200.children)?? || ((var2230.children)?? && (var2240.children)?? && (var2250.children)??) || (var2260.children)?? || (var2289?? 
		&& getFormulaDisplayText(var2289) != "notset" && getFormulaDisplayText(var2289) != "notfound") > 
	 	
	 	<#if (var10540.children)??  && ((var10540.children)?size > 0)>
	 	<#if isSelectedAnswer(var10540,var10541)>
	 		The Veteran is not concerned about, but may have been exposed
	 	<#elseif isSelectedAnswer(var10540,var10542)>
	 		The Veteran reported persistent major concerns related to the effects of exposure
	 	</#if>
	  	<#if (var2200.children)?? && hasValue(var2200) && getSelectMultiDisplayText(var2200)!= "notfound"> 	
			${NBSP}to ${getSelectMultiDisplayText(var2200)}
            <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		</#if>
		.
		</#if>
	
	    <#if var2289?? 
		&& getFormulaDisplayText(var2289) != "notset" && getFormulaDisplayText(var2289) != "notfound">
		<#assign score = getFormulaDisplayText(var2289)>
		<#assign scoreText = "notset">
	
		<#if (score?number >= 1) >
				<#assign scoreText = "being exposed">
		<#elseif score?number == 0>
			<#assign scoreText = "no exposure">
		</#if> 
        ${nextLine}The Veteran reported ${scoreText} to animal contact during a deployment in the past 18 months that could result in rabies.
        <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">	
		</#if>
		<#if (var2260.children)??  && hasValue(var2260)> 	
			<#assign combat = getSelectMultiDisplayText(var2260)>
			<#if combat == "notfound">
				<#assign combatText = "none">
			<#else>
				<#assign combatText = combat>
			</#if>
		</#if>
		${nextLine}The Veteran reported the following types of combat experience: ${combatText}.

	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}
'
where template_id = 15;


/*********** SERVICE INJURIES SKIP LOGIC **********************/

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SERVICE INJURIES:
${MODULE_TITLE_END}
${MODULE_START}
	<#-- var1380: ${var1380!""}<br><br>  var1390: ${var1390!""}<br><br>  var1400: ${var1400!""}<br><br>  var1410: ${var1410!""}<br><br>  var1480: ${var1480!""}<br><br>  var1490: ${var1490!""}<br><br> -->
	<#if (var1380.children)?? && (var1390.children)?? && (var1400.children)?? && (var1410.children)?? && (var1420.children)?? 
			&& (var1430.children)?? && (var1440.children)?? && (var1450.children)?? && (var1460.children)?? && (var1470.children)?? 
			&& (var1380.children?size > 0) && (var1390.children?size > 0)  && (var1400.children?size > 0) && (var1410.children?size > 0)
			&& (var1420.children?size > 0) && (var1430.children?size > 0) && (var1440.children?size > 0) && (var1450.children?size > 0)
			&& (var1460.children?size > 0) && (var1470.children?size > 0) 
			|| ((var1480.children)?? && (var1490.children)?? && (var1480.children?size > 0) && (var1490.children?size > 0))	
			||((var1510.children)??&& (var1510.children?size > 0))>
		
		<#assign answerTextHash = {"var1380":"blast injury", "var1390":"injury to spine or back", "var1400":"burn (second, 3rd degree)", 
									"var1410":"nerve damage", "var1420":"Loss or damage to vision", "var1430":"loss or damage to hearing", 
									"var1440":"amputation", "var1450":"broken/fractured bone(s)", "var1460":"joint or muscle damage", 
									"var1470":"internal or abdominal injuries", "var1480":"other", "var1490":"other"}>
		
		<#-- must have answer questions -->
		<#assign questions1 = [var1380, var1390, var1400, var1410, var1420, var1430, var1440, var1450, var1460, var1470]>

		<#-- optional answer questions -->
		<#assign questions2 = [var1480, var1490]>


		<#assign outputText = "">
		<#assign errorText = "">
		<#assign sentences = []>
		<#-- organize answers in a way to facilitate output -->
		<#assign noneAnswers = []>
		<#assign combatAnswers = []>
		<#assign nonCombatAnswers = []>
		<#assign otherAnswers = []>
		<#list questions1 as q>
			<#if (q.children[0])?? >
				<#assign key = (q.key)!"">
				<#assign text = (q.children[0].overrideText)!""> 
				<#if text == "none">
					<#assign answer = (answerTextHash[key])!"">
					<#assign noneAnswers = noneAnswers + [answer]> 
				<#elseif text == "combat">
					<#assign answer = (answerTextHash[key])!"">
					<#assign combatAnswers = combatAnswers + [answer]> 
				<#elseif text == "non-combat">
					<#assign answer = (answerTextHash[key])!"">
					<#assign nonCombatAnswers = nonCombatAnswers + [answer]> 
				<#elseif text == "other">
					<#assign answer = (answerTextHash[key])!"">
					<#assign otherAnswers = otherAnswers + [answer]> 
				</#if>
			<#else>
				<#-- if no children then question was not answered -->
				<#assign errorText =  getNotCompletedText()>
				<#break>
			</#if>
		</#list>

		
		<#if !(errorText?has_content)>
				<#if (var1480.children)?has_content && (var1480.children[0].overrideText == "none")>
					<#assign answer = (answerTextHash["var1480"])!"">
					<#-- dont do anything here this freeform Q is handled differently than the others -->
				<#elseif ((var1480.children?size) > 0) && !((var1481.otherText)?has_content) > 
					<#-- veteran did not complete this answer -->
					<#assign errorText = getNotCompletedText()>
				<#else>
					<#if (var1480.children?size > 0)>
						<#assign otherAnswer = var1481.otherText!"">
						<#list var1480.children as c>
							<#if (c.overrideText == "combat")>
								<#-- put in proper anser bucket -->
								<#assign combatAnswers = combatAnswers + [otherAnswer]> 
							<#elseif (c.overrideText == "non-combat")>
								<#-- put in proper anser bucket -->
								<#assign nonCombatAnswers = nonCombatAnswers + [otherAnswer]> 
							</#if>
						</#list>
					</#if>
				</#if>	
		</#if>
		
		<#if !(errorText?has_content)>
				<#if (var1490.children)?has_content && (var1490.children[0].overrideText == "none")>
					<#assign answer = (answerTextHash["var1490"])!"">
					<#-- dont do anything here this freeform Q is handled differently than the others -->
				<#elseif ((var1490.children?size) > 0) && !((var1491.otherText)?has_content) > 
					<#-- veteran did not complete this answer -->
					<#assign outputText = getNotCompletedText()>
				<#else>
					<#if (var1490.children?size > 0)>
						<#assign otherAnswer = var1491.otherText!"">
						<#list var1490.children as c>
							<#if (c.overrideText == "combat")>
								<#-- put in proper anser bucket -->
								<#assign combatAnswers = combatAnswers + [otherAnswer]> 
							<#elseif (c.overrideText == "non-combat")>
								<#-- put in proper anser bucket -->
								<#assign nonCombatAnswers = nonCombatAnswers + [otherAnswer]> 
							</#if>
						</#list>
					</#if>
				</#if>	
		</#if>
		
		<#-- build first two sentences -->
		<#if !(errorText?has_content)>
		<#if combatAnswers?has_content>
			The following injuries were reported during combat deployment: ${createSentence(combatAnswers)}.${LINE_BREAK}
		<#else>
			The Veteran reported no injuries during combat deployment.${LINE_BREAK}
		</#if>
		
		<#if nonCombatAnswers?has_content>
			The following injuries were reported during other service period or training: ${createSentence(nonCombatAnswers)}.${LINE_BREAK}
		<#else>
			The Veteran reported no injuries during other service period or training.${LINE_BREAK}
		</#if>
		</#if>
		
		<#if (var1510.children)??>
			<#assign frag = "">
			<#if isSelectedAnswer(var1510,var1512)>
				<#assign frag = "has">
			</#if>
			<#if isSelectedAnswer(var1510,var1511) || isSelectedAnswer(var1510,var1513)>
				<#assign frag = "does not have">
			</#if>
			The Veteran ${frag} a service connected disability rating.
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 16;

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Health Status:
${MODULE_TITLE_END}
${MODULE_START}
	  <#if var1189?? && (var1020.children)?? && (var1030.children)?? && (var1040.children)?? && (var1050.children)?? && (var1060.children)?? 
		&& (var1070.children)?? && (var1080.children)?? && (var1090.children)?? && (var1100.children)?? && (var1110.children)?? 
		&& (var1120.children)?? && (var1130.children)?? && (var1140.children)?? && (var1150.children)?? && (var1160.children)??
		&& ((var1020.children)?size > 0) && ((var1030.children)?size > 0) && ((var1040.children)?size > 0) && ((var1050.children)?size > 0) 
		&& ((var1060.children)?size > 0) && ((var1070.children)?size > 0) && ((var1080.children)?size > 0) && ((var1090.children)?size > 0) 
		&& ((var1100.children)?size > 0) && ((var1110.children)?size > 0) && ((var1120.children)?size > 0) && ((var1130.children)?size > 0) 
		&& ((var1140.children)?size > 0) && ((var1150.children)?size > 0) && ((var1160.children)?size > 0)>  
	
	<#assign totalScore = getFormulaDisplayText(var1189)>
	<#assign scoreText = "">
	<#if totalScore != "notset" && totalScore != "notfound">
		<#assign totalScore = totalScore?number>
		<#if (totalScore <= 4)>
			<#assign scoreText = "minimal">
		<#elseif (totalScore >= 5 &&  (totalScore <= 9))>
			<#assign scoreText = "low number of">
		<#elseif (totalScore >= 10 &&  (totalScore <= 14))>
			<#assign scoreText = "medium number of">
		<#elseif (totalScore >= 15 &&  (totalScore <= 30))>
			<#assign scoreText = "high number of">
		</#if>
	</#if>

	<#-- During the past 4 weeks, how much have you been bothered by any of the following problems -->
	<#-- this is almost identical to Other Health Symptoms -->
	<#assign fragments = []> 
    <#if (getScore(var1150) > 1)>
		<#assign fragments = fragments + ["stomach pain"] >
	</#if>
    <#if (getScore(var1160) > 1)>
		<#assign fragments = fragments + ["back pain"] >
	</#if>
	<#if (getScore(var1020) > 1)>
		<#assign fragments = fragments + ["pain in arms, legs or joints (knees, hips, etc.)"] >
	</#if>
	<#if (getScore(var1030) > 1)>
		<#assign fragments = fragments + ["menstrual cramps or other problems with your periods"] >
	</#if>
	<#if (getScore(var1040) > 1)>
		<#assign fragments = fragments + ["headaches"] >
	</#if>
	<#if (getScore(var1050) > 1)>
		<#assign fragments = fragments + ["chest pain"] >
	</#if>
	<#if (getScore(var1060) > 1)>
		<#assign fragments = fragments + ["dizziness"] >
	</#if>
	<#if (getScore(var1070) > 1)>
		<#assign fragments = fragments + ["fainting spells"] >
	</#if>
	<#if (getScore(var1080) > 1)>
		<#assign fragments = fragments + ["feeling your heart pound or race"] >
	</#if>
	<#if (getScore(var1090) > 1)>
		<#assign fragments = fragments + ["shortness of breath"] >
	</#if>
	<#if (getScore(var1100) > 1)>
		<#assign fragments = fragments + ["pain or problems during sexual intercourse"] >
	</#if>
	<#if (getScore(var1110) > 1)>
		<#assign fragments = fragments + ["constipation, loose bowels or diarrhea"] >
	</#if>
	<#if (getScore(var1120) > 1)>
		<#assign fragments = fragments + ["nausea, gas or indigestion"] >
	</#if>
	<#if (getScore(var1130) > 1)>
		<#assign fragments = fragments + ["feeling tired or having low energy"] >
	</#if>
	<#if (getScore(var1140) > 1)>
		<#assign fragments = fragments + ["trouble sleeping"] >
	</#if>

	<#if fragments?has_content  >
		<#assign resolved_fragments =  createSentence(fragments)>
	<#else>
		<#assign resolved_fragments = "None">
	</#if>

	The Veteran reported a ${scoreText} of somatic symptoms.${LINE_BREAK}${LINE_BREAK}
	The Veteran endorsed being bothered a lot by the following health symptoms over the past four weeks: ${resolved_fragments}.${NBSP}

	
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 17;

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Screen for Infectious Disease and Embedded Fragments:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var2500.children)?? && ((var2500.children)?size > 0) || ((var2501.children)?? && ((var2501.children)?size > 0))
	 || ((var2502.children)?? && ((var2502.children)?size > 0)) || ((var2009.children)?? && ((var2009.children)?size > 0))>

	<#-- ${createSentence(parts)}. -->
	<#assign questions = [var2500,var2501,var2502,var2009]>
	<#assign textHash = {"var2500": "chronic diarrhea or other gastrointestinal", 
							"var2501": "pain, unexplained fevers", 
							"var2502": "persistent popular or nodular skin rash that began after deployment to Southwest Asia", 
							"var2009": "suspects that he/she has retained fragments or shrapnel as a result of injuries"}>
	
	<#assign parts = []>
		
	<#list questions as q >
		<#assign child = q.children[0] >
		<#if ((child.calculationValue?number) > 0)>
			<#assign problem = textHash[q.key] >
			<#assign part = problem + ", with symptoms of " + getVariableDisplayText(child) >
			<#assign parts = parts + [part] >
		</#if>
	</#list>
	
	<#if parts?has_content>
		The Veteran endorsed other health problems of: ${createSentence(parts)}. ${NBSP}
	<#else>
		${getAnsweredNoAllText("OOO Infect & Embedded Fragment CR")}. ${NBSP}
	</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 19;

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PAIN:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var2300.children)?? && (var2300.children)?? || (var2330.children)?? >  

		<#if (var2300.children?size == 0)  || ((var2330.children?size == 0) || ((var2334.value) != "true"))>
			${getNotCompletedText()}
		<#else>
			<#--  --> 
			<#assign fragments = []>
			<#assign text ="notset">  
	
			<#assign level = getSelectOneDisplayText(var2300)!("-1"?number)>
			<#assign bodyParts = getTableMeasureValueDisplayText(var2330)!"">
			<#if !(bodyParts?has_content) && ((var2334.value) == "true")>
				<#assign bodyParts = "none">
			</#if>
			<#if level?? >
				Over the past month, the Veteran\'s reported pain level was ${level} of 10.
			</#if> 
			<#if bodyParts != "none">
				The pain was located in: ${bodyParts}.
			</#if>
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}
'
where template_id = 20;

update template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Promis Pain Scales:
${MODULE_TITLE_END}
${MODULE_START}

	<#if (var2550.children[0])?? || (var2560.children[0])?? || (var2570.children[0])?? || var2640??>
		
		<#if var2550?? && var2550.children?? && (var2550.children[0])??>
		The Veteran endorsed having ${getSelectOneDisplayText(var2550)} pain currently. 
		</#if>
		<#if var2560?? && var2560.children?? && (var2560.children[0])??>
		     Over the past week the veteran reported a pain intensity of ${getSelectOneDisplayText(var2560)} 
		     <#if var2570?? && var2570.children?? && (var2570.children[0])??>
		      ${NBSP}with ${getSelectOneDisplayText(var2570)} average pain. ${LINE_BREAK}${LINE_BREAK}
		     </#if>
		</#if>
		
		<#if var2640??>
			<#assign score = getFormulaDisplayText(var2640)>
			<#if score != "notset" && score != "notfound">
				<#assign score = score?number>		
				<#if (score >= 0) && (score < 17)>
					<#assign scoreText = "does not">
				<#elseif (score >= 17) && (score <= 998)>
					<#assign scoreText = "does">
				<#else>
					<#assign scoreText = "notset">
				</#if>
				<#if scoreText != "notset">
					The pain ${scoreText} significantly interfere.${NBSP}
				</#if>
			</#if>
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 21;


update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PRIOR TREATMENT:
${MODULE_TITLE_END}
${MODULE_START}
    <#assign nextLine = "">
	<#-- var1520: ${var1520!""}<br><br>  var1521: ${var1521!""}<br><br> -->

  	<#if (var1520.children)?? && ((var1520.children)?size > 0) || ((var1530.children)?? 
			 && ((var1530.children)?size > 0)) >
	  
	<#--  -->
	<#assign fragments = []>
	<#assign text =""> 
		
	<#if var1520?has_content> 	
		<#if isSelectedAnswer(var1520, var1521)><#-- was answer "none" or something else -->
			The Veteran reported being diagnosed with no prior mental health issues in the last 18 months.
			<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		<#else>
			The Veteran endorsed being diagnosed with ${getSelectMultiDisplayText(var1520)} within the last 18 months.
			<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		</#if>
	</#if>
	
	<#if var1530?has_content>
		<#if isSelectedAnswer(var1530, var1531)><#-- was answer "none" or something else -->
			${nextLine}The Veteran reported receiving no prior mental health treatment for a mental health diagnosis in the last 18 months.
		<#elseif getSelectMultiDisplayText(var1530) != "notfound">
			${nextLine}The Veteran endorsed having received the following treatments within the last 18 months for a mental health diagnosis: ${getSelectMultiDisplayText(var1530)}.
		</#if>
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}
'
where template_id = 23;


update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
WHODAS 2.0: The Veteran was given the WHODAS 2.0, which covers six domains of assessing health status and disability on a scale of one to five. 
${MODULE_TITLE_END}
${MODULE_START}
	<#function getScoreText score>
			<#assign text = "">
			<#if (score?number >= 1) && (score?number < 2)>
				<#assign text = "no">
			<#elseif (score?number >= 2) && (score?number < 3)>
				<#assign text = "mild">
			<#elseif (score?number >= 3) && (score?number < 4)>
				<#assign text = "moderate">
			<#elseif (score?number >= 4) && (score?number < 5)>
				<#assign text = "severe">
			<#elseif (score?number >= 5) && (score?number < 6)>
				<#assign text = "extreme">
			</#if>

			<#return text>
	</#function>	

		  <#t><b>Understanding and Communicating</b> - the veteran had an average score of${NBSP}
		  <#if var4119?? && (var4119.value)??>
			${var4119.value} which indicates ${getScoreText(var4119.value)} disability. ${LINE_BREAK}${LINE_BREAK}
		  <#else>
		  	could not be calculated.
		  </#if>
		  ${LINE_BREAK}${LINE_BREAK}
		  
			<b>Mobility</b> - the veteran had an average score of${NBSP}
			<#if var4239?? && (var4239.value)??>
			 ${var4239.value}, which indicates ${getScoreText(var4239.value)} disability.
			<#else> could not be calculated.
			</#if>
			
			${LINE_BREAK}${LINE_BREAK}
			<b>Self-Care</b> - the veteran had an average score of 
			<#if var4319?? && var4319.value??>
				${var4319.value} which indicates ${getScoreText(var4319.value)} disability.
			<#else>could not be calculated.
			</#if>
			${LINE_BREAK}${LINE_BREAK}		

			<b>Getting Along</b> - the veteran had an average score of 
			<#if var4419?? && var4419.value??>
				${var4419.value} which indicates ${getScoreText(var4419.value)} disability.
			<#else>could not be calculated.
			</#if>
			 ${LINE_BREAK}${LINE_BREAK}

			<b>Life Activities (Household/Domestic)</b> - the veteran had an average score of
			<#if var4499?? && var4499.value??>
			 ${var4499.value} which is a rating of ${getScoreText(var4499.value)} disability. 
			<#else>could not be calculated.
			</#if>
			 ${LINE_BREAK}${LINE_BREAK} 
			
			<#if var4200?? && ((var4200.children)?? && ((var4200.children)?size > 0))>
				<#if isSelectedAnswer(var4200, var4202)>
					<b>Life Activities (School /Work)</b> - the veteran had an average score of ${var4559.value} which is a rating of ${getScoreText(var4559.value)} disability. ${LINE_BREAK}${LINE_BREAK}    
				<#elseif isSelectedAnswer(var4200, var4201)>
					<b>Life Activities (School /Work)</b> - the veteran did not get a score because the veteran does not work or go to school. ${LINE_BREAK}${LINE_BREAK}   
				</#if>
			</else>
				<b>Life Activities (School /Work)</b> - the veteran had an average score of could not be calculated.
			</#if>
			
			<b>Participation in Society</b> - the veteran had an average score of 
			
			<#if var4789?? && var4789.value??>
			${var4789.value} which indicates ${getScoreText(var4789.value)} disability. ${NBSP} 
			<#else>could not be calculated.
			</#if>
${MODULE_END}'
where template_id = 24;

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
A/V HALLUCINATIONS:
${MODULE_TITLE_END}
${MODULE_START}

	<#if (var1350.children)?? && (var1350.children?size > 0) || ((var1360.children)?? && (var1360.children?size > 0))>  		
		The Veteran ${NBSP}
		<#if (var1350.children)?? && (var1350.children?size > 0)>
			<#assign Q1_Score = getScore(var1350)>
			<#if (Q1_Score > 0) >
				reported hearing things other people can\'t hear
			<#else>
				denied audio hallucinations
			</#if>
		</#if>
		
		<#if ((var1360.children)?? && (var1360.children?size > 0))>
		  <#assign Q2_Score = getScore(var1360)>		
			<#if (Q2_Score > 0) >
				,reported seeing things or having visions other people can\'t see"
			<#else>
				,denied visual hallucinations
			</#if>
		</#if>
		.
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 29;

update template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
TBI: 
${MODULE_TITLE_END}
${MODULE_START}

	<#assign scoreText = "negative">
	<#if var10717?? && var10717.value??>
		<#if (var10717.value?number  >=1) >
			<#assign scoreText = "positive">
		</#if>
	</#if>
	<#if !var10714?? || (var10714?? && !10715??) || (var10715?? && !var10716??) || (var10716?? && !var10717??)>
		<#assign scoreText = "not calculated due to the Veteran declining to answer some or all of the questions">			
	</#if>			
	The Veteran\'s TBI screen was ${scoreText}. ${NBSP}
			
	<#if var2047??>
		Veteran ${getSelectOneDisplayText(var2047)} to TBI consult for further evaluation. ${NBSP}
    </#if>
	
${MODULE_END}
'
where template_id = 30;

delete from variable_template where template_id = 30;

