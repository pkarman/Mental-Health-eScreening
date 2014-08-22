/* ********************************************** */
/* template   UPDATES */
/* ********************************************** */


-- /* MEDICATION */
update escreening.template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
MEDICATIONS:
${MODULE_TITLE_END}
${MODULE_START}
  <#assign med_section>
	
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
  </#assign>

  <#if !(med_section = "") >
     ${med_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
'
where template_id = 22;







-- /* WHODAS */
update escreening.template 
set template_file = '
<#include "clinicalnotefunctions"> 
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
		<#--  var4200: ${var4200!""}<br><br>  --> var4499: ${var4499!""}<br><br> var4559: ${var4559!""}<br><br>  var4789: ${var4789!""}<br><br>  var4200: ${var4200!""}<br><br>	

 		<#if (var4119.value)?? && (var4239.value)?? && (var4319.value)?? && (var4419.value)?? 
				&& (var4499.value)?? && (var4559.value)?? && (var4789.value)?? && (var4200.children)?? 
				&& ((var4200.children)?size > 0)>
		
			<#t><b>Understanding and Communicating</b> - the Veteran had an average score of ${var4119.value} which indicates ${getScoreText(var4119.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Mobility</b> - the Veteran had an average score of ${var4239.value}, which indicates ${getScoreText(var4239.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Self-Care</b> - the Veteran had an average score of ${var4319.value} which indicates ${getScoreText(var4319.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Getting Along</b> - the Veteran had an average score of ${var4419.value} which indicates ${getScoreText(var4419.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Life Activities (Household/Domestic)</b> - the Veteran had an average score of ${var4499.value} which is a rating of ${getScoreText(var4499.value)} disability. ${LINE_BREAK}${LINE_BREAK} 
			
			<#assign avg = 0>
			<#if isSelectedAnswer(var4200, var4202)>
				<#if var4559.value != 0>
					<#assign avg = var4559.value/4>
				</#if>
				<b>Life Activities (School /Work)</b> - the Veteran had an average score of ${avg} which is a rating of ${getScoreText(avg)} disability. ${LINE_BREAK}${LINE_BREAK}    
			<#elseif isSelectedAnswer(var4200, var4201)>
				<#if var4559.value != 0>
					<#assign avg = var4559.value/4>
				</#if>
				<b>Life Activities (School /Work)</b> - the Veteran did not get a score because the veteran does not work or go to school. ${LINE_BREAK}${LINE_BREAK}   
			</#if>
			
			<b>Participation in Society</b> - the Veteran had an average score of ${avg} which indicates ${getScoreText(avg)} disability. ${NBSP} 

		<#else>
			${getNotCompletedText()}. ${NBSP}
		</#if>
 	</#assign>
  	<#if !(section = "") >
     	${section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}
'
where template_id = 24;



/* Homelessness Clinical Reminder */

UPDATE template SET template_id = 8, 
	template_type_id = 3, 
	name = 'Homelessness CR CPRS Note Entry', 
	description = 'Homelessness Clinical Reminder Module CPRS Note Entry', 
	template_file = 
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
'
where template_id = 8;








/*  SCORING MATRIX  */

UPDATE template SET template_id = 100, 
	template_type_id = 4, 
	name = 'OOO Battery CPRS Note Scoring Matrix', 
	description = 'OOO Battery CPRS Note Scoring Matrix', 
	template_file = 
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
${MODULE_END}'
 WHERE template_id = 100;


/* remove TEST template */
DELETE FROM battery_template WHERE template_id = 101;
DELETE FROM template WHERE template_id = 101;

	
	
 /* VETERAN SUMMARY -  TEMPLATES*/	
	
/* VETERAN SUMMARY HEADER INSERT */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (200, 6, 'Veteran Summary Header', 'Veteran Summary Header',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_START}
<#-- TODO: This should be changed to how our WYSIWYG editor outputs (e.g. tables) -->
<div class="row moduleTemplateHeader">
    <div class="col-md-6"><h5>eScreening Summary</h5></div>
    <div class="col-md-6 text-right"><img width="198" height="66" src=" resources/images/ logo_va_veteran_summary.gif "> <img width="130" height="56" src=" resources/images/cesamh_blk_border.png"></div>
</div>
${MODULE_END}
');	
INSERT INTO battery_template (battery_id, template_id) VALUES (5, 200);	
	
	/* Veteran Summary - Footer */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (220, 7, 'Veteran Summary Footer', 'Veteran Summary Footer',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_START}
${MATRIX_TABLE_START}<#-- TODO: This should be changed to how our WYSIWYG editor outputs (e.g. tables) -->
	${MATRIX_TR_START}
		${TABLE_TD_CTR_START}For online information about support services and benefits, visit the VA Center of Excellence resource site:${TABLE_TD_END}
	${MATRIX_TR_END}
	${TABLE_TR_CTR_START}
		${TABLE_TD_CTR_START}http://escreening.cesamh.org ${TABLE_TD_END}
	${TABLE_TR_END}
	${TABLE_TR_CTR_START}
		${TABLE_TD_CTR_START}${IMG_VA_VET_SMRY}${TABLE_TD_END}
	${TABLE_TR_END}
	${MATRIX_TR_START}
		${TABLE_TD_LFT_START}For confidential help and support any time, call the Veteran\'s Suicide Prevention/Crisis Hotline at 
(800) 273-8255. The Hotline is never closed; someone is always there to take your call, even on holidays and in the middle of the night. 
${TABLE_TD_END}
	${MATRIX_TR_END}
${MATRIX_TABLE_END}

${MODULE_END}
');
INSERT INTO battery_template (battery_id, template_id) VALUES (5, 220);
	



-- /* VETERAN SUMMARY - Advance Directive  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (300, 8, 'Veteran Summary Advance Directive Entry', 'Veteran Summary Advance Directive Entry',
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var820.children)?? && ((var820.children)?size > 0)>
	${MODULE_TITLE_START}
	Advance Directive
	${MODULE_TITLE_END}
	${MODULE_START}					

	<#assign scoreText = "">
	<#if isSelectedAnswer(var820,var821)>
		<#assign scoreText = "Declined">
	<#elseif isSelectedAnswer(var820,var822)>
		<#assign scoreText = "Complete">
	</#if>
	This is a legal paper that tells your wishes for treatment if you become too sick to talk, and if needed, can help your doctors and family to make decisions about your care. 
	${LINE_BREAK}
	${LINE_BREAK}
	<b>Results:</b> ${scoreText}	
	<#if scoreText == "Declined">
		${LINE_BREAK}
		<b>Recommendations:</b> Call VA Social Work Service at (858) 552-8585 ext. 3500, and ask for help in creating and filing an advance directive. 
	</#if>
</#if>
${MODULE_END} ');
INSERT INTO survey_template (survey_id, template_id) VALUES (9, 300);


-- /* VETERAN SUMMARY - Homelessness  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (301, 8, 'Veteran Summary Homelessness Entry', 'Veteran Summary Homelessness Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var2000.children)?? && ((var2000.children)?size > 0)>
	${MODULE_TITLE_START}
	Homelessness
	${MODULE_TITLE_END}
	${MODULE_START}

	<#assign scoreText = "">
	<#assign addRec = false>
	<#if isSelectedAnswer(var2000,var761)>
		<#assign scoreText = "unstable housing/at risk">
		<#assign addRec = true>
	<#elseif isSelectedAnswer(var2000,var762)>
		<#assign scoreText = "stable housing">
	</#if>

	<#if scoreText?has_content>
		This is when you do not have a safe or stable place you can return to every night. The VA is committed to ending Veteran homelessness by the end of 2015. 
		${LINE_BREAK}
		${LINE_BREAK}
		<b>Results:</b> ${scoreText}	
		<#if addRec>
			${LINE_BREAK}
			<b>Recommendation:</b> Call the VA\'s free National Call Center for Homeless Veterans at (877)-424-3838 and ask for help. Someone is always there to take your call.
		</#if>
	</#if>
</#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (7, 301);


-- /* VETERAN SUMMARY - Alcohol Use  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (302, 8, 'Veteran Summary Alcohol Use Entry', 'Veteran Summary Alcohol Use Entry', 
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#assign score = -999>
<#assign isComplete = false> 
<#if var1229??>
	<#assign score = getFormulaDisplayText(var1229)>
	<#if score != "notset" && score != "notfound">
		<#assign isComplete = true>
		<#assign score = score?number>
	</#if>
</#if>

<#if isComplete>
	${MODULE_TITLE_START}
	Alcohol Use
	${MODULE_TITLE_END}
	${MODULE_START}

	<#assign scoreText = "">
	<#if (score >= 0) && (score <= 2)>
		<#assign scoreText = "negative screen">
	<#elseif (score == 3) >
		<#assign scoreText = "at risk">
	<#elseif (score >= 4) && (score <= 12)>
		<#assign scoreText = "at risk">
	</#if>
	Drinking too much, too often, or both, causes serious problems. Abuse can have negative effects on school, work, and relationships, and can cause liver disease and cirrhosis, congestive heart failure, seizures, falls, hypertension, and other serious health risks.
	${LINE_BREAK}
	${LINE_BREAK}
	<b>Results:</b> ${scoreText}	${LINE_BREAK}
	<b>Recommendation:</b> If female, limit yourself to one drink a day; if male, limit yourself to 2 drinks a day. If this is difficult, ask your clinician for help with managing your drinking.  
</#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (26, 302);


-- /* VETERAN SUMMARY - Insomnia  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (303, 8, 'Veteran Summary Insomnia Entry', 'Veteran Summary Insomnia Entry', 
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#assign score = -999>
<#assign isComplete = false> 
<#if var2189??>
	<#assign score = getFormulaDisplayText(var2189)>
	<#if score != "notset" && score != "notfound">
		<#assign isComplete = true>
		<#assign score = score?number>
	</#if>
</#if>

<#if isComplete>
	${MODULE_TITLE_START}
	Insomnia
	${MODULE_TITLE_END}
	${MODULE_START}

	<#assign scoreText = "">
	<#if (score >= 0) && (score <= 7)>
		<#assign scoreText = "negative screen">
	<#elseif (score >= 8) && (score <= 14)>
		<#assign scoreText = "subthreshold insomnia">
	<#elseif (score >= 15) && (score <= 21)>
		<#assign scoreText = "moderate insomnia">
	<#elseif (score >= 22) && (score <= 28)>
		<#assign scoreText = "severe insomnia">
	</#if>
	Insomnia is having trouble sleeping that lasts longer than a few weeks. Some causes are: medical (like depression or pain), lifestyle factors (such as too much caffeine), or even stress. 
	${LINE_BREAK}
	${LINE_BREAK}
	<b>Results:</b> ${scoreText}	
	<#if (score >= 15)>
		${LINE_BREAK}
		<b>Recommendation:</b> Describe your sleeping problems to your clinician, or learn more about insomnia at the  CESAMH site at: http://escreening.cesamh.org 
	</#if>
</#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (36, 303);



-- /* VETERAN SUMMARY - Environmental Exposure  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (304, 8, 'Veteran Summary Environmental Exposure Entry', 'Veteran Summary Environmental Exposure Entry', '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var10540.children)??  && ((var10540.children)?size > 0)>
	${MODULE_TITLE_START}
	Environmental Exposure
	${MODULE_TITLE_END}
	${MODULE_START}  

	<#assign showRec = false>
	<#assign scoreText = "">
	<#if isSelectedAnswer(var10540,var10541)>
		<#assign scoreText = "none reported">
	<#elseif isSelectedAnswer(var10540,var10542)>
		<#assign scoreText = "at risk">
		<#assign showRec = true>
	</#if>
	This is when you have been exposed to a hazard that may have potential health risks.
	${LINE_BREAK}
	${LINE_BREAK}
	<b>Results:</b> ${scoreText}	
	<#if showRec>
		${LINE_BREAK}
		<b>Recommendation:</b> Call Dale Willoughby at the Environmental Registry Program and discuss your exposure: (858) 642-3995, weekdays 7:30am-4:00pm. 
	</#if>

</#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (14, 304);



-- /* VETERAN SUMMARY - Military Sexual Trauma (MST)  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (305, 8, 'Veteran Summary Military Sexual Trauma (MST) Entry', 'Veteran Summary Military Sexual Trauma (MST) Entry', 
'
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var2003.children)?? && ((var2003.children)?size > 0)>
	${MODULE_TITLE_START}
	Military Sexual Trauma (MST)
	${MODULE_TITLE_END}
	${MODULE_START}
	
	<#assign scoreText = "">
	<#assign showRec = -999>
	<#if isSelectedAnswer(var2003,var2004)>
		<#assign scoreText = "negative screen">
		<#assign showRec = 0>
	<#elseif isSelectedAnswer(var2003,var2005)>
		<#assign scoreText = "postive screen">
		<#assign showRec = 1>
	<#elseif isSelectedAnswer(var2003,var2006)>
		<#assign scoreText = "declined to answer">
	</#if>
	MST is sexual assault or repeated, threatening sexual harassment that occurred while the Veteran was in the military. MST can happen any time or anywhere, to men and women. MST can affect your physical and mental health, even years later.
	${LINE_BREAK}
	${LINE_BREAK}
	<b>Results:</b> ${scoreText}
`	<#if showRec == 0>
		${LINE_BREAK}
		<b>Recommendation:</b> none
	<#elseif showRec == 1>
		${LINE_BREAK}
		<b>Recommendation:</b> Ask your clinician for help managing your MST. 
	</#if>
</#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (32, 305);



-- /* VETERAN SUMMARY - Tobacco Use  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (306, 8, 'Veteran Summary Tobacco Use Entry', 'Veteran Summary Tobacco Use Entry', 
' 
<#include "clinicalnotefunctions"> 
<#-- Template start -->
<#if (var600.children)?? && ((var600.children)?size > 0)>
	${MODULE_TITLE_START}
	Tobacco Use
	${MODULE_TITLE_END}
	${MODULE_START}

	<#assign showRec = false>
	<#if isSelectedAnswer(var600,var601) || isSelectedAnswer(var600,var602)>
		<#assign scoreText = "negative screen">
	<#elseif isSelectedAnswer(var600,var603)>
		<#assign scoreText = "current user">
		<#assign showRec = true>
	</#if>
	The use of tobacco causes harm to nearly every organ in the body. Quitting greatly lowers your risk of death from cancers, heart disease, stroke, and emphysema. There are many options, such as in-person and telephone counseling, nicotine replacement, and prescription medications.
	${LINE_BREAK}
	${LINE_BREAK}
	<b>Results:</b> ${scoreText}	
	<#if showRec>
		${LINE_BREAK}
		<b>Recommendations:</b> Prepare a plan to reduce or quit the use of tobacco. Get support from family and friends, and ask your clinician for help if needed.  
	</#if>

</#if>
${MODULE_END}
');
INSERT INTO survey_template (survey_id, template_id) VALUES (25, 306);





-- /* VETERAN SUMMARY - Traumatic Brain Injury (TBI)  */
INSERT INTO template(template_id, template_type_id, name, description, template_file) VALUES (307, 8, 'Veteran Summary Traumatic Brain Injury (TBI) Entry', 'Veteran Summary Traumatic Brain Injury (TBI) Entry',
'<#include "clinicalnotefunctions"> 
<#-- Template start -->
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

<#if isComplete>
	${MODULE_TITLE_START}
	Traumatic Brain Injury (TBI)
	${MODULE_TITLE_END}
	${MODULE_START}  

	<#if (score >= 0) && (score <= 3)>
		<#assign scoreText = "negative screen">
	<#elseif (score >= 4 )>
		<#assign scoreText = "at risk">
	</#if>

	<#assign showRec = false>
	<#assign tbi_consult_text = "">
	<#if (var2047.children)?? && ((var2047.children)?size > 0) && isSelectedAnswer(var2047,var3442)>
		<#assign showRec = true>
		<#assign tbi_consult_text = "You have requested further assessment">
	<#elseif (var2047.children)?? && ((var2047.children)?size > 0) && isSelectedAnswer(var2047,var3441)>
		<#assign showRec = true>
		<#assign tbi_consult_text = "You have declined further assessment">
	</#if>
	A TBI is physical damage to your brain, caused by a blow to the head. Common causes are falls, fights, sports, and car accidents. A blast or shot can also cause TBI.
	${LINE_BREAK}
	${LINE_BREAK}
	<b>Results:</b> ${scoreText}	
	<#if showRec>
		${LINE_BREAK}
		<b>Recommendation:</b> ${tbi_consult_text}.
	</#if>
	${MODULE_END}
</#if>
');
INSERT INTO survey_template (survey_id, template_id) VALUES (29, 307);



-- /* VETERAN SUMMARY - My Depression Score */
INSERT INTO template(template_id, template_type_id, is_graphical, name, description, template_file) VALUES (308, 8, 1, 'Veteran Summary PHQ 9 Depression Entry', 'Veteran Summary PHQ 9 Depression Entry', 
'
<#include "clinicalnotefunctions"> 
<#if (var1599)?? >
	<#assign score = getSelectOneDisplayText(var1599)>
	<#-- Template start -->
	${MODULE_TITLE_START}
	Depression
	${MODULE_TITLE_END}
	
	${GRAPH_SECTION_START}

		${GRAPH_BODY_START}
		  {
	        "type": "stacked",
	        "title": "My Depression Score",
	        "footer": "*a score of 10 or greater is a positive screen",
	        "data": {
	            "graphStart": 0,
	            "ticks": [0, 4, 10, 15, 20, 27],
	            "intervals": {
	                "Minimal":4,
	                "Mild":10,
	                "Moderate":15,
	                "Moderately Severe":20,
	                "Severe":27
	            },
	            "score": ${score}
	        }
    	}
		${GRAPH_BODY_END}
	${GRAPH_SECTION_END}
	
	${MODULE_START}
	Depression is when you feel sad and hopeless for much of the time. It affects your body and thoughts, and interferes with daily life. There are effective treatments and resources for dealing with depression.${LINE_BREAK}
	<b>Recommendation:</b> Ask your clinician for further evaluation and treatment options. 
	${MODULE_END}
</#if>
');
INSERT INTO survey_template (survey_id, template_id) VALUES (30, 308);



-- /* VETERAN SUMMARY -  My Pain Score  (Basic Pain) */
INSERT INTO template(template_id, template_type_id, is_graphical, name, description, template_file) VALUES (309, 8, 1, 'Veteran Summary Basic Pain Score Entry', 'Veteran Summary Basic Pain Score Entry', 
'
<#include "clinicalnotefunctions"> 
<#if (var2300.children)??  &&  ((var2300.children)?size > 0)>
	<#assign score = getSelectOneDisplayText(var2300)>
	<#-- Template start -->
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
	            "graphStart": 0,
	            "ticks": [0,1,4,6,8,10],
	            "intervals": {
	                "None": 1,
	                "Mild": 4,
	                "Moderate": 6,
	                "Severe": 8,
	                "Very Severe":10
	            },
	            "score": ${score}
	        }
    	  }
		${GRAPH_BODY_END}
	${GRAPH_SECTION_END}

	${MODULE_START}
	Pain can slow healing and stop you from being active. Untreated pain can harm your sleep, outlook, and ability to do things. ${LINE_BREAK}
	<b>Recommendation:</b> Tell your clinician if medications aren’t reducing your pain, or if the pain suddenly increases or changes, and ask for help with managing your pain. 
	${MODULE_END}	

</#if>
');
INSERT INTO survey_template (survey_id, template_id) VALUES (20, 309);



-- /* VETERAN SUMMARY -  My PTSD Score*/
INSERT INTO template(template_id, template_type_id, is_graphical, name, description, template_file) VALUES (310, 8, 1, 'Veteran Summary PTSD Entry', 'Veteran Summary PTSD Entry',
'
<#include "clinicalnotefunctions"> 
<#if var1989??>
	<#assign score = getCustomVariableDisplayText(var1989)>

	<#-- Template start -->
	${MODULE_TITLE_START}
	Post-traumatic Stress Disorder 
	${MODULE_TITLE_END}
	
	${GRAPH_SECTION_START}

		${GRAPH_BODY_START}
		  {
	        "type": "stacked",
	        "title": "My PTSD Score",
	        "footer": "",
	        "data": {
	            "graphStart": 17,
	            "ticks": [17,35,50,65,85],
	            "intervals": {
	                "Negative": 50,
	                "Positive":85
	            },
	            "score": ${score}
	        }
    	  }
		${GRAPH_BODY_END}
	${GRAPH_SECTION_END}
	
	${MODULE_START}
	PTSD is when remembering a traumatic event keeps you from living a normal life. It’s also called shell shock or combat stress. Common symptoms include recurring memories or nightmares of the event, sleeplessness, and feeling angry, irritable, or numb. ${LINE_BREAK}
	<b>Recommendation:</b> Ask your clinician for further evaluation and treatment options. 
	${MODULE_END}
</#if>
');
INSERT INTO survey_template (survey_id, template_id) VALUES (35, 310);



-- /* PRESENTING CONCERN(S) UPDATE */
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
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

	<#if (fragments?size > 0)>
		The Veteran indicated that he/she would like information or assistance with the following: ${createSentence(fragments)}.${NBSP}
	<#else>
		The Veteran indicated that he/she would not like information or assistance with Healthcare, VA BEnefits, Social, Legal or Housing concerns.
	</#if>
   <#else>
	 ${getNotCompletedText()}
   </#if>
  </#assign>
  <#if !(concerns_section = "") >
     ${concerns_section}
  <#else>
     ${noParagraphData}
  </#if>
${MODULE_END}
'
where template_id = 3;




-- /* DEMOGRAPHICS UPDATE */
update template 
set template_file = '
<#include "clinicalnotefunctions"> 
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
' where template_id = 3;



-- /* DEMOGRAPHICS UPDATE*/
update escreening.template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
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
    
		The Veteran is a ${age} year-old<#t>
        
		<#if isSelectedAnswer(var30,var33) >
          ,  <#t>
        <#else>
          ${""?left_pad(1)}whom is ${getSelectOneDisplayText(var30)},  <#t> 
        </#if> 
    
    <#-- The Veteran reports being a White/Caucasian, American Indian or Alaskan Native male. -->
    <#if hasValue(getSelectMultiDisplayText(var40)) || hasValue(getSelectOneDisplayText(var20)) >
      <#-- The Veteran reports being<#t>  -->
      <#if hasValue(getSelectMultiDisplayText(var40)) >
        ${""?left_pad(1)} ${getSelectMultiDisplayText(var40)}<#t>
      </#if> 
      <#if hasValue(getSelectOneDisplayText(var20)) >
        ${""?left_pad(1)}${getSelectOneDisplayText(var20)}<#t>
      </#if> 
      .  <#t>
    </#if> 
   
    <#--The Veteran reported BMI is 27, indicating that he/she may be is overweight.-->
    <#if hasValue(getFormulaDisplayText(var11))  >
      <#assign num = getFormulaDisplayText(var11)?number />
      The Veteran\'s reported BMI is ${num}, indicating that he/she <#t>
      <#if (num < 18.5) >
        is below a normal a weight.  <#t>
      <#elseif (num < 25) && (num >= 18.5) >
        is at a normal weight.  <#t>
      <#elseif (num < 30) && (num >= 25) >
        is overweight.  <#t>
      <#elseif (num < 40) && (num >= 30) >
        is obese.  <#t>
      <#elseif (num >= 40) >
        is morbid obese.  <#t>
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
'
where template_id = 4;


-- /* SOCIAL UPDATE */
update escreening.template 
set template_file = '
<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
SOCIAL: 
${MODULE_TITLE_END}
${MODULE_START}
  <#assign social_section> 
	<#if (var90.children)?? && (var160.children)??  && (var130.children)??  
			&& ((var90.children)?size > 0) && ((((var160.children)?size > 0) && ((var130.children)?size > 0)) || ((var131.value)?? && var131.value == "true")) >
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
		<#assign rowCount = ((var130.children)?size)!0>
        <#if !(rowCount = -1) >
          <#if (rowCount = 0) >
            The Veteran reported not having any children. ${NBSP}
          <#elseif (rowCount == 1) >
			<#assign text = ((var130.children[0].children[0].displayText)!"")>
            The Veteran has 1 child who is ${text} years old. ${NBSP}
          <#elseif (rowCount > 1) >
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
${MODULE_END}
'
where template_id = 6;


-- /* SPIRITUAL HEALTH */
update escreening.template 
set template_file = '
<#include "clinicalnotefunctions"> 
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
			The Veteran feels that combat or military service ${getSelectOneDisplayText(var440)} have an effect on his/her religious views.${NBSP}
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
'
where template_id = 11;


-- /* WHODAS UPDATE */
update escreening.template 
set template_file = '
<#include "clinicalnotefunctions"> 
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
				&& (var4499.value)?? && (var4789.value)?? && (var4200.children)?? 
				&& ((var4200.children)?size > 0)>
		  <#if (var4200.children)?? && ((isSelectedAnswer(var4200, var4201)) ||  (isSelectedAnswer(var4200, var4202) && var4559??))>
		
			<#t><b>Understanding and Communicating</b> - the Veteran had an average score of ${var4119.value} which indicates ${getScoreText(var4119.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Mobility</b> - the Veteran had an average score of ${var4239.value}, which indicates ${getScoreText(var4239.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Self-Care</b> - the Veteran had an average score of ${var4319.value} which indicates ${getScoreText(var4319.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Getting Along</b> - the Veteran had an average score of ${var4419.value} which indicates ${getScoreText(var4419.value)} disability. ${LINE_BREAK}${LINE_BREAK}

			<b>Life Activities (Household/Domestic)</b> - the Veteran had an average score of ${var4499.value} which is a rating of ${getScoreText(var4499.value)} disability. ${LINE_BREAK}${LINE_BREAK} 
			
			<#assign avg = 0>
			<#if isSelectedAnswer(var4200, var4202)>
				<#if (var4559.value)?? && var4559.value?number != 0>
					<#assign avg = var4559.value?number/4>
				</#if>
				<b>Life Activities (School /Work)</b> - the Veteran had an average score of ${avg} which is a rating of ${getScoreText(avg)} disability. ${LINE_BREAK}${LINE_BREAK}    
			<#elseif isSelectedAnswer(var4200, var4201)>
				<#if (var4559.value)?? && var4559.value?number != 0>
					<#assign avg = var4559.value?number/4>
				</#if>
				<b>Life Activities (School /Work)</b> - the Veteran did not get a score because the veteran does not work or go to school. ${LINE_BREAK}${LINE_BREAK}   
			</#if>
			
			<b>Participation in Society</b> - the Veteran had an average score of ${avg} which indicates ${getScoreText(avg)} disability. ${NBSP} 
		  <#else>
			${getNotCompletedText()}.
		  </#if>
		<#else>
			${getNotCompletedText()}.
		</#if>
 	</#assign>
  	<#if !(section = "") >
     	${section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}
'
where template_id = 24;



-- /* ROAS AGGRESSION UPDATE */
update escreening.template 
set template_file = '
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
				<#if (getQuestionCalcValue(var3200) > 1) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["shouting angrily"]>
				</#if>
				<#if (getQuestionCalcValue(var3210) > 1) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["yelling mild insults"]>
				</#if>
				<#if (getQuestionCalcValue(var3220) > 1) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["making vague threats"]>
				</#if>
				<#if (getQuestionCalcValue(var3230) > 1) >
					<#assign verbalAggressionSubText = verbalAggressionSubText + ["making clear threats of violence"]>
				</#if>
				<#if verbalAggressionSubText?has_content >      
					<#assign verbalAggressionText = "Verbal Aggression (" + createSentence(verbalAggressionSubText) + ")">
					<#assign fragments = fragments + [verbalAggressionText] >
				</#if>

				<#assign physicalAggressionObjectsText = "">
				<#assign physicalAggressionObjectsSubText = []>
				<#if (getQuestionCalcValue(var3240) > 1) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["slamming doors"]>
				</#if>
				<#if (getQuestionCalcValue(var3250) > 1) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["throwing objects"]>
				</#if>
				<#if (getQuestionCalcValue(var3260) > 1) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["breaking objects or smashing windows"]>
				</#if>
				<#if (getQuestionCalcValue(var3270) > 1) >
					<#assign physicalAggressionObjectsSubText = physicalAggressionObjectsSubText + ["setting fires or throwing object dangerously"]>
				</#if>
				<#if physicalAggressionObjectsSubText?has_content >      
					<#assign physicalAggressionObjectsText = "Physical aggression toward objects (" + createSentence(physicalAggressionObjectsSubText) + ")">
					<#assign fragments = fragments + [physicalAggressionObjectsText] >
				</#if>



				<#assign physicalAggressionSelfText = "">
				<#assign physicalAggressionSelfSubText = []>
				<#if (getQuestionCalcValue(var3280) > 1) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["hitting self or pulling own hair"]>
				</#if>
				<#if (getQuestionCalcValue(var3290) > 1) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["banging head or fists against objects"]>
				</#if>
				<#if (getQuestionCalcValue(var3300) > 1) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["cutting or bruising self"]>
				</#if>
				<#if (getQuestionCalcValue(var3310) > 1) >
					<#assign physicalAggressionSelfSubText = physicalAggressionSelfSubText + ["other serious self injury"]>
				</#if>
				<#if physicalAggressionSelfSubText?has_content >      
					<#assign physicalAggressionSelfText = "Physical aggression toward self (" + createSentence(physicalAggressionSelfSubText) + ")">
					<#assign fragments = fragments + [physicalAggressionSelfText] >
				</#if>

				<#assign physicalAggressionOthersText = "">
				<#assign physicalAggressionOthersSubText = []>
				<#if (getQuestionCalcValue(var3320) > 1) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["swinging at people or grabbing others"]>
				</#if>
				<#if (getQuestionCalcValue(var3330) > 1) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["striking or pushing on others"]>
				</#if>
				<#if (getQuestionCalcValue(var3340) > 1) >
					<#assign physicalAggressionOthersSubText = physicalAggressionOthersSubText + ["bruising or causing welts on others"]>
				</#if>
				<#if (getQuestionCalcValue(var3350) > 1) >
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
'
where template_id = 38;