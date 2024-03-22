package ru.pgk.pgk.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.pgk.pgk.features.apiToken.services.ApiTokenService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final ApiTokenService apiTokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) {
        try {
            //TODO:Using no sql
            String tokenFromHeader = request.getHeader("X-API-KEY");
            apiTokenService.getByToken(UUID.fromString(tokenFromHeader));
            filterChain.doFilter(request, response);
        }catch (Exception ignore) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
