package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.dto.User.NewUserRequestDTO;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.CreateUserInterface;

public class CreateUserUseCase {

    private final CreateUserInterface createUserInterface;

    public CreateUserUseCase(CreateUserInterface createUserInterface) {
        this.createUserInterface = createUserInterface;
    }

    public void execute(NewUserRequestDTO userRequest) {
        var newUser = User.create(
                userRequest.name(),
                userRequest.email(),
                userRequest.password(),
                userRequest.userType()
        );

        createUserInterface.createUser(newUser);
    }
}
