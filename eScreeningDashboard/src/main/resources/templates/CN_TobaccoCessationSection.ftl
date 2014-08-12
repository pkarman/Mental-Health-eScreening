<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
TOBACCO: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign tobacco_section>
  	<#if var600?? >
  	<#-- The Veteran endorsed using cigarettes.  --> 
    <#if hasValue(getSelectOneDisplayText(var600)) >
    
      <#if isSelectedAnswer(var600, var602) >
      	The Veteran ${getSelectOneDisplayText(var600)}. <#t>
      <#elseif isSelectedAnswer(var600, var601) >
      	The Veteran ${getSelectOneDisplayText(var600)} ${getSelectMultiDisplayText(var620)}.<#t>
      <#elseif isSelectedAnswer(var600, var603) >>
      	The Veteran ${getSelectOneDisplayText(var600)} ${getSelectMultiDisplayText(var620)} ${getSelectOneDisplayText(var610)}.  <#t>
      </#if>
	</#if>
	
  	<#else>
  		${getNotCompletedText()}
  	</#if>
  </#assign>
  <#if !(tobacco_section = "") >
     ${tobacco_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}