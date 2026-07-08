package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidObjectIdException;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserTypeMapper;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserTypeDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserTypeRepository;
import com.restcontrol.restcontrol_tc2.helper.UserTypeTestHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Gateway de persistência - UserType")
class UserTypeGatewayImplTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserTypeMapper userTypeMapper;

    @InjectMocks
    private UserTypeGatewayImpl userTypeGateway;

    private UserType userType;
    private UserTypeDocument document;
    private ObjectId objectId;

    @BeforeEach
    void setUp() {
        userType = UserTypeTestHelper.validUserType();
        objectId = new ObjectId(userType.getId());
        document = new UserTypeDocument();
        document.setId(objectId);
        document.setName(userType.getName());
        document.setCode(userType.getCode());
    }

    @Test
    @DisplayName("Deve criar tipo de usuário no repositório")
    void shouldCreateUserType() {
        when(userTypeMapper.toDocument(userType)).thenReturn(document);
        when(userTypeRepository.save(document)).thenReturn(document);
        when(userTypeMapper.toDomain(document)).thenReturn(userType);

        UserType result = userTypeGateway.create(userType);

        assertEquals(userType, result);
        verify(userTypeMapper).toDocument(userType);
        verify(userTypeRepository).save(document);
        verify(userTypeMapper).toDomain(document);
    }

    @Test
    @DisplayName("Deve atualizar tipo de usuário no repositório")
    void shouldUpdateUserType() {
        when(userTypeMapper.toDocument(userType)).thenReturn(document);
        when(userTypeRepository.save(document)).thenReturn(document);
        when(userTypeMapper.toDomain(document)).thenReturn(userType);

        UserType result = userTypeGateway.update(userType);

        assertEquals(userType, result);
        verify(userTypeRepository).save(document);
    }

    @Test
    @DisplayName("Deve listar todos os tipos de usuário")
    void shouldListAllUserTypes() {
        when(userTypeRepository.findAll()).thenReturn(List.of(document));
        when(userTypeMapper.toDomain(document)).thenReturn(userType);

        List<UserType> result = userTypeGateway.listAll();

        assertEquals(1, result.size());
        assertEquals(userType, result.getFirst());
    }

    @Test
    @DisplayName("Deve buscar tipo de usuário por ID")
    void shouldGetUserTypeById() {
        when(userTypeRepository.findById(objectId)).thenReturn(Optional.of(document));
        when(userTypeMapper.toDomain(document)).thenReturn(userType);

        Optional<UserType> result = userTypeGateway.getById(userType.getId());

        assertTrue(result.isPresent());
        assertEquals(userType, result.get());
    }

    @Test
    @DisplayName("Deve retornar vazio quando tipo não for encontrado")
    void shouldReturnEmptyWhenUserTypeNotFound() {
        when(userTypeRepository.findById(objectId)).thenReturn(Optional.empty());

        Optional<UserType> result = userTypeGateway.getById(userType.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve remover tipo de usuário por ID")
    void shouldDeleteUserType() {
        userTypeGateway.delete(userType.getId());

        verify(userTypeRepository).deleteById(objectId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o ID for inválido")
    void shouldThrowWhenIdIsInvalid() {
        assertThrows(InvalidObjectIdException.class, () -> userTypeGateway.getById("invalid-id"));
        assertThrows(InvalidObjectIdException.class, () -> userTypeGateway.getById(null));
        assertThrows(InvalidObjectIdException.class, () -> userTypeGateway.getById(""));
        assertThrows(InvalidObjectIdException.class, () -> userTypeGateway.delete("invalid-id"));
        assertThrows(InvalidObjectIdException.class, () -> userTypeGateway.delete(""));
        assertThrows(InvalidObjectIdException.class, () -> userTypeGateway.delete(null));
    }
}
