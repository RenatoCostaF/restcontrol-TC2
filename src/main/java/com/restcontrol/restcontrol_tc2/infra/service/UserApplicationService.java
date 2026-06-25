package com.restcontrol.restcontrol_tc2.infra.service;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateUserInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UserInput;
import com.restcontrol.restcontrol_tc2.domain.entity.User;
import com.restcontrol.restcontrol_tc2.domain.port.input.UserInputPort;
import com.restcontrol.restcontrol_tc2.domain.port.output.UserOutputPort;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserUseCase;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService implements UserInputPort {

    private final UserUseCase userUseCase;

    public UserApplicationService(UserOutputPort userOutputPort) {
        this.userUseCase = new UserUseCase(userOutputPort);
    }

    @Override
    public User create(UserInput userInput) {
        return userUseCase.create(userInput);
    }

    @Override
    public User update(UpdateUserInput updateUserInput, String id) {
        return userUseCase.update(updateUserInput, id);
    }

    @Override
    public User getById(String id) {
        return userUseCase.getById(id);
    }

    @Override
    public void delete(String id) {
        userUseCase.delete(id);
    }
}
