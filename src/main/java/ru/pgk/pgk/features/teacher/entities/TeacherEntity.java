package ru.pgk.pgk.features.teacher.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.io.Serializable;

@Data
@Entity(name = "teachers")
public class TeacherEntity implements Serializable {

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

    public String getFIO() {
        return lastName + " " + firstName.charAt(0) + ".";
    }
}
