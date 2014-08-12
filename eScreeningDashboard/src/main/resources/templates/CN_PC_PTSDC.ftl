<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
PTSD:
${LINE_BREAK}
${MODULE_TITLE_END}
${MODULE_START}
  <#assign ptsd_section>
	<#if var1940?? && var1950?? && var1960?? && var1970??>
	  <#t>
	<#--  -->
	<#assign fragments = []>
	<#assign resolved_fragments ="">
	<#assign text ="notset"> 
	<#assign score = (getListScore([var1940,var1950,var1960,var1970]))!("-1"?number)>
	<#assign scoreText ="notset">
	 
	<#if score??> 	
		<#if (score >= 0) && (score <= 2)>
			<#assign scoreText = "negative">
		<#elseif (score >= 3) && (score <= 998)><#-- value 999 means they didn\'t answer the question so to include would give a false positive-->
			<#assign scoreText = "positive">
		</#if>
		
		<#if (score >=0  && score <= 998)>
			The Veteran\'s PC-PTSD Screen was ${scoreText!} with a PC-PTSD score of ${score}. <#t>
		<#elseif score == 999 >
			The Veteran did not complete PC-PTSD Screen.<#t>
		</#if>
	</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
</#assign>
  <#if !(ptsd_section = "") >
     ${ptsd_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}