<#include "clinicalnotefunctions"> 
<#-- Template start --> 
<#if var739??>
${MODULE_TITLE_START} PROMIS EMOTIONAL SUPPORT: ${MODULE_TITLE_END} 
${MODULE_START}
<#if var739??> The Veteran had a score of ${getCustomVariableDisplayText(var739)?number}, indicating that they currently have <#if (var739.value?number>=0) && (var739.value?number <= 11)>	lower than average  
<#elseif (var739.value?number <= 19)> average  	<#else> above average  </#if>${NBSP} emotional support.${NBSP}
 <#else>${getNotCompletedText()} 
</#if> 
 ${MODULE_END}
</#if>
