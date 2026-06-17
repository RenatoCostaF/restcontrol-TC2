package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.gateway.User.DeleteUserInterface;

import java.util.UUID;

public class DeleteUserUseCase {

    private final DeleteUserInterface deleteUserGateway;

    public DeleteUserUseCase(DeleteUserInterface deleteUserGateway) {
        this.deleteUserGateway = deleteUserGateway;
    }

    public void execute(UUID id) {
        deleteUserGateway.deleteUser(id);
    }
}
