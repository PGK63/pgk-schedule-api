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
}
