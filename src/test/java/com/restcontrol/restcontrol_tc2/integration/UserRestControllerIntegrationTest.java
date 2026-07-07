package com.restcontrol.restcontrol_tc2.integration;

import com.restcontrol.restcontrol_tc2.support.UserTestFixtures;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Integração - User")
class UserRestControllerIntegrationTest extends AbstractMongoIntegrationTest {

    @Autowired
    private IntegrationTestDataFactory testDataFactory;

    @Test
    @DisplayName("Deve criar usuário com tipo de usuário seedado e persistir no MongoDB")
    void shouldCreateUserWithSeededUserType() throws Exception {
        var userTypeId = testDataFactory.getCustomerUserTypeId();
        var request = new com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserRequestDTO(
                UserTestFixtures.VALID_NAME,
                "integration-user@example.com",
                UserTestFixtures.VALID_PASSWORD,
                userTypeId
        );

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(UserTestFixtures.VALID_NAME))
                .andExpect(jsonPath("$.email").value("integration-user@example.com"))
                .andExpect(jsonPath("$.userTypeId").value(userTypeId))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @DisplayName("Deve retornar 404 ao criar usuário com tipo de usuário inexistente")
    void shouldReturnNotFoundWhenUserTypeDoesNotExist() throws Exception {
        var request = new com.restcontrol.restcontrol_tc2.infra.dto.request.CreateUserRequestDTO(
                UserTestFixtures.VALID_NAME,
                "missing-type@example.com",
                UserTestFixtures.VALID_PASSWORD,
                new ObjectId().toHexString()
        );

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("UserType not found"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao criar usuário com payload inválido")
    void shouldReturnBadRequestWhenCreatePayloadIsInvalid() throws Exception {
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDataFactory.invalidCreateUserRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"))
                .andExpect(jsonPath("$.errors").isMap());
    }

    @Test
    @DisplayName("Deve buscar, atualizar e remover usuário em fluxo completo")
    void shouldManageUserLifecycle() throws Exception {
        var createdUser = testDataFactory.createCustomerUser("lifecycle-user@example.com");
        var updateRequest = testDataFactory.validUpdateUserRequest(testDataFactory.getCustomerUserTypeId());

        mockMvc.perform(get("/v1/users/{id}", createdUser.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdUser.id()))
                .andExpect(jsonPath("$.email").value("lifecycle-user@example.com"));

        mockMvc.perform(put("/v1/users/{id}", createdUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane@example.com"));

        mockMvc.perform(delete("/v1/users/{id}", createdUser.id()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/users/{id}", createdUser.id()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User not found"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao atualizar usuário com payload inválido")
    void shouldReturnBadRequestWhenUpdatePayloadIsInvalid() throws Exception {
        var createdUser = testDataFactory.createCustomerUser("invalid-update@example.com");

        mockMvc.perform(put("/v1/users/{id}", createdUser.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDataFactory.invalidUpdateUserRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar usuário inexistente")
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        mockMvc.perform(get("/v1/users/{id}", new ObjectId().toHexString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User not found"));
    }

    @Test
    @DisplayName("Deve listar tipos de usuário seedados na inicialização")
    void shouldListSeededUserTypes() throws Exception {
        mockMvc.perform(get("/v1/user-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].code", hasItem("CUSTOMER")))
                .andExpect(jsonPath("$[*].code", hasItem("RESTAURANT_OWNER")));
    }
}
