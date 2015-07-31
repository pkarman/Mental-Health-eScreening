/* Triggers */
CREATE TRIGGER tr_survey_measure_response_modified 
BEFORE UPDATE ON survey_measure_response 
FOR EACH ROW SET NEW.date_modified = NOW();
