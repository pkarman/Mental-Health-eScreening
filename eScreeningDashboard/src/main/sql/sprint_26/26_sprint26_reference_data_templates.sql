update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Depression:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var1550.children)?? && (var1560.children)?? && (var1570.children)?? && (var1580.children)?? && (var1590.children)??
			&& ((var1550.children)?size > 0) && ((var1560.children)?size > 0) && ((var1570.children)?size > 0) 
			&& ((var1580.children)?size > 0) && ((var1590.children)?size > 0)>
	<#assign fragments = []>
	<#assign text ="notset"> 
	<#assign totScore = getFormulaDisplayText(var1599)>
	<#assign score = -1>
	<#assign scoreText ="notset">
		
	<#if totScore?? && totScore != "notfound"  && totScore != "notset"> 
		<#assign score = totScore?number>
		<#if (score == 0) >
			<#assign scoreText = "negative">
		<#elseif (score >= 1) && (score <= 4)>
			<#assign scoreText = "negative">
			<#assign text = "minimal depression">
		<#elseif (score >= 5) && (score <= 9)>
			<#assign scoreText = "negative">
			<#assign text = "mild depression">
		<#elseif (score >= 10) && (score <= 14)>
			<#assign scoreText = "positive">
			<#assign text = "moderate depression">
		<#elseif (score >= 15) && (score <= 19)>
			<#assign scoreText = "positive">
			<#assign text = "moderately severe depression">
		<#elseif (score >= 20) && (score <= 27)>
			<#assign scoreText = "positive">
			<#assign text = "severe depression">
		</#if>

		<#if (score >=1) && (score <= 27)>
			<#t>The Veteran\'s PHQ-9 screen was ${scoreText}.${NBSP} The score was ${(score)?string} which is suggestive of ${text}.${NBSP}
		<#elseif (score == 0)>
			The Veteran\'s PHQ-9 screen was ${scoreText}. The score was ${(score)?string}.${NBSP}
		</#if>
		</#if>
	<#else>
		The Veteran\'s PHQ-9 screen was not calculated due to the Veteran declining to answer some or all of the questions.
	</#if>
${MODULE_END}
' where template_id = 31;

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
		  , The Veteran ${NBSP}		
			<#if (Q2_Score > 0) >
				reported seeing things or having visions other people can\'t see"
			<#else>
				denied visual hallucinations
			</#if>
		</#if>
		.
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 29;

update variable_template set override_display_value='mild' where template_id=21 and assessment_variable_id in (2562,2572);

UPDATE template SET 
	template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SCORING MATRIX: 
${MODULE_TITLE_END}
${MODULE_START}

	<#macro resetRow >
		<#assign screen = "">
		<#assign status = "">
		<#assign score = "">
		<#assign cutoff = "">
	</#macro>

		
		<#assign empty = "--">
		<#assign rows = []>

		<#-- AUDIT C -->
		<#assign screen = "AUDIT C">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		<#assign rows = []>
	
		<#if var1229??>
			<#assign score = getFormulaDisplayText(var1229)>
			<#if score != "notset">
				<#assign cutoff = "3-women/4-men">
				<#assign score = score?number>
				
				<#if (score >= 0) && (score <= 2)>
					<#assign status = "Negative">
				<#elseif (score >= 4) && (score <= 998)>
					<#assign status = "Positive">
				<#elseif (score == 3 )>
					<#assign status = "Positive for women/Negative for men">
				</#if>
			<#else>
				<#assign score = empty> 
			</#if>
			<#if (score?string == empty) || (status == empty)>
				<#assign status = empty>
				<#assign score = empty>
			</#if>
		</#if>
	
		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>



		<#-- TBI -->
		<#assign screen = "BTBIS (TBI)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if (var2047.children)?? && ((var2047.children)?size > 0)>
			
			<#if var3442?? && var3442.value="true">
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif var3441?? && var3441.value="true"> 
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status ="Negative">
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>



		<#-- DAST 10 -->
		<#assign screen = "DAST-10 (Substance Abuse)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>

		<#if var1010?? >
			<#assign score = getFormulaDisplayText(var1010)>
			<#if (score?length > 0) &&  score != "notset">
				<#assign cutoff = "3">
				<#if ((score?number) <= 2)> 
					<#assign status ="Negative">
				<#else> 
					<#assign status ="Positive">
				</#if> 
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- GAD 7 -->
		<#assign screen = "GAD-7 (Anxiety)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var1749?? >
			<#assign score = getFormulaDisplayText(var1749)>
			<#if score != "notset">
					<#assign cutoff = "10">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- HOUSING - Homelessness -->
		<#assign screen = "Homelessness">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var2000?? && var2000.value??>
			<#if var2000.value?number == 0>
			    <#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif var2000.value?number == 1>	
			   <#if var2001?? && var2001.value?? && (var2001.value?number == 1)>
				 <#assign score = "N/A">
				 <#assign cutoff = "N/A">
				 <#assign status = "Positive">
			   <#elseif var2001?? && var2001.value?? && (var2001.value?number == 0) >
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Negative">
				</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- ISI -->
		<#assign screen = "ISI (Insomnia)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var2189?? >
			<#assign score = getFormulaDisplayText(var2189)>
			<#if score != "notset">
					<#assign cutoff = "15">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- MST -->
		<#assign screen = "MST">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var2003??>
			<#if isSelectedAnswer(var2003, var2005)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var2003, var2004)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Negative">
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- PCLC -->
		<#assign screen = "PCL-C (PTSD)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var1929?? >
			<#assign score = getFormulaDisplayText(var1929)>
			<#if score != "notset" && score != "notfound">
					<#assign cutoff = "50">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- PTSD -->
		<#assign screen = "PC-PTSD">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var1989?? >
			<#assign score = getFormulaDisplayText(var1989)>
			<#if score != "notset" && score != "notfound">
					<#assign cutoff = "3">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>
		


	
		<#-- PHQ 9 DEPRESSION -->
		<#assign screen = "PHQ-9 (Depression)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var1599?? >
			<#assign score = getFormulaDisplayText(var1599)>
			<#if score != "notset" && score != "notfound">
					<#assign cutoff = "10">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- Prior MH DX/TX - Prior Mental Health Treatment -->
		<#assign screen = "Prior MH DX/TX">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		<#assign acum = 0>
		<#assign Q1complete =false>
		<#assign Q2complete =false>
		<#assign Q3complete =false>
		<#assign Q4complete =false>
		<#-- var1520: ${var1520!""}<br><br>  var1530: ${var1530!""}<br><br>  var200: ${var200!""}<br><br>  var210: ${var210!""}<br><br>  -->
		<#if (var1520.children)?? && ((var1520.children)?size > 0)>
			<#assign Q1complete = true>
			<#list var1520.children as c>
				<#if ((c.key == "var1522")  || (c.key == "var1523")  || (c.key == "var1524")) && (c.value == "true")>
					<#assign acum = acum + 1>
					<#break>
				</#if>
			</#list>
		</#if>

		<#if (var1530.children)?? && ((var1530.children)?size > 0)>
			<#assign Q2complete = true>
			<#list var1530.children as c>
				<#if ((c.key == "var1532")  || (c.key == "var1533") 
						|| (c.key == "var1534") || (c.key == "var1535") 
						|| (c.key == "var1536")) && (c.value == "true") >
					<#assign acum = acum + 1>
					<#break>
				</#if>
			</#list>
		</#if>
		

		<#if (var200.children)?? && ((var200.children)?size > 0)>
			<#assign Q3complete = true>
			<#list var200.children as c>
				<#if ((c.key == "var202") && (c.value == "true"))  >
					<#assign acum = acum + 1>
					<#break>
				</#if>
			</#list>
		</#if>

		<#if (var210.children)?? && ((var210.children)?size > 0)>
			<#assign Q4complete = true>
			<#list var210.children as c>
				<#if ((c.key == "var214") && (c.value == "true"))  >
					<#assign acum = acum + 1>
					<#break>
				</#if>
			</#list>
		</#if>

		<#if Q1complete && Q2complete && Q3complete && Q3complete>
			<#assign score = "N/A">
			<#assign cutoff = "N/A">
			<#if (acum >= 3)>
				<#assign status = "Positive">
			<#elseif (acum > 0) && (acum <= 2)>
				<#assign status ="Negative">
			</#if>
		</#if>
			
		


		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>
	



		<#-- TOBACCO -->
		<#assign screen = "Tobacco Use">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if (var600.children)?? && ((var600.children)?size > 0)>
			<#if isSelectedAnswer(var600, var603)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var600, var601) || isSelectedAnswer(var600, var602)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Negative">
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- VAS PAIN - BASIC PAIN -->
		<#assign screen = "VAS PAIN">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>

		<#if (var2300)?? >
			<#assign score = getSelectOneDisplayText(var2300)>
			<#if score != "notset" && score != "notfound">
					<#assign cutoff = "4">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			<#else>
				<#assign score = empty>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>



		${MATRIX_TABLE_START}
			${MATRIX_TR_START}
				${MATRIX_TH_START}Screen${MATRIX_TH_END}
				${MATRIX_TH_START}Result${MATRIX_TH_END}
				${MATRIX_TH_START}Raw Score${MATRIX_TH_END}
				${MATRIX_TH_START}Cut-off Score${MATRIX_TH_END}
			${MATRIX_TR_END}
			${MATRIX_TR_START}
				<#list rows as row>
					${MATRIX_TR_START}
					<#list row as col>
						${MATRIX_TD_START}${col}${MATRIX_TD_END}
					</#list>
					${MATRIX_TR_END}
				</#list>
			${MATRIX_TR_END}
		${MATRIX_TABLE_END}
${MODULE_END}'
 WHERE template_id = 100;
 INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2001, 100);
 
 update template 
set template_file = '
<#include "clinicalnotefunctions">
 <#-- Template start --> ${MODULE_TITLE_START} MILITARY DEPLOYMENTS AND HISTORY: ${MODULE_TITLE_END} 
 ${MODULE_START} 
 
 <#assign allAnswersNone = false> 
 
  <#-- var5000 --> 
  <#assign Q1_complete = false> <#assign Q1_isAnswerNone = false>
  <#if (var5000.children)?? && ((var5000.children)?size > 0)>
  	<#assign Q1_complete = true>
  </#if>
  <#if (var5001.value)?? && var5001.value = "true"> 
  			<#assign Q1_isAnswerNone = true>
  </#if> 
  
  <#-- var5020 --> 
  <#assign Q2_complete = false> 
  <#assign Q2_isAnswerNone = false>
  <#if (var5020.children)?? && ((var5020.children)?size > 0)>
  	<#assign Q2_complete = true>
  </#if>
   <#if (var5021.value)??  && var5021.value = "true"> 
   			<#assign Q2_isAnswerNone = true> 
   </#if> 
   
   <#-- var5130 --> 
   <#assign Q3_complete = false> <#assign Q3_isAnswerNone = false> 
   
   <#if (var5131.value)?? && var5131.value = "true">
        <#assign Q3_complete = true> 
   		<#assign Q3_isAnswerNone = true>
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
    			<#if (loc?has_content)> ${innerNextLine}
    				<#assign all_rows = all_rows +	beg_month + "/" + beg_yr + "-"  + end_month + "/" + end_yr + ": "  + loc + LINE_BREAK> 
    				<#assign innerNextLine = "${LINE_BREAK}"> 
    				<#assign counter = counter + 1>  
    			<#else> 
    				<#break> 
    			</#if> 
    		</#list> 
    		<#if (counter > 0)> 
    			<#assign Q3_complete = true> 
    		</#if> 
    	</#if> 
    </#if>  
    
    <#if Q1_isAnswerNone && Q2_isAnswerNone && Q3_isAnswerNone> 
    	<#assign allAnswersNone = true> 
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
    
   		<#if Q3_complete>
   			<#if Q3_isAnswerNone>
   				${nextLine}The Veteran was not deployed in support of a combat operation.
   			<#elseif (counter > 0)>
   				<#if (counter == 1)> 
   					<#assign frag = counter + " time"> <#assign frag2 = "area"> <#assign frag3 = "period"> 
   				<#else> 
   					<#assign frag = counter + " times"> <#assign frag2 = "areas"> <#assign frag3 = "periods">     		
   				</#if> 		
   				${nextLine}The Veteran was deployed ${frag}
   				<#if (counter>0)> to the following ${frag2} during the following time ${frag3}:${LINE_BREAK}${LINE_BREAK} ${all_rows} 
   				<#else>.
   				</#if>
    		</#if>
    	</#if> 
    </#if>
    ${MODULE_END} 
    '
where template_id = 14;