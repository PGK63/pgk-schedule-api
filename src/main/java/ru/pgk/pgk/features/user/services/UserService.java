package ru.pgk.pgk.features.user.services;

public interface UserService {

    void setAliceBySecretKey(String aliceId, String secretKey);

    void deleteByTelegramId(Long id);
    void deleteByAliceId(String id);
}
