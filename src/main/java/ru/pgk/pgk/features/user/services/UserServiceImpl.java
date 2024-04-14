package ru.pgk.pgk.features.user.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.features.user.entities.UserEntity;
import ru.pgk.pgk.features.user.repositoties.UserRepository;
import ru.pgk.pgk.features.user.services.cache.UserCacheService;
import ru.pgk.pgk.features.user.services.queries.UserQueriesService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final UserQueriesService userQueriesService;
    private final UserCacheService userCacheService;

    @Override
    @Transactional
    public void deleteByTelegramId(Long id) {
        UserEntity user = userQueriesService.getByTelegramId(id);
        userRepository.delete(user);
        userCacheService.clearAll(user);
    }

    @Override
    @Transactional
    public void deleteByAliceId(String id) {
        UserEntity user = userQueriesService.getByAliceId(id);
        userRepository.delete(user);
        userCacheService.clearAll(user);
    }
}
