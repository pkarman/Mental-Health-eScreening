

--Ticket 642 test template

INSERT INTO template(template_id, template_type_id, name, description, template_file) 
VALUES (311, 9, 'VistA Q/A for ', 'Veteran Summary Header',
'<#include "clinicalnotefunctions"> 
Depression Screening:${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}Depression Screen:${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}------------------${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}PHQ-9${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}A PHQ-9 screen was performed.${NBSP}
    <#if (var1599)?? && isSet(var1599.value) >
      The score was ${var1599.value} which is suggestive of${NBSP}
        <#if (var1599.value?number < 1) >
         no
        <#elseif  (var1599.value?number <= 4)>
         minimal
        <#elseif  (var1599.value?number <= 9)>
         mild
        <#elseif  (var1599.value?number <= 14)>
         moderate
        <#elseif  (var1599.value?number <= 19)>
        moderately${NBSP}severe
        <#else>
        severe
        </#if>
${NBSP}depression.
    <#else>
An insufficient number of responses were given to calculate a PHQ-9 score.
    </#if>
${LINE_BREAK}${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}1. Little interest or pleasure in doing things${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var2960)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}2. Feeling down, depressed, or hopeless${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var2970)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}3. Trouble falling or staying asleep, or sleeping too much${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var2980)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}4. Feeling tired or having little energy${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var2990)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}5. Poor appetite or overeating${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var3000)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}6. Feeling bad about yourself or that you are a failure or${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}have let yourself or your family down${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var1550)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}7. Trouble concentrating on things, such as reading the${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}newspaper or watching television${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var1560)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}8. Moving or speaking so slowly that other people could have${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}noticed. Or the opposite being so fidgety or restless that you${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}have been moving around a lot more than usual${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var1570)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}9. Thoughts that you would be better off dead or of hurting${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}yourself in some way${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var1580)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}10. If you checked off any problems, how DIFFICULT have these${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}problems made it for you to do your work, take care of things${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}at home or get along with other people?${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${getSelectOneDisplayText(var1590)}${LINE_BREAK}
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}Was the patient\'s PHQ-2 score greater than 2, or PHQ-9 score greater${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}than 9?${LINE_BREAK}

      <#if (var1599)?? && isSet(var1599.value) && (var1599.value?number > 9) >
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}Yes.${LINE_BREAK} 
${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}NURSING/NON-PROVIDER: Follow-up:${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}The following action was taken: Patient\'s provider,${LINE_BREAK}
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}(Assigned Clincian), was notified for immediate intervention.${LINE_BREAK}
      <#else>
${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}${NBSP}No.${LINE_BREAK}
      </#if>
');

INSERT INTO survey_template (survey_id, template_id) VALUES (30, 311);