
/* Add test consults */
INSERT INTO consult (consult_id, name, vista_ien) VALUES (1000, 'Weight Management', 333);
INSERT INTO consult (consult_id, name, vista_ien) VALUES (2000, 'Vocational Rehabilitation Clinic', 334);

/* Add test rules */
INSERT INTO rule (rule_id, name, expression)
	VALUES (4001, 'Weight Control', '[142] > 350');

INSERT INTO rule (rule_id, name, expression) 
	VALUES (4002, 'No Employment', '[60] == 5'); 
	/* measure_answer_id =  254 is assessment variable 60's option with calculated value == 5*/

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4001, 142);  
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4002, 60);

/* Add test event */

/* events for adding consults */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4001, 1, 'Add high weight Consult', 1000);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4002, 1, 'Add no employment Consult', 2000);


/* Associate rules to events */

/* Weight management rule */
INSERT INTO rule_event (rule_id, event_id) VALUES(4001, 4001);

/* Empoyment rule */
INSERT INTO rule_event (rule_id, event_id) VALUES(4002, 4002);




/* MST Health Factor: Test data for veteran assessment health factors. */
INSERT INTO veteran_assessment_health_factor (veteran_assessment_id, health_factor_id) VALUES (55, 3);