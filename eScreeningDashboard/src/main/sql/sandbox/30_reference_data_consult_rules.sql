/* **********************************************************/
/* Create Consult Rules and add needed assessment variables */
/* **********************************************************/

/* Step 1 Define Consult */
INSERT INTO consult(consult_id, name, vista_ien) VALUES (1, 'Traumatic Brain Injury Consult Order', '1234');

/* Step 2 Create Assessment Variable Traumatic Brain Injury (TBI) Consult Order. */
/* Already done for Traumatic Brain Injury (TBI) Consult Order in 02_health_factors.sql line 758. */
/*
INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (2047, 1, 'If you screen positive for TBI, you want consult', 'TBI - does the veteran want a consult', 443);
*/

/* Step 3 Create Rule for Traumatic Brain Injury (TBI) Consult Order with Rule Expression. */
/* Already done for Traumatic Brain Injury (TBI) Consult Order in 02_health_factors.sql line 761 and 762. */
/*
INSERT INTO rule (rule_id, name, expression) VALUES (6065, 'TBI-REFERRAL DECLINED', '[2047] == 0');
INSERT INTO rule (rule_id, name, expression) VALUES (6066, 'TBI-REFERRAL SENT', '[2047] == 1');
*/

/* Step 4 Associate a Rule with an Assessment Variable for Traumatic Brain Injury (TBI) Consult Order. */
/* Already done for Traumatic Brain Injury (TBI) Consult Order in 02_health_factors.sql line 765 and 766. */
/*
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6065, 2047);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6066, 2047);
*/

/* Step 5 Create Event for Traumatic Brain Injury (TBI) Consult Order. */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7000, 1, 'Add TBI Consult Order', 1);

/* Step 6 Associate Event with a Rule for Traumatic Brain Injury (TBI) Consult Order. */
/* Tbi Health Factor: Add Rule Event */
INSERT INTO rule_event (rule_id, event_id) VALUES(6066, 7000);



