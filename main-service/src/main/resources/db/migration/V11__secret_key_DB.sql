CREATE TABLE secret_key_types
(
    id smallint generated always as identity,
    value varchar(16) unique not null,

    CONSTRAINT PK__secret_key_types__key PRIMARY KEY(id)
);

INSERT INTO secret_key_types (value) VALUES ('ALICE_LOGIN');

CREATE TABLE secret_keys
(
    user_id integer,
    type_id smallint not null,
    key varchar(6) unique not null,
    date timestamp not null default current_date,

    CONSTRAINT PK__secret_keys__key PRIMARY KEY(user_id, type_id),
    CONSTRAINT FK__secret_keys__user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT FK__secret_keys__type FOREIGN KEY(type_id) REFERENCES secret_key_types(id)
);