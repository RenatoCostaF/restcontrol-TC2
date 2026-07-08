package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.ActionNotAllowedForRunningUser;
import com.restcontrol.restcontrol_tc2.domain.exception.InvalidRestaurantException;
import com.restcontrol.restcontrol_tc2.helper.RestaurantHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Entidade Restaurant")
class RestaurantTest {

    @Test
    @DisplayName("Deve criar restaurante com dados válidos")
    void shouldCreateRestaurantWithValidData() {
        Restaurant restaurant = RestaurantHelper.createRestaurant();

        assertEquals(RestaurantHelper.RESTAURANT_ID, restaurant.getId());
        assertEquals(restaurant.getName(), restaurant.getName());
        assertEquals(restaurant.getAddress(), restaurant.getAddress());
        assertEquals(restaurant.getCuisineType(), restaurant.getCuisineType());
        assertEquals(restaurant.getOpeningHours(), restaurant.getOpeningHours());
        assertEquals(RestaurantHelper.OWNER_ID, restaurant.getOwnerId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o nome for inválido")
    void shouldThrowWhenNameIsInvalid(String name) {
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class, () ->
                new Restaurant(RestaurantHelper.RESTAURANT_ID, name, RestaurantHelper.RESTAURANT_ADDRESS,
                        RestaurantHelper.RESTAURANT_CUISINE_TYPE, RestaurantHelper.RESTAURANT_OPENING_HOURS,
                        RestaurantHelper.OWNER_ID)
        );

        assertEquals("Name cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o endereço for inválido")
    void shouldThrowWhenAddressIsInvalid(String address) {
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class, () ->
                new Restaurant(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.RESTAURANT_NAME, address,
                        RestaurantHelper.RESTAURANT_CUISINE_TYPE, RestaurantHelper.RESTAURANT_OPENING_HOURS,
                        RestaurantHelper.OWNER_ID)
        );

        assertEquals("Address cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o tipo de culinária for inválido")
    void shouldThrowWhenCuisineTypeIsInvalid(String cuisineType) {
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class, () ->
                new Restaurant(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.RESTAURANT_NAME,
                        RestaurantHelper.RESTAURANT_ADDRESS, cuisineType, RestaurantHelper.RESTAURANT_OPENING_HOURS,
                        RestaurantHelper.OWNER_ID)
        );

        assertEquals("Cuisine type cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o horário de funcionamento for inválido")
    void shouldThrowWhenOpeningHoursIsInvalid(String openingHours) {
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class, () ->
                new Restaurant(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.RESTAURANT_NAME,
                        RestaurantHelper.RESTAURANT_ADDRESS, RestaurantHelper.RESTAURANT_CUISINE_TYPE,
                        openingHours, RestaurantHelper.OWNER_ID)
        );

        assertEquals("Opening hours cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o ownerId for inválido")
    void shouldThrowWhenOwnerIdIsInvalid(String ownerId) {
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class, () ->
                new Restaurant(RestaurantHelper.RESTAURANT_ID, RestaurantHelper.RESTAURANT_NAME,
                        RestaurantHelper.RESTAURANT_ADDRESS, RestaurantHelper.RESTAURANT_CUISINE_TYPE,
                        RestaurantHelper.RESTAURANT_OPENING_HOURS, ownerId)
        );

        assertEquals("OwnerId cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não é o dono do restaurante")
    void shouldThrowWhenUserIsNotOwner() {
        Restaurant restaurant = RestaurantHelper.createRestaurant();
        String differentUserId = "different-user-id";

        ActionNotAllowedForRunningUser exception = assertThrows(ActionNotAllowedForRunningUser.class, () ->
                restaurant.ensureOwnedBy(differentUserId)
        );

        assertEquals("Only the restaurant owner can delete the restaurant!", exception.getMessage());
    }
}
