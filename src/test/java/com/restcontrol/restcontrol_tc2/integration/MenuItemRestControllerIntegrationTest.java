package com.restcontrol.restcontrol_tc2.integration;

import com.restcontrol.restcontrol_tc2.support.MenuItemTestFixtures;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Integração - MenuItem")
class MenuItemRestControllerIntegrationTest extends AbstractMongoIntegrationTest {

    @Autowired
    private IntegrationTestDataFactory testDataFactory;

    @Test
    @DisplayName("Deve criar item de cardápio quando restaurante existir")
    void shouldCreateMenuItemWhenRestaurantExists() throws Exception {
        var context = testDataFactory.createRestaurantContext();
        var request = new com.restcontrol.restcontrol_tc2.infra.dto.request.CreateMenuItemRequestDTO(
                MenuItemTestFixtures.VALID_NAME,
                MenuItemTestFixtures.VALID_DESCRIPTION,
                MenuItemTestFixtures.VALID_PRICE,
                false,
                MenuItemTestFixtures.VALID_IMAGE_URL,
                context.restaurant().id(),
                true
        );

        mockMvc.perform(post("/v1/menuitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(MenuItemTestFixtures.VALID_NAME))
                .andExpect(jsonPath("$.restaurantId").value(context.restaurant().id()))
                .andExpect(jsonPath("$.price").value(MenuItemTestFixtures.VALID_PRICE));
    }

    @Test
    @DisplayName("Deve retornar 404 ao criar item com restaurante inexistente")
    void shouldReturnNotFoundWhenRestaurantDoesNotExist() throws Exception {
        var request = new com.restcontrol.restcontrol_tc2.infra.dto.request.CreateMenuItemRequestDTO(
                MenuItemTestFixtures.VALID_NAME,
                MenuItemTestFixtures.VALID_DESCRIPTION,
                MenuItemTestFixtures.VALID_PRICE,
                false,
                MenuItemTestFixtures.VALID_IMAGE_URL,
                new ObjectId().toHexString(),
                true
        );

        mockMvc.perform(post("/v1/menuitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao criar item com payload inválido")
    void shouldReturnBadRequestWhenCreatePayloadIsInvalid() throws Exception {
        mockMvc.perform(post("/v1/menuitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDataFactory.invalidCreateMenuItemRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"))
                .andExpect(jsonPath("$.errors").isMap());
    }

    @Test
    @DisplayName("Deve listar, buscar, atualizar e remover item em fluxo completo")
    void shouldManageMenuItemLifecycle() throws Exception {
        var context = testDataFactory.createRestaurantContext();
        var createdItem = testDataFactory.createMenuItem(context.restaurant().id());
        var updateRequest = testDataFactory.validUpdateMenuItemRequest(context.restaurant().id());

        mockMvc.perform(get("/v1/menuitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(createdItem.id()));

        mockMvc.perform(get("/v1/menuitems/{id}", createdItem.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MenuItemTestFixtures.VALID_NAME));

        mockMvc.perform(put("/v1/menuitems/{id}", createdItem.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pepperoni Pizza"))
                .andExpect(jsonPath("$.price").value(34.90))
                .andExpect(jsonPath("$.active").value(false));

        mockMvc.perform(delete("/v1/menuitems/{id}", createdItem.id()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/v1/menuitems/{id}", createdItem.id()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Menu item not found"));
    }

    @Test
    @DisplayName("Deve retornar 400 ao atualizar item com payload inválido")
    void shouldReturnBadRequestWhenUpdatePayloadIsInvalid() throws Exception {
        var context = testDataFactory.createRestaurantContext();
        var createdItem = testDataFactory.createMenuItem(context.restaurant().id());

        mockMvc.perform(put("/v1/menuitems/{id}", createdItem.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDataFactory.invalidUpdateMenuItemRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    @DisplayName("Deve retornar 404 ao buscar item inexistente")
    void shouldReturnNotFoundWhenMenuItemDoesNotExist() throws Exception {
        mockMvc.perform(get("/v1/menuitems/{id}", new ObjectId().toHexString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Menu item not found"));
    }

    @Test
    @DisplayName("Deve validar cadeia completa de dependências entre User, Restaurant e MenuItem")
    void shouldValidateFullDependencyChain() throws Exception {
        var owner = testDataFactory.createRestaurantOwnerUser("chain-owner@example.com");
        var restaurant = testDataFactory.createRestaurant(owner.id());
        var menuItem = testDataFactory.createMenuItem(restaurant.id());

        mockMvc.perform(get("/v1/users/{id}", owner.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userTypeId").value(testDataFactory.getRestaurantOwnerUserTypeId()));

        mockMvc.perform(get("/v1/restaurants/{id}", restaurant.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerId").value(owner.id()));

        mockMvc.perform(get("/v1/menuitems/{id}", menuItem.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(restaurant.id()));
    }
}
