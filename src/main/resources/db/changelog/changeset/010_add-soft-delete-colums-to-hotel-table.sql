-- liquibase formatted sql

-- changeset kanaoki:1748859172057-1
ALTER TABLE hotel ADD deleted_at TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE hotel ADD is_deleted BOOLEAN DEFAULT FALSE;