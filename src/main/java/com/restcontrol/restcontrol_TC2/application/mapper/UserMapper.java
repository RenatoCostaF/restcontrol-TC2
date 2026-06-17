package com.restcontrol.restcontrol_TC2.application.mapper;

import com.restcontrol.restcontrol_TC2.application.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.request.UserRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.request.UserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.adapter.User.Input.UpdateUserInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.User.Input.UserInput;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserInput toUserInput(UserRequestDTO requestDTO) {
        return new UserInput(
                requestDTO.name(),
                requestDTO.email(),
                requestDTO.password(),
                toUserType(requestDTO.userType())
        );
    }

    public UpdateUserInput toUpdateUserInput(UpdateUserRequestDTO requestDTO) {
        return new UpdateUserInput(
                requestDTO.name(),
                requestDTO.email(),
                requestDTO.password(),
                requestDTO.userType() != null ? toUserType(requestDTO.userType()) : null
        );
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                toUserTypeResponseDTO(user.getUserType())
        );
    }

    public UpdateUserResponseDTO toUpdateUserResponseDTO(User user) {
        return new UpdateUserResponseDTO(
                user.getName(),
                user.getEmail(),
                toUserTypeResponseDTO(user.getUserType())
        );
    }

    private UserType toUserType(UserTypeRequestDTO dto) {
        return UserType.create(
                dto.id(),
                dto.name()
        );
    }

    private UserTypeResponseDTO toUserTypeResponseDTO(UserType userType) {
        return new UserTypeResponseDTO(
                userType.getId(),
                userType.getName()
        );
    }

}
