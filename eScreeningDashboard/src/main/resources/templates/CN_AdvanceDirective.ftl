<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
ADVANCE DIRECTIVE:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign advance_dir_section>
  	<#if var800?? && var810?? && var820??>
    <#if hasValue(getSelectMultiDisplayText(var800)) >
    	<#-- In what language do you prefer to get your health information? -->
      The Veteran reported they preferred to get your health information in ${getSelectMultiDisplayText(var800)}.  <#t>
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var810)) >
      <#-- Do you have and Advance Directive or Durable Power of Attorney for Healthcare?  -->
      The Veteran reported ${getSelectMultiDisplayText(var810)} an Advance Directive or Durable Power of Attorney for Healthcare.  <#t>
    </#if>
     <#if hasValue(getSelectMultiDisplayText(var820)) >
      	<#-- If no, would like information about this document? -->
      	The Veteran ${getSelectMultiDisplayText(var820)} like information about an Advance Directive.  <#t>
    </#if>     
    
    <#else>
    	${getNotCompletedText()}
    </#if>
    
  </#assign>
  <#if !(advance_dir_section = "") >
     ${advance_dir_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}