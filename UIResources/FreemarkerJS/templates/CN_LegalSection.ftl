<#include "clinicalnotefunctions"> 
<#-- Template start -->
${TITLE_START}
LEGAL: 
${TITLE_END}
${SECTION_START}
  <#assign section4>
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
  <#if !(section4 = '') >
     ${section4}
  <#else>
     ${noParagraphData}
  </#if>
${SECTION_END}