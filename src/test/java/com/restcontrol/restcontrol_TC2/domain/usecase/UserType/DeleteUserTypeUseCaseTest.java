package com.restcontrol.restcontrol_TC2.domain.usecase.UserType;

import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.DeleteUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.DeleteUserTypeUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserTypeUseCaseTest {

    @Mock
    private DeleteUserTypeInterface deleteUserTypeGateway;

    @InjectMocks
    private DeleteUserTypeUseCase deleteUserTypeUseCase;

    @Test
    @DisplayName("Should delegate deletion to gateway")
    void shouldDelegateDeletionToGateway() {
        deleteUserTypeUseCase.execute("user-type-id");

        verify(deleteUserTypeGateway).deleteUserType("user-type-id");
    }
}
