package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserDocument;
import com.restcontrol.restcontrol_tc2.helper.UserTestHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Mapper de infra - User")
class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    @DisplayName("Deve mapear User para UserDocument")
    void shouldMapUserToDocument() {
        User user = UserTestHelper.validUser();

        UserDocument document = userMapper.toDocument(user);

        assertEquals(new ObjectId(user.getId()), document.getId());
        assertEquals(user.getName(), document.getName());
        assertEquals(user.getEmail(), document.getEmail());
        assertEquals(user.getPassword(), document.getPassword());
        assertEquals(user.getUserTypeId(), document.getUserTypeId());
    }

    @Test
    @DisplayName("Deve mapear User para UserDocument com ID nulo")
    void shouldMapUserToDocumentWithNullId() {
        User user = new User(null, UserTestHelper.VALID_NAME, UserTestHelper.VALID_EMAIL,
                UserTestHelper.VALID_PASSWORD, UserTestHelper.VALID_USER_TYPE_ID);

        UserDocument document = userMapper.toDocument(user);

        assertNull(document.getId());
        assertEquals(user.getName(), document.getName());
    }

    @Test
    @DisplayName("Deve mapear UserDocument para User")
    void shouldMapDocumentToDomain() {
        ObjectId objectId = new ObjectId();
        UserDocument document = new UserDocument();
        document.setId(objectId);
        document.setName(UserTestHelper.VALID_NAME);
        document.setEmail(UserTestHelper.VALID_EMAIL);
        document.setPassword(UserTestHelper.VALID_PASSWORD);
        document.setUserTypeId(UserTestHelper.VALID_USER_TYPE_ID);

        User user = userMapper.toDomain(document);

        assertEquals(objectId.toHexString(), user.getId());
        assertEquals(document.getName(), user.getName());
        assertEquals(document.getEmail(), user.getEmail());
        assertEquals(document.getPassword(), user.getPassword());
        assertEquals(document.getUserTypeId(), user.getUserTypeId());
    }

    @Test
    @DisplayName("Deve mapear UserDocument para User com ID nulo")
    void shouldMapDocumentToDomainWithNullId() {
        UserDocument document = new UserDocument();
        document.setName(UserTestHelper.VALID_NAME);
        document.setEmail(UserTestHelper.VALID_EMAIL);
        document.setPassword(UserTestHelper.VALID_PASSWORD);
        document.setUserTypeId(UserTestHelper.VALID_USER_TYPE_ID);

        User user = userMapper.toDomain(document);

        assertNull(user.getId());
    }

    @Test
    @DisplayName("Deve mapear CreateUserRequestDTO para CreateUserInputDTO")
    void shouldMapCreateUserRequestToInput() {
        CreateUserRequestDTO request = new CreateUserRequestDTO(
                UserTestHelper.VALID_NAME,
                UserTestHelper.VALID_EMAIL,
                UserTestHelper.VALID_PASSWORD,
                UserTestHelper.VALID_USER_TYPE_ID
        );

        CreateUserInputDTO input = userMapper.toUserInput(request);

        assertNull(input.id());
        assertEquals(request.name(), input.name());
        assertEquals(request.email(), input.email());
        assertEquals(request.password(), input.password());
        assertEquals(request.userTypeId(), input.userTypeId());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserRequestDTO para UpdateUserInputDTO")
    void shouldMapUpdateUserRequestToInput() {
        UpdateUserRequestDTO request = new UpdateUserRequestDTO(
                "Jane Doe",
                "jane@example.com",
                "newpassword1",
                UserTestHelper.VALID_USER_TYPE_ID
        );

        UpdateUserInputDTO input = userMapper.toUpdateUserInput(request);

        assertEquals(request.name(), input.name());
        assertEquals(request.email(), input.email());
        assertEquals(request.password(), input.password());
        assertEquals(request.userTypeId(), input.userTypeId());
    }

    @Test
    @DisplayName("Deve mapear User para UserResponseDTO")
    void shouldMapUserToResponseDto() {
        User user = UserTestHelper.validUser();

        UserResponseDTO response = userMapper.toUserResponseDTO(user);

        assertEquals(user.getId(), response.id());
        assertEquals(user.getName(), response.name());
        assertEquals(user.getEmail(), response.email());
        assertEquals(user.getUserTypeId(), response.userTypeId());
    }
}
