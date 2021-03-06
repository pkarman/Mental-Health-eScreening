/******* Homelessness followup question rules update ***************/

/** combine rule 2&3, remove rule 3 **/
update rule set expression='[2000]==1 && [2001]==1' where rule_id=3;

update rule set expression = concat('([2000]==0 || ([2000]==1 && [2001]==1)) && ', expression) where rule_id between 4 and 11;

insert into rule_assessment_variable(rule_id, assessment_variable_id) values 
(3, 2000),(4,2000),(4,2001),(5,2000),(5,2001),(6,2000),(6,2001),(7,2000),(7,2001),(8,2000),(8,2001),(9,2000),(9,2001),(10,2000),(10,2001),(11,2000),(11,2001);

/******* Tobacco Followup Rules update ****************/

update rule set name='show quit time', expression = '[600]==1 && ([610]==3||[610]==4)' where rule_id=22;
insert into rule_assessment_variable (rule_id, assessment_variable_id) values (22, 600), (24, 600), (25,600),(26,600);

update rule set expression=concat('[600]==2 && ', expression) where rule_id in (24,25,26); 
delete from rule_event where rule_id=23;

/*******BTBIS update **********************************/

update rule set expression = '([2010] || [2011] || [2012] || [2013] || [2014] || [2015]) && ([2017] || [2018] || [2019] || [2020] || [2021])' where rule_id=31;

update rule set expression = '([2010] || [2011] || [2012] || [2013] || [2014] || [2015]) && ([2017] || [2018] || [2019] || [2020] || [2021]) && 
([2023] || [2024] || [2025] || [2027] || [2028] || [2029])' where rule_id=32;

update rule set expression = '([2010] || [2011] || [2012] || [2013] || [2014] || [2015]) && ([2017] || [2018] || [2019] || [2020] || [2021]) && 
([2023] || [2024] || [2025] || [2027] || [2028] || [2029]) && ([2031] || [2032] || [2033] || [2034] || [2035] || [2036])' where rule_id=33;

update rule set expression = '([2010] || [2011] || [2012] || [2013] || [2014] || [2015]) && ([2017] || [2018] || [2019] || [2020] || [2021]) && 
([2023] || [2024] || [2025] || [2027] || [2028] || [2029]) && ([2031] || [2032] || [2033] || [2034] || [2035] || [2036]) && [2047]==1' where rule_id=34;

insert into rule_assessment_variable (rule_id, assessment_variable_id) values 
(31, 2010), (32,2010),(33,2010),(34,2010),(31, 2011), (32,2011),(33,2011),(34,2011),
(31, 2012), (32,2012),(33,2012),(34,2012),
(31, 2013), (32,2013),(33,2013),(34,2013),
(31, 2014), (32,2014),(33,2014),(34,2014),
(31, 2015), (32,2015),(33,2015),(34,2015),
(32,2017),(33,2017),(34,2017),
(32,2018),(33,2018),(34,2018),
(32,2019),(33,2019),(34,2019),
(32,2020),(33,2020),(34,2020),
(32,2021),(33,2021),(34,2021),
(33,2023),(34,2023),
(33,2024),(34,2024),
(33,2025),(34,2025),
(33,2027),(34,2027),
(33,2028),(34,2028),
(33,2029),(34,2029),
(34,2031),(34,2032),(34,2033),(34,2034),(34,2035),(34,2036);