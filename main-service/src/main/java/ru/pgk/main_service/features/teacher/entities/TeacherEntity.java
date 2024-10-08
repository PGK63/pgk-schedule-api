package ru.pgk.main_service.features.teacher.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.pgk.main_service.features.department.entitites.DepartmentEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "teachers")
public class TeacherEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String middleName;
    private String cabinet;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "teachers_departments",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private Collection<DepartmentEntity> departments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Collection<TeacherUserEntity> teacherUsers;

    public String getFI() {
        return lastName + " " + firstName.charAt(0) + ".";
    }

    public String getFIO() {
        return lastName + " " + firstName.charAt(0) + "." + " " + lastName.charAt(0) + ".";
    }

    public String getDepartmentName() {
        StringBuilder name = new StringBuilder();
        List<DepartmentEntity> departments = this.departments.stream().toList();
        for (int i = 0; i < departments.size(); i++) {
            name.append(departments.get(i).getName());
            if (i < departments.size() - 1) {
                name.append(", ");
            }
        }
        return name.toString();
    }
}
