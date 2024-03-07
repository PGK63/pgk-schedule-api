package ru.pgk.pgk.features.user.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.pgk.pgk.features.user.services.UserService;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/by-telegram-id/{id}/exist")
    @ResponseStatus(HttpStatus.OK)
    private Boolean exist(
            @PathVariable Long id
    ) {
        return userService.existByTelegramId(id);
    }

    @GetMapping("/by-alice-id/{id}/exist")
    @ResponseStatus(HttpStatus.OK)
    private Boolean exist(
            @PathVariable String id
    ) {
        return userService.existByAliceId(id);
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
