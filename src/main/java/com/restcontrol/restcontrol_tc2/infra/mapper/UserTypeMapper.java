package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.CreateUserTypeResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UpdateUserTypeResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserTypeDocument;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserTypeMapper {

    public abstract UserTypeDocument toDocument(UserType userType);

    public abstract UserType toDomain(UserTypeDocument document);

    public abstract CreateUserTypeInputDTO toUserTypeInput(CreateUserTypeRequestDTO createUserTypeRequestDTO);

    public abstract CreateUserTypeResponseDTO toUserTypeResponseDTO(UserType userType);

    public abstract UpdateUserTypeInputDTO toUpdateUserTypeInput(UpdateUserTypeRequestDTO updateUserTypeRequestDTO);

    public abstract UpdateUserTypeResponseDTO toUpdateUserTypeResponseDTO(UserType userType);

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
