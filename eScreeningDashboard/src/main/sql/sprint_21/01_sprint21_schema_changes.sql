alter table veteran add column is_sensitive BOOLEAN NOT NULL DEFAULT 0;

alter table template add column is_graphical BOOLEAN NOT NULL DEFAULT 0;