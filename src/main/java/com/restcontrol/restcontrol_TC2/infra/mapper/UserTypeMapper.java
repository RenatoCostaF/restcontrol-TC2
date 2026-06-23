package com.restcontrol.restcontrol_TC2.infra.mapper;

import com.restcontrol.restcontrol_TC2.infra.dto.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.request.UserTypeRequestDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UpdateUserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.infra.dto.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UpdateUserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.adapter.input.UserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.entity.UserTypeDocument;
import org.springframework.stereotype.Component;

@Component("mongoUserTypeMapper")
public class UserTypeMapper {

    public UserTypeDocument toDocument(UserType userType) {
        UserTypeDocument document = new UserTypeDocument();
        if (userType.getId() != null && !userType.getId().isBlank()) {
            document.setId(new org.bson.types.ObjectId(userType.getId()));
        }
        document.setName(userType.getName());
        return document;
    }

    public UserType toDomain(UserTypeDocument document) {
        UserType userType = UserType.create(
                document.getName()
        );
        if (document.getId() != null) {
            userType.setId(document.getId().toHexString());
        }
        return userType;
    }

    public UserTypeInput toUserTypeInput(UserTypeRequestDTO requestDTO) {
        return new UserTypeInput(requestDTO.name());
    }

    public UpdateUserTypeInput toUpdateUserTypeInput(UpdateUserTypeRequestDTO requestDTO) {
        return new UpdateUserTypeInput(requestDTO.name());
    }

    public UserTypeResponseDTO toUserTypeResponseDTO(UserType userType) {
        return new UserTypeResponseDTO(
                userType.getId(),
                userType.getName()
        );
    }

    public UpdateUserTypeResponseDTO toUpdateUserTypeResponseDTO(UserType userType) {
        return new UpdateUserTypeResponseDTO(
                userType.getId(),
                userType.getName()
        );
    }
}
