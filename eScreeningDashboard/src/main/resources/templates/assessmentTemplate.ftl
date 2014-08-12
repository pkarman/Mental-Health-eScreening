<#-- Variables -->
<#assign noParagraphData='The required questions have not been answered to generate this section.' />

<#-- functions -->
<#function getVariableDisplayText variableObj='notset'>
  <#if variableObj = 'notset'>
    <#-- The object was not found -->
    <#return ''>
  <#else>
    <#-- If there is an other value use that before anything else -->
    <#if variableObj.otherText?? && !(variableObj.otherText='') >
      <#return variableObj.otherText >
    </#if>
    <#-- If there is an override value use that before the default value -->
    <#if variableObj.overrideText?? && !(variableObj.overrideText='') >
      <#return variableObj.overrideText >
    </#if>
    <#-- Use the default text -->
    <#if variableObj.displayText?? && !(variableObj.displayText='') >
      <#return variableObj.displayText >
    </#if>
    <#-- The object does not have text to return -->
    <#return '' >
  </#if>
</#function>

<#function getDisplayTextIfTrue variableObj='notset'>
  <#if variableObj = 'notset'>
    <#-- The object was not found -->
    <#return ''>
  <#else>
    <#if variableObj.value?? && !(variableObj.value='') && variableObj.value='true'>
      ${getVariableDisplayText(variableObj)} 
    <#else>
       <#-- The value was either not provided or it was false -->
      <#return ''>
    </#if>
  </#if>
</#function>

<#function buildSentenceFragmentForChildrenBoolean variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || variableObj.children?size=0 >
    <#-- The object was not found or there was a problem with the list -->
    <#return ''>
  <#else>
    <#assign sentenceFragement>
      <#compress>
        <#-- Filter the list by true values -->
        <#local filteredListByTrueValue = []>
        <#list variableObj.children as y>
          <#if y.value='true'>
              <#local filteredListByTrueValue = filteredListByTrueValue + [y]>
          </#if>
        </#list>
    
        <#list filteredListByTrueValue as x>
          <#if filteredListByTrueValue?size = 1>
            ${getDisplayTextIfTrue(x)}
          <#else>
            <#if !(x_has_next) >and </#if>${getDisplayTextIfTrue(x)}<#if x_has_next>, </#if><#t>
          </#if>
        </#list>
      </#compress>
    </#assign>
    <#return sentenceFragement>
  </#if>
</#function>

<#function buildSentenceFragmentForChildrenText variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || variableObj.children?size=0 >
    <#-- The object was not found or there was a problem with the list -->
    <#return ''>
  <#else>
    <#assign sentenceFragement>
      <#compress>
        <#list variableObj.children as x>
          <#if variableObj.children?size = 1>
            ${getVariableDisplayText(x)}
          <#else>
            <#if !(x_has_next) >and </#if>${getVariableDisplayText(x)}<#if x_has_next>, </#if><#t>
          </#if>
        </#list>
      </#compress>
    </#assign>
    <#return sentenceFragement>
  </#if>
</#function>

<#function buildTextForFirstTrueChild variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || variableObj.children?size=0 >
    <#-- The object was not found or there was a problem with the list -->
    <#return 'notfound'>
  <#else>
     <#list variableObj.children as x>
       <#if !(x.value='') && x.value='true'>
	 <#return getDisplayTextIfTrue(x) >
       </#if>
     </#list>
     <#return ''>
  </#if>
</#function>

<#function isFirstTrueChildNone variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || variableObj.children?size=0 >
    <#-- The object was not found or there was a problem with the list -->
    <#return false>
  <#else>
     <#list variableObj.children as x>
       <#if x.value?? && x.value='true' && x.type?? && x.type='none'>
	 <#return true>
       </#if>
     </#list>
     <#return false>
  </#if>
</#function>

<#function isValueTrue variableObj='notset'>
  <#if variableObj = 'notset'>
    <#-- The object was not found -->
    <#return false>
  <#else>
    <#if variableObj.value?? && !(variableObj.value='') && variableObj.value='true'>
      <#return true>
    <#else>
      <#return false>
    </#if>
  </#if>
</#function>

<#function getNumberOfChildren variableObj='notset'>
  <#if variableObj = 'notset'>
    <#-- The object was not found -->
    <#return 0>
  <#else>
    <#if variableObj.children?? >
      <#return variableObj.children?size>
    <#else>
      <#return 0>
    </#if>
  </#if>
</#function>


<#-- Template start -->
<div>Introduction Section:</div>
<div> 
  <#assign section1>
    <#if var1?? >
      The Veteran presented to enroll in the VA Healthcare System and consented to be screened using the electronic version v${getVariableDisplayText(var1)} of the Post-911 Screening Packet.  <#t>   
    </#if> 
    <#if var2?? >
      The eScreening was administered by ${getVariableDisplayText(var2)}.  <#t> 
    </#if>
    <#if var3?? >
      The results were used to auto-generate a CPRS note and reviewed by ${getVariableDisplayText(var3)}.  <#t> 
    </#if>
  </#assign>
  <#if !(section1 = '') >
     ${section1}
  <#else>
     ${noParagraphData}
  </#if>
</div>

<div>DEMOGRAPHICS and SOCIAL:</div>
<div>
  <#assign section2>
    <#-- The Veteran is a 28 year-old hispanic. -->
    <#if var10??>
      The Veteran is a ${getVariableDisplayText(var10)} year-old<#t>
        <#if !(var30??) || isFirstTrueChildNone(var30)>
          .  <#t>
        <#else>
          ${""?left_pad(1)}whom is ${buildTextForFirstTrueChild(var30)}.  <#t> 
        </#if> 
    </#if> 
    <#-- The Veteran reports being a White/Caucasian, American Indian or Alaskan Native male. -->
    <#if var40?? || var20??>
      The Veteran reports being<#t>
      <#if var40??>
        ${""?left_pad(1)}a ${buildSentenceFragmentForChildrenBoolean(var40)}<#t>
      </#if> 
      <#if var20??>
        ${""?left_pad(1)}${buildTextForFirstTrueChild(var20)}<#t>
      </#if> 
      .  <#t>
    </#if> 
    <#-- The Veteran reported completing some high school. -->
    <#if var50?? >
      The Veteran reported completing ${buildTextForFirstTrueChild(var50)}.  <#t>
    </#if> 
    <#-- The Veteran reported being currently an employed, who usually works as retail. -->
    <#if var60?? && var70??>
      The Veteran reported being currently ${buildTextForFirstTrueChild(var60)}, who usually works as an ${getVariableDisplayText(var70)}.  <#t>
    <#elseif var60?? && !(var70??)>
      The Veteran reported being currently ${buildTextForFirstTrueChild(var60)}.  <#t>
    <#elseif !(var60??) && var70??>
      The Veteran reported usually working as an ${getVariableDisplayText(var70)}.  <#t>
    </#if>
    <#--The Veteran reported that the primary source of income is work, and disability.  -->
    <#if var80?? >
      The Veteran reported that <#t>
      <#if isFirstTrueChildNone(var80)>
        they do not have any income.  <#t>
      <#else>
        their income is derived from ${buildSentenceFragmentForChildrenBoolean(var80)}.  <#t>
      </#if> 
    </#if> 
    <#--The Veteran is married.-->
    <#if var100?? >
      The Veteran is ${buildTextForFirstTrueChild(var100)}.  <#t>
    </#if> 
    <#--The Veteran lives in a house with spouse or partner, and children.-->
    <#if var110?? && var90??>
      The Veteran ${buildTextForFirstTrueChild(var110)} <#t>  
      <#if isFirstTrueChildNone(var90)>
        alone.  <#t>
      <#else> 
        with ${buildSentenceFragmentForChildrenBoolean(var90)}.  <#t>
      </#if>
    <#elseif var110?? && !(var90??)>
      The Veteran ${buildTextForFirstTrueChild(var110)}.  <#t>
    <#elseif !(var110??) && var90??>
      The Veteran lives with ${buildSentenceFragmentForChildrenBoolean(var90)}.  <#t>
    </#if>
    <#--The Veteran reported not having any children.-->
    <#if var131??>
      <#if isValueTrue(var131)>
        The Veteran reported not having any children.  <#t>
      </#if>
    </#if>
    <#--The Veteran has 2 child(ren) who are child(ren) are 2,4 years old.-->
    <#if var130??>
      <#if (getNumberOfChildren(var130) < 1) >
        The Veteran reported not having any children.  <#t>
      <#elseif (getNumberOfChildren(var130) = 1) >
        The Veteran has 1 child who is ${buildSentenceFragmentForChildrenText(var130)} years old.  <#t>
      <#elseif (getNumberOfChildren(var130) > 1) >
        The Veteran has ${getNumberOfChildren(var130)} children who are ${buildSentenceFragmentForChildrenText(var130)} years old.  <#t>
      </#if>
    </#if>
    <#--The Veteran reported BMI is 27, indicating that he/she may be is overweight.-->
    <#if var140??>
      <#assign num = getVariableDisplayText(var140)?number />
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
  <#if !(section2 = '') >
     ${section2}
  <#else>
     ${noParagraphData}
  </#if>
</div>

<div>SPIRITUAL HEALTH:</div>
<div />