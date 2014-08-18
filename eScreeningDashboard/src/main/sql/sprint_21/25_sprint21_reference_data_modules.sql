--INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (380, 6, "", false);
--update measure set parent_measure_id = 380 where measure_id in (383, 384, 385, 386, 387, 388, 389, 390, 391);
--INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (56, 380, 1);

update measure set parent_measure_id = null where measure_id=382;
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (56, 382, 0);
Update survey_page_measure set display_order=1 where survey_page_id=56 and measure_id=381;