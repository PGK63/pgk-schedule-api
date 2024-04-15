package ru.pgk.pgk.features.secretKey.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
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
