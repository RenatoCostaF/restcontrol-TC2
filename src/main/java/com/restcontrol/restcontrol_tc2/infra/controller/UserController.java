package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_tc2.domain.port.input.UserInputPort;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserMapper userMapper;
    private final UserInputPort userUseCase;

    public UserController(UserMapper userMapper, UserInputPort userUseCase) {
        this.userMapper = userMapper;
        this.userUseCase = userUseCase;
    }

    @PostMapping
    public UserResponseDTO create(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        var userInput = userMapper.toUserInput(userRequestDTO);
        var user = userUseCase.create(userInput);
        return userMapper.toUserResponseDTO(user);
    }

    @PutMapping("/{id}")
    public UpdateUserResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO
    ) {
        var updateUserInput = userMapper.toUpdateUserInput(updateUserRequestDTO);
        var user = userUseCase.update(updateUserInput, id);
        return userMapper.toUpdateUserResponseDTO(user);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(
            @PathVariable String id
    ) {
        var user = userUseCase.getById(id);
        return userMapper.toUserResponseDTO(user);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        userUseCase.delete(id);
    }

}
