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

<#-- if this is changed please update ExpressionExtentionUtil -->
<#assign DEFAULT_VALUE = 'notset' />

<#-- Please remember: be very careful about naming variables. If a variable 
in the function1 is named the same thing as a variable in a function2 which 
is called from function1, changes made in function2 *will change* the variable in function1.
For this to happen, you don't have to pass the variable from function1 to function2.  
It is as if the scope bleeds over...a hard lesson learned :( -->


<#-- ******** Legacy functions which are found in handwritten templates ******** -->

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

<#-- get a select question's score -->
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
<#function getFormulaDisplayText variableObj=DEFAULT_VALUE>
	${getVariableDisplayText(variableObj)}
</#function>

<#-- Facade for WYSIWYG custom type variable -->
<#function getCustomVariableDisplayText variableObj=DEFAULT_VALUE>
	${getVariableDisplayText(variableObj)}
</#function>

<#-- Facade for WYSIWYG measure answer type variable -->
<#function getAnswerDisplayText variableObj=DEFAULT_VALUE>
	${getVariableDisplayText(variableObj)}
</#function>

<#function getFreeformDisplayText obj>
	
	<#list obj.children as c>
		<#if (c.value)?has_content>
			<#return c.value>
		</#if>
	</#list>
	 
	<#return "">
</#function>

<#function wasAnswerTrue variableObj=DEFAULT_VALUE>
  <#if variableObj = DEFAULT_VALUE>
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
<#function getNumberOfTableResponseRows variableObj=DEFAULT_VALUE>
  <#if variableObj = DEFAULT_VALUE>
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

<#function getSelectOneDisplayText variableObj=DEFAULT_VALUE>
  <#if variableObj=DEFAULT_VALUE || !(variableObj.children??) || !(variableObj.children?size=1) >
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

<#function getSelectMultiDisplayText variableObj=DEFAULT_VALUE>
  <#if variableObj=DEFAULT_VALUE || !(variableObj.children??) || variableObj.children?size=0 >
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

<#function getTableMeasureDisplayText variableObj=DEFAULT_VALUE>
  <#if variableObj=DEFAULT_VALUE || !(variableObj.children??) || variableObj.children?size=0 >
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



<#function getTableMeasureValueDisplayText variableObj=DEFAULT_VALUE>
  <#if variableObj=DEFAULT_VALUE || !(variableObj.children??) || variableObj.children?size=0 >
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

<#function isSet str=DEFAULT_VALUE>
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

<#function getVariableDisplayText variableObj=DEFAULT_VALUE>
  <#if variableObj = DEFAULT_VALUE>
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
 
<#-- calculate number of years from given date -->
<#function calcAge dt>
	<#assign age = "">
	<#if dt?? >
		<#assign today = .now?date>
		<#assign bday = dt?date("MM/dd/yyyy")>
		<#assign age = ((((today?long - bday?long) / (1000 * 60 * 60 * 24))?int)/365)?int>
	</#if>
	
	<#return age>
</#function>

<#-- delimits the children of a variable using the prefix and suffix given, 
boolean indicates if the suffix should be appended at the end of the list --> 
<#function delimitChildren variableObj=DEFAULT_VALUE prefix='' suffix='' includeSuffixAtEnd=true> 
    <#assign result = ''>
    <#if (variableObj != DEFAULT_VALUE) && (variableObj.children)??> 
    <#list variableObj.children as child>
        <#assign result = result + prefix + child.value>
        <#if child_has_next || includeSuffixAtEnd>
            <#assign result = result + suffix>
        </#if>
    </#list>
    </#if>
    <#return result>
</#function>

<#-- delimits the children of a variable using the prefix and suffix given, 
boolean indicates if the suffix should be appended at the end of the list --> 
<#function delimitChildrenDisplayText variableObj=DEFAULT_VALUE prefix='' suffix='' includeSuffixAtEnd=true> 
    <#assign result = ''>
    <#if (variableObj != DEFAULT_VALUE) && (variableObj.children)??>
    <#list variableObj.children as child>
        <#assign result = result + prefix +' '+ child.displayText>
        <#if child_has_next || includeSuffixAtEnd>
            <#assign result = result + suffix>
        </#if>  
    </#list>
    </#if>
    <#return result>
</#function>


<#-- ***********************  Only  Template Editor functions under this line ************************* -->

<#-- All of these functions are used by the template editor for value extraction, condition evaluations, and transformations.
	They call ExpressionExtentionUtil.java methods. 
-->

<#-- checks if a specific answer was selected given a question -->
<#function isSelectedAnswer questionVariable=DEFAULT_VALUE answerVariable=DEFAULT_VALUE > 
	<#if (questionVariable?string) == DEFAULT_VALUE || (answerVariable?string) == DEFAULT_VALUE>
		<#return false>
	</#if>
    
    <#return util.isSelectedAnswer(questionVariable, answerVariable)>
</#function>

<#-- 
This transformation takes a table question and allows for the selection of a single child question (i.e. field) 
childQuestionId is the child question's ID that we should output values for (the field)
Other parameters are the same as the delimit function below
-->
<#function delimitTableField table=DEFAULT_VALUE childQuestionId=DEFAULT_VALUE prefix='' lastPrefix='and ' suffix=', ' includeSuffixAtEnd=false defaultValue=DEFAULT_VALUE>
	<#if (table?string) == DEFAULT_VALUE || (childQuestionId?string) == DEFAULT_VALUE >
		<#return defaultValue>
	</#if>

	<#return util.delimitTableField(table, childQuestionId, prefix, lastPrefix, suffix, includeSuffixAtEnd, defaultValue) > 
</#function> 

<#-- 
This transformation takes a table question and returns the number of entries given by the veteran
-->
<#function numberOfEntries table=DEFAULT_VALUE >
	<#if (table?string) == DEFAULT_VALUE>
        <#return DEFAULT_VALUE>
    </#if>
	<#return util.numberOfEntries(table)>
</#function>

<#-- 
This transformation takes a table question and returns an array of hashes where each entry is a row/entry, and the hash 
at that location has a key for every question which was answered for that row.  
It was done this way for simple calculation of number of rows used by numberOfEntries function.
Note: in the future we can change the way we organize the table question children array so it is not so flat. This would required
the update of any templates which were hand made (currently template IDs are: 6,12,14,20,22)
-->
<#function createTableHash table=DEFAULT_VALUE >
	<#if (table?string) == DEFAULT_VALUE>
		<#return DEFAULT_VALUE>
	</#if>
	<#return util.createTableHash(table)>
</#function>

<#--
This is a helper function which replaces raw references to a table's child fields to a lookup 
in a tableHash which gives the value of the needed entry for the given rowIndex.  Normally
we are looping through table entries and the rowIndex gives the context needed to get the 
correct assessment variable object.
-->
<#function getTableVariable tableHash=[] tableChildVar=[] rowIndex=0 >
	<#if !(tableChildVar?has_content) || !(tableChildVar.variableId??) || !(tableHash[rowIndex]??) >
	   <#return DEFAULT_VALUE>
	</#if>
	
    <#return util.getTableVariable(tableHash, tableChildVar, rowIndex)!DEFAULT_VALUE>        
</#function>

<#-- This transformation’s goal is to produce a list of text, one per question having at least one required column selected by the veteran
rowMeasureIdToOutputMap is a map from row measure ID to the text we should output if at least one column was selected by the veteran for the given question
columnAnswerIdList is a set of column answer IDs which we are testing to see if the veteran gave one of the responses
-->
<#function delimitedMatrixQuestions matrix=DEFAULT_VALUE rowMeasureIdToOutputMap=[] columnAnswerIdList=[] >
	<#if (matrix?string) == DEFAULT_VALUE || !(rowMeasureIdToOutputMap?has_content) || !(columnAnswerIdList?has_content)>
  		<#return DEFAULT_VALUE>
  	</#if>
	<#return util.delimitedMatrixQuestions(matrix, rowMeasureIdToOutputMap, columnAnswerIdList)>
</#function>  

<#-- 
yearsFromDate transformation takes a variable's value and returns the number of years from the current date
It is important to note that what we need is the number of years from the date given by the veteran and the date of the 
assessment. This function assumes that the templates for a veteran will be rendered at the end of the assessment.
-->
<#function yearsFromDate var=DEFAULT_VALUE >
    <#return util.yearsFromDate(var)>
</#function>

<#-- 
Delimits given variable.  If there is nothing to delimit the given default value will be returned.
Checks variable type to use correct delimiter helper functions.
-->
<#function delimit var=DEFAULT_VALUE prefix='' lastPrefix='and ' suffix=', ' includeSuffixAtEnd=false defaultValue=DEFAULT_VALUE > 
  	<#if (var?string) == DEFAULT_VALUE>
  		<#return defaultValue>
  	</#if>
  	<#return util.delimit(var, prefix, lastPrefix, suffix, includeSuffixAtEnd, defaultValue)>
</#function>

<#-- 
Returns a string which is generated using the given list of values and delimiter properties 
If there is only one element in the list it will be output without any prefix or suffix
-->
<#function delimitList parts=[] prefix='' lastPrefix='and ' suffix=', ' includeSuffixAtEnd=false defaultValue=DEFAULT_VALUE > 
    <#return util.delimitList(parts, prefix, lastPrefix, suffix, includeSuffixAtEnd, defaultValue)>
</#function>

<#-- 
Returns an array of the 'value' field for each child of the given variable
--> 
<#function getChildValues var=DEFAULT_VALUE >
    <#if (var?string) == DEFAULT_VALUE>
        <#return []>
    </#if>
	<#return util.getChildValues(var)>
</#function>

<#-- 
Returns an array of the 'displayText' field for each child of the given variable.
This uses getResponseText to pull out the value which checks several fields
--> 
<#function getChildDisplayText var=DEFAULT_VALUE >
    <#if (var?string) == DEFAULT_VALUE>
        <#return []>
    </#if>
	<#return util.getChildDisplayText(var)> 
</#function>

<#-- 
Supports the retrieval of a numerical value from a question (of types 1,2,3) or casting a value to be a number.
If measureTypeId is null then just casts the given value as number but if var is null, "not set" is returned.  
The var is tested to make sure it is not an object.
if measureTypeId==2 or 3:  return the calculated value  -->
<#function asNumber var=DEFAULT_VALUE measureTypeId=DEFAULT_VALUE>
	<#if (var?string) == DEFAULT_VALUE>
        <#return DEFAULT_VALUE>
    </#if>
    <#return util.asNumber(var)>    
</#function>

<#-- 
retrieves a value for the given Measure typed variable which is of the given measure type. 
For single select the answer's text should be returned
If the answer's type is "other" then the other value should be returned 
For multi select - returns a comma delimited list
-->
<#function getResponse var=DEFAULT_VALUE measureTypeId=DEFAULT_VALUE>
	<#if (var?string) == DEFAULT_VALUE>
        <#return DEFAULT_VALUE>
    </#if>
	<#return util.getResponse(var)>
</#function>

<#--
takes a custom variable and returns its value which can be a string, number, or array. 
    The variable ID is enough to know which custom variable we are getting. -->
<#function getCustomValue var=DEFAULT_VALUE>
	<#if (var?string) == DEFAULT_VALUE>
        <#return DEFAULT_VALUE>
    </#if>
    <#return util.getCustomValue(var)>
</#function>


<#-- 
returns the numerical value of the response or "not set" if it cannot be evaluated.
This function can return a number or a string. 
-->
<#function getFormulaValue var=DEFAULT_VALUE>
	<#if (var?string) == DEFAULT_VALUE || !(var.value)?? || !((var.value)?has_content)>
        <#return DEFAULT_VALUE>
    </#if>
    <#return util.getFormulaValue(var)>
</#function>

<#--
returns true if the variable is defined and has a value; false otherwise.
If type == 3 then at least one option must be set to true.
 -->
<#function wasAnswered var=DEFAULT_VALUE measureTypeId=DEFAULT_VALUE> 
	<#if (var?string) == DEFAULT_VALUE>
        <#return false>
    </#if>
	<#return util.wasAnswered(var)>
</#function>

<#--
returns the negation of wasAnswered
 -->
<#function wasntAnswered var=DEFAULT_VALUE measureTypeId=DEFAULT_VALUE> 
     <#return !(wasAnswered(var)) > 
</#function>

<#-- 
Returns true if the None typed answer was selected
-->
<#function wasAnswerNone var=DEFAULT_VALUE>
	<#if (var?string) == DEFAULT_VALUE >
    	<#return false>
    </#if>

    <#return util.wasAnswerNone(var)>
</#function>

<#--
returns the negation of wasAnswerNone
 -->
<#function wasntAnswerNone var=DEFAULT_VALUE>
	<#return !(wasAnswerNone(var))> 
</#function>

<#--
returns true if the formula can be evaluated 
-->
<#function formulaHasResult var=DEFAULT_VALUE >
	<#if (var?string) == DEFAULT_VALUE >
    	<#return false>
    </#if>
    <#return util.formulaHasResult(var)>
</#function>


<#-- returns the negation of formulaHasResult -->
<#function formulaHasNoResult var=DEFAULT_VALUE>
	<#return !(formulaHasResult(var)) >
</#function>

<#-- returns true if the custom variable has some value -->
<#function customHasResult var=DEFAULT_VALUE >
	<#if (var?string) == DEFAULT_VALUE >
    	<#return false>
    </#if>
    <#return util.customHasResult(var)>
</#function>
 
<#--
returns the negation of customHasResult
 -->
<#function customHasNoResult var=DEFAULT_VALUE >
    <#return !(customHasResult(var))>
</#function>

<#--
Returns true if the value given matrix has a value.
-->
<#function matrixHasResult matrix=DEFAULT_VALUE>
    <#if (matrix?string) == DEFAULT_VALUE >
        <#return false>
    </#if>

    <#return util.matrixHasResult(matrix)>
</#function>

<#function matrixHasNoResult var=DEFAULT_VALUE > 
    <#return !(matrixHasResult(var)) >
</#function>

<#--
 returns true if one of the given variable's responses is equal to right. 
 measureTypeId can be:
  2 - selectOne or 
  3 - selectMulti.  
  If var123 is null then false is returned.
  
  param right can be an answer object (not supported in UI right now), or an integer
-->
<#function responseIs var=DEFAULT_VALUE right=DEFAULT_VALUE measureTypeId=DEFAULT_VALUE>
    <#if ((var?string) == DEFAULT_VALUE) || ((right?string) == DEFAULT_VALUE)>
        <#return false>
    </#if>

    <#return util.responseIs(var, right)>
</#function>

<#--
returns the negation of responseIs
-->
<#function responseIsnt var=DEFAULT_VALUE right=DEFAULT_VALUE measureTypeId=DEFAULT_VALUE> 
    <#return !(responseIs(var, right))>
</#function>

<#function getFreeTextAnswer var=DEFAULT_VALUE deflt=DEFAULT_VALUE>
    <#if (var?string) == DEFAULT_VALUE >
    <#-- The object was not found -->
   	<#return deflt>
    </#if>

    <#return util.getFreeTextAnswer(var, deflt)>
</#function>

<#function getResponseText var=DEFAULT_VALUE>
    <#if (var?string) == DEFAULT_VALUE>
        <#return DEFAULT_VALUE>
    </#if>
	<#return util.getResponseText(var)>
</#function>

<#--
Allows for generic testing of values to see if the given value is defined.
Returns true if value is defined.
-->
<#function isDefined value=DEFAULT_VALUE>
	<#return util.isDefined(value)>
</#function>
