<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
PROMIS PAIN INTESITY & INTERFERENCE:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign pain_intensity_section>
	  <#t>

	<#if var2550?? && var2560?? && var2570?? && var2580?? && var2590?? && var2600?? && var2610?? && var2620?? && var2630??>
	<#--  -->
		<#assign score = getFormulaDisplayText(var2640)?number>
		<#assign scoreText = "">
		
		The Veteran endorsed having ${getSelectOneDisplayText(var2550)} severe pain currently. Over the past week the veteran reported a pain intensity of ${getSelectOneDisplayText(var2560)} with ${getSelectOneDisplayText(var2570)} average pain. 
		<br><br>
		<#if (score >= 0) && (score <= 17)>
			<#assign scoreText = "does not">
		<#elseif (score >= 18) && (score <= 998)>
			<#assign scoreText = "does">
		<#else>
			<#assign scoreText = "notset">
		</#if>
		
		The pain ${scoreText} significantly interfere.
		
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(pain_intensity_section = "") >
     ${pain_intensity_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}