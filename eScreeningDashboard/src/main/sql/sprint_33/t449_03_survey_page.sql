/*
-- Query: SELECT * FROM escreening.survey_page where date_created > '2015-02-21 07:00:00'
LIMIT 0, 1000

-- Date: 2015-03-01 08:10
*/
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (76,44,'Health Habits','Audit page',1,'2015-02-23 15:35:01');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (77,39,'Contact','Contact page',1,'2015-02-24 19:34:02');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (78,40,'Health Symptoms','Presenting Mental Health Issue page',1,'2015-02-25 04:17:08');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (79,41,'Health Symptoms','Stressors and Distressing Symptoms page',1,'2015-02-25 16:02:53');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (80,41,'Health Symptoms','Stressors and Distressing Symptoms page',2,'2015-02-25 16:02:53');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (81,42,'Psychological Health','Prior Hospitalizations and Meds page',2,'2015-02-26 02:24:23');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (82,42,'Psychological Health','Prior Hospitalizations and Meds page',1,'2015-02-26 02:24:23');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (83,42,'Psychological Health','Prior Hospitalizations and Meds page',3,'2015-02-26 02:24:23');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (84,42,'Psychological Health','Prior Hospitalizations and Meds page',4,'2015-02-26 02:24:23');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (85,42,'Psychological Health','Prior Hospitalizations and Meds page',5,'2015-02-26 02:24:23');
INSERT INTO `survey_page` (`survey_page_id`,`survey_id`,`title`,`description`,`page_number`,`date_created`) VALUES (86,43,'Goals','Goals page',1,'2015-02-26 10:53:06');

UPDATE survey SET display_order_for_section='101' WHERE survey_id='40';
UPDATE survey SET display_order_for_section='102' WHERE survey_id='41';
UPDATE survey SET display_order_for_section='103' WHERE survey_id='42'; 