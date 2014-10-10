UPDATE assessment_variable SET display_name='demo_totalheightin', description='Total height in inches formula' WHERE assessment_variable_id = 10;
UPDATE assessment_variable SET display_name='demo_BMI', description='veteran BMI formula' WHERE assessment_variable_id = 11;
UPDATE assessment_variable SET display_name='es_score_promis', description='es_score_promis formula' WHERE assessment_variable_id = 739;
UPDATE assessment_variable SET display_name='health_score_hearing', description='health_score_hearing formula' WHERE assessment_variable_id = 998;
UPDATE assessment_variable SET display_name='health_score_weight', description='health_score_weight formula' WHERE assessment_variable_id = 999;
UPDATE assessment_variable SET display_name='health_score_phq15', description='health_score_phq15 formula' WHERE assessment_variable_id = 1189;
UPDATE assessment_variable SET display_name='alc_score_audit', description='alc_score_audit formula' WHERE assessment_variable_id = 1229;
UPDATE assessment_variable SET display_name='DAST_score', description='DAST_score formula' WHERE assessment_variable_id = 1010;
UPDATE assessment_variable SET display_name='dep_score_phq9', description='dep_score_phq9 formula' WHERE assessment_variable_id = 1599;
UPDATE assessment_variable SET display_name='gad7_score', description='gad7_score formula' WHERE assessment_variable_id = 1749;
UPDATE assessment_variable SET display_name='pcl_score', description='pcl_score formula' WHERE assessment_variable_id = 1929;
UPDATE assessment_variable SET display_name='pcptsd_score', description='pcptsd_score formula' WHERE assessment_variable_id = 1989;
UPDATE assessment_variable SET display_name='sleep_score', description='sleep_score formula' WHERE assessment_variable_id = 2189;
UPDATE assessment_variable SET display_name='pain_score_interference', description='pain_score_interference formula' WHERE assessment_variable_id = 2640;
UPDATE assessment_variable SET display_name='pain_score_intensity', description='pain_score_intensity formula' WHERE assessment_variable_id = 2650;
UPDATE assessment_variable SET display_name='hyp_score', description='hyp_score formula' WHERE assessment_variable_id = 2809;
UPDATE assessment_variable SET display_name='res_score', description='res_score formula' WHERE assessment_variable_id = 2930;
UPDATE assessment_variable SET display_name='ROAS_weighted_score', description='ROAS_weighted_score formula', formula_template='([3200] + [3210]*2 + [3220]*3 + [3230]*4 + [3240]*2 + [3250]*3 + [3260]*4 + [3270]*5 + [3280]*3 + [3290]*4 + [3300]*5 + [3310]*6 + [3320]*3 + [3330]*4 + [3340]*5 + [3350]*6)' WHERE assessment_variable_id = 3389;
UPDATE assessment_variable SET display_name='whodas_understand_mean', description='whodas_understand_mean formula' WHERE assessment_variable_id = 4119;
UPDATE assessment_variable SET display_name='whodas_mobility_mean', description='whodas_mobility_mean formula' WHERE assessment_variable_id = 4239;
UPDATE assessment_variable SET display_name='whodas_selfcare_mean', description='whodas_selfcare_mean formula' WHERE assessment_variable_id = 4319;
UPDATE assessment_variable SET display_name='whodas_people_mean', description='whodas_people_mean formula' WHERE assessment_variable_id = 4419;
UPDATE assessment_variable SET display_name='whodas_household_mean', description='whodas_household_mean formula' WHERE assessment_variable_id = 4499;
UPDATE assessment_variable SET display_name='whodas_society_mean', description='whodas_society_mean formula' WHERE assessment_variable_id = 4789;

/* missing formulae added by krizvi to accomodate t646 */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10600, 4, 'whodas_understand_score', 'whodas_understand_score formula', '(([4000] + [4020] + [4040] + [4060] + [4080] + [4100]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (880, 10600, 4000);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (881, 10600, 4020);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (882, 10600, 4040);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (883, 10600, 4060);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (884, 10600, 4080);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (885, 10600, 4100);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10601, 4, 'whodas_mobility_score', 'whodas_mobility_score formula', '(([4120] + [4140] + [4160] + [4180] + [4220]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (875, 10601, 4120);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (876, 10601, 4140);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (877, 10601, 4160);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (878, 10601, 4180);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (879, 10601, 4220);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10602, 4, 'whodas_selfcare_score', 'whodas_selfcare_score formula', '(([4240] + [4260] + [4280] + [4300]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (871, 10602, 4240);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (872, 10602, 4260);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (873, 10602, 4280);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (874, 10602, 4300);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10603, 4, 'whodas_people_score', 'whodas_people_score formula', '(([4320] + [4340] + [4360] + [4380] + [4400]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (866, 10603, 4320);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (867, 10603, 4340);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (868, 10603, 4360);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (869, 10603, 4380);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (870, 10603, 4400);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10604, 4, 'whodas_household_score', 'whodas_household_score formula', '(([4420] + [4430] + [4440] + [4460]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (862, 10604, 4420);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (863, 10604, 4430);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (864, 10604, 4440);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (865, 10604, 4460);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10605, 4, 'whodas_work_mean', 'whodas_work_mean formula', '(([4480] + [4500] + [4520] + [4540])/4)');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (850, 10605, 4480);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (851, 10605, 4500);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (852, 10605, 4520);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (853, 10605, 4540);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10606, 4, 'whodas_society_score', 'whodas_society_score formula', '(([4560] + [4580] + [4600] + [4620] + [4640] + [4660] + [4680] + [4700]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (854, 10606, 4560);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (855, 10606, 4580);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (856, 10606, 4600);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (857, 10606, 4620);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (858, 10606, 4640);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (859, 10606, 4660);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (860, 10606, 4680);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (861, 10606, 4700);

/* following formulae is still missing
==>if Sum infect1a_gastro,infect2a_fever,infect3a_rash, infect4a_fragment >0	infect_score
*/
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10710, 4, 'infect_score', 'infect_score formula', '(([2500] + [2501] + [2502] + [2009]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (886, 10710, 2500);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (887, 10710, 2501);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (888, 10710, 2502);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (889, 10710, 2009);

/*
==>sum prior_dx_dep, prior_dx_ptsd, prior_dx_oth	prior_dx_score
*/
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10711, 4, 'prior_dx_score', 'prior_dx_score formula', '(([1522]?1:0 + [1523]?1:0 + [1524]?1:0))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (890, 10711, 1522);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (891, 10711, 1523);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (892, 10711, 1524);


/*
==>sum prior_tx_inpt, prior_tx_thpy, prior_tx_med, prior_tx_ect, prior_tx_oth	prior_tx_score
*/
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10712, 4, 'prior_tx_score', 'prior_tx_score formula', '(([1532]?1:0 + [1533]?1:0 + [1534]?1:0 + [1535]?1:0 + [1536]?1:0))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (893, 10712, 1532);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (894, 10712, 1533);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (895, 10712, 1534);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (896, 10712, 1535);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (897, 10712, 1536);


/*
==>prior_dx_score + Prior_tx_score + demo_info_ment+ demo_va_menthealth	Prior_hx_tx_req_appt
*/
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10713, 4, 'prior_hx_tx_req_appt', 'prior_hx_tx_req_appt formula', '(([$10711$] + [$10712$] + [202]?1:0 + [214]?1:0))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (898, 10713, 10711);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (899, 10713, 10712);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (900, 10713, 202);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (901, 10713, 214);

/*
==>Sum tbi_blast, tbi_vehicle, tbi_fragment, tbi_fall, tbi_blow, tbi_otherinj	tbi_q1_score
==>Sum tbi_immed_loss, tbi_immed_dazed, tbi_immed_memory, tbi_immed_concussion, tbi_immed_headinj	tbi_q2_score
==>Sum  tbi_worse_memory, tbi_worse_balance, tbi_worse_light, tbi_worse_irritable, tbi_worse_headache , tbi_worse_sleep	tbi_q3_score
==>Sum tbi_week_memory, tbi_week_balance, tbi_week_light, tbi_week_irritable, tbi_week_headache, tbi_week_sleep	tbi_q4_score
==>If sumQ1-Q4 =4	tbi_score1
==>If sumQ1-Q5 =5	tbi_score2
*/
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10714, 4, 'tbi_q1_score', 'tbi_q1_score formula', '(([2010]?1:0 + [2011]?1:0 + [2012]?1:0 + [2013]?1:0 + [2014]?1:0 + [2015]?1:0))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (902, 10714, 2010);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (903, 10714, 2011);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (904, 10714, 2012);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (905, 10714, 2013);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (906, 10714, 2014);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (907, 10714, 2015);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10715, 4, 'tbi_q2_score', 'tbi_q2_score formula', '(([2017]?1:0 + [2018]?1:0 + [2019]?1:0 + [2020]?1:0 + [2021]?1:0))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (908, 10715, 2017);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (909, 10715, 2018);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (910, 10715, 2019);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (911, 10715, 2020);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (912, 10715, 2021);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10716, 4, 'tbi_q3_score', 'tbi_q3_score formula', '(([2023]?1:0 + [2024]?1:0 + [2025]?1:0 + [2027]?1:0 + [2028]?1:0 + [2029]?1:0))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (913, 10716, 2023);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (914, 10716, 2024);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (915, 10716, 2025);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (916, 10716, 2027);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (917, 10716, 2028);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (918, 10716, 2029);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10717, 4, 'tbi_q4_score', 'tbi_q4_score formula', '(([2031]?1:0 + [2032]?1:0 + [2033]?1:0 + [2034]?1:0 + [2035]?1:0 + [2036]?1:0))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (919, 10717, 2031);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (920, 10717, 2032);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (921, 10717, 2033);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (922, 10717, 2034);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (923, 10717, 2035);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (924, 10717, 2036);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10718, 4, 'tbi_score1', 'tbi_score1 formula', '(([$10714$] + [$10715$] + [$10716$] + [$10717$]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (925, 10718, 10714);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (926, 10718, 10715);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (927, 10718, 10716);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (928, 10718, 10717);


