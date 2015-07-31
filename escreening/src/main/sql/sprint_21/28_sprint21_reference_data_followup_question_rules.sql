/* *********************************************************************/
/* Create Follow-Up Question Rules and add needed assessment variables */
/* *********************************************************************/

/** DAST-10 **/

INSERT INTO rule (rule_id, name, expression) VALUES (51, 'Show - DAST-10 follow up questions?', '[1000]==1');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (51, 1000);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (51, 4, 'Show - Dast 10 follow up?', 381);
INSERT INTO rule_event (rule_id, event_id) VALUES(51, 51);







