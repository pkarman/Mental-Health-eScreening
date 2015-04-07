-- used to store json structure of rules send to server from UI
ALTER TABLE rule ADD COLUMN condition_json mediumtext;

-- update rules which contain measure AVs so they also list each measure's answer AVs as well
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id)
    select r.rule_id, answer_av.assessment_variable_id
    from rule r
    join rule_assessment_variable rav on r.rule_id=rav.rule_id
    join assessment_variable measure_av on rav.assessment_variable_id=measure_av.assessment_variable_id
	join measure m on measure_av.measure_id=m.measure_id
	join measure_answer ma on m.measure_id=ma.measure_id
	join assessment_variable answer_av on ma.measure_answer_id=answer_av.measure_answer_id
    left join rule_assessment_variable answer_rav on 
		r.rule_id=answer_rav.rule_id AND answer_av.assessment_variable_id=answer_rav.assessment_variable_id
    WHERE 
		measure_av.assessment_variable_type_id=1 AND answer_rav.rule_assessment_variable_id is NULL;
		
		
-- add event for every question (name is set with first value that is non-null in this order: the variable name, or measure text, or question_[measure_id])
INSERT INTO `event` (event_type_id, `name`, related_object_id)
SELECT 4, SUBSTRING(
	IFNULL(NULLIF(m.variable_name, ''), 
    IFNULL(NULLIF(m.measure_text, ''), 
		   CONCAT('question_', CAST(m.measure_id AS CHAR)))),
	1, 30), m.measure_id
FROM measure m 
LEFT JOIN `event` e ON m.measure_id=e.related_object_id
WHERE e.event_id IS NULL;


