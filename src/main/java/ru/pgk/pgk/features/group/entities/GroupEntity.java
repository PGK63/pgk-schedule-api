package ru.pgk.pgk.features.group.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.student.entities.StudentEntity;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Entity(name = "groups")
public class GroupEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private DepartmentEntity department;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Collection<StudentEntity> students;
}
