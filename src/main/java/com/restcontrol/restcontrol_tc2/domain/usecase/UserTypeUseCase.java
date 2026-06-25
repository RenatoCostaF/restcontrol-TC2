package com.restcontrol.restcontrol_tc2.domain.usecase;

import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.exception.UserTypeNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.port.input.UserTypeInputPort;
import com.restcontrol.restcontrol_tc2.domain.port.output.UserTypeOutputPort;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateUserTypeInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UserTypeInput;

public class UserTypeUseCase implements UserTypeInputPort {

    private final UserTypeOutputPort userTypeOutputPort;

    public UserTypeUseCase(UserTypeOutputPort userTypeOutputPort) {
        this.userTypeOutputPort = userTypeOutputPort;
    }


    public UserType create(UserTypeInput userTypeInput) {
        var newUserType = UserType.create(userTypeInput.name());

        return userTypeOutputPort.create(newUserType);
    }

    public UserType update(UpdateUserTypeInput updateUserTypeInput, String id) {
        var userType = userTypeOutputPort.getById(id).orElseThrow(() -> new UserTypeNotFoundException("User type not found"));

        updateNameIfPresent(userType, updateUserTypeInput);

        return userTypeOutputPort.update(userType, id);
    }

    public UserType getById(String id) {
        var userType = userTypeOutputPort.getById(id);

        if (userType.isEmpty()) {
            throw new UserTypeNotFoundException("User type not found");
        }

        return userType.get();
    }

    public void delete(String id) {
        var userType = userTypeOutputPort.getById(id);

        if (userType.isEmpty()) {
            throw new UserTypeNotFoundException("The userType you are trying to delete doesn't exist");
        }
        userTypeOutputPort.delete(id);
    }

    private void updateNameIfPresent(UserType userType, UpdateUserTypeInput input) {
        if (hasText(input.name())) {
            userType.rename(input.name());
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }

}
