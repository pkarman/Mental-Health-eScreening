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

insert into clinic_program (clinic_id, program_id) (select clinic_id, program_id from clinic);

ALTER TABLE clinic
DROP FOREIGN KEY fk_clinic_program;
ALTER TABLE clinic
DROP COLUMN program_id,
DROP INDEX idx_fk_clinic_program;
