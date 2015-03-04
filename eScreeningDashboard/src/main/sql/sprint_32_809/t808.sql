UPDATE template SET template_file='<#include "clinicalnotefunctions"> 
<#-- Template start --> 
${MODULE_TITLE_START} 
	Service Injuries: 
${MODULE_TITLE_END} 

${MODULE_START}  
	<#assign answerTextHash = {"var1380":"blast injury", "var1390":"injury to spine or back", "var1400":"burn (second, 3rd degree)", "var1410":"nerve damage", "var1420":"loss or damage to vision", "var1430":"loss or damage to hearing", "var1440":"amputation", "var1450":"broken/fractured bone(s)", "var1460":"joint or muscle damage", "var1470":"internal or abdominal injuries", "var1480":"other", "var1490":"other"}>  

	<#-- must have answer questions --> 
	<#assign questions1 = [var1380!DEFAULT_VALUE, var1390!DEFAULT_VALUE, var1400!DEFAULT_VALUE, var1410!DEFAULT_VALUE, var1420!DEFAULT_VALUE, var1430!DEFAULT_VALUE, var1440!DEFAULT_VALUE, var1450!DEFAULT_VALUE, var1460!DEFAULT_VALUE, var1470!DEFAULT_VALUE]>  

	<#-- organize answers in a way to facilitate output --> 
	<#assign noneAnswers = []> 
	<#assign combatAnswers = []> 
	<#assign nonCombatAnswers = []> 
	<#assign otherAnswers = []> 
	<#list questions1 as q> 
		<#if q?? && q!= DEFAULT_VALUE && q.children?? && (q.children?size > 0) && (q.children[0])?? > 
			<#assign key = (q.key)!""> 
			<#list q.children as c> 
				<#assign text = (c.overrideText)!""> 
				<#if text == "none"> 
					<#assign answer = (answerTextHash[key])!""> 
					<#assign noneAnswers = noneAnswers + [answer]> 
				<#elseif text == "combat"> 
					<#assign answer = (answerTextHash[key])!""> 
					<#assign combatAnswers = combatAnswers + [answer]> 
				<#elseif text == "non-combat"> 
					<#assign answer = (answerTextHash[key])!""> 
					<#assign nonCombatAnswers = nonCombatAnswers + [answer]> 
				<#elseif text == "other"> 
					<#assign answer = (answerTextHash[key])!""> 
					<#assign otherAnswers = otherAnswers + [answer]> 
				</#if> 
			</#list> 
		</#if> 
	</#list>  
	<#if var1480?? && var1481?? && (var1480.children?size > 0)> 
		<#assign otherAnswer = var1481.otherText!""> 
		<#if otherAnswer != ""> 
			<#list var1480.children as c> 
				<#if (c.overrideText == "combat")> 
					<#-- put in proper anser bucket --> 
					<#assign combatAnswers = combatAnswers + [otherAnswer]> 
				<#elseif (c.overrideText == "non-combat")> 
					<#-- put in proper anser bucket --> 
					<#assign nonCombatAnswers = nonCombatAnswers + [otherAnswer]> 
				</#if> 
			</#list> 
		</#if> 
	</#if>  
<#if var1490?? && var1491?? && (var1490.children?size > 0)> 
	<#assign otherAnswer = var1491.otherText!""> 
	<#if otherAnswer != ""> 
		<#list var1490.children as c> 
			<#if (c.overrideText == "combat")> 
				<#-- put in proper anser bucket --> 
				<#assign combatAnswers = combatAnswers + [otherAnswer]> 
			<#elseif (c.overrideText == "non-combat")> 
				<#-- put in proper anser bucket --> 
				<#assign nonCombatAnswers = nonCombatAnswers + [otherAnswer]> 
			</#if> 
		</#list> 
	</#if> 
</#if>   

<#-- build first two sentences --> 
<#if combatAnswers?has_content> 
	The following injuries were reported during combat deployment: ${createSentence(combatAnswers)}.${LINE_BREAK} 
</#if>  
<#if nonCombatAnswers?has_content> 
	The following injuries were reported during other service period or training: ${createSentence(nonCombatAnswers)}.${LINE_BREAK} 
</#if>  

<#if var1510?? && (var1510.children)?? && (var1510.children?size>0)> 
	<#assign frag = ""> 
	<#if isSelectedAnswer(var1510,var1512)> 
		<#assign frag = "has"> 
	<#elseif isSelectedAnswer(var1510,var1511)> 
		<#assign frag = "does not have"> 
	<#elseif isSelectedAnswer(var1510,var1513)> 
		<#assign frag = "has the intent to file for, or has filed for"> 
	</#if> The Veteran ${frag} a service connected disability rating. 
</#if> 
${MODULE_END}' 
WHERE template_id='16';
