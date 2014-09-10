/* ********************************************** */
/* template   UPDATES */
/* ********************************************** */
/*  SCORING MATRIX  -- MST problem -- Ticket 481*/

UPDATE template SET template_type_id = 4, 
	name = 'OOO Battery CPRS Note Scoring Matrix', 
	description = 'OOO Battery CPRS Note Scoring Matrix', 
	template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SCORING MATRIX: 
${MODULE_TITLE_END}
${MODULE_START}
	

<#assign matrix_section>
	
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
			
			<#if isSelectedAnswer(var2047,var3442)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var2047,var3441)> 
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
		
		<#if var2008?? >
			<#if isSelectedAnswer(var2008, var792)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var2008, var791) >
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Negative">
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


</#assign>
	
	<#if !(matrix_section = "") >
     	${matrix_section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}'
 WHERE template_id = 100;

INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2003, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2004, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2005, 100);

/*** #589 Skip logic update for PROMIS EMOTIONAL SUPPORT ***/
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start --> 
<#if var739??>
${MODULE_TITLE_START} PROMIS EMOTIONAL SUPPORT: ${MODULE_TITLE_END} 
${MODULE_START}
<#if var739??> The Veteran had a score of ${getCustomVariableDisplayText(var739)?number}, indicating that they currently have ${NBSP} <#if (var739.value?number>=0) && (var739.value?number <= 11)>	lower than average  
<#elseif (var739.value?number <= 19)> average  	<#else> above average  </#if>${NBSP} emotional support.${NBSP}
 <#else>${getNotCompletedText()} 
</#if> 
 ${MODULE_END}
</#if>
'
where template_id = 7;


-- /* EDUCATION, EMPLOYMENT AND INCOME    UPDATE with skip logic #589*/
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if var50?? || var60?? ||var70?? || var80?? ||var100??>
${MODULE_TITLE_START}
EDUCATION, EMPLOYMENT AND INCOME: 
${MODULE_TITLE_END}
${MODULE_START}
    <#-- The Veteran reported completing some high school. -->
    <#if var50?? && hasValue(getSelectOneDisplayText(var50)) >
      The Veteran reported completing ${getSelectOneDisplayText(var50)}. ${NBSP}
    </#if> 
    <#-- The Veteran reported being currently an employed, who usually works as retail. -->
    <#if var60?? && var70??>
    <#if hasValue(getSelectOneDisplayText(var60)) && hasValue(getSelectOneDisplayText(var70)) >
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}, who usually works as a ${getFreeformDisplayText(var70)}.  ${NBSP}
    <#elseif hasValue(getSelectOneDisplayText(var60)) && !(hasValue(getFreeformDisplayText(var70)))>
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}.  ${NBSP}
    <#elseif !(hasValue(getSelectOneDisplayText(var60))) && hasValue(getFreeformDisplayText(var70)) >
      The Veteran reported usually working as a ${getFreeformDisplayText(var70)}.  ${NBSP}
    </#if>
    </#if>
    <#--The Veteran reported that the primary source of income is work, and disability.  -->
    <#if var80?? >
      The Veteran reported that${NBSP}  <#if wasAnswerNone(var80)>
        they do not have any income. ${NBSP}
      <#else>
        the primary source of income is ${getSelectMultiDisplayText(var80)}. ${NBSP}
      </#if> 
    </#if> 
    <#--The Veteran is married.-->
    <#if var100?? && hasValue(getSelectOneDisplayText(var100)) >
      The Veteran is ${getSelectOneDisplayText(var100)}. ${NBSP}
    </#if> 
${MODULE_END}
</#if>
'
where template_id = 5;

/**** Deployment history #ticket 586 ************/
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
    	<#else> The Veteran reported receiving the following disciplinary actions: ${getSelectMultiDisplayText(var5000)}. 
  			<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}"> 
   		</#if>  
    
   		<#if Q2_isAnswerNone> ${nextLine}The Veteran reported receiving no personal awards or commendations. <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">     		<#else> ${nextLine}The Veteran reported receiving the following personal awards or commendations: ${getSelectMultiDisplayText(var5020)}. 
    			<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}"> 
    	</#if>   
    
   		<#if (counter <= 1)> 
   			<#assign frag = counter + " time"> <#assign frag2 = "area"> <#assign frag3 = "period"> 
   		<#else> 
   			<#assign frag = counter + " times"> <#assign frag2 = "areas"> <#assign frag3 = "periods">     		
   		</#if> ${nextLine}The Veteran was deployed ${frag} to the following ${frag2} during the following time ${frag3}:${LINE_BREAK}${LINE_BREAK} ${all_rows} 
    </#if>  
    </#assign> <#if !(deployments_section = "") > ${deployments_section} <#else> ${noParagraphData} </#if> 
    ${MODULE_END} 
    '
where template_id = 14;
