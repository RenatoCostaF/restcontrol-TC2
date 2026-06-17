package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidUserException;

import java.util.UUID;

public class User {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private UserType userType;

    public User() {
    }

    public User(
            String name,
            String email, String password,
            UserType userType
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public UUID getId() {
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

    public UserType getUserType() {
        return userType;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    private static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    public static User create(
            String name,
            String email,
            String password,
            UserType userType
    ) {
        if (name == null || userType == null) {
            throw new InvalidUserException("Name and userType are required");
        }

        if (!isValidEmail(email)) {
            throw new InvalidUserException("Review Email");
        }

        if (!isValidPassword(password)) {
            throw new InvalidUserException("The password must contain at least 8 characters");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(userType);

        return user;
    }

}
