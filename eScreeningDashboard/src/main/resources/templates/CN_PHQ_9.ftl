<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
DEPRESSION:
${LINE_BREAK}
${MODULE_TITLE_END}
${MODULE_START}
  <#assign dep_section>
	  <#t>
	<#if var1550?? && var1560?? && var1570?? && var1580?? && var1590??>
	<#--  -->
	<#assign fragments = []>
	<#assign text ="notset"> 
	<#assign score = (getListScore([var1550,var1560,var1570,var1580,var1590]))!("-1"?number)>
	<#assign scoreText ="notset">
		
	<#if score??> 	
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
	</#if>
	
	<#if (score >=1) && (score <= 27)>
		The Veteran\'s PHQ-9 screen was ${scoreText}. The score was ${(score)?string} which is suggestive of ${text}.
	<#elseif (score == 0)>
		The Veteran\'s PHQ-9 screen was ${scoreText}. The score was ${(score)?string}.
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(dep_section = "") >
     ${dep_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}