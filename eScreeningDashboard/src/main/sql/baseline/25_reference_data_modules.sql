/** Note: display order must start at 0 with no gaps*/

/* Identification Survey */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight, is_patient_protected_info) VALUES (1, 5, 'First Name', 0, 0, 1);
INSERT INTO survey_page_measure (survey_page_measure_id, survey_page_id, measure_id, display_order) VALUES (1, 1, 1, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (1, 1, 'demo_firstname');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (1, 5, 30);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight, is_patient_protected_info) VALUES (2, 5, 'Middle Name', 0, 0, 1);
INSERT INTO survey_page_measure (survey_page_measure_id, survey_page_id, measure_id, display_order) VALUES (2, 1, 2, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (10, 2, 'demo_midname');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (2, 5, 30);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight, is_patient_protected_info) VALUES (4, 5, 'Last Name', 0, 0, 1);
INSERT INTO survey_page_measure (survey_page_measure_id, survey_page_id, measure_id, display_order) VALUES (4, 1, 4, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (30, 4, 'demo_lastname');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight, is_patient_protected_info) VALUES (5, 5, 'SSN Last 4 Digits', 0, 0, 1);
INSERT INTO survey_page_measure (survey_page_measure_id, survey_page_id, measure_id, display_order) VALUES (5, 1, 5, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (40, 5, 'demo_SSN');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (5, 4, 4);
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (5, 5, 4);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight, is_patient_protected_info) VALUES (6, 1, 'Email', 0, 0, 1);
INSERT INTO survey_page_measure (survey_page_measure_id, survey_page_id, measure_id, display_order) VALUES (6, 1, 6, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (50, 6, 'demo_email');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (6, 5, 50);
INSERT INTO measure_validation (measure_id, validation_id, text_value) VALUES (6, 1, 'email');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight, is_patient_protected_info) VALUES (7, 1, 'Best number to reach you', 1, 0, 1);
INSERT INTO survey_page_measure (survey_page_measure_id, survey_page_id, measure_id, display_order) VALUES (7, 1, 7, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, export_name) VALUES (60, 7, '(Example: 5551235555)', 'demo_contact');
INSERT INTO measure_validation (measure_id, validation_id, text_value) VALUES (7, 1, 'number');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (7, 9, 10);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (8, 2, 'Best time to call', 0, 0);
INSERT INTO survey_page_measure (survey_page_measure_id, survey_page_id, measure_id, display_order) VALUES (8, 1, 8, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name, calculation_type_id, calculation_value) VALUES (70, 8, 1, 'Any time', 0, 'demo_call', 1, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name, calculation_type_id, calculation_value) VALUES (71, 8, 2, 'Morning', 0, 'demo_call', 1, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name, calculation_type_id, calculation_value) VALUES (72, 8, 3, 'Afternoon', 0, 'demo_call', 1, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name, calculation_type_id, calculation_value) VALUES (73, 8, 4, 'Evening', 0, 'demo_call', 1, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name, other_export_name) VALUES (74, 8, 5, 'Other', 'other', 0, 1, 5, 'demo_call', 'demo_call_spec');

/* Presenting concerns page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (9, 3, 'What brings you to the VA health care system?', 0, 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (2, 9, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (80, 9, 1, 'Enrollment', 1, 'demo_va_enroll');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (81, 9, 2, 'Mental Health Concerns', 1, 'demo_va_menthealth');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (82, 9, 3, 'Physical Health Concerns', 1, 'demo_va_physhealth');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (83, 9, 4, 'Establish Primary Care', 1, 'demo_va_primcare');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (84, 9, 5, 'Other, please specify', 'other', 1, 'demo_va_other', 'demo_va_otherspec');

/* Presenting concerns page 2 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (10, 3, 'Do you want/need information or assistance with health care?', 0, 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (3, 10, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (91, 10, 0, 'Prosthetic Equipment',1,'demo_info_prost');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (92, 10, 1, 'Sexual Health', 1,'demo_info_sex');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (93, 10, 2, 'Mental Health Appointment', 1,'demo_info_ment');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (94, 10, 3, 'Substance Use', 1,'demo_info_subst');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (95, 10, 4, 'Visual Impairment Services Team (VIST)', 1,'demo_info_visual');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (90, 10, 5, 'None', 'none', 1, 'demo_info_health_none');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (11, 3, 'Do you want/need information or assistance with any of the following VA benefits?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (3, 11, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (101, 11, 0, 'VA Compensation', 1, 'demo_info_comp');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (102, 11, 1, 'GI Bill', 1, 'demo_info_gi');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (103, 11, 2, 'VA Home Loan', 1, 'demo_info_loan');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (100, 11, 3, 'None', 'none', 1, 'demo_info_va_none');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (12, 3, 'Do you want/need information or assistance with any of the following employment services?', 0, 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (3, 12, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (111, 12, 0, 'VA Vocational Rehabilitation', 1, 'demo_info_rehab');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (112, 12, 1, 'Unemployment Benefits', 1, 'demo_info_unemp');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (113, 12, 2, 'VA Work Study', 1, 'demo_info_work');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (110, 12, 3, 'None', 'none', 1, 'demo_info_emp_none');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (13, 3, 'Do you want/need information or assistance with financial hardship?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (3, 13, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (121, 13, 0, 'Information About VA Or Community Resources', 1, 'demo_info_comm');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (120, 13, 1, 'None', 'none', 1, 'demo_info_fin_none');

/* Presenting concerns page 3 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (14, 3, 'Do you want/need information or assistance with any of the following social matters?', 0, 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (4, 14, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (131, 14, 0, 'Adjustment to Civilian Life', 1, 'demo_info_adj');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (132, 14, 1, 'Relationship Concerns', 1, 'demo_info_relat');
INSERT INTO measure_answer (measure_answer_id,measure_id, display_order, answer_text, answer_value, export_name) VALUES (133, 14, 2, 'Support Groups', 1, 'demo_info_support');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (130, 14, 3, 'None', 'none', 1, 'demo_info_soc_none');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (15, 3, 'Do you want/need information or assistance with any of the following legal matters?', 0, 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (4, 15, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (141, 15, 0, 'Parole', 1, 'demo_info_parole');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (142, 15, 1, 'Probation', 1, 'demo_info_probat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (143, 15, 2, 'Warrants', 1, 'demo_info_warrant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (144, 15, 3, 'Bankruptcy', 1, 'demo_info_bank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (140, 15, 4, 'None', 'none', 1, 'demo_info_legal_none');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (16, 3, 'Do you want/need information or assistance with housing?', 0, 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (4, 16, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (151, 16, 0, 'Homeless', 1, 'demo_info_home');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (152, 16, 1, 'Foreclosure', 1, 'demo_info_forcl');
--INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (153, 16, 2, 'Other, please specify', 'other', 1, 'demo_info_other', 'demo_info_otherspec');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (150, 16, 2, 'None', 'none', 1, 'demo_info_house_none');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (80, 2, 'Other', 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (154, 80, 1, 'No', 0, 1, 0, 'demo_info_other');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name, other_export_name) VALUES (153, 80, 2, 'Yes, please specify?', 'other', 1, 1, 1, 'demo_info_other', 'demo_info_otherspec');	
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (4, 80, 3);

/* Basic demographics page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (17, 2, 'Gender', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (5, 17, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name, calculation_type_id, calculation_value) VALUES (160, 17, 1, 'Male', 0, 'demo_gender', 1, 1);
INSERT INTO measure_answer (measure_answer_id,measure_id, display_order, answer_text, answer_value, export_name, calculation_type_id, calculation_value) VALUES (161, 17, 2, 'Female', 0, 'demo_gender', 1, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight, is_patient_protected_info) VALUES (18, 1, 'Date of Birth (mm/dd/yyyy)', 1, 0, 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (5, 18, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (170, 18, 3, 'demo_DOB');
INSERT INTO measure_validation (measure_id, validation_id, text_value) VALUES (18, 1, 'date');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (20, 1, 'Weight', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (5, 20, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, calculation_type_id, export_name) VALUES (210, 20, 'lbs', 2, 'demo_weight');
INSERT INTO measure_validation (measure_id, validation_id, text_value) VALUES (20, 1, 'number');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (20, 6, '40');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (20, 7, '1300');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (19, 8, '<div style=padding-top:15px; font-size:16px; font-weight:700;">Please specify your height using the following:</div>', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (5, 19, 3);

/* This update to drop-downs for feet and inches happened after we set IDs for measures and answers.  So we use measures: 71 and 72, and measure_answer_id: 176-194 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (71, 2, 'Feet', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (5, 71, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (182, 71, 3, '3', 0, 1, '3', 'demo_heightft');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (183, 71, 4, '4', 0, 1, '4', 'demo_heightft');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (184, 71, 5, '5', 0, 1, '5', 'demo_heightft');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (185, 71, 6, '6', 0, 1, '6', 'demo_heightft');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (186, 71, 7, '7', 0, 1, '7', 'demo_heightft');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (72, 2, 'Inches', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (5, 72, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (190, 72, 1, '0', 0, 1, '0', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (191, 72, 2, '1', 0, 1, '1', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (192, 72, 3, '2', 0, 1, '2', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (193, 72, 4, '3', 0, 1, '3', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (194, 72, 5, '4', 0, 1, '4', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (195, 72, 6, '5', 0, 1, '5', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (196, 72, 7, '6', 0, 1, '6', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (197, 72, 8, '7', 0, 1, '7', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (198, 72, 9, '8', 0, 1, '8', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (199, 72, 10, '9', 0, 1, '9', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (200, 72, 11, '10', 0, 1, '10', 'demo_heightinch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (201, 72, 12, '11', 0, 1, '11', 'demo_heightinch');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (21, 2, 'Ethnicity', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (5, 21, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (220, 21, 1, 'Hispanic/Latino', 0, 1, 1, 'demo_ethnic');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (221, 21, 2, 'Not Hispanic/Latino', 0, 1, 0, 'demo_ethnic');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name) VALUES (222, 21, 3, 'Decline To Answer', 'none', 0, 1, 2, 'demo_ethnic');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (22, 3, 'Race', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (5, 22, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (230, 22, 1, 'White/Caucasian', 0,'demo_racewhite');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (231, 22, 2, 'Black/African American', 0, 'demo_race_black');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (232, 22, 3, 'American Indian or Alaskan Native', 0, 'demo_race_amind');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (233, 22, 5, 'Asian (Filipino, Japanese, Korean, Chinese, Vietnamese, etc.)', 0, 'demo_race_asian');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (234, 22, 4, 'Native Hawaiian or Pacific Islander', 0, 'demo_race_pacisl');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (235, 22, 7, 'Other, please specify', 'other', 1, 'demo_race_oth', 'demo_race_othspec');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name) VALUES (236, 22, 6, 'Decline To Answer', 'none', 0, 1, 2, 'demo_race_decline');

/* Education and employment page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (23, 2, 'What is the highest grade of education that you have completed?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (6, 23, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (240, 23, 1, 'Some High School', 0, 1, 1,'demo_education');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (241, 23, 2, 'GED', 0, 1, 2,'demo_education');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (242, 23, 3, 'High School Diploma', 0, 1, 3,'demo_education');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (243, 23, 4, 'Some College', 0, 1, 4,'demo_education');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (244, 23, 5, 'Associates Degree', 0, 1, 5,'demo_education');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (245, 23, 6, '4-year College Degree', 0, 1, 6,'demo_education');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (246, 23, 7, "Master's Degree", 0, 1, 7,'demo_education');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (247, 23, 8, 'Doctoral Degree (Ph.D, M.D., DDS, etc.)', 0, 1, 8,'demo_education');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (24, 2, 'What is your employment status?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (6, 24, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (250, 24, 1, 'Full Time', 0, 1, 1, 'demo_workstatus');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (251, 24, 2, 'Part Time', 0, 1, 2, 'demo_workstatus');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (252, 24, 3, 'Seasonal', 0, 1, 3, 'demo_workstatus');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (253, 24, 4, 'Day Labor', 0, 1, 4, 'demo_workstatus');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (254, 24, 5, 'Unemployed', 0, 1, 5, 'demo_workstatus');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (25, 2, 'How many hours per week are you currently employed?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (6, 25, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (260, 25, 1, '0-10', 0, 1, 1, 'demo_hours');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (261, 25, 2, '11-20', 0, 1, 2, 'demo_hours');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (262, 25, 3, '21-30', 0, 1, 3, 'demo_hours');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (263, 25, 4, '31-40', 0, 1, 4, 'demo_hours');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (264, 25, 5, '40+', 0, 1, 5, 'demo_hours');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (26, 1, 'What is your usual occupation?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (6, 26, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (270, 26, 2, 'demo_occupation');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (27, 3, 'What are your source(s) of income?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (6, 27, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (280, 27, 1, 'No Income', 'none', 1, 'demo_income_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (281, 27, 2, 'Work', 0, 'demo_income_wrk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (282, 27, 3, 'Unemployment', 0, 'demo_income_unemp');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (283, 27, 4, 'Disability', 0, 'demo_income_dis');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (284, 27, 5, 'GI Bill', 0, 'demo_income_gi');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (285, 27, 6, 'Retirement/Pension', 0, 'demo_income_retire');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (286, 27, 7, 'Other, please specify', 'other', 1, 'demo_income_other', 'demo_income_spec');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (108, 2, 'What was the total combined income of all members of this family in the past 12 months? Please include money from jobs, net income from business, farm or rent, pensions, dividends, welfare, social security payments and any other monetary income received by you or any other family member.', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (6, 108, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1080, 108, 1, 'Less than $15,000', 0, 1, 1, 'demo_income_group');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1081, 108, 2, '$15,000-$29,999', 0, 1, 2, 'demo_income_group');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1082, 108, 3, '$30,000-$44,999', 0, 1, 3, 'demo_income_group');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1083, 108, 4, '$45,000-$59,999', 0, 1, 4, 'demo_income_group');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1084, 108, 5, '$60,000-$74,999', 0, 1, 5, 'demo_income_group');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1085, 108, 6, '$75,000-$99,999', 0, 1, 6, 'demo_income_group');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1086, 108, 7, '$100,000+', 0, 1, 7, 'demo_income_group');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (28, 2, 'What is your current relationship status?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (6, 28, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (290, 28, 1, 'Single', 0, 1, 1, 'demo_relationship');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (291, 28, 2, 'Married', 0, 1, 2, 'demo_relationship');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (292, 28, 3, 'Separated', 0, 1, 3, 'demo_relationship');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (293, 28, 4, 'Divorced', 0, 1, 4, 'demo_relationship');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (294, 28, 5, 'Cohabitating', 0, 1, 5, 'demo_relationship');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (295, 28, 6, 'Civil Union', 0, 1, 6, 'demo_relationship');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (296, 28, 7, 'Widowed/Widower', 0, 1, 8, 'demo_relationship');

/* Social enviornment page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (29, 4, 'Please add the ages of your children', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (7, 29, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, answer_type, answer_value, display_order) VALUES (300, 29, 'Child', 'none', 0, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (30, 2, 'Child Age', 29, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (301, 30, 0, 'younger than 1', 0, 1, 0, 'child_agegroup');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (302, 30, 1, '1-2', 0, 1, 1, 'child_agegroup');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (303, 30, 2, '3-5', 0, 1, 2, 'child_agegroup');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (304, 30, 3, '6-17', 0, 1, 3, 'child_agegroup');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (305, 30, 4, '18 and older', 0, 1, 4, 'child_agegroup');

/* Social enviornment page 2 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (32, 3, 'Who do you live with?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (8, 32, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (320, 32, 1, 'Alone', 'none', 1, 'demo_livewith_alone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (321, 32, 2, 'Parents/Relatives', 0, 'demo_livewith_parent');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (322, 32, 3, 'Friends/Roommates', 0, 'demo_livewith_friend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (323, 32, 4, 'Spouse or Partner', 0, 'demo_livewith_spouse');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (324, 32, 5, 'Children', 0, 'demo_livewith_child');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (325, 32, 6, 'Other, please specify', 'other', 1, 'demo_livewith_other', 'demo_livewith_otherspec');

/* Social environnment page 3 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (34, 3, 'Who gives you emotional support and advice?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (9, 34, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (330, 34, 1, 'No One', 'none', 1, 'demo_emo_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (331, 34, 2, 'Parents', 0, 'demo_emo_parents');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (332, 34, 3, 'Friends', 0, 'demo_emo_friends');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (333, 34, 4, 'Partner/Spouse', 0, 'demo_emo_spouse');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (334, 34, 5, 'Therapist', 0, 'demo_emo_therapist');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (335, 34, 6, 'Spiritual/Religious Advisor', 0, 'demo_emo_spiritual');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (336, 34, 7, 'Children', 0, 'demo_emo_children');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (338, 34, 8, 'Other, please specify', 'other', 1, 'demo_emo_other', 'demo_emo_other_spec');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (35, 2, 'Are you in a relationship in which you have been physically hurt or felt threatened?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (9, 35, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (340, 35, 1, 'No', 0, 1, 0, 'demo_rel_hurt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (341, 35, 2, 'Yes', 0, 1, 1, 'demo_rel_hurt');

/* Promis Emotional Support Survey*/
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (100, 6, 'Please respond to each item by marking one box per row.', 0);
INSERT INTO survey_page_measure (survey_page_measure_id, survey_page_id, measure_id, display_order) VALUES (100, 10, 100, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (101, 2, 'I have someone who will listen to me when I need to talk', 100, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1010, 101, 1, 'never', 0, 1, 1, 'es1_listen');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1011, 101, 2, 'rarely', 0, 1, 2, 'es1_listen');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1012, 101, 3, 'sometimes', 0, 1, 3, 'es1_listen');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1013, 101, 4, 'usually', 0, 1, 4, 'es1_listen');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1014, 101, 5, 'always', 0, 1, 5, 'es1_listen');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (102, 2, 'I have someone to confide in or talk to about myself or my problems', 100, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1020, 102, 1, 'never', 0, 1, 1, 'es2_talk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1021, 102, 2, 'rarely', 0, 1, 2, 'es2_talk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1022, 102, 3, 'sometimes', 0, 1, 3, 'es2_talk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1023, 102, 4, 'usually', 0, 1, 4, 'es2_talk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1024, 102, 5, 'always', 0, 1, 5, 'es2_talk');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (103, 2, 'I have someone who makes me feel appreciated', 100, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1030, 103, 1, 'never', 0, 1, 1, 'es3_feel');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1031, 103, 2, 'rarely', 0, 1, 2, 'es3_feel');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1032, 103, 3, 'sometimes', 0, 1, 3, 'es3_feel');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1033, 103, 4, 'usually', 0, 1, 4, 'es3_feel');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1034, 103, 5, 'always', 0, 1, 5, 'es3_feel');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (104, 2, 'I have someone to talk with when I have a bad day', 100, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1040, 104, 1, 'never', 0, 1, 1, 'es4_bad');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1041, 104, 2, 'rarely', 0, 1, 2, 'es4_bad');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1042, 104, 3, 'sometimes', 0, 1, 3, 'es4_bad');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1043, 104, 4, 'usually', 0, 1, 4, 'es4_bad');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1044, 104, 5, 'always', 0, 1, 5, 'es4_bad');

/* 11 Homelessness Page 1 */

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (61, 2, 'In the past 2 months, have you been living in stable housing that you own, rent or stay in as part of a household?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 61, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (610, 61, 0, 'No, not living in stable housing', 0, 1, 0, 'HomelessCR_stable_house');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (611, 61, 1, 'Yes, living in stable housing', 0, 1, 1, 'HomelessCR_stable_house');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (612, 61, 2, ' I decline to be screened', 0, 1, 2, 'HomelessCR_stable_house');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (62, 2, 'Are you worried or concerned that in the next 2 months you may not have stable housing that you own, rent or stay in as part of a household?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 62, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (620, 62, 0, 'No, I am not worried about housing in the near future', 0, 1, 0, 'HomelessCR_stable_worry');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (621, 62, 1, 'Yes, worried about stable housing in the near future', 0, 1, 1, 'HomelessCR_stable_worry');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (63, 2, 'Where have you lived for the MOST of the past 2 months?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 63, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (630, 63, 1, 'Apartment/House/Room-no government subsidy', 0, 1, 1, 'HomelessCR_live_2mos');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (631, 63, 2, 'Apartment/House/Room- with government subsidy', 0, 1, 2, 'HomelessCR_live_2mos');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (632, 63, 3, 'With a friend or family member', 0, 1, 3, 'HomelessCR_live_2mos');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (633, 63, 4, 'Motel/Hotel', 0, 1, 4,  'HomelessCR_live_2mos');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (634, 63, 5, 'Short term institution like a hospital, rehabilitation center, or drug treatment facility', 0, 1, 5, 'HomelessCR_live_2mos');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (635, 63, 6, 'Homeless shelter', 0, 1, 6, 'HomelessCR_live_2mos');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (636, 63, 7, 'Anywhere outside, eg. street, vehicle, abandoned building', 0, 1, 7, 'HomelessCR_live_2mos');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (637, 63, 8, 'Other', 0, 1, 8, 'HomelessCR_live_2mos');

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (64, 1, 'Comment:', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 64, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (640, 64, 3, 'HomelessCR_homenogov_spec');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (64, 4, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (65, 1, 'Comment:', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 65, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (650, 65, 3, 'HomelessCR_homewgov_spec');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (65, 4, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (66, 1, 'Comment:', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 66, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (660, 66, 3, 'HomelessCR_friendfam_spec');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (66, 4, 2);
	
INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (67, 1, 'Comment:', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 67, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (670, 67, 3, 'HomelessCR_hotel_spec');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (67, 4, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (68, 1, 'Comment:', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 68, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (680, 68, 3, 'HomelessCR_shortins_spec');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (68, 4, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (69, 1, 'Comment:', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 69, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (690, 69, 3, 'HomelessCR_shel_spec');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (69, 4, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (70, 1, 'Comment:', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 70, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (700, 70, 3, 'HomelessCR_out_spec');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (70, 4, 2);
  
INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (50, 1, 'Comment:', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 50, 10);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (500, 50, 3, 'HomelessCR_oth_spec');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (50, 4, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (51, 2, 'Would you like to be referred to talk more about your housing situation?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 51, 11);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (510, 51, 1, 'No', 0, 1, 0, 'HomelessCR_house_ref');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (511, 51, 1, 'Yes', 0, 1, 1, 'HomelessCR_house_ref');

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (52, 1, 'What is the best way to reach you?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (11, 52, 12);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (520, 52, 3, 'HomelessCR_howreach');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (52, 4, 2);

/* Pragmatic concerns Page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (36, 3, 'Do you have any legal concerns?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (12, 36, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (350, 36, 1, 'None', 'none', 1, 'demo_legal_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (351, 36, 2, 'Civil Issues', 0, 'demo_legal_civil');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (352, 36, 3, 'Child Support', 0, 'demo_legal_child');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (353, 36, 4, 'Taxes', 0, 'demo_legal_tax');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (354, 36, 5, 'Bankruptcy', 0, 'demo_legal_bank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (355, 36, 6, 'Outstanding Tickets', 0, 'demo_legal_ticket');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (356, 36, 7, 'Arrest Warrants', 0, 'demo_legal_arrest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (357, 36, 8, 'Restraining Orders', 0, 'demo_legal_restrain');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (358, 36, 9, 'DUIs', 0, 'demo_legal_dui');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (359, 36, 10, 'Probation', 0, 'demo_legal_prob');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (360, 36, 11, 'Parole', 0, 'demo_legal_parole');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (361, 36, 12, 'JAG', 0, 'demo_legal_jag');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (362, 36, 13, 'Child Protective Services', 0, 'demo_legal_cps');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (363, 36, 14, 'Other, please specify', 'other', 1, 'demo_legal_other', 'demo_legal_otherspec');

/* Advance directive page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (37, 2, 'What language do you prefer to get your health information?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (13, 37, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (370, 37, 1, 'English', 0, 1, 1, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (371, 37, 2, 'Spanish', 0, 1, 2, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (372, 37, 3, 'Tagalog', 0, 1, 3, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (373, 37, 4, 'Chinese', 0, 1, 4, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (374, 37, 5, 'German', 0, 1, 5, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (375, 37, 6, 'Japanese', 0, 1, 6, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (376, 37, 7, 'Korean', 0, 1, 7, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (377, 37, 8, 'Russian', 0, 1, 8, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (378, 37, 9, 'Vietnamese', 0, 1, 9, 'demo_language');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name, other_export_name) VALUES (379, 37, 10, 'Other, please specify', 'other', 1, 1, 10, 'demo_language_other', 'demo_language_otherspec');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (38, 2, 'Do you have an Advance Directive or Durable Power of Attorney for Healthcare?', 1, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (13, 38, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (380, 38, 1, 'No', 0, 1, 0, 'demo_will');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (381, 38, 2, 'Yes', 0, 1, 1, 'demo_will');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (39, 2, 'Would you like information about Advance Directive?', 1, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (13, 39, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (390, 39, 1, 'No', 0, 1, 0, 'demo_will_info');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (391, 39, 2, 'Yes', 0, 1, 1, 'demo_will_info');

/* Spiritual Health page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (41, 2, 'Is spirituality and/or religion important to you now?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (14, 41, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (410, 41, 2, 'No', 0, 1, 0, 'spirit_rel_important');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (411, 41, 1, 'Yes', 0, 1, 1, 'spirit_rel_important');
/*INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (412, 41, 3, "I don't know", 0, 1, 2, 'spirit_rel_important');*/

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (44, 2, 'Did combat or military service affect your view of spirituality and/or religion?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (14, 44, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (440, 44, 2, 'No', 0, 1, 0, 'spirit_combat_change');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (441, 44, 1, 'Yes', 0, 1, 1, 'spirit_combat_change');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (442, 44, 3, "I don't Know", 1, 1, 2, 'spirit_combat_change');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (109, 2, 'Are you presently connected to a faith community?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (14, 109, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1090, 109, 2, 'No', 0, 1, 0, 'spirit_faith_comm');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1091, 109, 1, 'Yes', 0, 1, 1, 'spirit_faith_comm');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1092, 109, 3, "No, but I would like to be part of one", 1, 1, 2, 'spirit_faith_comm');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (40, 2, 'Would you like a VA chaplain to contact you  for any current concerns or support?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (14, 40, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (400, 40, 1, 'No, not now', 0, 1, 0, 'spirit_chap_referral');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (401, 40, 1, 'Yes', 0, 1, 1, 'spirit_chap_referral');

/* Service History Survey Page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (90, 4, 'Please add one entry for each enlistment, beginning with your most recent service', 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (20, 90, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, answer_type, answer_value, display_order, export_name) VALUES (900, 90, 'most recent service', 'most recent service', 0, 1, 'serv_oper_xxx');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order, is_required) VALUES (91, 2, 'Type of Service', 90, 1, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (910, 91, 1, 'Active Duty', 0, 1, 1, 'serv_type');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (911, 91, 2, 'Reserve', 0, 1, 2, 'serv_type');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (912, 91, 3, 'Guard', 0, 1, 3, 'serv_type');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (92, 2, 'Branch of Service', 90, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (920, 92, 1, 'Army', 0, 1, 1, 'serv_branch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (921, 92, 2, 'Air Force', 0, 1, 2, 'serv_branch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (922, 92, 3, 'Coast Guard', 0, 1, 3, 'serv_branch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (923, 92, 4, 'Marines', 0, 1, 4, 'serv_branch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (924, 92, 5, 'National Guard', 0, 1, 5, 'serv_branch');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (925, 92, 6, 'Navy', 0, 1, 6, 'serv_branch');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (93, 1, 'Year Entered Service', 90, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, export_name) VALUES (930, 93, '(yyyy)', 'serv_start');
INSERT INTO measure_validation (measure_id, validation_id, text_value) VALUES (93, 1, 'number');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (93, 9, 4);
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (93, 6, '1900');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (94, 1, 'Year Discharged from Service', 90, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, export_name) VALUES (940, 94, '(yyyy)', 'serv_stop');
INSERT INTO measure_validation (measure_id, validation_id, text_value) VALUES (94, 1, 'number');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (94, 9, 4);
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (94, 6, '1900');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (96, 2, 'Type of Discharge', 90, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (960, 96, 1, 'Honorable', 0, 1, 1, 'serv_discharge');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (961, 96, 2, 'Other than Honorable', 0, 1, 2, 'serv_discharge');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (962, 96, 3, 'Dishonorable', 0, 1, 3, 'serv_discharge');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (963, 96, 4, 'General w/ Honorable', 0, 1, 4, 'serv_discharge');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (964, 96, 5, 'Medical', 0, 1, 5, 'serv_discharge');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (965, 96, 6, 'Administrative Separation', 0, 1, 6, 'serv_discharge');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (966, 96, 7, 'Retired', 0, 1, 7, 'serv_discharge');




INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (97, 2, 'Rank upon discharge', 90, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (970, 97, 1, 'e1', 0, 1, 1, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (971, 97, 2, 'e2', 0, 1, 2, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (972, 97, 3, 'e3', 0, 1, 3, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (973, 97, 4, 'e4', 0, 1, 4, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (974, 97, 5, 'e5', 0, 1, 5, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (975, 97, 6, 'e6', 0, 1, 6, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (976, 97, 7, 'e7', 0, 1, 7, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (977, 97, 8, 'e8', 0, 1, 8, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (978, 97, 9, 'e9', 0, 1, 9, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (979, 97, 10, 'o1', 0, 1, 10, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (980, 97, 11, 'o2', 0, 1, 11, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (981, 97, 12, 'o3', 0, 1, 12, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (982, 97, 13, 'o4', 0, 1, 13, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (983, 97, 14, 'o5', 0, 1, 14, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (984, 97, 15, 'o6', 0, 1, 15, 'serv_rank');
--TODO: the calculated values for these are not correct.
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (985, 97, 16, 'o7', 0, 1, 21, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (986, 97, 17, 'o8', 0, 1, 22, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (987, 97, 18, 'o9', 0, 1, 23, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (988, 97, 19, 'o10', 0, 1, 24, 'serv_rank');

INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (989, 97, 20, 'w1', 0, 1, 16, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (990, 97, 21, 'w2', 0, 1, 17, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (991, 97, 22, 'w3', 0, 1, 18, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (992, 97, 23, 'w4', 0, 1, 19, 'serv_rank');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (993, 97, 24, 'w5', 0, 1, 20, 'serv_rank');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (99, 1, 'Job, MOS, or RATE', 90, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (1000, 99, 'serv_job');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (99, 5, 1000);

/* Service History Survey Page 2 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (110, 3, 'Did you ever serve in any of the following operations or foreign combat zones?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (21, 110, 0);
INSERT INTO measure_answer (measure_answer_id,measure_id, answer_text, answer_type, answer_value, export_name) VALUES (1100, 110, 'None', 'none', 1, 'serv_oper_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1101, 110, 2, 'Operation Enduring Freedom (OEF)', 0, 'serv_oper_OEF');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1102, 110, 3, 'Operation Iraqi Freedom (OIF)', 0, 'serv_oper_OIF');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1103, 110, 4, 'Global War on Terror operations other than OEF/OIF (e.g. Noble Eagle, Vigilant Mariner)', 0, 'serv_oper_gwot');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1104, 110, 5, 'Operation New Dawn', 0, 'serv_oper_ond');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1105, 110, 6, 'Caribbean / South America / Central America', 0, 'serv_oper_caribbean');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1106, 110, 7, 'Gulf War (1991)', 0, 'serv_oper_gulf');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1107, 110, 8, 'Somalia', 0, 'serv_oper_somalia');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1108, 110, 9, 'Bosnia', 0, 'serv_oper_bosnia');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1109, 110, 10, 'Kosovo', 0, 'serv_oper_kosovo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1110, 110, 11, 'Djibouti', 0, 'serv_oper_djibouti');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1111, 110, 12, 'Libya', 0, 'serv_oper_libya');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1112, 110, 13, 'Vietnam', 0, 'Serv_oper_vietnam');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1113, 110, 14, 'Korean War', 0, 'serv_oper_korea');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (1114, 110, 15, 'Other, please specify', 'other', 1, 'serv_oper_other', 'serv_oper_other1spec');	
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (1115, 110, 16, 'Other, please specify', 'other', 1, 'serv_oper_other', 'serv_oper_other2spec');	

/* 22 OEF OIF Clinical Reminder page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (112, 2, 'Did you serve in Operation Enduring Freedom (OEF) or in Operation Iraqi Freedom (OIF), either on the ground, in the nearby coastal waters, or in the air above, after September 11, 2001? (Mark one answer - consider only your most recent deployment or service period)');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (22, 112, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1120, 112, 0, 'No, no service in OEF or OIF', 0, 1, 0, 'serv_oef');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1121, 112, 1, 'Yes, Service in Operation Enduring Freedom (OEF) (Afghanistan, Georgia, Krgyzstan, Pakistan, Tajikistan, Uzbekisatn, the Phillippines, Other)', 0, 1, 1, 'serv_oef');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1122, 112, 2, 'Yes, Service in Operation Iraqi Freedom (OIF) (Iraq, Kuwait, Saudi Arabia, Turkey, Other)', 0, 1, 2, 'serv_oef');

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (113, 2, 'Where was your most recent OEF service?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (22, 113, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1130, 113, 0, 'Afghanistan', 0, 1, 1, 'serv_oef_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1131, 113, 1, 'Tajikistan', 0, 1, 2, 'serv_oef_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1132, 113, 2, 'Georgia', 0, 1, 4, 'serv_oef_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1133, 113, 3, 'Uzbekistan', 0, 1, 5, 'serv_oef_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1134, 113, 4, 'Kyrgystan', 0, 1, 6, 'serv_oef_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1135, 113, 5, 'The Philippines', 0, 1, 7, 'serv_oef_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1136, 113, 6, 'Pakistan', 0, 1, 8, 'serv_oef_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name, calculation_type_id, calculation_value) VALUES (1137, 113, 7, 'Other, please specify', 'other', 1, 'serv_oef_other', 'serv_oef_otherspec', 1, 10);	

--INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (114, 2, 'Did you serve in OIF?', 0, 0);
--INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (22, 114, 2);
--INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1140, 114, 1, 'No', 0, 1, 0, 'serv_oif');
--INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1141, 114, 2, 'Yes', 0, 1, 1, 'serv_oif');

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (115, 2, 'Where was your most recent OIF service?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (22, 115, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1150, 115, 0, 'Iraq', 0, 1, 1, 'serv_oif_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1151, 115, 1, 'Kuwait', 0, 1, 3, 'serv_oif_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1152, 115, 2, 'Saudi Arabia', 0, 1, 5, 'serv_oif_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1153, 115, 3, 'Turkey', 0, 1, 6, 'serv_oif_loc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name, calculation_type_id, calculation_value) VALUES (1154, 115, 4, 'Other, please specify', 'other', 1, 'serv_oif_loc_other', 'serv_oif_otherspec', 1, 9);	

/* 23 Military Deployments and History page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (116, 3, 'Did you receive any of the following disciplinary actions during your time in the service?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (23, 116, 0);
INSERT INTO measure_answer (measure_answer_id,measure_id, answer_text, answer_type, answer_value, export_name) VALUES (1160, 116, 'None', 'none', 1, 'serv_discipline_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1161, 116, 2, "Formal Counseling (e.g. Captain's Mast)", 0, 'serv_discipline_couns');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1162, 116, 3, 'Article 15 (Non-Judicial Punishment)', 0, 'serv_discipline_art15');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1163, 116, 4, 'Court Martial', 0, 'serv_discipline_martial');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (1164, 116, 5, 'Other, please specify', 'other', 1, 'serv_discipline_other', 'serv_discipline_otherspec');	

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (117, 3, 'Did you receive any of the following personal awards or commendations?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (23, 117, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1170, 117, 1, 'None', 'none', 1, 'serv_award_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1171, 117, 2, 'Medal of Honor', 0, 'serv_award_honor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1172, 117, 3, 'Distinguished Service Cross', 0, 'serv_award_distservcross');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1173, 117, 4, 'Distinguished Service Medal', 0, 'serv_award_distservmedal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1174, 117, 5, 'Meritorious Service Medal', 0, 'serv_award_meritservmedal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1175, 117, 6, 'Legion of Merit', 0, 'serv_award_legionmerit');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1176, 117, 7, 'Distinguished Flying Cross', 0, 'serv_distflycross');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1177, 117, 8, 'Bronze Star', 0, 'serv_award_bronzstar');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1178, 117, 9, 'Purple Heart', 0, 'serv_award_purpheart');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1179, 117, 10, 'Air Medal', 0, 'serv_award_airmedal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1180, 117, 11, 'Silver Star', 0, 'serv_award_silvstar');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1181, 117, 12, "Soldier's Medal", 0, 'serv_award_soldiermedal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1182, 117, 13, 'Achievement Medal', 0, 'serv_award_achievmedal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1183, 117, 14, 'Commendation Medal', 0, 'serv_award_commedal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (1184, 117, 15, 'Other, please specify', 'other', 1, 'serv_award_other', 'serv_award_otherspec');	

/* Military Deployments and History page 2 */
INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (119, 4, 'Please add one entry for each overseas deployment in support of a combat operation for more than 30 days.');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (24, 119, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, answer_type, answer_value, display_order) VALUES (1200, 119, 'Deployment', 'none', 0, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (120, 1, 'Combat deployment location', 119, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (1210, 120, 'serv_deploy_loc');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (120, 4, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (121, 2, 'Start of combat deployment month', 119, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1211, 121, 0, '01', 1, 1, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1212, 121, 1, '02', 1, 2, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1213, 121, 2, '03', 1, 3, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1214, 121, 3, '04', 1, 4, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1215, 121, 4, '05', 1, 5, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1216, 121, 5, '06', 1, 6, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1217, 121, 6, '07', 1, 7, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1218, 121, 7, '08', 1, 8, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1219, 121, 8, '09', 1, 9, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1220, 121, 9, '10', 1, 10, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1221, 121, 10, '11', 1, 11, 'serv_deploy_from_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1222, 121, 11, '12', 1, 12, 'serv_deploy_from_mo');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (122, 1, 'Start of combat deployment year (yyyy)', 119, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (1223, 122, 'serv_deploy_from_yr');
INSERT INTO measure_validation (measure_id, validation_id, text_value) VALUES (122, 1, 'number');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (122, 9, 4);
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (122, 6, '1900');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (123, 2, 'End of combat deployment month', 119, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1224, 123, 0, '01', 1, 1, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1225, 123, 1, '02', 1, 2, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1226, 123, 2, '03', 1, 3, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1227, 123, 3, '04', 1, 4, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1228, 123, 4, '05', 1, 5, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1229, 123, 5, '06', 1, 6, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1230, 123, 6, '07', 1, 7, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1231, 123, 7, '08', 1, 8, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1232, 123, 8, '09', 1, 9, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1233, 123, 9, '10', 1, 10, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1234, 123, 10, '11', 1, 11, 'serv_deploy_to_mo');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (1235, 123, 11, '12', 1, 12, 'serv_deploy_to_mo');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (124, 1, 'End of combat deployment year (yyyy)', 119, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (1240, 124, 'serv_deploy_to_yr');
INSERT INTO measure_validation (measure_id, validation_id, text_value) VALUES (124, 1, 'number');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (124, 9, 4);
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (124, 6, '1900');

/* Exposures page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (125, 2, 'Do you have any persistent major concerns regarding the effects of something you believe you may have been exposed to or encountered while deployed? ');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (25, 125, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1250, 125, 1, 'No', 0, 1, 0, 'serv_exposed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1251, 125, 2, 'Yes', 0, 1, 1, 'serv_exposed');

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (126, 3, 'Which of the following do you believe you may have been exposed to?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (25, 126, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1260, 126, 1, 'Chemical agents', 0, 'serv_exp_chemical');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1261, 126, 2, 'Biological agents', 0, 'serv_exp_bio');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1262, 126, 3, 'JP8 or other fuels', 0, 'serv_exp_jp8');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1263, 126, 4, 'Asbestos', 0, 'serv_exp_asbestos');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1264, 126, 5, 'Nerve gas', 0, 'serv_exp_nerve');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1265, 126, 6, 'Radiological agents', 0, 'serv_exp_radio');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1266, 126, 7, 'Sand/Dust or Particulate Matter', 0, 'serv_exp_sand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1267, 126, 8, 'Depleted uranium', 0, 'serv_exp_uranium');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1268, 126, 9, 'Industrial pollution', 0, 'serv_exp_industrial');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1269, 126, 10, 'Exhaust fumes', 0, 'serv_exp_fumes');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1270, 126, 11, 'Paints', 0, 'serv_exp_paint');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1271, 126, 12, 'Animal/insect bites', 0, 'serv_exp_bite');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1272, 126, 13, 'Smoke from burn pits', 0, 'serv_exp_burn');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1273, 126, 14, 'Pesticides', 0, 'serv_exp_pest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (1274, 126, 15, 'Other, please specify', 'other', 1, 'serv_exp_other', 'serv_exp_oth1spec');	
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (1275, 126, 16, 'Other, please specify', 'other', 1, 'serv_exp_other', 'serv_exp_oth2spec');	
	
/* 26 Service History', 'Exposures page 2 */
INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (127, 6, 'During any deployment in the last 18 months did any of the following occur?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (26, 127, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (128, 2, 'An animal bite that broke the skin?', 127, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1280, 128, 0, 'No', 0, 1, 0, 'serv_animal_bite');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1281, 128, 1, 'Yes', 0, 1, 1, 'serv_animal_bite');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (129, 2, 'Your mouth, eyes or broken skin exposed to the saliva or blood of an animal?', 127, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1290, 129, 0, 'No', 0, 1, 0, 'serv_animal_blood');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1291, 129, 1, 'Yes', 0, 1, 1, 'serv_animal_blood');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (130, 2, 'A bat in your sleeping quarters?', 127, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1300, 130, 0, 'No', 0, 1, 0, 'serv_animal_bat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1301, 130, 1, 'Yes', 0, 1, 1, 'serv_animal_bat');

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (131, 2, 'Did your military experience include exposure to combat?');

INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (26, 131, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1310, 131, 0, 'No', 0, 1, 0, 'serv_combat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1311, 131, 1, 'Yes', 0, 1, 1, 'serv_combat');

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (132, 3, 'Which of the following were you exposed to?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (26, 132, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1319, 132, 0, 'None', 'none', 0, 'serv_comb_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1320, 132, 1, 'Being Attacked or Ambushed', 0, 'serv_comb_attack');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1321, 132, 2, 'Firing Weapons at the Enemy', 0, 'serv_comb_fire');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1322, 132, 3, 'Hand to hand combat', 0, 'serv_comb_hand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1323, 132, 4, 'Caring for wounded', 0, 'serv_comb_wounded');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1324, 132, 5, 'Interrogation', 0, 'serv_comb_interro');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1325, 132, 6, 'Receiving rocket or mortar fire', 0, 'serv_comb_rocket');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1326, 132, 7, 'Seeing dead bodies', 0, 'serv_comb_seebody');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1327, 132, 8, 'Clearing or searching buildings', 0, 'serv_comb_clear');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1328, 132, 9, 'Firing from a Navy ship', 0, 'serv_comb_ship');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1329, 132, 10, 'Processing/handling detainees', 0, 'serv_comb_detain');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1330, 132, 11, 'Receiving small arms fire', 0, 'serv_comb_recdfire');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1331, 132, 12, 'Handling human remains', 0, 'serv_comb_handbody');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1332, 132, 13, 'Someone killed near you', 0, 'serv_comb_killed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1333, 132, 14, 'Caring for enemy wounded', 0, 'serv_comb_enemy');

/* Service Injures page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (140, 7, 'Did you have any of the following injuries at any time during your service?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (27, 140, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (141, 3, 'Blast injury', 140, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1410, 141, 1, 'None', 'none', 0, 'serv_blast_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1411, 141, 2, 'Yes, During Combat Deployment', 0, 'serv_blast_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1412, 141, 3, 'Yes, During Other Service Period or Training', 0, 'serv_blast_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (142, 3, 'Injury to spine or back', 140, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1420, 142, 1, 'None', 'none', 0, 'serv_spine_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1421, 142, 2, 'Yes, During Combat Deployment', 0, 'serv_spine_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1422, 142, 3, 'Yes, During Other Service Period or Training', 0, 'serv_spine_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (143, 3, 'Burn (second, 3rd degree)', 140, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1430, 143, 1, 'None', 'none', 0, 'serv_burn_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1431, 143, 2, 'Yes, During Combat Deployment', 0, 'serv_burn_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1432, 143, 3, 'Yes, During Other Service Period or Training', 0, 'serv_burn_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (144, 3, 'Nerve damage', 140, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1440, 144, 1, 'None', 'none', 0, 'serv_nerve_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1441, 144, 2, 'Yes, During Combat Deployment', 0, 'serv_nerve_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1442, 144, 3, 'Yes, During Other Service Period or Training', 0, 'serv_nerve_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (145, 3, 'Loss or damage to vision', 140, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1450, 145, 1, 'None', 'none', 0, 'serv_vision_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1451, 145, 2, 'Yes, During Combat Deployment', 0, 'serv_vision_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1452, 145, 3, 'Yes, During Other Service Period or Training', 0, 'serv_vision_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (146, 3, 'Loss or damage to hearing', 140, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1460, 146, 1, 'None', 'none', 0, 'serv_hearing_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1461, 146, 2, 'Yes, During Combat Deployment', 0, 'serv_hearing_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1462, 146, 3, 'Yes, During Other Service Period or Training', 0, 'serv_hearing_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (147, 3, 'Amputation', 140, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1470, 147, 1, 'None', 'none', 0, 'serv_amput_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1471, 147, 2, 'Yes, During Combat Deployment', 0, 'serv_amput_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1472, 147, 3, 'Yes, During Other Service Period or Training', 0, 'serv_amput_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (148, 3, 'Broken/fractured bone(s)', 140, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1480, 148, 1, 'None', 'none', 0, 'serv_bone_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1481, 148, 2, 'Yes, During Combat Deployment', 0, 'serv_bone_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1482, 148, 3, 'Yes, During Other Service Period or Training', 0, 'serv_bone_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (149, 3, 'Joint or muscle damage', 140, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1490, 149, 1, 'None', 'none', 0, 'serv_joint_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1491, 149, 2, 'Yes, During Combat Deployment', 0, 'serv_joint_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1492, 149, 3, 'Yes, During Other Service Period or Training', 0, 'serv_joint_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (150, 3, 'Internal or abdominal injuries', 140, 10);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1500, 150, 1, 'None', 'none', 0, 'serv_internal_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1501, 150, 2, 'Yes, During Combat Deployment', 0, 'serv_internal_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1502, 150, 3, 'Yes, During Other Service Period or Training', 0, 'serv_internal_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (151, 3, 'Other, please specify', 140, 11);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (1510, 151, 1, 'Other, please specify', 'other', 0, 'serv_other1_other', 'serv_other1_otherspec');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1511, 151, 2, 'None', 'none', 0, 'serv_other1_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1512, 151, 3, 'Yes, During Combat Deployment', 0, 'serv_other1_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1513, 151, 4, 'Yes, During Other Service Period or Training', 0, 'serv_other1_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (152, 3, 'Other, please specify', 140, 12);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (1520, 152, 1, 'Other, please specify', 'other', 0, 'serv_other2_other', 'serv_other2_otherspec');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (1521, 152, 2, 'None', 'none', 0, 'serv_other2_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1522, 152, 3, 'Yes, During Combat Deployment', 0, 'serv_other2_comb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (1523, 152, 4, 'Yes, During Other Service Period or Training', 0, 'serv_other2_other');

/* Servoce Injures page 2 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (155, 2, 'Did you receive service-connected compensation for an injury?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (28, 155, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1550, 155, 1, 'No', 0, 1, 0, 'serv_inj_comp');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1551, 155, 2, 'Yes', 0, 1, 1, 'serv_inj_comp');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1552, 155, 3, 'No, but intend to file/in progress', 0, 1, 2, 'serv_inj_comp');

/* 29 PHQ15 page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (160, 6, 'During the <b>past 4 weeks</b>, how much have you been bothered by any of the following problems? Select the option to indicate how much you were bothered.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (29, 160, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (161, 2, 'Stomach pain', 160, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1610, 161, 1, 'Not Bothered At All', 0, 1, 0, 'health1_stomach');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1611, 161, 2, 'Bothered A Little', 0, 1, 1, 'health1_stomach');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1612, 161, 3, 'Bothered A Lot', 0, 1, 2, 'health1_stomach');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (162, 2, 'Back pain', 160, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1620, 162, 1, 'Not Bothered At All', 0, 1, 0, 'health2_back');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1621, 162, 2, 'Bothered A Little', 0, 1, 1, 'health2_back');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1622, 162, 3, 'Bothered A Lot', 0, 1, 2, 'health2_back');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (163, 2, 'Pain in your arms, legs, or joints (knees, hips, etc.)', 160, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1630, 163, 1, 'Not Bothered At All', 0, 1, 0, 'health3_arm');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1631, 163, 2, 'Bothered A Little', 0, 1, 1, 'health3_arm');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1632, 163, 3, 'Bothered A Lot', 0, 1, 2, 'health3_arm');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (164, 2, 'Menstrual cramps or other problems with your periods (Women Only)', 160, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1640, 164, 1, 'Not Bothered At All', 0, 1, 0, 'health4_cramp');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1641, 164, 2, 'Bothered A Little', 0, 1, 1, 'health4_cramp');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1642, 164, 3, 'Bothered A Lot', 0, 1, 2, 'health4_cramp');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (165, 2, 'Headaches', 160, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1650, 165, 1, 'Not Bothered At All', 0, 1, 0, 'health5_headache');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1651, 165, 2, 'Bothered A Little', 0, 1, 1, 'health5_headache');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1652, 165, 3, 'Bothered A Lot', 0, 1, 2, 'health5_headache');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (166, 2, 'Chest pain', 160, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1660, 166, 1, 'Not Bothered At All', 0, 1, 0, 'health6_chest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1661, 166, 2, 'Bothered A Little', 0, 1, 1, 'health6_chest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1662, 166, 3, 'Bothered A Lot', 0, 1, 2, 'health6_chest');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (167, 2, 'Dizziness', 160, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1670, 167, 1, 'Not Bothered At All', 0, 1, 0, 'health7_dizzy');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1671, 167, 2, 'Bothered A Little', 0, 1, 1, 'health7_dizzy');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1672, 167, 3, 'Bothered A Lot', 0, 1, 2, 'health7_dizzy');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (168, 2, 'Fainting spells', 160, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1680, 168, 1, 'Not Bothered At All', 0, 1, 0, 'health8_faint');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1681, 168, 2, 'Bothered A Little', 0, 1, 1, 'health8_faint');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1682, 168, 3, 'Bothered A Lot', 0, 1, 2, 'health8_faint');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (169, 2, 'Feeling your heart pound or race', 160, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1690, 169, 1, 'Not Bothered At All', 0, 1, 0, 'health9_heart');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1691, 169, 2, 'Bothered A Little', 0, 1, 1, 'health9_heart');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1692, 169, 3, 'Bothered A Lot', 0, 1, 2, 'health9_heart');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (170, 2, 'Shortness of breath', 160, 10);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1700, 170, 1, 'Not Bothered At All', 0, 1, 0, 'health10_breath');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1701, 170, 2, 'Bothered A Little', 0, 1, 1, 'health10_breath');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1702, 170, 3, 'Bothered A Lot', 0, 1, 2, 'health10_breath');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (171, 2, 'Pain or problems during sexual intercourse', 160, 11);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1710, 171, 1, 'Not Bothered At All', 0, 1, 0, 'health11_sex');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1711, 171, 2, 'Bothered A Little', 0, 1, 1, 'health11_sex');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1712, 171, 3, 'Bothered A Lot', 0, 1, 2, 'health11_sex');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (172, 2, 'Constipation, loose bowels, or diarrhea', 160, 12);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1720, 172, 1, 'Not Bothered At All', 0, 1, 0, 'health12_constipation');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1721, 172, 2, 'Bothered A Little', 0, 1, 1, 'health12_constipation');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1722, 172, 3, 'Bothered A Lot', 0, 1, 2, 'health12_constipation');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (173, 2, 'Nausea, gas or indigestion', 160, 13);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1730, 173, 1, 'Not Bothered At All', 0, 1, 0, 'health13_nausea');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1731, 173, 2, 'Bothered A Little', 0, 1, 1, 'health13_nausea');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1732, 173, 3, 'Bothered A Lot', 0, 1, 2, 'health13_nausea');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (174, 2, 'Feeling tired or having low energy', 160, 14);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1740, 174, 1, 'Not Bothered At All', 0, 1, 0, 'health14_tired');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1741, 174, 2, 'Bothered A Little', 0, 1, 1, 'health14_tired');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1742, 174, 3, 'Bothered A Lot', 0, 1, 2, 'health14_tired');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (175, 2, 'Trouble sleeping', 160, 15);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1750, 175, 1, 'Not Bothered At All', 0, 1, 0, 'health15_sleeping');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1751, 175, 2, 'Bothered A Little', 0, 1, 1, 'health15_sleeping');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1752, 175, 3, 'Bothered A Lot', 0, 1, 2, 'health15_sleeping');

/* 30 Other Health Symptoms page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (180, 6, 'During the past 4 weeks, how much have you been bothered by any of the following problems?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (30, 180, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (181, 2, 'Hearing loss', 180, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1810, 181, 1, 'Not Bothered At All', 0, 1, 0, 'health16_hearing');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1811, 181, 2, 'Bothered A Little', 0, 1, 1, 'health16_hearing');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1812, 181, 3, 'Bothered A Lot', 0, 1, 2, 'health16_hearing');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (182, 2, 'Tinnitus (ringing in the ears)', 180, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1820, 182, 1, 'Not Bothered At All', 0, 1, 0, 'health17_tinnitus');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1821, 182, 2, 'Bothered A Little', 0, 1, 1, 'health17_tinnitus');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1822, 182, 3, 'Bothered A Lot', 0, 1, 2, 'health17_tinnitus');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (183, 2, 'Problem with vision (e.g., double or blurred vision, light sensitivity, difficulty seeing moving objects)', 180, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1830, 183, 1, 'Not Bothered At All', 0, 1, 0, 'health20_vision');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1831, 183, 2, 'Bothered A Little', 0, 1, 1, 'health20_vision');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1832, 183, 3, 'Bothered A Lot', 0, 1, 2, 'health20_vision');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (184, 2, 'Weight gain', 180, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1840, 184, 1, 'Not Bothered At All', 0, 1, 0, 'health21_wghtgain');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1841, 184, 2, 'Bothered A Little', 0, 1, 1, 'health21_wghtgain');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1842, 184, 3, 'Bothered A Lot', 0, 1, 2, 'health21_wghtgain');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (185, 2, 'Weight loss', 180, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1850, 185, 1, 'Not Bothered At All', 0, 1, 0, 'health22_wghtloss');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1851, 185, 2, 'Bothered A Little', 0, 1, 1, 'health22_wghtloss');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1852, 185, 3, 'Bothered A Lot', 0, 1, 2, 'health22_wghtloss');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (186, 2, 'Forgetfulness', 180, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1860, 186, 1, 'Not Bothered At All', 0, 1, 0, 'health23_forgetfulness');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1861, 186, 2, 'Bothered A Little', 0, 1, 1, 'health23_forgetfulness');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1862, 186, 3, 'Bothered A Lot', 0, 1, 2, 'health23_forgetfulness');

/* 31 OOO Infect&Embedded Fragment CR page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (190, 2, 'Do you have any problems with chronic diarrhea or other gastrointestinal complaints since serving in the area of conflict?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (31, 190, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1900, 190, 1, 'No', 0, 1, 0, 'infect1a_gastro');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name, other_export_name) VALUES (1901, 190, 2, 'Yes, what symptoms?', 'other', 1, 1, 1, 'infect1a_gastro', 'infect1b_gastrosympt');	

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (191, 2, 'Do you have any unexplained fevers?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (31, 191, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1910, 191, 1, 'No', 0, 1, 0, 'infect2a_fever');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name, other_export_name) VALUES (1911, 191, 2, 'Yes, what symptoms?', 'other', 1, 1, 1, 'infect2a_fever', 'infect2b_feversympt');	
	
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (192, 2, 'Do you have a persistent papular or nodular skin rash that began after deployment to Southwest Asia?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (31, 192, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1920, 192, 1, 'No', 0, 1, 0, 'infect3a_rash');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name, other_export_name) VALUES (1921, 192, 2, 'Yes, what symptoms?', 'other', 1, 1, 1, 'infect3a_rash', 'infect3b_rashsympt');	

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (193, 2, 'Do you have or suspect you have retained fragments or shrapnel as a result of injuries you received while serving in the area of conflict? For example, were you injured as a result of small arms fire or a blast or explosion caused by an IED, RPG, Landmine, enemy or friendly fire?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (31, 193, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1930, 193, 1, 'No', 0, 1, 0, 'infect4a_fragment');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, calculation_type_id, calculation_value, export_name, other_export_name) VALUES (1931, 193, 2, 'Yes, what symptoms?', 'other', 1, 1, 1, 'infect4a_fragment', 'infect4b_fragmentsympt');	

/* 32 Promis Pain Intensity & Interference page 1 */

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (196, 6, '', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (32, 196, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (197, 2, 'What is your level of pain right now?', 196, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1940, 197, 1, 'No pain', 0, 1, 1, 'pain_level');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1941, 197, 2, 'Mild', 0, 1, 2, 'pain_level');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1942, 197, 3, 'Moderate', 0, 1, 3, 'pain_level');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1943, 197, 4, 'Severe', 0, 1, 4, 'pain_level');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1944, 197, 5, 'Very severe', 0, 1, 5, 'pain_level');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (200, 6, 'In the past 1 week...', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (32, 200, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (201, 2, 'How intense was your pain at its worst?', 200, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2010, 201, 1, 'No pain', 0, 1, 1, 'pain_intensity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2011, 201, 2, 'Mild', 0, 1, 2, 'pain_intensity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2012, 201, 3, 'Moderate', 0, 1, 3, 'pain_intensity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2013, 201, 4, 'Severe', 0, 1, 4, 'pain_intensity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2014, 201, 5, 'Very severe', 0, 1, 5, 'pain_intensity');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (202, 2, 'How intense was your average pain?', 200, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2020, 202, 1, 'No pain', 0, 1, 1, 'pain_average');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2021, 202, 2, 'Mild', 0, 1, 2, 'pain_average');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2022, 202, 3, 'Moderate', 0, 1, 3, 'pain_average');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2023, 202, 4, 'Severe', 0, 1, 4, 'pain_average');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2024, 202, 5, 'Very severe', 0, 1, 5, 'pain_average');

/* 33 Promis Pain Intensity & Interference page 2 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (211, 6, 'In the past 1 week...', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (33, 211, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (212, 2, 'How much did pain interfere with your enjoyment of life?', 211, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2120, 212, 1, 'Not at all', 0, 1, 1, 'pain_interfere_life');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2121, 212, 2, 'A little bit', 0, 1, 2, 'pain_interfere_life');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2122, 212, 3, 'Somewhat', 0, 1, 3, 'pain_interfere_life');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2123, 212, 4, 'Quite a bit', 0, 1, 4, 'pain_interfere_life');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2124, 212, 5, 'Very much', 0, 1, 5, 'pain_interfere_life');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (213, 2, 'How much did pain interfere with your ability to concentrate?', 211, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2130, 213, 1, 'Not at all', 0, 1, 1, 'pain_interfere_conc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2131, 213, 2, 'A little', 0, 1, 2, 'pain_interfere_conc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2132, 213, 3, 'Somewhat', 0, 1, 3, 'pain_interfere_conc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2133, 213, 4, 'Quite a bit', 0, 1, 4, 'pain_interfere_conc');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2134, 213, 5, 'Very much', 0, 1, 5, 'pain_interfere_conc');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (214, 2, 'How much did pain interfere with your day to day activities?', 211, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2140, 214, 1, 'Not at all', 0, 1, 1, 'pain_interfere_day');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2141, 214, 2, 'A little', 0, 1, 2, 'pain_interfere_day');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2142, 214, 3, 'Somewhat', 0, 1, 3, 'pain_interfere_day');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2143, 214, 4, 'Quite a bit', 0, 1, 4, 'pain_interfere_day');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2144, 214, 5, 'Very much', 0, 1, 5, 'pain_interfere_day');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (215, 2, 'How much did pain interfere with your enjoyment of recreational activities?', 211, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2150, 215, 1, 'Not at all', 0, 1, 1, 'pain_interfere_rec');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2151, 215, 2, 'A little', 0, 1, 2, 'pain_interfere_rec');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2152, 215, 3, 'Somewhat', 0, 1, 3, 'pain_interfere_rec');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2153, 215, 4, 'Quite a bit', 0, 1, 4, 'pain_interfere_rec');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2154, 215, 5, 'Very much', 0, 1, 5, 'pain_interfere_rec');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (216, 2, 'How much did pain interfere with doing your tasks away from home (e.g., getting groceries, running errands)?', 211, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2160, 216, 1, 'Not at all', 0, 1, 1, 'pain_interfere_task');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2161, 216, 2, 'A little', 0, 1, 2, 'pain_interfere_task');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2162, 216, 3, 'Somewhat', 0, 1, 3, 'pain_interfere_task');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2163, 216, 4, 'Quite a bit', 0, 1, 4, 'pain_interfere_task');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2164, 216, 5, 'Very much', 0, 1, 5, 'pain_interfere_task');

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (217, 6, '');
INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (218, 2, 'How often did pain keep you from socializing with others?', 217, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (33, 217, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2170, 218, 1, 'Never', 0, 1, 1, 'pain_interfere_social');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2171, 218, 2, 'Rarely', 0, 1, 2, 'pain_interfere_social');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2172, 218, 3, 'Sometimes', 0, 1, 3, 'pain_interfere_social');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2173, 218, 4, 'Often', 0, 1, 4, 'pain_interfere_social');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2174, 218, 5, 'Always', 0, 1, 5, 'pain_interfere_social');

/* 34 Basic Pain page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (220, 2, 'Please indicate the number that represents how intense your pain has been over the <b>past 4 weeks</b>.', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (34, 220, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2199, 220, 0, '0', 1, 0, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2200, 220, 1, '1', 1, 1, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2201, 220, 2, '2', 1, 2, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2202, 220, 3, '3', 1, 3, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2203, 220, 4, '4', 1, 4, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2204, 220, 5, '5', 1, 5, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2205, 220, 6, '6', 1, 6, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2206, 220, 7, '7', 1, 7, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2207, 220, 8, '8', 1, 8, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2208, 220, 9, '9', 1, 9, 'pain_number');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, calculation_type_id, calculation_value, export_name) VALUES (2209, 220, 10, '10', 1, 10, 'pain_number');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (221, 4, 'Please list the places where you feel your pain (example: my knees)', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (34, 221, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, answer_type, answer_value, display_order) VALUES (2210, 221, 'Pain location', 'none', 0, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (222, 1, 'Place where you feel your pain', 221, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (2220, 222, 'pain_area');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (222, 4, 3);

/* 35 Medications page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (230, 4, 'Please list all current prescribed and over-the-counter medications, and nutrition supplements', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (35, 230, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, answer_text, answer_type, answer_value, display_order) VALUES (2300, 230, 'Medication', 'none', 0, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order, is_required) VALUES (231, 1, 'Medication/Supplement', 230, 1, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (2310, 231, 'med');
INSERT INTO measure_validation (measure_id, validation_id, number_value) VALUES (231, 4, 3);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order, is_required) VALUES (232, 1, 'Dosage', 230, 2, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (2320, 232, 'med_dose');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order, is_required) VALUES (233, 1, 'Frequency', 230, 3, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (2330, 233, 'med_freq');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order, is_required) VALUES (234, 1, 'Duration', 230, 4, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (2340, 234, 'med_dur');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order, is_required) VALUES (235, 1, 'Doctor and/or Facility', 230, 5, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, export_name) VALUES (2350, 235, 'med_doc');

/* 36 Prior MH Treatment page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (240, 3, 'Over the past 18 months has a Mental Health Provider (i.e. Psychiatrist, Psychologist, Social Worker) diagnosed you with any of the following', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (36, 240, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (2400, 240, 1, 'None', 'none', 1, 'Prior_dx_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (2401, 240, 2, 'Depression', 0, 'prior_dx_dep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (2402, 240, 3, 'Post Traumatic Stress Disorder (PTSD)', 0, 'prior_dx_ptsd');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (2404, 240, 4, 'Other, please specify', 'other', 1, 'prior_dx_oth', 'prior_dx_oth_spec');	

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (241, 3, 'In the past 18 months have you had any of the following treatments for your mental health diagnosis?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (36, 241, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (2410, 241, 1, 'None', 'none', 1, 'Prior_tx_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (2411, 241, 2, 'Inpatient psychiatric stay', 0, 'prior_tx_inpt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (2412, 241, 3, 'Psychotherapy', 0, 'prior_tx_thpy');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (2413, 241, 4, 'Psychiatric medication', 0, 'prior_tx_med');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (2414, 241, 5, 'Electro convulsive therapy', 0, 'prior_tx_ect');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (2415, 241, 6, 'Other, please specify', 'other', 1, 'prior_tx_oth', 'prior_tx_oth_spec');	

/* 40 WHODAS 2.0 page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (251, 6, 'This questionnaire asks about difficulties due to health/mental health conditions. Health conditions include diseases or illnesses, other health problems that may be short or long lasting, injuries, mental or emotional problems, and problems with alcohol or drugs. Think back over the past 30 days and answer these questions thinking about how much difficulty you had doing the following activities.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (40, 251, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (252, 2, 'Concentrating on doing something for ten minutes?', 251, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2520, 252, 1, 'None', 0, 1, 1, 'whodas1_1_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2521, 252, 2, 'Mild', 0, 1, 2, 'whodas1_1_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2522, 252, 3, 'Moderate', 0, 1, 3, 'whodas1_1_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2523, 252, 4, 'Severe', 0, 1, 4, 'whodas1_1_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2524, 252, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas1_1_concentrate');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (253, 2, 'Remembering to do important things?', 251, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2530, 253, 1, 'None', 0, 1, 1, 'whodas1_2_remember');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2531, 253, 2, 'Mild', 0, 1, 2, 'whodas1_2_remember');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2532, 253, 3, 'Moderate', 0, 1, 3, 'whodas1_2_remember');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2533, 253, 4, 'Severe', 0, 1, 4, 'whodas1_2_remember');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2534, 253, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas1_2_remember');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (254, 2, 'Analyzing and finding solutions to problems in day-to-day life?', 251, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2540, 254, 1, 'None', 0, 1, 1, 'whodas1_3_solution');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2541, 254, 2, 'Mild', 0, 1, 2, 'whodas1_3_solution');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2542, 254, 3, 'Moderate', 0, 1, 3, 'whodas1_3_solution');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2543, 254, 4, 'Severe', 0, 1, 4, 'whodas1_3_solution');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2544, 254, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas1_3_solution');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (255, 2, 'Learning a new task, for example, learning how to get to a new place?', 251, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2550, 255, 1, 'None', 0, 1, 1, 'whodas1_4_new');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2551, 255, 2, 'Mild', 0, 1, 2, 'whodas1_4_new');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2552, 255, 3, 'Moderate', 0, 1, 3, 'whodas1_4_new');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2553, 255, 4, 'Severe', 0, 1, 4, 'whodas1_4_new');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2554, 255, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas1_4_new');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (256, 2, 'Generally understanding what people say?', 251, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2560, 256, 1, 'None', 0, 1, 1, 'whodas1_5_understand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2561, 256, 2, 'Mild', 0, 1, 2, 'whodas1_5_understand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2562, 256, 3, 'Moderate', 0, 1, 3, 'whodas1_5_understand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2563, 256, 4, 'Severe', 0, 1, 4, 'whodas1_5_understand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2564, 256, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas1_5_understand');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (257, 2, 'Starting and maintaining a conversation?', 251, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2570, 257, 1, 'None', 0, 1, 1, 'whodas1_6_conversation');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2571, 257, 2, 'Mild', 0, 1, 2, 'whodas1_6_conversation');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2572, 257, 3, 'Moderate', 0, 1, 3, 'whodas1_6_conversation');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2573, 257, 4, 'Severe', 0, 1, 4, 'whodas1_6_conversation');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2574, 257, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas1_6_conversation');

/* 41 WHODAS 2.0 page 2 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (261, 6, 'This questionnaire asks about difficulties due to health/mental health conditions. Health conditions include diseases or illnesses, other health problems that may be short or long lasting, injuries, mental or emotional problems, and problems with alcohol or drugs. Think back over the past 30 days and answer these questions thinking about how much difficulty you had doing the following activities.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (41, 261, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (262, 2, 'Standing for long periods, such as 30 minutes?', 261, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2620, 262, 1, 'None', 0, 1, 1, 'whodas2_1_stand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2621, 262, 2, 'Mild', 0, 1, 2, 'whodas2_1_stand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2622, 262, 3, 'Moderate', 0, 1, 3, 'whodas2_1_stand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2623, 262, 4, 'Severe', 0, 1, 4, 'whodas2_1_stand');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2624, 262, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas2_1_stand');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (263, 2, 'Standing up from sitting down?', 261, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2630, 263, 1, 'None', 0, 1, 1, 'whodas2_2_standup');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2631, 263, 2, 'Mild', 0, 1, 2, 'whodas2_2_standup');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2632, 263, 3, 'Moderate', 0, 1, 3, 'whodas2_2_standup');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2633, 263, 4, 'Severe', 0, 1, 4, 'whodas2_2_standup');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2634, 263, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas2_2_standup');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (264, 2, 'Moving around inside your home?', 261, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2640, 264, 1, 'None', 0, 1, 1, 'whodas2_3_move');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2641, 264, 2, 'Mild', 0, 1, 2, 'whodas2_3_move');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2642, 264, 3, 'Moderate', 0, 1, 3, 'whodas2_3_move');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2643, 264, 4, 'Severe', 0, 1, 4, 'whodas2_3_move');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2644, 264, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas2_3_move');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (265, 2, 'Getting out of your home?', 261, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2650, 265, 1, 'None', 0, 1, 1, 'whodas2_4_getout');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2651, 265, 2, 'Mild', 0, 1, 2, 'whodas2_4_getout');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2652, 265, 3, 'Moderate', 0, 1, 3, 'whodas2_4_getout');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2653, 265, 4, 'Severe', 0, 1, 4, 'whodas2_4_getout');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2654, 265, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas2_4_getout');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (266, 2, 'Walking a long distance, such as a kilometer (or equivalent)?', 261, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2660, 266, 1, 'None', 0, 1, 1, 'whodas2_5_walk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2661, 266, 2, 'Mild', 0, 1, 2, 'whodas2_5_walk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2662, 266, 3, 'Moderate', 0, 1, 3, 'whodas2_5_walk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2663, 266, 4, 'Severe', 0, 1, 4, 'whodas2_5_walk');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2664, 266, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas2_5_walk');

/* 42 WHODAS 2.0 page 3 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (271, 6, 'This questionnaire asks about difficulties due to health/mental health conditions. Health conditions include diseases or illnesses, other health problems that may be short or long lasting, injuries, mental or emotional problems, and problems with alcohol or drugs. Think back over the past 30 days and answer these questions thinking about how much difficulty you had doing the following activities.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (42, 271, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (272, 2, 'Washing your whole body?', 271, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2720, 272, 1, 'None', 0, 1, 1, 'whodas3_1_wash');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2721, 272, 2, 'Mild', 0, 1, 2, 'whodas3_1_wash');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2722, 272, 3, 'Moderate', 0, 1, 3, 'whodas3_1_wash');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2723, 272, 4, 'Severe', 0, 1, 4, 'whodas3_1_wash');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2724, 272, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas3_1_wash');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (273, 2, 'Getting dressed?', 271, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2730, 273, 1, 'None', 0, 1, 1, 'whodas3_2_dressed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2731, 273, 2, 'Mild', 0, 1, 2, 'whodas3_2_dressed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2732, 273, 3, 'Moderate', 0, 1, 3, 'whodas3_2_dressed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2733, 273, 4, 'Severe', 0, 1, 4, 'whodas3_2_dressed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2734, 273, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas3_2_dressed');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (274, 2, 'Eating?', 271, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2740, 274, 1, 'None', 0, 1, 1, 'whodas3_3_eat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2741, 274, 2, 'Mild', 0, 1, 2, 'whodas3_3_eat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2742, 274, 3, 'Moderate', 0, 1, 3, 'whodas3_3_eat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2743, 274, 4, 'Severe', 0, 1, 4, 'whodas3_3_eat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2744, 274, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas3_3_eat');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (275, 2, 'Staying by yourself for a few days?', 271, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2750, 275, 1, 'None', 0, 1, 1, 'whodas3_4_stay');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2751, 275, 2, 'Mild', 0, 1, 2, 'whodas3_4_stay');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2752, 275, 3, 'Moderate', 0, 1, 3, 'whodas3_4_stay');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2753, 275, 4, 'Severe', 0, 1, 4, 'whodas3_4_stay');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2754, 275, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas3_4_stay');

/* 43 WHODAS 2.0 page 4 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (281, 6, 'This questionnaire asks about difficulties due to health/mental health conditions. Health conditions include diseases or illnesses, other health problems that may be short or long lasting, injuries, mental or emotional problems, and problems with alcohol or drugs. Think back over the past 30 days and answer these questions thinking about how much difficulty you had doing the following activities.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (43, 281, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (282, 2, 'Dealing with people you do not know?', 281, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2820, 282, 1, 'None', 0, 1, 1, 'whodas4_1_deal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2821, 282, 2, 'Mild', 0, 1, 2, 'whodas4_1_deal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2822, 282, 3, 'Moderate', 0, 1, 3, 'whodas4_1_deal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2823, 282, 4, 'Severe', 0, 1, 4, 'whodas4_1_deal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2824, 282, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas4_1_deal');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (283, 2, 'Maintaining a friendship?', 281, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2830, 283, 1, 'None', 0, 1, 1, 'whodas4_2_friend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2831, 283, 2, 'Mild', 0, 1, 2, 'whodas4_2_friend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2832, 283, 3, 'Moderate', 0, 1, 3, 'whodas4_2_friend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2833, 283, 4, 'Severe', 0, 1, 4, 'whodas4_2_friend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2834, 283, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas4_2_friend');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (284, 2, 'Getting along with people who are close to you?', 281, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2840, 284, 1, 'None', 0, 1, 1, 'whodas4_3_getalong');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2841, 284, 2, 'Mild', 0, 1, 2, 'whodas4_3_getalong');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2842, 284, 3, 'Moderate', 0, 1, 3, 'whodas4_3_getalong');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2843, 284, 4, 'Severe', 0, 1, 4, 'whodas4_3_getalong');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2844, 284, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas4_3_getalong');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (285, 2, 'Making new friends?', 281, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2850, 285, 1, 'None', 0, 1, 1, 'whodas4_4_newfriend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2851, 285, 2, 'Mild', 0, 1, 2, 'whodas4_4_newfriend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2852, 285, 3, 'Moderate', 0, 1, 3, 'whodas4_4_newfriend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2853, 285, 4, 'Severe', 0, 1, 4, 'whodas4_4_newfriend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2854, 285, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas4_4_newfriend');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (286, 2, 'Sexual activities?', 281, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2860, 286, 1, 'None', 0, 1, 1, 'whodas4_5_sexual');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2861, 286, 2, 'Mild', 0, 1, 2, 'whodas4_5_sexual');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2862, 286, 3, 'Moderate', 0, 1, 3, 'whodas4_5_sexual');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2863, 286, 4, 'Severe', 0, 1, 4, 'whodas4_5_sexual');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2864, 286, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas4_5_sexual');

/* 44 WHODAS 2.0 page 5 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (291, 6, 'This questionnaire asks about difficulties due to health/mental health conditions. Health conditions include diseases or illnesses, other health problems that may be short or long lasting, injuries, mental or emotional problems, and problems with alcohol or drugs. Think back over the past 30 days and answer these questions thinking about how much difficulty you had doing the following activities.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (44, 291, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (292, 2, 'Taking care of your household responsibilities?', 291, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2920, 292, 1, 'None', 0, 1, 1, 'whodas5_1_housecare');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2921, 292, 2, 'Mild', 0, 1, 2, 'whodas5_1_housecare');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2922, 292, 3, 'Moderate', 0, 1, 3, 'whodas5_1_housecare');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2923, 292, 4, 'Severe', 0, 1, 4, 'whodas5_1_housecare');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2924, 292, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas5_1_housecare');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (293, 2, 'Doing most important household tasks well?', 291, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2930, 293, 1, 'None', 0, 1, 1, 'whoda5_2_housetask');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2931, 293, 2, 'Mild', 0, 1, 2, 'whoda5_2_housetask');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2932, 293, 3, 'Moderate', 0, 1, 3, 'whoda5_2_housetask');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2933, 293, 4, 'Severe', 0, 1, 4, 'whoda5_2_housetask');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2934, 293, 5, 'Extreme or cannot do', 0, 1, 5, 'whoda5_2_housetask');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (294, 2, 'Getting all of the household work done that you needed to do?', 291, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2940, 294, 1, 'None', 0, 1, 1, 'whodas5_3_housedone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2941, 294, 2, 'Mild', 0, 1, 2, 'whodas5_3_housedone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2942, 294, 3, 'Moderate', 0, 1, 3, 'whodas5_3_housedone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2943, 294, 4, 'Severe', 0, 1, 4, 'whodas5_3_housedone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2944, 294, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas5_3_housedone');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (295, 2, 'Getting your household work done as quickly as needed?', 291, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2950, 295, 1, 'None', 0, 1, 1, 'whodas5_4_housequickly');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2951, 295, 2, 'Mild', 0, 1, 2, 'whodas5_4_housequickly');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2952, 295, 3, 'Moderate', 0, 1, 3, 'whodas5_4_housequickly');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2953, 295, 4, 'Severe', 0, 1, 4, 'whodas5_4_housequickly');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (2954, 295, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas5_4_housequickly');

/* 45 WHODAS 2.0 page 6 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (300, 2, 'Do you work (paid, non-paid, self-employed) or go to school?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (45, 300, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3000, 300, 1, 'No', 0, 1, 0, 'whodas_work');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3001, 300, 2, 'Yes', 0, 1, 1, 'whodas_work');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (302, 6, 'Because of your health condition, in the past 30 days, how much difficulty did you have in', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (45, 302, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (303, 2, 'Your day-to-day work/school?', 302, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3030, 303, 1, 'None', 0, 1, 1, 'whodas5_5_daily');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3031, 303, 2, 'Mild', 0, 1, 2, 'whodas5_5_daily');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3032, 303, 3, 'Moderate', 0, 1, 3, 'whodas5_5_daily');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3033, 303, 4, 'Severe', 0, 1, 4, 'whodas5_5_daily');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3034, 303, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas5_5_daily');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (304, 2, 'Doing your most important work/school tasks well?', 302, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3040, 304, 1, 'None', 0, 1, 1, 'whodas5_6_workwell');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3041, 304, 2, 'Mild', 0, 1, 2, 'whodas5_6_workwell');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3042, 304, 3, 'Moderate', 0, 1, 3, 'whodas5_6_workwell');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3043, 304, 4, 'Severe', 0, 1, 4, 'whodas5_6_workwell');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3044, 304, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas5_6_workwell');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (305, 2, 'Getting all of the work done that you need to do?', 302, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3050, 305, 1, 'None', 0, 1, 1, 'whodas5_7_workdone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3051, 305, 2, 'Mild', 0, 1, 2, 'whodas5_7_workdone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3052, 305, 3, 'Moderate', 0, 1, 3, 'whodas5_7_workdone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3053, 305, 4, 'Severe', 0, 1, 4, 'whodas5_7_workdone');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3054, 305, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas5_7_workdone');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (306, 2, 'Getting your work done as quickly as needed?', 302, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3060, 306, 1, 'None', 0, 1, 1, 'whodas5_8_workquickly');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3061, 306, 2, 'Mild', 0, 1, 2, 'whodas5_8_workquickly');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3062, 306, 3, 'Moderate', 0, 1, 3, 'whodas5_8_workquickly');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3063, 306, 4, 'Severe', 0, 1, 4, 'whodas5_8_workquickly');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3064, 306, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas5_8_workquickly');

/* 46 WHODAS 2.0 page 7 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (311, 6, 'This questionnaire asks about difficulties due to health/mental health conditions. Health conditions include diseases or illnesses, other health problems that may be short or long lasting, injuries, mental or emotional problems, and problems with alcohol or drugs. Think back over the past 30 days and answer these questions thinking about how much difficulty you had doing the following activities.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (46, 311, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (312, 2, 'How much of a problem did you have in joining in community activities (for example, festivities, religious, or other activities) in the same way as anyone else can?', 311, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3120, 312, 1, 'None', 0, 1, 1, 'whodas6_1_community');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3121, 312, 2, 'Mild', 0, 1, 2, 'whodas6_1_community');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3122, 312, 3, 'Moderate', 0, 1, 3, 'whodas6_1_community');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3123, 312, 4, 'Severe', 0, 1, 4, 'whodas6_1_community');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3124, 312, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas6_1_community');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (313, 2, 'How much of a problem did you have because of barriers or hindrances around you?', 311, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3130, 313, 1, 'None', 0, 1, 1, 'whodas6_2_barriers');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3131, 313, 2, 'Mild', 0, 1, 2, 'whodas6_2_barriers');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3132, 313, 3, 'Moderate', 0, 1, 3, 'whodas6_2_barriers');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3133, 313, 4, 'Severe', 0, 1, 4, 'whodas6_2_barriers');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3134, 313, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas6_2_barriers');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (314, 2, 'How much of a problem did you have living with dignity because of the attitudes and actions of others?', 311, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3140, 314, 1, 'None', 0, 1, 1, 'whodas6_3_dignity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3141, 314, 2, 'Mild', 0, 1, 2, 'whodas6_3_dignity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3142, 314, 3, 'Moderate', 0, 1, 3, 'whodas6_3_dignity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3143, 314, 4, 'Severe', 0, 1, 4, 'whodas6_3_dignity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3144, 314, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas6_3_dignity');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (315, 2, 'How much time did you spend on your health condition or its consequences?', 311, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3150, 315, 1, 'None', 0, 1, 1, 'whodas6_4_time');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3151, 315, 2, 'Mild', 0, 1, 2, 'whodas6_4_time');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3152, 315, 3, 'Moderate', 0, 1, 3, 'whodas6_4_time');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3153, 315, 4, 'Severe', 0, 1, 4, 'whodas6_4_time');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3154, 315, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas6_4_time');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (316, 2, 'How much have you been emotionally affected by your health condition?', 311, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3160, 316, 1, 'None', 0, 1, 1, 'whodas6_5_emotion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3161, 316, 2, 'Mild', 0, 1, 2, 'whodas6_5_emotion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3162, 316, 3, 'Moderate', 0, 1, 3, 'whodas6_5_emotion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3163, 316, 4, 'Severe', 0, 1, 4, 'whodas6_5_emotion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3164, 316, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas6_5_emotion');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (317, 2, 'How much has your health been a drain on the financial resources of you or your family?', 311, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3170, 317, 1, 'None', 0, 1, 1, 'whodas6_6_finance');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3171, 317, 2, 'Mild', 0, 1, 2, 'whodas6_6_finance');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3172, 317, 3, 'Moderate', 0, 1, 3, 'whodas6_6_finance');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3173, 317, 4, 'Severe', 0, 1, 4, 'whodas6_6_finance');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3174, 317, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas6_6_finance');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (318, 2, 'How much of a problem did your family have because of your health problems?', 311, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3180, 318, 1, 'None', 0, 1, 1, 'whodas6_7_family');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3181, 318, 2, 'Mild', 0, 1, 2, 'whodas6_7_family');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3182, 318, 3, 'Moderate', 0, 1, 3, 'whodas6_7_family');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3183, 318, 4, 'Severe', 0, 1, 4, 'whodas6_7_family');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3184, 318, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas6_7_family');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (319, 2, 'How much of a problem did you have in doing things by yourself for relaxation or pleasure?', 311, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3190, 319, 1, 'None', 0, 1, 1, 'whodas6_8_relax');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3191, 319, 2, 'Mild', 0, 1, 2, 'whodas6_8_relax');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3192, 319, 3, 'Moderate', 0, 1, 3, 'whodas6_8_relax');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3193, 319, 4, 'Severe', 0, 1, 4, 'whodas6_8_relax');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3194, 319, 5, 'Extreme or cannot do', 0, 1, 5, 'whodas6_8_relax');

/* 50 Caffeine Use page 1 */

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (329, 6, '');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (50, 329, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (330, 2, 'In the past 4 weeks how many servings of caffeinated beverages did you drink per day (e.g., coffee, Red Bull, etc.)?', 329, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3300, 330, 1, 'None', 0, 1, 0, 'caf_1');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3301, 330, 2, '1-2', 0, 1, 1, 'caf_1');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3302, 330, 3, '3-4', 0, 1, 2, 'caf_1');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3303, 330, 4, '5+', 0, 1, 3, 'caf_1');

/* 51 Tobacco Cessation Screen 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (341, 2, 'Do you use tobacco currently?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 341, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3410, 341, 1, 'Never. I have never used tobacco on a regular basis', 0, 1, 0, 'tob_use');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3411, 341, 2, 'No. I used tobacco in the past, but have quit', 0, 1, 1, 'tob_use');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3412, 341, 3, 'Yes. I currently use tobacco on a regular basis', 0, 1, 2, 'tob_use');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (342, 2, 'When did you quit using tobacco on a regular basis?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 342, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3420, 342, 1, 'I quit using tobacco more than 7 years ago', 0, 1, 5, 'tob_quit_when');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3421, 342, 2, 'I quit using tobacco more than 1 year, but less than 7 years ago', 0, 1, 3, 'tob_quit_when');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3422, 342, 3, 'I quit using tobacco within the last 12 months', 0, 1, 4, 'tob_quit_when');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (343, 8, 'When did you quit?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 343, 3);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (344, 2, 'Month', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 344, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3440, 344, 1, 'January', 0, 1, 1, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3441, 344, 2, 'Feburary', 0, 1, 2, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3442, 344, 3, 'March', 0, 1, 3, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3443, 344, 4, 'April', 0, 1, 4, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3444, 344, 5, 'May', 0, 1, 5, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3445, 344, 6, 'June', 0, 1, 6, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3446, 344, 7, 'July', 0, 1, 7, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3447, 344, 8, 'August', 0, 1, 8, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3448, 344, 9, 'September', 0, 1, 9, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3449, 344, 10, 'October', 0, 1, 10, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3450, 344, 11, 'November', 0, 1, 11, 'tob_quit_month');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3451, 344, 12, 'December', 0, 1, 12, 'tob_quit_month');

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (346, 1, 'Year', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 346, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (3460, 346, 3, 'tob_quit_date');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (346, 9, 4);
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (346, 6, 1900);
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (346, 7, 2025);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (347, 3, 'If yes, Which types of tobacco do you use', 0, 1);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 347, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (3470, 347, 1, 'Cigarettes',1,'tob_ciggs');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (3471, 347, 2, 'Cigars/pipes',1,'tob_cigar');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (3472, 347, 3, 'Smokeless tobacco (snuff/chewing)',1,'tob_smokeless');

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (348, 1, 'How many cigarettes do you smoke, and for how many years?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 348, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (3480, 348, 3, 'tob_cigg_quant_length');

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (349, 1, 'How many cigars/pipes do you smoke, and for how many years?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 349, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (3490, 349, 3, 'tob_pipe_quant_length');

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (350, 1, 'How much smokeless tobacco do you use, and for how many years?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (51, 350, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (3500, 350, 3, 'tob_chew_quant_length');

/* 55 AUDIT-C Screen 1 */

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (367, 6, 'How often did you have a drink containing alcohol in the <u>past 52 weeks</u>?<br/>Consider a drink a bottle of beer, a glass of wine, a wine cooler, one cocktail or a shot of hard liquor (like scotch, gin, or vodka).', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (55, 367, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (370, 2, '', 367, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3700, 370, 1, 'Never', 0, 1, 0, 'alc_drinkyr');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3701, 370, 2, 'Monthly or less', 0, 1, 1, 'alc_drinkyr');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3702, 370, 3, '2-4 times per month', 0, 1, 2, 'alc_drinkyr');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3703, 370, 4, '2-3 times per week', 0, 1, 3, 'alc_drinkyr');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3704, 370, 5, '4 or more times per week', 0, 1, 4, 'alc_drinkyr');


INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (368, 6, 'How many drinks containing alcohol did you have on a typical day when you were drinking in the past 52 weeks?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (55, 368, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (371, 2, '', 368, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3710, 371, 1, 'None', 0, 1, 0, 'alc_drinkday');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3711, 371, 2, '1-2 drinks', 0, 1, 0, 'alc_drinkday');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3712, 371, 3, '3-4 drinks', 0, 1, 1, 'alc_drinkday');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3713, 371, 4, '5-6 drinks', 0, 1, 2, 'alc_drinkday');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3714, 371, 5, '7-9 drinks', 0, 1, 3, 'alc_drinkday');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3715, 371, 6, '10+', 0, 1, 4, 'alc_drinkday');


INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (369, 6, 'How often did you have six or more drinks on one occasion in the past 52 weeks?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (55, 369, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (372, 2, '', 369, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3720, 372, 1, 'Never', 0, 1, 0, 'alc_drink6');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3721, 372, 2, 'Less than monthly', 0, 1, 0, 'alc_drink6');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3722, 372, 3, 'Monthly', 0, 1, 1, 'alc_drink6');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3723, 372, 4, 'Weekly', 0, 1, 2, 'alc_drink6');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3724, 372, 5, 'Daily', 0, 1, 3, 'alc_drink6');

/* 56 DAST 10 page 1 */  
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (381, 6, "The following questions concern information about your possible involvement with drugs not including alcoholic beverages during the past 52 weeks. Carefully read each statement and decide if your answer is 'Yes' or 'No.' Then, check the appropriate response beside the question. In the statements, 'drug abuse' refers to: (1) the use of prescribed or over-the-counter drugs in excess of the directions and (2) and non-medical use of drugs.", 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (56, 381, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (382, 2, 'Have you used drugs other than those required for medical reasons?', 381, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3820, 382, 1, 'No', 0, 1, 0, 'DAST1_other');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3821, 382, 2, 'Yes', 0, 1, 1, 'DAST1_other');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (383, 2, 'Do you abuse more than one drug at a time?', 381, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3830, 383, 1, 'No', 0, 1, 0, 'DAST2_abuse');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3831, 383, 2, 'Yes', 0, 1, 1, 'DAST2_abuse');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (384, 2, 'Are you always able to stop using drugs when you want to?', 381, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3840, 384, 1, 'No', 0, 1, 0, 'DAST3_ablestop');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3841, 384, 2, 'Yes', 0, 1, 1, 'DAST3_ablestop');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (385, 2, 'Have you had "blackouts" or "flashbacks" as a result of drug use?', 381, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3850, 385, 1, 'No', 0, 1, 0, 'DAST4_blackout');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3851, 385, 2, 'Yes', 0, 1, 1, 'DAST4_blackout');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (386, 2, 'Do you ever feel bad or guilty about your drug use?', 381, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3860, 386, 1, 'No', 0, 1, 0, 'DAST5_guilty');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3861, 386, 2, 'Yes', 0, 1, 1, 'DAST5_guilty');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (387, 2, 'Does your spouse (or parents) ever complain about your involvement with drugs?', 381, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3870, 387, 1, 'No', 0, 1, 0, 'DAST6_complain');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3871, 387, 2, 'Yes', 0, 1, 1, 'DAST6_complain');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (388, 2, 'Have you neglected your family because of your use of drugs?', 381, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3880, 388, 1, 'No', 0, 1, 0, 'DAST7_neglect');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3881, 388, 2, 'Yes', 0, 1, 1, 'DAST7_neglect');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (389, 2, 'Have you engaged in illegal activities in order to obtain drugs?', 381, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3890, 389, 1, 'No', 0, 1, 0, 'DAST8_illegal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3891, 389, 2, 'Yes', 0, 1, 1, 'DAST8_illegal');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (390, 2, 'Have you ever experienced withdrawal symptoms (felt sick) when you stopped taking drugs?', 381, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3900, 390, 1, 'No', 0, 1, 0, 'DAST9_withdraw');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3901, 390, 2, 'Yes', 0, 1, 1, 'DAST9_withdraw');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (391, 2, 'Have you had medical problems as a result of your drug use (e.g., memory loss, hepatitis, convulsions, bleeding, etc.)?', 381, 10);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3910, 391, 1, 'No', 0, 1, 0, 'DAST10_medical');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (3911, 391, 2, 'Yes', 0, 1, 1, 'DAST10_medical');

/* 57 AV Hallucinations page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (401, 6, 'During the past 4 weeks, how much have you been bothered by any of the following problems?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (57, 401, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (402, 2, "Hearing things other people couldn't hear (e.g., voices)", 401, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4020, 402, 1, 'Not bothered at all', 0, 1, 0, 'health18_voices');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4021, 402, 2, 'Bothered a little', 0, 1, 1, 'health18_voices');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4022, 402, 3, 'Bothered a lot', 0, 1, 2, 'health18_voices');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (403, 2, "Seeing things or having visions other people couldn't see (e.g., visions)", 401, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4030, 403, 1, 'Not bothered at all', 0, 1, 0, 'health19_seeing');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4031, 403, 2, 'Bothered a little', 0, 1, 1, 'health19_seeing');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4032, 403, 3, 'Bothered a lot', 0, 1, 2, 'health19_seeing');

/* 60 BTBIS page 1 */

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (412, 3, 'During any of your OEF/OIF deployments did you experience any of the following events?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (60, 412, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (4130, 412, 6, 'None', 'none', 1, 'tbi_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4131, 412, 0, 'Blast or explosion (IED, RPG, Land Mine, Grenade, etc.)', 1, 'tbi_blast');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4132, 412, 1, 'Vehicular accident/crash (any vehicle including aircraft)', 1, 'tbi_vehicle');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4133, 412, 2, 'Fragment wound or bullet wound above the shoulders', 1, 'tbi_fragment');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4134, 412, 3, 'Fall', 1, 'tbi_fall');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4135, 412, 4, 'Blow to head (hit by falling/flying object, head hit by another person, head hit against something, etc.)', 1, 'tbi_blow');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name, other_export_name) VALUES (4136, 412, 5, 'Other injury to head', 'other', 1, 'tbi_otherinj', 'tbi_otherinjspec');


INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (413, 3, 'Did you have any of these symptoms IMMEDIATELY afterwards?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (60, 413, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (4155, 413, 5, 'None', 'none', 1, 'tbi_immed_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4156, 413, 0, 'Loss of consciousness/"knocked out"', 1, 'tbi_immed_loss');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4157, 413, 1, 'Being dazed, confused or "seeing stars"', 1, 'tbi_immed_dazed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4158, 413, 2, 'Not remembering the event', 1, 'tbi_immed_memory');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4159, 413, 3, 'Concussion', 1, 'tbi_immed_concussion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4160, 413, 4, 'Head injury', 1, 'tbi_immed_headinj');


INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (414, 3, 'Did any of the following problems begin or get worse afterwards?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (60, 414, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (4180, 414, 6, 'None', 'none', 1, 'tbi_worse_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4181, 414, 0, 'Memory problems or lapses', 1, 'tbi_worse_memory');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4182, 414, 1, 'Balance problems or dizziness', 1, 'tbi_worse_balance');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4183, 414, 2, 'Sensitivity to bright light', 1, 'tbi_worse_light');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4184, 414, 3, 'Irritability', 1, 'tbi_worse_irritable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4185, 414, 4, 'Headaches', 1, 'tbi_worse_headache');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4186, 414, 5, 'Sleep problems', 1, 'tbi_worse_sleep');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (415, 3, 'In the past 1 week have you had any of the problems from the question above?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (60, 415, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_type, answer_value, export_name) VALUES (4206, 415, 6, 'None', 'none', 1, 'tbi_week_none');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4207, 415, 0, 'Memory problems or lapses', 1, 'tbi_week_memory');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4208, 415, 1, 'Balance problems or dizziness', 1, 'tbi_week_balance');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4209, 415, 2, 'Sensitivity to bright light', 1, 'tbi_week_light');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4210, 415, 3, 'Irritability', 1, 'tbi_week_irritable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4211, 415, 4, 'Headaches', 1, 'tbi_week_headache');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, export_name) VALUES (4212, 415, 5, 'Sleep problems', 1, 'tbi_week_sleep');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (443, 2, 'If you screen positive for possible Traumatic Brain Injury a consult for further assessment will be submitted unless you decline. Do you want a consult placed for more thorough testing?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (60, 443, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4430, 443, 1, 'No, I decline a consult at this time', 0, 1, 0, 'tbi_consult');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4431, 443, 2, 'Yes, place a consult', 0, 1, 1, 'tbi_consult');

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (444, 1, 'Where were you deployed when the head injury occurred?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (60, 444, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (4440, 444, 3, 'TBI_consult_where');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (444, 4, 3);

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (445, 1, 'What year did it occur?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (60, 445, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (4450, 445, 3, 'TBI_consult_when');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (445, 9, 4);
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (445, 6, 1900);
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (445, 7, 2025);

INSERT INTO measure (measure_id, measure_type_id, measure_text, score_weight, is_required) VALUES (446, 1, 'How did the head injury occur?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (60, 446, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, calculation_type_id, export_name) VALUES (4460, 446, 3, 'TBI_consult_how');
INSERT INTO measure_validation ( measure_id, validation_id, number_value) VALUES (446, 4, 3);


/* 65 PHQ-9 page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (451, 6, 'Over the past 2 weeks, how often have you been bothered by any of the following problems?<br/>Read each item carefully, and select the box to indicate how often you were bothered.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (65, 451, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (452, 2, 'Little interest or pleasure in doing things', 451, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4520, 452, 1, 'Not at all', 0, 1, 0, 'dep1_interest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4521, 452, 2, 'Several days', 0, 1, 1, 'dep1_interest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4522, 452, 3, 'More than half the days', 0, 1, 2, 'dep1_interest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4523, 452, 4, 'Nearly every day', 0, 1, 3, 'dep1_interest');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (453, 2, 'Feeling down, depressed, or hopeless', 451, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4530, 453, 1, 'Not at all', 0, 1, 0, 'dep2_down');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4531, 453, 2, 'Several days', 0, 1, 1, 'dep2_down');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4532, 453, 3, 'More than half the days', 0, 1, 2, 'dep2_down');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4533, 453, 4, 'Nearly every day', 0, 1, 3, 'dep2_down');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (454, 2, 'Trouble falling asleep, staying asleep, or sleeping too much', 451, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4540, 454, 1, 'Not at all', 0, 1, 0, 'dep3_sleep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4541, 454, 2, 'Several days', 0, 1, 1, 'dep3_sleep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4542, 454, 3, 'More than half the days', 0, 1, 2, 'dep3_sleep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4543, 454, 4, 'Nearly every day', 0, 1, 3, 'dep3_sleep');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (455, 2, 'Feeling tired or having little energy', 451, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4550, 455, 1, 'Not at all', 0, 1, 0, 'dep4_tired');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4551, 455, 2, 'Several days', 0, 1, 1, 'dep4_tired');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4552, 455, 3, 'More than half the days', 0, 1, 2, 'dep4_tired');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4553, 455, 4, 'Nearly every day', 0, 1, 3, 'dep4_tired');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (456, 2, 'Poor appetite or overeating', 451, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4560, 456, 1, 'Not at all', 0, 1, 0, 'dep5_appetite');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4561, 456, 2, 'Several days', 0, 1, 1, 'dep5_appetite');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4562, 456, 3, 'More than half the days', 0, 1, 2, 'dep5_appetite');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4563, 456, 4, 'Nearly every day', 0, 1, 3, 'dep5_appetite');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (457, 2, 'Feeling bad about yourself, feeling that you are a failure, or feeling that you have let yourself or your family down', 451, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4570, 457, 1, 'Not at all', 0, 1, 0, 'dep6_feelbad');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4571, 457, 2, 'Several days', 0, 1, 1, 'dep6_feelbad');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4572, 457, 3, 'More than half the days', 0, 1, 2, 'dep6_feelbad');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4573, 457, 4, 'Nearly every day', 0, 1, 3, 'dep6_feelbad');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (458, 2, 'Trouble concentrating on things such as reading the newspaper or watching television', 451, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4580, 458, 1, 'Not at all', 0, 1, 0, 'dep7_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4581, 458, 2, 'Several days', 0, 1, 1, 'dep7_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4582, 458, 3, 'More than half the days', 0, 1, 2, 'dep7_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4583, 458, 4, 'Nearly every day', 0, 1, 3, 'dep7_concentrate');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (459, 2, 'Moving or speaking so slowly that other people could have noticed. Or being so fidgety or restless that you have been moving around a lot more than usual', 451, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4590, 459, 1, 'Not at all', 0, 1, 0, 'dep8_moveslow');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4591, 459, 2, 'Several days', 0, 1, 1, 'dep8_moveslow');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4592, 459, 3, 'More than half the days', 0, 1, 2, 'dep8_moveslow');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4593, 459, 4, 'Nearly every day', 0, 1, 3, 'dep8_moveslow');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (460, 2, 'Thinking that you would be better off dead or that you want to hurt yourself in some way', 451, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4600, 460, 1, 'Not at all', 0, 1, 0, 'dep9_dead');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4601, 460, 2, 'Several days', 0, 1, 1, 'dep9_dead');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4602, 460, 3, 'More than half the days', 0, 1, 2, 'dep9_dead');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4603, 460, 4, 'Nearly every day', 0, 1, 3, 'dep9_dead');

/* single select matrix with a single question (this is done to support how cprs shows this standard question) */ 
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (462, 6, '', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (65, 462, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (461, 2, 'If you checked off any problem on this questionnaire so far, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people?', 462, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4610, 461, 1, 'Not difficult', 0, 1, 0, 'dep10_difficult');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4611, 461, 2, 'Somewhat difficult', 0, 1, 1, 'dep10_difficult');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4612, 461, 3, 'Very Difficult', 0, 1, 2, 'dep10_difficult');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4613, 461, 4, 'Extremely difficult', 0, 1, 3, 'dep10_difficult');

/* 66 MDQ page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (471, 6, 'Has there ever been a period of time when you were not your usual self and...', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (66, 471, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (472, 2, 'You felt so good or hyper that other people thought you were not your normal self or you were so hyper that you got into trouble?', 471, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4720, 472, 1, 'No', 0, 1, 0, 'hyp1a_good');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4721, 472, 2, 'Yes', 0, 1, 1, 'hyp1a_good');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (473, 2, 'You were so irritable that you shouted at people or started fights or arguments?', 471, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4730, 473, 1, 'No', 0, 1, 0, 'hyp1b_irritable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4731, 473, 2, 'Yes', 0, 1, 1, 'hyp1b_irritable');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (474, 2, 'You felt much more self-confident than usual?', 471, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4740, 474, 1, 'No', 0, 1, 0, 'hyp1c_confident');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4741, 474, 2, 'Yes', 0, 1, 1, 'hyp1c_confident');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (475, 2, "You got much less sleep than usual and found you didn't really miss it?", 471, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4750, 475, 1, 'No', 0, 1, 0, 'hyp1d_sleep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4751, 475, 2, 'Yes', 0, 1, 1, 'hyp1d_sleep');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (476, 2, 'You were much more talkative or spoke much faster than usual?', 471, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4760, 476, 1, 'No', 0, 1, 0, 'hyp1e_talkative');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4761, 476, 2, 'Yes', 0, 1, 1, 'hyp1e_talkative');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (477, 2, "Thoughts raced through your head or you couldn't slow your mind down?", 471, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4770, 477, 1, 'No', 0, 1, 0, 'hyp1f_thoughts');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4771, 477, 2, 'Yes', 0, 1, 1, 'hyp1f_thoughts');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (478, 2, 'You were so easily distracted by things around you that you had trouble concentrating or staying on track?', 471, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4780, 478, 1, 'No', 0, 1, 0, 'hyp1g_distracted');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4781, 478, 2, 'Yes', 0, 1, 1, 'hyp1g_distracted');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (479, 2, 'You had much more energy than usual?', 471, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4790, 479, 1, 'No', 0, 1, 0, 'hyp1h_energy');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4791, 479, 2, 'Yes', 0, 1, 1, 'hyp1h_energy');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (480, 2, 'You were much more active or did many more things than usual?', 471, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4800, 480, 1, 'No', 0, 1, 0, 'hyp1i_active');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4801, 480, 2, 'Yes', 0, 1, 1, 'hyp1i_active');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (481, 2, 'You were much more social or outgoing than usual, for example, you telephoned friends in the middle of the night?', 471, 10);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4810, 481, 1, 'No', 0, 1, 0, 'hyp1j_social');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4811, 481, 2, 'Yes', 0, 1, 1, 'hyp1j_social');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (482, 2, 'You were much more interested in sex than usual?', 471, 11);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4820, 482, 1, 'No', 0, 1, 0, 'hyp1k_sex');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4821, 482, 2, 'Yes', 0, 1, 1, 'hyp1k_sex');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (483, 2, 'You did things that were unusual for you or that other people might have thought were excessive foolish or risky?', 471, 12);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4830, 483, 1, 'No', 0, 1, 0, 'hyp1l_unusual');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4831, 483, 2, 'Yes', 0, 1, 1, 'hyp1l_unusual');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (484, 2, 'Spending money got you or your family into trouble?', 471, 13);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4840, 484, 1, 'No', 0, 1, 0, 'hyp1m_spend');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4841, 484, 2, 'Yes', 0, 1, 1, 'hyp1m_spend');


INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (485, 2, 'If you checked YES to more than one of the above, have several of these happened during the same period of time?');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4850, 485, 1, 'No', 0, 1, 0, 'hyp2_time');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4851, 485, 2, 'Yes', 0, 1, 1, 'hyp2_time');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (66, 485, 1);

/* 67 MDQ page 2 */

--INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (489, 6, '');
--INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (67, 489, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (490, 2, 'How much of a problem did any of these cause you, -- like being unable to work; having family, money or legal troubles; getting into arguments or fights?');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4900, 490, 1, 'No problem', 0, 1, 0, 'hyp3_problem');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4901, 490, 2, 'Minor problem', 0, 1, 1, 'hyp3_problem');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4902, 490, 3, 'Moderate problem', 0, 1, 2, 'hyp3_problem');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4903, 490, 4, 'Serious problem', 0, 1, 3, 'hyp3_problem');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (67, 490, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (491, 2, 'Have any of your blood relatives (i.e., children, siblings, parents, grandparents, aunts, uncles) had manic-depressive illness or bipolar disorder?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (67, 491, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4910, 491, 1, 'No', 0, 1, 0, 'hyp4_relative');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4911, 491, 2, 'Yes', 0, 1, 1, 'hyp4_relative');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (492, 2, 'Has a health professional ever told you that you have manic-depressive illness or bipolar disorder?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (67, 492, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4920, 492, 1, 'No', 0, 1, 0, 'hyp5_disorder');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (4921, 492, 2, 'Yes', 0, 1, 1, 'hyp5_disorder');

/* 68 MST page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (500, 2, '1. When you were in the military, did you ever receive uninvited or unwanted sexual attention(i.e, touching, cornering, pressure for sexual favors or inappropriate verbal remarks, etc.)? 2. When you were in the military, did anyone ever use force or the threat of force to have sex against your will?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (68, 500, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5000, 500, 1, "'No' to both questions", 0, 1, 0, 'mst1_2');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5001, 500, 2, "'Yes' to one or both questions", 0, 1, 1, 'mst1_2');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5002, 500, 3, 'Decline to answer question regarding MST', 0, 1, 2, 'mst1_2');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (502, 6, 'Have you experienced any of these types of unwanted sexual experience outside of the military?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (68, 502, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (503, 2, 'In childhood?', 502, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5030, 503, 1, 'No', 0, 1, 0, 'mst3a_childhood');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5031, 503, 2, 'Yes', 0, 1, 1, 'mst3a_childhood');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5032, 503, 3, 'Decline to answer', 0, 1, 2, 'mst3a_childhood');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (504, 2, 'In adulthood?', 502, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5040, 504, 1, 'No', 0, 1, 0, 'mst3b_adulthood');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5041, 504, 2, 'Yes', 0, 1, 1, 'mst3b_adulthood');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5042, 504, 3, 'Decline to answer', 0, 1, 2, 'mst3b_adulthood');

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (505, 2, 'Would you like a referral to see a VA clinician to discuss issues related to sexual trauma?', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (68, 505, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5050, 505, 1, 'No', 0, 1, 0, 'Mst_consult');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5051, 505, 2, 'Yes', 0, 1, 1, 'Mst_consult');

/* 69 GAD 7 Anxiety page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (511, 6, 'Over the last 2 weeks, how often have you been bothered by the following problems?', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (69, 511, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (512, 2, 'Feeling nervous, anxious or on edge', 511, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5120, 512, 1, 'Not at all', 0, 1, 0, 'gad1_nervous');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5121, 512, 2, 'Several days', 0, 1, 1, 'gad1_nervous');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5122, 512, 3, 'More than half the days', 0, 1, 2, 'gad1_nervous');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5123, 512, 4, 'Nearly every day', 0, 1, 3, 'gad1_nervous');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (513, 2, 'Not being able to stop or control worrying', 511, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5130, 513, 1, 'Not at all', 0, 1, 0, 'gad2_notable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5131, 513, 2, 'Several days', 0, 1, 1, 'gad2_notable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5132, 513, 3, 'More than half the days', 0, 1, 2, 'gad2_notable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5133, 513, 4, 'Nearly every day', 0, 1, 3, 'gad2_notable');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (514, 2, 'Worrying too much about different things', 511, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5140, 514, 1, 'Not at all', 0, 1, 0, 'gad3_worry');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5141, 514, 2, 'Several days', 0, 1, 1, 'gad3_worry');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5142, 514, 3, 'More than half the days', 0, 1, 2, 'gad3_worry');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5143, 514, 4, 'Nearly every day', 0, 1, 3, 'gad3_worry');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (515, 2, 'Trouble relaxing', 511, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5150, 515, 1, 'Not at all', 0, 1, 0, 'gad4_trouble');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5151, 515, 2, 'Several days', 0, 1, 1, 'gad4_trouble');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5152, 515, 3, 'More than half the days', 0, 1, 2, 'gad4_trouble');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5153, 515, 4, 'Nearly every day', 0, 1, 3, 'gad4_trouble');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (516, 2, 'Being so restless that it is hard to sit still', 511, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5160, 516, 1, 'Not at all', 0, 1, 0, 'gad5_restless');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5161, 516, 2, 'Several days', 0, 1, 1, 'gad5_restless');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5162, 516, 3, 'More than half the days', 0, 1, 2, 'gad5_restless');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5163, 516, 4, 'Nearly every day', 0, 1, 3, 'gad5_restless');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (517, 2, 'Becoming easily annoyed or irritable', 511, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5170, 517, 1, 'Not at all', 0, 1, 0, 'gad6_annoyed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5171, 517, 2, 'Several days', 0, 1, 1, 'gad6_annoyed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5172, 517, 3, 'More than half the days', 0, 1, 2, 'gad6_annoyed');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5173, 517, 4, 'Nearly every day', 0, 1, 3, 'gad6_annoyed');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (518, 2, 'Feeling afraid as if something awful might happen', 511, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5180, 518, 1, 'Not at all', 0, 1, 0, 'gad7_afraid');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5181, 518, 2, 'Several days', 0, 1, 1, 'gad7_afraid');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5182, 518, 3, 'More than half the days', 0, 1, 2, 'gad7_afraid');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5183, 518, 4, 'Nearly every day', 0, 1, 3, 'gad7_afraid');

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (519, 6, '');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (69, 519, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (520, 2, 'If you checked off any problems, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people?', 519, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5190, 520, 1, 'Not difficult at all', 0, 1, 0, 'gad8_difficult');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5191, 520, 2, 'Somewhat difficult', 0, 1, 1, 'gad8_difficult');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5192, 520, 3, 'Very difficult', 0, 1, 2, 'gad8_difficult');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5193, 520, 4, 'Extremely difficult', 0, 1, 3, 'gad8_difficult');


/* 70 PCL-C page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (521, 6, 'Instructions: Below is a list of problems and complaints that veterans sometimes have in response to stressful life experiences. Please read each one carefully, and choose a button to indicate how much you have been bothered by that problem in the past 4 weeks.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (70, 521, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (522, 2, 'Repeated, disturbing memories, thoughts, or images of a stressful experience from the past?', 521, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5220, 522, 1, 'Not at all', 0, 1, 1, 'pcl1_memories');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5221, 522, 2, 'A little bit', 0, 1, 2, 'pcl1_memories');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5222, 522, 3, 'Moderately', 0, 1, 3, 'pcl1_memories');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5223, 522, 4, 'Quite a bit', 0, 1, 4, 'pcl1_memories');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5224, 522, 5, 'Extremely', 0, 1, 5, 'pcl1_memories');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (523, 2, 'Repeated, disturbing dreams of a stressful experience from the past?', 521, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5230, 523, 1, 'Not at all', 0, 1, 1, 'pcl2_dreams');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5231, 523, 2, 'A little bit', 0, 1, 2, 'pcl2_dreams');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5232, 523, 3, 'Moderately', 0, 1, 3, 'pcl2_dreams');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5233, 523, 4, 'Quite a bit', 0, 1, 4, 'pcl2_dreams');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5234, 523, 5, 'Extremely', 0, 1, 5, 'pcl2_dreams');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (524, 2, 'Suddenly acting or feeling as if a stressful experience were happening again (as if you were reliving it)?', 521, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5240, 524, 1, 'Not at all', 0, 1, 1, 'pcl3_acting');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5241, 524, 2, 'A little bit', 0, 1, 2, 'pcl3_acting');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5242, 524, 3, 'Moderately', 0, 1, 3, 'pcl3_acting');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5243, 524, 4, 'Quite a bit', 0, 1, 4, 'pcl3_acting');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5244, 524, 5, 'Extremely', 0, 1, 5, 'pcl3_acting');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (525, 2, 'Feeling very upset when something reminded you of a stressful experience from the past?', 521, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5250, 525, 1, 'Not at all', 0, 1, 1, 'pcl4_remind');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5251, 525, 2, 'A little bit', 0, 1, 2, 'pcl4_remind');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5252, 525, 3, 'Moderately', 0, 1, 3, 'pcl4_remind');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5253, 525, 4, 'Quite a bit', 0, 1, 4, 'pcl4_remind');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5254, 525, 5, 'Extremely', 0, 1, 5, 'pcl4_remind');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (526, 2, 'Having physical reactions (e.g., heart pounding, trouble breathing, or sweating) when something reminded you of a stressful experience from the past?', 521, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5260, 526, 1, 'Not at all', 0, 1, 1, 'pcl5_reaction');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5261, 526, 2, 'A little bit', 0, 1, 2, 'pcl5_reaction');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5262, 526, 3, 'Moderately', 0, 1, 3, 'pcl5_reaction');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5263, 526, 4, 'Quite a bit', 0, 1, 4, 'pcl5_reaction');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5264, 526, 5, 'Extremely', 0, 1, 5, 'pcl5_reaction');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (527, 2, 'Avoid thinking about or talking about  a stressful experience from the past to avoide having feelings related to it?', 521, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5270, 527, 1, 'Not at all', 0, 1, 1, 'pcl6_think');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5271, 527, 2, 'A little bit', 0, 1, 2, 'pcl6_think');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5272, 527, 3, 'Moderately', 0, 1, 3, 'pcl6_think');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5273, 527, 4, 'Quite a bit', 0, 1, 4, 'pcl6_think');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5274, 527, 5, 'Extremely', 0, 1, 5, 'pcl6_think');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (528, 2, 'Avoid activities or situations because they remind you of a stressful experience from the past?', 521, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5280, 528, 1, 'Not at all', 0, 1, 1, 'pcl7_activity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5281, 528, 2, 'A little bit', 0, 1, 2, 'pcl7_activity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5282, 528, 3, 'Moderately', 0, 1, 3, 'pcl7_activity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5283, 528, 4, 'Quite a bit', 0, 1, 4, 'pcl7_activity');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5284, 528, 5, 'Extremely', 0, 1, 5, 'pcl7_activity');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (529, 2, 'Trouble remembering important parts of a stressful experience from the past?', 521, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5290, 529, 1, 'Not at all', 0, 1, 1, 'pcl8_trouble');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5291, 529, 2, 'A little bit', 0, 1, 2, 'pcl8_trouble');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5292, 529, 3, 'Moderately', 0, 1, 3, 'pcl8_trouble');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5293, 529, 4, 'Quite a bit', 0, 1, 4, 'pcl8_trouble');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5294, 529, 5, 'Extremely', 0, 1, 5, 'pcl8_trouble');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (530, 2, 'A loss of interest in things that you used to enjoy?', 521, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5300, 530, 1, 'Not at all', 0, 1, 1, 'pcl9_interest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5301, 530, 2, 'A little bit', 0, 1, 2, 'pcl9_interest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5302, 530, 3, 'Moderately', 0, 1, 3, 'pcl9_interest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5303, 530, 4, 'Quite a bit', 0, 1, 4, 'pcl9_interest');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5304, 530, 5, 'Extremely', 0, 1, 5, 'pcl9_interest');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (531, 2, 'Feeling distant or cut off from other people?', 521, 10);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5310, 531, 1, 'Not at all', 0, 1, 1, 'pcl10_distant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5311, 531, 2, 'A little bit', 0, 1, 2, 'pcl10_distant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5312, 531, 3, 'Moderately', 0, 1, 3, 'pcl10_distant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5313, 531, 4, 'Quite a bit', 0, 1, 4, 'pcl10_distant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5314, 531, 5, 'Extremely', 0, 1, 5, 'pcl10_distant');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (532, 2, 'Feeling emotionally numb or being unable to have loving feelings for those close to you?', 521, 11);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5320, 532, 1, 'Not at all', 0, 1, 1, 'pcl11_emotion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5321, 532, 2, 'A little bit', 0, 1, 2, 'pcl11_emotion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5322, 532, 3, 'Moderately', 0, 1, 3, 'pcl11_emotion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5323, 532, 4, 'Quite a bit', 0, 1, 4, 'pcl11_emotion');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5324, 532, 5, 'Extremely', 0, 1, 5, 'pcl11_emotion');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (533, 2, 'Feeling as if your future will somehow be cut short?', 521, 12);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5330, 533, 1, 'Not at all', 0, 1, 1, 'pcl12_future');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5331, 533, 2, 'A little bit', 0, 1, 2, 'pcl12_future');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5332, 533, 3, 'Moderately', 0, 1, 3, 'pcl12_future');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5333, 533, 4, 'Quite a bit', 0, 1, 4, 'pcl12_future');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5334, 533, 5, 'Extremely', 0, 1, 5, 'pcl12_future');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (534, 2, 'Trouble falling or staying asleep?', 521, 13);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5340, 534, 1, 'Not at all', 0, 1, 1, 'pcl13_asleep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5341, 534, 2, 'A little bit', 0, 1, 2, 'pcl13_asleep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5342, 534, 3, 'Moderately', 0, 1, 3, 'pcl13_asleep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5343, 534, 4, 'Quite a bit', 0, 1, 4, 'pcl13_asleep');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5344, 534, 5, 'Extremely', 0, 1, 5, 'pcl13_asleep');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (535, 2, 'Feeling irritable or having angry outbursts?', 521, 14);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5350, 535, 1, 'Not at all', 0, 1, 1, 'pcl14_irritable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5351, 535, 2, 'A little bit', 0, 1, 2, 'pcl14_irritable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5352, 535, 3, 'Moderately', 0, 1, 3, 'pcl14_irritable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5353, 535, 4, 'Quite a bit', 0, 1, 4, 'pcl14_irritable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5354, 535, 5, 'Extremely', 0, 1, 5, 'pcl14_irritable');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (536, 2, 'Difficulty concentrating?', 521, 15);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5360, 536, 1, 'Not at all', 0, 1, 1, 'pcl15_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5361, 536, 2, 'A little bit', 0, 1, 2, 'pcl15_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5362, 536, 3, 'Moderately', 0, 1, 3, 'pcl15_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5363, 536, 4, 'Quite a bit', 0, 1, 4, 'pcl15_concentrate');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5364, 536, 5, 'Extremely', 0, 1, 5, 'pcl15_concentrate');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (537, 2, 'Being "super alert" or watchful, on guard?', 521, 16);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5370, 537, 1, 'Not at all', 0, 1, 1, 'pcl16_alert');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5371, 537, 2, 'A little bit', 0, 1, 2, 'pcl16_alert');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5372, 537, 3, 'Moderately', 0, 1, 3, 'pcl16_alert');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5373, 537, 4, 'Quite a bit', 0, 1, 4, 'pcl16_alert');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5374, 537, 5, 'Extremely', 0, 1, 5, 'pcl16_alert');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (538, 2, 'Feeling jumpy or easily startled?', 521, 17);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5380, 538, 1, 'Not at all', 0, 1, 1, 'pcl17_jumpy');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5381, 538, 2, 'A little bit', 0, 1, 2, 'pcl17_jumpy');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5382, 538, 3, 'Moderately', 0, 1, 3, 'pcl17_jumpy');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5383, 538, 4, 'Quite a bit', 0, 1, 4, 'pcl17_jumpy');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5384, 538, 5, 'Extremely', 0, 1, 5, 'pcl17_jumpy');

/* 71 PC-PTSD page 1' */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (541, 6, 'Have you ever had any experience that was so frightening, horrible, or upsetting that, in the past month, you:', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (71, 541, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (542, 2, 'Have had any nightmares about it or thought about it when you did not want to?', 541, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5420, 542, 1, 'No', 0, 1, 0, 'pcptsdA_nightmare');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5421, 542, 2, 'Yes', 0, 1, 1, 'pcptsdA_nightmare');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (543, 2, 'Tried hard not to think about it; went out of your way to avoid situations that remind you of it?', 541, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5430, 543, 1, 'No', 0, 1, 0, 'pcptsdB_notthink');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5431, 543, 2, 'Yes', 0, 1, 1, 'pcptsdB_notthink');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (544, 2, 'Were constantly on guard, watchful, or easily startled?', 541, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5440, 544, 1, 'No', 0, 1, 0, 'pcptsdC_onguard');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5441, 544, 2, 'Yes', 0, 1, 1, 'pcptsdC_onguard');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (545, 2, 'Felt numb or detached from others, activities, or your surroundings?', 541, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5450, 545, 1, 'No', 0, 1, 0, 'pcptsdD_numb');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5451, 545, 2, 'Yes', 0, 1, 1, 'pcptsdD_numb');

/* 72 ISI page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required, score_weight) VALUES (550, 8, 'Below is a list of problems that people sometimes have with sleep. Please respond to the following questions about your sleep in the past 1 week.', 0, 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (72, 550, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (552, 6, 'Please rate the current (i.e., last week) SEVERITY of your insomnia problem(s) by selecting the best response', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (72, 552, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (553, 2, 'Difficulty falling asleep:', 552, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5530, 553, 1, 'None', 0, 1, 0, 'sleep1a_falling');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5531, 553, 2, 'Mild', 0, 1, 1, 'sleep1a_falling');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5532, 553, 3, 'Moderate', 0, 1, 2, 'sleep1a_falling');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5533, 553, 4, 'Severe', 0, 1, 3, 'sleep1a_falling');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5534, 553, 5, 'Very severe', 0, 1, 4, 'sleep1a_falling');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (554, 2, 'Difficulty staying asleep:', 552, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5540, 554, 1, 'None', 0, 1, 0, 'sleep1b_staying');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5541, 554, 2, 'Mild', 0, 1, 1, 'sleep1b_staying');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5542, 554, 3, 'Moderate', 0, 1, 2, 'sleep1b_staying');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5543, 554, 4, 'Severe', 0, 1, 3, 'sleep1b_staying');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5544, 554, 5, 'Very severe', 0, 1, 4, 'sleep1b_staying');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (555, 2, 'Problem waking up too early:', 552, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5550, 555, 1, 'None', 0, 1, 0, 'sleep1c_waking');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5551, 555, 2, 'Mild', 0, 1, 1, 'sleep1c_waking');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5552, 555, 3, 'Moderate', 0, 1, 2, 'sleep1c_waking');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5553, 555, 4, 'Severe', 0, 1, 3, 'sleep1c_waking');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5554, 555, 5, 'Very severe', 0, 1, 4, 'sleep1c_waking');

/* 73 ISI page 2 */

INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (600, 6, '');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (73, 600, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (556, 2, 'How <b>SATISFIED/DISSATISFIED</b> are you with your current sleep pattern?', 600, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5560, 556, 0, 'Very satisfied', 0, 1, 0, 'sleep2_satisfied');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5562, 556, 1, '', 0, 1, 1, 'sleep2_satisfied');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5563, 556, 2, '', 0, 1, 2, 'sleep2_satisfied');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5564, 556, 3, '', 0, 1, 3, 'sleep2_satisfied');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5561, 556, 4, 'Very dissatisfied', 0, 1, 4, 'sleep2_satisfied');


INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (601, 6, 'To what extent do you consider your sleep problem to <B>INTERFERE</B> with your daily functioning (e.g., daytime fatigue, ability to function at work/daily chores, concentration, memory, mood, etc.).');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (73, 601, 1);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (557, 2, '', 601, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5570, 557, 1, 'Not at all', 0, 1, 0, 'sleep3_interfere');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5571, 557, 2, 'A little', 0, 1, 1, 'sleep3_interfere');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5572, 557, 3, 'Somewhat', 0, 1, 2, 'sleep3_interfere');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5573, 557, 4, 'Much', 0, 1, 3, 'sleep3_interfere');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5574, 557, 5, 'Very much interfering', 0, 1, 4, 'sleep3_interfere');


INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (602, 6, 'How <b>NOTICEABLE</b> to others do you think your sleeping problem is in terms of impairing the quality of your life?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (73, 602, 2);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (558, 2, '', 602, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5580, 558, 1, 'Not noticeable at all', 0, 1, 0, 'sleep4_noticeable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5581, 558, 2, 'Barely', 0, 1, 1, 'sleep4_noticeable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5582, 558, 3, 'Somewhat', 0, 1, 2, 'sleep4_noticeable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5583, 558, 4, 'Much', 0, 1, 3, 'sleep4_noticeable');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5584, 558, 5, 'Very much noticeable', 0, 1, 4, 'sleep4_noticeable');


INSERT INTO measure (measure_id, measure_type_id, measure_text) VALUES (603, 6, 'How <b>WORRIED/DISTRESSED</b> are you about your current sleep problem?');
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (73, 603, 3);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (559, 2, '', 603, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5590, 559, 1, 'Not at all', 0, 1, 0, 'sleep5_worried');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5591, 559, 2, 'A little', 0, 1, 1, 'sleep5_worried');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5592, 559, 3, 'Somewhat', 0, 1, 2, 'sleep5_worried');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5593, 559, 4, 'Much', 0, 1, 3, 'sleep5_worried');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5594, 559, 5, 'Very much', 0, 1, 4, 'sleep5_worried');

/* 74 ROASsu page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (561, 6, 'Please mark how often (if applicable) each of the items is true of you when you have gotten angry in the past four weeks.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (74, 561, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (562, 2, 'Make loud noises, scream, shout angrily', 561, 0);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5620, 562, 1, 'Never', 0, 1, 0, 'ang1_noise');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5621, 562, 2, 'Sometimes', 0, 1, 1, 'ang1_noise');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5622, 562, 3, 'Often', 0, 1, 2, 'ang1_noise');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5623, 562, 4, 'Usually', 0, 1, 3, 'ang1_noise');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5624, 562, 5, 'Always', 0, 1, 4, 'ang1_noise');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (563, 2, 'Yell mild personal assaults at others', 561, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5630, 563, 1, 'Never', 0, 1, 0, 'ang2_yell');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5631, 563, 2, 'Sometimes', 0, 1, 1, 'ang2_yell');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5632, 563, 3, 'Often', 0, 1, 2, 'ang2_yell');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5633, 563, 4, 'Usually', 0, 1, 3, 'ang2_yell');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5634, 563, 5, 'Always', 0, 1, 4, 'ang2_yell');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (564, 2, 'Curse, use foul language, make vague threats to hurt myself or others', 561, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5640, 564, 1, 'Never', 0, 1, 0, 'ang3_curse');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5641, 564, 2, 'Sometimes', 0, 1, 1, 'ang3_curse');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5642, 564, 3, 'Often', 0, 1, 2, 'ang3_curse');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5643, 564, 4, 'Usually', 0, 1, 3, 'ang3_curse');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5644, 564, 5, 'Always', 0, 1, 4, 'ang3_curse');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (565, 2, 'Make clear threats of violence towards others or myself, or ask for help from others to control myself', 561, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5650, 565, 1, 'Never', 0, 1, 0, 'ang4_threat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5651, 565, 2, 'Sometimes', 0, 1, 1, 'ang4_threat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5652, 565, 3, 'Often', 0, 1, 2, 'ang4_threat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5653, 565, 4, 'Usually', 0, 1, 3, 'ang4_threat');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5654, 565, 5, 'Always', 0, 1, 4, 'ang4_threat');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (566, 2, 'Slam doors, make a mess, scatter clothing', 561, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5660, 566, 1, 'Never', 0, 1, 0, 'ang5_slamdoor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5661, 566, 2, 'Sometimes', 0, 1, 1, 'ang5_slamdoor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5662, 566, 3, 'Often', 0, 1, 2, 'ang5_slamdoor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5663, 566, 4, 'Usually', 0, 1, 3, 'ang5_slamdoor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5664, 566, 5, 'Always', 0, 1, 4, 'ang5_slamdoor');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (567, 2, 'Throw objects down, kick furniture, without breaking it, mark the walls', 561, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5670, 567, 1, 'Never', 0, 1, 0, 'ang6_throw');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5671, 567, 2, 'Sometimes', 0, 1, 1, 'ang6_throw');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5672, 567, 3, 'Often', 0, 1, 2, 'ang6_throw');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5673, 567, 4, 'Usually', 0, 1, 3, 'ang6_throw');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5674, 567, 5, 'Always', 0, 1, 4, 'ang6_throw');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (568, 2, 'Break objects, smash windows', 561, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5680, 568, 1, 'Never', 0, 1, 0, 'ang7_break');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5681, 568, 2, 'Sometimes', 0, 1, 1, 'ang7_break');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5682, 568, 3, 'Often', 0, 1, 2, 'ang7_break');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5683, 568, 4, 'Usually', 0, 1, 3, 'ang7_break');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5684, 568, 5, 'Always', 0, 1, 4, 'ang7_break');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (569, 2, 'Set fires, thrown objects dangerously', 561, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5690, 569, 1, 'Never', 0, 1, 0, 'ang8_setfire');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5691, 569, 2, 'Sometimes', 0, 1, 1, 'ang8_setfire');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5692, 569, 3, 'Often', 0, 1, 2, 'ang8_setfire');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5693, 569, 4, 'Usually', 0, 1, 3, 'ang8_setfire');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5694, 569, 5, 'Always', 0, 1, 4, 'ang8_setfire');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (570, 2, 'Make threatening gestures, swing at people, grab at clothes of others', 561, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5700, 570, 1, 'Never', 0, 1, 0, 'ang9_gesture');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5701, 570, 2, 'Sometimes', 0, 1, 1, 'ang9_gesture');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5702, 570, 3, 'Often', 0, 1, 2, 'ang9_gesture');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5703, 570, 4, 'Usually', 0, 1, 3, 'ang9_gesture');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5704, 570, 5, 'Always', 0, 1, 4, 'ang9_gesture');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (571, 2, 'Strike, kick, push, pull hair (others)', 561, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5710, 571, 1, 'Never', 0, 1, 0, 'ang10_strike');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5711, 571, 2, 'Sometimes', 0, 1, 1, 'ang10_strike');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5712, 571, 3, 'Often', 0, 1, 2, 'ang10_strike');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5713, 571, 4, 'Usually', 0, 1, 3, 'ang10_strike');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5714, 571, 5, 'Always', 0, 1, 4, 'ang10_strike');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (572, 2, 'Attack others causing physical injury (bruises, sprain, welts)', 561, 10);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5720, 572, 1, 'Never', 0, 1, 0, 'ang11_injury');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5721, 572, 2, 'Sometimes', 0, 1, 1, 'ang11_injury');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5722, 572, 3, 'Often', 0, 1, 2, 'ang11_injury');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5723, 572, 4, 'Usually', 0, 1, 3, 'ang11_injury');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5724, 572, 5, 'Always', 0, 1, 4, 'ang11_injury');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (573, 2, 'Attack other causing severe physical injury (broken bones, deep cuts)', 561, 11);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5730, 573, 1, 'Never', 0, 1, 0, 'ang12_severeinjury');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5731, 573, 2, 'Sometimes', 0, 1, 1, 'ang12_severeinjury');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5732, 573, 3, 'Often', 0, 1, 2, 'ang12_severeinjury');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5733, 573, 4, 'Usually', 0, 1, 3, 'ang12_severeinjury');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5734, 573, 5, 'Always', 0, 1, 4, 'ang12_severeinjury');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (574, 2, 'Pick or scratch my skin, hit myself on arms and body, pinch myself, pull my own hair', 561, 12);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5740, 574, 1, 'Never', 0, 1, 0, 'ang13_pick');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5741, 574, 2, 'Sometimes', 0, 1, 1, 'ang13_pick');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5742, 574, 3, 'Often', 0, 1, 2, 'ang13_pick');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5743, 574, 4, 'Usually', 0, 1, 3, 'ang13_pick');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5744, 574, 5, 'Always', 0, 1, 4, 'ang13_pick');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (575, 2, 'Bang my head, use my fist to hit objects, throw myself onto the floor or into others', 561, 13);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5750, 575, 1, 'Never', 0, 1, 0, 'ang14_banghead');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5751, 575, 2, 'Sometimes', 0, 1, 1, 'ang14_banghead');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5752, 575, 3, 'Often', 0, 1, 2, 'ang14_banghead');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5753, 575, 4, 'Usually', 0, 1, 3, 'ang14_banghead');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5754, 575, 5, 'Always', 0, 1, 4, 'ang14_banghead');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (576, 2, 'Cut, bruise, or cause minor burns to myself', 561, 14);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5760, 576, 1, 'Never', 0, 1, 0, 'ang15_cut');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5761, 576, 2, 'Sometimes', 0, 1, 1, 'ang15_cut');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5762, 576, 3, 'Often', 0, 1, 2, 'ang15_cut');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5763, 576, 4, 'Usually', 0, 1, 3, 'ang15_cut');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5764, 576, 5, 'Always', 0, 1, 4, 'ang15_cut');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (577, 2, 'Hurt myself (e.g., make deep cuts, bites that bleed or other ways that result in internal injury, fracture or loss of consciousness, loss of teeth)', 561, 15);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5770, 577, 1, 'Never', 0, 1, 0, 'ang16_hurt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5771, 577, 2, 'Sometimes', 0, 1, 1, 'ang16_hurt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5772, 577, 3, 'Often', 0, 1, 2, 'ang16_hurt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5773, 577, 4, 'Usually', 0, 1, 3, 'ang16_hurt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5774, 577, 5, 'Always', 0, 1, 4, 'ang16_hurt');

/* 75 CD-RISC-10 page 1 */
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (581, 6, 'Please indicate how much you agree with the following statements as they apply to you over the past 4 weeks. If a particular situation has not occurred recently, answer according to how you think you would have felt.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (75, 581, 0);

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (582, 2, 'I am able to adapt when changes occur', 581, 1);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5820, 582, 1, 'Not true at all', 0, 1, 0, 'res1_adapt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (1582, 582, 2, 'Rarely true', 0, 1, 1, 'res1_adapt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5822, 582, 3, 'Sometimes', 0, 1, 2, 'res1_adapt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5823, 582, 4, 'Often true', 0, 1, 3, 'res1_adapt');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5824, 582, 5, 'True nearly all the time', 0, 1, 4, 'res1_adapt');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (583, 2, 'I can deal with whatever comes my way', 581, 2);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5830, 583, 1, 'Not true at all', 0, 1, 0, 'res2_deal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5831, 583, 2, 'Rarely true', 0, 1, 1, 'res2_deal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5832, 583, 3, 'Sometimes', 0, 1, 2, 'res2_deal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5833, 583, 4, 'Often true', 0, 1, 3, 'res2_deal');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5834, 583, 5, 'True nearly all the time', 0, 1, 4, 'res2_deal');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (584, 2, 'I try to see the humorous side of things when I am faced with problems', 581, 3);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5840, 584, 1, 'Not true at all', 0, 1, 0, 'res3_humor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5841, 584, 2, 'Rarely true', 0, 1, 1, 'res3_humor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5842, 584, 3, 'Sometimes', 0, 1, 2, 'res3_humor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5843, 584, 4, 'Often true', 0, 1, 3, 'res3_humor');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5844, 584, 5, 'True nearly all the time', 0, 1, 4, 'res3_humor');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (585, 2, 'Having to cope with stress can make me stronger', 581, 4);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5850, 585, 1, 'Not true at all', 0, 1, 0, 'res4_cope');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5851, 585, 2, 'Rarely true', 0, 1, 1, 'res4_cope');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5852, 585, 3, 'Sometimes', 0, 1, 2, 'res4_cope');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5853, 585, 4, 'Often true', 0, 1, 3, 'res4_cope');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5854, 585, 5, 'True nearly all the time', 0, 1, 4, 'res4_cope');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (586, 2, 'I tend to bounce back after an illness, injury, or other hardships', 581, 5);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5860, 586, 1, 'Not true at all', 0, 1, 0, 'res5_bounce');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5861, 586, 2, 'Rarely true', 0, 1, 1, 'res5_bounce');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5862, 586, 3, 'Sometimes', 0, 1, 2, 'res5_bounce');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5863, 586, 4, 'Often true', 0, 1, 3, 'res5_bounce');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5864, 586, 5, 'True nearly all the time', 0, 1, 4, 'res5_bounce');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (587, 2, 'I believe I can achieve my goals, even if there are obstacles', 581, 6);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5870, 587, 1, 'Not true at all', 0, 1, 0, 'res6_goals');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5871, 587, 2, 'Rarely true', 0, 1, 1, 'res6_goals');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5872, 587, 3, 'Sometimes', 0, 1, 2, 'res6_goals');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5873, 587, 4, 'Often true', 0, 1, 3, 'res6_goals');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5874, 587, 5, 'True nearly all the time', 0, 1, 4, 'res6_goals');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (588, 2, 'Under pressure, I stay focused and think clearly', 581, 7);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5880, 588, 1, 'Not true at all', 0, 1, 0, 'res7_pressure');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5881, 588, 2, 'Rarely true', 0, 1, 1, 'res7_pressure');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5882, 588, 3, 'Sometimes', 0, 1, 2, 'res7_pressure');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5883, 588, 4, 'Often true', 0, 1, 3, 'res7_pressure');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5884, 588, 5, 'True nearly all the time', 0, 1, 4, 'res7_pressure');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (589, 2, 'I am not easily discouraged by failure', 581, 8);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5890, 589, 1, 'Not true at all', 0, 1, 0, 'res8_discouraged');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5891, 589, 2, 'Rarely true', 0, 1, 1, 'res8_discouraged');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5892, 589, 3, 'Sometimes', 0, 1, 2, 'res8_discouraged');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5893, 589, 4, 'Often true', 0, 1, 3, 'res8_discouraged');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5894, 589, 5, 'True nearly all the time', 0, 1, 4, 'res8_discouraged');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (590, 2, "I think of myself as a strong person when dealing with life's challenges and difficulties", 581, 9);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5900, 590, 1, 'Not true at all', 0, 1, 0, 'res9_strong');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5901, 590, 2, 'Rarely true', 0, 1, 1, 'res9_strong');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5902, 590, 3, 'Sometimes', 0, 1, 2, 'res9_strong');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5903, 590, 4, 'Often true', 0, 1, 3, 'res9_strong');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5904, 590, 5, 'True nearly all the time', 0, 1, 4, 'res9_strong');

INSERT INTO measure (measure_id, measure_type_id, measure_text, parent_measure_id, display_order) VALUES (591, 2, 'I am able to handle unpleasant or painful feelings like sadness, fear, or anger', 581, 10);
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5910, 591, 1, 'Not true at all', 0, 1, 0, 'res10_unpleasant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5911, 591, 2, 'Rarely true', 0, 1, 1, 'res10_unpleasant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5912, 591, 3, 'Sometimes', 0, 1, 2, 'res10_unpleasant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5913, 591, 4, 'Often true', 0, 1, 3, 'res10_unpleasant');
INSERT INTO measure_answer (measure_answer_id, measure_id, display_order, answer_text, answer_value, calculation_type_id, calculation_value, export_name) VALUES (5914, 591, 5, 'True nearly all the time', 0, 1, 4, 'res10_unpleasant');


/* Don't use measure_id in the range 600-650 */