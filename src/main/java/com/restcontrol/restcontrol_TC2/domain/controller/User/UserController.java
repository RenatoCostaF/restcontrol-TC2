package com.restcontrol.restcontrol_TC2.domain.controller.User;

import com.restcontrol.restcontrol_TC2.domain.Adapter.User.Input.UpdateUserInput;
import com.restcontrol.restcontrol_TC2.domain.Adapter.User.Input.UserInput;
import com.restcontrol.restcontrol_TC2.domain.Adapter.User.Output.UpdateUserOutput;
import com.restcontrol.restcontrol_TC2.domain.Adapter.User.Output.UserOutput;
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

    public UserOutput createUser(UserInput userInput) {
        var response = createUserUseCase.execute(userInput);

        return new UserOutput(
                response.getName(),
                response.getEmail(),
                response.getUserType()
        );
    }

    public UserOutput getByIdUser(UUID id) {
        var response = getByIdUserUseCase.execute(id);

        return new UserOutput(
                response.getName(),
                response.getEmail(),
                response.getUserType()
        );
    }

    public UpdateUserOutput updateUser(UpdateUserInput updateUserInput, UUID id) {
        var response = updateUserUseCase.execute(updateUserInput, id);

        return new UpdateUserOutput(
                response.getName(),
                response.getEmail(),
                response.getUserType()
        );
    }

    public void deleteUser(UUID id) {
        deleteUserUseCase.execute(id);

    }
}
