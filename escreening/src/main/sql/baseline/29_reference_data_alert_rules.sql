/* *********************************************************/
/* Create Alert Rules and add needed assessment variables  */
/* *********************************************************/

INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (1, 'Positive Depression Screen', 'Positive Depression Screen', 1);
INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (2, 'SI Present', 'SI Present', 1);
INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (3, 'Aggressive behavior requiring F/U', 'Aggressive behavior requiring F/U', 1);
INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (4, 'Aggressive Behavior(s)', 'Aggressive Behavior', 1);
INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (5, 'Positive PTSD Screen', 'Positive PTSD Screen', 1);
INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (6, 'Prior dx and/or tx for MH', 'Prior dx and/or tx for MH', 1);
INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (7, 'A/V Hallucination', 'A/V Hallucination', 1);
INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (8, 'ADTP Consult for Substance Abuse', 'ADTP Consult for Substance Abuse', 1);
INSERT INTO dashboard_alert (dashboard_alert_id, name, message, dashboard_alert_type_id) VALUES (9, 'TBI & requests F/U', 'TBI & requests F/U', 1);

-- Add Measure Variables
-- moved to 27_reference_data_template_variables.sql

-- Add Formula Variable
-- moved to 27_reference_data_template_variables.sql

-- Add Dependency Information
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1000);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1001);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1002);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1003);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1004);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1005);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1006);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1007);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1008);
INSERT INTO assessment_var_children (variable_parent, variable_child) VALUES (1010, 1009);

-- Add the Rule
INSERT INTO rule (rule_id, name, expression) VALUES (100, 'ADTP Consult for Substance Abuse', '([1000] + [1001] + [1003] + [1004] + [1005] + [1006] + [1007] + [1008] + [1009] + (1 - [1002])) >= 3'); 

-- Add association for rules and variables.
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1000);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1001);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1002);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1003);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1004);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1005);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1006);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1007);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1008);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1009);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (100, 1010);

-- Add Event for adding alert for a Veteran Assessment
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (100, 3, 'Add Dashboard Alert for ADTP Consult for Substance Abuse', 8);

-- Add Rule Event *** This alert is no longer applicable July 7.
--INSERT INTO rule_event (rule_id, event_id) VALUES(100, 100);

/** SI PRESENT -- PHQ-9 **/

INSERT INTO rule (rule_id, name, expression) VALUES (101, 'SI Present', '[1580] >= 1');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (101, 1580);

-- Add Event for adding alert for a Veteran Assessment
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (101, 3, 'Add SI Present', 2);

-- Add Rule Event
INSERT INTO rule_event (rule_id, event_id) VALUES(101, 101);

/** Add Positive Depression Screen -- PHQ-9**/
INSERT INTO rule (rule_id, name, expression) VALUES (125, 'Positive Depression Screen', '[2960]+[2970]+[2980]+[2990]+[3000]+[1550]+[1560]+[1570]+[1580]>=10');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 2960);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 2970);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 2980);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 2990);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 3000);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 1550);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 1560);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 1570);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (125, 1580);

-- Add Event for adding alert for a Veteran Assessment
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (125, 3, 'Add Positive Depression Screen', 1);

-- Add Rule Event
INSERT INTO rule_event (rule_id, event_id) VALUES(125, 125);



-- Add rule for TBI & requests F/U

INSERT INTO rule (rule_id, name, expression) VALUES (102, 'TBI & requests F/U', 
'([2010]||[2011]||[2012]||[2013]||[2014]||[2015]) && ([2017]||[2018]||[2019]||[2020]||[2021]) && ([2023]||[2024]||[2025]||[2027]||[2028]||[2029]) && ([2031]||[2032]||[2033]||[2034]||[2035]||[2036]) && ([2047]==1)');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2010);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2011);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2012);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2013);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2014);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2015);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2018);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2017);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2019);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2020);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2021);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2023);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2024);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2025);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2047);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2027);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2028);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2029);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2031);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2032);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2033);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2034);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2035);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (102, 2036);

-- Add Event for adding alert for a Veteran Assessment
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (102, 3, 'Add TBI & requests F/U', 9);

-- Add Rule Event
INSERT INTO rule_event (rule_id, event_id) VALUES(102, 102);

/** Add DX/TX Present **/
INSERT INTO rule (rule_id, name, expression) VALUES (103, 'Prior dx and/or tx for MH', 
	'((([1522]||[1523]||[1524])?1:0) + (([1532]||[1533]||[1534]||[1535]||[1536])?1:0) + ([202]?1:0) + ([214]?1:0)) >=3');

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 1522);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 1523);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 1524);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 1532);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 1533);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 1534);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 1535);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 1536);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 202);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (103, 214);

INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (103, 3, 'Add Prior dx and/or tx for MH', 6);

-- Add Rule Event
INSERT INTO rule_event (rule_id, event_id) VALUES(103, 103);

/** Add Positive PTSD Screen**/
INSERT INTO rule (rule_id, name, expression) VALUES (104, 'Positive PTSD Screen', 
	'([1750]+[1760]+[1770]+[1780]+[1790]+[1800]+[1810]+[1820]+[1830]+[1840]+[1850]+[1860]+[1870]+[1880]+[1890]+[1900]+[1910]) >=50');

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1750);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1760);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1770);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1780);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1790);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1800);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1810);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1820);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1830);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1840);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1850);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1860);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1870);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1880);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1890);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1900);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (104, 1910);


INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (104, 3, 'Add Positive PTSD Screen', 5);

-- Add Rule Event
INSERT INTO rule_event (rule_id, event_id) VALUES(104, 104);

INSERT INTO rule (rule_id, name, expression) VALUES (105, 'PTSD Follow-up PC-PTSD', 
	'([1940]+[1950]+[1960]+[1970])>=3');

INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (105, 1940);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (105, 1950);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (105, 1960);
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES (105, 1970);

-- Add Rule Event
INSERT INTO rule_event (rule_id, event_id) VALUES(105, 104);

/** ROAS **/
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (106, 3, 'Add Aggressive Behavior', 4);
INSERT INTO event (event_id, event_type_id, name, related_object_id) VALUES (107, 3, 'Add Imminent Risk', 3);

INSERT INTO rule (rule_id, name, expression) VALUES (106, "ang3_curse:aggressive behavior", '[3220] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(106, 3220);
INSERT INTO rule_event (rule_id, event_id) VALUES(106, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (107, "ang4_threat:imminent risk", '[3230] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(107, 3230);
INSERT INTO rule_event (rule_id, event_id) VALUES(107, 107);

INSERT INTO rule (rule_id, name, expression) VALUES (108, "ang6_throw:aggressive behavior", '[3250] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(108, 3250);
INSERT INTO rule_event (rule_id, event_id) VALUES(108, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (109, "ang7_break:aggressive behavior", '[3260] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(109, 3260);
INSERT INTO rule_event (rule_id, event_id) VALUES(109, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (110, "ang8_setfire:aggressive behavior", '[3270] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(110, 3270);
INSERT INTO rule_event (rule_id, event_id) VALUES(110, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (111, "ang9_gesture:aggressive behavior", '[3280] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(111, 3280);
INSERT INTO rule_event (rule_id, event_id) VALUES(111, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (112, "ang10_strike:imminent risk", '[3290] >=3');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(112, 3290);
INSERT INTO rule_event (rule_id, event_id) VALUES(112, 107);

INSERT INTO rule (rule_id, name, expression) VALUES (113, "ang10_strike:aggressive behavior", '[3290] ==2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(113, 3290);
INSERT INTO rule_event (rule_id, event_id) VALUES(113, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (114, "ang11_injury:aggressive behavior", '[3300] ==1');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(114, 3300);
INSERT INTO rule_event (rule_id, event_id) VALUES(114, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (115, "ang11_injury:imminent risk", '[3300] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(115, 3300);
INSERT INTO rule_event (rule_id, event_id) VALUES(115, 107);

INSERT INTO rule (rule_id, name, expression) VALUES (116, "ang12_severeinjury:aggressive behavior", '[3310] ==1');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(116, 3310);
INSERT INTO rule_event (rule_id, event_id) VALUES(116, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (117, "ang12_severeinjury:imminent risk", '[3310] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(117, 3310);
INSERT INTO rule_event (rule_id, event_id) VALUES(117, 107);

INSERT INTO rule (rule_id, name, expression) VALUES (118, "ang13_pick:aggressive behavior", '[3320] >=3');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(118, 3320);
INSERT INTO rule_event (rule_id, event_id) VALUES(118, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (119, "ang14_bang:aggressive behavior", '[3330] >=3');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(119, 3330);
INSERT INTO rule_event (rule_id, event_id) VALUES(119, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (120, "ang15_cut:aggressive behavior", '[3340] >=3');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(120, 3340);
INSERT INTO rule_event (rule_id, event_id) VALUES(120, 106);

INSERT INTO rule (rule_id, name, expression) VALUES (121, "ang16_hurt:imminent risk", '[3350] >=2');
INSERT INTO rule_assessment_variable (rule_id, assessment_variable_id) VALUES(121, 3350);
INSERT INTO rule_event (rule_id, event_id) VALUES(121, 107);











