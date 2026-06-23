package com.restcontrol.restcontrol_TC2.infra.mapper;

import com.restcontrol.restcontrol_TC2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.request.UserRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UpdateUserInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UserInput;
import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserDocument;
import org.springframework.stereotype.Component;

@Component("mongoUserMapper")
public class UserMapper {

    public UserDocument toDocument(User user) {
        UserDocument document = new UserDocument();
        document.setName(user.getName());
        document.setEmail(user.getEmail());
        document.setPassword(user.getPassword());
        document.setUserTypeId(user.getUserTypeId());

        return document;
    }

    public User toDomain(UserDocument document) {
        User user = User.create(
                document.getName(),
                document.getEmail(),
                document.getPassword(),
                document.getUserTypeId()
        );

        return user;
    }

    public UserInput toUserInput(UserRequestDTO requestDTO) {
        return new UserInput(
                requestDTO.name(),
                requestDTO.email(),
                requestDTO.password(),
                requestDTO.userTypeId()
        );
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getUserTypeId()
        );
    }

    public UpdateUserInput toUpdateUserInput(UpdateUserRequestDTO requestDTO) {
        return new UpdateUserInput(
                requestDTO.name(),
                requestDTO.email(),
                requestDTO.password(),
                requestDTO.userTypeId()
        );
    }

    public UpdateUserResponseDTO toUpdateUserResponseDTO(User user) {
        return new UpdateUserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getUserTypeId()
        );
    }
}
