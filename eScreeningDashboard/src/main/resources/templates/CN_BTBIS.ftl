<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
TBI: 
${MODULE_TITLE_END}
${MODULE_START}

	<#function isAnswered obj1>
		<#assign result = false>
		<#if (obj1.children)?has_content >
			<#assign result = true>
		</#if>
		<#return result>
	</#function>
	
	
	
  <#assign tbi_section>
		<#-- all these pre checks are to make sure all required questions are anwered -->
			<#-- flag that signals if answer was completed, used for ease in identifying which individual questions have been answered -->
			<#assign isCompleted1 = isAnswered(var3400!{})>
			<#assign isCompleted2 = isAnswered(var3410!{})>
			<#assign isCompleted3 = isAnswered(var3420!{})>
			<#assign isCompleted4 = isAnswered(var3430!{})>
			<#assign isCompleted3450 = isAnswered(var3450!{})>
			<#assign isCompleted3460 = isAnswered(var3460!{})>
			<#assign isCompleted3470 = isAnswered(var3470!{})>
			<#assign isCompleted2047 = isAnswered(var2047!{})>
			<#assign hasCompletedRequiredQuestions = false> <#-- if all required answers are completed == true -->
			<#assign score1 = 0> 
			<#assign score2 = 0> 
			<#assign score3 = 0> 
			<#assign score4 = 0> 
			<#assign score = 0> 
	
			<#assign isNoneForAllRequired = false >
				
    		<#if !hasCompletedRequiredQuestions >
				<#if isCompleted1> 
					<#assign isNoneForQ1 = isSelectedAnswer(var3400,var2016)>
					<#if isNoneForQ1> <#-- ansewered NONE to Q1 -->
						<#assign score1 = 0>
						<#assign hasCompletedRequiredQuestions = true> 
						<#assign isNoneForAllRequired = true>	<#-- this seems like you should check for \"none\" in all (Q1, Q2, Q3 and Q4) but this can only be true if Q1 == \"none\" b/c Q2, Q3 and Q4 won\'t get presented to Veteran if Q1 == \"none\"-->
					<#else>
						<#assign score1 = 1>
						<#assign hasCompletedRequiredQuestions = false> <#-- this is redundant since hasCompletedRequiredQuestions is originally set to false but put this in for clarity -->
					</#if>
					<#assign score = score + score1> 
				</#if>
			</#if>	
			
			<#if !hasCompletedRequiredQuestions>
				<#if isCompleted2>
					 <#assign isNoneForQ2 = isSelectedAnswer(var3410,var2022)>
					<#if isNoneForQ2> <#-- ansewered NONE to Q2 -->
						<#assign score2 = 0>
						<#assign hasCompletedRequiredQuestions = true> 
					<#else>
						<#assign score2 = 1>
						<#assign hasCompletedRequiredQuestions = false> <#-- this is redundant since hasCompletedRequiredQuestions is originally set to false but put this in for clarity -->
					</#if>
					<#assign score = score + score2> 
				</#if>
			</#if>
	
			<#if !hasCompletedRequiredQuestions>
				<#if isCompleted3>
					 <#assign isNoneForQ3 = isSelectedAnswer(var3420,var2030)>
					<#if isNoneForQ3> <#-- ansewered NONE to Q2 -->
						<#assign score3 = 0>
						<#assign hasCompletedRequiredQuestions = true> 
					<#else>
						<#assign score3 = 1>
						<#assign hasCompletedRequiredQuestions = false> <#-- this is redundant since hasCompletedRequiredQuestions is originally set to false but put this in for clarity -->
					</#if>
					<#assign score = score + score3> 
				</#if>
			</#if>

			<#if !hasCompletedRequiredQuestions>
				<#if isCompleted3>
					 <#assign isNoneForQ4 = isSelectedAnswer(var3430,var2037)>
					<#if isNoneForQ4> <#-- ansewered NONE to Q4 -->
						<#assign score4 = 0>
						<#assign hasCompletedRequiredQuestions = true> 
					<#else>
						<#assign score4 = 1>
						<#assign hasCompletedRequiredQuestions = false> <#-- this is redundant since hasCompletedRequiredQuestions is originally set to false but put this in for clarity -->
					</#if>
					<#assign score = score + score4> 
				</#if>
			</#if>
							 
		 <#if !hasCompletedRequiredQuestions >
			<#if isCompleted2047>
				<#if isSelectedAnswer(var2047, var3441)> 
					<#assign hasCompletedRequiredQuestions = true> 
				<#else>
					<#if (isCompleted3450) && (isCompleted3460) && (isCompleted3470)> 
						<#assign hasCompletedRequiredQuestions = true> 
					<#else>
						<#assign hasCompletedRequiredQuestions = false> <#-- this is redundant since hasCompletedRequiredQuestions is originally set to false but put this in for clarity -->
					</#if>
				</#if>
			</#if>
		</#if> 
			 
		<#if hasCompletedRequiredQuestions> <#-- all questions are complete -->
		  <#if !isNoneForAllRequired> <#-- all questions are complete  and did NOT answer NONE to all -->
			
			<#assign scoreText = "">
			<#if ((score >= 4) && (score <= 998))>
				<#assign scoreText = "positive">
			<#elseif ((score >= 0) && (score <= 3))>
				<#assign scoreText = "negative">
			<#else>
				<#assign scoreText = "notset">
			</#if>
				
				
			The Veteran\'s TBI screen was ${scoreText}. <#t>
			<#if isCompleted2047>
				Veteran ${getSelectOneDisplayText(var2047)} to TBI consult for further evaluation. 
    		</#if>
    		
    	  <#else> <#-- all questions are complete  and answered NONE to all -->
			${getAnsweredNoneAllText("TBI")}
		  </#if>
		<#else> <#-- all questions are NOT complete -->
			${getNotCompletedText()}
    	</#if>
	
  </#assign>
  <#if !(tbi_section = "") >
     ${tbi_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}