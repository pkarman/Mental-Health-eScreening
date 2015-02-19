INSERT INTO assessment_variable_type (assessment_variable_type_id, name, description)
VALUES ('5', 'Operator', 'Formula Operator Such as +,-,/,* etc');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11001', '5', '(', 'operator to fold left parenthesis => [(]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11002', '5', ')', 'operator to unfold right parenthesis => [)]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11003', '5', '+', 'operator to sum two operands => [+]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11004', '5', '-', 'operator to subtract two operands => [-]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11005', '5', '*', 'operator to find product of two operands => [*]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11006', '5', 'mod', 'operator to find remainder of two operands => [%]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11007', '5', '/', 'operator to divide two operands => [/]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11008', '5', 'random',
        'Returns a double value with a positive sign, greater than or equal to 0.0 and less than 1.0 => [T(Math).random]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11009', '5', 'max', 'operator to find the max => [T(Math).max]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11010', '5', 'min', 'operator to find the min => [T(Math).min]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11011', '5', 'toRadians',
        'Converts an angle measured in degrees to an approximately equivalent angle measured in radians. => [T(Math).toRadians]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11012', '5', 'toDegrees',
        'Converts an angle measured in radians to an approximately equivalent angle measured in degrees.  => [T(Math).toDegrees]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11013', '5', 'exp',
        'Returns Euler''s number e raised to the power of a double value => [T(Math).exp]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11014', '5', 'log',
        'Returns the natural logarithm (base e) of a double value => [T(Math).log]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11015', '5', 'log10',
        'Returns the base 10 logarithm of a double value => [T(Math).log10]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11016', '5', 'sqrt',
        'Returns the correctly rounded positive square root of a double value => [T(Math).sqrt]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11017', '5', 'cbrt',
        'Returns the cube root of a double value => [T(Math).cbrt]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11018', '5', 'ceil',
        'Returns the smallest double value that is greater than or equal to the argument and is equal to a mathematical integer => [T(Math).ceil]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11019', '5', 'floor',
        'Returns the largest double value that is less than or equal to the argument and is equal to a mathematical integer => [T(Math).floor]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11020', '5', 'pow',
        'Returns the value of the first argument raised to the power of the second argument => [T(Math).pow]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11021', '5', 'round',
        'Returns the closest int to the argument, with ties rounding up => [T(Math).round]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11022', '5', 'abs',
        'Returns the absolute value of an int value => [T(Math).abs]');

INSERT INTO assessment_variable (assessment_variable_id, assessment_variable_type_id, display_name, description)
VALUES ('11023', '5', ',',
        'separator between two operands => [,]');

CREATE TABLE assessment_formula (
  assessment_formula_id  INT          NOT NULL AUTO_INCREMENT,
  assessment_variable_id INT          NOT NULL,
  display_order          INT          NOT NULL,
  formula_token          VARCHAR(255) NOT NULL,
  date_created           TIMESTAMP    NOT NULL,
  PRIMARY KEY (assessment_formula_id),
  INDEX fk_assessment_var_id_idx (assessment_variable_id ASC),
  CONSTRAINT fk_assessment_var_id FOREIGN KEY (assessment_variable_id)
  REFERENCES assessment_variable (assessment_variable_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

/*ALTER TABLE assessment_formula ADD UNIQUE unique_av_disp_order(assessment_variable_id, display_order);*/


