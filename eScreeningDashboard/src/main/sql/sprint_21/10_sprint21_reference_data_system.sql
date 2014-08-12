

/* template_type tbl */
INSERT template_type (template_type_id, name, description) VALUES (1, 'Note Header', 'Template used to generate the header of a Note. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=1, name='Note Header', description='Template used to generate the header of a Note. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (2, 'Note Footer', 'Template used to generate the footer of a Note. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=2, name='Note Footer', description='Template used to generate the footer of a Note. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (3, 'Note Entry', 'Template used to generate the body elements of a Note. Associated with a Module.')
ON DUPLICATE KEY UPDATE template_type_id=3, name='Note Entry',  description='Template used to generate the body elements of a Note. Associated with a Module.';

INSERT template_type (template_type_id, name, description) VALUES (4, 'Note Special', 'Template used to generate a special section of a Note. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=4, name='Note Header', description='Template used to generate a special section of a Note. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (5, 'Note Conclusion', 'Template used to generate the conclusion of a Note. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=5, name='Note Header', description='Template used to generate the conclusion of a Note. Associated with a Battery.';