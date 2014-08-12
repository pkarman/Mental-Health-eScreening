<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
PROMIS EMOTIONAL SUPPORT: 
${MODULE_TITLE_END}
${MODULE_START}
<#assign promis_section>
	<#if var700?? &&  var710?? &&  var720?? && var730??>
	<#assign num = getFormulaDisplayText(var739!("-1"?number))>
  	<#assign score = getListScore([var700, var710, var720, var730])![]><br>
  	<#if score?? && (score < 11) >
  		<#assign avgText = "lower than average">
  	<#elseif score?? && (score >= 11) >
  		<#assign avgText = "average">
  	</#if>
  	
  	The Veteran had a score of ${num}, indicating that they currently have ${avgText} emotional support.<#t>
       
  <#else>
  	${getNotCompletedText()}
  </#if>
  </#assign>
  <#if !(promis_section = "") >
     ${promis_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}