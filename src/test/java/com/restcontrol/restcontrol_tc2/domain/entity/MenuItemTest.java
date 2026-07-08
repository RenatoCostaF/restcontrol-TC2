package com.restcontrol.restcontrol_tc2.domain.entity;

import com.restcontrol.restcontrol_tc2.domain.exception.InvalidMenuItemException;
import com.restcontrol.restcontrol_tc2.helper.MenuItemTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Entidade MenuItem")
class MenuItemTest {

    @Test
    @DisplayName("Deve criar item de cardápio com dados válidos")
    void shouldCreateMenuItemWithValidData() {
        MenuItem menuItem = MenuItemTestHelper.validMenuItem();

        assertEquals(MenuItemTestHelper.VALID_MENU_ITEM_ID, menuItem.getId());
        assertEquals(MenuItemTestHelper.VALID_NAME, menuItem.getName());
        assertEquals(MenuItemTestHelper.VALID_DESCRIPTION, menuItem.getDescription());
        assertEquals(MenuItemTestHelper.VALID_PRICE, menuItem.getPrice());
        assertEquals(false, menuItem.getAvailableOnlyInRestaurant());
        assertEquals(MenuItemTestHelper.VALID_IMAGE_URL, menuItem.getImageUrl());
        assertEquals(MenuItemTestHelper.VALID_RESTAURANT_ID, menuItem.getRestaurantId());
        assertEquals(true, menuItem.getActive());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o nome for inválido: {0}")
    void shouldThrowWhenNameIsInvalid(String name) {
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, name, MenuItemTestHelper.VALID_DESCRIPTION, MenuItemTestHelper.VALID_PRICE,
                        false, MenuItemTestHelper.VALID_IMAGE_URL, MenuItemTestHelper.VALID_RESTAURANT_ID, true)
        );

        assertEquals("Name is required", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando a descrição for inválida: {0}")
    void shouldThrowWhenDescriptionIsInvalid(String description) {
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, MenuItemTestHelper.VALID_NAME, description, MenuItemTestHelper.VALID_PRICE,
                        false, MenuItemTestHelper.VALID_IMAGE_URL, MenuItemTestHelper.VALID_RESTAURANT_ID, true)
        );

        assertEquals("Description is required", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o preço for nulo")
    void shouldThrowWhenPriceIsNull() {
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, MenuItemTestHelper.VALID_NAME, MenuItemTestHelper.VALID_DESCRIPTION, null,
                        false, MenuItemTestHelper.VALID_IMAGE_URL, MenuItemTestHelper.VALID_RESTAURANT_ID, true)
        );

        assertEquals("Price must be greater than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o preço for zero ou negativo")
    void shouldThrowWhenPriceIsZeroOrNegative() {
        InvalidMenuItemException zeroException = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, MenuItemTestHelper.VALID_NAME, MenuItemTestHelper.VALID_DESCRIPTION, 0.0,
                        false, MenuItemTestHelper.VALID_IMAGE_URL, MenuItemTestHelper.VALID_RESTAURANT_ID, true)
        );
        InvalidMenuItemException negativeException = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, MenuItemTestHelper.VALID_NAME, MenuItemTestHelper.VALID_DESCRIPTION, -1.0,
                        false, MenuItemTestHelper.VALID_IMAGE_URL, MenuItemTestHelper.VALID_RESTAURANT_ID, true)
        );

        assertEquals("Price must be greater than zero", zeroException.getMessage());
        assertEquals("Price must be greater than zero", negativeException.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando o ID do restaurante for inválido: {0}")
    void shouldThrowWhenRestaurantIdIsInvalid(String restaurantId) {
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, MenuItemTestHelper.VALID_NAME, MenuItemTestHelper.VALID_DESCRIPTION,
                        MenuItemTestHelper.VALID_PRICE, false, MenuItemTestHelper.VALID_IMAGE_URL, restaurantId, true)
        );

        assertEquals("Restaurant id is required", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("Deve lançar exceção quando a URL da imagem for inválida: {0}")
    void shouldThrowWhenImageUrlIsInvalid(String imageUrl) {
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, MenuItemTestHelper.VALID_NAME, MenuItemTestHelper.VALID_DESCRIPTION,
                        MenuItemTestHelper.VALID_PRICE, false, imageUrl, MenuItemTestHelper.VALID_RESTAURANT_ID, true)
        );

        assertEquals("Image path is required", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a disponibilidade for nula")
    void shouldThrowWhenAvailabilityIsNull() {
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, MenuItemTestHelper.VALID_NAME, MenuItemTestHelper.VALID_DESCRIPTION,
                        MenuItemTestHelper.VALID_PRICE, null, MenuItemTestHelper.VALID_IMAGE_URL,
                        MenuItemTestHelper.VALID_RESTAURANT_ID, true)
        );

        assertEquals("Availability is required", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o status ativo for nulo")
    void shouldThrowWhenActiveIsNull() {
        InvalidMenuItemException exception = assertThrows(InvalidMenuItemException.class, () ->
                new MenuItem(null, MenuItemTestHelper.VALID_NAME, MenuItemTestHelper.VALID_DESCRIPTION,
                        MenuItemTestHelper.VALID_PRICE, false, MenuItemTestHelper.VALID_IMAGE_URL,
                        MenuItemTestHelper.VALID_RESTAURANT_ID, null)
        );

        assertEquals("Active status is required", exception.getMessage());
    }
}
