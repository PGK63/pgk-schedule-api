package ru.pgk.security_service.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TeacherUserEntity teacher;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private StudentUserEntity student;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AdminEntity admin;

    public Role getRole() {
        if(teacher != null) return Role.TEACHER;
        if(student != null) return Role.STUDENT;
        return null;
    }

    public AdminTypeEntity.Type getAdminType() {
        if(admin != null) return admin.getType().getType();
        return null;
    }

    public enum Role {
        STUDENT,
        TEACHER
    }
}
