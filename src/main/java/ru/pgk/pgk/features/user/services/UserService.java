package ru.pgk.pgk.features.user.services;

public interface UserService {
    void deleteByTelegramId(Long id);
    void deleteByAliceId(String id);
}
