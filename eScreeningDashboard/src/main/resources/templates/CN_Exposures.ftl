<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
EXPOSURES:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign exposures_section>

	 <#-- var2200: ${var2200}<br><br>  var2230: ${var2230}<br><br>  var2240: ${var2240}<br><br>  var2250: ${var2250}<br><br> var2260: ${var2260}<br><br>  -->
	

	<#if (var2200.children)?? && (var2230.children)?? && (var2240.children)?? && (var2250.children)?? && (var2260.children)?? 
		&& ((var2200.children)?size > 0) && ((var2230.children)?size > 0) && ((var2240.children)?size > 0)  
		&& ((var2250.children)?size > 0) && ((var2260.children)?size > 0) > 
	 
	  	<#if hasValue(var2200)> 	
			The Veteran reported persistent major concerns related to the effects of exposure to ${getSelectMultiDisplayText(var2200)}. ${LINE_BREAK}${LINE_BREAK}
		</#if>
	
		<#assign score = getListScore([var2230,var2240,var2250])>
		<#assign scoreText = "notset">
	
		<#if ((score)??) && (score >= 0) && (score <= 998)>
			<#if (score >= 1) && (score <= 998)>
				<#assign scoreText = "being exposed">
			</#if>
		<#elseif score == 0>
			<#assign scoreText = "no exposure">
		</#if> 

		The Veteran reported ${scoreText} to animal contact during a deployment in the past 18 months that could result in rabies. ${LINE_BREAK}${LINE_BREAK}
	
	
		<#if hasValue(var2260)> 	
			<#assign combat = getSelectMultiDisplayText(var2260)>
			<#if combat == "notfound">
				<#assign combatText = "none">
			<#else>
				<#assign combatText = combat>
			</#if>
		</#if>
		The Veteran reported the following types of combat experience: ${combatText}. ${LINE_BREAK}${LINE_BREAK}



	<#else>
		${getNotCompletedText()}
	</#if>
	
  </#assign>
  <#if !(exposures_section = "") >
     ${exposures_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}