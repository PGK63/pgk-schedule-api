package ru.pgk.pgk.features.secretKey.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "secret_keys")
public class SecretKeyEntity implements Serializable {

    @EmbeddedId
    private Id id;

    private String key;
    private LocalDateTime date = LocalDateTime.now();

    @Getter
    @Setter
    @Embeddable
    public static class Id implements Serializable {

        @ManyToOne(fetch = FetchType.LAZY)
        private UserEntity user;

        @ManyToOne(fetch = FetchType.LAZY)
        private SecretKeyTypeEntity type;
    }
}
