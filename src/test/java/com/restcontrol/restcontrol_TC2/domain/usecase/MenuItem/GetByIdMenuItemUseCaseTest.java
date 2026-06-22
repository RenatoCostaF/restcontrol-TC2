package com.restcontrol.restcontrol_TC2.domain.usecase.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.GetByIdMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.GetByIdMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.testSupport.DomainFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetByIdMenuItemUseCaseTest {

    @Mock
    private GetByIdMenuItemInterface getByIdMenuItemGateway;

    @InjectMocks
    private GetByIdMenuItemUseCase getByIdMenuItemUseCase;

    @Test
    @DisplayName("Should return menu item when found")
    void shouldReturnMenuItemWhenFound() {
        var menuItem = DomainFixtures.validMenuItem();
        when(getByIdMenuItemGateway.getById(menuItem.getId())).thenReturn(Optional.of(menuItem));

        var result = getByIdMenuItemUseCase.execute(menuItem.getId());

        assertEquals(menuItem, result);
        verify(getByIdMenuItemGateway).getById(menuItem.getId());
    }

    @Test
    @DisplayName("Should throw when menu item is not found")
    void shouldThrowWhenNotFound() {
        when(getByIdMenuItemGateway.getById("missing-id")).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> getByIdMenuItemUseCase.execute("missing-id"));
    }
}
