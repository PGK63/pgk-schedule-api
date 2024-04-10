CREATE TABLE teachers_departments
(
    teacher_id integer,
    department_id smallint,

    CONSTRAINT PK__teachers_departments__key PRIMARY KEY(teacher_id, department_id),
    CONSTRAINT FK__teachers_departments__teacher FOREIGN KEY(teacher_id) REFERENCES teachers(id),
    CONSTRAINT FK__teachers_departments__department FOREIGN KEY(department_id) REFERENCES departments(id)
);

INSERT INTO teachers_departments(teacher_id, department_id)
SELECT id, department_id FROM teachers;

ALTER TABLE teachers
DROP COLUMN department_id;