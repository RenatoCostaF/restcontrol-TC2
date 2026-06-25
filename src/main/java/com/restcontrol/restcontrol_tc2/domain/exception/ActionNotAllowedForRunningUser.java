package com.restcontrol.restcontrol_tc2.domain.exception;

public class ActionNotAllowedForRunningUser extends RuntimeException {
    public ActionNotAllowedForRunningUser(String message) {
        super(message);
    }
}
