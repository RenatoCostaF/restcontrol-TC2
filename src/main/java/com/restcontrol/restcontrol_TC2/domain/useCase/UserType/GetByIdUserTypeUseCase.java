package com.restcontrol.restcontrol_TC2.domain.useCase.UserType;

import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.GetByIdUserTypeInterface;

public class GetByIdUserTypeUseCase {

    private final GetByIdUserTypeInterface getByIdUserTypeGateway;

    public GetByIdUserTypeUseCase(GetByIdUserTypeInterface getByIdUserTypeGateway) {
        this.getByIdUserTypeGateway = getByIdUserTypeGateway;
    }

    public UserType execute(String id) {
        var userType = getByIdUserTypeGateway.getById(id);

        if (userType.isEmpty()) {
            throw new UserTypeNotFoundException("User type not found");
        }

        return userType.get();
    }
}
