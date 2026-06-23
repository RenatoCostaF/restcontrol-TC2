package com.restcontrol.restcontrol_TC2.domain.useCase;

import com.restcontrol.restcontrol_TC2.domain.adapter.input.UpdateUserInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UserInput;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserGateway;

public class UserUseCase {

    private final UserGateway userGateway;

    public UserUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }


    public User create(UserInput userInput) {
        var newUser = User.create(
                userInput.name(),
                userInput.email(),
                userInput.password(),
                userInput.userTypeId()
        );

        return userGateway.create(newUser);
    }

    public User update(UpdateUserInput updateUserInput, String id) {
        var user = userGateway.getById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        var currentUser = user.get();

        var newUser = User.create(
                updateUserInput.name() != null && !updateUserInput.name().isBlank()
                        ? updateUserInput.name()
                        : currentUser.getName(),

                updateUserInput.email() != null && !updateUserInput.email().isBlank()
                        ? updateUserInput.email()
                        : currentUser.getEmail(),

                updateUserInput.password() != null && !updateUserInput.password().isBlank()
                        ? updateUserInput.password()
                        : currentUser.getPassword(),

                updateUserInput.userTypeId() != null
                        ? updateUserInput.userTypeId()
                        : currentUser.getUserTypeId()
        );

        return userGateway.update(newUser, id);
    }

    public User getById(String id) {
        var user = userGateway.getById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return user.get();
    }

    public void delete(String id) {
        userGateway.delete(id);
    }
}
