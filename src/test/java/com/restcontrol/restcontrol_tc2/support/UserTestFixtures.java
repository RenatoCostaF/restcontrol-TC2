package com.restcontrol.restcontrol_tc2.support;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import org.bson.types.ObjectId;

public final class UserTestFixtures {

    public static final String VALID_USER_ID = new ObjectId().toHexString();
    public static final String VALID_USER_TYPE_ID = new ObjectId().toHexString();
    public static final String VALID_EMAIL = "user@example.com";
    public static final String VALID_PASSWORD = "password123";
    public static final String VALID_NAME = "João Silva";

    private UserTestFixtures() {

    }

    public static User validUser() {
        return new User(VALID_USER_ID, VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_USER_TYPE_ID);
    }

    public static User validUser(String id) {
        return new User(id, VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_USER_TYPE_ID);
    }

    public static UserType validUserType() {
        return new UserType(VALID_USER_ID, "Cliente", UserType.CUSTOMER_CODE);
    }

    public static CreateUserInputDTO createUserInput() {
        return new CreateUserInputDTO(null, VALID_NAME, VALID_EMAIL, VALID_PASSWORD, VALID_USER_TYPE_ID);
    }

    public static UpdateUserInputDTO updateUserInput() {
        return new UpdateUserInputDTO("João da Silva", "joao@email.com", "newpassword123", VALID_USER_TYPE_ID);
    }
}
