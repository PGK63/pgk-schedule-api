package ru.pgk.pgk.security.apiKey;

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
            String uri = request.getRequestURI();
            if (uri != null && (uri.contains("/swagger-ui") || uri.contains("v3/api-docs"))) {
                filterChain.doFilter(request, response);
            } else {
                String tokenFromHeader = request.getHeader("X-API-KEY");
                apiTokenService.getByToken(UUID.fromString(tokenFromHeader));
                filterChain.doFilter(request, response);
            }
        }catch (Exception ignore) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
