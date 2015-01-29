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
		"varId": 2300,
		"title": "My Pain Score",
		"footer": "",
		"numberOfMonths": 12,
		"score": ${getSelectOneDisplayText(var2300)},
		"intervals": {
				"None":0,
				"Minimal":1,
				"Moderate":4,
				"Severe":6,
				"Very Severe":8,
				"Worst Possible":10
		},
		"maxXPoint" : 10, "ticks": [0, 1, 4, 6, 8, 10]
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


update template set template_file = '<#include "clinicalnotefunctions"> 
<#assign score = getCustomVariableDisplayText(var1929)>  
<#-- Template start --> 
${MODULE_TITLE_START} Post-traumatic Stress Disorder ${MODULE_TITLE_END}  
<#if score != "notfound">
	${GRAPH_SECTION_START}  
	${GRAPH_BODY_START} 
		{ 	
			"varId": 1929,
			"title": "My PTSD Score",
			"footer": "",
			"footer": "",
			"numberOfMonths": 12,
			"score": ${score},
			"intervals": {
					"Negative":17,
					"Positive":44
			},
			"maxXPoint" : 85, "ticks": [17, 31, 44, 65, 85]
		}
	${GRAPH_BODY_END} 
	${GRAPH_SECTION_END}  
</#if>
${MODULE_START} 
	PTSD is when remembering a traumatic event keeps you from living a normal life. It\'s also called shell shock or combat stress. Common symptoms include recurring memories or nightmares of the event, sleeplessness, and feeling angry, irritable, or numb. 
	${LINE_BREAK} 
	<b>Recommendation:</b> 
	<#if score = "notfound">
		${NBSP}Veteran declined to respond
	<#elseif score?number gt 43>
		Ask your clinician for further evaluation and treatment options
	<#elseif score?number lt 44>
		You may ask a clinician for help in the future if you feel you may have symptoms of PTSD
	</#if> 
${MODULE_END}' 
where template_id=310;

update template set template_file = '
<#include "clinicalnotefunctions"> <#assign score = getFormulaDisplayText(var1599)> <#if score != "notfound"> ${MODULE_TITLE_START} Depression ${MODULE_TITLE_END}  
${GRAPH_SECTION_START}
${GRAPH_BODY_START}
	{ 	
					"varId": 1599,
					"title": "My Depression Score",
					"footer": "*a score of 10 or greater is a positive screen",
					"footer": "",
					"numberOfMonths": 12,
					"score": ${score},
					"intervals": {
							"None":0,
							"Minimal":1,
							"Mild":5,
							"Moderate":10,
							"Moderately Severe":15,
							"Severe":20
					},
					"maxXPoint" : 27, "ticks": [0, 1, 5, 10, 15, 20, 27]
				}
${GRAPH_BODY_END}
${GRAPH_SECTION_END}  ${MODULE_START} Depression is when you feel sad and hopeless for much of the time. It affects your body and thoughts, and interferes with daily life. There are effective treatments and resources for dealing with depression. ${LINE_BREAK} <b>Recommendation:</b> 
<#if score = "notfound"> ${NBSP}Veteran declined to respond <#elseif score?number lt 10> Contact a clinician if in the future if you ever feel you may have a problem with depression 
<#elseif score?number gt 9> Ask your clinician for further evaluation and treatment options </#if> ${MODULE_END} </#if>'
where template_id=308;