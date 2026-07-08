package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.helper.UserTypeTestHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Mapper de domínio - UserType")
class UserTypeMapperTest {

    private UserTypeMapper userTypeMapper;

    @BeforeEach
    void setUp() {
        userTypeMapper = new UserTypeMapper();
    }

    @Test
    @DisplayName("Deve mapear CreateUserTypeInputDTO para entidade UserType")
    void shouldMapCreateUserTypeInputToEntity() {
        CreateUserTypeInputDTO input = UserTypeTestHelper.createUserTypeInput();

        UserType userType = userTypeMapper.toEntity(input);

        assertEquals(input.id(), userType.getId());
        assertEquals(input.name(), userType.getName());
        assertEquals(input.code(), userType.getCode());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserTypeInputDTO para entidade UserType com ID informado")
    void shouldMapUpdateUserTypeInputToEntity() {
        String id = new ObjectId().toHexString();
        UpdateUserTypeInputDTO input = UserTypeTestHelper.updateUserTypeInput();

        UserType userType = userTypeMapper.toEntity(input, id);

        assertEquals(id, userType.getId());
        assertEquals(input.name(), userType.getName());
        assertEquals(input.code(), userType.getCode());
    }
}
