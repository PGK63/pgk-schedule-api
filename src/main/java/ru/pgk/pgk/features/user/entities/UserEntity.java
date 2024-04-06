package ru.pgk.pgk.features.user.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.admin.entities.AdminEntity;
import ru.pgk.pgk.features.admin.entities.AdminTypeEntity;
import ru.pgk.pgk.features.student.entities.StudentEntity;
import ru.pgk.pgk.features.teacher.entities.TeacherUserEntity;

import java.io.Serializable;

@Data
@Entity(name = "users")
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AliceUserEntity alice;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TelegramUserEntity telegram;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private StudentEntity student;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TeacherUserEntity teacher;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AdminEntity admin;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UsernamePasswordEntity usernamePassword;

    public Role getRole() {
        if(teacher != null)
            return Role.TEACHER;
        else if(student != null)
            return Role.STUDENT;
        return null;
    }

    public AdminTypeEntity.Type getAdminType() {
        if(admin != null){
            return admin.getType().getType();
        }
        return null;
    }

    public enum Role {
        STUDENT,
        TEACHER
    }
}
