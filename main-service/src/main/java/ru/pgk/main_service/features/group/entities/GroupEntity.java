package ru.pgk.main_service.features.group.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;
import ru.pgk.main_service.features.student.entities.StudentEntity;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "groups")
public class GroupEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private DepartmentEntity department;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Collection<StudentEntity> students;
}
