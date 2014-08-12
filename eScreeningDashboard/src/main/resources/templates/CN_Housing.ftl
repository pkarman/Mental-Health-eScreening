<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
HOUSING: 
${MODULE_TITLE_END}
${MODULE_START}

  <#assign housing_section>
  	<#if var2000?? && var763?? && var761?? && var762?? && var2002?? && var2008??>
    <#if hasValue(getSelectOneDisplayText(var2000))  >
    	<#if isSelectedAnswer(var2000, var763)>
    		The Veteran declined to discuss their current housing situation.<#t>
    	<#else>
			<#if isSelectedAnswer(var2000, var761)>
				The Veteran ${getSelectOneDisplayText(var2000)} been living in stable housing for the last 2 months. <#t>
    		</#if>
			<#if isSelectedAnswer(var2000, var762)>
				The Veteran ${getSelectOneDisplayText(var2001)} concerned about housing in the future. <#t>
			</#if>
    	</#if>
    </#if>
	
    <#if hasValue(getSelectOneDisplayText(var2002)) >
      The Veteran has been living ${getSelectOneDisplayText(var2002)}. <#t>
    </#if>
    <#if hasValue(getSelectOneDisplayText(var2008)) >
      The Veteran ${getSelectOneDisplayText(var2008)} like a referral to talk more about his/her housing concerns. <#t>
    </#if>
    
    <#else>
    	${getNotCompletedText()}
    </#if>
  </#assign>
  <#if !(housing_section = "") >
     ${housing_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}