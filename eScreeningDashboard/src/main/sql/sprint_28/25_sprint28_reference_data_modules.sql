/* following insert script is added to fix t740 */
INSERT INTO measure_answer (measure_id, export_name, answer_text, answer_type, answer_value, display_order)
VALUES ('126', 'serv_exp_none', 'None', 'none', '0', '0');


/* fix for tbi_consult2 in t742 */
UPDATE assessment_variable SET formula_template='(( ([2010]?1:0)+ ([2011]?1:0) + ([2012]?1:0) + ([2013]?1:0) + ([2014]?1:0) + ([2015]?1:0))>=1?1:0)' WHERE assessment_variable_id='10714';
UPDATE assessment_var_children SET variable_child='2047' WHERE assessment_var_children_id='933';
UPDATE measure_answer SET export_name=NULL, answer_type='none' WHERE measure_answer_id='900';
