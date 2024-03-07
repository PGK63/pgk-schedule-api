package ru.pgk.pgk.features.department.entitites;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.teacher.entities.TeacherEntity;

import java.io.Serializable;
import java.util.Collection;

@Data
@Entity(name = "departments")
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Collection<StudentEntity> students;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Collection<TeacherEntity> teachers;
}
