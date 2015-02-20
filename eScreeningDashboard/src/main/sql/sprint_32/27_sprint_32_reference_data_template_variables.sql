UPDATE assessment_variable
SET
  formula_template = '([1000] + [1001] + [1003] + [1004] + [1005] + [1006] + [1007] + [1008] + [1009] + T(Math).min([1000],(1f - [1002])))'
WHERE assessment_variable_id = 1010;
