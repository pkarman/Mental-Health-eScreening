<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
PAIN:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign pain_section>
	  <#t>
	<#if var2300?? && var2330?? >  
	<#--  -->
	<#assign fragments = []>
	<#assign text ="notset"> 

	<#assign level = getSelectOneDisplayText(var2300)!("-1"?number)>
	<#assign bodyParts = getTableMeasureValueDisplayText(var2330)!"">
	
	<#if level?? && level?number == 999 >
		${getNotCompletedText()}.
	<#else>
		Over the past month, the Veteran\'s reported pain level was ${level} of 10. The pain was located in: ${bodyParts}.<#t> 
	</#if>
	
	<#else>
		${getNotCompletedText()}.
	</#if>

  </#assign>
  <#if !(pain_section = "") >
     ${pain_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}