package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.Adapter.User.Input.UserInput;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.CreateUserInterface;

public class CreateUserUseCase {

    private final CreateUserInterface createUserGateway;

    public CreateUserUseCase(CreateUserInterface createUserGateway) {
        this.createUserGateway = createUserGateway;
    }

    public User execute(UserInput userInput) {

        var newUser = User.create(
                userInput.name(),
                userInput.email(),
                userInput.password(),
                userInput.userType()
        );

        return createUserGateway.createUser(newUser);
    }
}
