package com.restcontrol.restcontrol_tc2.domain.mapper;

import com.restcontrol.restcontrol_tc2.domain.entity.Restaurant;
import com.restcontrol.restcontrol_tc2.helper.RestaurantHelper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.restcontrol.restcontrol_tc2.domain.dto.CreateRestaurantInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateRestaurantInputDTO;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantMapperTest {

    private RestaurantMapper restaurantMapper;

    @BeforeEach
    void setup(){
        restaurantMapper = new RestaurantMapper();
    }

    @Test
    @DisplayName("Deve mapear CreateRestaurantInputDTO para entidade Restaurant")
    void shouldMapCreateInputToRestaurant(){
        CreateRestaurantInputDTO input = RestaurantHelper.createRestaurantInputDTO();

        Restaurant restaurant = restaurantMapper.toEntity(input);

        assertEquals(input.name(), restaurant.getName());
        assertEquals(input.address(), restaurant.getAddress());
        assertEquals(input.cuisineType(), restaurant.getCuisineType());
        assertEquals(input.openingHours(), restaurant.getOpeningHours());
        assertEquals(input.ownerId(), restaurant.getOwnerId());
    }

    @Test
    @DisplayName("Deve mapear UpdateRestaurantInputDTO para entidade Restaurant")
    void shouldMapUpdateInputToRestaurant(){
        String restaurantId = new ObjectId().toHexString();
        UpdateRestaurantInputDTO input = RestaurantHelper.updateRestaurantInputDTO();

        Restaurant restaurant = restaurantMapper.toEntity(input, restaurantId);

        assertEquals(input.name(), restaurant.getName());
        assertEquals(input.address(), restaurant.getAddress());
        assertEquals(input.cuisineType(), restaurant.getCuisineType());
        assertEquals(input.openingHours(), restaurant.getOpeningHours());
        assertEquals(input.ownerId(), restaurant.getOwnerId());
    }
}