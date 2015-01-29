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
            "stackGraphParams": {
            	"maxXPoint" : 10,
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
            },
             "timeSeriesParams": {
            	"intervals": [
					{"key": "None 0-1", "range": "1", "color": "#75cc51"},
					{"key": "Mild 1-4", "range": "1-4", "color": "#f4e800"},
					{"key": "Moderate 4-6", "range": "4-6", "color": "#ff9e58"},
					{"key": "Severe 6-8", "range": "6-8", "color": "#ad3332"},
					{"key": "Very Severe 8-10", "range": "8-10", "color": "#7a100f"},
				]
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


update template set template_file = '<#include "clinicalnotefunctions"> 
<#assign score = getCustomVariableDisplayText(var1929)>  
<#-- Template start --> 
${MODULE_TITLE_START} Post-traumatic Stress Disorder ${MODULE_TITLE_END}  
<#if score != "notfound">
	${GRAPH_SECTION_START}  
	${GRAPH_BODY_START} 
		{"varId": 1929, 
		 "title": "My PTSD Score", 
		 "footer": "", 
		 "numberOfMonths": 12, 
		 "stackGraphParams": {
		 	"maxXPoint" : 85,
		 	"ticks": [17,31,44,65,85], 
		 	"intervals": {
		 		"Negative":17, 
		 		"Positive":44 
		 	}, 
		 	"score": ${score} 
		 },
		 "timeSeriesParams": {
            	"intervals": [
					{"key": "Negative 17-44", "range": "17-44", "color": "#75cc51"},
					{"key": "Positive 44-85", "range": "44-85", "color": "#450404"}
				]
            } 
		  
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