package ru.pgk.pgk.features.user.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "telegram_users")
public class TelegramUserEntity {

    @Id
    private Integer userId;

    private Long telegramId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
