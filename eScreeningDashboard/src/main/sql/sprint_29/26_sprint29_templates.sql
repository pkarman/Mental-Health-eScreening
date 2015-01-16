/* OOO CPRS Footer change for ticket 760 */

-- fix ptsd, depression and csra issues
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1599, 2);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1989, 2);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1929, 2);
-- fix gad-7 issue
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1749, 2);
-- fix insomnia because score's needed vars were not added
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2120, 2);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2130, 2);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2140, 2);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2150, 2);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2160, 2);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2170, 2);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2180, 2);

update template 
set template_file = '<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
INTERVENTIONS: 
${MODULE_TITLE_END}
${MODULE_START}
	
 	<#assign footer_section>

		<#assign outputText = "">
		<#assign errorText = "">
		<#assign incompleteText = "All of the required information has not been provided.">

		<#assign outputText = outputText + "${LINE_BREAK}*Explained confidentiality and the limits of confidentiality.${LINE_BREAK}">
		<#assign part = "">
		<#if (var1599.value)??>
			<#assign v1 = var1599.value> <#-- PHQ9 Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 10)>
					<#assign part = "*Conducted CSRA and other necessary follow-ups (see note(s) with same date)${LINE_BREAK}
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)${LINE_BREAK}">
				</#if>
			</#if>
		</#if>
        
		<#if (var1989.value)?? >
			<#assign v2 = var1989.value> <#-- PTSD Score -->  
			<#if (v2 != "notset")> 
				<#if (v2?number >= 3)>
					<#assign part = "*Conducted CSRA and other necessary follow-ups (see note(s) with same date)${LINE_BREAK}
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)${LINE_BREAK}">
				</#if>
			</#if>
		</#if>
		
		<#if (var1929.value)??>
			<#assign v3 = var1929.value> <#-- PCL Score -->  
			<#if (v3 != "notset")> 
				<#if (v3?number >= 50)>
					<#assign part = "*Conducted CSRA and other necessary follow-ups (see note(s) with same date)${LINE_BREAK}
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#assign outputText = outputText + "*Educated the Veteran on VA health care benefits and OEF/OIF care coordination services.${LINE_BREAK}
												*OEF/OIF Case Management not indicated at this time.${LINE_BREAK}">

		
		<#if ((var202.value)?? && var202.value == "true") || ((var9060.value)?? && var9060.value == "true")>
				<#assign outputText = outputText + "*Veteran endorsed mental health concerns, recommend outpatient consult to Psychiatry for MH.${LINE_BREAK}"> 
		</#if>
		
		<#if ((var215.value)?? && var215.value == "true") || ((var9066.value)?? && var9066.value == "true")>
				<#assign outputText = outputText + "*Veteran endorsed substance abuse concerns, recommend outpatient consult to SARRTP.${LINE_BREAK}"> 
		</#if>
		
		<#if ((var212.value)?? && var212.value == "true") || ((var9063.value)?? && var9063.value == "true")>
				<#assign outputText = outputText + "*Discuss  Veteran\'s prosthetics needs.${LINE_BREAK}"> 
		</#if>

		<#if ((var65.value)?? && var65.value == "true") || ((var9086.value)?? && var9086.value == "true")>
				<#assign outputText = outputText + "*Provided information on Vocational Rehabilitation and WAVE Clinic.${LINE_BREAK}"> 
		</#if>
		
		<#if ((var432.value)?? && var432.value == "true") || ((var9092.value)?? && var9092.value == "true")>
				<#assign outputText = outputText + "*Veteran requested Chaplain consult.${LINE_BREAK}"> 
		</#if>

		<#if (var1229.value)??>
			<#assign v1 = var1229.value> <#-- AUDICT C Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 4)>
					<#assign part = "*Recommend SARRTP referral for alcohol abuse.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">


		<#if (var1010.value)??>
			<#assign v1 = var1010.value> <#-- DAST Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 3)>
					<#assign part = "*Recommend SARRTP referral for Substance Abuse.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if ((var603.value)?? && var603.value == "true") || ((var9122.value)?? && var9122.value == "true")>
				<#assign outputText = outputText + "*Recommend tobacco cessation.${LINE_BREAK}"> 
		</#if>

		<#if ((var3442.value)?? && var3442.value == "true") || ((var9132.value)?? && var9132.value == "true")>
				<#assign outputText = outputText + "*Veteran requested TBI consult.${LINE_BREAK}"> 
		</#if>

		<#if (var1599.value)??>
			<#assign v1 = var1599.value> <#-- PHQ9 Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 10)>
					<#assign part = "*Recommend psychiatry outpatient consult for depression.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>
		
		<#assign outputText = outputText + part>
		<#assign part = "">


		<#if (var2809.value)??>
			<#assign v1 = var2809.value> <#-- MDQ Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 7)>
					<#if (var2792?? && var2792.value == "true") || (var9142?? && var9142.value == "true")>
						<#if (var2803?? && var2803.value == "true") || (var2804?? && var2804.value == "true") >
								<#assign part = "*Recommend psychiatry outpatient consult for mood.${LINE_BREAK}">
						</#if>
					</#if>
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if ((var1642.value)?? && var1642.value == "true") || ((var9172.value)?? && var9172.value == "true")>
				<#assign outputText = outputText + "*Recommend MST consult.${LINE_BREAK}"> 
		</#if>

		
		<#if (var1749.value)??>
			<#assign v1 = var1749.value> <#-- GAD7 Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 10)>
					<#assign part = "*Recommend psychiatry outpatient consult for anxiety.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if (var1989.value)?? >
			<#assign v2 = var1989.value> <#-- PTSD Score -->  
			<#if (v2 != "notset")> 
				<#if (v2?number >= 3)>
					<#assign part = "*Recommend OEF/OIF PTSD clinic consult.${LINE_BREAK}">
				</#if>
			</#if>
		<#elseif (var1929.value)??>
			<#assign v3 = var1929.value> <#-- PCL Score -->  
			<#if (v3 != "notset")> 
				<#if (v3?number >= 50)>
					<#assign part = "*Recommend OEF/OIF PTSD clinic consult.${LINE_BREAK}">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#-- ISI Score --> 
		<#if (var2189.value)?? && (var2189.value?string != "notset") && (var2189.value?number >= 15.0) > 
			<#assign outputText = outputText + "*Recommend psychiatry outpatient consult for insomnia.${LINE_BREAK}">
		</#if>

		<#if ((var3233.value)?? && var3233.value == "true") || ((var9203.value)?? && var9203.value == "true")
				|| ((var3234.value)?? && var3234.value == "true") || ((var9204.value)?? && var9204.value == "true")
				|| ((var3235.value)?? && var3235.value == "true") || ((var9205.value)?? && var9205.value == "true")
				|| ((var3313.value)?? && var3313.value == "true") || ((var9213.value)?? && var9213.value == "true")
				|| ((var3314.value)?? && var3314.value == "true") || ((var9214.value)?? && var9214.value == "true")
				|| ((var3315.value)?? && var3315.value == "true") || ((var9215.value)?? && var9215.value == "true")
				|| ((var3323.value)?? && var3323.value == "true") || ((var9223.value)?? && var9223.value == "true")
				|| ((var3324.value)?? && var3324.value == "true") || ((var9224.value)?? && var9224.value == "true")
				|| ((var3325.value)?? && var3325.value == "true") || ((var9225.value)?? && var9225.value == "true")>
				
				<#assign outputText = outputText + "*Recommend Anger Management consult.${LINE_BREAK}"> 
		</#if>

		<#if ((var81.value)?? && var81.value == "true") || ((var9231.value)?? && var9231.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran employment resource information.${LINE_BREAK}"> 
		</#if>

		<#if ((var242.value)?? && var242.value == "true") || ((var9251.value)?? && var9251.value == "true")
				|| ((var772.value)?? && var772.value == "true") || ((var9262.value)?? && var9262.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on housing resources and the VA homeless resources including HCHV outreach schedule.${LINE_BREAK}"> 
		</#if>

		<#if ((var402.value)?? && var402.value == "true") || ((var9272.value)?? && var9272.value == "true")>
				
				<#assign outputText = outputText + "*Educated the Veteran on chaplain services.${LINE_BREAK}"> 
		</#if>

		<#if ((var262.value)?? && var262.value == "true") || ((var9282.value)?? && var9282.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided information on financial resources.${LINE_BREAK}"> 
		</#if>

		<#if ((var301.value)?? && var301.value == "true") || ((var9301.value)?? && var9301.value == "true")
				|| ((var302.value)?? && var302.value == "true") || ((var9302.value)?? && var9302.value == "true")
				|| ((var303.value)?? && var303.value == "true") || ((var9303.value)?? && var9303.value == "true")
				|| ((var304.value)?? && var304.value == "true") || ((var9304.value)?? && var9304.value == "true")
				|| ((var305.value)?? && var305.value == "true") || ((var9305.value)?? && var9305.value == "true")
				|| ((var306.value)?? && var306.value == "true") || ((var9306.value)?? && var9306.value == "true")
				|| ((var307.value)?? && var307.value == "true") || ((var9307.value)?? && var9307.value == "true")
				|| ((var308.value)?? && var308.value == "true") || ((var9308.value)?? && var9308.value == "true")
				|| ((var309.value)?? && var309.value == "true") || ((var9309.value)?? && var9309.value == "true")
				|| ((var310.value)?? && var310.value == "true") || ((var9310.value)?? && var9310.value == "true")
				|| ((var311.value)?? && var311.value == "true") || ((var9311.value)?? && var9311.value == "true")
				|| ((var312.value)?? && var312.value == "true") || ((var9312.value)?? && var9312.value == "true")>
				
				<#assign outputText = outputText + "*Provided information on legal resources.${LINE_BREAK}"> 
		</#if>

		<#if ((var306.value)?? && var306.value == "true") || ((var9306.value)?? && var9306.value == "true")
				|| ((var307.value)?? && var307.value == "true") || ((var9307.value)?? && var9307.value == "true")
				|| ((var308.value)?? && var308.value == "true") || ((var9308.value)?? && var9308.value == "true")
				|| ((var309.value)?? && var309.value == "true") || ((var9309.value)?? && var9309.value == "true")
				|| ((var310.value)?? && var310.value == "true") || ((var9310.value)?? && var9310.value == "true")>
				
				<#assign outputText = outputText + "*Educated Veteran about Veteran\'s Justice Outreach Program.${LINE_BREAK}"> 
		</#if>

		<#if ((var253.value)?? && var253.value == "true") || ((var9323.value)?? && var9323.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9333.value)?? && var9333.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on the GI Bill, eBenefits, VA Work Study.${LINE_BREAK}"> 
		</#if>

		<#if ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on accessing unemployment resources.${LINE_BREAK}"> 
		</#if>

		<#if ((var603.value)?? && var603.value == "true") || ((var9122.value)?? && var9122.value == "true")>
				<#assign outputText = outputText + "*Educated Veteran on Tobacco cessation support services.${LINE_BREAK}"> 
		</#if>

		<#if ((var216.value)?? && var216.value == "true") || ((var9346.value)?? && var9346.value == "true")
				|| ((var1432.value)?? && var1432.value == "true") || ((var9352.value)?? && var9352.value == "true")
				|| ((var1433.value)?? && var1433.value == "true") || ((var9363.value)?? && var9363.value == "true")
				|| ((var942.value)?? && var942.value == "true") || ((var9372.value)?? && var9372.value == "true")
				|| ((var943.value)?? && var943.value == "true") || ((var9373.value)?? && var9373.value == "true")
				|| ((var932.value)?? && var932.value == "true") || ((var9382.value)?? && var9382.value == "true")
				|| ((var933.value)?? && var933.value == "true") || ((var9383.value)?? && var9383.value == "true")>
				
				<#assign outputText = outputText + "*Provided information on Audiology and Optometry services.${LINE_BREAK}"> 
		</#if>

		<#if ((var252.value)?? && var252.value == "true") || ((var9392.value)?? && var9392.value == "true")
				|| ((var1513.value)?? && var1513.value == "true") || ((var9403.value)?? && var9403.value == "true")>
				
				<#assign outputText = outputText + "*Encouraged the Veteran to meet with VSO for further advocacy and assistance with VA SCD claims process & provided VSO contact list.${LINE_BREAK}"> 
		</#if>

		<#if ((var821.value)?? && var821.value == "true") || ((var9421.value)?? && var9421.value == "true")
				|| ((var828.value)?? && var828.value == "true") || ((var9432.value)?? && var9432.value == "true")>
				
				<#assign outputText = outputText + "*Provided VA Advanced Directives brochure & form.${LINE_BREAK}"> 
		</#if>

			<#assign outputText = outputText + "*Educated Veteran on available VA mental health services (including outpatient general and specialty psychiatry clinics, PTSD and MST services, Anger Management, CORE and Family MH Clinic).${LINE_BREAK}">
			<#assign outputText = outputText + "*Provided the Veteran information on the VA Vet Centers services.${LINE_BREAK}">
			<#assign outputText = outputText + "*Educated the Veteran on alcohol and drug treatment services and reviewed ADTP walk-in hours & location.${LINE_BREAK}">
			<#assign outputText = outputText + "*Alerted the PCP to the Veteran medical/health concerns & requests.${LINE_BREAK}">
			<#assign outputText = outputText + "*Directed Veteran to Primary Care Call center for appointment scheduling.${LINE_BREAK}">
			<#assign outputText = outputText + "*Provided VA environmental registry POC information.${LINE_BREAK}">
			<#assign outputText = outputText + "*Educated the Veteran about same-day access clinic on 2North.${LINE_BREAK}">
			<#assign outputText = outputText + "*Reviewed available resources including PEC, Emergency Dept., VA-Telecare and Suicide hotlines.${LINE_BREAK}">
			<#assign outputText = outputText + "*Contact information of this writer, OEF/OIF brochure, and suicide prevention card were given to the Veteran. ${NBSP}Encouraged to call for any further questions or concerns.${LINE_BREAK}${LINE_BREAK}">
			<#assign outputText = outputText + "PLAN:${LINE_BREAK}">
			<#assign outputText = outputText + "*Veteran plans to continue to access physical and mental healthcare through VA San Diego Healthcare System as needed.${LINE_BREAK}">
			<#assign outputText = outputText + "*Veteran\'s Future Appointments:${LINE_BREAK}">
			<#assign outputText = outputText + "*Veteran agreed to the following consults:${LINE_BREAK}">
			<#assign outputText = outputText + "*This writer will remain available to assist Veteran with transition and accessing VA Healthcare services as needed.${LINE_BREAK}">
			<#assign outputText = outputText + "*Veteran to contact this writer for resources, referrals and other VA related healthcare inquiries.${LINE_BREAK}">

		<#if errorText?has_content>
			${errorText}
		<#else>
			${outputText}
		</#if>
	</#assign>
	
	<#if !(footer_section = "") >
     	${footer_section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}'
where template_id = 2;

-- Fix issue with Service Injuries cprs module template for ticket 760 
update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Service Injuries:
${MODULE_TITLE_END}
${MODULE_START}
		
		<#assign answerTextHash = {"var1380":"blast injury", "var1390":"injury to spine or back", "var1400":"burn (second, 3rd degree)", 
									"var1410":"nerve damage", "var1420":"loss or damage to vision", "var1430":"loss or damage to hearing", 
									"var1440":"amputation", "var1450":"broken/fractured bone(s)", "var1460":"joint or muscle damage", 
									"var1470":"internal or abdominal injuries", "var1480":"other", "var1490":"other"}>
		
		<#-- must have answer questions -->
		<#assign questions1 = [var1380, var1390, var1400, var1410, var1420, var1430, var1440, var1450, var1460, var1470]>

		<#-- organize answers in a way to facilitate output -->
		<#assign noneAnswers = []>
		<#assign combatAnswers = []>
		<#assign nonCombatAnswers = []>
		<#assign otherAnswers = []>
		<#list questions1 as q>
			<#if q?? && q.children?? && (q.children?size > 0) && (q.children[0])?? >
				<#assign key = (q.key)!"">
                <#list q.children as c>
					<#assign text = (c.overrideText)!""> 
					<#if text == "none">
						<#assign answer = (answerTextHash[key])!"">
						<#assign noneAnswers = noneAnswers + [answer]> 
					<#elseif text == "combat">
						<#assign answer = (answerTextHash[key])!"">
						<#assign combatAnswers = combatAnswers + [answer]> 
					<#elseif text == "non-combat">
						<#assign answer = (answerTextHash[key])!"">
						<#assign nonCombatAnswers = nonCombatAnswers + [answer]> 
					<#elseif text == "other">
						<#assign answer = (answerTextHash[key])!"">
						<#assign otherAnswers = otherAnswers + [answer]> 
					</#if>
				</#list>
			</#if>
		</#list>

					<#if var1480?? && var1481?? && (var1480.children?size > 0)>
						<#assign otherAnswer = var1481.otherText!"">
						<#if otherAnswer != "">
						<#list var1480.children as c>
							<#if (c.overrideText == "combat")>
								<#-- put in proper anser bucket -->
								<#assign combatAnswers = combatAnswers + [otherAnswer]> 
							<#elseif (c.overrideText == "non-combat")>
								<#-- put in proper anser bucket -->
								<#assign nonCombatAnswers = nonCombatAnswers + [otherAnswer]> 
							</#if>
						</#list>
						</#if>
					</#if>
				
					<#if var1490?? && var1491?? && (var1490.children?size > 0)>
						<#assign otherAnswer = var1491.otherText!"">
						<#if otherAnswer != "">
						<#list var1490.children as c>
							<#if (c.overrideText == "combat")>
								<#-- put in proper anser bucket -->
								<#assign combatAnswers = combatAnswers + [otherAnswer]> 
							<#elseif (c.overrideText == "non-combat")>
								<#-- put in proper anser bucket -->
								<#assign nonCombatAnswers = nonCombatAnswers + [otherAnswer]> 
							</#if>
						</#list>
						</#if>
					</#if>	
		
		
		<#-- build first two sentences -->
		<#if combatAnswers?has_content>
			The following injuries were reported during combat deployment: ${createSentence(combatAnswers)}.${LINE_BREAK}
		</#if>
		
		<#if nonCombatAnswers?has_content>
			The following injuries were reported during other service period or training: ${createSentence(nonCombatAnswers)}.${LINE_BREAK}
		</#if>
		
		<#if var1510?? && (var1510.children)?? && (var1510.children?size>0)>
			<#assign frag = "">
			<#if isSelectedAnswer(var1510,var1512)>
				<#assign frag = "has">
			<#elseif isSelectedAnswer(var1510,var1511)>
				<#assign frag = "does not have">
			<#elseif isSelectedAnswer(var1510,var1513)>
				<#assign frag = "has the intent to file for, or has filed for">
			</#if>
			The Veteran ${frag} a service connected disability rating.
		</#if>
${MODULE_END}'
where template_id = 16;

-- fix extra "pain" word stuck in for no reason (for ticket 760)
update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
Screen for Infectious Disease and Embedded Fragments:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var2500.children)?? && ((var2500.children)?size > 0) || ((var2501.children)?? && ((var2501.children)?size > 0))
	 || ((var2502.children)?? && ((var2502.children)?size > 0)) || ((var2009.children)?? && ((var2009.children)?size > 0))>

	<#-- ${createSentence(parts)}. -->
	<#assign questions = [var2500,var2501,var2502,var2009]>
	<#assign textHash = {"var2500": "chronic diarrhea or other gastrointestinal", 
							"var2501": "unexplained fevers", 
							"var2502": "persistent papular or nodular skin rash that began after deployment to Southwest Asia", 
							"var2009": "suspects that he/she has retained fragments or shrapnel as a result of injuries"}>
	
	<#assign parts = []>
		
	<#list questions as q >
		<#if q.children?? && ((q.children)?size>0)>
			<#assign child = q.children[0] >
			<#if ((child.calculationValue?number) > 0)>
				<#assign problem = textHash[q.key] >
				<#assign part = problem + ", with symptoms of " + getVariableDisplayText(child) >
				<#assign parts = parts + [part] >
			</#if>
		</#if>
	</#list>
	
	<#if parts?has_content>
		The Veteran endorsed other health problems of: ${createSentence(parts)}. ${NBSP}
	<#else>
		${getAnsweredNoAllText("OOO Infect & Embedded Fragment CR")}. ${NBSP}
	</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
${MODULE_END}'
where template_id = 19;

/** Ticket 771 Below ****/
update template set template_file = 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
A/V HALLUCINATIONS:
${MODULE_TITLE_END}
${MODULE_START}
	<#if (var1350.children)?? && (var1350.children?size > 0) || ((var1360.children)?? && (var1360.children?size > 0))>  		
The Veteran${NBSP}
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
