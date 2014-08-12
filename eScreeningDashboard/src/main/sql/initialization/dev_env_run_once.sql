/* This script requires global privilates and only needs to be run once per mysql install*/

/* add logging */
SET GLOBAL general_log = 'ON';

/* create database user */
CREATE USER 'escrapp'@'localhost' IDENTIFIED BY 'CHANGE_M3';

CREATE DATABASE IF NOT EXISTS `escreening`;
CREATE DATABASE IF NOT EXISTS `escreening-test`;
CREATE DATABASE IF NOT EXISTS `escreening-demo`;
CREATE DATABASE IF NOT EXISTS `escreening-int`;

/* These next lines eases our dev deployments but should not be used in production */
GRANT ALL ON escreening.* TO 'escrapp'@'localhost';
GRANT ALL ON `escreening-test`.*  TO 'escrapp'@'localhost';
GRANT ALL ON `escreening-demo`.* TO 'escrapp'@'localhost';
GRANT ALL ON `escreening-int`.* TO 'escrapp'@'localhost';
