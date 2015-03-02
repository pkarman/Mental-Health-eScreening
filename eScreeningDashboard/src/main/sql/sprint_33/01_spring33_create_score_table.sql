CREATE TABLE veteran_assessment_survey_score (
  assessment_survey_score_id INT NOT NULL AUTO_INCREMENT,
  veteran_assessment_id INT NOT NULL,
  survey_id INT NOT NULL,
  survey_score INT NOT NULL,
  veteran_id INT NOT NULL,
  clinic_id INT NOT NULL,
  date_completed DATETIME,
  PRIMARY KEY (assessment_survey_score_id));

create table report_type(
  report_id INTEGER NOT NULL,
  report_name varchar(256) NOT NULL,
  primary key (report_id));

insert into report_type values(1, 'Individual Statistics Reports');
insert into report_type values(2, 'Average Scores for patients by clinic report');
insert into report_type values(3, 'Clinic Statistics Reports Part VI: Positive Screens Report');
insert into report_type values(4, 'Clinic Statistics Reports: eScreening Batteries Report');
insert into report_type values(5, 'Clinic Statistics Reports: Most Common Types of Alerts and Percentage Report');
insert into report_type values(6, 'Clinic Statistics Reports: List of Top 20 Most Skipped Questions');
insert into report_type values(7, 'Clinic Statistics Reports: Average Time per Module');
