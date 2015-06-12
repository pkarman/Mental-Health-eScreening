ALTER TABLE `survey_measure_response` 
ADD COLUMN `copiedFromAssessment` INT NULL AFTER `date_modified`;
ALTER TABLE `survey_measure_response` 
ADD CONSTRAINT `fk_survey_meas_resp_va_copy`
  FOREIGN KEY (`copiedFromAssessment`)
  REFERENCES `veteran_assessment` (`veteran_assessment_id`)
  ON DELETE SET NULL
  ON UPDATE NO ACTION;