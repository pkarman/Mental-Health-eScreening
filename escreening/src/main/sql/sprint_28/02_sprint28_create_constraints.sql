-- For ticket 733
ALTER TABLE variable_template ADD CONSTRAINT ux_variable_template UNIQUE (assessment_variable_id, template_id);

-- t651
ALTER TABLE survey ADD COLUMN display_order_for_section INT NOT NULL DEFAULT 1 AFTER date_created;
ALTER TABLE survey DROP COLUMN display_order;

