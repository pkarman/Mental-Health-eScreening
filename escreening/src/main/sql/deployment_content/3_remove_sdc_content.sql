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

DELETE bt, t, vt
FROM battery_template bt 
join template t on bt.template_id=t.template_id
left join variable_template vt on t.template_id=vt.template_id
WHERE bt.battery_id in (1,2,3,7,8,10);

DELETE b, bs
FROM battery b
left join battery_survey bs on b.battery_id=bs.battery_id
WHERE b.battery_id in (1,2,3,7,8,10);

/*
Remove surveys (and associated entities):
    BDI-II (in progress) (ID 59)
    DELETE ME (ID 63)
    eScreening Quality Assessment (ID 64)
    DAST 10 Test (ID 65)
    939 (ID 67)
*/
-- Remove the survey templates
DELETE t, st, vt
FROM survey_template st
join template t on st.template_id=t.template_id
left join variable_template vt on t.template_id=vt.template_id
WHERE st.survey_id in (59,63,64,65,67);

-- Remove measure answers (and associated assessment_variables)
DELETE 
measure_answer,
assessment_variable
FROM 
survey 
JOIN survey_page ON survey.survey_id=survey_page.survey_id 
JOIN survey_page_measure ON survey_page.survey_page_id=survey_page_measure.survey_page_id
JOIN measure ON survey_page_measure.measure_id=measure.measure_id
LEFT JOIN measure child_measure ON measure.measure_id=child_measure.parent_measure_id
JOIN measure_answer 
	ON (measure.measure_id=measure_answer.measure_id 
		OR (child_measure.measure_id is not null && child_measure.measure_id=measure_answer.measure_id))
JOIN assessment_variable ON measure_answer.measure_answer_id=assessment_variable.measure_answer_id
WHERE survey.survey_id in (59,63,64,65,67);

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
LEFT JOIN measure child_measure ON measure.measure_id=child_measure.parent_measure_id
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
Remove formulas: 
DAST10_test_formula (ID 74955), 
liz'sformula (ID 74961), 
test_visible (ID 74958)
(associated entities: assessment_var_children, assessment_formula)
*/
DELETE av from assessment_var_children avc
join assessment_variable av on avc.variable_child=av.assessment_variable_id
where avc.variable_parent = 74955;

DELETE from assessment_var_children 
where assessment_var_children.variable_parent=74955
or  assessment_var_children.variable_child=74955;

DELETE FROM assessment_formula 
where assessment_variable_id=74955;

DELETE from assessment_var_children 
where assessment_var_children.variable_parent=74961
or assessment_var_children.variable_child=74961;

DELETE FROM assessment_formula 
where assessment_variable_id=74961;

DELETE FROM assessment_formula 
where assessment_variable_id=74958;

DELETE from assessment_var_children 
where assessment_var_children.variable_parent=74958
or assessment_var_children.variable_child=74958;

DELETE FROM
assessment_variable 
where assessment_variable_id in (74955,74961,74958);

/*
Notes: if this has to be done again rules using measures and/or measure_answers should be deleted. 
Below are the tables and some querys to use to check for this case.
Tables:
rule
rule_assessment_variable
rule_event
*/
-- select count(*) from survey_page sp, survey_page_measure spm, measure m, measure_answer ma, assessment_variable av, rule_assessment_variable rav
-- where 
-- sp.survey_page_id=spm.survey_page_id and
-- spm.measure_id=m.measure_id and
-- av.measure_id=m.measure_id and 
-- av.assessment_variable_id=rav.assessment_variable_id and 
-- survey_id in (59,63,64,65,67);


/* Some queries used to test results (should all be zero) */

-- test that there are not orphan measures 
-- select count(*) from measure child
-- left join measure parent on child.parent_measure_id=parent.measure_id
-- where child.parent_measure_id is not null and parent.measure_id is null;

-- test that all measure answers have a measure
-- select count(*) from measure_answer ma
-- left join measure m on ma.measure_id=m.measure_id
-- where m.measure_id is null;

-- test variable template (should be zero)
-- select count(*) from variable_template vt
-- left join assessment_variable av on vt.assessment_variable_id=av.assessment_variable_id
-- where av.assessment_variable_id is null;

-- select count(*) from variable_template vt
-- left join template t on vt.template_id=t.template_id
-- where t.template_id is null;

-- test var children
-- select count(*) from assessment_var_children avc
-- left join assessment_variable av on avc.variable_parent=av.assessment_variable_id
-- where av.assessment_variable_id is null;

-- select count(*) from assessment_var_children avc
-- left join assessment_variable av on avc.variable_child=av.assessment_variable_id
-- where av.assessment_variable_id is null;

-- test for any delete assessment variable used in variable_templates for template that were not deleted 
-- select count(*) from variable_template vt
-- left join assessment_variable av on vt.assessment_variable_id=av.assessment_variable_id
-- where av.assessment_variable_id is null;


-- There should be not templates using deleted formulas
-- select count(*) from template 
-- where template_file like "%74955%" or 
-- template_file like "%74961%" or
-- template_file like "%74958%";

-- select count(*) from variable_template where assessment_variable_id in (74955,74961,74958);
-- template 403 should not exist
-- select count(*) from template where template_id=403;

-- there should be not references to deleted formulas
-- select count(*) from assessment_var_children avc
-- left join assessment_variable av on avc.variable_child=av.assessment_variable_id
-- where avc.variable_parent in (74955,74961,74958)
-- or  avc.variable_child in (74955,74961,74958);

-- select count(*) from variable_template vt
-- left join assessment_variable av on vt.assessment_variable_id=av.assessment_variable_id
-- where vt.assessment_variable_id in (74955,74961,74958);

-- test survey_template table
-- select count(*) from survey_template st
-- left join template t on st.template_id=t.template_id
-- where t.template_id is null;

-- test battery_template table
-- select count(*) from battery_template bt
-- left join template t on bt.template_id=t.template_id
-- where t.template_id is null;

-- test that all survey_templates were deleted
-- select count(*) from survey_template 
-- where survey_id in (59,63,64,65,67);

-- test that all battery_templates were deleted
-- select count(*) from battery_template 
-- where battery_id in (1,2,3,7,8,10);

-- select count(*) 
-- FROM survey 
-- LEFT JOIN battery_survey ON survey.survey_id=battery_survey.survey_id
-- WHERE survey.survey_id in (59,63,64,65,67);