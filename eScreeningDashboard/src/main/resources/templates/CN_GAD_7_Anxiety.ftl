<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
ANXIETY:

${MODULE_TITLE_END}
${MODULE_START}
  <#assign anxiety_section>
	  <#t>
	<#if var1660?? && var1670?? && var1680?? && var1690?? && var1700?? && var1710?? && var1720??>

	<#--  -->
	<#assign fragments = []>
	<#assign resolved_fragments ="">
	<#assign text ="notset"> 
	<#assign score = (getListScore([var1660,var1670,var1680,var1690,var1700,var1710,var1720]))!("-1"?number)>
	<#assign scoreText ="notset">
	
	<#if (getScore(var1660) > 0)>
		<#assign fragments = fragments + ["feeling nervous"] ><#t>
	</#if>
    <#if (getScore(var1670) > 0)>
		<#assign fragments = fragments + ["can\'t control worrying"]> <#t>
	</#if>
	<#if (getScore(var1680) > 0)>
		<#assign fragments = fragments +  ["worrying too much"]><#t>
	</#if>
	<#if (getScore(var1690) > 0)>
		<#assign fragments = fragments +  ["trouble relaxing"]><#t>
	</#if>
	<#if (getScore(var1700) > 0)>
		<#assign fragments = fragments +  ["restlessness"]><#t>
	</#if>
	<#if (getScore(var1710) > 0)>
		<#assign fragments = fragments + ["irritability"]><#t>
	</#if>
	<#if (getScore(var1720) > 0)>
		<#assign fragments = fragments + ["feeling afraid"] ><#t>
	</#if>
	
	<#if (fragments?has_content) >
		<#assign resolved_fragments = createSentence(fragments)>
	<#else>
		<#assign resolved_fragments = "None">
	</#if>
	
		
	<#if score??> 	
		<#if (score >= 0) && (score <= 4)>
			<#if (score == 0) >
				<#assign scoreText = "negative">
			<#else>
				<#assign scoreText = "positive">
			</#if>
			<#assign text = "minimal anxiety">
		<#elseif (score >= 5) && (score <= 9)>
			<#assign scoreText = "positive">
			<#assign text = "mild anxiety">
		<#elseif (score >= 10) && (score <= 14)>
			<#assign scoreText = "positive">
			<#assign text = "moderate anxiety">
		<#elseif (score >= 15) && (score <= 21)>
			<#assign scoreText = "positive">
			<#assign text = "severe anxiety">
		</#if>
	</#if>
	
	<#if (score >=1) && (score <= 21)>
		The Veteran\'s GAD-7 screen was ${scoreText}. <#t>
		The Veteran endorsed the following symptoms were occurring more than half of the days in the past two weeks: ${resolved_fragments}.<#t>
	<#elseif (score == 0)>
		The Veteran\'s GAD-7 screen was ${scoreText}. <#t>
		The Veteran reported having no anxiety symptoms in the past 2 weeks.<#t>
	</#if>
	

	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(anxiety_section = "") >
     ${anxiety_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}