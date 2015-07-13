INSERT INTO 
	clinical_reminder_survey 
(
	clinical_reminder_id, 
	survey_id
) 
VALUES 
(
	(select clinical_reminder_id from clinical_reminder where vista_ien = '568022'), 
	(select survey_id from survey where name = 'PCL-C')
);
INSERT INTO 
	clinical_reminder_survey 
(
	clinical_reminder_id, 
	survey_id
) 
VALUES 
(
	(select clinical_reminder_id from clinical_reminder where vista_ien = '568022'), 
	(select survey_id from survey where name = 'PHQ-9')
);
INSERT INTO 
	clinical_reminder_survey 
(
	clinical_reminder_id, 
	survey_id
) 
VALUES 
(
	(select clinical_reminder_id from clinical_reminder where vista_ien = '568022'), 
	(select survey_id from survey where name = 'AUDIT-C')
);
INSERT INTO 
	clinical_reminder_survey 
(
	clinical_reminder_id, 
	survey_id
) 
VALUES 
(
	(select clinical_reminder_id from clinical_reminder where vista_ien = '568022'), 
	(select survey_id from survey where name = 'OOO Infect & Embedded Fragment CR')
);
