package ru.pgk.security_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "username_password_users")
public class UsernamePasswordEntity implements Serializable {

    @Id
    private Integer userId;

    private String username;
    private String password;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
