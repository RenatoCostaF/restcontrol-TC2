package com.restcontrol.restcontrol_TC2.domain.entity;

import com.restcontrol.restcontrol_TC2.domain.exception.InvalidMenuItemException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuItemTest {

    private static final String RESTAURANT_ID = UUID.randomUUID().toString();

    @Nested
    @DisplayName("create")
    class Create {

        @Test
        @DisplayName("Should create menu item with all fields")
        void shouldCreateWithAllFields() {
            var menuItem = MenuItem.create(
                    "Pizza Margherita",
                    "Classic pizza",
                    29.90,
                    true,
                    "https://example.com/pizza.jpg",
                    RESTAURANT_ID,
                    true
            );

            assertNull(menuItem.getId());
            assertEquals("Pizza Margherita", menuItem.getName());
            assertEquals("Classic pizza", menuItem.getDescription());
            assertEquals(29.90, menuItem.getPrice());
            assertTrue(menuItem.getAvailableForDelivery());
            assertEquals("https://example.com/pizza.jpg", menuItem.getImageUrl());
            assertEquals(RESTAURANT_ID, menuItem.getRestaurantId());
            assertTrue(menuItem.getActive());
        }

        @Test
        @DisplayName("Should default availableForDelivery and isActive to false when null")
        void shouldApplyDefaultsWhenOptionalFlagsAreNull() {
            var menuItem = MenuItem.create(
                    "Salad",
                    "Fresh greens",
                    15.00,
                    null,
                    null,
                    RESTAURANT_ID,
                    null
            );

            assertFalse(menuItem.getAvailableForDelivery());
            assertFalse(menuItem.getActive());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   "})
        @DisplayName("Should reject invalid name")
        void shouldRejectInvalidName(String name) {
            var exception = assertThrows(
                    InvalidMenuItemException.class,
                    () -> MenuItem.create(name, null, 10.0, false, null, RESTAURANT_ID, false)
            );

            assertEquals("Name is required", exception.getMessage());
        }

        @Test
        @DisplayName("Should reject null price")
        void shouldRejectNullPrice() {
            var exception = assertThrows(
                    InvalidMenuItemException.class,
                    () -> MenuItem.create("Burger", null, null, false, null, RESTAURANT_ID, false)
            );

            assertEquals("Price must be greater than zero", exception.getMessage());
        }

        @ParameterizedTest
        @ValueSource(doubles = {0.0, -1.0})
        @DisplayName("Should reject non-positive price")
        void shouldRejectNonPositivePrice(double price) {
            var exception = assertThrows(
                    InvalidMenuItemException.class,
                    () -> MenuItem.create("Burger", null, price, false, null, RESTAURANT_ID, false)
            );

            assertEquals("Price must be greater than zero", exception.getMessage());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   "})
        @DisplayName("Should reject invalid restaurant id")
        void shouldRejectInvalidRestaurantId(String restaurantId) {
            var exception = assertThrows(
                    InvalidMenuItemException.class,
                    () -> MenuItem.create("Burger", null, 10.0, false, null, restaurantId, false)
            );

            assertEquals("Restaurant id is required", exception.getMessage());
        }

        @Test
        @DisplayName("Should allow activation when required fields are valid")
        void shouldAllowActivationWhenDataIsValid() {
            var menuItem = MenuItem.create("Burger", null, 10.0, false, null, RESTAURANT_ID, true);

            assertTrue(menuItem.getActive());
        }
    }

    @Nested
    @DisplayName("canActivate")
    class CanActivate {

        @Test
        @DisplayName("Should return false for null item")
        void shouldReturnFalseForNullItem() {
            assertFalse(MenuItem.canActivate(null));
        }

        @Test
        @DisplayName("Should return true when all required fields are valid")
        void shouldReturnTrueForValidItem() {
            var menuItem = MenuItem.create("Burger", null, 10.0, false, null, RESTAURANT_ID, false);

            assertTrue(MenuItem.canActivate(menuItem));
        }

        @Test
        @DisplayName("Should return false when name is blank")
        void shouldReturnFalseWhenNameIsBlank() {
            var menuItem = new MenuItem();
            menuItem.setName("   ");
            menuItem.setPrice(10.0);
            menuItem.setRestaurantId(RESTAURANT_ID);

            assertFalse(MenuItem.canActivate(menuItem));
        }

        @Test
        @DisplayName("Should return false when price is invalid")
        void shouldReturnFalseWhenPriceIsInvalid() {
            var menuItem = new MenuItem();
            menuItem.setName("Burger");
            menuItem.setPrice(0.0);
            menuItem.setRestaurantId(RESTAURANT_ID);

            assertFalse(MenuItem.canActivate(menuItem));
        }

        @Test
        @DisplayName("Should return false when restaurant id is blank")
        void shouldReturnFalseWhenRestaurantIdIsBlank() {
            var menuItem = new MenuItem();
            menuItem.setName("Burger");
            menuItem.setPrice(10.0);
            menuItem.setRestaurantId("  ");

            assertFalse(MenuItem.canActivate(menuItem));
        }
    }
}
