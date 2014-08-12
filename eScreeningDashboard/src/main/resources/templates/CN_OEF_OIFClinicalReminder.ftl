<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
OEF/OIF CLINICAL REMINDER:

${MODULE_TITLE_END}
${MODULE_START}
  <#assign mst_section>
	<#if var660?? >
	  <#if hasValue(getSelectMultiDisplayText(var660)) >
    	<#if isSelectedAnswer(var660, var661)> <#-- no -->
			<#assign text = "The Veteran\'s most recent deployment was not for Operation Enduring Freedom (OEF) or Operation Iraqi Freedom (OIF)">  	<#t>
    		
		<#elseif isSelectedAnswer(var660, var659)><#-- yes -->
    			<#assign text = "The Veteran\'s most recent deployment was for " + getSelectOneDisplayText(var660) + " in " + getSelectOneDisplayText(var683)> <#t>	
    		
		<#elseif isSelectedAnswer(var660, var662)><#-- yes -->
    		<#assign text = "The Veteran\'s most recent deployment was for " + getSelectOneDisplayText(var660) + " in " + getSelectOneDisplayText(var663)> <#t>
    	</#if>
    </#if>
     
     ${text}.
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(mst_section = "") >
     ${mst_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}