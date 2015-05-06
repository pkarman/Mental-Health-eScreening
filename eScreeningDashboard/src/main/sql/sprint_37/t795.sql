ALTER TABLE survey ADD COLUMN is_published BOOLEAN NOT NULL DEFAULT 0;

UPDATE survey set is_published=1;