package ru.pgk.pgk.features.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity(name = "telegram_users")
public class TelegramUserEntity implements Serializable {

    @Id
    private Integer userId;

    private Long telegramId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserEntity user;
}
