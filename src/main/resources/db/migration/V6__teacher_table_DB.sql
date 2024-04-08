ALTER TABLE teachers ADD COLUMN middle_name varchar(48) default null;

ALTER TABLE teachers DROP CONSTRAINT IF EXISTS FK__teachers__user_id;

ALTER TABLE teachers
ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY;

ALTER TABLE teachers
    RENAME COLUMN user_id TO id;

CREATE TABLE teacher_users
(
    user_id integer,
    teacher_id integer not null,

    CONSTRAINT PK__teacher_users__key PRIMARY KEY(user_id),
    CONSTRAINT FK__teacher_users__user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT FK__teacher_users__teacher FOREIGN KEY(teacher_id) REFERENCES teachers(id)
);

INSERT INTO teacher_users (user_id, teacher_id)
SELECT id, id FROM teachers;

ALTER TABLE students RENAME TO student_users;
