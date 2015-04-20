ALTER TABLE veteran_assessment_survey_score
ADD COLUMN assessment_var_id INT(11) NOT NULL AFTER survey_id;

UPDATE survey_score_interval SET max='1.99' WHERE id='28';
UPDATE survey_score_interval SET max='2.99' WHERE id='29';
UPDATE survey_score_interval SET max='3.99' WHERE id='30';
UPDATE survey_score_interval SET max='4.99' WHERE id='31';
UPDATE survey_score_interval SET max='5.99' WHERE id='32';

