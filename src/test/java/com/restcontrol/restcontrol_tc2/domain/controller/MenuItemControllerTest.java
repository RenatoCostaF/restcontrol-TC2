package com.restcontrol.restcontrol_tc2.domain.controller;

import com.restcontrol.restcontrol_tc2.domain.dto.CreateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.dto.UpdateMenuItemInputDTO;
import com.restcontrol.restcontrol_tc2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_tc2.domain.mapper.MenuItemMapper;
import com.restcontrol.restcontrol_tc2.domain.usecase.MenuItemUseCase;
import com.restcontrol.restcontrol_tc2.support.MenuItemTestFixtures;
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
@DisplayName("Controller de domínio - MenuItem")
class MenuItemControllerTest {

    @Mock
    private MenuItemUseCase menuItemUseCase;

    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private MenuItemController menuItemController;

    private MenuItem menuItem;
    private CreateMenuItemInputDTO createInput;
    private UpdateMenuItemInputDTO updateInput;

    @BeforeEach
    void setUp() {
        menuItem = MenuItemTestFixtures.validMenuItem();
        createInput = MenuItemTestFixtures.createMenuItemInput();
        updateInput = MenuItemTestFixtures.updateMenuItemInput();
    }

    @Test
    @DisplayName("Deve criar item de cardápio delegando ao use case")
    void shouldCreateMenuItem() {
        when(menuItemMapper.toEntity(createInput)).thenReturn(menuItem);
        when(menuItemUseCase.create(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemController.create(createInput);

        assertEquals(menuItem, result);
        verify(menuItemMapper).toEntity(createInput);
        verify(menuItemUseCase).create(menuItem);
    }

    @Test
    @DisplayName("Deve atualizar item de cardápio delegando ao use case")
    void shouldUpdateMenuItem() {
        when(menuItemMapper.toEntity(updateInput, menuItem.getId())).thenReturn(menuItem);
        when(menuItemUseCase.update(menuItem)).thenReturn(menuItem);

        MenuItem result = menuItemController.update(updateInput, menuItem.getId());

        assertEquals(menuItem, result);
        verify(menuItemMapper).toEntity(updateInput, menuItem.getId());
        verify(menuItemUseCase).update(menuItem);
    }

    @Test
    @DisplayName("Deve listar todos os itens de cardápio")
    void shouldListAllMenuItems() {
        List<MenuItem> menuItems = List.of(menuItem);
        when(menuItemUseCase.listAll()).thenReturn(menuItems);

        List<MenuItem> result = menuItemController.listAll();

        assertEquals(menuItems, result);
        verify(menuItemUseCase).listAll();
    }

    @Test
    @DisplayName("Deve buscar item de cardápio por ID")
    void shouldGetMenuItemById() {
        when(menuItemUseCase.getById(menuItem.getId())).thenReturn(menuItem);

        MenuItem result = menuItemController.getById(menuItem.getId());

        assertEquals(menuItem, result);
        verify(menuItemUseCase).getById(menuItem.getId());
    }

    @Test
    @DisplayName("Deve remover item de cardápio por ID")
    void shouldDeleteMenuItem() {
        menuItemController.delete(menuItem.getId());

        verify(menuItemUseCase).delete(menuItem.getId());
    }
}
