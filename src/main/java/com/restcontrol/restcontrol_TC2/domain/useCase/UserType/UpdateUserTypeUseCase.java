package com.restcontrol.restcontrol_TC2.domain.useCase.UserType;

import com.restcontrol.restcontrol_TC2.domain.Adapter.UserType.Input.UpdateUserTypeInput;
import com.restcontrol.restcontrol_TC2.domain.entity.UserType;
import com.restcontrol.restcontrol_TC2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.GetByIdUserTypeInterface;
import com.restcontrol.restcontrol_TC2.domain.gateway.UserType.UpdateUserTypeInterface;

public class UpdateUserTypeUseCase {

    private final UpdateUserTypeInterface updateUserTypeGateway;
    private final GetByIdUserTypeInterface getByIdUserTypeGateway;

    public UpdateUserTypeUseCase(
            UpdateUserTypeInterface updateUserTypeGateway,
            GetByIdUserTypeInterface getByIdUserTypeGateway
    ) {
        this.updateUserTypeGateway = updateUserTypeGateway;
        this.getByIdUserTypeGateway = getByIdUserTypeGateway;
    }

    public UserType execute(UpdateUserTypeInput updateUserTypeInput, String id) {
        var userType = getByIdUserTypeGateway.getById(id);

        if (userType.isEmpty()) {
            throw new UserTypeNotFoundException("User type not found");
        }

        var currentUserType = userType.get();

        var newUserType = UserType.create(
                updateUserTypeInput.name() != null && !updateUserTypeInput.name().isBlank()
                        ? updateUserTypeInput.name()
                        : currentUserType.getName()
        );
        newUserType.setId(currentUserType.getId());

        return updateUserTypeGateway.updateUserType(newUserType, id);
    }
}
