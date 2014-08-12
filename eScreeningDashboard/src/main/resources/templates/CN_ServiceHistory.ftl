<#include "clinicalnotefunctions"> 
<#-- Template start -->
${MODULE_TITLE_START}
${LINE_BREAK}
SERVICE HISTORY: 
${MODULE_TITLE_END}
${MODULE_START}
	
 	<#assign history_section>
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
						+ ", received an " + dischargeType + " discharge in " + end_yr + " at the rank of " + rank + ".<br><br>")>
				<#else>
					<#assign outputText = ("The Veteran reported entering the " + type + " " + branch + " in " + beg_yr 
						+ ", received an " + dischargeType + " discharge in " + end_yr + " at the rank of " + rank + ".<br><br>")>
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
					${getNotCompletedText()}.
					<#-- no need to continue if we are finished processing a row and a var is missing -->
					<#break>
			<#else>
					${outputText}
					
			</#if>
		  </#list>
			
		  <#if allAnswersPresent>
			The Veteran also served in the following operations or combat zones: ${getSelectMultiDisplayText(var3160)}.<br><br>
		  </#if>
		<#else>	 
			${getNotCompletedText()}.
		</#if>
	</#assign>
  	
	<#if !(history_section = "") >
     	${history_section}
  	<#else>
     	${noParagraphData}
  </#if> 
${MODULE_END}