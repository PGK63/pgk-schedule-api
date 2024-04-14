package ru.pgk.pgk.features.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.user.dto.UserDetailsDto;
import ru.pgk.pgk.features.user.entities.UserEntity;
import ru.pgk.pgk.features.user.mappers.UserDetailsMapper;
import ru.pgk.pgk.features.user.services.UserService;
import ru.pgk.pgk.features.user.services.queries.UserQueriesService;
import ru.pgk.pgk.security.apiKey.GlobalSecurityRequirement;

@RestController
@RequestMapping("users")
@GlobalSecurityRequirement
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserQueriesService userQueriesService;

    private final UserDetailsMapper userDetailsMapper;

    @GetMapping("/by-telegram-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    private UserDetailsDto getById(
            @PathVariable Long id
    ) {
        UserEntity user = userQueriesService.getByTelegramId(id);
        return userDetailsMapper.toDto(user);
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
