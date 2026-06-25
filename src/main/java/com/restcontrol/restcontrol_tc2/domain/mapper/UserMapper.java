package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.User;

public class UserMapper {

    public User toEntity(CreateUserInputDTO input) {
        return new User(
                input.id(),
                input.name(),
                input.email(),
                input.password(),
                input.userTypeId()
        );
    }
}
