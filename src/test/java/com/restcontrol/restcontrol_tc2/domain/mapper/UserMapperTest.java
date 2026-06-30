package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    @DisplayName("Should convert CreateUserInputDTO to a user entity")
    void shouldMapCreateUserInputToEntity() {
        CreateUserInputDTO input = UserTestFixtures.createUserInput();

        User user = userMapper.toEntity(input);

        Assert.assertEquals(input.id(), user.getId());
        Assert.assertEquals(input.name(), user.getName());
        Assert.assertEquals(input.email(), user.getEmail());
        Assert.assertEquals(input.password(), user.getPassword());
        Assert.assertEquals(input.userTypeId(), user.getUserTypeId());
    }

    @Test
    @DisplayName("Should convert UpdateUserInputDTO to a user entity")
    void shouldMapUpdateUserInputToEntity() {
        String id = new ObjectId().toHexString();
        UpdateUserInputDTO input = UserTestFixtures.updateUserInput();

        User user = userMapper.toEntity(input, id);

        Assert.assertEquals(id, user.getId());
        Assert.assertEquals(input.name(), user.getName());
        Assert.assertEquals(input.email(), user.getEmail());
        Assert.assertEquals(input.password(), user.getPassword());
        Assert.assertEquals(input.userTypeId(), user.getUserTypeId());
    }
}
