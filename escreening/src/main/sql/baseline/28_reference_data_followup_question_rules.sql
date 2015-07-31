/* *********************************************************************/
/* Create Follow-Up Question Rules and add needed assessment variables */
/* *********************************************************************/

/* Any answer that will be checked by a rule (i.e. to decide to show follow-up quesitons) will have an assessment_variable created for it.
 * For each follow-up question, an event with that question's measure_id will be created
 */

/**** HOMELESS MODULE ****/



/** 
 * We may need to move to using an assessment variable for the question and then seeing if it evaluates to the desired answer.
 * For single select the answer_id resolves to the calculated value.
 * Not sure if that will work for multiselects but we don't have to support them right now.
 */	
INSERT INTO rule (rule_id, name, expression) VALUES (1, 'Stable Housing', '[2000]==1');
INSERT INTO rule (rule_id, name, expression) VALUES (2, 'No Stable Housing', '[2000]==0');
INSERT INTO rule (rule_id, name, expression) VALUES (3, 'Yes worried about housing', '[2001]==1');
INSERT INTO rule (rule_id, name, expression) VALUES (4, 'HomelessCR_live_2mos no government subsidy', '[2002]==1');
INSERT INTO rule (rule_id, name, expression) VALUES (5, 'HomelessCR_live_2mos with government subsidy', '[2002]==2');
INSERT INTO rule (rule_id, name, expression) VALUES (6, 'HomelessCR_live_2mos friend or family member', '[2002]==3');
INSERT INTO rule (rule_id, name, expression) VALUES (7, 'HomelessCR_live_2mos Motel/Hotel', '[2002]==4');
INSERT INTO rule (rule_id, name, expression) VALUES (8, 'HomelessCR_live_2mos Short term institution', '[2002]==5');
INSERT INTO rule (rule_id, name, expression) VALUES (9, 'HomelessCR_live_2mos Homeless shelter', '[2002]==6');
INSERT INTO rule (rule_id, name, expression) VALUES (10, 'HomelessCR_live_2mos Anywhere outside', '[2002]==7');
INSERT INTO rule (rule_id, name, expression) VALUES (11, 'HomelessCR_live_2mos other', '[2002]==8');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (1, 2000);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (2, 2000);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (3, 2001);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (4, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (5, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (6, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (7, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (8, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (9, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (10, 2002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (11, 2002);

/* Create ShowQuestion events (type_id=4) */
/* will show question with measure_id 62 */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (1, 4, 'Show - concerned about losing housing in 2 months', 62);
/* events to show question with measure_id 63, 51, 52 */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (2, 4, 'Show - where have you lived', 63);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (3, 4, 'Show - want housing referral', 51);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (4, 4, 'Show - best way to reach you', 52);
/* events for each of the comment field versions */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (5, 4, 'HomelessCR_homenogov_spec', 64);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (6, 4, 'HomelessCR_homewgov_spec', 65);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (7, 4, 'HomelessCR_friendfam_spec', 66);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (8, 4, 'HomelessCR_hotel_spec', 67);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (9, 4, 'HomelessCR_shortins_spec', 68);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (10, 4, 'HomelessCR_shel_spec', 69);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (11, 4, 'HomelessCR_out_spec', 70);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (12, 4, 'HomelessCR_oth_spec', 50);

/* Link rule to ShowQuestion events */
INSERT INTO rule_event (rule_id, event_id) VALUES(1, 1);
INSERT INTO rule_event (rule_id, event_id) VALUES(2, 2);
INSERT INTO rule_event (rule_id, event_id) VALUES(2, 3);
INSERT INTO rule_event (rule_id, event_id) VALUES(2, 4);
/* same question shown as rule 2 */
INSERT INTO rule_event (rule_id, event_id) VALUES(3, 2);
INSERT INTO rule_event (rule_id, event_id) VALUES(3, 3);
INSERT INTO rule_event (rule_id, event_id) VALUES(3, 4);
/* add comment questions to be shown based on dropdown in question 63*/ 
INSERT INTO rule_event (rule_id, event_id) VALUES(4, 5);
INSERT INTO rule_event (rule_id, event_id) VALUES(5, 6);
INSERT INTO rule_event (rule_id, event_id) VALUES(6, 7);
INSERT INTO rule_event (rule_id, event_id) VALUES(7, 8);
INSERT INTO rule_event (rule_id, event_id) VALUES(8, 9);
INSERT INTO rule_event (rule_id, event_id) VALUES(9, 10);
INSERT INTO rule_event (rule_id, event_id) VALUES(10, 11);
INSERT INTO rule_event (rule_id, event_id) VALUES(11, 12);



/**** TOBACCO CESSATION MODULE ****/

/* rules for answers to question 341 (do you use tobacco currently) */
INSERT INTO rule (rule_id, name, expression) VALUES (20, 'No. Used to use tobacco in past but quit', '[600]==1');
INSERT INTO rule (rule_id, name, expression) VALUES (21, 'Yes. Use tobacco', '[600]==2');

/* rules for answers to question 342 (when did you quit) */
INSERT INTO rule (rule_id, name, expression) VALUES (22, 'quit 1 year ago', '[610]==3');
INSERT INTO rule (rule_id, name, expression) VALUES (23, 'quit within 12 months', '[610]==4');


/* 
 * IMPORTANT NOTE: If you use an measure_answer typed assessment_variable and the answer it points to doesn't have a calculation value type, each response field will be tried and the first non-null one will be returned. 
 * In this case a multi select answer is resolved to either "true" or "false".
 */  
/* rules for answers to question 347 (which types of tobacco do you use) - MultiSelect */
/* cigarettes */
INSERT INTO rule (rule_id, name, expression) VALUES (24, 'uses cigarettes', '[621]');
  /* cigars/pipes */
INSERT INTO rule (rule_id, name, expression) VALUES (25, 'uses cigar/pipes', '[622]');
  /* smokeless tobacco */
INSERT INTO rule (rule_id, name, expression) VALUES (26, 'uses smokeless tobacco', '[623]');


/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (20, 600);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (21, 600);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (22, 610);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (23, 610);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (24, 621);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (25, 622);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (26, 623);

/* will show question with measure_id 342 (when did you quit) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (20, 4, 'Show - when did you quit', 342);
/* will show question with measure_id 347 (which types of tobacco do you use) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (21, 4, 'Show - which types of tobacco', 347);

/* will show all questions related to getting the date when they quit tobacco use (these go together)*/
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (22, 4, 'Show - when did you quit message', 343);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (23, 4, 'Show - when did you quite month', 344);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (24, 4, 'Show - when did you quit year', 346);

/* will show question with measure_id 348 (how many cigarettes do you smoke, and for how many years) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (25, 4, 'Show - how long cigarettes', 348);

/* will show question with measure_id 349 (how many cigars/pipes do you smoke, and for how many years) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (26, 4, 'Show - how long cigar/pipes', 349);

/* will show question with measure_id 350 (how much smokeless tobacco do you use, and for how many years) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (27, 4, 'Show - how long smokeless', 350);

/* If user answers "No. I used tobacco in the past, but have quit." then show question 342 (when did you quit) */
INSERT INTO rule_event (rule_id, event_id) VALUES(20, 20);

/* If user answers "Yes. I currently use tobacco on a regular basis." then show question 347 (which types of tobacco do you use) */
INSERT INTO rule_event (rule_id, event_id) VALUES(21, 21);

/* If user answers "quit a year ago" or "within a year" then shown "when did you quit" question group */
INSERT INTO rule_event (rule_id, event_id) VALUES(22, 22);
INSERT INTO rule_event (rule_id, event_id) VALUES(22, 23);
INSERT INTO rule_event (rule_id, event_id) VALUES(22, 24);
INSERT INTO rule_event (rule_id, event_id) VALUES(23, 22);
INSERT INTO rule_event (rule_id, event_id) VALUES(23, 23);
INSERT INTO rule_event (rule_id, event_id) VALUES(23, 24);

/* If user answers "cigaretts" then show question 348 (how many cigaretts ...) */
INSERT INTO rule_event (rule_id, event_id) VALUES(24, 25);
/* If user answers "cigar/pipes" then show question 349 (how many cigars/pipes ...) */
INSERT INTO rule_event (rule_id, event_id) VALUES(25, 26);
/* If user answers "smokeless" then show question 350 (how many cigaretts ...) */
INSERT INTO rule_event (rule_id, event_id) VALUES(26, 27);


/**** BTBIS MODULE ****/

/******************************************************************************************
     Follow up Rule to take answers from Question 412 and show follow up question 413
*******************************************************************************************/

/* assessment variables for question 412 */
-- the asset variable answers are in 27_reference_data_template_variables.sql


/* rule using answers for first question (412) to show follow up question */
INSERT INTO rule (rule_id, name, expression) VALUES (30, 'TBI during OEF/OIF deployments', '[2010] || [2011] || [2012] || [2013] || [2014] || [2015]');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (30, 2010);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (30, 2011);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (30, 2012);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (30, 2013);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (30, 2014);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (30, 2015);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (30, 4, 'Show - Did you have any of these symptoms IMMEDIATELY afterwards?', 413);
INSERT INTO rule_event (rule_id, event_id) VALUES(30, 30);

/******************************************************************************************
     Follow up Rule to take answers from Question 413 and show follow up question 414
*******************************************************************************************/

/* assessment variables of type measure for child questions of question 413 */
-- the asset variable answers are in 27_reference_data_template_variables.sql


/* rule for second question */
INSERT INTO rule (rule_id, name, expression) VALUES (31, 'Based on question: Did you have any of these symptoms IMMEDIATELY afterwards?', '[2017] || [2018] || [2019] || [2020] || [2021]');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (31, 2017);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (31, 2018);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (31, 2019);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (31, 2020);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (31, 2021);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (31, 4, 'Show - Did any of the following problems begin or get worse afterwards?', 414);
INSERT INTO rule_event (rule_id, event_id) VALUES(31, 31);


/******************************************************************************************
     Follow up Rule to take answers from Question 414 and show follow up question 415
*******************************************************************************************/

/* assessment variables for question 414 */
-- the asset variable answers are in 27_reference_data_template_variables.sql

/* rule for third question */
INSERT INTO rule (rule_id, name, expression) VALUES (32, 'Based on question: Did any of the following problems begin or get worse afterwards? show fourth follow up question', '[2023] || [2024] || [2025] || [2027] || [2028] || [2029]');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (32, 2023);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (32, 2024);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (32, 2025);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (32, 2027);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (32, 2028);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (32, 2029);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (32, 2030);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (32, 4, 'Show - In the past 1 week have you had any of the problems from the question above?', 415);
INSERT INTO rule_event (rule_id, event_id) VALUES(32, 32);

/******************************************************************************************
     Follow up Rule to take answers from Question 415 and show follow up question 443
*******************************************************************************************/

/* assessment variables for question 415 */
-- the asset variable answers are in 27_reference_data_template_variables.sql


/* rule for fourth question */
INSERT INTO rule (rule_id, name, expression) VALUES (33, 'Based on question: In the past 1 week have you had any of the problems from the question above? show a follow up question', '[2031] || [2032] || [2033] || [2034] || [2035] || [2036]');

/* Associate rules to the assessment variables they contain in their expressions */
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (33, 2031);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (33, 2032);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (33, 2033);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (33, 2034);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (33, 2035);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (33, 2036);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (33, 2037);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (33, 4, 'Show - Do you want a consult placed for more thorough testing?', 443);
INSERT INTO rule_event (rule_id, event_id) VALUES(33, 33);

/******************************************************************************************
     Follow up Rule to take answers from Question 443 and show follow up questions if Yes
*******************************************************************************************/

-- the assessment variable 2047 to 27_reference_data_template_variables.sql

INSERT INTO rule (rule_id, name, expression) VALUES (34, 'Based on question: Do you want a consult placed for more thorough testing?', '[2047] == 1');

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (34, 2047);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (34, 4, 'Show - Where were you deployed when the head injury occurred?', 444);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (35, 4, 'Show - What year did it occur?', 445);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (36, 4, 'Show - How did the head injury occur?', 446);

INSERT INTO rule_event (rule_id, event_id) VALUES(34, 34);
INSERT INTO rule_event (rule_id, event_id) VALUES(34, 35);
INSERT INTO rule_event (rule_id, event_id) VALUES(34, 36);


/**** MST MODULE ****/

INSERT INTO rule (rule_id, name, expression) VALUES (60, 'Unwanted Sexual attention', '[2003]==1');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (60, 2003);

/* will show question with measure_id 502 (have you experienced any of these types fo unwanted ...) */
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (60, 4, 'Show - Have you experienced any of these types of unwanted...', 502);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (61, 4, 'Show - Would you like sexual trauma referral', 505);

INSERT INTO rule_event (rule_id, event_id) VALUES(60, 60);
INSERT INTO rule_event (rule_id, event_id) VALUES(60, 61);

/*** OEF OIF Clinical Reminder ***/
INSERT INTO rule (rule_id, name, expression) VALUES (40, 'Show - Where was your most recent OEF service?', '[660]==1');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (40, 660);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (40, 4, 'Show - Where was your most recent OEF service?', 113);
INSERT INTO rule_event (rule_id, event_id) VALUES(40, 40);

INSERT INTO rule (rule_id, name, expression) VALUES (41, 'Show - Where was your most recent OIF service?', '[660]==2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (41, 660);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (41, 4, 'Show - Where was your most recent OIF service?', 115);
INSERT INTO rule_event (rule_id, event_id) VALUES(41, 41);

/** WHODAS2.0 **/
INSERT INTO rule (rule_id, name, expression) VALUES (50, 'Show - whodas_work follow up questions?', '[4200]==1');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (50, 4200);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (50, 4, 'Show - whodas_work follow up?', 302);
INSERT INTO rule_event (rule_id, event_id) VALUES(50, 50);







