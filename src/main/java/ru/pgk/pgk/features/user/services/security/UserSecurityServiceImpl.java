package ru.pgk.pgk.features.user.services.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.pgk.common.exceptions.BadRequestException;
import ru.pgk.pgk.features.user.dto.security.JwtRequestDto;
import ru.pgk.pgk.features.user.dto.security.JwtResponseDto;
import ru.pgk.pgk.features.user.entities.UserEntity;
import ru.pgk.pgk.features.user.services.queries.UserQueriesService;
import ru.pgk.pgk.security.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserQueriesService userQueriesService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public JwtResponseDto login(JwtRequestDto dto) {
        UserEntity user = userQueriesService.getByUsername(dto.username());

        if(!passwordEncoder.matches(dto.password(), user.getUsernamePassword().getPassword()))
            throw new BadRequestException("Password invalid");

        return userToJwtEntity(
                user,
                jwtTokenProvider.createAccessToken(user),
                jwtTokenProvider.createRefreshToken(user)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public JwtResponseDto refresh(String refreshToken) {
        if(jwtTokenProvider.validateRefreshToken(refreshToken)) {
            Claims claims = jwtTokenProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            UserEntity user = userQueriesService.getByUsername(username);
            return userToJwtEntity(user, jwtTokenProvider.createAccessToken(user), refreshToken);
        }
        throw new BadRequestException("Невалидный JWT токен");
    }

    private JwtResponseDto userToJwtEntity(UserEntity user, String accessToken, String refreshToken) {
        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setUserId(user.getId());
        jwtResponseDto.setRole(user.getRole());
        jwtResponseDto.setAdminType(user.getAdminType());
        jwtResponseDto.setAccessToken(accessToken);
        jwtResponseDto.setRefreshToken(refreshToken);
        return jwtResponseDto;
    }
}
