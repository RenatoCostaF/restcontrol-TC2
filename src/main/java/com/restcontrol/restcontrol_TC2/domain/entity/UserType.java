package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidUserTypeException;

import java.util.UUID;

public class UserType {
    private UUID id;
    private String name;

    public UserType() {
    }

    public UserType(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

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
        this.name = name;
    }

    public static UserType create(UUID id, String name) {
        if (name == null) {
            throw new InvalidUserTypeException("Name is required");
        }

        UserType userType = new UserType();
        userType.setId(id);
        userType.setName(name);

        return userType;
    }
}
