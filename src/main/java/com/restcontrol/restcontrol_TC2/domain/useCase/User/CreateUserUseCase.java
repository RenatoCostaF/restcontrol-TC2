package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.dto.User.Request.UserRequestDTO;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.CreateUserInterface;

public class CreateUserUseCase {

    private final CreateUserInterface createUserGateway;

    public CreateUserUseCase(CreateUserInterface createUserGateway) {
        this.createUserGateway = createUserGateway;
    }

    public User execute(UserRequestDTO userRequestDTO) {

        var newUser = User.create(
                userRequestDTO.name(),
                userRequestDTO.email(),
                userRequestDTO.password(),
                userRequestDTO.userType()
        );

        return createUserGateway.createUser(newUser);
    }
}
