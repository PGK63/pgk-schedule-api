CREATE TABLE api_token_types
(
    id smallint generated always as identity,
    type varchar(24) not null,

    CONSTRAINT PK__api_token_types__key PRIMARY KEY(id)
);

CREATE TABLE api_tokens
(
    id int generated always as identity,
    token varchar(64) not null,
    type_int smallint not null,
    date_create date not null,

    CONSTRAINT PK__users__api_tokens PRIMARY KEY(id),
    CONSTRAINT FK__users__type_id FOREIGN KEY(id) REFERENCES api_token_types(id)
);

CREATE TABLE users
(
    id int generated always as identity,
    telegram_id bigint default null,
    alice_id varchar(64) default null,

    CONSTRAINT PK__users__key PRIMARY KEY(id)
);

CREATE TABLE departments
(
    id smallint generated always as identity,
    name varchar(64) unique not null,

    CONSTRAINT PK__departments__key PRIMARY KEY(id)
);

CREATE TABLE students
(
    user_id int,
    group_name varchar(7) not null,
    department_id smallint not null,

    CONSTRAINT PK__students__key PRIMARY KEY(user_id),
    CONSTRAINT FK__students__user_id FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT FK__students__department_id  FOREIGN KEY(department_id) REFERENCES departments(id)
);

CREATE TABLE teachers
(
    user_id int,
    first_name varchar(48) not null,
    last_name varchar(48) not null,
    cabinet varchar(5) default null,
    department_id smallint not null,

    CONSTRAINT PK__teachers__key PRIMARY KEY(user_id),
    CONSTRAINT FK__teachers__user_id FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT FK__teachers__department_id  FOREIGN KEY(department_id) REFERENCES departments(id)
);