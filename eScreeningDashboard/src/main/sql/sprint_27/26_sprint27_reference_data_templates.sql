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
	<#if (var10714?? && var10714.value=="0" && var2016.value=="false") || (var10715?? && var10715.value=="0" && var2022.value=="false")
		||(var10716?? && var10716.value=="0" && var2030.value=="false") || (var10717?? && var10717.value=="0" && var2037.value=="false") >
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

INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2016, 30);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2022, 30);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2030, 30);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2037, 30);

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
		
		<#if (var2001?? && var2001.children?? && var2001.children?size>0) >
			<#if (var2001.children[0].key == "var772")>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif (var2001.children[0].key == "var771") >
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
<#if (var3389)?? && (getFormulaDisplayText(var3389) != "notfound")>
${MODULE_TITLE_START}
AGGRESSION: 
${MODULE_TITLE_END}
${MODULE_START}

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
			 <#if fragments?has_content>
				The Veteran indicated the following concerning aggressive behaviors in the past month: ${createSentence(fragments)}.${NBSP}
			  </#if>
${MODULE_END}
</#if>
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

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Health Status:
${MODULE_TITLE_END}
${MODULE_START}
    <#assign totalScore = -1>
    
	<#if var1189?? && var1189.value??>
		<#assign totalScore = var1189.value?number>
	<#elseif var10800?? && var10800.value??>
		<#assign totalScore = var10800.value?number>
	</#if>

	<#if totalScore != -1>
		
		<#assign scoreText = "---">
		<#if (totalScore <= 4)>
			<#assign scoreText = "minimal">
		<#elseif (totalScore >= 5 &&  (totalScore <= 9))>
			<#assign scoreText = "low number of">
		<#elseif (totalScore >= 10 &&  (totalScore <= 14))>
			<#assign scoreText = "medium number of">
		<#elseif (totalScore >= 15 &&  (totalScore <= 30))>
			<#assign scoreText = "high number of">
		</#if>
	
		The Veteran reported a ${scoreText} somatic symptoms. ${LINE_BREAK}${LINE_BREAK}
	
	</#if>


	<#-- During the past 4 weeks, how much have you been bothered by any of the following problems -->
	<#-- this is almost identical to Other Health Symptoms -->
	<#assign fragments = []> 
    <#if var1150?? && (getScore(var1150) > 1)>
		<#assign fragments = fragments + ["stomach pain"] >
	</#if>
    <#if var1160?? && (getScore(var1160) > 1)>
		<#assign fragments = fragments + ["back pain"] >
	</#if>
	<#if var1020?? && (getScore(var1020) > 1)>
		<#assign fragments = fragments + ["pain in arms, legs or joints (knees, hips, etc.)"] >
	</#if>
	<#if var1030?? && (getScore(var1030) > 1)>
		<#assign fragments = fragments + ["menstrual cramps or other problems with your periods"] >
	</#if>
	<#if var1040?? && (getScore(var1040) > 1)>
		<#assign fragments = fragments + ["headaches"] >
	</#if>
	<#if var1050?? && (getScore(var1050) > 1)>
		<#assign fragments = fragments + ["chest pain"] >
	</#if>
	<#if var1060?? && (getScore(var1060) > 1)>
		<#assign fragments = fragments + ["dizziness"] >
	</#if>
	<#if var1070?? && (getScore(var1070) > 1)>
		<#assign fragments = fragments + ["fainting spells"] >
	</#if>
	<#if var1080?? && (getScore(var1080) > 1)>
		<#assign fragments = fragments + ["feeling your heart pound or race"] >
	</#if>
	<#if var1090?? && (getScore(var1090) > 1)>
		<#assign fragments = fragments + ["shortness of breath"] >
	</#if>
	<#if var1100?? && (getScore(var1100) > 1)>
		<#assign fragments = fragments + ["pain or problems during sexual intercourse"] >
	</#if>
	<#if var1110?? && (getScore(var1110) > 1)>
		<#assign fragments = fragments + ["constipation, loose bowels or diarrhea"] >
	</#if>
	<#if var1120?? && (getScore(var1120) > 1)>
		<#assign fragments = fragments + ["nausea, gas or indigestion"] >
	</#if>
	<#if var1130?? && (getScore(var1130) > 1)>
		<#assign fragments = fragments + ["feeling tired or having low energy"] >
	</#if>
	<#if var1140?? && (getScore(var1140) > 1)>
		<#assign fragments = fragments + ["trouble sleeping"] >
	</#if>

	<#if fragments?has_content  >
		<#assign resolved_fragments =  createSentence(fragments)>
		The Veteran endorsed being bothered a lot by the following health symptoms over the past four weeks: ${resolved_fragments}.${NBSP}
	</#if>

${MODULE_END}'
where template_id = 17;


update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PAIN:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var2300.children)?? && (var2300.children?size>0) || (var2330.children)?? || (var2334?? && ((var2334.value) == "true"))>  
			<#--  --> 
			<#assign fragments = []>
			<#assign text ="notset">  
	 <#if (var2300.children)?? && (var2300.children?size>0)>
			<#assign level = getSelectOneDisplayText(var2300)!("-1"?number)>
			Over the past month, the Veteran\'s reported pain level was ${level} of 10.
	 </#if>
	 <#if var2330.children?? && (var2330.children?size>0) && var2334??>
			<#assign bodyParts = getTableMeasureValueDisplayText(var2330)!"">
			<#if !(bodyParts?has_content) && ((var2334.value) == "true")>
				<#assign bodyParts = "none">
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


/*tobacco decline template */
update template set template_file = '<#include "clinicalnotefunctions"> 
<#-- Template start --> 
${MODULE_TITLE_START} 
	Tobacco Use 
${MODULE_TITLE_END} 

${MODULE_START}  
	The use of tobacco causes harm to nearly every organ in the body. Quitting greatly lowers your risk of death from cancers, heart disease, stroke, and emphysema. There are many options, such as in-person and telephone counseling, nicotine replacement, and prescription medications. 

	${LINE_BREAK} 
	${LINE_BREAK} 

	<b>Results:</b>${NBSP} 
	
	<#if (var600.children)?? && ((var600.children)?size gt 0)> 
		<#if isSelectedAnswer(var600,var601) || isSelectedAnswer(var600,var602)> 
			negative screen 
		<#elseif isSelectedAnswer(var600,var603)> 
			current user 
			${LINE_BREAK} 
			<b>Recommendations:</b> Prepare a plan to reduce or quit the use of tobacco. Get support from family and friends, and ask your clinician for help if needed. 
		</#if> 
	<#else>
		${NBSP}Veteran declined to respond
	</#if> 	
${MODULE_END}' 
where template_id=306;

 
/*
t692
There is an issue with housing. The rule was: If HomelessCR_stable_house =1 then insert “stable housing”, if =0 “unstable housing/at risk”, if equal to 999 omit module. 
However, this won’t work because the Veteran could have stable housing now and be worried about the future, which would mean they need assistance. 
The changed rule is: 
====>>> if HomelessCR_stable_worry =1 then insert “unstable housing/at risk”, if =0 “no housing concerns”, if equal to 999 insert “Veteran declined to respond”.
====>>> also if user declined to respond then show Veteran declined to respond
*/
update variable_template set assessment_variable_id=771 where variable_template_id=10511;
update variable_template set assessment_variable_id=772 where variable_template_id=10512;
update variable_template set assessment_variable_id=2001 where variable_template_id=10510;
delete from variable_template where variable_template_id=10513;

update template set template_file = '<#include "clinicalnotefunctions"> 
<#-- Template start --> 
${MODULE_TITLE_START} Homelessness ${MODULE_TITLE_END} 
${MODULE_START} This is when you do not have a safe or stable place you can return to every night. The VA is committed to ending Veteran homelessness by the end of 2015. ${LINE_BREAK} 
${LINE_BREAK} 
<b>Results:</b>${NBSP} 
<#if (var2001)?? && (var2001.children)?? && ((var2001.children)?size > 0)> 
	<#if isSelectedAnswer(var2001,var772)> 
		unstable housing/at risk 
		${LINE_BREAK} 
		<b>Recommendation:</b> Call the VA\'s free National Call Center for Homeless Veterans at (877)-424-3838 and ask for help. Someone is always there to take your call. 
	<#elseif isSelectedAnswer(var2001,var771)> 
		stable housing 
	</#if>  
<#else>
	${NBSP}Veteran declined to respond
</#if>
${MODULE_END}' 
where template_id=301;

 
/* depression declined template */
update template set template_file = '<#include "clinicalnotefunctions"> 
<#assign score = getCustomVariableDisplayText(var1599)> 
<#-- Template start --> 
${MODULE_TITLE_START} Depression ${MODULE_TITLE_END} 
<#if score != "notfound">
	${GRAPH_SECTION_START} 
	${GRAPH_BODY_START} 
		{"type": "stacked", "title": "My Depression Score", "footer": "*a score of 10 or greater is a positive screen", "data": { "graphStart": 0,"ticks": [0, 4, 10, 15, 20, 27],"intervals": {"Minimal":4, "Mild":10, "Moderate":15, "Moderately Severe":20, "Severe":27 }, "score": ${score} } } 
	${GRAPH_BODY_END} 
	${GRAPH_SECTION_END}  
</#if>
${MODULE_START} 
	Depression is when you feel sad and hopeless for much of the time. It affects your body and thoughts, and interferes with daily life. There are effective treatments and resources for dealing with depression.
	${LINE_BREAK} 
	<b>Recommendation:</b> 
	<#if score = "notfound">
		${NBSP}Veteran declined to respond
	<#elseif score?number lt 10>
		Contact a clinician if in the future if you ever feel you may have a problem with depression
	<#elseif score?number gt 9>
		Ask your clinician for further evaluation and treatment options
	</#if> 
${MODULE_END}' 
where template_id=308;

/* PTSD declined template */
update template set template_file = '<#include "clinicalnotefunctions"> 
<#assign score = getCustomVariableDisplayText(var1929)>  
<#-- Template start --> 
${MODULE_TITLE_START} Post-traumatic Stress Disorder ${MODULE_TITLE_END}  

<#if score != "notfound">
	${GRAPH_SECTION_START}  
	${GRAPH_BODY_START} 
	{ "type": "stacked", "title": "My PTSD Score", "footer": "", "data": { "graphStart": 17, "ticks": [17,35,50,65,85], "intervals": { "Negative": 49, "Positive":85 }, "score": ${score} } } 
	${GRAPH_BODY_END} 
	${GRAPH_SECTION_END}  
</#if>

${MODULE_START} 
	PTSD is when remembering a traumatic event keeps you from living a normal life. It\'s also called shell shock or combat stress. Common symptoms include recurring memories or nightmares of the event, sleeplessness, and feeling angry, irresistible, or numb. 
	${LINE_BREAK} 
	<b>Recommendation:</b> 
	<#if score = "notfound">
		${NBSP}Veteran declined to respond
	<#elseif score?number gt 49>
		Ask your clinician for further evaluation and treatment options
	<#elseif score?number lt 50>
		You may ask a clinician for help in the future if you feel you may have symptoms of PTSD
	</#if> 
${MODULE_END}' 
where template_id=310;

/* TBI changes: If tbi_consult=1 then insert “You have requested further assessment” if TBI_consult =0 insert “you have declined further assessment” if =999 then omit recommendation.  */
update template set template_file = '<#include "clinicalnotefunctions"> 
<#function calcScore obj> 
	<#assign result = 0> 
	<#if (obj.children)?? && ((obj.children)?size > 0)> 
		<#list obj.children as c> 
			<#if c.overrideText != "none"> 
				<#assign result = result + 1> 
				<#break> 
		</#if> 
	</#list> 
	</#if> 
	<#return result> 
</#function>  
<#assign score = 0>  
<#assign isQ2Complete = false> 
<#assign isQ2None = false> 
<#if (var3400.children)?? && ((var3400.children)?size > 0)> 
	<#assign isQ2Complete = true> 
	<#if isSelectedAnswer(var3400, var2016)!false> 
		<#assign isQ2None = true> 
	<#else> 
		<#assign score = score + calcScore(var3400)> 
	</#if> 
</#if>  
<#assign isQ3Complete = false> 
<#assign isQ3None = false> 
<#if isQ2Complete> 
	<#if isQ2None> 
		<#assign isQ3Complete = true> 
	<#else> 
		<#if (var3410.children)?? && ((var3410.children)?size > 0) > 
			<#if isSelectedAnswer(var3410, var2022)!false> 
				<#assign isQ3None = true> 
			<#else> 
				<#assign score = score + calcScore(var3410)> 
			</#if> 
			<#assign isQ3Complete = true> 
		</#if> 
	</#if> 
</#if>  
<#assign isQ4Complete = false> 
<#assign isQ4None = false> 
<#if isQ3Complete> 
	<#if isQ2None || isQ3None> 
		<#assign isQ4Complete = true> 
	<#else> 
		<#if (var3420.children)?? && ((var3420.children)?size > 0) > 
			<#if isSelectedAnswer(var3420, var2030)!false> 
				<#assign isQ4None = true> 
			<#else> 
				<#assign score = score + calcScore(var3420)> 
			</#if> 
			<#assign isQ4Complete = true> 
		</#if> 
	</#if> 
</#if>  
<#assign isQ5Complete = false> 
<#assign isQ5None = false> 
<#if isQ4Complete> 
	<#if isQ2None || isQ3None || isQ4None> 
		<#assign isQ5Complete = true> 
	<#else> 
		<#if (var3430.children)?? && ((var3430.children)?size > 0) > 
			<#if isSelectedAnswer(var3430, var2037)!false> 
				<#assign isQ5None = true> 
			<#else> 
				<#assign score = score + calcScore(var3430)> 
			</#if> 
			<#assign isQ5Complete = true> 
		</#if> 
	</#if> 
</#if>  
<#assign isComplete = false> 
<#if isQ2Complete && isQ3Complete && isQ4Complete && isQ5Complete> 
	<#assign isComplete = true> 
</#if>  
${MODULE_TITLE_START} 
	Traumatic Brain Injury (TBI) 
${MODULE_TITLE_END} 

${MODULE_START}  
	<#assign showRec = false> <#assign tbi_consult_text = ""> 
	<#if isComplete && (var2047.children)?? && ((var2047.children)?size > 0) && isSelectedAnswer(var2047,var3442)> 
		<#assign showRec = true> 
		<#assign tbi_consult_text = "You have requested further assessment"> 
	<#elseif isComplete && (var2047.children)?? && ((var2047.children)?size > 0) && isSelectedAnswer(var2047,var3441)> 
		<#assign showRec = true> 
		<#assign tbi_consult_text = "You have declined further assessment">
	</#if> 
	A TBI is physical damage to your brain, caused by a blow to the head. Common causes are falls, fights, sports, and car accidents. A blast or shot can also cause TBI. ${LINE_BREAK} 
	${LINE_BREAK} 
	<#if isComplete> 
		<b>Results:</b>${NBSP} 
		<#if (score >= 0) && (score <= 3)> 
			negative screen 
		<#elseif (score >= 4 )> 
			positive screen
		</#if> 
	</#if>  
	<#if isComplete && showRec> 
		${LINE_BREAK} 
		<b>Recommendation:</b> 
			${tbi_consult_text}. 
	</#if> 
${MODULE_END} ' 
where template_id=307;


/****************** TICKET 680 CHANGES BELOW *********************************/
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
			<#if combat != "notfound">
				${nextLine}The Veteran reported the following types of combat experience: ${combat}.
			</#if>
		</#if>

	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}
'
where template_id = 15;

/*** ticket 680 comment #9 *********/
update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SLEEP:
${MODULE_TITLE_END}
${MODULE_START}
	<#if var2189?? && var2189.value??>
	<#assign score = var2189.value?number>
	<#if score??> 	
		<#if (score >= 0 && score <= 7) >
			<#assign scoreText = "negative">
			<#assign text = "no clinically significant insomnia">
		<#elseif (score >= 8) && (score <= 14)>
			<#assign scoreText = "negative">
			<#assign text = "subthreshold insomnia">
		<#elseif (score >= 15) && (score <= 21)>
			<#assign scoreText = "positive">
			<#assign text = "moderate insomnia">
		<#elseif (score >= 22) && (score <= 998)>  <#-- if score = 999 then veteran did not answer the question -->
			<#assign scoreText = "positive">
			<#assign text = "severe insomnia">
		</#if>
	</#if>
	
	<#if score??>
		<#if (score >=0) && (score <= 998)>
			<#t>The Veteran\'s ISI was ${scoreText} indicating ${text} for the past week.${NBSP}
		<#elseif score >= 999>
			<#-- this is score 999 -->
			<#t>The Veteran did not complete the ISI screen.${NBSP}>
		</#if>
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}
'
where template_id = 37;

delete from variable_template where template_id = 37;
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2189, 37);


/********** t680 MDQ ****************/
update template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if var10720?? && var10720.value??>
${MODULE_TITLE_START}
Mood Disorder:
${MODULE_TITLE_END}
${MODULE_START}
			<#assign t = "negative">
			<#assign text2 = "">
			<#assign fragments = []>
		
			<#if var10720.value?number == 1>
					<#assign t = "positive indicating that the Veteran may benefit from further assessment for possible symptoms of mania or other mood disorders">
				
					<#if (getScore(var2660) > 0)>
						<#assign fragments = fragments + ["so hyper got into trouble"]>
					</#if>
					<#if (getScore(var2670) > 0)>
						<#assign fragments = fragments + ["so irritable started fights"]>
					</#if>
					<#if (getScore(var2680) > 0)>
						<#assign fragments = fragments + ["felt much more self-confident than usual"]>
					</#if>
					<#if (getScore(var2690) > 0)>
						<#assign fragments = fragments + ["got less sleep"]>
					</#if>
					<#if (getScore(var2700) > 0)>
						<#assign fragments = fragments + ["was much more talkative/spoke much faster"]>
					</#if>
					<#if (getScore(var2710) > 0)>
						<#assign fragments = fragments + ["racing thoughts"]>
					</#if>
					<#if (getScore(var2720) > 0)>
						<#assign fragments = fragments + ["could not concentrate"]>
					</#if>
					<#if (getScore(var2730) > 0)>
						<#assign fragments = fragments + ["more energy"]>
					</#if>
					<#if (getScore(var2740) > 0)>
						<#assign fragments = fragments + ["more active/did more things"]>
					</#if>
					<#if (getScore(var2750) > 0)>
						<#assign fragments = fragments + ["more social/outgoing"]>
					</#if>
					<#if (getScore(var2760) > 0)>
						<#assign fragments = fragments + ["much more interested in sex"]>
					</#if>
					<#if (getScore(var2770) > 0)>
						<#assign fragments = fragments + ["was excessive/foolish/risky"]>
					</#if>
					<#if (getScore(var2780) > 0)>
						<#assign fragments = fragments + ["got in trouble spending money"]>
					</#if>
				
					<#assign text2 = createSentence(fragments)>
				</#if>
				
				The MDQ (Hyper mood) screen was ${t}.
			
				<#if text2?has_content>
					${LINE_BREAK}${LINE_BREAK}Symptoms endorsed: ${text2}.
				</#if>
${MODULE_END}
</#if> '
where template_id = 32;

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
MST:
${MODULE_TITLE_END}
${MODULE_START}
	  
	<#if var2003?? && var2003.value??>
	<#assign mstMilitaryScore = var2003.value?number>
		
		<#if (mstMilitaryScore == 0) >
			The Veteran\s MST screen was negative.
		<#elseif (mstMilitaryScore == 1) >	
			The Veteran\s MST screen was positive.
			<#if var1640?? && var1640.value??>
				<#if var1640.value?number == 0>
				The Veteran declined a referral to discuss the sexual trauma further.${NBSP}
				<#elseif var1640.value?number == 1>
				The Veteran requests a referral to discuss the sexual trauma further.${NBSP}
				</#if>
			</#if>
		<#elseif (mstMilitaryScore == 2)>
			The Veteran declined to answer the MST screen.
		</#if>
	<#else>
	The Veteran declined to answer some or all of the questions of the MST screen.
	</#if>
${MODULE_END}
'
where template_id = 33;

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
							"var2502": "persistent papular or nodular skin rash that began after deployment to Southwest Asia", 
							"var2009": "suspects that he/she has retained fragments or shrapnel as a result of injuries"}>
	
	<#assign parts = []>
		
	<#list questions as q >
		<#if q.children?? && ((q.children)?size>0)>
			<#assign child = q.children[0] >
			<#if ((child.calculationValue?number) > 0)>
				<#assign problem = textHash[q.key] >
				<#assign part = problem + ", with symptoms of " + getVariableDisplayText(child) >
				<#assign parts = parts + [part] >
			</#if>
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
 <#if (var300.children)?? && ((var300.children)?size > 0) >
${MODULE_TITLE_START}
Pragmatic Concerns: 
${MODULE_TITLE_END}
${MODULE_START}

    <#if hasValue(getSelectMultiDisplayText(var300)) >
      The Veteran reported the following legal concern(s): ${getSelectMultiDisplayText(var300)}.  ${NBSP}
    </#if>
    <#if hasValue(getSelectOneDisplayText(var320)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var320), "yes") >
      	The Veteran reported having an Advance Directive.  ${NBSP}
      </#if>
    </#if>
    <#if hasValue(getSelectOneDisplayText(var330)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var330), "yes") >
      	The Veteran would like information about an Advance Directive.  ${NBSP}
      </#if>
    </#if>  
${MODULE_END}
</#if>
' where template_id = 9;

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
			<#assign scoreText = "positive">
			<#assign text = "minimal depression">
		<#elseif (score >= 5) && (score <= 9)>
			<#assign scoreText = "positive">
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
PCLC:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var1750.children)?? && (var1760.children)?? && (var1770.children)?? && (var1780.children)?? 
			&& (var1790.children)?? && (var1800.children)?? && (var1810.children)?? && (var1820.children)?? 
			&& (var1830.children)?? && (var1840.children)?? && (var1850.children)?? && (var1860.children)?? 
			&& (var1870.children)?? && (var1880.children)?? && (var1890.children)?? && (var1900.children)?? 
			&& (var1910.children)?? 
			&& ((var1750.children)?size > 0) && ((var1760.children)?size > 0) && ((var1770.children)?size > 0) && ((var1780.children)?size > 0) 
			&& ((var1790.children)?size > 0) && ((var1800.children)?size > 0) && ((var1810.children)?size > 0) && ((var1820.children)?size > 0) 
			&& ((var1830.children)?size > 0) && ((var1840.children)?size > 0) && ((var1850.children)?size > 0) && ((var1860.children)?size > 0) 
			&& ((var1870.children)?size > 0) && ((var1880.children)?size > 0) && ((var1890.children)?size > 0) && ((var1900.children)?size > 0) 
			&& ((var1910.children)?size > 0)>
	 
	<#assign fragments = []>
	<#assign resolved_fragments ="">
	<#assign text ="notset"> 
	<#assign score = (getListScore([var1750,var1760,var1770,var1780,var1790,var1800,var1810,var1820,var1830,var1840,var1850,var1860,var1870,var1880,var1890,var1900,var1910]))!("-1"?number)>
	<#assign scoreText ="notset">
	
	<#if score??> 	
		<#if (score >= 0) && (score <= 49)>
			<#assign scoreText = "negative">
		<#elseif (score >= 50) && (score <= 998)><#-- value 999 means they didn\'t answer the question so to include would give a false positive-->
			<#assign scoreText = "positive">
		</#if>
		
		<#if (score >=0  && score <= 998)>
			<#t>The Veteran\'s PCL-C Screen was ${scoreText!} with a PCL-C score of ${score!("-1"?number)}. ${NBSP}
		</#if>
	</#if>
	
	<#else>
		<#t>The Veteran\'s PCL-C Screen could not be calculated because the Veteran declined to answer some or all of the questions.
	</#if>
${MODULE_END}
' where template_id = 35;

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PC-PTSD:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var1940.children)?? && (var1950.children)?? && (var1960.children)?? && (var1970.children)??
			&& ((var1940.children)?size > 0) && ((var1950.children)?size > 0) && ((var1960.children)?size > 0) && ((var1970.children)?size > 0)>

	<#assign score = (getListScore([var1940,var1950,var1960,var1970]))!("-1"?number)>
	<#assign scoreText ="notset">
	 
	<#if score??> 	
		<#if (score >= 0) && (score <= 2)>
			<#assign scoreText = "negative">
		<#elseif (score >= 3) && (score <= 998)><#-- value 999 means they didn\'t answer the question so to include would give a false positive-->
			<#assign scoreText = "positive">
		</#if>
		
		<#if (score >=0  && score <= 998)>
			<#t>The Veteran\'s PC-PTSD Screen was ${scoreText!} with a PC-PTSD score of ${score}. ${NBSP}
		</#if>
	</#if>
	<#else>
		<#t>The Veteran\'s PC-PTSD Screen could not be calculated because the Veteran declined to answer some or all of the questions.
	</#if>

${MODULE_END}'
 where template_id = 36;
 
 update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
WHODAS 2.0: The Veteran was given the WHODAS 2.0, which covers six domains of assessing health status and disability on a scale of one to five. ${LINE_BREAK}${LINE_BREAK}
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

		  <#t><b>Understanding and Communicating</b> - the Veteran
		  <#if var4119?? && (var4119.value)??>
			${NBSP}had an average score of ${var4119.value} which indicates ${getScoreText(var4119.value)} disability. ${LINE_BREAK}${LINE_BREAK}
		  <#else>
		  	\'s score could not be calculated.
		  </#if>
		  ${LINE_BREAK}${LINE_BREAK}
		  
			<b>Mobility</b> - the Veteran${NBSP} 
			<#if var4239?? && (var4239.value)??>
			 ${NBSP}had an average score of ${var4239.value}, which indicates ${getScoreText(var4239.value)} disability.
			<#else>\'s score could not be calculated.
			</#if>
			
			${LINE_BREAK}${LINE_BREAK}
			<b>Self-Care</b> - the Veteran ${NBSP} 
			<#if var4319?? && var4319.value??>
				${NBSP}had an average score of ${var4319.value} which indicates ${getScoreText(var4319.value)} disability.
			<#else>
				\'s score could not be calculated.
			</#if>
			${LINE_BREAK}${LINE_BREAK}		

			<b>Getting Along</b> - the Veteran 
			<#if var4419?? && var4419.value??>
				${NBSP} had an average score of ${var4419.value} which indicates ${getScoreText(var4419.value)} disability.
			<#else>\'s score could not be calculated.
			</#if>
			 ${LINE_BREAK}${LINE_BREAK}

			<b>Life Activities (Household/Domestic)</b> - the Veteran 
			<#if var4499?? && var4499.value??>
			 ${NBSP}had an average score of ${var4499.value} which is a rating of ${getScoreText(var4499.value)} disability. 
			<#else>\'s score could not be calculated.
			</#if>
			 ${LINE_BREAK}${LINE_BREAK} 
			
			<#if var4200?? && ((var4200.children)?? && ((var4200.children)?size > 0))>
				<#if isSelectedAnswer(var4200, var4202)>
					<#if var4559?? && var4559.value??>
					  <b>Life Activities (School /Work)</b> - the Veteran had an average score of ${var4559.value} which is a rating of ${getScoreText(var4559.value)} disability. ${LINE_BREAK}${LINE_BREAK}
					<#else>
					  <b>Life Activities (School /Work)</b> - the Veteran\'s score could not be calculated.${LINE_BREAK}${LINE_BREAK}
					</#if>      
				<#elseif isSelectedAnswer(var4200, var4201)>
					<b>Life Activities (School /Work)</b> - the Veteran did not get a score because the veteran does not work or go to school. ${LINE_BREAK}${LINE_BREAK}   
				</#if>
			<#else>
				<b>Life Activities (School /Work)</b> - the Veteran\'s score could not be calculated.${LINE_BREAK}${LINE_BREAK}
			</#if>
			
			<b>Participation in Society</b> - the Veteran 
			
			<#if var4789?? && var4789.value??>
			${NBSP}had an average score of ${var4789.value} which indicates ${getScoreText(var4789.value)} disability. ${NBSP} 
			<#else>\'s score could not be calculated.
			</#if>
${MODULE_END}'
where template_id = 24;

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Service Injuries:
${MODULE_TITLE_END}
${MODULE_START}
		
		<#assign answerTextHash = {"var1380":"blast injury", "var1390":"injury to spine or back", "var1400":"burn (second, 3rd degree)", 
									"var1410":"nerve damage", "var1420":"Loss or damage to vision", "var1430":"loss or damage to hearing", 
									"var1440":"amputation", "var1450":"broken/fractured bone(s)", "var1460":"joint or muscle damage", 
									"var1470":"internal or abdominal injuries", "var1480":"other", "var1490":"other"}>
		
		<#-- must have answer questions -->
		<#assign questions1 = [var1380, var1390, var1400, var1410, var1420, var1430, var1440, var1450, var1460, var1470]>

		<#-- organize answers in a way to facilitate output -->
		<#assign noneAnswers = []>
		<#assign combatAnswers = []>
		<#assign nonCombatAnswers = []>
		<#assign otherAnswers = []>
		<#list questions1 as q>
			<#if q?? && q.children?? && (q.children?size > 0) && (q.children[0])?? >
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
			</#if>
		</#list>

					<#if var1480?? && var1481?? && (var1480.children?size > 0)>
						<#assign otherAnswer = var1481.otherText!"">
						<#if otherAnswer != "">
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
				
					<#if var1490?? && var1491?? && (var1490.children?size > 0)>
						<#assign otherAnswer = var1491.otherText!"">
						<#if otherAnswer != "">
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
		
		
		<#-- build first two sentences -->
		<#if combatAnswers?has_content>
			The following injuries were reported during combat deployment: ${createSentence(combatAnswers)}.${LINE_BREAK}
		</#if>
		
		<#if nonCombatAnswers?has_content>
			The following injuries were reported during other service period or training: ${createSentence(nonCombatAnswers)}.${LINE_BREAK}
		</#if>
		
		<#if var1510?? && (var1510.children)?? && (var1510.children?size>0)>
			<#assign frag = "">
			<#if isSelectedAnswer(var1510,var1512)>
				<#assign frag = "has">
			<#elseif isSelectedAnswer(var1510,var1511)>
				<#assign frag = "does not have">
			<#elseif isSelectedAnswer(var1510,var1513)>
				<#assign frag = "has the intent to file for, or has filed for">
			</#if>
			The Veteran ${frag} a service connected disability rating.
		</#if>
${MODULE_END}'
where template_id = 16;

update template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start --> 
<#if (var3050.children)?? && (var3050.children?size > 0)>
${MODULE_TITLE_START} 
Caffeine: 
${MODULE_TITLE_END} 
${MODULE_START} 

	<#if (getScore(var3050) == 0)> 
		<#t>The Veteran reported consuming no caffeinated beverages per day.${NBSP} 
	<#elseif (getScore(var3050) >= 1) && (getScore(var3050) <= 998)> 
		<#t>The Veteran reported consuming ${getSelectOneDisplayText(var3050)} caffeinated beverages per day.${NBSP} 
 	</#if> 
 ${MODULE_END}
 </#if>' 
 where template_id = 25;
 
 update template set template_file = '
 <#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if var1010?? && var1010.value??>
${MODULE_TITLE_START}
Drugs:
${MODULE_TITLE_END}
${MODULE_START}
			 
	<#assign score = var1010.value?number> <#--getListScore([var1000,var1001,var1002,var1003,var1004,var1005,var1006,var1007,var1008,var1009])>-->
	<#assign status ="notset"> 
	<#assign statusText ="notset"> 
	<#assign gender = "">
	
	<#if score?has_content && ((score?number) == 0)> 
		<#assign status ="negative">
		<#assign statusText ="no problems">
	<#elseif (score)?has_content && ((score?number) >= 1) && ((score?number) <= 2)> 
		<#assign status ="negative">
		<#assign statusText ="a low level of problems">
	<#elseif (score)?has_content && ((score?number) >= 3) && ((score?number) <= 5)> 
		<#assign status ="positive">
		<#assign statusText ="a moderate level of problems">
	<#elseif (score)?has_content && ((score?number) >= 6) && ((score?number) <= 8)>
		<#assign status ="positive">
		<#assign statusText ="a substantial level of problems">
	<#elseif (score)?has_content && ((score?number) >= 9) && ((score?number) <= 10)>
		<#assign status ="positive">
		<#assign statusText ="a severe level of problems">
	</#if> 
	
    The Veteran\'s Drug screen was ${status} with ${statusText} reported. ${NBSP}
	
${MODULE_END}
</#if>'
where template_id = 28;

delete from variable_template where template_id=28;
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1010, 28);

 update template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if var2930?? && var2930.value??>
${MODULE_TITLE_START}
Resilience and Strengths:
${MODULE_TITLE_END}
${MODULE_START}
	
			<#assign score = getFormulaDisplayText(var2930)?number>
			<#assign scoreText = "">
			<#assign fragments = []>
					<#if (getScore(var2820) >= 2) && (getScore(var2820) <= 998)  >
						<#assign fragments = fragments + ["I am adaptable"]>
					</#if>	
					<#if (getScore(var2830) >= 2) && (getScore(var2830) <= 998)  >
						<#assign fragments = fragments + ["I can deal with whatever"]>
					</#if>	
					<#if (getScore(var2840) >= 2) && (getScore(var2840) <= 998)  >
						<#assign fragments = fragments + ["I find humor when faced with problems"]>
					</#if>	
					<#if (getScore(var2850) >= 2) && (getScore(var2850) <= 998)  >
						<#assign fragments = fragments + ["coping with stress can make me stronger"]>
					</#if>	
					<#if (getScore(var2860) >= 2) && (getScore(var2860) <= 998)  >
						<#assign fragments = fragments + ["I bounce back after hardships"]>
					</#if>	
					<#if (getScore(var2870) >= 2) && (getScore(var2870) <= 998) >
						<#assign fragments = fragments + ["I believe I can achieve"]>
					</#if>	
					<#if (getScore(var2880) >= 2) && (getScore(var2880) <= 998)  >
						<#assign fragments = fragments + ["focus under pressure"]>
					</#if>	
					<#if (getScore(var2890) >= 2) && (getScore(var2890) <= 998)  >
						<#assign fragments = fragments + ["not easily discouraged by failure"]>
					</#if>	
					<#if (getScore(var2900) >= 2) && (getScore(var2900) <= 998)  >
						<#assign fragments = fragments + ["think of myself as strong person"]>
					</#if>	
					<#if (getScore(var2910) >= 2) && (getScore(var2910) <= 998)  >
						<#assign fragments = fragments + ["can handle unpleasant or painful feelings"]>
					</#if>	
		
					<#if (score >= 0)  &&  (score <= 9)>
						<#assign scoreText = "minimal resilience">
					</#if>
					<#if (score >= 10)  &&  (score <= 29)>
						<#assign scoreText = "low resilience">
					</#if>
					<#if (score >= 20)  &&  (score <= 29)>
						<#assign scoreText = "medium resilience">
					</#if>
					<#if (score >= 30)  &&  (score <= 998)>
						<#assign scoreText = "high resilience">
					</#if>
				
					The Veteran had a score of ${score} indicating ${scoreText}. ${LINE_BREAK}${LINE_BREAK}
					
					<#assign beliefs = "none">
					<#if fragments?has_content >
						<#assign beliefs = createSentence(fragments)>
					</#if>
					
					The Veteran reported the following resilience beliefs at least sometimes during the past four weeks: ${beliefs}.${NBSP}
${MODULE_END}
</#if>
' where template_id = 39;

update template 
set template_file =
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if var1749?? && var1749.value??>
${MODULE_TITLE_START}
Anxiety:
${MODULE_TITLE_END}
${MODULE_START}

		<#assign fragments = []>
		<#assign resolved_fragments="">
		<#assign score = var1749.value?number>
		<#assign scoreText ="notset">
		
		<#if (getScore(var1660) > 1)>
			<#assign fragments = fragments + ["feeling nervous"] >
		</#if>
	    <#if (getScore(var1670) > 1)>
			<#assign fragments = fragments + ["can\'t control worrying"]>
		</#if>
		<#if (getScore(var1680) > 1)>
			<#assign fragments = fragments +  ["worrying too much"]>
		</#if>
		<#if (getScore(var1690) > 1)>
			<#assign fragments = fragments +  ["trouble relaxing"]>
		</#if>
		<#if (getScore(var1700) > 1)>
			<#assign fragments = fragments +  ["restlessness"]>
		</#if>
		<#if (getScore(var1710) > 1)>
			<#assign fragments = fragments + ["irritability"]>
		</#if>
		<#if (getScore(var1720) > 1)>
			<#assign fragments = fragments + ["feeling afraid"] >
		</#if>
		
		<#if (fragments?has_content) >
			<#assign resolved_fragments = createSentence(fragments)>
		<#else>
			<#assign resolved_fragments = "None">
		</#if>
			
		<#if score??> 	
			<#if (score >= 0) && (score <= 9)>
				<#assign scoreText = "negative">				
			<#elseif (score >= 10)>
				<#assign scoreText = "positive">
			</#if>
		</#if>
		
		<#if (score >=1) && (score <= 21)>
			<#t>The Veteran\'s GAD-7 screen was ${scoreText}. ${NBSP}
			<#if resolved_fragments != "None">
			The Veteran endorsed the following symptoms were occurring more than half of the days in the past two weeks: ${resolved_fragments}.${NBSP}
			</#if>
		<#elseif (score == 0)>
			<#t>The Veteran\'s GAD-7 screen was ${scoreText}. ${NBSP}
			The Veteran reported having no anxiety symptoms in the past 2 weeks.${NBSP}
		</#if>
${MODULE_END}
</#if>
'
where template_id = 34;
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1749, 34);