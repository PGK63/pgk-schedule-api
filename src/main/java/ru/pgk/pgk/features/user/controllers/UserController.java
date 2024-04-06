package ru.pgk.pgk.features.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.user.entities.UserEntity;
import ru.pgk.pgk.features.user.services.UserService;
import ru.pgk.pgk.features.user.services.role.UserRoleService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;

@RestController
@RequestMapping("users")
@GlobalSecurityRequirement
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRoleService userRoleService;

    @GetMapping("/role/by-telegram-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    private UserEntity.Role getRoleByTelegramId(
            @PathVariable Long id
    ) {
        return userRoleService.getRoleByTelegramId(id);
    }

    @DeleteMapping("/by-telegram-id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteByTelegramId(
        @PathVariable Long id
    ) {
        userService.deleteByTelegramId(id);
    }

    @DeleteMapping("/by-alice-id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteByAliceId(
            @PathVariable String id
    ) {
        userService.deleteByAliceId(id);
    }
}
