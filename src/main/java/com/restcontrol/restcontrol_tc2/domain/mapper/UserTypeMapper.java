package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;

public class UserTypeMapper {

    public UserType toEntity(CreateUserTypeInputDTO input) {
        return new UserType(
                input.id(),
                input.name()
        );
    }
}
