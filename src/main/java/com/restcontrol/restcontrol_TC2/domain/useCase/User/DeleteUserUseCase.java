package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.gateway.User.DeleteUserInterface;

import java.util.UUID;

public class DeleteUserUseCase {

    private final DeleteUserInterface deleteUserInterface;

    public DeleteUserUseCase(DeleteUserInterface deleteUserInterface) {
        this.deleteUserInterface = deleteUserInterface;
    }

    public void execute(UUID id) {
        deleteUserInterface.deleteUser(id);
    }
}
