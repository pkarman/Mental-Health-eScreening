/* phq-9 veteran summary. Update for time series graph  (719) */
update template set template_file = '<#include "clinicalnotefunctions"> 
<#assign score = getFormulaDisplayText(var1599)>
<#if score != "notfound">
	${MODULE_TITLE_START} Depression ${MODULE_TITLE_END} 
	
	${GRAPH_SECTION_START} 
	${GRAPH_BODY_START} 
		{
			"varId": 1599, 
		 	"title": "My Depression Score", 
 			"footer": "*a score of 10 or greater is a positive screen", 
		 	"footer": "", 
			"numberOfMonths": 12, 
	 		"stackGraphParams": {
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
            },
            "timeSeriesParams": {
            	"intervals": [
					{"key": "None 0-1", "range": "1", "color": "#75cc51"},
					{"key": "Minimally Depressed 1-5", "range": "5", "color": "#f4e800"},
					{"key": "Mildly Depressed 5-10", "range": "10", "color": "#ff9e58"},
					{"key": "Moderatly Depressed 10-15 and above", "range": "15", "color": "#ad3332"},
					{"key": "Moderately Severe Depressed 15-20 and above", "range": "15", "color": "#7a100f"},
					{"key": "Severely Depressed 20-27 and above", "range": "27", "color": "#450404"}
				]
            } 
	}
	${GRAPH_BODY_END} 
	${GRAPH_SECTION_END}  
	
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
	${MODULE_END}
</#if>' 
where template_id=308;