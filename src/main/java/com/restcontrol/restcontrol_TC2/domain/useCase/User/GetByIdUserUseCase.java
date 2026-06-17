package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.GetByIdUserInterface;

import java.util.UUID;

public class GetByIdUserUseCase {

    private final GetByIdUserInterface getByIdUserGateway;

    public GetByIdUserUseCase(GetByIdUserInterface getByIdUserGateway) {
        this.getByIdUserGateway = getByIdUserGateway;
    }


    public User execute(UUID id) {
        var user = getByIdUserGateway.getById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return user.get();
    }
}
