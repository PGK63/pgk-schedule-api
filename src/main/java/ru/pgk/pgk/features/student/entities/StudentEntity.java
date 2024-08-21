package ru.pgk.pgk.features.student.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.pgk.pgk.features.group.entities.GroupEntity;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "student_users")
public class StudentEntity implements Serializable {

    @Id
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    private GroupEntity group;
}
