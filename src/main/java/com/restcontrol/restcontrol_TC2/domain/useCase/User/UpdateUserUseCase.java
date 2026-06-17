package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.dto.User.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.UpdateUserInterface;

import java.util.UUID;

public class UpdateUserUseCase {

    private final UpdateUserInterface updateUserInterface;

    public UpdateUserUseCase(UpdateUserInterface updateUserInterface) {
        this.updateUserInterface = updateUserInterface;
    }

    public void execute(UpdateUserRequestDTO userRequest, UUID id) {
        var newUser = User.create(
                userRequest.name(),
                userRequest.email(),
                userRequest.password(),
                userRequest.userType()
        );

        updateUserInterface.updateUser(newUser, id);
    }
}
