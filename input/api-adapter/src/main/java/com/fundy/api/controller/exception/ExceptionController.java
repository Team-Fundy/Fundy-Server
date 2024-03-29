package com.fundy.api.controller.exception;

import com.fundy.api.common.response.GlobalExceptionResponse;
import com.fundy.application.exception.custom.DuplicateInstanceException;
import com.fundy.application.exception.custom.NoInstanceException;
import com.fundy.application.exception.custom.UnAuthorizedException;
import com.fundy.application.exception.custom.ValidationException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Hidden
@Slf4j
public class ExceptionController {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final GlobalExceptionResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        log.error("잘못된 RequestBody", e);
        return makeResponse(e.getBindingResult()
            .getFieldErrors()
            .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList()));
    }

    @ExceptionHandler({UnAuthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final GlobalExceptionResponse handleUnAuthorizedException(final UnAuthorizedException e) {
        return makeResponse(e.getMessage());
    }

    @ExceptionHandler({DuplicateInstanceException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public final GlobalExceptionResponse handleDuplicateInstanceException(final DuplicateInstanceException e) {
        log.error("Duplicate Exception", e);
        return makeResponse(e.getMessage());
    }

    @ExceptionHandler({NoInstanceException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final GlobalExceptionResponse handleNoInstanceException(final NoInstanceException e) {
        log.error("NoInstance Exception", e);
        return makeResponse(e.getMessage());
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final GlobalExceptionResponse handleValidationException(final ValidationException e) {
        log.error("Valid Exception", e);
        return makeResponse(e.getMessage());
    }

    private GlobalExceptionResponse<String> makeResponse(String message) {
        return GlobalExceptionResponse.<String>builder()
            .message(message)
            .build();
    }

    private GlobalExceptionResponse<List<String>> makeResponse(List<String> messages) {
        return GlobalExceptionResponse.<List<String>>builder()
            .message(messages)
            .build();
    }
}
