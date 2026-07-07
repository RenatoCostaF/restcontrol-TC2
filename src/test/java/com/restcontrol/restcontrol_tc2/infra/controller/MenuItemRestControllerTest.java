package com.restcontrol.restcontrol_tc2.infra.controller;

import com.restcontrol.restcontrol_tc2.domain.controller.MenuItemController;
import com.restcontrol.restcontrol_tc2.domain.dto.CreateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_tc2.infra.dto.request.CreateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateMenuItemRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.MenuItemResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.MenuItemMapper;
import com.restcontrol.restcontrol_tc2.support.MenuItemTestFixtures;
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

@WebMvcTest(MenuItemRestController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("RestController - MenuItem")
class MenuItemRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private MenuItemController menuItemController;

    @MockitoBean
    private MenuItemMapper menuItemMapper;

    @Test
    @DisplayName("POST /v1/menuitems deve criar item de cardápio com sucesso")
    void shouldCreateMenuItem() throws Exception {
        var request = new CreateMenuItemRequestDTO(
                MenuItemTestFixtures.VALID_NAME,
                MenuItemTestFixtures.VALID_DESCRIPTION,
                MenuItemTestFixtures.VALID_PRICE,
                false,
                MenuItemTestFixtures.VALID_IMAGE_URL,
                MenuItemTestFixtures.VALID_RESTAURANT_ID,
                true
        );
        var input = MenuItemTestFixtures.createMenuItemInput();
        var menuItem = MenuItemTestFixtures.validMenuItem();
        var response = new MenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableOnlyInRestaurant(),
                menuItem.getImageUrl(),
                menuItem.getRestaurantId(),
                menuItem.getActive()
        );

        when(menuItemMapper.toMenuItemInput(request)).thenReturn(input);
        when(menuItemController.create(input)).thenReturn(menuItem);
        when(menuItemMapper.toMenuItemResponseDTO(menuItem)).thenReturn(response);

        mockMvc.perform(post("/v1/menuitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(menuItem.getId()))
                .andExpect(jsonPath("$.name").value(menuItem.getName()))
                .andExpect(jsonPath("$.price").value(menuItem.getPrice()));
    }

    @Test
    @DisplayName("POST /v1/menuitems deve retornar 400 quando o payload for inválido")
    void shouldReturnBadRequestWhenCreateMenuItemPayloadIsInvalid() throws Exception {
        var request = new CreateMenuItemRequestDTO("", "", -1.0, null, "", "", null);

        mockMvc.perform(post("/v1/menuitems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    @DisplayName("PUT /v1/menuitems/{id} deve atualizar item de cardápio com sucesso")
    void shouldUpdateMenuItem() throws Exception {
        var request = new UpdateMenuItemRequestDTO(
                "Pepperoni Pizza",
                "Pizza with pepperoni",
                34.90,
                true,
                "https://example.com/pepperoni.jpg",
                MenuItemTestFixtures.VALID_RESTAURANT_ID,
                false
        );
        var input = new UpdateMenuItemInputDTO(
                request.name(),
                request.description(),
                request.price(),
                request.availableOnlyInRestaurant(),
                request.imageUrl(),
                request.restaurantId(),
                request.active()
        );
        var menuItem = MenuItemTestFixtures.validMenuItem();
        var response = new MenuItemResponseDTO(
                menuItem.getId(),
                request.name(),
                request.description(),
                request.price(),
                request.availableOnlyInRestaurant(),
                request.imageUrl(),
                request.restaurantId(),
                request.active()
        );

        when(menuItemMapper.toUpdateMenuItemInput(request)).thenReturn(input);
        when(menuItemController.update(input, menuItem.getId())).thenReturn(menuItem);
        when(menuItemMapper.toMenuItemResponseDTO(menuItem)).thenReturn(response);

        mockMvc.perform(put("/v1/menuitems/{id}", menuItem.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.price").value(request.price()));
    }

    @Test
    @DisplayName("PUT /v1/menuitems/{id} deve retornar 400 quando o payload for inválido")
    void shouldReturnBadRequestWhenUpdateMenuItemPayloadIsInvalid() throws Exception {
        var request = new UpdateMenuItemRequestDTO("", "", -1.0, null, "", "", null);

        mockMvc.perform(put("/v1/menuitems/{id}", MenuItemTestFixtures.VALID_MENU_ITEM_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    @DisplayName("GET /v1/menuitems deve listar todos os itens de cardápio")
    void shouldListAllMenuItems() throws Exception {
        var menuItem = MenuItemTestFixtures.validMenuItem();
        var response = new MenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableOnlyInRestaurant(),
                menuItem.getImageUrl(),
                menuItem.getRestaurantId(),
                menuItem.getActive()
        );

        when(menuItemController.listAll()).thenReturn(List.of(menuItem));
        when(menuItemMapper.toMenuItemResponseDTO(menuItem)).thenReturn(response);

        mockMvc.perform(get("/v1/menuitems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(menuItem.getId()))
                .andExpect(jsonPath("$[0].name").value(menuItem.getName()));
    }

    @Test
    @DisplayName("GET /v1/menuitems/{id} deve retornar item encontrado")
    void shouldGetMenuItemById() throws Exception {
        var menuItem = MenuItemTestFixtures.validMenuItem();
        var response = new MenuItemResponseDTO(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableOnlyInRestaurant(),
                menuItem.getImageUrl(),
                menuItem.getRestaurantId(),
                menuItem.getActive()
        );

        when(menuItemController.getById(menuItem.getId())).thenReturn(menuItem);
        when(menuItemMapper.toMenuItemResponseDTO(menuItem)).thenReturn(response);

        mockMvc.perform(get("/v1/menuitems/{id}", menuItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(menuItem.getId()));
    }

    @Test
    @DisplayName("GET /v1/menuitems/{id} deve retornar 404 quando item não existir")
    void shouldReturnNotFoundWhenMenuItemDoesNotExist() throws Exception {
        var menuItemId = MenuItemTestFixtures.VALID_MENU_ITEM_ID;
        when(menuItemController.getById(menuItemId))
                .thenThrow(new MenuItemNotFoundException("Menu item not found"));

        mockMvc.perform(get("/v1/menuitems/{id}", menuItemId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Menu item not found"));
    }

    @Test
    @DisplayName("DELETE /v1/menuitems/{id} deve remover item com sucesso")
    void shouldDeleteMenuItem() throws Exception {
        var menuItemId = MenuItemTestFixtures.VALID_MENU_ITEM_ID;

        mockMvc.perform(delete("/v1/menuitems/{id}", menuItemId))
                .andExpect(status().isOk());

        verify(menuItemController).delete(menuItemId);
    }

    @Test
    @DisplayName("DELETE /v1/menuitems/{id} deve retornar 404 quando item não existir")
    void shouldReturnNotFoundWhenDeletingNonExistentMenuItem() throws Exception {
        var menuItemId = MenuItemTestFixtures.VALID_MENU_ITEM_ID;
        doThrow(new MenuItemNotFoundException("Menu item not found"))
                .when(menuItemController).delete(menuItemId);

        mockMvc.perform(delete("/v1/menuitems/{id}", menuItemId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Menu item not found"));
    }
}
