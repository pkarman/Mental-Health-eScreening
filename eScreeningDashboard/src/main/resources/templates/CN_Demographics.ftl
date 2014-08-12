<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
DEMOGRAPHICS: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign demo_section>
  	<#if var12?? && var30?? && var40?? && var20??>
    <#-- The Veteran is a 28 year-old hispanic. -->
    <#if hasValue(getFormulaDisplayText(var12)) >
      The Veteran is a ${getFormulaDisplayText(var12)} year-old<#t>
        <#if !(hasValue(getSelectOneDisplayText(var30))) || wasAnswerNone(var30)>
          .  <#t>
        <#else>
          ${""?left_pad(1)}whom is ${getSelectOneDisplayText(var30)}.  <#t> 
        </#if> 
    </#if> 
    <#-- The Veteran reports being a White/Caucasian, American Indian or Alaskan Native male. -->
    <#if hasValue(getSelectMultiDisplayText(var40)) || hasValue(getSelectOneDisplayText(var20)) >
      The Veteran reports being<#t>
      <#if hasValue(getSelectMultiDisplayText(var40)) >
        ${""?left_pad(1)}a ${getSelectMultiDisplayText(var40)}<#t>
      </#if> 
      <#if hasValue(getSelectOneDisplayText(var20)) >
        ${""?left_pad(1)}${getSelectOneDisplayText(var20)}<#t>
      </#if> 
      .  <#t>
    </#if> 
   
    <#--The Veteran reported BMI is 27, indicating that he/she may be is overweight.-->
    <#if hasValue(getFormulaDisplayText(var11))  >
      <#assign num = getFormulaDisplayText(var11)?number />
      The Veteran\'s reported BMI is ${num}, indicating that he/she <#t>
      <#if (num < 18.5) >
        is below a normal a weight.  <#t>
      <#elseif (num < 25) && (num >= 18.5) >
        is at a normal weight.  <#t>
      <#elseif (num < 30) && (num >= 25) >
        is overweight.  <#t>
      <#elseif (num < 40) && (num >= 30) >
        is obese.  <#t>
      <#elseif (num >= 40) >
        is morbid obese.  <#t>
      </#if>
    </#if>
    
    <#else>
    	${getNotCompletedText()}
    </#if>>
  </#assign>
  <#if !(demo_section = "") >
     ${demo_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}