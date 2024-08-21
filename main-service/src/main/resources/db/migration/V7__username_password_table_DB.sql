CREATE TABLE username_password_users
(
    user_id integer,
    username varchar(48) unique not null,
    password varchar(64) not null,

    CONSTRAINT PK__username_password_users__key PRIMARY KEY(user_id),
    CONSTRAINT FK__username_password_users__user FOREIGN KEY(user_id) REFERENCES users(id)
);

ALTER TABLE users
DROP COLUMN username,
DROP COLUMN password;

DROP TABLE admins;

CREATE TABLE admins
(
    user_id int,
    type_id smallint not null,

    CONSTRAINT PK__admins__key PRIMARY KEY(user_id),
    CONSTRAINT FK__admins__type FOREIGN KEY(type_id) REFERENCES admin_types(id),
    CONSTRAINT FK__admins__user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);