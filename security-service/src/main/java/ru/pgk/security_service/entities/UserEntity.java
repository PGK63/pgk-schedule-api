package ru.pgk.security_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private TeacherUserEntity teacher;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private StudentUserEntity student;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AdminEntity admin;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UsernamePasswordEntity usernamePassword;

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
