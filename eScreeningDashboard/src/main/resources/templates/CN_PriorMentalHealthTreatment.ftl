<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
PRIOR TREATMENT:
${LINE_BREAK}
${MODULE_TITLE_END}
${MODULE_START}
  <#assign prior_mh_treatment_section>
  	<#if var1520?? && var1521?? && var1530?? && var1531??>
	  <#t>
	<#--  -->
	<#assign fragments = []>
	<#assign text =""> 
		
	<#if var1520?has_content> 	
		<#if isSelectedAnswer(var1520, var1521)><#-- was answer "none" or something else -->
			The Veteran reported being diagnosed with no prior mental health issues in the last 18 months.<#t><br>
		<#else>
			The Veteran endorsed being diagnosed with ${getSelectMultiDisplayText(var1520)} within the last 18 months.<#t><br>
		</#if>
	</#if>
	
	<#if var1530?has_content>
		<#if isSelectedAnswer(var1530, var1531)><#-- was answer "none" or something else -->
			The Veteran reported receiving no prior mental health treatment for a mental health diagnosis in the last 18 months.<#t><br>
		<#else>
			The Veteran endorsed having received the following treatments within the last 18 months for a mental health diagnosis: ${getSelectMultiDisplayText(var1530)}.<#t><br>
		</#if>
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(prior_mh_treatment_section = "") >
     ${prior_mh_treatment_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}