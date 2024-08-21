package ru.pgk.security_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.pgk.security_service.dto.JwtRequestDto;
import ru.pgk.security_service.dto.JwtResponseDto;
import ru.pgk.security_service.services.security.UserSecurityService;

@RestController
@RequiredArgsConstructor
public class UserSecurityController {

    private final UserSecurityService userSecurityService;

    @PostMapping("login")
    private JwtResponseDto login(@RequestBody JwtRequestDto body) {
        return userSecurityService.login(body);
    }

    @PostMapping("refresh")
    private JwtResponseDto refresh(@RequestHeader("refresh-token") String token) {
        return userSecurityService.refresh(token);
    }
}
