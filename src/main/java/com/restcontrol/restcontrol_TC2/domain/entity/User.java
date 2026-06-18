package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidUserException;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private UserType userType;

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

    public UserType getUserType() {
        return userType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        isValidName(name);
        this.name = name;
    }

    public void setEmail(String email) {
        isValidEmail(email);
        this.email = email;
    }

    public void setPassword(String password) {
        isValidPassword(password);
        this.password = password;
    }

    public void setUserType(UserType userType) {
        isValidUserType(userType);
        this.userType = userType;
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

    private static void isValidUserType(UserType userType) {
        if (userType == null) {
            throw new InvalidUserException("User type cannot be null");
        }
    }

    private static void isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidUserException("The password must contain at least 8 characters");
        }
    }

    public static User create(
            String name,
            String email,
            String password,
            UserType userType
    ) {
        isValidName(name);
        isValidEmail(email);
        isValidPassword(password);
        isValidUserType(userType);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserType(userType);

        return user;
    }

}
