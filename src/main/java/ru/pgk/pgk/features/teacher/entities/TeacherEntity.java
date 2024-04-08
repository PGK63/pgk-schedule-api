package ru.pgk.pgk.features.teacher.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.department.entitites.DepartmentEntity;

import java.io.Serializable;
import java.util.Collection;

@Data
@Entity(name = "teachers")
public class TeacherEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String middleName;
    private String cabinet;

    @ManyToOne(fetch = FetchType.EAGER)
    private DepartmentEntity department;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Collection<TeacherUserEntity> teacherUsers;

    public String getFIO() {
        return lastName + " " + firstName.charAt(0) + ".";
    }
}
