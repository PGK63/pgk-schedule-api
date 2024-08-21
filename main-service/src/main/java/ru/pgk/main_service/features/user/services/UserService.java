package ru.pgk.main_service.features.user.services;

public interface UserService {

    void setAliceBySecretKey(String aliceId, String secretKey);

    void deleteByTelegramId(Long id);
    void deleteByAliceId(String id);
}
