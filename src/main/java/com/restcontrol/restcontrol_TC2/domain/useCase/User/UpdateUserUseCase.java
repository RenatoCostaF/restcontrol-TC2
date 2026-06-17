package com.restcontrol.restcontrol_TC2.domain.useCase.User;

import com.restcontrol.restcontrol_TC2.domain.dto.User.Request.UpdateUserRequestDTO;
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


    public User execute(UpdateUserRequestDTO updateUserRequestDTO, UUID id) {
        var user = getByIdUserGateway.getById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        var currentUser = user.get();

        var newUser = User.create(
                updateUserRequestDTO.name() != null && !updateUserRequestDTO.name().isBlank()
                        ? updateUserRequestDTO.name()
                        : currentUser.getName(),

                updateUserRequestDTO.email() != null && !updateUserRequestDTO.email().isBlank()
                        ? updateUserRequestDTO.email()
                        : currentUser.getEmail(),

                updateUserRequestDTO.password() != null && !updateUserRequestDTO.password().isBlank()
                        ? updateUserRequestDTO.password()
                        : currentUser.getPassword(),

                updateUserRequestDTO.userType() != null
                        ? updateUserRequestDTO.userType()
                        : currentUser.getUserType()
        );

        return getUpdateUserGateway.updateUser(newUser, id);
    }
}
