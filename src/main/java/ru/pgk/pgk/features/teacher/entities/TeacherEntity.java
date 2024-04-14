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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "teachers_departments",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Collection<DepartmentEntity> departments;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Collection<TeacherUserEntity> teacherUsers;

    public String getFI() {
        return lastName + " " + firstName.charAt(0) + ".";
    }

    public String getFIO() {
        return lastName + " " + firstName.charAt(0) + "." + " " + lastName.charAt(0) + ".";
    }
}
