/* fix to multiply the duration by 60 if the assessment was created prior to 6th of June, 2015 */
UPDATE veteran_assessment
SET duration = duration * 60
WHERE date_created < '2015-06-06 00:00:00'