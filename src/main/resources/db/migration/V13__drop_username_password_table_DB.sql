ALTER TABLE admins
ADD COLUMN username VARCHAR(48) DEFAULT NOT NULL,
ADD COLUMN password VARCHAR(64) DEFAULT NULL;

UPDATE admins a
SET username = u.username,
    password = u.password FROM username_password_users u
WHERE a.user_id = u.user_id;

ALTER TABLE admins
ALTER COLUMN username SET NOT NULL,
ALTER COLUMN password SET NOT NULL;

DROP TABLE username_password_users;
