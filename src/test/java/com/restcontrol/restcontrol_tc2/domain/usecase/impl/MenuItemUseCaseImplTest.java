package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.helper.MenuItemTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Use case - MenuItem")
class MenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private MenuItemUseCaseImpl menuItemUseCase;

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItem = MenuItemTestHelper.validMenuItem();
    }

    @Test
    @DisplayName("Deve criar item de cardápio quando o restaurante existir")
    void shouldCreateMenuItemWhenRestaurantExists() {
        when(restaurantGateway.getById(menuItem.getRestaurantId())).thenReturn(Optional.of(MenuItemTestHelper.validRestaurant()));
        when(menuItemGateway.create(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemUseCase.create(menuItem);

        assertEquals(menuItem, result);
        verify(restaurantGateway).getById(menuItem.getRestaurantId());
        verify(menuItemGateway).create(menuItem);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar item com restaurante inexistente")
    void shouldThrowWhenCreatingMenuItemWithInvalidRestaurant() {
        when(restaurantGateway.getById(menuItem.getRestaurantId())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> menuItemUseCase.create(menuItem));

        verify(menuItemGateway, never()).create(menuItem);
    }

    @Test
    @DisplayName("Deve atualizar item quando item e restaurante existirem")
    void shouldUpdateMenuItemWhenMenuItemAndRestaurantExist() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.of(menuItem));
        when(restaurantGateway.getById(menuItem.getRestaurantId())).thenReturn(Optional.of(MenuItemTestHelper.validRestaurant()));
        when(menuItemGateway.update(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemUseCase.update(menuItem);

        assertEquals(menuItem, result);
        verify(menuItemGateway).getById(menuItem.getId());
        verify(restaurantGateway).getById(menuItem.getRestaurantId());
        verify(menuItemGateway).update(menuItem);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar item inexistente")
    void shouldThrowWhenUpdatingNonExistentMenuItem() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> menuItemUseCase.update(menuItem));

        verify(menuItemGateway, never()).update(menuItem);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar item com restaurante inexistente")
    void shouldThrowWhenUpdatingMenuItemWithInvalidRestaurant() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.of(menuItem));
        when(restaurantGateway.getById(menuItem.getRestaurantId())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> menuItemUseCase.update(menuItem));

        verify(menuItemGateway, never()).update(menuItem);
    }

    @Test
    @DisplayName("Deve listar todos os itens de cardápio")
    void shouldListAllMenuItems() {
        List<MenuItem> menuItems = List.of(menuItem);
        when(menuItemGateway.listAll()).thenReturn(menuItems);

        List<MenuItem> result = menuItemUseCase.listAll();

        assertEquals(menuItems, result);
    }

    @Test
    @DisplayName("Deve buscar item de cardápio por ID")
    void shouldGetMenuItemById() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.of(menuItem));

        MenuItem result = menuItemUseCase.getById(menuItem.getId());

        assertEquals(menuItem, result);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar item inexistente")
    void shouldThrowWhenGettingNonExistentMenuItem() {
        String menuItemId = menuItem.getId();
        when(menuItemGateway.getById(menuItemId)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> menuItemUseCase.getById(menuItemId));
    }

    @Test
    @DisplayName("Deve remover item de cardápio quando existir")
    void shouldDeleteMenuItemWhenExists() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.of(menuItem));

        menuItemUseCase.delete(menuItem.getId());

        verify(menuItemGateway).getById(menuItem.getId());
        verify(menuItemGateway).delete(menuItem.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover item inexistente")
    void shouldThrowWhenDeletingNonExistentMenuItem() {
        String menuItemId = menuItem.getId();
        when(menuItemGateway.getById(menuItemId)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> menuItemUseCase.delete(menuItemId));

        verify(menuItemGateway, never()).delete(menuItemId);
    }
}
