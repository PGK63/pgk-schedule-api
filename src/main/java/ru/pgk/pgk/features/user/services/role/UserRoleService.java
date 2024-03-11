package ru.pgk.pgk.features.user.services.role;

import ru.pgk.pgk.features.user.entities.UserEntity;

public interface UserRoleService {

    UserEntity.Role getRoleByTelegramId(Long telegramId);
}
