-- The syntax for how we call functions in our spel expressions changed in 643, this formula now calls it correctly
UPDATE assessment_variable SET formula_template='calculateAge(\"[143]\")' WHERE assessment_variable_id='12';
-- The use of a override value was causing template 4 to not work correctly
UPDATE variable_template SET override_display_value=null WHERE variable_template_id=2950;

UPDATE assessment_variable SET formula_template='([1000] + [1001] + [1003] + [1004] + [1005] + [1006] + [1007] + [1008] + [1009] + T(Math).min([1000],(1 - [1002])))' WHERE assessment_variable_id='1010';
