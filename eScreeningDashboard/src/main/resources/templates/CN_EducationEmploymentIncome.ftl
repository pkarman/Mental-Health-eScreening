<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
EDUCATION, EMPLOYMENT AND INCOME: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign ed_section>

  	<#if var50?? && var60?? && var100??>
    
    <#-- The Veteran reported completing some high school. -->
    <#if hasValue(getSelectOneDisplayText(var50)) >
      The Veteran reported completing ${getSelectOneDisplayText(var50)}.  <#t>
    </#if> 
    <#-- The Veteran reported being currently an employed, who usually works as retail. -->
    <#if hasValue(getSelectOneDisplayText(var60)) && hasValue(getSelectOneDisplayText(var70)) >
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}, who usually works as an ${getFreeformDisplayText(var70)}.  <#t>
    <#elseif hasValue(getSelectOneDisplayText(var60)) && !(hasValue(getFreeformDisplayText(var70)))>
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}.  <#t>
    <#elseif !(hasValue(getSelectOneDisplayText(var60))) && hasValue(getFreeformDisplayText(var70)) >
      The Veteran reported usually working as an ${getFreeformDisplayText(var70)}.  <#t>
    </#if>
    <#--The Veteran reported that the primary source of income is work, and disability.  -->
    <#if var80?? >
      The Veteran reported that <#t>
      <#if wasAnswerNone(var80)>
        they do not have any income.  <#t>
      <#else>
        their income is derived from ${getSelectMultiDisplayText(var80)}.  <#t>
      </#if> 
    </#if> 
    <#--The Veteran is married.-->
    <#if hasValue(getSelectOneDisplayText(var100)) >
      The Veteran is ${getSelectOneDisplayText(var100)}.  <#t>
    </#if> 
   
   	<#else>
   		${getNotCompletedText()}
   	</#if>
  </#assign>
  <#if !(ed_section = "") >
     ${ed_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}