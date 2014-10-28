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
