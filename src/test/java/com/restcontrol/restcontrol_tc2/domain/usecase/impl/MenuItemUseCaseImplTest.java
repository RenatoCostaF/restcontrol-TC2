package com.restcontrol.restcontrol_tc2.domain.usecase.impl;

import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.exception.RestaurantNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.gateway.MenuItemGateway;
import com.restcontrol.restcontrol_tc2.domain.gateway.RestaurantGateway;
import com.restcontrol.restcontrol_tc2.support.MenuItemTestFixtures;
import org.junit.jupiter.api.BeforeEach;
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
        menuItem = MenuItemTestFixtures.validMenuItem();
    }

    @Test
    void shouldCreateMenuItemWhenRestaurantExists() {
        when(restaurantGateway.getById(menuItem.getRestaurantId())).thenReturn(Optional.of(MenuItemTestFixtures.validRestaurant()));
        when(menuItemGateway.create(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemUseCase.create(menuItem);

        assertEquals(menuItem, result);
        verify(restaurantGateway).getById(menuItem.getRestaurantId());
        verify(menuItemGateway).create(menuItem);
    }

    @Test
    void shouldThrowWhenCreatingMenuItemWithInvalidRestaurant() {
        when(restaurantGateway.getById(menuItem.getRestaurantId())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> menuItemUseCase.create(menuItem));

        verify(menuItemGateway, never()).create(menuItem);
    }

    @Test
    void shouldUpdateMenuItemWhenMenuItemAndRestaurantExist() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.of(menuItem));
        when(restaurantGateway.getById(menuItem.getRestaurantId())).thenReturn(Optional.of(MenuItemTestFixtures.validRestaurant()));
        when(menuItemGateway.update(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemUseCase.update(menuItem);

        assertEquals(menuItem, result);
        verify(menuItemGateway).getById(menuItem.getId());
        verify(restaurantGateway).getById(menuItem.getRestaurantId());
        verify(menuItemGateway).update(menuItem);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistentMenuItem() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> menuItemUseCase.update(menuItem));

        verify(menuItemGateway, never()).update(menuItem);
    }

    @Test
    void shouldListAllMenuItems() {
        List<MenuItem> menuItems = List.of(menuItem);
        when(menuItemGateway.listAll()).thenReturn(menuItems);

        List<MenuItem> result = menuItemUseCase.listAll();

        assertEquals(menuItems, result);
    }

    @Test
    void shouldGetMenuItemById() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.of(menuItem));

        MenuItem result = menuItemUseCase.getById(menuItem.getId());

        assertEquals(menuItem, result);
    }

    @Test
    void shouldThrowWhenGettingNonExistentMenuItem() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> menuItemUseCase.getById(menuItem.getId()));
    }

    @Test
    void shouldDeleteMenuItemWhenExists() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.of(menuItem));

        menuItemUseCase.delete(menuItem.getId());

        verify(menuItemGateway).getById(menuItem.getId());
        verify(menuItemGateway).delete(menuItem.getId());
    }

    @Test
    void shouldThrowWhenDeletingNonExistentMenuItem() {
        when(menuItemGateway.getById(menuItem.getId())).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> menuItemUseCase.delete(menuItem.getId()));

        verify(menuItemGateway, never()).delete(menuItem.getId());
    }
}
