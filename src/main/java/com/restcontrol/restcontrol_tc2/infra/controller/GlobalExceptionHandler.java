package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidMenuItemException;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidRestaurantException;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserException;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserTypeException;
import com.restcontrol.restcontrol_tc2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantDuplicateIdentified;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            InvalidUserException.class,
            InvalidUserTypeException.class,
            InvalidRestaurantException.class,
            InvalidMenuItemException.class
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
