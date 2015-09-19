ALTER TABLE clinic
DROP FOREIGN KEY fk_clinic_program;
ALTER TABLE clinic
DROP COLUMN program_id,
DROP INDEX idx_fk_clinic_program;

-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (1, '64', 'AUDIOLOGY');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (2, '195', 'CARDIOLOGY');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (3, '137', 'COMP AND PEN');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (4, '246', 'CWT CLINIC');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (5, '228', 'DENTAL');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (6, '62', 'DERMATOLOGY');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (7, '285', 'DIABETIC');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (8, '191', 'DIABETIC TELERET READER LOCAL');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (9, '193', 'DIABETIC TELERET READER REMOTE');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (10, '190', 'DIABETIC TELERETINAL IMAGER');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (11, '133', 'EMPLOYEE HEALTH');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (12, '422', 'ENDOCRINE');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (13, '23', 'GENERAL MEDICINE');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (14, '298', 'GENERAL SURGERY');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (15, '229', 'HEMATOLOGY');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (16, '128', 'MAMMOGRAM');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (17, '17', 'MENTAL HYGIENE');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (18, '26', 'MENTAL HYGIENE-OPC');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (19, '114', 'NUCLEAR MEDICINE');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (20, '234', 'OBSERVATION');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (21, '127', 'PLASTIC SURGERY');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (22, '233', 'PODIATRY');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (23, '32', 'PRIMARY CARE');
-- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (24, '31', 'SOCIAL WORK');
-- -- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (25, 5, '239', 'SURGICAL CLINIC');
-- -- INSERT INTO clinic (clinic_id, vista_ien, name) VALUES (26, 5, '168', 'ULTRASOUND');
--
-- INSERT program (program_id, name) VALUES (1, 'OEF/OIF');
-- INSERT program (program_id, name) VALUES (2, 'Mental Health');
-- INSERT program (program_id, name) VALUES (3, 'Primary Care');
-- INSERT program (program_id, name) VALUES (4, 'OOO');
-- INSERT program (program_id, name) VALUES (5, 'Aspire');

CREATE TABLE clinic_program (
  clinic_program_id INT NOT NULL AUTO_INCREMENT,
  clinic_id         INT NOT NULL,
  program_id        INT NOT NULL,
  PRIMARY KEY (clinic_program_id)
);

ALTER TABLE clinic_program ADD CONSTRAINT fk_clnc_prgm_clnc
FOREIGN KEY (clinic_id) REFERENCES clinic (clinic_id);
ALTER TABLE clinic_program ADD CONSTRAINT fk_clnc_prgm_prgm
FOREIGN KEY (program_id) REFERENCES program (program_id);

CREATE INDEX idx_fk_clnc_prgm_clnc ON clinic_program (clinic_id ASC);
CREATE INDEX idx_fk_clnc_prgm_prgm ON clinic_program (program_id ASC);


-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (1, 1, 1);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (2, 2, 1);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (3, 3, 1);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (4, 4, 1);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (5, 5, 1);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (6, 6, 2);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (7, 7, 2);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (8, 8, 2);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (9, 9, 2);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (10, 10, 2);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (11, 11, 3);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (12, 12, 3);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (13, 13, 3);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (14, 14, 3);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (15, 15, 3);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (16, 16, 4);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (17, 17, 4);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (18, 18, 4);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (19, 19, 4);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (20, 20, 4);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (21, 21, 5);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (22, 22, 5);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (23, 23, 5);
-- INSERT INTO clinic_program (clinic_program_id, clinic_id, program_id) VALUES (24, 24, 5);
