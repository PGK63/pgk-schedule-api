CREATE TABLE telegram_users
(
    user_id integer,
    telegram_id bigint not null,

    CONSTRAINT PK__telegram_users__key PRIMARY KEY(user_id),
    CONSTRAINT FK__telegram_users__user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO telegram_users (user_id, telegram_id)
SELECT id, telegram_id FROM users WHERE telegram_id IS NOT NULL;

ALTER TABLE users
DROP COLUMN telegram_id;