package com.restcontrol.restcontrol_TC2.domain.usecase.MenuItem;

import com.restcontrol.restcontrol_TC2.domain.gateway.MenuItem.DeleteMenuItemInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.MenuItem.DeleteMenuItemUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteMenuItemUseCaseTest {

    @Mock
    private DeleteMenuItemInterface deleteMenuItemGateway;

    @InjectMocks
    private DeleteMenuItemUseCase deleteMenuItemUseCase;

    @Test
    @DisplayName("Should delegate deletion to gateway")
    void shouldDelegateDeletionToGateway() {
        deleteMenuItemUseCase.execute("menu-item-id");

        verify(deleteMenuItemGateway).deleteMenuItem("menu-item-id");
    }
}
