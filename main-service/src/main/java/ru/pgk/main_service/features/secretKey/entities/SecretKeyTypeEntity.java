package ru.pgk.main_service.features.secretKey.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Entity(name = "secret_key_types")
public class SecretKeyTypeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Enumerated(value = EnumType.STRING)
    private Type value;

    @OneToMany(mappedBy = "id.type", fetch = FetchType.LAZY)
    private Collection<SecretKeyEntity> secretKeys;

    public enum Type {
        ALICE_LOGIN
    }
}
