package ru.pgk.main_service.features.admin.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.pgk.main_service.features.user.entities.UserEntity;

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
