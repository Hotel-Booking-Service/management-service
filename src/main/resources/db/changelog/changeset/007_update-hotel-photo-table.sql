-- liquibase formatted sql

-- changeset kanaoki:1748348440547-1
ALTER TABLE hotel_photo RENAME COLUMN url TO s3key;

-- changeset kanaoki:1748348440547-2
ALTER TABLE hotel_photo ALTER COLUMN s3key SET NOT NULL;

-- changeset kanaoki:1748348440547-3
ALTER TABLE hotel_photo ADD CONSTRAINT uc_hotelphoto_s3key UNIQUE (s3key);