package ru.pgk.main_service.features.user.entities;

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
@Entity(name = "alice_users")
public class AliceUserEntity implements Serializable {

    @Id
    private Integer id;

    private String aliceId;

    @MapsId
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
