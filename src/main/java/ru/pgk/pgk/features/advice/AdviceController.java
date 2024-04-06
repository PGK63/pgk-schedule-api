package ru.pgk.pgk.features.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.pgk.pgk.common.exceptions.BadRequestException;
import ru.pgk.pgk.common.exceptions.ForbiddenException;
import ru.pgk.pgk.common.exceptions.ResourceNotFoundException;
import ru.pgk.pgk.features.advice.model.ExceptionModel;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionModel handleResourceNotFound(
            final ResourceNotFoundException e) {
        return new ExceptionModel(e.getMessage(), "resource_not_found_error");
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionModel handleIllegalState(final IllegalStateException e) {
        return new ExceptionModel(e.getMessage(), "illegal_state_error");
    }

    @ExceptionHandler({ AccessDeniedException.class, ForbiddenException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionModel handleAccessDenied(final Exception e) {
        return new ExceptionModel(e.getMessage() == null ? "Access denied" : e.getMessage(), "access_denied_error");
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionModel handleBadRequestException(final BadRequestException e) {
        return new ExceptionModel(e.getMessage(),"bad_request_error");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionModel handleException(final Exception e) {
        return new ExceptionModel(e.getMessage(), "internal_error");
    }
}
