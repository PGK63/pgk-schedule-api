package ru.pgk.pgk.features.admin.entities;

import jakarta.persistence.*;
import lombok.*;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "admins")
public class AdminEntity implements Serializable {

    @Id
    private Integer userId;

    private String username;
    private String password;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdminTypeEntity type;
}
