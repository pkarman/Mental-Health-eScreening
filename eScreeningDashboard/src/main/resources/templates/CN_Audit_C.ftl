<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
ALCOHOL:

${MODULE_TITLE_END}
${MODULE_START}
  <#assign alcohol_section>
	  <#t>
	
	var1200 - ${var1200}	

	<#if var1200?? && var1210?? && var1220?? >
	<#assign score = getListScore([var1200,var1210,var1220])>
	<#assign status ="">  <#-- positive or negative -->
	<#assign gender = "">
	
	 
		<#if (score >= 0 && score <= 2)>
			<#assign status = "negative">
		<#elseif (score >= 4 && score <= 998)>
			<#assign status = "positive">
		<#elseif (score == 3 )>
			<#assign status = "positive for women/negative for men">
		</#if>
	
	
    The Veteran\'s AUDIT-C screen was ${status} with a score of ${score}.
    
    <#else>
    	${getNotCompletedText()}
    </#if>
  </#assign>
  <#if !(alcohol_section = "") >
     ${alcohol_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}