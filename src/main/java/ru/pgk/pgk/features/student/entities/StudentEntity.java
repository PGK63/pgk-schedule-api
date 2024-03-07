package ru.pgk.pgk.features.student.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.io.Serializable;

@Data
@Entity(name = "students")
public class StudentEntity implements Serializable {

    @Id
    private Integer id;

    private String groupName;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private DepartmentEntity department;
}
