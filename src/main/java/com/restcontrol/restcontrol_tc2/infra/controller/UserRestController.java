package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.UserController;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.CreateUserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserRestController {

    private final UserController userController;
    private final UserMapper userMapper;

    public UserRestController(UserController userController, UserMapper userMapper) {
        this.userController = userController;
        this.userMapper = userMapper;
    }

    @PostMapping
    public CreateUserResponseDTO create(
            @RequestBody CreateUserRequestDTO createUserRequestDTO
    ) {
        var userInput = userMapper.toUserInput(createUserRequestDTO);
        var user = userController.create(userInput);
        return userMapper.toUserResponseDTO(user);
    }

    @PutMapping("/{id}")
    public UpdateUserResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateUserRequestDTO updateUserRequestDTO
    ) {
        var updateUserInput = userMapper.toUpdateUserInput(updateUserRequestDTO);
        var user = userController.update(updateUserInput, id);
        return userMapper.toUpdateUserResponseDTO(user);
    }

    @GetMapping("/{id}")
    public CreateUserResponseDTO getById(
            @PathVariable String id
    ) {
        var user = userController.getById(id);
        return userMapper.toUserResponseDTO(user);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        userController.delete(id);
    }

}
