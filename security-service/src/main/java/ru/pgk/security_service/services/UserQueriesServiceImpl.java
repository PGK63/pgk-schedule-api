package ru.pgk.security_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.security_service.common.exceptions.ResourceNotFoundException;
import ru.pgk.security_service.entities.UserEntity;
import ru.pgk.security_service.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserQueriesServiceImpl implements UserQueriesService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }
}
