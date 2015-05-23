UPDATE assessment_variable
SET
  formula_template = 'T(Math).max([2010]||[2011]||[2012]||[2013]||[2014]||[2015]||[2016]?0:999,[2010]||[2011]||[2012]||[2013]||[2014]||[2015]?1:0)'
WHERE
  assessment_variable_id = '10714';

UPDATE assessment_variable
SET
  formula_template = 'T(Math).max([2017]||[2018]||[2019]||[2020]||[2021]||[2022]?0:999,[2017]||[2018]||[2019]||[2020]||[2021]?1:0)'
WHERE
  assessment_variable_id = '10715';

UPDATE assessment_variable
SET
  formula_template = 'T(Math).max([2023]||[2024]||[2025]||[2027]||[2028]||[2029]||[2030]?0:999,[2023]||[2024]||[2025]||[2027]||[2028]||[2029]?1:0)'
WHERE
  assessment_variable_id = '10716';

UPDATE assessment_variable
SET
  formula_template = 'T(Math).max([2031]||[2032]||[2033]||[2034]||[2035]||[2036]||[2037]?0:999,[2031]||[2032]||[2033]||[2034]||[2035]||[2036]?1:0)'
WHERE
  assessment_variable_id = '10717';

INSERT INTO assessment_var_children (assessment_var_children_id, variable_parent, variable_child)
VALUES (1107, 10714, 2016);
INSERT INTO assessment_var_children (assessment_var_children_id, variable_parent, variable_child)
VALUES (1108, 10715, 2022);
INSERT INTO assessment_var_children (assessment_var_children_id, variable_parent, variable_child)
VALUES (1109, 10716, 2030);
INSERT INTO assessment_var_children (assessment_var_children_id, variable_parent, variable_child)
VALUES (1110, 10717, 2037);
