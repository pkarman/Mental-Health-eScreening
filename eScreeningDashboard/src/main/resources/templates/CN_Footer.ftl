<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
INTERVENTIONS: 
${MODULE_TITLE_END}
${MODULE_START}
	
 	<#assign footer_section>

		<#assign outputText = "">
		<#assign errorText = "">
		<#assign incompleteText = "All of the required information has not been provided.">
		
		<#assign outputText = outputText + "*Explained confidentiality and the limits of confidentiality.<br>">
		<#assign part = "">
		<#if (var1599.value)??>
			<#assign v1 = var1599.value> <#-- PHQ9 Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 10)>
					<#assign part = "*Conducted CSRA and other necessary follow-ups (see note(s) with same date)<br>
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)">
				</#if>
			</#if>
		</#if>
		
		<#if (var1989.value)?? >
			<#assign v2 = var1989.value> <#-- PTSD Score -->  
			<#if (v2 != "notset")> 
				<#if (v2?number >= 3)>
					<#assign part = "*Conducted CSRA and other necessary follow-ups (see note(s) with same date)<br>
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)">
				</#if>
			</#if>
		</#if>
		
		<#if (var1929.value)??>
			<#assign v3 = var1929.value> <#-- PCL Score -->  
			<#if (v3 != "notset")> 
				<#if (v3?number >= 50)>
					<#assign part = "*Conducted CSRA and other necessary follow-ups (see note(s) with same date)<br>
									*Conducted CSRA, SBR and Safety Plan (see notes with same date)">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#assign outputText = outputText + "*Educated the Veteran on VA health care benefits and OEF/OIF care coordination services.<br>
												* OEF/OIF Case Management not indicated at this time.<br>">

		
		<#if ((var202.value)?? && var202.value == "true") || ((var9060.value)?? && var9060.value == "true")>
				<#assign outputText = outputText + "*Veteran endorsed mental health concerns, recommend outpatient consult to Psychiatry for MH.<br>"> 
		</#if>
		
		<#if ((var215.value)?? && var215.value == "true") || ((var9066.value)?? && var9066.value == "true")>
				<#assign outputText = outputText + "*Veteran endorsed substance abuse concerns, recommend outpatient consult to SARRTP.<br>"> 
		</#if>
		
		<#if ((var212.value)?? && var212.value == "true") || ((var9063.value)?? && var9063.value == "true")>
				<#assign outputText = outputText + "*Discuss  Veteran\'s prosthetics needs.<br>"> 
		</#if>

		<#if ((var65.value)?? && var65.value == "true") || ((var9086.value)?? && var9086.value == "true")>
				<#assign outputText = outputText + "*Provided information on Vocational Rehabilitation and WAVE Clinic.<br>"> 
		</#if>
		
		<#if ((var432.value)?? && var432.value == "true") || ((var9092.value)?? && var9092.value == "true")>
				<#assign outputText = outputText + "*Veteran requested Chaplain consult.<br>"> 
		</#if>

		<#if (var1229.value)??>
			<#assign v1 = var1229.value> <#-- AUDICT C Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 4)>
					<#assign part = "*Recommend SARRTP referral for alcohol abuse.<br>">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">


		<#if (var1010.value)??>
			<#assign v1 = var1010.value> <#-- DAST Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 3)>
					<#assign part = "*Recommend SARRTP referral for Substance Abuse.<br>">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if ((var603.value)?? && var603.value == "true") || ((var9122.value)?? && var9122.value == "true")>
				<#assign outputText = outputText + "*Recommend tobacco cessation.<br>"> 
		</#if>

		<#if ((var3442.value)?? && var3442.value == "true") || ((var9132.value)?? && var9132.value == "true")>
				<#assign outputText = outputText + "*Veteran requested TBI consult.<br>"> 
		</#if>

		<#if (var1599.value)??>
			<#assign v1 = var1599.value> <#-- PHQ9 Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 10)>
					<#assign part = "*Recommend psychiatry outpatient consult for depression.<br>">
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
								<#assign part = "*Recommend psychiatry outpatient consult for mood.<br>">
						</#if>
					</#if>
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if ((var1642.value)?? && var1642.value == "true") || ((9172.value)?? && 9172.value == "true")>
				<#assign outputText = outputText + "*Recommend MST consult.<br>"> 
		</#if>

		
		<#if (var1749.value)??>
			<#assign v1 = var1749.value> <#-- GAD7 Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 10)>
					<#assign part = "*Recommend psychiatry outpatient consult for anxiety.<br>">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if (var1989.value)?? >
			<#assign v2 = var1989.value> <#-- PTSD Score -->  
			<#if (v2 != "notset")> 
				<#if (v2?number >= 3)>
					<#assign part = "*Recommend OEF/OIF PTSD clinic consult.<br>">
				</#if>
			</#if>
		<#elseif (var1929.value)??>
			<#assign v3 = var1929.value> <#-- PCL Score -->  
			<#if (v3 != "notset")> 
				<#if (v3?number >= 50)>
					<#assign part = "*Recommend OEF/OIF PTSD clinic consult.<br>">
				</#if>
			</#if>
		</#if>

		<#assign outputText = outputText + part>
		<#assign part = "">

		<#if (var2189.value)??>
			<#assign v1 = var2189.value> <#-- ISI Score -->  
			<#if (v1 != "notset")> 
				<#if (v1?number >= 15)>
					<#assign part = "*Recommend psychiatry outpatient consult for insomnia.<br>">
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
				
				<#assign outputText = outputText + "*Recommend Anger Management consult.<br>"> 
		</#if>

		<#if ((var81.value)?? && var81.value == "true") || ((var9231.value)?? && var9231.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran employment resource information.<br>"> 
		</#if>

		<#if ((var242.value)?? && var242.value == "true") || ((var9251.value)?? && var9251.value == "true")
				|| ((var772.value)?? && var772.value == "true") || ((var9262.value)?? && var9262.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on housing resources and the VA homeless resources including HCHV outreach schedule.<br>"> 
		</#if>

		<#if ((var402.value)?? && var402.value == "true") || ((var9272.value)?? && var9272.value == "true")>
				
				<#assign outputText = outputText + "*Educated the Veteran on chaplain services.<br>"> 
		</#if>

		<#if ((var262.value)?? && var262.value == "true") || ((var9282.value)?? && var9282.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided information on financial resources.<br>"> 
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
				
				<#assign outputText = outputText + "*Provided information on legal resources.<br>"> 
		</#if>

		<#if ((var306.value)?? && var306.value == "true") || ((var9306.value)?? && var9306.value == "true")
				|| ((var307.value)?? && var307.value == "true") || ((var9307.value)?? && var9307.value == "true")
				|| ((var308.value)?? && var308.value == "true") || ((var9308.value)?? && var9308.value == "true")
				|| ((var309.value)?? && var309.value == "true") || ((var9309.value)?? && var9309.value == "true")
				|| ((var310.value)?? && var310.value == "true") || ((var9310.value)?? && var9310.value == "true")>
				
				<#assign outputText = outputText + "*Educated Veteran about Veteran’s Justice Outreach Program.<br>"> 
		</#if>

		<#if ((var253.value)?? && var253.value == "true") || ((var9323.value)?? && var9323.value == "true")
				|| ((var223.value)?? && var223.value == "true") || ((var9333.value)?? && var9333.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on the GI Bill, eBenefits, VA Work Study.<br>"> 
		</#if>

		<#if ((var223.value)?? && var223.value == "true") || ((var9241.value)?? && var9241.value == "true")>
				
				<#assign outputText = outputText + "*Provided the Veteran information on accessing unemployment resources.<br>"> 
		</#if>

		<#if ((var603.value)?? && var603.value == "true") || ((var9122.value)?? && var9122.value == "true")>
				<#assign outputText = outputText + "*Educated Veteran on Tobacco cessation support services.<br>"> 
		</#if>

		<#if ((var216.value)?? && var216.value == "true") || ((var9346.value)?? && var9346.value == "true")
				|| ((var1432.value)?? && var1432.value == "true") || ((var9352.value)?? && var9352.value == "true")
				|| ((var1433.value)?? && var1433.value == "true") || ((var9363.value)?? && var9363.value == "true")
				|| ((var942.value)?? && var942.value == "true") || ((var9372.value)?? && var9372.value == "true")
				|| ((var943.value)?? && var943.value == "true") || ((var9373.value)?? && var9373.value == "true")
				|| ((var932.value)?? && var932.value == "true") || ((var9382.value)?? && var9382.value == "true")
				|| ((var933.value)?? && var933.value == "true") || ((var9383	.value)?? && var9383.value == "true")>
				
				<#assign outputText = outputText + "*Provided information on Audiology and Optometry services.<br>"> 
		</#if>

		<#if ((var252.value)?? && var252.value == "true") || ((var9392.value)?? && var9392.value == "true")
				|| ((var1513.value)?? && var1513.value == "true") || ((var9403.value)?? && var9403.value == "true")>
				
				<#assign outputText = outputText + "*Encouraged the Veteran to meet with VSO for further advocacy and assistance with VA SCD claims process & provided VSO contact list.<br>"> 
		</#if>

		<#if ((var821.value)?? && var821.value == "true") || ((var9421.value)?? && var9421.value == "true")
				|| ((var828.value)?? && var828.value == "true") || ((var9432.value)?? && var9432.value == "true")>
				
				<#assign outputText = outputText + "*Provided VA Advanced Directives brochure & form.<br>"> 
		</#if>

			<#assign outputText = outputText + "*Educated Veteran on available VA mental health services (including outpatient general and specialty psychiatry clinics, PTSD and MST services, Anger Management, CORE and Family MH Clinic).<br>">
			<#assign outputText = outputText + "*Provided the Veteran information on the VA Vet Centers services.<br>">
			<#assign outputText = outputText + "*Educated the Veteran on alcohol and drug treatment services and reviewed ADTP walk-in hours & location.<br>">
			<#assign outputText = outputText + "*Alerted the PCP to the Veteran medical/health concerns & requests.<br>">
			<#assign outputText = outputText + "*Directed Veteran to Primary Care Call center for appointment scheduling.<br>">
			<#assign outputText = outputText + "*Provided VA environmental registry POC information.<br>">
			<#assign outputText = outputText + "*Educated the Veteran about same-day access clinic on 2North.<br>">
			<#assign outputText = outputText + "*Reviewed available resources including PEC, Emergency Dept., VA-Telecare and Suicide hotlines.<br>">
			<#assign outputText = outputText + "*Contact information of this writer, OEF/OIF brochure, and suicide prevention card were given to the Veteran. Encouraged to call for any further questions or concerns.<br><br>">
			<#assign outputText = outputText + "PLAN:<br>">
			<#assign outputText = outputText + "*Veteran plans to continue to access physical and mental healthcare through VA San Diego Healthcare System as needed.<br>">
			<#assign outputText = outputText + "*Veteran’s Future Appointments:<br>">
			<#assign outputText = outputText + "*Veteran agreed to the following consults:<br>">
			<#assign outputText = outputText + "*This writer will remain available to assist Veteran with transition and accessing VA Healthcare services as needed.<br>">
			<#assign outputText = outputText + "*Veteran to contact this writer for resources, referrals and other VA related healthcare inquiries.<br>">



		<#if errorText?has_content>
			${errorText}<br>
		<#else>
			${outputText}<br>
		</#if>
	</#assign>
	
	<#if !(footer_section = "") >
     	${footer_section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}