/* This file filters out content that should not be part of our distribution */

SET FOREIGN_KEY_CHECKS=0; 

/*
Remove batteries (and associated entities):     
    Aspire Intake Battery (ID 1)
    Aspire Progress Monitoring Battery (ID 2)
    Aspire Discharge Battery (ID 3)
    Women’s Assessment Battery (ID 7)
    PTSD Progress – Jacobus (ID 8)
    Lowther Nurse Case Management Battery (ID 10)
*/
DELETE FROM program_battery WHERE battery_id in (1,2,3,7,8,10);

DELETE variable_template, battery_template, template
FROM variable_template, battery_template, template
WHERE 
battery_template.template_id=template.template_id AND 
template.template_id=variable_template.template_id AND
battery_template.battery_id in (1,2,3,7,8,10);

DELETE battery, battery_survey
FROM battery, battery_survey
WHERE 
battery_survey.battery_id=battery.battery_id AND 
battery.battery_id in (1,2,3,7,8,10);

/* 
Remove formula: DAST10_test_formula (ID 74955)
(associated entities: assessment_var_children, assessment_formula)
*/
DELETE av, af, avc
FROM
assessment_variable av
left join assessment_var_children avc on av.assessment_variable_id=avc.variable_parent
left join assessment_formula af on av.assessment_variable_id=af.assessment_variable_id
where av.assessment_variable_id=74955;

/*
Remove surveys (and associated entities):
    BDI-II (in progress) (ID 59)
    DELETE ME (ID 63)
    eScreening Quality Assessment (ID 64)
    DAST 10 Test (ID 65)
    939 (ID 67)
*/
-- Remove the survey templates
DELETE 
template,
survey_template,
variable_template
FROM 
survey,
template,
survey_template,
variable_template
WHERE 
survey.survey_id=survey_template.survey_id and
survey_template.template_id=template.template_id and 
template.template_id=variable_template.template_id and
survey.survey_id in (59,63,64,65,67);

-- Remove measure answers (and associated assessment_variables)
DELETE 
measure_answer,
assessment_variable
FROM 
survey 
JOIN survey_page ON survey.survey_id=survey_page.survey_id 
JOIN survey_page_measure ON survey_page.survey_page_id=survey_page_measure.survey_page_id
JOIN measure ON survey_page_measure.measure_id=measure.measure_id
LEFT JOIN measure child_measure ON child_measure.measure_id=measure.parent_measure_id
JOIN measure_answer 
	ON (measure.measure_id=measure_answer.measure_id 
		OR (child_measure.measure_id is not null && child_measure.measure_id=measure_answer.measure_id))
JOIN assessment_variable ON measure_answer.measure_answer_id=assessment_variable.measure_answer_id
WHERE 
survey.survey_id in (59,63,64,65,67);

-- Remove measure, child measures, and measure_validation (and associated assessment_variables)
DELETE 
survey_page,
survey_page_measure,
measure,
child_measure,
assessment_variable
FROM 
survey 
JOIN survey_page ON survey.survey_id=survey_page.survey_id 
JOIN survey_page_measure ON survey_page.survey_page_id=survey_page_measure.survey_page_id
JOIN measure ON survey_page_measure.measure_id=measure.measure_id
LEFT JOIN measure child_measure ON child_measure.measure_id=measure.parent_measure_id
JOIN assessment_variable 
	ON (measure.measure_id=assessment_variable.measure_id 
		OR (child_measure.measure_id is not null && child_measure.measure_id=assessment_variable.measure_id))
LEFT JOIN measure_validation ON measure.measure_id=measure_validation.measure_id
WHERE 
survey.survey_id in (59,63,64,65,67);

-- Remove battery_surveys
DELETE battery_survey
FROM survey 
LEFT JOIN battery_survey ON survey.survey_id=battery_survey.survey_id
WHERE survey.survey_id in (59,63,64,65,67);

SET FOREIGN_KEY_CHECKS=1;

DELETE FROM survey 
WHERE survey.survey_id in (59,63,64,65,67);

/*
Notes: if this has to be done again rules using measures and/or measure_answers should be deleted. 
Below are the tables and some querys to use to check for this case.
Tables:
rule
rule_assessment_variable
rule_event
*/
-- select * from survey_page sp, survey_page_measure spm, measure m, measure_answer ma, assessment_variable av, rule_assessment_variable rav
-- where 
-- sp.survey_page_id=spm.survey_page_id and
-- spm.measure_id=m.measure_id and
-- av.measure_id=m.measure_id and 
-- av.assessment_variable_id=rav.assessment_variable_id and 
-- survey_id in (59,63,64,65,67);
-- 
-- select * from survey_page sp, survey_page_measure spm, measure m, measure_answer ma, assessment_variable av, rule_assessment_variable rav
-- where 
-- sp.survey_page_id=spm.survey_page_id and
-- spm.measure_id=m.measure_id and
-- m.measure_id=ma.measure_id and 
-- av.measure_answer_id=ma.measure_answer_id and 
-- av.assessment_variable_id=rav.assessment_variable_id and 
-- survey_id in (59,63,64,65,67);

/* Some queries used to test */
-- select * 
-- FROM 
-- survey 
-- LEFT JOIN battery_survey ON survey.survey_id=battery_survey.survey_id
-- WHERE
-- survey.survey_id in (59,63,64,65,67);
-- 
-- 
-- select * from survey, survey_template, template, variable_template, assessment_variable
-- where
-- survey.survey_id=survey_template.survey_id and
-- survey_template.template_id=template.template_id and
-- template.template_id=variable_template.template_id and 
-- variable_template.assessment_variable_id=assessment_variable.assessment_variable_id and
-- assessment_variable.assessment_variable_type_id in (4,5) and
-- survey.survey_id in (59,63,64,65,67);
-- 
-- select * 
-- FROM 
-- survey,
-- template,
-- survey_template,
-- variable_template
-- WHERE 
-- survey.survey_id=survey_template.survey_id and
-- survey_template.template_id=template.template_id and 
-- template.template_id=variable_template.template_id and
-- survey.survey_id in (59,63,64,65,67);
-- 
-- select * from   
-- survey,
-- survey_page,
-- survey_page_measure,
-- measure,
-- measure_answer
-- WHERE 
-- survey.survey_id=survey_page.survey_id and
-- survey_page.survey_page_id=survey_page_measure.survey_page_id and
-- survey_page_measure.measure_id=measure.measure_id and
-- measure.measure_id=measure_answer.measure_id and
-- survey.survey_id in (59,63,64,65,67);
-- 
-- select *
-- from 
-- assessment_variable av
-- left join assessment_var_children avc on av.assessment_variable_id=avc.variable_parent
-- left join assessment_formula af on av.assessment_variable_id=af.assessment_variable_id
-- where av.assessment_variable_id=74955;

-- select * 
-- FROM 
-- survey 
-- JOIN survey_page ON survey.survey_id=survey_page.survey_id 
-- JOIN survey_page_measure ON survey_page.survey_page_id=survey_page_measure.survey_page_id
-- JOIN measure ON survey_page_measure.measure_id=measure.measure_id
-- LEFT JOIN measure child_measure ON child_measure.measure_id=measure.parent_measure_id
-- JOIN measure_answer 
--  	ON (measure.measure_id=measure_answer.measure_id 
--  		OR (child_measure.measure_id is not null && child_measure.measure_id=measure_answer.measure_id))
--  JOIN assessment_variable ON measure_answer.measure_answer_id=assessment_variable.measure_answer_id
-- WHERE 
-- survey.survey_id in (59,63,64,65,67);