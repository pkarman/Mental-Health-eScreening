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

