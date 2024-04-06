package ru.pgk.pgk.features.admin.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.io.Serializable;

@Data
@Entity(name = "admins")
public class AdminEntity implements Serializable {

    @Id
    private Integer userId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdminTypeEntity type;
}
