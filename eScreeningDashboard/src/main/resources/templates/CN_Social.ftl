<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
SOCIAL: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign social_section>
	<#if var90?? && var130??  >
    <#--The Veteran lives in a house with spouse or partner, and children.-->
    <#if hasValue(getSelectOneDisplayText(var110)) && hasValue(getSelectMultiDisplayText(var90)) >
      The Veteran ${getSelectOneDisplayText(var110)} <#t>  
      <#if wasAnswerNone(var90)>
        alone.  <#t>
      <#else> 
        with ${getSelectMultiDisplayText(var90)}.  <#t>
      </#if>
    <#elseif hasValue(getSelectOneDisplayText(var110)) && !(hasValue(getSelectMultiDisplayText(var90)))>
      The Veteran ${getSelectOneDisplayText(var110)}.  <#t>
    <#elseif !(hasValue(getSelectOneDisplayText(var110))) && hasValue(getSelectMultiDisplayText(var90)) >
      The Veteran lives with ${getSelectMultiDisplayText(var90)}.  <#t>
    </#if>
    <#--The Veteran reported not having any children.-->
    <#if hasValue(getAnswerDisplayText(var131)) >
      <#if wasAnswerTrue(var131) >
        ${getAnswerDisplayText(var131)}  <#t>
      <#else>
        <#--The Veteran has 2 child(ren) who are child(ren) are 2,4 years old.-->
        <#if !(getNumberOfTableResponseRows(var130) = -1) >
          <#if (getNumberOfTableResponseRows(var130) = 0) >
            The Veteran reported not having any children.  <#t>
          <#elseif (getNumberOfTableResponseRows(var130) = 1) >
            The Veteran has 1 child who is ${getTableMeasureDisplayText(var130)} years old.  <#t>
          <#elseif (getNumberOfTableResponseRows(var130) > 1) >
            The Veteran has ${getNumberOfTableResponseRows(var130)} children who are ${getTableMeasureDisplayText(var130)} years old.  <#t>
          </#if>
        </#if>
      </#if>
    </#if>
    
    <#if hasValue(getSelectMultiDisplayText(var160)) >
    	Source(s) of support is/are : ${getSelectMultiDisplayText(var160)}.<#t>
    </#if>
    
    <#--The Veteran indicated a history of physical violence .-->
    <#if hasValue(getSelectOneDisplayText(var150)) >
    	The Veteran ${getSelectOneDisplayText(var150)} a history of physical violence or intimidation in a current relationship.<#t>
    </#if>
   
    <#else>
    	${getNotCompletedText()}
    </#if>
    
    
  </#assign>
  <#if !(social_section = "") >
     ${social_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}