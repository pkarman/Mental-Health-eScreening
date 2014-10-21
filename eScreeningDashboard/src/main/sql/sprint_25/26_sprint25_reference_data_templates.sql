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
			
	<#if var2047?? && (var2047.children)?? && ((var2047.children)?size > 0)>
		Veteran${NBSP}
		<#if var3441?? && var3441.value = "true">
		 declined
		<#elseif var3442?? && var3442.value="true">agreed to
		</#if>
		  ${NBSP}TBI consult for further evaluation. ${NBSP}
    </#if>
	
${MODULE_END}
'
where template_id = 30;

/* ********************************************** */
/* template   UPDATES */
/* ********************************************** */
/*  SCORING MATRIX  -- MST problem -- Ticket 481*/

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
		
		<#if (var2000?? && var2000.children?? && var2000.children?size>0) >
			<#if (var2000.children[0].key == "var762")>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif (var2000.children[0].key == "var761") >
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
${MODULE_END}'
 WHERE template_id = 100;
 
 -- /* ROAS AGGRESSION UPDATE */
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
AGGRESSION: 
${MODULE_TITLE_END}
${MODULE_START}

    	<#if (var3200.children)?? && (var3210.children)?? && (var3220.children)?? && (var3230.children)?? && (var3240.children)?? 
				&& (var3250.children)?? && (var3260.children)?? && (var3270.children)?? && (var3280.children)?? && (var3290.children)?? 
				&& (var3300.children)?? && (var3310.children)?? && (var3320.children)?? && (var3330.children)?? && (var3340.children)?? 
				&& (var3350.children)??
				&& ((var3200.children)?size > 0) && ((var3210.children)?size > 0) && ((var3220.children)?size > 0) && ((var3230.children)?size > 0) && ((var3240.children)?size > 0) 
				&& ((var3250.children)?size > 0) && ((var3260.children)?size > 0) && ((var3270.children)?size > 0) && ((var3280.children)?size > 0) && ((var3290.children)?size > 0) 
				&& ((var3300.children)?size > 0) && ((var3310.children)?size > 0) && ((var3320.children)?size > 0) && ((var3330.children)?size > 0) && ((var3340.children)?size > 0) 
				&& ((var3350.children)?size > 0)>
	
			<#if !(getScore(var3200) == 999 ||  getScore(var3210) == 999 ||  getScore(var3220) == 999  || getScore(var3230) == 999  ||  getScore(var3240) == 999  || getScore(var3250) == 999  ||  getScore(var3260) == 999  
				|| getScore(var3270) == 999  ||  getScore(var3280) == 999  || getScore(var3290) == 999)  ||  getScore(var3300) == 999 ||  getScore(var3310) == 999  || getScore(var3320) == 999  ||  getScore(var3330) == 999  
				|| getScore(var3340) == 999  ||  getScore(var3350) == 999  >
			
			  <#if (var3389)?? && (getFormulaDisplayText(var3389) != "notfound") && (getFormulaDisplayText(var3389)?number == 0)>       <#-- veteran answered no to all questions" -->
				${getAnsweredNeverAllText("Aggression")}.${NBSP}
			  <#else>
    			The Veteran had a score of ${getFormulaDisplayText(var3389)} on the Retrospective Overt Aggression Scale (range 0-240).${LINE_BREAK}${LINE_BREAK}
    			
    			<#assign fragments = []>
	
	
				<#assign verbalAggressionText = "">
				<#assign verbalAggressionSubText = []>
				<#if (getQuestionCalcValue(var3200) > 1) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["shouting angrily"]>
				</#if>
				<#if (getQuestionCalcValue(var3210) > 1) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["yelling mild insults"]>
				</#if>
				<#if (getQuestionCalcValue(var3220) > 1) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["making vague threats"]>
				</#if>
				<#if (getQuestionCalcValue(var3230) > 1) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["making clear threats of violence"]>
				</#if>
				<#if verbalAggressionSubText?has_content >      
					<#assign verbalAggressionText = "Verbal Aggression (" + createSentence(verbalAggressionSubText) + ")">
					<#assign fragments = fragments + [verbalAggressionText] >
				</#if>

				<#assign physicalAggressionObjectsText = "">
				<#assign physicalAggressionObjectsSubText = []>
				<#if (getQuestionCalcValue(var3240) > 1) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["slamming doors"]>
				</#if>
				<#if (getQuestionCalcValue(var3250) > 1) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["throwing objects"]>
				</#if>
				<#if (getQuestionCalcValue(var3260) > 1) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["breaking objects or smashing windows"]>
				</#if>
				<#if (getQuestionCalcValue(var3270) > 1) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["setting fires or throwing object dangerously"]>
				</#if>
				<#if physicalAggressionObjectsSubText?has_content >      
					<#assign physicalAggressionObjectsText = "Physical aggression toward objects (" + createSentence(physicalAggressionObjectsSubText) + ")">
					<#assign fragments = fragments + [physicalAggressionObjectsText] >
				</#if>



				<#assign physicalAggressionSelfText = "">
				<#assign physicalAggressionSelfSubText = []>
				<#if (getQuestionCalcValue(var3320) > 1) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["hitting self or pulling own hair"]>
				</#if>
				<#if (getQuestionCalcValue(var3330) > 1) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["banging head or fists against objects"]>
				</#if>
				<#if (getQuestionCalcValue(var3340) > 1) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["cutting or bruising self"]>
				</#if>
				<#if (getQuestionCalcValue(var3350) > 1) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["other serious self injury"]>
				</#if>
				<#if physicalAggressionSelfSubText?has_content >      
					<#assign physicalAggressionSelfText = "Physical aggression toward self (" + createSentence(physicalAggressionSelfSubText) + ")">
					<#assign fragments = fragments + [physicalAggressionSelfText] >
				</#if>

				<#assign physicalAggressionOthersText = "">
				<#assign physicalAggressionOthersSubText = []>
				<#if (getQuestionCalcValue(var3280) > 1) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["swinging at people or grabbing others"]>
				</#if>
				<#if (getQuestionCalcValue(var3290) > 1) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["striking or pushing on others"]>
				</#if>
				<#if (getQuestionCalcValue(var3300) > 1) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["bruising or causing welts on others"]>
				</#if>
				<#if (getQuestionCalcValue(var3310) > 1) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["attacking others causing severe physical injury"]>
				</#if>
				<#if physicalAggressionOthersSubText?has_content >      
					<#assign physicalAggressionOthersText = "Physical aggression towards others (" + createSentence(physicalAggressionOthersSubText) + ")">
					<#assign fragments = fragments + [physicalAggressionOthersText]  >
				</#if>
				The Veteran indicated the following concerning aggressive behaviors in the past month: ${createSentence(fragments)}.${NBSP}
			  </#if>
    		<#else>
    			${getNotCompletedText()}
    		</#if>
    	<#else>
    		${getNotCompletedText()}
    	</#if>
${MODULE_END}
'
where template_id = 38;

UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
ADVANCE DIRECTIVE:
${MODULE_TITLE_END}
${MODULE_START}
  <#-- var800: ${var800}<br><br>var820: ${var820}<br><br> var826: ${var826}<br><br> --> <#-- test objs -->
  <#assign nextLine = "">
  	<#if (var800.children)?? && (var800.children?size > 0) || ((var820.children)??&& (var820.children?size > 0))
  	 || ((var826.children)??  && (var826.children?size > 0))>
    <#if (var800.children)?? && (var800.children?size > 0) && hasValue(getSelectMultiDisplayText(var800)) >
    	<#-- In what language do you prefer to get your health information? -->
      ${nextLine}The Veteran requests to receive information in ${getSelectMultiDisplayText(var800)}.
      <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
    </#if>
    <#if (var820.children)?? && (var820.children?size > 0) && hasValue(getSelectMultiDisplayText(var820)) >
      <#-- Do you have and Advance Directive or Durable Power of Attorney for Healthcare?  -->
      ${nextLine}The Veteran reported ${getSelectMultiDisplayText(var820)} an Advance Directive or Durable Power of Attorney for Healthcare.
      <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
    <#else>
     The Veteran declined to answer some or all of the questions.
     </#if>
    	   
     <#if (var826.children)?? && (var826.children?size > 0) && hasValue(getSelectMultiDisplayText(var826)) >
      	<#-- If no, would like information about this document? -->
      	${nextLine}The Veteran ${getSelectMultiDisplayText(var826)} like information about an Advance Directive.
    </#if>     
    
    <#else>
    	${getNotCompletedText()}
    </#if>    
${MODULE_END}'
where template_id = 10;

UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
ALCOHOL:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var1200.children)?? && (var1210.children)?? && (var1220.children)??
			&& ((var1200.children)?size > 0) && ((var1210.children)?size > 0) && ((var1220.children)?size > 0)>
	
	<#assign score = getListScore([var1200,var1210,var1220])>
	<#assign status ="">  <#-- positive or negative -->
	<#assign gender = "">
	
		<#if !((var1200.children)?has_content) || !((var1210.children)?has_content) || !((var1220.children)?has_content) >
	 		${getNotCompletedText()}
		<#else>
			<#if (score >= 0) && (score <= 2)>
				<#assign status = "negative">
			<#elseif (score >= 4) && (score <= 998)>
				<#assign status = "positive">
			<#elseif (score == 3 )>
				<#assign status = "positive for women/negative for men">
			</#if>
			The Veteran\'s AUDIT-C screen was ${status} with a score of ${score}. ${NBSP}
    	</#if>
    	
    <#else>
    	The Veteran\'s AUDIT-C screen was not calculated due to the Veteran declining to answer some or all of the questions.
    </#if>
${MODULE_END}
'
where template_id = 27;
