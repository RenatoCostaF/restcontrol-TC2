package com.restcontrol.restcontrol_tc2.infra.mapper;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserTypeDocument;
import com.restcontrol.restcontrol_tc2.helper.UserTypeTestHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Mapper de infra - UserType")
class UserTypeMapperTest {

    private UserTypeMapper userTypeMapper;

    @BeforeEach
    void setUp() {
        userTypeMapper = Mappers.getMapper(UserTypeMapper.class);
    }

    @Test
    @DisplayName("Deve mapear UserType para UserTypeDocument")
    void shouldMapUserTypeToDocument() {
        UserType userType = UserTypeTestHelper.validUserType();

        UserTypeDocument document = userTypeMapper.toDocument(userType);

        assertEquals(new ObjectId(userType.getId()), document.getId());
        assertEquals(userType.getName(), document.getName());
        assertEquals(userType.getCode(), document.getCode());
    }

    @Test
    @DisplayName("Deve mapear UserType para UserTypeDocument com ID nulo")
    void shouldMapUserTypeToDocumentWithNullId() {
        UserType userType = new UserType(null, UserTypeTestHelper.VALID_NAME, UserTypeTestHelper.VALID_CODE);

        UserTypeDocument document = userTypeMapper.toDocument(userType);

        assertNull(document.getId());
        assertEquals(userType.getName(), document.getName());
    }

    @Test
    @DisplayName("Deve mapear UserType para UserTypeDocument com ID em branco")
    void shouldMapUserTypeToDocumentWithBlankId() {
        UserType userType = new UserType("", UserTypeTestHelper.VALID_NAME, UserTypeTestHelper.VALID_CODE);

        UserTypeDocument document = userTypeMapper.toDocument(userType);

        assertNull(document.getId());
        assertEquals(userType.getName(), document.getName());
    }

    @Test
    @DisplayName("Deve mapear UserTypeDocument para UserType")
    void shouldMapDocumentToDomain() {
        ObjectId objectId = new ObjectId();
        UserTypeDocument document = new UserTypeDocument();
        document.setId(objectId);
        document.setName(UserTypeTestHelper.VALID_NAME);
        document.setCode(UserTypeTestHelper.VALID_CODE);

        UserType userType = userTypeMapper.toDomain(document);

        assertEquals(objectId.toHexString(), userType.getId());
        assertEquals(document.getName(), userType.getName());
        assertEquals(document.getCode(), userType.getCode());
    }

    @Test
    @DisplayName("Deve mapear UserTypeDocument para UserType com ID nulo")
    void shouldMapDocumentToDomainWithNullId() {
        UserTypeDocument document = new UserTypeDocument();
        document.setName(UserTypeTestHelper.VALID_NAME);
        document.setCode(UserTypeTestHelper.VALID_CODE);

        UserType userType = userTypeMapper.toDomain(document);

        assertNull(userType.getId());
    }

    @Test
    @DisplayName("Deve mapear CreateUserTypeRequestDTO para CreateUserTypeInputDTO")
    void shouldMapCreateUserTypeRequestToInput() {
        CreateUserTypeRequestDTO request = new CreateUserTypeRequestDTO(
                UserTypeTestHelper.VALID_NAME,
                UserTypeTestHelper.VALID_CODE
        );

        CreateUserTypeInputDTO input = userTypeMapper.toUserTypeInput(request);

        assertNull(input.id());
        assertEquals(request.name(), input.name());
        assertEquals(request.code(), input.code());
    }

    @Test
    @DisplayName("Deve mapear UpdateUserTypeRequestDTO para UpdateUserTypeInputDTO")
    void shouldMapUpdateUserTypeRequestToInput() {
        UpdateUserTypeRequestDTO request = new UpdateUserTypeRequestDTO(
                "Tipo Atualizado",
                "UPDATED_TYPE"
        );

        UpdateUserTypeInputDTO input = userTypeMapper.toUpdateUserTypeInput(request);

        assertEquals(request.name(), input.name());
        assertEquals(request.code(), input.code());
    }

    @Test
    @DisplayName("Deve mapear UserType para UserTypeResponseDTO")
    void shouldMapUserTypeToResponseDto() {
        UserType userType = UserTypeTestHelper.validUserType();

        UserTypeResponseDTO response = userTypeMapper.toUserTypeResponseDTO(userType);

        assertEquals(userType.getId(), response.id());
        assertEquals(userType.getName(), response.name());
        assertEquals(userType.getCode(), response.code());
    }
}
