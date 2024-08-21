package ru.pgk.main_service.features.advice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionModel {

    private final String message;
    private final String code;
}
