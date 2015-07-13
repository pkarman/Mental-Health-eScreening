/*
-- Query: select * from survey where survey_id between 39 and 44
LIMIT 0, 1000

-- Date: 2015-04-06 13:33
*/
/* Update Survey sections */
UPDATE survey_section set display_order=4 where survey_section_id=2;
UPDATE survey_section set display_order=5 where survey_section_id=3;
UPDATE survey_section set display_order=6 where survey_section_id=4;
UPDATE survey_section set display_order=7 where survey_section_id=5;
UPDATE survey_section set display_order=8 where survey_section_id=6;
UPDATE survey_section set display_order=9 where survey_section_id=7;
UPDATE survey_section set display_order=2 where survey_section_id=8;
UPDATE survey_section set display_order=10 where survey_section_id=9;
INSERT INTO `survey_section` (`survey_section_id`,`name`,`description`,`display_order`,`date_created`) 
	VALUES (11,'Presenting Concerns','Presenting concerns',3,'2015-03-07 09:45:03');

UPDATE `survey` SET name='Contact',description='Best address',survey_section_id=8,display_order_for_section=1  where survey_id= 39;
UPDATE `survey` SET name='Presenting Mental Health Issue',description='Presenting Mental Health Issues Checklist (MHAC)', 
survey_section_id=11,display_order_for_section=1 where survey_id= 40;
UPDATE `survey` SET name='Stressors and Distressing Symptoms',description='Stressors and Distressing Symptoms (MHAC)', 
survey_section_id=11,display_order_for_section=2 where survey_id= 41;
UPDATE `survey` SET name='Prior Hospitalizations and Meds',description='Prior Hospitalizations and Medications (MHAC)',
survey_section_id=7,display_order_for_section=1 where survey_id= 42;
UPDATE `survey` SET name='Goals',description='Goals for MHAC Visit', survey_section_id=9,display_order_for_section=1 where survey_id= 43;
UPDATE `survey` SET name='AUDIT',description='Alcohol Use Disorder Test', survey_section_id=6,display_order_for_section=5 where survey_id= 44;