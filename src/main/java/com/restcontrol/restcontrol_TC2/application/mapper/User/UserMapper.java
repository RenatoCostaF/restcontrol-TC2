package com.restcontrol.restcontrol_TC2.application.mapper.User;

import com.restcontrol.restcontrol_TC2.application.dto.User.request.UserRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.User.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.UserType.request.UserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.application.dto.User.response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.User.response.UserResponseDTO;
import com.restcontrol.restcontrol_TC2.application.dto.UserType.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.Adapter.User.Input.UpdateUserInput;
import com.restcontrol.restcontrol_TC2.domain.Adapter.User.Input.UserInput;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import org.springframework.stereotype.Component;

@Component("applicationUserMapper")
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
        UserType userType = UserType.create(dto.name());

        if (dto.id() != null) {
            userType.setId(dto.id());
        }

        return userType;
    }

    private UserTypeResponseDTO toUserTypeResponseDTO(UserType userType) {
        return new UserTypeResponseDTO(
                userType.getId(),
                userType.getName()
        );
    }

}
