update measure set measure_text = '' where measure_id=381;
INSERT INTO measure (measure_id, measure_type_id, measure_text, is_required) VALUES (605, 8,
    'The following questions concern information about your possible involvement with drugs not including alcoholic beverages during the past 52 weeks. Carefully read each statement and decide if your answer is \'Yes\' or \'No.\' Then, check the appropriate response beside the question. In the statements, \'drug abuse\' refers to: (1) the use of prescribed or over-the-counter drugs in excess of the directions and (2) and non-medical use of drugs.', 0);
INSERT INTO survey_page_measure (survey_page_id, measure_id, display_order) VALUES (56, 605, 0);

Update survey_page_measure set display_order=1 where survey_page_id=56 and measure_id=382;
Update survey_page_measure set display_order=2 where survey_page_id=56 and measure_id=381;

/** The following two lines fixes the display order gap problem **********/
update survey_page_measure set display_order = 2 where survey_page_measure_id = 126;
update survey_page_measure set display_order = (display_order -1) where survey_page_id=51;



