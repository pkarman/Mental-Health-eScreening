/*
-- Query: select * from survey where survey_id between 39 and 44
LIMIT 0, 1000

-- Date: 2015-04-06 13:33
*/
UPDATE `survey` SET name='Contact',description='Best address' where survey_id= 39;
UPDATE `survey` SET name='Presenting Mental Health Issue',description='Presenting Mental Health Issues Checklist (MHAC)' where survey_id= 40;
UPDATE `survey` SET name='Stressors and Distressing Symptoms',description='Stressors and Distressing Symptoms (MHAC)' where survey_id= 41;
UPDATE `survey` SET name='Prior Hospitalizations and Meds',description='Prior Hospitalizations and Medications (MHAC)' where survey_id= 42;
UPDATE `survey` SET name='Goals',description='Goals for MHAC Visit' where survey_id= 43;
UPDATE `survey` SET name='AUDIT',description='Alcohol Use Disorder Test' where survey_id= 44;