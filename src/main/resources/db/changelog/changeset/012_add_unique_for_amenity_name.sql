-- liquibase formatted sql

-- changeset aqua:1749761326391-1
ALTER TABLE amenity ADD CONSTRAINT uc_amenity_name UNIQUE (name);

