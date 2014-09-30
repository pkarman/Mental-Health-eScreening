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


</#assign>
	
	<#if !(matrix_section = "") >
     	${matrix_section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}'
 WHERE template_id = 100;
 
 INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2000, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (761, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (762, 100);

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
    <#if var60?? && hasValue(getSelectOneDisplayText(var60)) && var70?? && hasValue(getSelectOneDisplayText(var70)) >
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}, who usually works as a ${getFreeformDisplayText(var70)}.  ${NBSP}
    <#elseif var60?? && hasValue(getSelectOneDisplayText(var60)) && !(var70?? && hasValue(getFreeformDisplayText(var70)))>
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}.  ${NBSP}
    <#elseif !(var60?? && hasValue(getSelectOneDisplayText(var60))) && var70?? && hasValue(getFreeformDisplayText(var70)) >
      The Veteran reported usually working as a ${getFreeformDisplayText(var70)}.  ${NBSP}
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

/**** GAD-7 Ticket #545  ************/
update template 
set template_file =
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
ANXIETY:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign anxiety_section>

	<#if (var1660.children)?? && (var1670.children)?? && (var1680.children)?? && (var1690.children)?? 
		&& (var1700.children)?? && (var1710.children)?? && (var1720.children)??
		&& ((var1660.children)?size > 0) && ((var1670.children)?size > 0) && ((var1680.children)?size > 0)
		&& ((var1690.children)?size > 0) && ((var1700.children)?size > 0) && ((var1710.children)?size > 0)
			&& ((var1720.children)?size > 0)>
        
		<#assign fragments = []>
		<#assign resolved_fragments="">
		<#assign text="notset"> 
		<#assign score = (getListScore([var1660,var1670,var1680,var1690,var1700,var1710,var1720]))!("-1"?number)>
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
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(anxiety_section = "") >
     ${anxiety_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
'
where template_id = 34;

-- /* DEMOGRAPHICS UPDATE*/
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
DEMOGRAPHICS: 
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var30.children)?? || (var40.children)?? || (var20.children)?? || (var143.children)??>

		<#assign age = -1>
		<#if var143?? && (var143.children)?? && (var143.children?size > 0)>
			<#assign age = calcAge(var143.children[0].value)>
		</#if>
	
  	
		<#-- The Veteran is a 28 year-old hispanic. -->
    
		The Veteran is a ${NBSP} <#if age != -1> ${age} year-old<#t></#if>
        <#if var30?? && (var30.children)?? && (var30.children?size > 0)>
		<#if isSelectedAnswer(var30,var33) >
          ,  <#t>
        <#else>
          ${""?left_pad(1)}whom is ${getSelectOneDisplayText(var30)},  <#t> 
        </#if> 
        </#if>
    
    <#-- The Veteran reports being a White/Caucasian, American Indian or Alaskan Native male. -->
    <#if hasValue(getSelectMultiDisplayText(var40)) || hasValue(getSelectOneDisplayText(var20)) >
      <#-- The Veteran reports being<#t>  -->
      <#if hasValue(getSelectMultiDisplayText(var40)) >
        ${""?left_pad(1)} ${getSelectMultiDisplayText(var40)}<#t>
      </#if> 
      <#if hasValue(getSelectOneDisplayText(var20)) >
        ${""?left_pad(1)}${getSelectOneDisplayText(var20)}<#t>
      </#if> 
      .  <#t>
    </#if> 
   
    <#--The Veteran reported BMI is 27, indicating that he/she may be is overweight.-->
    <#if hasValue(getFormulaDisplayText(var11))  >
      <#assign num = getFormulaDisplayText(var11)?number />
      The Veteran\'s reported BMI is ${num}, indicating that he/she <#t>
      <#if (num < 18.5) >
        is below a normal a weight.  <#t>
      <#elseif (num < 25) && (num >= 18.5) >
        is at a normal weight.  <#t>
      <#elseif (num < 30) && (num >= 25) >
        is overweight.  <#t>
      <#elseif (num < 40) && (num >= 30) >
        is obese.  <#t>
      <#elseif (num >= 40) >
        is morbid obese.  <#t>
      </#if>
    </#if>
    
    <#else>
    	${getNotCompletedText()}
    </#if>
${MODULE_END}
'
where template_id = 4;

-- /*** SOCIAL UPDATE ticket-589 ***/
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SOCIAL: 
${MODULE_TITLE_END}
${MODULE_START}
	<#if var110?? || (var90.children)?? || (var160.children)??  || (var130.children)?? || (var131.value)?? >
    <#--The Veteran lives in a house with spouse or partner, and children.-->
    <#if hasValue(getSelectOneDisplayText(var110)) && hasValue(getSelectMultiDisplayText(var90)) >
      The Veteran ${getSelectOneDisplayText(var110)}  
      <#if wasAnswerNone(var90)>
        alone.  ${NBSP}
      <#else> 
        with ${getSelectMultiDisplayText(var90)}. ${NBSP}
      </#if>
    <#elseif hasValue(getSelectOneDisplayText(var110)) && !(hasValue(getSelectMultiDisplayText(var90)))>
      The Veteran ${getSelectOneDisplayText(var110)}.  ${NBSP}
    <#elseif !(hasValue(getSelectOneDisplayText(var110))) && hasValue(getSelectMultiDisplayText(var90)) >
     The Veteran lives with ${getSelectMultiDisplayText(var90)}. ${NBSP}
    </#if>
    <#--The Veteran reported not having any children.-->
    <#if var130?? || var131?? >
      <#if wasAnswerTrue(var131) >
        The Veteran reported not having any children. ${NBSP}
      <#else>
        <#--The Veteran has 2 child(ren) who are child(ren) are 2,4 years old.-->
		<#assign rowCount = ((var130.children)?size)!0>
        <#if (rowCount > 0) >
          <#if (rowCount == 1) >
			<#assign text = ((var130.children[0].children[0].displayText)!"")>
            The Veteran has 1 child who is ${text} years old. ${NBSP}
          <#elseif (rowCount > 1) >
            The Veteran has ${getNumberOfTableResponseRows(var130)} children who are ${getTableMeasureDisplayText(var130)} years old. ${NBSP}
          </#if>
        </#if>
      </#if>
    </#if>
    
    <#if hasValue(getSelectMultiDisplayText(var160)) >
    	${LINE_BREAK}${LINE_BREAK}Source(s) of support is/are: ${getSelectMultiDisplayText(var160)}. ${NBSP}
    </#if>
    
    <#--The Veteran indicated a history of physical violence .-->
    <#if hasValue(getSelectOneDisplayText(var150)) >
    	${LINE_BREAK}${LINE_BREAK}The Veteran ${getSelectOneDisplayText(var150)} a history of physical violence or intimidation in a current relationship. ${NBSP}
    </#if>
   
    <#else>
    	${getNotCompletedText()}
    </#if>    
${MODULE_END}
'
where template_id = 6;
/* Homelessness Clinical Reminder */

UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
HOUSING: 
${MODULE_TITLE_END}
${MODULE_START}

  	<#if var2000?? && (var2000.children)?? && ((var2000.children)?size > 0)  >
		<#if (var2000.children[0].key == "var763") >  <#-- declined -->
				The Veteran declined to discuss their current housing situation.
		
		<#elseif (var2000.children[0].key == "var761")>
				The Veteran ${getSelectOneDisplayText(var2000)} been living in stable housing for the last 2 months.
				<#if (var2002.children)?? && ((var2002.children)?size > 0)>
				The Veteran has been living ${getSelectOneDisplayText(var2002)}.</#if>
				<#if (var2008.children)?? && ((var2008.children)?size > 0)>
				The Veteran ${getSelectOneDisplayText(var2008)} like a referral to talk more about his/her housing concerns.${NBSP}
				</#if>
		
		<#elseif (var2000.children[0].key == "var762")>
			The Veteran ${getSelectOneDisplayText(var2000)} been living in stable housing for the last 2 months. 
			<#if (var2002.children)?? &&  ((var2002.children)?size > 0)>
				The Veteran has been living ${getSelectOneDisplayText(var2002)}.
			</#if>
			<#if var2001?? && var2001.children?? && ((var2001.children)?size > 0)> 
				The Veteran ${getSelectOneDisplayText(var2001)} concerned about housing in the future.
			</#if>
			<#if var2008?? && (var2008.children)?? &&  ((var2008.children)?size > 0)>
				The Veteran ${getSelectOneDisplayText(var2008)} like a referral to talk more about his/her housing concerns.
			</#if>
		
		<#else>
				${getNotCompletedText()}.
		</#if>
    <#else>
		${getNotCompletedText()}
	</#if> 
${MODULE_END}
'
where template_id = 8;

UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
ADVANCE DIRECTIVE:
${MODULE_TITLE_END}
${MODULE_START}
  <#-- var800: ${var800}<br><br>var820: ${var820}<br><br> var826: ${var826}<br><br> --> <#-- test objs -->
  <#assign nextLine = "">
  	<#if (var800.children)?? && (var800.children)?? && (var800.children)??  
			&& (var800.children?size > 0) && (var820.children?size > 0) && (var826.children?size > 0)>
    <#if (var800.children)?? && (var800.children?size > 0) && hasValue(getSelectMultiDisplayText(var800)) >
    	<#-- In what language do you prefer to get your health information? -->
      ${nextLine}The Veteran requests to receive information in ${getSelectMultiDisplayText(var800)}.
      <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
    </#if>
    <#if (var820.children)?? && (var820.children?size > 0) && hasValue(getSelectMultiDisplayText(var820)) >
      <#-- Do you have and Advance Directive or Durable Power of Attorney for Healthcare?  -->
      ${nextLine}The Veteran reported ${getSelectMultiDisplayText(var820)} an Advance Directive or Durable Power of Attorney for Healthcare.
      <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
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
SPIRITUAL HEALTH: 
${MODULE_TITLE_END}
${MODULE_START}
  	<#-- The Veteran reported that spirituality is important.  --> 
  	<#if (var400.children)?? || ((var420.children)?? && ((var420.children)?size > 0)) || ((var430.children)?? && ((var430.children)?size > 0)) || ((var440.children)?? && ((var440.children)?size > 0))>
	<#if (var400.children)?? && ((var400.children)?size > 0) && hasValue(getSelectOneDisplayText(var400)) >
		The Veteran reported that spirituality and/or religion ${getSelectOneDisplayText(var400)} important to him/her.  ${LINE_BREAK}
     </#if>

	<#if (var420.children)?? && ((var420.children)?size > 0)>
	  <#if isSelectedAnswer(var420,var423)>
		<#assign text2 = "is not connected to a faith community but would like to be part of one">
	  <#else>
		<#assign text2 = getSelectOneDisplayText(var420) + " connected to a faith community">
	  </#if>	
	  The Veteran ${text2} 
	</#if>
	
	<#if (var430.children)?? && ((var430.children)?size > 0)>
	  and ${getSelectOneDisplayText(var430)} a referral to see a chaplain at the time of screening. ${LINE_BREAK}
	<#else>
		.${LINE_BREAK}
	</#if>
	
	<#-- The Veteran feels that combat or military service affected his/her religious views in the following way: god. -->
  	<#if (var440.children)?? && ((var440.children)?size > 0) && hasValue(getSelectOneDisplayText(var440)) >
		<#if isSelectedAnswer(var440,var443)>
			The Veteran does not know how combat or military service has affected his/her religious views.  ${NBSP}
		<#else>
			The Veteran feels that combat or military service ${getSelectOneDisplayText(var440)} have an affect on his/her religious views.${NBSP}
		</#if>
    </#if>  	
    <#else>
    	${getNotCompletedText()}
    </#if>
${MODULE_END}'
where template_id = 11;

UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SERVICE HISTORY: 
${MODULE_TITLE_END}
${MODULE_START}
        <#assign nextLine = "">
		<#if (var3180.children)??  && (var3180.children?size>0) && ((var3160.children)?? && (var3160.children)?has_content)>
			<#-- var3180: ${var3180}<Br><br>  -->
		
			<#assign allAnswersPresent = false>
			<#assign allAnswersNone = false>
			<#assign outputText = "">
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

			<#if typeIsComplete && branchIsComplete && beg_yrIsComplete && end_yrIsComplete 
										&& dischargeTypeIsComplete && rankIsComplete && jobIsComplete>
				<#assign allAnswersPresent =true>
					
				<#if ((outputText?trim?length) > 0)>
					<#assign outputText = 
						("The Veteran reported previous enlistment in the " + type + " " + branch + " in " + beg_yr 
						+ ", received an " + dischargeType + " discharge in " + end_yr + " at the rank of " + rank + ".")>
				<#else>
					<#assign outputText = ("The Veteran reported entering the " + type + " " + branch + " in " + beg_yr 
						+ ", received an " + dischargeType + " discharge in " + end_yr + " at the rank of " + rank + ".")>
				</#if>
			<#else>
				<#assign allAnswersPresent =false>
			</#if>


			<#-- clear our vars for the next row\'s processing -->
			<#assign type = "">
			<#assign branch = "">
			<#assign beg_yr = "">
			<#assign end_yr = "">
			<#assign dischargeType = "">
			<#assign rank = "">
			<#assign job = "">

			<#-- after processing a row , if a var is missing then it is not complete therefore "all" questions were not answered -->
			<#if !allAnswersPresent>
					${nextLine}${getNotCompletedText()}.
                    <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
					<#-- no need to continue if we are finished processing a row and a var is missing -->
					<#break>
			<#else>
					${nextLine}${outputText} 
                    <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">		
			</#if>
		  </#list>
			
		  <#if allAnswersPresent>
			${nextLine}The Veteran also served in the following operations or combat zones: ${getSelectMultiDisplayText(var3160)}.
            <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		  </#if>
		<#else>	 
			${getNotCompletedText()}. ${NBSP}
		</#if>
${MODULE_END}
'
where template_id = 12;

/**** Veteran Summary header #ticket 230 ************/
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_START}

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="50%" valign="middle"><h2  style="color:#1b4164"><strong>eScreening Summary</strong></h2></td>
    <td width="50%" valign="top" align="right"><img width="198" height="66" src="resources/images/logo_va_veteran_summary.gif "> <img width="130" height="56" src="resources/images/cesamh_blk_border.png"><br></td>
  </tr>
</table>

  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom: 1px dashed #000000; border-top:1px dashed #000000;">
  <tr>

    <td width="50%" valign="top" style="border-right: 1px dashed #000000;">
        <div align="center" style="color:#1b4164"><h3> <strong>${getFreeTextAnswer(var630)} ${getFreeTextAnswer(var632)} ${getFreeTextAnswer(var634)}</strong></h3>

        <#if var2??>
        <strong>${getVariableDisplayText(var2)}, ACSW</strong><br>
        </#if>

        <#if var7??>
        <strong>${getVariableDisplayText(var7)}</strong>
        <#else>
        Assessment is incomplete
        </#if>
    <br>

    </div></td>

    <td width="50%" valign="top"><h3 align="center"  style="color:#1b4164">Appointments</h3>
      
      
        <#if var6?? && (var6.children)?? >

            <#if ((var6.children)?size > 0) >
                <ul>
                ${delimitChildren(var6 "<li>" "</li>" true)}
                </ul>
            <#else>
                <div align="center"><h4>None scheduled</h4></div>
            </#if>
        <#else>
            <div align="center"><h4>Appointments unavailable</h4><div>
        </#if>
      </td>

  </tr>

</table>
<br>

<div>For questions or concerns, or for a full report of your results, call  the OEF/OIF/OND Transition Case Manager, Natasha Schwarz, ACSW, at (858) 642-3615.</div>

<div style="text-align: center; color:#1b4164"> <strong>If  you need medical attention immediately, go straight to the Emergency  Department.</strong></div><br>

<div><strong>Note:</strong> The results of this screening are NOT diagnoses and do not affect VA  disability ratings. </div>
${MODULE_END}'
where template_id = 200;

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description) VALUES (6, 3, 'Veteran Appointments', 'Veteran appointments from VistA');
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description) VALUES (7, 3, 'Assessment Completion', 'Veteran\'s assessment completion date');
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (630, 200);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (632, 200);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (634, 200);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2, 200);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (7, 200);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (6, 200);

/**** Veteran Summary footer #ticket 230 ************/
UPDATE template 
set template_file = '<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_START}
<table>
    <tr>
        <td class="justifyCtrTableData">For online information about support services and benefits, visit the VA Center of Excellence resource site:</td>
    </tr>
    <tr>
        <td class="justifyCtrTableData">http://escreening.cesamh.org</td>
    </tr>
    <tr class="justifyCtrTableData">
        <td class="justifyCtrTableData"><img src="resources/images/escreening_cdsmith_QR_code_small.png"></td>
    </tr>
    <tr>
        <td class="justifyLftTableData">For confidential help and support any time, call the Veteran\'s Suicide Prevention/Crisis Hotline at 
(800) 273-8255. The Hotline is never closed; someone is always there to take your call, even on holidays and in the middle of the night. 
</td>
    </tr>
</table>
<br/>
<br/>
<div style="text-align: center;"><span style="font-size:11px;"><span style="font-family:arial,helvetica,sans-serif;">DEPARTMENT OF VETERANS AFFAIRS</span></span></div>
<div style="text-align: center;"><span style="font-size:11px;"><span style="font-family:arial,helvetica,sans-serif;">VA San Diego Healthcare System I 3350 La Jolla Village Dr. I San Diego, CA 92161</span></span></div>
${MODULE_END}'
where template_id = 220;

/**** Veteran advanced directive update ticket #230 ************/
UPDATE template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
    ${MODULE_TITLE_START}
    Advance Directive
    ${MODULE_TITLE_END}
    ${MODULE_START}                 

    This is a legal paper that tells your wishes for treatment if you become too sick to talk, and if needed, can help your doctors and family to make decisions about your care. 
    ${LINE_BREAK}
    ${LINE_BREAK}
    <b>Results:</b>${NBSP}
    <#if !(var820.children)?? || ((var820.children)?size == 0)>
        Declined
        ${LINE_BREAK}
        <b>Recommendations:</b> Call VA Social Work Service at (858) 552-8585 ext. 3500, and ask for help in creating and filing an advance directive.
    <#elseif isSelectedAnswer(var820,var821)>
        Incomplete
        ${LINE_BREAK}
        <b>Recommendations:</b> Call VA Social Work Service at (858) 552-8585 ext. 3500, and ask for help in creating and filing an advance directive.
    <#elseif isSelectedAnswer(var820,var822)>
        Complete
    </#if>
    ${MODULE_END}'
where template_id = 300;


-- /* VETERAN SUMMARY - Homelessness  Update #230 */
UPDATE template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var2000)?? && (var2000.children)?? && ((var2000.children)?size > 0)>
    ${MODULE_TITLE_START}
    Homelessness
    ${MODULE_TITLE_END}
    ${MODULE_START}
    This is when you do not have a safe or stable place you can return to every night. The VA is committed to ending Veteran homelessness by the end of 2015. 
    ${LINE_BREAK}
    ${LINE_BREAK}
    <b>Results:</b>${NBSP}
    <#if isSelectedAnswer(var2000,var761)>
        unstable housing/at risk
        ${LINE_BREAK}
        <b>Recommendation:</b> Call the VA\'s free National Call Center for Homeless Veterans at (877)-424-3838 and ask for help. Someone is always there to take your call.
    <#elseif isSelectedAnswer(var2000,var762)>
        stable housing
    </#if>

    ${MODULE_END}
</#if>'
where template_id=301;


-- /* VETERAN SUMMARY - Alcohol Use  Update #230*/
UPDATE template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var1229??) &&  getFormulaDisplayText(var1229) != "notfound">
    ${MODULE_TITLE_START}
    Alcohol Use
    ${MODULE_TITLE_END}
    ${MODULE_START}
    Drinking too much, too often, or both, causes serious problems. Abuse can have negative effects on school, work, and relationships, and can cause liver disease and cirrhosis, congestive heart failure, seizures, falls, hypertension, and other serious health risks.
    ${LINE_BREAK}
    ${LINE_BREAK}
    <b>Results:</b> ${NBSP}
    <#if (getFormulaDisplayText(var1229)?number >= 0) && (getFormulaDisplayText(var1229)?number <= 2)>
        negative screen
    <#elseif (getFormulaDisplayText(var1229)?number == 3) >
        at risk
    <#elseif (getFormulaDisplayText(var1229)?number >= 4) && (getFormulaDisplayText(var1229)?number <= 12)>
        at risk
    </#if>
    ${LINE_BREAK}
    <b>Recommendation:</b> If female, limit yourself to one drink a day; if male, limit yourself to 2 drinks a day. If this is difficult, ask your clinician for help with managing your drinking.  
    ${MODULE_END}
</#if>
' where template_id = 302;


-- /* VETERAN SUMMARY - Insomnia Update #230 */
UPDATE template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if var2189?? && getFormulaDisplayText(var2189) != "notfound">
    ${MODULE_TITLE_START}
    Insomnia
    ${MODULE_TITLE_END}
    ${MODULE_START}
    Insomnia is having trouble sleeping that lasts longer than a few weeks. Some causes are: medical (like depression or pain), lifestyle factors (such as too much caffeine), or even stress. 
    ${LINE_BREAK}
    ${LINE_BREAK}
    <b>Results:</b>${NBSP}
    <#if (getFormulaDisplayText(var2189)?number >= 0) && (getFormulaDisplayText(var2189)?number <= 7)>
        negative screen
    <#elseif (getFormulaDisplayText(var2189)?number >= 8) && (getFormulaDisplayText(var2189)?number <= 14)>
        subthreshold insomnia
    <#elseif (getFormulaDisplayText(var2189)?number >= 15) && (getFormulaDisplayText(var2189)?number <= 21)>
        moderate insomnia
    <#elseif (getFormulaDisplayText(var2189)?number >= 22) && (getFormulaDisplayText(var2189)?number <= 28)>
        severe insomnia
    </#if>
    <#if (getFormulaDisplayText(var2189)?number >= 15)>
        ${LINE_BREAK}
        <b>Recommendation:</b> Describe your sleeping problems to your clinician, or learn more about insomnia at the  CESAMH site at: http://escreening.cesamh.org 
    </#if>
    ${MODULE_END}
</#if>
' where template_id = 303;


-- /* VETERAN SUMMARY - Environmental Exposure Update #230 */
UPDATE template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var10540.children)??  && ((var10540.children)?size > 0)>
    ${MODULE_TITLE_START}
    Environmental Exposure
    ${MODULE_TITLE_END}
    ${MODULE_START}      
    This is when you have been exposed to a hazard that may have potential health risks.
    ${LINE_BREAK}
    ${LINE_BREAK}
    <b>Results:</b> ${NBSP}
    <#if isSelectedAnswer(var10540,var10541)>
        none reported
    <#elseif isSelectedAnswer(var10540,var10542)>
        at risk
        ${LINE_BREAK}
        <b>Recommendation:</b> Call Dale Willoughby at the Environmental Registry Program and discuss your exposure: (858) 642-3995, weekdays 7:30am-4:00pm.
    </#if>
    ${MODULE_END}
</#if>
' where template_id=304;


-- /* VETERAN SUMMARY - Military Sexual Trauma (MST) update #230 */
UPDATE template set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var2003.children)?? && ((var2003.children)?size > 0)>
    ${MODULE_TITLE_START}
    Military Sexual Trauma (MST)
    ${MODULE_TITLE_END}
    ${MODULE_START}
    MST is sexual assault or repeated, threatening sexual harassment that occurred while the Veteran was in the military. MST can happen any time or anywhere, to men and women. MST can affect your physical and mental health, even years later.
    ${LINE_BREAK}
    ${LINE_BREAK}
    <b>Results:</b>${NBSP} 
    <#if isSelectedAnswer(var2003,var2004)>
        negative screen
        ${LINE_BREAK}
        <b>Recommendation:</b> none
    <#elseif isSelectedAnswer(var2003,var2005)>
        postive screen
        ${LINE_BREAK}
        <b>Recommendation:</b> Ask your clinician for help managing your MST. 
    <#elseif isSelectedAnswer(var2003,var2006)>
        declined to answer
    </#if>
    ${MODULE_END}
</#if>
' where template_id=305;


-- /* VETERAN SUMMARY - Tobacco Use  update #230*/
UPDATE template set template_file = ' 
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var600.children)?? && ((var600.children)?size > 0)>
    ${MODULE_TITLE_START}
    Tobacco Use
    ${MODULE_TITLE_END}
    ${MODULE_START}

    The use of tobacco causes harm to nearly every organ in the body. Quitting greatly lowers your risk of death from cancers, heart disease, stroke, and emphysema. There are many options, such as in-person and telephone counseling, nicotine replacement, and prescription medications.
    ${LINE_BREAK}
    ${LINE_BREAK}
    <b>Results:</b>${NBSP} 
    <#if isSelectedAnswer(var600,var601) || isSelectedAnswer(var600,var602)>
        negative screen
    <#elseif isSelectedAnswer(var600,var603)>
        current user
        ${LINE_BREAK}
        <b>Recommendations:</b> Prepare a plan to reduce or quit the use of tobacco. Get support from family and friends, and ask your clinician for help if needed.
    </#if>
    ${MODULE_END}
</#if>
' where template_id=306;

-- fix for 230 where the veteran summary template for the PCL-C module module was mapped to another module
update survey_template st set survey_id=34 
where st.survey_id=35 and st.template_id=310;
