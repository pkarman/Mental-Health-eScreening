/* add a column to mark assessment is archived or not */
ALTER TABLE veteran_assessment 
ADD COLUMN date_archived DATETIME NULL DEFAULT NULL AFTER date_created,
ADD INDEX idx_fk_archive_date (assessment_status_id ASC, date_archived ASC);

/* t574 -- allow veteran to change first name and middle initials */
UPDATE measure SET measure_type_id = 1 WHERE measure_id in (1,2);

/* t574 -- 	if veteran changes the data imported from Vista--we still have to preserve the 
	first name and middle initials for export report
*/				
ALTER TABLE veteran 
ADD COLUMN vista_first_name VARCHAR(255) NULL DEFAULT NULL AFTER veteran_ien,
ADD COLUMN vista_middle_name VARCHAR(255) NULL DEFAULT NULL AFTER vista_first_name;

