SET NAMES 'utf8';


/* Create tables */
CREATE TABLE program (
  program_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  is_disabled BOOLEAN NOT NULL DEFAULT 0,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (program_id));

CREATE TABLE survey_section (
  survey_section_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  display_order INT,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (survey_section_id));

CREATE TABLE survey (
  survey_id INT NOT NULL AUTO_INCREMENT,
  survey_section_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  version INT,
  display_order INT,
  has_mha BOOLEAN NOT NULL DEFAULT 0,
  mha_test_name VARCHAR(255),
  mha_result_group_ien VARCHAR(255),
  vista_title VARCHAR(255),
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (survey_id));

CREATE TABLE measure_type (
  measure_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (measure_type_id));

CREATE TABLE measure (
  measure_id INT NOT NULL AUTO_INCREMENT,
  measure_type_id INT NOT NULL,
  measure_text VARCHAR(2000) NOT NULL,
  is_required BOOLEAN NOT NULL DEFAULT 0,
  is_patient_protected_info BOOLEAN NOT NULL DEFAULT 0,
  is_mha BOOLEAN NOT NULL DEFAULT 0,
  score_weight FLOAT NULL DEFAULT 0,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  parent_measure_id INT NULL DEFAULT NULL,
  display_order INT NULL DEFAULT NULL,
  vista_text VARCHAR(2000) NULL,
  variable_name VARCHAR(64) NULL,
PRIMARY KEY (measure_id));

CREATE TABLE calculation_type (
  calculation_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (calculation_type_id));

CREATE TABLE measure_answer (
  measure_answer_id INT NOT NULL AUTO_INCREMENT,
  measure_id INT NOT NULL,
  export_name VARCHAR(250),
  other_export_name VARCHAR(250),
  answer_text VARCHAR(1000),
  answer_type VARCHAR(250),
  answer_value INT,
  calculation_type_id INT,
  calculation_value VARCHAR(250),
  mha_value VARCHAR(250),
  display_order INT DEFAULT 0,
  vista_text VARCHAR(1000),
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (measure_answer_id));

CREATE TABLE survey_measure_response (
  survey_measure_response_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  survey_id INT NOT NULL,
  measure_id INT NOT NULL,
  measure_answer_id INT NOT NULL,
  boolean_value INT,
  number_value BIGINT,
  text_value VARCHAR(1000),
  other_value VARCHAR(1000),
  score FLOAT,
  tabular_row INT,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_modified TIMESTAMP NOT NULL DEFAULT 0,
PRIMARY KEY (survey_measure_response_id));

CREATE TABLE survey_attempt (
  survey_attempt_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_survey_id INT NOT NULL,
  start_date DATETIME,
  end_date DATETIME,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (survey_attempt_id));

CREATE TABLE survey_page (
  survey_page_id INT NOT NULL AUTO_INCREMENT,
  survey_id INT NOT NULL,
  title VARCHAR(250) NULL,
  description VARCHAR(250),
  page_number INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (survey_page_id));

CREATE TABLE survey_page_measure (
  survey_page_measure_id INT NOT NULL AUTO_INCREMENT,
  survey_page_id INT NOT NULL,
  measure_id INT NOT NULL,
  display_order INT DEFAULT 0,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (survey_page_measure_id));

CREATE TABLE validation (
  validation_id INT NOT NULL,
  code VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  data_type VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (validation_id));

CREATE TABLE measure_validation (
  measure_validation_id INT NOT NULL AUTO_INCREMENT,
  measure_id INT NOT NULL,
  validation_id INT NOT NULL,
  boolean_value INT,
  number_value INT,
  text_value VARCHAR(1000),
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (measure_validation_id));

CREATE TABLE measure_answer_validation (
  measure_answer_validation_id INT NOT NULL AUTO_INCREMENT,
  measure_answer_id INT NOT NULL,
  validation_id INT NOT NULL,
  boolean_value INT,
  number_value INT,
  text_value VARCHAR(1000),
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (measure_answer_validation_id));

CREATE TABLE role (
  role_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (role_id));

CREATE TABLE user_status (
  user_status_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (user_status_id));

CREATE TABLE assessment_status (
  assessment_status_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (assessment_status_id));

CREATE TABLE clinic (
  clinic_id INT NOT NULL AUTO_INCREMENT,
  program_id INT,
  name VARCHAR(255) NOT NULL,
  vista_ien VARCHAR(255),
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (clinic_id));

CREATE TABLE user (
  user_id INT NOT NULL AUTO_INCREMENT,
  role_id INT NOT NULL,
  user_status_id INT NOT NULL,
  login_id VARCHAR(255) NOT NULL,
  password VARCHAR(255),
  first_name VARCHAR(255),
  middle_name VARCHAR(255),
  last_name VARCHAR(255),
  email_address VARCHAR(255),
  email_address2 VARCHAR(255),
  phone_number VARCHAR(255),
  phone_number_ext VARCHAR(255),
  phone_number2 VARCHAR(255),
  phone_number2_ext VARCHAR(255),
  vista_duz VARCHAR(255),
  vista_vpid VARCHAR(255),
  vista_division VARCHAR(255),
  cprs_verified BOOLEAN NOT NULL DEFAULT 0,
  date_password_changed TIMESTAMP NULL,
  last_login_date TIMESTAMP NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (user_id));

CREATE TABLE user_clinic (
  user_clinic_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  clinic_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (user_clinic_id));

CREATE TABLE user_program (
  user_program_id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  program_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (user_program_id));

CREATE TABLE veteran (
  veteran_id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(255),
  middle_name VARCHAR(255),
  last_name VARCHAR(255) NOT NULL,
  suffix VARCHAR(255),
  ssn_last_four VARCHAR(4) NOT NULL,
  birth_date DATE,
  email VARCHAR(50),
  phone VARCHAR(50),
  office_phone VARCHAR(50),
  cell_phone VARCHAR(50),
  best_time_to_call VARCHAR(50),
  best_time_to_call_other VARCHAR(50),
  gender VARCHAR(10),
  vista_local_pid VARCHAR(16),
  guid VARCHAR(36) NOT NULL,
  veteran_ien VARCHAR(255),
  date_refreshed_from_vista TIMESTAMP NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_id));

CREATE TABLE veteran_assessment (
  veteran_assessment_id INT NOT NULL AUTO_INCREMENT,
  veteran_id INT NOT NULL,
  clinician_id INT,
  clinic_id INT,
  program_id INT,
  assessment_status_id INT NOT NULL,
  note_title_id INT,
  battery_id INT,
  access_mode INT,
  duration INT DEFAULT 0, 
  percent_complete INT DEFAULT 0,
  created_by_user_id INT NOT NULL,
  signed_by_user_id INT,
  date_completed DATETIME, 
  date_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_id));

CREATE TABLE veteran_assessment_survey (
  veteran_assessment_survey_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  survey_id INT NOT NULL,
  total_question_count INT DEFAULT 0,
  total_response_count INT DEFAULT 0,
  mha_result VARCHAR(1000),
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_survey_id));

CREATE TABLE veteran_assessment_action (
  veteran_assessment_action_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_action_id));

CREATE TABLE veteran_assessment_log (
  veteran_assessment_log_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  veteran_id INT NOT NULL,
  veteran_asessment_survey_id INT,
  survey_id INT,
  survey_page_id INT,
  veteran_assessment_action_id INT NOT NULL,
  measure_id INT, 
  message VARCHAR(2000) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_log_id));

CREATE TABLE veteran_assessment_event (
  veteran_assessment_event_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_event_id));

CREATE TABLE person_type (
  person_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (person_type_id));

CREATE TABLE veteran_assessment_audit_log (
  veteran_assessment_audit_log_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  person_id INT NOT NULL,
  person_last_name VARCHAR(255) NOT NULL,
  person_first_name VARCHAR(255),
  person_type_id INT NOT NULL,
  assessment_status_id INT NOT NULL,
  veteran_assessment_event_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_audit_log_id));

CREATE TABLE system_property (
  system_property_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  boolean_value INT,
  number_value INT,
  text_value VARCHAR(1000),
  description VARCHAR(1000),
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (system_property_id));

CREATE TABLE assessment_variable_type (
  assessment_variable_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(1024) NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (assessment_variable_type_id));

CREATE TABLE template_type (
  template_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(1024) NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (template_type_id));

CREATE TABLE assessment_variable (
  assessment_variable_id INT NOT NULL AUTO_INCREMENT,
  assessment_variable_type_id INT NOT NULL,
  display_name VARCHAR(255) NOT NULL,
  description VARCHAR(1024) NULL,
  measure_id INT,
  measure_answer_id INT,
  formula_template VARCHAR(4096) NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (assessment_variable_id));

CREATE TABLE template (
  template_id INT NOT NULL AUTO_INCREMENT,
  template_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(1024) NULL,
  template_file MEDIUMTEXT NOT NULL, 
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (template_id));

CREATE TABLE variable_template (
  variable_template_id INT NOT NULL AUTO_INCREMENT,
  assessment_variable_id INT NOT NULL,
  template_id INT NOT NULL,
  override_display_value VARCHAR(1024) NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (variable_template_id));

CREATE TABLE survey_template (
  survey_template_id INT NOT NULL AUTO_INCREMENT,
  survey_id INT NOT NULL,
  template_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (survey_template_id));

CREATE TABLE battery_template (
  battery_template_id INT NOT NULL AUTO_INCREMENT,
  battery_id INT NOT NULL,
  template_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (battery_template_id));

CREATE TABLE assessment_var_children (
  assessment_var_children_id INT NOT NULL AUTO_INCREMENT,
  variable_parent INT NOT NULL,
  variable_child INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (assessment_var_children_id));

CREATE TABLE clinical_note (
  clinical_note_id INT NOT NULL AUTO_INCREMENT,
  vista_ien VARCHAR(250) NULL,
  title VARCHAR(250) NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (clinical_note_id));

CREATE TABLE veteran_assessment_note (
  veteran_assessment_note_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  clinical_note_id INT NOT NULL,
PRIMARY KEY (veteran_assessment_note_id));

CREATE TABLE clinical_reminder (
  clinical_reminder_id INT NOT NULL AUTO_INCREMENT,
  vista_ien VARCHAR(250) NULL,
  name VARCHAR(250) NULL,
  print_name VARCHAR(250) NULL,
  clinical_reminder_class_code VARCHAR(250) NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (clinical_reminder_id));

CREATE TABLE clinical_reminder_survey (
  clinical_reminder_survey_id INT NOT NULL AUTO_INCREMENT,
  clinical_reminder_id INT NOT NULL,
  survey_id INT NOT NULL,
PRIMARY KEY (clinical_reminder_survey_id));

CREATE TABLE program_survey (
  program_survey_id INT NOT NULL AUTO_INCREMENT,
  program_id INT NOT NULL,
  survey_id INT NOT NULL,
PRIMARY KEY (program_survey_id));

CREATE TABLE clinic_survey (
  clinic_survey_id INT NOT NULL AUTO_INCREMENT,
  clinic_id INT NOT NULL,
  survey_id INT NOT NULL,
PRIMARY KEY (clinic_survey_id));

CREATE TABLE battery (
  battery_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(250) NULL,
  description VARCHAR(1000) NULL,
  is_disabled BOOLEAN NOT NULL DEFAULT 0,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (battery_id));

CREATE TABLE battery_survey (
  battery_survey_id INT NOT NULL AUTO_INCREMENT,
  battery_id INT NOT NULL,
  survey_id INT NOT NULL,
PRIMARY KEY (battery_survey_id));

CREATE TABLE note_title (
  note_title_id INT NOT NULL AUTO_INCREMENT,
  vista_ien VARCHAR(250) NULL,
  name VARCHAR(250) NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (note_title_id));

CREATE TABLE note_title_map (
  note_title_map_id INT NOT NULL AUTO_INCREMENT,
  note_title_id INT NOT NULL,
  program_id INT,
PRIMARY KEY (note_title_map_id));

CREATE TABLE rule (
  rule_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  expression VARCHAR(1000) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (rule_id));

/* Tracks the visibility of optional meaures for a given veteran assessment */
CREATE TABLE veteran_assessment_measure_visibility (
  veteran_assessment_measure_visibility_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  measure_id INT NOT NULL,
  is_visible BOOLEAN NOT NULL DEFAULT 0,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_measure_visibility_id));

CREATE TABLE rule_assessment_variable (
  rule_assessment_variable_id INT NOT NULL AUTO_INCREMENT,
  rule_id INT NOT NULL,
  assessment_variable_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (rule_assessment_variable_id));
  
CREATE TABLE event_type (
  event_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (event_type_id));

CREATE TABLE event (
  event_id INT NOT NULL AUTO_INCREMENT,
  event_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  related_object_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (event_id));

CREATE TABLE rule_event (
  rule_event_id INT NOT NULL AUTO_INCREMENT,
  rule_id INT NOT NULL,
  event_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (rule_event_id));
    
CREATE TABLE health_factor (
  health_factor_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  vista_ien VARCHAR(255) NOT NULL,
  clinical_reminder_id INT NOT NULL,
  is_historical BOOLEAN DEFAULT 0,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (health_factor_id));

CREATE TABLE dialog_prompt (
  dialog_prompt_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (dialog_prompt_id));

CREATE TABLE health_factor_dialog_prompt (
  health_factor_dialog_prompt_id INT NOT NULL AUTO_INCREMENT,
  health_factor_id INT NOT NULL,
  dialog_prompt_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (health_factor_dialog_prompt_id));

CREATE TABLE veteran_assessment_health_factor (
  veteran_assessment_health_factor_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  health_factor_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_health_factor_id));

CREATE TABLE consult (
  consult_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  vista_ien VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (consult_id));

CREATE TABLE veteran_assessment_consult (
  veteran_assessment_consult_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  consult_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_consult_id));

CREATE TABLE dashboard_alert_type (
  dashboard_alert_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
PRIMARY KEY (dashboard_alert_type_id));

CREATE TABLE dashboard_alert (
  dashboard_alert_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  message VARCHAR(1000) NOT NULL,
  dashboard_alert_type_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (dashboard_alert_id));

CREATE TABLE veteran_assessment_dashboard_alert (
  veteran_assessment_dashboard_alert_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  dashboard_alert_id INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (veteran_assessment_dashboard_alert_id));

CREATE TABLE assessment_variable_column (
  assessment_variable_column_id INT NOT NULL AUTO_INCREMENT,
  assessment_variable_id INT NOT NULL,
  column_num INT NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (assessment_variable_column_id));

CREATE TABLE export_type (
  export_type_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (export_type_id));

CREATE TABLE export_log (
  export_log_id INT NOT NULL AUTO_INCREMENT,
  exported_by_user_id INT NOT NULL,  
  clinician_user_id INT,  
  created_by_user_id INT,  
  export_type_id INT NOT NULL,  
  assessment_start_filter DATETIME,  
  assessment_end_filter DATETIME,  
  program_id INT,
  veteran_id INT,
  comment VARCHAR(1024), 
  file_path VARCHAR(255) NOT NULL,
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (export_log_id));

CREATE TABLE exportdata_filter_options (
  exportdata_filter_options_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  num_days INT NOT NULL,
  description VARCHAR(255),
  date_created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (exportdata_filter_options_id));

CREATE TABLE program_battery (
  program_battery_id INT NOT NULL AUTO_INCREMENT,
  program_id INT NOT NULL,
  battery_id INT NOT NULL,
  program_initials VARCHAR(15) NOT NULL,
PRIMARY KEY (program_battery_id));