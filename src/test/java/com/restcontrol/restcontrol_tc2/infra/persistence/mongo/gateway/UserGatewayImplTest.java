package com.restcontrol.restcontrol_tc2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidObjectIdException;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserMapper;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.entity.UserDocument;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserRepository;
import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserGatewayImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserGatewayImpl userGateway;

    private User user;
    private UserDocument document;
    private ObjectId objectId;

    @BeforeEach
    void setUp() {
        user = UserTestFixtures.validUser();
        objectId = new ObjectId(user.getId());
        document = new UserDocument();
        document.setId(objectId);
        document.setName(user.getName());
        document.setEmail(user.getEmail());
        document.setPassword(user.getPassword());
        document.setUserTypeId(user.getUserTypeId());
    }

    @Test
    @DisplayName("Should create an user")
    void shouldCreateUser() {
        when(userMapper.toDocument(user)).thenReturn(document);
        when(userRepository.save(document)).thenReturn(document);
        when(userMapper.toDomain(document)).thenReturn(user);

        User result = userGateway.create(user);

        assertEquals(user, result);
        verify(userMapper).toDocument(user);
        verify(userRepository).save(document);
        verify(userMapper).toDomain(document);
    }

    @Test
    @DisplayName("Should update an user")
    void shouldUpdateUser() {
        when(userMapper.toDocument(user)).thenReturn(document);
        when(userRepository.save(document)).thenReturn(document);
        when(userMapper.toDomain(document)).thenReturn(user);

        User result = userGateway.update(user);

        assertEquals(user, result);
        verify(userRepository).save(document);
    }

    @Test
    @DisplayName("Should get an user by the ID")
    void shouldGetUserById() {
        when(userRepository.findById(objectId)).thenReturn(Optional.of(document));
        when(userMapper.toDomain(document)).thenReturn(user);

        Optional<User> result = userGateway.getById(user.getId());

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    @DisplayName("Should return empty when user not found")
    void shouldReturnEmptyWhenUserNotFound() {
        when(userRepository.findById(objectId)).thenReturn(Optional.empty());

        Optional<User> result = userGateway.getById(user.getId());

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should delete an user")
    void shouldDeleteUser() {
        userGateway.delete(user.getId());

        verify(userRepository).deleteById(objectId);
    }

    @Test
    @DisplayName("Should throw an exception when ID is invalid")
    void shouldThrowWhenIdIsInvalid() {
        assertThrows(InvalidObjectIdException.class, () -> userGateway.getById("invalid-id"));
        assertThrows(InvalidObjectIdException.class, () -> userGateway.delete(""));
    }
}
