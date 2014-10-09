/* ********************************************** */
/* Assesment Variables  */
/* ********************************************** */

/* Introduction Section */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description) VALUES (1, 3, 'eScreening packet version', 'ESCREENING_PACKET_VERSION');
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description) VALUES (2, 3, 'Assigned Clinician', 'Clinician assigned to the eScreening');
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description) VALUES (4, 3, "Today's date", 'CUSTOM_TODAYS_DATE');
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description) VALUES (5, 3, 'Assessment duration HH:MM:SS', 'CUSTOM_ASSESSMENT_DURATION');
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description) VALUES (3, 3, 'Signing Clinician', 'Clinician signing');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1, 1, 1);

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2, 2, 1);

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3, 3, 1);




/* Demographics */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (40, 1, 'Veteran race', 'Veteran race question', 22);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (41, 2, 'White/Caucasian', 'White/Caucasian Answer', 230);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (42, 2, 'Black/African American', 'Black/African American Answer', 231);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (43, 2, 'American Indian or Alaskan Native', 'American Indian or Alaskan Native Answer', 232);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (44, 2, 'Asian (Filipino, Japanese, Korean, Chinese, Vietnamese, etc.)', 'Asian (Filipino, Japanese, Korean, Chinese, Vietnamese, etc.) Answer', 233);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (45, 2, 'Native Hawaiian or Pacific Islander', 'Native Hawaiian or Pacific Islander', 234);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (46, 2, 'Other, please specify', 'Other, please specify Answer', 235);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (47, 2, 'Decline To Answer', 'Decline To Answer Answer', 236);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (20, 1, 'What is your gender', 'Veteran gender question', 17);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (21, 2, 'Male', 'Male Answer', 160);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (22, 2, 'Female', 'Female Answer', 161);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (30, 1, 'Veteran ethnicity question', 'Veteran ethnicity question', 21);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (31, 2, 'Hispanic/Latino', 'Hispanic/Latino answer', 220);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (32, 2, 'Not Hispanic', 'Not Hispanic answer', 221);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (33, 2, 'Decline To Answer', 'Decline To Answer', 222);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (10, 4, 'demo_totalheightin', 'demo_totalheightin formula', '( [140] * 12 ) + [141]');
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (11, 4, 'demo_BMI', 'demo_BMI formula', '( [142] / ( [$10$] * [$10$] ) ) * 703');
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (140, 1, 'Veteran height feet', 'Veteran height feet question', 71);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (141, 1, 'Veteran height inches', 'Veteran height inches question', 72);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (142, 1, 'Veteran weight', 'Veteran weight question', 20);

	

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (12, 4, 'Calculate age', 'Calculate Veteran age formula', '#calculateAge(''[143]'')');
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (143, 1, 'Veteran age', 'Veteran age question', 18);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2950, 2, 'birth date', 'birth date answer', 170);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (20, 20, 4);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (21, 21, 4, 'male');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (22, 22, 4, 'female');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (30, 30, 4);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (31, 31, 4, 'Hispanic/Latino');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (32, 32, 4, 'non-Hispanic/Latino');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (33, 33, 4, 'Decline To Answer');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (40, 40, 4);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (41, 41, 4, 'White/Caucasian');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (42, 42, 4, 'Black/African American');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (43, 43, 4, 'American Indian or Alaskan Native');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (44, 44, 4, 'Asian');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (45, 45, 4, 'Native Hawaiian or Pacific Islander');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (46, 46, 4, 'Other, please specify');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (47, 47, 4, 'decline to answer');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10, 10, 4);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (11, 11, 4);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (140, 140, 4);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (141, 141, 4);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (142, 142, 4);





/* Education, Employment, Income */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (50, 1, 'Veteran education', 'Veteran education question', 23);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (51, 2, 'Some High School', 'Some High School Answer', 240);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (52, 2, 'GED', 'GED Answer', 241);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (53, 2, 'High School Diploma', 'High School Diploma Answer', 242);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (54, 2, 'Some College', 'Some College Answer', 243);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (55, 2, 'Associates degree', 'Associates Degree Answer', 244);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (56, 2, '4-year College Degree', '4-year College Degree Answer', 245);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (57, 2, 'Masters Degree', 'Masters Degree Answer', 246);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (58, 2, 'Doctoral Degree', 'Doctoral Degree Answer', 247);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (60, 1, 'Veteran employment', 'Veteran employment question', 24);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (61, 2, 'Full Time', 'Full Time Answer', 250);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (62, 2, 'Part Time', 'Part Time Answer', 251);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (63, 2, 'Seasonal', 'Seasonal Answer', 252);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (64, 2, 'Day Labor', 'Day Labor Answer', 253);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (65, 2, 'Unemployed', 'Unemployed Answer', 254);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (70, 1, 'Veteran employment', 'Veteran employment question', 26);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (71, 2, 'Employment description', 'Employment Description Answer', 270);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (80, 1, 'Veteran income question', 'Veteran income question', 27);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (81, 2, 'No Income', 'No Income Answer', 280);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (82, 2, 'Work', 'Work Answer', 281);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (83, 2, 'Unemployment', 'Unemployment Answer', 282);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (84, 2, 'Disability', 'Disability Answer', 283);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (85, 2, 'GI Bill', 'GI Bill Answer', 284);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (86, 2, 'Retirement/Pension', 'Retirement/Pension Answer', 285);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (87, 2, 'Other', 'Other Answer', 286);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (100, 1, 'Veteran marital status question', 'Veteran marital status question', 28);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (101, 2, 'Single', 'Single Answer', 290);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (102, 2, 'Married', 'Married Answer', 291);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (103, 2, 'Separated', 'Separated Answer', 292);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (104, 2, 'Divorced', 'Divorced Answer', 293);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (105, 2, 'Cohabitating', 'Cohabitating Answer', 294);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (106, 2, 'Civil Union', 'Civil Union Answer', 295);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (107, 2, 'Widowed/Widower', 'Widowed/Widower Answer', 296);	



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (50, 50, 5);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (51, 51, 5, 'some high school');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (52, 52, 5, 'a GED');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (53, 53, 5, 'a high school diploma');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (54, 54, 5, 'some college');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (55, 55, 5, 'an associates degree');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (56, 56, 5, 'a 4 year college degree');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (57, 57, 5, 'a masters degree');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (58, 58, 5, 'a doctoral degree');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (60, 60, 5);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (61, 61, 5, 'employed full time');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (62, 62, 5, 'employed part time');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (63, 63, 5, 'employed seasonally');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (64, 64, 5, 'working day labor');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (65, 65, 5, 'unemployed');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (70, 70, 5);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (71, 71, 5, 'occupation');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (80, 80, 5);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (81, 81, 5, 'no income');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (82, 82, 5, 'work');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (83, 83, 5, 'unemployment');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (84, 84, 5, 'disability');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (85, 85, 5, 'the GI Bill');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (86, 86, 5, 'retirement or pension');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (87, 87, 5, 'other');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (100, 100, 5);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (101, 101, 5, 'single');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (102, 102, 5, 'married');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (103, 103, 5, 'seperated');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (104, 104, 5, 'divorced');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (105, 105, 5, 'cohabitating with a partner');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (106, 106, 5, 'in a civil union');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (107, 107, 5, 'either widowed or a widower');


/* Social */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (90, 1, 'Veteran roommate question', 'Veteran roommate question', 32);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (92, 2, 'Parents/Relatives', 'Parents/Relatives Answer', 321);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (93, 2, 'Friends/Roommates', 'Friends/Roommates Answer', 322);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (94, 2, 'Spouse or Partner', 'Spouse or Partner Answer', 323);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (95, 2, 'Children', 'Children Answer', 324);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (130, 1, 'Enter child ages', 'Enter child ages question', 29);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (131, 2, 'No Children', 'Does not have children Answer', 300);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (132, 1, 'Veteran how many children', 'Veteran how many children question', 30);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (133, 2, 'younger than 1', 'younger than 1 answer', 301);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (134, 2, '1-2', '1-2 answer', 302);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (135, 2, '3-5', '3-5 answer', 303);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (136, 2, '6-17', '6-17 answer', 304);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (137, 2, '18 and older', '18 and older answer', 305);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (150, 1, 'Are you in a relationship in which you have been physically hurt or felt threatened', 'Are you in a relationship in which you have been physically hurt or felt threatened question', 35);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (151, 2, 'no', 'no Answer', 340);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (152, 2, 'yes', 'yes Answer', 341);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (160, 1, 'Veteran roommate question', 'Veteran roommate question', 34);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (161, 2, 'No One', 'No One Answer', 330);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (162, 2, 'Parents', 'Parents Answer', 331);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (163, 2, 'Friends', 'Friends Answer', 332);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (164, 2, 'Partner/Spouse', 'Partner/Spouse Answer', 333);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (165, 2, 'Therapist', 'Therapist Answer', 334);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (166, 2, 'Spiritual/Religious Advisor', 'Spiritual/Religious Advisor Answer', 335);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (167, 2, 'Children', 'Children Answer', 336);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (169, 2, 'Other, please specify', 'Other, please specify Answer', 338);


INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (1, 10, 140);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (2, 10, 141);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (3, 11, 10);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (4, 11, 142);



INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (5, 12, 143);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (90, 90, 6);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (92, 92, 6, 'parents or relatives');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (93, 93, 6, 'friends or roommates');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (94, 94, 6, 'a spouse or partner');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (95, 95, 6, 'children');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (130, 130, 6);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (131, 131, 6, 'The Veteran reported not having any children.');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (132, 132, 6);

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (150, 150, 6);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (151, 151, 6, 'denied');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (152, 152, 6, 'indicated');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (160, 160, 6);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (161, 161, 6, 'no one');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (162, 162, 6, 'parents');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (163, 163, 6, 'friends');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (164, 164, 6, 'partner/spouse');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (165, 165, 6, 'therapist');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (166, 166, 6, 'spiritual/religious advisor');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (167, 167, 6, 'children');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (169, 169, 6, 'other');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (12, 12, 4);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (143, 143, 4);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2950, 2950, 4, 'birth date ');



/* Presenting Concerns Section */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (200, 1, 'Presenting concerns', 'Why have you come to the VA today question', 9);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (201, 2, 'Enrollment', 'Enrollment answer', 80);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (202, 2, 'Mental Health Concerns', 'Mental Health Concerns answer', 81);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (203, 2, 'Physical Health Concerns', 'Physical Health Concerns answer', 82);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (204, 2, 'Establish Primary Care', 'Establish Primary Care answer', 83);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (205, 2, 'Other', 'Other answer', 84);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (210, 1, 'Healthcare resources information', 'Would you like information about healhcare resources question', 10);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (211, 2, 'None', 'None answer', 90);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (212, 2, 'Prosthetic Equipment', 'Prosthetic Equipment answer', 91);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (213, 2, 'Sexual Health', 'Sexual Health answer', 92);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (214, 2, 'Mental Health Appointment Equipment', 'Mental Health Appointment answer', 93);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (215, 2, 'Substance Use', 'Substance Use answer', 94);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (216, 2, 'Visual Impairment Services Team', 'Visual Impairment Services Team answer', 95);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (220, 1, 'Employment resources information', 'Would you like information about employment resources question', 12);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (221, 2, 'None', 'None answer', 110);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (222, 2, 'VA Vocational Rehabilitation', 'VA Vocational Rehabilitation answer', 111);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (223, 2, 'Unemployment Benefits', 'Unemployment Benefits answer', 112);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (224, 2, 'VA Work Study', 'VA Work Study answer', 113);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (230, 1, 'Social resources information', 'Would you like information about social resources question', 14);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (231, 2, 'None', 'None answer', 130);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (232, 2, 'Adjustment to Civilian Life', 'Adjustment to Civilian Life answer', 131);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (233, 2, 'Relationship Concern', 'Relationship Concern answer', 132);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (234, 2, 'Support Groups', 'Support Groups answer', 133);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (240, 1, 'Do you want/need information or assistance with housing', 'Do you want/need information or assistance with housing question', 16);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (241, 2, 'None', 'None answer', 150);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (242, 2, 'Homeless', 'Homeless answer', 151);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (243, 2, 'Foreclosure', 'Foreclosure answer', 152);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (244, 2, 'other', 'other answer', 153);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (250, 1, 'VA benifit resources information', 'Would you like information about VA benifits resources question', 11);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (251, 2, 'None', 'None answer', 100);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (252, 2, 'VA Compensation', 'VA Compensation answer', 101);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (253, 2, 'GI Bill', 'GI Bill answer', 102);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (254, 2, 'VA Home Loan', 'VA Home Loan answer', 103);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (260, 1, 'Financial resources information', 'Would you like information about Finanical resources question', 13);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (261, 2, 'None', 'None answer', 120);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (262, 2, 'Information About VA Or Community Resources', 'Information About VA Or Community Resources answer', 121);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (270, 1, 'Legal resources information', 'Would you like information about Legal resources question', 15);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (271, 2, 'None', 'None answer', 140);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (272, 2, 'Parole', 'Parole answer', 141);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (273, 2, 'Probation', 'Probation answer', 142);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (274, 2, 'Warrants', 'Warrants answer', 143);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (275, 2, 'Bankruptcy', 'Bankruptcy answer', 144);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (200, 200, 3);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (201, 201, 3, 'enrollment');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (202, 202, 3, 'mental health');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (203, 203, 3, 'physical health');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (204, 204, 3, 'establishing a PCP');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (205, 205, 3, 'other');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (210, 210, 3);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (211, 211, 3, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (212, 212, 3, 'prosthetic equipment');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (213, 213, 3, 'sexual health');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (214, 214, 3, 'mental health');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (215, 215, 3, 'substance use');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (216, 216, 3, 'visual impairment services team');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (220, 220, 3);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (221, 221, 3, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (222, 222, 3, 'VA vocational rehabilitation');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (223, 223, 3, 'unemployment benefits');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (224, 224, 3, 'VA work study');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (230, 230, 3);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (231, 231, 3, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (232, 232, 3, 'adjustment to civilian life');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (233, 233, 3, 'relationship concerns');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (234, 234, 3, 'support groups');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (240, 240, 3);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (241, 241, 3, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (242, 242, 3, 'homeless');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (243, 243, 3, 'foreclosure');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (244, 244, 3, 'other');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (250, 250, 3);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (251, 251, 3, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (252, 252, 3, 'VA compensation');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (253, 253, 3, 'GI Bill');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (254, 254, 3, 'VA home loan');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (260, 260, 3);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (261, 261, 3, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (262, 262, 3, 'Financial Information about VA or community resources');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (270, 270, 3);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (271, 271, 3, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (272, 272, 3, 'parole');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (273, 273, 3, 'probation');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (274, 274, 3, 'warrants');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (275, 275, 3, 'bankruptcy');

/* Legal Section */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (300, 1, 'Do you have legal concerns', 'Do you have legal concerns question', 36);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (301, 2, 'Civil Issues', 'Civil Issues answer', 351);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (302, 2, 'Child Support', 'Child Support answer', 352);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (303, 2, 'Taxes', 'Taxes answer', 353);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (304, 2, 'Bankruptcy', 'Bankruptcy answer', 354);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (305, 2, 'Outstanding Tickets', 'Outstanding Tickets answer', 355);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (306, 2, 'Arrest Warrants', 'Arrest Warrants answer', 356);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (307, 2, 'Restraining Orders', 'Restraining Orders answer', 357);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (308, 2, 'DUIs', 'DUIs answer', 358);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (309, 2, 'Probation', 'Probation answer', 359);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (310, 2, 'Parole', 'Parole answer', 360);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (311, 2, 'JAG', 'JAG answer', 361);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (312, 2, 'Child Protective Services', 'Child Protective Services answer', 362);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (300, 300, 9);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (301, 301, 9, 'civil issues');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (302, 302, 9, 'child support');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (303, 303, 9, 'taxes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (304, 304, 9, 'bankruptcy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (305, 305, 9, 'outstanding tickets');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (306, 306, 9, 'arrest warrants');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (307, 307, 9, 'restraining orders');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (308, 308, 9, 'DUIs');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (309, 309, 9, 'probation');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (310, 310, 9, 'parole');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (311, 311, 9, 'JAG');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (312, 312, 9, 'child protective services');





/* Spirituality Section */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (400, 1, 'Is spirituality important to you', 'Is spirituality important to you question', 41);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (401, 2, 'No', 'No answer', 410);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (402, 2, 'Yes', 'Yes answer', 411);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (420, 1, 'Are you presently connected to a faith community', 'Are you presently connected to a faith community question', 109);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (421, 2, 'is not', 'No answer', 1090);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (422, 2, 'is', 'Yes answer', 1091);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (423, 2, 'is not, but I would like to be part of one', 'No, but I would like to be part of one answer', 1092);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (430, 1, 'Request to see a chaplain', 'Request to see a chaplain question', 40);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (431, 2, 'No', 'No answer', 400);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (432, 2, 'Yes', 'Yes answer', 401);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (440, 1, 'How did combat affect religious views', 'How did combat affect religious views question', 44);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (441, 2, 'No', 'No answer', 440);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (442, 2, 'Yes and other', 'Yes and other answer', 441);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (443, 2, 'I do not know', 'I do not know answer', 442);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (400, 400, 11);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (401, 401, 11, 'is not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (402, 402, 11, 'is');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (420, 420, 11);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (421, 421, 11, 'is not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (422, 422, 11, 'is');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (423, 423, 11, 'is not, but I would like to be part of one');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (430, 430, 11);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (431, 431, 11, 'declines');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (432, 432, 11, 'requests');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (440, 440, 11);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (441, 441, 11, 'did not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (442, 442, 11, 'did');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (443, 443, 11, 'does not know');


/* Tobacco Cessation Section */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (600, 1, 'Do you use tobacco currently', 'Do you use tobacco currently question', 341);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (601, 2, 'Never. I have never used tobacco on a regular basis', 'Never. I have never used tobacco on a regular basis answer', 3410);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (602, 2, 'No. I used tobacco in the past, but have quit', 'No. I used tobacco in the past, but have quit answer', 3411);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (603, 2, 'Yes. I currently use tobacco on a regular basis', 'Yes. I currently use tobacco on a regular basis answer', 3412);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (610, 1, 'When did you quit using tobacco on a regular basis', 'When did you quit using tobacco on a regular basis question', 342);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (611, 2, 'I quit using tobacco more than 7 years ago', 'Never. I have never used tobacco on a regular basis answer', 3420);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (612, 2, 'I quit using tobacco more than 1 year, but less than 7 years ago', 'I quit using tobacco more than 1 year, but less than 7 years ago answer', 3421);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (613, 2, 'I quit using tobacco within the last 12 months', 'I quit using tobacco within the last 12 months answer', 3422);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (620, 1, 'If yes, Which types of tobacco do you use', 'If yes, Which types of tobacco do you use question', 347);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (621, 2, 'Cigarettes', 'Cigarettes answer', 3470);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (622, 2, 'Cigars/pipes', 'Cigars/pipes answer', 3471);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (623, 2, 'Smokeless tobacco (snuff/chewing)', 'Smokeless tobacco (snuff/chewing) answer', 3472);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (600, 600, 26);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (601, 601, 26, 'denied using tobacco ');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (602, 602, 26, 'quit using tobacco');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (603, 603, 26, 'endorsed using');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (610, 610, 26);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (611, 611, 26, 'more than 7 years ago');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (612, 612, 26, 'between 1 and 7 years ago');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (613, 613, 26, 'within the last 12 months');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (620, 620, 26);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (621, 621, 26, 'cigarettes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (622, 622, 26, 'cigar/pipes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (623, 623, 26, 'smokeless tobacco');


/* ID */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (630, 1, 'First Name', 'First Name question', 1);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (631, 2, 'Freeform Text', 'First Name answer', 10);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (632, 1, 'Middle Name', 'Middle Name question', 2);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (633, 2, 'Freeform Text', 'Middle Name answer', 1);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (634, 1, 'Last Name', 'Last Name question', 4);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (635, 2, 'Freeform Text', 'Last Name answer', 30);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (636, 1, 'SSN', 'SSN question', 5);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (637, 2, 'Freeform Text', 'SSN answer', 40);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (638, 1, 'Email', 'Email question', 6);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (639, 2, 'Freeform Text', 'Email answer', 50);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (640, 1, 'Phone Number', 'Phone Number question', 7);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (641, 2, 'Freeform Text', 'Phone Number answer', 60);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (642, 1, 'Best time to call', 'Best time to call question', 8);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (643, 2, 'anytime', 'Best time to call answer', 70);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (644, 2, 'morning', 'Best time to call answer', 71);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (645, 2, 'afternoon', 'Best time to call answer', 72);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (646, 2, 'evening', 'Best time to call answer', 73);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (647, 2, 'other', 'Best time to call answer', 74);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (630, 630, 40);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (631, 631, 40, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (632, 630, 40);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (633, 633, 40, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (634, 630, 40);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (635, 632, 40, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (636, 630, 40);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (637, 633, 40, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (638, 638, 40);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (639, 631, 40, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (640, 640, 40);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (641, 641, 40, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (642, 642, 40);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (643, 643, 40, 'anytime');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (644, 644, 40, 'morning');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (645, 645, 40, 'afternoon');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (646, 646, 40, 'evening');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (647, 647, 40, 'other');


/* OEF/OIF Clinical Reminder */
	-- OEF
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (660, 1, 'Did you serve in Operation Enduring Freedom (OEF) or in Operation Iraqi Freedom (OIF)', 'Did you serve in Operation Enduring Freedom (OEF) or in Operation Iraqi Freedom (OIF) question', 112);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (661, 2, 'No', 'No answer', 1120);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (662, 2, 'Yes - OEF', 'Yes, Service in Operation Enduring Freedom (OEF)', 1121);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (659, 2, 'Yes - OIF', 'Yes, Service in Operation Iraqi Freedom (OIF)', 1122);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (663, 1, 'If yes, where was your most recent OEF service', 'If yes, where was your most recent OEF service question', 113);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (664, 2, 'Afghanistan', 'Afghanistan answer', 1130);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (665, 2, 'Tajikistan', 'Tajikistan answer', 1131);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (666, 2, 'Georgia', 'Georgia answer', 1132);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (667, 2, 'Uzbekistan', 'Uzbekistan answer', 1133);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (668, 2, 'Kyrgystan', 'Kyrgystan answer', 1134);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (669, 2, 'The Philippines', 'The Philippines answer', 1135);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (670, 2, 'Pakistan', 'Pakistan answer', 1136);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (671, 2, 'Other', 'Other answer', 1137);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (660, 660, 13);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (661, 661, 13, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (662, 662, 13, 'Operation Enduring Freedom (OEF)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (659, 659, 13, 'Operation Iraqi Freedom (OIF)');



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (663, 663, 13);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (664, 664, 13, 'Afghanistan');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (665, 665, 13, 'Tajikistan');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (666, 666, 13, 'Georgia');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (667, 667, 13, 'Uzbekistan');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (668, 668, 13, 'Kyrgystan');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (669, 669, 13, 'The Philippines');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (670, 670, 13, 'Pakistan');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (671, 671, 13, 'other');


--OIF

-- The associated question (114) was removed and the option for saying the vet was in OIF was moved to question 112 (see assessment variable 663)
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (680, 1, 'Did you serve in OIF', 'Did you serve in OIF question', 114);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (681, 2, 'No', 'No answer', 1140);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (682, 2, 'Yes', 'Yes answer', 1141);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (683, 1, 'If yes, where was your most recent OIF service', 'If yes, where was your most recent OIF service question', 115);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (684, 2, 'Iraq', 'Iraq answer', 1150);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (685, 2, 'Kuwait', 'Kuwait answer', 1151);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (686, 2, 'Saudi Arabia', 'Saudi Arabia answer', 1152);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (687, 2, 'Turkey', 'Turkey answer', 1153);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (688, 2, 'Other', 'Other answer', 1154);



-- -- The associated question (114) was removed and the option for saying the vet was in OIF was moved to question 112 (see assessment variable 663)
--INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (680, 680, 13);
--INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (681, 681, 13, 'no');
--INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (682, 682, 13, 'yes');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (683, 683, 13);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (684, 684, 13, 'Iraq');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (685, 685, 13, 'Kuwait');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (686, 686, 13, 'Saudi Arabia');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (687, 687, 13, 'Turkey');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (688, 688, 13, 'other');


/* PROMIS Emotional Support */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (700, 1, 'I have someone who will listen to me when I need to talk', 'I have someone who will listen to me when I need to talk question', 101);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (701, 2, 'never', 'never answer', 1010);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (702, 2, 'rarely', 'rarely answer', 1011);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (703, 2, 'sometimes', 'sometimes answer', 1012);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (704, 2, 'usually', 'usually answer', 1013);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (705, 2, 'missing', 'missing', 1014);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (710, 1, 'I have someone to confide in or talk to about myself or my problems', 'I have someone to confide in or talk to about myself or my problems question', 102);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (711, 2, 'never', 'never answer', 1020);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (712, 2, 'rarely', 'rarely answer', 1021);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (713, 2, 'sometimes', 'sometimes answer', 1022);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (714, 2, 'usually', 'usually answer', 1023);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (715, 2, 'missing', 'missing', 1024);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (720, 1, 'I have someone who makes me feel appreciated', 'I have someone who makes me feel appreciated question', 103);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (721, 2, 'never', 'never answer', 1030);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (722, 2, 'rarely', 'rarely answer', 1031);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (723, 2, 'sometimes', 'sometimes answer', 1032);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (724, 2, 'usually', 'usually answer', 1033);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (725, 2, 'missing', 'missing', 1034);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (730, 1, 'I have someone to talk with when I have a bad day', 'I have someone to talk with when I have a bad day question', 104);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (731, 2, 'never', 'never answer', 1040);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (732, 2, 'rarely', 'rarely answer', 1041);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (733, 2, 'sometimes', 'sometimes answer', 1042);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (734, 2, 'usually', 'usually answer', 1043);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (735, 2, 'missing', 'missing', 1044);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (739, 4, 'es_score_promis', 'es_score_promis formula', '([700] + [710] + [720] + [730])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (6, 739, 700);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (7, 739, 710);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (8, 739, 720);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (9, 739, 730);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (700, 700, 7);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (701, 701, 7, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (702, 702, 7, 'rarely');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (703, 703, 7, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (704, 704, 7, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (705, 705, 7, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (710, 710, 7);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (711, 711, 7, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (712, 712, 7, 'rarely');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (713, 713, 7, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (714, 714, 7, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (715, 715, 7, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (720, 720, 7);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (721, 721, 7, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (722, 722, 7, 'rarely');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (723, 723, 7, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (724, 724, 7, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (725, 725, 7, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (730, 730, 7);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (731, 731, 7, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (732, 732, 7, 'rarely');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (733, 733, 7, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (734, 734, 7, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (735, 735, 7, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (739, 739, 7);



/* Housing - Homelessness */
/** variables for questions 61, 62, and 63 which shows follow-up questions **/
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) 
	VALUES (2000, 1, 'HomelessCR_stable_house', 'Stable housing', 61);

--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (760, 1, 'In the past 2 months, have you been living in stable housing', 'In the past 2 months, have you been living in stable housing question', 61);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (761, 2, 'No, not living in stable housing', 'No, not living in stable housing answer', 610);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (762, 2, 'Yes, living in stable housing', 'Yes, living in stable housing answer', 611);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (763, 2, ' I decline to be screened', ' I decline to be screened answer', 612);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) 
	VALUES (2001, 1, 'HomelessCR_stable_worry', 'Worried about next 2 months', 62);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (770, 1, 'Are you worried or concerned that in the next 2 months you may not have stable house', 'Are you worried or concerned that in the next 2 months you may not have stable house question', 62);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (771, 2, 'No, I am not worried about housing in the near future', 'No, I am not worried about housing in the near future answer', 620);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (772, 2, 'Yes, worried about stable housing in the near future', 'Yes, worried about stable housing in the near future answer', 621);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) 
	VALUES (2002, 1, 'HomelessCR_live_2mos', 'Lived in the past 2 months', 63);	
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (780, 1, 'Where have you lived for the MOST of the past 2 months', 'Where have you lived for the MOST of the past 2 months question', 63);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (781, 2, 'Apartment/House/Room-no government subsidy', 'Apartment/House/Room-no government subsidy', 630);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (782, 2, 'Apartment/House/Room- with government subsidy', 'Apartment/House/Room- with government subsidy answer', 631);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (783, 2, 'With a friend or family member', 'With a friend or family member answer', 632);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (784, 2, 'Motel/Hotel', 'Motel/Hotel answer', 633);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (785, 2, 'Short term institution like a hospital, rehabilitation center, or drug treatment facility', 'Short term institution like a hospital, rehabilitation center, or drug treatment facility answer', 634);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (786, 2, 'Homeless shelter', 'Homeless shelter answer', 635);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (787, 2, 'Anywhere outside, eg. street, vehicle, abandoned building', 'Anywhere outside, eg. street, vehicle, abandoned building answer', 636);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (788, 2, 'Other', 'Other answer', 637);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2008, 1, 'Homeless Question 1', 'Homeless Question 1', 51);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (790, 1, 'Would you like to be referred to talk more about your housing situation', 'Would you like to be referred to talk more about your housing situation', 51);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (791, 2, 'No', 'No answer', 510);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (792, 2, 'Yes', 'Yes answer', 511);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2007, 1, 'best way to reach', 'best way to reach', 52);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2000, 2000, 8);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (761, 761, 8, 'has not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (762, 762, 8, 'has');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (763, 763, 8, 'declined');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2001, 2001, 8);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (771, 771, 8, 'is not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (772, 772, 8, 'is');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2002, 2002, 8);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (781, 781, 8, 'in an apartment/House/Room-no government subsidy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (782, 782, 8, 'in an apartment/House/Room- with government subsidy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (783, 783, 8, 'with a friend or family member');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (784, 784, 8, 'in a motel/hotel');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (785, 785, 8, 'in a short term institution like a hospital');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (786, 786, 8, 'in a homeless shelter');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (787, 787, 8, 'anywhere outside, eg. street, vehicle, abandoned building');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (788, 788, 8, 'other');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2008, 2008, 8);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (791, 791, 8, 'would not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (792, 792, 8, 'would');




/* Advance Directive */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (800, 1, 'What language do you prefer to get your health information', 'What language do you prefer to get your health information question', 37);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (801, 2, 'English', 'English answer', 370);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (802, 2, 'Spanish', 'Spanish answer', 371);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (803, 2, 'Tagalog', 'Tagalog answer', 372);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (804, 2, 'Chinese', 'Chinese answer', 373);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (805, 2, 'German', 'German answer', 374);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (806, 2, 'Japanese', 'Japanese answer', 375);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (807, 2, 'Korean', 'Korean answer', 376);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (808, 2, 'Russian', 'Russian answer', 377);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (809, 2, 'Vietnamese', 'Vietnamese answer', 378);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (810, 2, 'Other', 'Other answer', 379);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (820, 1, 'Do you have an Advance Directive or Durable Power of Attorney for Healthcare', 'Do you have an Advance Directive or Durable Power of Attorney for Healthcare question', 38);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (821, 2, 'No', 'No answer', 380);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (822, 2, 'Yes', 'Yes answer', 381);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (826, 1, 'If no, would like information about this document', 'If no, would like information about this document question', 39);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (827, 2, 'No', 'No answer', 390);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (828, 2, 'Yes', 'Yes answer', 391);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (800, 800, 10);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (801, 801, 10, 'English');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (802, 802, 10, 'Spanish');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (803, 803, 10, 'Tagalog');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (804, 804, 10, 'Chinese');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (805, 805, 10, 'German');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (806, 806, 10, 'Japanese');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (807, 807, 10, 'Korean');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (808, 808, 10, 'Russian');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (809, 809, 10, 'Vietnamese');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (810, 810, 10, 'other');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (820, 820, 10);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (821, 821, 10, 'not having');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (822, 822, 10, 'having');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (826, 826, 10);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (827, 827, 10, 'would not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (828, 828, 10, 'would');





/* Other Health Symptoms */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (930, 1, 'Hearing loss', 'Hearing loss question', 181);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (931, 2, 'not bothered at all', 'not bothered at all answer', 1810);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (932, 2, 'bothered a little', 'bothered a little answer', 1811);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (933, 2, 'bothered a lot', 'bothered a lot answer', 1812);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (940, 1, 'Tinnitus (ringing in the ears)', 'Tinnitus (ringing in the ears) question', 182);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (941, 2, 'not bothered at all', 'not bothered at all answer', 1820);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (942, 2, 'bothered a little', 'bothered a little answer', 1821);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (943, 2, 'bothered a lot', 'bothered a lot answer', 1822);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (950, 1, 'Problem with vision (e.g., double or blurred vision, light sensitivity, difficulty seeing moving objects)', 'Problem with vision (e.g., double or blurred vision, light sensitivity, difficulty seeing moving objects) question', 183);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (951, 2, 'not bothered at all', 'not bothered at all answer', 1830);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (952, 2, 'bothered a little', 'bothered a little answer', 1831);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (953, 2, 'bothered a lot', 'bothered a lot answer', 1832);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (960, 1, 'weight gain', 'weight gain question', 184);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (961, 2, 'not bothered at all', 'not bothered at all answer', 1840);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (962, 2, 'bothered a little', 'bothered a little answer', 1841);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (963, 2, 'bothered a lot', 'bothered a lot answer', 1842);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (970, 1, 'weight loss', 'weight loss question', 185);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (971, 2, 'not bothered at all', 'not bothered at all answer', 1850);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (972, 2, 'bothered a little', 'bothered a little answer', 1851);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (973, 2, 'bothered a lot', 'bothered a lot answer', 1852);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (980, 1, 'forgetfulness', 'forgetfulness question', 186);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (981, 2, 'not bothered at all', 'not bothered at all answer', 1860);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (982, 2, 'bothered a little', 'bothered a little answer', 1861);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (983, 2, 'bothered a lot', 'bothered a lot answer', 1862);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (998, 4, 'health_score_hearing', 'health_score_hearing Formula', '([930] + [940])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (650, 998, 930);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (651, 998, 940);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (999, 4, 'health_score_weight', 'health_score_weight Formula', '([960] + [970])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (670, 999, 960);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (671, 999, 970);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (930, 930, 18);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (931, 931, 18, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (932, 932, 18, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (933, 933, 18, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (940, 940, 18);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (941, 941, 18, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (942, 942, 18, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (943, 943, 18, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (950, 950, 18);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (951, 951, 18, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (952, 952, 18, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (953, 953, 18, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (960, 960, 18);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (961, 961, 18, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (962, 962, 18, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (963, 963, 18, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (970, 970, 18);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (971, 971, 18, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (972, 972, 18, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (973, 973, 18, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (980, 980, 18);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (981, 981, 18, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (982, 982, 18, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (983, 983, 18, 'bothered a lot');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (998, 998, 18);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (999, 999, 18);




/* PHQ-15 section - Health Status */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1150, 1, 'stomach pain', 'stomach pain question', 161);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1151, 2, 'not bothered at all', 'not bothered at all answer', 1610);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1152, 2, 'bothered a little', 'bothered a little answer', 1611);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1153, 2, 'bothered a lot', 'bothered a lot answer', 1612);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1160, 1, 'back pain', 'back pain question', 162);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1161, 2, 'not bothered at all', 'not bothered at all answer', 1620);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1162, 2, 'bothered a little', 'bothered a little answer', 1621);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1163, 2, 'bothered a lot', 'bothered a lot answer', 1622);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1020, 1, 'pain in arms, legs or joints (knees, hips, etc.)', 'pain in arms, legs or joints (knees, hips, etc.) question', 163);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1021, 2, 'not bothered at all', 'not bothered at all answer', 1630);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1022, 2, 'bothered a little', 'bothered a little answer', 1631);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1023, 2, 'bothered a lot', 'bothered a lot answer', 1632);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1030, 1, 'menstrual cramps or other problems with your periods', 'menstrual cramps or other problems with your periods question', 164);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1031, 2, 'not bothered at all', 'not bothered at all answer', 1640);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1032, 2, 'bothered a little', 'bothered a little answer', 1641);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1033, 2, 'bothered a lot', 'bothered a lot answer', 1642);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1040, 1, 'headaches', 'headaches question', 165);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1041, 2, 'not bothered at all', 'not bothered at all answer', 1650);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1042, 2, 'bothered a little', 'bothered a little answer', 1651);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1043, 2, 'bothered a lot', 'bothered a lot answer', 1652);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1050, 1, 'chest pain', 'chest pain question', 166);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1051, 2, 'not bothered at all', 'not bothered at all answer', 1660);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1052, 2, 'bothered a little', 'bothered a little answer', 1661);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1053, 2, 'bothered a lot', 'bothered a lot answer', 1662);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1060, 1, 'dizziness', 'dizziness question', 167);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1061, 2, 'not bothered at all', 'not bothered at all answer', 1670);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1062, 2, 'bothered a little', 'bothered a little answer', 1671);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1063, 2, 'bothered a lot', 'bothered a lot answer', 1672);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1070, 1, 'fainting spells', 'fainting spells question', 168);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1071, 2, 'not bothered at all', 'not bothered at all answer', 1680);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1072, 2, 'bothered a little', 'bothered a little answer', 1681);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1073, 2, 'bothered a lot', 'bothered a lot answer', 1682);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1080, 1, 'feeling your heart pound or race', 'feeling your heart pound or race question', 169);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1081, 2, 'not bothered at all', 'not bothered at all answer', 1690);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1082, 2, 'bothered a little', 'bothered a little answer', 1691);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1083, 2, 'bothered a lot', 'bothered a lot answer', 1692);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1090, 1, 'shortness of breath', 'shortness of breath question', 170);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1091, 2, 'not bothered at all', 'not bothered at all answer', 1700);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1092, 2, 'bothered a little', 'bothered a little answer', 1701);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1093, 2, 'bothered a lot', 'bothered a lot answer', 1702);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1100, 1, 'pain or problems during sexual intercourse', 'pain or problems during sexual intercourse question', 171);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1101, 2, 'not bothered at all', 'not bothered at all answer', 1710);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1102, 2, 'bothered a little', 'bothered a little answer', 1711);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1103, 2, 'bothered a lot', 'bothered a lot answer', 1712);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1110, 1, 'constipation, loose bowels or diarrhea', 'constipation, loose bowels or diarrhea question', 172);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1111, 2, 'not bothered at all', 'not bothered at all answer', 1720);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1112, 2, 'bothered a little', 'bothered a little answer', 1721);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1113, 2, 'bothered a lot', 'bothered a lot answer', 1722);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1120, 1, 'nausea, gas or indigestion', 'nausea, gas or indigestion question', 173);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1121, 2, 'not bothered at all', 'not bothered at all answer', 1730);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1122, 2, 'bothered a little', 'bothered a little answer', 1731);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1123, 2, 'bothered a lot', 'bothered a lot answer', 1732);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1130, 1, 'feeling tired or having low energy', 'feeling tired or having low energy question', 174);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1131, 2, 'not bothered at all', 'not bothered at all answer', 1740);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1132, 2, 'bothered a little', 'bothered a little answer', 1741);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1133, 2, 'bothered a lot', 'bothered a lot answer', 1742);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1140, 1, 'trouble sleeping', 'trouble sleeping question', 175);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1141, 2, 'not bothered at all', 'not bothered at all answer', 1750);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1142, 2, 'bothered a little', 'bothered a little answer', 1751);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1143, 2, 'bothered a lot', 'bothered a lot answer', 1752);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (1189, 4, 'health_score_phq15', 'health_score_phq15 Formula', '([1020] + [1030] + [1040] + [1050] + [1060] + [1070] + [1080] + [1090] + [1100] + [1110] + [1120] + [1130] + [1140] + [1150]  + [1160])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (780, 1189, 1020);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (781, 1189, 1030);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (782, 1189, 1040);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (783, 1189, 1050);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (784, 1189, 1060);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (785, 1189, 1070);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (786, 1189, 1080);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (787, 1189, 1090);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (788, 1189, 1100);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (789, 1189, 1110);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (790, 1189, 1120);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (791, 1189, 1130);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (792, 1189, 1140);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (793, 1189, 1150);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (794, 1189, 1160);





INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1150, 1150, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1151, 1151, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1152, 1152, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1153, 1153, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1160, 1160, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1161, 1161, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1162, 1162, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1163, 1163, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1020, 1020, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1021, 1021, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1022, 1022, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1023, 1023, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1030, 1030, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1031, 1031, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1032, 1032, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1033, 1033, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1040, 1040, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1041, 1041, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1042, 1042, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1043, 1043, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1050, 1050, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1051, 1051, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1052, 1052, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1053, 1053, 17, 'bothered a lot');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1060, 1060, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1061, 1061, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1062, 1062, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1063, 1063, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1070, 1070, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1071, 1071, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1072, 1072, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1073, 1073, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1080, 1080, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1081, 1081, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1082, 1082, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1083, 1083, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1090, 1090, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1091, 1091, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1092, 1092, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1093, 1093, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1100, 1100, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1101, 1101, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1102, 1102, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1103, 1103, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1110, 1110, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1111, 1111, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1112, 1112, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1113, 1113, 17, 'bothered a lot');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1120, 1120, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1121, 1121, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1122, 1122, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1123, 1123, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1130, 1130, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1131, 1131, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1132, 1132, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1133, 1133, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1140, 1140, 17);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1141, 1141, 17, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1142, 1142, 17, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1143, 1143, 17, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1189, 1189, 17);


/* AUDIT-C */


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1200, 1, 'How often did you have a drink containing alcohol in the past 52 weeks', 'How often did you have a drink containing alcohol in the past 52 weeks question', 370);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1201, 2, 'never', 'never answer', 3700);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1202, 2, 'monthly or less', 'monthly or less answer', 3701);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1203, 2, '2-4 times per month', '2-4 times per month answer', 3702);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1204, 2, '2-3 times per week', '2-3 times per week answer', 3703);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1205, 2, '4 or more times per week', '4 or more times per week answer', 3704);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1210, 1, 'How many drinks containing alcohol did you have on a typical day when you were drinking in the past 52 weeks', 'How many drinks containing alcohol did you have on a typical day when you were drinking in the past 52 weeks question', 371);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1211, 2, 'none', 'none answer', 3710);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1212, 2, '1-2 drinks', '1-2 drinks answer', 3711);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1213, 2, '3-4 drinks', '3-4 drinks answer', 3712);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1214, 2, '5-6 drinks', '5-6 drinks answer', 3713);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1215, 2, '7-9 drinks', '7-9 drinks answer', 3714);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1216, 2, '10+', '10+ answer', 3715);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1220, 1, 'How often did you have six or more drinks on one occasion in the past 52 weeks', 'How often did you have six or more drinks on one occasion in the past 52 weeks question', 372);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1221, 2, 'never', 'not bothered at all answer', 3720);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1222, 2, 'less than monthly', 'less than monthly answer', 3721);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1223, 2, 'monthly', 'monthly answer', 3722);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1224, 2, 'weekly', 'weekly answer', 3723);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1225, 2, 'daily', 'daily answer', 3724);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (1229, 4, 'alc_score_audit', 'alc_score_audit Formula', '([1200] + [1210] + [1220])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (450, 1229, 1200);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (451, 1229, 1210);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (452, 1229, 1220);




INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1200, 1200, 27);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1201, 1201, 27, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1202, 1202, 27, 'monthly or less');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1203, 1203, 27, '2-4 times per month');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1204, 1204, 27, '2-3 times per week');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1205, 1205, 27, '4 or more times per week');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1210, 1210, 27);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1211, 1211, 27, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1212, 1212, 27, '1-2 drinks');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1213, 1213, 27, '3-4 drinks');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1214, 1214, 27, '5-6 drinks');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1215, 1215, 27, '7-9 drinks');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1216, 1216, 27, '10+');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1220, 1220, 27);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1221, 1221, 27, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1222, 1222, 27, 'less than monthly');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1223, 1223, 27, 'monthly');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1224, 1224, 27, 'weekly');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1225, 1225, 27, 'daily');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1229, 1229, 27);


/* DAST-10 */


INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1000, 1, 'DAST1_other', 'DAST1_other', 382);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1240, 1, 'Have you used drugs other than those required for medical reasons', 'Have you used drugs other than those required for medical reasons question', 382);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1241, 2, 'no', 'no answer', 3820);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1242, 2, 'yes', 'yes answer', 3821);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1001, 1, 'DAST2_abuse', 'DAST2_abuse', 383);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1250, 1, 'Do you abuse more than one drug at a time', 'Do you abuse more than one drug at a time question', 383);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1251, 2, 'no', 'no answer', 3830);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1252, 2, 'yes', 'yes answer', 3831);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1002, 1, 'DAST3_ablestop', 'DAST3_ablestop', 384);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1260, 1, 'Are you always able to stop using drugs when you want to', 'Are you always able to stop using drugs when you want to question', 384);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1261, 2, 'no', 'no answer', 3840);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1262, 2, 'yes', 'yes answer', 3841);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1003, 1, 'DAST4_blackout', 'DAST4_blackout', 385);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1270, 1, 'Have you had "blackouts" or "flashbacks" as a result of drug use', 'Have you had "blackouts" or "flashbacks" as a result of drug use question', 385);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1271, 2, 'no', 'no answer', 3850);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1272, 2, 'yes', 'yes answer', 3851);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1004, 1, 'DAST5_guilty', 'DAST5_guilty', 386);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1280, 1, 'Do you ever feel bad or guilty about your drug use', 'Do you ever feel bad or guilty about your drug use question', 386);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1281, 2, 'no', 'no answer', 3860);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1282, 2, 'yes', 'yes answer', 3861);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1005, 1, 'DAST6_complain', 'DAST6_complain', 387);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1290, 1, 'Does your spouse (or parents) ever complain about your involvement with drugs', 'Does your spouse (or parents) ever complain about your involvement with drugs question', 387);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1291, 2, 'no', 'no answer', 3870);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1292, 2, 'yes', 'yes answer', 3871);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1006, 1, 'DAST7_neglect', 'DAST7_neglect', 388);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1300, 1, 'Have you neglected your family because of your use of drugs', 'Have you neglected your family because of your use of drugs question', 388);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1301, 2, 'no', 'no answer', 3880);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1302, 2, 'yes', 'yes answer', 3881);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1007, 1, 'DAST8_illegal', 'DAST8_illegal', 389);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1310, 1, 'Have you engaged in illegal activities in order to obtain drugs', 'Have you engaged in illegal activities in order to obtain drugs question', 389);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1311, 2, 'no', 'no answer', 3890);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1312, 2, 'yes', 'yes answer', 3891);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1008, 1, 'DAST9_withdraw', 'DAST9_withdraw', 390);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1320, 1, 'Have you ever experienced withdrawal symptoms (felt sick) when you stopped taking drugs', 'Have you ever experienced withdrawal symptoms (felt sick) when you stopped taking drugs question', 390);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1321, 2, 'no', 'no answer', 3900);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1322, 2, 'yes', 'yes answer', 3901);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1009, 1, 'DAST10_medical', 'DAST10_medical', 391);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1330, 1, 'Have you had medical problems as a result of your drug use (e.g., memory loss, hepatitis, convulsions, bleeding, etc.)', 'Have you had medical problems as a result of your drug use (e.g., memory loss, hepatitis, convulsions, bleeding, etc.) question', 391);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1331, 2, 'no', 'no answer', 3910);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1332, 2, 'yes', 'yes answer', 3911);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (1010, 4, 'DAST_score', 'DAST_score Formula', '(([1000] + [1001] + [1003] + [1004] + [1005] + [1006] + [1007] + [1008] + [1009]) + (1 - [1002]))');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (730, 1010, 1000);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (731, 1010, 1001);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (732, 1010, 1002);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (733, 1010, 1003);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (734, 1010, 1004);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (735, 1010, 1005);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (736, 1010, 1006);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (737, 1010, 1007);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (738, 1010, 1008);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (739, 1010, 1009);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1240, 1000, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1241, 1241, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1242, 1242, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1250, 1001, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1251, 1251, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1252, 1252, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1260, 1002, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1261, 1261, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1262, 1262, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1270, 1003, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1271, 1271, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1272, 1272, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1280, 1004, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1281, 1281, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1282, 1282, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1290, 1005, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1291, 1291, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1292, 1292, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1300, 1006, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1301, 1301, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1302, 1302, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1310, 1007, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1311, 1311, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1312, 1312, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1320, 1008, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1321, 1321, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1322, 1322, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1330, 1009, 28);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1331, 1331, 28, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1332, 1332, 28, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1010, 1010, 28);


/* A/V Hallucinations */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1350, 1, 'health18_voices', 'health18_voices question', 402);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1351, 2, 'not bothered at all', 'not bothered at all answer', 4020);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1352, 2, 'bothered a little', 'bothered a little answer', 4021);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1353, 2, 'bothered a lot', 'bothered a lot answer', 4022);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1360, 1, 'health19_seeing', 'health19_seeing question', 403);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1361, 2, 'not bothered at all', 'not bothered at all answer', 4030);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1362, 2, 'bothered a little', 'bothered a little answer', 4031);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1363, 2, 'bothered a lot', 'bothered a lot answer', 4032);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1350, 1350, 29);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1351, 1351, 29, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1352, 1352, 29, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1353, 1353, 29, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1360, 1360, 29);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1361, 1361, 29, 'not bothered at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1362, 1362, 29, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1363, 1363, 29, 'bothered a lot');



/* Service Injuries */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1380, 1, 'blast injury', 'blast injury question', 141);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1381, 2, 'None', 'None answer', 1410);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1382, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1411);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1383, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1412);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1390, 1, 'injury to spine or back', 'injury to spine or back question', 142);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1391, 2, 'None', 'None answer', 1420);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1392, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1421);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1393, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1422);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1400, 1, 'Burn (second, 3rd degree)', 'Burn (second, 3rd degree) question', 143);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1401, 2, 'None', 'None answer', 1430);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1402, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1431);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1403, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1432);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1410, 1, 'Nerve damage', 'Nerve damage question', 144);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1411, 2, 'None', 'None answer', 1440);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1412, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1441);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1413, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1442);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1420, 1, 'Loss or damage to vision', 'Loss or damage to vision question', 145);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1421, 2, 'None', 'None answer', 1450);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1422, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1451);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1423, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1452);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1430, 1, 'Loss or damage to hearing', 'Loss or damage to hearing question', 146);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1431, 2, 'None', 'None answer', 1460);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1432, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1461);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1433, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1462);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1440, 1, 'Amputation', 'Amputation question', 147);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1441, 2, 'None', 'None answer', 1470);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1442, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1471);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1443, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1472);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1450, 1, 'Broken/fractured bone(s)', 'Broken/fractured bone(s) question', 148);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1451, 2, 'None', 'None answer', 1480);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1452, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1481);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1453, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1482);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1460, 1, 'Joint or muscle damage', 'Joint or muscle damage question', 149);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1461, 2, 'None', 'None answer', 1490);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1462, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1491);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1463, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1492);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1470, 1, 'Internal or abdominal injuries', 'Internal or abdominal injuries question', 150);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1471, 2, 'None', 'None answer', 1500);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1472, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1501);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1473, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1502);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1480, 1, 'Other, please specify', 'Other, please specify question', 151);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1481, 2, 'Other, please specify', 'Other, please specify answer', 1510);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1482, 2, 'None', 'None answer', 1511);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1483, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1512);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1484, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1513);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1490, 1, 'Other, please specify', 'Other, please specify question', 152);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1491, 2, 'Other, please specify', 'Other, please specify answer', 1520);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1492, 2, 'None', 'None answer', 1521);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1493, 2, 'Yes, During Combat Deployment', 'Yes, During Combat Deployment answer', 1522);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1494, 2, 'Yes, During Other Service Period or Training', 'Yes, During Other Service Period or Training answer', 1523);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1500, 1, 'Other, please specify', 'Other, please specify question', 140);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1510, 1, 'Did you receive service-connected compensation for an injury', 'Did you receive service-connected compensation for an injury question', 155);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1511, 2, 'No', 'No answer', 1550);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1512, 2, 'Yes', 'Yes answer', 1551);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1513, 2, 'No, but intend to file/in progress', 'No, but intend to file/in progress answer', 1552);





INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1380, 1380, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1381, 1381, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1382, 1382, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1383, 1383, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1390, 1390, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1391, 1391, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1392, 1392, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1393, 1393, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1400, 1400, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1401, 1401, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1402, 1402, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1403, 1403, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1410, 1410, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1411, 1411, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1412, 1412, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1413, 1413, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1420, 1420, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1421, 1421, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1422, 1422, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1423, 1423, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1430, 1430, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1431, 1431, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1432, 1432, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1433, 1433, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1440, 1440, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1441, 1441, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1442, 1442, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1443, 1443, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1450, 1450, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1451, 1451, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1452, 1452, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1453, 1453, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1460, 1460, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1461, 1461, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1462, 1462, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1463, 1463, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1470, 1470, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1471, 1471, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1472, 1472, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1473, 1473, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1480, 1480, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1481, 1481, 16, 'other');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1482, 1482, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1483, 1483, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1484, 1484, 16, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1490, 1490, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1491, 1491, 16, 'other');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1492, 1492, 16, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1493, 1493, 16, 'combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1494, 1494, 16, 'non-combat');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1500, 1500, 16);

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1510, 1510, 16);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1511, 1511, 16, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1512, 1512, 16, 'yes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1513, 1513, 16, 'no, but intend to file/in progress');


/* Prior Mental Health Treatment */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1520, 1, 'Over the past 18 months has a Mental Health Provider diagnosed you with any of the following', 'Over the past 18 months has a Mental Health Provider diagnosed you with any of the following question', 240);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1521, 2, 'None', 'None answer', 2400);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1522, 2, 'Depression', 'Depression answer', 2401);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1523, 2, 'Post Traumatic Stress Disorder (PTSD)', 'Post Traumatic Stress Disorder (PTSD) answer', 2402);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1524, 2, 'Other, please specify', 'Other, please specify answer', 2404);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1530, 1, 'In the past 18 months have you had any of the following treatments for your mental health diagnosis', 'In the past 18 months have you had any of the following treatments for your mental health diagnosis question', 241);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1531, 2, 'None', 'None answer', 2410);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1532, 2, 'inpatient psychiatric stay', 'Inpatient psychiatric stay answer', 2411);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1533, 2, 'psychotherapy', 'Psychotherapy answer', 2412);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1534, 2, 'psychiatric medication', 'Psychiatric medication answer', 2413);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1535, 2, 'electro convulsive therapy', 'Electro convulsive therapy answer', 2414);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1536, 2, 'other, please specify', 'Other, please specify answer', 2415);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1520, 1520, 23);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1521, 1521, 23, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1522, 1522, 23, 'depression');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1523, 1523, 23, 'Post Traumatic Stress Disorder (PTSD)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1524, 1524, 23, 'other');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1530, 1530, 23);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1531, 1531, 23, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1532, 1532, 23, 'inpatient psychiatric stay');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1533, 1533, 23, 'psychotherapy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1534, 1534, 23, 'psychiatric medication');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1535, 1535, 23, 'electro convulsive therapy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1536, 1536, 23, 'other');

/* PHQ-9 (Depression) */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2960, 1, 'Little interest or pleasure in doing things', 'Little interest or pleasure in doing things question', 452);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2961, 2, 'not at all', 'not at all answer', 4520);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2962, 2, 'several days', 'several days answer', 4521);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2963, 2, 'more than half the days', 'more than half the days answer', 4522);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2964, 2, 'nearly every day', ' nearly every day answer', 4523);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2970, 1, ' Feeling down, depressed, or hopeless', 'Feeling down, depressed, or hopeless question', 453);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2971, 2, 'not at all', 'not at all answer', 4530);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2972, 2, 'several days', 'several days answer', 4531);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2973, 2, 'more than half the days', 'more than half the days answer', 4532);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2974, 2, 'nearly every day', ' nearly every day answer', 4533);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2980, 1, 'Trouble falling asleep, staying asleep, or sleeping too much', 'Trouble falling asleep, staying asleep, or sleeping too much question', 454);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2981, 2, 'not at all', 'not at all answer', 4540);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2982, 2, 'several days', 'several days answer', 4541);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2983, 2, 'more than half the days', 'more than half the days answer', 4542);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2984, 2, 'nearly every day', ' nearly every day answer', 4543);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2990, 1, 'Feeling tired or having little energy', 'Feeling tired or having little energy question', 455);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2991, 2, 'not at all', 'not at all answer', 4550);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2992, 2, 'several days', 'several days answer', 4551);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2993, 2, 'more than half the days', 'more than half the days answer', 4552);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2994, 2, 'nearly every day', ' nearly every day answer', 4553);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3000, 1, 'Poor appetite or overeating', 'Poor appetite or overeating question', 456);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3001, 2, 'not at all', 'not at all answer', 4560);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3002, 2, 'several days', 'several days answer', 4561);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3003, 2, 'more than half the days', 'more than half the days answer', 4562);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3004, 2, 'nearly every day', ' nearly every day answer', 4563);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1550, 1, 'Feeling bad about yourself, feeling that you are a failure, or feeling that you have let yourself or your family down', 'Feeling bad about yourself, feeling that you are a failure, or feeling that you have let yourself or your family down question', 457);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1551, 2, 'not at all', 'not at all answer', 4570);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1552, 2, 'several days', 'several days answer', 4571);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1553, 2, 'more than half the days', 'more than half the days answer', 4572);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1554, 2, 'nearly every day', ' nearly every day answer', 4573);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1560, 1, ' Trouble concentrating on things such as reading the newspaper or watching television', 'Trouble concentrating on things such as reading the newspaper or watching television question', 458);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1561, 2, 'not at all', 'not at all answer', 4580);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1562, 2, 'several days', 'several days answer', 4581);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1563, 2, 'more than half the days', 'more than half the days answer', 4582);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1564, 2, 'nearly every day', ' nearly every day answer', 4583);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1570, 1, 'Moving or speaking so slowly that other people could have noticed. Or being so fidgety or restless that you have been moving around a lot more than usual', 'Moving or speaking so slowly that other people could have noticed. Or being so fidgety or restless that you have been moving around a lot more than usual question', 459);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1571, 2, 'not at all', 'not at all answer', 4590);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1572, 2, 'several days', 'several days answer', 4591);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1573, 2, 'more than half the days', 'more than half the days answer', 4592);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1574, 2, 'nearly every day', ' nearly every day answer', 4593);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1580, 1, 'Thinking that you would be better off dead or that you want to hurt yourself in some way', 'Thinking that you would be better off dead or that you want to hurt yourself in some way question', 460);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1581, 2, 'not at all', 'not at all answer', 4600);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1582, 2, 'several days', 'several days answer', 4601);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1583, 2, 'more than half the days', 'more than half the days answer', 4602);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1584, 2, 'nearly every day', ' nearly every day answer', 4603);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1590, 1, 'If you checked off any problem on this questionnaire so far, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people', 'If you checked off any problem on this questionnaire so far, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people question', 461);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1591, 2, 'not at all', 'not at all answer', 4610);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1592, 2, 'several days', 'several days answer', 4611);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1593, 2, 'more than half the days', 'more than half the days answer', 4612);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1594, 2, 'nearly every day', ' nearly every day answer', 4613);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (1599, 4, 'dep_score_phq9', 'dep_score_phq9', '([2960] + [2970] + [2980] + [2990] + [3000] + [1550] + [1560] + [1570] + [1580])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (50, 1599, 2960);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (51, 1599, 2970);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (52, 1599, 2980);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (53, 1599, 2990);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (54, 1599, 3000);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (55, 1599, 1550);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (56, 1599, 1560);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (57, 1599, 1570);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (58, 1599, 1580);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2960, 2960, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2961, 2961, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2962, 2962, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2963, 2963, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2964, 2964, 31, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2970, 2970, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2971, 2971, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2972, 2972, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2973, 2973, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2974, 2974, 31, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2980, 2980, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2981, 2981, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2982, 2982, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2983, 2983, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2984, 2984, 31, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2990, 2990, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2991, 2991, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2992, 2992, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2993, 2993, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2994, 2994, 31, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3000, 3000, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3001, 3001, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3002, 3002, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3003, 3003, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3004, 3004, 31, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1550, 1550, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1551, 1551, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1552, 1552, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1553, 1553, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1554, 1554, 31, 'nearly every day');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1560, 1560, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1561, 1561, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1562, 1562, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1563, 1563, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1564, 1564, 31, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1570, 1570, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1571, 1571, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1572, 1572, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1573, 1573, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1574, 1574, 31, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1580, 1580, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1581, 1581, 31, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1582, 1582, 31, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1583, 1583, 31, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1584, 1584, 31, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1590, 1590, 31);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1591, 1591, 31, 'not difficult');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1592, 1592, 31, 'somewhat difficult');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1593, 1593, 31, 'very Difficult');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1594, 1594, 31, 'extremely difficult');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1599, 1599, 31);	


/* MST */

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2003, 1, 'MST Question 1', 'MST Question 1', 500);
--INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1610, 1, 'mst in military', 'mst in military question', 500);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2004, 2, '\'No\' to both questions answer', '\'No\' to both questions answer', 5000);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2005, 2, '\'Yes\' to one or both questions answer', '\'Yes\' to one or both questions answer', 5001);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2006, 2, 'decline to answer question regarding MST', 'decline to answer question regarding MST answer', 5002);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1620, 1, 'Have you experienced any of these types of unwanted sexual experience outside of the military in childhood', 'Have you experienced any of these types of unwanted sexual experience outside of the military in childhood question', 503);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1621, 2, 'no', 'no answer', 5030);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1622, 2, 'yes', 'yes answer', 5031);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1623, 2, 'decline to answer', 'decline answer', 5032);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1630, 1, 'Have you experienced any of these types of unwanted sexual experience outside of the military in adulthood', 'Have you experienced any of these types of unwanted sexual experience outside of the military in adulthood question', 504);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1631, 2, 'no', 'no answer', 5040);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1632, 2, 'yes', 'yes answer', 5041);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1633, 2, 'decline to answer', 'decline answer', 5042);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1640, 1, 'Would you like a referral to see a VA clinician to discuss issues related to sexual trauma', 'Would you like a referral to see a VA clinician to discuss issues related to sexual trauma question', 505);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1641, 2, 'no', 'no answer', 5050);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1642, 2, 'yes', 'yes answer', 5051);




INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2003, 2003, 33);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2004, 2004, 33, 'answered no to both questions in');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2005, 2005, 33, 'answered yes to one or both questions in');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2006, 2006, 33, 'declined to answer');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1620, 1620, 33);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1621, 1621, 33, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1622, 1622, 33, 'yes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1623, 1623, 33, 'declined');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1630, 1630, 33);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1631, 1631, 33, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1632, 1632, 33, 'yes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1633, 1633, 33, 'declined');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1640, 1640, 33);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1641, 1641, 33, 'would not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1642, 1642, 33, 'would');




/* GAD 7 Anxiety */


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1660, 1, 'Feeling nervous, anxious or on edge', 'Feeling nervous, anxious or on edge question', 512);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1661, 2, 'not at all', 'not at all answer', 5120);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1662, 2, 'several days', 'several days answer', 5121);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1663, 2, 'more than half the days', 'more than half the days answer', 5122);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1664, 2, 'nearly every day', ' nearly every day answer', 5123);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1670, 1, 'Not being able to stop or control worrying', 'Not being able to stop or control worrying question', 513);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1671, 2, 'not at all', 'not at all answer', 5130);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1672, 2, 'several days', 'several days answer', 5131);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1673, 2, 'more than half the days', 'more than half the days answer', 5132);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1674, 2, 'nearly every day', ' nearly every day answer', 5133);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1680, 1, 'Worrying too much about different things', 'Worrying too much about different things question', 514);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1681, 2, 'not at all', 'not at all answer', 5140);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1682, 2, 'several days', 'several days answer', 5141);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1683, 2, 'more than half the days', 'more than half the days answer', 5142);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1684, 2, 'nearly every day', ' nearly every day answer', 5143);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1690, 1, 'Trouble relaxing', 'Trouble relaxing question', 515);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1691, 2, 'not at all', 'not at all answer', 5150);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1692, 2, 'several days', 'several days answer', 5151);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1693, 2, 'more than half the days', 'more than half the days answer', 5152);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1694, 2, 'nearly every day', ' nearly every day answer', 5153);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1700, 1, 'Being so restless that it is hard to sit still', 'Being so restless that it is hard to sit still question', 516);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1701, 2, 'not at all', 'not at all answer', 5160);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1702, 2, 'several days', 'several days answer', 5161);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1703, 2, 'more than half the days', 'more than half the days answer', 5162);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1704, 2, 'nearly every day', ' nearly every day answer', 5163);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1710, 1, 'Becoming easily annoyed or irritable', 'Becoming easily annoyed or irritable question', 517);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1711, 2, 'not at all', 'not at all answer', 5170);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1712, 2, 'several days', 'several days answer', 5171);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1713, 2, 'more than half the days', 'more than half the days answer', 5172);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1714, 2, 'nearly every day', ' nearly every day answer', 5173);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1720, 1, 'Feeling afraid as if something awful might happen', 'Feeling afraid as if something awful might happen question', 518);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1721, 2, 'not at all', 'not at all answer', 5180);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1722, 2, 'several days', 'several days answer', 5181);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1723, 2, 'more than half the days', 'more than half the days answer', 5182);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1724, 2, 'nearly every day', ' nearly every day answer', 5183);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1730, 1, 'how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people', 'how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people question', 520);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1731, 2, 'not difficult at all', 'not difficult at all answer', 5190);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1732, 2, 'somewhat difficult', 'somewhat difficult answer', 5191);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1733, 2, 'very difficult', 'very difficult answer', 5192);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1734, 2, 'extremely difficult', 'extremely difficult answer', 5193);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (1749, 4, 'gad7_score', 'gad7_score', '([1660] + [1670] + [1680] + [1690] + [1700] + [1710] + [1720] + [1730])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (500, 1749, 1660);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (501, 1749, 1670);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (502, 1749, 1680);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (503, 1749, 1690);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (504, 1749, 1700);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (505, 1749, 1710);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (506, 1749, 1720);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (507, 1749, 1730);





INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1660, 1660, 34);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1661, 1661, 34, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1662, 1662, 34, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1663, 1663, 34, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1664, 1664, 34, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1670, 1670, 34);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1671, 1671, 34, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1672, 1672, 34, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1673, 1673, 34, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1674, 1674, 34, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1680, 1680, 34);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1681, 1681, 34, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1682, 1682, 34, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1683, 1683, 34, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1684, 1684, 34, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1690, 1690, 34);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1691, 1691, 34, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1692, 1692, 34, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1693, 1693, 34, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1694, 1694, 34, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1700, 1700, 34);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1701, 1701, 34, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1702, 1702, 34, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1703, 1703, 34, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1704, 1704, 34, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1710, 1710, 34);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1711, 1711, 34, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1712, 1712, 34, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1713, 1713, 34, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1714, 1714, 34, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1720, 1720, 34);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1721, 1721, 34, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1722, 1722, 34, 'several days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1723, 1723, 34, 'more than half the days');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1724, 1724, 34, 'nearly every day');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1730, 1730, 34);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1731, 1731, 34, 'not difficult at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1732, 1732, 34, 'somewhat difficult');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1733, 1733, 34, 'very difficult');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1734, 1734, 34, 'extremely difficult');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1749, 1749, 34);	


/* PCLC */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1750, 1, 'disturbing memories', 'disturbing memories question', 522);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1751, 2, 'not at all', 'not at all answer', 5220);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1752, 2, 'a little bit', 'a little bit answer', 5221);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1753, 2, 'moderately', 'moderately answer', 5222);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1754, 2, 'quite a bit', 'quite a bit answer', 5223);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1755, 2, 'extremely', 'extremely answer', 5224);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1760, 1, 'disturbing dreams', 'disturbing dreams question', 523);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1761, 2, 'not at all', 'not at all answer', 5230);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1762, 2, 'a little bit', 'a little bit answer', 5231);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1763, 2, 'moderately', 'moderately answer', 5232);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1764, 2, 'quite a bit', 'quite a bit answer', 5233);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1765, 2, 'extremely', 'extremely answer', 5234);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1770, 1, 'stressful experience', 'stressful experience question', 524);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1771, 2, 'not at all', 'not at all answer', 5240);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1772, 2, 'a little bit', 'a little bit answer', 5241);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1773, 2, 'moderately', 'moderately answer', 5242);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1774, 2, 'quite a bit', 'quite a bit answer', 5243);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1775, 2, 'extremely', 'extremely answer', 5244);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1780, 1, 'feeling very upset', 'feeling very upset question', 525);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1781, 2, 'not at all', 'not at all answer', 5250);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1782, 2, 'a little bit', 'a little bit answer', 5251);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1783, 2, 'moderately', 'moderately answer', 5252);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1784, 2, 'quite a bit', 'quite a bit answer', 5253);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1785, 2, 'extremely', 'extremely answer', 5254);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1790, 1, 'having physical reactions', 'having physical reactions question', 526);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1791, 2, 'not at all', 'not at all answer', 5260);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1792, 2, 'a little bit', 'a little bit answer', 5261);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1793, 2, 'moderately', 'moderately answer', 5262);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1794, 2, 'quite a bit', 'quite a bit answer', 5263);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1795, 2, 'extremely', 'extremely answer', 5264);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1800, 1, 'avoid thinking about or talking about  a stressful experience from the past ', 'avoid thinking about or talking about  a stressful experience from the past question', 527);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1801, 2, 'not at all', 'not at all answer', 5270);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1802, 2, 'a little bit', 'a little bit answer', 5271);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1803, 2, 'moderately', 'moderately answer', 5272);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1804, 2, 'quite a bit', 'quite a bit answer', 5273);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1805, 2, 'extremely', 'extremely answer', 5274);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1810, 1, 'avoid activities or situations because they remind you of a stressful experience from the past', 'avoid activities or situations because they remind you of a stressful experience from the past question', 528);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1811, 2, 'not at all', 'not at all answer', 5280);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1812, 2, 'a little bit', 'a little bit answer', 5281);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1813, 2, 'moderately', 'moderately answer', 5282);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1814, 2, 'quite a bit', 'quite a bit answer', 5283);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1815, 2, 'extremely', 'extremely answer', 5284);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1820, 1, 'trouble remembering important parts of a stressful experience from the past', 'trouble remembering important parts of a stressful experience from the past question', 529);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1821, 2, 'not at all', 'not at all answer', 5290);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1822, 2, 'a little bit', 'a little bit answer', 5291);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1823, 2, 'moderately', 'moderately answer', 5292);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1824, 2, 'quite a bit', 'quite a bit answer', 5293);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1825, 2, 'extremely', 'extremely answer', 5294);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1830, 1, 'a loss of interest in things that you used to enjoy', 'a loss of interest in things that you used to enjoy question', 530);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1831, 2, 'not at all', 'not at all answer', 5300);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1832, 2, 'a little bit', 'a little bit answer', 5301);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1833, 2, 'moderately', 'moderately answer', 5302);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1834, 2, 'quite a bit', 'quite a bit answer', 5303);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1835, 2, 'extremely', 'extremely answer', 5304);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1840, 1, 'feeling distant or cut off from other people', 'feeling distant or cut off from other people question', 531);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1841, 2, 'not at all', 'not at all answer', 5310);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1842, 2, 'a little bit', 'a little bit answer', 5311);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1843, 2, 'moderately', 'moderately answer', 5312);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1844, 2, 'quite a bit', 'quite a bit answer', 5313);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1845, 2, 'extremely', 'extremely answer', 5314);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1850, 1, 'feeling emotionally numb or being unable to have loving feelings for those close to you', 'feeling emotionally numb or being unable to have loving feelings for those close to you question', 532);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1851, 2, 'not at all', 'not at all answer', 5320);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1852, 2, 'a little bit', 'a little bit answer', 5321);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1853, 2, 'moderately', 'moderately answer', 5322);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1854, 2, 'quite a bit', 'quite a bit answer', 5323);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1855, 2, 'extremely', 'extremely answer', 5324);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1860, 1, 'feeling as if your future will somehow be cut short', 'feeling as if your future will somehow be cut short question', 533);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1861, 2, 'not at all', 'not at all answer', 5330);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1862, 2, 'a little bit', 'a little bit answer', 5331);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1863, 2, 'moderately', 'moderately answer', 5332);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1864, 2, 'quite a bit', 'quite a bit answer', 5333);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1865, 2, 'extremely', 'extremely answer', 5334);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1870, 1, 'trouble falling or staying asleep', 'trouble falling or staying asleep question', 534);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1871, 2, 'not at all', 'not at all answer', 5340);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1872, 2, 'a little bit', 'a little bit answer', 5341);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1873, 2, 'moderately', 'moderately answer', 5342);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1874, 2, 'quite a bit', 'quite a bit answer', 5343);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1875, 2, 'extremely', 'extremely answer', 5344);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1880, 1, 'feeling irritable or having angry outbursts', 'feeling irritable or having angry outbursts question', 535);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1881, 2, 'not at all', 'not at all answer', 5350);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1882, 2, 'a little bit', 'a little bit answer', 5351);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1883, 2, 'moderately', 'moderately answer', 5352);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1884, 2, 'quite a bit', 'quite a bit answer', 5353);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1885, 2, 'extremely', 'extremely answer', 5354);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1890, 1, 'difficulty concentrating', 'difficulty concentrating question', 536);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1891, 2, 'not at all', 'not at all answer', 5360);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1892, 2, 'a little bit', 'a little bit answer', 5361);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1893, 2, 'moderately', 'moderately answer', 5362);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1894, 2, 'quite a bit', 'quite a bit answer', 5363);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1895, 2, 'extremely', 'extremely answer', 5364);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1900, 1, 'being "super alert" or watchful, on guard', 'being "super alert" or watchful, on guard', 537);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1901, 2, 'not at all', 'not at all answer', 5370);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1902, 2, 'a little bit', 'a little bit answer', 5371);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1903, 2, 'moderately', 'moderately answer', 5372);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1904, 2, 'quite a bit', 'quite a bit answer', 5373);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1905, 2, 'extremely', 'extremely answer', 5374);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1910, 1, 'feeling jumpy or easily startled', 'feeling jumpy or easily startled question', 538);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1911, 2, 'not at all', 'not at all answer', 5380);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1912, 2, 'a little bit', 'a little bit answer', 5381);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1913, 2, 'moderately', 'moderately answer', 5382);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1914, 2, 'quite a bit', 'quite a bit answer', 5383);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1915, 2, 'extremely', 'extremely answer', 5384);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (1929, 4, 'pcl_score', 'pcl_score', '([1750] + [1760] + [1770] + [1780] + [1790] + [1800] + [1810] + [1820] + [1830] + [1840] + [1850] + [1860] + [1870] + [1880] + [1890] + [1900] + [1910])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (350, 1929, 1750);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (351, 1929, 1760);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (352, 1929, 1770);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (353, 1929, 1780);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (354, 1929, 1790);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (355, 1929, 1800);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (356, 1929, 1810);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (357, 1929, 1820);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (358, 1929, 1830);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (359, 1929, 1840);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (360, 1929, 1850);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (361, 1929, 1860);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (362, 1929, 1870);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (363, 1929, 1880);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (364, 1929, 1890);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (365, 1929, 1900);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (366, 1929, 1910);




INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1750, 1750, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1751, 1751, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1752, 1752, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1753, 1753, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1754, 1754, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1755, 1755, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1760, 1760, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1761, 1761, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1762, 1762, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1763, 1763, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1764, 1764, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1765, 1765, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1770, 1770, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1771, 1771, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1772, 1772, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1773, 1773, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1774, 1774, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1775, 1775, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1780, 1780, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1781, 1781, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1782, 1782, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1783, 1783, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1784, 1784, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1785, 1785, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1790, 1790, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1791, 1791, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1792, 1792, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1793, 1793, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1794, 1794, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1795, 1795, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1800, 1800, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1801, 1801, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1802, 1802, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1803, 1803, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1804, 1804, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1805, 1805, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1810, 1810, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1811, 1811, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1812, 1812, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1813, 1813, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1814, 1814, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1815, 1815, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1820, 1820, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1821, 1821, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1822, 1822, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1823, 1823, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1824, 1824, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1825, 1825, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1830, 1830, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1831, 1831, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1832, 1832, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1833, 1833, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1834, 1834, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1835, 1835, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1840, 1840, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1841, 1841, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1842, 1842, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1843, 1843, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1844, 1844, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1845, 1845, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1850, 1850, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1851, 1851, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1852, 1852, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1853, 1853, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1854, 1854, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1855, 1855, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1860, 1860, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1861, 1861, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1862, 1862, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1863, 1863, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1864, 1864, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1865, 1865, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1870, 1870, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1871, 1871, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1872, 1872, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1873, 1873, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1874, 1874, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1875, 1875, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1880, 1880, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1881, 1881, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1882, 1882, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1883, 1883, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1884, 1884, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1885, 1885, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1890, 1890, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1891, 1891, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1892, 1892, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1893, 1893, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1894, 1894, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1895, 1895, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1900, 1900, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1901, 1901, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1902, 1902, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1903, 1903, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1904, 1904, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1905, 1905, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1910, 1910, 35);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1911, 1911, 35, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1912, 1912, 35, 'a little bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1913, 1913, 35, 'moderately');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1914, 1914, 35, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1915, 1915, 35, 'extremely');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1929, 1929, 35);	


/* PC PTSD */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1940, 1, 'Have had any nightmares about it or thought about it when you did not want to', 'Have had any nightmares about it or thought about it when you did not want to question', 542);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1941, 2, 'no', 'no answer', 5420);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1942, 2, 'yes', 'yes answer', 5421);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1950, 1, 'Tried hard not to think about it; went out of your way to avoid situations that remind you of it', 'Tried hard not to think about it; went out of your way to avoid situations that remind you of it question', 543);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1951, 2, 'no', 'no answer', 5430);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1952, 2, 'yes', 'yes answer', 5431);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1960, 1, 'Were constantly on guard, watchful, or easily startled', 'Were constantly on guard, watchful, or easily startled', 544);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1961, 2, 'no', 'no answer', 5440);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1962, 2, 'yes', 'yes answer', 5441);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (1970, 1, 'Felt numb or detached from others, activities, or your surroundings', 'Felt numb or detached from others, activities, or your surroundings', 545);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1971, 2, 'no', 'no answer', 5450);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (1972, 2, 'yes', 'yes answer', 5451);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (1989, 4, 'pcptsd_score', 'pcptsd_score', '([1940] + [1950] + [1960] + [1970])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (400, 1989, 1940);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (401, 1989, 1950);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (402, 1989, 1960);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (403, 1989, 1970);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1940, 1940, 36);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1941, 1941, 36, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1942, 1942, 36, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1950, 1950, 36);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1951, 1951, 36, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1952, 1952, 36, 'ys');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1960, 1960, 36);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1961, 1961, 36, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1962, 1962, 36, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1970, 1970, 36);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1971, 1971, 36, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (1972, 1972, 36, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (1989, 1989, 36);


/* ISI */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2120, 1, 'difficulty falling asleep', 'difficulty falling asleep question', 553);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2121, 2, 'none', 'none answer', 5530);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2122, 2, 'mild', 'mild answer', 5531);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2123, 2, 'moderate', 'moderate answer', 5532);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2124, 2, 'severe', 'severe answer', 5533);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2125, 2, 'very severe', 'very severe answer', 5534);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2130, 1, 'difficulty staying asleep', 'difficulty staying asleep question', 554);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2131, 2, 'none', 'none answer', 5540);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2132, 2, 'mild', 'mild answer', 5541);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2133, 2, 'moderate', 'moderate answer', 5542);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2134, 2, 'severe', 'severe answer', 5543);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2135, 2, 'very severe', 'very severe answer', 5544);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2140, 1, 'problem waking up too early', 'problem waking up too early question', 555);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2141, 2, 'none', 'none answer', 5550);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2142, 2, 'mild', 'mild answer', 5551);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2143, 2, 'moderate', 'moderate answer', 5552);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2144, 2, 'severe', 'severe answer', 5553);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2145, 2, 'very severe', 'very severe answer', 5554);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2150, 1, 'How <b>SATISFIED</b>/<b>dissatisfied</b> are you with your current sleep pattern', 'How <b>SATISFIED</b>/<b>dissatisfied</b> are you with your current sleep pattern question', 556);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2151, 2, 'very satisfied', 'very satisfied answer', 5560);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2152, 2, 'very dissatisfied', 'very dissatisfied answer', 5561);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2160, 1, 'To what extent do you consider your sleep problem to <B>INTERFERE</B> with your daily functioning', 'To what extent do you consider your sleep problem to <B>INTERFERE</B> with your daily functioning question', 557);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2161, 2, 'not at all', 'not at all', 5570);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2162, 2, 'a little', 'a little answer', 5571);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2163, 2, 'somewhat', 'somewhat answer', 5572);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2164, 2, 'much', 'much answer', 5573);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2165, 2, 'very much interfering', 'very much interfering answer', 5574);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2170, 1, 'How <b>NOTICEABLE</b> to others do you think your sleeping problem is in terms of impairing the quality of your life', 'How <b>NOTICEABLE</b> to others do you think your sleeping problem is in terms of impairing the quality of your life question', 558);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2171, 2, 'not noticeable at all', 'not noticeable at all answer', 5580);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2172, 2, 'barely', 'barely answer', 5581);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2173, 2, 'somewhat', 'somewhat answer', 5582);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2174, 2, 'much', 'much answer', 5583);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2175, 2, 'very much noticeable', 'very much noticeable answer', 5584);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2180, 1, 'How <b>WORRIED</b>/distressed are you about your current sleep problem', 'How <b>WORRIED</b>/distressed are you about your current sleep problem question', 559);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2181, 2, 'not at all', 'not at all answer', 5590);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2182, 2, 'a little', 'a little answer', 5591);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2183, 2, 'somewhat', 'somewhat answer', 5592);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2184, 2, 'much', 'much answer', 5593);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2185, 2, 'very much', 'very much	answer', 5594);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (2189, 4, 'sleep_score', 'sleep_score', '([2120] + [2130] + [2140] + [2150] + [2160] + [2170] + [2180])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (550, 2189, 2120);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (551, 2189, 2130);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (552, 2189, 2140);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (553, 2189, 2150);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (554, 2189, 2160);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (555, 2189, 2170);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (556, 2189, 2180);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2120, 2120, 37);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2121, 2121, 37, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2122, 2122, 37, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2123, 2123, 37, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2124, 2124, 37, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2125, 2125, 37, 'very severe');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2130, 2130, 37);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2131, 2131, 37, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2132, 2132, 37, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2133, 2133, 37, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2134, 2134, 37, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2135, 2135, 37, 'very severe');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2140, 2140, 37);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2141, 2141, 37, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2142, 2142, 37, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2143, 2143, 37, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2144, 2144, 37, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2145, 2145, 37, 'very severe');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2150, 2150, 37);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2151, 2151, 37, 'very satisfied');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2152, 2152, 37, 'very dissatisfied');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2160, 2160, 37);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2161, 2161, 37, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2162, 2162, 37, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2163, 2163, 37, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2164, 2164, 37, 'much');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2165, 2165, 37, 'very much interfering');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2170, 2170, 37);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2171, 2171, 37, 'not noticeable at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2172, 2172, 37, 'barely');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2173, 2173, 37, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2174, 2174, 37, 'much');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2175, 2175, 37, 'very much noticeable');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2180, 2180, 37);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2181, 2181, 37, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2182, 2182, 37, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2183, 2183, 37, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2184, 2184, 37, 'much');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2185, 2185, 37, 'very much');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2189, 2189, 37);

/* Exposures */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2200, 1, 'Do you have any persistent major concerns regarding the effects of something you believe you may have been exposed to or encountered while deployed', 'Do you have any persistent major concerns regarding the effects of something you believe you may have been exposed to or encountered while deployed question', 126);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2201, 2, 'Chemical agents', 'Chemical agents answer', 1260);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2202, 2, 'Biological agents', 'Biological agents answer', 1261);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2203, 2, 'JP8 or other fuels', 'JP8 or other fuels answer', 1262);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2204, 2, 'Asbestos', 'Asbestos answer', 1263);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2205, 2, 'Nerve gas', 'Nerve gas answer', 1264);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2206, 2, 'Radiological agents', 'Radiological agents answer', 1265);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2207, 2, 'Sand/Dust or Particulate Matter', 'Sand/Dust or Particulate Matter answer', 1266);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2208, 2, 'Depleted uranium', 'Depleted uranium answer', 1267);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2209, 2, 'Industrial pollution', 'Industrial pollution answer', 1268);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2210, 2, 'Exhaust fumes', 'Exhaust fumes answer', 1269);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2211, 2, 'Paints', 'Paints answer', 1270);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2212, 2, 'Animal/insect bites', 'Animal/insect bites answer', 1271);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2213, 2, 'Smoke from burn pits', 'Smoke from burn pits answer', 1272);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2214, 2, 'Pesticides', 'Pesticides answer', 1273);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2215, 2, 'Other, please specify', 'Other, please specify answer', 1274);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2216, 2, 'Other, please specify', 'Other, please specify answer', 1275);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2230, 1, 'An animal bite that broke the skin', 'An animal bite that broke the skin question', 128);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2231, 2, 'no', 'no answer', 1280);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2232, 2, 'yes', 'yes answer', 1281);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2240, 1, 'Your mouth, eyes or broken skin exposed to the saliva or blood of an animal', 'Your mouth, eyes or broken skin exposed to the saliva or blood of an animal question', 129);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2241, 2, 'no', 'no answer', 1290);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2242, 2, 'yes', 'yes answer', 1291);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2250, 1, 'A bat in your sleeping quarters', 'A bat in your sleeping quarters question', 130);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2251, 2, 'no', 'no answer', 1300);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2252, 2, 'yes', 'yes answer', 1301);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2260, 1, 'Which of the following were you exposed to', 'Which of the following were you exposed to question', 132);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2261, 2, 'Being Attacked or Ambushed', 'Being Attacked or Ambushed answer', 1320);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2262, 2, 'Firing Weapons at the Enemy', 'Firing Weapons at the Enemy answer', 1321);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2263, 2, 'Hand to hand combat', 'Hand to hand combat answer', 1322);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2264, 2, 'Caring for wounded', 'Caring for wounded answer', 1323);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2265, 2, 'Interrogation', 'Interrogation answer', 1324);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2266, 2, 'Receiving rocket or mortar fire', 'Receiving rocket or mortar fire answer', 1325);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2267, 2, 'Seeing dead bodies', 'Seeing dead bodies answer', 1326);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2268, 2, 'Clearing or searching buildings', 'Clearing or searching buildings answer', 1327);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2269, 2, 'Firing from a Navy ship', 'Firing from a Navy ship answer', 1328);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2270, 2, 'Processing/handling detainees', 'Processing/handling detainees answer', 1329);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2271, 2, 'Receiving small arms fire', 'Receiving small arms fire answer', 1330);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2272, 2, 'Handling human remains', 'Handling human remains answer', 1331);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2273, 2, 'Someone killed near you', 'Someone killed near you answer', 1332);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2274, 2, 'Caring for enemy wounded', 'Caring for enemy wounded answer', 1333);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (2289, 4, 'Exposures formula', 'Exposures formula', '([2230] + [2240] + [2250])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (630, 2289, 2230);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (631, 2289, 2240);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (632, 2289, 2250);





INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2200, 2200, 15);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2201, 2201, 15, 'chemical agents');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2202, 2202, 15, 'biological agents');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2203, 2203, 15, 'JP8 or other fuels');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2204, 2204, 15, 'asbestos');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2205, 2205, 15, 'nerve gas');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2206, 2206, 15, 'radiological agents');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2207, 2207, 15, 'sand/dust or particulate matter');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2208, 2208, 15, 'depleted uranium');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2209, 2209, 15, 'industrial pollution');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2210, 2210, 15, 'exhaust fumes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2211, 2211, 15, 'paints');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2212, 2212, 15, 'animal/insect bites');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2213, 2213, 15, 'smoke from burn pits');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2214, 2214, 15, 'pesticides');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2215, 2215, 15, 'other');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2216, 2216, 15, 'other');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2230, 2230, 15);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2231, 2231, 15, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2232, 2232, 15, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2240, 2240, 15);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2241, 2241, 15, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2242, 2242, 15, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2250, 2250, 15);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2251, 2251, 15, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2252, 2252, 15, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2260, 2260, 15);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2261, 2261, 15, 'being attacked or ambushed');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2262, 2262, 15, 'firing weapons at the enemy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2263, 2263, 15, 'hand to hand combat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2264, 2264, 15, 'caring for wounded');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2265, 2265, 15, 'interrogation');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2266, 2266, 15, 'receiving rocket or mortar fire');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2267, 2267, 15, 'seeing dead bodies');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2268, 2268, 15, 'clearing or searching buildings');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2269, 2269, 15, 'firing from a Navy ship');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2270, 2270, 15, 'processing/handling detainees');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2271, 2271, 15, 'receiving small-arms fire');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2272, 2272, 15, 'handling human remains');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2273, 2273, 15, 'someone killed near you');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2274, 2274, 15, 'caring for enemy wounded');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2289, 2289, 15);


/* Basic Pain */


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2300, 1, 'pain intensity', 'pain intensity question', 220);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2301, 2, '0', '0 answer', 2199);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2302, 2, '1', '1 answer', 2200);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2303, 2, '2', '2 answer', 2201);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2304, 2, '3', '3 answer', 2202);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2305, 2, '4', '4 answer', 2203);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2306, 2, '5', '5 answer', 2204);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2307, 2, '6', '6 answer', 2205);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2308, 2, '7', '7 answer', 2206);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2309, 2, '8', '8 answer', 2207);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2310, 2, '9', '9 answer', 2208);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2311, 2, '10', '10 answer', 2209);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2330, 1, 'pain area', 'pain area question', 221);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2334, 2, 'none', 'none answer', 2210);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2331, 2, 'pain area', 'pain area answer', 2220);




INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2300, 2300, 20);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2301, 2301, 20, '0');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2302, 2302, 20, '1');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2303, 2303, 20, '2');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2304, 2304, 20, '3');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2305, 2305, 20, '4');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2306, 2306, 20, '5');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2307, 2307, 20, '6');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2308, 2308, 20, '7');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2309, 2309, 20, '8');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2310, 2310, 20, '9');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2311, 2311, 20, '10');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2330, 2330, 20);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2334, 2334, 20, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2331, 2331, 20, 'pain area answer');




/* OOO Infect & Embedded Fragment CR */
INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2500, 1, 'SCREEN FOR GI SYMPTOMS', 'SCREEN FOR GI SYMPTOMS', 190);
-- INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2350, 1, 'Do you have any problems with chronic diarrhea or other gastrointestinal complaints', 'Do you have any problems with chronic diarrhea or other gastrointestinal complaints question', 190);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2351, 2, 'no', 'no answer', 1900);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2352, 2, 'yes what symptoms', 'yes what symptoms answer', 1901);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2501, 1, 'SCREEN FOR FEVER', 'SCREEN FOR FEVER', 191);
-- INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2370, 1, 'Do you have any unexplained fevers', 'Do you have any unexplained fevers question', 191);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2371, 2, 'no', 'no answer', 1910);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2372, 2, 'yes what symptoms', 'yes what symptoms answer', 1911);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2502, 1, 'SCREEN FOR SKIN RASH/LESIONS', 'SCREEN FOR SKIN RASH/LESIONS', 192);
-- INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2390, 1, 'Do you have a persistent papular or nodular skin rash that began after deployment to Southwest Asia', 'Do you have a persistent papular or nodular skin rash that began after deployment to Southwest Asia question', 192);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2391, 2, 'no', 'no answer', 1920);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2392, 2, 'yes what symptoms', 'yes what symptoms answer', 1921);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2009, 1, 'VA-Embedded fragments Question 1', 'VA-Embedded fragments Question 1', 193);
-- INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2400, 1, 'Do you have or suspect you have retained fragments or shrapnel', 'Do you have any problems with chronic diarrhea or other gastrointestinal question', 193);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2401, 2, 'no', 'no answer', 1930);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2402, 2, 'yes what symptoms', 'yes what symptoms answer', 1931);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2500, 2500, 19);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2351, 2351, 19, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2352, 2352, 19, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2501, 2501, 19);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2371, 2371, 19, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2372, 2372, 19, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2502, 2502, 19);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2391, 2391, 19, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2392, 2392, 19, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2009, 2009, 19);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2401, 2401, 19, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2402, 2402, 19, 'yes');



/* Promis Pain Intensity & Interference */

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2550, 1, 'What is your level of pain right now', 'What is your level of pain right now question', 197);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2551, 2, 'no pain', 'no pain answer', 1940);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2552, 2, 'mild', 'mild answer', 1941);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2553, 2, 'moderate', 'moderate answer', 1942);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2554, 2, 'severe', 'severe answer', 1943);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2555, 2, 'very severe', 'very severe answer', 1944);


INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2560, 1, 'What is your level of pain intensity last week', 'What is your level of pain intensity last week question', 201);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2561, 2, 'no pain', 'no pain answer', 2010);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2562, 2, 'bothered a little', 'bothered a little answer', 2011);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2563, 2, 'moderate', 'moderate answer', 2012);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2564, 2, 'severe', 'severe answer', 2013);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2565, 2, 'very severe', 'very severe answer', 2014);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2570, 1, 'pain average', 'pain average question', 202);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2571, 2, 'no pain', 'no pain answer', 2020);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2572, 2, 'bothered a little', 'bothered a little answer', 2021);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2573, 2, 'moderate', 'moderate answer', 2022);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2574, 2, 'severe', 'severe answer', 2023);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2575, 2, 'very severe', 'very severe answer', 2024);




INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2580 , 1, 'How much did pain interfere with your enjoyment of life', 'How much did pain interfere with your enjoyment of life question', 212);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2581, 2, 'not at all', 'not at all answer', 2120);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2582, 2, 'a little', 'a little answer', 2121);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2583, 2, 'somewhat', 'somewhat answer', 2122);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2584, 2, 'quite a bit', 'quite a bit answer', 2123);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2585, 2, 'very much', 'very much answer', 2124);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2590, 1, 'How much did pain interfere with your ability to concentrate', 'How much did pain interfere with your ability to concentrate question', 213);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2591, 2, 'not at all', 'not at all answer', 2130);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2592, 2, 'a little', 'a little answer', 2131);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2593, 2, 'somewhat', 'somewhat answer', 2132);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2594, 2, 'quite a bit', 'quite a bit answer', 2133);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2595, 2, 'very much', 'very much answer', 2134);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2600, 1, 'How much did pain interfere with your day to day activities', 'How much did pain interfere with your day to day activities question', 214);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2601, 2, 'not at all', 'not at all answer', 2140);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2602, 2, 'a little', 'a little answer', 2141);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2603, 2, 'somewhat', 'somewhat answer', 2142);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2604, 2, 'quite a bit', 'quite a bit answer', 2143);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2605, 2, 'very much', 'very much answer', 2144);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2610, 1, 'How much did pain interfere with you enjoyment of recreational activities', 'How much did pain interfere with you enjoyment of recreational activities question', 215);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2611, 2, 'not at all', 'not at all answer', 2150);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2612, 2, 'a little', 'a little answer', 2151);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2613, 2, 'somewhat', 'somewhat answer', 2152);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2614, 2, 'quite a bit', 'quite a bit answer', 2153);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2615, 2, 'very much', 'very much answer', 2154);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2620, 1, 'How much did pain interfere with doing your tasks away from home', 'How much did pain interfere with doing your tasks away from home question', 216);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2621, 2, 'not at all', 'not at all answer', 2160);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2622, 2, 'a little', 'a little answer', 2161);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2623, 2, 'somewhat', 'somewhat answer', 2162);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2624, 2, 'quite a bit', 'quite a bit answer', 2163);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2625, 2, 'very much', 'very much answer', 2164);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2630, 1, 'How often did pain keep you from socializing with others', 'How often did pain keep you from socializing with others question', 218);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2631, 2, 'not at all', 'not at all answer', 2170);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2632, 2, 'a little', 'a little answer', 2171);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2633, 2, 'somewhat', 'somewhat answer', 2172);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2634, 2, 'quite a bit', 'quite a bit answer', 2173);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2635, 2, 'very much', 'very much answer', 2174);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (2640, 4, 'pain_score_interference', 'pain_score_interference', '([2580] + [2590] + [2600] + [2610] + [2620] + [2630])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (30, 2640, 2580);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (31, 2640, 2590);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (32, 2640, 2600);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (33, 2640, 2610);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (34, 2640, 2620);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (35, 2640, 2630);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (2650, 4, 'pain_score_intensity', 'pain_score_intensity', '([2550] + [2560] + [2570])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (680, 2650, 2550);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (681, 2650, 2560);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (682, 2650, 2570);





INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2550, 2550, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2551, 2551, 21, 'no pain');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2552, 2552, 21, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2553, 2553, 21, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2554, 2554, 21, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2555, 2555, 21, 'very severe');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2560, 2560, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2561, 2561, 21, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2562, 2562, 21, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2563, 2563, 21, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2564, 2564, 21, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2565, 2565, 21, 'very severe');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2570, 2570, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2571, 2571, 21, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2572, 2572, 21, 'little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2573, 2573, 21, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2574, 2574, 21, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2575, 2575, 21, 'very severe');




INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2580, 2580, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2581, 2581, 21, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2582, 2582, 21, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2583, 2583, 21, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2584, 2584, 21, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2585, 2585, 21, 'very much');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2590, 2590, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2591, 2591, 21, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2592, 2592, 21, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2593, 2593, 21, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2594, 2594, 21, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2595, 2595, 21, 'very much');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2600, 2600, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2601, 2601, 21, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2602, 2602, 21, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2603, 2603, 21, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2604, 2604, 21, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2605, 2605, 21, 'very much');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2610, 2610, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2611, 2611, 21, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2612, 2612, 21, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2613, 2613, 21, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2614, 2614, 21, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2615, 2615, 21, 'very much');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2620, 2620, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2621, 2621, 21, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2622, 2622, 21, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2623, 2623, 21, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2624, 2624, 21, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2625, 2625, 21, 'very much');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2630, 2630, 21);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2631, 2631, 21, 'not at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2632, 2632, 21, 'a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2633, 2633, 21, 'somewhat');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2634, 2634, 21, 'quite a bit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2635, 2635, 21, 'very much');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2640, 2640, 21);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2650, 2650, 21);

/* MDQ */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2660, 1, 'You felt so good or hyper that other people thought you were not your normal self or you were so hyper that you got into trouble', 'You felt so good or hyper that other people thought you were not your normal self or you were so hyper that you got into trouble question', 472);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2661, 2, 'no', 'no answer', 4720);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2662, 2, 'yes', 'yes answer', 4721);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2670, 1, 'You were so irritable that you shouted at people or started fights or arguments', 'You were so irritable that you shouted at people or started fights or arguments question', 473);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2671, 2, 'no', 'no answer', 4730);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2672, 2, 'yes', 'yes answer', 4731);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2680, 1, 'You felt much more self-confident than usual', 'You felt much more self-confident than usual question', 474);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2681, 2, 'no', 'no answer', 4740);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2682, 2, 'yes', 'yes answer', 4741);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2690, 1, 'You got much less sleep than usual and found you didn\'t really miss it', 'You got much less sleep than usual and found you didn\'t really miss it question', 475);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2691, 2, 'no', 'no answer', 4750);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2692, 2, 'yes', 'yes answer', 4751);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2700, 1, 'You were much more talkative or spoke much faster than usual', 'You were much more talkative or spoke much faster than usual question', 476);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2701, 2, 'no', 'no answer', 4760);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2702, 2, 'yes', 'yes answer', 4761);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2710, 1, 'Thoughts raced through your head or you couldn\'t slow your mind down', 'Thoughts raced through your head or you couldn\'t slow your mind down question', 477);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2711, 2, 'no', 'no answer', 4770);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2712, 2, 'yes', 'yes answer', 4771);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2720, 1, 'You were so easily distracted by things around you that you had trouble concentrating or staying on track', 'You were so easily distracted by things around you that you had trouble concentrating or staying on track question', 478);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2721, 2, 'no', 'no answer', 4780);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2722, 2, 'yes', 'yes answer', 4781);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2730, 1, 'You had much more energy than usual', 'You had much more energy than usual question', 479);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2731, 2, 'no', 'no answer', 4790);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2732, 2, 'yes', 'yes answer', 4791);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2740, 1, 'You were much more active or did many more things than usual', 'You were much more active or did many more things than usual question', 480);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2741, 2, 'no', 'no answer', 4800);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2742, 2, 'yes', 'yes answer', 4801);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2750, 1, 'You were much more social or outgoing than usual, for example, you telephoned friends in the middle of the night', 'You were much more social or outgoing than usual, for example, you telephoned friends in the middle of the night question', 481);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2751, 2, 'no', 'no answer', 4810);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2752, 2, 'yes', 'yes answer', 4811);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2760, 1, 'You were much more interested in sex than usual', 'You were much more interested in sex than usual question', 482);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2761, 2, 'no', 'no answer', 4820);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2762, 2, 'yes', 'yes answer', 4821);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2770, 1, 'You did things that were unusual for you or that other people might have thought were excessive foolish or risky', 'You did things that were unusual for you or that other people might have thought were excessive foolish or risky question', 483);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2771, 2, 'no', 'no answer', 4830);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2772, 2, 'yes', 'yes answer', 4831);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2780, 1, 'Spending money got you or your family into trouble', 'Spending money got you or your family into trouble question', 484);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2781, 2, 'no', 'no answer', 4840);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2782, 2, 'yes', 'yes answer', 4841);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2790, 1, 'If you checked YES to more than one of the above, have several of these happened during the same period of time', 'If you checked YES to more than one of the above, have several of these happened during the same period of time question', 485);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2791, 2, 'no', 'no answer', 4850);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2792, 2, 'yes', 'yes answer', 4851);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2800, 1, 'How much of a problem did any of these cause you, -- like being unable to work; having family, money or legal troubles; getting into arguments or fights', 'How much of a problem did any of these cause you, -- like being unable to work; having family, money or legal troubles; getting into arguments or fights question', 490);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2801, 2, 'no problem', 'no problem answer', 4900);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2802, 2, 'minor problem', 'minor problem answer', 4901);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2803, 2, 'moderate problem', 'moderate problem answer', 4902);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2804, 2, 'serious problem', 'serious problem answer', 4903);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (2809, 4, 'hyp_score', 'MDQ hyp1 formula', '([2660] + [2670] + [2680] + [2690] + [2700] + [2710] + [2720] + [2730] + [2740] + [2750] + [2760] + [2770] + [2780])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (475, 2809, 2660);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (476, 2809, 2670);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (477, 2809, 2680);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (478, 2809, 2690);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (479, 2809, 2700);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (480, 2809, 2710);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (481, 2809, 2720);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (482, 2809, 2730);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (483, 2809, 2740);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (484, 2809, 2750);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (485, 2809, 2760);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (486, 2809, 2770);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (487, 2809, 2780);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2660, 2660, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2661, 2661, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2662, 2662, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2670, 2670, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2671, 2671, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2672, 2672, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2680, 2680, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2681, 2681, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2682, 2682, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2690, 2690, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2691, 2691, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2692, 2692, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2700, 2700, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2701, 2701, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2702, 2702, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2710, 2710, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2711, 2711, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2712, 2712, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2720, 2720, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2721, 2721, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2722, 2722, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2730, 2730, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2731, 2731, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2732, 2732, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2740, 2740, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2741, 2741, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2742, 2742, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2750, 2750, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2751, 2751, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2752, 2752, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2760, 2760, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2761, 2761, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2762, 2762, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2770, 2770, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2771, 2771, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2772, 2772, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2780, 2780, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2781, 2781, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2782, 2782, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2790, 2790, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2791, 2791, 32, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2792, 2792, 32, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2800, 2800, 32);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2801, 2801, 32, 'no problem');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2802, 2802, 32, 'minor problem');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2803, 2803, 32, 'moderate problem');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2804, 2804, 32, 'serious problem');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2809, 2809, 32);



/* CD-RISC-10 */

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2820, 1, 'I am able to adapt when changes occur', 'I am able to adapt when changes occur question', 582);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2821, 2, 'not true at all', 'not true at all answer', 5820);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2822, 2, 'rarely true', 'rarely true answer', 1582);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2823, 2, 'sometimes', 'sometimes answer', 5822);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2824, 2, 'often true', 'often true answer', 5823);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2825, 2, 'true nearly all the time', 'true nearly all the time answer', 5824);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2830, 1, 'I can deal with whatever comes my way', 'I can deal with whatever comes my way question', 583);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2831, 2, 'not true at all', 'not true at all answer', 5830);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2832, 2, 'rarely true', 'rarely true answer', 5831);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2833, 2, 'sometimes', 'sometimes answer', 5832);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2834, 2, 'often true', 'often true answer', 5833);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2835, 2, 'true nearly all the time', 'true nearly all the time answer', 5834);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2840, 1, 'I try to see the humorous side of things when I am faced with problems', 'I try to see the humorous side of things when I am faced with problems question', 584);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2841, 2, 'not true at all', 'not true at all answer', 5840);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2842, 2, 'rarely true', 'rarely true answer', 5841);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2843, 2, 'sometimes', 'sometimes answer', 5842);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2844, 2, 'often true', 'often true answer', 5843);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2845, 2, 'true nearly all the time', 'true nearly all the time answer', 5844);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2850, 1, 'Having to cope with stress can make me stronger', 'Having to cope with stress can make me stronger question', 585);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2851, 2, 'not true at all', 'not true at all answer', 5850);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2852, 2, 'rarely true', 'rarely true answer', 5851);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2853, 2, 'sometimes', 'sometimes answer', 5852);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2854, 2, 'often true', 'often true answer', 5853);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2855, 2, 'true nearly all the time', 'true nearly all the time answer', 5854);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2860, 1, 'I tend to bounce back after an illness, injury, or other hardships', 'I tend to bounce back after an illness, injury, or other hardships question', 586);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2861, 2, 'not true at all', 'not true at all answer', 5860);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2862, 2, 'rarely true', 'rarely true answer', 5861);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2863, 2, 'sometimes', 'sometimes answer', 5862);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2864, 2, 'often true', 'often true answer', 5863);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2865, 2, 'true nearly all the time', 'true nearly all the time answer', 5864);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2870, 1, 'I believe I can achieve my goals, even if there are obstacles', 'I believe I can achieve my goals, even if there are obstacles question', 587);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2871, 2, 'not true at all', 'not true at all answer', 5870);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2872, 2, 'rarely true', 'rarely true answer', 5871);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2873, 2, 'sometimes', 'sometimes answer', 5872);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2874, 2, 'often true', 'often true answer', 5873);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2875, 2, 'true nearly all the time', 'true nearly all the time answer', 5874);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2880, 1, 'Under pressure, I stay focused and think clearly', 'Under pressure, I stay focused and think clearly question', 588);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2881, 2, 'not true at all', 'not true at all answer', 5880);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2882, 2, 'rarely true', 'rarely true answer', 5881);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2883, 2, 'sometimes', 'sometimes answer', 5882);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2884, 2, 'often true', 'often true answer', 5883);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2885, 2, 'true nearly all the time', 'true nearly all the time answer', 5884);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2890, 1, 'I am not easily discouraged by failure', 'I am not easily discouraged by failure question', 589);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2891, 2, 'not true at all', 'not true at all answer', 5890);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2892, 2, 'rarely true', 'rarely true answer', 5891);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2893, 2, 'sometimes', 'sometimes answer', 5892);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2894, 2, 'often true', 'often true answer', 5893);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2895, 2, 'true nearly all the time', 'true nearly all the time answer', 5894);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2900, 1, 'I think of myself as a strong person when dealing with life\'s challenges and difficulties', 'I think of myself as a strong person when dealing with life\'s challenges and difficulties question', 590);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2901, 2, 'not true at all', 'not true at all answer', 5900);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2902, 2, 'rarely true', 'rarely true answer', 5901);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2903, 2, 'sometimes', 'sometimes answer', 5902);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2904, 2, 'often true', 'often true answer', 5903);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2905, 2, 'true nearly all the time', 'true nearly all the time answer', 5904);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2910, 1, 'I am able to handle unpleasant or painful feelings like sadness, fear, or anger', 'I am able to handle unpleasant or painful feelings like sadness, fear, or anger question', 591);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2911, 2, 'not true at all', 'not true at all answer', 5910);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2912, 2, 'rarely true', 'rarely true answer', 5911);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2913, 2, 'sometimes', 'sometimes answer', 5912);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2914, 2, 'often true', 'often true answer', 5913);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2915, 2, 'true nearly all the time', 'true nearly all the time answer', 5914);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (2930, 4, 'res_score', 'res_score', '([2820] + [2830] + [2840] + [2850] + [2860] + [2870] + [2880] + [2890] + [2900] + [2910])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (40, 2930, 2820);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (41, 2930, 2830);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (42, 2930, 2840);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (43, 2930, 2850);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (44, 2930, 2860);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (45, 2930, 2870);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (46, 2930, 2880);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (47, 2930, 2890);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (48, 2930, 2900);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (49, 2930, 2910);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2820, 2820, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2821, 2821, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2822, 2822, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2823, 2823, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2824, 2824, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2825, 2825, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2830, 2830, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2831, 2831, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2832, 2832, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2833, 2833, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2834, 2834, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2835, 2835, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2840, 2840, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2841, 2841, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2842, 2842, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2843, 2843, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2844, 2844, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2845, 2845, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2850, 2850, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2851, 2851, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2852, 2852, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2853, 2853, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2854, 2854, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2855, 2855, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2860, 2860, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2861, 2861, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2862, 2862, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2863, 2863, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2864, 2864, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2865, 2865, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2870, 2870, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2871, 2871, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2872, 2872, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2873, 2873, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2874, 2874, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2875, 2875, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2880, 2880, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2881, 2881, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2882, 2882, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2883, 2883, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2884, 2884, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2885, 2885, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2890, 2890, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2891, 2891, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2892, 2892, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2893, 2893, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2894, 2894, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2895, 2895, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2900, 2900, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2901, 2901, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2902, 2902, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2903, 2903, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2904, 2904, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2905, 2905, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2910, 2910, 39);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2911, 2911, 39, 'not true at all');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2912, 2912, 39, 'rarely true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2913, 2913, 39, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2914, 2914, 39, 'often true');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2915, 2915, 39, 'true nearly all the time');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2930, 2930, 39);	



/* Caffeine Use */

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3050, 1, 'In the past 4 weeks how many servings of caffeinated beverages did you drink per day', 'In the past 4 weeks how many servings of caffeinated beverages did you drink per day question', 330);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3051, 2, 'none', 'none answer', 3300);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3052, 2, '1-2', '1-2 answer', 3301);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3053, 2, '3-4', '3-4 answer', 3302);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3054, 2, '5+', '5+ answer', 3303);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3050, 3050, 25);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3051, 3051, 25, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3052, 3052, 25, '1-2');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3053, 3053, 25, '3-4');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3054, 3054, 25, '5+');



/* Service History */


INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3070, 1, 'Type of Service', 'Type of Service question', 91);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3071, 2, 'Active Duty', 'Active Duty answer', 910);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3072, 2, 'Reserve', 'Reserve answer', 911);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3073, 2, 'Guard', 'Guard answer', 912);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3080, 1, 'Branch of Service', 'Branch of Service question', 92);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3081, 2, 'Army', 'Army answer', 920);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3082, 2, 'Air Force', 'Air Force answer', 921);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3083, 2, 'Coast Guard', 'Coast Guard answer', 922);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3084, 2, 'Marines', 'Marines answer', 923);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3085, 2, 'National Guard', 'National Guard answer', 924);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3086, 2, 'Navy', 'Navy answer', 925);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3090, 1, 'Year Entered Service', 'Year Entered Service question', 93);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3091, 2, 'Year Entered Service', 'Year Entered Service answer', 930);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3100, 1, 'Year Discharged from Service', 'Year Discharged from Service question', 94);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3101, 2, 'Year Discharged from Service', 'Year Discharged from Service answer', 940);

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3110, 1, 'Type of Discharge', 'Type of Discharge question', 96);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3111, 2, 'Honorable', 'Honorable answer', 960);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3112, 2, 'Other than Honorable', 'Other than Honorable answer', 961);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3113, 2, 'Dishonorable', 'Dishonorable answer', 962);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3114, 2, 'General w/ Honorable Duty', 'General w/ Honorable answer', 963);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3115, 2, 'Medical', 'Medical answer', 964);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3116, 2, 'Administrative Separation', 'Administrative Separation answer', 965);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3117, 2, 'Retired', 'Retired answer', 966);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3120, 1, 'Rank', 'Rank question', 97);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3121, 2, 'e1', 'e1 answer', 970);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3122, 2, 'e2', 'e2 answer', 971);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3123, 2, 'e3', 'e3 answer', 972);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3124, 2, 'e4', 'e4 answer', 973);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3125, 2, 'e5', 'e5 answer', 974);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3126, 2, 'e6', 'e6 answer', 975);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3127, 2, 'e7', 'e7 answer', 976);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3128, 2, 'e8', 'e8 answer', 977);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3129, 2, 'e9', 'e9 answer', 978);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3130, 2, 'o1', 'o1 answer', 979);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3131, 2, 'o2', 'o2 answer', 980);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3132, 2, 'o3', 'o3 answer', 981);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3133, 2, 'o4', 'o4 answer', 982);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3134, 2, 'o5', 'o5 answer', 983);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3135, 2, 'o6', 'o6 answer', 984);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3136, 2, 'w1', 'w1 answer', 985);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3137, 2, 'w2', 'w2 answer', 986);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3138, 2, 'w3', 'w3 answer', 987);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3139, 2, 'w4', 'w4 answer', 988);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3140, 2, 'w5', 'w5 answer', 989);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3150, 1, 'Job, MOS, or RATE', 'Job, MOS, or RATE question', 99);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3151, 2, '', 'Job, MOS, or RATE answer', 1000);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3160, 1, 'Did you ever serve in any of the following operations or foreign combat zones', 'Did you ever serve in any of the following operations or foreign combat zones question', 110);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3161, 2, 'none', 'none answer', 1100);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3162, 2, 'Operation Enduring Freedom (OEF)', 'Operation Enduring Freedom (OEF) answer', 1101);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3163, 2, 'Operation Iraqi Freedom (OIF)', 'Operation Iraqi Freedom (OIF) answer', 1102);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3164, 2, 'Global War on Terror operations other than OEF/OIF (e.g. Noble Eagle, Vigilant Mariner)', 'Global War on Terror operations other than OEF/OIF (e.g. Noble Eagle, Vigilant Mariner) answer', 1103);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3165, 2, 'Operation New Dawn', 'Operation New Dawn answer', 1104);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3166, 2, 'Caribbean / South America / Central America', 'Caribbean / South America / Central America answer', 1105);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3167, 2, 'Gulf War (1991)', 'Gulf War (1991) answer', 1106);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3168, 2, 'Somalia', 'Somalia answer', 1107);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3169, 2, 'Bosnia', 'Bosnia answer', 1108);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3170, 2, 'Kosovo', 'Kosovo answer', 1109);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3171, 2, 'Djibouti', 'Djibouti answer', 1110);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3172, 2, 'Libya', 'Libya answer', 1111);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3173, 2, 'Vietnam', 'Vietnam answer', 1112);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3174, 2, 'Korean War', 'Korean War answer', 1113);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3175, 2, 'Other, please specify', 'Other, please specify answer', 1114);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3176, 2, 'Other, please specify', 'Other, please specify answer', 1115);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3180, 1, 'Please add one entry for each enlistment', 'Please add one entry for each enlistment question', 90);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3181, 2, 'enlistment', 'elistment answer', 900);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3070, 3070, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3071, 3071, 12, 'Active Duty');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3072, 3072, 12, 'Reserve');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3073, 3073, 12, 'Guard');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3080, 3080, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3081, 3081, 12, 'Army');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3082, 3082, 12, 'Air Force');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3083, 3083, 12, 'Coast Guard');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3084, 3084, 12, 'Marines');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3085, 3085, 12, 'National Guard');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3086, 3086, 12, 'Navy');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3090, 3090, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3091, 3091, 12, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3100, 3100, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3101, 3101, 12, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3110, 3110, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3111, 3111, 12, 'Honorable');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3112, 3112, 12, 'Other than Honorable');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3113, 3113, 12, 'Dishonorable');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3114, 3114, 12, 'General w/ Honorable');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3115, 3115, 12, 'Medical');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3116, 3116, 12, 'Administrative Separation');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3117, 3117, 12, 'Retired');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3120, 3120, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3121, 3121, 12, 'e1');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3122, 3122, 12, 'e2');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3123, 3123, 12, 'e3');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3124, 3124, 12, 'e4');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3125, 3125, 12, 'e5');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3126, 3126, 12, 'e6');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3127, 3127, 12, 'e7');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3128, 3128, 12, 'e8');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3129, 3129, 12, 'e9');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3130, 3130, 12, 'o1');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3131, 3131, 12, 'o2');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3132, 3132, 12, 'o3');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3133, 3133, 12, 'o4');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3134, 3134, 12, 'o5');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3135, 3135, 12, 'o6');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3136, 3136, 12, 'w1');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3137, 3137, 12, 'w2');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3138, 3138, 12, 'w3');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3139, 3139, 12, 'w4');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3140, 3140, 12, 'w5');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3150, 3150, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3151, 3151, 12, 'Job, MOS, or RATE');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3160, 3160, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3161, 3161, 12, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3162, 3162, 12, 'Operation Enduring Freedom (OEF)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3163, 3163, 12, 'Operation Iraqi Freedom (OIF)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3164, 3164, 12, 'Global War on Terror operations other than OEF/OIF (e.g. Noble Eagle, Vigilant Mariner)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3165, 3165, 12, 'Operation New Dawn');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3166, 3166, 12, 'Caribbean / South America / Central America');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3167, 3167, 12, 'Gulf War (1991)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3168, 3168, 12, 'Somalia');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3169, 3169, 12, 'Bosnia');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3170, 3170, 12, 'Kosovo');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3171, 3171, 12, 'Djibouti');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3172, 3172, 12, 'Libya');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3173, 3173, 12, 'Vietnam');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3174, 3174, 12, 'Korean War');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3175, 3175, 12, 'other');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3176, 3176, 12, 'other');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3180, 3180, 12);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3181, 3181, 12, 'answer');


/* ROAS - Agression */


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3200, 1, 'Make loud noises, scream, shout angrily', 'Make loud noises, scream, shout angrily question', 562);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3201, 2, 'never', 'never answer', 5620);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3202, 2, 'sometimes', 'sometimes answer', 5621);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3203, 2, 'often', 'often answer', 5622);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3204, 2, 'usually', 'usually answer', 5623);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3205, 2, 'always', 'always answer', 5624);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3210, 1, 'Yell mild personal assaults at others', 'Yell mild personal assaults at others question', 563);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3211, 2, 'never', 'never answer', 5630);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3212, 2, 'sometimes', 'sometimes answer', 5631);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3213, 2, 'often', 'often answer', 5632);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3214, 2, 'usually', 'usually answer', 5633);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3215, 2, 'always', 'always answer', 5634);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3220, 1, 'Curse, use foul language, make vague threats to hurt myself or others', 'Curse, use foul language, make vague threats to hurt myself or others question', 564);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3221, 2, 'never', 'never answer', 5640);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3222, 2, 'sometimes', 'sometimes answer', 5641);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3223, 2, 'often', 'often answer', 5642);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3224, 2, 'usually', 'usually answer', 5643);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3225, 2, 'always', 'always answer', 5644);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3230, 1, 'Make clear threats of violence towards others or myself, or ask for help from others to control myself', 'Make clear threats of violence towards others or myself, or ask for help from others to control myself question', 565);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3231, 2, 'never', 'never answer', 5650);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3232, 2, 'sometimes', 'sometimes answer', 5651);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3233, 2, 'often', 'often answer', 5652);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3234, 2, 'usually', 'usually answer', 5653);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3235, 2, 'always', 'always answer', 5654);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3240, 1, 'Slam doors, make a mess, scatter clothing', 'Slam doors, make a mess, scatter clothing question', 566);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3241, 2, 'never', 'never answer', 5660);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3242, 2, 'sometimes', 'sometimes answer', 5661);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3243, 2, 'often', 'often answer', 5662);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3244, 2, 'usually', 'usually answer', 5663);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3245, 2, 'always', 'always answer', 5664);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3250, 1, 'Throw objects down, kick furniture, without breaking it, mark the walls', 'Throw objects down, kick furniture, without breaking it, mark the walls question', 567);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3251, 2, 'never', 'never answer', 5670);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3252, 2, 'sometimes', 'sometimes answer', 5671);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3253, 2, 'often', 'often answer', 5672);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3254, 2, 'usually', 'usually answer', 5673);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3255, 2, 'always', 'always answer', 5674);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3260, 1, 'Break objects, smash windows', 'Break objects, smash windows question', 568);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3261, 2, 'never', 'never answer', 5680);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3262, 2, 'sometimes', 'sometimes answer', 5681);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3263, 2, 'often', 'often answer', 5682);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3264, 2, 'usually', 'usually answer', 5683);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3265, 2, 'always', 'always answer', 5684);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3270, 1, 'Set fires, thrown objects dangerously', 'Set fires, thrown objects dangerously question', 569);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3271, 2, 'never', 'never answer', 5690);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3272, 2, 'sometimes', 'sometimes answer', 5691);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3273, 2, 'often', 'often answer', 5692);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3274, 2, 'usually', 'usually answer', 5693);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3275, 2, 'always', 'always answer', 5694);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3280, 1, 'Make threatening gestures, swing at people, grab at clothes of others', 'Make threatening gestures, swing at people, grab at clothes of others question', 570);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3281, 2, 'never', 'never answer', 5700);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3282, 2, 'sometimes', 'sometimes answer', 5701);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3283, 2, 'often', 'often answer', 5702);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3284, 2, 'usually', 'usually answer', 5703);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3285, 2, 'always', 'always answer', 5704);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3290, 1, 'Strike, kick, push, pull hair (others)', 'Strike, kick, push, pull hair (others) question', 571);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3291, 2, 'never', 'never answer', 5710);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3292, 2, 'sometimes', 'sometimes answer', 5711);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3293, 2, 'often', 'often answer', 5712);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3294, 2, 'usually', 'usually answer', 5713);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3295, 2, 'always', 'always answer', 5714);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3300, 1, 'Attack others causing physical injury (bruises, sprain, welts)', 'Attack others causing physical injury (bruises, sprain, welts) question', 572);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3301, 2, 'never', 'never answer', 5720);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3302, 2, 'sometimes', 'sometimes answer', 5721);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3303, 2, 'often', 'often answer', 5722);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3304, 2, 'usually', 'usually answer', 5723);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3305, 2, 'always', 'always answer', 5724);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3310, 1, 'Attack other causing severe physical injury (broken bones, deep cuts)', 'Attack other causing severe physical injury (broken bones, deep cuts) question', 573);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3311, 2, 'never', 'never answer', 5730);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3312, 2, 'sometimes', 'sometimes answer', 5731);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3313, 2, 'often', 'often answer', 5732);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3314, 2, 'usually', 'usually answer', 5733);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3315, 2, 'always', 'always answer', 5734);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3320, 1, 'Pick or scratch my skin, hit myself on arms and body, pinch myself, pull my own hair', 'Pick or scratch my skin, hit myself on arms and body, pinch myself, pull my own hair question', 574);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3321, 2, 'never', 'never answer', 5740);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3322, 2, 'sometimes', 'sometimes answer', 5741);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3323, 2, 'often', 'often answer', 5742);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3324, 2, 'usually', 'usually answer', 5743);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3325, 2, 'always', 'always answer', 5744);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3330, 1, 'Bang my head, use my fist to hit objects, throw myself onto the floor or into others', 'Bang my head, use my fist to hit objects, throw myself onto the floor or into others question', 575);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3331, 2, 'never', 'never answer', 5750);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3332, 2, 'sometimes', 'sometimes answer', 5751);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3333, 2, 'often', 'often answer', 5752);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3334, 2, 'usually', 'usually answer', 5753);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3335, 2, 'always', 'always answer', 5754);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3340, 1, 'Cut, bruise, or cause minor burns to myself', 'Cut, bruise, or cause minor burns to myself question', 576);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3341, 2, 'never', 'never answer', 5760);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3342, 2, 'sometimes', 'sometimes answer', 5761);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3343, 2, 'often', 'often answer', 5762);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3344, 2, 'usually', 'usually answer', 5763);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3345, 2, 'always', 'always answer', 5764);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3350, 1, 'Hurt Myself', 'Hurt Myself question', 577);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3351, 2, 'never', 'never answer', 5770);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3352, 2, 'sometimes', 'sometimes answer', 5771);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3353, 2, 'often', 'often answer', 5772);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3354, 2, 'usually', 'usually answer', 5773);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3355, 2, 'always', 'always answer', 5774);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (3389, 4, 'ROAS_weighted_score', 'ROAS_weighted_score', '([3200] + [3210] + [3220] + [3230] + [3240] + [3250] + [3260] + [3270] + [3280] + [3290] + [3300] + [3310] + [3320] + [3330] + [3340] + [3350])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (70, 3389, 3200);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (71, 3389, 3210);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (72, 3389, 3220);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (73, 3389, 3230);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (74, 3389, 3240);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (75, 3389, 3250);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (76, 3389, 3260);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (77, 3389, 3270);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (78, 3389, 3280);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (79, 3389, 3290);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (80, 3389, 3300);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (81, 3389, 3310);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (82, 3389, 3320);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (83, 3389, 3330);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (84, 3389, 3340);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (85, 3389, 3350);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3200, 3200, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3201, 3201, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3202, 3202, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3203, 3203, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3204, 3204, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3205, 3205, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3210, 3210, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3211, 3211, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3212, 3212, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3213, 3213, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3214, 3214, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3215, 3215, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3220, 3220, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3221, 3221, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3222, 3222, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3223, 3223, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3224, 3224, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3225, 3225, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3230, 3230, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3231, 3231, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3232, 3232, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3233, 3233, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3234, 3234, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3235, 3235, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3240, 3240, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3241, 3241, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3242, 3242, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3243, 3243, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3244, 3244, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3245, 3245, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3250, 3250, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3251, 3251, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3252, 3252, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3253, 3253, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3254, 3254, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3255, 3255, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3260, 3260, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3261, 3261, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3262, 3262, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3263, 3263, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3264, 3264, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3265, 3265, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3270, 3270, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3271, 3271, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3272, 3272, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3273, 3273, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3274, 3274, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3275, 3275, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3280, 3280, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3281, 3281, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3282, 3282, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3283, 3283, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3284, 3284, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3285, 3285, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3290, 3290, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3291, 3291, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3292, 3292, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3293, 3293, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3294, 3294, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3295, 3295, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3300, 3300, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3301, 3301, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3302, 3302, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3303, 3303, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3304, 3304, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3305, 3305, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3310, 3310, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3311, 3311, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3312, 3312, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3313, 3313, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3314, 3314, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3315, 3315, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3320, 3320, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3321, 3321, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3322, 3322, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3323, 3323, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3324, 3324, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3325, 3325, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3330, 3330, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3331, 3331, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3332, 3332, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3333, 3333, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3334, 3334, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3335, 3335, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3340, 3340, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3341, 3341, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3342, 3342, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3343, 3343, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3344, 3344, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3345, 3345, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3350, 3350, 38);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3351, 3351, 38, 'never');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3352, 3352, 38, 'sometimes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3353, 3353, 38, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3354, 3354, 38, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3355, 3355, 38, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3389, 3389, 38);


/* BTBIS */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3400, 1, 'During any of your OEF/OIF deployments did you experience any of the following events', 'During any of your OEF/OIF deployments did you experience any of the following events question', 412);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2010, 2, 'OEF/OIF events - Blast', 'question -  Blast or explosion (IED, RPG, Land Mine, Grenade, etc.)', 4131);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2011, 2, 'OEF/OIF events - Vehicular', 'question - Vehicular accident/crash (any vehicle including aircraft)', 4132);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2012, 2, 'OEF/OIF events - Fragment', 'question - Fragment wound or bullet wound above the shoulders', 4133);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2013, 2, 'OEF/OIF events - Fall', 'question - Fall ', 4134);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2014, 2, 'OEF/OIF events - Blow to head', 'question -  Blow to head ', 4135);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2015, 2, 'OEF/OIF events - Other', 'question - Other', 4136);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2016, 2, 'OEF/OIF events - None', 'question - None of the above', 4130);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3410, 1, 'Did you have any of these symptoms IMMEDIATELY afterwards', 'Did you have any of these symptoms IMMEDIATELY afterwards question', 413);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2017, 2, 'Symptoms IMMEDIATELY afterwards - Loss', 'question -  loss', 4156);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2018, 2, 'Symptoms IMMEDIATELY afterwards - Dazed', 'question -  dazed', 4157);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2019, 2, 'Symptoms IMMEDIATELY afterwards - Memory', 'question - memory ', 4158);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2020, 2, 'Symptoms IMMEDIATELY afterwards - Concussion', 'question -  concussion', 4159);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2021, 2, 'Symptoms IMMEDIATELY afterwards - Head injury', 'question -  Head injury', 4160);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2022, 2, 'Symptoms IMMEDIATELY afterwards - None', 'question - None of the above', 4155);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3420, 1, 'Did any of the following problems begin or get worse afterwards', 'Did any of the following problems begin or get worse afterwards question', 414);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2023, 2, 'Problems begin or get worse afterwards - memory', 'question - Memory problems or lapses', 4181);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2024, 2, 'Problems begin or get worse afterwards - balance or dizziness', 'question - Balance problems or dizziness', 4182);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2025, 2, 'Problems begin or get worse afterwards - sensitivity to light', 'question - Sensitivity to bright light', 4183);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2027, 2, 'Problems begin or get worse afterwards - irritability', 'question - Irritability', 4184);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2028, 2, 'Problems begin or get worse afterwards - headaches', 'question - Headaches', 4185);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2029, 2, 'Problems begin or get worse afterwards - sleep problems', 'question - Sleep problems', 4186);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2030, 2, 'Problems begin or get worse afterwards - none', 'question - None', 4180);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3430, 1, 'In the past 1 week have you had any of the problems from the question above', 'In the past 1 week have you had any of the problems from the question above question', 415);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2031, 2, 'Problems from past week for question above - memory', 'question - Memory problems or lapses', 4207);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2032, 2, 'Problems from past week for question above - balance', 'question - Balance problems or dizziness', 4208);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2033, 2, 'Problems from past week for question above - sensitivity to light', 'question - Sensitivity to bright light', 4209);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2034, 2, 'Problems from past week for question above - irritability', 'question - Irritability', 4210);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2035, 2, 'Problems from past week for question above - headaches', 'question - Headaches', 4211);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2036, 2, 'Problems from past week for question above - sleep problems', 'question - Sleep problems', 4212);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (2037, 2, 'Problems from past week for question above - none', 'question - None', 4206);



INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2047, 1, 'If you screen positive for TBI, you want consult', 'TBI - does the veteran want a consult', 443);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3441, 2, 'No, I decline a consult at this time', 'No, I decline a consult at this time answer', 4430);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3442, 2, 'Yes, place a consult', 'Yes, place a consult answer', 4431);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3450, 1, 'Where were you deployed when the head injury occurred', 'Where were you deployed when the head injury occurred question', 444);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3451, 2, '', 'Where were you deployed when the head injury occurred answer', 4440);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3460, 1, 'What year did it occur', 'What year did it occur question', 445);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3461, 2, '', 'What year did it occur answer', 4450);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3470, 1, 'How did the head injury occur', 'How did the head injury occur question', 446);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3471, 2, '', 'How did the head injury occur answer', 4460);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (3489, 4, 'TBI formula', 'TBI formula', '([3400] + [3410] + [3420] + [3430])');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (580, 3489, 3400);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (581, 3489, 3410);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (582, 3489, 3420);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (583, 3489, 3430);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3400, 3400, 30);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2016, 2016, 30, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2010, 2010, 30, 'Blast or explosion (IED, RPG, Land Mine, Grenade, etc.)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2011, 2011, 30, 'Vehicular accident/crash (any vehicle including aircraft)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2012, 2012, 30, 'Fragment wound or bullet wound above the shoulders');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2013, 2013, 30, 'Fall');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2014, 2014, 30, 'Blow to head (hit by falling/flying object, head hit by another person, head hit against something, etc.)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2015, 2015, 30, 'Other injury to head');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3410, 3410, 30);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2022, 2022, 30, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2017, 2017, 30, 'Loss of consciousness\"knocked out\"');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2018, 2018, 30, 'Being dazed, confused or \"seeing stars\"');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2019, 2019, 30, 'Not remembering the event');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2020, 2020, 30, 'Concussion');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2021, 2021, 30, 'Head injury');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3420, 3420, 30);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2030, 2030, 30, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2023, 2023, 30, 'Memory problems or lapses');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2024, 2024, 30, 'Balance problems or dizziness');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2025, 2025, 30, 'Sensitivity to bright light');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2027, 2027, 30, 'Irritability');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2028, 2028, 30, 'Headaches');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2029, 2029, 30, 'Sleep problems');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3430, 3430, 30);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2037, 2037, 30, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2031, 2031, 30, 'Memory problems or lapses');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2032, 2032, 30, 'Balance problems or dizziness');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2033, 2033, 30, 'Sensitivity to bright light');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2034, 2034, 30, 'Irritability');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2035, 2035, 30, 'Headaches');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (2036, 2036, 30, 'Sleep problems');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (2047, 2047, 30);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3441, 3441, 30, 'did not agree');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3442, 3442, 30, 'agreed');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3450, 3450, 30);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3451, 3451, 30, 'Where were you deployed when the head injury occurred');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3460, 3460, 30);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3461, 3461, 30, 'What year did it occur');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3470, 3470, 30);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3471, 3471, 30, 'How did the head injury occur');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3489, 3489, 30);

/* Medications */

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3500, 1, 'Please list all current prescribed and over-the-counter medications, and nutrition supplements', 'Please list all current prescribed and over-the-counter medications, and nutrition supplements question', 230);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3501, 2, 'Medication', 'Medication answer', 2300);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3510, 1, 'med', 'med question', 231);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3511, 2, 'med', 'med answer', 2310);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3520, 1, 'med_dose', 'med_dose question', 232);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3521, 2, 'med_dose', 'med_dose answer', 2320);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3530, 1, 'med_freq', 'med_freq question', 233);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3531, 2, 'med_freq', 'med_freq answer', 2330);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3540, 1, 'med_dur', 'med_dur question', 234);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3541, 2, 'med_dur', 'med_dur answer', 2340);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (3550, 1, 'med_doc', 'med_doc question', 235);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (3551, 2, 'med_doc', 'med_doc answer', 2350);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3500, 3500, 22);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3501, 3501, 22, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3510, 3510, 22);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3511, 3511, 22, 'Medication/Supplement');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3520, 3520, 22);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3521, 3521, 22, 'Dosage');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3530, 3530, 22);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3531, 3531, 22, 'Frequency');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3540, 3540, 22);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3541, 3541, 22, 'Duration');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (3550, 3550, 22);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (3551, 3551, 22, 'Doctor and/or Facility');





/** WHODAS2.0 **/


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4000, 1, 'Concentrating on doing something for ten minutes', 'whodas1_1_concentrate', 252);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4001, 2, 'none', 'none answer', 2520);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4002, 2, 'mild', 'mild answer', 2521);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4003, 2, 'moderate', 'moderate answer', 2522);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4004, 2, 'severe', 'severe answer', 2523);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4005, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2524);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4020, 1, 'Remembering to do important things', 'whodas1_2_remember', 253);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4021, 2, 'none', 'none answer', 2530);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4022, 2, 'mild', 'mild answer', 2531);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4023, 2, 'moderate', 'moderate answer', 2532);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4024, 2, 'severe', 'severe answer', 2533);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4025, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2534);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4040, 1, 'Analyzing and finding solutions to problems in day-to-day life', 'whodas1_3_solution', 254);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4041, 2, 'none', 'none answer', 2540);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4042, 2, 'mild', 'mild answer', 2541);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4043, 2, 'moderate', 'moderate answer', 2542);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4044, 2, 'severe', 'severe answer', 2543);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4045, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2544);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4060, 1, 'Learning a new task, for example, learning how to get to a new place', 'whodas1_4_new', 255);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4061, 2, 'none', 'none answer', 2550);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4062, 2, 'mild', 'mild answer', 2551);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4063, 2, 'moderate', 'moderate answer', 2552);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4064, 2, 'severe', 'severe answer', 2553);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4065, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2554);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4080, 1, 'Generally understanding what people say', 'whodas1_5_understand', 256);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4081, 2, 'none', 'none answer', 2560);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4082, 2, 'mild', 'mild answer', 2561);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4083, 2, 'moderate', 'moderate answer', 2562);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4084, 2, 'severe', 'severe answer', 2563);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4085, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2564);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4100, 1, 'Starting and maintaining a conversation', 'whodas1_6_conversation', 257);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4101, 2, 'none', 'none answer', 2570);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4102, 2, 'mild', 'mild answer', 2571);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4103, 2, 'moderate', 'moderate answer', 2572);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4104, 2, 'severe', 'severe answer', 2573);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4105, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2574);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (4119, 4, 'whodas_understand_mean', 'whodas_understand_mean', '(([4000] + [4020] + [4040] + [4060] + [4080] + [4100])/6)');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (150, 4119, 4000);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (151, 4119, 4020);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (152, 4119, 4040);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (153, 4119, 4060);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (154, 4119, 4080);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (155, 4119, 4100);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4120, 1, 'Standing for long periods, such as 30 minutes', 'whodas2_1_stand', 262);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4121, 2, 'none', 'none answer', 2620);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4122, 2, 'mild', 'mild answer', 2621);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4123, 2, 'moderate', 'moderate answer', 2622);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4124, 2, 'severe', 'severe answer', 2623);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4125, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2624);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4140, 1, 'Standing up from sitting down', 'whodas2_2_standup', 263);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4141, 2, 'none', 'none answer', 2630);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4142, 2, 'mild', 'mild answer', 2631);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4143, 2, 'moderate', 'moderate answer', 2632);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4144, 2, 'severe', 'severe answer', 2633);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4145, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2634);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4160, 1, 'Moving around inside your home', 'whodas2_3_move', 264);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4161, 2, 'none', 'none answer', 2640);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4162, 2, 'mild', 'mild answer', 2641);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4163, 2, 'moderate', 'moderate answer', 2642);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4164, 2, 'severe', 'severe answer', 2643);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4165, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2644);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4180, 1, 'Getting out of your home', 'whodas2_4_getout', 265);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4181, 2, 'none', 'none answer', 2650);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4182, 2, 'mild', 'mild answer', 2651);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4183, 2, 'moderate', 'moderate answer', 2652);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4184, 2, 'severe', 'severe answer', 2653);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4185, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2654);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4220, 1, 'Walking a long distance, such as a kilometer (or equivalent)', 'whodas2_5_walk', 266);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4221, 2, 'none', 'none answer', 2660);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4222, 2, 'mild', 'mild answer', 2661);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4223, 2, 'moderate', 'moderate answer', 2662);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4224, 2, 'severe', 'severe answer', 2663);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4225, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2664);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (4239, 4, 'whodas_mobility_mean', 'whodas_mobility_mean', '(([4120] + [4140] + [4160] + [4180] + [4220])/5)');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (180, 4239, 4120);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (181, 4239, 4140);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (182, 4239, 4160);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (183, 4239, 4180);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (185, 4239, 4220);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4240, 1, 'Washing your whole body', 'whodas3_1_wash', 272);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4241, 2, 'none', 'none answer', 2720);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4242, 2, 'mild', 'mild answer', 2721);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4243, 2, 'moderate', 'moderate answer', 2722);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4244, 2, 'severe', 'severe answer', 2723);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4245, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2724);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4260, 1, 'Getting dressed', 'whodas3_2_dressed', 273);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4261, 2, 'none', 'none answer', 2730);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4262, 2, 'mild', 'mild answer', 2731);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4263, 2, 'moderate', 'moderate answer', 2732);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4264, 2, 'severe', 'severe answer', 2733);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4265, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2734);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4280, 1, 'Eating', 'whodas3_3_eat', 274);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4281, 2, 'none', 'none answer', 2740);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4282, 2, 'mild', 'mild answer', 2741);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4283, 2, 'moderate', 'moderate answer', 2742);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4284, 2, 'severe', 'severe answer', 2743);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4285, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2744);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4300, 1, 'Staying by yourself for a few days', 'whodas3_4_stay', 275);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4301, 2, 'none', 'none answer', 2750);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4302, 2, 'mild', 'mild answer', 2751);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4303, 2, 'moderate', 'moderate answer', 2752);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4304, 2, 'severe', 'severe answer', 2753);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4305, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2754);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (4319, 4, 'whodas_selfcare_mean', 'whodas_selfcare_mean', '(([4240] + [4260] + [4280] + [4300])/4)');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (200, 4319, 4240);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (201, 4319, 4260);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (202, 4319, 4280);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (203, 4319, 4300);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4320, 1, 'Dealing with people you do not know', 'whodas4_1_deal', 282);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4321, 2, 'none', 'none answer', 2820);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4322, 2, 'mild', 'mild answer', 2821);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4323, 2, 'moderate', 'moderate answer', 2822);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4324, 2, 'severe', 'severe answer', 2823);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4325, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2824);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4340, 1, 'Maintaining a friendship', 'whodas4_2_friend', 283);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4341, 2, 'none', 'none answer', 2830);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4342, 2, 'mild', 'mild answer', 2831);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4343, 2, 'moderate', 'moderate answer', 2832);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4344, 2, 'severe', 'severe answer', 2833);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4345, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2834);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4360, 1, 'Getting along with people who are close to you', 'whodas4_3_getalong', 284);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4361, 2, 'none', 'none answer', 2840);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4362, 2, 'mild', 'mild answer', 2841);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4363, 2, 'moderate', 'moderate answer', 2842);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4364, 2, 'severe', 'severe answer', 2843);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4365, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2844);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4380, 1, 'Making new friends', 'whodas4_4_newfriend', 285);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4381, 2, 'none', 'none answer', 2850);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4382, 2, 'mild', 'mild answer', 2851);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4383, 2, 'moderate', 'moderate answer', 2852);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4384, 2, 'severe', 'severe answer', 2853);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4385, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2854);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4400, 1, 'Sexual activities', 'whodas4_5_sexual', 286);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4401, 2, 'none', 'none answer', 2860);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4402, 2, 'mild', 'mild answer', 2861);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4403, 2, 'moderate', 'moderate answer', 2862);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4404, 2, 'severe', 'severe answer', 2863);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4405, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2864);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (4419, 4, 'whodas_people_mean', 'whodas_people_mean', '(([4320] + [4340] + [4360] + [4380] + [4400])/5)');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (220, 4419, 4320);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (221, 4419, 4340);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (222, 4419, 4360);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (223, 4419, 4380);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (224, 4419, 4400);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4420, 1, 'Taking care of your household responsibilities', 'whodas5_1_housecare', 292);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4421, 2, 'none', 'none answer', 2920);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4422, 2, 'mild', 'mild answer', 2921);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4423, 2, 'moderate', 'moderate answer', 2922);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4424, 2, 'severe', 'severe answer', 2923);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4425, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2924);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4430, 1, 'Taking care of your household responsibilities', 'whodas5_1_housecare', 293);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4431, 2, 'none', 'none answer', 2930);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4432, 2, 'mild', 'mild answer', 2931);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4433, 2, 'moderate', 'moderate answer', 2932);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4434, 2, 'severe', 'severe answer', 2933);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4435, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2934);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4440, 1, 'Getting all of the household work done that you needed to do', 'whodas5_3_housedone', 294);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4441, 2, 'none', 'none answer', 2940);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4442, 2, 'mild', 'mild answer', 2941);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4443, 2, 'moderate', 'moderate answer', 2942);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4444, 2, 'severe', 'severe answer', 2943);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4445, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2944);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4460, 1, 'Getting your household work done as quickly as needed', 'whodas5_4_housequickly', 295);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4461, 2, 'none', 'none answer', 2950);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4462, 2, 'mild', 'mild answer', 2951);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4463, 2, 'moderate', 'moderate answer', 2952);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4464, 2, 'severe', 'severe answer', 2953);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4465, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 2954);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (4499, 4, 'whodas_household_mean', 'whodas_household_mean', '(([4420] + [4430] + [4440] + [4460])/4)');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (240, 4499, 4420);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (241, 4499, 4430);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (242, 4499, 4440);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (243, 4499, 4460);



 
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4200, 1, 'Do you work (paid, non-paid, self-employed) or go to school', 'whodas_work', 300);  
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4201, 2, 'No', 'No answer', 3000);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4202, 2, 'Yes','Yes answer', 3001);

	

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4480, 1, 'Your day-to-day work/school', 'whodas5_5_daily', 303);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4481, 2, 'none', 'none answer', 3030);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4482, 2, 'mild', 'mild answer', 3031);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4483, 2, 'moderate', 'moderate answer', 3032);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4484, 2, 'severe', 'severe answer', 3033);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4485, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3034);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4500, 1, 'Doing your most important work/school tasks well', 'whodas5_6_workwell', 304);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4501, 2, 'none', 'none answer', 3040);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4502, 2, 'mild', 'mild answer', 3041);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4503, 2, 'moderate', 'moderate answer', 3042);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4504, 2, 'severe', 'severe answer', 3043);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4505, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3044);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4520, 1, 'Getting all of the work done that you need to do', 'whodas5_7_workdone', 305);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4521, 2, 'none', 'none answer', 3050);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4522, 2, 'mild', 'mild answer', 3051);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4523, 2, 'moderate', 'moderate answer', 3052);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4524, 2, 'severe', 'severe answer', 3053);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4525, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3054);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4540, 1, 'Getting your work done as quickly as needed', 'whodas5_8_workquickly', 306);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4541, 2, 'none', 'none answer', 3060);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4542, 2, 'mild', 'mild answer', 3061);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4543, 2, 'moderate', 'moderate answer', 3062);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4544, 2, 'severe', 'severe answer', 3063);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4545, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3064);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (4559, 4, 'whodas_work_score', 'whodas_work_score', '(([4480] + [4500] + [4520] + [4540])/4)');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (260, 4559, 4480);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (261, 4559, 4500);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (262, 4559, 4520);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (263, 4559, 4540);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4560, 1, 'How much of a problem did you have in joining in community activities in the same way as anyone else can', 'whodas6_1_community', 312);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4561, 2, 'none', 'none answer', 3120);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4562, 2, 'mild', 'mild answer', 3121);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4563, 2, 'moderate', 'moderate answer', 3122);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4564, 2, 'severe', 'severe answer', 3123);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4565, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3124);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4580, 1, 'How much of a problem did you have because of barriers or hindrances around you', 'whodas6_2_barriers', 313);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4581, 2, 'none', 'none answer', 3130);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4582, 2, 'mild', 'mild answer', 3131);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4583, 2, 'moderate', 'moderate answer', 3132);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4584, 2, 'severe', 'severe answer', 3133);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4585, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3134);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4600, 1, 'How much of a problem did you have living with dignity because of the attitudes and actions of others', 'whodas6_3_dignity', 314);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4601, 2, 'none', 'none answer', 3140);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4602, 2, 'mild', 'mild answer', 3141);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4603, 2, 'moderate', 'moderate answer', 3142);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4604, 2, 'severe', 'severe answer', 3143);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4605, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3144);



INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4620, 1, 'How much time did you spend on your health condition or its consequences', 'whodas6_4_time', 315);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4621, 2, 'none', 'none answer', 3150);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4622, 2, 'mild', 'mild answer', 3151);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4623, 2, 'moderate', 'moderate answer', 3152);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4624, 2, 'severe', 'severe answer', 3153);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4625, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3154);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4640, 1, 'How much have you been emotionally affected by your health condition', 'whodas6_5_emotion', 316);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4641, 2, 'none', 'none answer', 3160);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4642, 2, 'mild', 'mild answer', 3161);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4643, 2, 'moderate', 'moderate answer', 3162);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4644, 2, 'severe', 'severe answer', 3163);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4645, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3164);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4660, 1, 'How much has your health been a drain on the financial resources of you or your family', 'whodas6_6_finance', 317);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4661, 2, 'none', 'none answer', 3170);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4662, 2, 'mild', 'mild answer', 3171);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4663, 2, 'moderate', 'moderate answer', 3172);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4664, 2, 'severe', 'severe answer', 3173);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4665, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3174);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4680, 1, 'How much of a problem did your family have because of your health problems', 'whodas6_7_family', 318);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4681, 2, 'none', 'none answer', 3180);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4682, 2, 'mild', 'mild answer', 3181);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4683, 2, 'moderate', 'moderate answer', 3182);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4684, 2, 'severe', 'severe answer', 3183);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4685, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3184);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (4700, 1, 'How much of a problem did you have in doing things by yourself for relaxation or pleasure', 'whodas6_8_relax', 319);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4701, 2, 'none', 'none answer', 3190);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4702, 2, 'mild', 'mild answer', 3191);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4703, 2, 'moderate', 'moderate answer', 3192);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4704, 2, 'severe', 'severe answer', 3193);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (4705, 2, 'Extreme or cannot do', 'Extreme or cannot do answer', 3194);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, formula_template) VALUES (4789, 4, 'whodas_society_mean', 'whodas_society_mean', '(([4560] + [4580] + [4600] + [4620] + [4640] + [4660] + [4680] + [4700])/8)');
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (280, 4789, 4560);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (281, 4789, 4580);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (282, 4789, 4600);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (283, 4789, 4620);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (284, 4789, 4640);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (285, 4789, 4660);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (286, 4789, 4680);
INSERT INTO assessment_var_children(assessment_var_children_id, variable_parent, variable_child) VALUES (287, 4789, 4700);





INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4000, 4000, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4001, 4001, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4002, 4002, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4003, 4003, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4004, 4004, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4005, 4005, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4020, 4020, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4021, 4021, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4022, 4022, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4023, 4023, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4024, 4024, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4025, 4025, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4040, 4040, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4041, 4041, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4042, 4042, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4043, 4043, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4044, 4044, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4045, 4045, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4060, 4060, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4061, 4061, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4062, 4062, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4063, 4063, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4064, 4064, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4065, 4065, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4080, 4080, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4081, 4081, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4082, 4082, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4083, 4083, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4084, 4084, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4085, 4085, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4100, 4100, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4101, 4101, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4102, 4102, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4103, 4103, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4104, 4104, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4105, 4105, 24, 'extreme or cannot do');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4119, 4119, 24);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4120, 4120, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4121, 4121, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4122, 4122, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4123, 4123, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4124, 4124, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4125, 4125, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4140, 4140, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4141, 4141, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4142, 4142, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4143, 4143, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4144, 4144, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4145, 4145, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4160, 4160, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4161, 4161, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4162, 4162, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4163, 4163, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4164, 4164, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4165, 4165, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4180, 4180, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4181, 4181, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4182, 4182, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4183, 4183, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4184, 4184, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4185, 4185, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4200, 4200, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4201, 4201, 24, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4202, 4202, 24, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4220, 4220, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4221, 4221, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4222, 4222, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4223, 4223, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4224, 4224, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4225, 4225, 24, 'extreme or cannot do');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4239, 4239, 24);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4240, 4240, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4241, 4241, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4242, 4242, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4243, 4243, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4244, 4244, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4245, 4245, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4260, 4260, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4261, 4261, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4262, 4262, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4263, 4263, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4264, 4264, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4265, 4265, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4280, 4280, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4281, 4281, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4282, 4282, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4283, 4283, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4284, 4284, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4285, 4285, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4300, 4300, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4301, 4301, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4302, 4302, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4303, 4303, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4304, 4304, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4305, 4305, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4319, 4319, 24);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4320, 4320, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4321, 4321, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4322, 4322, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4323, 4323, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4324, 4324, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4325, 4325, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4340, 4340, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4341, 4341, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4342, 4342, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4343, 4343, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4344, 4344, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4345, 4345, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4360, 4360, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4361, 4361, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4362, 4362, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4363, 4363, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4364, 4364, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4365, 4365, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4380, 4380, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4381, 4381, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4382, 4382, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4383, 4383, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4384, 4384, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4385, 4385, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4400, 4400, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4401, 4401, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4402, 4402, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4403, 4403, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4404, 4404, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4405, 4405, 24, 'extreme or cannot do');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4419, 4419, 24);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4420, 4420, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4421, 4421, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4422, 4422, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4423, 4423, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4424, 4424, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4425, 4425, 24, 'extreme or cannot do');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4430, 4430, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4431, 4431, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4432, 4432, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4433, 4433, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4434, 4434, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4435, 4435, 24, 'extreme or cannot do');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4440, 4440, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4441, 4441, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4442, 4442, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4443, 4443, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4444, 4444, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4445, 4445, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4460, 4460, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4461, 4461, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4462, 4462, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4463, 4463, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4464, 4464, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4465, 4465, 24, 'extreme or cannot do');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4499, 4499, 24);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4480, 4480, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4481, 4481, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4482, 4482, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4483, 4483, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4484, 4484, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4485, 4485, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4500, 4500, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4501, 4501, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4502, 4502, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4503, 4503, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4504, 4504, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4505, 4505, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4520, 4520, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4521, 4521, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4522, 4522, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4523, 4523, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4524, 4524, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4525, 4525, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4540, 4540, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4541, 4541, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4542, 4542, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4543, 4543, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4544, 4544, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4545, 4545, 24, 'extreme or cannot do');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4559, 4559, 24);



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4560, 4560, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4561, 4561, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4562, 4562, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4563, 4563, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4564, 4564, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4565, 4565, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4580, 4580, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4581, 4581, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4582, 4582, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4583, 4583, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4584, 4584, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4585, 4585, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4600, 4600, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4601, 4601, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4602, 4602, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4603, 4603, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4604, 4604, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4605, 4605, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4620, 4620, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4621, 4621, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4622, 4622, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4623, 4623, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4624, 4624, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4625, 4625, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4640, 4640, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4641, 4641, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4642, 4642, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4643, 4643, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4644, 4644, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4645, 4645, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4660, 4660, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4661, 4661, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4662, 4662, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4663, 4663, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4664, 4664, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4665, 4665, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4680, 4680, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4681, 4681, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4682, 4682, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4683, 4683, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4684, 4684, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4685, 4685, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4700, 4700, 24);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4701, 4701, 24, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4702, 4702, 24, 'mild');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4703, 4703, 24, 'moderate');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4704, 4704, 24, 'severe');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (4705, 4705, 24, 'extreme or cannot do');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (4789, 4789, 24);


/* Military Deployments & History */


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (5000, 1, 'Did you receive any of the following disciplinary actions during your time in the service', 'Did you receive any of the following disciplinary actions during your time in the service question', 116);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5001, 2, 'none', 'serv_discipline_none', 1160);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5002, 2, 'Formal Counseling (e.g. Captain\'s Mast)', 'serv_discipline_couns', 1161);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5003, 2, 'Article 15 (Non-Judicial Punishment)', 'serv_discipline_art15', 1162);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5004, 2, 'Court Martial', 'serv_discipline_martial', 1163);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5005, 2, 'Other, please specify', 'serv_discipline_other', 1164);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (5020, 1, 'Did you receive any of the following personal awards or commendations', 'Did you receive any of the following personal awards or commendations', 117);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5021, 2, 'none', 'serv_award_none', 1170);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5022, 2, 'Medal of Honor', 'serv_award_honor', 1171);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5023, 2, 'Distinguished Service Cross', 'serv_award_distservcross', 1172);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5024, 2, 'Distinguished Service Medal', 'serv_award_distservmedal', 1173);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5025, 2, 'Meritorious Service Medal', 'serv_award_meritservmedal', 1174);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5026, 2, 'Legion of Merit', 'serv_award_legionmerit', 1175);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5027, 2, 'Bronze Star', 'serv_award_bronzstar', 1177);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5028, 2, 'Purple Heart', 'serv_award_purpheart', 1178);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5029, 2, 'Air Medal', 'serv_award_airmedal', 1179);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5030, 2, 'Silver Star', 'serv_award_silvstar', 1180);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5031, 2, 'Soldier\'s Medal', 'serv_award_soldiermedal', 1181);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5032, 2, 'Achievement Medal', 'serv_award_achievmedal', 1182);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5033, 2, 'Commendation Medal', 'serv_award_commedal', 1183);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5034, 2, 'Other, please specify', 'serv_award_other', 1184);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (5040, 1, 'serv_deploy_loc', 'Combat deployment location', 120);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5041, 2, 'serv_deploy_loc', 'freefrom text answer', 1210);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (5060, 1, 'serv_deploy_from_mo', 'serv_deploy_from_mo', 121);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5061, 2, 'serv_deploy_from_mo', '01', 1211);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5062, 2, 'serv_deploy_from_mo', '02', 1212);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5063, 2, 'serv_deploy_from_mo', '03', 1213);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5064, 2, 'serv_deploy_from_mo', '04', 1214);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5065, 2, 'serv_deploy_from_mo', '05', 1215);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5066, 2, 'serv_deploy_from_mo', '06', 1216);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5067, 2, 'serv_deploy_from_mo', '07', 1217);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5068, 2, 'serv_deploy_from_mo', '08', 1218);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5069, 2, 'serv_deploy_from_mo', '09', 1219);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5070, 2, 'serv_deploy_from_mo', '10', 1220);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5071, 2, 'serv_deploy_from_mo', '11', 1221);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5072, 2, 'serv_deploy_from_mo', '12', 1222);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (5080, 1, 'Start of combat deployment year (yyyy)', 'serv_deploy_from_yr', 122);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5081, 2, 'serv_deploy_from_yr', 'freefrom text answer', 1223);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (5100, 1, 'serv_deploy_to_mo', 'serv_deploy_to_mo', 123);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5101, 2, 'serv_deploy_to_mo', '01', 1224);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5102, 2, 'serv_deploy_to_mo', '02', 1225);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5103, 2, 'serv_deploy_to_mo', '03', 1226);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5104, 2, 'serv_deploy_to_mo', '04', 1227);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5105, 2, 'serv_deploy_to_mo', '05', 1228);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5106, 2, 'serv_deploy_to_mo', '06', 1229);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5107, 2, 'serv_deploy_to_mo', '07', 1230);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5108, 2, 'serv_deploy_to_mo', '08', 1231);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5109, 2, 'serv_deploy_to_mo', '09', 1232);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5110, 2, 'serv_deploy_to_mo', '10', 1233);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5111, 2, 'serv_deploy_to_mo', '11', 1234);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5112, 2, 'serv_deploy_to_mo', '12', 1235);


INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (5120, 1, 'End of combat deployment year (yyyy)', 'serv_deploy_to_yr', 124);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5121, 2, 'serv_deploy_to_yr', 'serv_deploy_to_yr', 1240);

INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (5130, 1, 'Please add one entry for each overseas deployment in support of a combat operation for more than 30 days', 'Please add one entry for each overseas deployment in support of a combat operation for more than 30 days', 119);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (5131, 2, 'deployments', 'deployments answer', 1200);


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (5000, 5000, 14);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5001, 5001, 14, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5002, 5002, 14, 'formal counseling ');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5003, 5003, 14, 'article 15');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5004, 5004, 14, 'court martial');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5005, 5005, 14, 'other');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (5020, 5020, 14);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5021, 5021, 14, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5022, 5022, 14, 'Medal of Honor');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5023, 5023, 14, 'Distinguished Service Cross');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5024, 5024, 14, 'Distinguished Service Medal');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5025, 5025, 14, 'Meritorious Service Medal');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5026, 5026, 14, 'Legion of Merit');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5027, 5027, 14, 'Bronze Star');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5028, 5028, 14, 'Purple Heart');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5029, 5029, 14, 'Air Medal');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5030, 5030, 14, 'Silver Star');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5031, 5031, 14, 'Soldier\'s Medal');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5032, 5032, 14, 'Achievement Medal');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5033, 5033, 14, 'Commendation Medal');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5034, 5034, 14, 'other');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (5040, 5040, 14);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5041, 5041, 14, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (5060, 5060, 14);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5061, 5061, 14, '01');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5062, 5062, 14, '02');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5063, 5063, 14, '03');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5064, 5064, 14, '04');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5065, 5065, 14, '05');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5066, 5066, 14, '06');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5067, 5067, 14, '07');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5068, 5068, 14, '08');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5069, 5069, 14, '09');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5070, 5070, 14, '10');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5071, 5071, 14, '11');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5072, 5072, 14, '12');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (5080, 5080, 14);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5081, 5081, 14, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (5100, 5100, 14);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5101, 5101, 14, '01');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5102, 5102, 14, '02');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5103, 5103, 14, '03');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5104, 5104, 14, '04');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5105, 5105, 14, '05');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5106, 5106, 14, '06');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5107, 5107, 14, '07');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5108, 5108, 14, '08');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5109, 5109, 14, '09');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5110, 5110, 14, '10');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5111, 5111, 14, '11');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5112, 5112, 14, '12');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (5120, 5120, 14);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5121, 5121, 14, '');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (5130, 5130, 14);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (5131, 5131, 14, '');






/* Footer - INTERVENTIONS */

/*

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9000, 1599, 2); 


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9020, 1989, 2); 


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9030, 1929, 2);
*/



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9050, 200, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9052, 202, 2, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9060, 210, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9063, 212, 2, '');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9066, 215, 2, '');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9080, 60, 2);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9086, 65, 2, 'unemployed');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9090, 430, 2);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9092, 432, 2, 'yes');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9100, 1010, 2); 

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9110, 1229, 2); 

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9120, 600, 2);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9122, 603, 2, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9130, 2047, 2);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9132, 3442, 2, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9160, 2809, 2); 

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9140, 2790, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9142, 2792, 2, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9150, 2800, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9153, 2803, 2, 'moderate problem');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9154, 2804, 2, 'serious problem');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9170, 1640, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9172, 1642, 2, 'would');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9180, 2189, 2); 

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9200, 3230, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9203, 3233, 2, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9204, 3234, 2, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9205, 3235, 2, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9210, 3310, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9213, 3313, 2, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9214, 3314, 2, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9215, 3315, 2, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9220, 3350, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9223, 3353, 2, 'often');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9224, 3354, 2, 'usually');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9225, 3355, 2, 'always');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9230, 80, 2);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9231, 81, 2, 'no income');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9240, 220, 2);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9241, 223, 2, 'unemployment benefits');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9250, 240, 2);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9251, 242, 2, 'homeless');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9260, 2001, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9262, 772, 2, 'is');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9270, 400, 2);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9272, 402, 2, 'yes');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9280, 260, 2);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9282, 262, 2, 'Financial Information about VA or community resources');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9300, 300, 2);	
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9301, 301, 2, 'civil issues');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9302, 302, 2, 'child support');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9303, 303, 2, 'taxes');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9304, 304, 2, 'bankruptcy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9305, 305, 2, 'outstanding tickets');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9306, 306, 2, 'arrest warrants');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9307, 307, 2, 'restraining orders');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9308, 308, 2, 'DUIs');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9309, 309, 2, 'probation');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9310, 310, 2, 'parole');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9311, 311, 2, 'JAG');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9312, 312, 2, 'child protective services');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9320, 250, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9323, 253, 2, 'GI Bill');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9330, 220, 2);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9333, 223, 2, 'unemployment benefits');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9340, 210, 2);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9346, 216, 2, 'visual impairment services team');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9350, 1430, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9352, 1432, 2, 'combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9360, 1430, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9363, 1433, 2, 'non-combat');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9370, 940, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9372, 942, 2, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9373, 943, 2, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9380, 930, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9382, 932, 2, 'bothered a little');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9383, 933, 2, 'bothered a lot');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9390, 250, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9392, 252, 2, 'VA compensation');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9400, 1510, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9403, 1513, 2, 'no, but intend to file/in progress');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9410, 250, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9412, 252, 2, 'VA compensation');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9420, 820, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9421, 821, 2, 'not having');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9422, 822, 2, 'having');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (9430, 826, 2);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9431, 827, 2, 'would not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (9432, 828, 2, 'would');	



/* Score - SPECIAL Scoring matrix */

	/* AUDIT C*/
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10000, 1229, 100);

	/* TBI */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10005, 2047, 100);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10006, 3441, 100, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10007, 3442, 100, 'yes');

	/* DAST 10 */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10010, 1010, 100);

	/* GAD 7 */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10015, 1749, 100);

	/* HOUSING - HOMLESSNESS */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10016, 2008, 100);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10017, 791, 100, 'would not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10018, 792, 100, 'would');

	/* ISI */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10020, 2189, 100);

	/* MST */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10030, 1640, 100);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10031, 1641, 100, 'would not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10032, 1642, 100, 'would');

	/* PCLC */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10034, 1929, 100);

	/* PTSD */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10035, 1989, 100);

	/* PHQ 9 DEPRESSION */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10040, 1599, 100);

	/* Prior MH DX/TX - Prior Mental Health Treatment*/
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10043, 1630, 100);

	/* TOBACCO */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10045, 600, 100);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10046, 601, 100, 'denied using tobacco ');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10047, 602, 100, 'quit using tobacco');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10048, 603, 100, 'endorsed using');

	/* VAS PAIN - BASIC PAIN*/
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10050, 2300, 100);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10051, 2334, 100, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10052, 2331, 100, 'pain area answer');



INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10070, 210, 100);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10071, 211, 100, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10072, 212, 100, 'prosthetic equipment');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10073, 213, 100, 'sexual health');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10074, 214, 100, 'mental health');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10075, 215, 100, 'substance use');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10076, 216, 100, 'visual impairment services team');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10080, 200, 100);		
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10081, 201, 100, 'enrollment');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10082, 202, 100, 'mental health');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10083, 203, 100, 'physical health');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10084, 204, 100, 'establishing a PCP');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10085, 205, 100, 'other');


INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10090, 1520, 100);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10091, 1521, 100, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10092, 1522, 100, 'depression');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10093, 1523, 100, 'Post Traumatic Stress Disorder (PTSD)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10094, 1524, 100, 'other');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10100, 1530, 100);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10101, 1531, 100, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10102, 1532, 100, 'inpatient psychiatric stay');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10103, 1533, 100, 'psychotherapy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10104, 1534, 100, 'psychiatric medication');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10105, 1535, 100, 'electro convulsive therapy');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10106, 1536, 100, 'other');