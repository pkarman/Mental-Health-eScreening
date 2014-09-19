

-- Needed variables for ticket 692 

-- update phq9 score assessment variable so it has the correct display name
UPDATE assessment_variable SET display_name='dep_score_phq9', description='PHQ-9 Score' 
WHERE assessment_variable_id=1599;

-- associate new template with the variables it uses
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2960, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2970, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2980, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (2990, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (3000, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1550, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1560, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1570, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1580, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1590, 311);
INSERT INTO variable_template(assessment_variable_id, template_id) VALUES (1599, 311);


