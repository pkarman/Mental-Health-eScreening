<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
LEGAL: 
${MODULE_TITLE_END}
${MODULE_START}
	
  <#assign legal_section>
  <#if var300?? && var320?? && var330??>
    <#if hasValue(getSelectMultiDisplayText(var300)) >
      The Veteran reported the following legal concern(s): ${getSelectMultiDisplayText(var300)}.  <#t>
    </#if>
    <#if hasValue(getSelectOneDisplayText(var320)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var320), "yes") >
      	The Veteran reported having an Advance Directive.  <#t>
      </#if>
    </#if>
    <#if hasValue(getSelectOneDisplayText(var330)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var330), "yes") >
      	The Veteran would like information about an Advance Directive.  <#t>
      </#if>
    </#if>  
    
    
   <#else>
    	${getNotCompletedText()}
   </#if>
  </#assign>
  <#if !(legal_section = "") >
     ${legal_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}