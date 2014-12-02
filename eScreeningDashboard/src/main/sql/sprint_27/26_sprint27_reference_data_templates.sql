update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Screen for Infectious Disease and Embedded Fragments:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var2500.children)?? && ((var2500.children)?size > 0) || ((var2501.children)?? && ((var2501.children)?size > 0))
	 || ((var2502.children)?? && ((var2502.children)?size > 0)) || ((var2009.children)?? && ((var2009.children)?size > 0))>

	<#-- ${createSentence(parts)}. -->
	<#assign questions = [var2500,var2501,var2502,var2009]>
	<#assign textHash = {"var2500": "chronic diarrhea or other gastrointestinal", 
							"var2501": "pain, unexplained fevers", 
							"var2502": "persistent papular or nodular skin rash that began after deployment to Southwest Asia", 
							"var2009": "suspects that he/she has retained fragments or shrapnel as a result of injuries"}>
	
	<#assign parts = []>
		
	<#list questions as q >
		<#if q.children?? && ((q.children)?size>0)>
			<#assign child = q.children[0] >
			<#if ((child.calculationValue?number) > 0)>
				<#assign problem = textHash[q.key] >
				<#assign part = problem + ", with symptoms of " + getVariableDisplayText(child) >
				<#assign parts = parts + [part] >
			</#if>
		</#if>
	</#list>
	
	<#if parts?has_content>
		The Veteran endorsed other health problems of: ${createSentence(parts)}. ${NBSP}
	<#else>
		${getAnsweredNoAllText("OOO Infect & Embedded Fragment CR")}. ${NBSP}
	</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 19;