/* Create indexes */
CREATE INDEX idx_fk_clinic_program ON clinic (program_id ASC);

CREATE INDEX idx_fk_measure_measure_type ON measure (measure_type_id ASC);

CREATE INDEX idx_fk_measure_answer_meas ON measure_answer (measure_id ASC);
CREATE INDEX idx_fk_measure_parent ON measure (parent_measure_id ASC);

CREATE INDEX idx_fk_survey_survey_section ON survey (survey_section_id ASC);

CREATE INDEX idx_fk_survey_attempt_vas ON survey_attempt (veteran_assessment_survey_id ASC);

CREATE INDEX idx_fk_survey_meas_resp_va ON survey_measure_response (veteran_assessment_id ASC);
CREATE INDEX idx_fk_survey_meas_resp_vs ON survey_measure_response (survey_id ASC);
CREATE INDEX idx_fk_survey_meas_resp_vm ON survey_measure_response (measure_id ASC);
CREATE INDEX idx_fk_survey_meas_resp_vma ON survey_measure_response (measure_answer_id ASC);

CREATE INDEX idx_fk_survey_page_survey ON survey_page (survey_page_id ASC);

CREATE INDEX idx_fk_survey_page_measure_sp ON survey_page_measure (survey_page_id ASC);
CREATE INDEX idx_fk_survey_page_measure_m ON survey_page_measure (measure_id ASC);

CREATE INDEX idx_fk_user_role ON user (role_id ASC);
CREATE INDEX idx_fk_user_status ON user (user_status_id ASC);

CREATE INDEX idx_fk_user_clinic_user ON user_clinic (user_id ASC);
CREATE INDEX idx_fk_user_clinic_clinic ON user_clinic (clinic_id ASC);

CREATE INDEX idx_fk_user_program_user ON user_program (user_id ASC);
CREATE INDEX idx_fk_user_program_program ON user_program (program_id ASC);

CREATE INDEX idx_fk_veteran_assess_veteran ON veteran_assessment (veteran_id ASC);
CREATE INDEX idx_fk_veteran_assess_clinician ON veteran_assessment (clinician_id ASC);
CREATE INDEX idx_fk_veteran_assess_clinic ON veteran_assessment (clinic_id ASC);
CREATE INDEX idx_fk_veteran_assess_prgm ON veteran_assessment (program_id ASC);
CREATE INDEX idx_fk_veteran_assess_creator ON veteran_assessment (created_by_user_id ASC);
CREATE INDEX idx_fk_veteran_assess_status ON veteran_assessment (assessment_status_id ASC);
CREATE INDEX idx_fk_veteran_assess_note_t ON veteran_assessment (note_title_id ASC);
CREATE INDEX idx_fk_veteran_assess_battery ON veteran_assessment (battery_id ASC);

CREATE INDEX idx_fk_veteran_assess_survey_pa ON veteran_assessment_survey (veteran_assessment_id ASC);
CREATE INDEX idx_fk_veteran_assess_survey_surv ON veteran_assessment_survey (survey_id ASC);

CREATE INDEX idx_fk_va_note_va ON veteran_assessment_note (veteran_assessment_id ASC);
CREATE INDEX idx_fk_va_note_cn ON veteran_assessment_note (clinical_note_id ASC);

CREATE INDEX idx_fk_clncl_rmndr_srvy_clncl ON clinical_reminder_survey (clinical_reminder_id ASC);
CREATE INDEX idx_fk_clncl_rmndr_srvy_srvy ON clinical_reminder_survey (survey_id ASC);

CREATE INDEX idx_fk_prgm_srvy_prgm ON program_survey (program_id ASC);
CREATE INDEX idx_fk_prgm_srvy_srvy ON program_survey (survey_id ASC);

CREATE INDEX idx_fk_clnc_srvy_clnc ON clinic_survey (clinic_id ASC);
CREATE INDEX idx_fk_clnc_srvy_srvy ON clinic_survey (survey_id ASC);

CREATE INDEX idx_fk_bttry_srvy_bttry ON battery_survey (battery_id ASC);
CREATE INDEX idx_fk_fk_bttry_srvy_srvy ON battery_survey (survey_id ASC);

CREATE INDEX idx_fk_note_title_map_nt ON note_title_map (note_title_id ASC);
CREATE INDEX idx_fk_note_title_map_prgm ON note_title_map (program_id ASC);

CREATE INDEX idx_fk_action_action_type ON event (event_type_id ASC);

CREATE INDEX idx_fk_rule_event_rule ON rule_event (rule_id ASC);
CREATE INDEX idx_fk_rule_event_event ON rule_event (event_id ASC);

CREATE INDEX idx_fk_rule_assessment_variable_rule ON rule_assessment_variable (rule_id ASC);
CREATE INDEX idx_fk_rule_assessment_variable_variable ON rule_assessment_variable (assessment_variable_id ASC);

CREATE INDEX idx_fk_health_factor_reminder ON health_factor (clinical_reminder_id ASC);

CREATE INDEX idx_fk_hlth_fctr_dlg_prmpt_hf ON health_factor_dialog_prompt (health_factor_id ASC);
CREATE INDEX idx_fk_hlth_fctr_dlg_prmpt_dp ON health_factor_dialog_prompt (dialog_prompt_id ASC);

CREATE INDEX idx_fk_veteran_assessment_health_factor_assessment ON veteran_assessment_health_factor (veteran_assessment_id ASC);
CREATE INDEX idx_fk_veteran_assessment_health_factor_health_factor ON veteran_assessment_health_factor (health_factor_id ASC);

CREATE INDEX idx_fk_veteran_assessment_health_factor_assessment ON veteran_assessment_consult (consult_id ASC);
CREATE INDEX idx_fk_veteran_assessment_health_factor_health_factor ON veteran_assessment_consult (veteran_assessment_id ASC);

CREATE INDEX idx_fk_dashboard_alert_type ON dashboard_alert (dashboard_alert_type_id ASC);

CREATE INDEX idx_fk_veteran_assessment_dashboard_alert_alert ON veteran_assessment_dashboard_alert (dashboard_alert_id ASC);
CREATE INDEX idx_fk_veteran_assessment_dashboard_alert_assessment ON veteran_assessment_dashboard_alert (veteran_assessment_id ASC);

CREATE INDEX idx_fk_veteran_assessment_measure_visibility_assessment ON veteran_assessment_measure_visibility (veteran_assessment_id ASC);
CREATE INDEX idx_fk_veteran_assessment_measure_visibility_measure ON veteran_assessment_measure_visibility (measure_id ASC);

CREATE INDEX idx_fk_survey_template_survey ON survey_template (survey_id ASC);
CREATE INDEX idx_fk_survey_template_template ON survey_template (template_id ASC);

CREATE INDEX idx_fk_battery_template_battery ON battery_template (battery_id ASC);
CREATE INDEX idx_fk_battery_template_template ON battery_template (template_id ASC);
