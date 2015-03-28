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