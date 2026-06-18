package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidUserTypeException;

public class UserType {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public static UserType create(
            String id,
            String name
    ) {
        isValidName(name);

        UserType userType = new UserType();
        userType.setId(id);
        userType.setName(name);

        return userType;
    }
}

