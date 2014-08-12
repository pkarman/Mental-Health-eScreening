<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
OTHER HEALTH SYMPTOMS
${MODULE_TITLE_END}
${MODULE_START}
  <#assign other_health_section>
  	<#if var930?? && var940?? && var950?? && var960?? && var970?? && var980?? >
	  <#t>
	<#-- The Veteran endorsed being bothered a lot by the following health symptoms over the past four weeks: hearing loss, tinnitus -->
	<#assign fragments = []> 
    <#if (getListScore([var930, var940]) > 0)>
		<#assign fragments = fragments + ["hearing"] ><#t>
	</#if>
    <#if (getScore(var950) > 0)>
		<#assign fragments = fragments + ["vision"]> <#t>
	</#if>
	<#if (getListScore([var960, var970]) > 0)>
		<#assign fragments = fragments + ["weight"] ><#t>
	</#if>
	<#if (getScore(var980) > 0)>
		<#assign fragments = fragments + ["forgetfulness"] ><#t>
	</#if>
	
	<#if fragments?has_content  >
		<#assign resolved_fragments =  createSentence(fragments)>
	<#else>
		<#assign resolved_fragments = "None">
	</#if>

	The Veteran endorsed being bothered by the following health symptoms over the past four weeks: ${resolved_fragments}.
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(other_health_section = "") >
     ${other_health_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}