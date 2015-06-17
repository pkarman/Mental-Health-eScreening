-- The syntax for how we call functions in our spel expressions changed in 643, this formula now calls it correctly
UPDATE `escreening`.`assessment_variable` SET `formula_template`='calculateAge(\"[143]\")' WHERE `assessment_variable_id`='12';
-- The use of a override value was causing template 4 to not work correctly
UPDATE variable_template SET override_display_value=null WHERE variable_template_id=2950;