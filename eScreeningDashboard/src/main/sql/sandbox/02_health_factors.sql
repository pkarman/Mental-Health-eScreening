/* *******************************************************************/
/* Create Health Factors, HF Rules, and needed assessment variables  */
/* *******************************************************************/


/****************************************************************************************************************************************
 ************************ MST from Sandbox Server. Add Measure Variables ********************************** 
 ****************************************************************************************************************************************/


/* MST Health Factor: HF from Sandbox Server */
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (3, 'MST NO DOES NOT REPORT', '76', 81, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (4, 'MST YES REPORTS', '77', 81, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (5, 'MST DECLINES TO ANSWER', '75', 81, 0);



/*  MST Health Factor: Add Measure Variables */
--Moved to 27_reference_data_template_variables.sql

/* MST Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (4003, 'No Report', '[2003] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4004, 'Yes Report', '[2003] == 1'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4005, 'Decline Report', '[2003] == 2');

/* MST Health Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4003, 2003);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4004, 2003);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4005, 2003);

/* MST Health Factor: Add event for adding health factors for MST Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4007, 2, 'Add MST NO DOES NOT REPORT HF', 3);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4008, 2, 'Add MST YES REPORTS HF', 4);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4009, 2, 'Add MST DECLINES TO ANSWER HF', 5);

/* MST Health Factor: Associate rules to events */
INSERT INTO rule_event (rule_id, event_id) VALUES(4003, 4007);
INSERT INTO rule_event (rule_id, event_id) VALUES(4004, 4008);
INSERT INTO rule_event (rule_id, event_id) VALUES(4005, 4009);



/****************************************************************************************************************************************
 ************************ Homelessness CR from Sandbox Server. Add Measure Variables ********************************** 
 ****************************************************************************************************************************************/


/* Homeless Health Factor: HF from Sandbox Server */
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (9, 'NEGATIVE - HAS STABLE HOUSING', '2212', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (10, 'POSITIVE - HAS WORRIES ABOUT HOUSING', '2221', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (11, 'LIVES IN HOUSE NO SUBSIDY', '2213', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (12, 'LIVES IN HOUSE WITH SUBSIDY', '2222', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (13, 'LIVES WITH FRIEND OR FAMILY', '2214', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (14, 'LIVES IN MOTEL/HOTEL', '2215', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (15, 'LIVES IN INSTITUTION', '2216', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (16, 'LIVES IN SHELTER', '2217', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (17, 'LIVES ON STREET', '2218', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (18, 'LIVES OTHER', '2219', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (19, 'POSITIVE - HAS NO STABLE HOUSING', '2220', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (20, 'REFERRED TO HOMELESS PROGRAM', '2223', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (21, 'REFERRED TO SOCIAL WORK', '2571', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (22, 'DECLINES SOCIAL WORK REFERRAL', '2572', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (23, 'BEST WAY TO REACH', '2235', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (24, 'HAS NO HOUSING CONCERNS', '2228', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (25, 'ALREADY RECEIVING ASSIST WITH HOUSING', '2225', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (26, 'NURSING HOME RESIDENT', '2226', 43, 0);
 INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (27, 'DECLINES HOMELESS SCREEN', '2230', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (28, 'UNABLE TO PERFORM HOMELESS SCREEN', '2227', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (29, 'DECLINES HOMELESS REFERRAL', '2224', 43, 0);

/* Homeless Clinical Reminder HF from Sandbox Server. Add Measure Variables */


/* Homeless Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (4032, 'REFERRED TO HOMELESS PROGRAM', '[2008] == 1'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4033, 'DECLINES HOMELESS REFERRAL ', '[2008] == 0');

/* HomelessHealth Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4032, 2008);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4033, 2008);

/* Homeless Health Factor: Add event for adding health factors for Homeless Clinical Reminder */

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4035, 2, 'Add DECLINES HOMELESS REFERRAL', 29);

/* Homeless Health Factor: Add Rule Event */

INSERT INTO rule_event (rule_id, event_id) VALUES(4033, 4035);


/* Homeless Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (4022, 'NEGATIVE - HAS STABLE HOUSING', '[2000] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4023, 'POSITIVE - HAS NO STABLE HOUSING', '[2000] == 1'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4024, 'DECLINES HOMELESS SCREEN', '[2000] == 2');
INSERT INTO rule (rule_id, name, expression) VALUES (4025, 'BEST WAY TO REACH', '[2008]==0 || [2008]==1');

/* HomelessHealth Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4022, 2000);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4023, 2000);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4024, 2000);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4025, 2008);

/* Homeless Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4025, 2, 'Add NEGATIVE - HAS STABLE HOUSING  HF', 9);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4026, 2, 'Add POSITIVE - HAS NO STABLE HOUSING HF', 19);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4027, 2, 'Add DECLINES HOMELESS SCREEN HF', 27);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4028, 2, 'Add BEST WAY TO REACH', 23);

/* Homeless Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(4022, 4026);
INSERT INTO rule_event (rule_id, event_id) VALUES(4023, 4025);
INSERT INTO rule_event (rule_id, event_id) VALUES(4024, 4027);
--INSERT INTO rule_event (rule_id, event_id) VALUES(4025, 4028);
INSERT INTO rule_event (rule_id, event_id) VALUES(2, 4028);
INSERT INTO rule_event (rule_id, event_id) VALUES(3, 4028);

/* Homeless Clinical Reminder HF from Sandbox Server. Add Measure Variables */
-- INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2007, 1, 'Homeless Question 1', 'Homeless Question 1', 62);
-- This question has been defined in 27_reference_data_followup_question_rules.sql assessment_variable_id = 2001

/* Homeless Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (4028, 'HAS NO HOUSING CONCERNS', '[2001] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4029, 'POSITIVE - HAS WORRIES ABOUT HOUSING ', '[2001] == 1');

/* HomelessHealth Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4028, 2001);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4029, 2001);

/* Homeless Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4030, 2, 'Add HAS NO HOUSING CONCERNS HF', 24);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4031, 2, 'Add POSITIVE - HAS WORRIES ABOUT HOUSING HF' , 10);

/* Homeless Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(4028, 4030);
INSERT INTO rule_event (rule_id, event_id) VALUES(4029, 4031);


/* Homeless Clinical Reminder HF from Sandbox Server. Add Measure Variables */
-- INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2005, 1, 'Homeless Question 1', 'Homeless Question 1', 63);
-- This question has been defined in 27_reference_data_followup_question_rules.sql assessment_variable_id = 2002

/* Homeless Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (4006, 'LIVES IN HOUSE NO SUBSIDY', '[2002] == 1'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4007, 'LIVES IN HOUSE WITH SUBSID', '[2002] == 2'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4008, 'LIVES WITH FRIEND OR FAMILY', '[2002] == 3');
INSERT INTO rule (rule_id, name, expression) VALUES (4009, 'LIVES IN MOTEL/HOTEL', '[2002] == 4'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4010, 'LIVES IN INSTITUTION', '[2002] == 5'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4011, 'LIVES IN SHELTER', '[2002] == 6'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4012, 'LIVES ON STREET', '[2002] == 7'); 
INSERT INTO rule (rule_id, name, expression) VALUES (4013, 'LIVES OTHER', '[2002] == 8'); 

/* HomelessHealth Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4006, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4007, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4008, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4009, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4010, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4011, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4012, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4013, 2002);

/* Homeless Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4014, 2, 'Add LIVES IN HOUSE NO SUBSIDY  HF', 11);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4015, 2, 'Add LIVES IN HOUSE WITH SUBSID HF', 12);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4016, 2, 'Add LIVES WITH FRIEND OR FAMILY HF', 13);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4017, 2, 'Add LIVES IN MOTEL/HOTEL  HF', 14);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4018, 2, 'Add LIVES IN INSTITUTION HF', 15);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4019, 2, 'Add LIVES IN SHELTER HF', 16);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4020, 2, 'Add LIVES ON STREET HF', 17);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4021, 2, 'Add LIVES OTHER HF', 18);

/* Homeless Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(4006, 4014);
INSERT INTO rule_event (rule_id, event_id) VALUES(4007, 4015);
INSERT INTO rule_event (rule_id, event_id) VALUES(4008, 4016);
INSERT INTO rule_event (rule_id, event_id) VALUES(4009, 4017);
INSERT INTO rule_event (rule_id, event_id) VALUES(4010, 4018);
INSERT INTO rule_event (rule_id, event_id) VALUES(4011, 4019);
INSERT INTO rule_event (rule_id, event_id) VALUES(4012, 4020);
INSERT INTO rule_event (rule_id, event_id) VALUES(4013, 4021);




/****************************************************************************************************************************************
 ************************ OOO Infect & Embedded Fragment CR from Sandbox Server. Add Measure Variables ********************************** 
 ****************************************************************************************************************************************/


/* VA-Embedded fragments Clinical Reminder HF from Sandbox Server */
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (99, 'NO EMBEDDED FRAGMENTS', '613039', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (30, 'EMBEDDED FRAGMENTS PRESENT', '613038', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (31, 'EF-NO BULLET INJURY', '476', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (32, 'EF-NO BLAST/EXPLOSION INJURY', '449', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (33, 'EF-BLAST/EXPLOSION INJURY', '475', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (34, 'EF-NOT IN VEHICLE', '473', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (35, 'EF-IN VEHICLE', '474', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (36, 'EF-IED', '472',90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (37, 'EF-RPG', '471', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (38, 'EF-LAND MINE', '470', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (39, 'EF-GRENADE', '469', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (40, 'EF-ENEMY FIRE', '468', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (41, 'EF-FRIENDLY FIRE', '467', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (42, 'EF-BLAST SOURCE UNKNOWN', '466', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (43, 'EF-BLAST SOURCE OTHER', '465', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (44, 'EF-FRAGMENTS NOT REMOVED IN SURGERY', '460', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (45, 'EF-NO FRAGMENTS IN BODY', '454', 43, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (46, 'EF-CONTACT NAME', '452', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (47, 'EF-CONTACT PHONE NUMBER', '451', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (48, 'EF-CONTACT EMAIL', '450', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (49, 'EF-UNKNOWN IF FRAGMENTS IN BODY', '453', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (50,  'EF-FRAGMENTS IN BODY', '458', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (51, 'EF-NO FRAGMENTS ON RADIOGRAPH', '457', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (52, 'EF-UNKNOWN IF FRAGMENTS ON RADIOGRAPH', '456', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (53, 'EF-FRAGMENTS ON RADIOGRAPH', '455', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (54, 'EF-UNKNOWN IF REMOVED IN SURGERY', '459', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (55, 'EF-FRAGMENTS REMOVED IN SURGERY', '464', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (56, 'EF-FRAGMENTS NOT SENT TO LAB', '463', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (57, 'EF-UNKNOWN IF SENT TO LAB', '462', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (58, 'EF-FRAGMENTS SENT TO LAB', '461', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (59, 'EF-BULLET INJURY', '477', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (60, 'EF-NO BLAST/EXPLOSION INJURY', '449', 90, 0);

INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (200, 'GI SYMPTOMS SCREEN NEGATIVE', '127', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (201, 'GI SYMPTOMS SCREEN POSITIVE', '126', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (202, 'UNEXPLAINED FEVERS SCREEN NEGATIVE', '129', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (203, 'UNEXPLAINED FEVERS SCREEN POSITIVE', '128', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (204, 'SKIN LESION SCREEN NEGATIVE', '132', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (205, 'SKIN LESION SCREEN POSITIVE', '124', 90, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (206, 'REFUSED ID & OTHER SX SCREEN', '612688', 90, 0);


/* VA-Embedded fragments  Clinical Reminder HF from Sandbox Server. Add Measure Variables */
-- Moved to 27_reference_data_template_variables.sql  --

/*VA-Embedded fragments  Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (5001, 'NO EMBEDDED FRAGMENTS', '[2009] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (5002, 'EMBEDDED FRAGMENTS PRESENT ', '[2009] == 1');
INSERT INTO rule (rule_id, name, expression) VALUES (6500, 'GI SYMPTOMS SCREEN NEGATIVE', '[2500] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (6501, 'GI SYMPTOMS SCREEN POSITIVE', '[2500] == 1');
INSERT INTO rule (rule_id, name, expression) VALUES (6502, 'UNEXPLAINED FEVERS SCREEN NEGATIVE', '[2501] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (6503, 'UNEXPLAINED FEVERS SCREEN POSITIVE', '[2501] == 1');
INSERT INTO rule (rule_id, name, expression) VALUES (6504, 'SKIN LESION SCREEN NEGATIVE', '[2502] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (6505, 'SKIN LESION SCREEN POSITIVE', '[2502] == 1');
INSERT INTO rule (rule_id, name, expression) VALUES (6506, 'REFUSED ID & OTHER SX SCREEN', '[2009]==null ||[2500]==null || [2501]==null ||[2502]==null');



/* VA-Embedded fragments Health Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (5001, 2009);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (5002, 2009);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6500, 2500);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6501, 2500);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6502, 2501);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6503, 2501);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6504, 2502);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6505, 2502);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6506, 2500);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6506, 2501);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6506, 2502);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6506, 2009);

/*VA-Embedded Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (5003, 2, 'Add NO EMBEDDED FRAGMENTS  HF', 99);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (5004, 2, 'Add EMBEDDED FRAGMENTS PRESENT  HF', 30);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7500, 2, 'Add GI SYMPTOMS SCREEN NEGATIVE', 200);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7501, 2, 'Add GI SYMPTOMS SCREEN POSITIVE', 201);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7502, 2, 'Add UNEXPLAINED FEVERS SCREEN NEGATIVE', 202);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7503, 2, 'Add UNEXPLAINED FEVERS SCREEN POSITIVE', 203);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7504, 2, 'Add SKIN LESION SCREEN NEGATIVE', 204);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7505, 2, 'Add SKIN LESION SCREEN POSITIVE', 205);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7506, 2, 'Add REFUSED ID & OTHER SX SCREEN', 206);


/* VA-Embedded  Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(5001, 5003);
INSERT INTO rule_event (rule_id, event_id) VALUES(5002, 5004);
INSERT INTO rule_event (rule_id, event_id) VALUES(6500, 7500);
INSERT INTO rule_event (rule_id, event_id) VALUES(6501, 7501);
INSERT INTO rule_event (rule_id, event_id) VALUES(6502, 7502);
INSERT INTO rule_event (rule_id, event_id) VALUES(6503, 7503);
INSERT INTO rule_event (rule_id, event_id) VALUES(6504, 7504);
INSERT INTO rule_event (rule_id, event_id) VALUES(6505, 7505);
INSERT INTO rule_event (rule_id, event_id) VALUES(6506, 7506);




/****************************************************************************************************************************************
 ************************* BTBIS Module (TBI Clinical Reminder) HF from Sandbox Server. Add Measure Variables *************************** 
 ****************************************************************************************************************************************/


/* Tbi Health Factor: HF from Sandbox Server */
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (61, 'NO IRAQ/AFGHAN SERVICE', '122', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (62, 'IRAQ/AFGHAN SERVICE', '125', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (63, 'TBI-PREVIOUS TBI DX', '618380', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (64, 'TBI-BLAST', '618335', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (65, 'TBI-VEHICULAR', '618333', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (66, 'TBI-FRAGMENT/BULLET', '618331', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (67, 'TBI-FALL', '618334', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (68, 'TBI-BLOW TO HEAD', '612929', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (69, 'TBI-OTHER INJURY TO HEAD', '612930', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (70, 'TBI-SECTION I - NO', '618372', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (71, 'TBI-SECTION I - YES', '618373', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (72, 'TBI-UNCONSCIOUS', '618341', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (73, 'TBI-DAZED/CONFUSED', '618337', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (74, 'TBI-NO MEMORY OF INJURY', '618338', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (75, 'TBI-CONCUSSION', '618342', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (76, 'TBI-HEAD INJURY', '618343', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (77, 'TBI-SECTION II - NO', '618374', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (78, ' TBI-SECTION II - YES', '618375', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (79, 'TBI-MEMORY PROBLEMS', '618347', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (80, 'TBI-DIZZINESS', '618346', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (81, 'TBI-VISUAL PROBLEMS', '618354', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (82, 'TBI-IRRITABILITY', '618352', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (83, 'TBI-HEADACHES', '618345', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (84, 'TBI-SLEEP PROBLEMS', '618353', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (85, 'TBI-SECTION III - NO', '618376', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (86, 'TBI-SECTION III - YES', '618377', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (87, 'TBI-CURRENT MEMORY PROBLEMS', '618382', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (88, 'TBI-CURRENT DIZZINESS', '618383', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (89, 'TBI-CURRENT VISUAL PROBLEMS', '618384', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (90, 'TBI-CURRENT IRRITABILITY', '618385', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (91, 'TBI-CURRENT HEADACHES', '618386', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (92, 'TBI-CURRENT SLEEP PROBLEM', '618395', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (93, 'TBI-SECTION IV - NO', '618378', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (94, 'TBI-SECTION IV - YES', '618379', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (95, 'TBI-REFERRAL SENT', '619393', 93, 0);  -- Correct IEN 618393  Wrong IEN 619393
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (96, 'TBI-REFERRAL DECLINED', '619394', 93, 0); -- Correct IEN 618394 Wrong IEN 619394
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (97, 'TBI-PT REFUSAL', '618387', 93, 0);
INSERT INTO health_factor (health_factor_id, name, vista_ien, clinical_reminder_id, is_historical) VALUES (98, ' TBI-SCREENED PREVIOUSLY', '612931', 93, 0);



/*********************************************
     HF Rules for Question 412
**********************************************/

/* Blast Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6001, 'TBI-BLAST', '[2010]'); 

/* Blast Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6001, 2010);

/* Blast Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6002, 2, 'Add TBI-BLAST  HF', 64);

/* Blast Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6001, 6002);



/* Vehicular Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6003, 'TBI-VEHICULAR', '[2011]'); 

/* Vehicular Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6003, 2011);


/* Vehicular Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6004, 2, 'Add TBI-VEHICULAR  HF', 65);

/* Vehicular  Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6003, 6004);



/* Fragment Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6005, 'TBI-FRAGMENT/BULLET', '[2012]'); 

/* Fragment Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6005, 2012);

/* Fragment Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6006, 2, 'Add TBI-FRAGMENT/BULLET  HF', 66);

/* Fragment Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6005, 6006);



/* Fall Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6007, 'TBI-FALL', '[2013]'); 

/* Fall Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6007, 2013);

/* Fall Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6008, 2, 'Add TBI-FALL  HF', 67);

/* Fall Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6007, 6008);



/* Blow to head Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6009, 'TBI-BLOW TO HEAD', '[2014]'); 

/* Blow to head Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6009, 2014);

/* Blow to head Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6010, 2, 'Add TBI-BLOW TO HEAD  HF', 68);

/* Blow to head Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6009, 6010);



/* Other Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6011, 'TBI-OTHER INJURY TO HEAD', '[2015]');

/* Other Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6011, 2015);

/* Other Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6012, 2, 'Add TBI-OTHER INJURY TO HEAD  HF', 69);

/* Other Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6011, 6012);


/* Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6013, 'TBI-SECTION I - NO', '[2016]'); 
INSERT INTO rule (rule_id, name, expression) VALUES (6014, 'TBI-SECTION I - YES', '[2010] || [2011] || [2012] || [2013] || [2014] || [2015]');
INSERT INTO rule (rule_id, name, expression) VALUES (6067, 'TBI-PT REFUSAL', '!([2010]||[2011]||[2012]||[2013]||[2014]||[2015]||[2016])');

/* Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6013, 2016);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6014, 2010);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6014, 2011);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6014, 2012);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6014, 2013);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6014, 2014);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6014, 2015);

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6067, 2010);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6067, 2011);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6067, 2012);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6067, 2013);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6067, 2014);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6067, 2015);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6067, 2016);


/* Tbi Health Factor: Add event for adding health factors */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6015, 2, 'Add TBI-SECTION I - NO  HF', 70);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6016, 2, 'Add TBI-SECTION I - YES  HF', 71);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6067, 2, 'Add TBI-PT REFUSAL', 97);

/* Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6013, 6015);
INSERT INTO rule_event (rule_id, event_id) VALUES(6014, 6016);
INSERT INTO rule_event (rule_id, event_id) VALUES(6067, 6067);



/*********************************************
     HF Rules for Question 413
**********************************************/

/* Loss Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6017, 'TBI-UNCONSCIOUS', '[2017]'); 

/* Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6017, 2017);

/* Loss Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6018, 2, 'Add TBI-UNCONSCIOUS HF', 72);

/* Loss Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6017, 6018);



/* Dazed Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6019, 'TBI-DAZED/CONFUSED', '[2018]'); 

/* Dazed Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6019, 2018);

/* Dazed Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6020, 2, 'Add TBI-DAZED/CONFUSED HF', 73);

/* Dazed Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6019, 6020);




/* Memory Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6021, 'TBI-NO MEMORY OF INJURY', '[2019]'); 

/* Memory Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6021, 2019);

/* Memory Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6022, 2, 'Add TBI-NO MEMORY OF INJURY HF', 74);

/* Memory Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6021, 6022);



/* Concussion Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6023, 'TBI-CONCUSSION', '[2020]'); 

/* Concussion Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6023, 2020);

/* Concussion Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6024, 2, 'Add TBI-CONCUSSION HF', 75);

/* Concussion Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6023, 6024);



/* Head injury Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6025, 'TBI-HEAD INJURY', '[2021]'); 

/* Head injury Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6025, 2021);

/* Head injury Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6026, 2, 'Add TBI-HEAD INJURY HF', 76);

/* Head injury Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6025, 6026);



/* Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6027, 'TBI-SECTION II - NO', '[2022]'); 
INSERT INTO rule (rule_id, name, expression) VALUES (6028, 'TBI-SECTION II - YES', '[2017] || [2018] || [2019] || [2020] || [2021]');

/* Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6027, 2022);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6028, 2017);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6028, 2018);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6028, 2019);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6028, 2020);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6028, 2021);

/* Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6029, 2, 'Add TBI-SECTION II - NO  HF', 77);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6030, 2, 'Add TBI-SECTION II - YES  HF', 78);

/* Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6027, 6029);
INSERT INTO rule_event (rule_id, event_id) VALUES(6028, 6030);


/*********************************************
     HF Rules for Question 414
**********************************************/

/* Memory Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6031, 'TBI-MEMORY PROBLEMS', '[2023]'); 

/* Memory Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6031, 2023);

/* Memory Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6032, 2, 'Add TBI-MEMORY PROBLEMS HF', 79);

/* Memory Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6031, 6032);



/* Balance Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6033, 'TBI-DIZZINESS', '[2024]'); 

/* Balance Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6033, 2024);

/* Balance Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6034, 2, 'Add TBI-DIZZINESS HF', 80);

/* Balance Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6033, 6034);



/* Sensitivity to light Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6035, 'TBI-VISUAL PROBLEMS', '[2025]'); 

/* Sensitivity to light Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6035, 2025);

/* Sensitivity to light Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6036, 2, 'Add TBI-VISUAL PROBLEMS HF', 81);

/* Sensitivity to light Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6035, 6036);



/* Irritability Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6039, 'TBI-IRRITABILITY', '[2027]'); 

/* Irritability Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6039, 2027);

/* Irritability Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6040, 2, 'Add TBI-IRRITABILITY  HF', 82);

/* Irritability Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6039, 6040);



/* Headaches Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6041, 'TBI-HEADACHES', '[2028]'); 

/* Headaches Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6041, 2028);

/* Headaches Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6042, 2, 'Add TBI-HEADACHES  HF', 83);

/* Headaches Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6041, 6042);



/* Sleep problems Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6043, 'TBI-SLEEP PROBLEMS', '[2029]'); 

/* Sleep problems Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6043, 2029);

/* Sleep problems Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6044, 2, 'Add TBI-SLEEP PROBLEMS  HF', 84);

/* Sleep problems Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6043, 6044);



/* Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6045, 'TBI-SECTION III - NO', '[2030]'); 
INSERT INTO rule (rule_id, name, expression) VALUES (6046, 'TBI-SECTION III - YES', '[2023] || [2024] || [2025] || [2027] || [2028] || [2029]');

/* Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6045, 2030);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6046, 2023);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6046, 2024);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6046, 2025);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6046, 2027);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6046, 2028);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6046, 2029);

/* Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6047, 2, 'Add TBI-SECTION III - NO  HF', 85);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6048, 2, 'Add TBI-SECTION III - YES  HF', 86);

/* Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6045, 6047);
INSERT INTO rule_event (rule_id, event_id) VALUES(6046, 6048);



/*********************************************
     HF Rules for Question 415
**********************************************/

/* Memory Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6049, 'TBI-CURRENT MEMORY PROBLEMS', '[2031]'); 

/* Memory Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6049, 2031);

/* Memory Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6050, 2, 'Add TBI-CURRENT MEMORY PROBLEMS HF', 87);

/* Memory Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6049, 6050);



/* Balance Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6051, 'TBI-CURRENT DIZZINESS', '[2032]'); 

/* Balance Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6051, 2032);

/* Balance Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6052, 2, 'Add TBI-CURRENT DIZZINESS HF', 88);

/* Balance Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6051, 6052);


/* Sensitivity to light Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6053, 'TBI-CURRENT VISUAL PROBLEMS', '[2033]'); 

/* Sensitivity to light Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6053, 2033);

/* Sensitivity to light Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6054, 2, 'Add TBI-CURRENT VISUAL PROBLEMS  HF', 89);

/* Sensitivity to light Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6053, 6054);


/* Irritability Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6055, 'TBI-CURRENT IRRITABILITY', '[2034]'); 

/* Irritability Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6055, 2034);

/* Irritability Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6056, 2, 'Add TBI-CURRENT IRRITABILITY HF', 90);

/* Irritability Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6055, 6056);



/* Headaches Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6057, 'TBI-CURRENT HEADACHES', '[2035]'); 

/* Headaches Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6057, 2035);

/* Headaches Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6058, 2, 'Add TBI-CURRENT HEADACHES  HF', 91);

/* Headaches Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6057, 6058);



/* Sleep problems Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6059, 'TBI-CURRENT SLEEP PROBLEM', '[2036]'); 

/* Sleep problems Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6059, 2036);

/* Sleep problems Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6060, 2, 'Add TBI-CURRENT SLEEP PROBLEM  HF', 92);

/* Sleep problems Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6059, 6060);



/* Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6061, 'TBI-SECTION IV - NO', '[2037]'); 
INSERT INTO rule (rule_id, name, expression) VALUES (6062, 'TBI-SECTION IV - YES', '[2031] || [2032] || [2033] || [2034] || [2035] ||[2036]');

/* Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6061, 2037);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6062, 2031);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6062, 2032);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6062, 2033);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6062, 2034);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6062, 2035);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6062, 2036);

/* Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6063, 2, 'Add TBI-SECTION IV - NO  HF', 93);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6064, 2, 'Add TBI-SECTION IV - YES  HF', 94);

/* Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6061, 6063);
INSERT INTO rule_event (rule_id, event_id) VALUES(6062, 6064);


/*********************************************
     HF Rules for Question 443
**********************************************/

/* Tbi Health Factor: Add rules */
INSERT INTO rule (rule_id, name, expression) VALUES (6065, 'TBI-REFERRAL DECLINED', '[2047] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (6066, 'TBI-REFERRAL SENT', '[2047] == 1');

/* Tbi Factor: Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6065, 2047);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6066, 2047);


/* Tbi Health Factor: Add event for adding health factors for Homeless Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6073, 2, 'Add TBI-REFERRAL DECLINED HF', 96);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6074, 2, 'Add TBI-REFERRAL SENT HF', 95);

/* Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6065, 6073);
INSERT INTO rule_event (rule_id, event_id) VALUES(6066, 6074);





