CREATE TABLE groups
(
    id integer generated always as identity,
    name varchar(7) not null,
    department_id smallint not null,

    CONSTRAINT PK__groups__key PRIMARY KEY(id),
    CONSTRAINT FK__groups__department FOREIGN KEY(department_id) REFERENCES departments(id)
);

ALTER TABLE student_users
ADD COLUMN group_id integer,
ADD CONSTRAINT FK__student_users__group FOREIGN KEY(group_id) REFERENCES groups(id);

WITH inserted_group AS (
    INSERT INTO groups(name, department_id)
    SELECT DISTINCT s.group_name, s.department_id
    FROM student_users s
    WHERE NOT EXISTS (
        SELECT 1
        FROM groups g
        WHERE g.name = s.group_name
          AND g.department_id = s.department_id
    )
    RETURNING id, name, department_id
)
UPDATE student_users
SET group_id = (
    SELECT id FROM inserted_group
              WHERE student_users.group_name = inserted_group.name
              AND student_users.department_id = inserted_group.department_id
);

ALTER TABLE student_users
DROP COLUMN group_name,
DROP COLUMN department_id;

ALTER TABLE student_users
ALTER COLUMN group_id SET NOT NULL;