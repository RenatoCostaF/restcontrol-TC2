package com.restcontrol.restcontrol_TC2.domain.useCase;

import com.restcontrol.restcontrol_TC2.domain.adapter.input.UpdateUserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserTypeGateway;

public class UserTypeUseCase {

    private final UserTypeGateway userTypeGateway;

    public UserTypeUseCase(UserTypeGateway userTypeGateway) {
        this.userTypeGateway = userTypeGateway;
    }


    public UserType create(UserTypeInput userTypeInput) {
        var newUserType = UserType.create(userTypeInput.name());

        return userTypeGateway.create(newUserType);
    }

    public UserType update(UpdateUserTypeInput updateUserTypeInput, String id) {
        var userType = userTypeGateway.getById(id);

        if (userType.isEmpty()) {
            throw new UserTypeNotFoundException("User type not found");
        }

        var currentUserType = userType.get();

        var newUserType = UserType.create(
                updateUserTypeInput.name() != null && !updateUserTypeInput.name().isBlank()
                        ? updateUserTypeInput.name()
                        : currentUserType.getName()
        );
        newUserType.setId(currentUserType.getId());

        return userTypeGateway.update(newUserType, id);
    }

    public UserType getById(String id) {
        var userType = userTypeGateway.getById(id);

        if (userType.isEmpty()) {
            throw new UserTypeNotFoundException("User type not found");
        }

        return userType.get();
    }

    public void delete(String id) {
        userTypeGateway.delete(id);
    }
}
