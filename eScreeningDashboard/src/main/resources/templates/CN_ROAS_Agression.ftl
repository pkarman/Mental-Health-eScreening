<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
AGGRESSION: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign agression_section>

			
    	<#if (var3200?? && var3210?? && var3220?? && var3230?? && var3240?? && var3250?? && var3260?? && var3270?? && var3280?? && var3290?? 
    		&& var3300?? && var3310?? && var3320?? && var3330?? && var3340?? && var3350??) >
		
			<#if !(getScore(var3200) == 999 ||  getScore(var3210) == 999 ||  getScore(var3220) == 999  || getScore(var3230) == 999  ||  getScore(var3240) == 999  || getScore(var3250) == 999  ||  getScore(var3260) == 999  
				|| getScore(var3270) == 999  ||  getScore(var3280) == 999  || getScore(var3290) == 999)  ||  getScore(var3300) == 999 ||  getScore(var3310) == 999  || getScore(var3320) == 999  ||  getScore(var3330) == 999  
				|| getScore(var3340) == 999  ||  getScore(var3350) == 999  >
			
			  <#if (getFormulaDisplayText(var3389)?number == 0)><#-- veteran answered no to all questions" -->
				${getAnsweredNeverAllText("Aggression")}.<#t>
			  <#else>
    			The Veteran had a score of ${getFormulaDisplayText(var3389)}  on the Retrospective Overt Aggression Scale (range 0-84).
    			<br><br>
    			
    			<#assign fragments = []>
	
	
				<#assign verbalAggressionText = "">
				<#assign verbalAggressionSubText = []>
				<#if (getQuestionCalcValue(var3200) > 0) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["shouting angrily"]>
				</#if>
				<#if (getQuestionCalcValue(var3210) > 0) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["yelling mild insults"]>
				</#if>
				<#if (getQuestionCalcValue(var3220) > 0) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["making vague threats"]>
				</#if>
				<#if (getQuestionCalcValue(var3230) > 0) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["making clear threats of violence"]>
				</#if>
				<#if verbalAggressionSubText?has_content >      
					<#assign verbalAggressionText = "Verbal Aggression (" + createSentence(verbalAggressionSubText) + ")">
					<#assign fragments = fragments + [verbalAggressionText] >
				</#if>

				<#assign physicalAggressionObjectsText = "">
				<#assign physicalAggressionObjectsSubText = []>
				<#if (getQuestionCalcValue(var3240) > 0) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["slamming doors"]>
				</#if>
				<#if (getQuestionCalcValue(var3250) > 0) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["throwing objects"]>
				</#if>
				<#if (getQuestionCalcValue(var3260) > 0) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["breaking objects or smashing windows"]>
				</#if>
				<#if (getQuestionCalcValue(var3270) > 0) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["setting fires or throwing object dangerously"]>
				</#if>
				<#if physicalAggressionObjectsSubText?has_content >      
					<#assign physicalAggressionObjectsText = "Physical aggression toward objects (" + createSentence(physicalAggressionObjectsSubText) + ")">
					<#assign fragments = fragments + [physicalAggressionObjectsText] >
				</#if>



				<#assign physicalAggressionSelfText = "">
				<#assign physicalAggressionSelfSubText = []>
				<#if (getQuestionCalcValue(var3280) > 0) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["hitting self or pulling own hair"]>
				</#if>
				<#if (getQuestionCalcValue(var3290) > 0) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["banging head or fists against objects"]>
				</#if>
				<#if (getQuestionCalcValue(var3300) > 0) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["cutting or bruising self"]>
				</#if>
				<#if (getQuestionCalcValue(var3310) > 0) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["other serious self injury"]>
				</#if>
				<#if physicalAggressionSelfSubText?has_content >      
					<#assign physicalAggressionSelfText = "Physical aggression toward self (" + createSentence(physicalAggressionSelfSubText) + ")">
					<#assign fragments = fragments + [physicalAggressionSelfText] >
				</#if>

				<#assign physicalAggressionOthersText = "">
				<#assign physicalAggressionOthersSubText = []>
				<#if (getQuestionCalcValue(var3320) > 0) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["swinging at people or grabbing others"]>
				</#if>
				<#if (getQuestionCalcValue(var3330) > 0) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["striking or pushing on others"]>
				</#if>
				<#if (getQuestionCalcValue(var3340) > 0) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["bruising or causing welts on others"]>
				</#if>
				<#if (getQuestionCalcValue(var3350) > 0) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["attacking others causing severe physical injury"]>
				</#if>
				<#if physicalAggressionOthersSubText?has_content >      
					<#assign physicalAggressionOthersText = "Physical aggression towards others (" + createSentence(physicalAggressionOthersSubText) + ")">
					<#assign fragments = fragments + [physicalAggressionOthersText]  >
				</#if>

				The Veteran indicated the following concerning aggressive behaviors in the past month: ${createSentence(fragments)}.<#t>
			  </#if>
    		<#else>
    			${getNotCompletedText()}
    		</#if>
    	<#else>
    		${getNotCompletedText()}
    	</#if>
  </#assign>
  <#if !(agression_section = "") >
     ${agression_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}