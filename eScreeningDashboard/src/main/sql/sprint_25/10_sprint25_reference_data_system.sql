/* add a column to mark assessment is archived or not */
ALTER TABLE veteran_assessment 
ADD COLUMN date_archived DATETIME NULL DEFAULT NULL AFTER date_created,
ADD INDEX idx_fk_archive_date (assessment_status_id ASC, date_archived ASC);

