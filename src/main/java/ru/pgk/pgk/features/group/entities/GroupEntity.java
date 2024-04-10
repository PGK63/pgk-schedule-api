package ru.pgk.pgk.features.group.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.student.entities.StudentEntity;

import java.util.Collection;

@Data
@Entity(name = "groups")
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private DepartmentEntity department;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Collection<StudentEntity> students;
}
