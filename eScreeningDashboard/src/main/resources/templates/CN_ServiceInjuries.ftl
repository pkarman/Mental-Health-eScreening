<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
SERVICE INURIES:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign injuries_section>
	<#-- var1380: ${var1380!""}<br><br>  var1390: ${var1390!""}<br><br>  var1400: ${var1400!""}<br><br>  var1410: ${var1410!""}<br><br>  var1480: ${var1480!""}<br><br>  var1490: ${var1490!""}<br><br> -->
	<#if (var1380.children)?? && (var1390.children)?? && (var1400.children)?? && (var1410.children)?? && (var1420.children)?? 
			&& (var1430.children)?? && (var1440.children)?? && (var1450.children)?? && (var1460.children)?? && (var1470.children)?? 
			&& (var1480.children)?? && (var1490.children)?? && (var1510.children)??
			&& (var1380.children?size > 0) && (var1390.children?size > 0)  && (var1400.children?size > 0) && (var1410.children?size > 0)
			&& (var1420.children?size > 0) && (var1430.children?size > 0) && (var1440.children?size > 0) && (var1450.children?size > 0)
			&& (var1460.children?size > 0) && (var1470.children?size > 0) && (var1480.children?size > 0) && (var1490.children?size > 0)
			&& (var1510.children?size > 0)>
		
		<#assign answerTextHash = {"var1380":"blast injury", "var1390":"injury to spine or back", "var1400":"burn (second, 3rd degree)", 
									"var1410":"nerve damage", "var1420":"Loss or damage to vision", "var1430":"loss or damage to hearing", 
									"var1440":"amputation", "var1450":"broken/fractured bone(s)", "var1460":"joint or muscle damage", 
									"var1470":"internal or abdominal injuries", "var1480":"other", "var1490":"other"}>
		
		<#-- must have answer questions -->
		<#assign questions1 = [var1380, var1390, var1400, var1410, var1420, var1430, var1440, var1450, var1460, var1470]>

		<#-- optional answer questions -->
		<#assign questions2 = [var1480, var1490]>


		<#assign outputText = "">
		<#assign errorText = "">
		<#assign sentences = []>
		<#-- organize answers in a way to facilitate output -->
		<#assign noneAnswers = []>
		<#assign combatAnswers = []>
		<#assign nonCombatAnswers = []>
		<#assign otherAnswers = []>
		<#list questions1 as q>
			<#if (q.children[0])?? >
				<#assign key = (q.key)!"">
				<#assign text = (q.children[0].overrideText)!""> 
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
			<#else>
				<#-- if no children then question was not answered -->
				<#assign errorText =  getNotCompletedText()>
				<#break>
			</#if>
		</#list>

		
		<#if !(errorText?has_content)>
				<#if (var1480.children)?has_content && (var1480.children[0].overrideText == "none")>
					<#assign answer = (answerTextHash["var1480"])!"">
					<#-- dont do anything here this freeform Q is handled differently than the others -->
				<#elseif ((var1480.children?size) > 0) && !((var1481.otherText)?has_content) > 
					<#-- veteran did not complete this answer -->
					<#assign errorText = getNotCompletedText()>
				<#else>
					<#if (var1480.children?size > 0)>
						<#assign otherAnswer = var1481.otherText!"">
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
		</#if>
		
		<#if !(errorText?has_content)>
				<#if (var1490.children)?has_content && (var1490.children[0].overrideText == "none")>
					<#assign answer = (answerTextHash["var1490"])!"">
					<#-- dont do anything here this freeform Q is handled differently than the others -->
				<#elseif ((var1490.children?size) > 0) && !((var1491.otherText)?has_content) > 
					<#-- veteran did not complete this answer -->
					<#assign outputText = getNotCompletedText()>
				<#else>
					<#if (var1490.children?size > 0)>
						<#assign otherAnswer = var1491.otherText!"">
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
		</#if>
		
		<#-- build first two sentences -->
		<#if combatAnswers?has_content>
			<#assign sentence = "The following injuries were reported during combat deployment: " + createSentence(combatAnswers) + ".">
			<#assign sentences = sentences + [sentence]>
		<#else>
			<#assign sentences = sentences + ["The Veteran reported no injuries during combat deployment."]>
		</#if>
		
		<#if nonCombatAnswers?has_content>
			<#assign sentence = "The following injuries were reported during other service period or training: " + createSentence(nonCombatAnswers) + ".">
			<#assign sentences = sentences + [sentence]>
		<#else>
			<#assign sentences = sentences + ["The Veteran reported no injuries during other service period or training."]>
		</#if>
		<#if !(errorText?has_content)>
			<#assign frag = "">
			<#if isSelectedAnswer(var1510,var1512)>
				<#assign frag = "has">
			</#if>
			<#if isSelectedAnswer(var1510,var1511) || isSelectedAnswer(var1510,var1513)>
				<#assign frag = "does not have">
			</#if>
			<#assign sentence = "The Veteran " + frag + " a service connected disability rating.">
			<#assign sentences = sentences + [sentence]>
		</#if>
		<#if errorText?has_content>
			${errorText}
		<#else>
			<#list sentences as s>
				${s}<br><br>
			</#list>
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(injuries_section = "") >
     ${injuries_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}