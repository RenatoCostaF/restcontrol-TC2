package com.restcontrol.restcontrol_TC2.infra.persistence.mongo.gateway;

import com.restcontrol.restcontrol_TC2.domain.entity.User;
import com.restcontrol.restcontrol_TC2.domain.gateway.User.GetByIdUserInterface;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.mapper.UserMapper;
import com.restcontrol.restcontrol_TC2.infra.persistence.mongo.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GetByIdUserGateway implements GetByIdUserInterface {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public GetByIdUserGateway(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> getById(String id) {
        return userRepository.findById(id).map(userMapper::toDomain);
    }
}
