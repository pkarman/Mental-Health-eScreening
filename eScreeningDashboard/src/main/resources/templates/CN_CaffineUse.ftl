<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PHQ-15
${MODULE_TITLE_END}
${MODULE_START}
  <#assign phq15_section>
	  <#t>
	<#if var1000?? && var1030?? && var1040?? && var1050?? && var1060?? && var1070?? && var1080?? 
		&& var1090?? && var1100 && var1110 && var1120?? && var1130?? && var1140?? && var1150?? && var1160??>  
		
	<#-- During the past 4 weeks, how much have you been bothered by any of the following problems -->
	<#-- this is almost identical to Other Health Symptoms -->
	<#assign fragments = []> 
    <#if (getScore(var1000) > 0)>
		<#assign fragments = fragments + ["stomach pain"] ><#t>
	</#if>
    <#if (getScore(var1150) > 0)>
		<#assign fragments = fragments + ["back pain"] ><#t>
	</#if>
	<#if (getScore(var1160) > 0)>
		<#assign fragments = fragments + ["pain in arms, legs or joints (knees, hips, etc.)"] ><#t>
	</#if>
	<#if (getScore(var1030) > 0)>
		<#assign fragments = fragments + ["menstrual cramps or other problems with your periods"] ><#t>
	</#if>
	<#if (getScore(var1040) > 0)>
		<#assign fragments = fragments + ["headaches"] ><#t>
	</#if>
	<#if (getScore(var1050) > 0)>
		<#assign fragments = fragments + ["chest pain"] ><#t>
	</#if>
	<#if (getScore(var1060) > 0)>
		<#assign fragments = fragments + ["dizziness"] ><#t>
	</#if>
	<#if (getScore(var1070) > 0)>
		<#assign fragments = fragments + ["fainting spells"] ><#t>
	</#if>
	<#if (getScore(var1080) > 0)>
		<#assign fragments = fragments + ["feeling your heart pound or race"] ><#t>
	</#if>
	<#if (getScore(var1090) > 0)>
		<#assign fragments = fragments + ["shortness of breath"] ><#t>
	</#if>
	<#if (getScore(var1100) > 0)>
		<#assign fragments = fragments + ["pain or problems during sexual intercourse"] ><#t>
	</#if>
	<#if (getScore(var1110) > 0)>
		<#assign fragments = fragments + ["constipation, loose bowels or diarrhea"] ><#t>
	</#if>
	<#if (getScore(var1120) > 0)>
		<#assign fragments = fragments + ["nausea, gas or indigestion"] ><#t>
	</#if>
	<#if (getScore(var1130) > 0)>
		<#assign fragments = fragments + ["feeling tired or having low energy"] ><#t>
	</#if>
	<#if (getScore(var1140) > 0)>
		<#assign fragments = fragments + ["trouble sleeping"] ><#t>
	</#if>

	The Veteran endorsed being bothered by the following health symptoms over the past four weeks: ${resolved_fragments}.
	
	<#else>
		${getNotCompletedText()}
	
	</#if>
  </#assign>
  <#if !(phq15_section = "") >
     ${phq15_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}