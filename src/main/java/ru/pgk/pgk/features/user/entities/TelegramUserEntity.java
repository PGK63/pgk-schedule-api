package ru.pgk.pgk.features.user.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity(name = "telegram_users")
public class TelegramUserEntity implements Serializable {

    @Id
    private Integer userId;

    private Long telegramId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
