package com.restcontrol.restcontrol_TC2.application.controller.UserType;

import com.restcontrol.restcontrol_TC2.application.dto.UserType.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.UserType.request.UserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.UserType.response.UpdateUserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.UserType.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.application.mapper.UserType.UserTypeMapper;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.CreateUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.DeleteUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.GetByIdUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.UpdateUserTypeUseCase;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user-types")
public class UserTypeController {

    private final CreateUserTypeUseCase createUserTypeUseCase;
    private final GetByIdUserTypeUseCase getByIdUserTypeUseCase;
    private final UpdateUserTypeUseCase updateUserTypeUseCase;
    private final DeleteUserTypeUseCase deleteUserTypeUseCase;
    private final UserTypeMapper userTypeMapper;

    public UserTypeController(
            CreateUserTypeUseCase createUserTypeUseCase,
            GetByIdUserTypeUseCase getByIdUserTypeUseCase,
            UpdateUserTypeUseCase updateUserTypeUseCase,
            DeleteUserTypeUseCase deleteUserTypeUseCase,
            UserTypeMapper userTypeMapper
    ) {
        this.createUserTypeUseCase = createUserTypeUseCase;
        this.getByIdUserTypeUseCase = getByIdUserTypeUseCase;
        this.updateUserTypeUseCase = updateUserTypeUseCase;
        this.deleteUserTypeUseCase = deleteUserTypeUseCase;
        this.userTypeMapper = userTypeMapper;
    }

    @PostMapping
    public UserTypeResponseDTO create(
            @RequestBody UserTypeRequestDTO userTypeRequestDTO
    ) {
        var userTypeInput = userTypeMapper.toUserTypeInput(userTypeRequestDTO);
        var userType = createUserTypeUseCase.execute(userTypeInput);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @GetMapping("/{id}")
    public UserTypeResponseDTO getById(
            @PathVariable String id
    ) {
        var userType = getByIdUserTypeUseCase.execute(id);
        return userTypeMapper.toUserTypeResponseDTO(userType);
    }

    @PutMapping("/{id}")
    public UpdateUserTypeResponseDTO update(
            @PathVariable String id,
            @RequestBody UpdateUserTypeRequestDTO updateUserTypeRequestDTO
    ) {
        var updateUserTypeInput = userTypeMapper.toUpdateUserTypeInput(updateUserTypeRequestDTO);
        var userType = updateUserTypeUseCase.execute(updateUserTypeInput, id);
        return userTypeMapper.toUpdateUserTypeResponseDTO(userType);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable String id
    ) {
        deleteUserTypeUseCase.execute(id);
    }
}
