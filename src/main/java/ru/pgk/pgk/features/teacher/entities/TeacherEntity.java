package ru.pgk.pgk.features.teacher.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.user.entities.UserEntity;

@Data
@Entity(name = "teachers")
public class TeacherEntity {

    @Id
    private Integer id;

    private String firstName;
    private String lastName;
    private String cabinet;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private DepartmentEntity department;
}
