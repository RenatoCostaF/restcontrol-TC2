package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.UserTypeController;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateUserTypeInputDTO;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateUserTypeRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.UserTypeResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.UserTypeMapper;
import com.restcontrol.restcontrol_tc2.helper.UserTypeTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserTypeRestController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("RestController - UserType")
class UserTypeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private UserTypeController userTypeController;

    @MockitoBean
    private UserTypeMapper userTypeMapper;

    @Test
    @DisplayName("POST /v1/user-types deve criar tipo de usuário com sucesso")
    void shouldCreateUserType() throws Exception {
        var request = new CreateUserTypeRequestDTO(UserTypeTestHelper.VALID_NAME, UserTypeTestHelper.VALID_CODE);
        var input = UserTypeTestHelper.createUserTypeInput();
        var userType = UserTypeTestHelper.validUserType();
        var response = new UserTypeResponseDTO(userType.getId(), userType.getName(), userType.getCode());

        when(userTypeMapper.toUserTypeInput(request)).thenReturn(input);
        when(userTypeController.create(input)).thenReturn(userType);
        when(userTypeMapper.toUserTypeResponseDTO(userType)).thenReturn(response);

        mockMvc.perform(post("/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userType.getId()))
                .andExpect(jsonPath("$.name").value(userType.getName()))
                .andExpect(jsonPath("$.code").value(userType.getCode()));
    }

    @Test
    @DisplayName("POST /v1/user-types deve retornar 400 quando o payload for inválido")
    void shouldReturnBadRequestWhenCreatePayloadIsInvalid() throws Exception {
        var request = new CreateUserTypeRequestDTO("", "");

        mockMvc.perform(post("/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    @DisplayName("PUT /v1/user-types/{id} deve atualizar tipo de usuário com sucesso")
    void shouldUpdateUserType() throws Exception {
        var request = new UpdateUserTypeRequestDTO("Tipo Atualizado", "UPDATED_TYPE");
        var input = new UpdateUserTypeInputDTO(request.name(), request.code());
        var userType = UserTypeTestHelper.validUserType();
        var response = new UserTypeResponseDTO(userType.getId(), request.name(), request.code());

        when(userTypeMapper.toUpdateUserTypeInput(request)).thenReturn(input);
        when(userTypeController.update(input, userType.getId())).thenReturn(userType);
        when(userTypeMapper.toUserTypeResponseDTO(userType)).thenReturn(response);

        mockMvc.perform(put("/v1/user-types/{id}", userType.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.code").value(request.code()));
    }

    @Test
    @DisplayName("PUT /v1/user-types/{id} deve retornar 400 quando o payload for inválido")
    void shouldReturnBadRequestWhenUpdatePayloadIsInvalid() throws Exception {
        var request = new UpdateUserTypeRequestDTO("", "");

        mockMvc.perform(put("/v1/user-types/{id}", UserTypeTestHelper.VALID_USER_TYPE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    @DisplayName("PUT /v1/user-types/{id} deve retornar 404 quando tipo não existir")
    void shouldReturnNotFoundWhenUpdatingNonExistentUserType() throws Exception {
        var request = new UpdateUserTypeRequestDTO("Tipo Atualizado", "UPDATED_TYPE");
        var input = new UpdateUserTypeInputDTO(request.name(), request.code());

        when(userTypeMapper.toUpdateUserTypeInput(request)).thenReturn(input);
        when(userTypeController.update(input, UserTypeTestHelper.VALID_USER_TYPE_ID))
                .thenThrow(new UserTypeNotFoundException("UserType not found"));

        mockMvc.perform(put("/v1/user-types/{id}", UserTypeTestHelper.VALID_USER_TYPE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("UserType not found"));
    }

    @Test
    @DisplayName("GET /v1/user-types deve listar todos os tipos de usuário")
    void shouldListAllUserTypes() throws Exception {
        var userType = UserTypeTestHelper.validUserType();
        var response = new UserTypeResponseDTO(userType.getId(), userType.getName(), userType.getCode());

        when(userTypeController.listAll()).thenReturn(List.of(userType));
        when(userTypeMapper.toUserTypeResponseDTO(userType)).thenReturn(response);

        mockMvc.perform(get("/v1/user-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(userType.getId()))
                .andExpect(jsonPath("$[0].code").value(userType.getCode()));
    }

    @Test
    @DisplayName("GET /v1/user-types/{id} deve retornar tipo encontrado")
    void shouldGetUserTypeById() throws Exception {
        var userType = UserTypeTestHelper.validUserType();
        var response = new UserTypeResponseDTO(userType.getId(), userType.getName(), userType.getCode());

        when(userTypeController.getById(userType.getId())).thenReturn(userType);
        when(userTypeMapper.toUserTypeResponseDTO(userType)).thenReturn(response);

        mockMvc.perform(get("/v1/user-types/{id}", userType.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userType.getId()));
    }

    @Test
    @DisplayName("GET /v1/user-types/{id} deve retornar 404 quando tipo não existir")
    void shouldReturnNotFoundWhenUserTypeDoesNotExist() throws Exception {
        when(userTypeController.getById(UserTypeTestHelper.VALID_USER_TYPE_ID))
                .thenThrow(new UserTypeNotFoundException("UserType not found"));

        mockMvc.perform(get("/v1/user-types/{id}", UserTypeTestHelper.VALID_USER_TYPE_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("UserType not found"));
    }

    @Test
    @DisplayName("DELETE /v1/user-types/{id} deve remover tipo com sucesso")
    void shouldDeleteUserType() throws Exception {
        mockMvc.perform(delete("/v1/user-types/{id}", UserTypeTestHelper.VALID_USER_TYPE_ID))
                .andExpect(status().isOk());

        verify(userTypeController).delete(UserTypeTestHelper.VALID_USER_TYPE_ID);
    }

    @Test
    @DisplayName("DELETE /v1/user-types/{id} deve retornar 404 quando tipo não existir")
    void shouldReturnNotFoundWhenDeletingNonExistentUserType() throws Exception {
        doThrow(new UserTypeNotFoundException("UserType not found"))
                .when(userTypeController).delete(UserTypeTestHelper.VALID_USER_TYPE_ID);

        mockMvc.perform(delete("/v1/user-types/{id}", UserTypeTestHelper.VALID_USER_TYPE_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("UserType not found"));
    }
}
