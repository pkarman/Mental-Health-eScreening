/* *******************************************************************/
/* Create Health Factors, HF Rules, and needed assessment variables  */
/* *******************************************************************/


/* *********************** Advanced Directives ********************************* */ 
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (221, 'PREFERRED HEALTHCARE LANGUAGE-ENGLISH', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (222, 'PREFERRED HEALTHCARE LANGUAGE-SPANISH', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (223, 'PREFERRED HEALTHCARE LANGUAGE-TAGALOG', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (224, 'PREFERRED HEALTHCARE LANGUAGE-CHINESE', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (225, 'PREFERRED HEALTHCARE LANGUAGE-GERMAN', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (226, 'PREFERRED HEALTHCARE LANGUAGE-JAPANESE', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (227, 'PREFERRED HEALTHCARE LANGUAGE-KOREAN', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (228, 'PREFERRED HEALTHCARE LANGUAGE-RUSSIAN', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (229, 'PREFERRED HEALTHCARE LANGUAGE-VIETNAMESE', 91, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (230, 'PREFERRED HEALTHCARE LANGUAGE-OTHER', 91, 0);

INSERT INTO rule (rule_id, name, expression) VALUES (3001, 'LANGUAGE-ENGLISH', '[800] == 1'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3002, 'LANGUAGE-SPANISH', '[800] == 2'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3003, 'LANGUAGE-TAGALOG', '[800] == 3');
INSERT INTO rule (rule_id, name, expression) VALUES (3004, 'LANGUAGE-CHINESE', '[800] == 4'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3005, 'LANGUAGE-GERMAN', '[800] == 5'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3006, 'LANGUAGE-JAPANESE', '[800] == 6');
INSERT INTO rule (rule_id, name, expression) VALUES (3007, 'LANGUAGE-KOREAN', '[800] == 7'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3008, 'LANGUAGE-RUSSIAN', '[800] == 8'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3009, 'LANGUAGE-VIETNAMESE', '[800] == 9');
INSERT INTO rule (rule_id, name, expression) VALUES (3010, 'LANGUAGE-OTHER', '[800] == 10');

/* Add association for rules and variables. */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3001, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3002, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3003, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3004, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3005, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3006, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3007, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3008, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3009, 800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3010, 800);


/* MST Health Factor: Add event for adding health factors for MST Clinical Reminder */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3001, 2, 'Add HF LANGUAGE-ENGLISH', 221);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3002, 2, 'Add HF LANGUAGE-SPANISH', 222);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3003, 2, 'Add HF LANGUAGE-TAGALOG', 223);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3004, 2, 'Add HF LANGUAGE-CHINESE', 224);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3005, 2, 'Add HF LANGUAGE-GERMAN', 225);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3006, 2, 'Add HF LANGUAGE-JAPANESE', 226);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3007, 2, 'Add HF LANGUAGE-KOREAN', 227);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3008, 2, 'Add HF LANGUAGE-RUSSIAN', 228);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3009, 2, 'Add HF LANGUAGE-VIETNAMESE', 229);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3010, 2, 'Add HF LANGUAGE-OTHER', 230);

/* MST Health Factor: Associate rules to events */
INSERT INTO rule_event (rule_id, event_id) VALUES(3001, 3001);
INSERT INTO rule_event (rule_id, event_id) VALUES(3002, 3002);
INSERT INTO rule_event (rule_id, event_id) VALUES(3003, 3003);
INSERT INTO rule_event (rule_id, event_id) VALUES(3004, 3004);
INSERT INTO rule_event (rule_id, event_id) VALUES(3005, 3005);
INSERT INTO rule_event (rule_id, event_id) VALUES(3006, 3006);
INSERT INTO rule_event (rule_id, event_id) VALUES(3007, 3007);
INSERT INTO rule_event (rule_id, event_id) VALUES(3008, 3008);
INSERT INTO rule_event (rule_id, event_id) VALUES(3009, 3009);
INSERT INTO rule_event (rule_id, event_id) VALUES(3010, 3010);



