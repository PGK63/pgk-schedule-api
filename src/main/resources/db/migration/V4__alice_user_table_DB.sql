CREATE TABLE alice_users
(
    user_id INTEGER PRIMARY KEY,
    alice_id VARCHAR(64) NOT NULL UNIQUE,

    CONSTRAINT FK__alice_users__key FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

INSERT INTO alice_users (user_id, alice_id)
SELECT id, alice_id FROM users WHERE alice_id IS NOT NULL;

ALTER TABLE users
DROP COLUMN alice_id;