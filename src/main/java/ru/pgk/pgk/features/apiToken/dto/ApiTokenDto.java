package ru.pgk.pgk.features.apiToken.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ApiTokenDto(
        Integer id,
        String name,
        UUID token,
        LocalDate date
) {}
