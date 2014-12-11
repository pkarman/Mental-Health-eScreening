-- For ticket 733
ALTER TABLE variable_template ADD CONSTRAINT ux_variable_template UNIQUE (assessment_variable_id, template_id);