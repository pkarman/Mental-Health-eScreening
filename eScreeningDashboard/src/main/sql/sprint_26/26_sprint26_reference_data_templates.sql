update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Depression:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var1550.children)?? && (var1560.children)?? && (var1570.children)?? && (var1580.children)?? && (var1590.children)??
			&& ((var1550.children)?size > 0) && ((var1560.children)?size > 0) && ((var1570.children)?size > 0) 
			&& ((var1580.children)?size > 0) && ((var1590.children)?size > 0)>
	<#assign fragments = []>
	<#assign text ="notset"> 
	<#assign totScore = getFormulaDisplayText(var1599)>
	<#assign score = -1>
	<#assign scoreText ="notset">
		
	<#if totScore?? && totScore != "notfound"  && totScore != "notset"> 
		<#assign score = totScore?number>
		<#if (score == 0) >
			<#assign scoreText = "negative">
		<#elseif (score >= 1) && (score <= 4)>
			<#assign scoreText = "negative">
			<#assign text = "minimal depression">
		<#elseif (score >= 5) && (score <= 9)>
			<#assign scoreText = "negative">
			<#assign text = "mild depression">
		<#elseif (score >= 10) && (score <= 14)>
			<#assign scoreText = "positive">
			<#assign text = "moderate depression">
		<#elseif (score >= 15) && (score <= 19)>
			<#assign scoreText = "positive">
			<#assign text = "moderately severe depression">
		<#elseif (score >= 20) && (score <= 27)>
			<#assign scoreText = "positive">
			<#assign text = "severe depression">
		</#if>

		<#if (score >=1) && (score <= 27)>
			<#t>The Veteran\'s PHQ-9 screen was ${scoreText}.${NBSP} The score was ${(score)?string} which is suggestive of ${text}.${NBSP}
		<#elseif (score == 0)>
			The Veteran\'s PHQ-9 screen was ${scoreText}. The score was ${(score)?string}.${NBSP}
		</#if>
		</#if>
	<#else>
		The Veteran\'s PHQ-9 screen was not calculated due to the Veteran declining to answer some or all of the questions.
	</#if>
${MODULE_END}
' where template_id = 31;

update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
A/V HALLUCINATIONS:
${MODULE_TITLE_END}
${MODULE_START}

	<#if (var1350.children)?? && (var1350.children?size > 0) || ((var1360.children)?? && (var1360.children?size > 0))>  		
		The Veteran ${NBSP}
		<#if (var1350.children)?? && (var1350.children?size > 0)>
			<#assign Q1_Score = getScore(var1350)>
			<#if (Q1_Score > 0) >
				reported hearing things other people can\'t hear
			<#else>
				denied audio hallucinations
			</#if>
		</#if>
		
		<#if ((var1360.children)?? && (var1360.children?size > 0))>
		  <#assign Q2_Score = getScore(var1360)>
		  , The Veteran ${NBSP}		
			<#if (Q2_Score > 0) >
				reported seeing things or having visions other people can\'t see"
			<#else>
				denied visual hallucinations
			</#if>
		</#if>
		.
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 29;

update variable_template set override_display_value='mild' where template_id=21 and assessment_variable_id in (2562,2572);