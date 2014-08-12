<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
MDQ:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign mdq_section>
	  <#t>

	<#if var2670?? && var2670?? && var2680?? && var2690?? && var2700?? && var2710?? && var2720?? && var2730?? && var2740?? && var2750?? && var2760?? && var2770?? && var2780?? && var2790?? && var2800??>
	<#--  -->
		<#if getScore(var2660) == 999 ||  getScore(var2670) == 999 ||  getScore(var2680) == 999  || getScore(var2690) == 999  ||  getScore(var2700) == 999  || getScore(var2710) == 999  ||  getScore(var2720) == 999  
				|| getScore(var2730) == 999  ||  getScore(var2740) == 999  || getScore(var2750) == 999  ||  getScore(var2760) == 999  ||
					getScore(var2770) == 999  || getScore(var2780) == 999  ||  getScore(var2790) == 999  || getScore(var2800) == 999 >
			
			<#assign text = getNotCompletedText()>		
		<#else>			
			<#assign score1 = getListScore([var2670, var2670 , var2680 , var2690, var2700 , var2710, var2720, var2730, var2740, var2750, var2760, var2770, var2780])>
			<#assign score2 =  getScore(var2790) >
			<#assign score3 =  getScore(var2800) >

			<#assign t = "">
			<#assign text2 = "">
			<#assign fragments = []>
		
				
				<#if (score1 >= 7) && (score2 == 1) && (score3 >= 2) >
					<#assign t = "positive indicating that the Veteran may benefit from further assessment for possible symptoms of mania or other mood disorders">
				
					<#if (getScore(var2660) > 0)>
						<#assign fragments = fragments + ["so hyper got into trouble"]>
					</#if>
					<#if (getScore(var2670) > 0)>
						<#assign fragments = fragments + ["so irritable started fights"]>
					</#if>
					<#if (getScore(var2680) > 0)>
						<#assign fragments = fragments + ["felt much more self-confident than usual"]>
					</#if>
					<#if (getScore(var2690) > 0)>
						<#assign fragments = fragments + ["got less sleep"]>
					</#if>
					<#if (getScore(var2700) > 0)>
						<#assign fragments = fragments + ["was much more talkative/spoke much faster"]>
					</#if>
					<#if (getScore(var2710) > 0)>
						<#assign fragments = fragments + ["racing thoughts"]>
					</#if>
					<#if (getScore(var2720) > 0)>
						<#assign fragments = fragments + ["could not concentrate"]>
					</#if>
					<#if (getScore(var2730) > 0)>
						<#assign fragments = fragments + ["more energy"]>
					</#if>
					<#if (getScore(var2740) > 0)>
						<#assign fragments = fragments + ["more active/did more things"]>
					</#if>
					<#if (getScore(var2750) > 0)>
						<#assign fragments = fragments + ["more social/outgoing"]>
					</#if>
					<#if (getScore(var2760) > 0)>
						<#assign fragments = fragments + ["much more interested in sex"]>
					</#if>
					<#if (getScore(var2770) > 0)>
						<#assign fragments = fragments + ["was excessive/foolish/risky"]>
					</#if>
					<#if (getScore(var2780) > 0)>
						<#assign fragments = fragments + ["got in trouble spending money"]>
					</#if>
				
					<#assign text2 = createSentence(fragments)>
				
				<#else>
					<#assign t = "negative">
				</#if>
		
				The MDQ (Hyper mood) screen was ${t}.<br><br>
			
				<#if text2?has_content>
					Symptoms endorsed: ${text2}.
				</#if>
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(mdq_section = "") >
     ${mdq_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}