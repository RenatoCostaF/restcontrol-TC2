package com.restcontrol.restcontrol_TC2.domain.exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
