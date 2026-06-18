package com.restcontrol.restcontrol_TC2.application.controller;

import com.restcontrol.restcontrol_TC2.application.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.request.UserRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_TC2.application.mapper.UserMapper;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.CreateUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.DeleteUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.GetByIdUserUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.User.UpdateUserUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetByIdUserUseCase getByIdUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserMapper userMapper;

    public UserController(CreateUserUseCase createUserUseCase, GetByIdUserUseCase getByIdUserUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase, UserMapper userMapper) {
        this.createUserUseCase = createUserUseCase;
        this.getByIdUserUseCase = getByIdUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.userMapper = userMapper;
    }

    @PostMapping
    public UserResponseDTO create(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        var userInput = userMapper.toUserInput(userRequestDTO);
        var user = createUserUseCase.execute(userInput);
        return userMapper.toUserResponseDTO(user);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(
            @PathVariable String id
    ) {
        var user = getByIdUserUseCase.execute(id);
        return userMapper.toUserResponseDTO(user);
    }

    @PutMapping("/{id}")
    public UpdateUserResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO
    ) {
        var updateUserInput = userMapper.toUpdateUserInput(updateUserRequestDTO);
        var user = updateUserUseCase.execute(updateUserInput, id);
        return userMapper.toUpdateUserResponseDTO(user);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        deleteUserUseCase.execute(id);
    }
}
