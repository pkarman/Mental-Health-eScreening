

-- Needed variables for ticket 642

-- update phq9 score assessment variable so it has the correct display name
UPDATE assessment_variable SET display_name='dep_score_phq9', description='PHQ-9 Score' 
WHERE assessment_variable_id=1599;



UPDATE assessment_variable SET formula_template= '((([2010]?1:0) + ([2011]?1:0) + ([2012]?1:0) + ([2013]?1:0) + ([2014]?1:0) + ([2015]?1:0))>=1?1:0)' where assessment_variable_id=10714;
UPDATE assessment_variable SET formula_template= '((([2017]?1:0) + ([2018]?1:0) + ([2019]?1:0) + ([2020]?1:0) + ([2021]?1:0))>=1?1:0)' where assessment_variable_id=10715;
UPDATE assessment_variable SET formula_template= '((([2023]?1:0) + ([2024]?1:0) + ([2025]?1:0) + ([2027]?1:0) + ([2028]?1:0) + ([2029]?1:0))>=1?1:0)' where assessment_variable_id=10716;
UPDATE assessment_variable SET formula_template= '((([2031]?1:0) + ([2032]?1:0) + ([2033]?1:0) + ([2034]?1:0) + ([2035]?1:0) + ([2036]?1:0))>=1?1:0)' where assessment_variable_id=10717;

/*
if hyp_score >= 7 && hyp2_time ==1 && hyp3_problem >=2 then hyp_criteria=1 else 0
*/

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10720, 4, 'hyp_criteria', 'hyp_criteria formula', '(([$2809$])>=7 && ([2790])==1 && ([2800])>=2)?1:0');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (940, 10720, 2809);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (941, 10720, 2790);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (942, 10720, 2800);

INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10720, 32);

update variable_template set override_display_value = 'no' where variable_template_id=2551;

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10800, 4, 'health_score_phq14', 'health_score without the cramp question', '([1020] + [1040] + [1050] + [1060] + [1070] + [1080] + [1090] + [1100] + [1110] + [1120] + [1130] + [1140] + [1150]  + [1160])');
INSERT INTO assessment_var_children(variable_parent, variable_child) VALUES (10800, 1020), (10800,1040), (10800, 1050), (10800,1060), (10800, 1070), (10800, 1080), (10800, 1090), (10800, 1100), 
	(10800, 1110), (10800, 1120), (10800,1130), (10800, 1140), (10800, 1150), (10800, 1160);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (10800, 17);
	