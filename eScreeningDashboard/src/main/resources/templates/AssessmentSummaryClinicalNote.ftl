<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_START}
  <#assign section1>
    <#if var1?? >
      The Veteran presented to enroll in the VA Healthcare System and consented to be screened using the electronic version v${getCustomVariableDisplayText(var1)} of the Post-911 Screening Packet.  <#t>   
    </#if> 
    <#if var2?? >
      The eScreening was administered by ${getCustomVariableDisplayText(var2)}.  <#t> 
    </#if>
    <#if var3?? >
      The results were used to auto-generate a CPRS note and reviewed by ${getCustomVariableDisplayText(var3)}.  <#t> 
    </#if>
  </#assign>
  <#if !(section1 = '') >
     ${section1}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
${MODULE_TITLE_START}
PRESENTING CONCERN(s):
${MODULE_TITLE_END}
${MODULE_START}
  <#assign section2>
    <#if var10000?? >
      SHOULD NEVER GET HERE.  <#t>
    </#if> 
  </#assign>
  <#if !(section2 = '') >
     ${section2}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
${MODULE_TITLE_START}
DEMOGRAPHICS and SOCIAL: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign section3>
    <#-- The Veteran is a 28 year-old hispanic. -->
    <#if var10??>
      The Veteran is a ${getFormulaDisplayText(var10)} year-old<#t>
        <#if !(var30??) || wasAnswerNone(var30)>
          .  <#t>
        <#else>
          ${""?left_pad(1)}whom is ${getSelectOneDisplayText(var30)}.  <#t> 
        </#if> 
    </#if> 
    <#-- The Veteran reports being a White/Caucasian, American Indian or Alaskan Native male. -->
    <#if var40?? || var20??>
      The Veteran reports being<#t>
      <#if var40??>
        ${""?left_pad(1)}a ${getSelectMultiDisplayText(var40)}<#t>
      </#if> 
      <#if var20??>
        ${""?left_pad(1)}${getSelectOneDisplayText(var20)}<#t>
      </#if> 
      .  <#t>
    </#if> 
    <#-- The Veteran reported completing some high school. -->
    <#if var50?? >
      The Veteran reported completing ${getSelectOneDisplayText(var50)}.  <#t>
    </#if> 
    <#-- The Veteran reported being currently an employed, who usually works as retail. -->
    <#if var60?? && var70??>
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}, who usually works as an ${getAnswerDisplayText(var70)}.  <#t>
    <#elseif var60?? && !(var70??)>
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}.  <#t>
    <#elseif !(var60??) && var70??>
      The Veteran reported usually working as an ${getAnswerDisplayText(var70)}.  <#t>
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
    <#if var100?? >
      The Veteran is ${getSelectOneDisplayText(var100)}.  <#t>
    </#if> 
    <#--The Veteran lives in a house with spouse or partner, and children.-->
    <#if var110?? && var90??>
      The Veteran ${getSelectOneDisplayText(var110)} <#t>  
      <#if wasAnswerNone(var90)>
        alone.  <#t>
      <#else> 
        with ${getSelectMultiDisplayText(var90)}.  <#t>
      </#if>
    <#elseif var110?? && !(var90??)>
      The Veteran ${getSelectOneDisplayText(var110)}.  <#t>
    <#elseif !(var110??) && var90??>
      The Veteran lives with ${getSelectMultiDisplayText(var90)}.  <#t>
    </#if>
    <#--The Veteran reported not having any children.-->
    <#if var131??>
      <#if wasAnswerTrue(var131)>
        ${getAnswerDisplayText(var131)}  <#t>
      <#else>
        <#--The Veteran has 2 child(ren) who are child(ren) are 2,4 years old.-->
        <#if var130??>
          <#if (getNumberOfTableResponseRows(var130) < 1) >
            The Veteran reported not having any children.  <#t>
          <#elseif (getNumberOfTableResponseRows(var130) = 1) >
            The Veteran has 1 child who is ${getTableMeasureDisplayText(var130)} years old.  <#t>
          <#elseif (getNumberOfTableResponseRows(var130) > 1) >
            The Veteran has ${getNumberOfTableResponseRows(var130)} children who are ${getTableMeasureDisplayText(var130)} years old.  <#t>
          </#if>
        </#if>        
      </#if>
    </#if>
    <#--The Veteran reported BMI is 27, indicating that he/she may be is overweight.-->
    <#if var11??>
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
  </#assign>
  <#if !(section3 = '') >
     ${section3}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
${MODULE_TITLE_START}
LEGAL: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign section4>
    <#if var10000?? >
      SHOULD NEVER GET HERE.  <#t>
    </#if> 
  </#assign>
  <#if !(section4 = '') >
     ${section4}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
${MODULE_TITLE_START}
SPIRITUAL HEALTH: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign section5>
    <#if var10000?? >
      SHOULD NEVER GET HERE.  <#t>
    </#if> 
  </#assign>
  <#if !(section5 = '') >
     ${section5}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
