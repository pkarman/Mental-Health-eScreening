<#include "clinicalnotefunctions"> 
<#-- Template start -->
${TITLE_START}
PRESENTING CONCERN(s):
${TITLE_END}
${SECTION_START}
  <#assign section2>
    <#-- The Veteran identified enrollment, mental health , physical health, establishing a PCP, and vic as the presenting concerns. -->
    <#if hasValue(getSelectMultiDisplayText(var200)) >
      The Veteran identified ${getSelectMultiDisplayText(var200)} as the presenting concern(s).  <#t>
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var210)) >
      The Veteran indicated that he/she would like healthcare information or assistance with ${getSelectMultiDisplayText(var210)}.  <#t>
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var220)) >
      The Veteran indicated that he/she would like employment information or assistance with ${getSelectMultiDisplayText(var220)}.  <#t>
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var230)) >
      The Veteran indicated that he/she would like social information or assistance with ${getSelectMultiDisplayText(var230)}.  <#t>
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var240)) >
      The Veteran indicated that he/she would like housing information or assistance with ${getSelectMultiDisplayText(var240)}.  <#t>
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var250)) >
      The Veteran indicated that he/she would like VA benefits information or assistance with ${getSelectMultiDisplayText(var250)}.  <#t>
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var260)) >
      The Veteran indicated that he/she would like information or assistance with ${getSelectMultiDisplayText(var260)}.  <#t>
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var270)) >
      The Veteran indicated that he/she would like legal resources information or assistance with ${getSelectMultiDisplayText(var270)}.  <#t>
    </#if>
  </#assign>
  <#if !(section2 = '') >
     ${section2}
  <#else>
     ${noParagraphData}
  </#if>
${SECTION_END}