package com.restcontrol.restcontrol_TC2.domain.usecase.UserType;

import com.restcontrol.restcontrol_TC2.domain.Adapter.UserType.Input.UserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.domain.exception.InvalidUserTypeException;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.CreateUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.CreateUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.testSupport.DomainFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserTypeUseCaseTest {

    @Mock
    private CreateUserTypeInterface createUserTypeGateway;

    @InjectMocks
    private CreateUserTypeUseCase createUserTypeUseCase;

    @Test
    @DisplayName("Should create user type and persist through gateway")
    void shouldCreateAndPersistUserType() {
        var input = new UserTypeInput("Administrator");
        var persisted = DomainFixtures.validUserType();
        when(createUserTypeGateway.createUserType(any(UserType.class))).thenReturn(persisted);

        var result = createUserTypeUseCase.execute(input);

        assertEquals(persisted, result);

        var captor = ArgumentCaptor.forClass(UserType.class);
        verify(createUserTypeGateway).createUserType(captor.capture());
        assertEquals("Administrator", captor.getValue().getName());
    }

    @Test
    @DisplayName("Should reject invalid input before calling gateway")
    void shouldRejectInvalidInput() {
        var input = new UserTypeInput(null);

        assertThrows(InvalidUserTypeException.class, () -> createUserTypeUseCase.execute(input));
    }
}
