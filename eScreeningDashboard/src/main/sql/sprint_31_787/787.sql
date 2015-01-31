
ALTER TABLE veteran ADD hash VARCHAR(255) NOT NULL DEFAULT '-';;

ALTER TABLE veteran ADD CONSTRAINT UNIQUE KEY ux_veteran_hash (hash);;


-- to make sure we are unique over these fields (even if null): last_name, ssn_last_four, birth_date, middle_name
CREATE TRIGGER veteran_hash_before_insert BEFORE INSERT ON veteran
FOR EACH ROW
BEGIN
    SET NEW.hash = CONCAT(LOWER(NEW.last_name), '%->', NEW.ssn_last_four, '%->', IFNULL(NEW.birth_date, '{[DATE]}'), '%->', IFNULL(LOWER(NEW.middle_name), '{[MIDDLE]}'));
END;;
CREATE TRIGGER veteran_hash_Before_Update BEFORE UPDATE ON veteran
FOR EACH ROW
BEGIN
    IF NEW.last_name != OLD.last_name OR NEW.ssn_last_four != OLD.ssn_last_four OR NEW.birth_date != OLD.birth_date OR NEW.middle_name != OLD.middle_name THEN 
        SET NEW.hash = CONCAT(LOWER(NEW.last_name), '%->', NEW.ssn_last_four, '%->', IFNULL(NEW.birth_date, '{[DATE]}'), '%->', IFNULL(LOWER(NEW.middle_name), '{[MIDDLE]}'));
    END IF;
END;;