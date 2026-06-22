package com.restcontrol.restcontrol_TC2.domain.usecase.UserType;

import com.restcontrol.restcontrol_TC2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.GetByIdUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.useCase.UserType.GetByIdUserTypeUseCase;
import com.restcontrol.restcontrol_TC2.testsupport.DomainFixtures;
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
class GetByIdUserTypeUseCaseTest {

    @Mock
    private GetByIdUserTypeInterface getByIdUserTypeGateway;

    @InjectMocks
    private GetByIdUserTypeUseCase getByIdUserTypeUseCase;

    @Test
    @DisplayName("Should return user type when found")
    void shouldReturnUserTypeWhenFound() {
        var userType = DomainFixtures.validUserType();
        when(getByIdUserTypeGateway.getById(userType.getId())).thenReturn(Optional.of(userType));

        var result = getByIdUserTypeUseCase.execute(userType.getId());

        assertEquals(userType, result);
        verify(getByIdUserTypeGateway).getById(userType.getId());
    }

    @Test
    @DisplayName("Should throw when user type is not found")
    void shouldThrowWhenNotFound() {
        when(getByIdUserTypeGateway.getById("missing-id")).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> getByIdUserTypeUseCase.execute("missing-id"));
    }
}
