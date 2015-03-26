create table veteran_assessment_question_presence
(
veteran_assessment_id int,
measure_id int,
skipped int
);

CREATE TRIGGER upd_question_presence BEFORE UPDATE ON veteran_assessment
FOR EACH ROW
BEGIN
	IF NEW.date_completed is not null AND OLD.date_completed is null then
		insert into veteran_assessment_question_presence
		(veteran_assessment_id, measure_id, skipped)
		SELECT NEW.veteran_assessment_id, all_measures.measure_id, ifnull(answered_measures.measure_id, -1) response
                FROM(
                    SELECT spm.survey_page_id, spm.measure_id as measure_id
                    FROM measure m
                    LEFT OUTER JOIN veteran_assessment_measure_visibility vamv ON
                    m.measure_id=vamv.measure_id AND vamv.veteran_assessment_id= NEW.veteran_assessment_id
                    INNER JOIN survey_page_measure spm ON ifnull(m.parent_measure_id, m.measure_id)=spm.measure_id
                    INNER JOIN survey_page sp ON spm.survey_page_id=sp.survey_page_id
                    INNER JOIN survey s ON sp.survey_id=s.survey_id
                    INNER JOIN veteran_assessment_survey vas ON sp.survey_id=vas.survey_id
                    WHERE vas.veteran_assessment_id = NEW.veteran_assessment_id
                    AND m.measure_type_id IN (1,2,3,4,6,7)
                    AND ifnull(vamv.is_visible, 1)
                    GROUP BY spm.survey_page_id, spm.measure_id
                ) all_measures
                LEFT OUTER JOIN (
                   /* Business rules are: if table parent question contains a non-false answer we include it; if an entire row
                      of child questions have at least one true or non-null we return the parent;  if there is any number
                      of completed rows but at least one that is incomplete we don't include the parent */
                    SELECT DISTINCT spm.measure_id
                    FROM survey_measure_response smr
                    INNER JOIN measure m ON smr.measure_id=m.measure_id
                    INNER JOIN survey_page_measure spm on ifnull(m.parent_measure_id, m.measure_id)=spm.measure_id
                    INNER JOIN survey s ON smr.survey_id=s.survey_id
                    WHERE smr.veteran_assessment_id = NEW.veteran_assessment_id
                    AND m.measure_type_id IN ( 1,2,3,4,6,7)
                    AND (
                        (spm.measure_id=smr.measure_id AND (smr.boolean_value = 1 OR smr.number_value IS NOT NULL
                        OR smr.text_value IS NOT NULL) )
                        OR ( /* check to see if this is a parent with answered children */

                            /* used to make sure the second sub-query contains at least one answer
                               (i.e. when no child answers have been given the second sub-query will be true which we don't want) */
                            0 NOT IN (
                                    SELECT COUNT(*) FROM survey_measure_response smr2
                                    INNER JOIN measure m ON smr2.measure_id=m.measure_id
                                    WHERE smr2.veteran_assessment_id = NEW.veteran_assessment_id
                                    AND m.parent_measure_id = spm.measure_id )
                            AND
                                 /* collect responses grouped by measure and tabular row. Get sum of the number of answers.
                                    If a row has a 0 then that row had no answer for that measure */
                                0 NOT IN (
                                    SELECT ifnull( sum(smr2.boolean_value = 1 OR smr2.number_value IS NOT NULL OR smr2.text_value IS NOT NULL), 0) answer_count
                                    FROM survey_measure_response smr2
                                    INNER JOIN survey s ON smr2.survey_id=s.survey_id
                                    INNER JOIN measure m ON smr2.measure_id=m.measure_id
                                    WHERE smr2.veteran_assessment_id = NEW.veteran_assessment_id
                                    AND m.parent_measure_id = spm.measure_id
                                    AND m.measure_type_id IN (  1,2,3,4,6,7 )
                                    GROUP BY smr2.measure_id, smr2.tabular_row )
                        )
                    )
                ) answered_measures
                ON answered_measures.measure_id=all_measures.measure_id;
	END IF;
END;;

