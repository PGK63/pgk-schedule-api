package ru.pgk.pgk.features.user.services.security;

import ru.pgk.pgk.features.user.dto.security.JwtRequestDto;
import ru.pgk.pgk.features.user.dto.security.JwtResponseDto;

public interface UserSecurityService {

    JwtResponseDto login(JwtRequestDto dto);

    JwtResponseDto refresh(String refreshToken);
}
