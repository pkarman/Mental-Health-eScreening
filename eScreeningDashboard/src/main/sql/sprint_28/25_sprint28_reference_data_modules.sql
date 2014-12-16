/* following insert script is added to fix t740 */
INSERT INTO measure_answer (measure_id, export_name, answer_text, answer_type, answer_value, display_order)
VALUES ('126', 'serv_exp_none', 'None', 'none', '0', '0');


/* start after this line ==> fix for t742 */
DELETE FROM assessment_var_children WHERE assessment_var_children_id='929';
DELETE FROM assessment_var_children WHERE assessment_var_children_id='930';
DELETE FROM assessment_var_children WHERE assessment_var_children_id='931';
DELETE FROM assessment_var_children WHERE assessment_var_children_id='932';
UPDATE assessment_variable SET formula_template='([$10718$] +[2047])' WHERE assessment_variable_id='10719';
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (932, 10719, 2047);
UPDATE measure_answer SET export_name=NULL, answer_type='none' WHERE measure_answer_id='900';
/* finish before this line ==> fix for t742 */
