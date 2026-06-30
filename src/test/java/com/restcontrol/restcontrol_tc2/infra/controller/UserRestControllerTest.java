package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.UserController;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserInputDTO;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserMapper;
import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
@Import(GlobalExceptionHandler.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private tools.jackson.databind.ObjectMapper objectMapper;

    @MockitoBean
    private UserController userController;

    @MockitoBean
    private UserMapper userMapper;

    @Test
    void shouldCreateUser() throws Exception {
        var request = new CreateUserRequestDTO(
                UserTestFixtures.VALID_NAME,
                UserTestFixtures.VALID_EMAIL,
                UserTestFixtures.VALID_PASSWORD,
                UserTestFixtures.VALID_USER_TYPE_ID
        );
        var input = UserTestFixtures.createUserInput();
        var user = UserTestFixtures.validUser();
        var response = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserTypeId()
        );

        when(userMapper.toUserInput(request)).thenReturn(input);
        when(userController.create(input)).thenReturn(user);
        when(userMapper.toUserResponseDTO(user)).thenReturn(response);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.userTypeId").value(user.getUserTypeId()));
    }

    @Test
    void shouldReturnBadRequestWhenCreateUserPayloadIsInvalid() throws Exception {
        var request = new CreateUserRequestDTO("", "invalid-email", "short", "");

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        var request = new UpdateUserRequestDTO(
                "Jane Doe",
                "jane@example.com",
                "newpassword1",
                UserTestFixtures.VALID_USER_TYPE_ID
        );
        var input = new UpdateUserInputDTO(
                request.name(),
                request.email(),
                request.password(),
                request.userTypeId()
        );
        var user = UserTestFixtures.validUser();
        var response = new UserResponseDTO(
                user.getId(),
                request.name(),
                request.email(),
                request.userTypeId()
        );

        when(userMapper.toUpdateUserInput(request)).thenReturn(input);
        when(userController.update(input, user.getId())).thenReturn(user);
        when(userMapper.toUserResponseDTO(user)).thenReturn(response);

        mockMvc.perform(put("/v1/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.email").value(request.email()));
    }

    @Test
    void shouldGetUserById() throws Exception {
        var user = UserTestFixtures.validUser();
        var response = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getUserTypeId()
        );

        when(userController.getById(user.getId())).thenReturn(user);
        when(userMapper.toUserResponseDTO(user)).thenReturn(response);

        mockMvc.perform(get("/v1/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        var userId = UserTestFixtures.VALID_USER_ID;
        when(userController.getById(userId)).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/v1/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User not found"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        var userId = UserTestFixtures.VALID_USER_ID;

        mockMvc.perform(delete("/v1/users/{id}", userId))
                .andExpect(status().isOk());

        verify(userController).delete(userId);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentUser() throws Exception {
        var userId = UserTestFixtures.VALID_USER_ID;
        doThrow(new UserNotFoundException("User not found")).when(userController).delete(userId);

        mockMvc.perform(delete("/v1/users/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User not found"));
    }
}
