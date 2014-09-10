<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
EDUCATION, EMPLOYMENT AND INCOME: 
${MODULE_TITLE_END}
${MODULE_START}
    <#-- The Veteran reported completing some high school. -->
    <#if hasValue(getSelectOneDisplayText(var50)) >
      The Veteran reported completing ${getSelectOneDisplayText(var50)}. ${NBSP}
    </#if> 
    <#-- The Veteran reported being currently an employed, who usually works as retail. -->
    <#if hasValue(getSelectOneDisplayText(var60)) && hasValue(getSelectOneDisplayText(var70)) >
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}, who usually works as a ${getFreeformDisplayText(var70)}.  ${NBSP}
    <#elseif hasValue(getSelectOneDisplayText(var60)) && !(hasValue(getFreeformDisplayText(var70)))>
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}.  ${NBSP}
    <#elseif !(hasValue(getSelectOneDisplayText(var60))) && hasValue(getFreeformDisplayText(var70)) >
      The Veteran reported usually working as a ${getFreeformDisplayText(var70)}.  ${NBSP}
    </#if>
    <#--The Veteran reported that the primary source of income is work, and disability.  -->
    <#if var80?? >
      The Veteran reported that 
      <#if wasAnswerNone(var80)>
        they do not have any income. ${NBSP}
      <#else>
        their income is derived from ${getSelectMultiDisplayText(var80)}. ${NBSP}
      </#if> 
    </#if> 
    <#--The Veteran is married.-->
    <#if hasValue(getSelectOneDisplayText(var100)) >
      The Veteran is ${getSelectOneDisplayText(var100)}. ${NBSP}
    </#if> 
${MODULE_END}