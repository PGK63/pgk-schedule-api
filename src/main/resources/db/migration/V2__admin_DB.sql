CREATE TABLE admin_types
(
    id smallint generated always as identity,
    type varchar(32) unique not null,

    CONSTRAINT PK__admin_types__key PRIMARY KEY(id)
);

CREATE TABLE admins
(
    id int generated always as identity,
    username varchar(32) unique not null,
    password varchar(64) not null,
    type_id smallint not null,

    CONSTRAINT PK__admins__key PRIMARY KEY(id),
    CONSTRAINT FK__admins__type FOREIGN KEY(type_id) REFERENCES admin_types(id)
);