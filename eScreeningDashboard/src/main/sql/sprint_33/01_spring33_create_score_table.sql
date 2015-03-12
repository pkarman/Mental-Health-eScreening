CREATE TABLE veteran_assessment_survey_score (
  assessment_survey_score_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  survey_id INT NOT NULL,
  survey_score INT NOT NULL,
  veteran_id INT NOT NULL,
  clinic_id INT NOT NULL,
  date_completed DATETIME,
  PRIMARY KEY (assessment_survey_score_id));

CREATE TABLE survey_score_interval (
  `id` INT NOT NULL AUTO_INCREMENT,
  `survey_id` INT NOT NULL,
  `min` VARCHAR(10) NOT NULL,
  `max` VARCHAR(10) NOT NULL,
  `meaning` VARCHAR(200) NOT NULL,
  exception int(1) NOT NULL default 0,
  PRIMARY KEY (`id`));

insert into survey_score_interval (survey_id, min, max, meaning) values (30, '0', '0', 'no depression');
insert into survey_score_interval (survey_id, min, max, meaning) values (30, '1', '4', 'minimal');
insert into survey_score_interval (survey_id, min, max, meaning) values (30, '5', '9', 'mild');
insert into survey_score_interval (survey_id, min, max, meaning) values (30, '10', '14', 'moderate');
insert into survey_score_interval (survey_id, min, max, meaning) values (30, '15', '19', 'moderately severe');
insert into survey_score_interval (survey_id, min, max, meaning) values (30, '20', '27', 'severe depression');
insert into survey_score_interval (survey_id, min, max, meaning, exception) values (30, '999', '999', 'declined', 1);

insert into survey_score_interval (survey_id, min, max, meaning) values (34, '0', '43', 'negative');
insert into survey_score_interval (survey_id, min, max, meaning) values (34, '44', '85', 'positive');

insert into survey_score_interval (survey_id, min, max, meaning) values (38, '0', '9', 'Minimal resilience');
insert into survey_score_interval (survey_id, min, max, meaning) values (38, '10', '19', 'low resilience');
insert into survey_score_interval (survey_id, min, max, meaning) values (38, '20', '29', 'medium resilience');
insert into survey_score_interval (survey_id, min, max, meaning) values (38, '30', '40', 'high resilience');

insert into survey_score_interval (survey_id, min, max, meaning) values (33, '0', '4', 'minimal');
insert into survey_score_interval (survey_id, min, max, meaning) values (33, '5', '9', 'mild anxiety');
insert into survey_score_interval (survey_id, min, max, meaning) values (33, '10', '14', 'moderate anxiety');
insert into survey_score_interval (survey_id, min, max, meaning) values (33, '15', '21', 'severe anxiety');



