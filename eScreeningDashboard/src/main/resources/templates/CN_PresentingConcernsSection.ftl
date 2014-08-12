<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
PRESENTING CONCERN(S):
${MODULE_TITLE_END}
${MODULE_START}
  <#assign concerns_section>
	<#if var200?? && var210?? && var220?? && var230?? && var240?? && var250?? && var260?? && var270??>
    <#-- The Veteran identified enrollment, mental health , physical health, establishing a PCP, and vic as the presenting concerns. -->
    <#if hasValue(getSelectMultiDisplayText(var200)) >
      The Veteran identified ${getSelectMultiDisplayText(var200)} as the presenting concern(s).  <#t>
					${LINE_BREAK}
					${LINE_BREAK}
	</#if>
    
	<#assign fragments = []>
	<#assign healthCareText = "">
	<#assign vaBeneifitsText = "">
	<#assign employmentText = "">
	<#assign financialInfoText = "">
	<#assign SocialText = "">
	<#assign legalText = "">
	<#assign housingText = "">
	<#assign otherText = "">

	<#if hasValue(var210) >      
		<#assign healthCareText = "Healthcare (specifically, " + getSelectMultiDisplayText(var210) + ")">
		<#assign fragments = fragments + [healthCareText] >
	</#if>

	<#if hasValue(var250) >      
		<#assign vaBeneifitsText = "VA Benefits (specifically, " + getSelectMultiDisplayText(var250) + ")" >
		<#assign fragments = fragments + [vaBeneifitsText] >
	</#if>

	<#if hasValue(var220) >      
		<#assign employmentText = "Employment (specifically, " + getSelectMultiDisplayText(var220) + ")">
		<#assign fragments = fragments + [employmentText] >
	</#if>

	<#if hasValue(var230) >      
		<#assign SocialText = "Social (specifically, " + getSelectMultiDisplayText(var230) + ")">
		<#assign fragments = fragments + [SocialText]  >
	</#if>

	<#if hasValue(var270) >      
		<#assign legalText = "Legal (specifically, " + getSelectMultiDisplayText(var270) + ")" >
		<#assign fragments = fragments + [legalText]  >
	</#if>

	<#if hasValue(var240) >      
		<#assign housingText = "Housing (specifically, " + getSelectMultiDisplayText(var240) + ")">
		<#assign fragments = fragments + [housingText] >
	</#if>

	
  	The Veteran indicated that he/she would like information or assistance with the following: ${createSentence(fragments)}.
    
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(concerns_section = "") >
     ${concerns_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}