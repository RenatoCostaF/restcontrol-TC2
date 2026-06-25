package com.restcontrol.restcontrol_tc2.domain.usecase;

import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.exception.UserNotFoundException;
import com.restcontrol.restcontrol_tc2.domain.port.input.UserInputPort;
import com.restcontrol.restcontrol_tc2.domain.port.output.UserOutputPort;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateUserInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UserInput;

public class UserUseCase implements UserInputPort {

    private final UserOutputPort userOutputPort;

    public UserUseCase(UserOutputPort userOutputPort) {
        this.userOutputPort = userOutputPort;
    }


    public User create(UserInput userInput) {
        var newUser = User.create(
                userInput.name(),
                userInput.email(),
                userInput.password(),
                userInput.userTypeId()
        );

        return userOutputPort.create(newUser);
    }

    public User update(UpdateUserInput updateUserInput, String id) {
        var user = userOutputPort.getById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

        updateNameIfPresent(user, updateUserInput);
        updateEmailIfPresent(user, updateUserInput);
        updatePasswordIfPresent(user, updateUserInput);
        updateUserTypeIfPresent(user, updateUserInput);

        return userOutputPort.update(user, id);
    }

    public User getById(String id) {
        var user = userOutputPort.getById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        return user.get();
    }

    public void delete(String id) {
        var existingUser = userOutputPort.getById(id);

        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("The user you are trying to delete doesn't exist");
        }
        userOutputPort.delete(id);
    }

    private void updateNameIfPresent(User user, UpdateUserInput input) {
        if (hasText(input.name())) {
            user.rename(input.name());
        }
    }

    private void updateEmailIfPresent(User user, UpdateUserInput input) {
        if (hasText(input.email())) {
            user.changeEmail(input.email());
        }
    }

    private void updatePasswordIfPresent(User user, UpdateUserInput input) {
        if (hasText(input.password())) {
            user.changePassword(input.password());
        }
    }

    private void updateUserTypeIfPresent(User user, UpdateUserInput input) {
        if (hasText(input.userTypeId())) {
            user.changeUserType(input.userTypeId());
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
