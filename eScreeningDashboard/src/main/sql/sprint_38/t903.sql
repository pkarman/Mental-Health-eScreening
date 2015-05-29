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
<#assign status = "Skipped"> <#assign score = "N/A"> <#assign cutoff = "N/A">  
<#if var10718?? && var10718.value??> 
	<#if (var10718.value?number  >=4) && (var10718.value?number <999)> 
		<#assign score = "N/A"> <#assign cutoff = "N/A"> <#assign status = "Positive"> 
	</#if>  
	<#if (var10718.value?number  <4)> 
		<#assign status = "Negative"> <#assign score = empty> <#assign cutoff = empty> 
	</#if> 
	</#if>
<#assign rows = rows + [[screen, status, score, cutoff]]> <@resetRow/>  
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
	
INSERT INTO `variable_template` ( `assessment_variable_id`, `template_id` ) VALUES (10718, 100);

