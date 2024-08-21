package ru.pgk.security_service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.pgk.security_service.entities.UserEntity;
import ru.pgk.security_service.jwt.props.JwtProperties;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtTokenProvider {

    private final Key jwtAccessSecret;
    private final Key jwtRefreshSecret;

    public JwtTokenProvider(
            JwtProperties jwtProperties
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(jwtProperties.getAccess().getBytes());
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(jwtProperties.getRefresh().getBytes());
    }

    public String createAccessToken(@NonNull UserEntity user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusYears(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsernamePassword().getUsername())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("role", user.getRole())
                .claim("id", user.getId())
                .claim("admin_type", user.getAdminType())
                .compact();
    }

    public String createRefreshToken(@NonNull UserEntity user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusYears(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsernamePassword().getUsername())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    public String getUserRole(String accessToken) {
        Object role =  getAccessClaims(accessToken).get("role");
        return role == null ? null : role.toString();
    }

    public String getAdminType(String accessToken) {
        Object role =  getAccessClaims(accessToken).get("admin_type");
        return role == null ? null : role.toString();
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
