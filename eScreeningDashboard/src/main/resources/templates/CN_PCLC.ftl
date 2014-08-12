<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
PCLC:
${LINE_BREAK}
${MODULE_TITLE_END}
${MODULE_START}
  <#assign pclc_section>
	<#if var1750?? && var1760?? && var1770?? && var1780?? && var1790?? && var1800?? && var1810?? 
		&& var1830?? && var1840?? && var1850?? && var1860?? && var1870?? && var1880?? && var1890?? && var1900?? && var1910??>
	  <#t>
	<#--  -->
	<#assign fragments = []>
	<#assign resolved_fragments ="">
	<#assign text ="notset"> 
	<#assign score = (getListScore([var1750,var1760,var1770,var1780,var1790,var1800,var1810,var1830,var1840,var1850,var1860,var1870,var1880,var1890,var1900,var1910]))!("-1"?number)>
	<#assign scoreText ="notset">
	
	<#if score??> 	
		<#if (score >= 0) && (score <= 49)>
			<#assign scoreText = "negative">
		<#elseif (score >= 50) && (score <= 998)><#-- value 999 means they didn\'t answer the question so to include would give a false positive-->
			<#assign scoreText = "positive">
		</#if>
		
		<#if (score >=0  && score <= 998)>
			The Veteran\'s PCL-C Screen was ${scoreText!} with a PCL-C score of ${score!("-1"?number)}. <#t>
		<#elseif score == 999 >
			The Veteran did not complete PCL-C Screen.<#t>
		</#if>
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
</#assign>
  <#if !(pclc_section = "") >
     ${pclc_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}