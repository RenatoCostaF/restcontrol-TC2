package com.restcontrol.restcontrol_tc2.domain.port.input;

import com.restcontrol.restcontrol_tc2.domain.usecase.input.UpdateUserInput;
import com.restcontrol.restcontrol_tc2.domain.usecase.input.UserInput;
import com.restcontrol.restcontrol_tc2.domain.entity.User;

public interface UserInputPort {

    User create(UserInput userInput);

    User update(UpdateUserInput updateUserInput, String id);

    User getById(String id);

    void delete(String id);
}
