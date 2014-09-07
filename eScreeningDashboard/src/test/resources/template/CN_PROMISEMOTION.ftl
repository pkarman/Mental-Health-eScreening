<#include "clinicalnotefunctions"> 
<#-- Template start --> 
${MODULE_TITLE_START} PROMIS EMOTIONAL SUPPORT: ${MODULE_TITLE_END} 
${MODULE_START}
<#if var739??> The Veteran had a score of ${getCustomVariableDisplayText(var739)}, indicating that they currently have  
 <#if (var739.value?number>=0) && (var739.value?number <= 11)> lower than average  
 <#elseif (var739.value?number <= 19) > average	
 <#else> above average 
 </#if> emotional support.${NBSP}
 <#else>${getNotCompletedText()} 
</#if> 
 ${MODULE_END} 
