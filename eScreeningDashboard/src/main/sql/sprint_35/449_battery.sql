/*
-- Query: SELECT * FROM escreening.battery
LIMIT 0, 1000

-- Date: 2015-04-06 13:18
*/

UPDATE battery set name='Mental Health Access Clinic POC', description='Intial paperwork for all veterans presenting to MHAC' where battery_id=4;

DELETE FROM battery_survey where battery_id=4;

/*
-- Query: select * from battery_survey where battery_id=4
LIMIT 0, 1000

-- Date: 2015-04-07 19:29
*/
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (131,4,34);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (132,4,1);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (133,4,32);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (134,4,33);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (135,4,4);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (136,4,5);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (137,4,39);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (138,4,7);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (139,4,42);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (140,4,25);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (141,4,8);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (142,4,43);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (143,4,40);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (144,4,41);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (145,4,44);
INSERT INTO `battery_survey` (`battery_survey_id`,`battery_id`,`survey_id`) VALUES (146,4,30);
 
