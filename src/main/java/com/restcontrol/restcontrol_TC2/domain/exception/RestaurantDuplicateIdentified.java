package com.restcontrol.restcontrol_TC2.domain.exception;

public class RestaurantDuplicateIdentified extends RuntimeException {
    public RestaurantDuplicateIdentified(String message) {
        super(message);
    }
}
