package com.restcontrol.restcontrol_tc2.domain.port.input;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateUserTypeInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UserTypeInput;
import com.restcontrol.restcontrol_tc2.domain.entity.UserType;

public interface UserTypeInputPort {

    UserType create(UserTypeInput userTypeInput);

    UserType update(UpdateUserTypeInput updateUserTypeInput, String id);

    UserType getById(String id);

    void delete(String id);
}
