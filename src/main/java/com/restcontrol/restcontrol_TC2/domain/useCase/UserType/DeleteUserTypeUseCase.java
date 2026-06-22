package com.restcontrol.restcontrol_TC2.domain.useCase.UserType;

import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.DeleteUserTypeInterface;

public class DeleteUserTypeUseCase {

    private final DeleteUserTypeInterface deleteUserTypeGateway;

    public DeleteUserTypeUseCase(DeleteUserTypeInterface deleteUserTypeGateway) {
        this.deleteUserTypeGateway = deleteUserTypeGateway;
    }

    public void execute(String id) {
        deleteUserTypeGateway.deleteUserType(id);
    }
}
