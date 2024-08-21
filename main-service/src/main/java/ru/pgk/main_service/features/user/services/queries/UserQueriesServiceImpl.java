package ru.pgk.main_service.features.user.services.queries;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.main_service.common.exceptions.ResourceNotFoundException;
import ru.pgk.main_service.features.user.entities.UserEntity;
import ru.pgk.main_service.features.user.repositoties.UserRepository;

@Service
@RequiredArgsConstructor
public class UserQueriesServiceImpl implements UserQueriesService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getById", key = "#id")
    public UserEntity getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getByTelegramId", key = "#id")
    public UserEntity getByTelegramId(Long id) {
        return userRepository.findByTelegramId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "UserService::getByAliceId", key = "#id")
    public UserEntity getByAliceId(String id) {
        return userRepository.findByAliceId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
