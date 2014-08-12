/* Create foreign key constraints */
ALTER TABLE clinic ADD CONSTRAINT fk_clinic_program 
FOREIGN KEY (program_id) REFERENCES program (program_id);

ALTER TABLE measure ADD CONSTRAINT fk_measure_measure_type 
FOREIGN KEY (measure_type_id) REFERENCES measure_type (measure_type_id);

ALTER TABLE measure ADD CONSTRAINT fk_measure_parent 
FOREIGN KEY (parent_measure_id) REFERENCES measure (measure_id);

ALTER TABLE measure_answer ADD CONSTRAINT fk_measure_answer_measure 
FOREIGN KEY (measure_id) REFERENCES measure (measure_id);

ALTER TABLE measure_answer ADD CONSTRAINT fk_calculation_type 
FOREIGN KEY (calculation_type_id) REFERENCES calculation_type(calculation_type_id);

ALTER TABLE measure_validation ADD CONSTRAINT fk_measure_validation_measure
FOREIGN KEY (measure_id) REFERENCES measure (measure_id);

ALTER TABLE measure_validation ADD CONSTRAINT fk_measure_validation_validation 
FOREIGN KEY (validation_id) REFERENCES validation (validation_id);

ALTER TABLE measure_answer_validation ADD CONSTRAINT fk_measure_answer_validation_measure
FOREIGN KEY (measure_answer_id) REFERENCES measure_answer (measure_answer_id);

ALTER TABLE measure_answer_validation ADD CONSTRAINT fk_measure_answer_validation_validation 
FOREIGN KEY (validation_id) REFERENCES validation (validation_id);

ALTER TABLE survey ADD CONSTRAINT fk_survey_survey_section
FOREIGN KEY (survey_section_id) REFERENCES survey_section (survey_section_id);

ALTER TABLE survey_attempt ADD CONSTRAINT fk_survey_attempt_vas
FOREIGN KEY (veteran_assessment_survey_id) REFERENCES veteran_assessment_survey (veteran_assessment_survey_id);

ALTER TABLE survey_measure_response ADD CONSTRAINT fk_survey_meas_resp_va
FOREIGN KEY (veteran_assessment_id) REFERENCES veteran_assessment (veteran_assessment_id);

ALTER TABLE survey_measure_response ADD CONSTRAINT fk_survey_meas_resp_vs 
FOREIGN KEY (survey_id) REFERENCES survey (survey_id);

ALTER TABLE survey_measure_response ADD CONSTRAINT fk_survey_meas_resp_vm
FOREIGN KEY (measure_id) REFERENCES measure (measure_id);

ALTER TABLE survey_measure_response ADD CONSTRAINT fk_survey_meas_resp_vma 
FOREIGN KEY (measure_answer_id) REFERENCES measure_answer (measure_answer_id);

ALTER TABLE survey_page ADD CONSTRAINT fk_survey_page_survey
FOREIGN KEY (survey_id) REFERENCES survey (survey_id);

ALTER TABLE survey_page_measure ADD CONSTRAINT fk_survey_page_measure_sp
FOREIGN KEY (survey_page_id) REFERENCES survey_page (survey_page_id);

ALTER TABLE survey_page_measure ADD CONSTRAINT fk_survey_page_measure_m 
FOREIGN KEY (measure_id) REFERENCES measure (measure_id);

ALTER TABLE user ADD CONSTRAINT ux_login_id UNIQUE (login_id);

ALTER TABLE user ADD CONSTRAINT fk_user_role 
FOREIGN KEY (role_id) REFERENCES role (role_id);

ALTER TABLE user ADD CONSTRAINT fk_user_status 
FOREIGN KEY (user_status_id) REFERENCES user_status (user_status_id);

ALTER TABLE user_clinic ADD CONSTRAINT fk_user_clinic_user 
FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE user_clinic ADD CONSTRAINT fk_user_clinic_clinic 
FOREIGN KEY (clinic_id) REFERENCES clinic (clinic_id);

ALTER TABLE user_program ADD CONSTRAINT fk_user_program_user 
FOREIGN KEY (user_id) REFERENCES user (user_id);

ALTER TABLE user_program ADD CONSTRAINT fk_user_program_program 
FOREIGN KEY (program_id) REFERENCES program (program_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_veteran 
FOREIGN KEY (veteran_id) REFERENCES veteran (veteran_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_clinician 
FOREIGN KEY (clinician_id) REFERENCES user (user_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_created_by 
FOREIGN KEY (created_by_user_id) REFERENCES user (user_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_signed_by 
FOREIGN KEY (signed_by_user_id) REFERENCES user (user_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_clinic 
FOREIGN KEY (clinic_id) REFERENCES clinic (clinic_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_prgm 
FOREIGN KEY (program_id) REFERENCES program (program_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_status 
FOREIGN KEY (assessment_status_id) REFERENCES assessment_status (assessment_status_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_note_title
FOREIGN KEY (note_title_id) REFERENCES note_title (note_title_id);

ALTER TABLE veteran_assessment ADD CONSTRAINT fk_veteran_assess_battery
FOREIGN KEY (battery_id) REFERENCES battery (battery_id);

ALTER TABLE veteran_assessment_survey ADD CONSTRAINT fk_veteran_assess_survey_pa 
FOREIGN KEY (veteran_assessment_id) REFERENCES veteran_assessment (veteran_assessment_id);

ALTER TABLE veteran_assessment_survey ADD CONSTRAINT fk_veteran_assess_survey_surv 
FOREIGN KEY (survey_id) REFERENCES survey (survey_id);

ALTER TABLE veteran_assessment_log ADD CONSTRAINT fk_veteran_assessment_action
FOREIGN KEY (veteran_assessment_action_id) REFERENCES veteran_assessment_action (veteran_assessment_action_id);

ALTER TABLE veteran_assessment_audit_log ADD CONSTRAINT fk_person_type_id
FOREIGN KEY (person_type_id) REFERENCES person_type (person_type_id);

ALTER TABLE veteran_assessment_audit_log ADD CONSTRAINT fk_assessment_status_id
FOREIGN KEY (assessment_status_id) REFERENCES assessment_status (assessment_status_id);

ALTER TABLE veteran_assessment_audit_log ADD CONSTRAINT fk_veteran_assessment_event_id
FOREIGN KEY (veteran_assessment_event_id) REFERENCES veteran_assessment_event (veteran_assessment_event_id);

ALTER TABLE assessment_variable ADD CONSTRAINT fk_assessment_variable_type 
FOREIGN KEY (assessment_variable_type_id) REFERENCES assessment_variable_type (assessment_variable_type_id);

ALTER TABLE assessment_variable ADD CONSTRAINT fk_measure 
FOREIGN KEY (measure_id) REFERENCES measure (measure_id);

ALTER TABLE assessment_variable ADD CONSTRAINT fk_measure_answer 
FOREIGN KEY (measure_answer_id) REFERENCES measure_answer (measure_answer_id);

ALTER TABLE assessment_variable ADD CONSTRAINT ux_assessment_variable_measure_id UNIQUE (measure_id);
ALTER TABLE assessment_variable ADD CONSTRAINT ux_assessment_variable_measure_answer_id UNIQUE (measure_answer_id);

ALTER TABLE template ADD CONSTRAINT fk_template_type 
FOREIGN KEY (template_type_id) REFERENCES template_type (template_type_id);

ALTER TABLE variable_template ADD CONSTRAINT fk_assessment_variable 
FOREIGN KEY (assessment_variable_id) REFERENCES assessment_variable (assessment_variable_id);

ALTER TABLE variable_template ADD CONSTRAINT fk_template 
FOREIGN KEY (template_id) REFERENCES template (template_id);

ALTER TABLE survey_template ADD CONSTRAINT fk_survey_template_survey
FOREIGN KEY (survey_id) REFERENCES survey (survey_id);

ALTER TABLE survey_template ADD CONSTRAINT fk_survey_template_template 
FOREIGN KEY (template_id) REFERENCES template (template_id);

ALTER TABLE battery_template ADD CONSTRAINT fk_battery_template_battery
FOREIGN KEY (battery_id) REFERENCES battery (battery_id);

ALTER TABLE battery_template ADD CONSTRAINT fk_battery_template_template 
FOREIGN KEY (template_id) REFERENCES template (template_id);

ALTER TABLE assessment_var_children ADD CONSTRAINT fk_assessment_variable_parent
FOREIGN KEY (variable_parent) REFERENCES assessment_variable (assessment_variable_id);

ALTER TABLE assessment_var_children ADD CONSTRAINT fk_assessment_variable_child
FOREIGN KEY (variable_child) REFERENCES assessment_variable (assessment_variable_id);

ALTER TABLE veteran_assessment_note ADD CONSTRAINT fk_va_note_va 
FOREIGN KEY (veteran_assessment_id) REFERENCES veteran_assessment (veteran_assessment_id);

ALTER TABLE veteran_assessment_note ADD CONSTRAINT fk_va_note_cn 
FOREIGN KEY (clinical_note_id) REFERENCES clinical_note (clinical_note_id);

ALTER TABLE clinical_reminder_survey ADD CONSTRAINT fk_clncl_rmndr_srvy_clncl
FOREIGN KEY (clinical_reminder_id) REFERENCES clinical_reminder (clinical_reminder_id);

ALTER TABLE clinical_reminder_survey ADD CONSTRAINT fk_clncl_rmndr_srvy_srvy 
FOREIGN KEY (survey_id) REFERENCES survey (survey_id);

ALTER TABLE program_survey ADD CONSTRAINT fk_prgm_srvy_prgm
FOREIGN KEY (program_id) REFERENCES program (program_id);

ALTER TABLE program_survey ADD CONSTRAINT fk_prgm_srvy_srvy 
FOREIGN KEY (survey_id) REFERENCES survey (survey_id);

ALTER TABLE clinic_survey ADD CONSTRAINT fk_clnc_srvy_clnc
FOREIGN KEY (clinic_id) REFERENCES clinic (clinic_id);

ALTER TABLE clinic_survey ADD CONSTRAINT fk_clnc_srvy_srvy 
FOREIGN KEY (survey_id) REFERENCES survey (survey_id);

ALTER TABLE battery_survey ADD CONSTRAINT fk_bttry_srvy_bttry
FOREIGN KEY (battery_id) REFERENCES battery (battery_id);

ALTER TABLE battery_survey ADD CONSTRAINT fk_bttry_srvy_srvy 
FOREIGN KEY (survey_id) REFERENCES survey (survey_id);

ALTER TABLE note_title_map ADD CONSTRAINT fk_note_title_map_nt 
FOREIGN KEY (note_title_id) REFERENCES note_title (note_title_id);

ALTER TABLE note_title_map ADD CONSTRAINT fk_note_title_map_prgm 
FOREIGN KEY (program_id) REFERENCES program (program_id);

ALTER TABLE veteran_assessment_measure_visibility ADD CONSTRAINT ux_assessment_measure_visibility UNIQUE (veteran_assessment_id, measure_id);

ALTER TABLE veteran_assessment_measure_visibility ADD CONSTRAINT fk_veteran_assessment_measure_visibility_assessment
FOREIGN KEY (veteran_assessment_id) REFERENCES veteran_assessment (veteran_assessment_id);

ALTER TABLE veteran_assessment_measure_visibility ADD CONSTRAINT fk_veteran_assessment_measure_visibility_measure
FOREIGN KEY (measure_id) REFERENCES measure (measure_id);

ALTER TABLE event ADD CONSTRAINT fk_action_action_type
FOREIGN KEY (event_type_id) REFERENCES event_type (event_type_id);

ALTER TABLE rule_event ADD CONSTRAINT fk_rule_event_rule
FOREIGN KEY (rule_id) REFERENCES rule (rule_id);

ALTER TABLE rule_event ADD CONSTRAINT fk_rule_event_event
FOREIGN KEY (event_id) REFERENCES event (event_id);

ALTER TABLE rule_event ADD CONSTRAINT ux_rule_event UNIQUE (rule_id, event_id);

ALTER TABLE rule_assessment_variable ADD CONSTRAINT fk_rule_assessment_variable_rule
FOREIGN KEY (rule_id) REFERENCES rule (rule_id);

ALTER TABLE rule_assessment_variable ADD CONSTRAINT fk_rule_assessment_variable_variable
FOREIGN KEY (assessment_variable_id) REFERENCES assessment_variable (assessment_variable_id);

ALTER TABLE rule_assessment_variable ADD CONSTRAINT ux_rule_variable UNIQUE (rule_id, assessment_variable_id);

ALTER TABLE health_factor ADD CONSTRAINT fk_health_factor_reminder
FOREIGN KEY (clinical_reminder_id) REFERENCES clinical_reminder (clinical_reminder_id);

ALTER TABLE health_factor_dialog_prompt ADD CONSTRAINT fk_hlth_fctr_dlg_prmpt_hf 
FOREIGN KEY (health_factor_id) REFERENCES health_factor (health_factor_id);

ALTER TABLE health_factor_dialog_prompt ADD CONSTRAINT fk_hlth_fctr_dlg_prmpt_dp 
FOREIGN KEY (dialog_prompt_id) REFERENCES dialog_prompt (dialog_prompt_id);

ALTER TABLE veteran_assessment_health_factor ADD CONSTRAINT fk_veteran_assessment_health_factor_assessment
FOREIGN KEY (veteran_assessment_id) REFERENCES veteran_assessment (veteran_assessment_id);

ALTER TABLE veteran_assessment_health_factor ADD CONSTRAINT fk_veteran_assessment_health_factor_health_factor
FOREIGN KEY (health_factor_id) REFERENCES health_factor (health_factor_id);

ALTER TABLE veteran_assessment_health_factor ADD CONSTRAINT ux_assessment_health_factor UNIQUE (veteran_assessment_id, health_factor_id);

ALTER TABLE veteran_assessment_consult ADD CONSTRAINT fk_veteran_assessment_consult_consult
FOREIGN KEY (consult_id) REFERENCES consult (consult_id);

ALTER TABLE veteran_assessment_consult ADD CONSTRAINT fk_veteran_assessment_consult_assessment
FOREIGN KEY (veteran_assessment_id) REFERENCES veteran_assessment (veteran_assessment_id);

ALTER TABLE veteran_assessment_consult ADD CONSTRAINT ux_assessment_consult UNIQUE (veteran_assessment_id, consult_id);

ALTER TABLE dashboard_alert ADD CONSTRAINT fk_dashboard_alert_type
FOREIGN KEY (dashboard_alert_type_id) REFERENCES dashboard_alert_type (dashboard_alert_type_id);

ALTER TABLE veteran_assessment_dashboard_alert ADD CONSTRAINT fk_veteran_assessment_dashboard_alert_alert
FOREIGN KEY (dashboard_alert_id) REFERENCES dashboard_alert (dashboard_alert_id);

ALTER TABLE veteran_assessment_dashboard_alert ADD CONSTRAINT fk_veteran_assessment_dashboard_alert_assessment
FOREIGN KEY (veteran_assessment_id) REFERENCES veteran_assessment (veteran_assessment_id);

ALTER TABLE veteran_assessment_dashboard_alert ADD CONSTRAINT ux_assessment_dashboard_alert UNIQUE (veteran_assessment_id, dashboard_alert_id);

ALTER TABLE assessment_variable_column ADD CONSTRAINT fk_assessment_variable_column_av 
FOREIGN KEY (assessment_variable_id) REFERENCES assessment_variable (assessment_variable_id);

ALTER TABLE export_log ADD CONSTRAINT fk_user_el1 
FOREIGN KEY (exported_by_user_id) REFERENCES user (user_id);

ALTER TABLE export_log ADD CONSTRAINT fk_user_el2 
FOREIGN KEY (clinician_user_id) REFERENCES user (user_id);

ALTER TABLE export_log ADD CONSTRAINT fk_user_el3
FOREIGN KEY (created_by_user_id) REFERENCES user (user_id);

ALTER TABLE export_log ADD CONSTRAINT fk_export_type_el
FOREIGN KEY (export_type_id) REFERENCES export_type (export_type_id);

ALTER TABLE export_log ADD CONSTRAINT fk_program_el
FOREIGN KEY (program_id) REFERENCES program (program_id);

ALTER TABLE export_log ADD CONSTRAINT fk_veteran_el
FOREIGN KEY (veteran_id) REFERENCES veteran (veteran_id);

ALTER TABLE program_battery ADD CONSTRAINT fk_program_battery_program
FOREIGN KEY (program_id) REFERENCES program (program_id);

ALTER TABLE program_battery ADD CONSTRAINT fk_program_battery_battery
FOREIGN KEY (battery_id) REFERENCES battery (battery_id);
