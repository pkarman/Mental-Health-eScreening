<#include "clinicalnotefunctions"> 
<#-- Template start -->
${SECTION_START}
  <#assign section1>
    <#if hasValue(getCustomVariableDisplayText(var1)) >
      The Veteran presented to enroll in the VA Healthcare System and consented to be screened using the electronic version v${getCustomVariableDisplayText(var1)} of the Post-911 Screening Packet.  <#t>   
    </#if> 
    <#if hasValue(getCustomVariableDisplayText(var2)) >
      The eScreening was administered by ${getCustomVariableDisplayText(var2)}.  <#t> 
    </#if>
    <#if hasValue(getCustomVariableDisplayText(var3)) >
      The results were used to auto-generate a CPRS note and reviewed by ${getCustomVariableDisplayText(var3)}.  <#t> 
    </#if>
  </#assign>
  <#if !(section1 = '') >
     ${section1}
  <#else>
     ${noParagraphData}
  </#if>
${SECTION_END}