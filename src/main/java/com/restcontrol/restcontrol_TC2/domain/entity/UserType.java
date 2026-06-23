package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidUserTypeException;

import java.util.UUID;

public class UserType {
    private UUID id;
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        isValidName(name);
        this.name = name;
    }

    private static void isValidName(String name) {
        if (name == null) {
            throw new InvalidUserTypeException("Name is required");
        }
    }

    public static UserType create(String name) {
        isValidName(name);

        UserType userType = new UserType();
        userType.setName(name);

        return userType;
    }
}

