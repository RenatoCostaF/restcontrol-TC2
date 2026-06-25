package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserDocument;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserDocument toDocument(User user);

    public abstract User toDomain(UserDocument document);

    public abstract CreateUserInputDTO toUserInput(CreateUserRequestDTO createUserRequestDTO);

    public abstract UserResponseDTO toUserResponseDTO(User user);

    public abstract UpdateUserInputDTO toUpdateUserInput(UpdateUserRequestDTO updateUserRequestDTO);

    protected ObjectId map(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }

        return new ObjectId(id);
    }

    protected String map(ObjectId id) {
        if (id == null) {
            return null;
        }

        return id.toHexString();
    }
}
