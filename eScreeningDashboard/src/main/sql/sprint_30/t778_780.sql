INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1000, 100);

/******** Update PCL-C cutoff score *************/
/*** Also update DAST-10 ***********/
UPDATE template SET template_file = 
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
<#-- AUDIT C --> <#assign screen = "AUDIT C"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty> <#assign rows = []>  <#if var1229??> <#assign score = getFormulaDisplayText(var1229)> <#if score != "notset"> <#assign cutoff = "3-women/4-men"> <#assign score = score?number>  <#if (score >= 0) && (score <= 2)> <#assign status = "Negative"> <#elseif (score >= 4) && (score <= 998)> <#assign status = "Positive"> <#elseif (score == 3 )> <#assign status = "Positive for women/Negative for men"> </#if> <#else> <#assign score = empty> </#if> <#if (score?string == empty) || (status == empty)> <#assign status = empty> <#assign score = empty> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  
<#-- TBI --> 
<#assign screen = "BTBIS (TBI)"> 
<#assign status = "negative"> <#assign score = "N/A"> <#assign cutoff = "N/A">  
<#if var10717?? && var10717.value??> 
	<#if (var10717.value?number  >=1) > 
		<#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Positive"> </#if> 
	</#if>  
	<#if (!(var10714??)) || (var10714?? && var10714.value=="0" && var2016?? && var2016.value=="false") || (var10715?? && var10715.value=="0" && var2022?? && var2022.value=="false") ||(var10716?? && var10716.value=="0" && var2030?? && var2030.value=="false") || (var10717?? && var10717.value=="0" && var2037?? && var2037.value=="false") > 
		<#assign status = empty> <#assign score = empty> <#assign cutoff = empty> 
	</#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  
	<#-- DAST 10 --> <#assign screen = "DAST-10 (Substance Abuse)"> 
	<#assign status = empty> <#assign score = empty> <#assign cutoff = empty>
	<#if var1000?? && var1000.value?? && var1000.value == "0">
		<#assign status ="Negative">
		<#assign score="0">
		<#assign cutoff = "3"> 
	<#elseif var1010?? > <#assign score = getFormulaDisplayText(var1010)> 
		<#if (score?length > 0) &&  score != "notset"> <#assign cutoff = "3"> 
			<#if ((score?number) <= 2)> <#assign status ="Negative"> 
			<#else> <#assign status ="Positive"> 
			</#if> 
		</#if> 
	</#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>     
	<#-- GAD 7 --> <#assign screen = "GAD-7 (Anxiety)"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if var1749?? > <#assign score = getFormulaDisplayText(var1749)> <#if score != "notset"> <#assign cutoff = "10"> <#if (score?number >= cutoff?number)> <#assign status = "Positive"> <#else> <#assign status ="Negative"> </#if> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>     
	<#-- HOUSING - Homelessness --> <#assign screen = "Homelessness"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if var2000?? && var2000.value??> <#if var2000.value?number == 0> <#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Positive"> <#elseif var2000.value?number == 1> <#if var2001?? && var2001.value?? && (var2001.value?number == 1)> <#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Positive"> <#elseif var2001?? && var2001.value?? && (var2001.value?number == 0) > <#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Negative"> </#if> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  
	<#-- ISI --> <#assign screen = "ISI (Insomnia)"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if var2189?? > <#assign score = getFormulaDisplayText(var2189)> <#if score != "notset"> <#assign cutoff = "15"> <#if (score?number >= cutoff?number)> <#assign status = "Positive"> <#else> <#assign status ="Negative"> </#if> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  
	<#-- MST --> <#assign screen = "MST"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if var2003??> <#if isSelectedAnswer(var2003, var2005)> <#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Positive"> <#elseif isSelectedAnswer(var2003, var2004)> <#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Negative"> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>     
	<#-- PCLC --> <#assign screen = "PCL-C (PTSD)"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if var1929?? > <#assign score = getFormulaDisplayText(var1929)> <#if score != "notset" && score != "notfound"> <#assign cutoff = "44"> <#if (score?number >= cutoff?number)> <#assign status = "Positive"> <#else> <#assign status ="Negative"> </#if> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  <#-- PTSD --> <#assign screen = "PC-PTSD"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if var1989?? > <#assign score = getFormulaDisplayText(var1989)> <#if score != "notset" && score != "notfound"> <#assign cutoff = "3"> <#if (score?number >= cutoff?number)> <#assign status = "Positive"> <#else> <#assign status ="Negative"> </#if> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>     <#-- PHQ 9 DEPRESSION --> <#assign screen = "PHQ-9 (Depression)"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if var1599?? > <#assign score = getFormulaDisplayText(var1599)> <#if score != "notset" && score != "notfound"> <#assign cutoff = "10"> <#if (score?number >= cutoff?number)> <#assign status = "Positive"> <#else> <#assign status ="Negative"> </#if> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  <#-- Prior MH DX/TX - Prior Mental Health Treatment --> <#assign screen = "Prior MH DX/TX"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty> <#assign acum = 0> <#assign Q1complete =false> <#assign Q2complete =false> <#assign Q3complete =false> <#assign Q4complete =false> <#-- var1520: ${var1520!""}<br><br>  var1530: ${var1530!""}<br><br>  var200: ${var200!""}<br><br>  var210: ${var210!""}<br><br>  --> <#if (var1520.children)?? && ((var1520.children)?size > 0)> <#assign Q1complete = true> <#list var1520.children as c> <#if ((c.key == "var1522")  || (c.key == "var1523")  || (c.key == "var1524")) && (c.value == "true")> <#assign acum = acum + 1> <#break> </#if> </#list> </#if>  <#if (var1530.children)?? && ((var1530.children)?size > 0)> <#assign Q2complete = true> <#list var1530.children as c> <#if ((c.key == "var1532")  || (c.key == "var1533") || (c.key == "var1534") || (c.key == "var1535") || (c.key == "var1536")) && (c.value == "true") > <#assign acum = acum + 1> <#break> </#if> </#list> </#if>   <#if (var200.children)?? && ((var200.children)?size > 0)> <#assign Q3complete = true> <#list var200.children as c> <#if ((c.key == "var202") && (c.value == "true"))  > <#assign acum = acum + 1> <#break> </#if> </#list> </#if>  <#if (var210.children)?? && ((var210.children)?size > 0)> <#assign Q4complete = true> <#list var210.children as c> <#if ((c.key == "var214") && (c.value == "true"))  > <#assign acum = acum + 1> <#break> </#if> </#list> </#if>  <#if Q1complete && Q2complete && Q3complete && Q3complete> <#assign score = "N/A"> <#assign cutoff = "N/A"> <#if (acum >= 3)> <#assign status = "Positive"> <#elseif (acum > 0) && (acum <= 2)> <#assign status ="Negative"> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  <#-- TOBACCO --> <#assign screen = "Tobacco Use"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if (var600.children)?? && ((var600.children)?size > 0)> <#if isSelectedAnswer(var600, var603)> <#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Positive"> <#elseif isSelectedAnswer(var600, var601) || isSelectedAnswer(var600, var602)> <#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Negative"> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  <#-- VAS PAIN - BASIC PAIN --> <#assign screen = "VAS PAIN"> <#assign status = empty> <#assign score = empty> <#assign cutoff = empty>  <#if (var2300)?? > <#assign score = getSelectOneDisplayText(var2300)> <#if score != "notset" && score != "notfound"> <#assign cutoff = "4"> <#if (score?number >= cutoff?number)> <#assign status = "Positive"> <#else> <#assign status ="Negative"> </#if> <#else> <#assign score = empty> </#if> </#if>  <#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  ${MATRIX_TABLE_START} ${MATRIX_TR_START} ${MATRIX_TH_START}Screen${MATRIX_TH_END} ${MATRIX_TH_START}Result${MATRIX_TH_END} ${MATRIX_TH_START}Raw Score${MATRIX_TH_END} ${MATRIX_TH_START}Cut-off Score${MATRIX_TH_END} ${MATRIX_TR_END} ${MATRIX_TR_START} <#list rows as row> ${MATRIX_TR_START} <#list row as col> ${MATRIX_TD_START}${col}${MATRIX_TD_END} </#list> ${MATRIX_TR_END} </#list> ${MATRIX_TR_END} ${MATRIX_TABLE_END} ${MODULE_END}'
WHERE
	`template_id` = '100';


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
		<#if (score >= 0) && (score <= 43)>
			<#assign scoreText = "negative">
		<#elseif (score >= 44) && (score <= 998)><#-- value 999 means they didn\'t answer the question so to include would give a false positive-->
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

update template 
set template_file = '<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
INTERVENTIONS: 
${MODULE_TITLE_END}
${MODULE_START}
	
 	<#assign footer_section>

		<#assign outputText = "">
		<#assign errorText = "">
		<#assign incompleteText = "All of the required information has not been provided.">

		<#assign outputText = outputText + "${LINE_BREAK}*Explained confidentiality and the limits of confidentiality.${LINE_BREAK}">

		<#assign part = "">

		<#assign outputText = outputText + "*Educated the Veteran on VA health care benefits and OEF/OIF care coordination services.${LINE_BREAK}
												*OEF/OIF Case Management not indicated at this time.${LINE_BREAK}">
		
		<#if ((var202.value)?? && var202.value == "true") || ((var9060.value)?? && var9060.value == "true")>
				<#assign outputText = outputText + "*Veteran endorsed mental health concerns, recommend outpatient consult to Psychiatry for MH.${LINE_BREAK}"> 
		</#if>
		
		<#if ((var215.value)?? && var215.value == "true") || ((var9066.value)?? && var9066.value == "true")>
				<#assign outputText = outputText + "*Veteran endorsed substance abuse concerns, recommend outpatient consult to ADTP.${LINE_BREAK}"> 
		</#if>
		
		<#if ((var212.value)?? && var212.value == "true") || ((var9063.value)?? && var9063.value == "true")>
				<#assign outputText = outputText + "*Discuss  Veteran\'s prosthetics needs.${LINE_BREAK}"> 
		</#if>

		<#if ((var65.value)?? && var65.value == "true") || ((var9086.value)?? && var9086.value == "true")>
				<#assign outputText = outputText + "*Provided information on Vocational Rehabilitation and WAVE Clinic.${LINE_BREAK}"> 
		</#if>
		
		<#if ((var432.value)?? && var432.value == "true") || ((var9092.value)?? && var9092.value == "true")>
				<#assign outputText = outputText + "*Veteran requested Chaplain consult.${LINE_BREAK}"> 
		</#if>

		<#if (var1229.value)??>
			<#assign v1 = var1229.value> <#-- AUDICT C Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 4)>
					<#assign part = "*Recommend ADTP referral for alcohol abuse.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">


		<#if (var1010.value)??>
			<#assign v1 = var1010.value> <#-- DAST Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 3)>
					<#assign part = "*Recommend ADTP referral for Substance Abuse.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if ((var603.value)?? && var603.value == "true") || ((var9122.value)?? && var9122.value == "true")>
				<#assign outputText = outputText + "*Recommend tobacco cessation.${LINE_BREAK}"> 
		</#if>

		<#if ((var3442.value)?? && var3442.value == "true") || ((var9132.value)?? && var9132.value == "true")>
				<#assign outputText = outputText + "*Veteran requested TBI consult.${LINE_BREAK}"> 
		</#if>

		<#if (var1599.value)??>
			<#assign v1 = var1599.value> <#-- PHQ9 Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 10)>
					<#assign part = "*Recommend psychiatry outpatient consult for depression.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>
		
		<#assign outputText = outputText + part>
		<#assign part = "">


		<#if (var2809.value)??>
			<#assign v1 = var2809.value> <#-- MDQ Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 7)>
					<#if (var2792?? && var2792.value == "true") || (var9142?? && var9142.value == "true")>
						<#if (var2803?? && var2803.value == "true") || (var2804?? && var2804.value == "true") >
								<#assign part = "*Recommend psychiatry outpatient consult for mood.${LINE_BREAK}">
						</#if>
					</#if>
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if ((var1642.value)?? && var1642.value == "true") || ((var9172.value)?? && var9172.value == "true")>
				<#assign outputText = outputText + "*Recommend MST consult.${LINE_BREAK}"> 
		</#if>

		
		<#if (var1749.value)??>
			<#assign v1 = var1749.value> <#-- GAD7 Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 10)>
					<#assign part = "*Recommend psychiatry outpatient consult for anxiety.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if (var1989.value)?? >
			<#assign v2 = var1989.value> <#-- PTSD Score -->  
			<#if (v2 != "notset")> 
				<#if (v2?number >= 3)>
					<#assign part = "*Recommend OEF/OIF PTSD clinic consult.${LINE_BREAK}">
				</#if>
			</#if>
		<#elseif (var1929.value)??>
			<#assign v3 = var1929.value> <#-- PCL Score -->  
			<#if (v3 != "notset")> 
				<#if (v3?number >= 44)>
					<#assign part = "*Recommend OEF/OIF PTSD clinic consult.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#-- ISI Score --> 
		<#if (var2189.value)?? && (var2189.value?string != "notset") && (var2189.value?number >= 15.0) > 
			<#assign outputText = outputText + "*Recommend psychiatry outpatient consult for insomnia.${LINE_BREAK}">
		</#if>

		<#if ((var3233.value)?? && var3233.value == "true") || ((var9203.value)?? && var9203.value == "true")
				|| ((var3234.value)?? && var3234.value == "true") || ((var9204.value)?? && var9204.value == "true")
				|| ((var3235.value)?? && var3235.value == "true") || ((var9205.value)?? && var9205.value == "true")
				|| ((var3313.value)?? && var3313.value == "true") || ((var9213.value)?? && var9213.value == "true")
				|| ((var3314.value)?? && var3314.value == "true") || ((var9214.value)?? && var9214.value == "true")
				|| ((var3315.value)?? && var3315.value == "true") || ((var9215.value)?? && var9215.value == "true")
				|| ((var3323.value)?? && var3323.value == "true") || ((var9223.value)?? && var9223.value == "true")
				|| ((var3324.value)?? && var3324.value == "true") || ((var9224.value)?? && var9224.value == "true")
				|| ((var3325.value)?? && var3325.value == "true") || ((var9225.value)?? && var9225.value == "true")>
				
				<#assign outputText = outputText + "*Recommend Anger Management consult.${LINE_BREAK}"> 
		</#if>

		<#if ((var81.value)?? && var81.value == "true") || ((var9231.value)?? && var9231.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran employment resource information.${LINE_BREAK}"> 
		</#if>

		<#if ((var242.value)?? && var242.value == "true") || ((var9251.value)?? && var9251.value == "true")
				|| ((var772.value)?? && var772.value == "true") || ((var9262.value)?? && var9262.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on housing resources and the VA homeless resources including HCHV outreach schedule.${LINE_BREAK}"> 
		</#if>

		<#if ((var402.value)?? && var402.value == "true") || ((var9272.value)?? && var9272.value == "true")>
				
				<#assign outputText = outputText + "*Educated the Veteran on chaplain services.${LINE_BREAK}"> 
		</#if>

		<#if ((var262.value)?? && var262.value == "true") || ((var9282.value)?? && var9282.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided information on financial resources.${LINE_BREAK}"> 
		</#if>

		<#if ((var301.value)?? && var301.value == "true") || ((var9301.value)?? && var9301.value == "true")
				|| ((var302.value)?? && var302.value == "true") || ((var9302.value)?? && var9302.value == "true")
				|| ((var303.value)?? && var303.value == "true") || ((var9303.value)?? && var9303.value == "true")
				|| ((var304.value)?? && var304.value == "true") || ((var9304.value)?? && var9304.value == "true")
				|| ((var305.value)?? && var305.value == "true") || ((var9305.value)?? && var9305.value == "true")
				|| ((var306.value)?? && var306.value == "true") || ((var9306.value)?? && var9306.value == "true")
				|| ((var307.value)?? && var307.value == "true") || ((var9307.value)?? && var9307.value == "true")
				|| ((var308.value)?? && var308.value == "true") || ((var9308.value)?? && var9308.value == "true")
				|| ((var309.value)?? && var309.value == "true") || ((var9309.value)?? && var9309.value == "true")
				|| ((var310.value)?? && var310.value == "true") || ((var9310.value)?? && var9310.value == "true")
				|| ((var311.value)?? && var311.value == "true") || ((var9311.value)?? && var9311.value == "true")
				|| ((var312.value)?? && var312.value == "true") || ((var9312.value)?? && var9312.value == "true")>
				
				<#assign outputText = outputText + "*Provided information on legal resources.${LINE_BREAK}"> 
		</#if>

		<#if ((var306.value)?? && var306.value == "true") || ((var9306.value)?? && var9306.value == "true")
				|| ((var307.value)?? && var307.value == "true") || ((var9307.value)?? && var9307.value == "true")
				|| ((var308.value)?? && var308.value == "true") || ((var9308.value)?? && var9308.value == "true")
				|| ((var309.value)?? && var309.value == "true") || ((var9309.value)?? && var9309.value == "true")
				|| ((var310.value)?? && var310.value == "true") || ((var9310.value)?? && var9310.value == "true")>
				
				<#assign outputText = outputText + "*Educated Veteran about Veteran\'s Justice Outreach Program.${LINE_BREAK}"> 
		</#if>

		<#if ((var253.value)?? && var253.value == "true") || ((var9323.value)?? && var9323.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9333.value)?? && var9333.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on the GI Bill, eBenefits, VA Work Study.${LINE_BREAK}"> 
		</#if>

		<#if ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on accessing unemployment resources.${LINE_BREAK}"> 
		</#if>

		<#if ((var603.value)?? && var603.value == "true") || ((var9122.value)?? && var9122.value == "true")>
				<#assign outputText = outputText + "*Educated Veteran on Tobacco cessation support services.${LINE_BREAK}"> 
		</#if>

		<#if ((var216.value)?? && var216.value == "true") || ((var9346.value)?? && var9346.value == "true")
				|| ((var1432.value)?? && var1432.value == "true") || ((var9352.value)?? && var9352.value == "true")
				|| ((var1433.value)?? && var1433.value == "true") || ((var9363.value)?? && var9363.value == "true")
				|| ((var942.value)?? && var942.value == "true") || ((var9372.value)?? && var9372.value == "true")
				|| ((var943.value)?? && var943.value == "true") || ((var9373.value)?? && var9373.value == "true")
				|| ((var932.value)?? && var932.value == "true") || ((var9382.value)?? && var9382.value == "true")
				|| ((var933.value)?? && var933.value == "true") || ((var9383.value)?? && var9383.value == "true")>
				
				<#assign outputText = outputText + "*Provided information on Audiology and Optometry services.${LINE_BREAK}"> 
		</#if>

		<#if ((var252.value)?? && var252.value == "true") || ((var9392.value)?? && var9392.value == "true")
				|| ((var1513.value)?? && var1513.value == "true") || ((var9403.value)?? && var9403.value == "true")>
				
				<#assign outputText = outputText + "*Encouraged the Veteran to meet with VSO for further advocacy and assistance with VA SCD claims process & provided VSO contact list.${LINE_BREAK}"> 
		</#if>

		<#if ((var821.value)?? && var821.value == "true") || ((var9421.value)?? && var9421.value == "true")
				|| ((var828.value)?? && var828.value == "true") || ((var9432.value)?? && var9432.value == "true")>
				
				<#assign outputText = outputText + "*Provided VA Advanced Directives brochure & form.${LINE_BREAK}"> 
		</#if>

			<#assign outputText = outputText + "*Educated Veteran on available VA mental health services (including outpatient general and specialty psychiatry clinics, PTSD and MST services, Anger Management, CORE and Family MH Clinic).${LINE_BREAK}">
			<#assign outputText = outputText + "*Provided the Veteran information on the VA Vet Centers services.${LINE_BREAK}">
			<#assign outputText = outputText + "*Educated the Veteran on alcohol and drug treatment services and reviewed ADTP walk-in hours & location.${LINE_BREAK}">
			<#assign outputText = outputText + "*Alerted the PCP to the Veteran medical/health concerns & requests.${LINE_BREAK}">
			<#assign outputText = outputText + "*Directed Veteran to Primary Care Call center for appointment scheduling.${LINE_BREAK}">
			<#assign outputText = outputText + "*Provided VA environmental registry POC information.${LINE_BREAK}">
			<#assign outputText = outputText + "*Educated the Veteran about same-day access clinic on 2North.${LINE_BREAK}">
			<#assign outputText = outputText + "*Reviewed available resources including PEC, Emergency Dept., VA-Telecare and Suicide hotlines.${LINE_BREAK}">
			<#assign outputText = outputText + "*Contact information of this writer, OEF/OIF brochure, and suicide prevention card were given to the Veteran. ${NBSP}Encouraged to call for any further questions or concerns.${LINE_BREAK}${LINE_BREAK}">
			<#assign outputText = outputText + "PLAN:${LINE_BREAK}">
			<#assign outputText = outputText + "*Veteran plans to continue to access physical and mental healthcare through VA San Diego Healthcare System as needed.${LINE_BREAK}">
			<#assign outputText = outputText + "*Veteran\'s Future Appointments:${LINE_BREAK}">
			<#assign outputText = outputText + "*Veteran agreed to the following consults:${LINE_BREAK}">
			<#assign outputText = outputText + "*This writer will remain available to assist Veteran with transition and accessing VA Healthcare services as needed.${LINE_BREAK}">
			<#assign outputText = outputText + "*Veteran to contact this writer for resources, referrals and other VA related healthcare inquiries.${LINE_BREAK}">

		<#if errorText?has_content>
			${errorText}
		<#else>
			${outputText}
		</#if>
	</#assign>
	
	<#if !(footer_section = "") >
     	${footer_section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}'
where template_id = 2;

/* PCL-C veteran summary. Updating intervals (t744) */
update template set template_file = '<#include "clinicalnotefunctions"> 
<#assign score = getCustomVariableDisplayText(var1929)>  
<#-- Template start --> 
${MODULE_TITLE_START} Post-traumatic Stress Disorder ${MODULE_TITLE_END}  

<#if score != "notfound">
	${GRAPH_SECTION_START}  
	${GRAPH_BODY_START} 
		{"type": "stacked", 
		 "title": "My PTSD Score", 
		 "footer": "", 
		 "data": {
		 	"maxXPoint" : 85,
		 	"ticks": [17,31,44,65,85], 
		 	"intervals": {
		 		"Negative":17, 
		 		"Positive":44 
		 	}, 
		 	"score": ${score} 
		 } 
		} 
	${GRAPH_BODY_END} 
	${GRAPH_SECTION_END}  
</#if>

${MODULE_START} 
	PTSD is when remembering a traumatic event keeps you from living a normal life. It\'s also called shell shock or combat stress. Common symptoms include recurring memories or nightmares of the event, sleeplessness, and feeling angry, irritable, or numb. 
	${LINE_BREAK} 
	<b>Recommendation:</b> 
	<#if score = "notfound">
		${NBSP}Veteran declined to respond
	<#elseif score?number gt 43>
		Ask your clinician for further evaluation and treatment options
	<#elseif score?number lt 44>
		You may ask a clinician for help in the future if you feel you may have symptoms of PTSD
	</#if> 
${MODULE_END}' 
where template_id=310;

/*** Update the dashboard alert rule ******/
update rule set expression=
'([1750]+[1760]+[1770]+[1780]+[1790]+[1800]+[1810]+[1820]+[1830]+[1840]+[1850]+[1860]+[1870]+[1880]+[1890]+[1900]+[1910]) >=44'
where rule_id=104 ;


/********* Ticket 780 Dast_other=0 is considered as negative ****************/
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1000, 28);
 update template set template_file = '
 <#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var1000?? && var1000.value??) || (var1010?? && var1010.value??)>
${MODULE_TITLE_START}
Drugs:
${MODULE_TITLE_END}
${MODULE_START}
	
	<#if var1000?? && var1000.value?? && var1000.value == "0">
	   	<#assign score = 0>
	   	<#assign status ="negative">
		<#assign statusText ="no problems">
	<#else>		 
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
	</#if>
    The Veteran\'s Drug screen was ${status} with ${statusText} reported. ${NBSP}
	
${MODULE_END}
</#if>'
where template_id = 28;