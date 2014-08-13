<#-- Constants -->
<#assign noParagraphData='The information required to generate this section has not been supplied.' />

<#assign TITLE_START='<TITLE_START>' />
<#assign TITLE_END='<TITLE_END>' />
<#assign SECTION_START='<SECTION_START>' />
<#assign SECTION_END='<SECTION_END>' />
<#assign LINE_BREAK='<LINE_BREAK>' />

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