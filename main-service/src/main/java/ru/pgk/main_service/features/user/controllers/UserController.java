package ru.pgk.main_service.features.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pgk.main_service.features.user.dto.UserDetailsDto;
import ru.pgk.main_service.features.user.entities.UserEntity;
import ru.pgk.main_service.features.user.mappers.UserDetailsMapper;
import ru.pgk.main_service.features.user.services.UserService;
import ru.pgk.main_service.features.user.services.queries.UserQueriesService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserQueriesService userQueriesService;

    private final UserDetailsMapper userDetailsMapper;

    @GetMapping("/by-telegram-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    private UserDetailsDto getByTelegramId(
            @PathVariable Long id
    ) {
        UserEntity user = userQueriesService.getByTelegramId(id);
        return userDetailsMapper.toDto(user);
    }

    @GetMapping("/by-alice-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    private UserDetailsDto getByAliceId(
            @PathVariable String id
    ) {
        UserEntity user = userQueriesService.getByAliceId(id);
        return userDetailsMapper.toDto(user);
    }

    @PostMapping("/alice")
    private void setAliceBySecretKey(
            @RequestParam String aliceId,
            @RequestParam String secretKey
    ) {
        userService.setAliceBySecretKey(aliceId, secretKey);
    }

    @DeleteMapping("/by-telegram-id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteByTelegramId(
        @PathVariable Long id
    ) {
        userService.deleteByTelegramId(id);
    }
}
