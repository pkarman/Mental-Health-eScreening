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
	
	<#if hasValue(var260) && !isSelectedAnswer(var260, var261)>       
		<#assign financialInfoText = "Financial (specifically, " + getSelectMultiDisplayText(var260) + ")">
		<#assign fragments = fragments + [financialInfoText] >
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
	<#if (getScore(var930) > 1)>
		<#assign fragments = fragments + ["hearing loss"]>
	</#if>
    <#if (getScore(var940) > 1)>
		<#assign fragments = fragments + ["tinnitus (ringing in the ears)"] >
	</#if>
    <#if (getScore(var950) > 1)>
		<#assign fragments = fragments + ["problem with visions"]> 
	</#if>
	<#if (getScore(var960) > 1)>
		<#assign fragments = fragments + ["weight gain"] >
	</#if>
	<#if (getScore(var970) > 1)>
		<#assign fragments = fragments + ["weight loss"] >
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

UPDATE template SET 
	template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SCORING MATRIX: 
${MODULE_TITLE_END}
${MODULE_START}

	<#macro resetRow >
		<#assign screen = "">
		<#assign status = "">
		<#assign score = "">
		<#assign cutoff = "">
	</#macro>

		
		<#assign empty = "--">
		<#assign rows = []>

		<#-- AUDIT C -->
		<#assign screen = "AUDIT C">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		<#assign rows = []>
	
		<#if var1229??>
			<#assign score = getFormulaDisplayText(var1229)>
			<#if score != "notset">
				<#assign cutoff = "3-women/4-men">
				<#assign score = score?number>
				
				<#if (score >= 0) && (score <= 2)>
					<#assign status = "Negative">
				<#elseif (score >= 4) && (score <= 998)>
					<#assign status = "Positive">
				<#elseif (score == 3 )>
					<#assign status = "Positive for women/Negative for men">
				</#if>
			<#else>
				<#assign score = empty> 
			</#if>
			<#if (score?string == empty) || (status == empty)>
				<#assign status = empty>
				<#assign score = empty>
			</#if>
		</#if>
	
		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- TBI -->
		<#assign screen = "BTBIS (TBI)">
		<#assign status = "negative">
		<#assign score = "N/A">
		<#assign cutoff = "N/A">
		
		<#if var10717?? && var10717.value??>
		  <#if (var10717.value?number  >=1) >
			<#assign score = "N/A">
			<#assign cutoff = "N/A">
			<#assign status = "Positive">
		  </#if>
	    </#if>

		<#if (!(var10714??)) || (var10714?? && var10714.value=="0" && var2016.value=="false") || (var10715?? && var10715.value=="0" && var2022.value=="false")
		||(var10716?? && var10716.value=="0" && var2030.value=="false") || (var10717?? && var10717.value=="0" && var2037.value=="false") >
			<#assign status = empty>
			<#assign score = empty>
			<#assign cutoff = empty>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- DAST 10 -->
		<#assign screen = "DAST-10 (Substance Abuse)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>

		<#if var1010?? >
			<#assign score = getFormulaDisplayText(var1010)>
			<#if (score?length > 0) &&  score != "notset">
				<#assign cutoff = "3">
				<#if ((score?number) <= 2)> 
					<#assign status ="Negative">
				<#else> 
					<#assign status ="Positive">
				</#if> 
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- GAD 7 -->
		<#assign screen = "GAD-7 (Anxiety)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var1749?? >
			<#assign score = getFormulaDisplayText(var1749)>
			<#if score != "notset">
					<#assign cutoff = "10">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- HOUSING - Homelessness -->
		<#assign screen = "Homelessness">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var2000?? && var2000.value??>
			<#if var2000.value?number == 0>
			    <#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif var2000.value?number == 1>	
			   <#if var2001?? && var2001.value?? && (var2001.value?number == 1)>
				 <#assign score = "N/A">
				 <#assign cutoff = "N/A">
				 <#assign status = "Positive">
			   <#elseif var2001?? && var2001.value?? && (var2001.value?number == 0) >
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Negative">
				</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- ISI -->
		<#assign screen = "ISI (Insomnia)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var2189?? >
			<#assign score = getFormulaDisplayText(var2189)>
			<#if score != "notset">
					<#assign cutoff = "15">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- MST -->
		<#assign screen = "MST">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var2003??>
			<#if isSelectedAnswer(var2003, var2005)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var2003, var2004)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Negative">
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>




		<#-- PCLC -->
		<#assign screen = "PCL-C (PTSD)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var1929?? >
			<#assign score = getFormulaDisplayText(var1929)>
			<#if score != "notset" && score != "notfound">
					<#assign cutoff = "50">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- PTSD -->
		<#assign screen = "PC-PTSD">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var1989?? >
			<#assign score = getFormulaDisplayText(var1989)>
			<#if score != "notset" && score != "notfound">
					<#assign cutoff = "3">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>
		


	
		<#-- PHQ 9 DEPRESSION -->
		<#assign screen = "PHQ-9 (Depression)">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if var1599?? >
			<#assign score = getFormulaDisplayText(var1599)>
			<#if score != "notset" && score != "notfound">
					<#assign cutoff = "10">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- Prior MH DX/TX - Prior Mental Health Treatment -->
		<#assign screen = "Prior MH DX/TX">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		<#assign acum = 0>
		<#assign Q1complete =false>
		<#assign Q2complete =false>
		<#assign Q3complete =false>
		<#assign Q4complete =false>
		<#-- var1520: ${var1520!""}<br><br>  var1530: ${var1530!""}<br><br>  var200: ${var200!""}<br><br>  var210: ${var210!""}<br><br>  -->
		<#if (var1520.children)?? && ((var1520.children)?size > 0)>
			<#assign Q1complete = true>
			<#list var1520.children as c>
				<#if ((c.key == "var1522")  || (c.key == "var1523")  || (c.key == "var1524")) && (c.value == "true")>
					<#assign acum = acum + 1>
					<#break>
				</#if>
			</#list>
		</#if>

		<#if (var1530.children)?? && ((var1530.children)?size > 0)>
			<#assign Q2complete = true>
			<#list var1530.children as c>
				<#if ((c.key == "var1532")  || (c.key == "var1533") 
						|| (c.key == "var1534") || (c.key == "var1535") 
						|| (c.key == "var1536")) && (c.value == "true") >
					<#assign acum = acum + 1>
					<#break>
				</#if>
			</#list>
		</#if>
		

		<#if (var200.children)?? && ((var200.children)?size > 0)>
			<#assign Q3complete = true>
			<#list var200.children as c>
				<#if ((c.key == "var202") && (c.value == "true"))  >
					<#assign acum = acum + 1>
					<#break>
				</#if>
			</#list>
		</#if>

		<#if (var210.children)?? && ((var210.children)?size > 0)>
			<#assign Q4complete = true>
			<#list var210.children as c>
				<#if ((c.key == "var214") && (c.value == "true"))  >
					<#assign acum = acum + 1>
					<#break>
				</#if>
			</#list>
		</#if>

		<#if Q1complete && Q2complete && Q3complete && Q3complete>
			<#assign score = "N/A">
			<#assign cutoff = "N/A">
			<#if (acum >= 3)>
				<#assign status = "Positive">
			<#elseif (acum > 0) && (acum <= 2)>
				<#assign status ="Negative">
			</#if>
		</#if>
			
		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- TOBACCO -->
		<#assign screen = "Tobacco Use">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if (var600.children)?? && ((var600.children)?size > 0)>
			<#if isSelectedAnswer(var600, var603)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var600, var601) || isSelectedAnswer(var600, var602)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Negative">
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		<#-- VAS PAIN - BASIC PAIN -->
		<#assign screen = "VAS PAIN">
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>

		<#if (var2300)?? >
			<#assign score = getSelectOneDisplayText(var2300)>
			<#if score != "notset" && score != "notfound">
					<#assign cutoff = "4">
					<#if (score?number >= cutoff?number)>
						<#assign status = "Positive">
					<#else> 
						<#assign status ="Negative">
					</#if>
			<#else>
				<#assign score = empty>
			</#if>
		</#if>

		<#assign rows = rows + [[screen, status, score, cutoff]]>	
		<@resetRow/>

		${MATRIX_TABLE_START}
			${MATRIX_TR_START}
				${MATRIX_TH_START}Screen${MATRIX_TH_END}
				${MATRIX_TH_START}Result${MATRIX_TH_END}
				${MATRIX_TH_START}Raw Score${MATRIX_TH_END}
				${MATRIX_TH_START}Cut-off Score${MATRIX_TH_END}
			${MATRIX_TR_END}
			${MATRIX_TR_START}
				<#list rows as row>
					${MATRIX_TR_START}
					<#list row as col>
						${MATRIX_TD_START}${col}${MATRIX_TD_END}
					</#list>
					${MATRIX_TR_END}
				</#list>
			${MATRIX_TR_END}
		${MATRIX_TABLE_END}
${MODULE_END}'
 WHERE template_id = 100;
 
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2016, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2022, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2030, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2037, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10714, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10715, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10716, 100);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10717, 100);


-- The following are for for Ticket 744

-- Veteran summary Homelessness
update template set template_file = '<#include "clinicalnotefunctions"> 
<#-- Template start --> 
${MODULE_TITLE_START} Homelessness ${MODULE_TITLE_END} 
${MODULE_START} This is when you do not have a safe or stable place you can return to every night. The VA is committed to ending Veteran homelessness by the end of 2015. ${LINE_BREAK} 
${LINE_BREAK} 
<b>Results:</b>${NBSP}

<#if isSelectedAnswer(var2000, var761) || ( isSelectedAnswer(var2000, var762) && isSelectedAnswer(var2001, var772) ) >
	unstable housing/at risk 
		${LINE_BREAK} 
		<b>Recommendation:</b> Call the VA\'s free National Call Center for Homeless Veterans at (877)-424-3838 and ask for help. Someone is always there to take your call. 
<#elseif isSelectedAnswer(var2000, var762) && isSelectedAnswer(var2001, var771)>
	stable housing 
<#else>
	declined
</#if>
${MODULE_END}' 
where template_id=301;

-- update the variable templates entries for Homelessness veteran summary template
DELETE FROM variable_template where template_id=301;
-- HomelessCR_stable_house question (measure_id=61)
INSERT INTO variable_template (assessment_variable_id, template_id) VALUES (2000, 301);
-- HomelessCR_stable_house = 0
INSERT INTO variable_template (assessment_variable_id, template_id) VALUES (761, 301);
-- HomelessCR_stable_house = 1
INSERT INTO variable_template (assessment_variable_id, template_id) VALUES (762, 301);
-- HomelessCR_stable_house = 2
INSERT INTO variable_template (assessment_variable_id, template_id) VALUES (763, 301);

-- HomelessCR_stable_worry question (measure_id=62)
INSERT INTO variable_template (assessment_variable_id, template_id) VALUES (2001, 301);
-- HomelessCR_stable_worry = 0
INSERT INTO variable_template (assessment_variable_id, template_id) VALUES (771, 301);
-- HomelessCR_stable_worry = 1
INSERT INTO variable_template (assessment_variable_id, template_id) VALUES (772, 301);



-- Veteran summary TBI 
update template set template_file = '<#include "clinicalnotefunctions"> 
<#function calcScore obj> 
	<#assign result = 0> 
	<#if (obj.children)?? && ((obj.children)?size > 0)> 
		<#list obj.children as c> 
			<#if c.overrideText != "none"> 
				<#assign result = result + 1> 
				<#break> 
		</#if> 
	</#list> 
	</#if> 
	<#return result> 
</#function>  
<#assign score = 0>  
<#assign isQ2Complete = false> 
<#assign isQ2None = false> 
<#if (var3400.children)?? && ((var3400.children)?size > 0)> 
	<#assign isQ2Complete = true> 
	<#if isSelectedAnswer(var3400, var2016)!false> 
		<#assign isQ2None = true> 
	<#else> 
		<#assign score = score + calcScore(var3400)> 
	</#if> 
</#if>  
<#assign isQ3Complete = false> 
<#assign isQ3None = false> 
<#if isQ2Complete> 
	<#if isQ2None> 
		<#assign isQ3Complete = true> 
	<#else> 
		<#if (var3410.children)?? && ((var3410.children)?size > 0) > 
			<#if isSelectedAnswer(var3410, var2022)!false> 
				<#assign isQ3None = true> 
			<#else> 
				<#assign score = score + calcScore(var3410)> 
			</#if> 
			<#assign isQ3Complete = true> 
		</#if> 
	</#if> 
</#if>  
<#assign isQ4Complete = false> 
<#assign isQ4None = false> 
<#if isQ3Complete> 
	<#if isQ2None || isQ3None> 
		<#assign isQ4Complete = true> 
	<#else> 
		<#if (var3420.children)?? && ((var3420.children)?size > 0) > 
			<#if isSelectedAnswer(var3420, var2030)!false> 
				<#assign isQ4None = true> 
			<#else> 
				<#assign score = score + calcScore(var3420)> 
			</#if> 
			<#assign isQ4Complete = true> 
		</#if> 
	</#if> 
</#if>  
<#assign isQ5Complete = false> 
<#assign isQ5None = false> 
<#if isQ4Complete> 
	<#if isQ2None || isQ3None || isQ4None> 
		<#assign isQ5Complete = true> 
	<#else> 
		<#if (var3430.children)?? && ((var3430.children)?size > 0) > 
			<#if isSelectedAnswer(var3430, var2037)!false> 
				<#assign isQ5None = true> 
			<#else> 
				<#assign score = score + calcScore(var3430)> 
			</#if> 
			<#assign isQ5Complete = true> 
		</#if> 
	</#if> 
</#if>  
<#assign isComplete = false> 
<#if isQ2Complete && isQ3Complete && isQ4Complete && isQ5Complete> 
	<#assign isComplete = true> 
</#if>  
${MODULE_TITLE_START} 
	Traumatic Brain Injury (TBI) 
${MODULE_TITLE_END} 

${MODULE_START}  
	<#assign showRec = false> <#assign tbi_consult_text = ""> 
	<#if isComplete && (var2047.children)?? && ((var2047.children)?size > 0) && isSelectedAnswer(var2047,var3442)> 
		<#assign showRec = true> 
		<#assign tbi_consult_text = "You have requested further assessment"> 
	<#elseif isComplete && (var2047.children)?? && ((var2047.children)?size > 0) && isSelectedAnswer(var2047,var3441)> 
		<#assign showRec = true> 
		<#assign tbi_consult_text = "You have declined further assessment">
	</#if> 
	A TBI is physical damage to your brain, caused by a blow to the head. Common causes are falls, fights, sports, and car accidents. A blast or shot can also cause TBI. ${LINE_BREAK} 
	${LINE_BREAK} 
	<#if isComplete> 
		<b>Results:</b>${NBSP} 
		<#if (score >= 0) && (score <= 3)> 
			negative screen 
		<#elseif (score >= 4 )> 
			at risk 
		</#if> 
	</#if>  
	<#if isComplete && showRec> 
		${LINE_BREAK} 
		<b>Recommendation:</b> 
			${tbi_consult_text}. 
	</#if> 
${MODULE_END} ' 
where template_id=307;


/* Basic Pain veteran summary. Updating intervals (t744)*/
UPDATE template set template_file = ' 
<#include "clinicalnotefunctions"> 
<#if (var2300.children)??  &&  ((var2300.children)?size > 0)>
    ${MODULE_TITLE_START}
    Pain 
    ${MODULE_TITLE_END}
    ${GRAPH_SECTION_START}

        ${GRAPH_BODY_START}
          {
            "type": "stacked",
            "title": "My Pain Score",
            "footer": "",
            "data": {
                "ticks": [0,1,4,6,8,10],
                "intervals": {
					"None": 0,
                    "Mild": 1,
                    "Moderate": 4,
                    "Severe": 6,
                    "Very Severe":8,
                    "Worst Possible":10
                },
                "score": ${getSelectOneDisplayText(var2300)}
            }
          }
        ${GRAPH_BODY_END}
    ${GRAPH_SECTION_END}

    ${MODULE_START}
    Pain can slow healing and stop you from being active. Untreated pain can harm your sleep, outlook, and ability to do things. ${LINE_BREAK}
    <b>Recommendation:</b>${NBSP} 
    <#if getSelectOneDisplayText(var2300)?number < 4 >
        Seek medical attention if your pain suddenly increases or changes.
    <#else>
        Tell your clinician if medications aren\'t reducing your pain, or if the pain suddenly increases or changes, and ask for help with managing your pain. 
    </#if>
    ${MODULE_END}   

</#if>
' where template_id=309;

/* phq-9 veteran summary. Updating to intervals  (t744) */
update template set template_file = '<#include "clinicalnotefunctions"> 
<#assign score = getCustomVariableDisplayText(var1599)> 
<#-- Template start --> 
${MODULE_TITLE_START} Depression ${MODULE_TITLE_END} 
<#if score != "notfound">
	${GRAPH_SECTION_START} 
	${GRAPH_BODY_START} 
		{"type": "stacked", 
		 "title": "My Depression Score", 
		 "footer": "*a score of 10 or greater is a positive screen", 
		 "data": {
			"maxXPoint" : 27,
		 	"ticks": [0, 1, 5, 10, 15, 20, 27],
		 	"intervals": {
				"None":0,
		 		"Minimal":1, 
		 		"Mild":5, 
		 		"Moderate":10, 
		 		"Moderately Severe":15, 
		 		"Severe":20
		 	}, 
		 	"score": ${score} 
		 } 
		} 
	${GRAPH_BODY_END} 
	${GRAPH_SECTION_END}  
</#if>
${MODULE_START} 
	Depression is when you feel sad and hopeless for much of the time. It affects your body and thoughts, and interferes with daily life. There are effective treatments and resources for dealing with depression.
	${LINE_BREAK} 
	<b>Recommendation:</b> 
	<#if score = "notfound">
		${NBSP}Veteran declined to respond
	<#elseif score?number lt 10>
		Contact a clinician if in the future if you ever feel you may have a problem with depression
	<#elseif score?number gt 9>
		Ask your clinician for further evaluation and treatment options
	</#if> 
${MODULE_END}' 
where template_id=308;

/* PCL-C veteran summary. Updating intervals (t744) */
update template set template_file = '<#include "clinicalnotefunctions"> 
<#assign score = getCustomVariableDisplayText(var1929)>  
<#-- Template start --> 
${MODULE_TITLE_START} Post-traumatic Stress Disorder ${MODULE_TITLE_END}  

<#if score != "notfound">
	${GRAPH_SECTION_START}  
	${GRAPH_BODY_START} 
		{"type": "stacked", 
		 "title": "My PTSD Score", 
		 "footer": "", 
		 "data": {
		 	"maxXPoint" : 85,
		 	"ticks": [17,35,50,65,85], 
		 	"intervals": {
		 		"Negative":17, 
		 		"Positive":50 
		 	}, 
		 	"score": ${score} 
		 } 
		} 
	${GRAPH_BODY_END} 
	${GRAPH_SECTION_END}  
</#if>

${MODULE_START} 
	PTSD is when remembering a traumatic event keeps you from living a normal life. It\'s also called shell shock or combat stress. Common symptoms include recurring memories or nightmares of the event, sleeplessness, and feeling angry, irresistible, or numb. 
	${LINE_BREAK} 
	<b>Recommendation:</b> 
	<#if score = "notfound">
		${NBSP}Veteran declined to respond
	<#elseif score?number gt 49>
		Ask your clinician for further evaluation and treatment options
	<#elseif score?number lt 50>
		You may ask a clinician for help in the future if you feel you may have symptoms of PTSD
	</#if> 
${MODULE_END}' 
where template_id=310;

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
			<#if (Q1_Score > 1) >
				reported hearing things other people can\'t hear
			<#else>
				denied audio hallucinations
			</#if>
		</#if>
		
		<#if ((var1360.children)?? && (var1360.children?size > 0))>
		  <#assign Q2_Score = getScore(var1360)>
		  , The Veteran ${NBSP}		
			<#if (Q2_Score > 1) >
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

/***** t753 remove all ACSW ****/
update template
set template_file = '
<#include "clinicalnotefunctions">
<#-- Template start -->
${MODULE_START}

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="50%" valign="middle"><h2  style="color:#1b4164"><strong>eScreening Summary</strong></h2></td>
    <td width="50%" valign="top" align="right"><img width="198" height="66" src="resources/images/logo_va_veteran_summary.gif "> <img width="130" height="56" src="resources/images/cesamh_blk_border.png"><br></td>
  </tr>
</table>

  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom: 1px dashed #000000; border-top:1px dashed #000000;">
  <tr>

    <td width="50%" valign="top" style="border-right: 1px dashed #000000;">
        <div align="center" style="color:#1b4164"><h3> <strong>${getFreeTextAnswer(var630)} ${getFreeTextAnswer(var632)} ${getFreeTextAnswer(var634)}</strong></h3>

        <#if var2??>
        <strong>${getVariableDisplayText(var2)}</strong><br>
        </#if>

        <#if var7??>
        <strong>${getVariableDisplayText(var7)}</strong>
        <#else>
        Assessment is incomplete
        </#if>
    <br>

    </div></td>

    <td width="50%" valign="top"><h3 align="center"  style="color:#1b4164">Appointments</h3>


        <#if var6?? && (var6.children)?? >

            <#if ((var6.children)?size > 0) >
                <ul>
                ${delimitChildren(var6 "<li>" "</li>" true)}
                </ul>
            <#else>
                <div align="center"><h4>None scheduled</h4></div>
            </#if>
        <#else>
            <div align="center"><h4>Appointments unavailable</h4><div>
        </#if>
      </td>

  </tr>

</table>
<br>

<div>For questions or concerns, or for a full report of your results, call  the OEF/OIF/OND Transition Case Manager, Natasha Schwarz at (858) 642-3615.</div>

<div style="text-align: center; color:#1b4164"> <strong>If  you need medical attention immediately, go straight to the Emergency  Department.</strong></div><br>

<div><strong>Note:</strong> The results of this screening are NOT diagnoses and do not affect VA  disability ratings. </div>
${MODULE_END}'
where template_id = 200;

