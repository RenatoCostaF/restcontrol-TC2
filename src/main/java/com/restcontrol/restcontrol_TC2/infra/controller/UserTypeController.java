package com.restcontrol.restcontrol_TC2.infra.controller;

import com.restcontrol.restcontrol_TC2.infra.dto.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.request.UserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UpdateUserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserTypeUseCase;
import com.restcontrol.restcontrol_TC2.infra.mapper.UserTypeMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/userstypes")
public class UserTypeController {

    private final UserTypeMapper userTypeMapper;
    private final UserTypeUseCase userTypeUseCase;

    public UserTypeController(UserTypeMapper userTypeMapper, UserTypeUseCase userTypeUseCase) {
        this.userTypeMapper = userTypeMapper;
        this.userTypeUseCase = userTypeUseCase;
    }

    @PostMapping
    public UserTypeResponseDTO create(
            @RequestBody UserTypeRequestDTO userTypeRequestDTO
    ) {
        var userTypeInput = userTypeMapper.toUserTypeInput(userTypeRequestDTO);
        var userType = userTypeUseCase.create(userTypeInput);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @PutMapping("/{id}")
    public UpdateUserTypeResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateUserTypeRequestDTO updateUserTypeRequestDTO
    ) {
        var updateUserTypeInput = userTypeMapper.toUpdateUserTypeInput(updateUserTypeRequestDTO);
        var userType = userTypeUseCase.update(updateUserTypeInput, id);
        return userTypeMapper.toUpdateUserTypeResponseDTO(userType);
    }

    @GetMapping("/{id}")
    public UserTypeResponseDTO getById(
            @PathVariable String id
    ) {
        var userType = userTypeUseCase.getById(id);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        userTypeUseCase.delete(id);
    }
}
