package com.restcontrol.restcontrol_tc2.infra.controller;

import tools.jackson.databind.ObjectMapper;
import com.restcontrol.restcontrol_tc2.domain.controller.RestaurantController;
import com.restcontrol.restcontrol_tc2.domain.dto.CreateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidRestaurantOwnerTypeException;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.infra.dto.request.RestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.request.UpdateRestaurantRequestDTO;
import com.restcontrol.restcontrol_tc2.infra.dto.response.RestaurantResponseDTO;
import com.restcontrol.restcontrol_tc2.infra.mapper.RestaurantMapper;
import com.restcontrol.restcontrol_tc2.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantRestController.class)
@Import(GlobalExceptionHandler.class)
class RestaurantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RestaurantController restaurantController;

    @MockitoBean
    private RestaurantMapper restaurantMapper;

    @Test
    void shouldCreateRestaurant() throws Exception {
        var request = new RestaurantRequestDTO(
                "Rest Test", "Awesome Street, 1001", "Italian",
                "from 6PM - 1AM", RestaurantHelper.OWNER_ID
        );
        var restaurant = RestaurantHelper.createRestaurant();
        var response = new RestaurantResponseDTO(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getCuisineType(), restaurant.getOpeningHours(), restaurant.getOwnerId()
        );

        when(restaurantMapper.toCreateRestaurantInput(request)).thenReturn(mock(CreateRestaurantInputDTO.class));
        when(restaurantController.create(any())).thenReturn(restaurant);
        when(restaurantMapper.toRestaurantResponseDTO(restaurant)).thenReturn(response);

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(restaurant.getName()))
                .andExpect(jsonPath("$.address").value(restaurant.getAddress()));
    }

    @Test
    void shouldReturnBadRequestWhenCreatePayloadIsInvalid() throws Exception {
        var request = new RestaurantRequestDTO("", "", "", "", "");

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenUpdatePayloadIsInvalid() throws Exception {
        var request = new UpdateRestaurantRequestDTO("", "", "", "", "");

        mockMvc.perform(put("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentRestaurant() throws Exception {
        var request = new UpdateRestaurantRequestDTO(
                "New Name", "New Address, 999", "Japanese",
                "from 5PM - 11PM", RestaurantHelper.OWNER_ID
        );

        when(restaurantMapper.toUpdateRestaurantInput(request)).thenReturn(mock(UpdateRestaurantInputDTO.class));
        when(restaurantController.update(any(), eq(RestaurantHelper.RESTAURANT_ID)))
                .thenThrow(new RestaurantNotFoundException("Restaurant not found"));

        mockMvc.perform(put("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    void shouldReturnNotFoundWhenGettingRestaurantByUnknownName() throws Exception {
        when(restaurantController.getByName("Unknown Restaurant"))
                .thenThrow(new RestaurantNotFoundException("Restaurant not found"));

        mockMvc.perform(get("/v1/restaurants/search").param("name", "Unknown Restaurant"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    void shouldReturnNotFoundWhenCreatingRestaurantWithMissingOwner() throws Exception {
        var request = new RestaurantRequestDTO(
                "Rest Test", "Awesome Street, 1001", "Italian",
                "from 6PM - 1AM", RestaurantHelper.OWNER_ID
        );

        when(restaurantMapper.toCreateRestaurantInput(request)).thenReturn(mock(CreateRestaurantInputDTO.class));
        when(restaurantController.create(any())).thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User not found"));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingRestaurantWithInvalidOwnerType() throws Exception {
        var request = new RestaurantRequestDTO(
                "Rest Test", "Awesome Street, 1001", "Italian",
                "from 6PM - 1AM", RestaurantHelper.OWNER_ID
        );

        when(restaurantMapper.toCreateRestaurantInput(request)).thenReturn(mock(CreateRestaurantInputDTO.class));
        when(restaurantController.create(any()))
                .thenThrow(new InvalidRestaurantOwnerTypeException("Only users with type code 'RESTAURANT_OWNER' can own restaurants"));

        mockMvc.perform(post("/v1/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Only users with type code 'RESTAURANT_OWNER' can own restaurants"));
    }

    @Test
    void shouldReturnForbiddenWhenDeletingRestaurantAsNonOwner() throws Exception {
        doThrow(new ActionNotAllowedForRunningUser("Only the restaurant owner can delete the restaurant!"))
                .when(restaurantController).delete(RestaurantHelper.RESTAURANT_ID, "other-user-id");

        mockMvc.perform(delete("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID)
                        .param("runningUserId", "other-user-id"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.detail").value("Only the restaurant owner can delete the restaurant!"));
    }

    @Test
    void shouldReturnNotFoundWhenDeletingWithMissingRunningUser() throws Exception {
        doThrow(new UserNotFoundException("User not found"))
                .when(restaurantController).delete(RestaurantHelper.RESTAURANT_ID, "missing-user-id");

        mockMvc.perform(delete("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID)
                        .param("runningUserId", "missing-user-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("User not found"));
    }

    @Test
    void shouldUpdateRestaurant() throws Exception {
        var request = new UpdateRestaurantRequestDTO(
                "New Name", "New Address, 999", "Japanese",
                "from 5PM - 11PM", RestaurantHelper.OWNER_ID
        );
        var restaurant = RestaurantHelper.createRestaurant();
        var response = new RestaurantResponseDTO(
                restaurant.getId(), request.name(), request.address(),
                request.cuisineType(), request.openingHours(), request.ownerId()
        );

        when(restaurantMapper.toUpdateRestaurantInput(request)).thenReturn(mock(UpdateRestaurantInputDTO.class));
        when(restaurantController.update(any(), eq(RestaurantHelper.RESTAURANT_ID))).thenReturn(restaurant);
        when(restaurantMapper.toRestaurantResponseDTO(restaurant)).thenReturn(response);

        mockMvc.perform(put("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(request.name()));
    }

    @Test
    void shouldListAllRestaurants() throws Exception {
        var restaurant = RestaurantHelper.createRestaurant();
        var response = new RestaurantResponseDTO(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getCuisineType(), restaurant.getOpeningHours(), restaurant.getOwnerId()
        );

        when(restaurantController.listAll()).thenReturn(List.of(restaurant));
        when(restaurantMapper.toRestaurantResponseDTO(restaurant)).thenReturn(response);

        mockMvc.perform(get("/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(restaurant.getName()));
    }

    @Test
    void shouldGetRestaurantByName() throws Exception {
        var restaurant = RestaurantHelper.createRestaurant();
        var response = new RestaurantResponseDTO(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getCuisineType(), restaurant.getOpeningHours(), restaurant.getOwnerId()
        );

        when(restaurantController.getByName(restaurant.getName())).thenReturn(restaurant);
        when(restaurantMapper.toRestaurantResponseDTO(restaurant)).thenReturn(response);

        mockMvc.perform(get("/v1/restaurants/search").param("name", restaurant.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(restaurant.getName()));
    }

    @Test
    void shouldGetRestaurantById() throws Exception {
        var restaurant = RestaurantHelper.createRestaurant();
        var response = new RestaurantResponseDTO(
                restaurant.getId(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getCuisineType(), restaurant.getOpeningHours(), restaurant.getOwnerId()
        );

        when(restaurantController.getById(RestaurantHelper.RESTAURANT_ID)).thenReturn(restaurant);
        when(restaurantMapper.toRestaurantResponseDTO(restaurant)).thenReturn(response);

        mockMvc.perform(get("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(restaurant.getId()));
    }

    @Test
    void shouldReturnNotFoundWhenRestaurantDoesNotExist() throws Exception {
        when(restaurantController.getById(RestaurantHelper.RESTAURANT_ID))
                .thenThrow(new RestaurantNotFoundException("Restaurant not found"));

        mockMvc.perform(get("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }

    @Test
    void shouldDeleteRestaurant() throws Exception {
        mockMvc.perform(delete("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID)
                        .param("runningUserId", RestaurantHelper.OWNER_ID))
                .andExpect(status().isOk());

        verify(restaurantController).delete(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.OWNER_ID);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentRestaurant() throws Exception {
        doThrow(new RestaurantNotFoundException("Restaurant not found"))
                .when(restaurantController).delete(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.OWNER_ID);

        mockMvc.perform(delete("/v1/restaurants/{id}", RestaurantHelper.RESTAURANT_ID)
                        .param("runningUserId", RestaurantHelper.OWNER_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Restaurant not found"));
    }
}