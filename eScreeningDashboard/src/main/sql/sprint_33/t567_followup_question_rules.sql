/* *********************************************************************/
/* Create Follow-Up Question Rules and add needed assessment variables */
/* *********************************************************************/

/* Any answer that will be checked by a rule (i.e. to decide to show follow-up quesitons) will have an assessment_variable created for it.
 * For each follow-up question, an event with that question's measure_id will be created
 */

/**** Presenting Mental Health Issue MODULE ****/

INSERT INTO rule (rule_id, name, expression) VALUES (7600, 'Today Drug', '[10875]');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (7600, 10875);

/* Create ShowQuestion events (type_id=4) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7600, 4, 'Show - How long since you last used alcohol or drugs?', 610);

/* Link rule to ShowQuestion events */
INSERT INTO rule_event (rule_id, event_id) VALUES(7600, 7600);


INSERT INTO rule (rule_id, name, expression) VALUES (7601, 'Today Worth', '[10879]');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (7601, 10879);

/* Create ShowQuestion events (type_id=4) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7601, 4, 'Show - How long have you had thoughts that life is not worth living?', 611);

/* Link rule to ShowQuestion events */
INSERT INTO rule_event (rule_id, event_id) VALUES(7601, 7601);

INSERT INTO rule (rule_id, name, expression) VALUES (7602, 'Today Form', '[10881]');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (7602, 10881);

/* Create ShowQuestion events (type_id=4) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7602, 4, 'Show - Which paperwork/forms do you need help with?', 613);

/* Link rule to ShowQuestion events */
INSERT INTO rule_event (rule_id, event_id) VALUES(7602, 7602);

INSERT INTO rule (rule_id, name, expression) VALUES (7603, 'Today med', '[10882] || [10883]');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (7603, 10882);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (7603, 10883);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7603, 4, 'Show - Medications will be mailed unless', 612);
INSERT INTO rule_event (rule_id, event_id) VALUES(7603, 7603);

/********** Prior Hospitalizations and Meds module *******************/
INSERT INTO rule (rule_id, name, expression) VALUES (7610, 'Med VA', '[10955]==2');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (7610, 10955);

/* Create ShowQuestion events (type_id=4) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7610, 4, 'Show - I stopped because:?', 623);

/* Link rule to ShowQuestion events */
INSERT INTO rule_event (rule_id, event_id) VALUES(7610, 7610);

