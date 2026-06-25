package com.restcontrol.restcontrol_tc2.infra.service;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateUserTypeInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UserTypeInput;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;
import com.restcontrol.restcontrol_tc2.domain.port.input.UserTypeInputPort;
import com.restcontrol.restcontrol_tc2.domain.port.output.UserTypeOutputPort;
import com.restcontrol.restcontrol_tc2.domain.usecase.UserTypeUseCase;
import org.springframework.stereotype.Service;

@Service
public class UserTypeApplicationService implements UserTypeInputPort {

    private final UserTypeUseCase userTypeUseCase;

    public UserTypeApplicationService(UserTypeOutputPort userTypeOutputPort) {
        this.userTypeUseCase = new UserTypeUseCase(userTypeOutputPort);
    }

    @Override
    public UserType create(UserTypeInput userTypeInput) {
        return userTypeUseCase.create(userTypeInput);
    }

    @Override
    public UserType update(UpdateUserTypeInput updateUserTypeInput, String id) {
        return userTypeUseCase.update(updateUserTypeInput, id);
    }

    @Override
    public UserType getById(String id) {
        return userTypeUseCase.getById(id);
    }

    @Override
    public void delete(String id) {
        userTypeUseCase.delete(id);
    }
}
