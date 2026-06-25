package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserTypeException;

public class UserType {
    private String id;
    private String name;

    public UserType(String id, String name) {

        isValidName(name);

        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    private static void isValidName(String name) {
        if (name == null) {
            throw new InvalidUserTypeException("Name is required");
        }
    }

}

