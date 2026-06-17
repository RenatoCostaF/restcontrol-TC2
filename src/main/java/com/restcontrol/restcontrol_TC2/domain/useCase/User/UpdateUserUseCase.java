package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.Adapter.User.Input.UpdateUserInput;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.GetByIdUserInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.UpdateUserInterface;

import java.util.UUID;

public class UpdateUserUseCase {

    private final UpdateUserInterface updateUserGateway;
    private final GetByIdUserInterface getByIdUserGateway;
    private final UpdateUserInterface getUpdateUserGateway;

    public UpdateUserUseCase(UpdateUserInterface updateUserGateway, GetByIdUserInterface getByIdUserGateway, UpdateUserInterface getUpdateUserGateway) {
        this.updateUserGateway = updateUserGateway;
        this.getByIdUserGateway = getByIdUserGateway;
        this.getUpdateUserGateway = getUpdateUserGateway;
    }


    public User execute(UpdateUserInput updateUserInput, UUID id) {
        var user = getByIdUserGateway.getById(id);

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

                updateUserInput.userType() != null
                        ? updateUserInput.userType()
                        : currentUser.getUserType()
        );

        return getUpdateUserGateway.updateUser(newUser, id);
    }
}
