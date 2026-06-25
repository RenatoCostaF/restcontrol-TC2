package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidUserException;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String userTypeId;

    public User(String id, String name, String email, String password, String userTypeId) {

        isValidName(name);
        isValidEmail(email);
        isValidPassword(password);
        isValidUserTypeId(userTypeId);

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userTypeId = userTypeId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static void isValidEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new InvalidUserException("Invalid email format");
        }
    }

    private static void isValidName(String name) {
        if (name == null) {
            throw new InvalidUserException("Name cannot be null");
        }
    }

    private static void isValidUserTypeId(String userTypeId) {
        if (userTypeId == null) {
            throw new InvalidUserException("User type cannot be null");
        }
    }

    private static void isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidUserException("The password must contain at least 8 characters");
        }
    }

}
