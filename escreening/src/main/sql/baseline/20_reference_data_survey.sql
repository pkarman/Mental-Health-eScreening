/* Modules for OEF/OIF, Mental Health, Primary Care, OOO, Aspire */

/* Insert Survey table data */
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (1, 'Identification', 'Autoassigned', 1);
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (2, 'Demographics and Social Information', 'OOO Demographics and Social Information Survey Group', 2);
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (3, 'Service History', 'OOO Service History Survey Group', 3);
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (4, 'Health Symptoms', 'OOO Health Symptoms Survey Group', 4);
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (5, 'Health Functioning', 'OOO Health Functioning Survey Group', 5);
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (6, 'Health Habits', 'OOO Health Habits Survey Group', 6);
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (7, 'Psychological Health', 'OOO Psychological Health Survey Group', 7);
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (8, 'Contact', 'MHAC Contact Survey Group', 8);
INSERT survey_section (survey_section_id, name, description, display_order) VALUES (9, 'Goals', 'MHAC Goal Survey Group', 9);

/* Surveys aka Modules */
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (1, 1, 'Identification', 'General identification survey for use in all batteries.', 1, 1);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (2, 2, 'Presenting Problems', 'OOO Battery Presenting Problems', 1, 2);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (3, 2, 'Basic Demographics', 'OOO Battery Basic Demographics', 1, 3);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (4, 2, 'Education, Employment & Income', 'OOO Battery Education, Employment & Income', 1, 4);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (5, 2, 'Social Environment', 'OOO Battery Social Environment', 1, 5);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (6, 2, 'Promis Emotional Support', 'OOO Battery Promis Emotional Support', 1, 6);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (7, 2, 'Homelessness Clinical Reminder', 'OOO Battery Homelessness Clinical Reminder', 1, 7);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (8, 2, 'Pragmatic Concerns', 'OOO Battery Pragmatic Concerns', 1, 8);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (9, 2, 'Advance Directive', 'OOO Battery Advance Directive', 1, 9);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (10, 2, 'Spiritual Health', 'OOO Battery Spiritual Health', 1, 10);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (11, 3, 'Service History', 'OOO Battery Service History', 1, 11);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (12, 3, 'OEF OIF Clinical Reminder', 'OOO Battery OEF OIF Clinical Reminder', 1, 12);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (13, 3, 'Military Deployments & History', 'OOO Battery Military Deployments & History', 1, 13);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (14, 3, 'Exposures', 'OOO Battery Exposures', 1, 14);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (15, 3, 'Service Injuries', 'OOO Battery Service Injuries', 1, 15);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (16, 4, 'PHQ-15', 'OOO Battery PHQ-15', 1, 16);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (17, 4, 'Other Health Symptoms', 'OOO Battery Other Health Symptoms', 1, 17);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (18, 4, 'OOO Infect & Embedded Fragment CR', 'OOO Battery OOO Infect & Embedded Fragment CR', 1, 18);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (19, 4, 'Promis Pain Intensity & Interference', 'OOO Battery Promis Pain Intensity & Interference', 1, 19);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (20, 4, 'Basic Pain', 'OOO Battery Basic Pain', 1, 20);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (21, 4, 'Medications', 'OOO Battery Medications', 1, 21);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (22, 5, 'Prior MH Treatment', 'OOO Battery Prior MH Treatment', 1, 22);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (23, 5, 'WHODAS 2.0', 'OOO Battery WHODAS 2.0', 1, 23);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (24, 6, 'Caffeine Use', 'OOO Battery Caffeine Use', 1, 24);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (25, 6, 'Tobacco Cessation Screen', 'OOO Battery Tobacco Cessation Screen', 1, 25);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (26, 6, 'AUDIT-C', 'OOO Battery AUDIT-C', 1, 26);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (27, 6, 'DAST-10', 'OOO Battery DAST-10', 1, 27);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (28, 7, 'AV Hallucinations', 'OOO Battery AV Hallucinations', 1, 28);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (29, 7, 'BTBIS', 'OOO Battery BTBIS', 1, 29);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (30, 7, 'PHQ-9', 'OOO Battery PHQ-9', 1, 30);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (31, 7, 'MDQ', 'OOO Battery MDQ', 1, 31);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (32, 7, 'MST', 'OOO Battery MST', 1, 32);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (33, 7, 'GAD 7 Anxiety', 'OOO Battery GAD 7 Anxiety', 1, 33);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (34, 7, 'PCL-C', 'OOO Battery PCL-C', 1, 34);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (35, 7, 'PC-PTSD', 'OOO Battery PC-PTSD', 1, 35);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (36, 7, 'ISI', 'OOO Battery ISI', 1, 36);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (37, 7, 'ROAS', 'OOO Battery ROAS', 1, 37);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (38, 7, 'CD-RISC-10', 'OOO Battery CD-RISC-10', 1, 38);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (39, 8, 'Contact', 'MHAC Battery Contact', 1, 39);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (40, 7, 'Presenting Mental Health Issue', 'MHAC Battery Presenting Mental Health Issue', 1, 40);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (41, 7, 'Stressors and Distressing Symptoms', 'MHAC Battery Stressors and Distressing Symptoms', 1, 41);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (42, 7, 'Prior Hospitalizations and Meds', 'MHAC Battery Prior Hospitalizations and Meds', 1, 42);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (43, 9, 'Goals', 'MHAC Battery Goals', 1, 43);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (44, 6, 'Audit', 'MHAC Battery Audit', 1, 44);
INSERT INTO survey (survey_id, survey_section_id, name, description, version, display_order) VALUES (45, 6, 'BMI over 24', 'PC Battery BMI over 24', 1, 45);
