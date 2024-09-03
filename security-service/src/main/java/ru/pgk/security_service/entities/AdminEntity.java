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
@Entity(name = "admins")
public class AdminEntity implements Serializable {

    @Id
    private Integer userId;

    private String username;
    private String password;

    @MapsId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private AdminTypeEntity type;
}
