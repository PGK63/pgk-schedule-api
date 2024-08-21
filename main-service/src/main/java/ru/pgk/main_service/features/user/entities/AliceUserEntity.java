package ru.pgk.main_service.features.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "alice_users")
public class AliceUserEntity implements Serializable {

    @Id
    private Integer id;

    private String aliceId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
