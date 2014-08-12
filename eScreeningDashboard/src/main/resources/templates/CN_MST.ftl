<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
MST:

${MODULE_TITLE_END}
${MODULE_START}
  <#assign mst_section>
	  <#t>

	  <#if var2003??>
	<#--  -->
	<#assign fragments = []>
	<#assign text ="notset"> 
	<#assign mstMilitaryScore = getScore(var2003)!("-1"?number)>
	<#-- <#assign mstChildhoodScore = getScore(var1620)!("-1"?number)> --><#-- not used at this time -->
	<#-- <#assign mstAdulthoodScore = getScore(var1630)!("-1"?number)> --><#-- not used at this time -->
	<#assign scoreText ="notset">
		
	<#if mstMilitaryScore??> 	
		<#if (mstMilitaryScore == 0) >
			<#assign scoreText = "negative">
		<#elseif (mstMilitaryScore == 1) >	
			<#assign scoreText = "positive">
		</#if>
	</#if>
	
	<#if var2005?? && isSelectedAnswer(var2003,var2005)>
	 <#if (mstMilitaryScore == 0  || mstMilitaryScore == 1)>
		The Veteran\s MST screen was ${(scoreText)!"notset"}. The Veteran ${getSelectOneDisplayText(var1640)!"notset"} like a referral to discuss the sexual trauma further.
	 <#elseif (mstMilitaryScore == 2)>
		The Veteran ${getSelectOneDisplayText(var2003)!"notset"} the MST screen. The Veteran ${getSelectOneDisplayText(var1640)!"notset"} like a referral to discuss the sexual trauma further.<#t>
		
	 </#if>
	<#else>
		The Veteran declined to answer MST assessment.
	</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(mst_section = "") >
     ${mst_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}