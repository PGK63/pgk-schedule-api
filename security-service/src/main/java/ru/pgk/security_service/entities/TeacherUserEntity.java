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
@Entity(name = "teacher_users")
public class TeacherUserEntity implements Serializable {

    @Id
    private Integer userId;

    @MapsId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
