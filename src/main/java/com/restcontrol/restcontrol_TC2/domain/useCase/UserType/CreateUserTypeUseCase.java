package com.restcontrol.restcontrol_TC2.domain.useCase.UserType;

import com.restcontrol.restcontrol_TC2.domain.Adapter.UserType.Input.UserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.CreateUserTypeInterface;

public class CreateUserTypeUseCase {

    private final CreateUserTypeInterface createUserTypeGateway;

    public CreateUserTypeUseCase(CreateUserTypeInterface createUserTypeGateway) {
        this.createUserTypeGateway = createUserTypeGateway;
    }

    public UserType execute(UserTypeInput userTypeInput) {
        var newUserType = UserType.create(userTypeInput.name());

        return createUserTypeGateway.createUserType(newUserType);
    }
}
