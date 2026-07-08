package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.helper.UserTestHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Mapper de domínio - User")
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("Deve mapear CreateUserInputDTO para entidade User")
    void shouldMapCreateUserInputToEntity() {
        CreateUserInputDTO input = UserTestHelper.createUserInput();

        User user = userMapper.toEntity(input);

        assertEquals(input.id(), user.getId());
        assertEquals(input.name(), user.getName());
        assertEquals(input.email(), user.getEmail());
        assertEquals(input.password(), user.getPassword());
        assertEquals(input.userTypeId(), user.getUserTypeId());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserInputDTO para entidade User com ID informado")
    void shouldMapUpdateUserInputToEntity() {
        String id = new ObjectId().toHexString();
        UpdateUserInputDTO input = UserTestHelper.updateUserInput();

        User user = userMapper.toEntity(input, id);

        assertEquals(id, user.getId());
        assertEquals(input.name(), user.getName());
        assertEquals(input.email(), user.getEmail());
        assertEquals(input.password(), user.getPassword());
        assertEquals(input.userTypeId(), user.getUserTypeId());
    }
}
