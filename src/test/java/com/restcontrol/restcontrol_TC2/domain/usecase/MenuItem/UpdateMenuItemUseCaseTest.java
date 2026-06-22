package com.restcontrol.restcontrol_TC2.domain.usecase.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.Adapter.MenuItem.Input.UpdateMenuItemInput;
import com.restcontrol.restcontrol_TC2.domain.entity.MenuItem;
import com.restcontrol.restcontrol_TC2.domain.exception.MenuItemNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.GetByIdMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.UpdateMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.UpdateMenuItemUseCase;
import com.restcontrol.restcontrol_TC2.testSupport.DomainFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateMenuItemUseCaseTest {

    @Mock
    private UpdateMenuItemInterface updateMenuItemGateway;

    @Mock
    private GetByIdMenuItemInterface getByIdMenuItemGateway;

    @InjectMocks
    private UpdateMenuItemUseCase updateMenuItemUseCase;

    @Test
    @DisplayName("Should merge provided fields and keep current values for null inputs")
    void shouldMergeProvidedFields() {
        var current = DomainFixtures.validMenuItem();
        when(getByIdMenuItemGateway.getById(current.getId())).thenReturn(Optional.of(current));
        when(updateMenuItemGateway.updateMenuItem(any(MenuItem.class), eq(current.getId())))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var newRestaurantId = UUID.randomUUID().toString();
        var input = new UpdateMenuItemInput(
                "Updated Name",
                null,
                39.90,
                null,
                "https://example.com/updated.jpg",
                newRestaurantId,
                true
        );

        var result = updateMenuItemUseCase.execute(input, current.getId());

        assertEquals("Updated Name", result.getName());
        assertEquals(current.getDescription(), result.getDescription());
        assertEquals(39.90, result.getPrice());
        assertEquals(current.getAvailableForDelivery(), result.getAvailableForDelivery());
        assertEquals("https://example.com/updated.jpg", result.getImageUrl());
        assertEquals(newRestaurantId, result.getRestaurantId());
        assertTrue(result.getActive());
        assertEquals(current.getId(), result.getId());
    }

    @Test
    @DisplayName("Should keep current name when input name is blank")
    void shouldKeepCurrentNameWhenInputIsBlank() {
        var current = DomainFixtures.validMenuItem();
        when(getByIdMenuItemGateway.getById(current.getId())).thenReturn(Optional.of(current));
        when(updateMenuItemGateway.updateMenuItem(any(MenuItem.class), eq(current.getId())))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = updateMenuItemUseCase.execute(
                new UpdateMenuItemInput("   ", null, null, null, null, null, null),
                current.getId()
        );

        assertEquals(current.getName(), result.getName());
    }

    @Test
    @DisplayName("Should throw when menu item is not found")
    void shouldThrowWhenNotFound() {
        when(getByIdMenuItemGateway.getById("missing-id")).thenReturn(Optional.empty());

        assertThrows(
                MenuItemNotFoundException.class,
                () -> updateMenuItemUseCase.execute(
                        new UpdateMenuItemInput("Name", null, 10.0, false, null, UUID.randomUUID().toString(), false),
                        "missing-id"
                )
        );
    }

    @Test
    @DisplayName("Should preserve entity id when updating")
    void shouldPreserveEntityId() {
        var current = DomainFixtures.validMenuItem();
        when(getByIdMenuItemGateway.getById(current.getId())).thenReturn(Optional.of(current));
        when(updateMenuItemGateway.updateMenuItem(any(MenuItem.class), eq(current.getId())))
                .thenAnswer(invocation -> invocation.getArgument(0));

        updateMenuItemUseCase.execute(
                new UpdateMenuItemInput("New Name", null, null, null, null, null, null),
                current.getId()
        );

        var captor = ArgumentCaptor.forClass(MenuItem.class);
        verify(updateMenuItemGateway).updateMenuItem(captor.capture(), eq(current.getId()));
        assertEquals(current.getId(), captor.getValue().getId());
    }
}
