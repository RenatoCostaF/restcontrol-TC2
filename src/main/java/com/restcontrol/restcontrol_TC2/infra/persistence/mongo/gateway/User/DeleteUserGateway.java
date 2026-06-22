package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway.User;

import com.restcontrol.restcontrol_TC2.domain.gateway.User.DeleteUserInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserGateway implements DeleteUserInterface {

    private final UserRepository userRepository;

    public DeleteUserGateway(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
