<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
SLEEP:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign sleep_section>
	  <#t>
	<#if var2120?? && var2130?? && var2140?? && var2150?? && var2160?? && var2170?? && var2180?? >  
	<#--  -->
	<#assign fragments = []>
	<#assign text ="notset"> 
	<#assign score = (getListScore([var2120,var2130,var2140,var2150,var2160,var2170,var2180]))!("-1"?number)>
	<#assign scoreText ="notset">
		
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
			The Veteran\'s ISI was ${scoreText} indicating ${text} for the past week.<#t>
		<#elseif score == 999>
			<#-- this is score 999 -->
			The Veteran did not complete the ISI screen.<#t>>
		</#if>
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(sleep_section = "") >
     ${sleep_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}