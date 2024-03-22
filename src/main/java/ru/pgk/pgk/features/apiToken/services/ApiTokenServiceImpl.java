package ru.pgk.pgk.features.apiToken.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.apiToken.entities.ApiTokenEntity;
import ru.pgk.pgk.features.apiToken.repositories.ApiTokenRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiTokenServiceImpl implements ApiTokenService {

    private final ApiTokenRepository apiTokenRepository;

    @Override
    @Transactional(readOnly = true)
    public ApiTokenEntity getByToken(UUID token) {
        return apiTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Api token not found"));
    }
}
