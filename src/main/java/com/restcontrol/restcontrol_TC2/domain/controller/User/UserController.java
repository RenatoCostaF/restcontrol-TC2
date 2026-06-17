package com.restcontrol.restcontrol_TC2.domain.controller.User;

import com.restcontrol.restcontrol_TC2.domain.dto.User.Request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_TC2.domain.dto.User.Request.UserRequestDTO;
import com.restcontrol.restcontrol_TC2.domain.dto.User.Response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.dto.User.Response.UserResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.CreateUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.DeleteUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.GetByIdUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.UpdateUserUseCase;

import java.util.UUID;

public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetByIdUserUseCase getByIdUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase, GetByIdUserUseCase getByIdUserUseCase, DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getByIdUserUseCase = getByIdUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        var response = createUserUseCase.execute(userRequestDTO);

        return new UserResponseDTO(
                response.getName(),
                response.getEmail(),
                response.getUserType()
        );
    }

    public UserResponseDTO getByIdUser(UUID id) {
        var response = getByIdUserUseCase.execute(id);

        return new UserResponseDTO(
                response.getName(),
                response.getEmail(),
                response.getUserType()
        );
    }

    public UpdateUserResponseDTO updateUser(UpdateUserRequestDTO updateUserRequestDTO, UUID id) {
        var response = updateUserUseCase.execute(updateUserRequestDTO, id);

        return new UpdateUserResponseDTO(
                response.getName(),
                response.getEmail(),
                response.getUserType()
        );
    }

    public void deleteUser(UUID id) {
        deleteUserUseCase.execute(id);

    }
}
