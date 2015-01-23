UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SPIRITUAL HEALTH: 
${MODULE_TITLE_END}
${MODULE_START}
  	<#-- The Veteran reported that spirituality is important.  --> 
  	<#if (var400.children)?? || ((var420.children)?? && ((var420.children)?size > 0)) || ((var430.children)?? && ((var430.children)?size > 0)) || ((var440.children)?? && ((var440.children)?size > 0))>
	<#if (var400.children)?? && ((var400.children)?size > 0) && hasValue(getSelectOneDisplayText(var400)) >
		<#if isSelectedAnswer(var400, var402)>
		  The Veteran reported that spirituality and/or religion is important to him/her.  ${LINE_BREAK}
		 <#else>
		  The Veteran reported that spirituality and/or religion is not important to him/her.  ${LINE_BREAK}
		  </#if>
     </#if>

	<#if (var420.children)?? && ((var420.children)?size > 0)>
	  <#if isSelectedAnswer(var420,var423)>
		<#assign text2 = "is not connected to a faith community but would like to be part of one">
	  <#else>
		<#assign text2 = getSelectOneDisplayText(var420) + " connected to a faith community">
	  </#if>	
	  The Veteran ${text2} 
	</#if>
	
	<#if (var430.children)?? && ((var430.children)?size > 0)>
		<#if isSelectedAnswer(var430, var432)>
	  ${NBSP}and requests a referral to see a chaplain at the time of screening. ${LINE_BREAK}
	   <#else>
	   ${NBSP}and declines a referral to see a chaplain at the time of screening. ${LINE_BREAK}
	   </#if>
	<#else>
		.${LINE_BREAK}
	</#if>
	
	<#-- The Veteran feels that combat or military service affected his/her religious views in the following way: god. -->
  	<#if (var440.children)?? && ((var440.children)?size > 0) && hasValue(getSelectOneDisplayText(var440)) >
		<#if isSelectedAnswer(var440,var443)>
			The Veteran does not know how combat or military service has affected his/her religious views.  ${NBSP}
		<#else>
			The Veteran feels that combat or military service ${getSelectOneDisplayText(var440)} have an effect on his/her religious views.${NBSP}
		</#if>
    </#if>  	
    <#else>
    	${getNotCompletedText()}
    </#if>
${MODULE_END}'
where template_id = 11;


UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
TOBACCO: 
${MODULE_TITLE_END}
${MODULE_START}
  	<#if (var600.children)?? && ((var600.children)?size > 0)>
	  	<#-- The Veteran endorsed using cigarettes.  --> 
	    <#if hasValue(getSelectOneDisplayText(var600)) >
	    
			<#assign part1 = getSelectOneDisplayText(var600)>
			<#assign part2 = getSelectMultiDisplayText(var620)>
			<#assign part3 = getSelectOneDisplayText(var610)>
	
			<#-- check if set, if not set to empty string for display purposes -->
			<#if !(isSet(part1))>
				<#assign part1 = "">
			</#if>
			<#if !(isSet(part2))>
				<#assign part2 = "">
			</#if>
			<#if !(isSet(part3))>
				<#assign part3 = "">
			</#if>
	
			<#assign nextLine = "">
			<#if isSelectedAnswer(var600,var601)>
				The Veteran ${part1}.
				<#assign nextLine = "${LINE_BREAK}">
			</#if>
			<#if isSelectedAnswer(var600,var602)>
				${nextLine}The Veteran ${part1} ${part3}.
				<#assign nextLine = "${LINE_BREAK}">
			</#if>
			<#if isSelectedAnswer(var600,var603)>
				${nextLine}The Veteran endorsed using ${part2}.
			</#if>
		</#if>
	
  	<#else>
  		${getNotCompletedText()}
  	</#if>
${MODULE_END}'
where template_id = 26;

