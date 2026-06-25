package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.UserTypeController;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.CreateUserTypeResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateUserTypeResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserTypeMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/userstypes")
public class UserTypeRestController {

    private final UserTypeController userTypeController;
    private final UserTypeMapper userTypeMapper;

    public UserTypeRestController(UserTypeController userTypeController, UserTypeMapper userTypeMapper) {
        this.userTypeController = userTypeController;
        this.userTypeMapper = userTypeMapper;
    }

    @PostMapping
    public CreateUserTypeResponseDTO create(
            @RequestBody CreateUserTypeRequestDTO createUserTypeRequestDTO
    ) {
        var userTypeInput = userTypeMapper.toUserTypeInput(createUserTypeRequestDTO);
        var userType = userTypeController.create(userTypeInput);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @PutMapping("/{id}")
    public UpdateUserTypeResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateUserTypeRequestDTO updateUserTypeRequestDTO
    ) {
        var updateUserTypeInput = userTypeMapper.toUpdateUserTypeInput(updateUserTypeRequestDTO);
        var userType = userTypeController.update(updateUserTypeInput, id);
        return userTypeMapper.toUpdateUserTypeResponseDTO(userType);
    }

    @GetMapping("/{id}")
    public CreateUserTypeResponseDTO getById(
            @PathVariable String id
    ) {
        var userType = userTypeController.getById(id);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        userTypeController.delete(id);
    }
}
