/* Add an assessment with each state value */
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (1, 1, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (2, 2, 5, 8, 1, 10, 50, 2, 1); 
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (3, 3, 5, 9, 1, 10, 50, 3, 1); 
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (4, 1, 5, 7, 0, 150, 100, 4, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (5, 2, 5, 8, 0, 300, 100, 5, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (6, 3, 5, 9, 0, 30, 100, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (7, 1, 5, 7, 0, 40, 100, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (8, 2, 5, 8, 0, 50, 100, 3, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id, signed_by_user_id) VALUES (9, 3, 5, 9, 0, 60, 100, 4, 1, 1);

/* These are associated with a clinician who is not associated with the same clinics at above */
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (10, 4, 5, 1, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (11, 5, 5, 2, 1, 10, 50, 2, 1); 
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (12, 6, 5, 3, 1, 300, 100, 4, 1); 

/* Minimal vets used */
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (13, 14, 6, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (14, 15, 5, 1, 0, 0, 0, 1, 1);

/* Add Surveys to Assessments */
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (1, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (1, 2);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (1, 3);

INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (2, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (2, 2);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (2, 3);

INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (3, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (3, 2);

INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (13, 1);

/* bulk assessment create for Veterans */
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (15, 15, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id, battery_id, note_title_id) VALUES (16, 16, 5, 17, 0, 0, 0, 1, 1, 5, 8);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id, battery_id, note_title_id) VALUES (17, 17, 5, 17, 0, 0, 0, 1, 1, 5, 8);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id, battery_id, note_title_id) VALUES (18, 18, 5, 17, 0, 0, 100, 2, 1, 5, 8);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (19, 19, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (20, 20, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (21, 21, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (22, 22, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (23, 23, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (24, 24, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (25, 25, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (26, 26, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (27, 27, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (28, 28, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (29, 29, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (30, 30, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (31, 31, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (32, 32, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (33, 33, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (34, 34, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (35, 35, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (36, 36, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (37, 37, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (38, 38, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (39, 39, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (40, 40, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (41, 41, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (42, 42, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (43, 43, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (44, 44, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (45, 45, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (46, 46, 5, 7, 0, 0, 0, 2, 2);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (47, 47, 5, 7, 0, 0, 0, 2, 2);
INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (48, 48, 5, 7, 0, 0, 0, 2, 1);
INSERT INTO `veteran_assessment` (`veteran_assessment_id`,`veteran_id`,`clinician_id`,`clinic_id`,`program_id`,`assessment_status_id`,`note_title_id`,`battery_id`,`access_mode`,`duration`,`percent_complete`,`created_by_user_id`,`signed_by_user_id`,`date_completed`,`date_updated`,`date_created`,`date_archived`) VALUES (56,16,5,17,4,3,8,5,NULL,0,100,4,NULL,'2014-09-23 12:36:48','2014-09-23 12:36:48','2014-09-23 12:34:22',NULL);
INSERT INTO `veteran_assessment` (`veteran_assessment_id`,`veteran_id`,`clinician_id`,`clinic_id`,`program_id`,`assessment_status_id`,`note_title_id`,`battery_id`,`access_mode`,`duration`,`percent_complete`,`created_by_user_id`,`signed_by_user_id`,`date_completed`,`date_updated`,`date_created`,`date_archived`) VALUES (57,16,5,18,4,3,7,5,NULL,0,100,4,NULL,'2015-01-23 12:51:17','2015-01-23 12:51:17','2015-01-23 12:38:59',NULL);


/* insert veteran assessment audit log example data */
INSERT INTO veteran_assessment_audit_log (veteran_assessment_audit_log_id, veteran_assessment_id, person_id, person_last_name, person_type_id, assessment_status_id, veteran_assessment_event_id) VALUES (1, 1, 100, 'Clinician', 1, 1, 1);
INSERT INTO veteran_assessment_audit_log (veteran_assessment_audit_log_id, veteran_assessment_id, person_id, person_last_name, person_type_id, assessment_status_id, veteran_assessment_event_id) VALUES (2, 1, 100, 'Clinician', 1, 1, 2);
INSERT INTO veteran_assessment_audit_log (veteran_assessment_audit_log_id, veteran_assessment_id, person_id, person_last_name, person_first_name, person_type_id, assessment_status_id, veteran_assessment_event_id) VALUES (3, 1, 200, 'One', 'Veteran', 2, 2, 3);
INSERT INTO veteran_assessment_audit_log (veteran_assessment_audit_log_id, veteran_assessment_id, person_id, person_last_name, person_first_name, person_type_id, assessment_status_id, veteran_assessment_event_id) VALUES (4, 1, 200, 'One', 'Veteran', 2, 2, 3);
INSERT INTO veteran_assessment_audit_log (veteran_assessment_audit_log_id, veteran_assessment_id, person_id, person_last_name, person_first_name, person_type_id, assessment_status_id, veteran_assessment_event_id) VALUES (5, 1, 200, 'One', 'Veteran', 2, 2, 3);
INSERT INTO veteran_assessment_audit_log (veteran_assessment_audit_log_id, veteran_assessment_id, person_id, person_last_name, person_first_name, person_type_id, assessment_status_id, veteran_assessment_event_id) VALUES (6, 1, 200, 'One', 'Veteran', 2, 4,4);
INSERT INTO veteran_assessment_audit_log (veteran_assessment_audit_log_id, veteran_assessment_id, person_id, person_last_name, person_type_id, assessment_status_id, veteran_assessment_event_id) VALUES (7, 1, 100, 'Clinician', 1, 5, 5);

/*Simple setup for quick testing of OOO */

/* Add full OOO assessment for veteran 18 (set each one to 100% complete because the next file will fill it up with responses. */
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (11,18,1,3,3,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (12,18,2,9,9,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (13,18,3,7,7,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (14,18,4,7,7,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (15,18,5,4,4,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (16,18,6,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (17,18,7,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (18,18,8,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (19,18,9,3,3,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (20,18,10,4,4,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (21,18,11,2,2,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (22,18,12,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (23,18,13,3,3,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (24,18,14,5,5,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (25,18,15,2,2,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (26,18,16,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (27,18,17,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (28,18,18,4,4,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (29,18,19,4,4,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (30,18,20,2,2,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (31,18,21,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (32,18,22,2,2,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (33,18,23,8,8,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (34,18,24,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (35,18,25,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (36,18,26,3,3,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (37,18,27,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (38,18,28,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (39,18,29,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (40,18,30,2,2,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (41,18,31,5,5,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (42,18,32,2,2,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (43,18,33,2,2,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (44,18,34,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (45,18,35,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (46,18,36,5,5,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (47,18,37,1,1,NULL,'2014-08-06 14:09:18');
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_survey_id`,`veteran_assessment_id`,`survey_id`,`total_question_count`,`total_response_count`,`mha_result`,`date_created`) VALUES (48,18,38,1,1,NULL,'2014-08-06 14:09:18');



/* Add a short OOO assessment for veteran 16 */
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (16, 1);

/* Add full OOO assessment for veteran 17 which will have not responses set for it */
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 2);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 3);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 4);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 5);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 6);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 7);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 8);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 9);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 10);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 11); -- table type question
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 12);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 13);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 14);  -- select one matrix
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 15);  -- select multi matrix
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 16);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 17);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 18);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 19);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 20);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 21);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 22);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 23);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 24);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 25);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 26);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 27);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 28);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 29);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 30);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 31);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 32);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 33);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 34);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 35);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 36);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 37);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (17, 38);


/* test data for clinical note template */

INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (49, 49, 5, 7, 0, 0, 0, 2, 4);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 1);
/*
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 2);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 3);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 3);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 5);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 6);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 7);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 8);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 9);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 10);
*/
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 11); -- table type question
/*
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 12);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 13);
*/
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 14);  -- select one matrix
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 15);  -- select multi matrix
/*
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 16);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 17);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 18);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 19);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 20);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 21);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 22);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 23);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 24);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 25);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 26);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 27);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 28);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 29);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 30);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 31);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 32);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 33);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 34);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 35);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 36);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 37);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (49, 38);
*/

INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id,clinician_id,clinic_id,access_mode,duration,percent_complete,assessment_status_id, created_by_user_id) VALUES (50, 49, 5, 7, 0, 0, 0, 2, 4);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (50, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (50, 11);

INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id, clinician_id, clinic_id, access_mode, duration, percent_complete, assessment_status_id, created_by_user_id) VALUES (51, 10, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (51, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (51, 2);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (51, 3);

INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id, clinician_id, clinic_id, access_mode, duration, percent_complete, assessment_status_id, created_by_user_id) VALUES (52, 11, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (52, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (52, 2);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (52, 3);

INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id, clinician_id, clinic_id, access_mode, duration, percent_complete, assessment_status_id, created_by_user_id) VALUES (53, 12, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (53, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (53, 2);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (53, 3);

INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id, clinician_id, clinic_id, access_mode, duration, percent_complete, assessment_status_id, created_by_user_id) VALUES (54, 13, 5, 7, 0, 0, 0, 1, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (54, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (54, 2);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (54, 3);

INSERT INTO veteran_assessment (veteran_assessment_id, veteran_id, clinician_id, clinic_id, access_mode, duration, percent_complete, assessment_status_id, created_by_user_id) VALUES (55, 44, 5, 7, 0, 0, 0, 3, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (55, 1);
INSERT INTO veteran_assessment_survey (veteran_assessment_id, survey_id) VALUES (55, 32);

INSERT INTO `veteran_assessment_survey` (`veteran_assessment_id`,`survey_id`) VALUES (16, 30);
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_id`,`survey_id`) VALUES (56, 30);
INSERT INTO `veteran_assessment_survey` (`veteran_assessment_id`,`survey_id`) VALUES (57, 30);

-- Update the pgoram_id field to be the clinic's
UPDATE veteran_assessment va 
INNER JOIN clinic c ON va.clinic_id = c.clinic_id 
SET va.program_id = c.program_id
WHERE va.program_id IS NULL;

-- Update the note_title_id field (we might have to fix this if some note titles don't make sense for an assessment)
UPDATE veteran_assessment va 
SET va.note_title_id = 3
WHERE va.note_title_id IS NULL;


INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (1, 1);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (1, 2);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (1, 3);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (1, 4);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (1, 5);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (1, 6);

INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (55, 1);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (55, 2);

-- data for alerts graph 
-- veteran_assessment_dashboard_alert should have pointer to veteran_assessment_id which is in Incomplete State (assessment_status_id=2)
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (38, 1);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (39, 2);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (40, 3);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (41, 4);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (42, 5);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (43, 6);

INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (46, 1);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (47, 2);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (48, 3);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (2, 4);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (7, 5);
INSERT INTO veteran_assessment_dashboard_alert (veteran_assessment_id, dashboard_alert_id) VALUES (11, 6);

