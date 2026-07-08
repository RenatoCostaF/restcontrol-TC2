package com.restcontrol.restcontrol_tc2.domain.controller;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.domain.mapper.RestaurantMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.RestaurantUseCase;
import com.restcontrol.restcontrol_tc2.helper.RestaurantHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Controller de domínio - Restaurant")
class RestaurantControllerTest {

    @Mock
    private RestaurantUseCase restaurantUseCase;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantController restaurantController;

    private Restaurant restaurant;
    private CreateRestaurantInputDTO createInput;
    private UpdateRestaurantInputDTO updateInput;

    @BeforeEach
    void setUp() {
        restaurant = RestaurantHelper.createRestaurant();
        createInput = RestaurantHelper.createRestaurantInputDTO();
        updateInput = RestaurantHelper.updateRestaurantInputDTO();
    }

    @Test
    @DisplayName("Deve criar restaurante delegando ao use case")
    void shouldCreateRestaurant() {
        when(restaurantMapper.toEntity(createInput)).thenReturn(restaurant);
        when(restaurantUseCase.create(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantController.create(createInput);

        assertEquals(restaurant, result);
        verify(restaurantMapper).toEntity(createInput);
        verify(restaurantUseCase).create(restaurant);
    }

    @Test
    @DisplayName("Deve atualizar restaurante delegando ao use case")
    void shouldUpdateRestaurant() {
        when(restaurantMapper.toEntity(updateInput, restaurant.getId())).thenReturn(restaurant);
        when(restaurantUseCase.update(restaurant)).thenReturn(restaurant);

        Restaurant result = restaurantController.update(updateInput, restaurant.getId());

        assertEquals(restaurant, result);
        verify(restaurantMapper).toEntity(updateInput, restaurant.getId());
        verify(restaurantUseCase).update(restaurant);
    }

    @Test
    @DisplayName("Deve listar todos os restaurantes")
    void shouldListAllRestaurants() {
        List<Restaurant> restaurants = List.of(restaurant);
        when(restaurantUseCase.listAll()).thenReturn(restaurants);

        List<Restaurant> result = restaurantController.listAll();

        assertEquals(restaurants, result);
        verify(restaurantUseCase).listAll();
    }

    @Test
    @DisplayName("Deve buscar restaurante por nome")
    void shouldGetRestaurantByName() {
        when(restaurantUseCase.getByName(restaurant.getName())).thenReturn(restaurant);

        Restaurant result = restaurantController.getByName(restaurant.getName());

        assertEquals(restaurant, result);
        verify(restaurantUseCase).getByName(restaurant.getName());
    }

    @Test
    @DisplayName("Deve buscar restaurante por ID")
    void shouldGetRestaurantById() {
        when(restaurantUseCase.getById(restaurant.getId())).thenReturn(restaurant);

        Restaurant result = restaurantController.getById(restaurant.getId());

        assertEquals(restaurant, result);
        verify(restaurantUseCase).getById(restaurant.getId());
    }

    @Test
    @DisplayName("Deve remover restaurante delegando ao use case")
    void shouldDeleteRestaurant() {
        restaurantController.delete(restaurant.getId(), RestaurantHelper.OWNER_ID);

        verify(restaurantUseCase).delete(restaurant.getId(), RestaurantHelper.OWNER_ID);
    }
}
