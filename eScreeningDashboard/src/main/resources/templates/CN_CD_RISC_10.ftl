<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
RESILIENCE AND STRENGTHS:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign risc_section>
	
	<#if (var2820.children)?? && (var2830.children)?? && (var2840.children)?? && (var2850.children)?? && (var2860.children)?? 
			&& (var2870.children)?? && (var2880.children)?? && (var2890.children)?? && (var2900.children)?? && (var2910.children)?? 
			&& (var2930.children)??
			&& ((var2820.children)?size > 0) && ((var2830.children)?size > 0) && ((var2840.children)?size > 0) && ((var2850.children)?size > 0) 
			&& ((var2860.children)?size > 0) && ((var2870.children)?size > 0) && ((var2880.children)?size > 0) && ((var2890.children)?size > 0) 
			&& ((var2900.children)?size > 0) && ((var2910.children)?size > 0) > 
	<#--  -->
		<#if getScore(var2820) == 999 ||  getScore(var2830) == 999 ||  getScore(var2840) == 999  || getScore(var2850) == 999  ||  getScore(var2860) == 999  || getScore(var2870) == 999  ||  getScore(var2880) == 999  
				|| getScore(var2890) == 999  ||  getScore(var2900) == 999  || getScore(var2910) == 999  >
			
			<#assign text = getNotCompletedText()>		
		<#else>			
			<#assign score = getFormulaDisplayText(var2930)?number>
			<#assign scoreText = "">
			<#assign fragments = []>
					<#if (getScore(var2820) >= 2) && (getScore(var2820) <= 998)  >
						<#assign fragments = fragments + ["I am adaptable"]>
					</#if>	
					<#if (getScore(var2830) >= 2) && (getScore(var2830) <= 998)  >
						<#assign fragments = fragments + ["I can deal with whatever"]>
					</#if>	
					<#if (getScore(var2840) >= 2) && (getScore(var2840) <= 998)  >
						<#assign fragments = fragments + ["I find humor when faced with problems"]>
					</#if>	
					<#if (getScore(var2850) >= 2) && (getScore(var2850) <= 998)  >
						<#assign fragments = fragments + ["coping with stress can make me stronger"]>
					</#if>	
					<#if (getScore(var2860) >= 2) && (getScore(var2860) <= 998)  >
						<#assign fragments = fragments + ["I bounce back after hardships"]>
					</#if>	
					<#if (getScore(var2870) >= 2) && (getScore(var2870) <= 998) >
						<#assign fragments = fragments + ["I believe I can achieve"]>
					</#if>	
					<#if (getScore(var2880) >= 2) && (getScore(var2880) <= 998)  >
						<#assign fragments = fragments + ["focus under pressure"]>
					</#if>	
					<#if (getScore(var2890) >= 2) && (getScore(var2890) <= 998)  >
						<#assign fragments = fragments + ["not easily discouraged by failure"]>
					</#if>	
					<#if (getScore(var2900) >= 2) && (getScore(var2900) <= 998)  >
						<#assign fragments = fragments + [", think of myself as strong person"]>
					</#if>	
					<#if (getScore(var2910) >= 2) && (getScore(var2910) <= 998)  >
						<#assign fragments = fragments + [", can handle unpleasant or painful feelings"]>
					</#if>	
		
					<#if (score >= 0)  &&  (score <= 9)>
						<#assign scoreText = "minimal resilience">
					</#if>
					<#if (score >= 10)  &&  (score <= 29)>
						<#assign scoreText = "low resilience">
					</#if>
					<#if (score >= 20)  &&  (score <= 29)>
						<#assign scoreText = "medium resilience">
					</#if>
					<#if (score >= 30)  &&  (score <= 998)>
						<#assign scoreText = "high resilience">
					</#if>
				
				
					The Veteran had a score of ${score} indicating ${scoreText}. ${LINE_BREAK}${LINE_BREAK}
					
					<#assign beliefs = "none">
					<#if fragments?has_content >
						<#assign beliefs = createSentence(fragments)>
					</#if>
					
					The Veteran reported the following resilience beliefs at least sometimes during the past four weeks: ${beliefs}.
			</#if>		
		<#else>
			${getNotCompletedText()}
		</#if>
			
  </#assign>
  <#if !(risc_section = "") >
     ${risc_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}