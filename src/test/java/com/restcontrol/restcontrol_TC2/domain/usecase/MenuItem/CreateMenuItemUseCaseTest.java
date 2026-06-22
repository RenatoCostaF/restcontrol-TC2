package com.restcontrol.restcontrol_TC2.domain.usecase.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.Adapter.MenuItem.Input.MenuItemInput;
import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.exception.InvalidMenuItemException;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.CreateMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.CreateMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.testSupport.DomainFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateMenuItemUseCaseTest {

    @Mock
    private CreateMenuItemInterface createMenuItemGateway;

    @InjectMocks
    private CreateMenuItemUseCase createMenuItemUseCase;

    @Test
    @DisplayName("Should create menu item and persist through gateway")
    void shouldCreateAndPersistMenuItem() {
        var restaurantId = UUID.randomUUID().toString();
        var input = new MenuItemInput(
                "Pizza Margherita",
                "Classic pizza",
                29.90,
                true,
                "https://example.com/pizza.jpg",
                restaurantId,
                false
        );
        var persisted = DomainFixtures.validMenuItem();
        when(createMenuItemGateway.createMenuItem(any(MenuItem.class))).thenReturn(persisted);

        var result = createMenuItemUseCase.execute(input);

        assertEquals(persisted, result);

        var captor = ArgumentCaptor.forClass(MenuItem.class);
        verify(createMenuItemGateway).createMenuItem(captor.capture());
        var created = captor.getValue();
        assertEquals("Pizza Margherita", created.getName());
        assertEquals("Classic pizza", created.getDescription());
        assertEquals(29.90, created.getPrice());
        assertEquals(restaurantId, created.getRestaurantId());
        assertFalse(created.getActive());
    }

    @Test
    @DisplayName("Should reject invalid input before calling gateway")
    void shouldRejectInvalidInput() {
        var input = new MenuItemInput(null, null, 10.0, false, null, UUID.randomUUID().toString(), false);

        assertThrows(InvalidMenuItemException.class, () -> createMenuItemUseCase.execute(input));
    }
}
