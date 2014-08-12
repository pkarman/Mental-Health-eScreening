<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
SPIRITUAL HEALTH: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign spiritual_section>
  
  	<#if var400?? && var410?? && var420?? && var430??>
  	<#-- The Veteran reported that spirituality is important.  --> 
    <#if hasValue(getSelectOneDisplayText(var400)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var400), "yes") >
      	The Veteran reported that spirituality is important.  <#t>
      <#else>
      	The Veteran reported that spirituality is not important.  <#t>
      </#if>
    </#if>
  	<#-- The patient finds that prayer, meditation, fellowship, positive energy, and walks is helpful when dealing with problems. -->
  	<#if hasValue(getSelectMultiDisplayText(var410)) >
      The patient finds that ${getSelectMultiDisplayText(var410)} helps when dealing with problems.  <#t>
    </#if>
  	<#-- The Veteran attends the following formal religious or spiritual organization: church, and synagogue. -->
  	<#if hasValue(getSelectMultiDisplayText(var420)) >
      The Veteran attends the following formal religious or spiritual organization(s): ${getSelectMultiDisplayText(var420)}.  <#t>
    </#if>
  	<#-- The Veteran requests a referral to see a chaplain at the time of screening. -->
  	<#if hasValue(getSelectOneDisplayText(var430)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var430), "yes") >
      	The Veteran requests a referral to see a chaplain at the time of screening.  <#t>
      </#if>
    </#if>
  	<#-- The Veteran feels that combat or military service affected his/her religious views in the following way: god. -->
  	<#if hasValue(getSelectOneDisplayText(var440)) >
  	  <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var440), "no") >
  	    The Veteran feels that combat or military service did not affect his/her religious views.  <#t>
      <#elseif doesValueOneEqualValueTwo(getSelectOneDisplayText(var440), "does not know") >
        The Veteran does not know how combat or military service has affected his/her religious views.  <#t>
      <#else>
      	The Veteran feels that combat or military service affected his/her religious views in the following way: ${getSelectOneDisplayText(var440)}.  <#t> 
      </#if>
    </#if>  	
    
    <#else>
    	${getNotCompletedText()}
    </#if>
  </#assign>
  <#if !(spiritual_section = "") >
     ${spiritual_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}