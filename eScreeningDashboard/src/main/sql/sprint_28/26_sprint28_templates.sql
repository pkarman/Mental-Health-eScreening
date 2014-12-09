update template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PRESENTING CONCERN(S):
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var200.children)?? && (var210.children)?? && (var220.children)?? && (var230.children)?? && (var240.children)?? 
		&& (var250.children)?? && (var260.children)?? && (var270.children)??
		&& ((var200.children)?size > 0) && ((var210.children)?size > 0) && ((var220.children)?size > 0) && ((var230.children)?size > 0) && ((var240.children)?size > 0) 
		&& ((var250.children)?size > 0) && ((var260.children)?size > 0) && ((var270.children)?size > 0)>

    <#-- The Veteran identified enrollment, mental health , physical health, establishing a PCP, and vic as the presenting concerns. -->
    <#if hasValue(getSelectMultiDisplayText(var200)) >
      The Veteran identified ${getSelectMultiDisplayText(var200)} as the presenting concern(s). ${NBSP}
					${LINE_BREAK}
					${LINE_BREAK}
	</#if>
    
	<#assign fragments = []>
	<#assign healthCareText = "">
	<#assign vaBeneifitsText = "">
	<#assign employmentText = "">
	<#assign financialInfoText = "">
	<#assign SocialText = "">
	<#assign legalText = "">
	<#assign housingText = "">
	<#assign otherText = "">

	<#if hasValue(var210) && !isSelectedAnswer(var210, var211)>      
		<#assign healthCareText = "Healthcare (specifically, " + getSelectMultiDisplayText(var210) + ")">
		<#assign fragments = fragments + [healthCareText] >
	</#if>

	<#if hasValue(var250) && !isSelectedAnswer(var250, var251)>   
		<#assign vaBeneifitsText = "VA Benefits (specifically, " + getSelectMultiDisplayText(var250) + ")" >
		<#assign fragments = fragments + [vaBeneifitsText] >
	</#if>

	<#if hasValue(var220) && !isSelectedAnswer(var220, var221)>  
		<#assign employmentText = "Employment (specifically, " + getSelectMultiDisplayText(var220) + ")">
		<#assign fragments = fragments + [employmentText] >
	</#if>

	<#if hasValue(var230) && !isSelectedAnswer(var230, var231)>      
		<#assign SocialText = "Social (specifically, " + getSelectMultiDisplayText(var230) + ")">
		<#assign fragments = fragments + [SocialText]  >
	</#if>

	<#if hasValue(var270) && !isSelectedAnswer(var270, var271)>      
		<#assign legalText = "Legal (specifically, " + getSelectMultiDisplayText(var270) + ")" >
		<#assign fragments = fragments + [legalText]  >
	</#if>

	<#if hasValue(var240) && !isSelectedAnswer(var240, var241)>       
		<#assign housingText = "Housing (specifically, " + getSelectMultiDisplayText(var240) + ")">
		<#assign fragments = fragments + [housingText] >
	</#if>
	
	<#if var244?? && hasValue(var244) && var244.value == "true">
		<#assign fragments = fragments + [var244.otherValue]>
	</#if>

	<#if (fragments?size > 0)>
		The Veteran indicated that he/she would like information or assistance with the following: ${createSentence(fragments)}.${NBSP}
	<#else>
		The Veteran indicated that he/she would not like information or assistance with Healthcare, VA BEnefits, Social, Legal or Housing concerns.
	</#if>
   <#else>
	 ${getNotCompletedText()}
   </#if>
${MODULE_END}
'
where template_id = 3;

UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
OEF/OIF CLINICAL REMINDER:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var660.children)?? && ((var660.children)?size > 0)  >
	  <#if hasValue(getSelectMultiDisplayText(var660)) >
    	<#if isSelectedAnswer(var660, var661)> <#-- no -->
			The Veteran\'s most recent deployment was not for Operation Enduring Freedom (OEF) or Operation Iraqi Freedom (OIF)
    		
		<#elseif isSelectedAnswer(var660, var659)><#-- yes -->
    			The Veteran\'s most recent deployment was for ${getSelectOneDisplayText(var660)}
    			<#if var683?? && getSelectOneDisplayText(var683) != "notfound"> ${NBSP}in ${getSelectOneDisplayText(var683)}</#if>   		
		<#elseif isSelectedAnswer(var660, var662)><#-- yes -->
    		The Veteran\'s most recent deployment was for ${getSelectOneDisplayText(var660)}
    		<#if var663?? && getSelectOneDisplayText(var663) != "notfound"> ${NBSP}in ${getSelectOneDisplayText(var663)}</#if>
    	</#if>
    	. ${NBSP}
    </#if>
	<#else>
		${getNotCompletedText()}.
	</#if>
${MODULE_END}'
where template_id = 13;

UPDATE template SET template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
OTHER HEALTH SYMPTOMS:
${MODULE_TITLE_END}
${MODULE_START}
  	<#if (var930.children)?? && (var940.children)?? && (var950.children)?? && (var960.children)?? && (var970.children)?? && (var980.children)?? 
			&& ((var930.children)?size > 0) && ((var940.children)?size > 0) && ((var950.children)?size > 0) 
			&& ((var960.children)?size > 0) && ((var970.children)?size > 0) && ((var980.children)?size > 0) >
	 
	<#-- The Veteran endorsed being bothered a lot by the following health symptoms over the past four weeks: hearing loss, tinnitus -->
	<#assign fragments = []> 
    <#if (getListScore([var930, var940]) > 1)>
		<#assign fragments = fragments + ["hearing"] >
	</#if>
    <#if (getScore(var950) > 1)>
		<#assign fragments = fragments + ["vision"]> 
	</#if>
	<#if (getListScore([var960, var970]) > 1)>
		<#assign fragments = fragments + ["weight"] >
	</#if>
	<#if (getScore(var980) > 1)>
		<#assign fragments = fragments + ["forgetfulness"] >
	</#if>
	
	<#if ((fragments?size) > 0)  >
		<#assign resolved_fragments =  createSentence(fragments)>
	<#else>
		<#assign resolved_fragments = "None">
	</#if>

	The Veteran endorsed being bothered by the following health symptoms over the past four weeks: ${resolved_fragments}.
	
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 18;

-- /* MEDICATION */
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
MEDICATIONS:
${MODULE_TITLE_END}
${MODULE_START}
	<#-- var3500: ${var3500!""}<br><br>  var3501: ${var3501!""}<br><br>  var3501.value:  ${var3501.value!""}<br><br>  -->
<#assign noMedText = "The Veteran reported taking no medication.">
<#if var3501?? && (var3501.value == "true")>
	<#assign allAnswersNone = true>
	<#assign allAnswersPresent = true>
<#else>
	<#assign allAnswersNone = false>
	<#assign allAnswersPresent = false>
</#if>

<#if !allAnswersPresent && !allAnswersNone>
	<#if ((var3500.children)?? && ((var3500.children?size) > 0))>
		
				<#assign rows = {}>
				<#list ((var3500.children)![]) as v>
					<#list v.children as c>
						<#if (c.row)?? >
							<#assign row_idx = (c.row)?string>
							<#assign row_key = (c.key)?string>
							<#assign row_value = (c.value)?string>
							<#assign r ={}>
							<#assign row_name = ("row" + row_idx + "_" + row_key)?string >
							<#assign rows =  rows + {row_name:row_value}>
						</#if>
					</#list>
				</#list>
	
				<#assign uniqueRows = []>
				<#assign e = []>
				<#if (rows?keys)??>
					<#list (rows?keys?sort) as k>
						<#assign e = (k?split("_"))>
						<#if !(uniqueRows?seq_contains(e[0]))>
							<#assign uniqueRows = uniqueRows + [e[0]]>
						</#if>
					</#list>
				</#if>
			
				<#assign outputText = "">
				<#if (uniqueRows?size > 0)>
					<#assign all_rows = "">
					<#assign nextLine = "">
					<#list uniqueRows as r>
						<#assign med = (rows[r + "_" + "var3511"])!"">
						<#assign dose = (rows[r + "_" + "var3521"])!"">
						<#assign freq = (rows[r + "_" + "var3531"])!"">
						<#assign dur = (rows[r + "_" + "var3541"])!"">
						<#assign doc = (rows[r + "_" + "var3551"])!"">

						<#if (med?has_content) >
							<#assign all_rows = all_rows + nextLine +
							"MEDICATION/SUPPLEMENT: " + med + "${LINE_BREAK}" 
							+ "DOSAGE: " + dose + "${LINE_BREAK}" 
							+ "FREQUENCY: " + freq + "${LINE_BREAK}" 
							+ "DURATION: " + dur + "${LINE_BREAK}" 
							+ "PRESCRIBER NAME AND/OR FACILITY: " + doc >
							<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
						</#if>
					</#list>

					<#if outputText?has_content>
						${outputText}
					<#else>
						${all_rows!""}
					</#if>
				<#else>
					${getNotCompletedText()}
				</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
<#else>
	${noMedText}
</#if>
${MODULE_END}
' where template_id = 22;

 update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
WHODAS 2.0: The Veteran was given the WHODAS 2.0, which covers six domains of assessing health status and disability on a scale of one to five. ${LINE_BREAK}${LINE_BREAK}
${MODULE_TITLE_END}
${MODULE_START}
	<#function getScoreText score>
			<#assign text = "">
			<#if (score?number >= 1) && (score?number < 2)>
				<#assign text = "no">
			<#elseif (score?number >= 2) && (score?number < 3)>
				<#assign text = "mild">
			<#elseif (score?number >= 3) && (score?number < 4)>
				<#assign text = "moderate">
			<#elseif (score?number >= 4) && (score?number < 5)>
				<#assign text = "severe">
			<#elseif (score?number >= 5) && (score?number < 6)>
				<#assign text = "extreme">
			</#if>

			<#return text>
	</#function>	

		  <#t><b>Understanding and Communicating</b> - the Veteran
		  <#if var4119?? && (var4119.value)??>
			${NBSP}had an average score of ${var4119.value} which indicates ${getScoreText(var4119.value)} disability.
		  <#else>
		  	\'s score could not be calculated.
		  </#if>
		  ${LINE_BREAK}${LINE_BREAK}
		  
			<b>Mobility</b> - the Veteran${NBSP} 
			<#if var4239?? && (var4239.value)??>
			 ${NBSP}had an average score of ${var4239.value}, which indicates ${getScoreText(var4239.value)} disability.
			<#else>\'s score could not be calculated.
			</#if>
			
			${LINE_BREAK}${LINE_BREAK}
			<b>Self-Care</b> - the Veteran ${NBSP} 
			<#if var4319?? && var4319.value??>
				${NBSP}had an average score of ${var4319.value} which indicates ${getScoreText(var4319.value)} disability.
			<#else>
				\'s score could not be calculated.
			</#if>
			${LINE_BREAK}${LINE_BREAK}		

			<b>Getting Along</b> - the Veteran 
			<#if var4419?? && var4419.value??>
				${NBSP} had an average score of ${var4419.value} which indicates ${getScoreText(var4419.value)} disability.
			<#else>\'s score could not be calculated.
			</#if>
			 ${LINE_BREAK}${LINE_BREAK}

			<b>Life Activities (Household/Domestic)</b> - the Veteran 
			<#if var4499?? && var4499.value??>
			 ${NBSP}had an average score of ${var4499.value} which is a rating of ${getScoreText(var4499.value)} disability. 
			<#else>\'s score could not be calculated.
			</#if>
			 ${LINE_BREAK}${LINE_BREAK} 
			
			<#if var4200?? && ((var4200.children)?? && ((var4200.children)?size > 0))>
				<#if isSelectedAnswer(var4200, var4202)>
					<#if var4559?? && var4559.value??>
					  <b>Life Activities (School /Work)</b> - the Veteran had an average score of ${var4559.value} which is a rating of ${getScoreText(var4559.value)} disability. ${LINE_BREAK}${LINE_BREAK}
					<#else>
					  <b>Life Activities (School /Work)</b> - the Veteran\'s score could not be calculated.${LINE_BREAK}${LINE_BREAK}
					</#if>      
				<#elseif isSelectedAnswer(var4200, var4201)>
					<b>Life Activities (School /Work)</b> - the Veteran did not get a score because the veteran does not work or go to school. ${LINE_BREAK}${LINE_BREAK}   
				</#if>
			<#else>
				<b>Life Activities (School /Work)</b> - the Veteran\'s score could not be calculated.${LINE_BREAK}${LINE_BREAK}
			</#if>
			
			<b>Participation in Society</b> - the Veteran 
			
			<#if var4789?? && var4789.value??>
			${NBSP}had an average score of ${var4789.value} which indicates ${getScoreText(var4789.value)} disability. ${NBSP} 
			<#else>\'s score could not be calculated.
			</#if>
${MODULE_END}'
where template_id = 24;

update assessment_variable set formula_template = '([4480] + [4500] + [4520] + [4540])/4' where assessment_variable_id=4559;

