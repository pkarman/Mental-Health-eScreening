<#-- Constants -->
<#assign noParagraphData='The information required to generate this section has not been supplied.' />

<#-- If these are changed please update TemplateTags.java -->
<#assign BATTERY_HEADER_START='<BATTERY_HEADER_START>' />
<#assign BATTERY_HEADER_END='<BATTERY_HEADER_END>' />
<#assign BATTERY_FOOTER_START='<BATTERY_FOOTER_START>' />
<#assign BATTERY_FOOTER_END='<BATTERY_FOOTER_END>' />

<#assign SECTION_TITLE_START='<SECTION_TITLE_START>' />
<#assign SECTION_TITLE_END='<SECTION_TITLE_END>' />
<#assign SECTION_START='<SECTION_START>' />
<#assign SECTION_END='<SECTION_END>' />

<#assign MODULE_TITLE_START='<MODULE_TITLE_START>' />
<#assign MODULE_TITLE_END='<MODULE_TITLE_END>' />
<#assign MODULE_START='<MODULE_START>' />
<#assign MODULE_END='<MODULE_END>' />

<#assign LINE_BREAK='<LINE_BREAK>' />
<#assign NBSP = '<NBSP>'/>

<#assign MATRIX_TABLE_START = '<MATRIX_TABLE_START>' />
<#assign MATRIX_TABLE_END = '<MATRIX_TABLE_END>'/>
<#assign MATRIX_TR_START = '<MATRIX_TR_START>'/>
<#assign TABLE_TR_CTR_START = '<TABLE_TR_CTR_START>'/>
<#assign TABLE_TR_END = '<TABLE_TR_END>'/>
<#assign MATRIX_TR_END = '<MATRIX_TR_END>'/>
<#assign MATRIX_TD_START = '<MATRIX_TD_START>'/>
<#assign MATRIX_TD_END = '<MATRIX_TD_END>'/>
<#assign MATRIX_TH_START = '<MATRIX_TH_START>'/>
<#assign MATRIX_TH_END = '<MATRIX_TH_END>'/>
<#assign TABLE_TD_RT_START = '<TABLE_TD_RT_START>'/>
<#assign TABLE_TD_CTR_START = '<TABLE_TD_CTR_START>'/>
<#assign TABLE_TD_LFT_START = '<TABLE_TD_LFT_START>'/>
<#assign TABLE_TD_SPACER1_START = '<TABLE_TD_SPACER1_START>'/>
<#assign TABLE_TD_END = '<TABLE_TD_END>'/>

<#assign IMG_LOGO_VA_HC = '<IMG_LOGO_VA_HC>'/>
<#assign IMG_CESMITH_BLK_BRDR = '<IMG_CESMITH_BLK_BRDR>'/>
<#assign IMG_VA_VET_SMRY = '<IMG_VA_VET_SMRY>'/> 

<#assign GRAPH_SECTION_START = '<GRAPH_SECTION_START>'/>
<#assign GRAPH_SECTION_END = '<GRAPH_SECTION_END>'/> 
<#assign GRAPH_BODY_START = '<GRAPH_BODY_START>'/>  
<#assign GRAPH_BODY_END = '<GRAPH_BODY_END>'/>

<#-- String functions -->
<#function hasValue stringValue>
	<#if stringValue='notfound' >
		<#return false>
	<#else>
		<#return true>
  	</#if>	
</#function>

<#function doesValueOneEqualValueTwo stringValueOne stringValueTwo>
	<#if stringValueOne=stringValueTwo >
		<#return true>
	<#else>
		<#return false>
	</#if>
</#function>

<#-- same as function doesValueOneEqualValueTwo just shorter function name -->
<#function equals stringValueOne stringValueTwo>
	<#if stringValueOne=stringValueTwo >
		<#return true>
	<#else>
		<#return false>
	</#if>
</#function>

<#-- checks if a specific answer was selected given a question -->
<#function  isSelectedAnswer variableObj1 variableObj2 > 
	<#if (variableObj1.children)?? && (variableObj2)??> 
	<#list variableObj1.children as v>
		<#if (v.variableId)?? && (variableObj2.variableId)?? && v.variableId = variableObj2.variableId>
			<#return true>
		</#if>
	</#list>
	</#if>
	<#return false>
</#function>


<#-- get a question's score -->
<#-- example: "getScore(var1)"-->
<#function  getScore variableObj > 
	<#assign total = 0>
	<#if (variableObj)?? > 
		<#list variableObj.children as v>
			<#if ((v.calculationValue)?? && (v.calculationValue)?has_content && (v.value)?? && v.value = 'true')>	
				<#assign num = (v.calculationValue)?number>
				<#assign total = total + num>
			</#if>
		</#list>
	</#if>
	<#return total>
</#function>


<#-- get a list of question's score. (it is the same as getScore() with the exception that this f(x) takes a list of obj's instead of a single obj as a param.)-->
<#-- example: "getListScore([var1, var2, var3, var4])"-->
<#function  getListScore objList > 
	<#assign tot = 0>
	<#if (objList)?? > 
		<#list objList as o>
			<#assign nbr = getScore(o)>
			<#if nbr??>
				<#assign tot = tot + nbr?number>
			</#if>
		</#list>
	</#if>
	<#return tot>
</#function>

<#function getQuestionCalcValue obj >
	<#assign result = -999>
	
	<#if (obj.children[0].calculationValue)?has_content>
		<#assign result = (obj.children[0].calculationValue)?number>
	</#if>
	
	<#return result>
</#function>
<#-- Formula, Answer, and Custom variable functions -->
<#-- Facade for WYSIWYG formula type variable -->
<#function getFormulaDisplayText variableObj='notset'>
	${getVariableDisplayText(variableObj)}
</#function>

<#-- Facade for WYSIWYG custom type variable -->
<#function getCustomVariableDisplayText variableObj='notset'>
	${getVariableDisplayText(variableObj)}
</#function>

<#-- Facade for WYSIWYG measure answer type variable -->
<#function getAnswerDisplayText variableObj='notset'>
	${getVariableDisplayText(variableObj)}
</#function>

<#function wasAnswerNone variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || variableObj.children?size=0 >
    <#-- The object was not found or there was a problem with the list -->
    <#return false>
  <#else>
     <#list variableObj.children as x>
       <#if x.value?? && x.value='true' && x.type?? && x.type='none'>
	     <#return true>
       </#if>
     </#list>
     <#return false>
  </#if>
</#function>

<#function getVariableDisplayText variableObj='notset'>
  <#if variableObj = 'notset'>
    <#-- The object was not found -->
    <#return 'notfound'>
  <#else>
    <#-- If there is an other value use that before anything else -->
    <#if variableObj.otherText?? && !(variableObj.otherText='') >
      <#return variableObj.otherText >
    </#if>
    <#-- If there is an override value use that before the default value -->
    <#if variableObj.overrideText?? && !(variableObj.overrideText='') >
      <#return variableObj.overrideText >
    </#if>
    <#-- Use the default text -->
    <#if variableObj.displayText?? && !(variableObj.displayText='') >
      <#return variableObj.displayText >
    </#if>
    <#-- The object does not have text to return -->
    <#return 'notfound' >
  </#if>
</#function>

<#function getFreeformDisplayText obj>
	
	<#list obj.children as c>
		<#if (c.value)?has_content>
			<#return c.value>
		</#if>
	</#list>
	 
	<#return "">
</#function>

<#function wasAnswerTrue variableObj='notset'>
  <#if variableObj = 'notset'>
    <#-- The object was not found -->
    <#return false>
  <#else>
    <#if variableObj.value?? && !(variableObj.value='') && variableObj.value='true'>
      <#return true>
    <#else>
      <#return false>
    </#if>
  </#if>
</#function>

<#-- Measure level functions -->
<#function getNumberOfTableResponseRows variableObj='notset'>
  <#if variableObj = 'notset'>
    <#-- The object was not found -->
    <#return -1>
  <#else>
    <#if variableObj.children?? >
      <#return variableObj.children?size>
    <#else>
      <#return 0>
    </#if>
  </#if>
</#function>

<#function getSelectOneDisplayText variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || !(variableObj.children?size=1) >
    <#-- The object was not found or there was a problem with the list -->
    <#return 'notfound'>
  <#else>
	${getVariableDisplayText(variableObj.children[0])}
  </#if>
</#function>

<#function getSelectOneListDisplayText variableObjList>
  <#if variableObjList?has_content >
  	<#assign text = "">
  	<#list variableObjList as e>
  		<#if !(e_has_next) >
    		<#assign text = e + " and "> 
    	</#if>
    	<#assign text = text + getVariableDisplayText(e.children[0])>
    	<#if e_has_next>
    		<#assign text = e + ", ">
    	</#if><#t>
    </#list>
  </#if>
  
  <#return text>
</#function>


<#function createSentence aList>
  <#assign text = "">
  	<#if (aList!?size > 0)>  
  	<#list aList as e>
  		<#if ((text?length) >0) >
  			<#if !(e_has_next) >
  				<#assign text = text + " and "> 
    		</#if>
    		
    		<#if e_has_next>
    			<#assign text = text + ", ">
    		</#if><#t>
    	</#if>
    	<#assign text = text + e >
    </#list>
   	</#if>
   	
 	<#return text>
</#function>

<#function getSelectMultiDisplayText variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || variableObj.children?size=0 >
    <#-- The object was not found or there was a problem with the list -->
    <#return 'notfound'>
  <#else>
    <#assign sentenceFragement>
      <#compress>
        <#-- Filter the list by true values -->
        <#list variableObj.children as x>
          <#if variableObj.children?size = 1>
            ${getVariableDisplayText(x)}
          <#else>
            <#if !(x_has_next) >and </#if>${getVariableDisplayText(x)}<#if x_has_next>, </#if><#t>
          </#if>
        </#list>
      </#compress>
    </#assign>
    <#return sentenceFragement>
  </#if>
</#function>

<#function getTableMeasureDisplayText variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || variableObj.children?size=0 >
    <#-- The object was not found or there was a problem with the list -->
    <#return ''>
  <#else>
    <#assign sentenceFragement>
      <#compress>
        <#list variableObj.children as x>
          <#if x.children?? && (x.children?size > 0) >
            <#list x.children as y>
              <#if y.children?size = 1>
                ${getVariableDisplayText(y)}
              <#else>
                <#if !(x_has_next) && (x.children?size > 0) >and </#if>${getVariableDisplayText(y)}<#if x_has_next>, </#if><#t>
              </#if>
            </#list>
          </#if>
        </#list>
      </#compress>
    </#assign>
    <#return sentenceFragement>
  </#if>
</#function>



<#function getTableMeasureValueDisplayText variableObj='notset'>
  <#if variableObj='notset' || !(variableObj.children??) || variableObj.children?size=0 >
    <#-- The object was not found or there was a problem with the list -->
    <#return ''>
  <#else>
    <#assign sentenceFragement>
     	<#assign col = []>
        <#list variableObj.children as x>
          <#if x.children?? && (x.children?size > 0) >
            <#list x.children as y>
            	<#if y.value?? && y.value?has_content>
            		<#assign col = col + [y.value]>
            	</#if>
            </#list>
          </#if>
        </#list>
        <#-- don't separate this expression into multiple lines -->
        <#t><#list col as c><#if col?size = 1><#t>${c}<#t><#else><#if !(c_has_next) >and </#if>${c}<#if c_has_next>, </#if></#if></#list><#t>
    </#assign>
    <#return sentenceFragement>
  </#if>
</#function>


<#function isNull variableObj>
	<#if !((variableObj)?has_content)>
		<#return true>
	<#else>
		<#return false>
	</#if> 

</#function>

<#function isSet str>
	<#if str?? && (str?has_content) && ( (str?trim) != "notset") &&  ((str?trim) != "notfound")>
		<#return true>
	<#else>
		<#return false>
	</#if> 

</#function>

<#function getNotCompletedText >
	<#assign text = "Veteran has not completed this module"> 
	<#return  text>
</#function>

<#function getAnsweredNoText module >
	<#assign text = "Veteran answered \"No\" to questions in " + module + " assessment module"> 
	<#return  text>
</#function>

<#function getAnsweredNoAllText module >
	<#assign text = "Veteran answered \"No\" to all questions in the " + module + " assessment module"> 
	<#return  text>
</#function>

<#function getAnsweredNoneAllText module >
	<#assign text = "Veteran answered \"None\" to all questions in the " + module + " assessment module"> 
	<#return  text>
</#function>

<#function getAnsweredNeverAllText module >
	<#assign text = "Veteran answered \"Never\" to all questions in the " + module + " assessment module"> 
	<#return  text>
</#function>

<#function calcAge dt>
	<#assign age = "">
	<#if dt?? >
		<#assign today = .now?date>
		<#assign bday = dt?date("MM/dd/yyyy")>
		<#assign age = ((((today?long - bday?long) / (1000 * 60 * 60 * 24))?int)/365)?int>
	</#if>
	
	<#return age>
</#function>


<#-- group obj's but their row -->
<#function groupRows objs>
	<#assign rows = {}>
	<#assign children = (objs.children)![]>
	
	<#list children as c>
		<#assign child = c.children[0]>
		<#if (child.row)?? >
		<#assign i = (child.row)?string>
		
		<#if rows?? && (i?number > 0) && (rows.get(i))?has_content>
			
			<#assign r = rows.get(i) + [child]>
			<#assign rows = rows + {i: r}>
		<#else>
			
			<#assign rows = rows + {i: [child]}>
		</#if>
		</#if> 
	</#list>
	
	<#return rows>
</#function>