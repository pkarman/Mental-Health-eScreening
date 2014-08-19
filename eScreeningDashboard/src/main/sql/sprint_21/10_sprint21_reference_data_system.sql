

/* template_type tbl */
INSERT template_type (template_type_id, name, description) VALUES (1, 'CPRS Note Header', 'Template used to generate the header of a CPRS Note. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=1, name='CPRS Note Header', description='Template used to generate the header of a CPRS Note. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (2, 'CPRS Note Footer', 'Template used to generate the footer of a CPRS Note. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=2, name='CPRS Note Footer', description='Template used to generate the footer of a CPRS Note. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (3, 'CPRS Note Entry', 'Template used to generate the body elements of a CPRS Note. Associated with a Module.')
ON DUPLICATE KEY UPDATE template_type_id=3, name='CPRS Note Entry',  description='Template used to generate the body elements of a CPRS Note. Associated with a Module.';

INSERT template_type (template_type_id, name, description) VALUES (4, 'Assessment Table of Scores', 'Template used to generate a table of scores for an assessment. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=4, name='Assessment Table of Scores', description='Template used to generate a table of scores for an assessment. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (5, 'Assessment Conclusion Text', 'Template used to generate the conclusion text shown to a veteran at the end of an assessment. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=5, name='Assessment Conclusion Text', description='Template used to generate the conclusion text shown to a veteran at the end of an assessment. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (6, 'Veteran Summary Printout Header', 'Template used to generate the header element of a Veteran Summary Printout. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=3, name='Veteran Summary Printout Header',  description='Template used to generate the header element of a Veteran Summary Printout. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (7, 'Veteran Summary Printout Footer', 'Template used to generate the footer element of a Veteran Summary Printout. Associated with a Battery.')
ON DUPLICATE KEY UPDATE template_type_id=3, name='Veteran Summary Printout Footer',  description='Template used to generate the footer element of a Veteran Summary Printout. Associated with a Battery.';

INSERT template_type (template_type_id, name, description) VALUES (8, 'Veteran Summary Printout Entry', 'Template used to generate the body elements of a Veteran Summary Printout. Associated with a Module.')
ON DUPLICATE KEY UPDATE template_type_id=3, name='Veteran Summary Printout Entry',  description='Template used to generate the body elements of a Veteran Summary Printout. Associated with a Module.';

INSERT template_type (template_type_id, name, description) VALUES (9, 'VistA Questions and Answers', 'Template used to generate the VistA question and answer text which is included in the CPRS Note. Associated with a Module.')
ON DUPLICATE KEY UPDATE template_type_id=3, name='VistA Questions and Answers',  description='Template used to generate the VistA question and answer text which is included in the CPRS Note. Associated with a Module.';
