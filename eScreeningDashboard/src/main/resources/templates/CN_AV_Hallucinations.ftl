<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
A/V HALLUCINATIONS:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign av_hallucinations_section>
	  <#t>
	
	<#if var1350?? && var1360??>  
	<#--  -->
	<#assign fragments = []>
	<#assign text =""> 
	<#if ((var1350)?? && (getScore(var1350) > 0))> 
		<#assign fragments = fragments + ["hearing things other people can\'t hear"]>
	</#if>

	<#if ((var1360)?? && (getScore(var1360) > 0))> 
		<#assign fragments = fragments + ["seeing things or having visions other people can\'t see"]>
	</#if> 
	
	<#if fragments?has_content>
		<#assign text = text + createSentence(fragments)>
	    The Veteran reported ${text}. <#t>
	</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(av_hallucinations_section = "") >
     ${av_hallucinations_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}