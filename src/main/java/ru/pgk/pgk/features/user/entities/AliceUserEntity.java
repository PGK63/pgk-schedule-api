package ru.pgk.pgk.features.user.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity(name = "alice_users")
public class AliceUserEntity implements Serializable {

    @Id
    private Integer id;

    private String aliceId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
