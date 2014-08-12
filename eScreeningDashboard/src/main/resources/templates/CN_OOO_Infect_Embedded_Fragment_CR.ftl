<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
OOO INFECT & EMBEDDED FRAGMENT CR:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign fragment_section>
	  <#t>
	
	<#if var2500?? && var2501?? && var2502?? && var2009??>  
	<#-- ${createSentence(parts)}. -->
	<#assign questions = [var2500,var2501,var2502,var2009]>
	<#assign textHash = {"var2500": "chronic diarrhea or other gastrointestinal", 
							"var2501": "pain, unexplained fevers", 
							"var2502": "persistent popular or nodular skin rash that began after deployment to Southwest Asia", 
							"var2009": "suspects that he/she has retained fragments or shrapnel as a result of injuries"}>
	
	<#assign parts = []>
		
	<#list questions as q >
		<#assign child = q.children[0] >
		<#if ((child.calculationValue?number) > 0)>
			<#assign problem = textHash[q.key] >
			<#assign part = problem + ", with symptoms of " + getVariableDisplayText(child) >
			<#assign parts = parts + [part] >
		</#if>
	</#list>
	
	<#if parts?has_content>
		The Veteran endorsed other health problems of: ${createSentence(parts)}.
	<#else>
		${getAnsweredNoAllText("OOO Infect & Embedded Fragment CR")}.
	</#if>
	<#else>
		${getNotCompletedText()}
	</#if>

  </#assign>
  <#if !(fragment_section = "") >
     ${fragment_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}