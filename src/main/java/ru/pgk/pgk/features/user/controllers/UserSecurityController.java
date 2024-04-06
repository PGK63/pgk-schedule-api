package ru.pgk.pgk.features.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.user.dto.security.JwtRequestDto;
import ru.pgk.pgk.features.user.dto.security.JwtResponseDto;
import ru.pgk.pgk.features.user.services.security.UserSecurityService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;

@RestController
@RequestMapping("user/security")
@RequiredArgsConstructor
@GlobalSecurityRequirement
public class UserSecurityController {

    private final UserSecurityService userSecurityService;

    @PostMapping("login")
    private JwtResponseDto login(
            @RequestBody JwtRequestDto request
    ) {
        return userSecurityService.login(request);
    }

    @PostMapping("refresh")
    private JwtResponseDto refresh(
        @RequestHeader("refresh-token") String refreshToken
    ) {
        return userSecurityService.refresh(refreshToken);
    }
}
