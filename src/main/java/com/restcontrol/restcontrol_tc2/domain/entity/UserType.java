package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserTypeException;

public class UserType {
    public static final String RESTAURANT_OWNER_CODE = "RESTAURANT_OWNER";

    private String id;
    private String name;
    private String code;

    public UserType(String id, String name, String code) {

        isValidName(name);
        isValidCode(code);

        this.id = id;
        this.name = name;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public boolean isRestaurantOwner() {
        return RESTAURANT_OWNER_CODE.equalsIgnoreCase(code);
    }

    private static void isValidName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidUserTypeException("Name is required");
        }
    }

    private static void isValidCode(String code) {
        if (code == null || code.isBlank()) {
            throw new InvalidUserTypeException("Code is required");
        }
    }

}
