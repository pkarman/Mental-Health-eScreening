<#include "clinicalnotefunctions"> 
${MODULE_TITLE_START}
ID Screen
${MODULE_TITLE_END}
${MODULE_START}
  <#assign id_section>
    <#if hasValue(getSelectMultiDisplayText(var300)) >
      The Veteran reported the following legal concern(s): ${getSelectMultiDisplayText(var300)}.  <#t>
    </#if>
    <#if hasValue(getSelectOneDisplayText(var320)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var320), 'yes') >
      	The Veteran reported having an Advance Directive.  <#t>
      </#if>
    </#if>
    <#if hasValue(getSelectOneDisplayText(var330)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var330), 'yes') >
      	The Veteran would like information about an Advance Directive.  <#t>
      </#if>
    </#if>       
  </#assign>
  <#if !(id_section = '') >
     ${id_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}