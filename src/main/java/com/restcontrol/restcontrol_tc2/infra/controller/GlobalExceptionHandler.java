package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Request validation failed"
        );
        problemDetail.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getRequestURI());

        var errors = new LinkedHashMap<String, String>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    @ExceptionHandler({
            InvalidUserException.class,
            InvalidUserTypeException.class,
            InvalidRestaurantException.class,
            InvalidMenuItemException.class,
            InvalidObjectIdException.class
    })
    public ProblemDetail handleBadRequest(RuntimeException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception, request);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            UserTypeNotFoundException.class,
            RestaurantNotFoundException.class,
            MenuItemNotFoundException.class
    })
    public ProblemDetail handleNotFound(RuntimeException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, exception, request);
    }

    @ExceptionHandler(RestaurantDuplicateIdentified.class)
    public ProblemDetail handleConflict(RuntimeException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, exception, request);
    }

    @ExceptionHandler(ActionNotAllowedForRunningUser.class)
    public ProblemDetail handleForbidden(RuntimeException exception, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, exception, request);
    }

    private ProblemDetail buildResponse(
            HttpStatus status,
            RuntimeException exception,
            HttpServletRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getRequestURI());
        return problemDetail;
    }
}
