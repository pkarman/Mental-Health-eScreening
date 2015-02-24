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
  user_defined           BINARY       NOT NULL,
  date_created           TIMESTAMP    NOT NULL,
  PRIMARY KEY (assessment_formula_id),
  INDEX fk_assessment_var_id_idx (assessment_variable_id ASC),
  CONSTRAINT fk_assessment_var_id FOREIGN KEY (assessment_variable_id)
  REFERENCES assessment_variable (assessment_variable_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

ALTER TABLE assessment_formula ADD INDEX idx_ass_id_displ_order (assessment_variable_id ASC, display_order ASC);
/*ALTER TABLE assessment_formula ADD UNIQUE unique_av_disp_order(assessment_variable_id, display_order);*/

/* insert all formula token */
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10, 2, '140',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10, 3, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10, 4, '12',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10, 5, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10, 6, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10, 7, '141',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 2, '142',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 3, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 4, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 5, '10',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 6, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 7, '10',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 8, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 10, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (11, 11, '703',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 2, '700',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 4, '710',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 6, '720',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 8, '730',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (739, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (998, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (998, 2, '930',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (998, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (998, 4, '940',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (998, 5, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (999, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (999, 2, '960',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (999, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (999, 4, '970',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (999, 5, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 2, '1000',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 4, '1001',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 6, '1003',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 8, '1004',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 10, '1005',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 12, '1006',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 14, '1007',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 16, '1008',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 18, '1009',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 19, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 20, 'T',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 21, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 22, 'M',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 23, 'a',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 24, 't',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 25, 'h',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 26, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 27, '.',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 28, 'm',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 29, 'i',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 30, 'n',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 31, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 32, '1000',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 33, ',',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 34, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 35, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 36, 'f',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 37, '-',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 38, '1002',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 39, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 40, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1010, 41, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 2, '1020',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 4, '1030',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 6, '1040',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 8, '1050',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 10, '1060',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 12, '1070',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 14, '1080',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 16, '1090',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 18, '1100',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 19, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 20, '1110',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 21, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 22, '1120',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 23, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 24, '1130',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 25, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 26, '1140',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 27, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 28, '1150',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 29, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 30, '1160',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1189, 31, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1229, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1229, 2, '1200',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1229, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1229, 4, '1210',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1229, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1229, 6, '1220',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1229, 7, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 2, '2960',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 4, '2970',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 6, '2980',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 8, '2990',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 10, '3000',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 12, '1550',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 14, '1560',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 16, '1570',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 18, '1580',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1599, 19, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 2, '1660',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 4, '1670',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 6, '1680',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 8, '1690',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 10, '1700',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 12, '1710',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 14, '1720',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1749, 15, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 2, '1750',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 4, '1760',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 6, '1770',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 8, '1780',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 10, '1790',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 12, '1800',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 14, '1810',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 16, '1820',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 18, '1830',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 19, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 20, '1840',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 21, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 22, '1850',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 23, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 24, '1860',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 25, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 26, '1870',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 27, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 28, '1880',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 29, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 30, '1890',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 31, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 32, '1900',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 33, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 34, '1910',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1929, 35, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 2, '1940',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 4, '1950',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 6, '1960',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 8, '1970',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (1989, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 2, '2120',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 4, '2130',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 6, '2140',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 8, '2150',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 10, '2160',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 12, '2170',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 14, '2180',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2189, 15, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2289, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2289, 2, '2230',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2289, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2289, 4, '2240',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2289, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2289, 6, '2250',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2289, 7, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 2, '2580',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 4, '2590',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 6, '2600',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 8, '2610',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 10, '2620',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 12, '2630',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2640, 13, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2650, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2650, 2, '2550',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2650, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2650, 4, '2560',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2650, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2650, 6, '2570',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2650, 7, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 2, '2660',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 4, '2670',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 6, '2680',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 8, '2690',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 10, '2700',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 12, '2710',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 14, '2720',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 16, '2730',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 18, '2740',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 19, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 20, '2750',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 21, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 22, '2760',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 23, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 24, '2770',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 25, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 26, '2780',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2809, 27, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 2, '2820',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 4, '2830',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 6, '2840',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 8, '2850',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 10, '2860',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 12, '2870',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 14, '2880',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 16, '2890',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 18, '2900',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 19, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 20, '2910',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (2930, 21, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 2, '3200',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 4, '3210',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 5, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 6, '2',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 8, '3220',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 9, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 10, '3',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 12, '3230',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 13, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 14, '4',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 16, '3240',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 17, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 18, '2',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 19, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 20, '3250',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 21, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 22, '3',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 23, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 24, '3260',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 25, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 26, '4',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 27, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 28, '3270',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 29, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 30, '5',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 31, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 32, '3280',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 33, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 34, '3',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 35, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 36, '3290',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 37, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 38, '4',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 39, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 40, '3300',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 41, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 42, '5',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 43, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 44, '3310',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 45, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 46, '6',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 47, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 48, '3320',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 49, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 50, '3',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 51, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 52, '3330',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 53, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 54, '4',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 55, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 56, '3340',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 57, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 58, '5',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 59, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 60, '3350',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 61, '*',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 62, '6',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3389, 63, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 2, '3400',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 4, '3410',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 6, '3420',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 8, '3430',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (3489, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 3, '4000',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 4, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 5, '4020',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 6, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 7, '4040',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 8, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 9, '4060',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 10, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 11, '4080',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 12, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 13, '4100',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 14, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 15, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 16, '6',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4119, 17, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 3, '4120',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 4, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 5, '4140',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 6, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 7, '4160',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 8, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 9, '4180',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 10, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 11, '4220',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 12, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 13, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 14, '5',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4239, 15, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 3, '4240',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 4, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 5, '4260',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 6, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 7, '4280',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 8, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 9, '4300',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 10, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 11, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 12, '4',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4319, 13, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 3, '4320',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 4, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 5, '4340',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 6, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 7, '4360',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 8, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 9, '4380',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 10, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 11, '4400',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 12, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 13, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 14, '5',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4419, 15, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 3, '4420',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 4, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 5, '4430',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 6, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 7, '4440',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 8, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 9, '4460',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 10, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 11, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 12, '4',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4499, 13, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 2, '4480',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 4, '4500',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 6, '4520',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 8, '4540',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 10, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4559, 11, '4',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 3, '4560',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 4, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 5, '4580',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 6, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 7, '4600',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 8, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 9, '4620',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 10, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 11, '4640',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 12, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 13, '4660',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 14, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 15, '4680',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 16, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 17, '4700',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 18, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 19, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 20, '8',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (4789, 21, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 2, '4000',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 4, '4020',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 6, '4040',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 8, '4060',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 10, '4080',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 12, '4100',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10600, 13, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 2, '4120',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 4, '4140',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 6, '4160',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 8, '4180',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 10, '4220',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10601, 11, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 2, '4240',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 4, '4260',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 6, '4280',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 8, '4300',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10602, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 2, '4320',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 4, '4340',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 6, '4360',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 8, '4380',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 10, '4400',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10603, 11, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 2, '4420',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 4, '4430',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 6, '4440',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 8, '4460',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10604, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 3, '4480',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 4, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 5, '4500',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 6, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 7, '4520',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 8, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 9, '4540',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 10, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 11, '/',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 12, '4',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10605, 13, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 2, '4560',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 4, '4580',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 6, '4600',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 8, '4620',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 10, '4640',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 12, '4660',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 14, '4680',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 16, '4700',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10606, 17, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 2, '2500',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 4, '2501',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 6, '2502',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 8, '2009',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10710, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 3, '1522',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 4, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 5, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 6, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 7, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 8, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 10, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 11, '1523',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 12, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 13, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 14, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 15, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 16, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 18, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 19, '1524',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 20, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 21, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 22, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 23, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 24, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10711, 25, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 3, '1532',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 4, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 5, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 6, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 7, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 8, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 10, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 11, '1533',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 12, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 13, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 14, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 15, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 16, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 18, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 19, '1534',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 20, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 21, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 22, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 23, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 24, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 25, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 26, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 27, '1535',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 28, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 29, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 30, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 31, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 32, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 33, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 34, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 35, '1536',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 36, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 37, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 38, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 39, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 40, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10712, 41, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 2, '10711',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 4, '10712',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 6, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 7, '202',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 8, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 9, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 10, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 11, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 12, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 14, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 15, '214',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 16, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 17, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 18, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 19, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 20, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10713, 21, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 3, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 4, '2010',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 5, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 6, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 7, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 8, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 10, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 11, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 12, '2011',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 13, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 14, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 15, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 16, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 17, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 18, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 19, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 20, '2012',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 21, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 22, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 23, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 24, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 25, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 26, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 27, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 28, '2013',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 29, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 30, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 31, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 32, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 33, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 34, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 35, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 36, '2014',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 37, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 38, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 39, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 40, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 41, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 42, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 43, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 44, '2015',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 45, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 46, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 47, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 48, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 49, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 50, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 51, '>',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 52, '=',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 53, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 54, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 55, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 56, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 57, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10714, 58, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 3, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 4, '2017',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 5, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 6, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 7, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 8, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 10, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 11, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 12, '2018',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 13, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 14, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 15, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 16, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 17, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 18, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 19, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 20, '2019',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 21, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 22, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 23, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 24, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 25, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 26, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 27, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 28, '2020',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 29, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 30, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 31, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 32, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 33, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 34, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 35, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 36, '2021',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 37, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 38, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 39, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 40, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 41, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 42, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 43, '>',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 44, '=',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 45, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 46, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 47, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 48, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 49, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10715, 50, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 3, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 4, '2023',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 5, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 6, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 7, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 8, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 10, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 11, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 12, '2024',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 13, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 14, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 15, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 16, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 17, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 18, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 19, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 20, '2025',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 21, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 22, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 23, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 24, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 25, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 26, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 27, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 28, '2027',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 29, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 30, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 31, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 32, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 33, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 34, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 35, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 36, '2028',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 37, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 38, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 39, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 40, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 41, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 42, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 43, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 44, '2029',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 45, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 46, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 47, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 48, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 49, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 50, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 51, '>',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 52, '=',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 53, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 54, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 55, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 56, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 57, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10716, 58, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 3, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 4, '2031',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 5, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 6, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 7, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 8, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 10, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 11, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 12, '2032',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 13, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 14, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 15, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 16, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 17, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 18, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 19, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 20, '2033',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 21, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 22, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 23, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 24, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 25, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 26, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 27, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 28, '2034',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 29, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 30, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 31, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 32, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 33, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 34, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 35, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 36, '2035',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 37, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 38, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 39, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 40, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 41, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 42, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 43, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 44, '2036',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 45, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 46, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 47, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 48, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 49, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 50, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 51, '>',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 52, '=',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 53, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 54, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 55, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 56, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 57, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10717, 58, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 2, '10714',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 4, '10715',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 6, '10716',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 8, '10717',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10718, 9, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10719, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10719, 2, '10718',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10719, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10719, 4, '2047',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10719, 5, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 2, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 3, '2809',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 4, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 5, '>',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 6, '=',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 7, '7',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 8, '&',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 9, '&',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 10, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 11, '2790',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 12, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 13, '=',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 14, '=',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 15, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 16, '&',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 17, '&',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 18, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 19, '2800',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 20, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 21, '>',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 22, '=',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 23, '2',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 24, ')',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 25, '?',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 26, '1',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 27, ':',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10720, 28, '0',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 1, '(',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 2, '1020',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 3, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 4, '1040',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 5, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 6, '1050',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 7, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 8, '1060',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 9, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 10, '1070',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 11, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 12, '1080',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 13, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 14, '1090',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 15, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 16, '1100',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 17, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 18, '1110',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 19, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 20, '1120',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 21, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 22, '1130',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 23, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 24, '1140',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 25, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 26, '1150',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 27, '+',true);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 28, '1160',false);
insert into assessment_formula (assessment_variable_id,display_order,formula_token,user_defined) values (10800, 29, ')',true);


/* update assessment variable table with right informations*/
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_gender', description = "Gender"
WHERE assessment_variable_id = 20;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_gender', description = "Male"
WHERE assessment_variable_id = 21;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_gender', description = "Female"
WHERE assessment_variable_id = 22;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_ethnic', description = "Ethnicity"
WHERE assessment_variable_id = 30;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_ethnic', description = "Hispanic/Latino"
WHERE assessment_variable_id = 31;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_ethnic', description = "Not Hispanic/Latino"
WHERE assessment_variable_id = 32;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_ethnic', description = "Decline To Answer"
WHERE assessment_variable_id = 33;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_racewhite', description = "Race"
WHERE assessment_variable_id = 40;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_racewhite', description = "White/Caucasian"
WHERE assessment_variable_id = 41;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_race_black', description = "Black/African American"
WHERE assessment_variable_id = 42;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_race_amind', description = "American Indian or Alaskan Native"
WHERE assessment_variable_id = 43;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_race_asian',
  description                   = "Asian (Filipino, Japanese, Korean, Chinese, Vietnamese, etc.)"
WHERE assessment_variable_id = 44;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_race_pacisl',
  description                   = "Native Hawaiian or Pacific Islander"
WHERE assessment_variable_id = 45;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_race_oth', description = "Other, please specify"
WHERE assessment_variable_id = 46;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_race_decline', description = "Decline To Answer"
WHERE assessment_variable_id = 47;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_education',
  description                   = "What is the highest grade of education that you have completed?"
WHERE assessment_variable_id = 50;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_education', description = "Some High School"
WHERE assessment_variable_id = 51;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_education', description = "GED"
WHERE assessment_variable_id = 52;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_education', description = "High School Diploma"
WHERE assessment_variable_id = 53;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_education', description = "Some College"
WHERE assessment_variable_id = 54;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_education', description = "Associates Degree"
WHERE assessment_variable_id = 55;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_education', description = "4-year College Degree"
WHERE assessment_variable_id = 56;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_education', description = "Master's Degree"
WHERE assessment_variable_id = 57;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_education',
  description                   = "Doctoral Degree (Ph.D, M.D., DDS, etc.)"
WHERE assessment_variable_id = 58;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_workstatus', description = "What is your employment status?"
WHERE assessment_variable_id = 60;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_workstatus', description = "Full Time"
WHERE assessment_variable_id = 61;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_workstatus', description = "Part Time"
WHERE assessment_variable_id = 62;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_workstatus', description = "Seasonal"
WHERE assessment_variable_id = 63;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_workstatus', description = "Day Labor"
WHERE assessment_variable_id = 64;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_workstatus', description = "Unemployed"
WHERE assessment_variable_id = 65;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_occupation', description = "What is your usual occupation?"
WHERE assessment_variable_id = 70;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_income_none',
  description                   = "What are your source(s) of income?"
WHERE assessment_variable_id = 80;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_income_none', description = "No Income"
WHERE assessment_variable_id = 81;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_income_wrk', description = "Work"
WHERE assessment_variable_id = 82;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_income_unemp', description = "Unemployment"
WHERE assessment_variable_id = 83;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_income_dis', description = "Disability"
WHERE assessment_variable_id = 84;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_income_gi', description = "GI Bill"
WHERE assessment_variable_id = 85;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_income_retire', description = "Retirement/Pension"
WHERE assessment_variable_id = 86;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_income_other', description = "Other, please specify"
WHERE assessment_variable_id = 87;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_livewith_alone', description = "Who do you live with?"
WHERE assessment_variable_id = 90;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_livewith_parent', description = "Parents/Relatives"
WHERE assessment_variable_id = 92;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_livewith_friend', description = "Friends/Roommates"
WHERE assessment_variable_id = 93;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_livewith_spouse', description = "Spouse or Partner"
WHERE assessment_variable_id = 94;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_livewith_child', description = "Children"
WHERE assessment_variable_id = 95;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_relationship',
  description                   = "What is your current relationship status?"
WHERE assessment_variable_id = 100;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_relationship', description = "Single"
WHERE assessment_variable_id = 101;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_relationship', description = "Married"
WHERE assessment_variable_id = 102;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_relationship', description = "Separated"
WHERE assessment_variable_id = 103;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_relationship', description = "Divorced"
WHERE assessment_variable_id = 104;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_relationship', description = "Cohabitating"
WHERE assessment_variable_id = 105;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_relationship', description = "Civil Union"
WHERE assessment_variable_id = 106;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_relationship', description = "Widowed/Widower"
WHERE assessment_variable_id = 107;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'child_agegroup', description = "Child Age"
WHERE assessment_variable_id = 132;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'child_agegroup', description = "younger than 1"
WHERE assessment_variable_id = 133;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'child_agegroup', description = "1-2"
WHERE assessment_variable_id = 134;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'child_agegroup', description = "3-5"
WHERE assessment_variable_id = 135;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'child_agegroup', description = "6-17"
WHERE assessment_variable_id = 136;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'child_agegroup', description = "18 and older"
WHERE assessment_variable_id = 137;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_heightft', description = "Feet"
WHERE assessment_variable_id = 140;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_heightinch', description = "Inches"
WHERE assessment_variable_id = 141;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_weight', description = "Weight"
WHERE assessment_variable_id = 142;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_DOB', description = "Date of Birth (mm/dd/yyyy)"
WHERE assessment_variable_id = 143;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_rel_hurt',
  description                   = "Are you in a relationship in which you have been physically hurt or felt threatened?"
WHERE assessment_variable_id = 150;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_rel_hurt', description = "No"
WHERE assessment_variable_id = 151;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_rel_hurt', description = "Yes"
WHERE assessment_variable_id = 152;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_emo_none',
  description                   = "Who gives you emotional support and advice?"
WHERE assessment_variable_id = 160;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_emo_none', description = "No One"
WHERE assessment_variable_id = 161;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_emo_parents', description = "Parents"
WHERE assessment_variable_id = 162;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_emo_friends', description = "Friends"
WHERE assessment_variable_id = 163;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_emo_spouse', description = "Partner/Spouse"
WHERE assessment_variable_id = 164;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_emo_therapist', description = "Therapist"
WHERE assessment_variable_id = 165;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_emo_spiritual', description = "Spiritual/Religious Advisor"
WHERE assessment_variable_id = 166;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_emo_children', description = "Children"
WHERE assessment_variable_id = 167;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_emo_other', description = "Other, please specify"
WHERE assessment_variable_id = 169;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_va_enroll',
  description                   = "What brings you to the VA health care system?"
WHERE assessment_variable_id = 200;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_va_enroll', description = "Enrollment"
WHERE assessment_variable_id = 201;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_va_menthealth', description = "Mental Health Concerns"
WHERE assessment_variable_id = 202;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_va_physhealth', description = "Physical Health Concerns"
WHERE assessment_variable_id = 203;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_va_primcare', description = "Establish Primary Care"
WHERE assessment_variable_id = 204;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_va_other', description = "Other, please specify"
WHERE assessment_variable_id = 205;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_info_prost',
  description                   = "Do you want/need information or assistance with health care?"
WHERE assessment_variable_id = 210;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_health_none', description = "None"
WHERE assessment_variable_id = 211;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_prost', description = "Prosthetic Equipment"
WHERE assessment_variable_id = 212;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_sex', description = "Sexual Health"
WHERE assessment_variable_id = 213;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_ment', description = "Mental Health Appointment"
WHERE assessment_variable_id = 214;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_subst', description = "Substance Use"
WHERE assessment_variable_id = 215;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_visual',
  description                   = "Visual Impairment Services Team (VIST)"
WHERE assessment_variable_id = 216;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_info_rehab',
  description                   = "Do you want/need information or assistance with any of the following employment services?"
WHERE assessment_variable_id = 220;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_emp_none', description = "None"
WHERE assessment_variable_id = 221;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_rehab', description = "VA Vocational Rehabilitation"
WHERE assessment_variable_id = 222;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_unemp', description = "Unemployment Benefits"
WHERE assessment_variable_id = 223;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_work', description = "VA Work Study"
WHERE assessment_variable_id = 224;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_info_adj',
  description                   = "Do you want/need information or assistance with any of the following social matters?"
WHERE assessment_variable_id = 230;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_soc_none', description = "None"
WHERE assessment_variable_id = 231;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_adj', description = "Adjustment to Civilian Life"
WHERE assessment_variable_id = 232;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_relat', description = "Relationship Concerns"
WHERE assessment_variable_id = 233;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_support', description = "Support Groups"
WHERE assessment_variable_id = 234;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_info_home',
  description                   = "Do you want/need information or assistance with housing?"
WHERE assessment_variable_id = 240;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_house_none', description = "None"
WHERE assessment_variable_id = 241;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_home', description = "Homeless"
WHERE assessment_variable_id = 242;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_forcl', description = "Foreclosure"
WHERE assessment_variable_id = 243;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_other', description = "Yes, please specify?"
WHERE assessment_variable_id = 244;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_info_comp',
  description                   = "Do you want/need information or assistance with any of the following VA benefits?"
WHERE assessment_variable_id = 250;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_va_none', description = "None"
WHERE assessment_variable_id = 251;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_comp', description = "VA Compensation"
WHERE assessment_variable_id = 252;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_gi', description = "GI Bill"
WHERE assessment_variable_id = 253;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_loan', description = "VA Home Loan"
WHERE assessment_variable_id = 254;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_info_comm',
  description                   = "Do you want/need information or assistance with financial hardship?"
WHERE assessment_variable_id = 260;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_fin_none', description = "None"
WHERE assessment_variable_id = 261;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_comm',
  description                   = "Information About VA Or Community Resources"
WHERE assessment_variable_id = 262;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_info_parole',
  description                   = "Do you want/need information or assistance with any of the following legal matters?"
WHERE assessment_variable_id = 270;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_legal_none', description = "None"
WHERE assessment_variable_id = 271;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_parole', description = "Parole"
WHERE assessment_variable_id = 272;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_probat', description = "Probation"
WHERE assessment_variable_id = 273;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_warrant', description = "Warrants"
WHERE assessment_variable_id = 274;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_info_bank', description = "Bankruptcy"
WHERE assessment_variable_id = 275;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_legal_none', description = "Do you have any legal concerns?"
WHERE assessment_variable_id = 300;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_civil', description = "Civil Issues"
WHERE assessment_variable_id = 301;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_child', description = "Child Support"
WHERE assessment_variable_id = 302;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_tax', description = "Taxes"
WHERE assessment_variable_id = 303;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_bank', description = "Bankruptcy"
WHERE assessment_variable_id = 304;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_ticket', description = "Outstanding Tickets"
WHERE assessment_variable_id = 305;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_arrest', description = "Arrest Warrants"
WHERE assessment_variable_id = 306;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_restrain', description = "Restraining Orders"
WHERE assessment_variable_id = 307;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_dui', description = "DUIs"
WHERE assessment_variable_id = 308;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_prob', description = "Probation"
WHERE assessment_variable_id = 309;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_parole', description = "Parole"
WHERE assessment_variable_id = 310;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_jag', description = "JAG"
WHERE assessment_variable_id = 311;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_legal_cps', description = "Child Protective Services"
WHERE assessment_variable_id = 312;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'spirit_rel_important',
  description                   = "Is spirituality and/or religion important to you now?"
WHERE assessment_variable_id = 400;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_rel_important', description = "No"
WHERE assessment_variable_id = 401;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_rel_important', description = "Yes"
WHERE assessment_variable_id = 402;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'spirit_faith_comm',
  description                   = "Are you presently connected to a faith community?"
WHERE assessment_variable_id = 420;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_faith_comm', description = "No"
WHERE assessment_variable_id = 421;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_faith_comm', description = "Yes"
WHERE assessment_variable_id = 422;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_faith_comm',
  description                   = "No, but I would like to be part of one"
WHERE assessment_variable_id = 423;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'spirit_chap_referral',
  description                   = "Would you like a VA chaplain to contact you  for any current concerns or support?"
WHERE assessment_variable_id = 430;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_chap_referral', description = "No, not now"
WHERE assessment_variable_id = 431;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_chap_referral', description = "Yes"
WHERE assessment_variable_id = 432;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'spirit_combat_change',
  description                   = "Did combat or military service affect your view of spirituality and/or religion?"
WHERE assessment_variable_id = 440;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_combat_change', description = "No"
WHERE assessment_variable_id = 441;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_combat_change', description = "Yes"
WHERE assessment_variable_id = 442;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'spirit_combat_change', description = "I don't Know"
WHERE assessment_variable_id = 443;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tob_use', description = "Do you use tobacco currently?"
WHERE assessment_variable_id = 600;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_use',
  description                   = "Never. I have never used tobacco on a regular basis"
WHERE assessment_variable_id = 601;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_use',
  description                   = "No. I used tobacco in the past, but have quit"
WHERE assessment_variable_id = 602;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_use',
  description                   = "Yes. I currently use tobacco on a regular basis"
WHERE assessment_variable_id = 603;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tob_quit_when',
  description                   = "When did you quit using tobacco on a regular basis?"
WHERE assessment_variable_id = 610;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_quit_when',
  description                   = "I quit using tobacco more than 7 years ago"
WHERE assessment_variable_id = 611;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_quit_when',
  description                   = "I quit using tobacco more than 1 year, but less than 7 years ago"
WHERE assessment_variable_id = 612;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_quit_when',
  description                   = "I quit using tobacco within the last 12 months"
WHERE assessment_variable_id = 613;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tob_ciggs',
  description                   = "If yes, Which types of tobacco do you use"
WHERE assessment_variable_id = 620;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_ciggs', description = "Cigarettes"
WHERE assessment_variable_id = 621;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_cigar', description = "Cigars/pipes"
WHERE assessment_variable_id = 622;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tob_smokeless', description = "Smokeless tobacco (snuff/chewing)"
WHERE assessment_variable_id = 623;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_firstname', description = "First Name"
WHERE assessment_variable_id = 630;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_midname', description = "Middle Name"
WHERE assessment_variable_id = 632;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_lastname', description = "Last Name"
WHERE assessment_variable_id = 634;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_SSN', description = "SSN Last 4 Digits"
WHERE assessment_variable_id = 636;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_email', description = "Email"
WHERE assessment_variable_id = 638;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_contact', description = "Best number to reach you"
WHERE assessment_variable_id = 640;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_contact', description = "(Example: 5551235555)"
WHERE assessment_variable_id = 641;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_call', description = "Best time to call"
WHERE assessment_variable_id = 642;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_call', description = "Any time"
WHERE assessment_variable_id = 643;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_call', description = "Morning"
WHERE assessment_variable_id = 644;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_call', description = "Afternoon"
WHERE assessment_variable_id = 645;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_call', description = "Evening"
WHERE assessment_variable_id = 646;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_call', description = "Other"
WHERE assessment_variable_id = 647;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_oif',
  description                   = "Yes, Service in Operation Iraqi Freedom (OIF) (Iraq, Kuwait, Saudi Arabia, Turkey, Other)"
WHERE assessment_variable_id = 659;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_oef_oif',
  description                   = "Did you serve in Operation Enduring Freedom (OEF) or in Operation Iraqi Freedom (OIF), either on the ground, in the nearby coastal waters, or in the air above, after September 11, 2001? (Mark one answer - consider only your most recent deployment or service period)"
WHERE assessment_variable_id = 660;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_oif', description = "No, no service in OEF or OIF"
WHERE assessment_variable_id = 661;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_oif',
  description                   = "Yes, Service in Operation Enduring Freedom (OEF) (Afghanistan, Georgia, Krgyzstan, Pakistan, Tajikistan, Uzbekisatn, the Phillippines, Other)"
WHERE assessment_variable_id = 662;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_oef_loc',
  description                   = "Where was your most recent OEF service?"
WHERE assessment_variable_id = 663;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_loc', description = "Afghanistan"
WHERE assessment_variable_id = 664;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_loc', description = "Tajikistan"
WHERE assessment_variable_id = 665;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_loc', description = "Georgia"
WHERE assessment_variable_id = 666;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_loc', description = "Uzbekistan"
WHERE assessment_variable_id = 667;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_loc', description = "Kyrgystan"
WHERE assessment_variable_id = 668;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_loc', description = "The Philippines"
WHERE assessment_variable_id = 669;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_loc', description = "Pakistan"
WHERE assessment_variable_id = 670;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oef_other', description = "Other, please specify"
WHERE assessment_variable_id = 671;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_oif_loc',
  description                   = "Where was your most recent OIF service?"
WHERE assessment_variable_id = 683;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oif_loc', description = "Iraq"
WHERE assessment_variable_id = 684;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oif_loc', description = "Kuwait"
WHERE assessment_variable_id = 685;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oif_loc', description = "Saudi Arabia"
WHERE assessment_variable_id = 686;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oif_loc', description = "Turkey"
WHERE assessment_variable_id = 687;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oif_loc_other', description = "Other, please specify"
WHERE assessment_variable_id = 688;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'es1_listen',
  description                   = "I have someone who will listen to me when I need to talk"
WHERE assessment_variable_id = 700;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es1_listen', description = "never"
WHERE assessment_variable_id = 701;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es1_listen', description = "rarely"
WHERE assessment_variable_id = 702;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es1_listen', description = "sometimes"
WHERE assessment_variable_id = 703;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es1_listen', description = "usually"
WHERE assessment_variable_id = 704;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es1_listen', description = "always"
WHERE assessment_variable_id = 705;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'es2_talk',
  description                   = "I have someone to confide in or talk to about myself or my problems"
WHERE assessment_variable_id = 710;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es2_talk', description = "never"
WHERE assessment_variable_id = 711;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es2_talk', description = "rarely"
WHERE assessment_variable_id = 712;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es2_talk', description = "sometimes"
WHERE assessment_variable_id = 713;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es2_talk', description = "usually"
WHERE assessment_variable_id = 714;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es2_talk', description = "always"
WHERE assessment_variable_id = 715;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'es3_feel',
  description                   = "I have someone who makes me feel appreciated"
WHERE assessment_variable_id = 720;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es3_feel', description = "never"
WHERE assessment_variable_id = 721;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es3_feel', description = "rarely"
WHERE assessment_variable_id = 722;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es3_feel', description = "sometimes"
WHERE assessment_variable_id = 723;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es3_feel', description = "usually"
WHERE assessment_variable_id = 724;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es3_feel', description = "always"
WHERE assessment_variable_id = 725;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'es4_bad',
  description                   = "I have someone to talk with when I have a bad day"
WHERE assessment_variable_id = 730;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es4_bad', description = "never"
WHERE assessment_variable_id = 731;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es4_bad', description = "rarely"
WHERE assessment_variable_id = 732;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es4_bad', description = "sometimes"
WHERE assessment_variable_id = 733;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es4_bad', description = "usually"
WHERE assessment_variable_id = 734;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'es4_bad', description = "always"
WHERE assessment_variable_id = 735;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_stable_house',
  description                   = "No, not living in stable housing"
WHERE assessment_variable_id = 761;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_stable_house',
  description                   = "Yes, living in stable housing"
WHERE assessment_variable_id = 762;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_stable_house', description = " I decline to be screened"
WHERE assessment_variable_id = 763;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_stable_worry',
  description                   = "No, I am not worried about housing in the near future"
WHERE assessment_variable_id = 771;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_stable_worry',
  description                   = "Yes, worried about stable housing in the near future"
WHERE assessment_variable_id = 772;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_live_2mos',
  description                   = "Apartment/House/Room-no government subsidy"
WHERE assessment_variable_id = 781;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_live_2mos',
  description                   = "Apartment/House/Room- with government subsidy"
WHERE assessment_variable_id = 782;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_live_2mos',
  description                   = "With a friend or family member"
WHERE assessment_variable_id = 783;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_live_2mos', description = "Motel/Hotel"
WHERE assessment_variable_id = 784;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_live_2mos',
  description                   = "Short term institution like a hospital, rehabilitation center, or drug treatment facility"
WHERE assessment_variable_id = 785;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_live_2mos', description = "Homeless shelter"
WHERE assessment_variable_id = 786;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_live_2mos',
  description                   = "Anywhere outside, eg. street, vehicle, abandoned building"
WHERE assessment_variable_id = 787;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_live_2mos', description = "Other"
WHERE assessment_variable_id = 788;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_house_ref', description = "No"
WHERE assessment_variable_id = 791;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'HomelessCR_house_ref', description = "Yes"
WHERE assessment_variable_id = 792;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_language',
  description                   = "What language do you prefer to get your health information?"
WHERE assessment_variable_id = 800;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "English"
WHERE assessment_variable_id = 801;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "Spanish"
WHERE assessment_variable_id = 802;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "Tagalog"
WHERE assessment_variable_id = 803;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "Chinese"
WHERE assessment_variable_id = 804;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "German"
WHERE assessment_variable_id = 805;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "Japanese"
WHERE assessment_variable_id = 806;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "Korean"
WHERE assessment_variable_id = 807;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "Russian"
WHERE assessment_variable_id = 808;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language', description = "Vietnamese"
WHERE assessment_variable_id = 809;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_language_other', description = "Other, please specify"
WHERE assessment_variable_id = 810;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_will',
  description                   = "Do you have an Advance Directive or Durable Power of Attorney for Healthcare?"
WHERE assessment_variable_id = 820;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_will', description = "No"
WHERE assessment_variable_id = 821;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_will', description = "Yes"
WHERE assessment_variable_id = 822;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_will_info',
  description                   = "Would you like information about Advance Directive?"
WHERE assessment_variable_id = 826;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_will_info', description = "No"
WHERE assessment_variable_id = 827;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'demo_will_info', description = "Yes"
WHERE assessment_variable_id = 828;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health16_hearing', description = "Hearing loss"
WHERE assessment_variable_id = 930;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health16_hearing', description = "Not Bothered At All"
WHERE assessment_variable_id = 931;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health16_hearing', description = "Bothered A Little"
WHERE assessment_variable_id = 932;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health16_hearing', description = "Bothered A Lot"
WHERE assessment_variable_id = 933;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health17_tinnitus', description = "Tinnitus (ringing in the ears)"
WHERE assessment_variable_id = 940;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health17_tinnitus', description = "Not Bothered At All"
WHERE assessment_variable_id = 941;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health17_tinnitus', description = "Bothered A Little"
WHERE assessment_variable_id = 942;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health17_tinnitus', description = "Bothered A Lot"
WHERE assessment_variable_id = 943;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health20_vision',
  description                   = "Problem with vision (e.g., double or blurred vision, light sensitivity, difficulty seeing moving objects)"
WHERE assessment_variable_id = 950;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health20_vision', description = "Not Bothered At All"
WHERE assessment_variable_id = 951;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health20_vision', description = "Bothered A Little"
WHERE assessment_variable_id = 952;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health20_vision', description = "Bothered A Lot"
WHERE assessment_variable_id = 953;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health21_wghtgain', description = "Weight gain"
WHERE assessment_variable_id = 960;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health21_wghtgain', description = "Not Bothered At All"
WHERE assessment_variable_id = 961;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health21_wghtgain', description = "Bothered A Little"
WHERE assessment_variable_id = 962;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health21_wghtgain', description = "Bothered A Lot"
WHERE assessment_variable_id = 963;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health22_wghtloss', description = "Weight loss"
WHERE assessment_variable_id = 970;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health22_wghtloss', description = "Not Bothered At All"
WHERE assessment_variable_id = 971;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health22_wghtloss', description = "Bothered A Little"
WHERE assessment_variable_id = 972;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health22_wghtloss', description = "Bothered A Lot"
WHERE assessment_variable_id = 973;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health23_forgetfulness', description = "Forgetfulness"
WHERE assessment_variable_id = 980;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health23_forgetfulness', description = "Not Bothered At All"
WHERE assessment_variable_id = 981;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health23_forgetfulness', description = "Bothered A Little"
WHERE assessment_variable_id = 982;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health23_forgetfulness', description = "Bothered A Lot"
WHERE assessment_variable_id = 983;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST1_other',
  description                   = "Have you used drugs other than those required for medical reasons?"
WHERE assessment_variable_id = 1000;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST2_abuse',
  description                   = "Do you abuse more than one drug at a time?"
WHERE assessment_variable_id = 1001;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST3_ablestop',
  description                   = "Are you always able to stop using drugs when you want to?"
WHERE assessment_variable_id = 1002;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST4_blackout',
  description                   = "Have you had 'blackouts' or 'flashbacks' as a result of drug use?"
WHERE assessment_variable_id = 1003;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST5_guilty',
  description                   = "Do you ever feel bad or guilty about your drug use?"
WHERE assessment_variable_id = 1004;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST6_complain',
  description                   = "Does your spouse (or parents) ever complain about your involvement with drugs?"
WHERE assessment_variable_id = 1005;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST7_neglect',
  description                   = "Have you neglected your family because of your use of drugs?"
WHERE assessment_variable_id = 1006;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST8_illegal',
  description                   = "Have you engaged in illegal activities in order to obtain drugs?"
WHERE assessment_variable_id = 1007;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST9_withdraw',
  description                   = "Have you ever experienced withdrawal symptoms (felt sick) when you stopped taking drugs?"
WHERE assessment_variable_id = 1008;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'DAST10_medical',
  description                   = "Have you had medical problems as a result of your drug use (e.g., memory loss, hepatitis, convulsions, bleeding, etc.)?"
WHERE assessment_variable_id = 1009;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health3_arm',
  description                   = "Pain in your arms, legs, or joints (knees, hips, etc.)"
WHERE assessment_variable_id = 1020;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health3_arm', description = "Not Bothered At All"
WHERE assessment_variable_id = 1021;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health3_arm', description = "Bothered A Little"
WHERE assessment_variable_id = 1022;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health3_arm', description = "Bothered A Lot"
WHERE assessment_variable_id = 1023;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health4_cramp',
  description                   = "Menstrual cramps or other problems with your periods (Women Only)"
WHERE assessment_variable_id = 1030;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health4_cramp', description = "Not Bothered At All"
WHERE assessment_variable_id = 1031;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health4_cramp', description = "Bothered A Little"
WHERE assessment_variable_id = 1032;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health4_cramp', description = "Bothered A Lot"
WHERE assessment_variable_id = 1033;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health5_headache', description = "Headaches"
WHERE assessment_variable_id = 1040;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health5_headache', description = "Not Bothered At All"
WHERE assessment_variable_id = 1041;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health5_headache', description = "Bothered A Little"
WHERE assessment_variable_id = 1042;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health5_headache', description = "Bothered A Lot"
WHERE assessment_variable_id = 1043;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health6_chest', description = "Chest pain"
WHERE assessment_variable_id = 1050;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health6_chest', description = "Not Bothered At All"
WHERE assessment_variable_id = 1051;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health6_chest', description = "Bothered A Little"
WHERE assessment_variable_id = 1052;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health6_chest', description = "Bothered A Lot"
WHERE assessment_variable_id = 1053;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health7_dizzy', description = "Dizziness"
WHERE assessment_variable_id = 1060;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health7_dizzy', description = "Not Bothered At All"
WHERE assessment_variable_id = 1061;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health7_dizzy', description = "Bothered A Little"
WHERE assessment_variable_id = 1062;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health7_dizzy', description = "Bothered A Lot"
WHERE assessment_variable_id = 1063;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health8_faint', description = "Fainting spells"
WHERE assessment_variable_id = 1070;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health8_faint', description = "Not Bothered At All"
WHERE assessment_variable_id = 1071;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health8_faint', description = "Bothered A Little"
WHERE assessment_variable_id = 1072;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health8_faint', description = "Bothered A Lot"
WHERE assessment_variable_id = 1073;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health9_heart', description = "Feeling your heart pound or race"
WHERE assessment_variable_id = 1080;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health9_heart', description = "Not Bothered At All"
WHERE assessment_variable_id = 1081;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health9_heart', description = "Bothered A Little"
WHERE assessment_variable_id = 1082;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health9_heart', description = "Bothered A Lot"
WHERE assessment_variable_id = 1083;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health10_breath', description = "Shortness of breath"
WHERE assessment_variable_id = 1090;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health10_breath', description = "Not Bothered At All"
WHERE assessment_variable_id = 1091;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health10_breath', description = "Bothered A Little"
WHERE assessment_variable_id = 1092;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health10_breath', description = "Bothered A Lot"
WHERE assessment_variable_id = 1093;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health11_sex',
  description                   = "Pain or problems during sexual intercourse"
WHERE assessment_variable_id = 1100;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health11_sex', description = "Not Bothered At All"
WHERE assessment_variable_id = 1101;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health11_sex', description = "Bothered A Little"
WHERE assessment_variable_id = 1102;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health11_sex', description = "Bothered A Lot"
WHERE assessment_variable_id = 1103;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health12_constipation',
  description                   = "Constipation, loose bowels, or diarrhea"
WHERE assessment_variable_id = 1110;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health12_constipation', description = "Not Bothered At All"
WHERE assessment_variable_id = 1111;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health12_constipation', description = "Bothered A Little"
WHERE assessment_variable_id = 1112;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health12_constipation', description = "Bothered A Lot"
WHERE assessment_variable_id = 1113;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health13_nausea', description = "Nausea, gas or indigestion"
WHERE assessment_variable_id = 1120;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health13_nausea', description = "Not Bothered At All"
WHERE assessment_variable_id = 1121;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health13_nausea', description = "Bothered A Little"
WHERE assessment_variable_id = 1122;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health13_nausea', description = "Bothered A Lot"
WHERE assessment_variable_id = 1123;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health14_tired', description = "Feeling tired or having low energy"
WHERE assessment_variable_id = 1130;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health14_tired', description = "Not Bothered At All"
WHERE assessment_variable_id = 1131;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health14_tired', description = "Bothered A Little"
WHERE assessment_variable_id = 1132;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health14_tired', description = "Bothered A Lot"
WHERE assessment_variable_id = 1133;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health15_sleeping', description = "Trouble sleeping"
WHERE assessment_variable_id = 1140;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health15_sleeping', description = "Not Bothered At All"
WHERE assessment_variable_id = 1141;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health15_sleeping', description = "Bothered A Little"
WHERE assessment_variable_id = 1142;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health15_sleeping', description = "Bothered A Lot"
WHERE assessment_variable_id = 1143;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health1_stomach', description = "Stomach pain"
WHERE assessment_variable_id = 1150;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health1_stomach', description = "Not Bothered At All"
WHERE assessment_variable_id = 1151;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health1_stomach', description = "Bothered A Little"
WHERE assessment_variable_id = 1152;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health1_stomach', description = "Bothered A Lot"
WHERE assessment_variable_id = 1153;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health2_back', description = "Back pain"
WHERE assessment_variable_id = 1160;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health2_back', description = "Not Bothered At All"
WHERE assessment_variable_id = 1161;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health2_back', description = "Bothered A Little"
WHERE assessment_variable_id = 1162;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health2_back', description = "Bothered A Lot"
WHERE assessment_variable_id = 1163;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'alc_drinkyr', description = ""
WHERE assessment_variable_id = 1200;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkyr', description = "Never"
WHERE assessment_variable_id = 1201;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkyr', description = "Monthly or less"
WHERE assessment_variable_id = 1202;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkyr', description = "2-4 times per month"
WHERE assessment_variable_id = 1203;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkyr', description = "2-3 times per week"
WHERE assessment_variable_id = 1204;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkyr', description = "4 or more times per week"
WHERE assessment_variable_id = 1205;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'alc_drinkday', description = ""
WHERE assessment_variable_id = 1210;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkday', description = "None"
WHERE assessment_variable_id = 1211;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkday', description = "1-2 drinks"
WHERE assessment_variable_id = 1212;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkday', description = "3-4 drinks"
WHERE assessment_variable_id = 1213;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkday', description = "5-6 drinks"
WHERE assessment_variable_id = 1214;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkday', description = "7-9 drinks"
WHERE assessment_variable_id = 1215;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drinkday', description = "10+"
WHERE assessment_variable_id = 1216;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'alc_drink6', description = ""
WHERE assessment_variable_id = 1220;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drink6', description = "Never"
WHERE assessment_variable_id = 1221;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drink6', description = "Less than monthly"
WHERE assessment_variable_id = 1222;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drink6', description = "Monthly"
WHERE assessment_variable_id = 1223;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drink6', description = "Weekly"
WHERE assessment_variable_id = 1224;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'alc_drink6', description = "Daily"
WHERE assessment_variable_id = 1225;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST1_other', description = "No"
WHERE assessment_variable_id = 1241;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST1_other', description = "Yes"
WHERE assessment_variable_id = 1242;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST2_abuse', description = "No"
WHERE assessment_variable_id = 1251;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST2_abuse', description = "Yes"
WHERE assessment_variable_id = 1252;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST3_ablestop', description = "No"
WHERE assessment_variable_id = 1261;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST3_ablestop', description = "Yes"
WHERE assessment_variable_id = 1262;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST4_blackout', description = "No"
WHERE assessment_variable_id = 1271;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST4_blackout', description = "Yes"
WHERE assessment_variable_id = 1272;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST5_guilty', description = "No"
WHERE assessment_variable_id = 1281;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST5_guilty', description = "Yes"
WHERE assessment_variable_id = 1282;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST6_complain', description = "No"
WHERE assessment_variable_id = 1291;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST6_complain', description = "Yes"
WHERE assessment_variable_id = 1292;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST7_neglect', description = "No"
WHERE assessment_variable_id = 1301;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST7_neglect', description = "Yes"
WHERE assessment_variable_id = 1302;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST8_illegal', description = "No"
WHERE assessment_variable_id = 1311;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST8_illegal', description = "Yes"
WHERE assessment_variable_id = 1312;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST9_withdraw', description = "No"
WHERE assessment_variable_id = 1321;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST9_withdraw', description = "Yes"
WHERE assessment_variable_id = 1322;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST10_medical', description = "No"
WHERE assessment_variable_id = 1331;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'DAST10_medical', description = "Yes"
WHERE assessment_variable_id = 1332;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health18_voices',
  description                   = "Hearing things other people couldn't hear (e.g., voices)"
WHERE assessment_variable_id = 1350;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health18_voices', description = "Not bothered at all"
WHERE assessment_variable_id = 1351;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health18_voices', description = "Bothered a little"
WHERE assessment_variable_id = 1352;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health18_voices', description = "Bothered a lot"
WHERE assessment_variable_id = 1353;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'health19_seeing',
  description                   = "Seeing things or having visions other people couldn't see (e.g., visions)"
WHERE assessment_variable_id = 1360;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health19_seeing', description = "Not bothered at all"
WHERE assessment_variable_id = 1361;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health19_seeing', description = "Bothered a little"
WHERE assessment_variable_id = 1362;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'health19_seeing', description = "Bothered a lot"
WHERE assessment_variable_id = 1363;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_blast_none', description = "Blast injury"
WHERE assessment_variable_id = 1380;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_blast_none', description = "None"
WHERE assessment_variable_id = 1381;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_blast_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1382;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_blast_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1383;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_spine_none', description = "Injury to spine or back"
WHERE assessment_variable_id = 1390;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_spine_none', description = "None"
WHERE assessment_variable_id = 1391;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_spine_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1392;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_spine_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1393;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_burn_none', description = "Burn (second, 3rd degree)"
WHERE assessment_variable_id = 1400;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_burn_none', description = "None"
WHERE assessment_variable_id = 1401;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_burn_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1402;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_burn_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1403;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_nerve_none', description = "Nerve damage"
WHERE assessment_variable_id = 1410;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_nerve_none', description = "None"
WHERE assessment_variable_id = 1411;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_nerve_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1412;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_nerve_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1413;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_vision_none', description = "Loss or damage to vision"
WHERE assessment_variable_id = 1420;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_vision_none', description = "None"
WHERE assessment_variable_id = 1421;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_vision_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1422;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_vision_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1423;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_hearing_none', description = "Loss or damage to hearing"
WHERE assessment_variable_id = 1430;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_hearing_none', description = "None"
WHERE assessment_variable_id = 1431;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_hearing_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1432;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_hearing_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1433;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_amput_none', description = "Amputation"
WHERE assessment_variable_id = 1440;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_amput_none', description = "None"
WHERE assessment_variable_id = 1441;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_amput_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1442;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_amput_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1443;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_bone_none', description = "Broken/fractured bone(s)"
WHERE assessment_variable_id = 1450;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_bone_none', description = "None"
WHERE assessment_variable_id = 1451;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_bone_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1452;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_bone_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1453;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_joint_none', description = "Joint or muscle damage"
WHERE assessment_variable_id = 1460;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_joint_none', description = "None"
WHERE assessment_variable_id = 1461;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_joint_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1462;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_joint_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1463;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_internal_none', description = "Internal or abdominal injuries"
WHERE assessment_variable_id = 1470;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_internal_none', description = "None"
WHERE assessment_variable_id = 1471;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_internal_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1472;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_internal_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1473;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_other1', description = "Other, please specify"
WHERE assessment_variable_id = 1480;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_other1', description = "Other, please specify"
WHERE assessment_variable_id = 1481;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_other1_none', description = "None"
WHERE assessment_variable_id = 1482;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_other1_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1483;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_other1_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1484;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_other2', description = "Other, please specify"
WHERE assessment_variable_id = 1490;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_other2', description = "Other, please specify"
WHERE assessment_variable_id = 1491;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_other2_none', description = "None"
WHERE assessment_variable_id = 1492;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_other2_comb', description = "Yes, During Combat Deployment"
WHERE assessment_variable_id = 1493;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_other2_other',
  description                   = "Yes, During Other Service Period or Training"
WHERE assessment_variable_id = 1494;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_inj_comp',
  description                   = "Did you receive service-connected compensation for an injury?"
WHERE assessment_variable_id = 1510;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_inj_comp', description = "No"
WHERE assessment_variable_id = 1511;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_inj_comp', description = "Yes"
WHERE assessment_variable_id = 1512;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_inj_comp', description = "No, but intend to file/in progress"
WHERE assessment_variable_id = 1513;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'Prior_dx_none',
  description                   = "Over the past 18 months has a Mental Health Provider (i.e. Psychiatrist, Psychologist, Social Worker) diagnosed you with any of the following"
WHERE assessment_variable_id = 1520;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'Prior_dx_none', description = "None"
WHERE assessment_variable_id = 1521;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'prior_dx_dep', description = "Depression"
WHERE assessment_variable_id = 1522;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'prior_dx_ptsd',
  description                   = "Post Traumatic Stress Disorder (PTSD)"
WHERE assessment_variable_id = 1523;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'prior_dx_oth', description = "Other, please specify"
WHERE assessment_variable_id = 1524;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'Prior_tx_none',
  description                   = "In the past 18 months have you had any of the following treatments for your mental health diagnosis?"
WHERE assessment_variable_id = 1530;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'Prior_tx_none', description = "None"
WHERE assessment_variable_id = 1531;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'prior_tx_inpt', description = "Inpatient psychiatric stay"
WHERE assessment_variable_id = 1532;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'prior_tx_thpy', description = "Psychotherapy"
WHERE assessment_variable_id = 1533;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'prior_tx_med', description = "Psychiatric medication"
WHERE assessment_variable_id = 1534;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'prior_tx_ect', description = "Electro convulsive therapy"
WHERE assessment_variable_id = 1535;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'prior_tx_oth', description = "Other, please specify"
WHERE assessment_variable_id = 1536;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep6_feelbad',
  description                   = "Feeling bad about yourself, feeling that you are a failure, or feeling that you have let yourself or your family down"
WHERE assessment_variable_id = 1550;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep6_feelbad', description = "Not at all"
WHERE assessment_variable_id = 1551;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep6_feelbad', description = "Several days"
WHERE assessment_variable_id = 1552;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep6_feelbad', description = "More than half the days"
WHERE assessment_variable_id = 1553;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep6_feelbad', description = "Nearly every day"
WHERE assessment_variable_id = 1554;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep7_concentrate',
  description                   = "Trouble concentrating on things such as reading the newspaper or watching television"
WHERE assessment_variable_id = 1560;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep7_concentrate', description = "Not at all"
WHERE assessment_variable_id = 1561;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep7_concentrate', description = "Several days"
WHERE assessment_variable_id = 1562;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep7_concentrate', description = "More than half the days"
WHERE assessment_variable_id = 1563;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep7_concentrate', description = "Nearly every day"
WHERE assessment_variable_id = 1564;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep8_moveslow',
  description                   = "Moving or speaking so slowly that other people could have noticed. Or being so fidgety or restless that you have been moving around a lot more than usual"
WHERE assessment_variable_id = 1570;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep8_moveslow', description = "Not at all"
WHERE assessment_variable_id = 1571;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep8_moveslow', description = "Several days"
WHERE assessment_variable_id = 1572;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep8_moveslow', description = "More than half the days"
WHERE assessment_variable_id = 1573;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep8_moveslow', description = "Nearly every day"
WHERE assessment_variable_id = 1574;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep9_dead',
  description                   = "Thinking that you would be better off dead or that you want to hurt yourself in some way"
WHERE assessment_variable_id = 1580;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep9_dead', description = "Not at all"
WHERE assessment_variable_id = 1581;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep9_dead', description = "Several days"
WHERE assessment_variable_id = 1582;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep9_dead', description = "More than half the days"
WHERE assessment_variable_id = 1583;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep9_dead', description = "Nearly every day"
WHERE assessment_variable_id = 1584;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep10_difficult',
  description                   = "If you checked off any problem on this questionnaire so far, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people?"
WHERE assessment_variable_id = 1590;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep10_difficult', description = "Not difficult"
WHERE assessment_variable_id = 1591;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep10_difficult', description = "Somewhat difficult"
WHERE assessment_variable_id = 1592;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep10_difficult', description = "Very Difficult"
WHERE assessment_variable_id = 1593;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep10_difficult', description = "Extremely difficult"
WHERE assessment_variable_id = 1594;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'mst3a_childhood', description = "In childhood?"
WHERE assessment_variable_id = 1620;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst3a_childhood', description = "No"
WHERE assessment_variable_id = 1621;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst3a_childhood', description = "Yes"
WHERE assessment_variable_id = 1622;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst3a_childhood', description = "Decline to answer"
WHERE assessment_variable_id = 1623;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'mst3b_adulthood', description = "In adulthood?"
WHERE assessment_variable_id = 1630;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst3b_adulthood', description = "No"
WHERE assessment_variable_id = 1631;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst3b_adulthood', description = "Yes"
WHERE assessment_variable_id = 1632;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst3b_adulthood', description = "Decline to answer"
WHERE assessment_variable_id = 1633;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'Mst_consult',
  description                   = "Would you like a referral to see a VA clinician to discuss issues related to sexual trauma?"
WHERE assessment_variable_id = 1640;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'Mst_consult', description = "No"
WHERE assessment_variable_id = 1641;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'Mst_consult', description = "Yes"
WHERE assessment_variable_id = 1642;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'gad1_nervous', description = "Feeling nervous, anxious or on edge"
WHERE assessment_variable_id = 1660;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad1_nervous', description = "Not at all"
WHERE assessment_variable_id = 1661;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad1_nervous', description = "Several days"
WHERE assessment_variable_id = 1662;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad1_nervous', description = "More than half the days"
WHERE assessment_variable_id = 1663;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad1_nervous', description = "Nearly every day"
WHERE assessment_variable_id = 1664;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'gad2_notable',
  description                   = "Not being able to stop or control worrying"
WHERE assessment_variable_id = 1670;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad2_notable', description = "Not at all"
WHERE assessment_variable_id = 1671;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad2_notable', description = "Several days"
WHERE assessment_variable_id = 1672;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad2_notable', description = "More than half the days"
WHERE assessment_variable_id = 1673;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad2_notable', description = "Nearly every day"
WHERE assessment_variable_id = 1674;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'gad3_worry',
  description                   = "Worrying too much about different things"
WHERE assessment_variable_id = 1680;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad3_worry', description = "Not at all"
WHERE assessment_variable_id = 1681;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad3_worry', description = "Several days"
WHERE assessment_variable_id = 1682;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad3_worry', description = "More than half the days"
WHERE assessment_variable_id = 1683;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad3_worry', description = "Nearly every day"
WHERE assessment_variable_id = 1684;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'gad4_trouble', description = "Trouble relaxing"
WHERE assessment_variable_id = 1690;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad4_trouble', description = "Not at all"
WHERE assessment_variable_id = 1691;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad4_trouble', description = "Several days"
WHERE assessment_variable_id = 1692;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad4_trouble', description = "More than half the days"
WHERE assessment_variable_id = 1693;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad4_trouble', description = "Nearly every day"
WHERE assessment_variable_id = 1694;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'gad5_restless',
  description                   = "Being so restless that it is hard to sit still"
WHERE assessment_variable_id = 1700;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad5_restless', description = "Not at all"
WHERE assessment_variable_id = 1701;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad5_restless', description = "Several days"
WHERE assessment_variable_id = 1702;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad5_restless', description = "More than half the days"
WHERE assessment_variable_id = 1703;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad5_restless', description = "Nearly every day"
WHERE assessment_variable_id = 1704;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'gad6_annoyed', description = "Becoming easily annoyed or irritable"
WHERE assessment_variable_id = 1710;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad6_annoyed', description = "Not at all"
WHERE assessment_variable_id = 1711;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad6_annoyed', description = "Several days"
WHERE assessment_variable_id = 1712;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad6_annoyed', description = "More than half the days"
WHERE assessment_variable_id = 1713;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad6_annoyed', description = "Nearly every day"
WHERE assessment_variable_id = 1714;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'gad7_afraid',
  description                   = "Feeling afraid as if something awful might happen"
WHERE assessment_variable_id = 1720;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad7_afraid', description = "Not at all"
WHERE assessment_variable_id = 1721;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad7_afraid', description = "Several days"
WHERE assessment_variable_id = 1722;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad7_afraid', description = "More than half the days"
WHERE assessment_variable_id = 1723;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad7_afraid', description = "Nearly every day"
WHERE assessment_variable_id = 1724;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'gad8_difficult',
  description                   = "If you checked off any problems, how difficult have these problems made it for you to do your work, take care of things at home, or get along with other people?"
WHERE assessment_variable_id = 1730;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad8_difficult', description = "Not difficult at all"
WHERE assessment_variable_id = 1731;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad8_difficult', description = "Somewhat difficult"
WHERE assessment_variable_id = 1732;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad8_difficult', description = "Very difficult"
WHERE assessment_variable_id = 1733;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'gad8_difficult', description = "Extremely difficult"
WHERE assessment_variable_id = 1734;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl1_memories',
  description                   = "Repeated, disturbing memories, thoughts, or images of a stressful experience from the past?"
WHERE assessment_variable_id = 1750;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl1_memories', description = "Not at all"
WHERE assessment_variable_id = 1751;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl1_memories', description = "A little bit"
WHERE assessment_variable_id = 1752;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl1_memories', description = "Moderately"
WHERE assessment_variable_id = 1753;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl1_memories', description = "Quite a bit"
WHERE assessment_variable_id = 1754;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl1_memories', description = "Extremely"
WHERE assessment_variable_id = 1755;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl2_dreams',
  description                   = "Repeated, disturbing dreams of a stressful experience from the past?"
WHERE assessment_variable_id = 1760;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl2_dreams', description = "Not at all"
WHERE assessment_variable_id = 1761;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl2_dreams', description = "A little bit"
WHERE assessment_variable_id = 1762;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl2_dreams', description = "Moderately"
WHERE assessment_variable_id = 1763;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl2_dreams', description = "Quite a bit"
WHERE assessment_variable_id = 1764;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl2_dreams', description = "Extremely"
WHERE assessment_variable_id = 1765;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl3_acting',
  description                   = "Suddenly acting or feeling as if a stressful experience were happening again (as if you were reliving it)?"
WHERE assessment_variable_id = 1770;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl3_acting', description = "Not at all"
WHERE assessment_variable_id = 1771;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl3_acting', description = "A little bit"
WHERE assessment_variable_id = 1772;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl3_acting', description = "Moderately"
WHERE assessment_variable_id = 1773;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl3_acting', description = "Quite a bit"
WHERE assessment_variable_id = 1774;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl3_acting', description = "Extremely"
WHERE assessment_variable_id = 1775;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl4_remind',
  description                   = "Feeling very upset when something reminded you of a stressful experience from the past?"
WHERE assessment_variable_id = 1780;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl4_remind', description = "Not at all"
WHERE assessment_variable_id = 1781;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl4_remind', description = "A little bit"
WHERE assessment_variable_id = 1782;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl4_remind', description = "Moderately"
WHERE assessment_variable_id = 1783;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl4_remind', description = "Quite a bit"
WHERE assessment_variable_id = 1784;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl4_remind', description = "Extremely"
WHERE assessment_variable_id = 1785;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl5_reaction',
  description                   = "Having physical reactions (e.g., heart pounding, trouble breathing, or sweating) when something reminded you of a stressful experience from the past?"
WHERE assessment_variable_id = 1790;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl5_reaction', description = "Not at all"
WHERE assessment_variable_id = 1791;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl5_reaction', description = "A little bit"
WHERE assessment_variable_id = 1792;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl5_reaction', description = "Moderately"
WHERE assessment_variable_id = 1793;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl5_reaction', description = "Quite a bit"
WHERE assessment_variable_id = 1794;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl5_reaction', description = "Extremely"
WHERE assessment_variable_id = 1795;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl6_think',
  description                   = "Avoid thinking about or talking about  a stressful experience from the past to avoide having feelings related to it?"
WHERE assessment_variable_id = 1800;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl6_think', description = "Not at all"
WHERE assessment_variable_id = 1801;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl6_think', description = "A little bit"
WHERE assessment_variable_id = 1802;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl6_think', description = "Moderately"
WHERE assessment_variable_id = 1803;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl6_think', description = "Quite a bit"
WHERE assessment_variable_id = 1804;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl6_think', description = "Extremely"
WHERE assessment_variable_id = 1805;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl7_activity',
  description                   = "Avoid activities or situations because they remind you of a stressful experience from the past?"
WHERE assessment_variable_id = 1810;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl7_activity', description = "Not at all"
WHERE assessment_variable_id = 1811;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl7_activity', description = "A little bit"
WHERE assessment_variable_id = 1812;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl7_activity', description = "Moderately"
WHERE assessment_variable_id = 1813;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl7_activity', description = "Quite a bit"
WHERE assessment_variable_id = 1814;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl7_activity', description = "Extremely"
WHERE assessment_variable_id = 1815;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl8_trouble',
  description                   = "Trouble remembering important parts of a stressful experience from the past?"
WHERE assessment_variable_id = 1820;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl8_trouble', description = "Not at all"
WHERE assessment_variable_id = 1821;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl8_trouble', description = "A little bit"
WHERE assessment_variable_id = 1822;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl8_trouble', description = "Moderately"
WHERE assessment_variable_id = 1823;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl8_trouble', description = "Quite a bit"
WHERE assessment_variable_id = 1824;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl8_trouble', description = "Extremely"
WHERE assessment_variable_id = 1825;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl9_interest',
  description                   = "A loss of interest in things that you used to enjoy?"
WHERE assessment_variable_id = 1830;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl9_interest', description = "Not at all"
WHERE assessment_variable_id = 1831;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl9_interest', description = "A little bit"
WHERE assessment_variable_id = 1832;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl9_interest', description = "Moderately"
WHERE assessment_variable_id = 1833;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl9_interest', description = "Quite a bit"
WHERE assessment_variable_id = 1834;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl9_interest', description = "Extremely"
WHERE assessment_variable_id = 1835;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl10_distant',
  description                   = "Feeling distant or cut off from other people?"
WHERE assessment_variable_id = 1840;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl10_distant', description = "Not at all"
WHERE assessment_variable_id = 1841;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl10_distant', description = "A little bit"
WHERE assessment_variable_id = 1842;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl10_distant', description = "Moderately"
WHERE assessment_variable_id = 1843;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl10_distant', description = "Quite a bit"
WHERE assessment_variable_id = 1844;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl10_distant', description = "Extremely"
WHERE assessment_variable_id = 1845;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl11_emotion',
  description                   = "Feeling emotionally numb or being unable to have loving feelings for those close to you?"
WHERE assessment_variable_id = 1850;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl11_emotion', description = "Not at all"
WHERE assessment_variable_id = 1851;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl11_emotion', description = "A little bit"
WHERE assessment_variable_id = 1852;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl11_emotion', description = "Moderately"
WHERE assessment_variable_id = 1853;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl11_emotion', description = "Quite a bit"
WHERE assessment_variable_id = 1854;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl11_emotion', description = "Extremely"
WHERE assessment_variable_id = 1855;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl12_future',
  description                   = "Feeling as if your future will somehow be cut short?"
WHERE assessment_variable_id = 1860;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl12_future', description = "Not at all"
WHERE assessment_variable_id = 1861;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl12_future', description = "A little bit"
WHERE assessment_variable_id = 1862;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl12_future', description = "Moderately"
WHERE assessment_variable_id = 1863;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl12_future', description = "Quite a bit"
WHERE assessment_variable_id = 1864;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl12_future', description = "Extremely"
WHERE assessment_variable_id = 1865;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl13_asleep', description = "Trouble falling or staying asleep?"
WHERE assessment_variable_id = 1870;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl13_asleep', description = "Not at all"
WHERE assessment_variable_id = 1871;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl13_asleep', description = "A little bit"
WHERE assessment_variable_id = 1872;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl13_asleep', description = "Moderately"
WHERE assessment_variable_id = 1873;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl13_asleep', description = "Quite a bit"
WHERE assessment_variable_id = 1874;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl13_asleep', description = "Extremely"
WHERE assessment_variable_id = 1875;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl14_irritable',
  description                   = "Feeling irritable or having angry outbursts?"
WHERE assessment_variable_id = 1880;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl14_irritable', description = "Not at all"
WHERE assessment_variable_id = 1881;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl14_irritable', description = "A little bit"
WHERE assessment_variable_id = 1882;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl14_irritable', description = "Moderately"
WHERE assessment_variable_id = 1883;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl14_irritable', description = "Quite a bit"
WHERE assessment_variable_id = 1884;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl14_irritable', description = "Extremely"
WHERE assessment_variable_id = 1885;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl15_concentrate', description = "Difficulty concentrating?"
WHERE assessment_variable_id = 1890;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl15_concentrate', description = "Not at all"
WHERE assessment_variable_id = 1891;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl15_concentrate', description = "A little bit"
WHERE assessment_variable_id = 1892;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl15_concentrate', description = "Moderately"
WHERE assessment_variable_id = 1893;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl15_concentrate', description = "Quite a bit"
WHERE assessment_variable_id = 1894;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl15_concentrate', description = "Extremely"
WHERE assessment_variable_id = 1895;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl16_alert',
  description                   = "Being 'super alert' or watchful, on guard?"
WHERE assessment_variable_id = 1900;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl16_alert', description = "Not at all"
WHERE assessment_variable_id = 1901;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl16_alert', description = "A little bit"
WHERE assessment_variable_id = 1902;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl16_alert', description = "Moderately"
WHERE assessment_variable_id = 1903;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl16_alert', description = "Quite a bit"
WHERE assessment_variable_id = 1904;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl16_alert', description = "Extremely"
WHERE assessment_variable_id = 1905;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcl17_jumpy', description = "Feeling jumpy or easily startled?"
WHERE assessment_variable_id = 1910;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl17_jumpy', description = "Not at all"
WHERE assessment_variable_id = 1911;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl17_jumpy', description = "A little bit"
WHERE assessment_variable_id = 1912;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl17_jumpy', description = "Moderately"
WHERE assessment_variable_id = 1913;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl17_jumpy', description = "Quite a bit"
WHERE assessment_variable_id = 1914;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcl17_jumpy', description = "Extremely"
WHERE assessment_variable_id = 1915;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcptsdA_nightmare',
  description                   = "Have had any nightmares about it or thought about it when you did not want to?"
WHERE assessment_variable_id = 1940;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcptsdA_nightmare', description = "No"
WHERE assessment_variable_id = 1941;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcptsdA_nightmare', description = "Yes"
WHERE assessment_variable_id = 1942;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcptsdB_notthink',
  description                   = "Tried hard not to think about it; went out of your way to avoid situations that remind you of it?"
WHERE assessment_variable_id = 1950;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcptsdB_notthink', description = "No"
WHERE assessment_variable_id = 1951;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcptsdB_notthink', description = "Yes"
WHERE assessment_variable_id = 1952;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcptsdC_onguard',
  description                   = "Were constantly on guard, watchful, or easily startled?"
WHERE assessment_variable_id = 1960;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcptsdC_onguard', description = "No"
WHERE assessment_variable_id = 1961;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcptsdC_onguard', description = "Yes"
WHERE assessment_variable_id = 1962;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pcptsdD_numb',
  description                   = "Felt numb or detached from others, activities, or your surroundings?"
WHERE assessment_variable_id = 1970;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcptsdD_numb', description = "No"
WHERE assessment_variable_id = 1971;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pcptsdD_numb', description = "Yes"
WHERE assessment_variable_id = 1972;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_stable_house',
  description                   = "In the past 2 months, have you been living in stable housing that you own, rent or stay in as part of a household?"
WHERE assessment_variable_id = 2000;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_stable_worry',
  description                   = "Are you worried or concerned that in the next 2 months you may not have stable housing that you own, rent or stay in as part of a household?"
WHERE assessment_variable_id = 2001;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_live_2mos',
  description                   = "Where have you lived for the MOST of the past 2 months?"
WHERE assessment_variable_id = 2002;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'mst1_2',
  description                   = "1. When you were in the military, did you ever receive uninvited or unwanted sexual attention(i.e, touching, cornering, pressure for sexual favors or inappropriate verbal remarks, etc.)? 2. When you were in the military, did anyone ever use force or the threat of force to have sex against your will?"
WHERE assessment_variable_id = 2003;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst1_2', description = "'No' to both questions"
WHERE assessment_variable_id = 2004;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst1_2', description = "'Yes' to one or both questions"
WHERE assessment_variable_id = 2005;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'mst1_2', description = "Decline to answer question regarding MST"
WHERE assessment_variable_id = 2006;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_howreach',
  description                   = "What is the best way to reach you?"
WHERE assessment_variable_id = 2007;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_house_ref',
  description                   = "Would you like to be referred to talk more about your housing situation?"
WHERE assessment_variable_id = 2008;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'infect4a_fragment',
  description                   = "Do you have or suspect you have retained fragments or shrapnel as a result of injuries you received while serving in the area of conflict? For example, were you injured as a result of small arms fire or a blast or explosion caused by an IED, RPG, Landmine, enemy or friendly fire?"
WHERE assessment_variable_id = 2009;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_blast',
  description                   = "Blast or explosion (IED, RPG, Land Mine, Grenade, etc.)"
WHERE assessment_variable_id = 2010;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_vehicle',
  description                   = "Vehicular accident/crash (any vehicle including aircraft)"
WHERE assessment_variable_id = 2011;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_fragment',
  description                   = "Fragment wound or bullet wound above the shoulders"
WHERE assessment_variable_id = 2012;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_fall', description = "Fall"
WHERE assessment_variable_id = 2013;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_blow',
  description                   = "Blow to head (hit by falling/flying object, head hit by another person, head hit against something, etc.)"
WHERE assessment_variable_id = 2014;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_otherinj', description = "Other injury to head"
WHERE assessment_variable_id = 2015;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_none', description = "None"
WHERE assessment_variable_id = 2016;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_immed_loss',
  description                   = "Loss of consciousness/'knocked out'"
WHERE assessment_variable_id = 2017;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_immed_dazed',
  description                   = "Being dazed, confused or 'seeing stars'"
WHERE assessment_variable_id = 2018;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_immed_memory', description = "Not remembering the event"
WHERE assessment_variable_id = 2019;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_immed_concussion', description = "Concussion"
WHERE assessment_variable_id = 2020;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_immed_headinj', description = "Head injury"
WHERE assessment_variable_id = 2021;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_immed_none', description = "None"
WHERE assessment_variable_id = 2022;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_worse_memory', description = "Memory problems or lapses"
WHERE assessment_variable_id = 2023;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_worse_balance', description = "Balance problems or dizziness"
WHERE assessment_variable_id = 2024;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_worse_light', description = "Sensitivity to bright light"
WHERE assessment_variable_id = 2025;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_worse_irritable', description = "Irritability"
WHERE assessment_variable_id = 2027;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_worse_headache', description = "Headaches"
WHERE assessment_variable_id = 2028;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_worse_sleep', description = "Sleep problems"
WHERE assessment_variable_id = 2029;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_worse_none', description = "None"
WHERE assessment_variable_id = 2030;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_week_memory', description = "Memory problems or lapses"
WHERE assessment_variable_id = 2031;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_week_balance', description = "Balance problems or dizziness"
WHERE assessment_variable_id = 2032;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_week_light', description = "Sensitivity to bright light"
WHERE assessment_variable_id = 2033;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_week_irritable', description = "Irritability"
WHERE assessment_variable_id = 2034;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_week_headache', description = "Headaches"
WHERE assessment_variable_id = 2035;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_week_sleep', description = "Sleep problems"
WHERE assessment_variable_id = 2036;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_week_none', description = "None"
WHERE assessment_variable_id = 2037;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tbi_consult',
  description                   = "If you screen positive for possible Traumatic Brain Injury a consult for further assessment will be submitted unless you decline. Do you want a consult placed for more thorough testing?"
WHERE assessment_variable_id = 2047;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'sleep1a_falling', description = "Difficulty falling asleep:"
WHERE assessment_variable_id = 2120;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1a_falling', description = "None"
WHERE assessment_variable_id = 2121;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1a_falling', description = "Mild"
WHERE assessment_variable_id = 2122;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1a_falling', description = "Moderate"
WHERE assessment_variable_id = 2123;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1a_falling', description = "Severe"
WHERE assessment_variable_id = 2124;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1a_falling', description = "Very severe"
WHERE assessment_variable_id = 2125;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'sleep1b_staying', description = "Difficulty staying asleep:"
WHERE assessment_variable_id = 2130;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1b_staying', description = "None"
WHERE assessment_variable_id = 2131;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1b_staying', description = "Mild"
WHERE assessment_variable_id = 2132;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1b_staying', description = "Moderate"
WHERE assessment_variable_id = 2133;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1b_staying', description = "Severe"
WHERE assessment_variable_id = 2134;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1b_staying', description = "Very severe"
WHERE assessment_variable_id = 2135;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'sleep1c_waking', description = "Problem waking up too early:"
WHERE assessment_variable_id = 2140;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1c_waking', description = "None"
WHERE assessment_variable_id = 2141;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1c_waking', description = "Mild"
WHERE assessment_variable_id = 2142;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1c_waking', description = "Moderate"
WHERE assessment_variable_id = 2143;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1c_waking', description = "Severe"
WHERE assessment_variable_id = 2144;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep1c_waking', description = "Very severe"
WHERE assessment_variable_id = 2145;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'sleep2_satisfied',
  description                   = "How <b>SATISFIED/DISSATISFIED</b> are you with your current sleep pattern?"
WHERE assessment_variable_id = 2150;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep2_satisfied', description = "Very satisfied"
WHERE assessment_variable_id = 2151;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep2_satisfied', description = "Very dissatisfied"
WHERE assessment_variable_id = 2152;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'sleep3_interfere', description = ""
WHERE assessment_variable_id = 2160;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep3_interfere', description = "Not at all"
WHERE assessment_variable_id = 2161;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep3_interfere', description = "A little"
WHERE assessment_variable_id = 2162;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep3_interfere', description = "Somewhat"
WHERE assessment_variable_id = 2163;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep3_interfere', description = "Much"
WHERE assessment_variable_id = 2164;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep3_interfere', description = "Very much interfering"
WHERE assessment_variable_id = 2165;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'sleep4_noticeable', description = ""
WHERE assessment_variable_id = 2170;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep4_noticeable', description = "Not noticeable at all"
WHERE assessment_variable_id = 2171;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep4_noticeable', description = "Barely"
WHERE assessment_variable_id = 2172;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep4_noticeable', description = "Somewhat"
WHERE assessment_variable_id = 2173;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep4_noticeable', description = "Much"
WHERE assessment_variable_id = 2174;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep4_noticeable', description = "Very much noticeable"
WHERE assessment_variable_id = 2175;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'sleep5_worried', description = ""
WHERE assessment_variable_id = 2180;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep5_worried', description = "Not at all"
WHERE assessment_variable_id = 2181;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep5_worried', description = "A little"
WHERE assessment_variable_id = 2182;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep5_worried', description = "Somewhat"
WHERE assessment_variable_id = 2183;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep5_worried', description = "Much"
WHERE assessment_variable_id = 2184;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'sleep5_worried', description = "Very much"
WHERE assessment_variable_id = 2185;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_exp_none',
  description                   = "Which of the following do you believe you may have been exposed to?"
WHERE assessment_variable_id = 2200;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_chemical', description = "Chemical agents"
WHERE assessment_variable_id = 2201;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_bio', description = "Biological agents"
WHERE assessment_variable_id = 2202;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_jp8', description = "JP8 or other fuels"
WHERE assessment_variable_id = 2203;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_asbestos', description = "Asbestos"
WHERE assessment_variable_id = 2204;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_nerve', description = "Nerve gas"
WHERE assessment_variable_id = 2205;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_radio', description = "Radiological agents"
WHERE assessment_variable_id = 2206;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_sand', description = "Sand/Dust or Particulate Matter"
WHERE assessment_variable_id = 2207;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_uranium', description = "Depleted uranium"
WHERE assessment_variable_id = 2208;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_industrial', description = "Industrial pollution"
WHERE assessment_variable_id = 2209;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_fumes', description = "Exhaust fumes"
WHERE assessment_variable_id = 2210;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_paint', description = "Paints"
WHERE assessment_variable_id = 2211;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_bite', description = "Animal/insect bites"
WHERE assessment_variable_id = 2212;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_burn', description = "Smoke from burn pits"
WHERE assessment_variable_id = 2213;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_pest', description = "Pesticides"
WHERE assessment_variable_id = 2214;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_other', description = "Other, please specify"
WHERE assessment_variable_id = 2215;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exp_other', description = "Other, please specify"
WHERE assessment_variable_id = 2216;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_animal_bite',
  description                   = "An animal bite that broke the skin?"
WHERE assessment_variable_id = 2230;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_animal_bite', description = "No"
WHERE assessment_variable_id = 2231;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_animal_bite', description = "Yes"
WHERE assessment_variable_id = 2232;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_animal_blood',
  description                   = "Your mouth, eyes or broken skin exposed to the saliva or blood of an animal?"
WHERE assessment_variable_id = 2240;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_animal_blood', description = "No"
WHERE assessment_variable_id = 2241;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_animal_blood', description = "Yes"
WHERE assessment_variable_id = 2242;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_animal_bat', description = "A bat in your sleeping quarters?"
WHERE assessment_variable_id = 2250;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_animal_bat', description = "No"
WHERE assessment_variable_id = 2251;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_animal_bat', description = "Yes"
WHERE assessment_variable_id = 2252;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_comb_none',
  description                   = "Which of the following were you exposed to?"
WHERE assessment_variable_id = 2260;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_attack', description = "Being Attacked or Ambushed"
WHERE assessment_variable_id = 2261;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_fire', description = "Firing Weapons at the Enemy"
WHERE assessment_variable_id = 2262;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_hand', description = "Hand to hand combat"
WHERE assessment_variable_id = 2263;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_wounded', description = "Caring for wounded"
WHERE assessment_variable_id = 2264;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_interro', description = "Interrogation"
WHERE assessment_variable_id = 2265;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_rocket', description = "Receiving rocket or mortar fire"
WHERE assessment_variable_id = 2266;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_seebody', description = "Seeing dead bodies"
WHERE assessment_variable_id = 2267;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_clear', description = "Clearing or searching buildings"
WHERE assessment_variable_id = 2268;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_ship', description = "Firing from a Navy ship"
WHERE assessment_variable_id = 2269;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_detain', description = "Processing/handling detainees"
WHERE assessment_variable_id = 2270;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_recdfire', description = "Receiving small arms fire"
WHERE assessment_variable_id = 2271;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_handbody', description = "Handling human remains"
WHERE assessment_variable_id = 2272;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_killed', description = "Someone killed near you"
WHERE assessment_variable_id = 2273;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_comb_enemy', description = "Caring for enemy wounded"
WHERE assessment_variable_id = 2274;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_number',
  description                   = "Please indicate the number that represents how intense your pain has been over the <b>past 4 weeks</b>."
WHERE assessment_variable_id = 2300;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "0"
WHERE assessment_variable_id = 2301;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "1"
WHERE assessment_variable_id = 2302;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "2"
WHERE assessment_variable_id = 2303;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "3"
WHERE assessment_variable_id = 2304;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "4"
WHERE assessment_variable_id = 2305;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "5"
WHERE assessment_variable_id = 2306;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "6"
WHERE assessment_variable_id = 2307;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "7"
WHERE assessment_variable_id = 2308;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "8"
WHERE assessment_variable_id = 2309;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "9"
WHERE assessment_variable_id = 2310;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_number', description = "10"
WHERE assessment_variable_id = 2311;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'infect1a_gastro', description = "No"
WHERE assessment_variable_id = 2351;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'infect1a_gastro', description = "Yes, what symptoms?"
WHERE assessment_variable_id = 2352;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'infect2a_fever', description = "No"
WHERE assessment_variable_id = 2371;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'infect2a_fever', description = "Yes, what symptoms?"
WHERE assessment_variable_id = 2372;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'infect3a_rash', description = "No"
WHERE assessment_variable_id = 2391;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'infect3a_rash', description = "Yes, what symptoms?"
WHERE assessment_variable_id = 2392;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'infect4a_fragment', description = "No"
WHERE assessment_variable_id = 2401;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'infect4a_fragment', description = "Yes, what symptoms?"
WHERE assessment_variable_id = 2402;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'infect1a_gastro',
  description                   = "Do you have any problems with chronic diarrhea or other gastrointestinal complaints since serving in the area of conflict?"
WHERE assessment_variable_id = 2500;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'infect2a_fever',
  description                   = "Do you have any unexplained fevers?"
WHERE assessment_variable_id = 2501;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'infect3a_rash',
  description                   = "Do you have a persistent papular or nodular skin rash that began after deployment to Southwest Asia?"
WHERE assessment_variable_id = 2502;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_level', description = "What is your level of pain right now?"
WHERE assessment_variable_id = 2550;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_level', description = "No pain"
WHERE assessment_variable_id = 2551;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_level', description = "Mild"
WHERE assessment_variable_id = 2552;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_level', description = "Moderate"
WHERE assessment_variable_id = 2553;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_level', description = "Severe"
WHERE assessment_variable_id = 2554;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_level', description = "Very severe"
WHERE assessment_variable_id = 2555;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_intensity',
  description                   = "How intense was your pain at its worst?"
WHERE assessment_variable_id = 2560;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_intensity', description = "No pain"
WHERE assessment_variable_id = 2561;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_intensity', description = "Mild"
WHERE assessment_variable_id = 2562;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_intensity', description = "Moderate"
WHERE assessment_variable_id = 2563;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_intensity', description = "Severe"
WHERE assessment_variable_id = 2564;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_intensity', description = "Very severe"
WHERE assessment_variable_id = 2565;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_average', description = "How intense was your average pain?"
WHERE assessment_variable_id = 2570;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_average', description = "No pain"
WHERE assessment_variable_id = 2571;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_average', description = "Mild"
WHERE assessment_variable_id = 2572;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_average', description = "Moderate"
WHERE assessment_variable_id = 2573;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_average', description = "Severe"
WHERE assessment_variable_id = 2574;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_average', description = "Very severe"
WHERE assessment_variable_id = 2575;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_interfere_life',
  description                   = "How much did pain interfere with your enjoyment of life?"
WHERE assessment_variable_id = 2580;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_life', description = "Not at all"
WHERE assessment_variable_id = 2581;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_life', description = "A little bit"
WHERE assessment_variable_id = 2582;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_life', description = "Somewhat"
WHERE assessment_variable_id = 2583;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_life', description = "Quite a bit"
WHERE assessment_variable_id = 2584;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_life', description = "Very much"
WHERE assessment_variable_id = 2585;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_interfere_conc',
  description                   = "How much did pain interfere with your ability to concentrate?"
WHERE assessment_variable_id = 2590;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_conc', description = "Not at all"
WHERE assessment_variable_id = 2591;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_conc', description = "A little"
WHERE assessment_variable_id = 2592;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_conc', description = "Somewhat"
WHERE assessment_variable_id = 2593;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_conc', description = "Quite a bit"
WHERE assessment_variable_id = 2594;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_conc', description = "Very much"
WHERE assessment_variable_id = 2595;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_interfere_day',
  description                   = "How much did pain interfere with your day to day activities?"
WHERE assessment_variable_id = 2600;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_day', description = "Not at all"
WHERE assessment_variable_id = 2601;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_day', description = "A little"
WHERE assessment_variable_id = 2602;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_day', description = "Somewhat"
WHERE assessment_variable_id = 2603;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_day', description = "Quite a bit"
WHERE assessment_variable_id = 2604;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_day', description = "Very much"
WHERE assessment_variable_id = 2605;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_interfere_rec',
  description                   = "How much did pain interfere with your enjoyment of recreational activities?"
WHERE assessment_variable_id = 2610;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_rec', description = "Not at all"
WHERE assessment_variable_id = 2611;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_rec', description = "A little"
WHERE assessment_variable_id = 2612;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_rec', description = "Somewhat"
WHERE assessment_variable_id = 2613;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_rec', description = "Quite a bit"
WHERE assessment_variable_id = 2614;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_rec', description = "Very much"
WHERE assessment_variable_id = 2615;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_interfere_task',
  description                   = "How much did pain interfere with doing your tasks away from home (e.g., getting groceries, running errands)?"
WHERE assessment_variable_id = 2620;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_task', description = "Not at all"
WHERE assessment_variable_id = 2621;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_task', description = "A little"
WHERE assessment_variable_id = 2622;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_task', description = "Somewhat"
WHERE assessment_variable_id = 2623;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_task', description = "Quite a bit"
WHERE assessment_variable_id = 2624;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_task', description = "Very much"
WHERE assessment_variable_id = 2625;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_interfere_social',
  description                   = "How often did pain keep you from socializing with others?"
WHERE assessment_variable_id = 2630;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_social', description = "Never"
WHERE assessment_variable_id = 2631;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_social', description = "Rarely"
WHERE assessment_variable_id = 2632;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_social', description = "Sometimes"
WHERE assessment_variable_id = 2633;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_social', description = "Often"
WHERE assessment_variable_id = 2634;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'pain_interfere_social', description = "Always"
WHERE assessment_variable_id = 2635;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1a_good',
  description                   = "You felt so good or hyper that other people thought you were not your normal self or you were so hyper that you got into trouble?"
WHERE assessment_variable_id = 2660;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1a_good', description = "No"
WHERE assessment_variable_id = 2661;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1a_good', description = "Yes"
WHERE assessment_variable_id = 2662;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1b_irritable',
  description                   = "You were so irritable that you shouted at people or started fights or arguments?"
WHERE assessment_variable_id = 2670;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1b_irritable', description = "No"
WHERE assessment_variable_id = 2671;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1b_irritable', description = "Yes"
WHERE assessment_variable_id = 2672;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1c_confident',
  description                   = "You felt much more self-confident than usual?"
WHERE assessment_variable_id = 2680;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1c_confident', description = "No"
WHERE assessment_variable_id = 2681;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1c_confident', description = "Yes"
WHERE assessment_variable_id = 2682;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1d_sleep',
  description                   = "You got much less sleep than usual and found you didn't really miss it?"
WHERE assessment_variable_id = 2690;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1d_sleep', description = "No"
WHERE assessment_variable_id = 2691;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1d_sleep', description = "Yes"
WHERE assessment_variable_id = 2692;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1e_talkative',
  description                   = "You were much more talkative or spoke much faster than usual?"
WHERE assessment_variable_id = 2700;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1e_talkative', description = "No"
WHERE assessment_variable_id = 2701;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1e_talkative', description = "Yes"
WHERE assessment_variable_id = 2702;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1f_thoughts',
  description                   = "Thoughts raced through your head or you couldn't slow your mind down?"
WHERE assessment_variable_id = 2710;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1f_thoughts', description = "No"
WHERE assessment_variable_id = 2711;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1f_thoughts', description = "Yes"
WHERE assessment_variable_id = 2712;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1g_distracted',
  description                   = "You were so easily distracted by things around you that you had trouble concentrating or staying on track?"
WHERE assessment_variable_id = 2720;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1g_distracted', description = "No"
WHERE assessment_variable_id = 2721;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1g_distracted', description = "Yes"
WHERE assessment_variable_id = 2722;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1h_energy', description = "You had much more energy than usual?"
WHERE assessment_variable_id = 2730;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1h_energy', description = "No"
WHERE assessment_variable_id = 2731;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1h_energy', description = "Yes"
WHERE assessment_variable_id = 2732;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1i_active',
  description                   = "You were much more active or did many more things than usual?"
WHERE assessment_variable_id = 2740;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1i_active', description = "No"
WHERE assessment_variable_id = 2741;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1i_active', description = "Yes"
WHERE assessment_variable_id = 2742;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1j_social',
  description                   = "You were much more social or outgoing than usual, for example, you telephoned friends in the middle of the night?"
WHERE assessment_variable_id = 2750;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1j_social', description = "No"
WHERE assessment_variable_id = 2751;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1j_social', description = "Yes"
WHERE assessment_variable_id = 2752;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1k_sex',
  description                   = "You were much more interested in sex than usual?"
WHERE assessment_variable_id = 2760;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1k_sex', description = "No"
WHERE assessment_variable_id = 2761;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1k_sex', description = "Yes"
WHERE assessment_variable_id = 2762;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1l_unusual',
  description                   = "You did things that were unusual for you or that other people might have thought were excessive foolish or risky?"
WHERE assessment_variable_id = 2770;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1l_unusual', description = "No"
WHERE assessment_variable_id = 2771;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1l_unusual', description = "Yes"
WHERE assessment_variable_id = 2772;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp1m_spend',
  description                   = "Spending money got you or your family into trouble?"
WHERE assessment_variable_id = 2780;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1m_spend', description = "No"
WHERE assessment_variable_id = 2781;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp1m_spend', description = "Yes"
WHERE assessment_variable_id = 2782;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp2_time',
  description                   = "If you checked YES to more than one of the above, have several of these happened during the same period of time?"
WHERE assessment_variable_id = 2790;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp2_time', description = "No"
WHERE assessment_variable_id = 2791;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp2_time', description = "Yes"
WHERE assessment_variable_id = 2792;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp3_problem',
  description                   = "How much of a problem did any of these cause you, -- like being unable to work; having family, money or legal troubles; getting into arguments or fights?"
WHERE assessment_variable_id = 2800;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp3_problem', description = "No problem"
WHERE assessment_variable_id = 2801;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp3_problem', description = "Minor problem"
WHERE assessment_variable_id = 2802;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp3_problem', description = "Moderate problem"
WHERE assessment_variable_id = 2803;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'hyp3_problem', description = "Serious problem"
WHERE assessment_variable_id = 2804;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res1_adapt', description = "I am able to adapt when changes occur"
WHERE assessment_variable_id = 2820;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res1_adapt', description = "Not true at all"
WHERE assessment_variable_id = 2821;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res1_adapt', description = "Rarely true"
WHERE assessment_variable_id = 2822;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res1_adapt', description = "Sometimes"
WHERE assessment_variable_id = 2823;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res1_adapt', description = "Often true"
WHERE assessment_variable_id = 2824;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res1_adapt', description = "True nearly all the time"
WHERE assessment_variable_id = 2825;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res2_deal', description = "I can deal with whatever comes my way"
WHERE assessment_variable_id = 2830;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res2_deal', description = "Not true at all"
WHERE assessment_variable_id = 2831;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res2_deal', description = "Rarely true"
WHERE assessment_variable_id = 2832;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res2_deal', description = "Sometimes"
WHERE assessment_variable_id = 2833;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res2_deal', description = "Often true"
WHERE assessment_variable_id = 2834;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res2_deal', description = "True nearly all the time"
WHERE assessment_variable_id = 2835;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res3_humor',
  description                   = "I try to see the humorous side of things when I am faced with problems"
WHERE assessment_variable_id = 2840;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res3_humor', description = "Not true at all"
WHERE assessment_variable_id = 2841;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res3_humor', description = "Rarely true"
WHERE assessment_variable_id = 2842;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res3_humor', description = "Sometimes"
WHERE assessment_variable_id = 2843;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res3_humor', description = "Often true"
WHERE assessment_variable_id = 2844;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res3_humor', description = "True nearly all the time"
WHERE assessment_variable_id = 2845;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res4_cope',
  description                   = "Having to cope with stress can make me stronger"
WHERE assessment_variable_id = 2850;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res4_cope', description = "Not true at all"
WHERE assessment_variable_id = 2851;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res4_cope', description = "Rarely true"
WHERE assessment_variable_id = 2852;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res4_cope', description = "Sometimes"
WHERE assessment_variable_id = 2853;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res4_cope', description = "Often true"
WHERE assessment_variable_id = 2854;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res4_cope', description = "True nearly all the time"
WHERE assessment_variable_id = 2855;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res5_bounce',
  description                   = "I tend to bounce back after an illness, injury, or other hardships"
WHERE assessment_variable_id = 2860;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res5_bounce', description = "Not true at all"
WHERE assessment_variable_id = 2861;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res5_bounce', description = "Rarely true"
WHERE assessment_variable_id = 2862;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res5_bounce', description = "Sometimes"
WHERE assessment_variable_id = 2863;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res5_bounce', description = "Often true"
WHERE assessment_variable_id = 2864;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res5_bounce', description = "True nearly all the time"
WHERE assessment_variable_id = 2865;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res6_goals',
  description                   = "I believe I can achieve my goals, even if there are obstacles"
WHERE assessment_variable_id = 2870;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res6_goals', description = "Not true at all"
WHERE assessment_variable_id = 2871;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res6_goals', description = "Rarely true"
WHERE assessment_variable_id = 2872;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res6_goals', description = "Sometimes"
WHERE assessment_variable_id = 2873;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res6_goals', description = "Often true"
WHERE assessment_variable_id = 2874;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res6_goals', description = "True nearly all the time"
WHERE assessment_variable_id = 2875;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res7_pressure',
  description                   = "Under pressure, I stay focused and think clearly"
WHERE assessment_variable_id = 2880;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res7_pressure', description = "Not true at all"
WHERE assessment_variable_id = 2881;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res7_pressure', description = "Rarely true"
WHERE assessment_variable_id = 2882;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res7_pressure', description = "Sometimes"
WHERE assessment_variable_id = 2883;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res7_pressure', description = "Often true"
WHERE assessment_variable_id = 2884;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res7_pressure', description = "True nearly all the time"
WHERE assessment_variable_id = 2885;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res8_discouraged',
  description                   = "I am not easily discouraged by failure"
WHERE assessment_variable_id = 2890;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res8_discouraged', description = "Not true at all"
WHERE assessment_variable_id = 2891;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res8_discouraged', description = "Rarely true"
WHERE assessment_variable_id = 2892;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res8_discouraged', description = "Sometimes"
WHERE assessment_variable_id = 2893;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res8_discouraged', description = "Often true"
WHERE assessment_variable_id = 2894;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res8_discouraged', description = "True nearly all the time"
WHERE assessment_variable_id = 2895;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res9_strong',
  description                   = "I think of myself as a strong person when dealing with life's challenges and difficulties"
WHERE assessment_variable_id = 2900;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res9_strong', description = "Not true at all"
WHERE assessment_variable_id = 2901;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res9_strong', description = "Rarely true"
WHERE assessment_variable_id = 2902;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res9_strong', description = "Sometimes"
WHERE assessment_variable_id = 2903;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res9_strong', description = "Often true"
WHERE assessment_variable_id = 2904;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res9_strong', description = "True nearly all the time"
WHERE assessment_variable_id = 2905;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'res10_unpleasant',
  description                   = "I am able to handle unpleasant or painful feelings like sadness, fear, or anger"
WHERE assessment_variable_id = 2910;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res10_unpleasant', description = "Not true at all"
WHERE assessment_variable_id = 2911;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res10_unpleasant', description = "Rarely true"
WHERE assessment_variable_id = 2912;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res10_unpleasant', description = "Sometimes"
WHERE assessment_variable_id = 2913;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res10_unpleasant', description = "Often true"
WHERE assessment_variable_id = 2914;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'res10_unpleasant', description = "True nearly all the time"
WHERE assessment_variable_id = 2915;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep1_interest',
  description                   = "Little interest or pleasure in doing things"
WHERE assessment_variable_id = 2960;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep1_interest', description = "Not at all"
WHERE assessment_variable_id = 2961;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep1_interest', description = "Several days"
WHERE assessment_variable_id = 2962;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep1_interest', description = "More than half the days"
WHERE assessment_variable_id = 2963;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep1_interest', description = "Nearly every day"
WHERE assessment_variable_id = 2964;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep2_down', description = "Feeling down, depressed, or hopeless"
WHERE assessment_variable_id = 2970;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep2_down', description = "Not at all"
WHERE assessment_variable_id = 2971;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep2_down', description = "Several days"
WHERE assessment_variable_id = 2972;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep2_down', description = "More than half the days"
WHERE assessment_variable_id = 2973;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep2_down', description = "Nearly every day"
WHERE assessment_variable_id = 2974;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep3_sleep',
  description                   = "Trouble falling asleep, staying asleep, or sleeping too much"
WHERE assessment_variable_id = 2980;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep3_sleep', description = "Not at all"
WHERE assessment_variable_id = 2981;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep3_sleep', description = "Several days"
WHERE assessment_variable_id = 2982;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep3_sleep', description = "More than half the days"
WHERE assessment_variable_id = 2983;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep3_sleep', description = "Nearly every day"
WHERE assessment_variable_id = 2984;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep4_tired', description = "Feeling tired or having little energy"
WHERE assessment_variable_id = 2990;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep4_tired', description = "Not at all"
WHERE assessment_variable_id = 2991;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep4_tired', description = "Several days"
WHERE assessment_variable_id = 2992;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep4_tired', description = "More than half the days"
WHERE assessment_variable_id = 2993;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep4_tired', description = "Nearly every day"
WHERE assessment_variable_id = 2994;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'dep5_appetite', description = "Poor appetite or overeating"
WHERE assessment_variable_id = 3000;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep5_appetite', description = "Not at all"
WHERE assessment_variable_id = 3001;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep5_appetite', description = "Several days"
WHERE assessment_variable_id = 3002;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep5_appetite', description = "More than half the days"
WHERE assessment_variable_id = 3003;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'dep5_appetite', description = "Nearly every day"
WHERE assessment_variable_id = 3004;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'caf_1',
  description                   = "In the past 4 weeks how many servings of caffeinated beverages did you drink per day (e.g., coffee, Red Bull, etc.)?"
WHERE assessment_variable_id = 3050;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'caf_1', description = "None"
WHERE assessment_variable_id = 3051;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'caf_1', description = "1-2"
WHERE assessment_variable_id = 3052;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'caf_1', description = "3-4"
WHERE assessment_variable_id = 3053;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'caf_1', description = "5+"
WHERE assessment_variable_id = 3054;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_type', description = "Type of Service"
WHERE assessment_variable_id = 3070;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_type', description = "Active Duty"
WHERE assessment_variable_id = 3071;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_type', description = "Reserve"
WHERE assessment_variable_id = 3072;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_type', description = "Guard"
WHERE assessment_variable_id = 3073;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_branch', description = "Branch of Service"
WHERE assessment_variable_id = 3080;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_branch', description = "Army"
WHERE assessment_variable_id = 3081;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_branch', description = "Air Force"
WHERE assessment_variable_id = 3082;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_branch', description = "Coast Guard"
WHERE assessment_variable_id = 3083;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_branch', description = "Marines"
WHERE assessment_variable_id = 3084;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_branch', description = "National Guard"
WHERE assessment_variable_id = 3085;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_branch', description = "Navy"
WHERE assessment_variable_id = 3086;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_start', description = "Year Entered Service"
WHERE assessment_variable_id = 3090;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_start', description = "(yyyy)"
WHERE assessment_variable_id = 3091;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_stop', description = "Year Discharged from Service"
WHERE assessment_variable_id = 3100;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_stop', description = "(yyyy)"
WHERE assessment_variable_id = 3101;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_discharge', description = "Type of Discharge"
WHERE assessment_variable_id = 3110;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discharge', description = "Honorable"
WHERE assessment_variable_id = 3111;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discharge', description = "Other than Honorable"
WHERE assessment_variable_id = 3112;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discharge', description = "Dishonorable"
WHERE assessment_variable_id = 3113;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discharge', description = "General w/ Honorable"
WHERE assessment_variable_id = 3114;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discharge', description = "Medical"
WHERE assessment_variable_id = 3115;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discharge', description = "Administrative Separation"
WHERE assessment_variable_id = 3116;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discharge', description = "Retired"
WHERE assessment_variable_id = 3117;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_rank', description = "Rank upon discharge"
WHERE assessment_variable_id = 3120;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e1"
WHERE assessment_variable_id = 3121;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e2"
WHERE assessment_variable_id = 3122;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e3"
WHERE assessment_variable_id = 3123;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e4"
WHERE assessment_variable_id = 3124;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e5"
WHERE assessment_variable_id = 3125;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e6"
WHERE assessment_variable_id = 3126;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e7"
WHERE assessment_variable_id = 3127;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e8"
WHERE assessment_variable_id = 3128;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "e9"
WHERE assessment_variable_id = 3129;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o1"
WHERE assessment_variable_id = 3130;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o2"
WHERE assessment_variable_id = 3131;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o3"
WHERE assessment_variable_id = 3132;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o4"
WHERE assessment_variable_id = 3133;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o5"
WHERE assessment_variable_id = 3134;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o6"
WHERE assessment_variable_id = 3135;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o7"
WHERE assessment_variable_id = 3136;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o8"
WHERE assessment_variable_id = 3137;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o9"
WHERE assessment_variable_id = 3138;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "o10"
WHERE assessment_variable_id = 3139;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_rank', description = "w1"
WHERE assessment_variable_id = 3140;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_job', description = "Job, MOS, or RATE"
WHERE assessment_variable_id = 3150;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_oper_none',
  description                   = "Did you ever serve in any of the following operations or foreign combat zones?"
WHERE assessment_variable_id = 3160;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_none', description = "None"
WHERE assessment_variable_id = 3161;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_OEF', description = "Operation Enduring Freedom (OEF)"
WHERE assessment_variable_id = 3162;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_OIF', description = "Operation Iraqi Freedom (OIF)"
WHERE assessment_variable_id = 3163;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_gwot',
  description                   = "Global War on Terror operations other than OEF/OIF (e.g. Noble Eagle, Vigilant Mariner)"
WHERE assessment_variable_id = 3164;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_ond', description = "Operation New Dawn"
WHERE assessment_variable_id = 3165;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_caribbean',
  description                   = "Caribbean / South America / Central America"
WHERE assessment_variable_id = 3166;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_gulf', description = "Gulf War (1991)"
WHERE assessment_variable_id = 3167;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_somalia', description = "Somalia"
WHERE assessment_variable_id = 3168;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_bosnia', description = "Bosnia"
WHERE assessment_variable_id = 3169;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_kosovo', description = "Kosovo"
WHERE assessment_variable_id = 3170;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_djibouti', description = "Djibouti"
WHERE assessment_variable_id = 3171;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_libya', description = "Libya"
WHERE assessment_variable_id = 3172;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'Serv_oper_vietnam', description = "Vietnam"
WHERE assessment_variable_id = 3173;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_korea', description = "Korean War"
WHERE assessment_variable_id = 3174;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_other', description = "Other, please specify"
WHERE assessment_variable_id = 3175;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_oper_other', description = "Other, please specify"
WHERE assessment_variable_id = 3176;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang1_noise',
  description                   = "Make loud noises, scream, shout angrily"
WHERE assessment_variable_id = 3200;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang1_noise', description = "Never"
WHERE assessment_variable_id = 3201;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang1_noise', description = "Sometimes"
WHERE assessment_variable_id = 3202;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang1_noise', description = "Often"
WHERE assessment_variable_id = 3203;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang1_noise', description = "Usually"
WHERE assessment_variable_id = 3204;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang1_noise', description = "Always"
WHERE assessment_variable_id = 3205;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang2_yell', description = "Yell mild personal assaults at others"
WHERE assessment_variable_id = 3210;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang2_yell', description = "Never"
WHERE assessment_variable_id = 3211;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang2_yell', description = "Sometimes"
WHERE assessment_variable_id = 3212;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang2_yell', description = "Often"
WHERE assessment_variable_id = 3213;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang2_yell', description = "Usually"
WHERE assessment_variable_id = 3214;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang2_yell', description = "Always"
WHERE assessment_variable_id = 3215;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang3_curse',
  description                   = "Curse, use foul language, make vague threats to hurt myself or others"
WHERE assessment_variable_id = 3220;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang3_curse', description = "Never"
WHERE assessment_variable_id = 3221;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang3_curse', description = "Sometimes"
WHERE assessment_variable_id = 3222;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang3_curse', description = "Often"
WHERE assessment_variable_id = 3223;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang3_curse', description = "Usually"
WHERE assessment_variable_id = 3224;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang3_curse', description = "Always"
WHERE assessment_variable_id = 3225;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang4_threat',
  description                   = "Make clear threats of violence towards others or myself, or ask for help from others to control myself"
WHERE assessment_variable_id = 3230;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang4_threat', description = "Never"
WHERE assessment_variable_id = 3231;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang4_threat', description = "Sometimes"
WHERE assessment_variable_id = 3232;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang4_threat', description = "Often"
WHERE assessment_variable_id = 3233;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang4_threat', description = "Usually"
WHERE assessment_variable_id = 3234;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang4_threat', description = "Always"
WHERE assessment_variable_id = 3235;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang5_slamdoor',
  description                   = "Slam doors, make a mess, scatter clothing"
WHERE assessment_variable_id = 3240;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang5_slamdoor', description = "Never"
WHERE assessment_variable_id = 3241;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang5_slamdoor', description = "Sometimes"
WHERE assessment_variable_id = 3242;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang5_slamdoor', description = "Often"
WHERE assessment_variable_id = 3243;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang5_slamdoor', description = "Usually"
WHERE assessment_variable_id = 3244;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang5_slamdoor', description = "Always"
WHERE assessment_variable_id = 3245;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang6_throw',
  description                   = "Throw objects down, kick furniture, without breaking it, mark the walls"
WHERE assessment_variable_id = 3250;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang6_throw', description = "Never"
WHERE assessment_variable_id = 3251;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang6_throw', description = "Sometimes"
WHERE assessment_variable_id = 3252;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang6_throw', description = "Often"
WHERE assessment_variable_id = 3253;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang6_throw', description = "Usually"
WHERE assessment_variable_id = 3254;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang6_throw', description = "Always"
WHERE assessment_variable_id = 3255;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang7_break', description = "Break objects, smash windows"
WHERE assessment_variable_id = 3260;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang7_break', description = "Never"
WHERE assessment_variable_id = 3261;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang7_break', description = "Sometimes"
WHERE assessment_variable_id = 3262;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang7_break', description = "Often"
WHERE assessment_variable_id = 3263;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang7_break', description = "Usually"
WHERE assessment_variable_id = 3264;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang7_break', description = "Always"
WHERE assessment_variable_id = 3265;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang8_setfire',
  description                   = "Set fires, thrown objects dangerously"
WHERE assessment_variable_id = 3270;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang8_setfire', description = "Never"
WHERE assessment_variable_id = 3271;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang8_setfire', description = "Sometimes"
WHERE assessment_variable_id = 3272;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang8_setfire', description = "Often"
WHERE assessment_variable_id = 3273;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang8_setfire', description = "Usually"
WHERE assessment_variable_id = 3274;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang8_setfire', description = "Always"
WHERE assessment_variable_id = 3275;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang9_gesture',
  description                   = "Make threatening gestures, swing at people, grab at clothes of others"
WHERE assessment_variable_id = 3280;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang9_gesture', description = "Never"
WHERE assessment_variable_id = 3281;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang9_gesture', description = "Sometimes"
WHERE assessment_variable_id = 3282;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang9_gesture', description = "Often"
WHERE assessment_variable_id = 3283;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang9_gesture', description = "Usually"
WHERE assessment_variable_id = 3284;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang9_gesture', description = "Always"
WHERE assessment_variable_id = 3285;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang10_strike',
  description                   = "Strike, kick, push, pull hair (others)"
WHERE assessment_variable_id = 3290;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang10_strike', description = "Never"
WHERE assessment_variable_id = 3291;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang10_strike', description = "Sometimes"
WHERE assessment_variable_id = 3292;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang10_strike', description = "Often"
WHERE assessment_variable_id = 3293;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang10_strike', description = "Usually"
WHERE assessment_variable_id = 3294;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang10_strike', description = "Always"
WHERE assessment_variable_id = 3295;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang11_injury',
  description                   = "Attack others causing physical injury (bruises, sprain, welts)"
WHERE assessment_variable_id = 3300;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang11_injury', description = "Never"
WHERE assessment_variable_id = 3301;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang11_injury', description = "Sometimes"
WHERE assessment_variable_id = 3302;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang11_injury', description = "Often"
WHERE assessment_variable_id = 3303;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang11_injury', description = "Usually"
WHERE assessment_variable_id = 3304;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang11_injury', description = "Always"
WHERE assessment_variable_id = 3305;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang12_severeinjury',
  description                   = "Attack other causing severe physical injury (broken bones, deep cuts)"
WHERE assessment_variable_id = 3310;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang12_severeinjury', description = "Never"
WHERE assessment_variable_id = 3311;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang12_severeinjury', description = "Sometimes"
WHERE assessment_variable_id = 3312;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang12_severeinjury', description = "Often"
WHERE assessment_variable_id = 3313;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang12_severeinjury', description = "Usually"
WHERE assessment_variable_id = 3314;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang12_severeinjury', description = "Always"
WHERE assessment_variable_id = 3315;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang13_pick',
  description                   = "Pick or scratch my skin, hit myself on arms and body, pinch myself, pull my own hair"
WHERE assessment_variable_id = 3320;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang13_pick', description = "Never"
WHERE assessment_variable_id = 3321;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang13_pick', description = "Sometimes"
WHERE assessment_variable_id = 3322;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang13_pick', description = "Often"
WHERE assessment_variable_id = 3323;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang13_pick', description = "Usually"
WHERE assessment_variable_id = 3324;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang13_pick', description = "Always"
WHERE assessment_variable_id = 3325;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang14_banghead',
  description                   = "Bang my head, use my fist to hit objects, throw myself onto the floor or into others"
WHERE assessment_variable_id = 3330;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang14_banghead', description = "Never"
WHERE assessment_variable_id = 3331;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang14_banghead', description = "Sometimes"
WHERE assessment_variable_id = 3332;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang14_banghead', description = "Often"
WHERE assessment_variable_id = 3333;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang14_banghead', description = "Usually"
WHERE assessment_variable_id = 3334;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang14_banghead', description = "Always"
WHERE assessment_variable_id = 3335;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang15_cut',
  description                   = "Cut, bruise, or cause minor burns to myself"
WHERE assessment_variable_id = 3340;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang15_cut', description = "Never"
WHERE assessment_variable_id = 3341;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang15_cut', description = "Sometimes"
WHERE assessment_variable_id = 3342;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang15_cut', description = "Often"
WHERE assessment_variable_id = 3343;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang15_cut', description = "Usually"
WHERE assessment_variable_id = 3344;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang15_cut', description = "Always"
WHERE assessment_variable_id = 3345;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'ang16_hurt',
  description                   = "Hurt myself (e.g., make deep cuts, bites that bleed or other ways that result in internal injury, fracture or loss of consciousness, loss of teeth)"
WHERE assessment_variable_id = 3350;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang16_hurt', description = "Never"
WHERE assessment_variable_id = 3351;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang16_hurt', description = "Sometimes"
WHERE assessment_variable_id = 3352;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang16_hurt', description = "Often"
WHERE assessment_variable_id = 3353;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang16_hurt', description = "Usually"
WHERE assessment_variable_id = 3354;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'ang16_hurt', description = "Always"
WHERE assessment_variable_id = 3355;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tbi_blast',
  description                   = "During any of your OEF/OIF deployments did you experience any of the following events?"
WHERE assessment_variable_id = 3400;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tbi_immed_loss',
  description                   = "Did you have any of these symptoms IMMEDIATELY afterwards?"
WHERE assessment_variable_id = 3410;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tbi_worse_memory',
  description                   = "Did any of the following problems begin or get worse afterwards?"
WHERE assessment_variable_id = 3420;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tbi_week_memory',
  description                   = "In the past 1 week have you had any of the problems from the question above?"
WHERE assessment_variable_id = 3430;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_consult', description = "No, I decline a consult at this time"
WHERE assessment_variable_id = 3441;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'tbi_consult', description = "Yes, place a consult"
WHERE assessment_variable_id = 3442;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'TBI_consult_where',
  description                   = "Where were you deployed when the head injury occurred?"
WHERE assessment_variable_id = 3450;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'TBI_consult_when', description = "What year did it occur?"
WHERE assessment_variable_id = 3460;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'TBI_consult_how', description = "How did the head injury occur?"
WHERE assessment_variable_id = 3470;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'med', description = "Medication/Supplement"
WHERE assessment_variable_id = 3510;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'med_dose', description = "Dosage"
WHERE assessment_variable_id = 3520;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'med_freq', description = "Frequency"
WHERE assessment_variable_id = 3530;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'med_dur', description = "Duration"
WHERE assessment_variable_id = 3540;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'med_doc', description = "Doctor and/or Facility"
WHERE assessment_variable_id = 3550;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas1_1_concentrate',
  description                   = "Concentrating on doing something for ten minutes?"
WHERE assessment_variable_id = 4000;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_1_concentrate', description = "None"
WHERE assessment_variable_id = 4001;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_1_concentrate', description = "Mild"
WHERE assessment_variable_id = 4002;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_1_concentrate', description = "Moderate"
WHERE assessment_variable_id = 4003;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_1_concentrate', description = "Severe"
WHERE assessment_variable_id = 4004;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_1_concentrate', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4005;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas1_2_remember',
  description                   = "Remembering to do important things?"
WHERE assessment_variable_id = 4020;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_2_remember', description = "None"
WHERE assessment_variable_id = 4021;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_2_remember', description = "Mild"
WHERE assessment_variable_id = 4022;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_2_remember', description = "Moderate"
WHERE assessment_variable_id = 4023;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_2_remember', description = "Severe"
WHERE assessment_variable_id = 4024;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_2_remember', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4025;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas1_3_solution',
  description                   = "Analyzing and finding solutions to problems in day-to-day life?"
WHERE assessment_variable_id = 4040;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_3_solution', description = "None"
WHERE assessment_variable_id = 4041;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_3_solution', description = "Mild"
WHERE assessment_variable_id = 4042;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_3_solution', description = "Moderate"
WHERE assessment_variable_id = 4043;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_3_solution', description = "Severe"
WHERE assessment_variable_id = 4044;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_3_solution', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4045;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas1_4_new',
  description                   = "Learning a new task, for example, learning how to get to a new place?"
WHERE assessment_variable_id = 4060;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_4_new', description = "None"
WHERE assessment_variable_id = 4061;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_4_new', description = "Mild"
WHERE assessment_variable_id = 4062;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_4_new', description = "Moderate"
WHERE assessment_variable_id = 4063;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_4_new', description = "Severe"
WHERE assessment_variable_id = 4064;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_4_new', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4065;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas1_5_understand',
  description                   = "Generally understanding what people say?"
WHERE assessment_variable_id = 4080;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_5_understand', description = "None"
WHERE assessment_variable_id = 4081;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_5_understand', description = "Mild"
WHERE assessment_variable_id = 4082;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_5_understand', description = "Moderate"
WHERE assessment_variable_id = 4083;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_5_understand', description = "Severe"
WHERE assessment_variable_id = 4084;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_5_understand', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4085;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas1_6_conversation',
  description                   = "Starting and maintaining a conversation?"
WHERE assessment_variable_id = 4100;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_6_conversation', description = "None"
WHERE assessment_variable_id = 4101;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_6_conversation', description = "Mild"
WHERE assessment_variable_id = 4102;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_6_conversation', description = "Moderate"
WHERE assessment_variable_id = 4103;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_6_conversation', description = "Severe"
WHERE assessment_variable_id = 4104;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas1_6_conversation', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4105;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas2_1_stand',
  description                   = "Standing for long periods, such as 30 minutes?"
WHERE assessment_variable_id = 4120;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_1_stand', description = "None"
WHERE assessment_variable_id = 4121;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_1_stand', description = "Mild"
WHERE assessment_variable_id = 4122;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_1_stand', description = "Moderate"
WHERE assessment_variable_id = 4123;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_1_stand', description = "Severe"
WHERE assessment_variable_id = 4124;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_1_stand', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4125;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas2_2_standup', description = "Standing up from sitting down?"
WHERE assessment_variable_id = 4140;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_2_standup', description = "None"
WHERE assessment_variable_id = 4141;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_2_standup', description = "Mild"
WHERE assessment_variable_id = 4142;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_2_standup', description = "Moderate"
WHERE assessment_variable_id = 4143;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_2_standup', description = "Severe"
WHERE assessment_variable_id = 4144;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_2_standup', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4145;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas2_3_move', description = "Moving around inside your home?"
WHERE assessment_variable_id = 4160;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_3_move', description = "None"
WHERE assessment_variable_id = 4161;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_3_move', description = "Mild"
WHERE assessment_variable_id = 4162;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_3_move', description = "Moderate"
WHERE assessment_variable_id = 4163;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_3_move', description = "Severe"
WHERE assessment_variable_id = 4164;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_3_move', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4165;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas2_4_getout', description = "Getting out of your home?"
WHERE assessment_variable_id = 4180;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_4_getout', description = "None"
WHERE assessment_variable_id = 4181;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_4_getout', description = "Mild"
WHERE assessment_variable_id = 4182;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_4_getout', description = "Moderate"
WHERE assessment_variable_id = 4183;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_4_getout', description = "Severe"
WHERE assessment_variable_id = 4184;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_4_getout', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4185;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas_work',
  description                   = "Do you work (paid, non-paid, self-employed) or go to school?"
WHERE assessment_variable_id = 4200;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas_work', description = "No"
WHERE assessment_variable_id = 4201;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas_work', description = "Yes"
WHERE assessment_variable_id = 4202;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas2_5_walk',
  description                   = "Walking a long distance, such as a kilometer (or equivalent)?"
WHERE assessment_variable_id = 4220;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_5_walk', description = "None"
WHERE assessment_variable_id = 4221;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_5_walk', description = "Mild"
WHERE assessment_variable_id = 4222;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_5_walk', description = "Moderate"
WHERE assessment_variable_id = 4223;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_5_walk', description = "Severe"
WHERE assessment_variable_id = 4224;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas2_5_walk', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4225;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas3_1_wash', description = "Washing your whole body?"
WHERE assessment_variable_id = 4240;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_1_wash', description = "None"
WHERE assessment_variable_id = 4241;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_1_wash', description = "Mild"
WHERE assessment_variable_id = 4242;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_1_wash', description = "Moderate"
WHERE assessment_variable_id = 4243;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_1_wash', description = "Severe"
WHERE assessment_variable_id = 4244;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_1_wash', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4245;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas3_2_dressed', description = "Getting dressed?"
WHERE assessment_variable_id = 4260;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_2_dressed', description = "None"
WHERE assessment_variable_id = 4261;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_2_dressed', description = "Mild"
WHERE assessment_variable_id = 4262;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_2_dressed', description = "Moderate"
WHERE assessment_variable_id = 4263;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_2_dressed', description = "Severe"
WHERE assessment_variable_id = 4264;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_2_dressed', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4265;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas3_3_eat', description = "Eating?"
WHERE assessment_variable_id = 4280;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_3_eat', description = "None"
WHERE assessment_variable_id = 4281;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_3_eat', description = "Mild"
WHERE assessment_variable_id = 4282;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_3_eat', description = "Moderate"
WHERE assessment_variable_id = 4283;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_3_eat', description = "Severe"
WHERE assessment_variable_id = 4284;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_3_eat', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4285;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas3_4_stay',
  description                   = "Staying by yourself for a few days?"
WHERE assessment_variable_id = 4300;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_4_stay', description = "None"
WHERE assessment_variable_id = 4301;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_4_stay', description = "Mild"
WHERE assessment_variable_id = 4302;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_4_stay', description = "Moderate"
WHERE assessment_variable_id = 4303;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_4_stay', description = "Severe"
WHERE assessment_variable_id = 4304;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas3_4_stay', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4305;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas4_1_deal',
  description                   = "Dealing with people you do not know?"
WHERE assessment_variable_id = 4320;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_1_deal', description = "None"
WHERE assessment_variable_id = 4321;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_1_deal', description = "Mild"
WHERE assessment_variable_id = 4322;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_1_deal', description = "Moderate"
WHERE assessment_variable_id = 4323;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_1_deal', description = "Severe"
WHERE assessment_variable_id = 4324;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_1_deal', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4325;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas4_2_friend', description = "Maintaining a friendship?"
WHERE assessment_variable_id = 4340;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_2_friend', description = "None"
WHERE assessment_variable_id = 4341;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_2_friend', description = "Mild"
WHERE assessment_variable_id = 4342;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_2_friend', description = "Moderate"
WHERE assessment_variable_id = 4343;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_2_friend', description = "Severe"
WHERE assessment_variable_id = 4344;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_2_friend', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4345;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas4_3_getalong',
  description                   = "Getting along with people who are close to you?"
WHERE assessment_variable_id = 4360;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_3_getalong', description = "None"
WHERE assessment_variable_id = 4361;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_3_getalong', description = "Mild"
WHERE assessment_variable_id = 4362;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_3_getalong', description = "Moderate"
WHERE assessment_variable_id = 4363;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_3_getalong', description = "Severe"
WHERE assessment_variable_id = 4364;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_3_getalong', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4365;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas4_4_newfriend', description = "Making new friends?"
WHERE assessment_variable_id = 4380;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_4_newfriend', description = "None"
WHERE assessment_variable_id = 4381;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_4_newfriend', description = "Mild"
WHERE assessment_variable_id = 4382;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_4_newfriend', description = "Moderate"
WHERE assessment_variable_id = 4383;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_4_newfriend', description = "Severe"
WHERE assessment_variable_id = 4384;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_4_newfriend', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4385;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas4_5_sexual', description = "Sexual activities?"
WHERE assessment_variable_id = 4400;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_5_sexual', description = "None"
WHERE assessment_variable_id = 4401;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_5_sexual', description = "Mild"
WHERE assessment_variable_id = 4402;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_5_sexual', description = "Moderate"
WHERE assessment_variable_id = 4403;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_5_sexual', description = "Severe"
WHERE assessment_variable_id = 4404;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas4_5_sexual', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4405;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas5_1_housecare',
  description                   = "Taking care of your household responsibilities?"
WHERE assessment_variable_id = 4420;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_1_housecare', description = "None"
WHERE assessment_variable_id = 4421;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_1_housecare', description = "Mild"
WHERE assessment_variable_id = 4422;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_1_housecare', description = "Moderate"
WHERE assessment_variable_id = 4423;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_1_housecare', description = "Severe"
WHERE assessment_variable_id = 4424;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_1_housecare', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4425;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whoda5_2_housetask',
  description                   = "Doing most important household tasks well?"
WHERE assessment_variable_id = 4430;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whoda5_2_housetask', description = "None"
WHERE assessment_variable_id = 4431;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whoda5_2_housetask', description = "Mild"
WHERE assessment_variable_id = 4432;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whoda5_2_housetask', description = "Moderate"
WHERE assessment_variable_id = 4433;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whoda5_2_housetask', description = "Severe"
WHERE assessment_variable_id = 4434;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whoda5_2_housetask', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4435;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas5_3_housedone',
  description                   = "Getting all of the household work done that you needed to do?"
WHERE assessment_variable_id = 4440;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_3_housedone', description = "None"
WHERE assessment_variable_id = 4441;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_3_housedone', description = "Mild"
WHERE assessment_variable_id = 4442;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_3_housedone', description = "Moderate"
WHERE assessment_variable_id = 4443;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_3_housedone', description = "Severe"
WHERE assessment_variable_id = 4444;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_3_housedone', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4445;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas5_4_housequickly',
  description                   = "Getting your household work done as quickly as needed?"
WHERE assessment_variable_id = 4460;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_4_housequickly', description = "None"
WHERE assessment_variable_id = 4461;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_4_housequickly', description = "Mild"
WHERE assessment_variable_id = 4462;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_4_housequickly', description = "Moderate"
WHERE assessment_variable_id = 4463;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_4_housequickly', description = "Severe"
WHERE assessment_variable_id = 4464;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_4_housequickly', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4465;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas5_5_daily', description = "Your day-to-day work/school?"
WHERE assessment_variable_id = 4480;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_5_daily', description = "None"
WHERE assessment_variable_id = 4481;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_5_daily', description = "Mild"
WHERE assessment_variable_id = 4482;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_5_daily', description = "Moderate"
WHERE assessment_variable_id = 4483;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_5_daily', description = "Severe"
WHERE assessment_variable_id = 4484;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_5_daily', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4485;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas5_6_workwell',
  description                   = "Doing your most important work/school tasks well?"
WHERE assessment_variable_id = 4500;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_6_workwell', description = "None"
WHERE assessment_variable_id = 4501;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_6_workwell', description = "Mild"
WHERE assessment_variable_id = 4502;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_6_workwell', description = "Moderate"
WHERE assessment_variable_id = 4503;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_6_workwell', description = "Severe"
WHERE assessment_variable_id = 4504;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_6_workwell', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4505;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas5_7_workdone',
  description                   = "Getting all of the work done that you need to do?"
WHERE assessment_variable_id = 4520;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_7_workdone', description = "None"
WHERE assessment_variable_id = 4521;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_7_workdone', description = "Mild"
WHERE assessment_variable_id = 4522;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_7_workdone', description = "Moderate"
WHERE assessment_variable_id = 4523;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_7_workdone', description = "Severe"
WHERE assessment_variable_id = 4524;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_7_workdone', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4525;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas5_8_workquickly',
  description                   = "Getting your work done as quickly as needed?"
WHERE assessment_variable_id = 4540;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_8_workquickly', description = "None"
WHERE assessment_variable_id = 4541;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_8_workquickly', description = "Mild"
WHERE assessment_variable_id = 4542;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_8_workquickly', description = "Moderate"
WHERE assessment_variable_id = 4543;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_8_workquickly', description = "Severe"
WHERE assessment_variable_id = 4544;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas5_8_workquickly', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4545;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas6_1_community',
  description                   = "How much of a problem did you have in joining in community activities (for example, festivities, religious, or other activities) in the same way as anyone else can?"
WHERE assessment_variable_id = 4560;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_1_community', description = "None"
WHERE assessment_variable_id = 4561;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_1_community', description = "Mild"
WHERE assessment_variable_id = 4562;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_1_community', description = "Moderate"
WHERE assessment_variable_id = 4563;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_1_community', description = "Severe"
WHERE assessment_variable_id = 4564;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_1_community', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4565;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas6_2_barriers',
  description                   = "How much of a problem did you have because of barriers or hindrances around you?"
WHERE assessment_variable_id = 4580;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_2_barriers', description = "None"
WHERE assessment_variable_id = 4581;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_2_barriers', description = "Mild"
WHERE assessment_variable_id = 4582;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_2_barriers', description = "Moderate"
WHERE assessment_variable_id = 4583;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_2_barriers', description = "Severe"
WHERE assessment_variable_id = 4584;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_2_barriers', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4585;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas6_3_dignity',
  description                   = "How much of a problem did you have living with dignity because of the attitudes and actions of others?"
WHERE assessment_variable_id = 4600;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_3_dignity', description = "None"
WHERE assessment_variable_id = 4601;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_3_dignity', description = "Mild"
WHERE assessment_variable_id = 4602;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_3_dignity', description = "Moderate"
WHERE assessment_variable_id = 4603;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_3_dignity', description = "Severe"
WHERE assessment_variable_id = 4604;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_3_dignity', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4605;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas6_4_time',
  description                   = "How much time did you spend on your health condition or its consequences?"
WHERE assessment_variable_id = 4620;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_4_time', description = "None"
WHERE assessment_variable_id = 4621;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_4_time', description = "Mild"
WHERE assessment_variable_id = 4622;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_4_time', description = "Moderate"
WHERE assessment_variable_id = 4623;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_4_time', description = "Severe"
WHERE assessment_variable_id = 4624;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_4_time', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4625;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas6_5_emotion',
  description                   = "How much have you been emotionally affected by your health condition?"
WHERE assessment_variable_id = 4640;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_5_emotion', description = "None"
WHERE assessment_variable_id = 4641;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_5_emotion', description = "Mild"
WHERE assessment_variable_id = 4642;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_5_emotion', description = "Moderate"
WHERE assessment_variable_id = 4643;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_5_emotion', description = "Severe"
WHERE assessment_variable_id = 4644;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_5_emotion', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4645;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas6_6_finance',
  description                   = "How much has your health been a drain on the financial resources of you or your family?"
WHERE assessment_variable_id = 4660;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_6_finance', description = "None"
WHERE assessment_variable_id = 4661;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_6_finance', description = "Mild"
WHERE assessment_variable_id = 4662;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_6_finance', description = "Moderate"
WHERE assessment_variable_id = 4663;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_6_finance', description = "Severe"
WHERE assessment_variable_id = 4664;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_6_finance', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4665;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas6_7_family',
  description                   = "How much of a problem did your family have because of your health problems?"
WHERE assessment_variable_id = 4680;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_7_family', description = "None"
WHERE assessment_variable_id = 4681;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_7_family', description = "Mild"
WHERE assessment_variable_id = 4682;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_7_family', description = "Moderate"
WHERE assessment_variable_id = 4683;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_7_family', description = "Severe"
WHERE assessment_variable_id = 4684;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_7_family', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4685;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'whodas6_8_relax',
  description                   = "How much of a problem did you have in doing things by yourself for relaxation or pleasure?"
WHERE assessment_variable_id = 4700;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_8_relax', description = "None"
WHERE assessment_variable_id = 4701;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_8_relax', description = "Mild"
WHERE assessment_variable_id = 4702;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_8_relax', description = "Moderate"
WHERE assessment_variable_id = 4703;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_8_relax', description = "Severe"
WHERE assessment_variable_id = 4704;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'whodas6_8_relax', description = "Extreme or cannot do"
WHERE assessment_variable_id = 4705;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_discipline_none',
  description                   = "Did you receive any of the following disciplinary actions during your time in the service?"
WHERE assessment_variable_id = 5000;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discipline_none', description = "None"
WHERE assessment_variable_id = 5001;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discipline_couns',
  description                   = "Formal Counseling (e.g. Captain's Mast)"
WHERE assessment_variable_id = 5002;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discipline_art15',
  description                   = "Article 15 (Non-Judicial Punishment)"
WHERE assessment_variable_id = 5003;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discipline_martial', description = "Court Martial"
WHERE assessment_variable_id = 5004;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_discipline_other', description = "Other, please specify"
WHERE assessment_variable_id = 5005;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_award_none',
  description                   = "Did you receive any of the following personal awards or commendations?"
WHERE assessment_variable_id = 5020;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_none', description = "None"
WHERE assessment_variable_id = 5021;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_honor', description = "Medal of Honor"
WHERE assessment_variable_id = 5022;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_distservcross',
  description                   = "Distinguished Service Cross"
WHERE assessment_variable_id = 5023;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_distservmedal',
  description                   = "Distinguished Service Medal"
WHERE assessment_variable_id = 5024;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_meritservmedal',
  description                   = "Meritorious Service Medal"
WHERE assessment_variable_id = 5025;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_legionmerit', description = "Legion of Merit"
WHERE assessment_variable_id = 5026;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_bronzstar', description = "Bronze Star"
WHERE assessment_variable_id = 5027;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_purpheart', description = "Purple Heart"
WHERE assessment_variable_id = 5028;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_airmedal', description = "Air Medal"
WHERE assessment_variable_id = 5029;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_silvstar', description = "Silver Star"
WHERE assessment_variable_id = 5030;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_soldiermedal', description = "Soldier's Medal"
WHERE assessment_variable_id = 5031;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_achievmedal', description = "Achievement Medal"
WHERE assessment_variable_id = 5032;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_commedal', description = "Commendation Medal"
WHERE assessment_variable_id = 5033;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_award_other', description = "Other, please specify"
WHERE assessment_variable_id = 5034;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_deploy_loc', description = "Combat deployment location"
WHERE assessment_variable_id = 5040;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_deploy_from_mo',
  description                   = "Start of combat deployment month"
WHERE assessment_variable_id = 5060;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "01"
WHERE assessment_variable_id = 5061;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "02"
WHERE assessment_variable_id = 5062;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "03"
WHERE assessment_variable_id = 5063;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "04"
WHERE assessment_variable_id = 5064;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "05"
WHERE assessment_variable_id = 5065;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "06"
WHERE assessment_variable_id = 5066;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "07"
WHERE assessment_variable_id = 5067;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "08"
WHERE assessment_variable_id = 5068;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "09"
WHERE assessment_variable_id = 5069;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "10"
WHERE assessment_variable_id = 5070;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "11"
WHERE assessment_variable_id = 5071;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_from_mo', description = "12"
WHERE assessment_variable_id = 5072;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_deploy_from_yr',
  description                   = "Start of combat deployment year (yyyy)"
WHERE assessment_variable_id = 5080;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_deploy_to_mo', description = "End of combat deployment month"
WHERE assessment_variable_id = 5100;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "01"
WHERE assessment_variable_id = 5101;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "02"
WHERE assessment_variable_id = 5102;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "03"
WHERE assessment_variable_id = 5103;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "04"
WHERE assessment_variable_id = 5104;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "05"
WHERE assessment_variable_id = 5105;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "06"
WHERE assessment_variable_id = 5106;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "07"
WHERE assessment_variable_id = 5107;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "08"
WHERE assessment_variable_id = 5108;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "09"
WHERE assessment_variable_id = 5109;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "10"
WHERE assessment_variable_id = 5110;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "11"
WHERE assessment_variable_id = 5111;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_deploy_to_mo', description = "12"
WHERE assessment_variable_id = 5112;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_deploy_to_yr',
  description                   = "End of combat deployment year (yyyy)"
WHERE assessment_variable_id = 5120;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_exposed',
  description                   = "Do you have any persistent major concerns regarding the effects of something you believe you may have been exposed to or encountered while deployed? "
WHERE assessment_variable_id = 10540;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exposed', description = "No"
WHERE assessment_variable_id = 10541;
UPDATE assessment_variable
SET assessment_variable_type_id = 2, display_name = 'serv_exposed', description = "Yes"
WHERE assessment_variable_id = 10542;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_hours',
  description                   = "How many hours per week are you currently employed?"
WHERE assessment_variable_id = 10810;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_oth_spec', description = "Comment:"
WHERE assessment_variable_id = 10811;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_homenogov_spec', description = "Comment:"
WHERE assessment_variable_id = 10812;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_homewgov_spec', description = "Comment:"
WHERE assessment_variable_id = 10813;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_friendfam_spec', description = "Comment:"
WHERE assessment_variable_id = 10814;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_hotel_spec', description = "Comment:"
WHERE assessment_variable_id = 10815;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_shortins_spec', description = "Comment:"
WHERE assessment_variable_id = 10816;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_shel_spec', description = "Comment:"
WHERE assessment_variable_id = 10817;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'HomelessCR_out_spec', description = "Comment:"
WHERE assessment_variable_id = 10818;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_info_other', description = "Other"
WHERE assessment_variable_id = 10819;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'demo_income_group',
  description                   = "What was the total combined income of all members of this family in the past 12 months? Please include money from jobs, net income from business, farm or rent, pensions, dividends, welfare, social security payments and any other monetary income received by you or any other family member."
WHERE assessment_variable_id = 10821;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'serv_combat',
  description                   = "Did your military experience include exposure to combat?"
WHERE assessment_variable_id = 10823;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'pain_area', description = "Place where you feel your pain"
WHERE assessment_variable_id = 10830;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tob_quit_month', description = "Month"
WHERE assessment_variable_id = 10839;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tob_quit_date', description = "Year"
WHERE assessment_variable_id = 10840;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tob_cigg_quant_length',
  description                   = "How many cigarettes do you smoke, and for how many years?"
WHERE assessment_variable_id = 10841;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tob_pipe_quant_length',
  description                   = "How many cigars/pipes do you smoke, and for how many years?"
WHERE assessment_variable_id = 10842;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'tob_chew_quant_length',
  description                   = "How much smokeless tobacco do you use, and for how many years?"
WHERE assessment_variable_id = 10843;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp4_relative',
  description                   = "Have any of your blood relatives (i.e., children, siblings, parents, grandparents, aunts, uncles) had manic-depressive illness or bipolar disorder?"
WHERE assessment_variable_id = 10852;
UPDATE assessment_variable
SET assessment_variable_type_id = 1, display_name = 'hyp5_disorder',
  description                   = "Has a health professional ever told you that you have manic-depressive illness or bipolar disorder?"
WHERE assessment_variable_id = 10853;
