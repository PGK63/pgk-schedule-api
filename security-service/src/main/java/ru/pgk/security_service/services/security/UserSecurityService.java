package ru.pgk.security_service.services.security;

import ru.pgk.security_service.dto.JwtRequestDto;
import ru.pgk.security_service.dto.JwtResponseDto;

public interface UserSecurityService {

    JwtResponseDto login(JwtRequestDto dto);

    JwtResponseDto refresh(String refreshToken);
}
