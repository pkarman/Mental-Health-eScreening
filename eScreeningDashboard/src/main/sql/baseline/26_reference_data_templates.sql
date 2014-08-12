/* ********************************************** */
/* Create templates   */
/* ********************************************** */

/* Introduction */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (1, 1, 'OOO Battery CPRS Note Introduction', 'OOO Battery CPRS Note Introduction',
'<#include "clinicalnotefunctions"> 
${MODULE_TITLE_START}
INTRODUCTION: 
${MODULE_TITLE_END}
<#-- Template start -->
${MODULE_START}
  <#assign section1>
    <#if hasValue(getCustomVariableDisplayText(var1)) >
      The Veteran presented to enroll in the VA Healthcare System and consented to be screened using the electronic version v${getCustomVariableDisplayText(var1)} of the Post-911 Screening Packet.${NBSP}   
    </#if> 
    <#if hasValue(getCustomVariableDisplayText(var2)) >
      The eScreening was administered by ${getCustomVariableDisplayText(var2)}.${NBSP}
    </#if>
    <#if hasValue(getCustomVariableDisplayText(var3)) >
      The results were used to auto-generate a CPRS note and reviewed by ${getCustomVariableDisplayText(var3)}.${NBSP}
    </#if>
  </#assign>
  <#if !(section1 = "") >
     ${section1}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}');
INSERT INTO battery_template (battery_id, template_id) VALUES (5, 1);




/* Footer */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (2, 2, 'OOO Battery CPRS Note Footer', 'OOO Battery CPRS Note Footer',
'
<#include "clinicalnotefunctions"> 
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
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)">
				</#if>
			</#if>
		</#if>
		
		<#if (var1989.value)?? >
			<#assign v2 = var1989.value> <#-- PTSD Score -->  
			<#if (v2 != "notset")> 
				<#if (v2?number >= 3)>
					<#assign part = "*Conducted CSRA and other necessary follow-ups (see note(s) with same date)${LINE_BREAK}
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)">
				</#if>
			</#if>
		</#if>
		
		<#if (var1929.value)??>
			<#assign v3 = var1929.value> <#-- PCL Score -->  
			<#if (v3 != "notset")> 
				<#if (v3?number >= 50)>
					<#assign part = "*Conducted CSRA and other necessary follow-ups (see note(s) with same date)${LINE_BREAK}
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)">
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
						<#if (var2803?? && var2803.value == "true") || (var9153?? && var9153.value == "true")
							|| (var2804?? && var2804.value == "true") || (var9154?? && var9154.value == "true")>
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

		<#if (var2189.value)??>
			<#assign v1 = var2189.value> <#-- ISI Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 15)>
					<#assign part = "*Recommend psychiatry outpatient consult for insomnia.${LINE_BREAK}">
				</#if>
			</#if>
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
${MODULE_END}
');
INSERT INTO battery_template (battery_id, template_id) VALUES (5, 2);





/* Presenting Concerns */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (3, 3, 'Presenting Concerns CPRS Note Entry', 'Presenting Concerns Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PRESENTING CONCERN(S):
${MODULE_TITLE_END}
${MODULE_START}
  <#assign concerns_section> 

	

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

	<#if hasValue(var210) >      
		<#assign healthCareText = "Healthcare (specifically, " + getSelectMultiDisplayText(var210) + ")">
		<#assign fragments = fragments + [healthCareText] >
	</#if>

	<#if hasValue(var250) >      
		<#assign vaBeneifitsText = "VA Benefits (specifically, " + getSelectMultiDisplayText(var250) + ")" >
		<#assign fragments = fragments + [vaBeneifitsText] >
	</#if>

	<#if hasValue(var220) >      
		<#assign employmentText = "Employment (specifically, " + getSelectMultiDisplayText(var220) + ")">
		<#assign fragments = fragments + [employmentText] >
	</#if>

	<#if hasValue(var230) >      
		<#assign SocialText = "Social (specifically, " + getSelectMultiDisplayText(var230) + ")">
		<#assign fragments = fragments + [SocialText]  >
	</#if>

	<#if hasValue(var270) >      
		<#assign legalText = "Legal (specifically, " + getSelectMultiDisplayText(var270) + ")" >
		<#assign fragments = fragments + [legalText]  >
	</#if>

	<#if hasValue(var240) >      
		<#assign housingText = "Housing (specifically, " + getSelectMultiDisplayText(var240) + ")">
		<#assign fragments = fragments + [housingText] >
	</#if>

	
  	The Veteran indicated that he/she would like information or assistance with the following: ${createSentence(fragments)}.${NBSP}
    
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(concerns_section = "") >
     ${concerns_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}'
);

INSERT INTO survey_template (survey_id, template_id) VALUES (2, 3);

/* Basic Demographics */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (4, 3, 'Basic Demographics CPRS Note Entry', 'Basic Demographics Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
DEMOGRAPHICS: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign demo_section>
	
	<#-- var30: ${var30}<br><br> var40: ${var40}<br><br> var20: ${var20}<br><br> var143: ${var143}<br><br> --> <#-- test objs -->
	
	<#if (var30.children)?? && (var40.children)?? && (var20.children)?? && (var143.children)??
			&& (var30.children?size > 0) && (var40.children?size > 0) && (var20.children?size > 0) && (var143.children?size > 0)	>

		<#assign age = "">
		<#if var143?? >
			<#assign age = calcAge(var143.children[0].value)>
		</#if>
	
  	
		<#-- The Veteran is a 28 year-old hispanic. -->
    
		The Veteran is a ${age} year-old
        
		<#if isSelectedAnswer(var30,var33) >
          ,  ${NBSP}
        <#else>
          ${""?left_pad(1)}whom is ${getSelectOneDisplayText(var30)}. ${NBSP}
        </#if> 
    
    <#-- The Veteran reports being a White/Caucasian, American Indian or Alaskan Native male. -->
    <#if hasValue(getSelectMultiDisplayText(var40)) || hasValue(getSelectOneDisplayText(var20)) >
      The Veteran reports being
      <#if hasValue(getSelectMultiDisplayText(var40)) >
        ${""?left_pad(1)}a ${getSelectMultiDisplayText(var40)}
      </#if> 
      <#if hasValue(getSelectOneDisplayText(var20)) >
        ${""?left_pad(1)}${getSelectOneDisplayText(var20)}
      </#if> 
      .  ${NBSP}
    </#if> 
   
    <#--The Veteran reported BMI is 27, indicating that he/she may be is overweight.-->
    <#if hasValue(getFormulaDisplayText(var11))  >
      <#assign num = getFormulaDisplayText(var11)?number />
      The Veteran\'s reported BMI is ${num}, indicating that he/she 
      <#if (num < 18.5) >
        is below a normal a weight.
      <#elseif (num < 25) && (num >= 18.5) >
        is at a normal weight. 
      <#elseif (num < 30) && (num >= 25) >
        is overweight.  
      <#elseif (num < 40) && (num >= 30) >
        is obese.  
      <#elseif (num >= 40) >
        is morbid obese.  ${NBSP}
      </#if>
    </#if>
    
    <#else>
    	${getNotCompletedText()}
    </#if>
  </#assign>
  <#if !(demo_section = "") >
     ${demo_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (3, 4);

/* Education, Employment & Income */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (5, 3, 'Education, Employment & Income CPRS Note Entry', 'Education, Employment & Income Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
EDUCATION, EMPLOYMENT AND INCOME: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign ed_section>
	<#-- var50: ${var50}<br><br>  var60: ${var60}<br><br>  var70: ${var70!""}<br><br>   var80: ${var80!""}<br><br>  var100: ${var100}<br><br> --> 
  	
<#if (var50.children)?? && (var60.children)?? && (var70.children)?? && (var80.children)?? && (var100.children)??
		&& ((var50.children)?size > 0) && ((var60.children)?size > 0) && ((var70.children)?size > 0)  
		&& ((var80.children)?size > 0) && ((var100.children)?size > 0)>
    
    <#-- The Veteran reported completing some high school. -->
    <#if hasValue(getSelectOneDisplayText(var50)) >
      The Veteran reported completing ${getSelectOneDisplayText(var50)}. ${NBSP}
    </#if> 
    <#-- The Veteran reported being currently an employed, who usually works as retail. -->
    <#if hasValue(getSelectOneDisplayText(var60)) && hasValue(getSelectOneDisplayText(var70)) >
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}, who usually works as an ${getFreeformDisplayText(var70)}.  ${NBSP}
    <#elseif hasValue(getSelectOneDisplayText(var60)) && !(hasValue(getFreeformDisplayText(var70)))>
      The Veteran reported being currently ${getSelectOneDisplayText(var60)}.  ${NBSP}
    <#elseif !(hasValue(getSelectOneDisplayText(var60))) && hasValue(getFreeformDisplayText(var70)) >
      The Veteran reported usually working as an ${getFreeformDisplayText(var70)}.  ${NBSP}
    </#if>
    <#--The Veteran reported that the primary source of income is work, and disability.  -->
    <#if var80?? >
      The Veteran reported that 
      <#if wasAnswerNone(var80)>
        they do not have any income. ${NBSP}
      <#else>
        their income is derived from ${getSelectMultiDisplayText(var80)}. ${NBSP}
      </#if> 
    </#if> 
    <#--The Veteran is married.-->
    <#if hasValue(getSelectOneDisplayText(var100)) >
      The Veteran is ${getSelectOneDisplayText(var100)}. ${NBSP}
    </#if> 
   
   	<#else>
   		${getNotCompletedText()}
   	</#if>
  </#assign>
  <#if !(ed_section = "") >
     ${ed_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);
INSERT INTO survey_template (survey_id, template_id) VALUES (4, 5);

/* Social Environment */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (6, 3, 'Social Environment CPRS Note Entry', 'Social Environment Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SOCIAL: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign social_section>
	<#if (var90.children)?? && (var160.children)??  && (var130.children)??  
			&& ((var90.children)?size > 0) && ((var160.children)?size > 0) && ((var130.children)?size > 0)>
    <#--The Veteran lives in a house with spouse or partner, and children.-->
    <#if hasValue(getSelectOneDisplayText(var110)) && hasValue(getSelectMultiDisplayText(var90)) >
      The Veteran ${getSelectOneDisplayText(var110)}  
      <#if wasAnswerNone(var90)>
        alone.  ${NBSP}
      <#else> 
        with ${getSelectMultiDisplayText(var90)}. ${NBSP}
      </#if>
    <#elseif hasValue(getSelectOneDisplayText(var110)) && !(hasValue(getSelectMultiDisplayText(var90)))>
      The Veteran ${getSelectOneDisplayText(var110)}.  ${NBSP}
    <#elseif !(hasValue(getSelectOneDisplayText(var110))) && hasValue(getSelectMultiDisplayText(var90)) >
     The Veteran lives with ${getSelectMultiDisplayText(var90)}. ${NBSP}
    </#if>
    <#--The Veteran reported not having any children.-->
    <#if hasValue(getAnswerDisplayText(var131)) >
      <#if wasAnswerTrue(var131) >
        ${getAnswerDisplayText(var131)}  
      <#else>
        <#--The Veteran has 2 child(ren) who are child(ren) are 2,4 years old.-->
        <#if !(getNumberOfTableResponseRows(var130) = -1) >
          <#if (getNumberOfTableResponseRows(var130) = 0) >
            The Veteran reported not having any children. ${NBSP}
          <#elseif (getNumberOfTableResponseRows(var130) = 1) >
            The Veteran has 1 child who is ${getTableMeasureDisplayText(var130)} years old. ${NBSP}
          <#elseif (getNumberOfTableResponseRows(var130) > 1) >
            The Veteran has ${getNumberOfTableResponseRows(var130)} children who are ${getTableMeasureDisplayText(var130)} years old. ${NBSP}
          </#if>
        </#if>
      </#if>
    </#if>
    
    <#if hasValue(getSelectMultiDisplayText(var160)) >
    	${LINE_BREAK}${LINE_BREAK}Source(s) of support is/are: ${getSelectMultiDisplayText(var160)}. ${NBSP}
    </#if>
    
    <#--The Veteran indicated a history of physical violence .-->
    <#if hasValue(getSelectOneDisplayText(var150)) >
    	${LINE_BREAK}${LINE_BREAK}The Veteran ${getSelectOneDisplayText(var150)} a history of physical violence or intimidation in a current relationship. ${NBSP}
    </#if>
   
    <#else>
    	${getNotCompletedText()}
    </#if>
    
    
  </#assign>
  <#if !(social_section = "") >
     ${social_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);
INSERT INTO survey_template (survey_id, template_id) VALUES (5, 6);



/* Promis Emotional Support */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (7, 3, 'Promis Emotional Support CPRS Note Entry', 'Promis Emotional Support Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PROMIS EMOTIONAL SUPPORT: 
${MODULE_TITLE_END}
${MODULE_START}
<#assign promis_section>
	<#if (var700.children)?? &&  (var710.children)?? &&  (var720.children)?? && (var730.children)??
		&& ((var700.children)?size > 0) &&  ((var710.children)?size > 0) &&  ((var720.children)?size > 0) && ((var730.children)?size > 0)>

	<#assign num = getFormulaDisplayText(var739!("-1"?number))>
  	<#-- <#assign score = getListScore([var700, var710, var720, var730])![]>${LINE_BREAK}  -->
	<#if var739??>
		<#assign score = getCustomVariableDisplayText(var739)?number>
	<#else>
		<#assign score = -1>
	</#if>

  	<#if score?? && (score >= 0) && (score < 11) >
  		<#assign avgText = "lower than average">
  	<#elseif score?? && (score >= 11) && (score <= 998)>
  		<#assign avgText = "average">
	<#else>
		<#assign avgText = "notset">
  	</#if>
  	
  	The Veteran had a score of ${score}, indicating that they currently have ${avgText} emotional support.${NBSP}
  <#else>
  	${getNotCompletedText()}
  </#if>
  </#assign>
  <#if !(promis_section = "") >
     ${promis_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (6, 7);



/* Homelessness Clinical Reminder */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (8, 3, 'Homelessness CR CPRS Note Entry', 'Homelessness Clinical Reminder Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
HOUSING: 
${MODULE_TITLE_END}
${MODULE_START}

  <#assign housing_section>

	<#-- var763: ${var763}<br><br>     var2000: ${var2000!""}<br><br> var2001: ${var2001!""}<br><br>  var2002: ${var2002!""}<br><br>  -->  
  	<#if (var2000.children)?? && ((var2000.children)?size > 0)  >
		
		
		<#if (var2000.children[0].key == "var763") >  <#-- declined -->
				
				The Veteran declined to discuss their current housing situation.
		
		<#elseif (var2000.children[0].key == "var761")
				&& (var2002.children)?? && (var2008.children)?? 
					&& ((var2002.children)?size > 0) && ((var2008.children)?size > 0) >   <#-- no -->
	
				The Veteran ${getSelectOneDisplayText(var2000)} been living in stable housing for the last 2 months. ${LINE_BREAK}
				The Veteran has been living ${getSelectOneDisplayText(var2002)}.${LINE_BREAK}
				The Veteran ${getSelectOneDisplayText(var2008)} like a referral to talk more about his/her housing concerns.${NBSP}
				
		
		<#elseif (var2000.children[0].key == "var762")
				&& (var2001.children)?? && ((var2001.children)?size > 0) >   <#-- yes -->
				
				<#assign allComplete = false>
				<#assign showQ = false>

				<#if var2001.children[0].key == "var772">
					<#if (var2002.children)?? &&  ((var2002.children)?size > 0)
							&& (var2008.children)?? &&  ((var2008.children)?size > 0)>
						<#assign showQ = true>
						<#assign allComplete = true>
					</#if>
				<#elseif var2001.children[0].key == "var771">
					<#assign allComplete = true>
				<#else>
					<#assign allComplete = true>
				</#if>

				<#if allComplete>
					The Veteran ${getSelectOneDisplayText(var2000)} been living in stable housing for the last 2 months. ${LINE_BREAK}
					<#if showQ>
						The Veteran has been living ${getSelectOneDisplayText(var2002)}.${LINE_BREAK}
					</#if>
					The Veteran ${getSelectOneDisplayText(var2001)} concerned about housing in the future.${LINE_BREAK}
					<#if showQ>
						The Veteran ${getSelectOneDisplayText(var2008)} like a referral to talk more about his/her housing concerns.
					</#if>
				<#else>
					${getNotCompletedText()}.
				</#if>
		<#else>
				${getNotCompletedText()}.
		</#if>


    <#else>
		${getNotCompletedText()}
	</#if> 

  </#assign>
  <#if !(housing_section = "") >
     ${housing_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
');

INSERT INTO survey_template (survey_id, template_id) VALUES (7, 8);




/* Pragmatic Concerns section (Legal)*/  
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (9, 3, 'Pragmatic Concerns CPRS Note Entry', 'Pragmatic Concerns Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
LEGAL: 
${MODULE_TITLE_END}
${MODULE_START}
	
  <#assign legal_section>
  <#if (var300.children)?? && ((var300.children)?size > 0) >

    <#if hasValue(getSelectMultiDisplayText(var300)) >
      The Veteran reported the following legal concern(s): ${getSelectMultiDisplayText(var300)}.  ${NBSP}
    </#if>
    <#if hasValue(getSelectOneDisplayText(var320)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var320), "yes") >
      	The Veteran reported having an Advance Directive.  ${NBSP}
      </#if>
    </#if>
    <#if hasValue(getSelectOneDisplayText(var330)) >
      <#if doesValueOneEqualValueTwo(getSelectOneDisplayText(var330), "yes") >
      	The Veteran would like information about an Advance Directive.  ${NBSP}
      </#if>
    </#if>  
    
    
   <#else>
    	${getNotCompletedText()}
   </#if>
  </#assign>
  <#if !(legal_section = "") >
     ${legal_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (8, 9);


/* Advance Directive */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (10, 3, 'Advance Directive CPRS Note Entry', 'Advance Directive Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
ADVANCE DIRECTIVE:
${MODULE_TITLE_END}
${MODULE_START}
  <#-- var800: ${var800}<br><br>var820: ${var820}<br><br> var826: ${var826}<br><br> --> <#-- test objs -->
  <#assign nextLine = "">
  <#assign advance_dir_section> 
  	<#if (var800.children)?? && (var800.children)?? && (var800.children)??  
			&& (var800.children?size > 0) && (var820.children?size > 0) && (var826.children?size > 0)>
    <#if hasValue(getSelectMultiDisplayText(var800)) >
    	<#-- In what language do you prefer to get your health information? -->
      ${nextLine}The Veteran requests to receive information in ${getSelectMultiDisplayText(var800)}.
      <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
    </#if>
    <#if hasValue(getSelectMultiDisplayText(var820)) >
      <#-- Do you have and Advance Directive or Durable Power of Attorney for Healthcare?  -->
      ${nextLine}The Veteran reported ${getSelectMultiDisplayText(var820)} an Advance Directive or Durable Power of Attorney for Healthcare.
      <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
    </#if>
     <#if hasValue(getSelectMultiDisplayText(var826)) >
      	<#-- If no, would like information about this document? -->
      	${nextLine}The Veteran ${getSelectMultiDisplayText(var826)} like information about an Advance Directive.
    </#if>     
    
    <#else>
    	${getNotCompletedText()}
    </#if>
    
  </#assign>
  <#if !(advance_dir_section = "") >
     ${advance_dir_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
');

INSERT INTO survey_template (survey_id, template_id) VALUES (9, 10);






/* Spiritual Health */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (11, 3, 'Spiritual Health CPRS Note Entry', 'Spiritual Health Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SPIRITUAL HEALTH: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign spiritual_section>
	<#--  var420: ${var420!""}<br><br>  -->
  	<#if (var400.children)?? && (var430.children)?? && (var440.children)?? && (var420.children)?? 
			&& ((var400.children)?size > 0) && ((var430.children)?size > 0) && ((var440.children)?size > 0) && ((var420.children)?size > 0)>
  	<#-- The Veteran reported that spirituality is important.  --> 
    <#assign text1 = "">
	<#if hasValue(getSelectOneDisplayText(var400)) >
		<#assign text1 = getSelectOneDisplayText(var400)>
     </#if>

	<#if isSelectedAnswer(var420,var423)>
		<#assign text2 = "is not connected to a faith community but would like to be part of one">
	<#else>
		<#assign text2 = getSelectOneDisplayText(var420) + " connected to a faith community">
	</#if>
	
	The Veteran reported that spirituality and/or religion ${text1} important to him/her.  ${LINE_BREAK}${LINE_BREAK}
	The Veteran ${text2} and ${getSelectOneDisplayText(var430)} a referral to see a chaplain at the time of screening. ${LINE_BREAK}${LINE_BREAK}
	
	
	<#-- The Veteran feels that combat or military service affected his/her religious views in the following way: god. -->
  	<#if hasValue(getSelectOneDisplayText(var440)) >
		<#if isSelectedAnswer(var440,var443)>
			The Veteran does not know how combat or military service has affected his/her religious views.  ${NBSP}
		<#else>
			The Veteran feels that combat or military service ${getSelectOneDisplayText(var440)} have an affect on his/her religious views.${NBSP}
		</#if>
    </#if>  	

	
    <#else>
    	${getNotCompletedText()}
    </#if>
  </#assign>
  <#if !(spiritual_section = "") >
     ${spiritual_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (10, 11);






/* Service History */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (12, 3, 'Service History CPRS Note Entry', 'Service History Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SERVICE HISTORY: 
${MODULE_TITLE_END}
${MODULE_START}
 	<#assign history_section>
        <#assign nextLine = "">
		<#if (var3180.children)??  && (var3180.children?size>0) && (var3160.children)?? && (var3160.children)?has_content>
			<#-- var3180: ${var3180}<Br><br>  -->
		
			<#assign allAnswersPresent = false>
			<#assign allAnswersNone = false>
			<#assign outputText = "">
			<#assign rows = {}>

			<#list ((var3180.children)![]) as v>
				<#list v.children as c>
					<#if (c.row)?? >
						<#assign row_idx = (c.row)?string>
						<#assign row_key = (c.key)?string>
						<#assign row_value = (c.displayText)?string>
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

			<#list uniqueRows as ur>
				
				<#assign group = {}>
				<#assign group_keys = []>
				<#list rows?keys as rk>
					<#if rk?starts_with(ur)>
						<#assign group = group + {rk:rows[rk]}> 
						<#assign group_keys = group_keys + [rk]>
					</#if>
				</#list>
			
				<#assign type = "">
				<#assign branch = "">
				<#assign beg_yr = "">
				<#assign end_yr = "">
				<#assign dischargeType = "">
				<#assign rank = "">
				<#assign job = "">
				
				<#assign typeIsComplete = false>
				<#assign branchIsComplete = false>
				<#assign beg_yrIsComplete = false>
				<#assign end_yrIsComplete = false>
				<#assign dischargeTypeIsComplete = false>
				<#assign rankIsComplete = false>
				<#assign jobIsComplete = false>

				<#-- build a row -->
				<#list group_keys as gk>
			
					<#assign var = ((gk?split("_"))[1])!"">
					
					<#-- check if TYPE answer is present -->
					<#if (["var3071", "var3072", "var3073"])?seq_contains(var)>
						<#assign type =  ((group[gk])!"")>
						<#if (type?length > 0)>
							<#assign typeIsComplete = true>
						</#if>
					
						
					</#if>

					<#-- check if BRANCH answer is present -->
					<#if (["var3081", "var3082", "var3083", "var3084", "var3085", "var3086"])?seq_contains(var)>
						<#assign branch =  ((group[gk])!"")>
						<#if (branch?length > 0)>
							<#assign branchIsComplete = true>
						</#if>
						
						
					</#if>

					<#-- check if BEGINNING YEAR answer is present -->
					<#if (["var3091"])?seq_contains(var)>
						<#assign beg_yr =  ((group[gk])!"")>
						<#if (beg_yr?length > 0)>
							<#assign beg_yrIsComplete = true>
						</#if>
						
						
					</#if>


					<#-- check if ENDING YEAR answer is present -->
					<#if (["var3101"])?seq_contains(var)>
						<#assign end_yr =  ((group[gk])!"")>
						<#if (end_yr?length > 0)>
							<#assign end_yrIsComplete = true>
						</#if>
						
						
					</#if>


					<#-- check if DISCHARGE TYPE answer is present -->
					<#if (["var3111", "var3112", "var3113", "var3114", "var3115", "var3116", "var3117"])?seq_contains(var)>
						<#assign dischargeType =  ((group[gk])!"")>
						<#if (dischargeType?length > 0)>
							<#assign dischargeTypeIsComplete = true>
						</#if>
						
						
					</#if>


					<#-- check if RANK answer is present -->
					<#if (["var3121", "var3122", "var3123", "var3124", "var3125", "var3126", "var3127",
							"var3128", "var3129", "var3130", "var3131", "var3132", "var3133", "var3134",
							"var3135", "var3136", "var3137", "var3138", "var3139", "var3140"])?seq_contains(var)>
						<#assign rank =  ((group[gk])!"")>
						<#if (rank?length > 0)>
							<#assign rankIsComplete = true>
						</#if>
						
						
					</#if>

					<#-- check if JOB answer is present -->
					<#if (["var3151"])?seq_contains(var)>
						<#assign job =  ((group[gk])!"")>
						<#if (job?length > 0)>
							<#assign jobIsComplete = true>
						</#if>
						
						
					</#if>
			</#list>

			<#if typeIsComplete && branchIsComplete && beg_yrIsComplete && end_yrIsComplete 
										&& dischargeTypeIsComplete && rankIsComplete && jobIsComplete>
				<#assign allAnswersPresent =true>
					
				<#if ((outputText?trim?length) > 0)>
					<#assign outputText = 
						("The Veteran reported previous enlistment in the " + type + " " + branch + " in " + beg_yr 
						+ ", received an " + dischargeType + " discharge in " + end_yr + " at the rank of " + rank + ".")>
				<#else>
					<#assign outputText = ("The Veteran reported entering the " + type + " " + branch + " in " + beg_yr 
						+ ", received an " + dischargeType + " discharge in " + end_yr + " at the rank of " + rank + ".")>
				</#if>
			<#else>
				<#assign allAnswersPresent =false>
			</#if>


			<#-- clear our vars for the next row\'s processing -->
			<#assign type = "">
			<#assign branch = "">
			<#assign beg_yr = "">
			<#assign end_yr = "">
			<#assign dischargeType = "">
			<#assign rank = "">
			<#assign job = "">

			<#-- after processing a row , if a var is missing then it is not complete therefore "all" questions were not answered -->
			<#if !allAnswersPresent>
					${nextLine}${getNotCompletedText()}.
                    <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
					<#-- no need to continue if we are finished processing a row and a var is missing -->
					<#break>
			<#else>
					${nextLine}${outputText} 
                    <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">		
			</#if>
		  </#list>
			
		  <#if allAnswersPresent>
			${nextLine}The Veteran also served in the following operations or combat zones: ${getSelectMultiDisplayText(var3160)}.
            <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		  </#if>
		<#else>	 
			${getNotCompletedText()}. ${NBSP}
		</#if>
	</#assign>
  	
	<#if !(history_section = "") >
     	${history_section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (11, 12);





/* OEF/OIF Clinical Reminder */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (13, 3, 'OEF/OIF Clinical Reminder CPRS Note Entry', 'OEF/OIF Clinical Reminder Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
OEF/OIF CLINICAL REMINDER:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign mst_section>
	<#if (var660.children)?? && ((var660.children)?size > 0)  >
	  <#if hasValue(getSelectMultiDisplayText(var660)) >
    	<#if isSelectedAnswer(var660, var661)> <#-- no -->
			<#assign text = "The Veteran\'s most recent deployment was not for Operation Enduring Freedom (OEF) or Operation Iraqi Freedom (OIF)">  
    		
		<#elseif isSelectedAnswer(var660, var659)><#-- yes -->
    			<#assign text = "The Veteran\'s most recent deployment was for " + getSelectOneDisplayText(var660) + " in " + getSelectOneDisplayText(var683)> 
    		
		<#elseif isSelectedAnswer(var660, var662)><#-- yes -->
    		<#assign text = "The Veteran\'s most recent deployment was for " + getSelectOneDisplayText(var660) + " in " + getSelectOneDisplayText(var663)> 
    	</#if>
    </#if>
     
     ${text}. ${NBSP}

	<#else>
		${getNotCompletedText()}.
	</#if>
  </#assign>
  <#if !(mst_section = "") >
     ${mst_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);

INSERT INTO survey_template (survey_id, template_id) VALUES (12, 13);





/* Military Deployments & History */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (14, 3, 'Military Deployments & History CPRS Note Entry', 'Military Deployments & History Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
MILITARY DEPLOYMENTS AND HISTORY:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign deployments_section>
	
	<#assign allComplete = false>
	<#assign allAnswersNone = false>

	<#-- var5000 -->
	<#assign Q1_text = "">
	<#assign Q1_complete = false>
	<#assign Q1_isAnswerNone = false>
	<#if (var5001.value)??>
		<#if var5001.value = "true">
			<#assign Q1_isAnswerNone = true>
			<#assign Q1_complete = true>
		<#else>
			<#assign Q1_isAnswerNone = false>
		</#if>
	</#if>
	<#if !Q1_isAnswerNone && !Q1_complete && (var5000.children)?? && ((var5000.children)?size > 0)>
		<#assign Q1_complete = true>
	</#if>

	<#-- var5020 -->
	<#assign Q2_text = "">
	<#assign Q2_complete = false>
	<#assign Q2_isAnswerNone = false>
	<#if (var5021.value)??>
		<#if var5021.value = "true">
			<#assign Q2_isAnswerNone = true>
			<#assign Q2_complete = true>
		<#else>
			<#assign Q2_isAnswerNone = false>
		</#if>
	</#if>
	<#if !Q2_isAnswerNone && !Q2_complete && (var5020.children)?? && ((var5020.children)?size > 0)>
		<#assign Q2_complete = true>
	</#if>

	<#-- var5130 -->
	<#assign Q3_text = "">
	<#assign Q3_complete = false>
	<#assign Q3_isAnswerNone = false>
	<#if (var5131.value)??>
		<#if var5131.value = "true">
			<#assign Q3_isAnswerNone = true>
			<#assign Q3_complete = true>
		<#else>
			<#assign Q3_isAnswerNone = false>
		</#if>
	</#if>
	<#assign counter = 0>
	<#assign all_rows = "">

<#if !Q3_isAnswerNone && !Q3_complete && (var5130.children)?? && (var5130.children)?has_content>
	<#assign rows = {}>
	  <#list ((var5130.children)![]) as v>
		<#if (v.children)?? >
		  <#list v.children as c>
		  <#if (c.row)??>
			<#assign beg_months = ["var5061", "var5062", "var5063", "var5064", "var5065", "var5066",
									"var5067", "var5068", "var5069", "var5070", "var5071", "var5072"]>

			<#assign end_months = ["var5100", "var5102", "var5103", "var5104", "var5105", "var5106",
									"var5107", "var5108", "var5109", "var5110", "var5111", "var5112"]>

			<#if beg_months?seq_contains(c.key)>
				<#assign row_key = "var5060">
			<#elseif end_months?seq_contains(c.key)>	
				<#assign row_key = "var5100">
			<#else>
				<#assign row_key = (c.key)?string>
			</#if>

			<#assign row_idx = (c.row)?string>
			<#assign row_value = ((c.displayText)!"")?string>
			<#assign r ={}>
			<#assign row_name = ("row" + row_idx + "_" + row_key)?string > 
			
			<#assign rows =  rows + {row_name:row_value}> 
		   </#if>
		</#list>
		</#if>
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
	
	<#if uniqueRows?has_content>
        <#assign innerNextLine = "">
		<#list uniqueRows as r>
			<#assign loc = (rows[r + "_" + "var5041"])!"">
			
			<#assign beg_month = (rows[r + "_" + "var5060"])!"">
			<#assign beg_yr = (rows[r + "_" + "var5081"])!"">
			
			<#assign end_month = (rows[r + "_" + "var5100"])!"">
			<#assign end_yr = (rows[r + "_" + "var5121"])!"">
			
			<#if (loc?has_content) && (beg_month?has_content) && (beg_yr?has_content) && (end_month?has_content) && (end_yr?has_content)>
				${innerNextLine}<#assign all_rows = all_rows +	beg_month + "/" + beg_yr + "-"  + end_month + "/" + end_yr + ": "  + loc >
                <#assign innerNextLine = "${LINE_BREAK}">
				<#assign counter = counter + 1>
				
			<#else>
				<#-- if Q3 is missing any answers, reset everything  and set complete = false-->
				<#assign Q3_complete = false>
				<#assign all_rows = "">
				<#assign counter = 0>
				<#break>
			</#if>
		</#list>
		<#if (counter > 0)>
			<#assign Q3_complete = true>
		</#if>
	</#if>
  </#if>
  
  	<#if Q1_complete && Q2_complete && Q3_complete>
  		<#assign allComplete = true>
  	</#if>
	<#if Q1_isAnswerNone && Q2_isAnswerNone && Q3_isAnswerNone>
  		<#assign allAnswersNone = true>
  	</#if>
	
	<#if allComplete>
        <#assign nextLine = "">
		<#if allAnswersNone>
			${getAnsweredNoneAllText("Military Deployments History")}
		<#else>
			<#if Q1_isAnswerNone>
				The Veteran reported receiving no disciplinary actions.
                <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
			<#else>
				The Veteran reported receiving the following disciplinary actions: ${getSelectMultiDisplayText(var5000)}.
                <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
			</#if>

			<#if Q2_isAnswerNone>
                ${nextLine}The Veteran reported receiving no personal awards or commendations.
                <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
			<#else>
                ${nextLine}The Veteran reported receiving the following personal awards or commendations: ${getSelectMultiDisplayText(var5020)}.
                <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
			</#if>

			
			<#if (counter == 1)>
				<#assign frag = counter + " time">
				<#assign frag2 = "area">
				<#assign frag3 = "period">
			<#else>
				<#assign frag = counter + " times">
				<#assign frag2 = "areas">
				<#assign frag3 = "periods">
			</#if>
            ${nextLine}The Veteran was deployed ${frag} to the following ${frag2} during the following time ${frag3}:${LINE_BREAK}${LINE_BREAK}
			${all_rows}
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>


  </#assign>
  <#if !(deployments_section = "") >
     ${deployments_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (13, 14);



/* Exposures */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (15, 3, 'Exposures CPRS Note Entry', 'Exposures Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
EXPOSURES:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign exposures_section>

	 <#-- var2200: ${var2200}<br><br>  var2230: ${var2230}<br><br>  var2240: ${var2240}<br><br>  var2250: ${var2250}<br><br> var2260: ${var2260}<br><br>  -->
	
    <#assign nextLine = "">
	<#if (var2200.children)?? && (var2230.children)?? && (var2240.children)?? && (var2250.children)?? && (var2260.children)?? 
		&& ((var2200.children)?size > 0) && ((var2230.children)?size > 0) && ((var2240.children)?size > 0)  
		&& ((var2250.children)?size > 0) && ((var2260.children)?size > 0) && var2289?? 
		&& getFormulaDisplayText(var2289) != "notset" && getFormulaDisplayText(var2289) != "notfound" > 
	 
	  	<#if hasValue(var2200)> 	
			The Veteran reported persistent major concerns related to the effects of exposure to ${getSelectMultiDisplayText(var2200)}. 
            <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		</#if>
	
		<#assign score = getFormulaDisplayText(var2289)>
		<#assign scoreText = "notset">
	
		<#if (score?number >= 1) >
				<#assign scoreText = "being exposed">
		<#elseif score?number == 0>
			<#assign scoreText = "no exposure">
		</#if> 
        ${nextLine}The Veteran reported ${scoreText} to animal contact during a deployment in the past 18 months that could result in rabies.
        <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">	
	
		<#if hasValue(var2260)> 	
			<#assign combat = getSelectMultiDisplayText(var2260)>
			<#if combat == "notfound">
				<#assign combatText = "none">
			<#else>
				<#assign combatText = combat>
			</#if>
		</#if>
		${nextLine}The Veteran reported the following types of combat experience: ${combatText}.

	<#else>
		${getNotCompletedText()}
	</#if>
	
  </#assign>
  <#if !(exposures_section = "") >
     ${exposures_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (14, 15);





/* Service Injuries */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (16, 3, 'Service Injuries CPRS Note Entry', 'Service Injuries Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SERVICE INJURIES:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign injuries_section>
	<#-- var1380: ${var1380!""}<br><br>  var1390: ${var1390!""}<br><br>  var1400: ${var1400!""}<br><br>  var1410: ${var1410!""}<br><br>  var1480: ${var1480!""}<br><br>  var1490: ${var1490!""}<br><br> -->
	<#if (var1380.children)?? && (var1390.children)?? && (var1400.children)?? && (var1410.children)?? && (var1420.children)?? 
			&& (var1430.children)?? && (var1440.children)?? && (var1450.children)?? && (var1460.children)?? && (var1470.children)?? 
			&& (var1480.children)?? && (var1490.children)?? && (var1510.children)??
			&& (var1380.children?size > 0) && (var1390.children?size > 0)  && (var1400.children?size > 0) && (var1410.children?size > 0)
			&& (var1420.children?size > 0) && (var1430.children?size > 0) && (var1440.children?size > 0) && (var1450.children?size > 0)
			&& (var1460.children?size > 0) && (var1470.children?size > 0) && (var1480.children?size > 0) && (var1490.children?size > 0)
			&& (var1510.children?size > 0)>
		
		<#assign answerTextHash = {"var1380":"blast injury", "var1390":"injury to spine or back", "var1400":"burn (second, 3rd degree)", 
									"var1410":"nerve damage", "var1420":"Loss or damage to vision", "var1430":"loss or damage to hearing", 
									"var1440":"amputation", "var1450":"broken/fractured bone(s)", "var1460":"joint or muscle damage", 
									"var1470":"internal or abdominal injuries", "var1480":"other", "var1490":"other"}>
		
		<#-- must have answer questions -->
		<#assign questions1 = [var1380, var1390, var1400, var1410, var1420, var1430, var1440, var1450, var1460, var1470]>

		<#-- optional answer questions -->
		<#assign questions2 = [var1480, var1490]>


		<#assign outputText = "">
		<#assign errorText = "">
		<#assign sentences = []>
		<#-- organize answers in a way to facilitate output -->
		<#assign noneAnswers = []>
		<#assign combatAnswers = []>
		<#assign nonCombatAnswers = []>
		<#assign otherAnswers = []>
		<#list questions1 as q>
			<#if (q.children[0])?? >
				<#assign key = (q.key)!"">
				<#assign text = (q.children[0].overrideText)!""> 
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
			<#else>
				<#-- if no children then question was not answered -->
				<#assign errorText =  getNotCompletedText()>
				<#break>
			</#if>
		</#list>

		
		<#if !(errorText?has_content)>
				<#if (var1480.children)?has_content && (var1480.children[0].overrideText == "none")>
					<#assign answer = (answerTextHash["var1480"])!"">
					<#-- dont do anything here this freeform Q is handled differently than the others -->
				<#elseif ((var1480.children?size) > 0) && !((var1481.otherText)?has_content) > 
					<#-- veteran did not complete this answer -->
					<#assign errorText = getNotCompletedText()>
				<#else>
					<#if (var1480.children?size > 0)>
						<#assign otherAnswer = var1481.otherText!"">
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
		</#if>
		
		<#if !(errorText?has_content)>
				<#if (var1490.children)?has_content && (var1490.children[0].overrideText == "none")>
					<#assign answer = (answerTextHash["var1490"])!"">
					<#-- dont do anything here this freeform Q is handled differently than the others -->
				<#elseif ((var1490.children?size) > 0) && !((var1491.otherText)?has_content) > 
					<#-- veteran did not complete this answer -->
					<#assign outputText = getNotCompletedText()>
				<#else>
					<#if (var1490.children?size > 0)>
						<#assign otherAnswer = var1491.otherText!"">
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
		</#if>
		
		<#-- build first two sentences -->
		<#if combatAnswers?has_content>
			<#assign sentence = "The following injuries were reported during combat deployment: " + createSentence(combatAnswers) + ".">
			<#assign sentences = sentences + [sentence]>
		<#else>
			<#assign sentences = sentences + ["The Veteran reported no injuries during combat deployment."]>
		</#if>
		
		<#if nonCombatAnswers?has_content>
			<#assign sentence = "The following injuries were reported during other service period or training: " + createSentence(nonCombatAnswers) + ".">
			<#assign sentences = sentences + [sentence]>
		<#else>
			<#assign sentences = sentences + ["The Veteran reported no injuries during other service period or training."]>
		</#if>
		<#if !(errorText?has_content)>
			<#assign frag = "">
			<#if isSelectedAnswer(var1510,var1512)>
				<#assign frag = "has">
			</#if>
			<#if isSelectedAnswer(var1510,var1511) || isSelectedAnswer(var1510,var1513)>
				<#assign frag = "does not have">
			</#if>
			<#assign sentence = "The Veteran " + frag + " a service connected disability rating.">
			<#assign sentences = sentences + [sentence]>
		</#if>
		<#if errorText?has_content>
			${errorText}
		<#else>
            <#assign nextLine = "">
			<#list sentences as s>
				${nextLine}${s}
                <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
			</#list>
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(injuries_section = "") >
     ${injuries_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}');
INSERT INTO survey_template (survey_id, template_id) VALUES (15, 16);




/* PHQ-15 section - Health Status */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (17, 3, 'PHQ-15 CPRS Note Entry', 'Health Status Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PHQ-15:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign phq15_section>
		 <#-- var1189: ${var1189!""}<br><br>  -->
	  <#if var1189?? && (var1020.children)?? && (var1030.children)?? && (var1040.children)?? && (var1050.children)?? && (var1060.children)?? 
		&& (var1070.children)?? && (var1080.children)?? && (var1090.children)?? && (var1100.children)?? && (var1110.children)?? 
		&& (var1120.children)?? && (var1130.children)?? && (var1140.children)?? && (var1150.children)?? && (var1160.children)??
		&& ((var1020.children)?size > 0) && ((var1030.children)?size > 0) && ((var1040.children)?size > 0) && ((var1050.children)?size > 0) 
		&& ((var1060.children)?size > 0) && ((var1070.children)?size > 0) && ((var1080.children)?size > 0) && ((var1090.children)?size > 0) 
		&& ((var1100.children)?size > 0) && ((var1110.children)?size > 0) && ((var1120.children)?size > 0) && ((var1130.children)?size > 0) 
		&& ((var1140.children)?size > 0) && ((var1150.children)?size > 0) && ((var1160.children)?size > 0)>  
	
	<#assign totalScore = getFormulaDisplayText(var1189)>
	<#assign scoreText = "">
	<#if totalScore != "notset" && totalScore != "notfound">
		<#assign totalScore = totalScore?number>
		<#if (totalScore <= 4)>
			<#assign scoreText = "minimal">
		<#elseif (totalScore >= 5 &&  (totalScore <= 9))>
			<#assign scoreText = "low number of">
		<#elseif (totalScore >= 10 &&  (totalScore <= 14))>
			<#assign scoreText = "medium number of">
		<#elseif (totalScore >= 15 &&  (totalScore <= 30))>
			<#assign scoreText = "high number of">
		</#if>
	</#if>

	<#-- During the past 4 weeks, how much have you been bothered by any of the following problems -->
	<#-- this is almost identical to Other Health Symptoms -->
	<#assign fragments = []> 
    <#if (getScore(var1150) > 1)>
		<#assign fragments = fragments + ["stomach pain"] >
	</#if>
    <#if (getScore(var1160) > 1)>
		<#assign fragments = fragments + ["back pain"] >
	</#if>
	<#if (getScore(var1020) > 1)>
		<#assign fragments = fragments + ["pain in arms, legs or joints (knees, hips, etc.)"] >
	</#if>
	<#if (getScore(var1030) > 1)>
		<#assign fragments = fragments + ["menstrual cramps or other problems with your periods"] >
	</#if>
	<#if (getScore(var1040) > 1)>
		<#assign fragments = fragments + ["headaches"] >
	</#if>
	<#if (getScore(var1050) > 1)>
		<#assign fragments = fragments + ["chest pain"] >
	</#if>
	<#if (getScore(var1060) > 1)>
		<#assign fragments = fragments + ["dizziness"] >
	</#if>
	<#if (getScore(var1070) > 1)>
		<#assign fragments = fragments + ["fainting spells"] >
	</#if>
	<#if (getScore(var1080) > 1)>
		<#assign fragments = fragments + ["feeling your heart pound or race"] >
	</#if>
	<#if (getScore(var1090) > 1)>
		<#assign fragments = fragments + ["shortness of breath"] >
	</#if>
	<#if (getScore(var1100) > 1)>
		<#assign fragments = fragments + ["pain or problems during sexual intercourse"] >
	</#if>
	<#if (getScore(var1110) > 1)>
		<#assign fragments = fragments + ["constipation, loose bowels or diarrhea"] >
	</#if>
	<#if (getScore(var1120) > 1)>
		<#assign fragments = fragments + ["nausea, gas or indigestion"] >
	</#if>
	<#if (getScore(var1130) > 1)>
		<#assign fragments = fragments + ["feeling tired or having low energy"] >
	</#if>
	<#if (getScore(var1140) > 1)>
		<#assign fragments = fragments + ["trouble sleeping"] >
	</#if>

	<#if fragments?has_content  >
		<#assign resolved_fragments =  createSentence(fragments)>
	<#else>
		<#assign resolved_fragments = "None">
	</#if>

	The Veteran reported a ${scoreText} of somatic symptoms.${LINE_BREAK}${LINE_BREAK}
	The Veteran endorsed being bothered a lot by the following health symptoms over the past four weeks: ${resolved_fragments}.${NBSP}

	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(phq15_section = "") >
     ${phq15_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);
INSERT INTO survey_template (survey_id, template_id) VALUES (16, 17);



/* Other Health Symptoms */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (18, 3, 'Other Health Symptoms CPRS Note Entry', 'Other Health Symptoms Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
OTHER HEALTH SYMPTOMS:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign other_health_section>
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
	
	<#if ((fragments?size) > 1)  >
		<#assign resolved_fragments =  createSentence(fragments)>
	<#else>
		<#assign resolved_fragments = "None">
	</#if>

	The Veteran endorsed being bothered by the following health symptoms over the past four weeks: ${resolved_fragments}.
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(other_health_section = "") >
     ${other_health_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);

INSERT INTO survey_template (survey_id, template_id) VALUES (17, 18);






/* OOO Infect&Embedded Fragment CR */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (19, 3, 'OOO Infect & Embedded Fragment CR Module CPRS Note Entry', 'OOO Infect&Embedded Fragment CR Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
OOO INFECT & EMBEDDED FRAGMENT CR:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign fragment_section>
	 
	<#if (var2500.children)?? && (var2501.children)?? && (var2502.children)?? && (var2009.children)??
		&& ((var2500.children)?size > 0) && ((var2501.children)?size > 0) && ((var2502.children)?size > 0) && ((var2009.children)?size > 0)>  

	<#-- ${createSentence(parts)}. -->
	<#assign questions = [var2500,var2501,var2502,var2009]>
	<#assign textHash = {"var2500": "chronic diarrhea or other gastrointestinal", 
							"var2501": "pain, unexplained fevers", 
							"var2502": "persistent popular or nodular skin rash that began after deployment to Southwest Asia", 
							"var2009": "suspects that he/she has retained fragments or shrapnel as a result of injuries"}>
	
	<#assign parts = []>
		
	<#list questions as q >
		<#assign child = q.children[0] >
		<#if ((child.calculationValue?number) > 0)>
			<#assign problem = textHash[q.key] >
			<#assign part = problem + ", with symptoms of " + getVariableDisplayText(child) >
			<#assign parts = parts + [part] >
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

  </#assign>
  <#if !(fragment_section = "") >
     ${fragment_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (18, 19);






/* Basic Pain */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (20, 3, 'Basic Pain CPRS Note Entry', 'Basic Pain Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PAIN:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign pain_section>
	<#--  var2330: ${var2330}<br><br>   <#if var2334??>var2334: ${var2334}<br><br> </#if> -->
 
	<#if (var2300.children)?? && (var2330.children)?? && (var2300.children)??>  

		<#if (var2300.children?size == 0)  || ((var2330.children?size == 0) && ((var2334.value) != "true"))>
			${getNotCompletedText()}
		<#else>
			<#--  --> 
			<#assign fragments = []>
			<#assign text ="notset">  
	
			<#assign level = getSelectOneDisplayText(var2300)!("-1"?number)>
			<#assign bodyParts = getTableMeasureValueDisplayText(var2330)!"">
			<#if !(bodyParts?has_content) && ((var2334.value) == "true")>
				<#assign bodyParts = "none">
			</#if>
			<#if !level?? >
				${getNotCompletedText()}. 
			<#else>
				Over the past month, the Veteran\'s reported pain level was ${level} of 10. The pain was located in: ${bodyParts}.
			</#if>
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>

  </#assign>
  <#if !(pain_section = "") >
     ${pain_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (20, 20);






/* Promis Pain Intensity & Interference */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (21, 3, 'Promis Pain Intensity & Interference CPRS Note Entry', 'Promis Pain Intensity & Interference Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PROMIS PAIN INTESITY & INTERFERENCE:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign pain_intensity_section>
	 
	
	<#if (var2550.children[0])?? && (var2560.children[0])?? && (var2570.children[0])?? && (var2580.children[0])?? 
			&& (var2590.children[0])?? && (var2600.children[0])?? && (var2610.children[0])?? && (var2620.children[0])?? && (var2630.children[0])?? 
				&& var2640??>
	<#--  -->
		<#assign score1 = var2550.children[0].calculationValue?number>
		<#assign score2 = var2560.children[0].calculationValue?number>
		<#assign score3 = var2570.children[0].calculationValue?number>
		<#assign score4 = var2580.children[0].calculationValue?number>
		<#assign score5 = var2590.children[0].calculationValue?number>
		<#assign score6 = var2600.children[0].calculationValue?number>
		<#assign score7 = var2610.children[0].calculationValue?number>
		<#assign score8 = var2620.children[0].calculationValue?number>
		<#assign score9 = var2630.children[0].calculationValue?number>
	
		<#assign score = -1>
		
		<#assign score = getFormulaDisplayText(var2640)>
		<#if score != "notset" && score != "notfound">
			<#assign score = score?number>
		</#if>
		<#assign scoreText = "">
		
		The Veteran endorsed having ${getSelectOneDisplayText(var2550)} pain currently. Over the past week the veteran reported a pain intensity of ${getSelectOneDisplayText(var2560)} with ${getSelectOneDisplayText(var2570)} average pain. ${LINE_BREAK}${LINE_BREAK}
		<#if (score >= 0) && (score < 17)>
			<#assign scoreText = "does not">
		<#elseif (score >= 17) && (score <= 998)>
			<#assign scoreText = "does">
		<#else>
			<#assign scoreText = "notset">
		</#if>
		
		The pain ${scoreText} significantly interfere.${NBSP}
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(pain_intensity_section = "") >
     ${pain_intensity_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (19, 21);







/* Medications */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (22, 3, 'Medications CPRS Note Entry', 'Medications Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
MEDICATIONS:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign med_section>
	
	
<#if (var3500.children)??>
	<#assign allAnswersPresent = true>
	<#assign allAnswersNone = true>
	<#list var3500.children as c>
		
			<#-- if first child has children then all answers cannot be None -->
			
			<#if (c.children)?? && (c.children?size > 0) && allAnswersNone>
				<#assign allAnswersNone = false>
			</#if>

			<#-- if first child has children and each one of those chidren has a value then all answers are present and complete -->
			<#if (c.children)?? && !((c.children[0].value)?has_content) && allAnswersPresent>
				<#assign allAnswersPresent = false>
			</#if>
			
			<#if allAnswersNone && allAnswersPresent>
				<#break>
			</#if>
	
	</#list>


	<#if allAnswersPresent>
		<#if !allAnswersNone>
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

					<#if (med?has_content) && (dose?has_content) && (freq?has_content) && (dur?has_content) && (doc?has_content)>
						<#assign all_rows = all_rows + nextLine +
						"MEDICATION/SUPPLEMENT: " + med + "${LINE_BREAK}" 
						+ "DOSAGE: " + dose + "${LINE_BREAK}" 
						+ "FREQUENCY: " + freq + "${LINE_BREAK}" 
						+ "DURATION: " + dur + "${LINE_BREAK}" 
						+ "PRESCRIBER NAME AND/OR FACILITY: " + doc >
					   <#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
					<#else>
						<assign outputText = getNotCompletedText()>
					</#if>
				</#list>
		
			<#else>
				<assign outputText = getAnsweredNoneAllText("Medications")>
			</#if>

			<#if outputText?has_content>
				${outputText}
			<#else>
				${all_rows!""}
			</#if>
		<#else>
			${getNotCompletedText()}
		</#if>
 
	<#elseif !allAnswersPresent && !allAnswersNone>
		${getNotCompletedText()}
	<#elseif allAnswersNone>
		${getAnsweredNoneAllText("Medications")}
	</#if>
<#else>
	${getNotCompletedText()}
</#if>
  </#assign>

  <#if !(med_section = "") >
     ${med_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (21, 22);





/* Prior MH Treatment */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (23, 3, 'Prior MH Treatment CPRS Note Entry', 'Prior MH Treatment Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PRIOR TREATMENT:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign prior_mh_treatment_section>   
    <#assign nextLine = "">
	<#-- var1520: ${var1520!""}<br><br>  var1521: ${var1521!""}<br><br> -->

  	<#if (var1520.children)?? && (var1530.children)?? 
			&& ((var1520.children)?size > 0) && ((var1530.children)?size > 0) >
	  
	<#--  -->
	<#assign fragments = []>
	<#assign text =""> 
		
	<#if var1520?has_content> 	
		<#if isSelectedAnswer(var1520, var1521)><#-- was answer "none" or something else -->
			The Veteran reported being diagnosed with no prior mental health issues in the last 18 months.
			<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		<#else>
			The Veteran endorsed being diagnosed with ${getSelectMultiDisplayText(var1520)} within the last 18 months.
			<#assign nextLine = "${LINE_BREAK}${LINE_BREAK}">
		</#if>
	</#if>
	
	<#if var1530?has_content>
		<#if isSelectedAnswer(var1530, var1531)><#-- was answer "none" or something else -->
			${nextLine}The Veteran reported receiving no prior mental health treatment for a mental health diagnosis in the last 18 months.
		<#else>
			${nextLine}The Veteran endorsed having received the following treatments within the last 18 months for a mental health diagnosis: ${getSelectMultiDisplayText(var1530)}.
		</#if>
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(prior_mh_treatment_section = "") >
     ${prior_mh_treatment_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (22, 23);





/* WHODAS 2.0 - Understanding and Communicating, Mobility, Self-Care, Getting Along, Life Activities (Household/Domestic) , Life Activities (School /Work), Participation in Society*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (24, 3, 'WHODAS 2.0 CPRS Note Entry', 'WHODAS 2.0 Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
WHODAS:
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

 	<#assign section>
		<#-- I DON\'T think that WHODAS needs ALL NONE Logic -->
		<#--  var4200: ${var4200!""}<br><br>  -->

 		<#if (var4119.value)?? && (var4239.value)?? && (var4319.value)?? && (var4419.value)?? 
				&& (var4499.value)?? && (var4559.value)?? && (var4789.value)?? && (var4200.children)?? 
				&& ((var4200.children)?size > 0)>
		
			<#t><b>Understanding and Communicating</b> - the veteran had an average score of ${var4119.value} which indicates ${getScoreText(var4119.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Mobility</b> - the veteran had an average score of ${var4239.value}, which indicates ${getScoreText(var4239.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Self-Care</b> - the veteran had an average score of ${var4319.value} which indicates ${getScoreText(var4319.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Getting Along</b> - the veteran had an average score of ${var4419.value} which indicates ${getScoreText(var4419.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Life Activities (Household/Domestic)</b> - the veteran had an average score of ${var4499.value} which is a rating of ${getScoreText(var4499.value)} disability. ${LINE_BREAK}${LINE_BREAK} 
			
			<#if isSelectedAnswer(var4200, var4202)>
				<b>Life Activities (School /Work)</b> - the veteran had an average score of ${var4559.value} which is a rating of ${getScoreText(var4559.value)} disability. ${LINE_BREAK}${LINE_BREAK}    
			<#elseif isSelectedAnswer(var4200, var4201)>
				<b>Life Activities (School /Work)</b> - the veteran did not get a score because the veteran does not work or go to school. ${LINE_BREAK}${LINE_BREAK}   
			</#if>
			
			<b>Participation in Society</b> - the veteran had an average score of ${var4789.value} which indicates ${getScoreText(var4789.value)} disability. ${NBSP} 

		<#else>
			${getNotCompletedText()}. ${NBSP}
		</#if>
 	</#assign>
  	<#if !(section = "") >
     	${section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}');
INSERT INTO survey_template (survey_id, template_id) VALUES (23, 24);



/* Caffeine Use */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (25, 3, 'Caffeine Use CPRS Note Entry', 'Caffeine Use Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SUBSTANCE USE:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign caffeine_section>
  	<#if (var3050.children)?? && (var3050.children?size > 0)> 
		<#if (getScore(var3050) == 0)>
			<#t>The Veteran reported consuming no caffeinated beverages per day.${NBSP}
		<#elseif (getScore(var3050) >= 1) && (getScore(var3050) <= 998)>
			<#t>The Veteran reported consuming ${getSelectOneDisplayText(var3050)} caffeinated beverages per day.${NBSP}
		<#else>
			${getNotCompletedText()}
		</#if>	
	<#else>
  		${getNotCompletedText()}
  	</#if>
  </#assign>
  <#if !(caffeine_section = "") >
     ${caffeine_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}');
INSERT INTO survey_template (survey_id, template_id) VALUES (24, 25);





/* Tobacco Cessation Screen */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (26, 3, 'Tobacco Cessation Screen CPRS Note Entry', 'Tobacco Cessation Screen Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
TOBACCO: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign tobacco_section>			

	<#--   var600: ${var600!""}<br><br>   -->

  	<#if (var600.children)?? && ((var600.children)?size > 0)>
	  	<#-- The Veteran endorsed using cigarettes.  --> 
	    <#if hasValue(getSelectOneDisplayText(var600)) >
	    
			<#assign part1 = getSelectOneDisplayText(var600)>
			<#assign part2 = getSelectMultiDisplayText(var620)>
			<#assign part3 = getSelectOneDisplayText(var610)>
	
			<#-- check if set, if not set to empty string for display purposes -->
			<#if !(isSet(part1))>
				<#assign part1 = "">
			</#if>
			<#if !(isSet(part2))>
				<#assign part2 = "">
			</#if>
			<#if !(isSet(part3))>
				<#assign part3 = "">
			</#if>
	
			<#assign nextLine = "">
			<#if isSelectedAnswer(var600,var601)>
				The Veteran ${part1}.
				<#assign nextLine = "${LINE_BREAK}">
			</#if>
			<#if isSelectedAnswer(var600,var602)>
				${nextLine}The Veteran ${part1} ${part3}.
				<#assign nextLine = "${LINE_BREAK}">
			</#if>
			<#if isSelectedAnswer(var600,var603)>
				${nextLine}The Veteran ${part1} ${part2}.
			</#if>
		</#if>
	
  	<#else>
  		${getNotCompletedText()}
  	</#if>
  </#assign>
  <#if !(tobacco_section = "") >
     ${tobacco_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);

INSERT INTO survey_template (survey_id, template_id) VALUES (25, 26);





/* AUDIT-C section - Alcohol*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (27, 3, 'AUDIT-C CPRS Note Entry', 'Other health symptoms Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
ALCOHOL:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign alcohol_section>

	<#if (var1200.children)?? && (var1210.children)?? && (var1220.children)??
			&& ((var1200.children)?size > 0) && ((var1210.children)?size > 0) && ((var1220.children)?size > 0)>
	
	<#assign score = getListScore([var1200,var1210,var1220])>
	<#assign status ="">  <#-- positive or negative -->
	<#assign gender = "">
	
		<#if !((var1200.children)?has_content) || !((var1210.children)?has_content) || !((var1220.children)?has_content) >
	 		${getNotCompletedText()}
		<#else>
			<#if (score >= 0) && (score <= 2)>
				<#assign status = "negative">
			<#elseif (score >= 4) && (score <= 998)>
				<#assign status = "positive">
			<#elseif (score == 3 )>
				<#assign status = "positive for women/negative for men">
			</#if>
			The Veteran\'s AUDIT-C screen was ${status} with a score of ${score}. ${NBSP}
    	</#if>
    	
    <#else>
    	${getNotCompletedText()}
    </#if>
  </#assign>
  <#if !(alcohol_section = "") >
     ${alcohol_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (26, 27);





/* DAST-10 section - Drug Screen*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (28, 3, 'DAST-10 CPRS Note Entry', 'DAST-10 Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
DRUGS:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign drugs_section>
	  
	<#-- var1000: ${var1000!""}<br><br>  var1001: ${var1001!""}<br><br>  var1002: ${var1002!""}<br><br>  var1003: ${var1003!""}<br><br>  -->

	<#if (var1000.children)?? && (var1001.children)?? &&  (var1002.children)?? && (var1003.children)?? 
			&& (var1004.children)?? && (var1005.children)?? && (var1006.children)?? 
			&& (var1007.children)?? && (var1008.children)?? && (var1009.children)??
			&& ((var1000.children)?size > 0) && ((var1001.children)?size > 0) &&  ((var1002.children)?size > 0) 
			&& ((var1003.children)?size > 0) && ((var1004.children)?size > 0) && ((var1005.children)?size > 0) 
			&& ((var1006.children)?size > 0) && ((var1007.children)?size > 0) && ((var1008.children)?size > 0)
			&& ((var1009.children)?size > 0) >
			 
	<#-- During the past 4 weeks, how much have you been bothered by any of the following problems -->
	<#-- this is almost identical to Other Health Symptoms -->
	
	<#-- <#assign score = getListScore([var1240,var1250,var1260,var1270,var1280,var1290,var1300,var1310,var1320,var1330])>  -->
	<#assign score = getListScore([var1000,var1001,var1002,var1003,var1004,var1005,var1006,var1007,var1008,var1009])>
	<#assign status ="notset"> 
	<#assign statusText ="notset"> 
	<#assign gender = "">
	
	<#if score?has_content && ((score?number) == 0)> 
		<#assign status ="negative">
		<#assign statusText ="no problems">
	<#elseif (score)?has_content && ((score?number) >= 1) && ((score?number) <= 2)> 
		<#assign status ="negative">
		<#assign statusText ="a low level of problems">
	<#elseif (score)?has_content && ((score?number) >= 3) && ((score?number) <= 5)> 
		<#assign status ="positive">
		<#assign statusText ="a moderate level of problems">
	<#elseif (score)?has_content && ((score?number) >= 6) && ((score?number) <= 8)>
		<#assign status ="positive">
		<#assign statusText ="a substantial level of problems">
	<#elseif (score)?has_content && ((score?number) >= 9) && ((score?number) <= 10)>
		<#assign status ="positive">
		<#assign statusText ="a severe level of problems">
	</#if> 
	
    The Veteran\'s Drug screen was ${status} with ${statusText} reported. ${NBSP}
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(drugs_section = "") >
     ${drugs_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);
INSERT INTO survey_template (survey_id, template_id) VALUES (27, 28);







/* AV Hallucinations section - Mental Health*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (29, 3, 'A/V Hallucinations CPRS Note Entry', 'A/V Hallucinations Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
A/V HALLUCINATIONS:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign av_hallucinations_section>
	<#--  var1350: ${var1350}<br><br>  var1360: ${var1360}<br><br> -->
	<#if (var1350.children)?? && (var1360.children)??
		 && (var1350.children?size > 0) && (var1360.children?size > 0)>  
		<#--  -->
		<#assign fragments = []>
		<#assign text =""> 
		<#assign Q1_Score = getScore(var1350)>
		<#assign Q2_Score = getScore(var1360)>
		<#if (Q1_Score > 0) || (Q2_Score > 0) >
			<#if (Q1_Score > 0)> 
				<#assign fragments = fragments + ["reported hearing things other people can\'t hear"]>
			</#if>
			<#if (Q2_Score > 0) >
				<#assign fragments = fragments + ["reported seeing things or having visions other people can\'t see"]>
			</#if>
		<#else> 
			<#assign fragments = ["denied A/V hallucinations"]>
		</#if>

		<#if fragments?has_content>
			<#assign text = text + createSentence(fragments)>
			The Veteran ${text}. ${NBSP}
		</#if>
	<#else>
		${getNotCompletedText()}
	</#if>


  </#assign>
  <#if !(av_hallucinations_section = "") >
     ${av_hallucinations_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);
INSERT INTO survey_template (survey_id, template_id) VALUES (28, 29);









/* BTBIS section - TBI Screen*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (30, 3, 'BTBIS CPRS Note Entry', 'TBI Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
TBI: 
${MODULE_TITLE_END}
${MODULE_START}

	<#function isAnswered obj1>
		<#assign result = false>
		<#if (obj1.children)?? && ((obj1.children)?size > 0) >
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
				
				
			The Veteran\'s TBI screen was ${scoreText}. ${NBSP}
			<#if isCompleted2047>
				Veteran ${getSelectOneDisplayText(var2047)} to TBI consult for further evaluation. ${NBSP}
    		</#if>
    		
    	  <#else> <#-- all questions are complete  and answered NONE to all -->
			${getAnsweredNoneAllText("TBI")}. ${NBSP}
		  </#if>
		<#else> <#-- all questions are NOT complete -->
			${getNotCompletedText()}. ${NBSP}
    	</#if>
	
  </#assign>
  <#if !(tbi_section = "") >
     ${tbi_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (29, 30);




/* PHQ-9 section - Depression*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (31, 3, 'PHQ-9 CPRS Note Entry', 'Depression Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
DEPRESSION:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign dep_section>
	  
	<#if (var1550.children)?? && (var1560.children)?? && (var1570.children)?? && (var1580.children)?? && (var1590.children)??
			&& ((var1550.children)?size > 0) && ((var1560.children)?size > 0) && ((var1570.children)?size > 0) 
			&& ((var1580.children)?size > 0) && ((var1590.children)?size > 0)>
	<#assign fragments = []>
	<#assign text ="notset"> 
	<#-- <#assign score = (getListScore([var1550,var1560,var1570,var1580,var1590]))!("-1"?number)> -->
	<#assign totScore = getFormulaDisplayText(var1599)>
	<#assign score = -1>
	<#assign scoreText ="notset">
		
	<#if totScore?? && totScore != "notfound"  && totScore != "notset"> 
		<#assign score = totScore?number>
		<#if (score == 0) >
			<#assign scoreText = "negative">
		<#elseif (score >= 1) && (score <= 4)>
			<#assign scoreText = "positive">
			<#assign text = "minimal depression">
		<#elseif (score >= 5) && (score <= 9)>
			<#assign scoreText = "positive">
			<#assign text = "mild depression">
		<#elseif (score >= 10) && (score <= 14)>
			<#assign scoreText = "positive">
			<#assign text = "moderate depression">
		<#elseif (score >= 15) && (score <= 19)>
			<#assign scoreText = "positive">
			<#assign text = "moderately severe depression">
		<#elseif (score >= 20) && (score <= 27)>
			<#assign scoreText = "positive">
			<#assign text = "severe depression">
		</#if>

		<#if (score >=1) && (score <= 27)>
			<#t>The Veteran\'s PHQ-9 screen was ${scoreText}.${NBSP} The score was ${(score)?string} which is suggestive of ${text}.${NBSP}
		<#elseif (score == 0)>
			The Veteran\'s PHQ-9 screen was ${scoreText}. The score was ${(score)?string}.${NBSP}
		</#if>

	<#else>
		${getNotCompletedText()}
	</#if>

	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(dep_section = "") >
     ${dep_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (30, 31);







/* MDQ section - (Hyper mood) screen */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (32, 3, 'MDQ (Hyper mood) CPRS Note Entry', 'MDQ (Hyper mood) Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
MDQ:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign mdq_section>

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
				The MDQ (Hyper mood) screen was ${t}.
			
				<#if text2?has_content>
					${LINE_BREAK}${LINE_BREAK}Symptoms endorsed: ${text2}.
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
');
INSERT INTO survey_template (survey_id, template_id) VALUES (31, 32);




/* MST */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (33, 3, 'MST CPRS Note Entry', 'MST Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
MST:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign mst_section>
	  
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
	
	<#if var2005?? && (isSelectedAnswer(var2003,var2005) || isSelectedAnswer(var2003,var2004))>
	 	<#if (mstMilitaryScore == 0 )>
			The Veteran\s MST screen was ${(scoreText)!"notset"}. ${NBSP}
	 	<#elseif (mstMilitaryScore == 1)>
			The Veteran ${getSelectOneDisplayText(var2003)!"notset"} the MST screen. The Veteran ${getSelectOneDisplayText(var1640)!"notset"} like a referral to discuss the sexual trauma further.${NBSP}
		<#elseif (mstMilitaryScore == 2)>
			The Veteran ${getSelectOneDisplayText(var2003)!"notset"} the MST screen. The Veteran ${getSelectOneDisplayText(var1640)!"notset"} like a referral to discuss the sexual trauma further.${NBSP}
		</#if>
	<#elseif var2005?? && isSelectedAnswer(var2003,var2006)>
		The Veteran declined to answer MST assessment. ${NBSP}
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
');
INSERT INTO survey_template (survey_id, template_id) VALUES (32, 33);





/* GAD 7 Anxiety */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (34, 3, 'GAD 7 Anxiety CPRS Note Entry', 'Anxiety Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
ANXIETY:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign anxiety_section>

	<#if (var1660.children)?? && (var1670.children)?? && (var1680.children)?? && (var1690.children)?? 
		&& (var1700.children)?? && (var1710.children)?? && (var1720.children)??
		&& ((var1660.children)?size > 0) && ((var1670.children)?size > 0) && ((var1680.children)?size > 0)
		&& ((var1690.children)?size > 0) && ((var1700.children)?size > 0) && ((var1710.children)?size > 0)
			&& ((var1720.children)?size > 0)>
        
		<#assign fragments = []>
		<#assign resolved_fragments="">
		<#assign text="notset"> 
		<#assign score = (getListScore([var1660,var1670,var1680,var1690,var1700,var1710,var1720]))!("-1"?number)>
		<#assign scoreText ="notset">
		
		<#if (getScore(var1660) > 0)>
			<#assign fragments = fragments + ["feeling nervous"] >
		</#if>
	    <#if (getScore(var1670) > 0)>
			<#assign fragments = fragments + ["can\'t control worrying"]>
		</#if>
		<#if (getScore(var1680) > 0)>
			<#assign fragments = fragments +  ["worrying too much"]>
		</#if>
		<#if (getScore(var1690) > 0)>
			<#assign fragments = fragments +  ["trouble relaxing"]>
		</#if>
		<#if (getScore(var1700) > 0)>
			<#assign fragments = fragments +  ["restlessness"]>
		</#if>
		<#if (getScore(var1710) > 0)>
			<#assign fragments = fragments + ["irritability"]>
		</#if>
		<#if (getScore(var1720) > 0)>
			<#assign fragments = fragments + ["feeling afraid"] >
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
			<#t>The Veteran\'s GAD-7 screen was ${scoreText}. ${NBSP}
			The Veteran endorsed the following symptoms were occurring more than half of the days in the past two weeks: ${resolved_fragments}.${NBSP}
		<#elseif (score == 0)>
			<#t>The Veteran\'s GAD-7 screen was ${scoreText}. ${NBSP}
			The Veteran reported having no anxiety symptoms in the past 2 weeks.${NBSP}
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
');
INSERT INTO survey_template (survey_id, template_id) VALUES (33, 34);





/* PCL-C */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (35, 3, 'PCL-C CPRS Note Entry', 'PCL-C Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PCLC:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign pclc_section>
	<#if (var1750.children)?? && (var1760.children)?? && (var1770.children)?? && (var1780.children)?? 
			&& (var1790.children)?? && (var1800.children)?? && (var1810.children)?? && (var1820.children)?? 
			&& (var1830.children)?? && (var1840.children)?? && (var1850.children)?? && (var1860.children)?? 
			&& (var1870.children)?? && (var1880.children)?? && (var1890.children)?? && (var1900.children)?? 
			&& (var1910.children)?? 
			&& ((var1750.children)?size > 0) && ((var1760.children)?size > 0) && ((var1770.children)?size > 0) && ((var1780.children)?size > 0) 
			&& ((var1790.children)?size > 0) && ((var1800.children)?size > 0) && ((var1810.children)?size > 0) && ((var1820.children)?size > 0) 
			&& ((var1830.children)?size > 0) && ((var1840.children)?size > 0) && ((var1850.children)?size > 0) && ((var1860.children)?size > 0) 
			&& ((var1870.children)?size > 0) && ((var1880.children)?size > 0) && ((var1890.children)?size > 0) && ((var1900.children)?size > 0) 
			&& ((var1910.children)?size > 0)>
	 
	<#assign fragments = []>
	<#assign resolved_fragments ="">
	<#assign text ="notset"> 
	<#assign score = (getListScore([var1750,var1760,var1770,var1780,var1790,var1800,var1810,var1820,var1830,var1840,var1850,var1860,var1870,var1880,var1890,var1900,var1910]))!("-1"?number)>
	<#assign scoreText ="notset">
	
	<#if score??> 	
		<#if (score >= 0) && (score <= 49)>
			<#assign scoreText = "negative">
		<#elseif (score >= 50) && (score <= 998)><#-- value 999 means they didn\'t answer the question so to include would give a false positive-->
			<#assign scoreText = "positive">
		</#if>
		
		<#if (score >=0  && score <= 998)>
			<#t>The Veteran\'s PCL-C Screen was ${scoreText!} with a PCL-C score of ${score!("-1"?number)}. ${NBSP}
		<#elseif score == 999 >
			${getNotCompletedText()}.${NBSP}
		</#if>
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
</#assign>
  <#if !(pclc_section = "") >
     ${pclc_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (34, 35);






/* PC-PTSD */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (36, 3, 'PC-PTSD CPRS Note Entry', 'PC-PTSD Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
PTSD:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign ptsd_section>
	<#if (var1940.children)?? && (var1950.children)?? && (var1960.children)?? && (var1970.children)??
			&& ((var1940.children)?size > 0) && ((var1950.children)?size > 0) && ((var1960.children)?size > 0) && ((var1970.children)?size > 0)>
	 
	<#assign fragments = []>
	<#assign resolved_fragments ="">
	<#assign text ="notset"> 
	<#assign score = (getListScore([var1940,var1950,var1960,var1970]))!("-1"?number)>
	<#assign scoreText ="notset">
	 
	<#if score??> 	
		<#if (score >= 0) && (score <= 2)>
			<#assign scoreText = "negative">
		<#elseif (score >= 3) && (score <= 998)><#-- value 999 means they didn\'t answer the question so to include would give a false positive-->
			<#assign scoreText = "positive">
		</#if>
		
		<#if (score >=0  && score <= 998)>
			<#t>The Veteran\'s PC-PTSD Screen was ${scoreText!} with a PC-PTSD score of ${score}. ${NBSP}
		<#elseif score == 999 >
			<#t>The Veteran did not complete PC-PTSD Screen. ${NBSP}
		</#if>
	</#if>
	<#else>
		${getNotCompletedText()}.${NBSP}
	</#if>
</#assign>
  <#if !(ptsd_section = "") >
     ${ptsd_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}'
);
INSERT INTO survey_template (survey_id, template_id) VALUES (35, 36);







/* ISI section - Sleep */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (37, 3, 'ISI Sleep CPRS Note Entry', 'ISI Sleep Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SLEEP:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign sleep_section>
	 
	<#if var2120?? && var2130?? && var2140?? && var2150?? && var2160?? && var2170?? && var2180?? >  
	<#assign fragments = []>
	<#assign text ="notset"> 
	<#assign score = (getListScore([var2120,var2130,var2140,var2150,var2160,var2170,var2180]))!("-1"?number)>
	<#assign scoreText ="notset">
		
	<#if score??> 	
		<#if (score >= 0 && score <= 7) >
			<#assign scoreText = "negative">
			<#assign text = "no clinically significant insomnia">
		<#elseif (score >= 8) && (score <= 14)>
			<#assign scoreText = "negative">
			<#assign text = "subthreshold insomnia">
		<#elseif (score >= 15) && (score <= 21)>
			<#assign scoreText = "positive">
			<#assign text = "moderate insomnia">
		<#elseif (score >= 22) && (score <= 998)>  <#-- if score = 999 then veteran did not answer the question -->
			<#assign scoreText = "positive">
			<#assign text = "severe insomnia">
		</#if>
	</#if>
	
	<#if score??>
		<#if (score >=0) && (score <= 998)>
			<#t>The Veteran\'s ISI was ${scoreText} indicating ${text} for the past week.${NBSP}
		<#elseif score == 999>
			<#-- this is score 999 -->
			<#t>The Veteran did not complete the ISI screen.${NBSP}>
		</#if>
	</#if>
	
	<#else>
		${getNotCompletedText()}
	</#if>
  </#assign>
  <#if !(sleep_section = "") >
     ${sleep_section}
  <#else>
     ${noParagraphData}
  </#if> 
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (36, 37);





/* ROAS section - Aggression*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (38, 3, 'ROAS CPRS Note Entry', 'Aggression Module CPRS Note Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
AGGRESSION: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign agression_section>

    	<#if (var3200.children)?? && (var3210.children)?? && (var3220.children)?? && (var3230.children)?? && (var3240.children)?? 
				&& (var3250.children)?? && (var3260.children)?? && (var3270.children)?? && (var3280.children)?? && (var3290.children)?? 
				&& (var3300.children)?? && (var3310.children)?? && (var3320.children)?? && (var3330.children)?? && (var3340.children)?? 
				&& (var3350.children)??
				&& ((var3200.children)?size > 0) && ((var3210.children)?size > 0) && ((var3220.children)?size > 0) && ((var3230.children)?size > 0) && ((var3240.children)?size > 0) 
				&& ((var3250.children)?size > 0) && ((var3260.children)?size > 0) && ((var3270.children)?size > 0) && ((var3280.children)?size > 0) && ((var3290.children)?size > 0) 
				&& ((var3300.children)?size > 0) && ((var3310.children)?size > 0) && ((var3320.children)?size > 0) && ((var3330.children)?size > 0) && ((var3340.children)?size > 0) 
				&& ((var3350.children)?size > 0)>
	
			<#if !(getScore(var3200) == 999 ||  getScore(var3210) == 999 ||  getScore(var3220) == 999  || getScore(var3230) == 999  ||  getScore(var3240) == 999  || getScore(var3250) == 999  ||  getScore(var3260) == 999  
				|| getScore(var3270) == 999  ||  getScore(var3280) == 999  || getScore(var3290) == 999)  ||  getScore(var3300) == 999 ||  getScore(var3310) == 999  || getScore(var3320) == 999  ||  getScore(var3330) == 999  
				|| getScore(var3340) == 999  ||  getScore(var3350) == 999  >
			
			  <#if (var3389)?? && (getFormulaDisplayText(var3389) != "notfound") && (getFormulaDisplayText(var3389)?number == 0)>       <#-- veteran answered no to all questions" -->
				${getAnsweredNeverAllText("Aggression")}.${NBSP}
			  <#else>
    			The Veteran had a score of ${getFormulaDisplayText(var3389)} on the Retrospective Overt Aggression Scale (range 0-84).${LINE_BREAK}${LINE_BREAK}
    			
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
				The Veteran indicated the following concerning aggressive behaviors in the past month: ${createSentence(fragments)}.${NBSP}
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
');
INSERT INTO survey_template (survey_id, template_id) VALUES (37, 38);





/* CD-RISC-10 section - Resilience and Strengths*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (39, 3, 'CD-RISC-10 CPRS Note Entry', 'Resilience and Strengths Module CPRS Note Entry',
'
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
						<#assign fragments = fragments + ["think of myself as strong person"]>
					</#if>	
					<#if (getScore(var2910) >= 2) && (getScore(var2910) <= 998)  >
						<#assign fragments = fragments + ["can handle unpleasant or painful feelings"]>
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
					
					The Veteran reported the following resilience beliefs at least sometimes during the past four weeks: ${beliefs}.${NBSP}
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
');
INSERT INTO survey_template (survey_id, template_id) VALUES (38, 39);




/*  NOT BEING USED AS OF 7/31/2014 */
/* ID Screen (this is out of order b/c it had to be inserted after the fact)*/
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (40, 3, 'ID CPRS Note Entry', 'ID Module CPRS Note Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
ID: 
${MODULE_TITLE_END}
${MODULE_START}
 	<#assign section>
 		WORK IN PROGRESS
 	</#assign>
  	<#if !(section = "") >
     	${section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}');
INSERT INTO survey_template (survey_id, template_id) VALUES (39, 40);



/*  SCORING MATRIX  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (100, 4, 'OOO Battery CPRS Note Scoring Matrix', 'OOO Battery CPRS Note Scoring Matrix',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SCORING MATRIX: 
${MODULE_TITLE_END}
${MODULE_START}
	

<#assign matrix_section>
	
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
		<#assign status = empty>
		<#assign score = empty>
		<#assign cutoff = empty>
		
		<#if (var2047.children)?? && ((var2047.children)?size > 0)>
			
			<#if isSelectedAnswer(var2047,var3442)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var2047,var3441)> 
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status ="Negative">
			</#if>
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
		
		<#if var2008?? >
			<#if isSelectedAnswer(var2008, var792)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var2008, var791) >
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Negative">
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
		
		<#if var1640?? && var1642?? >
			<#if isSelectedAnswer(var1640, var1642)>
				<#assign score = "N/A">
				<#assign cutoff = "N/A">
				<#assign status = "Positive">
			<#elseif isSelectedAnswer(var1640, var1641)>
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


</#assign>
	
	<#if !(matrix_section = "") >
     	${matrix_section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}');
INSERT INTO battery_template (battery_id, template_id) VALUES (5, 100);







/* SPECIAL STYLE TYPE */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (101, 4, 'Special Section Test', 'Special Section Test',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SPECIAL STYLE TYPE:
${MODULE_TITLE_END}
${MODULE_START}
  SPECIAL STYLE TYPE
 ${MODULE_END}
');

INSERT INTO battery_template (battery_id, template_id) VALUES (5, 101);