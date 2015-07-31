-- from t757 --
drop table export_log_data;

ALTER TABLE export_log ADD COLUMN export_zip BLOB NOT NULL AFTER date_created;

CREATE TABLE export_log_audit (
  export_log_audit_id INT(11) NOT NULL AUTO_INCREMENT,
  comment VARCHAR(100) NOT NULL,
  exported_by_user_id INT(11) NOT NULL,
  export_log_id INT(11) NOT NULL,
  date_updated datetime NOT NULL,
  PRIMARY KEY (export_log_audit_id),
  INDEX fk_export_log_audit_id_idx (export_log_audit_id ASC),
  INDEX fk_export_log_id_idx (export_log_id ASC),
  CONSTRAINT fk_export_log_id FOREIGN KEY (export_log_id) REFERENCES export_log (export_log_id) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_exported_by_user_id FOREIGN KEY (exported_by_user_id) REFERENCES user (user_id) ON DELETE RESTRICT ON UPDATE RESTRICT
);