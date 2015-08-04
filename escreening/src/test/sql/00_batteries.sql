INSERT INTO battery (battery_id, name) VALUES (1, 'Aspire Intake Battery');
INSERT INTO battery (battery_id, name) VALUES (2, 'Aspire Progress Monitoring Battery');
INSERT INTO battery (battery_id, name) VALUES (3, 'Aspire Discharge Battery');

/* Map Aspire Intake Battery */
INSERT INTO battery_survey (battery_id, survey_id) VALUES (1, 1);
INSERT INTO battery_survey (battery_id, survey_id) VALUES (1, 2);

/* Map Aspire Progress Monitoring Battery */
INSERT INTO battery_survey (battery_id, survey_id) VALUES (2, 3);
INSERT INTO battery_survey (battery_id, survey_id) VALUES (2, 4);

/* Map Aspire Discharge Battery */
INSERT INTO battery_survey (battery_id, survey_id) VALUES (3, 5);
INSERT INTO battery_survey (battery_id, survey_id) VALUES (3, 6);