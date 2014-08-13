<#include "clinicalnotefunctions"> 
<#-- Template start -->
${TITLE_START}
SERVICE HISTORY TEST FOR SELECT MULTI MATRIX AND SELECT SINGLE MATRIX: 
${TITLE_END}
${SECTION_START}
  <#assign section6>
    <#-- Select multi matrix text -->
    <#if hasValue(getSelectMultiDisplayText(var200)) >
      The Veteran identified ${getSelectMultiDisplayText(var200)} as the presenting concern(s).  <#t>
    </#if>
    <#-- Select single matrix calculation example -->
    ${LINE_BREAK}
    <#if hasValue(getFormulaDisplayText(var510))  >
      Past 4 weeks Pain select one matrix question score was: ${getFormulaDisplayText(var510)}  <#t>
    </#if>
  </#assign>
  <#if !(section6 = '') >
     ${section6}
  <#else>
     ${noParagraphData}
  </#if>
${SECTION_END}