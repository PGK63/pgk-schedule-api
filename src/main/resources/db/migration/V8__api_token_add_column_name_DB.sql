ALTER TABLE api_tokens ADD COLUMN name varchar(48);

UPDATE api_tokens SET name = 'unknown' WHERE name IS NULL;

ALTER TABLE api_tokens ALTER COLUMN name SET NOT NULL;
