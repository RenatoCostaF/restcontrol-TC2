package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateUserInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UserInput;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateUserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserDocument;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDocument toDocument(User user) {
        UserDocument document = new UserDocument();
        if (user.getId() != null && !user.getId().isBlank()) {
            document.setId(new org.bson.types.ObjectId(user.getId()));
        }
        document.setName(user.getName());
        document.setEmail(user.getEmail());
        document.setPassword(user.getPassword());
        document.setUserTypeId(user.getUserTypeId());

        return document;
    }

    public User toDomain(UserDocument document) {
        return User.restore(
                document.getId() != null ? document.getId().toHexString() : null,
                document.getName(),
                document.getEmail(),
                document.getPassword(),
                document.getUserTypeId()
        );
    }

    public UserInput toUserInput(UserRequestDTO requestDTO) {
        return new UserInput(
                requestDTO.name(),
                requestDTO.email(),
                requestDTO.password(),
                requestDTO.userTypeId()
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

    public UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getUserTypeId()
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
