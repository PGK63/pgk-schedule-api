package ru.pgk.pgk.features.secretKey.entities;

import jakarta.persistence.*;
import lombok.Data;
import ru.pgk.pgk.features.user.entities.UserEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity(name = "secret_keys")
public class SecretKeyEntity implements Serializable {

    @EmbeddedId
    private Id id;

    private String key;
    private LocalDateTime date = LocalDateTime.now();

    @Data
    @Embeddable
    public static class Id implements Serializable {

        @ManyToOne(fetch = FetchType.LAZY)
        private UserEntity user;

        @ManyToOne(fetch = FetchType.LAZY)
        private SecretKeyTypeEntity type;
    }
}
