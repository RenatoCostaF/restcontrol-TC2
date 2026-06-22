package com.restcontrol.restcontrol_TC2.domain.usecase.UserType;

import com.restcontrol.restcontrol_TC2.domain.Adapter.UserType.Input.UpdateUserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.GetByIdUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.UpdateUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.UpdateUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.testSupport.DomainFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserTypeUseCaseTest {

    @Mock
    private UpdateUserTypeInterface updateUserTypeGateway;

    @Mock
    private GetByIdUserTypeInterface getByIdUserTypeGateway;

    @InjectMocks
    private UpdateUserTypeUseCase updateUserTypeUseCase;

    @Test
    @DisplayName("Should update name when a new value is provided")
    void shouldUpdateNameWhenProvided() {
        var current = DomainFixtures.validUserType();
        var updated = UserType.create("Manager");
        updated.setId(current.getId());
        when(getByIdUserTypeGateway.getById(current.getId())).thenReturn(Optional.of(current));
        when(updateUserTypeGateway.updateUserType(any(UserType.class), eq(current.getId()))).thenReturn(updated);

        var result = updateUserTypeUseCase.execute(new UpdateUserTypeInput("Manager"), current.getId());

        assertEquals("Manager", result.getName());

        var captor = ArgumentCaptor.forClass(UserType.class);
        verify(updateUserTypeGateway).updateUserType(captor.capture(), eq(current.getId()));
        assertEquals("Manager", captor.getValue().getName());
        assertEquals(current.getId(), captor.getValue().getId());
    }

    @Test
    @DisplayName("Should keep current name when input name is null")
    void shouldKeepCurrentNameWhenInputIsNull() {
        var current = DomainFixtures.validUserType();
        when(getByIdUserTypeGateway.getById(current.getId())).thenReturn(Optional.of(current));
        when(updateUserTypeGateway.updateUserType(any(UserType.class), eq(current.getId()))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = updateUserTypeUseCase.execute(new UpdateUserTypeInput(null), current.getId());

        assertEquals(current.getName(), result.getName());
    }

    @Test
    @DisplayName("Should keep current name when input name is blank")
    void shouldKeepCurrentNameWhenInputIsBlank() {
        var current = DomainFixtures.validUserType();
        when(getByIdUserTypeGateway.getById(current.getId())).thenReturn(Optional.of(current));
        when(updateUserTypeGateway.updateUserType(any(UserType.class), eq(current.getId()))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = updateUserTypeUseCase.execute(new UpdateUserTypeInput("   "), current.getId());

        assertEquals(current.getName(), result.getName());
    }

    @Test
    @DisplayName("Should throw when user type is not found")
    void shouldThrowWhenNotFound() {
        when(getByIdUserTypeGateway.getById("missing-id")).thenReturn(Optional.empty());

        assertThrows(
                UserTypeNotFoundException.class,
                () -> updateUserTypeUseCase.execute(new UpdateUserTypeInput("Manager"), "missing-id")
        );
    }
}
