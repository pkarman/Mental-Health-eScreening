-- from #541 - removing fields that are not needed
ALTER TABLE battery DROP COLUMN complete_message, DROP COLUMN welcome_message;