package com.restcontrol.restcontrol_tc2.integration;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.infra.persistence.mongo.repository.UserTypeRepository;
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

@DisplayName("Integração - UserType")
class UserTypeIntegrationTest extends AbstractMongoIntegrationTest {

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Test
    @DisplayName("Deve listar tipos de usuário seedados")
    void shouldListSeededUserTypes() throws Exception {
        mockMvc.perform(get("/v1/user-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].code", hasItem(UserType.CUSTOMER_CODE)))
                .andExpect(jsonPath("$[*].code", hasItem(UserType.RESTAURANT_OWNER_CODE)));
    }

    @Test
    @DisplayName("Deve criar tipo de usuário e persistir no MongoDB")
    void shouldCreateUserType() throws Exception {
        var body = """
                {
                    "name": "Administrador",
                    "code": "ADMIN"
                }
                """;

        mockMvc.perform(post("/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Administrador"))
                .andExpect(jsonPath("$.code").value("ADMIN"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao criar tipo com payload inválido")
    void shouldReturnBadRequestWhenCreatePayloadIsInvalid() throws Exception {
        var body = """
                {
                    "name": "",
                    "code": ""
                }
                """;

        mockMvc.perform(post("/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    @DisplayName("Deve buscar, atualizar e remover tipo de usuário em fluxo completo")
    void shouldManageUserTypeLifecycle() throws Exception {
        var createBody = """
                {
                    "name": "Gerente",
                    "code": "MANAGER"
                }
                """;

        var createResponse = mockMvc.perform(post("/v1/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdId = objectMapper.readValue(createResponse, UserType.class).getId();

        mockMvc.perform(get("/v1/user-types/{id}", createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.code").value("MANAGER"));

        var updateBody = """
                {
                    "name": "Gerente Atualizado",
                    "code": "MANAGER_UPDATED"
                }
                """;

        mockMvc.perform(put("/v1/user-types/{id}", createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gerente Atualizado"))
                .andExpect(jsonPath("$.code").value("MANAGER_UPDATED"));

        mockMvc.perform(delete("/v1/user-types/{id}", createdId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/user-types/{id}", createdId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("UserType not found"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar tipo inexistente")
    void shouldReturnNotFoundWhenUserTypeDoesNotExist() throws Exception {
        mockMvc.perform(get("/v1/user-types/{id}", new ObjectId().toHexString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("UserType not found"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao atualizar tipo inexistente")
    void shouldReturnNotFoundWhenUpdatingNonExistentUserType() throws Exception {
        var body = """
                {
                    "name": "Tipo Inexistente",
                    "code": "MISSING"
                }
                """;

        mockMvc.perform(put("/v1/user-types/{id}", new ObjectId().toHexString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("UserType not found"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao remover tipo inexistente")
    void shouldReturnNotFoundWhenDeletingNonExistentUserType() throws Exception {
        mockMvc.perform(delete("/v1/user-types/{id}", new ObjectId().toHexString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("UserType not found"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao atualizar tipo com payload inválido")
    void shouldReturnBadRequestWhenUpdatePayloadIsInvalid() throws Exception {
        var customerTypeId = userTypeRepository.findByCode(UserType.CUSTOMER_CODE)
                .orElseThrow()
                .getId()
                .toHexString();

        var body = """
                {
                    "name": "",
                    "code": ""
                }
                """;

        mockMvc.perform(put("/v1/user-types/{id}", customerTypeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }
}
