

-- remove default value for module's display order
ALTER TABLE survey MODIFY COLUMN display_order_for_section INT NOT NULL;

-- set all the display order correctly grouped by section (this was ordered by old display order field)
UPDATE survey set display_order_for_section=1 where survey_id=1;
UPDATE survey set display_order_for_section=1 where survey_id=2;
UPDATE survey set display_order_for_section=2 where survey_id=3;
UPDATE survey set display_order_for_section=3 where survey_id=4;
UPDATE survey set display_order_for_section=4 where survey_id=5;
UPDATE survey set display_order_for_section=5 where survey_id=6;
UPDATE survey set display_order_for_section=6 where survey_id=7;
UPDATE survey set display_order_for_section=7 where survey_id=8;
UPDATE survey set display_order_for_section=8 where survey_id=9;
UPDATE survey set display_order_for_section=9  where survey_id=10;
UPDATE survey set display_order_for_section=1  where survey_id=11;
UPDATE survey set display_order_for_section=2  where survey_id=12;
UPDATE survey set display_order_for_section=3  where survey_id=13;
UPDATE survey set display_order_for_section=4  where survey_id=14;
UPDATE survey set display_order_for_section=5  where survey_id=15;
UPDATE survey set display_order_for_section=1  where survey_id=16;
UPDATE survey set display_order_for_section=2  where survey_id=17;
UPDATE survey set display_order_for_section=3  where survey_id=18;
UPDATE survey set display_order_for_section=4  where survey_id=19;
UPDATE survey set display_order_for_section=5  where survey_id=20;
UPDATE survey set display_order_for_section=6  where survey_id=21;

UPDATE survey set display_order_for_section=1  where survey_id=22;
UPDATE survey set display_order_for_section=2  where survey_id=23;

UPDATE survey set display_order_for_section=1  where survey_id=24;
UPDATE survey set display_order_for_section=2  where survey_id=25;
UPDATE survey set display_order_for_section=3  where survey_id=26;
UPDATE survey set display_order_for_section=4  where survey_id=27;
UPDATE survey set display_order_for_section=5  where survey_id=44;
UPDATE survey set display_order_for_section=6  where survey_id=45;

UPDATE survey set display_order_for_section=1  where survey_id=28;
UPDATE survey set display_order_for_section=2  where survey_id=29;
UPDATE survey set display_order_for_section=3  where survey_id=30;
UPDATE survey set display_order_for_section=4  where survey_id=31;
UPDATE survey set display_order_for_section=5  where survey_id=32;
UPDATE survey set display_order_for_section=6  where survey_id=33;
UPDATE survey set display_order_for_section=7  where survey_id=34;
UPDATE survey set display_order_for_section=8  where survey_id=35;
UPDATE survey set display_order_for_section=9  where survey_id=36;
UPDATE survey set display_order_for_section=10  where survey_id=37;
UPDATE survey set display_order_for_section=11 where survey_id=38;

UPDATE survey set display_order_for_section=12  where survey_id=40;
UPDATE survey set display_order_for_section=13  where survey_id=41;
UPDATE survey set display_order_for_section=14  where survey_id=42;

UPDATE survey set display_order_for_section=1  where survey_id=39;
UPDATE survey set display_order_for_section=1  where survey_id=43;