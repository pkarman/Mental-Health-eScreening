<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
DRUGS:
${LINE_BREAK}
${MODULE_TITLE_END}
${MODULE_START}
  <#assign drugs_section>
	  <#t>
	<#if var1000?? && var1001?? &&  var1002?? && var1003?? && var1004?? && var1005?? && var1006?? && var1007?? && var1008?? && var1009??>  
	<#-- During the past 4 weeks, how much have you been bothered by any of the following problems -->
	<#-- this is almost identical to Other Health Symptoms -->
	
	<#-- <#assign score = getListScore([var1240,var1250,var1260,var1270,var1280,var1290,var1300,var1310,var1320,var1330])>  -->
	<#assign score = getListScore([var1000,var1001,var1002,var1003,var1004,var1005,var1006,var1007,var1008,var1009])>
	<#assign status ="notset"> 
	<#assign statusText ="notset"> 
	<#assign gender = "">
	
	<#if score?has_content && ((score?number) == 0)> 
		<#assign status ="negative">
		<#assign statusText ="no problems">
	<#elseif (score)?has_content && ((score?number) >= 1) && ((score?number) <= 2)> 
		<#assign status ="negative">
		<#assign statusText ="a low level of problems">
	<#elseif (score)?has_content && ((score?number) >= 3) && ((score?number) <= 5)> 
		<#assign status ="positive">
		<#assign statusText ="a moderate level of problems">
	<#elseif (score)?has_content && ((score?number) >= 6) && ((score?number) <= 8)>
		<#assign status ="positive">
		<#assign statusText ="a substantial level of problems">
	<#elseif (score)?has_content && ((score?number) >= 9) && ((score?number) <= 10)>
		<#assign status ="positive">
		<#assign statusText ="a severe level of problems">
	</#if> 
	
    The Veteran\'s Drug screen was ${status} with ${statusText} reported. <#t>
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(drugs_section = "") >
     ${drugs_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}