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

INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (235, 'ADV DIRECTIVE OUTPT SCREEN COMPLETED', 91, 0);

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


INSERT INTO rule (rule_id, name, expression) VALUES (3015, 'ADV DIRECTIVE OUTPT SCREEN COMPLETED', '([820]==1)||([820]==0)');

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
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3015, 820);


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

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3015, 2, 'Add HF ADV DIRECTIVE OUTPT SCREEN COMPLETED', 235);

/* Advance Directives Health Factor: Associate rules to events */
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
INSERT INTO rule_event (rule_id, event_id) VALUES(3015, 3015);

/****************************************************************************************/
/************** Health Factors for OEF OIF Clinical Reminder ***************************/
/****************************************************************************************/

INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (251, 'IRAQ/AFGHAN SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (252, 'NO IRAQ/AFGHAN SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (253, 'AFGHANISTAN SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (254, 'TAJIKISTAN SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (255, 'GEORGIA SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (256, 'UZBEKISTAN SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (257, 'KYRGYZSTAN SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (258, 'PHILIPPINES SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (259, 'PAKISTAN SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (260, 'OTHER OEF SERVICE', 52, 0);

INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (261, 'IRAQ SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (262, 'KUWAIT SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (263, 'SAUDI ARABIA SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (264, 'TURKEY SERVICE', 52, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (265, 'OTHER OIF SERVICE', 52, 0);

INSERT INTO rule (rule_id, name, expression) VALUES (3051, 'IRAQ/AFGHAN', '([660] == 1) || ([660]==2)'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3052, 'NO IRAQ/AFGHAN SERVICE', '[660] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3053, 'AFGHANISTAN', '[663] == 1');
INSERT INTO rule (rule_id, name, expression) VALUES (3054, 'TAJIKISTAN', '[663] == 2'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3055, 'GEORGIA', '[663] == 4'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3056, 'UZBEKISTAN', '[663] == 5');
INSERT INTO rule (rule_id, name, expression) VALUES (3057, 'KYRGYZSTAN', '[663] == 6'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3058, 'PHILIPPINES', '[663] == 7'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3059, 'PAKISTAN', '[663] == 8');
INSERT INTO rule (rule_id, name, expression) VALUES (3060, 'OTHER OEF SERVICE', '[663] == 10');

INSERT INTO rule (rule_id, name, expression) VALUES (3061, 'IRAQ', '[683]==1'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3062, 'KUWAIT SERVICE', '[683] == 3'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3063, 'SAUDI ARABIA', '[683] == 5');
INSERT INTO rule (rule_id, name, expression) VALUES (3064, 'TURKEY', '[683] == 6'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3065, 'OTHER OIF', '[683] == 9'); 

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3051, 660);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3052, 660);

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3053, 663);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3054, 663);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3055, 663);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3056, 663);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3057, 663);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3058, 663);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3059, 663);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3060, 663);

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3061, 683);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3062, 683);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3063, 683);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3064, 683);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3065, 683);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3051, 2, 'Add HF 251', 251);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3052, 2, 'Add HF 252', 252);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3053, 2, 'Add HF 253', 253);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3054, 2, 'Add HF 254', 254);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3055, 2, 'Add HF 255', 255);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3056, 2, 'Add HF 256', 256);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3057, 2, 'Add HF 257', 257);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3058, 2, 'Add HF 258', 258);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3059, 2, 'Add HF 259', 259);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3060, 2, 'Add HF 260', 260);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3061, 2, 'Add HF 261', 261);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3062, 2, 'Add HF 262', 262);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3063, 2, 'Add HF 263', 263);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3064, 2, 'Add HF 264', 264);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3065, 2, 'Add HF 265', 265);

INSERT INTO rule_event (rule_id, event_id) VALUES(3051, 3051);
INSERT INTO rule_event (rule_id, event_id) VALUES(3052, 3052);
INSERT INTO rule_event (rule_id, event_id) VALUES(3053, 3053);
INSERT INTO rule_event (rule_id, event_id) VALUES(3054, 3054);
INSERT INTO rule_event (rule_id, event_id) VALUES(3055, 3055);
INSERT INTO rule_event (rule_id, event_id) VALUES(3056, 3056);
INSERT INTO rule_event (rule_id, event_id) VALUES(3057, 3057);
INSERT INTO rule_event (rule_id, event_id) VALUES(3058, 3058);
INSERT INTO rule_event (rule_id, event_id) VALUES(3059, 3059);
INSERT INTO rule_event (rule_id, event_id) VALUES(3060, 3060);
INSERT INTO rule_event (rule_id, event_id) VALUES(3061, 3061);
INSERT INTO rule_event (rule_id, event_id) VALUES(3062, 3062);
INSERT INTO rule_event (rule_id, event_id) VALUES(3063, 3063);
INSERT INTO rule_event (rule_id, event_id) VALUES(3064, 3064);
INSERT INTO rule_event (rule_id, event_id) VALUES(3065, 3065);

/*****************************************************************
*************  Tobacco Cessation Screen **************************
******************************************************************/
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (271, 'LIFETIME NON-USER OF TOBACCO', 320, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (272, 'CURRENT TOBACCO USER', 320, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (273, 'QUIT TOBACCO >7 YEARS AGO', 320, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (274, 'QUIT TOBACCO >12 MO & <7 YRS AGO (Historical)', 320, 0);
INSERT INTO health_factor (health_factor_id, name, clinical_reminder_id, is_historical) VALUES (275, 'QUIT TOBACCO IN THE LAST 12 MONTHS', 320, 0);

INSERT INTO rule (rule_id, name, expression) VALUES (3071, 'LIFETIME NON-USER OF TOBACCO', '[600] == 0'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3072, 'CURRENT TOBACCO USER', '[600] == 2'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3073, 'QUIT TOBACCO >7 YEARS AGO', '[610] == 5');
INSERT INTO rule (rule_id, name, expression) VALUES (3074, 'QUIT TOBACCO >12 MO & <7 YRS AGO', '[610] == 3'); 
INSERT INTO rule (rule_id, name, expression) VALUES (3075, 'QUIT TOBACCO IN THE LAST 12 MONTHS', '[610] == 4'); 

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3071, 600);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3072, 600);

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3073, 610);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3074, 610);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3075, 610);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3071, 2, 'Add HF LIFETIME NON-USER OF TOBACCO', 271);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3072, 2, 'Add HF CURRENT TOBACCO USER', 272);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3073, 2, 'Add HF QUIT TOBACCO >7 YEARS AGO', 273);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3074, 2, 'Add HF QUIT TOBACCO >12 MO & <7 YRS AGO', 274);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3075, 2, 'Add HF QUIT TOBACCO IN THE LAST 12 MONTHS', 275);

INSERT INTO rule_event (rule_id, event_id) VALUES(3071, 3071);
INSERT INTO rule_event (rule_id, event_id) VALUES(3072, 3072);
INSERT INTO rule_event (rule_id, event_id) VALUES(3073, 3073);
INSERT INTO rule_event (rule_id, event_id) VALUES(3074, 3074);
INSERT INTO rule_event (rule_id, event_id) VALUES(3075, 3075);

