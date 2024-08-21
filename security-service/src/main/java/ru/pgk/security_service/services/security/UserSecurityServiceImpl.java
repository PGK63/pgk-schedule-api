package ru.pgk.security_service.services.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.security_service.common.exceptions.BadRequestException;
import ru.pgk.security_service.dto.JwtRequestDto;
import ru.pgk.security_service.dto.JwtResponseDto;
import ru.pgk.security_service.entities.UserEntity;
import ru.pgk.security_service.jwt.JwtTokenProvider;
import ru.pgk.security_service.services.UserQueriesService;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {

    private final UserQueriesService userQueriesService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public JwtResponseDto login(JwtRequestDto dto) {
        UserEntity user = userQueriesService.getByUsername(dto.username());

        if(!passwordEncoder.matches(dto.password(), user.getAdmin().getPassword()))
            throw new BadRequestException("Password invalid.");

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
        throw new BadRequestException("Token invalid.");
    }

    private JwtResponseDto userToJwtEntity(UserEntity user, String accessToken, String refreshToken) {
        return new JwtResponseDto(
                user.getId(),
                user.getRole(),
                user.getAdminType(),
                accessToken,
                refreshToken
        );
    }
}
