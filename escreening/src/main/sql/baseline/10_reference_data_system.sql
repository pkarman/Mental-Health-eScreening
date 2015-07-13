/* Insert lookup table data */
INSERT calculation_type (calculation_type_id, name) VALUES (1, 'number');
INSERT calculation_type (calculation_type_id, name) VALUES (2, 'userenterednumber');
INSERT calculation_type (calculation_type_id, name) VALUES (3, 'userenteredstring');
INSERT calculation_type (calculation_type_id, name) VALUES (4, 'userenteredboolean');

INSERT user_status (user_status_id, name) VALUES (1, 'Active');
INSERT user_status (user_status_id, name) VALUES (2, 'Inactive'); 
INSERT user_status (user_status_id, name) VALUES (3, 'Deleted');

INSERT assessment_status (assessment_status_id, name) VALUES (1, 'Clean');
INSERT assessment_status (assessment_status_id, name) VALUES (2, 'Incomplete');
INSERT assessment_status (assessment_status_id, name) VALUES (3, 'Complete');
INSERT assessment_status (assessment_status_id, name) VALUES (4, 'Reviewed');
INSERT assessment_status (assessment_status_id, name) VALUES (5, 'Finalized');
INSERT assessment_status (assessment_status_id, name) VALUES (6, 'Error');
INSERT assessment_status (assessment_status_id, name) VALUES (7, 'Deleted');

INSERT veteran_assessment_event (veteran_assessment_event_id, name) VALUES (1, N'Assessment created');
INSERT veteran_assessment_event (veteran_assessment_event_id, name) VALUES (2, N'Survey(s) assigned to assessment');
INSERT veteran_assessment_event (veteran_assessment_event_id, name) VALUES (3, N'Assessment updated');
INSERT veteran_assessment_event (veteran_assessment_event_id, name) VALUES (4, N'Assessment marked as completed');
INSERT veteran_assessment_event (veteran_assessment_event_id, name) VALUES (5, N'Assessment marked as finalized');
INSERT veteran_assessment_event (veteran_assessment_event_id, name) VALUES (6, N'Assessment saved to Vista Successfully');

INSERT person_type (person_type_id, name) VALUES (1, 'USER');
INSERT person_type (person_type_id, name) VALUES (2, 'VETERAN');

INSERT dashboard_alert_type (dashboard_alert_type_id, name) VALUES (1, 'Rule Triggered Alert');

INSERT event_type (event_type_id, name) VALUES (1, 'Add Consult');
INSERT event_type (event_type_id, name) VALUES (2, 'Add Health Factor');
INSERT event_type (event_type_id, name) VALUES (3, 'Add Dashboard Alert');
INSERT event_type (event_type_id, name) VALUES (4, 'Show Question');

INSERT assessment_variable_type (assessment_variable_type_id, name, description) VALUES (1, 'Measure', 'Assessment measure');
INSERT assessment_variable_type (assessment_variable_type_id, name, description) VALUES (2, 'Measure Answer', 'Assessment measure answer');
INSERT assessment_variable_type (assessment_variable_type_id, name, description) VALUES (3, 'Custom', 'The mapping of this variable requires custom logic');
INSERT assessment_variable_type (assessment_variable_type_id, name, description) VALUES (4, 'Formula', 'Formula such as ((X + Y) / Z)');

INSERT template_type (template_type_id, name, description) VALUES (1, 'Note Header', 'Template used to generate the header of a Note. Associated with a Battery.');
INSERT template_type (template_type_id, name, description) VALUES (2, 'Note Footer', 'Template used to generate the footer of a Note. Associated with a Battery.');
INSERT template_type (template_type_id, name, description) VALUES (3, 'Note Entry', 'Template used to generate the body elements of a Note. Associated with a Module.');
INSERT template_type (template_type_id, name, description) VALUES (4, 'Note Special', 'Template used to generate a special section of a Note. Associated with a Battery.');
INSERT template_type (template_type_id, name, description) VALUES (5, 'Note Conclusin', 'Template used to generate the conclusion of a Note. Associated with a Battery.');


/* Question types */
INSERT INTO measure_type (measure_type_id, name) VALUES (1, 'freeText');
INSERT INTO measure_type (measure_type_id, name) VALUES (2, 'selectOne');
INSERT INTO measure_type (measure_type_id, name) VALUES (3, 'selectMulti');
INSERT INTO measure_type (measure_type_id, name) VALUES (4, 'tableQuestion');
INSERT INTO measure_type (measure_type_id, name) VALUES (5, 'readOnlyText');
INSERT INTO measure_type (measure_type_id, name) VALUES (6, 'selectOneMatrix');
INSERT INTO measure_type (measure_type_id, name) VALUES (7, 'selectMultiMatrix');
INSERT INTO measure_type (measure_type_id, name) VALUES (8, 'instruction');

/* Validations */
INSERT INTO validation (validation_id, code, description, data_type) VALUES (1, 'dataType', 'Data Type', 'string');
/* This validation has been deprecated. If you want to make a measure required set the measure's is_required field to true. */
/* INSERT INTO validation (validation_id, code, description, data_type) VALUES (2, 'required', 'warn or mandatory', 'string'); */
INSERT INTO validation (validation_id, code, description, data_type) VALUES (3, 'warnIfSkipped', 'Warn if skipped', 'string');
INSERT INTO validation (validation_id, code, description, data_type) VALUES (4, 'minLength', 'Min Length', 'number');
INSERT INTO validation (validation_id, code, description, data_type) VALUES (5, 'maxLength', 'Max Length', 'number');
INSERT INTO validation (validation_id, code, description, data_type) VALUES (6, 'minValue', 'Min Value', 'number');
INSERT INTO validation (validation_id, code, description, data_type) VALUES (7, 'maxValue', 'Max Value', 'number');
INSERT INTO validation (validation_id, code, description, data_type) VALUES (8, 'exactValue', 'Exact Value', 'number');
INSERT INTO validation (validation_id, code, description, data_type) VALUES (9, 'exactLength', 'Exact Length', 'number');

/* Export type */
INSERT INTO export_type (export_type_id, name) VALUES (1, 'Identified');
INSERT INTO export_type (export_type_id, name) VALUES (2, 'De-identified');

/* Export data filter options */
INSERT INTO exportdata_filter_options (exportdata_filter_options_id, name, num_days, description) VALUES (1, 'Last 30 days', 30, 'Exports for the last 30 days');
INSERT INTO exportdata_filter_options (exportdata_filter_options_id, name, num_days, description) VALUES (2, 'Last 60 days', 60, 'Exports for the last 60 days');
INSERT INTO exportdata_filter_options (exportdata_filter_options_id, name, num_days, description) VALUES (3, 'Last 90 days', 90, 'Exports for the last 90 days');
INSERT INTO exportdata_filter_options (exportdata_filter_options_id, name, num_days, description) VALUES (4, 'Last 180 days', 180, 'Exports for the last 180 days');
INSERT INTO exportdata_filter_options (exportdata_filter_options_id, name, num_days, description) VALUES (5, 'Last  365 days', 365, 'Exports for the last 365 days');
INSERT INTO exportdata_filter_options (exportdata_filter_options_id, name, num_days, description) VALUES (6, 'All exports', -1, 'All of the exports');

/* These correspond to roles used by Spring Security */
INSERT role (role_id, name) VALUES (1, 'Consultation and Program Evaluation Administrator');
/*INSERT role (role_id, name) VALUES (2, 'Healthcare System Clinical Administrator');*/
INSERT role (role_id, name) VALUES (3, 'Healthcare System Technical Administrator');
INSERT role (role_id, name) VALUES (4, 'Clinician');
INSERT role (role_id, name) VALUES (5, 'Assistant');

/* system properities stored in DB */
INSERT system_property (system_property_id, name, text_value, description) VALUES (1, 'ESCREENING_PACKET_VERSION', '1.1.801', 'Version number of the eScreening assessment packet.');

/* Possible Clinical Reminder Dialog prompts used for saving Health Factors to VistA */
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (1, 'COM');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (2, 'VST_LOC');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (3, 'VST_DATE');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (4, 'CPT_QTY');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (5, 'POV_PRIM');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (6, 'POV_ADD');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (7, 'XAM_RES');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (8, 'SK_RES');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (9, 'SK_READ');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (10, 'HF_LVL');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (11, 'IMM_SER');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (12, 'IMM_RCTN');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (13, 'IMM_CNTR');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (14, 'PED_LVL');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (15, 'WH_PAP_RESULT');
INSERT INTO dialog_prompt (dialog_prompt_id, name) VALUES (16, 'WH_NOT_PURP');
