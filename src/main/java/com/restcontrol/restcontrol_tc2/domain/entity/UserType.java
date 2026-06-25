package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserTypeException;

public class UserType {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setName(String name) {
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

    public static UserType restore(String id, String name) {
        UserType userType = create(name);
        userType.setId(id);
        return userType;
    }

    public void rename(String name) {
        setName(name);
    }
}

