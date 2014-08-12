/* Start user_id = 2 since we already have a Default Sys Admin account created */

/* Insert super user account */
INSERT INTO user(user_id,role_id,user_status_id,login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2, cprs_verified)
VALUES(2,1,1,'evaladmin','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','One','M','EvalAdminOne','some@nowher.oro','some@nowher.oro','1234567890','1234567890', 0);

/* Insert clinical admin user account 
INSERT INTO user(user_id,role_id,user_status_id,login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2, cprs_verified)
VALUES(3,2,1,'clinicaladmin','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','One','M','ClinicalAdminOne','some@nowher.oro','some@nowher.oro','1234567890','1234567890', 0);
*/

/* Insert tech admin user account */
INSERT INTO user(user_id,role_id,user_status_id,login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2, cprs_verified)
VALUES(4,3,1,'techadmin','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','One','M','TechAdminOne','some@nowher.oro','some@nowher.oro','1234567890','1234567890', 1);

INSERT INTO user_program (user_id, program_id) VALUES (4, 1);
INSERT INTO user_program (user_id, program_id) VALUES (4, 2);
INSERT INTO user_program (user_id, program_id) VALUES (4, 3);
INSERT INTO user_program (user_id, program_id) VALUES (4, 4);
INSERT INTO user_program (user_id, program_id) VALUES (4, 5);


/* insert clinician accounts */
INSERT INTO user(user_id,role_id,user_status_id,login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2, cprs_verified)
VALUES(5,4,1,'1pharmacist','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','One','M','1pharmacist','some@nowher.oro','some@nowher.oro','1234567890','1234567890', 1);

INSERT INTO user_program (user_id, program_id) VALUES (5, 1);
INSERT INTO user_program (user_id, program_id) VALUES (5, 2);
INSERT INTO user_program (user_id, program_id) VALUES (5, 3);
INSERT INTO user_program (user_id, program_id) VALUES (5, 4);
INSERT INTO user_program (user_id, program_id) VALUES (5, 5);

INSERT INTO user(user_id,role_id,user_status_id,login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2, cprs_verified)
VALUES(6,4,1,'1radiologist','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','One','M','1radiologist','some@nowher.oro','some@nowher.oro','1234567890','1234567890', 0);

INSERT INTO user_program (user_id, program_id) VALUES (6, 1);
INSERT INTO user_program (user_id, program_id) VALUES (6, 2);
INSERT INTO user_program (user_id, program_id) VALUES (6, 3);
INSERT INTO user_program (user_id, program_id) VALUES (6, 4);
INSERT INTO user_program (user_id, program_id) VALUES (6, 5);

INSERT INTO user(user_id,role_id,user_status_id,login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2, cprs_verified)
VALUES(8,5,1,'assistant','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8','One','M','Assistant','some@nowher.oro','some@nowher.oro','1234567890','1234567890', 0);

/* insert deactivated users of each type */

INSERT INTO user(login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2,user_status_id,role_id, cprs_verified) 
VALUES ('deactivatedevaladmin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'One', 'One', 'EvalAdmin', 'evaladminone@nowhere.com', 'evaladminone@nowhere.com', '0123456789', '0123456789', 2, 1, 0);
/*
INSERT INTO user(login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2,user_status_id,role_id, cprs_verified) 
VALUES ('deactivatedclinicaladmin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'One', 'One', 'ClincialAdmin', 'clincialadmin@nowhere.com', 'clincialadmin@nowhere.com', '0123456789', '0123456789', 2, 2, 0);
*/
INSERT INTO user(login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2,user_status_id,role_id, cprs_verified) 
VALUES ('deactivatedtechadmin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'One', 'One', 'TechnicalAdmin', 'technicaladmin@nowhere.com', 'techncialadmin@nowhere.com', '0123456789', '0123456789', 2, 3, 0);
INSERT INTO user(login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2,user_status_id,role_id, cprs_verified) 
VALUES ('deactivatedassistant', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'Pablo', 'Laik', 'Palani', 'ppalani1@test.com', 'ppalani2@test.com', '2342342323', '2342342323', 2, 5, 0);

/* insert deleted users of each type */
INSERT INTO user(login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2,user_status_id,role_id, cprs_verified) 
VALUES ('deletedevaladmin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'One', 'One', 'EvalAdmin', 'evaladminone@nowhere.com', 'evaladminone@nowhere.com', '0123456789', '0123456789', 3, 1, 0);
/*
INSERT INTO user(login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2,user_status_id,role_id, cprs_verified) 
VALUES ('deletedclinicaladmin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'One', 'One', 'ClincialAdmin', 'clincialadmin@nowhere.com', 'clincialadmin@nowhere.com', '0123456789', '0123456789', 3, 2, 0);
*/
INSERT INTO user(login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2,user_status_id,role_id, cprs_verified) 
VALUES ('deletedtechadmin', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'One', 'One', 'TechnicalAdmin', 'technicaladmin@nowhere.com', 'techncialadmin@nowhere.com', '0123456789', '0123456789', 3, 3, 0);
INSERT INTO user(login_id,password,first_name,middle_name,last_name,email_address,email_address2,phone_number,phone_number2,user_status_id,role_id, cprs_verified) 
VALUES ('deletedassistant', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8', 'Pablo', 'Laik', 'Palani',  'ppalani1@test.com', 'ppalani2@test.com', '2342342323', '2342342323', 3, 5, 0);

/* To provide capability to communicate with VistA */
UPDATE user SET vista_division = '500', vista_duz = '10000000056' WHERE user_id < 100;
