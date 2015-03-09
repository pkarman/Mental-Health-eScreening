
-- Add Assessment Variables for all measure answers
INSERT INTO assessment_variable (assessment_variable_type_id, display_name, description, measure_answer_id)
SELECT 2, IFNULL(ma.export_name,""), ma.answer_text, ma.measure_answer_id 
FROM measure_answer ma
LEFT JOIN assessment_variable av ON ma.measure_answer_id=av.measure_answer_id
WHERE av.assessment_variable_id is NULL;

