

-- Needed variables for ticket 642

-- update phq9 score assessment variable so it has the correct display name
UPDATE assessment_variable SET display_name='dep_score_phq9', description='PHQ-9 Score' 
WHERE assessment_variable_id=1599;



