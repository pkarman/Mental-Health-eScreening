/* ********************************************** */
/* Assesment Variables  UPDATES */
/* ********************************************** */





/* ********************************************** */
/* Template Variables  UPDATES */
/* ********************************************** */

/* Veteran Summary Template Variables Update */


/* VETERAN SUMMARY - Advance Directive  */




/* VETERAN SUMMARY - Homelessness  */




/* VETERAN SUMMARY - Alcohol Use  */



/* VETERAN SUMMARY - Insomnia  */




/* VETERAN SUMMARY - Environmental Exposure  */




/* VETERAN SUMMARY - Military Sexual Trauma (MST)  */




/* VETERAN SUMMARY - Tobacco Use  */




/* VETERAN SUMMARY - Traumatic Brain Injury (TBI)  */

	/* Advance Directive */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10500, 820, 300);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10501, 821, 300, 'not having');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10502, 822, 300, 'having');

	/* Homelessness */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10510, 2000, 301);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10511, 761, 301, 'has not');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10512, 762, 301, 'has');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10513, 763, 301, 'declined');

	/* Alcohol Use */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10515, 1229, 302);

	/* Insomnia ISI*/
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10520, 2189, 303);

	/* Environmental Exposure */
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_id) VALUES (10540, 1, 'Do you have any persistent major concerns regarding the effects of something you believe you may have been exposed to or encountered while deployed', 'Do you have any persistent major concerns regarding the effects of something you believe you may have been exposed to or encountered while deployed question', 125);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (10541, 2, 'No', 'No answer', 1250);
INSERT INTO assessment_variable(assessment_variable_id, assessment_variable_type_id, display_name, description, measure_answer_id) VALUES (10542, 2, 'yes', 'yes  answer', 1251);

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10540, 10540, 304);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10541, 10541, 304, 'no');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10542, 10542, 304, 'yes');



	/* Military Sexual Trauma (MST) */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10560, 2003, 305);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10561, 2004, 305, 'answered no to both questions in');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10562, 2005, 305, 'answered yes to one or both questions in');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10563, 2006, 305, 'declined to answer');

	/* Tobacco Use */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10580, 600, 306);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10581, 601, 306, 'denied using tobacco ');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10582, 602, 306, 'quit using tobacco');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10583, 603, 306, 'endorsed using');

	/* Traumatic Brain Injury (TBI) */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10600, 2047, 307);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10601, 3441, 307, 'did not agree');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10602, 3442, 307, 'agreed');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10620, 3400, 307);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10621, 2016, 307, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10622, 2010, 307, 'Blast or explosion (IED, RPG, Land Mine, Grenade, etc.)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10623, 2011, 307, 'Vehicular accident/crash (any vehicle including aircraft)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10624, 2012, 307, 'Fragment wound or bullet wound above the shoulders');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10625, 2013, 307, 'Fall');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10626, 2014, 307, 'Blow to head (hit by falling/flying object, head hit by another person, head hit against something, etc.)');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10627, 2015, 307, 'Other injury to head');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10630, 3410, 307);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10631, 2022, 307, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10632, 2017, 307, 'Loss of consciousness\"knocked out\"');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10633, 2018, 307, 'Being dazed, confused or \"seeing stars\"');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10634, 2019, 307, 'Not remembering the event');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10635, 2020, 307, 'Concussion');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10636, 2021, 307, 'Head injury');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10640, 3420, 307);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10641, 2030, 307, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10642, 2023, 307, 'Memory problems or lapses');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10643, 2024, 307, 'Balance problems or dizziness');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10644, 2025, 307, 'Sensitivity to bright light');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10645, 2027, 307, 'Irritability');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10646, 2028, 307, 'Headaches');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10647, 2029, 307, 'Sleep problems');

INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (10650, 3430, 307);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10651, 2037, 307, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10652, 2031, 307, 'Memory problems or lapses');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10653, 2032, 307, 'Balance problems or dizziness');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10654, 2033, 307, 'Sensitivity to bright light');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10655, 2034, 307, 'Irritability');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10656, 2035, 307, 'Headaches');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (10657, 2036, 307, 'Sleep problems');






	/* VAS PAIN - BASIC PAIN*/
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (11500, 2300, 309);
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (11501, 2334, 309, 'none');
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id, override_display_value) VALUES (11502, 2331, 309, 'pain area answer');


	/* PTSD */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (11510, 1989, 310);

	/* PHQ 9 DEPRESSION */
INSERT INTO variable_template(variable_template_id, assessment_variable_id, template_id) VALUES (11515, 1599, 308);